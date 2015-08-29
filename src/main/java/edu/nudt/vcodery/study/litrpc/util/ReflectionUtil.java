package edu.nudt.vcodery.study.litrpc.util;

import java.lang.reflect.Method;
import java.util.concurrent.RecursiveAction;

import edu.nudt.vcodery.study.litrpc.RpcConsumer;

public class ReflectionUtil {
	
	public static class ConsumerReflectionTask extends RecursiveAction{
		
		private RpcConsumer consumer;
		private Method[] methods;
		private int from;
		private int to;
		
		public ConsumerReflectionTask(RpcConsumer consumer) {
			super();
			this.consumer = consumer;
			this.methods = consumer.getInterfaceClazz().getDeclaredMethods();
			this.from = 0;
			this.to = methods.length;
		}

		@Override
		protected void compute() {
			// TODO Auto-generated method stub
//			if ( 1 <  )
		}
		
	}

}
