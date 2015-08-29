package edu.nudt.vcodery.study.litrpc.model;

import java.util.Arrays;

import edu.nudt.vcodery.study.litrpc.util.StringUtil;

import io.netty.buffer.ByteBuf;

public class RpcRequestKey implements Comparable<RpcRequestKey> {

	private int[] keys;

	public RpcRequestKey() {
		super();
	}

	public RpcRequestKey(String interfaceName, String version) {
		super();
		this.keys = StringUtil.StringArray2IntArray(interfaceName, version);
	}

	public void writeTo(ByteBuf dst) {
		dst.writeInt(keys.length);
		for (int key : keys) {
			dst.writeInt(key);
		}
	}

	public void readFrom(ByteBuf src) {
		int size = src.readInt();
		keys = new int[size];
		for (int i = 0; i < size; i++) {
			keys[i] = src.readInt();
		}
	}

	// public String getMethodName(Map<Integer, String> key2Name) {
	// return null == methodName ? key2Name.get(methodKey) : methodName;
	// }

	public int compareTo(RpcRequestKey o) {
		// TODO Auto-generated method stub
		int result;
		for (int i = Math.min(keys.length, o.keys.length) - 1; i > -1; i--) {
			if (0 != (result = keys[i] - o.keys[i])) {
				return result;
			}
		}
		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		try {
			RpcRequestKey osk = (RpcRequestKey) obj;
			if (keys.length != osk.keys.length) {
				return false;
			}
			for (int i = keys.length -1; i > -1; i--){
				if (keys[i] != osk.keys[i]) {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public String toString() {
		return "RpcRequestKey [keys=" + Arrays.toString(keys) + "]";
	}

}
