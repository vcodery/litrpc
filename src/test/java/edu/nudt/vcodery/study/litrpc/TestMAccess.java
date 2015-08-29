package edu.nudt.vcodery.study.litrpc;

import com.esotericsoftware.reflectasm.MethodAccess;

public class TestMAccess {

	public void fun1() {

	}

	public String fun2(String a) {
		return a;
	}

	public static void main(String[] args) {
		
		TestMAccess obj = new TestMAccess();
		
		MethodAccess access = MethodAccess.get(TestMAccess.class);
		
		System.out.println(access.invoke(obj, 1, "hello"));
	}

}
