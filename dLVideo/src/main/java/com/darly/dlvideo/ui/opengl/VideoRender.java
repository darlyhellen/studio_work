/**下午2:52:59
 * @author zhangyh2
 * VideoRender.java
 * TODO
 */
package com.darly.dlvideo.ui.opengl;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;

/**
 * @author zhangyh2 VideoRender 下午2:52:59 TODO 实现背景颜色变更
 */
public class VideoRender extends Render {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.opengl.GLSurfaceView.Renderer#onSurfaceCreated(javax.microedition
	 * .khronos.opengles.GL10, javax.microedition.khronos.egl.EGLConfig)
	 */
	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.opengl.GLSurfaceView.Renderer#onSurfaceChanged(javax.microedition
	 * .khronos.opengles.GL10, int, int)
	 */
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		// 设置坐标系
		gl.glViewport(0, 0, width, height);
		// 设置投影变换
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		// Calculate The Aspect Ratio Of The Window
		GLU.gluPerspective(gl, 45.0f, (float) width / (float) height, 0.1f,
				100.0f);
		gl.glMatrixMode(GL10.GL_MODELVIEW); // 设定模型视图矩阵
		gl.glLoadIdentity();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.opengl.GLSurfaceView.Renderer#onDrawFrame(javax.microedition.
	 * khronos.opengles.GL10)
	 */
	@Override
	public void onDrawFrame(GL10 gl) {
		// TODO Auto-generated method stub
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		// 设定背景颜色 此处为绿
		gl.glClearColor(0.0f, 1.0f, 0.0f, 0.0f);
		// 重设视图模型变换 ， 用于观测创建的物体
		gl.glLoadIdentity();
		gl.glTranslatef(0.0f, 0.0f, -5.0f);
	}

}
