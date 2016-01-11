package com.xj.mobileprotecter.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamTools {

	/*
	 * ��ȡ��������������������ת��string����
	 */
	public static String readFromStream(InputStream is) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int length = 0;

		while (is != null && (length = is.read(buf)) != -1) {
			bos.write(buf, 0, length);
		}
		is.close();
		bos.flush();
		String result = bos.toString();
		bos.close();
		return result;
	}
}
