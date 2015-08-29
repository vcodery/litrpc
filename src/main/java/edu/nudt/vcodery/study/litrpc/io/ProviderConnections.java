package edu.nudt.vcodery.study.litrpc.io;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import edu.nudt.vcodery.study.litrpc.RpcProvider;
import edu.nudt.vcodery.study.litrpc.conf.Configuration;

public abstract class ProviderConnections{
	
	protected AtomicBoolean isRunning = new AtomicBoolean(true);
	
	private static final ProviderConnections INSTANCE = createInstance();

	private static ProviderConnections createInstance() {
		ProviderConnections connections = null;
		try {
			connections = (ProviderConnections) Class.forName(Configuration.PROVIDER_CONNECTION_INSTANCE).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (null != connections){
			try {
				connections.startUpOn(Configuration.SIP, Configuration.PORT);
				return connections;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			System.out.println("Failed while create ProviderConnections instance:" + Configuration.PROVIDER_CONNECTION_INSTANCE);
			System.out.println("Provider is going to exit...");
			System.exit(2);
		}
		return null;
	}

	public static ProviderConnections getInstance() {
		return INSTANCE;
	}

	protected RpcProvider provider;

	public ProviderConnections publishService(RpcProvider provider) {
		this.provider = provider;
		return this;
	}

	public abstract void startUpOn(String host, int port) throws IOException;

	public abstract void shutDown() throws IOException;

}
