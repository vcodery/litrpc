package edu.nudt.vcodery.study.litrpc;

import java.lang.reflect.Method;
import java.util.HashMap;

import com.esotericsoftware.reflectasm.MethodAccess;

import edu.nudt.vcodery.study.litrpc.RaceDO;
import edu.nudt.vcodery.study.litrpc.common.RpcContext;
import edu.nudt.vcodery.study.litrpc.io.ProviderConnections;
import edu.nudt.vcodery.study.litrpc.model.RpcRequest;
import edu.nudt.vcodery.study.litrpc.model.RpcRequestKey;
import edu.nudt.vcodery.study.litrpc.model.RpcResponse;
import edu.nudt.vcodery.study.litrpc.serializer.SerializerFactory;

public class RpcProvider {

	private Class<?> interfaceClass;
	private String version;

	private RpcRequestKey key;
	private MethodAccess mAccess;
	private Object serviceInstance;
	private HashMap<String, Integer> methodAccessInfo;

	private int timeOut;

	private String serializerType;

	public RpcProvider serviceInterface(Class<?> serviceInterface) {
		// TODO Auto-generated method stub
		interfaceClass = serviceInterface;
		return this;
	}

	public RpcProvider version(String version) {
		// TODO Auto-generated method stub
		this.version = version;
		this.key = new RpcRequestKey(interfaceClass.getName(), this.version);
		return this;
	}

	public RpcProvider impl(Object serviceInstance) {
		// TODO Auto-generated method stub
		this.serviceInstance = serviceInstance;
		
		mAccess = MethodAccess.get(serviceInstance.getClass());
		this.methodAccessInfo = new HashMap<String, Integer>();
		Method[] methods = interfaceClass.getDeclaredMethods();
		String name;
		for (Method method : methods){
			name = method.getName();
			methodAccessInfo.put(name, mAccess.getIndex(name));
		}
		SerializerFactory.getInstace().preIdentify(String.class);
		SerializerFactory.getInstace().preIdentify(RaceDO.class);
		return this;
	}

	public RpcProvider timeout(int timeout) {
		// TODO Auto-generated method stub
		this.timeOut = timeout;
		return this;
	}

	public RpcProvider serializeType(String serializeType) {
		// TODO Auto-generated method stub
		this.serializerType = serializeType;
		return this;
	}

	public void publish() {
		// TODO Auto-generated method stub
		ProviderConnections.getInstance().publishService(this);
	}

	public RpcResponse handle(RpcRequest request) {
		// check key
		if (this.key.equals(request.getKey())) {
			if ( request.isHandShakeRequest()){
				return request.getResponse().buildSuccess(methodAccessInfo);
				
			} else {				
				RpcContext.props.set(request.getProps());
				try {
					Object obj = mAccess.invoke(serviceInstance, request.getMethodID(), request.getArgs());
					return request.getResponse().buildSuccess(obj);
				} catch (Exception e) {
					return request.getResponse().buildException(e);
				}
			}
		} else {
			return request.getResponse().buildException("Service not found!");
		}
	}

	public int getTimeOut() {
		return timeOut;
	}

}
