package DividedMerge;

/*----------------------------------------------------------------
 *  Author:        Lyo Zhou
 *  Written:       2013年9月8日       
 *        
 * 
 *  Purpose: N elements array, 1<k<n, Swap [0,k-1] vs [k+1,n-1] subArray
 *  
 *
 *----------------------------------------------------------------*/

public class SwapSubArray<T> {
	public static void main(String[] args){
		SwapSubArray<Integer> swap = new SwapSubArray();
		Integer[] array = {1,2,3,4,5,6};
		swap.swapSubArray(array,4);
		System.out.println("lyo");
		for(int i=0;i<array.length;i++)
			System.out.print(array[i]+" ");
	}
	
	public void swapSubArray(T[] array, int k){
		int n = array.length;
		//right shift k equals to left shift -k
		for(int i=0,cyc=gcd(k,n-k);i<cyc;i++){
			T tmp = array[i];
			int p = i, j = (k+i)%n;
			while(j!=i){
				array[p] = array[j];
				p = j;
				j = (k+p)%n;
			}			
			array[p] = tmp;
		}
	}
	
	public Integer gcd(int x, int y){
		if(x==0) return y;
		if(y==0) return x;
		if(x>y) return gcd(x%y,y);
		else return gcd(x,y%x);
	}
}
