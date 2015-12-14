package com.areong.socket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import Msg.Client_table_item;
import Msg.msg;

public class Mes_Manage extends Thread{
	private boolean isRunning;
	private List<ConnectionThread> connections = new ArrayList<ConnectionThread>();
	private List<Client_table_item> table = new ArrayList<Client_table_item>();
	
	
	public Mes_Manage(List<ConnectionThread> connections){
		this.connections = connections;
		isRunning=true;
	}
	
	private int iscontains(short addr){
		// 遍历列表的话，就不能从0开始了
		for (int j = 0; j < this.table.size(); j++) {
			if(this.table.get(j).getDestNum()==addr)
				return j;
		}
		return -1;
	}
	
	public void run(){
		while(isRunning) {
			// 这里要加入延迟，否则工作就不正常
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 先检查是否有客户端断开了，如果断开了，就将它从列表中删除
			for (int i = 0; i < connections.size(); i++) {
				if(connections.get(i).getSocket().isClosed()){
					connections.remove(i);
				}
			}
			
			for (int i = 0; i < table.size(); i++) {
				if(table.get(i).getSocket().isClosed()){
					table.remove(i);
				}
			}
			// 第一：遍历表获得最新的连接数据		
			for (int i = 0; i < connections.size(); i++) {
				if(connections.get(i).getData() != null){
					msg original_data = new msg(connections.get(i).getData());
					// 这里会出错
					if(original_data.CCR_check()){
						// 判读队列中是否为空，或者队列中没有源地址
						if(this.table.isEmpty()||(iscontains(original_data.getSourAddr())== -1)){
						// 将地址加入到List中	
							Client_table_item item = new Client_table_item();
							// 将地址加入到
							item.setDestNum(original_data.getSourAddr());
							item.setSocket(connections.get(i).getSocket());		
							this.table.add(item);
						}
					}
					else{
						System.out.println(Arrays.toString(connections.get(i).getData()));
					}
				}
			}
			//表处理前 
			// 2:获得目的地址并向其添加数据	
			for (int j = 0; j < connections.size(); j++) {
				if(connections.get(j).getData()!=null){
					msg original_data2=new msg(connections.get(j).getData());
					// 如果获得的数据CCR没有错
					if(original_data2.CCR_check()){
						// 判读队列中是否为空，或者队列中有要发送的目的地址
						if(!table.isEmpty()){
						// 循环遍历列表查找目的地址
								// 找到了目的地址
								int SourAddr=iscontains(original_data2.getDestAddr());
								if(SourAddr!= -1){
									//将数据加到这个列表元素中
									// 目前的数据加到源目的地址上去			
									table.get(SourAddr).setData(connections.get(j).getData());
									//将数据的长度加到这个列表元素中
									table.get(SourAddr).setDataLen(connections.get(j).getDateLen());
								}		
						}
					}
				}
			}
			// 3：遍历列表，向链表里每一个元素都创建一个发送线程
				for(int n = 0; n < table.size(); n++){
					// 只有在有数据并且Socket没有关闭的情况下，才发送数据
					if(table.get(n).getData()!=null&&!table.get(n).getSocket().isClosed()){
						new SendMsgThread(table.get(n).getSocket(), table.get(n).getData(), table.get(n).getDataLen()).start();	
						table.get(n).setData(null);
					}
				}			
			// 4：清空列表，等待下一次数据接受	
				for (int i = 0; i < connections.size(); i++) {
					connections.get(i).setDataLen(0);
					connections.get(i).setMessage(null);
				}	
		}
		
	}
	 public void stopRunning() {
	        isRunning = false;
	    }
}
