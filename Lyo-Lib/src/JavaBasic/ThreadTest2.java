package JavaBasic;

/*----------------------------------------------------------------
 *  Author:        Lyo Zhou
 *  Written:       2013Äê11ÔÂ16ÈÕ       
 *        
 * 
 *  Purpose: 
 *  main thread and sub thread, 1\sub run 10 times then 2\main run 10 times
 *  then 3\sub run 10 times, then 4\main run 10 times
 *  all of above run 50 time 
 *
 *----------------------------------------------------------------*/

public class ThreadTest2 {
	private int j = 0;
	private boolean flag = true;

	public static void main(String[] args) {
		ThreadTest2 tt2 = new ThreadTest2();
		final ThreadTest2.MainSubThread ms = tt2.new MainSubThread();
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				for(int i=0;i<50;i++){
					ms.subThread();
				}
			}
			
		}).start();
		
		for(int i=0;i<50;i++){
			ms.mainThread();
		}
	}

	public class MainSubThread {
		public synchronized void mainThread() {
			if (!flag) {
				for (int i = 0; i < 10; i++) {
					System.out.println("main Thread " + j++);
				}
				flag = true;
			}else{
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			notify();
		}

		public synchronized void subThread() {
			if (flag) {
				for (int i = 0; i < 10; i++) {
					System.out.println("sub Thread " + j++);
				}
				flag = false;
			} else {
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			notify();
		}
	}

}
