package edu.nudt.vcodery.study.litrpc.io;

import edu.nudt.vcodery.study.litrpc.model.RpcResponse;

public abstract class ProviderConnection {
	
	public abstract void doResponse(RpcResponse response);

}
