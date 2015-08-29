package edu.nudt.vcodery.study.litrpc.model;

import java.util.Map;

import edu.nudt.vcodery.study.litrpc.conf.Configuration;
import edu.nudt.vcodery.study.litrpc.util.TimeUtil;

public class RpcRequest {

	public static final long TIME_OUT = 3000;

	private RpcRequestKey key = new RpcRequestKey();

	private RpcRequestBody body = new RpcRequestBody();

	private long timeLimit;
	
	private RpcResponse response;

	// used while request send to work thread
	// private ProviderConnection connection;

	public RpcRequest(RpcRequestKey key, RpcRequestBody body, int ticket) {
		super();
		this.key = key;
		this.body = body;
		this.response = new RpcResponse(ticket);
		this.timeLimit = TimeUtil.currentTimeMillis() + TIME_OUT;
	}
	
	public boolean isHandShakeRequest(){
		return this.body.methodId == Configuration.SHAKE_HAND_REQUEST_METHOD_ID;
	}

	public RpcRequestKey getKey() {
		return key;
	}

	public void setKey(RpcRequestKey key) {
		this.key = key;
	}

	public RpcRequestBody getBody() {
		return body;
	}

	public void setBody(RpcRequestBody body) {
		this.body = body;
	}
	
	public int getMethodID(){
		return body.methodId;
	}
	
	public Object[] getArgs(){
		return body.args;
	}
	
	public Map<String,Object> getProps(){
		return body.props;
	}

	public long getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(long timeLimit) {
		this.timeLimit = timeLimit;
	}

	public RpcResponse getResponse() {
		return response;
	}

	public void setResponse(RpcResponse response) {
		this.response = response;
	}
	
	public boolean isTimeOut(){
		return TimeUtil.currentTimeMillis() > timeLimit;
	}
	
	public boolean isDone(){
		return null != this.response;
	}

	@Override
	public String toString() {
		return "RpcRequestImpl [key=" + key + ", body=" + body + ", timeLimit=" + timeLimit + ", response=" + response
				+ "]";
	}
	
}
