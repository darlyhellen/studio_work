/**下午1:57:38
 * @author zhangyh2
 * WebViewActivity.java
 * TODO
 */
package com.darly.dlvideo.ui.uzan;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.darly.dlvideo.R;
import com.darly.dlvideo.base.BaseActivity;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.youzan.sdk.YouzanBridge;
import com.youzan.sdk.YouzanSDK;
import com.youzan.sdk.model.goods.GoodsShareModel;
import com.youzan.sdk.web.bridge.IBridgeEnv;
import com.youzan.sdk.web.event.ShareDataEvent;
import com.youzan.sdk.web.event.UserInfoEvent;
import com.youzan.sdk.web.plugin.YouzanChromeClient;
import com.youzan.sdk.web.plugin.YouzanWebClient;

/**
 * @author zhangyh2 WebViewActivity 下午1:57:38 TODO
 */
@ContentView(R.layout.activity_webview)
public class WebViewActivity extends BaseActivity implements OnClickListener {

	private String url = "https://wap.koudaitong.com/v2/showcase/homepage?alias=1e99alxjl";
	@ViewInject(R.id.webview_view)
	private WebView wv;
	@ViewInject(R.id.header_back)
	private ImageView back;
	@ViewInject(R.id.header_title)
	private TextView title;
	@ViewInject(R.id.header_other)
	private ImageView other;

	/**
	 * H5和原生的桥接对象
	 */
	private YouzanBridge bridge;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.darly.dlclent.base.BaseActivity#initView(android.os.Bundle)
	 */
	@Override
	protected void initView(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		title.setText("网页加载");
		back.setVisibility(View.VISIBLE);
		other.setVisibility(View.INVISIBLE);
		initWeb();
		initBridge();
		openWebview();

	}

	/**
	 * 初始化桥接对象.
	 * 
	 * <pre>
	 * 可使用的扩展有:
	 *      {@link UserInfoEvent}
	 *          用户同步登录,使用{@link YouzanSDK#asyncRegisterUser}完成信息注册的可以忽略这个扩展;
	 *          具体参考{@link LoginWebActivity}
	 * 
	 *      {@link ShareDataEvent}
	 *          获取分享数据
	 *          具体参考{@link ShareEvent}
	 * 
	 * ...
	 * </pre>
	 */
	private void initBridge() {
		bridge = YouzanBridge.build(this, wv).setWebClient(new WebClient())
				.setChromeClient(new ChromeClient()).create();
		bridge.hideTopbar(true);// 隐藏顶部店铺信息栏
		// 根据需求添加相应的桥接事件
		bridge.add(new ShareEvent());// 分享
	}

	/**
	 * 打开链接
	 */
	private void openWebview() {
		if (wv != null && !TextUtils.isEmpty(url)) {
			wv.loadUrl(url);
		}
	}

	/**
	 * 下午2:22:43
	 * 
	 * @author zhangyh2 TODO
	 */
	@SuppressLint("SetJavaScriptEnabled")
	@SuppressWarnings("deprecation")
	private void initWeb() {
		// TODO Auto-generated method stub
		WebSettings webSettings = wv.getSettings();
		// 设置WebView属性，能够执行Javascript脚本
		webSettings.setJavaScriptEnabled(true);
		webSettings.setSupportZoom(false);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		//
		webSettings.setUseWideViewPort(true);// 关键点
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setAppCacheEnabled(true);
		webSettings.setAppCacheMaxSize(8 * 1024 * 1024); // 8MB
		// webSettings.setAppCachePath(Constants.WEBVIEW_CACHE_DIR );
		String appCacheDir = this.getApplicationContext()
				.getDir("cache", Context.MODE_PRIVATE).getPath();
		webSettings.setAppCachePath(appCacheDir);
		webSettings.setAllowFileAccess(true);
		webSettings.setDomStorageEnabled(true);
		webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
		// js调用安卓方法
		// wv.addJavascriptInterface(this, "javajs");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.darly.dlclent.base.BaseActivity#loadData()
	 */
	@Override
	protected void loadData() {
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.darly.dlclent.base.BaseActivity#initListener()
	 */
	@Override
	protected void initListener() {
		// TODO Auto-generated method stub
		back.setOnClickListener(this);
	}

	/**
	 * 自定义ChromeClient 必须继承自{@link YouzanWebClient}
	 */
	private class ChromeClient extends YouzanChromeClient {

		@Override
		public void onReceivedTitle(WebView view, String title) {
			super.onReceivedTitle(view, title);
			// 这里获取到WebView的标题
		}
	}

	/**
	 * 自定义WebClient 必须继承自{@link YouzanWebClient} 这里完成WebView监听
	 */
	private class WebClient extends YouzanWebClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			if (super.shouldOverrideUrlLoading(view, url)) {
				return true;
			}
			return false;// 或者做其他操作
		}
	}

	/**
	 * 页面回退 bridge.pageGoBack()返回True表示处理的是网页的回退
	 */
	@Override
	public void onBackPressed() {
		if (bridge == null || !bridge.pageGoBack()) {
			super.onBackPressed();
		}
	}

	/**
	 * 处理WebView上传文件
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (bridge.isReceiveFileForWebView(requestCode, data)) {
			return;
		}
		// ...Other request things
	}

	/**
	 * @author zhangyh2 ShareEvent 上午10:09:50 TODO 分享
	 */
	public class ShareEvent extends ShareDataEvent {

		/**
		 * 回传分享数据, 再调用组件进行分享
		 * 
		 * @param env
		 *            一些上下文环境
		 * @param data
		 *            分享数据
		 */
		@Override
		public void call(IBridgeEnv env, GoodsShareModel data) {
			new AlertDialog.Builder(env.getActivity())
					.setTitle(data.getTitle())
					.setMessage(data.getDesc() + "\n\n" + data.getLink())
					.create().show();
		}
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
		case R.id.header_back:
			if (wv.canGoBack()) {
				wv.goBack();
			} else {
				finish();
			}
			break;

		default:
			break;
		}
	}

}
