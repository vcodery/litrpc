package edu.nudt.vcodery.study.litrpc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CountDownLatch;

import edu.nudt.vcodery.study.litrpc.RaceDO;
import edu.nudt.vcodery.study.litrpc.aop.ConsumerHook;
import edu.nudt.vcodery.study.litrpc.async.ResponseCallbackListener;
import edu.nudt.vcodery.study.litrpc.conf.Configuration;
import edu.nudt.vcodery.study.litrpc.io.ConsumerConnection;
import edu.nudt.vcodery.study.litrpc.io.ConsumerConnectionFactory;
import edu.nudt.vcodery.study.litrpc.model.RpcResponseContainer;
import edu.nudt.vcodery.study.litrpc.serializer.SerializerFactory;
import io.netty.buffer.ByteBuf;

public class RpcConsumer implements InvocationHandler{

	private Class<?> interfaceClazz;
	private String version;

	private Map<Method, MethodInvoker> methodHandlers = new ConcurrentSkipListMap<Method, MethodInvoker>(
			new Comparator<Method>() {

				public int compare(Method o1, Method o2) {
					// TODO Auto-generated method stub
					if (o1.equals(o2))
						return 0;
					else {
						return o1.hashCode() - o2.hashCode();
					}
				}
			});
	private ConsumerConnection connection;

	private int timeout;
	private ConsumerHook hook;

	public final CountDownLatch proxyBuildControler = new CountDownLatch(1);

	public Class<?> getInterfaceClazz() {
		return interfaceClazz;
	}

	public String getVersion() {
		return version;
	}

	public Map<Method, MethodInvoker> getMethodHandlers() {
		return methodHandlers;
	}

	public int getTimeout() {
		return timeout;
	}

	// request buffer
	public static final int BUFFER_SIZE = 32 * 1024;

	public static final int PORT = 8888;

	public RpcConsumer() {
		super();
		// TODO Auto-generated constructor stub
		this.connection = ConsumerConnectionFactory.getConnection(Configuration.SIP, false);
	}

	public RpcConsumer interfaceClass(Class<?> interfaceClass) {
		// TODO Auto-generated method stub
		this.interfaceClazz = interfaceClass;
		return this;
	}

	@SuppressWarnings("unchecked")
	public RpcConsumer version(String version) {
		// TODO Auto-generated method stub
		this.version = version;

		String iName = this.interfaceClazz.getName();
		MethodInvoker.headInfo(iName, version);

		RpcResponseContainer container = this.connection.send(MethodInvoker.buildShakeHandRequest());
		try {
			Method[] methods = this.interfaceClazz.getDeclaredMethods();
			HashMap<String, Integer> methodIds = (HashMap<String, Integer>) container.get();
			for (Method method : methods) {
				String mName = method.getName();
				Integer id = methodIds.get(mName);
				if (null != id){
					this.methodHandlers.put(method, new MethodInvoker(mName, id));
				}
			}
			try{
				SerializerFactory.getInstace().preIdentify(String.class);
				SerializerFactory.getInstace().preIdentify(HashMap.class);
				SerializerFactory.getInstace().preIdentify(RaceDO.class);
			} catch (Exception e){
				e.printStackTrace();
			}
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return this;
	}

	public RpcConsumer clientTimeout(int clientTimeout) {
		// TODO Auto-generated method stub
		this.timeout = clientTimeout;
		return this;
	}

	public RpcConsumer hook(ConsumerHook hook) {
		// TODO Auto-generated method stub
		this.hook = hook;
		return this;
	}

	public Object instance() {
		// TODO Auto-generated method stub
		return Proxy.newProxyInstance(interfaceClazz.getClassLoader(), new Class<?>[] { interfaceClazz }, this);
	}

	public void asynCall(String methodName) {
		// TODO Auto-generated method stub

		for (MethodInvoker handler : methodHandlers.values()) {
			if (handler.methodName.equals(methodName)) {
				handler.enableFuture();
				return;
			}
		}
	}

	public <T extends ResponseCallbackListener> void asynCall(String methodName, T callbackListener) {
		// TODO Auto-generated method stub

		for (MethodInvoker handler : methodHandlers.values()) {
			if (handler.methodName.equals(methodName)) {
				handler.enableCallBack(callbackListener);
				return;
			}
		}
	}

	public void cancelAsyn(String methodName) {
		// TODO Auto-generated method stub

		for (MethodInvoker handler : methodHandlers.values()) {
			if (handler.methodName.equals(methodName)) {
				handler.cancelAsyn();
				return;
			}
		}
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub

		if (null != hook) {
			hook.before(null);
		}
		MethodInvoker handler = methodHandlers.get(method);
		if (null == handler) {
			System.out.println("null");
			System.exit(0);
		}

		ByteBuf requestBuffer = handler.build(args);
		RpcResponseContainer response = connection.send(requestBuffer);
		return handler.doResponse(response, hook);
	}

}
