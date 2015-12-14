package com.areong.socket.example;

import java.util.Arrays;

import com.areong.socket.MessageHandler;
import com.areong.socket.SocketServer;

public class ServiceDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 SocketServer server = new SocketServer(2222, new byte[512]);
	        System.out.println("Server starts.");

	}

}
