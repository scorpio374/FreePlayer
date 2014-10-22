package tv.danmaku.ijk.media.app;

public class AppConst {
	
	public static String[] channelNameArray = {
			"热播频道","VIP频道","央视频道",
			"卫视频道","体育频道","影视频道",
			"游戏频道","教育频道","生活频道",
			"资讯频道","地方频道","添加",
			};
	
	public static String[][] localChannelMap = {
//		{"时移测试","http://124.161.62.197:9000/TV/0/00000000000000050000000000000014/9.m3u8?bitrate=auto&tvodstarttime=1410364800&tvodendtime=1410368400"},
		{"时移测试",  "http://124.161.62.197:9000/TV/0/00000000000000050000000000000209/10.m3u8"},	
//		{"时移测试",  "http://124.161.62.196:8007/TV/0/00000000000000050000000000000209/10.m3u8?bitrate=auto"},
		{"时移测试-1","http://124.161.62.197:9000/TV/0/00000000000000050000000000000014/9.m3u8?bitrate=auto"},
		{"本地时移测试","http://192.168.3.144/hls/test/test.m3u8"},
		{"湖南卫视","http://url.52itv.cn/vlive/pptv/hnws.m3u8"},
		{"浙江卫视","http://vapptime.cntv.wscdns.com/cache/64_/seg0/index.m3u8"},
		{"江苏卫视","http://219.232.160.143:5080/hls/2ae90f52c9d90a319d6dcaadeb49844a.m3u8"},
		{"安徽卫视","http://hot.vrs.sohu.com/ipad1955485_4622696832371_4166706.m3u8?plat=17"},
		{"东方卫视","http://zb.v.qq.com:1863/?progid=3900155972"},
		{"浙江卫视回放","/mnt/sdcard/zhejiang.m3u8"},
		{"CCTV-1","http://vapptime.cntv.wscdns.com/cache/14_/seg0/index.m3u8"},
	};
	
/*	{"浙江卫视回放","http://yvhynjw.xicp.net:50594/zhejiang.m3u8"},*/

	public static String[][] cctvChannelMap = {
		{"iHome-CCTV-1","http://scgd-m3u8.3atv.cn:10009/m3u8/m3u8?id=134100&version=1&rate=400k"},
		{"iHome-CCTV-1-回放","http://scgd-m3u8.3atv.cn:10009/m3u8/m3u8?begintime=1413421680&duration=1080&mode=11&id=134100&version=1&rate=400k"},
		{"CCTV-1","http://vapptime.cntv.wscdns.com/cache/14_/seg0/index.m3u8"},
		{"CCTV-2","http://vapptime.cntv.wscdns.com/cache/204_/seg0/index.m3u8"},
		{"CCTV-3","http://vapptime.cntv.wscdns.com/cache/209_/seg0/index.m3u8"},
		{"CCTV-4","http://vapptime.cntv.wscdns.com/cache/19_/seg0/index.m3u8"},
		{"CCTV-5","http://vapptime.cntv.wscdns.com/cache/214_/seg0/index.m3u8"},
		{"CCTV-6","http://vapptime.cntv.wscdns.com/cache/219_/seg0/index.m3u8"},
		{"CCTV-7","http://vapptime.cntv.wscdns.com/cache/224_/seg0/index.m3u8"},
		{"CCTV-8","http://vapptime.cntv.wscdns.com/cache/229_/seg0/index.m3u8"},
		{"CCTV-9","http://vapptime.cntv.wscdns.com/cache/294_/seg0/index.m3u8"},
		{"CCTV-10","http://vapptime.cntv.wscdns.com/cache/234_/seg0/index.m3u8"},
		{"CCTV-11","http://vapptime.cntv.wscdns.com/cache/239_/seg0/index.m3u8"},
		{"CCTV-12","http://nowtv.tvesou.com:5023/m3u8/tvwshd/cctv12.m3u8?pwd=yuntutv&nwtime=1411568732&sign=15b03f4fdb88fde45e75d001b9abff94"},
		{"CCTV-新闻","http://vapptime.cntv.wscdns.com/cache/24_/seg0/index.m3u8"},
		{"CCTV-14","http://nowtv.tvesou.com:5023/m3u8/tvwshd/cctv14.m3u8?pwd=yuntutv&nwtime=1411568981&sign=ad6db079a050d17a26b69748ec314a0e"},
		{"CCTV-音乐","http://vapptime.cntv.wscdns.com/cache/254_/seg0/index.m3u8"},
	};
}
