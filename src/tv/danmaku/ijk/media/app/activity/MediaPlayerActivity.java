package tv.danmaku.ijk.media.app.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import tv.danmaku.ijk.media.app.bean.ChannelBean;
import tv.danmaku.ijk.media.app.player.SystemPlayer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.SurfaceHolder.Callback;
import android.view.View.OnClickListener;
import android.widget.Toast;
import cn.sz.free.player.R;

import com.tvplayer.play.IMediaPlayer;
import com.tvplayer.play.IMediaPlayer.OnCompletionListener;
import com.tvplayer.play.IMediaPlayer.OnErrorListener;
import com.tvplayer.play.IMediaPlayer.OnInfoListener;
import com.tvplayer.play.IMediaPlayer.OnPreparedListener;

public class MediaPlayerActivity extends Activity {
	private static final int MSG_HIDE_MEDIACONTROL = 9000;
	private static final int MSG_PLAY_AGAIN = 9001;
	private static final int MSG_NETWORK_SPEED = 9002;
	private static final int MSG_BUFFER_TIMEOUT = 9003;

	private static final long DELAY_PLAY_AGAIN = 10000;
	private static final long TIMEOUT_BUFFERING = 15 * 1000;
	private SystemPlayer mMediaPlayer;
	private View mBufferingIndicator;
	private SurfaceView mSurfaceView;
	private SurfaceHolder mSurfaceHolder;
	private String mUrl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mediaplayer);

		initView();
		initParams();
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
		cancelBufferingTimeout();

		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		mHandler.sendEmptyMessage(MSG_PLAY_AGAIN);
		super.onResume();
	}

	private void initView() {
		// TODO Auto-generated method stub
		mBufferingIndicator = findViewById(R.id.buffering_indicator);
		mSurfaceView = (SurfaceView) findViewById(R.id.mediaplayer_surfaceview);
	}

	private void initParams() {
		// TODO Auto-generated method stub
		mMediaPlayer = new SystemPlayer();
		mMediaPlayer.setOnPreparedListener(mOnPreparedListener);
		mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
		mMediaPlayer.setOnErrorListener(mOnErrorListener);
		mMediaPlayer.setOnInfoListener(mOnInfoListener);
		mSurfaceView.setOnClickListener(mSurfaceViewOnClickListener);
		mSurfaceHolder = mSurfaceView.getHolder();
		mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		mSurfaceHolder.setKeepScreenOn(true);
		mSurfaceHolder.addCallback(mHolderCallback);

		ChannelBean channelBean = getIntentData();
		if (channelBean != null) {
			String path = channelBean.getUrl();
			mUrl = doUrlDecoder(path);
		} else {
			Intent intent = getIntent();
			String path = intent.getDataString().toString();
			mUrl = doUrlDecoder(path);
		}
	}

	private String doUrlDecoder(String path) {
		try {
			path = URLDecoder.decode(path, "utf-8");
			Log.d("Debug", "url decode:" + path);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return path;
	}

	private ChannelBean getIntentData() {
		Intent intent = getIntent();
		ChannelBean channelBean = (ChannelBean) intent
				.getSerializableExtra("channel");
		return channelBean;
	}

	private void play(String url) {
		// TODO Auto-generated method stub
		if (mMediaPlayer != null) {
			Log.d("Debug", "play:" + url);
			cancelBufferingTimeout();
			mSurfaceView.requestFocus();
			mBufferingIndicator.setVisibility(View.VISIBLE);
			mMediaPlayer.setDataSource(url);
			mMediaPlayer.prepareAsync();
		}

		// for test
	}

	private void stop() {
		if (mMediaPlayer != null) {
			cancelBufferingTimeout();
			mMediaPlayer.stop();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		return super.onKeyDown(keyCode, event);
	}

	/**
	 * SurfaceView监听器
	 */
	private OnClickListener mSurfaceViewOnClickListener = new OnClickListener() {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
		}
	};

	private void showMediaControl() {

	}

	private void hideMediaControl() {
	}

	private void checkBufferingTimeout() {
		mHandler.removeMessages(MSG_BUFFER_TIMEOUT);
		mHandler.sendEmptyMessageDelayed(MSG_BUFFER_TIMEOUT, TIMEOUT_BUFFERING);
	}

	private void cancelBufferingTimeout() {
		mHandler.removeMessages(MSG_BUFFER_TIMEOUT);
	}

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case MSG_HIDE_MEDIACONTROL:
				hideMediaControl();
				break;

			case MSG_PLAY_AGAIN:
				play(mUrl);
				break;

			case MSG_NETWORK_SPEED:
				break;

			case MSG_BUFFER_TIMEOUT:
				Toast.makeText(MediaPlayerActivity.this, "网络异常，缓冲超时，重新请求~~~~",
						Toast.LENGTH_SHORT).show();
				Log.e("Debug", "MSG_BUFFER_TIMEOUT，缓冲超时，重新请求~~~~");
				play(mUrl);
				break;

			default:
				break;
			}
			super.handleMessage(msg);
		}
	};

	/**
	 * SurfaceHolder回调函数
	 */
	private Callback mHolderCallback = new Callback() {
		@Override
		public void surfaceDestroyed(SurfaceHolder holder) {
		}

		/**
		 * 首次播放在这里，要等SurfaceView创建成功才能播放，否则会报错
		 */
		@Override
		public void surfaceCreated(SurfaceHolder holder) {
			mSurfaceHolder = holder;
			mMediaPlayer.setDisplay(mSurfaceHolder);
		}

		@Override
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
		}
	};

	private OnPreparedListener mOnPreparedListener = new OnPreparedListener() {

		@Override
		public void onPrepared(IMediaPlayer mp) {
			// TODO Auto-generated method stub
			mMediaPlayer.start();
			mBufferingIndicator.setVisibility(View.INVISIBLE);
		}
	};

	private OnErrorListener mOnErrorListener = new OnErrorListener() {

		@Override
		public boolean onError(IMediaPlayer mp, int what, int extra) {
			return false;
		}
	};

	private OnInfoListener mOnInfoListener = new OnInfoListener() {

		@Override
		public boolean onInfo(IMediaPlayer mp, int what, int extra) {
			// TODO Auto-generated method stub
			if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
				if (mBufferingIndicator != null) {
					mBufferingIndicator.setVisibility(View.VISIBLE);
					checkBufferingTimeout();
				}
			} else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
				if (mBufferingIndicator != null) {
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
			Toast.makeText(MediaPlayerActivity.this, "播放异常,10秒后重新请求~~~~",
					Toast.LENGTH_SHORT).show();
			mHandler.sendEmptyMessageDelayed(MSG_PLAY_AGAIN, DELAY_PLAY_AGAIN);
		}
	};

}
