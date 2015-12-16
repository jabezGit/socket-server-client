package com.areong.socket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SendMsgThread extends Thread{
	private Socket socket;
	private DataOutputStream os;
	private byte[] MsgToSend;
	private int MsgToSendLen;
	
	public SendMsgThread(Socket socket,byte[] msg,int Len){
		this.socket =socket;
		this.MsgToSend=msg;
		this.MsgToSendLen=Len;
	}
	
	public void run(){
            try {
				os = new DataOutputStream(socket.getOutputStream());
			} catch (IOException e1) {
				System.out.println("发送数据失败");
			}
            	if(os!=null){
					try {	
						// 报警告!!
						if(MsgToSendLen!=0){
							os.write(MsgToSend, 0, MsgToSendLen);
						}
					} catch (IOException e) {
						System.out.println("发送数据失败");
					}	
				}
				// 这里如果执行了os.close; 那么这个socket连接就被释放了。所以这里不需要关闭	    	
	}
}
