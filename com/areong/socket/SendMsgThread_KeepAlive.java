package com.areong.socket;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import Msg.Client_table_item;

public class SendMsgThread_KeepAlive extends Thread{
	private List<Client_table_item> table = new ArrayList<Client_table_item>();
	private DataOutputStream os;
	private boolean isRunning;
	
	public SendMsgThread_KeepAlive(List<Client_table_item> table){
		this.table = table;
		isRunning = true;
	}
	
	public void run(){
		System.out.println("发送线程启动");
		while(isRunning){
//			System.out.println("table.size()"+table.size());
			// 这里需要加延时，才能正常工作，可能是多个线程访问数据
			try {
				Thread.sleep(1);
			} catch (InterruptedException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			for(int n = 0; n < table.size(); n++){
				// 只有在有数据并且Socket没有关闭的情况下，才发送数据
				if(table.get(n).getData()!=null&&!table.get(n).getSocket().isClosed()){
					// 这里可以没必要一直的创建线程，可以创建一个用于发送的就可以了
					 try {
							os = new DataOutputStream(table.get(n).getSocket().getOutputStream());
						} catch (IOException e1) {
							System.out.println("发送数据失败");
						}
			            	if(os!=null){
								try {	
									if(table.get(n).getDataLen()!=0){
										os.write(table.get(n).getData(), 0, table.get(n).getDataLen());
									}
								} catch (IOException e) {
									System.out.println("发送数据失败");
								}	
							}
//					System.out.println(">>>>>>>>>>>>发送数据");
					table.get(n).setData(null);
				}
			}
		}
	}
}
