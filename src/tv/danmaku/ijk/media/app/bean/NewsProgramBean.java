package tv.danmaku.ijk.media.app.bean;

import java.util.ArrayList;

public class NewsProgramBean {
	private int count;
	private int errorCode;
	private ArrayList<ProgramData> mProgramDatas;
	private String msg;
	
	public void setCount(int count) {
		this.count = count;
	}

	public int getCount() {
		return count;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setmProgramDatas(ArrayList<ProgramData> mProgramDatas) {
		this.mProgramDatas = mProgramDatas;
	}

	public ArrayList<ProgramData> getmProgramDatas() {
		return mProgramDatas;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}
}


