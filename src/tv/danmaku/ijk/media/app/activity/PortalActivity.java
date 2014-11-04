package tv.danmaku.ijk.media.app.activity;

import java.util.ArrayList;
import java.util.List;

import tv.danmaku.ijk.media.app.adapter.FragmentTabAdapter;
import tv.danmaku.ijk.media.app.fragment.ChannelFragment;
import tv.danmaku.ijk.media.app.fragment.FindFragment;
import tv.danmaku.ijk.media.app.fragment.MyFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import cn.sz.free.player.R;

public class PortalActivity extends FragmentActivity implements
		OnCheckedChangeListener {

	private FragmentTabHost mTabHost;
	private RadioGroup mRadioGroup;
	public List<Fragment> fragments = new ArrayList<Fragment>();

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.portal_activity);
		fragments.add(new ChannelFragment());
		fragments.add(new FindFragment());
		fragments.add(new MyFragment());

		mRadioGroup = (RadioGroup) findViewById(R.id.radiogroup);
		FragmentTabAdapter tabAdapter = new FragmentTabAdapter(this, fragments,
				R.id.realtabcontent, mRadioGroup);
		tabAdapter
				.setOnRgsExtraCheckedChangedListener(new FragmentTabAdapter.OnRgsExtraCheckedChangedListener() {
					@Override
					public void OnRgsExtraCheckedChanged(RadioGroup radioGroup,
							int checkedId, int index) {
						System.out.println("Extra---- " + index
								+ " checked!!! ");
					}
				});

		// initView();
	}

	private void initView() {
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
		mTabHost.getTabWidget().setVisibility(View.GONE);

		mTabHost.addTab(mTabHost.newTabSpec("channel").setIndicator("Channel"),
				ChannelFragment.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("find").setIndicator("Find"),
				FindFragment.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("my").setIndicator("My"),
				MyFragment.class, null);

		mTabHost.setCurrentTabByTag("channel");
		((RadioButton) findViewById(R.id.radio_channel)).setChecked(true);
		mRadioGroup = (RadioGroup) findViewById(R.id.radiogroup);
		mRadioGroup.setOnCheckedChangeListener(this);
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		FragmentManager fm = getSupportFragmentManager();
		ChannelFragment channel = (ChannelFragment) (fm
				.findFragmentByTag("channel"));
		FindFragment find = (FindFragment) fm.findFragmentByTag("find");
		MyFragment my = (MyFragment) fm.findFragmentByTag("my");

		FragmentTransaction ft = fm.beginTransaction();

		// ** Detaches the androidfragment if exists */
		// if (channel != null)
		// ft.detach(channel);
		// if (find != null)
		// ft.detach(find);
		// if (my != null)
		// ft.detach(my);

		switch (checkedId) {
		case R.id.radio_channel:
			if (channel == null) {
				ft.add(R.id.realtabcontent, new ChannelFragment(), "channel");
			} else {
				ft.attach(channel);
			}
			mTabHost.setCurrentTabByTag("channel");
			break;
		case R.id.radio_find:
			if (find == null) {
				ft.add(R.id.realtabcontent, new FindFragment(), "find");
			} else {
				ft.attach(find);
			}
			mTabHost.setCurrentTabByTag("find");
			break;
		case R.id.radio_my:
			if (my == null) {
				ft.add(R.id.realtabcontent, new MyFragment(), "my");
			} else {
				ft.attach(my);
			}
			mTabHost.setCurrentTabByTag("my");
			break;

		default:
			break;
		}
		ft.commit();

	}
}
