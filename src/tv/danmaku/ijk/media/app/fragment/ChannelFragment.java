package tv.danmaku.ijk.media.app.fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import tv.danmaku.ijk.media.app.AppConst;
import tv.danmaku.ijk.media.app.activity.ChannelListActivity;
import tv.danmaku.ijk.media.app.adapter.ChannelBoxAdapter;
import tv.danmaku.ijk.media.app.adapter.ProgramPagerAdapter;
import tv.danmaku.ijk.media.app.bean.ChannelBoxBean;
import tv.danmaku.ijk.media.app.widget.NoScrollGridView;
import tv.danmaku.ijk.media.demo.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;

public class ChannelFragment extends Fragment implements OnPageChangeListener{
	
	protected static final int MSG_UPDATE_VIEWPAGER = 9000;
	private static final long DELAY_PAGER_TIMER = 7*1000;
	private static final long PERIOD_PAGER_TIMER = 7*1000;
	
	/**
	 * ViewPager
	 */
	private ViewPager viewPager;
	
	/**
	 * 装点点的ImageView数组
	 */
	private ImageView[] tips;
	
	/**
	 * 装ImageView数组
	 */
	private ImageView[] mImageViews;
	
	/**
	 * 图片资源id
	 */
	private int[] imgIdArray ;

	private Context context;
	private ProgramPagerAdapter pagerAdapter;
	private NoScrollGridView gridView;
	private ChannelBoxAdapter channelBoxAdapter;
	private List<ChannelBoxBean> mChannelBoxList;
	
	private int[] channelDrawableArray = {
			R.drawable.box_favourite,R.drawable.box_special,R.drawable.box_yangshi,
			R.drawable.box_weishi,R.drawable.box_sport,R.drawable.box_movie,
			R.drawable.box_game,R.drawable.box_edu,R.drawable.box_life,
			R.drawable.box_news,R.drawable.box_conties,R.drawable.box_more,
	};

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.d("Debug","onCreateView");
		View v = inflater.inflate(R.layout.fragment_channel, null);
		initViewPager(v);
		initGridView(v);
		return v;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		Log.d("Debug","onCreate");
		super.onCreate(savedInstanceState);
		context = getActivity().getApplicationContext();
		Timer mTimer = new Timer();
		mTimer.schedule(pagerTimerTask, DELAY_PAGER_TIMER, PERIOD_PAGER_TIMER);
	}
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}
	
	private void initViewPager(View v) {
		// TODO Auto-generated method stub
		ViewGroup group = (ViewGroup)v.findViewById(R.id.channel_hot_group);
		viewPager = (ViewPager) v.findViewById(R.id.viewPager);
		
		//载入图片资源ID
		imgIdArray = new int[]{R.drawable.day_hot_1, R.drawable.day_hot_2, R.drawable.day_hot_3, 
				R.drawable.day_hot_4,R.drawable.day_hot_5};
		
		
		//将点点加入到ViewGroup中
		tips = new ImageView[imgIdArray.length];
		for(int i=0; i<tips.length; i++){
			ImageView imageView = new ImageView(context);
//	    	imageView.setLayoutParams(new LayoutParams(10,10));
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(10, 10);  // , 1是可选写的
			lp.setMargins(10, 0, 0, 0); 
			imageView.setLayoutParams(lp); 
	    	tips[i] = imageView;
	    	if(i == 0){
	    		tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
	    	}else{
	    		tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
	    	}
	    	
	    	 group.addView(imageView);
		}
		
		
		//将图片装载到数组中
		mImageViews = new ImageView[imgIdArray.length];
		for(int i=0; i<mImageViews.length; i++){
			ImageView imageView = new ImageView(context);
			mImageViews[i] = imageView;
			imageView.setBackgroundResource(imgIdArray[i]);
			imageView.setScaleType(ScaleType.FIT_XY);
		}
		
		//设置Adapter
		pagerAdapter = new ProgramPagerAdapter(context, mImageViews);
		viewPager.setAdapter(pagerAdapter);
		//设置监听，主要是设置点点的背景
		viewPager.setOnPageChangeListener(this);
		//设置ViewPager的默认项, 设置为长度的100倍，这样子开始就能往左滑动
		viewPager.setCurrentItem((mImageViews.length) * 100);
	}
	
	private void initGridView(View v){
		gridView = (NoScrollGridView)v.findViewById(R.id.channel_box_gridview);
		mChannelBoxList = new ArrayList<ChannelBoxBean>();
		
		for(int i = 0; i < AppConst.channelNameArray.length; i++){
			ChannelBoxBean channelBoxBean = new ChannelBoxBean();
			channelBoxBean.setBoxName(AppConst.channelNameArray[i]);
			channelBoxBean.setBoxResourceId(channelDrawableArray[i]);
			mChannelBoxList.add(channelBoxBean);
		}
			
		channelBoxAdapter = new ChannelBoxAdapter(context,mChannelBoxList);
		gridView.setAdapter(channelBoxAdapter);
		gridView.setOnItemClickListener(mOnItemClickListener);
		channelBoxAdapter.notifyDataSetChanged();
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		
	}

	/**
	 * 动画待续---------
	 */
	@Override
	public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		//
	}

	@Override
	public void onPageSelected(int arg0) {
		setImageBackground(arg0 % mImageViews.length);
	}
	
	/**
	 * 设置选中的tip的背景
	 * @param selectItems
	 */
	private void setImageBackground(int selectItems){
		for(int i=0; i<tips.length; i++){
			if(i == selectItems){
				tips[i].setBackgroundResource(R.drawable.page_indicator_focused);
			}else{
				tips[i].setBackgroundResource(R.drawable.page_indicator_unfocused);
			}
		}
	}
	
	private TimerTask pagerTimerTask = new TimerTask(){
		@Override
		public void run() {
			// TODO Auto-generated method stub
			mHandler.sendEmptyMessage(MSG_UPDATE_VIEWPAGER);
		}
	};
	
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch(msg.what){
			case MSG_UPDATE_VIEWPAGER:
				changeViewPager();
				break;
				
			default:
				break;
			}
			super.handleMessage(msg);
		}
	};

	protected void changeViewPager() {
		// TODO Auto-generated method stub
		if(viewPager != null){
			int count = viewPager.getCurrentItem();
			viewPager.setCurrentItem(count+1,true);
		}
	}
	
	private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {
		
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			// TODO Auto-generated method stub
			Log.d("Debug","onItemClick");
			Intent intent = new Intent(context, ChannelListActivity.class);
			intent.putExtra("channelType", position);
			startActivity(intent);
		}
	};
	
}
