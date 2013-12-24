package ACM;

import java.util.Scanner;

public class SelectNumbr1011 {
	private static int n,k;
	private static int[] result;
	private static int[] number;
	private static int primeNum;
	public static void main(String[] args) {
		Scanner cin = new Scanner(System.in);
		while (cin.hasNext()){
			n = cin.nextInt();
			k = cin.nextInt();
			number = new int[n];
			for(int i=0;i<n;i++){
				number[i] = cin.nextInt();
			}
			result = new int[k];
			primeNum = 0;
			dfs(0,0);
			//System.out.println(primeNum);
						
		}
	}
	
	private static void dfs(int depth, int width){
		System.out.println("d and w= "+depth+" " + width);
		if(width>n || (n-width+depth) <k){
			return;
		}
		if(depth==k){
			int sum = 0;
			for(int i=0;i<k-1;i++){
				System.out.print(result[i]+" ");
			}
			System.out.println(result[k-1]);
			if(isPrime(sum)) primeNum++;
			return;
		}
		result[depth] = number[width];
		dfs(depth+1,width+1);
		dfs(depth,width+1);
	}
	
	private static boolean isPrime(int num){
		for(int i=2;i<Math.sqrt(num);i++){
			if(num%i==0) return false;
		}
		return true;
	}

}
