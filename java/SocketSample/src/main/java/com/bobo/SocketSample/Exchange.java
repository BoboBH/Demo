package com.bobo.SocketSample;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


public class Exchange implements Runnable {
	private Socket socket;

	private int seqNo;
	public Exchange(Socket socket, int seqNo) {
		this.socket = socket;
		this.seqNo = seqNo;
	}

	public void run() {
		try {
			while (true) {
				InputStream inputStream = socket.getInputStream();
				OutputStream outputStream = socket.getOutputStream();
				byte[] buff = new byte[1024];
				int len = 0;
				StringBuilder sb = new StringBuilder();
				while ((len = inputStream.read(buff)) != -1) {
					if(len > 0){
						sb.append(new String(buff, 0, len, "UTF-8"));
						System.out.println("length of message is " + String.valueOf(len) + "; Msg is " +  new String(buff,0,len, "UTF-8"));
						outputStream.write(buff,0,len);
					}
				}
				String data = sb.toString();
				//System.out.println("Server socket " + String.valueOf(seqNo) + "get message from client:" + sb);
				inputStream.close();
				if (data != null && data.startsWith("exit")) {
					break;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			this.socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
