package com.areong.socket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class HeartBitThread extends Thread{
	private boolean isRunning;
	private Socket socket;
	private DataOutputStream os;
	public HeartBitThread(Socket socket){
		this.socket =socket;
		isRunning=true;
	}
	
	public void run(){
		while(isRunning) {
            if (socket.isClosed()) {
            	System.out.println("心跳包结束发送");
                isRunning = false;
                break;
            }
            try {
				os = new DataOutputStream(socket.getOutputStream());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
          
				byte[] HeartBit_Pack = {(byte) 0xAA,0x44,(byte) 0Xff,(byte) 0Xff,0X00,0X01,0X0B,0X00,0x10,(byte) 0XAF};
		
				if(os!=null){
					try {
						// 报错！！
						// 解决问题：在发送之前要判断服务器socket是否空闲
						os.write(HeartBit_Pack, 0, HeartBit_Pack.length);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					try {
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}				
				}
				else {
					stopRunning();
				}    
		}
		
	}
	 public void stopRunning() {
	        isRunning = false;
	    }
}


