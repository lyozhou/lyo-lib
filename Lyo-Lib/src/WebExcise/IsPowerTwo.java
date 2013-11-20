package WebExcise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/*----------------------------------------------------------------
 *  Author:        Lyo Zhou
 *  Written:       2013年11月20日       
 *        
 * 
 *  Purpose: 
 *  if a number is power two, and get x of 2^x = Num
 *
 *----------------------------------------------------------------*/

public class IsPowerTwo {
	public static int log2(int value){
		if(value==1){
			return 0;
		}else{
			return 1+ log2(value>>1);
		}
	}
	
	public static void main(String[] args){
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in)); 
		System.out.println("输入第一个数："); 
		try {
			int a=Integer.parseInt(br.readLine());
			if((a&(a-1))!=0){  // if power 2 then first is 1, rest is 0
				System.out.printf("%d is not power 2",a);
			}else{
				System.out.printf("%d is power 2 by %d",a,log2(a));
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
 