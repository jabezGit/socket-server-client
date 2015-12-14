package com.areong.socket.example;

import java.io.IOException;
import java.net.InetAddress;

import com.areong.socket.SocketClient;

public class ClientDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException{
		SocketClient client = new SocketClient(InetAddress.getLocalHost(), 2222);
        client.println("Hello!");
        //System.out.println(client.readLine());

	}

}
