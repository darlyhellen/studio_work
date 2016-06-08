/**
 * 上午11:38:28
 * @author zhangyh2
 * $
 * BaseModel.java
 * TODO
 */
package com.darly.dlvideo.bean;

/**
 * @author zhangyh2 BaseModel $ 上午11:38:28 TODO 所有请求的基础类。
 */
public class BaseModel<T> {

	private int code;

	private String msg;

	private T data;

	public BaseModel(int code, String msg, T data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
