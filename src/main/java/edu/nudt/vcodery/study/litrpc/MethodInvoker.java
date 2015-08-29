package edu.nudt.vcodery.study.litrpc;

import edu.nudt.vcodery.study.litrpc.aop.ConsumerHook;
import edu.nudt.vcodery.study.litrpc.async.CallBackExecutor;
import edu.nudt.vcodery.study.litrpc.async.FutureAdapter;
import edu.nudt.vcodery.study.litrpc.async.ResponseCallbackListener;
import edu.nudt.vcodery.study.litrpc.async.ResponseFuture;
import edu.nudt.vcodery.study.litrpc.conf.Configuration;
import edu.nudt.vcodery.study.litrpc.model.RpcRequestBody;
import edu.nudt.vcodery.study.litrpc.model.RpcRequestKey;
import edu.nudt.vcodery.study.litrpc.model.RpcResponseContainer;
import edu.nudt.vcodery.study.litrpc.serializer.Serializer;
import edu.nudt.vcodery.study.litrpc.serializer.SerializerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;

public class MethodInvoker {

	private Serializer serializer = SerializerFactory.getInstace();

	//////////////////////////////////////////////
	///////// buffer with key
	/////////////////////////////////////////////
	public static final int BUFFER_SIZE = 1024;
	
	private static byte[] headBytes;
	
	public static void headInfo(String interfaceName, String version) {
		ByteBuf buf = PooledByteBufAllocator.DEFAULT.directBuffer(BUFFER_SIZE);
		new RpcRequestKey(interfaceName, version).writeTo(buf);
		headBytes = new byte[buf.readableBytes()];
		buf.readBytes(headBytes);
	}
	
	public static ByteBuf buildShakeHandRequest(){
		ByteBuf buffer = PooledByteBufAllocator.DEFAULT.directBuffer(BUFFER_SIZE);
		buffer.writeBytes(headBytes);
		// build body
		RpcRequestBody body = new RpcRequestBody(Configuration.SHAKE_HAND_REQUEST_METHOD_ID);
		byte[] bodyBytes = SerializerFactory.getInstace().buildObject(body);
		buffer.writeInt(bodyBytes.length);
		buffer.writeBytes(bodyBytes);
		
		return buffer;
	}
//	
//	public static final ThreadLocal<ByteBuf> BUFFER = new ThreadLocal<ByteBuf>() {
//
//		protected ByteBuf initialValue() {
//			ByteBuf buffer = Unpooled.directBuffer(BUFFER_SIZE);
////			buffer.markReaderIndex();
////			buffer.writeBytes(headBytes);
////			buffer.markWriterIndex();
//			return buffer;
//		}
//
//		@Override
//		public ByteBuf get() {
//			// TODO Auto-generated method stub
//			ByteBuf buffer = super.get();
//			buffer.clear();
//			buffer.writeBytes(headBytes);
////			buffer.resetReaderIndex();
////			buffer.resetWriterIndex();
//			return buffer;
//		}
//		
//		public void set(ByteBuf value) {
//			value.markReaderIndex();
//			value.writeBytes(headBytes);
//			value.markWriterIndex();
//			super.set(value);
//		}
//	};

	private RpcRequestBody body;
	
	Object[] objs = new Object[0];

	public ByteBuf build(Object[] args) {
//		if (null == args){
//			args = objs;
//		}
		
		ByteBuf buffer = PooledByteBufAllocator.DEFAULT.directBuffer(BUFFER_SIZE);
		buffer.writeBytes(headBytes);
		// build body
		byte[] bodyBytes = serializer.buildObject(body.init(args));
		buffer.writeInt(bodyBytes.length);
		buffer.writeBytes(bodyBytes);
		
		return buffer;
	}

	//////////////////////////////////////////////
	///////// basic info
	/////////////////////////////////////////////
	public final String methodName;

	public MethodInvoker(String methodName, int id) {
		super();
		// TODO Auto-generated constructor stub
		this.methodName = methodName;
		this.body = new RpcRequestBody(id);
	}

	//////////////////////////////////////////////
	///////// process response
	/////////////////////////////////////////////
	public boolean futureEnable;

	private ResponseCallbackListener callBackListener = null;

	public void enableFuture() {
		this.futureEnable = true;
	}

	public void enableCallBack(ResponseCallbackListener callBackListener) {
		this.callBackListener = callBackListener;
	}

	public void cancelAsyn() {
		this.futureEnable = false;
		this.callBackListener = null;
	}

	public Object doResponse(RpcResponseContainer container, ConsumerHook hook) throws Throwable {
		if (futureEnable) {
			FutureAdapter fa = new FutureAdapter(container, hook);
			ResponseFuture.futureThreadLocal.set(fa);
			return null;

		} else if (null != callBackListener) {
			new CallBackExecutor(callBackListener, container, hook).start();
			return null;

		} else {
			Object result = container.get();
			if (null != hook) {
				hook.after(null);
			}
			return result;
		}
	}

}
