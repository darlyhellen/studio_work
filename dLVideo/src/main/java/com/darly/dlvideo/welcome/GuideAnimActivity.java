/**
 * 下午3:05:15
 * @author zhangyh2
 * $
 * GuideAnim.java
 * TODO
 */
package com.darly.dlvideo.welcome;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.darly.dlvideo.MainActivity;
import com.darly.dlvideo.R;
import com.darly.dlvideo.adapter.ImageAdapter;
import com.darly.dlvideo.base.APP;
import com.darly.dlvideo.base.BaseActivity;
import com.darly.dlvideo.base.ConsVideo;
import com.darly.dlvideo.common.SharePreferHelp;
import com.darly.dlvideo.welcome.welcome_anim.ColorAnimationView;
import com.darly.dlvideo.welcome.welcome_anim.CubeTransformer;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @author zhangyh2 GuideAnim $ 下午3:05:15 TODO
 */
@ContentView(R.layout.activity_guide)
public class GuideAnimActivity extends BaseActivity implements
		OnPageChangeListener {

	@ViewInject(R.id.viewpager)
	public ViewPager viewPager;

	@ViewInject(R.id.coloranimationview)
	private ColorAnimationView colview;

	private ArrayList<View> list;

	private int currentIndex;

	private View viewShow, viewSec, viewDown;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.darly.oop.base.BaseActivity#initView(android.os.Bundle)
	 */
	@Override
	protected void initView(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// 第一次使用
		initViewPager();// 初始化ViewPager对象
		// 设置非第一次使用
		SharePreferHelp.putValue(ConsVideo.ISFIRSTCOME.getDec()
				+ APP.getInstance().getVersion(), false);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.darly.oop.base.BaseActivity#initListener()
	 */
	@Override
	protected void initListener() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.darly.oop.base.BaseActivity#loadData()
	 */
	@Override
	protected void loadData() {
		// TODO Auto-generated method stub

	}

	private void initViewPager() {
		list = getList(this);
		// 设置适配器
		ImageAdapter adapter = new ImageAdapter(list,this);
		// 绑定适配器
		viewPager.setAdapter(adapter);
		// colview.setmViewPager(viewPager, list.size());

		viewPager.setPageTransformer(true, new CubeTransformer());
		
		// 设置页卡切换监听
		viewPager.setOnPageChangeListener(this);
		// colview.setOnPageChangeListener(this);
	}

	/**
	 * 
	 * 下午3:37:03
	 * 
	 * @author zhangyh2 GuideAnim.java TODO
	 */
	private void intoMain() {
		// TODO Auto-generated method stub
		startActivity(new Intent(this, MainActivity.class));
		finish();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.view.ViewPager.OnPageChangeListener#
	 * onPageScrollStateChanged(int)
	 */
	@Override
	public void onPageScrollStateChanged(int position) {
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrolled
	 * (int, float, int)
	 */
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.view.ViewPager.OnPageChangeListener#onPageSelected
	 * (int)
	 */
	@Override
	public void onPageSelected(int position) {
		LogUtils.i("onPageSelected" + position);
		// TODO Auto-generated method stub
		if (position == list.size() - 1) {
			Button startButton = (Button) findViewById(R.id.startbutton);
			startButton.setOnClickListener(new OnClickListener() {// 匿名内部类，区分小圆圈的点击事件

						@Override
						public void onClick(View v) {
							// 跳到首页
							intoMain();
						}
					});
		}
		currentIndex = position;
	}

	private ArrayList<View> getList(Context context) {
		ArrayList<View> list = new ArrayList<View>();
		viewShow = View.inflate(context, R.layout.activity_guide_v1, null);
		viewSec = View.inflate(context, R.layout.activity_guide_v2, null);
		viewDown = View.inflate(context, R.layout.activity_guide_v3, null);
		list.add(viewShow);
		list.add(viewSec);
		list.add(viewDown);
		return list;
	};

	private ArrayList<ImageView> getPoint(int size, LinearLayout linearLayout) {
		ArrayList<ImageView> points = new ArrayList<ImageView>();
		for (int i = 0; i < size; i++) {
			ImageView iv = (ImageView) linearLayout.getChildAt(i);
			iv.setEnabled(true);// 设置当前状态为允许（可点，灰色）
			// 额外设置一个标识符，以便点击小圆点时跳转对应页面
			iv.setTag(i);// 标识符与圆点顺序一致
			points.add(iv);// 遍历LinearLayout下的所有ImageView子节点
		}
		return points;
	};
}
