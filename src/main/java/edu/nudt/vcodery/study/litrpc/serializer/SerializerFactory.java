package edu.nudt.vcodery.study.litrpc.serializer;

import edu.nudt.vcodery.study.litrpc.conf.Configuration;

public class SerializerFactory {

	private static Serializer INSTANCE;

	static {
		try {
			INSTANCE = (Serializer) Class
					.forName(Configuration.SERIALIZER_INSTANCE).newInstance();
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
		if ( null == INSTANCE){
			System.out.println("Failed while get Serializer， System is going to exit!");
			System.exit(1);
		}
	}

	public static Serializer getInstace() {
		return INSTANCE;
	}

}
