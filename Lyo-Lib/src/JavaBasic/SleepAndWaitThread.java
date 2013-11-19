package JavaBasic;

/*----------------------------------------------------------------
 *  Author:        Lyo Zhou
 *  Written:       2013年11月15日       
 *        
 * 
 *  Purpose: 
 *  1、Sleep() and Notify() won't give up the lock
 *  2、Wait() will give up the lock
 *  3、Using synchronized (SleepAndWaitThread.class) rather than 
 *  synchronized (this) because thread1 and thread2 are point to 
 *  different object
 *
 *----------------------------------------------------------------*/

public class SleepAndWaitThread {
	private static int i = 0;
	
	public static void main(String[] args){
		/***************************************/
//		new Thread(new Thread1()).start();
//		try {
//			Thread.sleep(10);    //just want thread2 is run after thread1
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		new Thread(new Thread2()).start();
		/***************************************/
		final SleepAndWaitThread sw = new SleepAndWaitThread();
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				sw.syncFunc();
			}
			
		}).start();
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generat
				sw.notSyncFunc();
			}
			
		}).start();
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generat
				sw.syncFunc2();
			}
			
		}).start();
	}
	
	private synchronized void syncFunc() {
		System.out.println("syncFunc Modify i ...."+ ++i);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/********************************************  
	 * @Title: notSyncFunc  
	 * @Description: 
 	 * notSyncFunc as non sync, then other thread can
 	 * invoke notSyncFunc during SyncFunc has run
	 *
	 * 
	 * @return void
	 * @throws  
	*********************************************/
	private void notSyncFunc(){
		System.out.println("notSyncFunc Modify i ...."+ ++i);
	}
	
	/********************************************  
	 * @Title: syncFunc2  
	 * @Description: 
	 * SyncFunc2 as synchronized, then other thread cannot 
 	 * invoke SyncFunc2 during SyncFunc has run
	 *
	 * @return void
	 * @throws  
	*********************************************/
	private synchronized void syncFunc2(){
		System.out.println("syncFunc2 Modify i ...."+ ++i);
	}
	
	private static class Thread1 implements Runnable{

		@Override
		public void run() {
			synchronized (SleepAndWaitThread.class) {
				System.out.println("thread1 is entering...");
				System.out.println("thread1 is waiting...");
				try {
					SleepAndWaitThread.class.wait();   // give up the object lock, let thread2 run
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("thread1 is going on...");
				System.out.println("thread1 is being over!");	
			}
		}
		
	}
	
	private static class Thread2 implements Runnable{

		@Override
		public void run() {
			synchronized (SleepAndWaitThread.class) {
				System.out.println("thread2 is entering...");
				System.out.println("thread2 is waiting...");
				SleepAndWaitThread.class.notify();  // notify thread1 thread2 is going to finish, but notify doesn't give up the lock
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("thread2 is going on...");
				System.out.println("thread2 is being over!");   //finish, thread2 give the lock to thread1
			}			
		}
		
	}

}
 