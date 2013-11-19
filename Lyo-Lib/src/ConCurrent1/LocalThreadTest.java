package ConCurrent1;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/*----------------------------------------------------------------
 *  Author:        Lyo Zhou
 *  Written:       2013/11/17       
 *        
 * 
 *  Purpose: 
 *  UnsafeTask not use threadLocal, safeTask use threadLocal
 *  For UnsafeTask, startDate value is shared among three thread,
 *  So after 3th Thread init startDate, 1th and 2th Thread startDate
 *  has become the 3th 
 *  For safeTask, startDate is stored locally
 *
 *----------------------------------------------------------------*/

public class LocalThreadTest {
	
	public static void main(String[] args){
		UnsafeTask ut = new UnsafeTask();
		Thread[] threads = new Thread[3];
		for(int i=0;i<3;i++){
			Thread thread = new Thread(ut);
			thread.start();
			threads[i] = thread;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			threads[0].join();
			threads[1].join();
			threads[2].join();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		SafeTask st = new SafeTask();
		for(int i=0;i<3;i++){
			Thread thread = new Thread(st);
			thread.start();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}

class SafeTask implements Runnable{
	private ThreadLocal<Date> startDate = new ThreadLocal<Date>(){
		 protected Date initialValue(){
		      return new Date();
		    }
	};
	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.printf("Starting Thread: %s : %s\n",Thread.currentThread().getId(),startDate.get());
	    try {
	      TimeUnit.SECONDS.sleep( (int)Math.rint(Math.random()*10));
	    } catch (InterruptedException e) {
	      e.printStackTrace();
	    }
	    System.out.printf("Thread Finished: %s : %s\n",Thread.currentThread().getId(),startDate.get());
	}
	
}

class UnsafeTask implements Runnable{
	private Date startDate;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		startDate=new Date();
	    System.out.printf("Starting Thread: %s : %s\n",Thread.currentThread().getId(),startDate);
	    try {
	      TimeUnit.SECONDS.sleep( (int)Math.rint(Math.random()*10));
	    } catch (InterruptedException e) {
	      e.printStackTrace();
	    }
	    System.out.printf("Thread Finished: %s : %s\n",Thread.currentThread().getId(),startDate);
	}
	
}
 