我利用过去一周的业余时间写了一个简单的RPC程序，该程序
	支持异步调用，提供future、callback的能力；
	提供RPC上下文，客户端可以通过RPCContext将数据传递给服务端；
	提供Hook，让开发人员进行RPC层面的AOP；
	并且该程序在服务端处理时间较长时提供了超时机制。
同时，程序中充分运用了
	Java反射机制（reflectasm）,
	Java动态代理机制,
	Netty,
	Java 序列化机制（protostuff）等等。
经测试，通过该RPC程序调用远程服务并返回一个字符串操作的平均相应时间为0.52ms。

1. 将litrpc文件夹中的maven工程导入eclipse；

2. 运行src/test/java下的edu.nudt.vcodery.study.litrpc.MainProvider.java启动服务。

3. 运行src/test/java下的edu.nudt.vcodery.study.litrpc.MainConsumer.java进行服务调用。

4. 示例程序包括：

       示例协议：
           edu.nudt.vcodery.study.litrpc.TestService.java

       示例协议实现
	   edu.nudt.vcodery.study.litrpc.TestServiceImpl.java

       示例服务提供者
           edu.nudt.vcodery.study.litrpc.MainProvider.java

       示例服务消费者
           edu.nudt.vcodery.study.litrpc.MainConsumer.java