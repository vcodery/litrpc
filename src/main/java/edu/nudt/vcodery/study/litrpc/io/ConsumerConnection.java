package edu.nudt.vcodery.study.litrpc.io;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import edu.nudt.vcodery.study.litrpc.model.RpcResponseContainer;

import io.netty.buffer.ByteBuf;

public abstract class ConsumerConnection {

	public static final int TIME_OUT = 4 * 60;// 50s

	// waiting response with ticket
	protected AtomicInteger ticketCreater = new AtomicInteger(0);
	
	protected Map<Integer, RpcResponseContainer> waitingResponse = new HashMap<Integer, RpcResponseContainer>();
	

	/**
	 * connect to host:port
	 */
	public abstract void connectTo(String host, int port) throws IOException;

	/**
	 * <p>
	 * buffer is expected as : <br/>
	 * +---------+-------------+----------+------+--------+<br/>
	 * | keySize | service key | bodySize | body | ticket |<br/>
	 * +---------+-------------+----------+------+--------+<br/>
	 * </p>
	 * <p>
	 * The connection will add a ticket in the end to check response, and the
	 * checked response will be put into ResponseContainer while received.
	 * </p>
	 */
	public abstract RpcResponseContainer send(ByteBuf buffer);

	/**
	 * Active close connection
	 */
	public abstract void close();

}
