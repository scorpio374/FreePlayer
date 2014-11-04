package tv.danmaku.ijk.media.app.fragment;

import tv.danmaku.ijk.media.demo.FFMpegFileExplorer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import cn.sz.free.player.R;

public class MyFragment extends Fragment{
	
	Button btn_local_movie;
	
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment_my, null);
		initView(v);
		return v;
	}
	
	private void initView(View v){
		btn_local_movie = (Button)v.findViewById(R.id.btn_local_movie);
		btn_local_movie.setOnClickListener(mOnClickListener);
	}
	
	private OnClickListener mOnClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch(view.getId()){
			case R.id.btn_local_movie:
				Intent intent =new Intent(getActivity().getApplicationContext(),FFMpegFileExplorer.class);
				startActivity(intent);
				break;
				
			default:
				break;
			}
		}
	};

}

