/**下午2:45:36
 * @author zhangyh2
 * EventLinear.java
 * TODO
 */
package com.darly.dlvideo.event;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.lidroid.xutils.util.LogUtils;

/**
 * @author zhangyh2 EventLinear 下午2:45:36 TODO
 */
public class EventLinear extends LinearLayout {

	public EventLinear(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public EventLinear(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public EventLinear(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
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
		LogUtils.d(getClass().getSimpleName() + "onInterceptTouchEvent" + str);
		return super.onInterceptTouchEvent(ev);
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

}
