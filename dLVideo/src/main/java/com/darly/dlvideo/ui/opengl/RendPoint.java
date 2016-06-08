/**下午4:46:24
 * @author zhangyh2
 * RendPoint.java
 * TODO
 */
package com.darly.dlvideo.ui.opengl;

/**
 * @author zhangyh2 RendPoint 下午4:46:24 TODO
 */
public class RendPoint {

	private float rendx;
	private float rendy;
	private float rendz;
	
	/** 下午4:48:33
	 * @author zhangyh2
	 */
	public RendPoint() {
		// TODO Auto-generated constructor stub
	}

	public RendPoint(float rendx, float rendy, float rendz) {
		super();
		this.rendx = rendx;
		this.rendy = rendy;
		this.rendz = rendz;
	}

	public float getRendx() {
		return rendx;
	}

	public void setRendx(float rendx) {
		this.rendx = rendx;
	}

	public float getRendy() {
		return rendy;
	}

	public void setRendy(float rendy) {
		this.rendy = rendy;
	}

	public float getRendz() {
		return rendz;
	}

	public void setRendz(float rendz) {
		this.rendz = rendz;
	}

}
