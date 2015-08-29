package edu.nudt.vcodery.study.litrpc.util;

public class TimeUtil {

	public static long currentTimeMillis = System.currentTimeMillis();

	static {
		new Ticker().start();
	}

	public static long currentTimeMillis() {
		return currentTimeMillis;
	}

	public static class Ticker extends Thread {

		public static final int TICK_INTERVAL = 32;// ms

		public Ticker() {
			super();
			setDaemon(true);
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				try {
					Thread.sleep(TICK_INTERVAL);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				currentTimeMillis = System.currentTimeMillis();
			}
		}
	}

}
