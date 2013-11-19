package DividedMerge;

import java.math.BigInteger;
import java.util.Random;

/*----------------------------------------------------------------
 *  Author:        Lyo Zhou
 *  Written:       2013年9月6日       
 *        
 * 
 *  Purpose: Divide and Conquer based method for multiplication.
 *  
 *
 *----------------------------------------------------------------*/


public class BigIntMuti {
	
	
	/********************************************  
	 * @Title: fastMulti  
	 * @Description: 
	 * a*b = (A1*2^n + A2)*(B12^n + B2) 
     *     = (A1*B1)*2^(2n) + (A1*B2 + A2*B1)*2^n + A2*B2
     *     = (A1*B1)*(2^(2n)+2^n) + (A1-A2)*(B2-B1)*2^n + (A2*B2)*(2^n+1)
     *     = (A1*B1)<<(2n) + (A1*B2)<<n + (A1-A2)*(B2-B1)<<n
     *      + (A2*B2)<<n + (A2*B2)
	 *
	 * @param x  number a
	 * @param y  number b
	 * @return a*b
	 * @return BigInteger
	 * @throws  
	*********************************************/
	public BigInteger fastMulti(BigInteger x, BigInteger y){
		int n = Math.max(x.bitLength(), y.bitLength());
		if ( n <= 3000 ) // so might as well multiply the usual way
		    return x.multiply(y);
		BigInteger a, b, c, d;
		n = (n+1)/2;  // exactly half when n was originally even.
		// Imagine, If length is even, just add a 0 at the beginning of number
		a = x.shiftRight(n);
		b = subtract(x,a.shiftLeft(n)); //eg:x=1010, a=10, b = 1010-1000, suitable for hex or bin
		c = y.shiftRight(n);
		d = subtract(y,c.shiftLeft(n));
		BigInteger M1,M2,M3;
		M1 = fastMulti(a,c);
		M2 = fastMulti(b,d);
		M3 = fastMulti(a.subtract(b),d.subtract(c));
		return M1.shiftLeft(2*n).add(M1.shiftLeft(n)).add(M2.shiftLeft(n).add(M3.shiftLeft(n)).add(M2));
	}
	
	public BigInteger subtract(BigInteger a, BigInteger b){
		return a.subtract(b);
	}
	
	public static void main(String[] args){
		BigIntMuti bigMulti = new BigIntMuti();
		Random r = new Random(); // generates pseudo-random FBI's
		long fixedSeed = r.nextLong();
		BigInteger a, b; 
		long t0, simpleTime, fastTime;
		for(int l=1; l <= 10000000; l*=2){ // restrict bit length is 10000000
			// using BigInteger.multiply 
			r.setSeed(fixedSeed);
		    System.gc();  //garbage collect to get more reliable results
		    t0 = System.currentTimeMillis();
		    a = new BigInteger(l,r);
			b = new BigInteger(l,r);
		    BigInteger simpleResult = a.multiply(b);
		    simpleTime = System.currentTimeMillis()-t0;
		    
		    // using divide and conquer multiply
		    r.setSeed(fixedSeed);
		    System.gc();  //garbage collect to get more reliable results
		    t0 = System.currentTimeMillis();
		    a = new BigInteger(l,r);
			b = new BigInteger(l,r);
			BigInteger fastResult = bigMulti.fastMulti(a,b);
		    fastTime = System.currentTimeMillis()-t0;
		    if(simpleResult.equals(fastResult))
		    System.out.println(l + " bit==> " + "simpleTime = " + simpleTime + " fastTime = " + fastTime);
		}
		
		
	}
}
