package tv.danmaku.ijk.media.app.util;

import java.io.File;
import java.io.FileFilter;
import java.util.Arrays;
import java.util.Comparator;

import tv.danmaku.ijk.media.app.activity.MediaPlayerActivity;
import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import cn.sz.free.player.R;

public class FFMpegFileExplorer extends ListActivity {

	private static final String TAG = "FFMpegFileExplorer";
	
	private String 			mRoot = "/sdcard";
	private TextView 		mTextViewLocation;
	private File[]			mFiles;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.ffmpeg_file_explorer);
		mTextViewLocation = (TextView) findViewById(R.id.textview_path);
		getDirectory(mRoot);
	}
	
	protected static boolean checkExtension(File file) {
//		String[] exts = FFMpeg.EXTENSIONS;
//		for(int i=0;i<exts.length;i++) {
//			if(file.getName().indexOf(exts[i]) > 0) {
//				return true;
//			}
//		}
//		return false;
		return true;
	}
	
	private void sortFilesByDirectory(File[] files) {
		Arrays.sort(files, new Comparator<File>() {

			public int compare(File f1, File f2) {
				return Long.valueOf(f1.length()).compareTo(f2.length());
			}
			
		});
	}

	private void getDirectory(String dirPath) {
		try {
			mTextViewLocation.setText("Location: " + dirPath);
	
			File f = new File(dirPath);
			File[] temp = f.listFiles(new FileFilter() {
				
				@Override
				public boolean accept(File file) {
					// TODO Auto-generated method stub
					if(file.getName().startsWith(".")){
						return false;
					}
					return true;
				}
			});
			
			sortFilesByDirectory(temp);
			
			File[] files = null;
			if(!dirPath.equals(mRoot)) {
				files = new File[temp.length + 1];
				System.arraycopy(temp, 0, files, 1, temp.length);
				files[0] = new File(f.getParent());
			} else {
				
				files = temp;
			}
			
			mFiles = files;
			setListAdapter(new FileExplorerAdapter(this, files, temp.length == files.length));
		} catch(Exception ex) {
			FFMpegMessageBox.show(this, "Error", ex.getMessage());
		}
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		File file = mFiles[position];

		if (file.isDirectory()) {
			if (file.canRead())
				getDirectory(file.getAbsolutePath());
			else {
				FFMpegMessageBox.show(this, "Error", "[" + file.getName() + "] folder can't be read!");
			}
		} else {
//			if(!checkExtension(file)) {				StringBuilder strBuilder = new StringBuilder();
//				for(int i=0;i<FFMpeg.EXTENSIONS.length;i++)
//					strBuilder.append(FFMpeg.EXTENSIONS[i] + " ");
//				FFMpegMessageBox.show(this, "Error", "File must have this extensions: " + strBuilder.toString());
//				return;
//			}
			
			startPlayer(file.getAbsolutePath());
		}
	}
	
	private void startPlayer(String filePath) {
//    	Intent i = new Intent(this, FFMpegPlayerActivity.class);
//    	i.putExtra(getResources().getString(R.string.input_file), filePath);
//    	startActivity(i);
    	
		
		//隐式启动视频播放器的方法，存在问题
//    	Uri localUri = Uri.parse(filePath);
//        Intent localIntent = new Intent(Intent.ACTION_VIEW);
//        localIntent.setDataAndType(localUri, "video/*");
//        startActivity(localIntent);
		
		Uri netUri = Uri.parse(filePath.trim());
//		Intent netIntent = new Intent(this, VideoPlayerActivity.class);
		Intent netIntent = new Intent(this, MediaPlayerActivity.class);
		netIntent.setData(netUri);
		startActivity(netIntent);
    }
	
}
