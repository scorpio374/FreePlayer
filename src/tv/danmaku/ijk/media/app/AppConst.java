package tv.danmaku.ijk.media.app;

public class AppConst {
	
	public static final int CHANNEL_TYPE_HOT = 0;
	public static final int CHANNEL_TYPE_VIP = 1;
	public static final int CHANNEL_TYPE_CCTV = 2;
	public static final int CHANNEL_TYPE_SATELLITE = 3;
	public static final int CHANNEL_TYPE_SPORTS = 4;
	public static final int CHANNEL_TYPE_MOVIES = 5;
	public static final int CHANNEL_TYPE_INFO = 6;
	public static final int CHANNEL_TYPE_EDUCATION = 7;
	public static final int CHANNEL_TYPE_LIFE = 8;
	
	public static String[] channelNameArray = {
			"热播频道","VIP频道","央视频道",
			"卫视频道","体育频道","影视频道",
			"资讯频道","教育频道","生活频道",
			"游戏频道","地方频道","添加频道",
			};
	
	public static String[][] hotChannelMap = {
		{"CCTV5-HD","http://v14.tv.cctv5.cctv.com/approve/live?channel=CCTV5HD&type=ipsd"},
		{"CCTV-新闻","http://vapptime.cntv.wscdns.com/cache/24_/seg0/index.m3u8"},
		{"湖南卫视","http://url.52itv.cn/vlive/pptv/hnws.m3u8"},
		{"浙江卫视","http://vapptime.cntv.wscdns.com/cache/64_/seg0/index.m3u8"},
		{"江苏卫视","http://219.232.160.143:5080/hls/2ae90f52c9d90a319d6dcaadeb49844a.m3u8"},
		{"云图电影HD","http://g3.letv.cn/vod/v2/OTMvMjEvMTAvbGV0di11dHMvMTQvdmVyXzAwXzE2LTMwOTkxMDcyLWF2Yy00NzY1OTUtYWFjLTMyMDAxLTYxNTM0MzktMzk4NTU0OTQ4LWI4YjE3MTlmN2QyODg1Mjc3YjQ1MTgyZDc5ODk3ZjFjLTE0MTQ3NjYxMjUyNDQubXA0?b=518&mmsid=1918146&tm=1414842796&key=a9f106a662708033205fb09421840d77&platid=3&splatid=302&playid=0&tss=ios&vtype=13&cvid=351656049097&format=0&sign=mb&dname=mobile&expect=1&tag=mobile"},
		{"云图剧场HD","http://hot.vrs.sohu.com/ipad480445_4625896241605_701577.m3u8?plat=17"},
		{"电影HD","http://14.22.30.65/m3u8/lb_movie_1000/desc.m3u8?stream_id=lb_movie_1000&ltm=1414861127&lkey=1bcce0f959a65769a5b1250cc517ce8d&platid=10&splatid=1004&tag=live&video_type=m3u8&useloc=0&mslice=3&path=14.22.30.70,183.60.210.147,183.60.210.116&ver=live_3&buss=202&qos=4&cips=183.12.198.45&geo=CN-19-248-1&tmn=1414843127&pnl=896,889,242&rson=1&ext=m3u8&sign=live_tv&nwtime=1414843126"},
		{"香港影院","http://125.88.64.175/m3u8/lb_hkmovie_1000/desc.m3u8?stream_id=lb_hkmovie_1000&ltm=1414861227&lkey=74fd5ac0ec9df0a4dd0b52b200598380&platid=10&splatid=1004&tag=live&video_type=m3u8&useloc=0&mslice=3&path=121.14.111.92,121.14.111.6&ver=live_3&buss=202&qos=4&cips=113.116.30.149&geo=CN-19-248-1&tmn=1414843227&pnl=606,895,286&rson=1&ext=m3u8&sign=live_tv&nwtime=1414843227"},
		{"动作影院","http://183.60.199.43/m3u8/lb_dzdy_1000/desc.m3u8?stream_id=lb_dzdy_1000&ltm=1414861373&lkey=79f50572936d7b2db05044b8c8dce5c9&platid=10&splatid=1004&tag=live&video_type=m3u8&useloc=0&mslice=3&path=183.60.199.39,183.60.210.87,183.60.210.116&ver=live_3&buss=202&qos=4&cips=113.97.183.248&geo=CN-19-248-1&tmn=1414843373&pnl=859,889,242&rson=1&ext=m3u8&sign=live_tv&nwtime=1414843373"},
		{"电视剧1HD","http://14.22.30.69/m3u8/lb_tv_1000/desc.m3u8?stream_id=lb_tv_1000&ltm=1414861434&lkey=7ce743370b1cb3c35627bac4d0843f90&platid=10&splatid=1004&tag=live&video_type=m3u8&useloc=0&mslice=3&path=14.22.30.93,183.60.210.89,183.60.210.116&ver=live_3&buss=202&qos=4&cips=183.38.70.37&geo=CN-19-248-1&tmn=1414843434&pnl=896,889,242&rson=1&ext=m3u8&sign=live_tv&nwtime=1414843434"},
	};
	
	public static String[][] vipChannelMap = {
		{"云图影院HD","http://g3.letv.cn/vod/v2/OTYvMjIvOTcvbGV0di11dHMvMTQvdmVyXzAwXzE2LTMwOTkxMDU3LWF2Yy0yMjQwMzItYWFjLTMyMDAxLTYxNTM0MzktMjA0MjgxODM3LWQ5MDc0NzMwMjk0YjFlZDA3MTIyMWE4N2FmYmVlMzA2LTE0MTQ3NjY4NDg5NzYubXA0?b=265&mmsid=1918146&tm=1414846422&key=c9963bce3406a21e3278d60763633e2e&platid=3&splatid=302&playid=0&tss=ios&vtype=21&cvid=351656049097&format=0&sign=mb&dname=mobile&expect=1&tag=mobile		"},
		{"云图记录HD","http://pl.youku.com/playlist/m3u8?ctype=12&ep=eiaVHkGJUcwE5yPdiT8bYXnrdnEKXJZ3vGaH%2F4gfA8ZALaHQnTrVzw%3D%3D&ev=1&keyframe=1&oip=2032688683&sid=8414847242021126e9265&token=4448&ts=1414847242&vid=XMzI2NjM3MTUy&type=mp4"},
		{"云图综艺HD","http://hot.vrs.sohu.com/ipad2029226_4625441600626_5230547.m3u8?plat=17"},
		{"云图热剧HD","http://hot.vrs.sohu.com/ipad480447_4625896241714_701579.m3u8?plat=17"},
		{"猛男频道","http://nowtv.tvesou.com:4110/b/live.php?src=dytv&id=4332&pwd=yuntutv&nwtime=1414847784&sign=5df9647fa1600921a78fc47672f4b7ee"},
		{"香港影院","http://125.88.64.175/m3u8/lb_hkmovie_1000/desc.m3u8?stream_id=lb_hkmovie_1000&ltm=1414861227&lkey=74fd5ac0ec9df0a4dd0b52b200598380&platid=10&splatid=1004&tag=live&video_type=m3u8&useloc=0&mslice=3&path=121.14.111.92,121.14.111.6&ver=live_3&buss=202&qos=4&cips=113.116.30.149&geo=CN-19-248-1&tmn=1414843227&pnl=606,895,286&rson=1&ext=m3u8&sign=live_tv&nwtime=1414843227"},
	};
	
	public static String[][] satelliteChannelMap = {
//		{"时移测试",  "http://124.161.62.196:8007/TV/0/00000000000000050000000000000209/10.m3u8?bitrate=auto"},
		{"湖南卫视","http://url.52itv.cn/vlive/pptv/hnws.m3u8"},
		{"浙江卫视-HD","http://v14.tv.cctv5.cctv.com/approve/live?channel=ZheJiangHD&type=ipsd"},
		{"江苏卫视","http://121.201.106.2:443/m3u8/jiangsuHD_1800/desc.m3u8?stream_id=jiangsuHD_1800&ltm=1414866482&lkey=016427459492f45bede2893d9aba8009&platid=10&splatid=1004&tag=live&video_type=m3u8&useloc=0&mslice=3&path=121.201.106.11,121.14.111.56,121.14.111.13&buss=0&qos=1&cips=113.92.248.102&geo=CN-19-248-1&tmn=1414848482&pnl=1012,895,286&rson=1&ext=m3u8&sign=live_tv&nwtime=1414848482"},
		{"深圳卫视","http://14.152.86.61/m3u8/shenzhenHD_1800/desc.m3u8?stream_id=shenzhenHD_1800&ltm=1414867506&lkey=a69a1b3203cec6cbac34d302a865f6b0&platid=10&splatid=1004&tag=live&video_type=m3u8&useloc=0&mslice=3&path=14.152.86.42,121.14.111.46,121.14.111.12&buss=0&qos=1&cips=183.12.198.45&geo=CN-19-248-1&tmn=1414849506&pnl=1021,895,286&rson=1&ext=m3u8&sign=live_tv&nwtime=1414849505"},
		{"东方卫视","http://14.22.30.67/m3u8/lb_tv_720p/desc.m3u8?stream_id=lb_tv_720p&ltm=1414866636&lkey=03c67563d67adb37f4e3b7351a3f02a6&platid=10&splatid=1004&tag=live&video_type=m3u8&useloc=0&mslice=3&path=14.22.30.79,121.14.111.79,121.14.111.6&ver=live_3&buss=202&qos=4&cips=183.12.198.45&geo=CN-19-248-1&tmn=1414848636&pnl=896,895,286&rson=1&ext=m3u8&sign=live_tv&nwtime=1414848633"},
		{"安徽卫视","http://nowtv.tvesou.com:5023/m3u8/cckw1/ahws.m3u8?pwd=yuntutv&nwtime=1414848789&sign=e1553baa5402ea1a55b0783c785857b4"},
		{"浙江卫视","http://vapptime.cntv.wscdns.com/cache/64_/seg0/index.m3u8"},
		{"浙江卫视回放","/mnt/sdcard/zhejiang.m3u8"},
	};

	public static String[][] cctvChannelMap = {
//		{"iHome-CCTV-1-回放","http://scgd-m3u8.3atv.cn:10009/m3u8/m3u8?begintime=1413421680&duration=1080&mode=11&id=134100&version=1&rate=400k"},
		{"CCTV-1","http://vapptime.cntv.wscdns.com/cache/14_/seg0/index.m3u8"},
		{"iHome-CCTV-1","http://scgd-m3u8.3atv.cn:10009/m3u8/m3u8?id=134100&version=1&rate=400k"},
		{"CCTV-1-HD","http://v14.tv.cctv5.cctv.com/approve/live?channel=CCTV1HD&type=ipsd"},
		{"CCTV-2","http://vapptime.cntv.wscdns.com/cache/204_/seg0/index.m3u8"},
		{"CCTV-3","http://vapptime.cntv.wscdns.com/cache/209_/seg0/index.m3u8"},
		{"CCTV-4","http://vapptime.cntv.wscdns.com/cache/19_/seg0/index.m3u8"},
		{"CCTV-5","http://vapptime.cntv.wscdns.com/cache/214_/seg0/index.m3u8"},
		{"CCTV-5-HD","http://v14.tv.cctv5.cctv.com/approve/live?channel=CCTV5HD&type=ipsd"},
		{"CCTV-6","http://vapptime.cntv.wscdns.com/cache/219_/seg0/index.m3u8"},
		{"CCTV-7","http://vapptime.cntv.wscdns.com/cache/224_/seg0/index.m3u8"},
		{"CCTV-8","http://vapptime.cntv.wscdns.com/cache/229_/seg0/index.m3u8"},
		{"CCTV-9","http://vapptime.cntv.wscdns.com/cache/294_/seg0/index.m3u8"},
		{"CCTV-10","http://vapptime.cntv.wscdns.com/cache/234_/seg0/index.m3u8"},
		{"CCTV-11","http://vapptime.cntv.wscdns.com/cache/239_/seg0/index.m3u8"},
		{"CCTV-12","http://nowtv.tvesou.com:5023/m3u8/tvwshd/cctv12.m3u8?pwd=yuntutv&nwtime=1411568732&sign=15b03f4fdb88fde45e75d001b9abff94"},
		{"CCTV-新闻","http://vapptime.cntv.wscdns.com/cache/24_/seg0/index.m3u8"},
		{"CCTV-新闻-HD","http://14.152.86.48/m3u8/cctvnew/desc.m3u8?stream_id=cctvnew&ltm=1414867623&lkey=5069ef010e34dc8b711fa89598314162&platid=10&splatid=1004&tag=live&video_type=m3u8&useloc=0&mslice=3&path=14.152.86.52,183.60.210.73,183.60.210.113&buss=0&qos=1&cips=113.91.164.128&geo=CN-19-248-1&tmn=1414849623&pnl=1021,889,242&rson=1&ext=m3u8&sign=live_tv&nwtime=1414849623"},
		{"CCTV-14","http://nowtv.tvesou.com:5023/m3u8/tvwshd/cctv14.m3u8?pwd=yuntutv&nwtime=1411568981&sign=ad6db079a050d17a26b69748ec314a0e"},
		{"CCTV-音乐","http://vapptime.cntv.wscdns.com/cache/254_/seg0/index.m3u8"},
	};
	
	public static String[][] sportsChannelMap = {
		{"CCTV5-HD","http://v14.tv.cctv5.cctv.com/approve/live?channel=CCTV5HD&type=ipsd"},
		{"CCTV5+","http://v14.tv.cctv5.cctv.com/approve/live?channel=CCTV5AHD&type=ipsd"},
		{"体育HD","http://14.22.30.93/m3u8/lb_hdz_720p/desc.m3u8?stream_id=lb_hdz_720p&ltm=1414860361&lkey=0b9a9faf7ac96cb63145c16d81a8f185&platid=10&splatid=1004&tag=live&video_type=m3u8&useloc=0&mslice=3&path=14.22.30.90,183.60.210.88,183.60.210.116&ver=live_3&buss=202&qos=4&cips=183.12.198.45&geo=CN-19-248-1&tmn=1414842361&pnl=896,889,242&rson=1&ext=m3u8&sign=live_tv&nwtime=1414842361"},
		{"游戏竞技","http://zb.v.qq.com:1863/?progid=1980972519&ostype=ios"},
		{"游戏风云","http://live-cdn1.kksmg.com/channels/tvie/yxfy/m3u8:sd"},
	};
	
	public static String[][] moviesChannelMap = {
		{"云图电影HD","http://g3.letv.cn/vod/v2/OTMvMjEvMTAvbGV0di11dHMvMTQvdmVyXzAwXzE2LTMwOTkxMDcyLWF2Yy00NzY1OTUtYWFjLTMyMDAxLTYxNTM0MzktMzk4NTU0OTQ4LWI4YjE3MTlmN2QyODg1Mjc3YjQ1MTgyZDc5ODk3ZjFjLTE0MTQ3NjYxMjUyNDQubXA0?b=518&mmsid=1918146&tm=1414842796&key=a9f106a662708033205fb09421840d77&platid=3&splatid=302&playid=0&tss=ios&vtype=13&cvid=351656049097&format=0&sign=mb&dname=mobile&expect=1&tag=mobile"},
		{"云图剧场HD","http://hot.vrs.sohu.com/ipad480445_4625896241605_701577.m3u8?plat=17"},
		{"电影HD","http://14.22.30.65/m3u8/lb_movie_1000/desc.m3u8?stream_id=lb_movie_1000&ltm=1414861127&lkey=1bcce0f959a65769a5b1250cc517ce8d&platid=10&splatid=1004&tag=live&video_type=m3u8&useloc=0&mslice=3&path=14.22.30.70,183.60.210.147,183.60.210.116&ver=live_3&buss=202&qos=4&cips=183.12.198.45&geo=CN-19-248-1&tmn=1414843127&pnl=896,889,242&rson=1&ext=m3u8&sign=live_tv&nwtime=1414843126"},
		{"香港影院","http://125.88.64.175/m3u8/lb_hkmovie_1000/desc.m3u8?stream_id=lb_hkmovie_1000&ltm=1414861227&lkey=74fd5ac0ec9df0a4dd0b52b200598380&platid=10&splatid=1004&tag=live&video_type=m3u8&useloc=0&mslice=3&path=121.14.111.92,121.14.111.6&ver=live_3&buss=202&qos=4&cips=113.116.30.149&geo=CN-19-248-1&tmn=1414843227&pnl=606,895,286&rson=1&ext=m3u8&sign=live_tv&nwtime=1414843227"},
		{"动作影院","http://183.60.199.43/m3u8/lb_dzdy_1000/desc.m3u8?stream_id=lb_dzdy_1000&ltm=1414861373&lkey=79f50572936d7b2db05044b8c8dce5c9&platid=10&splatid=1004&tag=live&video_type=m3u8&useloc=0&mslice=3&path=183.60.199.39,183.60.210.87,183.60.210.116&ver=live_3&buss=202&qos=4&cips=113.97.183.248&geo=CN-19-248-1&tmn=1414843373&pnl=859,889,242&rson=1&ext=m3u8&sign=live_tv&nwtime=1414843373"},
		{"电视剧1HD","http://14.22.30.69/m3u8/lb_tv_1000/desc.m3u8?stream_id=lb_tv_1000&ltm=1414861434&lkey=7ce743370b1cb3c35627bac4d0843f90&platid=10&splatid=1004&tag=live&video_type=m3u8&useloc=0&mslice=3&path=14.22.30.93,183.60.210.89,183.60.210.116&ver=live_3&buss=202&qos=4&cips=183.38.70.37&geo=CN-19-248-1&tmn=1414843434&pnl=896,889,242&rson=1&ext=m3u8&sign=live_tv&nwtime=1414843434"},
		{"动漫HD","http://183.61.63.79/m3u8/lb_comic_1000/desc.m3u8?stream_id=lb_comic_1000&ltm=1414861871&lkey=4e663456eb945c9d44a295004dae750b&platid=10&splatid=1004&tag=live&video_type=m3u8&useloc=0&mslice=3&path=183.61.63.75,121.14.111.68,121.14.111.6&ver=live_3&buss=202&qos=4&cips=14.153.1.192&geo=CN-19-248-1&tmn=1414843871&pnl=605,895,286&rson=1&ext=m3u8&sign=live_tv&nwtime=1414843871"},
	};
	
	public static String[][] infoChannelMap = {
		{"凤凰资讯","http://live.3gv.ifeng.com/zixun.m3u8"},
		{"彭博财经","http://hd4.lsops.net/live/bloomber_en_hls.smil/playlist.m3u8"},
		{"CCTV-2","http://nowtv.tvesou.com:5023/m3u8/tvfwhd/cctv2.m3u8?pwd=yuntutv&nwtime=1414845635&sign=744481d4080dcea0e798680d7fd1fd82"},
		{"CCTV-4","http://vapptime.cntv.wscdns.com/cache/19_/seg0/index.m3u8"},
		{"CCTV-新闻","http://vapptime.cntv.wscdns.com/cache/24_/seg0/index.m3u8"},
		{"CCTV-NEWS","http://nowtv.tvesou.com:5023/m3u8/tvfwhd/cctvnews.m3u8?pwd=yuntutv&nwtime=1414845926&sign=7afe1201463eeb234fea2a97403aa5c6"},
	};
	
	public static String[][] educationChannelMap = {
		{"CCTV-10","http://nowtv.tvesou.com:5023/m3u8/tvfwhd/cctv10.m3u8?pwd=yuntutv&nwtime=1414844703&sign=9c97ee9f39183c5393f21e7f0e2b9ca4"},
		{"中国教育-1","http://streaming.centv.cn/live/361fec8a6b0947a79c2c79d75fa38dfc.m3u8?fmt=h264_500k_ts&m3u8"},
		{"中国教育-2","http://streaming.centv.cn/live/7bcc72bf01b5485d8289778030c07ec6.m3u8?fmt=h264_500k_ts&m3u8"},
		{"中国教育-3","http://streaming.centv.cn/live/9e7752a97af34d2c8226118e272a28ab.m3u8?fmt=h264_500k_ts&m3u8"},
		{"职业指南","http://live9.hbtv.com.cn/channels/zbk/zyzl/flv:sd/live"},
	};
	
	public static String[][] lifeChannelMap = {
		{"CCTV-10","http://nowtv.tvesou.com:5023/m3u8/tvfwhd/cctv10.m3u8?pwd=yuntutv&nwtime=1414844703&sign=9c97ee9f39183c5393f21e7f0e2b9ca4"},
		{"云图剧场HD","http://hot.vrs.sohu.com/ipad480445_4625896241605_701577.m3u8?plat=17"},
		{"电影HD","http://14.22.30.65/m3u8/lb_movie_1000/desc.m3u8?stream_id=lb_movie_1000&ltm=1414861127&lkey=1bcce0f959a65769a5b1250cc517ce8d&platid=10&splatid=1004&tag=live&video_type=m3u8&useloc=0&mslice=3&path=14.22.30.70,183.60.210.147,183.60.210.116&ver=live_3&buss=202&qos=4&cips=183.12.198.45&geo=CN-19-248-1&tmn=1414843127&pnl=896,889,242&rson=1&ext=m3u8&sign=live_tv&nwtime=1414843126"},
		{"香港影院","http://125.88.64.175/m3u8/lb_hkmovie_1000/desc.m3u8?stream_id=lb_hkmovie_1000&ltm=1414861227&lkey=74fd5ac0ec9df0a4dd0b52b200598380&platid=10&splatid=1004&tag=live&video_type=m3u8&useloc=0&mslice=3&path=121.14.111.92,121.14.111.6&ver=live_3&buss=202&qos=4&cips=113.116.30.149&geo=CN-19-248-1&tmn=1414843227&pnl=606,895,286&rson=1&ext=m3u8&sign=live_tv&nwtime=1414843227"},
		{"动作影院","http://183.60.199.43/m3u8/lb_dzdy_1000/desc.m3u8?stream_id=lb_dzdy_1000&ltm=1414861373&lkey=79f50572936d7b2db05044b8c8dce5c9&platid=10&splatid=1004&tag=live&video_type=m3u8&useloc=0&mslice=3&path=183.60.199.39,183.60.210.87,183.60.210.116&ver=live_3&buss=202&qos=4&cips=113.97.183.248&geo=CN-19-248-1&tmn=1414843373&pnl=859,889,242&rson=1&ext=m3u8&sign=live_tv&nwtime=1414843373"},
		{"电视剧1HD","http://14.22.30.69/m3u8/lb_tv_1000/desc.m3u8?stream_id=lb_tv_1000&ltm=1414861434&lkey=7ce743370b1cb3c35627bac4d0843f90&platid=10&splatid=1004&tag=live&video_type=m3u8&useloc=0&mslice=3&path=14.22.30.93,183.60.210.89,183.60.210.116&ver=live_3&buss=202&qos=4&cips=183.38.70.37&geo=CN-19-248-1&tmn=1414843434&pnl=896,889,242&rson=1&ext=m3u8&sign=live_tv&nwtime=1414843434"},
		{"动漫HD","http://183.61.63.79/m3u8/lb_comic_1000/desc.m3u8?stream_id=lb_comic_1000&ltm=1414861871&lkey=4e663456eb945c9d44a295004dae750b&platid=10&splatid=1004&tag=live&video_type=m3u8&useloc=0&mslice=3&path=183.61.63.75,121.14.111.68,121.14.111.6&ver=live_3&buss=202&qos=4&cips=14.153.1.192&geo=CN-19-248-1&tmn=1414843871&pnl=605,895,286&rson=1&ext=m3u8&sign=live_tv&nwtime=1414843871"},
	};
	
	//游戏频道视频源为YY直播，解码为YY提供
}
