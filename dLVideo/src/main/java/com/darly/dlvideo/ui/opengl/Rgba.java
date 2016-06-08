/**上午11:55:49
 * @author zhangyh2
 * RBGA.java
 * TODO
 */
package com.darly.dlvideo.ui.opengl;

/**
 * @author zhangyh2 RBGA 上午11:55:49 TODO 一组颜色通过RGB设置色彩
 */
public class Rgba {

	private float red;
	private float green;
	private float blue;
	private float alpha;

	public Rgba(float red, float green, float blue, float alpha) {
		super();
		this.red = red;
		this.green = green;
		this.blue = blue;
		this.alpha = alpha;
	}

	public float getRed() {
		return red;
	}

	public void setRed(float red) {
		this.red = red;
	}

	public float getGreen() {
		return green;
	}

	public void setGreen(float green) {
		this.green = green;
	}

	public float getBlue() {
		return blue;
	}

	public void setBlue(float blue) {
		this.blue = blue;
	}

	public float getAlpha() {
		return alpha;
	}

	public void setAlpha(float alpha) {
		this.alpha = alpha;
	}

}
