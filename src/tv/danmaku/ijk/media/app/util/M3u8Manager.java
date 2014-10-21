package tv.danmaku.ijk.media.app.util;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.util.Log;

import com.tvplayer.util.StringUtils;

public class M3u8Manager{
	
	public static final int TYPE_LIVE = 1;
	public static final int TYPE_TIMESHIFT = 2;
	public static final int TYPE_PLAYBACK = 3;
	
	private static final long TIME_PERIOID_LIVE = 5*1000;
	private static final long TIME_PERIOID_TIMESHIFT = 60*1000;
	private static final int mTimeShift = 60;//60s
	private Context mContext;
	private OnCompletionListerner mOnCompletionListerner;
	private M3u8TimerTask mM3u8TimerTask;
	private Timer mTimer;
	private String mNetWorkUrl;
	private int mPlayerType;
	private long endTime;
	private long startTime;
	private String mUrl;
	
	public M3u8Manager(Context context) {
		// TODO Auto-generated constructor stub
		mContext = context;
	}
	
	public void start(String url,int playerType){
		reset();
		mPlayerType = playerType;
		mTimer = new Timer();
		mM3u8TimerTask = new M3u8TimerTask();
		mUrl = url;
		if(mPlayerType == TYPE_LIVE){
			mNetWorkUrl = url;
			mTimer.schedule(mM3u8TimerTask, 0, TIME_PERIOID_LIVE);
		}
		else if(mPlayerType == TYPE_TIMESHIFT){
//			mNetWorkUrl = getTimeShiftUrl(url);
			mTimer.schedule(mM3u8TimerTask, 0, TIME_PERIOID_TIMESHIFT);
		}
	}
	
	public void stop(){
		reset();
	}
	
	public void reset(){
		if(mTimer != null){
			mTimer.cancel();
			mTimer = null;
		}
		if(mM3u8TimerTask != null){
			mM3u8TimerTask.cancel();
			mM3u8TimerTask = null;
		}
		mPlayerType = TYPE_LIVE;
		mNetWorkUrl = null;
		startTime = 0;
		endTime = 0;
		deleteLocalUrl();
	}
	
	public void setOnCompeleteListerner(OnCompletionListerner listerner){
		mOnCompletionListerner = listerner;
	}
	
	private String requstM3u8(String url){
		if(url == null){
			return null;
		}
		String response = HttpUtil.postHttpClient(url, null, null);
		return parseM3u8(response);
	}
	
	/**
	 *  解析M3U8:
	 *  http://124.161.62.197:9000/TV/0/00000000000000050000000000000014/9.m3u8
	 *  http://124.161.62.197:9000/TV/0/00000000000000050000000000000014/9.m3u8?bitrate=auto&tvodstarttime=1410364800&tvodendtime=1410368400
	 *	http://124.161.62.197:9000/TV/0/00000000000000050000000000000014/9.m3u8?tvodstarttime=1410364800&tvodendtime=1410368400
	 *
	   	直播URL:
	  	#EXTM3U
		#EXT-X-STREAM-INF:PROGRAM-ID=1,BANDWIDTH=3053568
		http://124.161.62.197:9000/TV/0/00000000000000050000000000000014/9.m3u8?tvodstarttime=1410364800&tvodendtime=1410368400
		
		直播嵌套URL:
		#EXTM3U
		#EXT-X-MEDIA-SEQUENCE:141385986
		#EXT-X-TARGETDURATION:10
		#EXTINF:10,
		http://124.161.62.197:9000/VOD/tvod_root/0/00000000000000050000000000000014/9/1413849600/1413856800/1413859860.ts
		#EXTINF:10,
		http://124.161.62.197:9000/VOD/tvod_root/0/00000000000000050000000000000014/9/1413849600/1413856800/1413859870.ts
		#EXTINF:10,
		http://124.161.62.197:9000/VOD/tvod_root/0/00000000000000050000000000000014/9/1413849600/1413856800/1413859880.ts
	 * @param response
	 * @return
	 */
	private String parseM3u8(String response){
		// may be error
		if(StringUtils.isEmpty(response))
			return null;
		
		Log.d("Debug","response before:"+response.toString());
		if(mPlayerType == TYPE_TIMESHIFT){
			if(response.contains("#EXT-X-ENDLIST")){
//				response = response.replace("#EXT-X-MEDIA-SEQUENCE:0", "#EXT-X-MEDIA-SEQUENCE:"+startTime/10);
//				response = response.replace("#EXT-X-ENDLIST", "");
				Log.d("Debug","response:"+response);
			}
		}
		
//		String[] params = response.split("#");
//		for(int i = 0; i < params.length; i++){
////			Log.d("Debug","get params["+i+"]"+":"+params[i]);
//			
//			// choice the default url
//			if(params[i].contains("EXT-X-STREAM-INF")){
//				String[] array = params[i].split("\n");
//				mNetWorkUrl = array[1].trim();
//				Log.d("Debug","nextUrl:"+mNetWorkUrl);
//				return requstM3u8(mNetWorkUrl);
//			}
//			
//			if(mPlayerType == TYPE_TIMESHIFT){
//				if(response.contains("#EXT-X-ENDLIST")){
//					response = response.replace("#EXT-X-ENDLIST", "");
//					Log.d("Debug","response:"+response);
//				}
//			}
//		}
		String localUrl = createLocalUrl(response);
		Log.d("Debug","localUrl:"+localUrl);
		return localUrl;
	}
	
	private String createLocalUrl(String response){
		Cache.writeCacheData(getLocalPath(), response);
		String localUrl ="file://"+getLocalPath();
		return localUrl;
	}
	
	private void deleteLocalUrl(){
		File file = new File(getLocalPath());
		if(file.exists())
			file.delete();
	}
	
	private String getLocalPath(){
		return "/mnt/sdcard/test.m3u8";
//		return mContext.getCacheDir()+"/"+"test.m3u8";
	}
	
	private long getCurrentTime(){
		return System.currentTimeMillis();
	}
	
	private String getTimeShiftUrl(String url){
		StringBuffer sb = new StringBuffer(url);
		if(endTime == 0){
			// the firt time
			endTime = getCurrentTime()/1000;
			startTime = endTime - 60;
		}else{
			// next time
			startTime = endTime;
			endTime += TIME_PERIOID_TIMESHIFT/1000;
		}
		
		if(url.contains("?")){
			sb.append("&tvodstarttime="+startTime);
			sb.append("&tvodendtime="+endTime);
		}else{
			sb.append("?tvodstarttime="+startTime);
			sb.append("&tvodendtime="+endTime);
		}
		Log.d("Debug","getTimeShiftUrl:"+sb.toString());
		return sb.toString();
	}
	
	private class M3u8TimerTask extends TimerTask{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Log.d("Debug","M3u8TimerTask run:"+mPlayerType+" url:"+mNetWorkUrl);
			String localUrl = null;
			if(mPlayerType == TYPE_LIVE)
				localUrl = requstM3u8(mUrl);
			else if(mPlayerType == TYPE_TIMESHIFT){
				mNetWorkUrl = getTimeShiftUrl(mUrl);
				localUrl = requstM3u8(mNetWorkUrl);
			}
			if(mOnCompletionListerner != null){
				if(!StringUtils.isEmpty(localUrl))
					mOnCompletionListerner.onCompletion(localUrl);
			}
		}
	};
	
	public static interface OnCompletionListerner{
		public void onCompletion(String url);
	}

}
