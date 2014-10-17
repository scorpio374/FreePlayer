package tv.danmaku.ijk.media.app.player;

import android.view.SurfaceHolder;

public abstract class AbstractMediaPlayer {

	public abstract int getAudioTrack();

	public abstract int getAudioTrackCount();

	public abstract int getCurrentPosition();

	public abstract int getDuration();

	public abstract int getSubtitleTrack();

	public abstract int getSubtitleTrackCount();

	public abstract int getVideoHeight();

	public abstract int getVideoWidth();

	public abstract boolean isLooping();

	public abstract boolean isPlaying();

	public abstract void pause();

	public abstract void prepare();

	public abstract void prepareAsync();

	public abstract void release();

	public abstract void reset();

	public abstract void seekTo(int paramInt);

	public abstract void setAudioTrack(int paramInt);

	public abstract void setDataSource(String paramString);

	public abstract void setDisplay(SurfaceHolder paramSurfaceHolder);

	public abstract void setLooping(boolean paramBoolean);

	public abstract void setOnBufferingUpdateListener(
			OnBufferingUpdateListener paramOnBufferingUpdateListener);

	public abstract void setOnCompletionListener(
			OnCompletionListener paramOnCompletionListener);

	public abstract void setOnErrorListener(OnErrorListener paramOnErrorListener);

	public abstract void setOnInfoListener(OnInfoListener paramOnInfoListener);

	public abstract void setOnPreparedListener(
			OnPreparedListener paramOnPreparedListener);

	public abstract void setOnVideoSizeChangedListener(
			OnVideoSizeChangedListener paramOnVideoSizeChangedListener);

	public abstract void setSubtitleTrack(int paramInt);

	public abstract void start();

	public abstract void stop();

	public abstract interface OnBufferingUpdateListener {
		public abstract void onBufferingUpdate(
				AbstractMediaPlayer paramAbsMediaPlayer, int paramInt);
	}

	public abstract interface OnCompletionListener {
		public abstract void onCompletion(AbstractMediaPlayer paramAbsMediaPlayer);
	}

	public abstract interface OnErrorListener {
		public abstract boolean onError(AbstractMediaPlayer paramAbsMediaPlayer,
				int paramInt1, int paramInt2);
	}

	public abstract interface OnInfoListener {
		public abstract boolean onInfo(AbstractMediaPlayer paramAbsMediaPlayer,
				int paramInt1, int paramInt2);
	}

	public abstract interface OnPreparedListener {
		public abstract void onPrepared(AbstractMediaPlayer paramAbsMediaPlayer);
		
	}

	public abstract interface OnVideoSizeChangedListener {
		public abstract void onVideoSizeChangedListener(
				AbstractMediaPlayer paramAbsMediaPlayer, int paramInt1, int paramInt2);
	}
}