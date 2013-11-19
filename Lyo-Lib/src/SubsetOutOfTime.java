
/*----------------------------------------------------------------
 *  Author:        Lyo Zhou
 *  Written:       2013-9-2       
 *        
 * 
 *  Purpose: % echo A B C D E F G H I | java Subset 3   
 *  				C
 *  				G
 *  				A    
 *  
 *  % echo AA BB BB BB BB BB CC CC | java Subset 8
 *                                               BB
 *                                               AA
 *                                               BB
 *                                               CC
 *                                               BB
 *                                               BB
 *                                               CC
 *                                               BB
 *  
 *----------------------------------------------------------------*/

public class SubsetOutOfTime {
	
	private int n;
	private String[] input;
	
	public SubsetOutOfTime(){
		n=0;
		input = new String[1];
	}
	
	public static void main(String[] args) {
		SubsetOutOfTime subset = new SubsetOutOfTime();
		int k = Integer.parseInt(args[0]);
		while(!StdIn.isEmpty()){
			if(subset.input.length == subset.n){
				int newSize = subset.input.length+1;
				String[] temp = new String[newSize];
		        for (int i = 0; i < subset.input.length; i++) {
		            temp[i] = subset.input[i];
		        }
		        subset.input = temp;
			}
			subset.input[subset.n] = StdIn.readString();
			subset.n++;
			
		}
		StdRandom.shuffle(subset.input);
		for(int j=0;j<k;j++){
			StdOut.println(subset.input[j]);
		}
	}
}
 