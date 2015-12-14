package com.areong.socket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SendMsgThread extends Thread{
	private boolean isRunning;
	private Socket socket;
	private DataOutputStream os;
	private byte[] MsgToSend;
	private int MsgToSendLen;
	
	public SendMsgThread(Socket socket,byte[] msg,int Len){
		this.socket =socket;
		this.MsgToSend=msg;
		this.MsgToSendLen=Len;
		isRunning=true;
	}
	
	public void run(){
            try {
				os = new DataOutputStream(socket.getOutputStream());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
            	if(os!=null){
					try {	
						// 报警告!!
						if(MsgToSendLen!=0){
							os.write(MsgToSend, 0, MsgToSendLen);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}	
				}
				stopRunning();	    	
	}
	 public void stopRunning() {
	        isRunning = false;
	    }
}
