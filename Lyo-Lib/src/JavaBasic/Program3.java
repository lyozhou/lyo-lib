package JavaBasic;

import java.io.UnsupportedEncodingException;

/*----------------------------------------------------------------
 *  Author:        Lyo Zhou
 *  Written:       2013年11月16日       
 *        
 * 
 *  Purpose: 
 *  
 *
 *----------------------------------------------------------------*/

public class Program3 {
	public static void main(String[] args) throws UnsupportedEncodingException{
		String str = "我A爱B中华";
		System.out.println(str.charAt(1));
		int num = trimGBK(str.getBytes("GBK"),5);  // !! change str into GBK byte
		System.out.println(str.substring(0,num) );
	}

	/********************************************  
	 * @Title: trimGBK  
	 * @Description: 
	 *
	 *
	 * @param bytes
	 * @param i
	 * @return
	 * @return int
	 * @throws  
	*********************************************/
	private static int trimGBK(byte[] buf, int n) {
		// TODO Auto-generated method stub
		int num = 0;
		boolean bChineseFirstHalf = false;
		for(int i=0;i<n;i++)
		{
			if(buf[i]<0 && !bChineseFirstHalf){  // GBK: one Chinese include 2 minus number
												// UTF-8: one Chinese include 3 minus number
				bChineseFirstHalf = true;
			}else{
				num++;
				bChineseFirstHalf = false;				
			}
		}
		return num;
	}
}
 