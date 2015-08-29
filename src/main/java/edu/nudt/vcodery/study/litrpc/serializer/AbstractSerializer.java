package edu.nudt.vcodery.study.litrpc.serializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class AbstractSerializer implements Serializer{

	public <T> byte[] buildObject(T message) {
		// TODO Auto-generated method stub
		return buildObject(message.getClass(), message);
	}

	public <T> void parseObject(T message, byte[] data) {
		// TODO Auto-generated method stub
		parseObject(message.getClass(), message, data);
	}

	public <T> int writeObject(T message, OutputStream out) throws IOException {
		// TODO Auto-generated method stub
		return writeObject(message.getClass(), message, out);
	}

	public <T> void readObject(T message, InputStream in) throws IOException {
		// TODO Auto-generated method stub
		readObject(message.getClass(),  message, in);
	}
	
	public void preIdentify(Class<?>... clazzs) {
		// TODO Auto-generated method stub	
	}
	
}
