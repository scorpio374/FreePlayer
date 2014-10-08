package tv.danmaku.ijk.media.demo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class PortalActivity extends Activity implements OnClickListener{
	
	private Button urlPlay;
	private Button fileSelect;
	private EditText inputUrl;
//	private String testUrl = "http://nowtv.tvesou.com:4110/b/dptv.php?src=dptv&tvn=zjws&pwd=yuntutv&nwtime=1410314050&sign=681d508569e824d878b6c9e838093507";
//	private String testUrl = "http://live.itv.doplive.com.cn/live347/index_512k.m3u8?date=20140910232707&uid=0&rnd=2014091023270725442&appk=4MUAHmQFf1YR&key=3ce95e5bf0dd8e336e8ce142851119d8&rnd2=52597";
//	private String testUrl = "http://10.48.114.12:8007/TV/0/00000001000000050000000000000002/4.m3u8?TVODStartTime=1410364800&TVODEndTime=1410368400";
//	private String testUrl = "http://zb.v.qq.com:1863/?progid=3900155972&ostype=ios";
	private String testUrl = "http://vapptime.cntv.wscdns.com/cache/24_/seg0/index.m3u8";
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		
		urlPlay = (Button)findViewById(R.id.bt_net_play);
		fileSelect = (Button)findViewById(R.id.bt_select_file);
		inputUrl = (EditText)findViewById(R.id.et_net_url);
		
		urlPlay.setOnClickListener(this);
		fileSelect.setOnClickListener(this);
		
		inputUrl.setText(testUrl);
	}


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch(arg0.getId()){
		case R.id.bt_net_play:
//			Uri netUri = Uri.parse(inputUrl.getText().toString().trim());
			Uri netUri = Uri.parse(testUrl.trim());
			Intent netIntent = new Intent(this, VideoPlayerActivity.class);
			netIntent.setData(netUri);
			startActivity(netIntent);
			break;
			
		case R.id.bt_select_file:
			Intent fileIntent = new Intent(this, FFMpegFileExplorer.class);
			startActivity(fileIntent);
			break;
			
		default:
			break;
		}
	}
}
