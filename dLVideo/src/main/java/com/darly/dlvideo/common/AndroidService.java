/**上午10:04:04
 * @author zhangyh2
 * AndroidService.java
 * TODO
 */
package com.darly.dlvideo.common;

import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * @author zhangyh2 AndroidService 上午10:04:04 TODO 获取设备信息的一些基本方法
 */
public class AndroidService {

	/**
	 * 默认的IMSI号
	 */
	private static final String IMSI_DEFAULT = "460000000000000";
	private static final String IMEI_DEFAULT = "000000000000000";

	/**
	 * 提供短信发送功能. 短信将自动判断内容长度，切分短信发送.
	 * 
	 * @param telNum
	 *            目标短信接收者号码
	 * @param message
	 *            短信内容.
	 */
	public static void sendSms(String telNum, String message) {

		if (message != null) {
			// 移动运营商允许每次发送的字节数据有限，我们可以使用Android给我们提供 的短信工具。
			SmsManager smsMgr = SmsManager.getDefault();
			// 如果短信没有超过限制长度，则返回一个长度的List。
			List<String> texts = smsMgr.divideMessage(message);
			for (String text : texts) {
				smsMgr.sendTextMessage(telNum, null, text, null, null);
				Log.d("SMSSender", "send a message");
			}
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * 检测网络连接是否是cmwap 如果是的话需要设置代理
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isWap(Context context) {
		// ConnectivityManager主要管理和网络连接相关的操作
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo nInfo = cm.getActiveNetworkInfo();
		if (nInfo == null || nInfo.getType() != ConnectivityManager.TYPE_MOBILE)
			return false;
		String extraInfo = nInfo.getExtraInfo();
		if (extraInfo == null || extraInfo.length() < 3)
			return false;
		if (extraInfo.toLowerCase(Locale.getDefault()).contains("wap"))
			return true;
		return false;
	}

	/**
	 * 获取Imsi号
	 * 
	 * @param context
	 * @return
	 */
	public static String getIMSI(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String imsi = telephonyManager.getSubscriberId();
		if (imsi == null || imsi.equals("")) {
			imsi = IMSI_DEFAULT;
		}
		return imsi;
	}

	/**
	 * 获取Imei号
	 * 
	 * @param context
	 * @return
	 */
	public static String getIMEI(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = telephonyManager.getDeviceId();
		if (imei == null || imei.equals("")) {
			imei = IMEI_DEFAULT;
		}
		return imei;
	}

	/**
	 * 获取手机机型
	 * 
	 * @return
	 */
	public static String getModel() {
		return Build.MODEL;
	}

	/**
	 * 判断当前手机是否开启了GPS 对应GPS_PROVIDER
	 * 
	 * @return
	 */
	public static boolean isGPSEnabled(Context context) {
		LocationManager mLocationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断手机当前的网络状况 1.是否开启WIFI 2.是否开启蜂窝数据 对应NETWORK_PROVIDER
	 * 
	 * @return
	 */
	public static boolean isNetworkEnabled(Context context) {
		return (isTelephonyEnabled(context) || isWIFIEnabled(context));
	}

	/**
	 * 判断移动网络是否开启
	 * 
	 * @return
	 */
	private static boolean isTelephonyEnabled(Context context) {
		boolean enable = false;
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		if (telephonyManager != null) {
			if (telephonyManager.getNetworkType() != TelephonyManager.NETWORK_TYPE_UNKNOWN) {
				enable = true;
			}
		}
		return enable;
	}

	/**
	 * 判断wifi是否开启
	 */
	private static boolean isWIFIEnabled(Context context) {
		boolean enable = false;
		WifiManager wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		if (wifiManager.isWifiEnabled()) {
			enable = true;
		}
		return enable;
	}
}
