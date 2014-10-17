package tv.danmaku.ijk.media.app.util;

import android.content.Context;
import android.net.TrafficStats;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class NetworkSpeed {
	
	public static long lastRxBytes;
	public static boolean firstFlag = true;
	public NetworkSpeed(Context context,int type) {
		// TODO Auto-generated constructor stub
		WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE); 
		WifiInfo    wifiInfo     = wifiManager.getConnectionInfo();
	}
	
	public static long getTotalRxBytes(){  //获取总的接受字节数，包含Mobile和WiFi等  
		long totalRxBytes = TrafficStats.getTotalRxBytes()==TrafficStats.UNSUPPORTED?0:(TrafficStats.getTotalRxBytes()/1024);  
		return totalRxBytes;
    }
	
	public static long getSpeed(){
		if(firstFlag){
			lastRxBytes = TrafficStats.getTotalRxBytes()==TrafficStats.UNSUPPORTED?0:(TrafficStats.getTotalRxBytes()/1024); 
			firstFlag = false;
			return 100;
		}
		long totalRxBytes = TrafficStats.getTotalRxBytes()==TrafficStats.UNSUPPORTED?0:(TrafficStats.getTotalRxBytes()/1024); 
		long speed = totalRxBytes - lastRxBytes;
		lastRxBytes = totalRxBytes;
		return speed;
	}
	
	public static long getUidRxBytes(int uid){
		return TrafficStats.getUidRxBytes(uid)==TrafficStats.UNSUPPORTED?0:(TrafficStats.getUidRxBytes(uid)/1024);
	}
}
