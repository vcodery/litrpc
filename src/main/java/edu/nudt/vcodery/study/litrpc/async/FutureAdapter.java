package edu.nudt.vcodery.study.litrpc.async;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import edu.nudt.vcodery.study.litrpc.aop.ConsumerHook;
import edu.nudt.vcodery.study.litrpc.model.RpcResponse;
import edu.nudt.vcodery.study.litrpc.model.RpcResponseContainer;

public class FutureAdapter implements Future<Object> {
	
	private RpcResponseContainer handler;
	
	private ConsumerHook hook;

	public FutureAdapter(RpcResponseContainer handler,ConsumerHook hook) {
		super();
		this.handler = handler;
		this.hook = hook;
	}
	
	public void init(RpcResponseContainer handler,ConsumerHook hook) {
		this.handler = handler;
		this.hook = hook;
	}

	public boolean cancel(boolean mayInterruptIfRunning) {
		// TODO Auto-generated method stub
		return handler.cancele(mayInterruptIfRunning);
	}

	public boolean isCancelled() {
		// TODO Auto-generated method stub
		return handler.isCanceled();
	}

	public boolean isDone() {
		// TODO Auto-generated method stub
		return handler.isDone();
	}

	public RpcResponse get() throws InterruptedException, ExecutionException {
		// TODO Auto-generated method stub
		try {
			RpcResponse result = (RpcResponse) handler.getResponse();
			if ( null != hook){
				hook.after(null);
			}
			return result;
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			throw new ExecutionException(e);
		}
	}

	public RpcResponse get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		// TODO Auto-generated method stub
		try {
			RpcResponse result = (RpcResponse) handler.getResponse();
			if ( null != hook){
				hook.after(null);
			}
			return result;
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			throw new ExecutionException(e);
		}
	}

}
