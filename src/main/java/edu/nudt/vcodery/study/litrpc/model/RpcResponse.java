package edu.nudt.vcodery.study.litrpc.model;

import java.io.Serializable;

import edu.nudt.vcodery.study.litrpc.RaceDO;

public class RpcResponse implements Serializable{

	static private final long serialVersionUID = -4364536436151723421L;

	private int ticket;

	public RpcResponse() {
		super();
	}

	public RpcResponse(int ticket) {
		super();
		this.setTicket(ticket);
	}

	public int getTicket() {
		return ticket;
	}

	public void setTicket(int ticket) {
		this.ticket = ticket;
	}

	private String errorMsg;

	private Object appResponse;

	public RpcResponse buildSuccess(Object result) {
		this.appResponse = result;
		return this;
	}

	public RpcResponse buildException(Throwable result) {
		this.errorMsg = result.getMessage();
		return this;
	}

	public RpcResponse buildException(String cause) {
		this.errorMsg = cause;
		return this;
	}

	public Object getAppResponse() {
		// It is bad here for class RaceDO did not provide empty Constructor!!!
		// RaceDO() provides default value in the list, and ProtoStuff
		// Serializer add the received value into the list.
		// we just deleted the old data and keep the new ones.
		if (RaceDO.class == appResponse.getClass()) {
			((RaceDO) appResponse).getList().remove(0);
		}
		return appResponse;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public boolean isError() {
		return errorMsg == null ? false : true;
	}

	@Override
	public String toString() {
		return "RpcResponseImpl [ticket=" + ticket + ", errorMsg=" + errorMsg + ", appResponse=" + appResponse + "]";
	}

}