package edu.nudt.vcodery.study.litrpc.util;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class StringUtil {

	public static final Charset UTF_8 = Charset.forName("UTF-8");

	public static final int EOF = -1;

	/**
	 * write String to dstBuffer as UTF-8
	 */
	public static void writeUTF8(String src, ByteBuffer dst) {
		if (null == src) {
			dst.putInt(EOF);
			return;
		}
		byte[] b = src.getBytes(UTF_8);
		dst.putInt(b.length);
		dst.put(b);
	}

	/**
	 * read String from srcBuffer as UTF-8
	 */
	public static String readUTF8(ByteBuffer src) {
		int size = src.getInt();
		if (EOF == size) {
			return null;
		}
		byte[] b = new byte[size];
		src.get(b);
		return new String(b, UTF_8);
	}

	/**
	 * converse: String[] ==> byte[] ==> int[]
	 */
	public static int[] StringArray2IntArray(String... srcs) {
		ByteBuffer buf = ByteBuffer.allocate(1024);
		int len = 0;
		byte[] b = null;
		for (String src : srcs) {
			b = src.getBytes(UTF_8);
			len += b.length;
			buf.put(b);
		}
		if (0 != len % 4) {
			len = len / 4 + 1;
			buf.putInt(0);
		} else {
			len = len / 4;
		}

		int[] dst = new int[len];
		buf.flip();
		for (int i = 0; i < len; i++) {
			dst[i] = buf.getInt();
		}
		return dst;
	}

}
