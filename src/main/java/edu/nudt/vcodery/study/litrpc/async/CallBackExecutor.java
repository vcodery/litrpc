package edu.nudt.vcodery.study.litrpc.async;

import edu.nudt.vcodery.study.litrpc.aop.ConsumerHook;
import edu.nudt.vcodery.study.litrpc.model.RpcResponseContainer;

import io.netty.handler.timeout.TimeoutException;

public class CallBackExecutor extends Thread{
	
	ResponseCallbackListener listener;
	
	RpcResponseContainer handler;
	
	ConsumerHook hook;

	public CallBackExecutor(ResponseCallbackListener listener, RpcResponseContainer handler,ConsumerHook hook) {
		super();
		this.listener = listener;
		this.handler = handler;
		this.hook = hook;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			Object result = handler.get();
			if (null != hook){
				hook.after(null);
			}
			listener.onResponse(result);
			
		} catch(TimeoutException e){
			listener.onTimeout();
			
		}catch (Throwable e) {
			// TODO Auto-generated catch block
			listener.onException((Exception) e);
		}
	}
	
}
