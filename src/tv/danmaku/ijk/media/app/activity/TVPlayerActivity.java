package tv.danmaku.ijk.media.app.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import tv.danmaku.ijk.media.app.bean.ChannelBean;
import tv.danmaku.ijk.media.app.util.M3u8Manager;
import tv.danmaku.ijk.media.app.util.M3u8Manager.OnCompletionListerner;
import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IMediaPlayer.OnCompletionListener;
import tv.danmaku.ijk.media.player.IMediaPlayer.OnErrorListener;
import tv.danmaku.ijk.media.player.IMediaPlayer.OnInfoListener;
import tv.danmaku.ijk.media.player.IMediaPlayer.OnPreparedListener;
import tv.danmaku.ijk.media.player.OTTMediaPlayer;
import tv.danmaku.ijk.media.widget.MediaController;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import cn.sz.free.player.R;

public class TVPlayerActivity extends Activity {
	private static final int MSG_HIDE_MEDIACONTROL = 9000;
	private static final int MSG_PLAY_VIDEO = 9001;
	private static final int MSG_NETWORK_SPEED = 9002;
	private static final int MSG_BUFFER_TIMEOUT = 9003;
	protected static final int MSG_M3U8_MANAGER = 9004;
	protected static final int MSG_GOTO_LVIE = 9005;
	
	private static final long DELAY_HIDE_MEDIACONTROL = 10000;
	private static final long DELAY_PLAY_AGAIN = 3000;
	private static final long TIMEOUT_BUFFERING = 20*1000;
	
	
	private OTTMediaPlayer mMediaPlayer;
	private View mBufferingIndicator;
	private MediaController mMediaController;
	private SurfaceView mSurfaceView;
	private String mPlayUrl;
	private String mUrl;
	private M3u8Manager mM3u8Manager;
	
	private Button backButton;
	private Button forwardButton;
	private Button pauseButton;
	private Button liveButton;
	private Button playButton;
	
	private long mStartTime;
	private long mTimeshiftStartTime;
	private long mTimeshiftEndTime;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tvplayer);

		initView();
		initParams();
//		play(url);
	}
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		stop();
		super.onStop();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
//		mHandler.removeMessages(MSG_NETWORK_SPEED);
		cancelBufferingTimeout();
		stopRequestM3u8();
		super.onPause();
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
//		mHandler.sendEmptyMessageDelayed(MSG_NETWORK_SPEED,1000);
//		if(!mMediaPlayer.isPlaying())
//			mHandler.sendEmptyMessage(MSG_PLAY_AGAIN);
//		startRequestM3u8(mUrl);
		super.onResume();
	}

	private void initView() {
		// TODO Auto-generated method stub
		mBufferingIndicator = findViewById(R.id.buffering_indicator);
		mSurfaceView = (SurfaceView)findViewById(R.id.mediaplayer_surfaceview);
		mMediaController = new MediaController(this);
		
		backButton = (Button)findViewById(R.id.timeshift_back);
		forwardButton = (Button)findViewById(R.id.timeshift_forward);
		pauseButton = (Button)findViewById(R.id.timeshift_pause);
		liveButton = (Button)findViewById(R.id.timeshift_live);
		playButton = (Button)findViewById(R.id.timeshift_play);
	}
	
	private void initParams() {
		// TODO Auto-generated method stub
		mMediaPlayer = new OTTMediaPlayer(this, mSurfaceView);
		mMediaPlayer.setMediaController(mMediaController);
		mMediaPlayer.setMediaBufferingIndicator(mBufferingIndicator);
		mMediaPlayer.setOnPreparedListener(mOnPreparedListener);
		mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
		mMediaPlayer.setOnErrorListener(mOnErrorListener);
		mMediaPlayer.setOnInfoListener(mOnInfoListener);
		mSurfaceView.setOnClickListener(mSurfaceViewOnClickListener);
		mM3u8Manager = new M3u8Manager(this);
		mM3u8Manager.setOnCompeleteListerner(mOnM3u8CompletionListerner);
		
		backButton.setOnClickListener(mOnClickListener);
		forwardButton.setOnClickListener(mOnClickListener);
		pauseButton.setOnClickListener(mOnClickListener);
		liveButton.setOnClickListener(mOnClickListener);
		playButton.setOnClickListener(mOnClickListener);
		
		ChannelBean channelBean = getIntentData();
		if(channelBean != null){
			String path = channelBean.getUrl();
			mUrl = mPlayUrl = doUrlDecoder(path);
		}else{
			Intent intent = getIntent();
			String path = intent.getDataString().toString();
			mUrl = mPlayUrl = doUrlDecoder(path);
		}
		
		// Just for test,need use AsyncTask
		startRequestM3u8(mPlayUrl, M3u8Manager.TYPE_LIVE,0,0);
	}
	
	private void startRequestM3u8(String url, int playerType, long startTime, long endTime){
		mM3u8Manager.start(url,playerType,startTime,endTime,0);
	}
	
	private void stopRequestM3u8(){
		mTimeshiftStartTime = 0;
		mTimeshiftEndTime = 0;
		mM3u8Manager.stop();
	}
	
	private String doUrlDecoder(String path){
		try {
			path = URLDecoder.decode(path, "utf-8");
			Log.d("Debug", "url decode:"+path);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return path;
	}
	
	private ChannelBean getIntentData(){
		Intent intent = getIntent();
		ChannelBean channelBean = (ChannelBean)intent.getSerializableExtra("channel");
		return channelBean;
	}
	
	private void play(String url) {
		// TODO Auto-generated method stub
		if(mMediaPlayer != null){
			cancelBufferingTimeout();
			mSurfaceView.requestFocus();
			mBufferingIndicator.setVisibility(View.VISIBLE);
			mMediaPlayer.stopPlayback();
			mMediaPlayer.setVideoPath(url);
			mMediaPlayer.start();
			if(mM3u8Manager.getPlayerType() != M3u8Manager.TYPE_TIMESHIFT)
				mStartTime = System.currentTimeMillis();
		}
		//for test
	}
	
	private void stop(){
		if(mMediaPlayer != null){
			cancelBufferingTimeout();
			mMediaPlayer.stopPlayback();
			mStartTime = 0;
		}
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		boolean isKeyCodeSupported = keyCode != KeyEvent.KEYCODE_BACK
		   && keyCode != KeyEvent.KEYCODE_VOLUME_UP
		   && keyCode != KeyEvent.KEYCODE_VOLUME_DOWN
		   && keyCode != KeyEvent.KEYCODE_MENU
		   && keyCode != KeyEvent.KEYCODE_CALL
		   && keyCode != KeyEvent.KEYCODE_ENDCALL;
		
		if (mMediaPlayer.isInPlaybackState() && isKeyCodeSupported
				   && mMediaController != null) {
			if (keyCode == KeyEvent.KEYCODE_HEADSETHOOK
		       || keyCode == KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE
		       || keyCode == KeyEvent.KEYCODE_SPACE) {
				if (mMediaPlayer.isPlaying()) {
					mMediaPlayer.pause();
					mMediaController.show();
				} else {
					mMediaPlayer.start();
					mMediaController.hide();
				}
				return true;
			} else if (keyCode == KeyEvent.KEYCODE_MEDIA_STOP
					&& mMediaPlayer.isPlaying()) {
				mMediaPlayer.pause();
				mMediaController.show();
			} else {
				mMediaPlayer.toggleMediaControlsVisiblity();
			}
		}
		
		return super.onKeyDown(keyCode, event);
	}
	
	/**
	 * SurfaceView监听器
	 */
	private OnClickListener mSurfaceViewOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			if (mMediaPlayer.isInPlaybackState() && mMediaController != null){
				showMediaControl();
			}
		}
	};
	
	private void showMediaControl(){
		mMediaPlayer.toggleMediaControlsVisiblity();
//		mHandler.removeMessages(MSG_HIDE_MEDIACONTROL);
//		mHandler.sendEmptyMessageDelayed(MSG_HIDE_MEDIACONTROL, DELAY_HIDE_MEDIACONTROL);
	}
	
	private void hideMediaControl(){
		mMediaController.hide();
	}
	
	private void checkBufferingTimeout(){
		mHandler.removeMessages(MSG_BUFFER_TIMEOUT);
		mHandler.sendEmptyMessageDelayed(MSG_BUFFER_TIMEOUT, TIMEOUT_BUFFERING);
	}
	
	private void cancelBufferingTimeout(){
		mHandler.removeMessages(MSG_BUFFER_TIMEOUT);
	}
	
	private void playLive(){
		stop();
		mM3u8Manager.stop();
		mM3u8Manager.start(mUrl, M3u8Manager.TYPE_LIVE,0,0,0);
	}
	
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case MSG_HIDE_MEDIACONTROL:
				hideMediaControl();
				break;
				
			case MSG_PLAY_VIDEO:
				play(mPlayUrl);
				
			case MSG_NETWORK_SPEED:
//				PackageManager pm = getPackageManager();
//				ApplicationInfo ai = null;
//				try {
//					ai = pm.getApplicationInfo("tv.danmaku.ijk.media.demo", PackageManager.GET_ACTIVITIES);
//				} catch (NameNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				int uid = ai.uid;
//				long speed = NetworkSpeed.getUidRxBytes(uid);
//				Toast.makeText(MediaPlayerActivity.this, "speed:"+speed+"/kbs", Toast.LENGTH_SHORT).show();
//				mHandler.removeMessages(MSG_NETWORK_SPEED);
//				mHandler.sendEmptyMessageDelayed(MSG_NETWORK_SPEED,1000);
				break;
				
			case MSG_BUFFER_TIMEOUT:
				Toast.makeText(TVPlayerActivity.this, "网络异常，缓冲超时，重新请求~~~", Toast.LENGTH_SHORT).show();
				Log.e("Debug","MSG_BUFFER_TIMEOUT，缓冲超时，重新请求");
				mM3u8Manager.stop();
				play(mUrl);
			
			case MSG_GOTO_LVIE:
				Toast.makeText(TVPlayerActivity.this, "时移切回直播", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}
	};
	
	private OnPreparedListener mOnPreparedListener = new OnPreparedListener() {
		
		@Override
		public void onPrepared(IMediaPlayer mp) {
			// TODO Auto-generated method stub
			mBufferingIndicator.setVisibility(View.INVISIBLE);
		}
	};
	
	private OnErrorListener mOnErrorListener = new OnErrorListener() {
		
		@Override
		public boolean onError(IMediaPlayer mp, int what, int extra) {
			// TODO Auto-generated method stub
//			Toast.makeText(MediaPlayerActivity.this, "onError", Toast.LENGTH_SHORT).show();
			return false;
		}
	};
	
	private OnInfoListener mOnInfoListener = new OnInfoListener() {
		
		@Override
		public boolean onInfo(IMediaPlayer mp, int what, int extra) {
			// TODO Auto-generated method stub
		      if (what == IMediaPlayer.MEDIA_INFO_BUFFERING_START) {
                  if (mBufferingIndicator != null){
                	  mBufferingIndicator.setVisibility(View.VISIBLE);
                	  checkBufferingTimeout();
                  } 
              } else if (what == IMediaPlayer.MEDIA_INFO_BUFFERING_END) {
                  if (mBufferingIndicator != null){
                	  mBufferingIndicator.setVisibility(View.GONE);
                	  cancelBufferingTimeout();
                  }
              }
			return true;
		}
	};
	
	private OnCompletionListener mOnCompletionListener = new OnCompletionListener() {
		
		@Override
		public void onCompletion(IMediaPlayer mp) {
			// TODO Auto-generated method stub
			mBufferingIndicator.setVisibility(View.VISIBLE);
			Toast.makeText(TVPlayerActivity.this, "播放异常,3秒后重新请求", Toast.LENGTH_SHORT).show();
			mHandler.sendEmptyMessageDelayed(MSG_PLAY_VIDEO, DELAY_PLAY_AGAIN);
		}
	};
	
	/**
	 * 第一个m3u8生成时播放
	 */
	private OnCompletionListerner mOnM3u8CompletionListerner = new OnCompletionListerner() {
		
		@Override
		public void onCompletion(String url, long startTime, long endTime, boolean isLive) {
			// TODO Auto-generated method stub
			if(isLive){
				mHandler.sendEmptyMessage(MSG_GOTO_LVIE);
				playLive();
				return;
			}
			mPlayUrl = url;
			if(mM3u8Manager.getPlayerType() == M3u8Manager.TYPE_TIMESHIFT){
				mTimeshiftStartTime = startTime;
				mTimeshiftEndTime = endTime;
				Log.d("Debug","onCompletion startTime:"+startTime+" endTime:"+endTime);
			}
			if(!mMediaPlayer.isPlaying()){
				mHandler.sendEmptyMessage(MSG_PLAY_VIDEO);
			}
		}
	}; 
	
	private OnClickListener mOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			switch (arg0.getId()) {
			case R.id.timeshift_back:
				int position = mMediaPlayer.getCurrentPosition();
				if(mM3u8Manager.getPlayerType() == M3u8Manager.TYPE_LIVE)
					mTimeshiftEndTime = (mStartTime + position)/1000;
				else if(mM3u8Manager.getPlayerType() == M3u8Manager.TYPE_TIMESHIFT){
					mTimeshiftEndTime = (mTimeshiftStartTime*1000 + position)/1000;
				}
				Log.d("Debug","timeshift position:"+position/1000+" endTime:"+mTimeshiftEndTime);
				
				stop();
				mM3u8Manager.stop();
				mM3u8Manager.start(mUrl, M3u8Manager.TYPE_TIMESHIFT,0,mTimeshiftEndTime,M3u8Manager.TYPE_BACK);
				break;
				
			case R.id.timeshift_forward:
				Log.d("Debug","timeshift_");
				int pos = mMediaPlayer.getCurrentPosition();
				if(mM3u8Manager.getPlayerType() == M3u8Manager.TYPE_LIVE){
					Toast.makeText(TVPlayerActivity.this, "当前已是直播状态...", Toast.LENGTH_SHORT).show();
					return;
				}
				else if(mM3u8Manager.getPlayerType() == M3u8Manager.TYPE_TIMESHIFT){
					if(mM3u8Manager.getPlayerType() == M3u8Manager.TYPE_TIMESHIFT){
						mTimeshiftEndTime = (mTimeshiftStartTime*1000 + pos)/1000;
					}
				}
				stop();
				mM3u8Manager.stop();
				mM3u8Manager.start(mUrl, M3u8Manager.TYPE_TIMESHIFT,0,mTimeshiftEndTime,M3u8Manager.TYPE_FORWARD);
				break;
			
			case R.id.timeshift_live:
				playLive();
				break;
				
			case R.id.timeshift_pause:
//				mMediaPlayer.pause();
//				pauseButton.setVisibility(View.GONE);
//				playButton.setVisibility(View.VISIBLE);
				break;
				
			case R.id.timeshift_play:
//				mMediaPlayer.resume();
//				pauseButton.setVisibility(View.VISIBLE);
//				playButton.setVisibility(View.GONE);
				
			default:
				break;
			}
		}
	};
	
}
