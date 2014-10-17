package tv.danmaku.ijk.media.app.activity;

import java.util.ArrayList;
import java.util.List;

import tv.danmaku.ijk.media.app.AppConst;
import tv.danmaku.ijk.media.app.adapter.ChannelListAdapter;
import tv.danmaku.ijk.media.app.bean.ChannelBean;
import tv.danmaku.ijk.media.demo.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;

public class ChannelListActivity extends Activity{
	
	private ListView channelList;
	private List<ChannelBean> mChannelBeanList;
	private ChannelListAdapter channelListAdapter;
	private int[] resourceIdArray = {
			R.drawable.channel_program05,
			R.drawable.channel_program01,
			R.drawable.channel_program02,
			R.drawable.channel_program04,
			R.drawable.channel_program03,
			R.drawable.channel_program06,
			R.drawable.channel_program07,
			
			R.drawable.channel_program02,
			R.drawable.channel_program04,
			R.drawable.channel_program03,
			
			R.drawable.channel_program05,
			R.drawable.channel_program01,
			R.drawable.channel_program02,
			R.drawable.channel_program04,
			R.drawable.channel_program03,
			R.drawable.channel_program06,
			R.drawable.channel_program07,
			
			R.drawable.channel_program02,
			R.drawable.channel_program04,
			R.drawable.channel_program03,
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.channel_box_activity);
		int channelType = getIntent().getIntExtra("channelType", 2);
		initView(channelType);
	}

	private void initView(int channelType) {
		// TODO Auto-generated method stub
		channelList = (ListView)findViewById(R.id.channel_box_listview);
	
		getChannelList(channelType);
		channelListAdapter = new ChannelListAdapter(this, mChannelBeanList);
		channelList.setAdapter(channelListAdapter);
		channelListAdapter.setOnClickListener(onClickListener);
		channelListAdapter.notifyDataSetChanged();
	}
	
	public void getChannelList(int channelType){
		int channelNum = 1;
		String[][] channelMap = null;
		switch(channelType){
		case 2:
			channelMap = AppConst.cctvChannelMap;
			break;
			
		case 3:
			channelMap = AppConst.localChannelMap;
			break;
		
		default:
			channelMap = AppConst.cctvChannelMap;
			break;
		}
		
		mChannelBeanList = new ArrayList<ChannelBean>();
		channelNum = channelMap.length;
		for(int i = 0; i < 20; i++){
			int channelIndex = i % channelNum;
			ChannelBean channelBean = new ChannelBean();
			channelBean.setChannelName(channelMap[channelIndex][0]);
			channelBean.setUrl(channelMap[channelIndex][1]);
			channelBean.setPeriodTime("08:00~09:00");
			channelBean.setImageResourceId(resourceIdArray[channelIndex]);
			mChannelBeanList.add(channelBean);
		}
	}
	
	private OnClickListener onClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			int position = Integer.parseInt(view.getTag().toString());
			ChannelBean channelBean = mChannelBeanList.get(position);
			Intent intent = new Intent(ChannelListActivity.this, MediaPlayerActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable("channel", channelBean);
			intent.putExtras(bundle);
			startActivity(intent);
		}
	};
}
