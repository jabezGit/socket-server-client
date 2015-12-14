/*
 * 这个数据类完成所有数据的解包，校验，打包数据等
 * 这里先做一个假设：就是收到的数据帧都是完好的帧（格式正确的帧）
 */
package Msg;

import java.util.Arrays;

public class msg {
	private byte[] msg={0x00};
	//定义数据格式
	int Sync_offset = 2;
	int Sync_Sour_offset = Sync_offset;
	int Sync_Dest_offset = 4;
	int Len_offset = 7;
	int data_offset =8;
	

	// 获得8位的无符号类型
	private static short getUint8(short s){return (short) (s & 0x00ff);}
	private static int getUint16(int i){return i & 0x0000ffff;}
	private static long getUint32(long l){return l & 0x00000000ffffffff;}
	
	
	public msg(byte[] msg){
		this.msg =msg;
	}
	
	
	public byte[] getMsg() {
		return msg;
	}
	
	public byte getMsg(int i){
		return msg[i];
	}
	
	/*
	 *  计算的格式如下：
	 *  1，将获得的数据强制转换成更大的数据类型
	 *  2，将数据转换成无符号类型对应的实际数据
	 *  3，因为有2个字节的头，所以偏移两位
	 */
	private int getCCR_offset(){
		return getUint8((short) getLen())+ data_offset ;
	}

	/*
	 * 这里获得的长度是指数据包里的长度位，而不是整个数据包的长度 
	 */
	private int getLen(){
		return getMsg(Len_offset);
	}

	 // 获得对应的CCR这是双字节的
	private short getCCR(){
		return (short) ((getMsg(getCCR_offset()+0) << 8) | (getMsg(getCCR_offset()+1)& 0xff));
	}

	// 获得源地址
	public short getSourAddr(){
		 return (short) ((getMsg(Sync_Sour_offset+0) << 8) | (getMsg(Sync_Sour_offset+1)& 0xff));
	}
	
	// 获得目的地址
		public short getDestAddr(){
			 return (short) ((getMsg(Sync_Dest_offset+0) << 8) | (getMsg(Sync_Dest_offset+1)& 0xff));
		}
	
	public void setMsg(byte[] msg) {
		this.msg = msg;
	}
	// 获得数据的校验
	public boolean CCR_check(){
		byte[] read_data = Arrays.copyOfRange(getMsg(), Sync_offset, getCCR_offset());
		if(crc_0.RRC16(read_data, getLen()+data_offset-Sync_offset)== getCCR())
			return true;
		else
			return false;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		 byte[] test = {(byte) 0xAA,0x44,0X00,0X01,0X00,0X02,0X15,0X06,(byte) 0XFF,(byte) 0XFF,(byte) 0XFF,(byte) 0XFF,(byte) 0XFF,(byte) 0XFF,(byte)0X3E,(byte) 0XAC};
		 msg msg =new msg(test);
		 System.out.println(msg.CCR_check());
	}
	
}
