package tv.danmaku.ijk.media.app.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tv.danmaku.ijk.media.app.activity.ChannelListActivity;
import tv.danmaku.ijk.media.app.activity.NewsActivity;
import tv.danmaku.ijk.media.app.adapter.NewsProgramAdapter;
import tv.danmaku.ijk.media.app.bean.NewsProgramBean;
import tv.danmaku.ijk.media.app.bean.ProgramData;
import tv.danmaku.ijk.media.app.bean.ProgramItem;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import cn.sz.free.player.R;

public class FindFragment extends Fragment {

	private ListView mListView;
	private NewsProgramAdapter mNewsProgramAdapter;
	private ScrollView mScrollView;
	private int scrollX = 0;
	private int scrollY = 0;

	Button hotButton;
	Button shakeButton;
	Button beautyButton;
	Button webresourceButton;
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_find, null);
		initView(v);
		initParams();
		return v;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		scrollX = mScrollView.getScrollX();
		scrollY = mScrollView.getScrollY();
		super.onPause();
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		mScrollView.smoothScrollTo(scrollX, scrollY);
		mScrollView.scrollTo(scrollX, scrollY);
		mScrollView.post(new Runnable() {   
		    public void run() {  
		    	mScrollView.scrollTo(scrollX, scrollY);  
		    }   
		});
		super.onResume();
	}

	private void initView(View v) {
		// TODO Auto-generated method stub
		mListView = (ListView) v.findViewById(R.id.find_listview);
		mScrollView = (ScrollView) v.findViewById(R.id.find_scrollview);
		hotButton = (Button)v.findViewById(R.id.find_hot_button);
		shakeButton = (Button)v.findViewById(R.id.find_shake_button);
		beautyButton = (Button)v.findViewById(R.id.find_beauty_button);
		webresourceButton = (Button)v.findViewById(R.id.find_webresource_button);
	}

	private void initParams() {
		// TODO Auto-generated method stub
		String json = getFileFromAssets("yuntu_find_portal_json_20141103.txt");
		// Log.d("Debug","json:"+json);
		ArrayList<ProgramData> programDatas = parseJson(json);
		mNewsProgramAdapter = new NewsProgramAdapter(getActivity(), programDatas);
		mListView.setAdapter(mNewsProgramAdapter);
		mListView.setOnItemClickListener(mOnItemClickListener);
		mNewsProgramAdapter.notifyDataSetChanged();
		
		hotButton.setOnClickListener(mOnClickListener);
	}

	private ArrayList<ProgramData> parseJson(String json) {
		// TODO Auto-generated method stub
		NewsProgramBean newsProgramBean = null;
		ArrayList<ProgramData> programDatas = new ArrayList<ProgramData>();
		if (TextUtils.isEmpty(json)) {
			return programDatas;
		}
		try {
			JSONObject jsonObject = new JSONObject(json);
			JSONObject dataObject = jsonObject.getJSONObject("data");
			JSONArray programDataArray = dataObject.getJSONArray("data");
			for (int i = 0; i < programDataArray.length(); i++) {
				JSONObject programDataObject = programDataArray
						.getJSONObject(i);
				String url = programDataObject.getString("url");

				JSONArray programItemArray = programDataObject
						.getJSONArray("items");
				ArrayList<ProgramItem> programItems = new ArrayList<ProgramItem>();
				for (int j = 0; j < programItemArray.length(); j++) {
					JSONObject programItemObject = programItemArray
							.getJSONObject(j);
					String title = programItemObject.getString("title");
					String isNew = programItemObject.getString("new");

					ProgramItem programItem = new ProgramItem();
					programItem.setTitle(title);
					programItem.setIsNew(isNew);
					programItems.add(programItem);
					Log.d("Debug", "programItem title:" + title + " new:"
							+ isNew);
				}

				ProgramData programData = new ProgramData();
				programData.setImgUrl(url);
				programData.setProgramItems(programItems);
				programDatas.add(programData);
				Log.d("Debug", "programData imgUrl:" + url);
			}

			newsProgramBean = new NewsProgramBean();
			int count = dataObject.getInt("count");
			newsProgramBean.setCount(count);

			Log.d("Debug", "count:" + count);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("Debug", "parseJson end");
		return programDatas;
	}

	private String getFileFromAssets(String fileName) {
		try {
			InputStream in = getResources().getAssets().open(fileName);
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					in, "UTF-8"));
			StringBuffer sb = new StringBuffer();
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(getActivity(),NewsActivity.class);
			startActivity(intent);
		}
	};
	
	private OnClickListener mOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch (view.getId()) {
			case R.id.find_hot_button:
				Intent intent = new Intent(getActivity(),ChannelListActivity.class);
				intent.putExtra("channelType", 0);
				startActivity(intent);
				break;
				
			case R.id.find_shake_button:
				break;
				
			case R.id.find_beauty_button:
				break;
				
			case R.id.find_webresource_button:
				break;

			default:
				break;
			}
		}
	};
}
