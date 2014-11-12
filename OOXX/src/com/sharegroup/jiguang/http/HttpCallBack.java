package com.sharegroup.jiguang.http;

public abstract interface HttpCallBack<T> {
	public final static int SUCCESS = 0;
	public final static int FAIL = 1;
	public final static int START = 2;

	public abstract void onStartRequest();

	public abstract  void onSuccess(T paramObject);

	public abstract void onFailure(Exception paramException);
}