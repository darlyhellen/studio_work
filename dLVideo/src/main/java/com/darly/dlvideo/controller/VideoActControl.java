/**下午2:08:40
 * @author zhangyh2
 * VideoActControl.java
 * TODO
 */
package com.darly.dlvideo.controller;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.provider.MediaStore.Video.VideoColumns;
import android.util.Log;
import android.view.View;

import com.darly.dlvideo.R;
import com.darly.dlvideo.adapter.VideoAdapter.OnRecyclerViewListener;
import com.darly.dlvideo.base.APP;
import com.darly.dlvideo.base.ConsHttpUrl;
import com.darly.dlvideo.base.HttpClient;
import com.darly.dlvideo.bean.BaseModel;
import com.darly.dlvideo.bean.BaseModelPaser;
import com.darly.dlvideo.bean.ClientVideo;
import com.darly.dlvideo.bean.Video;
import com.darly.dlvideo.common.ToastApp;
import com.darly.dlvideo.ui.video.VideoInfoActivity;
import com.darly.dlvideo.v7recycler.RecyclerActivity;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.util.LogUtils;

/**
 * @author zhangyh2 VideoActControl 下午2:08:40 TODO对应控制类
 */
public class VideoActControl implements OnRecyclerViewListener {

	private RecyclerActivity contr;

	private boolean hasSurface;

	public VideoActControl(RecyclerActivity contr) {
		super();
		this.contr = contr;
	}

	/**
	 * 下午2:26:17
	 * 
	 * @author zhangyh2 TODO 控制器中实现数据加载。逻辑判别
	 */
	public void initData() {
		// TODO Auto-generated method stub
		getNetVideos();
	}

	/**
	 * 下午2:10:25
	 * 
	 * @author zhangyh2 TODO 获取视频列表
	 */
	public List<Video> getVideos() {
		List<Video> list = null;
		Cursor cursor = APP
				.getInstance()
				.getContentResolver()
				.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null,
						null, null);
		if (cursor != null) {
			list = new ArrayList<Video>();
			while (cursor.moveToNext()) {
				int id = cursor.getInt(cursor
						.getColumnIndexOrThrow(BaseColumns._ID));
				String title = cursor.getString(cursor
						.getColumnIndexOrThrow(MediaColumns.TITLE));
				String album = cursor.getString(cursor
						.getColumnIndexOrThrow(VideoColumns.ALBUM));
				String artist = cursor.getString(cursor
						.getColumnIndexOrThrow(VideoColumns.ARTIST));
				String displayName = cursor.getString(cursor
						.getColumnIndexOrThrow(MediaColumns.DISPLAY_NAME));
				String mimeType = cursor.getString(cursor
						.getColumnIndexOrThrow(MediaColumns.MIME_TYPE));
				String path = cursor.getString(cursor
						.getColumnIndexOrThrow(MediaColumns.DATA));
				long duration = cursor.getInt(cursor
						.getColumnIndexOrThrow(VideoColumns.DURATION));
				long size = cursor.getLong(cursor
						.getColumnIndexOrThrow(MediaColumns.SIZE));
				Video video = new Video(id, title, album, artist, displayName,
						mimeType, path, size, duration);
				list.add(video);
			}
			cursor.close();
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.darly.dlvideo.adapter.VideoAdapter.OnRecyclerViewListener#onItemClick
	 * (android.view.View, int)
	 */
	@Override
	public void onItemClick(View v, int position) {
		// TODO Auto-generated method stub
		Log.i("onClick", "onItemClick" + v + position);
		Video video = contr.list.get(position);
		if (video != null) {
			Intent intent = new Intent(contr, VideoInfoActivity.class);
			int[] location = new int[2];
			v.getLocationOnScreen(location);
			intent.putExtra("locationX", location[0]);// 必须
			intent.putExtra("locationY", location[1]);// 必须
			intent.putExtra("width", v.getWidth());// 必须
			intent.putExtra("height", v.getHeight());// 必须
			intent.putExtra("video", video);
			if (position % 2 == 0) {
				hasSurface = true;
			} else {
				hasSurface = false;
			}
			intent.putExtra("hasSurface", hasSurface);
			contr.startActivity(intent);
			contr.overridePendingTransition(0, 0);
		} else {
			ToastApp.showToast("数据为空");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.darly.dlvideo.adapter.VideoAdapter.OnRecyclerViewListener#onItemLongClick
	 * (android.view.View, int)
	 */
	@Override
	public boolean onItemLongClick(View v, int position) {
		// TODO Auto-generated method stub
		Log.i("onLongClick", "onItemLongClick" + v + position);
		return false;
	}

	/**
	 * 上午10:48:59
	 * 
	 * @author zhangyh2 TODO 获取服务端Video视频集合
	 */
	private void getNetVideos() {
		// TODO Auto-generated method stub
		HttpClient.get(contr, ConsHttpUrl.CLIENTVIDEO, new RequestParams(),
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// TODO Auto-generated method stub
						contr.list = getVideos();
						paserVideo(arg0.result);

					}

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub
						contr.list = getVideos();
						contr.adapter.setList(contr.list);
						ToastApp.showToast(R.string.neterror_norespanse);
					}
				});
	}

	/**
	 * 上午10:51:44
	 * 
	 * @author zhangyh2 TODO 解析服务器返回的视频JSON
	 */
	protected void paserVideo(String result) {
		// TODO Auto-generated method stub
		if (result == null) {
			return;
		}
		LogUtils.i(result);
		BaseModel<List<ClientVideo>> data = new BaseModelPaser<List<ClientVideo>>()
				.paserJson(result, new TypeToken<List<ClientVideo>>() {
				});
		if (data != null && data.getCode() == 200) {
			List<ClientVideo> vids = data.getData();
			if (vids != null && vids.size() > 0) {
				for (ClientVideo video : vids) {
					Video vi = new Video();
					vi.setDisplayName(video.getVideoName());
					vi.setAlbum(video.getVideoImage());
					vi.setTitle(video.getVideoDescripe());
					vi.setPath(video.getVideoUrl());
					vi.setArtist("#综合  / 02'23\"");
					contr.list.add(vi);
				}
			}
			contr.adapter.setList(contr.list);
		} else {
			ToastApp.showToast(data.getMsg());
		}
	}

}
