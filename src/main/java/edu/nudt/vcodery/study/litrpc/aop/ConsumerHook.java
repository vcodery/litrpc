package edu.nudt.vcodery.study.litrpc.aop;

import edu.nudt.vcodery.study.litrpc.model.RpcRequest;

public interface ConsumerHook {
	public void before(RpcRequest request);

	public void after(RpcRequest request);
}