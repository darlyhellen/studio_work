/**
 * 上午11:40:12
 * @author zhangyh2
 * $
 * BaseModelPaser.java
 * TODO
 */
package com.darly.dlvideo.bean;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.util.LogUtils;

/**
 * @author zhangyh2 BaseModelPaser $ 上午11:40:12 TODO
 *         基础类的解析器，由于不能使用多重反射进行解析。故而通过解析外层，之后直接解析内层获取对象内容。
 */
public class BaseModelPaser<T> {

	/**
	 * 
	 * 上午11:56:54
	 * 
	 * @author zhangyh2 BaseModelPaser.java TODO
	 */
	public BaseModelPaser() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param json
	 * @param token
	 * @return 下午2:22:16
	 * @author zhangyh2 BaseModelPaser.java TODO 使用一个基础类解析全部服务器返回的JSON
	 */
	public BaseModel<T> paserJson(String json, TypeToken<T> token) {
		if (json == null) {
			return null;
		} else {
			try {
				JSONObject jsonObject = new JSONObject(json);
				int code = jsonObject.getInt("code");
				String msg = jsonObject.getString("msg");
				if (code == 200) {
					String data = null;
					try {
						data = jsonObject.getString("data");
					} catch (Exception e) {
						// TODO: handle exception
						data = null;
					}
					if (data !=null) {
						T t = new Gson().fromJson(data, token.getType());
						return new BaseModel<T>(code, msg, t);
					}else {
						return new BaseModel<T>(code, msg, null);
					}
				} else {
					return new BaseModel<T>(code, msg, null);
				}

			} catch (Exception e) {
				// TODO: handle exception
				LogUtils.i("解析出错");
				e.printStackTrace();
			}

		}
		LogUtils.i("其他情况");
		return null;
	}
}
