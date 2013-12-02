package ACM;

import java.io.BufferedInputStream;
import java.util.Scanner;

/*----------------------------------------------------------------
 *  Author:        Lyo Zhou
 *  Written:       2013Äê12ÔÂ1ÈÕ       
 *        
 * 
 *  Purpose: 
 *  
 *
 *----------------------------------------------------------------*/

public class AngryBird {
	public static void main(String[] args) {
		Scanner cin = new Scanner(new BufferedInputStream(System.in));
		int caseNum = cin.nextInt();
		for(int i=0;i<caseNum;i++){
			int result = 0;
			String flyMile = cin.next();
			int pigNum = Integer.parseInt(cin.next());
			for(int k=0;k<pigNum;k++){
				int pigPos = Integer.parseInt(cin.next());
				if(Integer.parseInt(flyMile) < pigPos){
					result++;
				}
			}
			System.out.println("Case "+i+1+": "+result);
		}
	}
}
 