package tv.danmaku.ijk.media.app.bean;

import java.io.Serializable;

public class ChannelBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String channelId;
	private String channelName;
	private String programName;
	private String periodTime;
	private String url;
	private int imageResourceId;
	
	
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	
	public String getChannelName() {
		return channelName;
	}

	public void setImageResourceId(int imageResourceId) {
		this.imageResourceId = imageResourceId;
	}

	public int getImageResourceId() {
		return imageResourceId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public String getProgramName() {
		return programName;
	}

	public void setPeriodTime(String periodTime) {
		this.periodTime = periodTime;
	}

	public String getPeriodTime() {
		return periodTime;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}
}
