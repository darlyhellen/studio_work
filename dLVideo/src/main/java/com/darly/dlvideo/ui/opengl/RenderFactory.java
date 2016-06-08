/**下午2:52:37
 * @author zhangyh2
 * RenderFactory.java
 * TODO
 */
package com.darly.dlvideo.ui.opengl;

import com.darly.dlvideo.base.APP;


/**
 * @author zhangyh2 RenderFactory 下午2:52:37 TODO 生产工厂模式，来进行静态生产Renderer实现类
 */
public class RenderFactory {

	public static Render producVideoRender() {
		return new VideoRender();
	}

	public static Render produVertexRender() {
		return new VertexRender();
	}

	public static Render produLineRender() {
		return new LineRender();
	}

	public static Render produGraphicsRender() {
		return new GraphicsRender();
	}

	public static Render produThreeDRender() {
		return new ThreeDRender();
	}
	
	public static Render produImageRender() {
		return new ImageRender(APP.getInstance());
	}
}
