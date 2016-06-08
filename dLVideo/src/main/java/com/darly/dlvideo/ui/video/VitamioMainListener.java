/**上午10:05:44
 * @author zhangyh2
 * VitamioMainListener.java
 * TODO
 */
package com.darly.dlvideo.ui.video;

import io.vov.vitamio.VitamioListener;

/**
 * @author zhangyh2 VitamioMainListener 上午10:05:44 TODO 主体重定义一个实现了lib接口的子类
 *         然后在APP初始化即可
 */
public class VitamioMainListener implements VitamioListener {

	private static VitamioMainListener instance;

	/**
	 * 上午10:11:45
	 * 
	 * @author zhangyh2
	 */
	private VitamioMainListener() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the instance
	 */
	public static VitamioMainListener getInstance() {
		if (instance == null) {
			instance = new VitamioMainListener();
		}
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see io.vov.vitamio.VitamioListener#startTime()
	 */
	@Override
	public void startTime() {
		// TODO Auto-generated method stub

	}

}
