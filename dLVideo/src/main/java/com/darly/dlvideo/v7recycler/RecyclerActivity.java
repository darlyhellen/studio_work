/**上午10:46:36
 * @author zhangyh2
 * RecyclerActivity.java
 * TODO
 */
package com.darly.dlvideo.v7recycler;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.darly.dlvideo.R;
import com.darly.dlvideo.adapter.VideoAdapter;
import com.darly.dlvideo.base.BaseActivity;
import com.darly.dlvideo.bean.Video;
import com.darly.dlvideo.controller.VideoActControl;
import com.lidroid.xutils.view.annotation.ContentView;

/**
 * @author zhangyh2 RecyclerActivity 上午10:46:36 TODO 处理正规的View层
 */
@ContentView(R.layout.activity_recycler)
public class RecyclerActivity extends BaseActivity {

	public RecyclerView recycler;

	public VideoAdapter adapter;

	public List<Video> list = new ArrayList<Video>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.darly.dlvideo.base.BaseActivity#initView(android.os.Bundle)
	 */
	@Override
	protected void initView(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		recycler = (RecyclerView) findViewById(R.id.v7_recycler);
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		recycler.setLayoutManager(layoutManager);
		recycler.addItemDecoration(new DividerItemDecoration(this,
				layoutManager.getOrientation()));
		recycler.setHasFixedSize(true);
		adapter = new VideoAdapter(list);
		recycler.setAdapter(adapter);
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
		VideoActControl control = new VideoActControl(this);
		control.initData();
		adapter.setListener(control);
	}

}
