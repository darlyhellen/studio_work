/**上午10:05:19
 * @author zhangyh2
 * ShowVideoActivity.java
 * TODO
 */
package com.darly.dlvideo.ui.video;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.darly.dlvideo.R;
import com.darly.dlvideo.base.BaseActivity;
import com.darly.dlvideo.bean.Video;
import com.darly.dlvideo.common.ImageLoaderUtil;
import com.darly.dlvideo.common.ProgressDialogUtil;
import com.darly.dlvideo.common.ToastApp;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @author zhangyh2 ShowVideoActivity 上午10:05:19 TODO
 */
@ContentView(R.layout.activity_show_video)
public class VideoShowActivity extends BaseActivity {
	@ViewInject(R.id.video_videoview)
	private VideoView vv;
	@ViewInject(R.id.video_image)
	private ImageView iv;

	private Video video;

	private MediaController controller;

	private ProgressDialogUtil loading;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.darly.dlclent.base.BaseActivity#initView(android.os.Bundle)
	 */
	@Override
	protected void initView(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		video = (Video) getIntent().getSerializableExtra("video");
		loading = new ProgressDialogUtil(this);
		loading.setMessage(R.string.loading);
		loading.show();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.darly.dlclent.base.BaseActivity#loadData()
	 */
	@Override
	protected void loadData() {
		// TODO Auto-generated method stub
		ImageLoaderUtil.getInstance().loadImageNor(video.getAlbum(), iv);
		if (video != null) {
			vv.setVideoPath(video.getPath());
			controller = new MediaController(this);
			vv.setMediaController(controller);
			vv.requestFocus();
			vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
				@Override
				public void onPrepared(MediaPlayer mediaPlayer) {
					loading.cancel();
					iv.setVisibility(View.GONE);
					mediaPlayer.setPlaybackSpeed(1.0f);
				}
			});
		} else {
			ToastApp.showToast("URL 为空");
			return;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.darly.dlvideo.base.BaseActivity#initListener()
	 */
	@Override
	protected void initListener() {
		// TODO Auto-generated method stub

	}

}
