/**下午2:45:26
 * @author zhangyh2
 * EventAct.java
 * TODO
 */
package com.darly.dlvideo.event;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.darly.dlvideo.R;
import com.darly.dlvideo.base.BaseActivity;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @author zhangyh2 EventAct 下午2:45:26 TODO Activity 的
 *         dispatchTouchEvent的事件分发给ViewGroup
 *         ，ViewGroup先通过事件拦截onInterceptTouchEvent（只有 ViewGroup
 *         才有此方法）来判断，是否拦截，如果没有拦截则将事件分发给 View 的分发机制dispatchTouchEvent；View
 *         获取到事件后，就开始调用 onTouchEvent 方法，如果 View 的 onTouchEvent 未把事件消费掉，则把事件有传递给
 *         ViewGroup 的 onTouchEvent，同理，ViewGroup 未把事件消费掉，继续往下级传到了 Activity 的
 *         onTouchEvent 事件处理中
 * 
 *         1、Android 事件传递是层级传递的；
 * 
 *         2、dispatchTouchEvent从底层向上层传递，而onTouchEvent刚好相反；
 * 
 *         3、onInterceptTouchEvent返回为 true
 *         时，将执行同层级的onTouchEvent，而dispatchTouchEvent和onTouchEvent返回 true
 *         时，将终止事件的传递。
 * 
 *         上面我们提到了两个关键词，拦截和消费，其实都是图片中表示的 return 返回值，在 dispatchTouchEvent
 *         我们习惯用拦截，而在 onTouchEvent 中则习惯用消费来说明（个人习惯而已）。return false
 *         表示事件未被dispatchTouchEvent拦截，也未被onTouchEvent消费。
 */
@ContentView(R.layout.activity_event)
public class EventAct extends BaseActivity implements FrostedGlassListener,
		OnClickListener {
	@ViewInject(R.id.event_imageview)
	private EventImage iv;
	@ViewInject(R.id.event_imageview_test)
	private ImageView iv_t;

	@ViewInject(R.id.event_imag)
	private EventImage image;
	@ViewInject(R.id.event_titles)
	private ImageView title;
	@ViewInject(R.id.event_text)
	private EventText tv;

	private Bitmap originBitmap;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.darly.dlvideo.base.BaseActivity#initView(android.os.Bundle)
	 */
	@Override
	protected void initView(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		originBitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.ic_fast_blur);
		iv.setTimes(2000);
		iv.setFroster(new Random().nextInt(50), new Random().nextInt(50), 2);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.darly.dlvideo.base.BaseActivity#loadData()
	 */
	@Override
	protected void loadData() {
		// TODO Auto-generated method stub
		title.setImageBitmap(originBitmap);
		Bitmap scaledBitmap = Bitmap.createScaledBitmap(originBitmap,
				originBitmap.getWidth() / 5, originBitmap.getHeight() / 5,
				false);
		image.setImageBitmap(scaledBitmap);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.darly.dlvideo.base.BaseActivity#initListener()
	 */
	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
		iv.setFrostedGlassListener(this);
		tv.setOnClickListener(this);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		String str = "ACTION_DOWN";
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			str = "ACTION_DOWN";
			break;
		case MotionEvent.ACTION_MOVE:
			str = "ACTION_MOVE";
			break;
		case MotionEvent.ACTION_UP:
			str = "ACTION_UP";
			break;
		}
		LogUtils.d(getClass().getSimpleName() + "dispatchTouchEvent" + str);
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		String str = "ACTION_DOWN";
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			str = "ACTION_DOWN";
			break;
		case MotionEvent.ACTION_MOVE:
			str = "ACTION_MOVE";
			break;
		case MotionEvent.ACTION_UP:
			str = "ACTION_UP";
			break;
		}
		LogUtils.d(getClass().getSimpleName() + "onTouchEvent" + str);
		return super.onTouchEvent(event);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.darly.dlvideo.event.FrostedGlassListener#RefreshImage(android.widget
	 * .ImageView, int, android.graphics.Bitmap)
	 */
	@Override
	public void RefreshImage(int scaleRatio) {
		// TODO Auto-generated method stub
		LogUtils.i("RefreshImage" + scaleRatio);
		// 尝试开启模糊效果
		// 缩放程度
		// 虚化程序(一般为8)
		int blurRadius = 8;
		// 我们可以利用这个function来进行bitmap的缩放。其中前三个参数很明显，其中宽高我们可以选择为原图尺寸的1/10；
		// 第四个filter是指缩放的效果，filter为true则会得到一个边缘平滑的bitmap，反之，则会得到边缘锯齿、pixelrelated的bitmap。
		// 这里我们要对缩放的图片进行虚化，所以无所谓边缘效果，filter=false。
		try {
			Bitmap scaledBitmap = Bitmap.createScaledBitmap(originBitmap,
					originBitmap.getWidth() / scaleRatio,
					originBitmap.getHeight() / scaleRatio, false);
			Bitmap blurBitmap = FastBlurUtil.doBlur(scaledBitmap, blurRadius,
					true);
			iv_t.setImageBitmap(scaledBitmap);
			// iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
			iv.setImageBitmap(blurBitmap);
		} catch (Exception e) {
			// TODO: handle exception
			iv_t.setImageBitmap(originBitmap);
			// iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
			iv.setImageBitmap(originBitmap);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.event_text:
			iv.setFroster(new Random().nextInt(50), new Random().nextInt(50),
					new Random().nextInt(4) + 1);
			iv.invalidate();
			break;

		default:
			break;
		}
	}

}
