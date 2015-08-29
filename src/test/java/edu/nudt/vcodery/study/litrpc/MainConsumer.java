package edu.nudt.vcodery.study.litrpc;

public class MainConsumer {

	private static Class<?> getConsumerImplClass() {
		try {
			return Class.forName("edu.nudt.vcodery.study.litrpc.RpcConsumer");
		} catch (ClassNotFoundException e) {
			System.out.println("Cannot found the class which must exist and override all RpcProvider's methods");
			e.printStackTrace();
			System.exit(1);
		}
		return null;
	}

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		RpcConsumer consumer = null;
		RaceTestService apiService;

		try {
			consumer = (RpcConsumer) getConsumerImplClass().newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		if (consumer == null) {
			System.out.println("Start rpc consumer failed");
			System.exit(1);
		}
		
		apiService = (RaceTestService) consumer.interfaceClass(RaceTestService.class).version("1.0.0.api")
				.clientTimeout(3000).instance();

		long start;
		String result = null;
		
		start = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			result = apiService.getString();
		}
		System.out.println("costs: " + (System.currentTimeMillis() - start));
		System.out.println("result: " + result);

		// start = System.currentTimeMillis();
		// Map<String, Object> map = apiService.getMap();
		// System.out.println("costs: " + (System.currentTimeMillis() - start));
		// System.out.println("result: " + map.size());
		//
		// start = System.currentTimeMillis();
		// RaceDO resDo = apiService.getDO();
		// System.out.println("costs: " + (System.currentTimeMillis() - start));
		// System.out.println("result: " + new RaceDO().equals(resDo));
		//
		// long beginTime = System.currentTimeMillis();
		// try {
		// boolean toResult = apiService.longTimeMethod();
		// } catch (Exception e) {
		// long period = System.currentTimeMillis() - beginTime;
		// System.out.println("result: " + period);
		// }
		//
		// start = System.currentTimeMillis();
		// result = apiService.getString();
		// System.out.println("costs: " + (System.currentTimeMillis() - start));
		// System.out.println("result: " + result);
		//
		// consumer.asynCall("getString");
		// start = System.currentTimeMillis();
		// String nullValue = apiService.getString();
		// System.out.println("costs: " + (System.currentTimeMillis() - start));
		// start = System.currentTimeMillis();
		// String unNUllresult = (String) ResponseFuture.getResponse(3000);
		// System.out.println("costs: " + (System.currentTimeMillis() - start));
		// System.out.println(null == nullValue);
		// System.out.println(unNUllresult);
		//
		// RpcContext.addProp("context", "please pass me!");
		// Map<String, Object> resultMap = apiService.getMap();
		// System.out.println(resultMap.get("context"));

	}

}
