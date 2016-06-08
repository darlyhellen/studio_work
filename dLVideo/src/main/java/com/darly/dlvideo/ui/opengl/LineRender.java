/**下午2:52:59
 * @author zhangyh2
 * VideoRender.java
 * TODO
 */
package com.darly.dlvideo.ui.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;

/**
 * @author zhangyh2 VideoRender 下午2:52:59 TODO 绘制直线
 */
public class LineRender extends Render {

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
		// 设定背景颜色 此处为黑
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		// 重设视图模型变换 ， 用于观测创建的物体
		gl.glLoadIdentity();
		gl.glTranslatef(0.0f, 0.0f, -5.0f);
		// 开始绘制
		Line line = new Line();
		line.draw(gl);
	}

	class Line {
		private FloatBuffer vertexBuffer;
		// 6 lines and 12 vertices
		private float vertices[] = { 1.0f, 0.0f, 0.0f, -1.0f, 0.0f, 0.0f, 1.0f,
				0.4f, 0.0f, -1.0f, 0.4f, 0.0f, 1.0f, -0.4f, 0.0f, -1.0f, -0.4f,
				0.0f, 1.0f, 0.8f, 0.0f, -1.0f, 0.8f, 0.0f, 1.0f, -0.8f, 0.0f,
				-1.0f, -0.8f, 0.0f, 1.0f, 1.2f, 0.0f, -1.0f, 1.2f, 0.0f };
		private byte line1[] = { 0, 1 };
		private byte line2[] = { 2, 3 };
		private byte line3[] = { 4, 5, 9, 8 };
		private byte line4[] = { 8, 9 };
		private byte line5[] = { 10, 11 };

		private void init() {
			ByteBuffer vertexByteBuffer = ByteBuffer
					.allocateDirect(vertices.length * 4);
			vertexByteBuffer.order(ByteOrder.nativeOrder());
			vertexBuffer = vertexByteBuffer.asFloatBuffer();
			vertexBuffer.put(vertices);
			vertexBuffer.position(0);
		}

		public Line() {
			init();
		}

		public void draw(GL10 gl) {
			// 开始数组功能 GL_VERTEX_ARRAY
			gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
			// 设定颜色值 此处为红 (red, green, blue, alpha)~[0.0f-1.0f]
			gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
			// 指向数组数据
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
			// 实际绘制 6 条线
			drawlines(gl);
			// 关闭数组功能 GL_VERTEX_ARRAY
			gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		}

		private void drawlines(GL10 gl) {
			// 设置直线的宽度
			gl.glLineWidth(1.0f);
			gl.glDrawElements(GL10.GL_LINE_LOOP, 2, GL10.GL_UNSIGNED_BYTE,
					ByteBuffer.wrap(line1));
			gl.glLineWidth(5.0f);
			gl.glDrawElements(GL10.GL_LINE_STRIP, 2, GL10.GL_UNSIGNED_BYTE,
					ByteBuffer.wrap(line2));
			gl.glDrawElements(GL10.GL_LINE_LOOP, 4, GL10.GL_UNSIGNED_BYTE,
					ByteBuffer.wrap(line3));
			gl.glDrawElements(GL10.GL_LINE_LOOP, 2, GL10.GL_UNSIGNED_BYTE,
					ByteBuffer.wrap(line4));
			gl.glDrawElements(GL10.GL_LINE_LOOP, 2, GL10.GL_UNSIGNED_BYTE,
					ByteBuffer.wrap(line5));
		}
	}

}
