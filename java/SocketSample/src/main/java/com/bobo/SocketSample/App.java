package com.bobo.SocketSample;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	//new SocketServer().start();
        System.out.println( "Hello World!" );
        if(args.length > 0 && "server".equals(args[0])){
        	new SocketServer().start();
        }
        else
        	new ClientSocket("localhost", SocketServer.DEFAULT_PORT).Start();
    }
}
