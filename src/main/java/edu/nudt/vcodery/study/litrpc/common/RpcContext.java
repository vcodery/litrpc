package edu.nudt.vcodery.study.litrpc.common;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class RpcContext {
	// TODO
	public static ThreadLocal<Map<String, Object>> props = new ThreadLocal<Map<String, Object>>() {
		protected java.util.Map<String, Object> initialValue() {
			return new HashMap<String, Object>();
		};
	};

	public static void addProp(String key, Object value) {
		props.get().put(key, value);
	}

	public static Object getProp(String key) {
		return props.get().get(key);
	}

	public static Map<String, Object> getProps() {
		return Collections.unmodifiableMap(props.get());
	}
}