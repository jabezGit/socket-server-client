/*
 * CCR 校验程序 2015/12/7
 * *******************************************************
 * C语言版本的CCR校验程序如下
 * int16_t RRC16(uint8_t *puchMsg,uint16_t usDataLEN){
	uint16_t crc_gen =0x2B2B;
	uint16_t crc;
	uint32_t i,j;
	crc = 0xffff;
	if(usDataLEN==0)
		return crc;
	for ( i = 0; i < usDataLEN; i++)
	{
		crc^=(uint16_t)(puchMsg[i]);
		//  oxffff^0x11;
		printf("异或位置的值%X\n", crc);
		for ( j = 0; j < 8; j++)
		{
			if((crc&0x01)==0x01){
				crc>>=1;
				crc^=crc_gen;
				printf("移位1\n");
			}
			else{
				crc>>=1;
				printf("移位2\n");
			}
			printf("%x\n", crc);
		}
	}
	return crc;
}
 ********************************************************
 */
package Msg;

public class crc_0 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//byte[] test = {0X00,0X01,0X00,0X02,0X0B,0X0C,(byte) 0XFF,(byte) 0XFF,(byte) 0XFF,(byte) 0XFF,(byte) 0XFF,(byte) 0XFF};
		byte[] test = {(byte) 0XFF,(byte) 0XFF,0X00,0X00,0X0B,0X06};
		 for (int j = 0; j < test.length; j++) {
         	System.out.println(test[j]);
				//out.print(test[i]);
			}
		System.out.println(Integer.toHexString(RRC16(test,test.length)));

	}
	
	// 这个片段的代码用于将有符号的数据类型转换成对应的无符号数据类型
	// 转换的思想是使用一个更高的数据类型来存储本数据，来防止数据的溢出
	public static short getUint8(short s){return (short) (s & 0x00ff);}
	public static int getUint16(int i){return i & 0x0000ffff;}
	public static long getUint32(long l){return l & 0x00000000ffffffff;}
	
	public static int RRC16(byte[] puchMsg,int usDataLen){
		int crc_gen= 0x2B2B;
		int crc = 0xffff;
		int i,j;
		if(usDataLen == 0)
			return crc;
		for(i = 0; i < usDataLen; i++){
			crc=getUint16(crc)^getUint8(puchMsg[i]);
			for(j = 0; j< 8; j++){
				if((crc& 0x01) ==0x01){
					crc>>=1;
					crc=getUint16(crc)^crc_gen;
				}
				else{
				   crc>>=1;
				}
			}
		}
		return crc;
		
	}
}
