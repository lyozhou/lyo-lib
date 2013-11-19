package DividedMerge;

import java.math.BigInteger;
import java.util.Random;

/*----------------------------------------------------------------
 *  Author:        Lyo Zhou
 *  Written:       2013Äê9ÔÂ6ÈÕ       
 *        
 * 
 *  Purpose: 
 *  
 *
 *----------------------------------------------------------------*/

public class FastBigInteger{
    private BigInteger N;
    public static final int BREAKLENGTH = 3000;
    public static final int MAXBITLENGTH = 10000000;


    public FastBigInteger(String val){
	N = new BigInteger(val);
    }

    public FastBigInteger(BigInteger N){
	this.N = N;
    }

    /** 
     * Method that creates a pseudo-random
     * FastBigInteger of a specified length. The
     * random object is required so that the
     * results of the methods can be recreated
     */
    public FastBigInteger(int l, Random r){
	N = new BigInteger(l,r);
    }

    public String toString(){
	return N.toString();
    }

    /** Wrapper of standard addition method */
    public FastBigInteger add(FastBigInteger y){
	return new FastBigInteger(N.add(y.N));
    }

    /** Wrapper of standard subtraction method */
    public FastBigInteger subtract(FastBigInteger y){
	return new FastBigInteger(N.subtract(y.N));
    }

    /** Wrapper of standard multiplication method */
    public FastBigInteger multiply(FastBigInteger y){
	return new FastBigInteger(N.multiply(y.N));
    }

    /** Wrapper of standard left shift method */
    public FastBigInteger shiftLeft(int n){
	return new FastBigInteger(N.shiftLeft(n));
    }

    /** Wrapper of standard right shift method */
    public FastBigInteger shiftRight(int n){
	return new FastBigInteger(N.shiftRight(n));
    }

    /** Wrapper of standard negate method */
    public FastBigInteger negate(){
	return new FastBigInteger(N.negate());
    }

    /** Wrapper of standard absolute value method */
    public FastBigInteger abs(){
	return new FastBigInteger(N.abs());
    }

    /**
     * The recursive, divide and conquer
     * fast multiplication works as follows:
     * Suppose we want to multiply two binary
     * numbers a and b.  Suppose that in binary
     * we have a = [A1][A2], and b = [B1][B2]
     * where A1, A2, B1 and B2 are length-n bitstrings.
     * Then the following holds
     * a*b = (A1*2^n + A2)*(B12^n + B2) 
     *     = (A1*B1)*2^(2n) + (A1*B2 + A2*B1)*2^n + A2*B2
     *     = (A1*B1)*(2^(2n)+2^n) + (A1-A2)*(B2-B1)*2^n + (A2*B2)*(2^n+1)
     *     = (A1*B1)<<(2n) + (A1*B2)<<n + (A1-A2)*(B2-B1)<<n
     *      + (A2*B2)<<n + (A2*B2)
     * Thus the most expensive operation ---multiplication---
     * need only be computed three times:
     *    1. A1*B1
     *    2. (A1-A2)*(B2-B1)
     *    3. A2*B2
     * instead of the apparent four times evident in the first line
     * of the derivation above.
     */
    public FastBigInteger multiplyFast(FastBigInteger b){
	int n = java.lang.Math.max(N.bitLength(), b.N.bitLength());
	if ( n <= 1 ) // so might as well multiply the usual way
	    return multiply(b);
	int s1 = N.signum();
	int s2 = b.N.signum();
	if( s1 < 0 )
	    return negate().multiplyFast(b).negate();
	if( s2 < 0 )
	    return multiplyFast(b.negate()).negate();
	n = (n+1)/2;  // exactly half when n was originally even.
	FastBigInteger A1, A2, B1, B2; //"divide and conquer"
	A1 = shiftRight(n);
	A2 = subtract(A1.shiftLeft(n));
	B1 = b.shiftRight(n);
	B2 = b.subtract(B1.shiftLeft(n));
	FastBigInteger M1, M2, M3;  // the three multiplications
	M1 = A1.multiplyFast(B1);
	M2 = A1.subtract(A2).multiplyFast(B2.subtract(B1));
	M3 = A2.multiplyFast(B2);
	return M1.shiftLeft(2*n).add(M1.shiftLeft(n))
	    .add(M2.shiftLeft(n)).add(M3.shiftLeft(n).add(M3));
    }

    /** 
     * Though the previous is in principle faster than the simple 
     * method, in reality this is not the case.  To get a truly
     * faster method, we need to combine the two method, using 
     * fast multiplication only with very large integers.
     */
    public FastBigInteger multiplyFastPractically(FastBigInteger b){
	int n = Math.max(N.bitLength(), b.N.bitLength());
	/*** THE FOLLOWING IS THE ONLY REALLY DIFFERING LINE OF CODE ***/
	if ( n <= BREAKLENGTH ) // so might as well multiply the usual way
	    return multiply(b);
	int s1 = N.signum();
	int s2 = b.N.signum();
	if( s1 < 0 )
	    return negate().multiplyFastPractically(b).negate();
	if( s2 < 0 )
	    return multiplyFastPractically(b.negate()).negate();
	n = (n+1)/2;  // exactly half when n was originally even.
	FastBigInteger A1, A2, B1, B2; //"divide and conquer"
	A1 = shiftRight(n);
	A2 = subtract(A1.shiftLeft(n));
	B1 = b.shiftRight(n);
	B2 = b.subtract(B1.shiftLeft(n));
	FastBigInteger M1, M2, M3;  // the three multiplications
	M1 = A1.multiplyFastPractically(B1);
	M2 = A1.subtract(A2).multiplyFastPractically(B2.subtract(B1));
	M3 = A2.multiplyFastPractically(B2);
	return M1.shiftLeft(2*n).add(M1.shiftLeft(n))
	    .add(M2.shiftLeft(n)).add(M3.shiftLeft(n).add(M3));
    }

    static void compareSimpleWithFast(String[] args) throws Exception{
	int n = Integer.parseInt(args[0]);
	
	Random r = new Random(); // generates pseudo-random FBI's
	long fixedSeed = r.nextLong();
	
	long t0;   // start time of a given multiplication experiment
	long simpleTime;  // total time to intersect the HashSets
	long fastTime;  // total time to intersect the TreeSets
	long prevSimpleTime = 1, prevFastTime = 1; //So don't divide by 0 later
	
	FastBigInteger a, b;  //the numbers to multiply
	
	System.out.println("\n\n\n");
	System.out.println("EXPERIMENT ON PERFORMANCE OF Simple vs. Fast MULTIPLICATION");
	System.out.println("-----------------------------------------------------------\n");
	System.out.println("Number of repetitions for each size: "+n+"\n");
	System.out.println("       || avg. time   | avg. time ");
	System.out.println("# bits || simple mult | fast mult ");
	System.out.println("----------------------------------");
	for(int l=1; l <= MAXBITLENGTH; l*=2){
	    //multiply together the simple way, n times 
	    r.setSeed(fixedSeed);
	    System.gc();  //garbage collect to get more reliable results
	    t0 = System.currentTimeMillis();
	    for(int i=1; i<=n; i++){
		a = new FastBigInteger(l,r);
		b = new FastBigInteger(l,r);
		a.multiply(b);
	    }
	    simpleTime = System.currentTimeMillis()-t0;
	    
	    //multiply together the fast way, n times 
	    r.setSeed(fixedSeed);   // so repeat same FBI sequence
	    System.gc();  //garbage collect to get more reliable results
	    t0 = System.currentTimeMillis();
	    for(int i=1; i<=n; i++){
		a = new FastBigInteger(l,r);
		b = new FastBigInteger(l,r);
		a.multiplyFast(b);
	    }
	    fastTime = System.currentTimeMillis()-t0;
	    double simpleRatio, fastRatio;
	    
	    System.out.println(l+" || "+simpleTime/n+" | "+fastTime/n);
	    prevSimpleTime = simpleTime;
	    prevFastTime = fastTime;
	}// for 
    }

   static void compareSimpleWithFastPractical(String[] args) throws Exception{
	int n = Integer.parseInt(args[0]);
	
	Random r = new Random(); // generates pseudo-random FBI's
	long fixedSeed = r.nextLong();
	
	long t0;   // start time of a given multiplication experiment
	long simpleTime;  // total time to intersect the HashSets
	long fastTime;  // total time to intersect the TreeSets
	long prevSimpleTime = 1, prevFastTime = 1; //So don't divide by 0 later
	
	FastBigInteger a, b;  //the numbers to multiply
	
	System.out.println("\n\n\n");
	System.out.println("EXPERIMENT ON PERFORMANCE OF Simple vs. PRACTICAL Fast MULTIPLICATION");
	System.out.println("---------------------------------------------------------------------\n");
	System.out.println("Number of repetitions for each size: "+n+"\n");
	System.out.println("       || avg. time   | avg. time ");
	System.out.println("# bits || simple mult | fast mult ");
	System.out.println("----------------------------------");
	for(int l=1; l <= MAXBITLENGTH; l*=2){
	    //multiply together the simple way, n times 
	    r.setSeed(fixedSeed);
	    System.gc();  //garbage collect to get more reliable results
	    t0 = System.currentTimeMillis();
	    for(int i=1; i<=n; i++){
		a = new FastBigInteger(l,r);
		b = new FastBigInteger(l,r);
		a.multiply(b);
	    }
	    simpleTime = System.currentTimeMillis()-t0;
	    
	    //multiply together the fast way, n times 
	    r.setSeed(fixedSeed);   // so repeat same FBI sequence
	    System.gc();  //garbage collect to get more reliable results
	    t0 = System.currentTimeMillis();
	    for(int i=1; i<=n; i++){
		a = new FastBigInteger(l,r);
		b = new FastBigInteger(l,r);
		a.multiplyFastPractically(b);
	    }
	    fastTime = System.currentTimeMillis()-t0;
	    double simpleRatio, fastRatio;
	    
	    System.out.println(l+" || "+simpleTime/n+" | "+fastTime/n);
	    prevSimpleTime = simpleTime;
	    prevFastTime = fastTime;
	}// for 
    }


    static void testSimple(String args[]) throws Exception{
	int n = Integer.parseInt(args[0]);
	
	Random r = new Random(); // generates pseudo-random FBI's
	long fixedSeed = r.nextLong();
	
	long t0;   // start time of a given multiplication experiment
	long simpleTime;  // total time to intersect the HashSets
	long prevSimpleTime = 1;
	
	FastBigInteger a, b;  //the numbers to multiply
	
	System.out.println("\n\n\n");
	System.out.println("EXPERIMENT ON PERFORMANCE OF Simple MULTIPLICATION");
	System.out.println("--------------------------------------------------\n");
	System.out.println("Number of repetitions for each size: "+n+"\n");
	System.out.println("       || avg. time for  || ratio to || log_2(ratio) ");	
	System.out.println("# bits || multiplication || previous ||");
	System.out.println("---------------------------------------\n");
	for(int l=1; l <= MAXBITLENGTH; l*=2){
	    //multiply together the simple way, n times 
	    r.setSeed(fixedSeed);
	    System.gc();  //garbage collect to get more reliable results
	    t0 = System.currentTimeMillis();
	    for(int i=1; i<=n; i++){
		a = new FastBigInteger(l,r);
		b = new FastBigInteger(l,r);
		a.multiply(b);
	    }
	    simpleTime = System.currentTimeMillis()-t0;
	    
	    double simpleRatio;
	    
	    System.out.println(l+" || "+simpleTime/n+
			       " || "+(simpleRatio=((double)simpleTime/prevSimpleTime))+
			       " || "+(Math.log(simpleRatio)/Math.log(2)) );
	    
	    prevSimpleTime = simpleTime;
	}// for 
    }

    static void testFast(String args[]) throws Exception{
	int n = Integer.parseInt(args[0]);
	
	Random r = new Random(); // generates pseudo-random FBI's
	long fixedSeed = r.nextLong();
	
	long t0;   // start time of a given multiplication experiment
	long fastTime;  // total time to intersect the HashSets
	long prevFastTime = 1;
	
	FastBigInteger a, b;  //the numbers to multiply
	
	System.out.println("\n\n\n");
	System.out.println("EXPERIMENT ON PERFORMANCE OF Fast MULTIPLICATION");
	System.out.println("--------------------------------------------------\n");
	System.out.println("Number of repetitions for each size: "+n+"\n");
	System.out.println("       || avg. time for  || ratio to || log_2(ratio) ");	
	System.out.println("# bits || multiplication || previous ||");
	System.out.println("---------------------------------------\n");
	for(int l=1; l <= MAXBITLENGTH; l*=2){
	    //multiply together the fast way, n times 
	    System.gc();  //garbage collect to get more reliable results
	    r.setSeed(fixedSeed);
	    t0 = System.currentTimeMillis();
	    for(int i=1; i<=n; i++){
		a = new FastBigInteger(l,r);
		b = new FastBigInteger(l,r);
		a.multiplyFast(b);
	    }
	    fastTime = System.currentTimeMillis()-t0;
	    
	    double fastRatio;
	    
	    System.out.println(l+" || "+fastTime/n+
			       " || "+(fastRatio=((double)fastTime/prevFastTime))+
			       " || "+(Math.log(fastRatio)/Math.log(2)) );
	    
	    prevFastTime = fastTime;
	}// for 
    }

    static void testFastPractically(String args[]) throws Exception{
	int n = Integer.parseInt(args[0]);
	
	Random r = new Random(); // generates pseudo-random FBI's
	long fixedSeed = r.nextLong();
	
	long t0;   // start time of a given multiplication experiment
	long fastPracticallyTime;  // total time to intersect the HashSets
	long prevFastPracticallyTime = 1;
	
	FastBigInteger a, b;  //the numbers to multiply
	
	System.out.println("\n\n\n");
	System.out.println("EXPERIMENT ON PERFORMANCE OF FastPractically MULTIPLICATION");
	System.out.println("--------------------------------------------------\n");
	System.out.println("Number of repetitions for each size: "+n+"\n");
	System.out.println("       || avg. time for  || ratio to || log_2(ratio) ");	
	System.out.println("# bits || multiplication || previous ||");
	System.out.println("---------------------------------------\n");
	for(int l=1; l <= MAXBITLENGTH; l*=2){
	    //multiply together the fastPractically way, n times 
	    r.setSeed(fixedSeed);
	    System.gc();  //garbage collect to get more reliable results
	    t0 = System.currentTimeMillis();
	    for(int i=1; i<=n; i++){
		a = new FastBigInteger(l,r);
		b = new FastBigInteger(l,r);
		a.multiplyFastPractically(b);
	    }
	    fastPracticallyTime = System.currentTimeMillis()-t0;
	    
	    double fastPracticallyRatio;
	    
	    System.out.println(l+" || "+fastPracticallyTime/n+
			       " || "+(fastPracticallyRatio=((double)fastPracticallyTime/prevFastPracticallyTime))+
			       " || "+(Math.log(fastPracticallyRatio)/Math.log(2)) );
	    
	    prevFastPracticallyTime = fastPracticallyTime;
	}// for 
    }


    public static void main(String[] args){
	try{
	    //compareSimpleWithFast(args);
	    compareSimpleWithFastPractical(args);
	    //testSimple(args);
	    //testFast(args);
	    //testFastPractically(args);
	}catch(Exception e){
	    System.err.println("Program requires an int paramater");
	    e.printStackTrace();
	}
    }//main
}//FastBigInteger