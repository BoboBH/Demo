package com.bobo.SocketSample;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SocketServer {

	public static final int DEFAULT_PORT = 55533;
	private int port;
	private ServerSocket serverSocket;
	private ExecutorService executorService;
	public SocketServer(){
		this(DEFAULT_PORT);
	}
	public SocketServer(int port){
		this.port = port;
		try {
			executorService = Executors.newFixedThreadPool(2);
			this.serverSocket = new ServerSocket(this.port);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	public void start(){
		int index  = 0;
		try{
			while(true){
				index++;
				System.out.println("Waiting client connection...");
				Socket socket = serverSocket.accept();
				System.out.println("build a connection with client and will exchange data");
				Exchange exchange = new Exchange(socket, index);
				executorService.submit(exchange);
				//Thread thread = new Thread(new Exchange(socket, index));
				//thread.start();
				
			}
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		try {
			this.serverSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
