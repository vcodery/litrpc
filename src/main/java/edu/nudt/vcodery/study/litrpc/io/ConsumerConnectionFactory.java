package edu.nudt.vcodery.study.litrpc.io;

import java.io.IOException;
import java.util.IdentityHashMap;
import java.util.Map;

import edu.nudt.vcodery.study.litrpc.conf.Configuration;

public class ConsumerConnectionFactory {

	private static Map<String, ConsumerConnection> connections = new IdentityHashMap<String, ConsumerConnection>();
	static {
		connections.put(Configuration.SIP, createConection(Configuration.SIP));
	}
	
	private static ConsumerConnection createConection(String host) {
		ConsumerConnection conn = null;
		try {
			conn = (ConsumerConnection) Class.forName(Configuration.CONSUMER_CONNECTION_INSTANCE).newInstance();
			conn.connectTo(host, Configuration.PORT);
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (null != conn) {
			return conn;
		} else {
			System.out.println(
					"Failed while create Consumer connection to addr:" + host + " with instance: " + Configuration.CONSUMER_CONNECTION_INSTANCE);
			System.out.println("RPC is going to exit...");
			System.exit(1);
			return null;
		}
	}

	public static ConsumerConnection getConnection(String host, boolean newConection) {
		
		ConsumerConnection connection = null;
		if (newConection || null == (connection = connections.get(host))) {
			connection = createConection(host);
			connections.put(host, connection);
		}
		return connection;
	}

}
