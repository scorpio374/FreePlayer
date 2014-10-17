///*
// * Copyright (C) 2006 The Android Open Source Project
// * Copyright (C) 2013 Zhang Rui <bbcallen@gmail.com>
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package tv.danmaku.ijk.media.app.player;
//
//import java.io.IOException;
//import java.lang.ref.WeakReference;
//
//import tv.danmaku.ijk.media.player.MediaInfo;
//import tv.danmaku.ijk.media.player.SimpleMediaPlayer;
//import tv.danmaku.ijk.media.player.pragma.DebugLog;
//import android.content.Context;
//import android.media.AudioManager;
//import android.media.MediaPlayer;
//import android.net.Uri;
//import android.text.TextUtils;
//import android.view.Surface;
//import android.view.SurfaceHolder;
//import android.view.SurfaceHolder.Callback;
//
//import com.tvplayer.play.IMediaPlayer;
//import com.tvplayer.play.SystemPlayer;
//import com.tvplayer.play.TVPlayer;
//
//public class TVMediaPlayer extends SimpleMediaPlayer {
//    private TVPlayer mTVPlayer;
//    private IMediaPlayer mMediaPlayer;
//    private AndroidMediaPlayerListenerHolder mInternalListenerAdapter;
//    private String mDataSource;
//
//    private Object mInitLock = new Object();
//    private boolean mIsReleased;
//
//    private boolean mKeepInBackground;
//
//    private static MediaInfo sMediaInfo;
//
//    public TVMediaPlayer(Context context) {
//        synchronized (mInitLock) {
//        	mMediaPlayer = new SystemPlayer();
//        }
//        mInternalListenerAdapter = new AndroidMediaPlayerListenerHolder(this);
//        attachInternalListeners();
//    }
//
//    @Override
//    public void setDisplay(SurfaceHolder sh) {
//        synchronized (mInitLock) {
//            if (!mIsReleased) {
//                mTVPlayer.setDisplay(sh);
//                if (sh != null)
//                    sh.addCallback(mSurfaceCallback);
//            }
//        }
//    }
//
//    private SurfaceHolder.Callback mSurfaceCallback = new Callback() {
//        public void surfaceChanged(SurfaceHolder holder, int format, int width,
//                int height) {
//        }
//
//        public void surfaceCreated(SurfaceHolder holder) {
//        }
//
//        public void surfaceDestroyed(SurfaceHolder holder) {
//            if (mTVPlayer != null) {
//                if (!mKeepInBackground) {
//                    mTVPlayer.release();
//                }
//            }
//        }
//    };
//
//    @Override
//    public void setSurface(Surface surface) {
//        mTVPlayer.setSurface(surface);
//    }
//
//    @Override
//    public void setDataSource(String path) throws IOException,
//            IllegalArgumentException, SecurityException, IllegalStateException {
//        mDataSource = path;
//
//        Uri uri = Uri.parse(path);
//        String scheme = uri.getScheme();
//        if (!TextUtils.isEmpty(scheme) && scheme.equalsIgnoreCase("file")) {
//            mTVPlayer.setDataSource(uri.getPath());
//        } else {
//            mTVPlayer.setDataSource(path);
//        }
//    }
//
//    @Override
//    public String getDataSource() {
//        return mDataSource;
//    }
//
//    @Override
//    public void prepareAsync() throws IllegalStateException {
//        mTVPlayer.prepareAsync();
//    }
//
//    @Override
//    public void start() throws IllegalStateException {
//        mTVPlayer.start();
//    }
//
//    @Override
//    public void stop() throws IllegalStateException {
//        mTVPlayer.stop();
//    }
//
//    @Override
//    public void pause() throws IllegalStateException {
//        mTVPlayer.pause();
//    }
//
//    @Override
//    public void setScreenOnWhilePlaying(boolean screenOn) {
//        
//    }
//
//    @Override
//    public int getVideoWidth() {
//        return mTVPlayer.getVideoWidth();
//    }
//
//    @Override
//    public int getVideoHeight() {
//        return mTVPlayer.getVideoHeight();
//    }
//
//    @Override
//    public int getVideoSarNum() {
//        return 1;
//    }
//
//    @Override
//    public int getVideoSarDen() {
//        return 1;
//    }
//
//    @Override
//    public boolean isPlaying() {
//        try {
//            return mTVPlayer.isPlaying();
//        } catch (IllegalStateException e) {
//            DebugLog.printStackTrace(e);
//            return false;
//        }
//    }
//
//    @Override
//    public void seekTo(long msec) throws IllegalStateException {
//       
//    }
//
//    @Override
//    public long getCurrentPosition() {
//        try {
//            return mTVPlayer.getCurrentPosition();
//        } catch (IllegalStateException e) {
//            DebugLog.printStackTrace(e);
//            return 0;
//        }
//    }
//
//    @Override
//    public long getDuration() {
//        try {
//            return mTVPlayer.getDuration();
//        } catch (IllegalStateException e) {
//            DebugLog.printStackTrace(e);
//            return 0;
//        }
//    }
//
//    @Override
//    public void release() {
//        mIsReleased = true;
//        mTVPlayer.release();
//
//        resetListeners();
//        attachInternalListeners();
//    }
//
//    @Override
//    public void reset() {
//        mTVPlayer.reset();
//
//        resetListeners();
//        attachInternalListeners();
//    }
//
//    @Override
//    public void setVolume(float leftVolume, float rightVolume) {
//        mTVPlayer.setVolume(leftVolume, rightVolume);
//    }
//
//    @Override
//    public MediaInfo getMediaInfo() {
//        if (sMediaInfo == null) {
//            MediaInfo module = new MediaInfo();
//
//            module.mVideoDecoder = "android";
//            module.mVideoDecoderImpl = "HW";
//
//            module.mAudioDecoder = "android";
//            module.mAudioDecoderImpl = "HW";
//
//            sMediaInfo = module;
//        }
//
//        return sMediaInfo;
//    }
//
//    /*--------------------
//     * misc
//     */
//    @Override
//    public void setWakeMode(Context context, int mode) {
//        mTVPlayer.setWakeMode(context, mode);
//    }
//
//    @Override
//    public void setAudioStreamType(int streamtype) {
//        mTVPlayer.setAudioStreamType(streamtype);
//    }
//
//    @Override
//    public void setKeepInBackground(boolean keepInBackground) {
//        mKeepInBackground = keepInBackground;
//    }
//
//    /*--------------------
//     * Listeners adapter
//     */
//    private final void attachInternalListeners() {
//        mTVPlayer.setOnPreparedListener(mInternalListenerAdapter);
//        mTVPlayer
//                .setOnBufferingUpdateListener(mInternalListenerAdapter);
//        mTVPlayer.setOnCompletionListener(mInternalListenerAdapter);
//        mTVPlayer
//                .setOnSeekCompleteListener(mInternalListenerAdapter);
//        mTVPlayer
//                .setOnVideoSizeChangedListener(mInternalListenerAdapter);
//        mTVPlayer.setOnErrorListener(mInternalListenerAdapter);
//        mTVPlayer.setOnInfoListener(mInternalListenerAdapter);
//    }
//
//    private class AndroidMediaPlayerListenerHolder implements
//            MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener,
//            MediaPlayer.OnBufferingUpdateListener,
//            MediaPlayer.OnSeekCompleteListener,
//            MediaPlayer.OnVideoSizeChangedListener,
//            MediaPlayer.OnErrorListener, MediaPlayer.OnInfoListener {
//        public WeakReference<TVMediaPlayer> mWeakMediaPlayer;
//
//        public AndroidMediaPlayerListenerHolder(TVMediaPlayer mp) {
//            mWeakMediaPlayer = new WeakReference<TVMediaPlayer>(mp);
//        }
//
//        @Override
//        public boolean onInfo(MediaPlayer mp, int what, int extra) {
//            TVMediaPlayer self = mWeakMediaPlayer.get();
//            if (self == null)
//                return false;
//
//            return notifyOnInfo(what, extra);
//        }
//
//        @Override
//        public boolean onError(MediaPlayer mp, int what, int extra) {
//            TVMediaPlayer self = mWeakMediaPlayer.get();
//            if (self == null)
//                return false;
//
//            return notifyOnError(what, extra);
//        }
//
//        @Override
//        public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
//            TVMediaPlayer self = mWeakMediaPlayer.get();
//            if (self == null)
//                return;
//
//            notifyOnVideoSizeChanged(width, height, 1, 1);
//        }
//
//        @Override
//        public void onSeekComplete(MediaPlayer mp) {
//            TVMediaPlayer self = mWeakMediaPlayer.get();
//            if (self == null)
//                return;
//
//            notifyOnSeekComplete();
//        }
//
//        @Override
//        public void onBufferingUpdate(MediaPlayer mp, int percent) {
//            TVMediaPlayer self = mWeakMediaPlayer.get();
//            if (self == null)
//                return;
//
//            notifyOnBufferingUpdate(percent);
//        }
//
//        @Override
//        public void onCompletion(MediaPlayer mp) {
//            TVMediaPlayer self = mWeakMediaPlayer.get();
//            if (self == null)
//                return;
//
//            notifyOnCompletion();
//        }
//
//        @Override
//        public void onPrepared(MediaPlayer mp) {
//            TVMediaPlayer self = mWeakMediaPlayer.get();
//            if (self == null)
//                return;
//
//            notifyOnPrepared();
//        }
//    }
//}
