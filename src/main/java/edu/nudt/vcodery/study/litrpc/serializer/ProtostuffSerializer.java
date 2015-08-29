package edu.nudt.vcodery.study.litrpc.serializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

import edu.nudt.vcodery.study.litrpc.util.GenerationMap;

/**
 * ProtostuffSerializer based on Protostuff.
 */
public class ProtostuffSerializer extends AbstractSerializer {

	public ProtostuffSerializer() {
		super();
	}

	/**
	 * Schema cache
	 */
	private Map<Class<?>, Schema<?>> knownSchemas = new GenerationMap<Class<?>, Schema<?>>(64);

	private Schema<?> getSchema(Class<?> clazz) {
		Schema<?> schema = null;
		if (null == (schema = knownSchemas.get(clazz))) {
			schema = RuntimeSchema.getSchema(clazz);
			knownSchemas.put(clazz, schema);
		}
		return schema;
	}
	
	@Override
	public void preIdentify(Class<?>... clazzs) {
		// TODO Auto-generated method stub
		for (Class<?> clazz : clazzs){
			knownSchemas.put(clazz, RuntimeSchema.getSchema(clazz));
		}
	}

	public static int BUFFER_SIZE = 4096;

	/**
	 * LinkedBuffer cache
	 */
	private static ThreadLocal<LinkedBuffer> BUFFERS = new ThreadLocal<LinkedBuffer>() {
		protected LinkedBuffer initialValue() {
			return LinkedBuffer.allocate(BUFFER_SIZE);
		}

		@Override
		public LinkedBuffer get() {
			// TODO Auto-generated method stub
			return super.get().clear();
		};
		
	};

	/**
	 * Serialize util
	 */
	@SuppressWarnings("unchecked")
	public <T> byte[] buildObject(Class<T> clazz, Object message) {
		if (null == message) {
			return null;
		}
		Schema<T> schema = (Schema<T>) getSchema(clazz);
		return ProtostuffIOUtil.toByteArray((T) message, schema, BUFFERS.get());
	}

	@SuppressWarnings("unchecked")
	public <T> void parseObject(Class<? extends T> clazz, Object message, byte[] data) {
		if (null == clazz || null == message) {
			return;
		}
		Schema<T> schema = (Schema<T>) getSchema(clazz);
		ProtostuffIOUtil.mergeFrom(data, (T) message, schema);
	}

	@SuppressWarnings("unchecked")
	public <T> int writeObject(Class<T> clazz, Object message, OutputStream out) throws IOException {
		if (null == clazz || null == message) {
			return -1;
		}
		Schema<T> schema = (Schema<T>) getSchema(clazz);
		return ProtostuffIOUtil.writeDelimitedTo(out, (T) message, schema, BUFFERS.get());
	}

	@SuppressWarnings("unchecked")
	public <T> void readObject(Class<T> clazz, Object message, InputStream in) throws IOException {
		if (null == clazz || null == message) {
			return;
		}

		Schema<T> schema = (Schema<T>) getSchema(clazz);
		ProtostuffIOUtil.mergeDelimitedFrom(in, (T) message, schema, BUFFERS.get());
	}

}
