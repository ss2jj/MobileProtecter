package com.xj.mobileprotecter.utils;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpDownload {
	
	/*
	 * ���߳�����
	 * @param url ���ص�ַ
	 * @param filePath �����ַ
	 */
	public static void down(String url,String filePath)	{
		int threadCount = 3;
		long blockSize;
		try {
			URL requestUrl = new URL(url);
			HttpURLConnection connect = (HttpURLConnection) requestUrl.openConnection();
			connect.setRequestMethod("GET");
			connect.setConnectTimeout(5000);
			//connect.setReadTimeout(1000);
			int status = connect.getResponseCode();
			if(status == 200)	{
				long length =  connect.getContentLength();
				System.out.println("�ļ���С��"+length);
				blockSize = length/threadCount;
				//�����հ��ļ�
				File file = new File(filePath);
				RandomAccessFile raf = new RandomAccessFile(file, "rw");
				raf.setLength(length);
				for(int i =1;i <= threadCount;i++)	{
					System.out.println("�����̣߳�"+i);
					long startIndex = 0;
					if(i == 1) startIndex = 1;
					else startIndex = (i-1)*blockSize;
					long endIndex = i*blockSize-1;
					if(i == threadCount)	{
						endIndex = length;
					}
					System.out.println("��ʼλ�ã�"+startIndex+" ����λ�ã�"+endIndex);
					new Thread(new DownloadThread(i, startIndex, endIndex, url,filePath)).start();
				}
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	private static class DownloadThread implements Runnable	{
		private int threadId;
		private long startIndex;
		private long endIndex;
		private String url;
		private String filePath;
		public DownloadThread(int threadId, long startIndex, long endIndex,String url,String filePath) {
			this.threadId = threadId;
			this.startIndex = startIndex;
			this.endIndex = endIndex;
			this.url = url;
			this.filePath = filePath;
		}
		
		public void run() {
			// TODO Auto-generated method stub
			try {
				URL requesturl = new URL(url);
				HttpURLConnection connect = (HttpURLConnection) requesturl.openConnection();
				connect.setRequestMethod("GET");
				connect.setRequestProperty("Range", "bytes="+startIndex+"-"+endIndex);
				connect.setConnectTimeout(5000);
				//connect.setReadTimeout(5000);
				int status = connect.getResponseCode();
				InputStream is = connect.getInputStream();
				File file = new File(filePath);
				RandomAccessFile raf = new RandomAccessFile(file, "rw");
				raf.seek(startIndex);
				System.out.println("��" + threadId + "���̣߳�д�ļ��Ŀ�ʼλ�ã�"
						+ String.valueOf(startIndex));
				int len = 0;
				byte []buf = new byte[1024];
				while((len=is.read(buf)) != -1)	{
					raf.write(buf, 0, len);
				}
				
				is.close();
				raf.close();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
}
