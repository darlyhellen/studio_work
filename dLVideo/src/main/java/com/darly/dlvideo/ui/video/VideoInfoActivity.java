/**上午11:24:40
 * @author zhangyh2
 * VideoInfoActivity.java
 * TODO
 */
package com.darly.dlvideo.ui.video;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.darly.dlvideo.R;
import com.darly.dlvideo.base.BaseActivity;
import com.darly.dlvideo.base.ConsVideo;
import com.darly.dlvideo.bean.Video;
import com.darly.dlvideo.common.ImageLoaderUtil;
import com.darly.dlvideo.widget.smoth.SmoothImageView;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nineoldandroids.animation.ObjectAnimator;

/**
 * @author zhangyh2 VideoInfoActivity 上午11:24:40 TODO
 */
@ContentView(R.layout.activity_info_video)
public class VideoInfoActivity extends BaseActivity implements OnClickListener {

	@ViewInject(R.id.info_pic)
	private SmoothImageView info_image;
	@ViewInject(R.id.info_name)
	private TextView info_name;
	@ViewInject(R.id.info_descrip)
	private TextView info_desc;

	private Video video;

	private int mLocationX, mLocationY, mWidth, mHeight;

	private boolean hasSurface;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.darly.dlvideo.base.BaseActivity#initView(android.os.Bundle)
	 */
	@Override
	protected void initView(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		video = (Video) getIntent().getSerializableExtra("video");
		hasSurface = getIntent().getBooleanExtra("hasSurface", false);
		mLocationX = getIntent().getIntExtra("locationX", 0);
		mLocationY = getIntent().getIntExtra("locationY", 0);
		mWidth = getIntent().getIntExtra("width", 0);
		mHeight = getIntent().getIntExtra("height", 0);
		info_image.setOriginalInfo(mWidth, mHeight, mLocationX, mLocationY);
		info_image.transformIn();
		info_image.setLayoutParams(new LayoutParams(ConsVideo.WIDTH.getLen(),
				ConsVideo.WIDTH.getLen()));
		info_image.setScaleType(ScaleType.FIT_CENTER);

		ImageLoaderUtil.getInstance()
				.loadImageNor(video.getAlbum(), info_image);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.darly.dlvideo.base.BaseActivity#loadData()
	 */
	@Override
	protected void loadData() {
		// TODO Auto-generated method stub

		info_name.setText(video.getDisplayName());
		ObjectAnimator name = ObjectAnimator
				.ofFloat(info_name, "alpha", 0f, 1f);
		name.setDuration(200);
		name.start();
		ObjectAnimator des = ObjectAnimator.ofFloat(info_desc, "alpha", 0f, 1f);
		des.setDuration(200);
		des.start();
		info_desc.setText(video.getTitle());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.darly.dlvideo.base.BaseActivity#initListener()
	 */
	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
		info_image.setOnClickListener(this);
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
		case R.id.info_pic:
			if (hasSurface) {
				Intent intent = new Intent(this, SurfaceVideoActivity.class);
				intent.putExtra("video", video);
				startActivity(intent);
			} else {
				Intent intent = new Intent(this, SurfaceViewActivity.class);
				intent.putExtra("video", video);
				startActivity(intent);
			}
			break;

		default:
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#finish()
	 */
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		info_image.transformIn();
		info_image.setLayoutParams(new LayoutParams(ConsVideo.WIDTH.getLen(),
				(int) (ConsVideo.WIDTH.getLen() / 2.6)));
		info_image.setScaleType(ScaleType.CENTER_CROP);
		overridePendingTransition(0, 0);
		super.finish();
	}

}
