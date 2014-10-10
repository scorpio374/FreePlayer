package tv.danmaku.ijk.media.app.activity;

import java.util.ArrayList;
import java.util.List;

import tv.danmaku.ijk.media.app.adapter.ChannelListAdapter;
import tv.danmaku.ijk.media.app.bean.ChannelBean;
import tv.danmaku.ijk.media.app.bean.ChannelInfo;
import tv.danmaku.ijk.media.demo.R;
import tv.danmaku.ijk.media.demo.VideoPlayerActivity;
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
			R.drawable.channel_program01,
			R.drawable.channel_program02,
			R.drawable.channel_program03,
			R.drawable.channel_program04,
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.channel_box_activity);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		channelList = (ListView)findViewById(R.id.channel_box_listview);
		mChannelBeanList = new ArrayList<ChannelBean>();
		
		int channelNum = ChannelInfo.channelInfoMap.length;
		for(int i = 0; i < 15; i++){
			int channelIndex = i % channelNum;
			ChannelBean channelBean = new ChannelBean();
			channelBean.setChannelName(ChannelInfo.channelInfoMap[channelIndex][0]);
			channelBean.setUrl(ChannelInfo.channelInfoMap[channelIndex][1]);
			channelBean.setPeriodTime("08:00~09:00");
			channelBean.setImageResourceId(resourceIdArray[channelIndex]);
			mChannelBeanList.add(channelBean);
		}
		
		channelListAdapter = new ChannelListAdapter(this, mChannelBeanList);
		channelList.setAdapter(channelListAdapter);
		channelListAdapter.setOnClickListener(onClickListener);
		channelListAdapter.notifyDataSetChanged();
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
