package tv.danmaku.ijk.media.app.player;

import java.io.IOException;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.tvplayer.play.IMediaPlayer;

public class SystemPlayer implements IMediaPlayer {

    private volatile int mState = STATE_UNDEF;
    private MediaPlayer mMediaPlayer = null;
    private MediaPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener = null;
    private MediaPlayer.OnCompletionListener mOnCompletionListener = null;
    private MediaPlayer.OnErrorListener mOnFlagErrorListener = null;
    private MediaPlayer.OnInfoListener mOnInfoListener = null;
    private MediaPlayer.OnPreparedListener mOnPreparedListener = null;
    private MediaPlayer.OnSeekCompleteListener mSeekCompleteListener = null;
    private MediaPlayer.OnVideoSizeChangedListener mVideoSizeChangedListener = null;

    public SystemPlayer() {
        mMediaPlayer = new MediaPlayer();
        mState = STATE_IDLE;
    }
    

    @Override
    public int getCurrentPosition() {
        int position = 0;
        if (mState != STATE_ERROR) {
            try {
                position = mMediaPlayer.getCurrentPosition();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("error!call getCurrentPosition() in state " + mState);
        }
        return position;
    }

    @Override
    public int getDuration() {
        int duration = 0;
        if (mState == STATE_PREPARED || mState == STATE_STARTED
                || mState == STATE_PAUSED || mState == STATE_STOPED
                || mState == STATE_PLAYBACK_COMPLETE) {
            try {
            	if(mMediaPlayer!=null){
            		duration = mMediaPlayer.getDuration();
            	}
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("error!call getDuration() in state " + mState);
        }
        return duration;
    }

    @Override
    public int getVideoHeight() {
        int height = 0;
        if (mState != STATE_ERROR) {
            try {
                height = mMediaPlayer.getVideoHeight();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("error!call getVideoHeight() in state " + mState);
        }
        return height;
    }

    @Override
    public int getVideoWidth() {
        int width = 0;
        if (mState != STATE_ERROR) {
            try {
                width = mMediaPlayer.getVideoWidth();
                System.out.println("[DefaultPlayer|getVideoWidth]width="+width);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("error!call getVideoWidth() in state " + mState);
        }
        return width;
    }

    @Override
    public boolean isPlaying() {
        boolean isPlaying = false;
        if (mState != STATE_ERROR) {
            try {
            	if(mMediaPlayer!=null){
            		isPlaying = mMediaPlayer.isPlaying();
            	}
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("error!call isPlaying() in state " + mState);
        }
        return isPlaying;
    }

    @Override
    public void pause() {
        if (mState == STATE_PREPARED || mState == STATE_STARTED
                || mState == STATE_PAUSED) {
            try {
                mMediaPlayer.pause();
                mState = STATE_PAUSED;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("error!call pause() in state " + mState);
        }
    }

    @Override
    public void prepare() {
        if (mState == STATE_INITIALIZED || mState == STATE_STOPED) {
            try {
                mMediaPlayer.prepare();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mState = STATE_PREPARED;
        } else {
            System.out.println("error!call prepare() in state " + mState);
        }
    }

    @Override
    public void prepareAsync() {
        if (mState == STATE_INITIALIZED || mState == STATE_STOPED) {
            try {
                mMediaPlayer.prepareAsync();
                mState = STATE_PREPARING;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("error!call prepareAsync() in state " + mState);
        }
    }

    @Override
    public void release() {
        try {
        	if (mMediaPlayer != null) {
                mMediaPlayer.release();
                mMediaPlayer = null;
                mState = STATE_END;
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reset() {
    	System.out.println("call reset() in state " + mState);
        if (mState == STATE_END) {
            mMediaPlayer = new MediaPlayer();
        }
        try {
            mMediaPlayer.reset();
            mState = STATE_IDLE;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void seekTo(int msec) {
        if (mState == STATE_PREPARED || mState == STATE_STARTED
                || mState == STATE_PAUSED || mState == STATE_PLAYBACK_COMPLETE) {
            try {
                mMediaPlayer.seekTo(msec);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("error!call seekTo() in state " + mState);
        }
    }
    
    @Override
    public void setDataSource(String path) {
        if (mState == STATE_IDLE) {
            try {
                mMediaPlayer.setDataSource(path);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mState = STATE_INITIALIZED;
        } else {
            System.out.println("error!call setDataSource() in state " + mState);
        }
    }
    

    @Override
    public void setOnBufferingUpdateListener(IMediaPlayer.OnBufferingUpdateListener l) {
        final IMediaPlayer.OnBufferingUpdateListener tempListener = l;

        mOnBufferingUpdateListener = new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                tempListener.onBufferingUpdate(SystemPlayer.this, percent);
            }
        };
        mMediaPlayer.setOnBufferingUpdateListener(mOnBufferingUpdateListener);
    }

    @Override
    public void setOnCompletionListener(IMediaPlayer.OnCompletionListener l) {
        final IMediaPlayer.OnCompletionListener tempListener = l;

        mOnCompletionListener = new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                tempListener.onCompletion(SystemPlayer.this);
                mState = STATE_PLAYBACK_COMPLETE;
            }
        };
        mMediaPlayer.setOnCompletionListener(mOnCompletionListener);
    }

    @Override
    public void setOnErrorListener(IMediaPlayer.OnErrorListener l) {
        final IMediaPlayer.OnErrorListener tempListener = l;

        mOnFlagErrorListener = new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                mState = STATE_ERROR;
                return tempListener.onError(SystemPlayer.this, what, extra);
            }
        };
        mMediaPlayer.setOnErrorListener(mOnFlagErrorListener);
    }  
    
	@Override
	public void setOnInfoListener(IMediaPlayer.OnInfoListener l) {		
		final IMediaPlayer.OnInfoListener tempListener = l;
		
		mOnInfoListener = new MediaPlayer.OnInfoListener() {			
			@Override
			public boolean onInfo(MediaPlayer mp, int what, int extra) {
//				mState = STATE_ERROR;
				return tempListener.onInfo(SystemPlayer.this, what, extra);
			}
		};
		mMediaPlayer.setOnInfoListener(mOnInfoListener);
	}
	
    @Override
    public void setOnPreparedListener(IMediaPlayer.OnPreparedListener l) {
        final IMediaPlayer.OnPreparedListener tempListener = l;

        mOnPreparedListener = new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mState = STATE_PREPARED;
                tempListener.onPrepared(SystemPlayer.this);
            }
        };
        mMediaPlayer.setOnPreparedListener(mOnPreparedListener);
    }

    @Override
    public void setOnSeekCompleteListener(IMediaPlayer.OnSeekCompleteListener l) {
        final IMediaPlayer.OnSeekCompleteListener tempListener = l;

        mSeekCompleteListener = new MediaPlayer.OnSeekCompleteListener() {
            @Override
            public void onSeekComplete(MediaPlayer mp) {
                tempListener.onSeekComplete(SystemPlayer.this);
            }
        };
        mMediaPlayer.setOnSeekCompleteListener(mSeekCompleteListener);
    }

    @Override
    public void setOnVideoSizeChangedListener(IMediaPlayer.OnVideoSizeChangedListener l) {
        final IMediaPlayer.OnVideoSizeChangedListener tempListener = l;

        mVideoSizeChangedListener = new MediaPlayer.OnVideoSizeChangedListener() {
            @Override
            public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                tempListener.onVideoSizeChanged(SystemPlayer.this, width, height);
            }
        };
        mMediaPlayer.setOnVideoSizeChangedListener(mVideoSizeChangedListener);
    }


    @Override
    public void setScreenOnWhilePlaying(boolean screenOn) {
        try {
            mMediaPlayer.setScreenOnWhilePlaying(screenOn);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start() {
        if (mState == STATE_PREPARED || mState == STATE_STARTED
                || mState == STATE_PAUSED || mState == STATE_PLAYBACK_COMPLETE) {
            try {
            	System.out.println("mMediaPlayer.start()");
                mMediaPlayer.start();
                mState = STATE_STARTED;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("error!call start() in state " + mState);
        }
    }

    @Override
    public void stop() {
        if (mState == STATE_PREPARED || mState == STATE_STARTED
                || mState == STATE_PAUSED || mState == STATE_STOPED
                || mState == STATE_PLAYBACK_COMPLETE) {
            try {
            	System.out.println("mMediaPlayer.stop()");
                mMediaPlayer.stop();
                mState = STATE_STOPED;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("error!call stop() in state " + mState);
        }
    }

    @Override
    public int getCurrentState() {
        return mState;
    }

    @Override
    public void setDisplay(SurfaceHolder holder) {
    	try {
			if(holder != null){
				mMediaPlayer.setDisplay(holder);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }


    @Override
    public void setSurfaceType(SurfaceView surface) {
        surface.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }


	@Override
	public void setAudioStreamType(int streamtype) {
		mMediaPlayer.setAudioStreamType(streamtype);
	}

	@Override
	public void setDataSource(Context context, Uri uri) {
		if (mState == STATE_IDLE) {
			try {
				mMediaPlayer.setDataSource(context, uri);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			mState = STATE_INITIALIZED;
		} else {
			System.out.println("error!call setDataSource() in state " + mState);
		}
	}



}
