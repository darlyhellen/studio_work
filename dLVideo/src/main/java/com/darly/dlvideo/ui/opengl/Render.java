/**下午1:30:35
 * @author zhangyh2
 * Render.java
 * TODO
 */
package com.darly.dlvideo.ui.opengl;

import java.util.List;

import android.opengl.GLSurfaceView.Renderer;

/**
 * @author zhangyh2 Render 下午1:30:35 TODO 统一的参数和方法
 */
public abstract class Render implements Renderer {

	// 控制旋转的角度
	protected float rotate = 0;
	// 设置点的大小 为1 像素
	protected final int SIZES = 15;
	
	protected final int SLEEPTIME = 300;
	/**
	 * 上午11:58:16 TODO背景色彩
	 */
	protected Rgba bRgba = null;

	/**
	 * 上午11:58:29 TODO 画笔色彩
	 */
	protected Rgba pRgba = null;

	/**
	 * 下午5:38:04 TODO 传递进来图像的点
	 */
	protected List<RendPoint> pos;

	public float getRotate() {
		return rotate;
	}

	public void setRotate(float rotate) {
		this.rotate = rotate;
	}

	public Rgba getbRgba() {
		return bRgba;
	}

	public void setbRgba(Rgba bRgba) {
		this.bRgba = bRgba;
	}

	public Rgba getpRgba() {
		return pRgba;
	}

	public void setpRgba(Rgba pRgba) {
		this.pRgba = pRgba;
	}

	public int getSIZES() {
		return SIZES;
	}

	public List<RendPoint> getPos() {
		return pos;
	}

	public void setPos(List<RendPoint> pos) {
		this.pos = pos;
	}

}
