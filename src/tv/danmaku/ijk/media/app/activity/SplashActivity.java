package tv.danmaku.ijk.media.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import cn.sz.free.player.R;

public class SplashActivity extends Activity {
	private static final long DELAY_TIME = 2000;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SplashActivity.this, PortalActivity.class);
				SplashActivity.this.startActivity(intent);
			}
		}, DELAY_TIME);
	}
}
