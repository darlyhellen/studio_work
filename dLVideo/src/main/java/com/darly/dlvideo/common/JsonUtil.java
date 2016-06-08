/**
 * 下午4:53:43
 * @author zhangyh2
 * $
 * JsonUtil.java
 * TODO
 */
package com.darly.dlvideo.common;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.annotation.SuppressLint;

/**
 * @author zhangyh2 JsonUtil $ 下午4:53:43 TODO
 * 
 *         将对象变为JSON数据的类
 */
@SuppressLint({ "SimpleDateFormat", "DefaultLocale" })
public class JsonUtil {

	@SuppressLint("DefaultLocale")
	private static String transform(String str) {
		if (str == null || str.length() < 1) {
			return "";
		}
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

	/**
	 * 将POJO对象转换成JSON格式的字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String pojo2Json(Object obj) {
		if (obj == null) {
			return "";
		}
		@SuppressWarnings("rawtypes")
		Class clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		StringBuilder buff = new StringBuilder();
		buff.append("{");
		for (Field f : fields) {
			try {
				String methodName = "get" + transform(f.getName());
				Method me = obj.getClass().getDeclaredMethod(methodName);
				Object value = me.invoke(obj, null, null);
				if (f.getName().equals("data")) {
					if (value != null) {
						buff.append("\"" + "data" + "\":");
						if (value instanceof ArrayList<?>) {
							ArrayList<?> v = (ArrayList<?>) value;
							buff.append("[");
							int size = v.size();
							for (Object object : v) {
								buff.append(pojo2Json(object));
								buff.append(",");
							}
							if (size > 0) {
								buff.delete(buff.length() - 1, buff.length());
							}
							buff.append("]");
						} else {
							buff.append(pojo2Json(value));
						}
						buff.append(",");
					}

				} else if (f.getName().equals("menu")) {
					if (value != null) {
						buff.append("\"" + "menu" + "\":");
						if (value instanceof ArrayList<?>) {
							ArrayList<?> v = (ArrayList<?>) value;
							buff.append("[");
							int size = v.size();
							for (Object object : v) {
								buff.append(pojo2Json(object));
								buff.append(",");
							}
							if (size > 0) {
								buff.delete(buff.length() - 1, buff.length());
							}
							buff.append("]");
						} else {
							buff.append(pojo2Json(value));
						}
						buff.append(",");
					}

				} else if (f.getName().equals("showinfo")) {
					if (value != null) {
						buff.append("\"" + "showinfo" + "\":");
						if (value instanceof ArrayList<?>) {
							ArrayList<?> v = (ArrayList<?>) value;
							buff.append("[");
							int size = v.size();
							for (Object object : v) {
								buff.append(pojo2Json(object));
								buff.append(",");
							}
							if (size > 0) {
								buff.delete(buff.length() - 1, buff.length());
							}
							buff.append("]");
						} else {
							buff.append(pojo2Json(value));
						}
						buff.append(",");
					}

				} else {
					buff.append("\"");
					buff.append(f.getName());
					buff.append("\":");
					if (value == null) {
						buff.append("\"\",");
						continue;
					}
					if (value instanceof Boolean) {
						buff.append(value);
						buff.append(",");
					} else if (value instanceof Number) {
						buff.append(value);
						buff.append(",");
					} else if (value instanceof Date) {
						buff.append("\"");
						buff.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
								.format((Date) value));
						buff.append("\",");
					} else {
						buff.append("\"");
						buff.append(value.toString());
						buff.append("\",");
					}
				}
			} catch (SecurityException e) {
				// e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// e.printStackTrace();
			} catch (IllegalAccessException e) {
				// e.printStackTrace();
			} catch (InvocationTargetException e) {
				// e.printStackTrace();
			}

		}
		if (buff.length() > 1) {
			buff = buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("}");
		return buff.toString();
	}
}
