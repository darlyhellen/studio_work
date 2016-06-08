package com.darly.dlvideo.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;

import com.darly.dlvideo.R;
import com.darly.dlvideo.adapter.VideoAdapter.OnRecyclerViewListener;
import com.darly.dlvideo.base.ConsVideo;
import com.darly.dlvideo.widget.smoth.SmoothImageView;

/**
 * @author zhangyh2 qa 上午11:43:56 TODO
 */
public class VideoViewHolder extends RecyclerView.ViewHolder implements
		OnClickListener, OnLongClickListener {

	public View rootView;
	public SmoothImageView iconTv;
	public TextView nameTv;
	public TextView ageTv;
	public int position;

	private OnRecyclerViewListener listener;

	public VideoViewHolder(View itemView, OnRecyclerViewListener listener) {
		super(itemView);
		this.listener = listener;
		iconTv = (SmoothImageView) itemView.findViewById(R.id.pic);
		nameTv = (TextView) itemView.findViewById(R.id.name);
		ageTv = (TextView) itemView.findViewById(R.id.birth);
		iconTv.setLayoutParams(new LayoutParams(ConsVideo.WIDTH.getLen(),
				(int) (ConsVideo.WIDTH.getLen() / 2.6)));
		rootView = itemView
				.findViewById(R.id.recycler_view_test_item_person_view);
		rootView.setOnClickListener(this);
		rootView.setOnLongClickListener(this);
		Log.i("view", rootView + "");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnLongClickListener#onLongClick(android.view.View)
	 */
	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		if (listener != null) {
			listener.onItemLongClick(v, getPosition());
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (listener != null) {
			listener.onItemClick(v, getPosition());
		}
	}

}