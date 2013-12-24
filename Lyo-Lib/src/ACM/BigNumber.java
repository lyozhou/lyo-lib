package ACM;

import java.io.BufferedInputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class BigNumber {
	public static void main(String[] args){
		Scanner cin = new Scanner(new BufferedInputStream(System.in));
		ArrayList<BigInteger> list = new ArrayList<BigInteger>();
		list.add(new BigInteger("1"));
		list.add(new BigInteger("3"));
		list.add(new BigInteger("6"));
		while(cin.hasNext()){
			int num = cin.nextInt();
			int len = list.size();
			if(num>len){
				for(int i=len;i<num;i++){
					list.add(list.get(i-1).add(list.get(i-2)).subtract(list.get(i-3)));
				}
			}
			System.out.println(list.get(num-1));
		}
	}
}
