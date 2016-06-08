/**
 * 下午2:27:28
 * @author zhangyh2
 * $
 * APP.java
 * TODO
 */
package com.darly.dlvideo.base;

import io.vov.vitamio.VitamioHelper;

import java.io.File;
import java.math.BigInteger;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.view.WindowManager;

import com.darly.dlvideo.common.ImageLoaderUtil;
import com.darly.dlvideo.ui.video.VitamioMainListener;
import com.lidroid.xutils.util.LogUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.analytics.MobclickAgent.EScenarioType;
import com.youzan.sdk.YouzanSDK;

/**
 * @author zhangyh2 APP $ 下午2:27:28 TODO 系统入口程序。开始初始化的东西。
 */
public class APP extends Application {

	private static APP instance;

	public static APP getInstance() {
		if (instance == null) {
			LogUtils.i("系统初始化错误...");
		}
		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Application#onCreate()
	 */
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		instance = this;
		// 初始化友盟统计EScenarioType. E_UM_NORMAL　　普通统计场景类型
		// EScenarioType. E_UM_GAME 　　游戏场景类型
		// EScenarioType. E_UM_ANALYTICS_OEM 统计盒子场景类型
		// EScenarioType. E_UM_GAME_OEM 　 游戏盒子场景类型
		MobclickAgent.setScenarioType(this, EScenarioType.E_UM_NORMAL);
		if (ConsVideo.WIDTH.getLen() == 0 || ConsVideo.HEIGHT.getLen() == 0) {
			getParamsWithWH();
		}
		creatFile();
		ImageLoaderUtil.getInstance();
		initVitamio();
		initUzan();
		// LogUtils.i(getDeviceInfo(instance));
	}

	/**
	 * 上午9:58:48
	 * 
	 * @author zhangyh2 TODO
	 */
	private void initUzan() {
		// TODO Auto-generated method stub
		/**
		 * 初始化
		 * 
		 * @param Context
		 *            application Context
		 * @param userAgent
		 *            用户代理, 填写UA
		 */
		YouzanSDK.init(this, "9d4c27edeafad2e13a1464165102698");
	}

	/**
	 * 上午10:12:38
	 * 
	 * @author zhangyh2 TODO 初始化视频Lib的静态接口方式
	 */
	private void initVitamio() {
		// TODO Auto-generated method stub
		VitamioHelper.getInstance().setmVitamioListener(
				VitamioMainListener.getInstance());
	}

	/**
	 * 
	 * 下午1:53:28
	 * 
	 * @author zhangyh2 APP.java TODO
	 */
	@SuppressWarnings("deprecation")
	private void getParamsWithWH() {
		// TODO Auto-generated method stub
		WindowManager wm = (WindowManager) instance
				.getSystemService(Context.WINDOW_SERVICE);
		ConsVideo.WIDTH.setLen(wm.getDefaultDisplay().getWidth());
		ConsVideo.HEIGHT.setLen(wm.getDefaultDisplay().getHeight());
	}

	/**
	 * @param context
	 * @return 上午9:23:50
	 * @author Zhangyuhui AppStack.java TODO 判断网络连接状态
	 */
	public static boolean isNetworkConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}

	/**
	 * 获得当前进程的名字
	 *
	 * @param context
	 * @return 进程号
	 */
	public static String getCurProcessName(Context context) {

		int pid = android.os.Process.myPid();

		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);

		for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
				.getRunningAppProcesses()) {

			if (appProcess.pid == pid) {
				return appProcess.processName;
			}
		}
		return null;
	}

	/**
	 * @param context
	 * @return 下午1:53:21
	 * @author Zhangyuhui AppStack.java TODO 友盟获取设备信息的方法体。
	 */
	public String getDeviceInfo(Context context) {
		try {
			org.json.JSONObject json = new org.json.JSONObject();
			android.telephony.TelephonyManager tm = (android.telephony.TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			String device_id = tm.getDeviceId();
			android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context
					.getSystemService(Context.WIFI_SERVICE);

			String mac = wifi.getConnectionInfo().getMacAddress();
			json.put("mac", mac);
			if (TextUtils.isEmpty(device_id)) {
				device_id = mac;
			}
			if (TextUtils.isEmpty(device_id)) {
				device_id = android.provider.Settings.Secure.getString(
						context.getContentResolver(),
						android.provider.Settings.Secure.ANDROID_ID);
			}
			json.put("device_id", device_id);
			/** 通过包管理器获得指定包名包含签名的包信息 **/
			PackageInfo packageInfo = getPackageManager().getPackageInfo(
					getPackageName(), PackageManager.GET_SIGNATURES);
			/******* 通过返回的包信息获得签名数组 *******/
			Signature[] signatures = packageInfo.signatures;
			/******* 循环遍历签名数组拼接应用签名 *******/
			for (Signature signature : signatures) {
				json.put("device_signature",
						new BigInteger(signature.toByteArray()).toString(16));
			}
			return json.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * @return 下午3:23:16
	 * @author zhangyh2 APP.java TODO 获取XML文件中的版本信息，用于展示页面。
	 */
	public String getVersion() {
		String version = "0.0.0";
		try {
			PackageInfo packageInfo = getPackageManager().getPackageInfo(
					getPackageName(), 0);
			version = packageInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		return version;
	}

	/**
	 * @return 下午3:24:51
	 * @author zhangyh2 APP.java TODO 获取XML文件件中的版本号。
	 */
	public int getVersionCode() {
		int version = 0;
		try {
			PackageInfo packageInfo = getPackageManager().getPackageInfo(
					getPackageName(), 0);
			version = packageInfo.versionCode;

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return version;
	}

	/**
	 * 
	 * 下午6:02:54
	 * 
	 * @author Zhangyuhui BaseActivity.java TODO 建立文件夹
	 */
	private void creatFile() {
		// TODO Auto-generated method stub
		File boot = new File(ConsVideo.ROOT);
		if (!boot.exists()) {
			boot.mkdir();
		}
		File radio = new File(ConsVideo.MAINRADIO);
		if (!radio.exists()) {
			radio.mkdir();
		}
		File image = new File(ConsVideo.IMAGE);
		if (!image.exists()) {
			image.mkdir();
		}
		File log = new File(ConsVideo.LOG);
		if (!log.exists()) {
			log.mkdir();
		}
	}

	/**
	 * 返回配置文件的日志开关
	 * 
	 * @return
	 */
	public boolean getLoggingSwitch() {
		try {
			ApplicationInfo appInfo = getPackageManager().getApplicationInfo(
					getPackageName(), PackageManager.GET_META_DATA);
			boolean b = appInfo.metaData.getBoolean("LOGGING");
			LogUtils.w("[ECApplication - getLogging] logging is: " + b);
			return b;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}

		return false;
	}

	public boolean getAlphaSwitch() {
		try {
			ApplicationInfo appInfo = getPackageManager().getApplicationInfo(
					getPackageName(), PackageManager.GET_META_DATA);
			boolean b = appInfo.metaData.getBoolean("ALPHA");
			LogUtils.w("[ECApplication - getAlpha] Alpha is: " + b);
			return b;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public void onLowMemory() {
		super.onLowMemory();
	}

}
