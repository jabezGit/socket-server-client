package Msg;

import java.net.Socket;

public class Client_table_item {
	private short DestNum;
	private Socket socket;
	private byte[] Data;
	private int DataLen;
	
	
	public Client_table_item() {
		this.DestNum=-1;
		this.socket =null;
		this.Data =null;
		this.DataLen=0;
	}
	public Client_table_item(short sourAddr) {
		this.DestNum=sourAddr;
		this.socket =null;
		this.Data =null;
		this.DataLen=0;
	}
	public short getDestNum() {
		return DestNum;
	}
	public void setDestNum(short destNum) {
		DestNum = destNum;
	}
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	public byte[] getData() {
		return Data;
	}
	public void setData(byte[] data) {
		Data = data;
	}
	public int getDataLen() {
		return DataLen;
	}
	public void setDataLen(int dataLen) {
		DataLen = dataLen;
	}
	

}
