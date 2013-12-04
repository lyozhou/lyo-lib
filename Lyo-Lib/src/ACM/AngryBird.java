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
			int result = 1;
			int basePoint = 0;
			int flyMile = Integer.parseInt(cin.next());
			int pigNum = Integer.parseInt(cin.next());
			int[] pigPos = new int[pigNum];
			for(int k=0;k<pigNum;k++){
				pigPos[k] = Integer.parseInt(cin.next());
			}
			for(int k=1;k<pigPos.length;k++){
				if(pigPos[k]- pigPos[basePoint]>flyMile){
					basePoint = k;
					result++;
				}
			}
			System.out.println("Case "+ (i+1)+": "+result);
		}
	}
}
 