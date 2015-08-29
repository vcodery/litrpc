package edu.nudt.vcodery.study.litrpc.serializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Serializer {
	
	public <T> byte[] buildObject(T message);
	
	public <T> byte[] buildObject(Class<T> clazz, Object message);
	
	public <T> void parseObject(T message, byte[] data);
	
	public <T> void parseObject(Class<? extends T> clazz, Object message, byte[] data);
	
	public <T> int writeObject(T message, OutputStream out) throws IOException;
	
	public <T> int writeObject(Class<T> clazz, Object message, OutputStream out) throws IOException;
	
	public <T> void readObject(T message, InputStream in) throws IOException;
	
	public <T> void readObject(Class<T> clazz, Object message, InputStream in) throws IOException;
	
	public void preIdentify(Class<?>... clazzs);
}
