
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

public class Subset {
	
	public static void main(String[] args) {
		int k=Integer.parseInt(args[0]);        
        String [] input=new String[k];
        for(int i=0;i<k;i++){            
            input [i] =StdIn.readString(); 
        }
        StdRandom.shuffle(input);
        for(String s:input){
            StdOut.println(s);
        }
	}
}
 