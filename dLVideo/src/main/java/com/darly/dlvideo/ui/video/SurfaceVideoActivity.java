/**上午10:36:27
 * @author zhangyh2
 * SurfaceVideoActivity.java
 * TODO
 */
package com.darly.dlvideo.ui.video;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.annotation.SuppressLint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;
import com.darly.dlvideo.R;
import com.darly.dlvideo.base.BaseActivity;
import com.darly.dlvideo.bean.Video;
import com.darly.dlvideo.common.ToastApp;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * @author zhangyh2 SurfaceVideoActivity 上午10:36:27 TODO 一种方案的播放视频。但主要播放本地视频
 */
@ContentView(R.layout.activity_surface_video)
public class SurfaceVideoActivity extends BaseActivity implements
		OnClickListener, Callback, OnSeekBarChangeListener {
	private final String TAG = getClass().getSimpleName();
	@ViewInject(R.id.sv)
	private SurfaceView sv;
	@ViewInject(R.id.btn_play)
	private Button btn_play;
	@ViewInject(R.id.btn_pause)
	private Button btn_pause;
	@ViewInject(R.id.btn_replay)
	private Button btn_replay;
	@ViewInject(R.id.btn_stop)
	private Button btn_stop;
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

	@ViewInject(R.id.seekBar)
	private SeekBar seekBar;

	private MediaPlayer mediaPlayer;
	private int currentPosition = 0;
	private boolean isPlaying;

	private Video video;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.darly.dlvideo.base.BaseActivity#initView(android.os.Bundle)
	 */
	@Override
	protected void initView(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		video = (Video) getIntent().getSerializableExtra("video");
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
		btn_play.setOnClickListener(this);
		btn_pause.setOnClickListener(this);
		btn_replay.setOnClickListener(this);
		btn_stop.setOnClickListener(this);
		sv.getHolder().addCallback(this);
		sv.setOnClickListener(this);
		seekBar.setOnSeekBarChangeListener(this);
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
		case R.id.btn_play:
			play(0);
			break;
		case R.id.btn_pause:
			pause();
			break;
		case R.id.btn_replay:
			replay();
			break;
		case R.id.btn_stop:
			stop();
			break;
		case R.id.sv:
			// 点击视频屏幕
			if (isPlaying) {
				pause();
			} else {
				replay();
			}
			ToastApp.showToast(isPlaying + "sv");
			break;
		default:
			break;
		}
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
		LogUtils.i(TAG + "SurfaceHolder surfaceCreated");
		if (currentPosition > 0) {
			play(currentPosition);
			currentPosition = 0;
		}
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
		LogUtils.i(TAG + "SurfaceHolder surfaceChanged");
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
		LogUtils.i(TAG + "SurfaceHolder surfaceDestroyed");
		if (mediaPlayer != null && mediaPlayer.isPlaying()) {
			currentPosition = mediaPlayer.getCurrentPosition();
			mediaPlayer.stop();
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
		int progress = seekBar.getProgress();
		if (mediaPlayer != null && mediaPlayer.isPlaying()) {
			mediaPlayer.seekTo(progress);
		}
	}

	/**
	 * 上午10:52:38
	 * 
	 * @author zhangyh2 TODO 用户开始播放视频 通过播放初始位置
	 */
	protected void play(final int msec) {
		File file = new File(video.getPath());
		if (!file.exists()) {
			ToastApp.showToast("视频文件路径错误");
			return;
		}
		try {
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			// 设置播放的视频源
			mediaPlayer.setDataSource(file.getAbsolutePath());
			// 设置显示视频的SurfaceHolder
			mediaPlayer.setDisplay(sv.getHolder());
			LogUtils.i(TAG + "开始装载");
			mediaPlayer.prepareAsync();
			mediaPlayer.setOnPreparedListener(new OnPreparedListener() {

				@Override
				public void onPrepared(MediaPlayer mp) {
					LogUtils.i(TAG + "装载完成");
					mediaPlayer.start();
					// 按照初始位置播放
					mediaPlayer.seekTo(msec);
					// 设置进度条的最大进度为视频流的最大播放时长
					seekBar.setMax(mediaPlayer.getDuration());
					// 设置播放时间
					videoTimeLong = mediaPlayer.getDuration();
					videoTimeString = getShowTime(videoTimeLong);
					vedioTiemTextView.setText("00:00:00/" + videoTimeString);
					
					// 开始线程，更新进度条的刻度
					new Thread() {

						@Override
						public void run() {
							try {
								isPlaying = true;
								while (isPlaying) {
									int current = mediaPlayer
											.getCurrentPosition();
									seekBar.setProgress(current);

									sleep(500);
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}.start();

					btn_play.setEnabled(false);
				}
			});
			mediaPlayer.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {
					// 在播放完毕被回调
					btn_play.setEnabled(true);
					isPlaying = false;
				}
			});

			mediaPlayer.setOnErrorListener(new OnErrorListener() {

				@Override
				public boolean onError(MediaPlayer mp, int what, int extra) {
					// 发生错误重新播放
					play(0);
					isPlaying = false;
					return false;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
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

	/**
	 * 上午10:49:48
	 * 
	 * @author zhangyh2 TODO 用户重新开始播放
	 */
	protected void replay() {
		if (mediaPlayer != null && mediaPlayer.isPlaying()) {
			mediaPlayer.seekTo(0);
			ToastApp.showToast("重新播放");
			btn_pause.setText("暂停");
			return;
		}
		isPlaying = false;
		play(0);

	}

	/**
	 * 上午10:48:24
	 * 
	 * @author zhangyh2 TODO 用户暂停
	 */
	protected void pause() {
		if (btn_pause.getText().toString().trim().equals("继续")) {
			btn_pause.setText("暂停");
			mediaPlayer.start();
			ToastApp.showToast("继续播放");
			return;
		}
		if (mediaPlayer != null && mediaPlayer.isPlaying()) {
			mediaPlayer.pause();
			btn_pause.setText("继续");
			ToastApp.showToast("暂停播放");
		}

	}

	/**
	 * 上午10:50:04
	 * 
	 * @author zhangyh2 TODO 用户停止播放
	 */
	protected void stop() {
		if (mediaPlayer != null && mediaPlayer.isPlaying()) {
			mediaPlayer.stop();
			mediaPlayer.release();
			mediaPlayer = null;
			btn_play.setEnabled(true);
			isPlaying = false;
		}
	}

}
