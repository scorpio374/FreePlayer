package tv.danmaku.ijk.media.app.adapter;

import java.util.List;

import tv.danmaku.ijk.media.app.bean.ChannelBoxBean;
import tv.danmaku.ijk.media.demo.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

public class ChannelBoxAdapter extends BaseAdapter {

	private Context context;
	private List<ChannelBoxBean> mChannelBoxList;
	
	public ChannelBoxAdapter(Context context, List<ChannelBoxBean> mChannelBoxList){
		this.context = context;
		this.mChannelBoxList = mChannelBoxList;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mChannelBoxList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mChannelBoxList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		View v =LayoutInflater.from(context).inflate(R.layout.item_channel_box,null);
		Button button = (Button)v.findViewById(R.id.channel_box_button);
		button.setText(mChannelBoxList.get(position).getBoxName());
		Drawable drawable= context.getResources().getDrawable(mChannelBoxList.get(position).getBoxResourceId());
		drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
		button.setCompoundDrawables(null, drawable, null, null);
		return v;
	}

}
