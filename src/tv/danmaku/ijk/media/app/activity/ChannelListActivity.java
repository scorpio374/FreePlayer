package tv.danmaku.ijk.media.app.activity;

import java.util.ArrayList;
import java.util.List;

import tv.danmaku.ijk.media.app.AppConst;
import tv.danmaku.ijk.media.app.adapter.ChannelListAdapter;
import tv.danmaku.ijk.media.app.bean.ChannelBean;
import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SpinnerAdapter;
import cn.sz.free.player.R;

public class ChannelListActivity extends Activity {

	private ListView channelList;
	private List<ChannelBean> mChannelBeanList;
	private ChannelListAdapter channelListAdapter;
	private int[] resourceIdArray = { R.drawable.channel_program05,
			R.drawable.channel_program01, R.drawable.channel_program02,
			R.drawable.channel_program04, R.drawable.channel_program03,
			R.drawable.channel_program06, R.drawable.channel_program07,

			R.drawable.channel_program02, R.drawable.channel_program04,
			R.drawable.channel_program03,

			R.drawable.channel_program05, R.drawable.channel_program01,
			R.drawable.channel_program02, R.drawable.channel_program04,
			R.drawable.channel_program03, R.drawable.channel_program06,
			R.drawable.channel_program07,

			R.drawable.channel_program02, R.drawable.channel_program04,
			R.drawable.channel_program03, };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_channellist);
		initView();
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(false);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);
		
		//增加下拉导航
//		ArrayAdapter<?> mSpinnerAdapter = ArrayAdapter.createFromResource(this,  
//	            R.array.channel_type_array,  
//	            android.R.layout.simple_spinner_dropdown_item); 
        ArrayAdapter mSpinnerAdapter = new ArrayAdapter(this , android.R.layout.simple_spinner_item , AppConst.channelNameArray);
		mSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
		actionBar.setListNavigationCallbacks(mSpinnerAdapter,  
		        mOnNavigationListener);  
		int channelType = getIntent().getIntExtra("channelType", 2);
		actionBar.setSelectedNavigationItem(channelType);
//		initParams(channelType);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.activity_actionbar, menu);
		MenuItem searchItem = menu.findItem(R.id.action_search);
		SearchView searchView = (SearchView) searchItem.getActionView();
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {

		case android.R.id.home:
			Intent upIntent = NavUtils.getParentActivityIntent(this);
			if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
				TaskStackBuilder.create(this)
						.addNextIntentWithParentStack(upIntent)
						.startActivities();
			} else {
				upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				NavUtils.navigateUpTo(this, upIntent);
			}
			return true;

		default:
			Log.d("Debug", "onOptionsItemSelected default");
			return super.onOptionsItemSelected(item);
		}
	}

	private void initView() {
		// TODO Auto-generated method stub
		channelList = (ListView) findViewById(R.id.channel_box_listview);
	}
	
	private void initParams(int channelType){
		getChannelList(channelType);
		channelListAdapter = new ChannelListAdapter(this, mChannelBeanList);
		channelList.setAdapter(channelListAdapter);
		channelListAdapter.setOnClickListener(onClickListener);
		channelListAdapter.notifyDataSetChanged();
	}

	
	public void getChannelList(int channelType) {
		int channelNum = 1;
		String[][] channelMap = null;
		switch (channelType) {
		case AppConst.CHANNEL_TYPE_HOT:
			channelMap = AppConst.hotChannelMap;
			break;

		case AppConst.CHANNEL_TYPE_VIP:
			channelMap = AppConst.vipChannelMap;
			break;

		case AppConst.CHANNEL_TYPE_CCTV:
			channelMap = AppConst.cctvChannelMap;
			break;

		case AppConst.CHANNEL_TYPE_SATELLITE:
			channelMap = AppConst.satelliteChannelMap;
			break;

		case AppConst.CHANNEL_TYPE_SPORTS:
			channelMap = AppConst.sportsChannelMap;
			break;

		case AppConst.CHANNEL_TYPE_MOVIES:
			channelMap = AppConst.moviesChannelMap;
			break;

		case AppConst.CHANNEL_TYPE_INFO:
			channelMap = AppConst.infoChannelMap;
			break;

		case AppConst.CHANNEL_TYPE_EDUCATION:
			channelMap = AppConst.educationChannelMap;
			break;

		case AppConst.CHANNEL_TYPE_LIFE:
			channelMap = AppConst.lifeChannelMap;
			break;

		default:
			channelMap = AppConst.cctvChannelMap;
			break;
		}

		mChannelBeanList = new ArrayList<ChannelBean>();
		channelNum = channelMap.length;
		for (int i = 0; i < channelMap.length; i++) {
			int channelIndex = i % channelNum;
			ChannelBean channelBean = new ChannelBean();
			channelBean.setChannelName(channelMap[channelIndex][0]);
			channelBean.setUrl(channelMap[channelIndex][1]);
			channelBean.setPeriodTime("08:00~09:00");
			channelBean.setImageResourceId(resourceIdArray[channelIndex]);
			mChannelBeanList.add(channelBean);
		}
	}

	private Intent getShareIntent() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("image/*");
		return intent;
	}
	
	
	/** 
     * 在这里配合Fragment，实现不同的页面导航 
     */  
    OnNavigationListener mOnNavigationListener = new OnNavigationListener() {  
        @Override  
        public boolean onNavigationItemSelected(int position, long itemId) {  
        	initParams(position);
        	return true;
        }  
        
    };  

    /**
     * 点击频道播放
     */
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
