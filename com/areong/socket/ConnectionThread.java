package com.areong.socket;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

class ConnectionThread extends Thread {
    private Socket socket;
    private SocketServer socketServer;
    private Connection connection;
    private DataInputStream is;
    private DataOutputStream os;
    private BufferedInputStream br;
    private boolean isRunning;
    
    volatile private byte[] message;
    volatile private int dataLen;

    public ConnectionThread(Socket socket, SocketServer socketServer) {
        this.socket = socket;
        // 开辟开个线程：一个是用来转发，一个用于不断的发送心跳包
        this.socketServer = socketServer;
        connection = new Connection(socket);
        new HeartBitThread(socket).start();
        isRunning = true;
    }
    

    @Override
    public void run() {
    	byte[] encry_byte = new byte[512];  
		int read_length = 0;
        while(isRunning) {
            // Check whether the socket is closed.
            if (socket.isClosed()) {
            	System.out.println("客户端 自动断开了");
                isRunning = false;
                break;
            }
            try {
	                is = new DataInputStream(socket.getInputStream());
	                os = new DataOutputStream(socket.getOutputStream());
	    			br = new BufferedInputStream(is);
	    			while(isRunning){
	    				read_length = br.read(encry_byte);
	    				// 判读客户端是否断开了
	    				if(read_length== -1){
	    					stopRunning();
	    					break;
	    				}
	    				this.message = encry_byte;
	    				this.dataLen = read_length;	    
	        		}
		    			if(is!=null)
		    				is.close();
		    			if(os!=null)
		    				os.close();
		    			if(br!=null)
		    				br.close();
            } catch (IOException e) {
            	stopRunning();
                System.out.println("连接超时，关闭与客户端连接");
            }
        }
    }
    
    
    public void setMessage(byte[] message) {
		this.message = message;
	}


	public void setDataLen(int dataLen) {
		this.dataLen = dataLen;
	}
    
    public byte[] getData(){
    	return this.message;
    }
    
    public int getDateLen(){
    	return this.dataLen;
    }
    
    public Socket getSocket(){
    	return this.socket;
    }
    
    public void stopRunning() {
    	System.out.println("客户端断开了");
        isRunning = false;
        try {
            socket.close();
        } catch (IOException e) {
            System.out.println("客户端未正常断开连接");
        }
    }
}