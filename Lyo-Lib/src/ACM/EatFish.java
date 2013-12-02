package ACM;

import java.io.BufferedInputStream;
import java.util.Arrays;
import java.util.HashMap;
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

public class EatFish {
	public static void main(String[] args) {
		Scanner cin = new Scanner(new BufferedInputStream(System.in));
		int caseNum = cin.nextInt();
		for (int i = 0; i < caseNum; i++) {
			HashMap<String, Integer> wordCount = new HashMap<String, Integer>();
			String word = cin.next();
			for (int j = 0; j < word.length(); j++) {
				String a = word.substring(j, j + 1);
				if (wordCount.containsKey(a)) {
					int num = wordCount.get(a) + 1;
					wordCount.put(a, num);
				} else {
					wordCount.put(a, 1);
				}
			}
			int min = Integer.MAX_VALUE;
			if (!(wordCount.containsKey("F") & wordCount.containsKey("I")
					& wordCount.containsKey("S") & wordCount.containsKey("H"))) {
				min = 0;
			} else {
				if (wordCount.get("F") < min) {
					min = wordCount.get("F");
				}
				if (wordCount.get("I") < min) {
					min = wordCount.get("I");
				}
				if (wordCount.get("S") < min) {
					min = wordCount.get("S");
				}
				if (wordCount.get("H") < min) {
					min = wordCount.get("H");
				}
			}

			System.out.println("Case " + (i + 1) + ":" + min);
		}
	}
}
