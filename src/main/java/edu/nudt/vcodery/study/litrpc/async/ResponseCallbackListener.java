package edu.nudt.vcodery.study.litrpc.async;

public interface ResponseCallbackListener {
	void onResponse(Object response);

	void onTimeout();

	void onException(Exception e);
}