package tv.danmaku.ijk.media.app.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tv.danmaku.ijk.media.app.adapter.NewsProgramAdapter;
import tv.danmaku.ijk.media.app.bean.NewsProgramBean;
import tv.danmaku.ijk.media.app.bean.ProgramData;
import tv.danmaku.ijk.media.app.bean.ProgramItem;
import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import cn.sz.free.player.R;

public class NewsActivity extends Activity {

	private ListView mListView;
	private Button headerButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news);
		
		initView();

		ActionBar actionBar = getActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(false);
			actionBar.setHomeButtonEnabled(true);
			actionBar.setDisplayShowHomeEnabled(true);
			actionBar.setDisplayShowCustomEnabled(true);
			
			LayoutInflater inflator = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        View v = inflator.inflate(R.layout.actionbar_news, null);
	        ActionBar.LayoutParams layout = new ActionBar.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
	        actionBar.setCustomView(v,layout);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.menu_news, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	private void initView() {
		// TODO Auto-generated method stub
		mListView = (ListView)findViewById(R.id.news_listview);
		View headerView = getLayoutInflater().inflate(R.layout.news_listview_headerview, null);
		mListView.addHeaderView(headerView);
		
		String json = getFileFromAssets("yuntu_find_portal_json_20141103.txt");
		ArrayList<ProgramData> programDatas = parseJson(json);
		NewsProgramAdapter mNewsProgramAdapter = new NewsProgramAdapter(this, programDatas);
		mListView.setAdapter(mNewsProgramAdapter);
		mNewsProgramAdapter.notifyDataSetChanged();
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
}
