package tv.danmaku.ijk.media.app.widget;

import android.content.Context;
import android.view.ActionProvider;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.view.View;
import cn.sz.free.player.R;

public class ChannelActionProvider extends ActionProvider {

	public ChannelActionProvider(Context context) {
		super(context);
	}

	@Override
	public View onCreateActionView() {
		return null;
	}

	@Override
	public void onPrepareSubMenu(SubMenu subMenu) {
		subMenu.clear();
		subMenu.add("sub item 1").setIcon(R.drawable.ic_launcher)
				.setOnMenuItemClickListener(new OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						return true;
					}
				});
		subMenu.add("sub item 2").setIcon(R.drawable.ic_launcher)
				.setOnMenuItemClickListener(new OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						return false;
					}
				});
		subMenu.add("sub item 3").setIcon(R.drawable.ic_launcher)
				.setOnMenuItemClickListener(new OnMenuItemClickListener() {
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						return false;
					}
				});
	}

	@Override
	public boolean hasSubMenu() {
		return true;
	}

}
