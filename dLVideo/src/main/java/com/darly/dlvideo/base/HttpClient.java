package com.darly.dlvideo.base;

import java.io.File;
import java.io.UnsupportedEncodingException;

import org.apache.http.entity.StringEntity;

import android.content.Context;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.util.LogUtils;

public class HttpClient {

	private HttpClient() {
	}

	private static HttpUtils httpUtils = new HttpUtils();

	public static void post(String url, String s,
			RequestCallBack<String> callBack) {
		RequestParams params = new RequestParams();
		params.addHeader("Content-Type", "text/html;charset=UTF-8");
		// params.addHeader("Accept", "text/html");
		params.addHeader("charset", "utf-8");
		try {
			StringEntity se = new StringEntity(s, "utf-8");
			params.setBodyEntity(se);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		httpUtils.configTimeout(5 * 1000);
		httpUtils.configRequestRetryCount(1);
		httpUtils.configRequestThreadPoolSize(3);
		httpUtils.configResponseTextCharset("utf-8");
		httpUtils.send(HttpMethod.POST, url, params, callBack);
		LogUtils.i("POST:" + url + " params:" + s.toString());
	}

	public static void put(String url, String s,
			RequestCallBack<String> callBack) {
		RequestParams params = new RequestParams();
		params.addHeader("Content-Type", "application/json;charset=UTF-8");
		// params.addHeader("Accept", "text/html");
		params.addHeader("charset", "utf-8");
		try {
			StringEntity se = new StringEntity(s, "utf-8");
			params.setBodyEntity(se);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		httpUtils.configTimeout(5 * 1000);
		httpUtils.configRequestRetryCount(1);
		httpUtils.configRequestThreadPoolSize(3);
		httpUtils.configResponseTextCharset("utf-8");
		httpUtils.send(HttpMethod.PUT, url, params, callBack);
		LogUtils.i("PUT:" + url + " params:" + s.toString());
	}

	public static void options(String url, String param,
			RequestCallBack<String> callBack) {
		RequestParams params = new RequestParams();
		params.addBodyParameter("param", param);
		httpUtils.configTimeout(5 * 1000);
		httpUtils.configRequestRetryCount(1);
		httpUtils.configRequestThreadPoolSize(3);
		httpUtils.configResponseTextCharset("utf-8");
		httpUtils.send(HttpMethod.OPTIONS, url, params, callBack);
		LogUtils.i("OPTIONS:" + url + " params:" + params);
	}

	public static void get(Context context, String url, RequestParams params,
			RequestCallBack<String> callBack) {
		params.addHeader("Content-Type", "text/html");
		// params.addHeader("Accept", "text/html");
		httpUtils.configTimeout(1000 * 1);
		httpUtils.configResponseTextCharset("utf-8");
		// 设置当前请求的缓存时间
		httpUtils.configCurrentHttpCacheExpiry(0 * 1000);
		// 设置默认请求的缓存时间
		httpUtils.configDefaultHttpCacheExpiry(0);
		// 设置线程数
		httpUtils.configRequestThreadPoolSize(1);
		httpUtils.send(HttpMethod.GET, url, params, callBack);
		if (null != params.getQueryStringParams()) {
			LogUtils.i("GET:" + url + " params:"
					+ params.getQueryStringParams().toString());
		} else {
			LogUtils.i("GET:" + url);
		}

	}

	public static void delete(Context context, String url,
			RequestParams params, RequestCallBack<String> callBack) {
		if (null != params.getQueryStringParams()) {
		}
		params.addHeader("Content-Type", "application/json");
		// params.addHeader("Accept", "text/html");
		httpUtils.configTimeout(5 * 1000);
		httpUtils.configResponseTextCharset("utf-8");
		httpUtils.send(HttpMethod.DELETE, url, params, callBack);
	}

	@SuppressWarnings("rawtypes")
	public static HttpHandler download(final Context context, final String url,
			final String fileneme, final RequestCallBack<File> callBack) {
		httpUtils.configCurrentHttpCacheExpiry(1000 * 10);
		return httpUtils.download(url, fileneme, true, true, callBack);
	}

}
