package edu.nudt.vcodery.study.litrpc.conf;

public class Configuration {

	public static final String SIP = "127.0.0.1";

	public static final int PORT = 8888;

	public static final String SERIALIZER_INSTANCE = "edu.nudt.vcodery.study.litrpc.serializer.ProtostuffSerializer";

	public static final String CONSUMER_CONNECTION_INSTANCE = "edu.nudt.vcodery.study.litrpc.transfer.netty.ConsumerConnectionNettyImpl";

	public static final String PROVIDER_CONNECTION_INSTANCE = "edu.nudt.vcodery.study.litrpc.transfer.netty.ProviderConnectionsNettyImpl";
	
	public static final int SHAKE_HAND_REQUEST_METHOD_ID = Integer.MIN_VALUE;
	
	public static final int CONNECTION_ACTIVE_TIMEOUT = 16 * 60;
}
