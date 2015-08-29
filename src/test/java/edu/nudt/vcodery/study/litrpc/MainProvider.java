package edu.nudt.vcodery.study.litrpc;

public class MainProvider {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RpcProvider rpcProvider = null;
        try {
            rpcProvider = (RpcProvider) getProviderImplClass().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        if(rpcProvider == null){
            System.out.println("Start Rpc Provider failed.");
            System.exit(1);
        }

        rpcProvider.serviceInterface(RaceTestService.class)
                .impl(new RaceTestServiceImpl())
                .version("1.0.0.api")
                .timeout(3000)
                .serializeType("java").publish();

        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            // ignore
        }

	}
	
	 private static Class<?> getProviderImplClass(){
	        try {
	            return Class.forName("edu.nudt.vcodery.study.litrpc.RpcProvider");
	        } catch (ClassNotFoundException e) {
	            System.out.println("Cannot found the class which must exist and override all RpcProvider's methods");
	            e.printStackTrace();
	            System.exit(1);
	        }
	        return null;
	    }

}
