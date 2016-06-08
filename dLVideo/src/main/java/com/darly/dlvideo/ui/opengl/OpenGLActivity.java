/**上午11:35:25
 * @author zhangyh2
 * OpenGLActivity.java
 * TODO
 */
package com.darly.dlvideo.ui.opengl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Intent;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.darly.dlvideo.base.BaseActivity;
import com.darly.dlvideo.event.EventAct;
import com.darly.dlvideo.ui.uzan.WebViewActivity;
import com.umeng.analytics.MobclickAgent;
import com.youzan.sdk.Callback;
import com.youzan.sdk.YouzanSDK;
import com.youzan.sdk.YouzanUser;

/**
 * @author zhangyh2 OpenGLActivity 上午11:35:25 TODO
 */
public class OpenGLActivity extends BaseActivity {

	// 定义立方体的8个顶点
	float[] vertices = new float[] { 1f, 1f, 1f, 1f, -1f, 1f, -1f,
			-1f,
			1f,
			-1f,
			1f,
			1f,
			// 上顶面正方形的四个顶点0.8f, 0.8f, 0.8f, 0.8f, -0.8f, 0.8f,
			-0.8f, -0.8f,
			0.8f,
			-0.8f,
			0.8f,
			0.8f,
			// 上顶面正方形的四个顶点
			0.5f, 0.5f, 0.5f, 0.5f, -0.5f, 0.5f, -0.5f, -0.5f,
			0.5f,
			-0.5f,
			0.5f,
			0.5f,
			// 上顶面正方形的四个顶点
			0.2f, 0.2f, 0.2f, 0.2f, -0.2f, 0.2f, -0.2f, -0.2f, 0.2f,
			-0.2f,
			0.2f,
			0.2f,
			// 下底面正方形的四个顶点
			0.2f, 0.2f, -0.2f, 0.2f, -0.2f, -0.2f, -0.2f, -0.2f, -0.2f, -0.2f,
			0.2f,
			-0.2f,
			// 下底面正方形的四个顶点
			0.5f, 0.5f, -0.5f, 0.5f, -0.5f, -0.5f, -0.5f, -0.5f, -0.5f, -0.5f,
			0.5f, -0.5f,
			// 下底面正方形的四个顶点
			0.8f, 0.8f, -0.8f, 0.8f, -0.8f, -0.8f, -0.8f, -0.8f, -0.8f, -0.8f,
			0.8f, -0.8f,
			// 下底面正方形的四个顶点
			1f, 1f, -1f, 1f, -1f, -1f, -1f, -1f, -1f, -1f, 1f, -1f };
	GLSurfaceView myView;

	private Random ran = new Random();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.darly.dlvideo.base.BaseActivity#initView(android.os.Bundle)
	 */
	@Override
	protected void initView(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		myView = new GLSurfaceView(this);
		Render render = RenderFactory.produVertexRender();

		render.setbRgba(new Rgba(0.2f, 0.2f, 0.2f, 0.5f));
		render.setpRgba(new Rgba(0.6f, 0.2f, 0.4f, 0.2f));
		render.setPos(getPoint());
		// GLSurfaceView默认会创建像素格式为PixelFormat.RGB_565的surface。如果需要透明效果，调用
		// getHolder().setFormat(PixelFormat.TRANSLUCENT)。透明(TRANSLUCENT)的surface的像
		// 素格式都是32位，每个色彩单元都是8位深度，像素格式是设备相关的，这意味着它可能是ARGB、RGBA或其它。
		myView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
		myView.setRenderer(render);
		// 渲染器设定之后，你可以使用setRenderMode(int)指定渲染模式是按需(
		// RENDERMODE_WHEN_DIRTY)还是连续(RENDERMODE_CONTINUOUSLY)。默认是连续渲染。
		myView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
		setContentView(myView);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.darly.dlvideo.base.BaseActivity#loadData()
	 */

	@Override
	protected void loadData() {
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.darly.dlvideo.base.BaseActivity#initListener()
	 */
	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
		myView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// myView.requestRender();
				if (ran.nextInt(2) + 1 < 0) {
					registerYouzanUserForWeb();
				} else {
					requestEvent();
				}
			}
		});
	}

	/**
	 * 下午2:58:36
	 * 
	 * @author zhangyh2 TODO
	 */
	protected void requestEvent() {
		// TODO Auto-generated method stub
		startActivity(new Intent(this, EventAct.class));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.darly.dlvideo.base.BaseActivity#onResume()
	 */
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		myView.onResume();
		MobclickAgent.onPageStart(getClass().getSimpleName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.darly.dlvideo.base.BaseActivity#onPause()
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		myView.onPause();
		MobclickAgent.onPageEnd(getClass().getSimpleName());
	}

	/**
	 * 上午10:16:38
	 * 
	 * @author zhangyh2 TODO
	 */
	protected void registerYouzanUserForWeb() {
		/**
		 * 打开有赞入口网页需先注册有赞用户
		 * 
		 * <pre>
		 * 如果你们App的用户这个时候还没有登录, 请先跳转你们的登录页面, 然后再回来同步用户信息
		 * 
		 * 或者参考{@link LoginWebActivity}
		 * </pre>
		 */
		YouzanUser user = new YouzanUser();
		user.setUserId("YZ_11107");
		user.setGender(1);
		user.setNickName("大明的昵称");
		user.setTelephone("110123456789");
		user.setUserName("大明");

		YouzanSDK.asyncRegisterUser(user, new Callback() {
			@Override
			public void onCallback() {
				Intent intent = new Intent(OpenGLActivity.this,
						WebViewActivity.class);
				// 传入链接, 请修改成你们店铺的链接
				startActivity(intent);
			}
		});
	}

	private List<RendPoint> getPoint() {
		List<RendPoint> points = new ArrayList<RendPoint>();
		for (int i = 0; i < vertices.length / 3; i++) {
			// 8个点6个面,获取每个面的4个点
			RendPoint point = new RendPoint();
			for (int j = 0; j < 3; j++) {
				// 获取每个点的xyz
				switch (j) {
				case 0:
					point.setRendx(vertices[i * 3 + j]);
					break;
				case 1:
					point.setRendy(vertices[i * 3 + j]);
					break;
				case 2:
					point.setRendz(vertices[i * 3 + j]);
					break;

				default:
					break;
				}
				// 将点封装完成
			}
			points.add(point);
		}
		return points;
	}
}
