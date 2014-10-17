package tv.danmaku.ijk.media.app.player;

import java.io.IOException;

import tv.danmaku.ijk.media.player.MediaInfo;
import tv.danmaku.ijk.media.player.SimpleMediaPlayer;
import tv.danmaku.ijk.media.player.IMediaPlayer.OnBufferingUpdateListener;
import tv.danmaku.ijk.media.player.IMediaPlayer.OnCompletionListener;
import tv.danmaku.ijk.media.player.IMediaPlayer.OnErrorListener;
import tv.danmaku.ijk.media.player.IMediaPlayer.OnInfoListener;
import tv.danmaku.ijk.media.player.IMediaPlayer.OnPreparedListener;
import tv.danmaku.ijk.media.player.IMediaPlayer.OnVideoSizeChangedListener;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.SurfaceHolder;

public class AndroidMediaPlayerSim extends SimpleMediaPlayer 
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

	public AndroidMediaPlayerSim(){
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
	public long getCurrentPosition() {
		return mMediaPlayer.getCurrentPosition();
	}

	@Override
	public long getDuration() {
		return mMediaPlayer.getDuration();
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
	public boolean isPlaying() {
		return mMediaPlayer.isPlaying();
	}

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
	public void seekTo(long paramInt) {
		mMediaPlayer.seekTo((int)paramInt);
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

	public void onSeekComplete(MediaPlayer mp) {
		// TODO Auto-generated method stub
	}

	public void onPrepared(MediaPlayer mp) {
		if (this.mOnPreparedListener != null)
		      this.mOnPreparedListener.onPrepared(this);
	}

	public boolean onInfo(MediaPlayer mp, int what, int extra) {
      return this.mOnInfoListener.onInfo(this, what, extra);
	}

	public boolean onError(MediaPlayer mp, int what, int extra) {
		return this.mOnErrorListener.onError(this, what, extra);
	}

	public void onCompletion(MediaPlayer mp) {
		if (this.mOnCompletionListener != null)
		      this.mOnCompletionListener.onCompletion(this);
	}

	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		if (this.mOnBufferingUpdateListener != null)
		      this.mOnBufferingUpdateListener.onBufferingUpdate(this, percent);
	}

	@Override
	public String getDataSource() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setScreenOnWhilePlaying(boolean screenOn) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setVolume(float leftVolume, float rightVolume) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MediaInfo getMediaInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onVideoSizeChanged(MediaPlayer arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
}
