package edu.nudt.vcodery.study.litrpc.model;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class RpcResponseContainer {

	public static final int RESPONSE_HEAD_LEN = 4 * 2;
	
	private CountDownLatch countDownLatch = new CountDownLatch(1);

	private RpcResponse response;
	
	private boolean isDone = false;
	private boolean isCanceled = false;

	public void put(RpcResponse response) {
		this.response = response;
		this.isDone = true;
		countDownLatch.countDown();
	}

	public Object get() throws TimeoutException, Throwable {
		countDownLatch.await(3, TimeUnit.SECONDS);

		if (isDone) {
			if (response.isError()) {
				throw new Exception(response.getErrorMsg());
			}
			return response.getAppResponse();
		} else {
			throw new TimeoutException("");
		}
	}
	
	public RpcResponse getResponse() throws InterruptedException, TimeoutException{
		countDownLatch.await(3, TimeUnit.SECONDS);
		if (isDone) {
			return response;
		} else{
			throw new TimeoutException("");
		}
	}

	public boolean isDone() {
		return isDone;
	}

	public boolean isCanceled() {
		return isCanceled;
	}

	public boolean cancele(boolean interrupt) {
		this.isCanceled = true;
		if (interrupt){
			// do interrupt
		}
		return true;
	}

}
