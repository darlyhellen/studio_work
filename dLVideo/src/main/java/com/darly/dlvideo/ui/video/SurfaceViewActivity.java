/**下午2:03:36
 * @author zhangyh2
 * SurfaceViewActivity.java
 * TODO
 */
package com.darly.dlvideo.ui.video;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.darly.dlvideo.R;
import com.darly.dlvideo.base.BaseActivity;
import com.darly.dlvideo.base.ConsVideo;
import com.darly.dlvideo.bean.Video;
import com.darly.dlvideo.common.ProgressDialogUtil;
import com.darly.dlvideo.common.ToastApp;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @author zhangyh2 SurfaceViewActivity 下午2:03:36 TODO 使用SurfaceView和MediaPlayer
 *         整合播放网络视频。
 */
@ContentView(R.layout.activity_surface_view)
public class SurfaceViewActivity extends BaseActivity implements Callback,
		MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener,
		MediaPlayer.OnErrorListener, OnBufferingUpdateListener,
		OnClickListener, OnSeekBarChangeListener {

	/**
	 * {@link SurfaceView}控件
	 */
	@ViewInject(R.id.surfaceView)
	private SurfaceView surfaceView;

	/**
	 * surfaceView播放控制
	 */
	private SurfaceHolder surfaceHolder;

	/**
	 * 播放控制条
	 */
	@ViewInject(R.id.seekbar)
	private SeekBar seekBar;

	/**
	 * 暂停播放按钮
	 */
	@ViewInject(R.id.button_play)
	private Button playButton;

	/**
	 * 重新播放按钮
	 */
	@ViewInject(R.id.button_replay)
	private Button replayButton;

	/**
	 * 截图按钮
	 */
	@ViewInject(R.id.button_screenShot)
	private Button screenShotButton;

	/**
	 * 改变视频大小button
	 */
	@ViewInject(R.id.button_videoSize)
	private Button videoSizeButton;

	/**
	 * 加载进度显示条
	 */
	@ViewInject(R.id.progressBar)
	private ProgressBar progressBar;

	/**
	 * 播放视频
	 */
	private MediaPlayer mediaPlayer;

	/**
	 * seekBar是否自动拖动
	 */
	private boolean seekBarAutoFlag = false;

	/**
	 * 视频时间显示
	 */
	@ViewInject(R.id.textView_showTime)
	private TextView vedioTiemTextView;

	/**
	 * 播放总时间
	 */
	private String videoTimeString;

	private long videoTimeLong;

	private Video video;

	private ProgressDialogUtil loading;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.darly.dlvideo.base.BaseActivity#initView(android.os.Bundle)
	 */
	@SuppressWarnings("deprecation")
	@Override
	protected void initView(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		video = (Video) getIntent().getSerializableExtra("video");
		loading = new ProgressDialogUtil(this);
		loading.setMessage(R.string.loading);
		loading.show();
		// 设置surfaceHolder
		surfaceHolder = surfaceView.getHolder();
		// 设置Holder类型,该类型表示surfaceView自己不管理缓存区,虽然提示过时，但最好还是要设置
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		// 设置surface回调
		surfaceHolder.addCallback(this);
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
		// 判断播放位置
		if (ConsVideo.PLAYPOSITION.getLen() >= 0) {
			if (null != mediaPlayer) {
				seekBarAutoFlag = true;
				mediaPlayer.seekTo(ConsVideo.PLAYPOSITION.getLen());
				mediaPlayer.start();
			} else {
				playVideo();
			}

		}
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
		try {
			if (null != mediaPlayer && mediaPlayer.isPlaying()) {
				ConsVideo.PLAYPOSITION.setLen(mediaPlayer.getCurrentPosition());
				mediaPlayer.pause();
				seekBarAutoFlag = false;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.FragmentActivity#onSaveInstanceState(android.os
	 * .Bundle) 发生屏幕旋转时调用
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		if (null != mediaPlayer) {
			// 保存播放位置
			ConsVideo.PLAYPOSITION.setLen(mediaPlayer.getCurrentPosition());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onRestoreInstanceState(android.os.Bundle)
	 * 屏幕旋转完成时调用
	 */
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.FragmentActivity#onConfigurationChanged(android
	 * .content.res.Configuration)
	 */
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		super.onConfigurationChanged(newConfig);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.view.SurfaceHolder.Callback#surfaceCreated(android.view.SurfaceHolder
	 * )
	 */
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		// 设置播放资源
		playVideo();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.view.SurfaceHolder.Callback#surfaceChanged(android.view.SurfaceHolder
	 * , int, int, int)
	 */
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.SurfaceHolder.Callback#surfaceDestroyed(android.view.
	 * SurfaceHolder)
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		// surfaceView销毁,同时销毁mediaPlayer
		if (null != mediaPlayer) {
			mediaPlayer.release();
			mediaPlayer = null;
		}
	}

	/**
	 * 播放视频
	 */
	public void playVideo() {
		// 初始化MediaPlayer
		mediaPlayer = new MediaPlayer();
		// 重置mediaPaly,建议在初始滑mediaplay立即调用。
		mediaPlayer.reset();
		// 设置声音效果
		mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
		// 设置播放完成监听
		mediaPlayer.setOnCompletionListener(this);
		// 设置媒体加载完成以后回调函数。
		mediaPlayer.setOnPreparedListener(this);
		// 错误监听回调函数
		mediaPlayer.setOnErrorListener(this);
		// 设置缓存变化监听
		mediaPlayer.setOnBufferingUpdateListener(this);
		Uri uri = Uri.parse(video.getPath());
		try {
			mediaPlayer.reset();
			mediaPlayer.setDataSource(this, uri);
			// 设置异步加载视频，包括两种方式 prepare()同步，prepareAsync()异步
			mediaPlayer.prepareAsync();
		} catch (IOException e) {
			e.printStackTrace();
			ToastApp.showToast("加载视频错误！");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.media.MediaPlayer.OnBufferingUpdateListener#onBufferingUpdate
	 * (android.media.MediaPlayer, int)
	 */
	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		// TODO Auto-generated method stub
		// percent 表示缓存加载进度，0为没开始，100表示加载完成，在加载完成以后也会一直调用该方法
		Log.e("text", "onBufferingUpdate-->" + percent);
		// 可以根据大小的变化来 缓冲文件大小
		seekBar.setSecondaryProgress(mediaPlayer.getDuration() * percent / 100);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.media.MediaPlayer.OnErrorListener#onError(android.media.MediaPlayer
	 * , int, int)
	 */
	@Override
	public boolean onError(MediaPlayer mp, int what, int extra) {
		// TODO Auto-generated method stub
		switch (what) {
		case MediaPlayer.MEDIA_ERROR_UNKNOWN:
			ToastApp.showToast("MEDIA_ERROR_UNKNOWN");
			break;
		case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
			ToastApp.showToast("MEDIA_ERROR_SERVER_DIED");
			break;
		default:
			break;
		}
		switch (extra) {
		case MediaPlayer.MEDIA_ERROR_IO:
			ToastApp.showToast("MEDIA_ERROR_IO");
			break;
		case MediaPlayer.MEDIA_ERROR_MALFORMED:
			ToastApp.showToast("MEDIA_ERROR_MALFORMED");
			break;
		case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
			ToastApp.showToast("MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK");
			break;
		case MediaPlayer.MEDIA_ERROR_TIMED_OUT:
			ToastApp.showToast("MEDIA_ERROR_TIMED_OUT");
			break;
		case MediaPlayer.MEDIA_ERROR_UNSUPPORTED:
			ToastApp.showToast("MEDIA_ERROR_UNSUPPORTED");
			break;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.media.MediaPlayer.OnPreparedListener#onPrepared(android.media
	 * .MediaPlayer)
	 */
	@Override
	public void onPrepared(MediaPlayer mp) {
		// TODO Auto-generated method stub
		// 当视频加载完毕以后，隐藏加载进度条
		progressBar.setVisibility(View.GONE);
		// 判断是否有保存的播放位置,防止屏幕旋转时，界面被重新构建，播放位置丢失。
		if (ConsVideo.PLAYPOSITION.getLen() >= 0) {
			mediaPlayer.seekTo(ConsVideo.PLAYPOSITION.getLen());
			ConsVideo.PLAYPOSITION.setLen(-1);
			// surfaceHolder.unlockCanvasAndPost(Constants.getCanvas());
		}
		seekBarAutoFlag = true;
		// 设置控制条,放在加载完成以后设置，防止获取getDuration()错误
		seekBar.setMax(mediaPlayer.getDuration());
		// 设置播放时间
		videoTimeLong = mediaPlayer.getDuration();
		videoTimeString = getShowTime(videoTimeLong);
		vedioTiemTextView.setText("00:00:00/" + videoTimeString);
		// 设置拖动监听事件
		seekBar.setOnSeekBarChangeListener(this);
		// 设置按钮监听事件
		// 重新播放
		replayButton.setOnClickListener(this);
		// 暂停和播放
		playButton.setOnClickListener(this);
		// 截图按钮
		screenShotButton.setOnClickListener(this);
		// 视频大小
		videoSizeButton.setOnClickListener(this);
		// 播放视频
		mediaPlayer.start();
		// 设置显示到屏幕
		mediaPlayer.setDisplay(surfaceHolder);
		// 开启线程 刷新进度条
		new Thread(runnable).start();
		// 设置surfaceView保持在屏幕上
		mediaPlayer.setScreenOnWhilePlaying(true);
		surfaceHolder.setKeepScreenOn(true);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.media.MediaPlayer.OnCompletionListener#onCompletion(android.media
	 * .MediaPlayer)
	 */
	@Override
	public void onCompletion(MediaPlayer mp) {
		// TODO Auto-generated method stub
		// 设置seeKbar跳转到最后位置
		seekBar.setProgress(Integer.parseInt(String.valueOf(videoTimeLong)));
		// 设置播放标记为false
		seekBarAutoFlag = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		// 重新播放
		switch (v.getId()) {
		case R.id.button_replay:
			replay();
			break;
		case R.id.button_play:
			// 播放、暂停按钮
			play();
			break;
		case R.id.button_screenShot:
			screen();
			break;
		case R.id.button_videoSize:
			// 调用改变大小的方法
			changeVideoSize();
			break;
		default:
			break;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.SeekBar.OnSeekBarChangeListener#onProgressChanged(android
	 * .widget.SeekBar, int, boolean)
	 */
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		if (progress >= 0) {
			// 如果是用户手动拖动控件，则设置视频跳转。
			if (fromUser) {
				mediaPlayer.seekTo(progress);
			}
			// 设置当前播放时间
			vedioTiemTextView.setText(getShowTime(progress) + "/"
					+ videoTimeString);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.SeekBar.OnSeekBarChangeListener#onStartTrackingTouch(android
	 * .widget.SeekBar)
	 */
	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.SeekBar.OnSeekBarChangeListener#onStopTrackingTouch(android
	 * .widget.SeekBar)
	 */
	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub

	}

	/**
	 * 滑动条变化线程
	 */
	private Runnable runnable = new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			// 增加对异常的捕获，防止在判断mediaPlayer.isPlaying的时候，报IllegalStateException异常
			try {
				while (seekBarAutoFlag) {
					/*
					 * mediaPlayer不为空且处于正在播放状态时，使进度条滚动。
					 * 通过指定类名的方式判断mediaPlayer防止状态发生不一致
					 */

					if (null != SurfaceViewActivity.this.mediaPlayer
							&& SurfaceViewActivity.this.mediaPlayer.isPlaying()) {
						seekBar.setProgress(mediaPlayer.getCurrentPosition());
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	/**
	 * 下午2:24:22
	 * 
	 * @author zhangyh2 TODO
	 */
	private void play() {
		// TODO Auto-generated method stub
		if (null != mediaPlayer) {
			// 正在播放
			if (mediaPlayer.isPlaying()) {
				ConsVideo.PLAYPOSITION.setLen(mediaPlayer.getCurrentPosition());
				// seekBarAutoFlag = false;
				mediaPlayer.pause();
				playButton.setText("播放");
			} else {
				if (ConsVideo.PLAYPOSITION.getLen() >= 0) {
					// seekBarAutoFlag = true;
					mediaPlayer.seekTo(ConsVideo.PLAYPOSITION.getLen());
					mediaPlayer.start();
					playButton.setText("暂停");
					ConsVideo.PLAYPOSITION.setLen(-1);
				}
			}

		}
	}

	/**
	 * 下午2:23:22
	 * 
	 * @author zhangyh2 TODO
	 */
	private void replay() {
		// TODO Auto-generated method stub
		// mediaPlayer不空，则直接跳转
		if (null != mediaPlayer) {
			// MediaPlayer和进度条都跳转到开始位置
			mediaPlayer.seekTo(0);
			seekBar.setProgress(0);
			// 如果不处于播放状态，则开始播放
			if (!mediaPlayer.isPlaying()) {
				mediaPlayer.start();
			}
		} else {
			// 为空则重新设置mediaPlayer
			playVideo();
		}
	}

	/**
	 * 下午2:25:21
	 * 
	 * @author zhangyh2 TODO
	 */
	private void screen() {
		// TODO Auto-generated method stub
		if (null != mediaPlayer) {
			// 视频正在播放，
			if (mediaPlayer.isPlaying()) {
				// 获取播放位置
				ConsVideo.PLAYPOSITION.setLen(mediaPlayer.getCurrentPosition());
				// 暂停播放
				mediaPlayer.pause();
				//
				playButton.setText("播放");
			}
			// 视频截图
			savaScreenShot(ConsVideo.PLAYPOSITION.getLen());
		} else {
			ToastApp.showToast("视频暂未播放！");
		}
	}

	/**
	 * 保存视频截图.该方法只能支持本地视频文件
	 * 
	 * @param time视频当前位置
	 */
	public void savaScreenShot(long time) {
		// 标记是否保存成功
		boolean isSave = false;
		// 获取文件路径
		String path = null;
		// 文件名称
		String fileName = null;
		if (time >= 0) {
			try {
				MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
				mediaMetadataRetriever.setDataSource(video.getPath());
				// 获取视频的播放总时长单位为毫秒
				// 转换格式为微秒
				// 计算当前视频截取的位置
				// 获取当前视频指定位置的截图,时间参数的单位是微秒,做了*1000处理
				// 第二个参数为指定位置，意思接近的位置截图
				Bitmap bitmap = mediaMetadataRetriever
						.getFrameAtTime(time * 1000,
								MediaMetadataRetriever.OPTION_CLOSEST_SYNC);
				// 释放资源
				mediaMetadataRetriever.release();
				// 判断外部设备SD卡是否存在
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					// 存在获取外部文件路径
					path = Environment.getExternalStorageDirectory().getPath();
				} else {
					// 不存在获取内部存储
					path = Environment.getDataDirectory().getPath();
				}
				// 设置文件名称 ，以事件毫秒为名称
				fileName = Calendar.getInstance().getTimeInMillis() + ".jpg";
				// 设置保存文件
				File file = new File(path + "/shen/" + fileName);

				if (!file.exists()) {
					file.createNewFile();
				}
				FileOutputStream fileOutputStream = new FileOutputStream(file);
				bitmap.compress(CompressFormat.JPEG, 100, fileOutputStream);
				isSave = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
			// 保存成功以后，展示图片
			if (isSave) {
				ImageView imageView = new ImageView(this);
				imageView.setLayoutParams(new LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
				imageView.setImageBitmap(BitmapFactory.decodeFile(path
						+ "/shen/" + fileName));
				new AlertDialog.Builder(this).setView(imageView).show();
			}
		}

	}

	/**
	 * 改变视频的显示大小，全屏，窗口，内容
	 */
	public void changeVideoSize() {
		// 改变视频大小
		String videoSizeString = videoSizeButton.getText().toString();
		// 获取视频的宽度和高度
		int width = mediaPlayer.getVideoWidth();
		int height = mediaPlayer.getVideoHeight();
		// 如果按钮文字为窗口则设置为窗口模式
		if ("窗口".equals(videoSizeString)) {
			/*
			 * 如果为全屏模式则改为适应内容的，前提是视频宽高小于屏幕宽高，如果大于宽高 我们要做缩放
			 * 如果视频的宽高度有一方不满足我们就要进行缩放. 如果视频的大小都满足就直接设置并居中显示。
			 */
			if (width > ConsVideo.WIDTH.getLen()
					|| height > ConsVideo.HEIGHT.getLen()) {
				// 计算出宽高的倍数
				float vWidth = (float) width / (float) ConsVideo.WIDTH.getLen();
				float vHeight = (float) height
						/ (float) ConsVideo.HEIGHT.getLen();
				// 获取最大的倍数值，按大数值进行缩放
				float max = Math.max(vWidth, vHeight);
				// 计算出缩放大小,取接近的正值
				width = (int) Math.ceil(width / max);
				height = (int) Math.ceil(height / max);
			}
			// 设置SurfaceView的大小并居中显示
			RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
					width, height);
			layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
			surfaceView.setLayoutParams(layoutParams);
			videoSizeButton.setText("全屏");
		} else if ("全屏".equals(videoSizeString)) {
			// 设置全屏
			// 设置SurfaceView的大小并居中显示
			RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
					ConsVideo.WIDTH.getLen(), ConsVideo.HEIGHT.getLen());
			layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
			surfaceView.setLayoutParams(layoutParams);
			videoSizeButton.setText("窗口");
		}
	}

	@SuppressLint("SimpleDateFormat")
	public String getShowTime(long milliseconds) {
		// 获取日历函数
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(milliseconds);
		SimpleDateFormat dateFormat = null;
		// 判断是否大于60分钟，如果大于就显示小时。设置日期格式
		if (milliseconds / 60000 > 60) {
			dateFormat = new SimpleDateFormat("hh:mm:ss");
		} else {
			dateFormat = new SimpleDateFormat("mm:ss");
		}
		return dateFormat.format(calendar.getTime());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// 由于MediaPlay非常占用资源，所以建议屏幕当前activity销毁时，则直接销毁
		try {
			if (null != mediaPlayer) {
				// 提前标志为false,防止在视频停止时，线程仍在运行。
				seekBarAutoFlag = false;
				// 如果正在播放，则停止。
				if (mediaPlayer.isPlaying()) {
					mediaPlayer.stop();
				}
				ConsVideo.PLAYPOSITION.setLen(-1);
				// 释放mediaPlayer
				mediaPlayer.release();
				mediaPlayer = null;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
