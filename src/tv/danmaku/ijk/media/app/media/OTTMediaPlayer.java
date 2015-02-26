/*
 * Copyright (C) 2006 The Android Open Source Project
 * Copyright (C) 2012 YIXIA.COM
 * Copyright (C) 2013 Zhang Rui <bbcallen@gmail.com>

 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tv.danmaku.ijk.media.app.media;

import java.io.IOException;

import tv.danmaku.ijk.media.widget.DebugLog;
import tv.danmaku.ijk.media.widget.MediaController;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.net.Uri;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.ipanel.join.mediaplayer.IMediaPlayer;
import com.ipanel.join.mediaplayer.IMediaPlayer.OnBufferingUpdateListener;
import com.ipanel.join.mediaplayer.IMediaPlayer.OnCompletionListener;
import com.ipanel.join.mediaplayer.IMediaPlayer.OnErrorListener;
import com.ipanel.join.mediaplayer.IMediaPlayer.OnInfoListener;
import com.ipanel.join.mediaplayer.IMediaPlayer.OnPreparedListener;
import com.ipanel.join.mediaplayer.IMediaPlayer.OnVideoSizeChangedListener;
import com.ipanel.join.mediaplayer.MediaPlayerFactory;

/**
 * Displays a video file. The VideoView class can load images from various
 * sources (such as resources or content providers), takes care of computing its
 * measurement from the video so that it can be used in any layout manager, and
 * provides various display options such as scaling and tinting.
 * 
 * VideoView also provide many wrapper methods for
 * {@link io.vov.vitamio.MediaPlayer}, such as {@link #getVideoWidth()},
 * {@link #setSubShown(boolean)}
 */
public class OTTMediaPlayer implements MediaController.MediaPlayerControl {
	private static final String TAG = OTTMediaPlayer.class.getName();

	private Uri mUri;
	private long mDuration;

	private static final int STATE_ERROR = -1;
	private static final int STATE_IDLE = 0;
	private static final int STATE_PREPARING = 1;
	private static final int STATE_PREPARED = 2;
	private static final int STATE_PLAYING = 3;
	private static final int STATE_PAUSED = 4;
	private static final int STATE_PLAYBACK_COMPLETED = 5;
	private static final int STATE_SUSPEND = 6;
	private static final int STATE_RESUME = 7;
	private static final int STATE_SUSPEND_UNSUPPORTED = 8;

	private int mCurrentState = STATE_IDLE;
	private int mTargetState = STATE_IDLE;

	private int mVideoLayout = VIDEO_LAYOUT_SCALE;
	public static final int VIDEO_LAYOUT_ORIGIN = 0;
	public static final int VIDEO_LAYOUT_SCALE = 1;
	public static final int VIDEO_LAYOUT_STRETCH = 2;
	public static final int VIDEO_LAYOUT_ZOOM = 3;

	private SurfaceHolder mSurfaceHolder = null;
	private IMediaPlayer mMediaPlayer = null;
	private int mVideoWidth;
	private int mVideoHeight;
	private int mVideoSarNum;
	private int mVideoSarDen;
	private int mSurfaceWidth;
	private int mSurfaceHeight;
	private MediaController mMediaController;
	private View mMediaBufferingIndicator;
	private OnCompletionListener mOnCompletionListener;
	private OnPreparedListener mOnPreparedListener;
	private OnErrorListener mOnErrorListener;
	private OnSeekCompleteListener mOnSeekCompleteListener;
	private OnInfoListener mOnInfoListener;
	private OnBufferingUpdateListener mOnBufferingUpdateListener;
	private int mCurrentBufferPercentage;
	private long mSeekWhenPrepared;
	private boolean mCanPause = true;
	private boolean mCanSeekBack = true;
	private boolean mCanSeekForward = true;
	private Context mContext;
	private SurfaceView mSurfaceView = null;
	private boolean firstPlay = true;

	public OTTMediaPlayer(Context context, SurfaceView surfaceView) {
		initVideoView(context, surfaceView);
	}

	private void initVideoView(Context ctx, SurfaceView surfaceView) {
		mContext = ctx;
		mVideoWidth = 0;
		mVideoHeight = 0;
		mVideoSarNum = 0;
		mVideoSarDen = 0;

		mSurfaceView = surfaceView;
		mSurfaceView.setFocusable(true);
		mSurfaceView.setFocusableInTouchMode(true);
		mSurfaceHolder = mSurfaceView.getHolder();
		mSurfaceHolder.addCallback(mSHCallback);

		mCurrentState = STATE_IDLE;
		mTargetState = STATE_IDLE;
		if (ctx instanceof Activity) {
			Log.d("Debug", "VideoView set stream music");
			((Activity) ctx).setVolumeControlStream(AudioManager.STREAM_MUSIC);
		}
	}

	public void setSurfaceView(SurfaceView surfaceView) {
		mSurfaceView = surfaceView;
		SurfaceHolder sh = surfaceView.getHolder();

		if (mMediaPlayer != null)
			mMediaPlayer.setDisplay(sh);
		else {
			Log.d("Debug", "mMediaPlay is null");
		}
	}

	public void setMediaController(MediaController controller) {
		if (mMediaController != null)
			mMediaController.hide();
		mMediaController = controller;
		attachMediaController();
	}

	public void setMediaBufferingIndicator(View mediaBufferingIndicator) {
		if (mediaBufferingIndicator != null)
			mediaBufferingIndicator.setVisibility(View.VISIBLE);
		if (mediaBufferingIndicator == null) {
			Log.d("Debug",
					"mediaBufferingIndicator is null---------------------");
		}
		mMediaBufferingIndicator = mediaBufferingIndicator;
	}

	public boolean isValid() {
		return (mSurfaceHolder != null && mSurfaceHolder.getSurface().isValid());
	}

	public void setVideoPath(String path) {
		setVideoURI(Uri.parse(path));
	}

	public void setVideoURI(Uri uri) {
		mUri = uri;
		mSeekWhenPrepared = 0;
		openVideo();
		mSurfaceView.requestLayout();
		mSurfaceView.invalidate();
	}

	public void stop() {
		if (mMediaPlayer != null) {
			mMediaPlayer.stop();
			// mMediaPlayer.release();
			mMediaPlayer = null;
			mCurrentState = STATE_IDLE;
			mTargetState = STATE_IDLE;
		}
	}

	public void setVideoLayout(int layout) {
		if (mVideoHeight > 0 && mVideoWidth > 0) {
			LayoutParams lp = mSurfaceView.getLayoutParams();
			lp.width = mSurfaceWidth = mSurfaceView.getWidth();
			lp.height = mSurfaceHeight = mSurfaceView.getHeight();
			// mSurfaceView.setLayoutParams(lp);
			mSurfaceHolder.setFixedSize(mSurfaceWidth, mSurfaceHeight);
			Log.d("Debug", "setVideoLayout width:" + mSurfaceWidth + " height:"
					+ mSurfaceHeight);
		}
	}

	private void attachMediaController() {
		if (mMediaPlayer != null && mMediaController != null) {
			mMediaController.setMediaPlayer(this);
			View anchorView = mSurfaceView;
			mMediaController.setAnchorView(anchorView);
			mMediaController.setEnabled(isInPlaybackState());

			// if (mUri != null) {
			// List<String> paths = mUri.getPathSegments();
			// String name = paths == null || paths.isEmpty() ? "null" : paths
			// .get(paths.size() - 1);
			// // mMediaController.setFileName(name);
			// }
		}
	}

	SurfaceHolder.Callback mSHCallback = new SurfaceHolder.Callback() {
		public void surfaceChanged(SurfaceHolder holder, int format, int w,
				int h) {
			mSurfaceHolder = holder;
			if (mMediaPlayer != null) {
				mMediaPlayer.setDisplay(mSurfaceHolder);
			}

			mSurfaceWidth = w;
			mSurfaceHeight = h;
			boolean isValidState = (mTargetState == STATE_PLAYING);
			boolean hasValidSize = (mVideoWidth == w && mVideoHeight == h);
			if (mMediaPlayer != null && isValidState && hasValidSize) {
				if (mSeekWhenPrepared != 0)
					seekTo(mSeekWhenPrepared);
				start();
				if (mMediaController != null) {
					if (mMediaController.isShowing())
						mMediaController.hide();
					mMediaController.show();
				}
			}
		}

		public void surfaceCreated(SurfaceHolder holder) {
			mSurfaceHolder = holder;
			if (mMediaPlayer != null && mCurrentState == STATE_SUSPEND
					&& mTargetState == STATE_RESUME) {
				mMediaPlayer.setDisplay(mSurfaceHolder);
				resume();
			} else {
				Log.d("Debug", "surfaceCreated openVideo");
				// openVideo();
				// modify here
			}
		}

		public void surfaceDestroyed(SurfaceHolder holder) {
			Log.d("Debug", "surfaceDestroyed");
			mSurfaceHolder = null;
			if (mMediaController != null)
				mMediaController.hide();
			if (mCurrentState != STATE_SUSPEND)
				release(true);
		}
	};

	private void openVideo() {
		if (mUri == null || mSurfaceHolder == null)
			return;

		Intent i = new Intent("com.android.music.musicservicecommand");
		i.putExtra("command", "pause");
		mContext.sendBroadcast(i);

		release(false);
		try {
			mDuration = -1;
			mCurrentBufferPercentage = 0;
			mMediaPlayer = MediaPlayerFactory.createPlayer();
			mMediaPlayer.setOnPreparedListener(mPreparedListener);
			mMediaPlayer.setOnVideoSizeChangedListener(mSizeChangedListener);
			mMediaPlayer.setOnCompletionListener(mCompletionListener);
			mMediaPlayer.setOnErrorListener(mErrorListener);
			mMediaPlayer.setOnBufferingUpdateListener(mBufferingUpdateListener);
			mMediaPlayer.setOnInfoListener(mInfoListener);
			// mMediaPlayer.setOnSeekCompleteListener(mSeekCompleteListener);
			if (mUri != null){
				mMediaPlayer.setDataSource(mUri.toString());
			}
			mMediaPlayer.setDisplay(mSurfaceHolder);
			// mMediaPlayer.setScreenOnWhilePlaying(true);
			mMediaPlayer.prepareAsync();

			mCurrentState = STATE_PREPARING;
			attachMediaController();
		} catch (IllegalArgumentException ex) {
			DebugLog.e(TAG, "Unable to open content: " + mUri, ex);
			mCurrentState = STATE_ERROR;
			mTargetState = STATE_ERROR;
//			mErrorListener.onError(mMediaPlayer,
//					IMediaPlayer.MEDIA_ERROR_UNKNOWN, 0);
			return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	OnVideoSizeChangedListener mSizeChangedListener = new OnVideoSizeChangedListener() {
		public void onVideoSizeChanged(IMediaPlayer mp, int width,
				int height) {
			DebugLog.dfmt("Debug", "onVideoSizeChanged: (%dx%d)", width, height);
			mVideoWidth = mp.getVideoWidth();
			mVideoHeight = mp.getVideoHeight();
			mVideoSarNum = 0;
			mVideoSarDen = 0;
			if (mVideoWidth != 0 && mVideoHeight != 0)
				setVideoLayout(mVideoLayout);
		}
	};

	OnPreparedListener mPreparedListener = new OnPreparedListener() {
		public void onPrepared(IMediaPlayer mp) {
			DebugLog.d("Debug", "onPrepared");
			mCurrentState = STATE_PREPARED;
			mTargetState = STATE_PLAYING;

			if (mOnPreparedListener != null)
				mOnPreparedListener.onPrepared(mMediaPlayer);
			if (mMediaController != null)
				mMediaController.setEnabled(true);
			mVideoWidth = mp.getVideoWidth();
			mVideoHeight = mp.getVideoHeight();

			long seekToPosition = mSeekWhenPrepared;

			if (seekToPosition != 0)
				seekTo(seekToPosition);
			if (mVideoWidth != 0 && mVideoHeight != 0) {
				start();
				// setVideoLayout(mVideoLayout);
				// if (mSurfaceWidth == mVideoWidth
				// && mSurfaceHeight == mVideoHeight) {
				// if (mTargetState == STATE_PLAYING) {
				// start();
				// if (mMediaController != null)
				// mMediaController.show();
				// } else if (!isPlaying()
				// && (seekToPosition != 0 || getCurrentPosition() > 0)) {
				// // if (mMediaController != null)
				// // mMediaController.show();
				// }
				// }
			} else if (mTargetState == STATE_PLAYING) {
				start();
			}
		}
	};

	private OnCompletionListener mCompletionListener = new OnCompletionListener() {
		public void onCompletion(IMediaPlayer mp) {
			DebugLog.d(TAG, "onCompletion");
			mCurrentState = STATE_PLAYBACK_COMPLETED;
			mTargetState = STATE_PLAYBACK_COMPLETED;
			if (mMediaController != null)
				mMediaController.hide();
			if (mOnCompletionListener != null)
				mOnCompletionListener.onCompletion(mMediaPlayer);
		}
	};

	private OnErrorListener mErrorListener = new OnErrorListener() {
		public boolean onError(IMediaPlayer mp, int framework_err,
				int impl_err) {
			DebugLog.dfmt(TAG, "Error: %d, %d", framework_err, impl_err);
			mCurrentState = STATE_ERROR;
			mTargetState = STATE_ERROR;
			if (mMediaController != null)
				mMediaController.hide();

			if (mOnErrorListener != null) {
				if (mOnErrorListener.onError(mMediaPlayer, framework_err,
						impl_err))
					return true;
			}

			// int message = framework_err ==
			// IMediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK ?
			// R.string.vitamio_videoview_error_text_invalid_progressive_playback
			// : R.string.vitamio_videoview_error_text_unknown;

			if (mOnCompletionListener != null)
				mOnCompletionListener.onCompletion(mMediaPlayer);

			// 屏蔽播放错误对话框
			// mMediaController.showErrorDialog(message);
			return true;
		}
	};

	private OnBufferingUpdateListener mBufferingUpdateListener = new OnBufferingUpdateListener() {
		public void onBufferingUpdate(IMediaPlayer mp, int percent) {
			mCurrentBufferPercentage = percent;
			if (mOnBufferingUpdateListener != null)
				mOnBufferingUpdateListener.onBufferingUpdate(mp, percent);
		}
	};

	private OnInfoListener mInfoListener = new OnInfoListener() {
		@Override
		public boolean onInfo(IMediaPlayer mp, int what, int extra) {
			// DebugLog.dfmt(TAG, "onInfo: (%d, %d)", what, extra);
			if (mOnInfoListener != null) {
				mOnInfoListener.onInfo(mp, what, extra);
				if (what == 701) {
					DebugLog.dfmt(TAG, "onInfo: (MEDIA_INFO_BUFFERING_START)");
					if (mMediaBufferingIndicator != null) {
						mMediaBufferingIndicator.setVisibility(View.VISIBLE);
					}
				} else if (what == 702) {
					DebugLog.dfmt(TAG, "onInfo: (MEDIA_INFO_BUFFERING_END)");
					if (mMediaBufferingIndicator != null) {
						mMediaBufferingIndicator.setVisibility(View.GONE);
					}
				}
			}

			return true;
		}
	};

	public void toggleMediaControlsVisiblity(boolean isFastSpeed) {
		if (isFastSpeed) {
			if (!mMediaController.isShowing()) {
				mMediaController.show();
			}
			// 快进30秒

			// if((mMediaPlayer.getCurrentPosition()+30000)<mMediaPlayer.getDuration()){
			mMediaPlayer.seekTo(mMediaPlayer.getCurrentPosition() + 30000);
			// }else{
			// mMediaPlayer.seekTo(mMediaPlayer.getDuration());
			// }

		} else {
			if (!mMediaController.isShowing()) {
				mMediaController.show();
			}
			// 快退30秒
			if ((mMediaPlayer.getCurrentPosition() - 30000) > 0) {
				mMediaPlayer.seekTo(mMediaPlayer.getCurrentPosition() - 30000);
			}
		}

	}

	public void setOnPreparedListener(OnPreparedListener l) {
		mOnPreparedListener = l;
	}

	public void setOnCompletionListener(OnCompletionListener l) {
		mOnCompletionListener = l;
	}

	public void setOnErrorListener(OnErrorListener l) {
		mOnErrorListener = l;
	}

	public void setOnBufferingUpdateListener(OnBufferingUpdateListener l) {
		mOnBufferingUpdateListener = l;
	}

	public void setOnSeekCompleteListener(OnSeekCompleteListener l) {
		mOnSeekCompleteListener = l;
	}

	public void setOnInfoListener(OnInfoListener l) {
		mOnInfoListener = l;
	}

	private void release(boolean cleartargetstate) {
		if (mMediaPlayer != null) {
			mMediaPlayer.reset();
			mMediaPlayer.release();
			mMediaPlayer = null;
			mCurrentState = STATE_IDLE;
			if (cleartargetstate)
				mTargetState = STATE_IDLE;
		}
	}

	@Override
	public void start() {
		if (isInPlaybackState()) {
			mMediaPlayer.start();
			mCurrentState = STATE_PLAYING;
		}
		mTargetState = STATE_PLAYING;
	}

	@Override
	public void pause() {
		if (isInPlaybackState()) {
			if (mMediaPlayer.isPlaying()) {
				mMediaPlayer.pause();
				mCurrentState = STATE_SUSPEND;
			}
		}
		mTargetState = STATE_PAUSED;
	}

	public void resume() {
		if (mSurfaceHolder == null && mCurrentState == STATE_SUSPEND) {
			mTargetState = STATE_RESUME;
			mCurrentState = STATE_RESUME;
			mMediaPlayer.start();
		} else if (mCurrentState == STATE_SUSPEND_UNSUPPORTED) {
			openVideo();
		}
	}

	@Override
	public int getDuration() {
		if (isInPlaybackState()) {
			if (mDuration > 0)
				return (int) mDuration;
			mDuration = mMediaPlayer.getDuration();
			return (int) mDuration;
		}
		mDuration = -1;
		return (int) mDuration;
	}

	@Override
	public int getCurrentPosition() {
		if (isInPlaybackState()) {
			long position = mMediaPlayer.getCurrentPosition();
			return (int) position;
		}
		return 0;
	}

	@Override
	public void seekTo(long msec) {
		if (isInPlaybackState()) {
			mMediaPlayer.seekTo((int) msec);
			mSeekWhenPrepared = 0;
		} else {
			mSeekWhenPrepared = msec;
		}
	}

	@Override
	public boolean isPlaying() {
		return isInPlaybackState() && mMediaPlayer.isPlaying();
	}

	@Override
	public int getBufferPercentage() {
		if (mMediaPlayer != null)
			return mCurrentBufferPercentage;
		return 0;
	}

	public int getVideoWidth() {
		return mVideoWidth;
	}

	public int getVideoHeight() {
		return mVideoHeight;
	}

	public boolean isInPlaybackState() {
		return (mMediaPlayer != null && mCurrentState != STATE_ERROR
				&& mCurrentState != STATE_IDLE && mCurrentState != STATE_PREPARING);
	}

	public boolean canPause() {
		return mCanPause;
	}

	public boolean canSeekBackward() {
		return mCanSeekBack;
	}

	public boolean canSeekForward() {
		return mCanSeekForward;
	}
}
