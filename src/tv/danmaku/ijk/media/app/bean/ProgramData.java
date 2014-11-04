package tv.danmaku.ijk.media.app.bean;

import java.util.ArrayList;

public class ProgramData{
	String id;
	String index;
	String title;
	String bannerUrl;
	String endTime;
	private ArrayList<ProgramItem> programItems;
	private String imgUrl;
	
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	public String getImgUrl() {
		return imgUrl;
	}

	public void setProgramItems(ArrayList<ProgramItem> programItems) {
		this.programItems = programItems;
	}

	public ArrayList<ProgramItem> getProgramItems() {
		return programItems;
	}
	
}
