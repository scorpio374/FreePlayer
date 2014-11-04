package tv.danmaku.ijk.media.app.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tv.danmaku.ijk.media.app.bean.NewsProgramBean;
import tv.danmaku.ijk.media.app.bean.ProgramData;
import tv.danmaku.ijk.media.app.bean.ProgramItem;
import tv.danmaku.ijk.media.demo.R;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FindFragment extends Fragment {

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
		initView();
		return v;
	}

	private void initView() {
		// TODO Auto-generated method stub
		String json = getFileFromAssets("yuntu_find_portal_json_20141103.txt");
		// Log.d("Debug","json:"+json);
		parseJson(json);

	}

	private void parseJson(String json) {
		// TODO Auto-generated method stub
		if(TextUtils.isEmpty(json)){
			Log.e("Debug","parseJson:"+json);
		}
		try {
			JSONObject jsonObject = new JSONObject(json);
			JSONObject dataObject = jsonObject.getJSONObject("data");
			JSONArray programDataArray = dataObject.getJSONArray("data");
			ArrayList<ProgramData> mProgramDatas = new ArrayList<ProgramData>();
			for (int i = 0; i < programDataArray.length(); i++) {
				JSONObject programDataObject = programDataArray.getJSONObject(i);
				String url = programDataObject.getString("url");
				
				JSONArray programItemArray = programDataObject.getJSONArray("items");
				ArrayList<ProgramItem> programItems = new ArrayList<ProgramItem>();
				for (int j = 0; j < programItemArray.length(); j++) {
					JSONObject programItemObject = programItemArray.getJSONObject(j);
					String title = programItemObject.getString("title");
					String isNew = programItemObject.getString("new");
					
					ProgramItem programItem = new ProgramItem();
					programItem.setTitle(title);
					programItems.add(programItem);
					Log.d("Debug","programItem title:"+title+" new:"+isNew);
				}
				
				ProgramData programData = new ProgramData();
				programData.setImgUrl(url);
				mProgramDatas.add(programData);
				Log.d("Debug", "programData imgUrl:" + url);
			}
			
			NewsProgramBean newsProgramBean = new NewsProgramBean();
			int count = dataObject.getInt("count");
			newsProgramBean.setCount(count);

			Log.d("Debug", "count:" + count);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("Debug", "parseJson end");
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

}
