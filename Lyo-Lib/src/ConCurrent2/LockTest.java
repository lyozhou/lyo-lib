package ConCurrent2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*----------------------------------------------------------------
 *  Author:        Lyo Zhou
 *  Written:       2013/11/18       
 *        
 * 
 *  Purpose: 
 *  there is a code block which want to synchronized
 *  we use Lock class to lock() and unlock()
 *  100 Thread going to write sth, but they all in the printQueue
 *
 *----------------------------------------------------------------*/

public class LockTest {
	
	public static void main(String[] args){
		PrintQueue pq = new PrintQueue();
		for(int i=0;i<100;i++){
		Thread thread = new Thread(new Job(pq));
		thread.start();
		}
	}

}

class Job implements Runnable{
	private PrintQueue pq;
	
	public Job(PrintQueue pq){
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

class PrintQueue {
	private Lock queueLock = new ReentrantLock();

	public void printJob() {
		queueLock.lock();
		try {
			Long duration = (long) (Math.random() * 10000);
			System.out.println(Thread.currentThread().getName()
					+ ":PrintQueue: Printing a Job during " + (duration / 1000)
					+ " seconds");
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally{
			queueLock.unlock();
		}
	}

}