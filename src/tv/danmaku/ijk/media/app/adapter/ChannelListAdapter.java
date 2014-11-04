package tv.danmaku.ijk.media.app.adapter;

import java.util.List;

import tv.danmaku.ijk.media.app.bean.ChannelBean;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import cn.sz.free.player.R;

public class ChannelListAdapter extends BaseAdapter {

	private Context context;
	private List<ChannelBean> mChannelBeanList; 
	private OnClickListener onClickListener;
	
	public ChannelListAdapter(Context context,List<ChannelBean> mChannelBeanList) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.mChannelBeanList = mChannelBeanList;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mChannelBeanList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mChannelBeanList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(context).inflate(R.layout.item_channel, null);
		Holder holder = new Holder();
		holder.channelName = (TextView)view.findViewById(R.id.tv_channel_name);
		holder.periodTime = (TextView)view.findViewById(R.id.tv_program_period);
		holder.button = (Button)view.findViewById(R.id.btn_program_play);
		
		holder.channelName.setText(mChannelBeanList.get(position).getChannelName());
		holder.periodTime.setText(mChannelBeanList.get(position).getPeriodTime());
		holder.button.setBackgroundResource(mChannelBeanList.get(position).getImageResourceId());
		holder.button.setOnClickListener(onClickListener);
		holder.button.setTag(position);
		return view;
	}
	
	public void setOnClickListener(OnClickListener onClickListener){
		this.onClickListener = onClickListener;
	}
	
	private class Holder{
		TextView channelName;
		TextView periodTime;
		Button button;
	}
}
