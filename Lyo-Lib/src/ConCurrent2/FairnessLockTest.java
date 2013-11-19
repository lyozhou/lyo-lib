package ConCurrent2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*----------------------------------------------------------------
 *  Author:        Lyo Zhou
 *  Written:       2013/11/18       
 *        
 * 
 *  Purpose: 
 *  ReentrantLock(true) means using Fairness strategy
 *  when system get the lock, it will pass to the thread
 *  which wait most time
 *  default lock is unfair, it will give at some not clear strategy
 *
 *----------------------------------------------------------------*/

public class FairnessLockTest {
	
	public static void main(String[] args){
		PrintQueueFairness pq = new PrintQueueFairness();
		for(int i=0;i<100;i++){
		Thread thread = new Thread(new JobFairness(pq));
		thread.start();
		}
	}

}

class JobFairness implements Runnable{
	private PrintQueueFairness pq;
	
	public JobFairness(PrintQueueFairness pq){
		this.pq = pq;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.printf("%s: Going to print a document\n", Thread.currentThread().getName());
		pq.printJob();
		System.out.printf("%s: The document has been printed\n",Thread.currentThread().getName()); 
	}
	
}

class PrintQueueFairness {
	private Lock queueLock = new ReentrantLock(true);

	public void printJob() {
		queueLock.lock();
	    try {
	      Long duration=(long)(Math.random()*10000);
	      System.out.println(Thread.currentThread().getName()+":PrintQueue: Printing a Job during "+(duration/1000)+" seconds");
	      Thread.sleep(duration);
	    } catch (InterruptedException e) {
	      e.printStackTrace();
	    } finally {
	       queueLock.unlock();
	    }
	    queueLock.lock();
	    try {
	      Long duration=(long)(Math.random()*10000);
	      System.out.println(Thread.currentThread().getName()+":PrintQueue: Printing a Job during "+(duration/1000)+" seconds");
	      Thread.sleep(duration);
	    } catch (InterruptedException e) {
	      e.printStackTrace();
	    } finally {
	          queueLock.unlock();
	       }
	}

}