/**下午1:36:12
 * @author zhangyh2
 * ConsVideo.java
 * TODO
 */
package com.darly.dlvideo.base;

import android.os.Environment;

/**
 * @author zhangyh2 ConsVideo 下午1:36:12 TODO 一些常量，跟一些固定值
 */
public enum ConsVideo {
	// 方案一
	/**
	 * 下午3:30:24 TODO 是否首次安装程序，判断是否进入引导页面。
	 */
	ISFIRSTCOME("checkisupdate", 0), WIDTH("screen width", 0), HEIGHT(
			"screen height", 0), PLAYPOSITION("playPosition", 0);

	public static final int DB_SELECT = 0x0001;

	public static final int DB_INSERT = 0x0002;

	public static final int DB_UPDATA = 0x0003;

	public static final int DB_DELETE = 0x0004;

	public static final int DB_REQUST = 0x0005;

	public static final int CENTER_NAME = 0x0011;

	public static final int CENTER_TEL = 0x0012;

	public static final int CENTER_SEX = 0x0013;

	public static final int CENTER_CARD = 0x0014;

	public static final int CENTER_CHANGE = 0x0014;

	public static final int ADDRESS = 0x0024;

	public static final int ADDRESS_NEW = 0x0021;

	public static final int ADDRESS_CHA = 0x0022;

	public static final int REQUESTCODE_CAM = 0x1001;

	public static final int REQUESTCODE_CAP = 0x1002;

	public static final int REQUESTCODE_CUT = 0x1003;

	public static final String ROOT = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + "/dvideos/";

	public static final String MAINRADIO = ROOT + "mainimage/";

	public static final String IMAGE = ROOT + "image/";

	public static final String LOG = ROOT + "log/";

	public static final String HEAD = ROOT + "head.png";

	public static String capUri;

	private String dec;

	private int len;

	/**
	 * 下午1:36:38
	 * 
	 * @author zhangyh2
	 */
	private ConsVideo(String dec, int len) {
		// TODO Auto-generated constructor stub
		this.dec = dec;
		this.len = len;
	}

	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}

	public String getDec() {
		return dec;
	}

	public void setDec(String dec) {
		this.dec = dec;
	}

}
