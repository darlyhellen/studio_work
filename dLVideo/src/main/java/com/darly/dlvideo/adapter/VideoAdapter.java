/**上午10:58:32
 * @author zhangyh2
 * RecyclerAdaper.java
 * TODO
 */
package com.darly.dlvideo.adapter;

import java.util.List;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.darly.dlvideo.R;
import com.darly.dlvideo.bean.Video;
import com.darly.dlvideo.common.ImageLoaderUtil;

/**
 * Author: wangjie Email: tiantian.china.2@gmail.com Date: 1/17/15.
 */
public class VideoAdapter extends RecyclerView.Adapter<VideoViewHolder> {
	public static interface OnRecyclerViewListener {
		void onItemClick(View v, int position);

		boolean onItemLongClick(View v, int position);
	}

	private OnRecyclerViewListener listener;

	private List<Video> list;

	public VideoAdapter(List<Video> list) {
		this.list = list;
	}

	@Override
	public VideoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		View view = LayoutInflater.from(viewGroup.getContext()).inflate(
				R.layout.activity_recycler_item, null);
		FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.WRAP_CONTENT);
		view.setLayoutParams(lp);
		return new VideoViewHolder(view, listener);
	}

	@Override
	public void onBindViewHolder(VideoViewHolder viewHolder, int i) {
		viewHolder.position = i;
		Video video = list.get(i);
		viewHolder.nameTv.setText(video.getDisplayName());
		viewHolder.ageTv.setText(video.getArtist());
		ImageLoaderUtil.getInstance().loadImageNor(video.getAlbum(),
				viewHolder.iconTv);
	}

	@Override
	public int getItemCount() {
		return list == null ? 0 : list.size();
	}

	/**
	 * @param list
	 *            the list to set
	 */
	public void setList(List<Video> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	public void setListener(OnRecyclerViewListener listener) {
		this.listener = listener;
	}

}