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
 * @author zhangyh2 VideoRender 下午2:52:59 TODO 绘制图形
 */
public class GraphicsRender extends Render {

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
		Shape line = new Shape();
		line.draw(gl);
	}



    class Shape{  
        private FloatBuffer vertexBuffer;  
        //点的坐标（x, y, z)；   
        private float vertices1[] = {  
                -0.5f, 0.3f , 0.0f,  
                0.0f, 0.0f, 0.0f,  
                -0.5f, 0.8f, 0.0f,  
                0.5f, 0.3f, 0.0f,  
                0.5f, 0.8f, 0.0f,  
                0.0f, 1.0f, 0.0f,  
        };  
        //准备顶点数据   
        private void init(){  
            ByteBuffer vertexByteBuffer = ByteBuffer.allocateDirect(vertices1.length * 4);  
            vertexByteBuffer.order(ByteOrder.nativeOrder());  
            vertexBuffer = vertexByteBuffer.asFloatBuffer();  
            vertexBuffer.put(vertices1);  
            vertexBuffer.position(0);  
        }  
        public Shape(){  
            init();  
        }  
        public void draw(GL10 gl){  
            //开始数组功能 GL_VERTEX_ARRAY   
            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);  
            gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);  
            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);  
            // 绘制三角形 ； GL10.GL_TRIANGLES 还有其他模式  修改顶点坐标观察结果   
            gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vertices1.length / 3);  
        //  gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices1.length / 3);   
        //  gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, vertices1.length / 3);   
            gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);  
        }  
    }  

}
