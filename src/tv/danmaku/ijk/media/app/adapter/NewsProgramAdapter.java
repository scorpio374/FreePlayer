package tv.danmaku.ijk.media.app.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import tv.danmaku.ijk.media.app.bean.ProgramData;
import tv.danmaku.ijk.media.app.bean.ProgramItem;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cn.sz.free.player.R;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

public class NewsProgramAdapter extends BaseAdapter {

	private Context mContext;
	private ArrayList<ProgramData> mProgramDatas;
	private DisplayImageOptions options;
	private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

	public NewsProgramAdapter(Context context,
			ArrayList<ProgramData> programDatas) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.mProgramDatas = programDatas;

		// initImageLoader(mContext);
		options = new DisplayImageOptions.Builder()
				.showImageOnLoading(R.drawable.ic_stub)
				.showImageForEmptyUri(R.drawable.ic_empty)
				.showImageOnFail(R.drawable.ic_error).cacheInMemory(true)
				.cacheOnDisk(true).considerExifParams(true)
				.displayer(new RoundedBitmapDisplayer(20)).build();
	}

	@Override
	public int getCount() {
		return mProgramDatas.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mProgramDatas.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ProgramData programData = mProgramDatas.get(arg0);
		ArrayList<ProgramItem> programItems = programData.getProgramItems();
		if (programData == null || programItems == null) {
			return null;
		}

		View view = LayoutInflater.from(mContext).inflate(
				R.layout.item_find_listview, null);
		for (int i = 0; i < programItems.size(); i++) {
			if (i > 2) {
				break;
			}
			ProgramItem programItem = programItems.get(i);
			ImageView topicImageView = null;
			ImageView imageView = null;
			TextView textView = null;

			topicImageView = (ImageView) view
					.findViewById(R.id.find_listview_topic_img);
			String topImageUrl = programData.getImgUrl();
			ImageLoader.getInstance().displayImage(topImageUrl, topicImageView,
					options, animateFirstListener);

			if (i == 0) {
				imageView = (ImageView) view
						.findViewById(R.id.find_listview_img01);
				textView = (TextView) view
						.findViewById(R.id.find_listview_tv01);
			} else if (i == 1) {
				imageView = (ImageView) view
						.findViewById(R.id.find_listview_img02);
				textView = (TextView) view
						.findViewById(R.id.find_listview_tv02);
			} else if (i == 2) {
				imageView = (ImageView) view
						.findViewById(R.id.find_listview_img03);
				textView = (TextView) view
						.findViewById(R.id.find_listview_tv03);
			}

			if (imageView != null) {
				if (programItem.getIsNew().equals("Y")) {
					imageView.setImageResource(R.drawable.ic_topic_new);
				} else {
					imageView.setImageResource(R.drawable.ic_topic_hot);
				}
			}
			if (textView != null) {
				int index = i + 1;
				textView.setText(index + "." + programItem.getTitle());
			}
		}
		return view;
	}

	private static class AnimateFirstDisplayListener extends
			SimpleImageLoadingListener {

		static final List<String> displayedImages = Collections
				.synchronizedList(new LinkedList<String>());

		@Override
		public void onLoadingComplete(String imageUri, View view,
				Bitmap loadedImage) {
			if (loadedImage != null) {
				ImageView imageView = (ImageView) view;
				boolean firstDisplay = !displayedImages.contains(imageUri);
				if (firstDisplay) {
					FadeInBitmapDisplayer.animate(imageView, 500);
					displayedImages.add(imageUri);
				}
			}
		}
	}
}
