package com.bobo.SocketSample;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientSocket {

	private int port;
	private String host;
	private Socket socket;
	public ClientSocket(String host, int port){
		this.host = host;
		this.port = port;
	}
	
	private void ReceiveMessage(){
		
		if(socket == null)
			return;
		try {

			int len = 0;
			byte[] buff = new byte[1024];
			InputStream inputStream = socket.getInputStream();
			String receivedMsg = null;
			while((len = inputStream.read(buff))> 0){
				receivedMsg = new String(buff,0,len);
				System.out.println("Receive message : " + receivedMsg);	
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void Start(){
		try {
			socket = new Socket(host, port);
			Runnable t1 = new Runnable() {
				
				public void run() {
					ReceiveMessage();
					
				}
			};
			Thread thread = new Thread(t1);
			thread.start();
			OutputStream outputStream = socket.getOutputStream();
			//InputStream inputStream = socket.getInputStream();
			byte[] buff = new byte[1024];
			for(int i = 0;i <= 10; i++){
				String msg = "Item:" + i;
				if(i == 10)
					msg = "exit!";
				System.out.println("will send message:" + msg);
				outputStream.write(msg.getBytes("UTF-8"));
				//outputStream.flush();
				/*System.out.println("Sent message succesfully");
				int len = inputStream.read(buff);
				String receiveMsg =null;
				if(len > 0){
					System.out.println("receive " + new String(buff,"UTF-8").trim()  +  " succesfully");
					receiveMsg = new String(buff,"UTF-8").trim();
				}
				if(!msg.equals(receiveMsg))
					System.out.println("Sent Msg does not equals received msg");*/
				
				try {
					Thread.sleep(1000 * 2);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			outputStream.close();
			socket.close();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
