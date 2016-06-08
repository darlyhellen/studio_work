/**下午2:58:42
 * @author zhangyh2
 * VertexRender.java
 * TODO
 */
package com.darly.dlvideo.ui.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;

/**
 * @author zhangyh2 VertexRender 下午2:58:42 TODO 顶点数组与绘制
 */
public class VertexRender extends Render {

	private FloatBuffer vertexBuffer;
	// 点的坐标（x, y, z)；
	// private float vertices[] = { 0.5f, 0.5f, 0.0f, -0.5f, 0.5f, 0.0f,
	// 0.5f,
	// -0.5f, 0.0f, -0.5f, -0.5f, 0.0f, 5f, 5f, 0f, };

	List<List<Integer>> planes;

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
		// 关闭抗抖动
		gl.glDisable(GL10.GL_DITHER);
		// 设置系统对透视进行修正
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
		gl.glClearColor(0, 0, 0, 0);
		// 设置阴影平滑模式
		gl.glShadeModel(GL10.GL_SMOOTH);
		// 启用深度测试
		gl.glEnable(GL10.GL_DEPTH_TEST);
		// 设置深度测试的类型
		gl.glDepthFunc(GL10.GL_LEQUAL);

		getPlane();

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
		// 设置投影变换 GL_PROJECTION 投影, GL_MODELVIEW 模型视图, GL_TEXTURE 纹理.
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
		gl.glClearColor(bRgba.getRed(), bRgba.getGreen(), bRgba.getBlue(),
				bRgba.getAlpha());
		// 重设视图模型变换 ， 用于观测创建的物体
		gl.glLoadIdentity();
		gl.glTranslatef(0.0f, 0.0f, -5.0f);
		// 沿着Y轴旋转
		gl.glRotatef(rotate, 0f, 0.5f, 0f);
		// 沿着X轴旋转
		gl.glRotatef(rotate, 1f, 0.2f, 0.5f);
		// 开始绘制
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		// 设定颜色值 此处为红 (red, green, blue, alpha)~[0.0f-1.0f]
		gl.glColor4f(pRgba.getRed(), pRgba.getGreen(), pRgba.getBlue(),
				pRgba.getAlpha());
		// 指向数组数据
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		// 设置点的大小 为 5 像素
		// gl.glposizex(SIZES);
		// 绘制点 GL_pos ； vertices.length/3 点的个数
		// gl.glDrawArrays(GL10.GL_pos, 0, vertices.length / 3);
		drawLine(gl);
		// drawShape(gl);
		// 关闭数组功能 GL_VERTEX_ARRAY
		gl.glFinish();
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		rotate += 1;
		if (SLEEPTIME > 16) {
			try {
				Thread.sleep(SLEEPTIME);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 下午3:59:55
	 * 
	 * @author zhangyh2 TODO
	 */
	protected void drawShape(GL10 gl) {
		// TODO Auto-generated method stub
		for (List<Integer> list : planes) {
			byte[] trang = { list.get(0).byteValue(), list.get(1).byteValue(),
					list.get(2).byteValue(), list.get(3).byteValue() };
			gl.glDrawElements(GL10.GL_TRIANGLE_STRIP, ByteBuffer.wrap(trang)
					.remaining(), GL10.GL_UNSIGNED_BYTE, ByteBuffer.wrap(trang));
		}

		// gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length / 3);
	}

	/**
	 * 下午5:11:45
	 * 
	 * @author zhangyh2 TODO 根据点阵。获取各个面的关系
	 */
	private void getPlane() {
		planes = new ArrayList<List<Integer>>();
		List<Integer> point0x = new ArrayList<Integer>();
		List<Integer> point0y = new ArrayList<Integer>();
		List<Integer> point0z = new ArrayList<Integer>();
		List<Integer> point6x = new ArrayList<Integer>();
		List<Integer> point6y = new ArrayList<Integer>();
		List<Integer> point6z = new ArrayList<Integer>();
		List<Float> change = new ArrayList<Float>();
		for (int j = 0; j < pos.size(); j++) {
			change.add(pos.get(j).getRendx());
			change.add(pos.get(j).getRendy());
			change.add(pos.get(j).getRendz());
			if (pos.get(0).getRendx() == pos.get(j).getRendx()) {
				// 和第一个点在同一X平面 3点
				point0x.add(j);
			}
			if (pos.get(0).getRendy() == pos.get(j).getRendy()) {
				// 和第一个点在同一Y平面 3点
				point0y.add(j);
			}
			if (pos.get(0).getRendz() == pos.get(j).getRendz()) {
				// 和第一个点在同一Z平面 3点
				point0z.add(j);
			}
			if (pos.get(6).getRendx() == pos.get(j).getRendx()) {
				// 和第六个点在同一X平面 3点
				point6x.add(j);
			}
			if (pos.get(6).getRendy() == pos.get(j).getRendy()) {
				// 和第六个点在同一Y平面 3点
				point6y.add(j);
			}
			if (pos.get(6).getRendz() == pos.get(j).getRendz()) {
				// 和第六个点在同一Z平面 3点
				point6z.add(j);
			}
			// 将所有平面的点进行归类

		}
		planes.add(point0x);
		planes.add(point0y);
		planes.add(point0z);
		planes.add(point6x);
		planes.add(point6y);
		planes.add(point6z);

		float[] vertices = new float[change.size()];
		for (int i = 0; i < change.size(); i++) {
			vertices[i] = change.get(i);
		}
		// 数据转换
		ByteBuffer vertexByteBuffer = ByteBuffer
				.allocateDirect(vertices.length * 4);
		vertexByteBuffer.order(ByteOrder.nativeOrder());
		vertexBuffer = vertexByteBuffer.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);
	}

	/**
	 * 上午10:50:37
	 * 
	 * @author zhangyh2 TODO
	 * @param gl
	 */
	private void drawLine(GL10 gl) {
		// TODO Auto-generated method stub
		for (byte i = 0; i < pos.size(); i++) {
			// 8个点 0——7
			for (byte j = (byte) (i + 1); j < pos.size(); j++) {
				byte[] t = { i, j };
				// 划线
				gl.glDrawElements(GL10.GL_LINE_LOOP, 2, GL10.GL_UNSIGNED_BYTE,
						ByteBuffer.wrap(t));
			}
		}
	}
}
