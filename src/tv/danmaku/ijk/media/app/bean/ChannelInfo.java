package tv.danmaku.ijk.media.app.bean;

public class ChannelInfo {
	public static String[][] channelInfoMap = {
			{"浙江卫视","http://vapptime.cntv.wscdns.com/cache/64_/seg0/index.m3u8"},
			{"江苏卫视","http://219.232.160.143:5080/hls/2ae90f52c9d90a319d6dcaadeb49844a.m3u8"},
			{"东方卫视","http://zb.v.qq.com:1863/?progid=3900155972"},
			{"安徽卫视","http://zb.v.qq.com:1863/?progid=623043810"},
	};

	public static String[][] getChannelInfoMap() {
		return channelInfoMap;
	}
}
