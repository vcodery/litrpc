package edu.nudt.vcodery.study.litrpc.model;

import java.util.HashMap;

import edu.nudt.vcodery.study.litrpc.common.RpcContext;

public class RpcRequestBody {
	
	public int methodId;
	
	public Object[] args;
	
	public HashMap<String, Object> props;

	public RpcRequestBody() {
		super();
	}
	
	public RpcRequestBody(int methodId) {
		super();
		this.methodId = methodId;
		this.props = (HashMap<String, Object>) RpcContext.props.get();
	}
	
	public RpcRequestBody init(Object[] args){
		this.args = args;
		this.props = (HashMap<String, Object>) RpcContext.props.get();
		return this;
	}

}
