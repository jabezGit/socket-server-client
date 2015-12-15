package com.areong.socket.example;

import com.areong.socket.SocketServer;

public class ServiceDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 SocketServer server = new SocketServer(2222);
	        System.out.println("Server starts.");

	}

}
