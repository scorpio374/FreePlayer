package tv.danmaku.ijk.media.app.player;

import java.io.IOException;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.SurfaceHolder;

public class AndroidMediaPlayerAbs extends AbstractMediaPlayer 
		implements MediaPlayer.OnErrorListener,MediaPlayer.OnCompletionListener,MediaPlayer.OnBufferingUpdateListener,MediaPlayer.OnInfoListener,
		MediaPlayer.OnPreparedListener,MediaPlayer.OnSeekCompleteListener,MediaPlayer.OnVideoSizeChangedListener
{
	private MediaPlayer mMediaPlayer;
	private OnBufferingUpdateListener mOnBufferingUpdateListener;
	private OnCompletionListener mOnCompletionListener;
	private OnErrorListener mOnErrorListener;
	private OnInfoListener mOnInfoListener;
	private OnPreparedListener mOnPreparedListener;
	private OnVideoSizeChangedListener mOnVideoSizeChangedListener;

	public AndroidMediaPlayerAbs(){
		mMediaPlayer = new MediaPlayer();
		mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
	    mMediaPlayer.setOnBufferingUpdateListener(this);
	    mMediaPlayer.setOnCompletionListener(this);
	    mMediaPlayer.setOnErrorListener(this);
	    mMediaPlayer.setOnInfoListener(this);
	    mMediaPlayer.setOnPreparedListener(this);
	    mMediaPlayer.setOnSeekCompleteListener(this);
	    mMediaPlayer.setOnVideoSizeChangedListener(this);	    
	}
	
	@Override
	public void pause() {
		mMediaPlayer.pause();
	}

	@Override
	public void stop() {
		mMediaPlayer.stop();
		mMediaPlayer.release();
        mMediaPlayer = null;
	}

	@Override
	public void reset() {
		mMediaPlayer.reset();
	}

	@Override
	public void start() {
		mMediaPlayer.start();
	}

	@Override
	public int getAudioTrack() {
		return -1;
	}

	@Override
	public int getAudioTrackCount() {
		return -1;
	}

	@Override
	public int getCurrentPosition() {
		return mMediaPlayer.getCurrentPosition();
	}

	@Override
	public int getDuration() {
		return mMediaPlayer.getDuration();
	}

	@Override
	public int getSubtitleTrack() {
		return -1;
	}

	@Override
	public int getSubtitleTrackCount() {
		return -1;
	}

	@Override
	public int getVideoHeight() {
		return mMediaPlayer.getVideoHeight();
	}

	@Override
	public int getVideoWidth() {
		return mMediaPlayer.getVideoWidth();
	}

	@Override
	public boolean isLooping() {
		return mMediaPlayer.isLooping();
	}

	@Override
	public boolean isPlaying() {
		return mMediaPlayer.isPlaying();
	}

	@Override
	public void prepare() {
		try {
			mMediaPlayer.prepare();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void prepareAsync() {
		mMediaPlayer.prepareAsync();
	}

	@Override
	public void release() {
		mMediaPlayer.release();
	}

	@Override
	public void seekTo(int paramInt) {
		mMediaPlayer.seekTo(paramInt);
	}

	@Override
	public void setAudioTrack(int paramInt) {
		return;
	}

	@Override
	public void setDataSource(String path) {
		try {
			mMediaPlayer.setDataSource(path);
			//mediaPlayer.setAudioStreamType(3);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void setDisplay(SurfaceHolder sh) {
		mMediaPlayer.setDisplay(sh);
	}

	@Override
	public void setLooping(boolean isLoop) {
		mMediaPlayer.setLooping(isLoop);
	}

	@Override
	public void setOnBufferingUpdateListener(
			OnBufferingUpdateListener onBufferingUpdateListener) {
		mOnBufferingUpdateListener = onBufferingUpdateListener;
	}

	@Override
	public void setOnCompletionListener(
			OnCompletionListener onCompletionListener) {
		mOnCompletionListener = onCompletionListener;
	}

	@Override
	public void setOnErrorListener(OnErrorListener onErrorListener) {
		mOnErrorListener = onErrorListener;
	}

	@Override
	public void setOnInfoListener(OnInfoListener onInfoListener) {
		mOnInfoListener = onInfoListener;
	}

	@Override
	public void setOnPreparedListener(OnPreparedListener onPreparedListener) {
		mOnPreparedListener = onPreparedListener;
	}

	@Override
	public void setOnVideoSizeChangedListener(
			OnVideoSizeChangedListener onProgressUpdateListener) {
		mOnVideoSizeChangedListener = onProgressUpdateListener;
	}

	@Override
	public void setSubtitleTrack(int paramInt) {
		// TODO Auto-generated method stub
	}

	public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
		if (this.mOnVideoSizeChangedListener != null)
		      this.mOnVideoSizeChangedListener.onVideoSizeChangedListener(this, width, height);
	}

	public void onSeekComplete(MediaPlayer mp) {
		// TODO Auto-generated method stub
	}

	public void onPrepared(MediaPlayer mp) {
		if (mOnPreparedListener != null)
		      mOnPreparedListener.onPrepared(this);
	}

	public boolean onInfo(MediaPlayer mp, int what, int extra) {
		if(mOnInfoListener != null)
			return mOnInfoListener.onInfo(this, what, extra);
		return false;
	}

	public boolean onError(MediaPlayer mp, int what, int extra) {
		if(mOnInfoListener != null)
			return mOnErrorListener.onError(this, what, extra);
		return false;
	}

	public void onCompletion(MediaPlayer mp) {
		if (mOnCompletionListener != null)
		      mOnCompletionListener.onCompletion(this);
	}

	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		if (mOnBufferingUpdateListener != null)
		      mOnBufferingUpdateListener.onBufferingUpdate(this, percent);
	}
}
