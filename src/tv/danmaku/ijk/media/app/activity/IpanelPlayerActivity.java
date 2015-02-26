/*
 * Copyright (C) 2013 Zhang Rui <bbcallen@gmail.com>
 *
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

package tv.danmaku.ijk.media.app.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import tv.danmaku.ijk.media.widget.MediaController;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.MediaController.MediaPlayerControl;
import cn.sz.free.player.R;

import com.ipanel.join.mediaplayer.MediaControllerCallback;
import com.ipanel.join.mediaplayer.VideoSurface;

public class IpanelPlayerActivity extends Activity {
	private VideoSurface mVideoSurface;
	private View mBufferingIndicator;
	private MediaControllerCallback mMediaControllerCallback;
	private MediaController mMediaController;
	private String mVideoPath;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ipanelplayer);

//		mVideoPath = "http://192.168.3.100/hls/test/test.m3u8";
//		mVideoPath = "file:/mnt/sdcard/Movies/stephen_zhou.ts";

		Intent intent = getIntent();
		String intentAction = intent.getAction();
		mVideoPath = intent.getDataString().toString();
		try {
			Log.d("TQ", "mVideoPath orgcode:"+mVideoPath);
			mVideoPath = URLDecoder.decode(mVideoPath, "utf-8");
			Log.d("TQ", "mVideoPath decode:"+mVideoPath);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		if (!TextUtils.isEmpty(intentAction)
//				&& intentAction.equals(Intent.ACTION_VIEW)) {
//			mVideoPath = intent.getDataString();
//		}
//
//		if (TextUtils.isEmpty(mVideoPath)) {
//			mVideoPath = new File(Environment.getExternalStorageDirectory(),
//					"download/test.mp4").getAbsolutePath();
//		}

		mBufferingIndicator = findViewById(R.id.buffering_indicator);
		mMediaController = new MediaController(this);
		mMediaControllerCallback = new MediaControllerCallback() {
			
			@Override
			public void show(int arg0) {
				// TODO Auto-generated method stub
				mMediaController.show();
			}
			
			@Override
			public void show() {
				// TODO Auto-generated method stub
				mMediaController.show();
			}
			
			@Override
			public void setMediaPlayer(MediaPlayerControl arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setLoadingView(View arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setEnabled(boolean arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onInfo(int arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onBuffering(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean isShowing() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void hide() {
				// TODO Auto-generated method stub
				mMediaController.hide();
			}
		};

		mVideoSurface = (VideoSurface) findViewById(R.id.video_view);
		mVideoSurface.setMediaController(mMediaControllerCallback);
//		mVideoSurface.setMediaBufferingIndicator(mBufferingIndicator);
		mVideoSurface.setVideoPath(mVideoPath);
		mVideoSurface.requestFocus();
		mVideoSurface.start();
	}
}
