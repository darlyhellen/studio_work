/**下午2:45:45
 * @author zhangyh2
 * EventText.java
 * TODO
 */
package com.darly.dlvideo.event;

import com.lidroid.xutils.util.LogUtils;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * @author zhangyh2 EventText 下午2:45:45 TODO
 */
public class EventImage extends ImageView {

	public EventImage(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public EventImage(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public EventImage(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 下午2:45:47 TODO 倍数效率
	 */
	private int rate = 1;

	private int scale;

	private int dest;

	private int times;

	private boolean isFrost;

	private FrostedGlassListener frostedGlassListener;

	public void setFrostedGlassListener(
			FrostedGlassListener frostedGlassListener) {
		this.frostedGlassListener = frostedGlassListener;
	}

	/**
	 * 上午10:42:55
	 * 
	 * @author zhangyh2 TODO 从res值变化到dst值，变大表示模糊，变小表示清晰
	 *         ,相等不进行调用,<rate>字段属于特殊字段，值越大速度越快完成模糊，值越小密度越高。效果越好。自然数rate>0
	 */
	public void setFroster(int res, int dst, int rate) {
		LogUtils.i("from:" + res + " to:" + dst);
		scale = res;
		dest = dst;
		this.rate = rate;
		if (res > dst) {
			// 模糊到清晰
			isFrost = true;
		} else {
			// 清晰到模糊
			isFrost = false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.ImageView#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		onRefreshImageView();
	}

	/**
	 * 下午4:56:10
	 * 
	 * @author zhangyh2 TODO 每次重绘调用刷新.
	 */
	protected void onRefreshImageView() {
		try {
			getScale();
			if (isFrost) {
				if (scale >= dest) {
					frostedGlassListener.RefreshImage(scale);
				}
			} else {
				if (scale <= dest) {
					frostedGlassListener.RefreshImage(scale);
				}
			}
			Thread.sleep(times);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private int getScale() {
		if (isFrost) {
			// 模糊到清晰
			return scale -= rate;
		} else {
			// 清晰到模糊
			return scale += rate;
		}

	}

	public void setTimes(long interval) {
		try {
			times = (int) (interval / scale * rate);
		} catch (Exception e) {
			// TODO: handle exception
			// 这个错误是允许范围内，错误发生后times的值为0，不进行睡眠。
		}

	}

}
