package JavaBasic;

/*----------------------------------------------------------------
 *  Author:        Lyo Zhou
 *  Written:       2013��11��15��       
 *        
 * 
 *  Purpose: 
 *  Four Thread, two add j, other minus j
 *
 *----------------------------------------------------------------*/

public class ThreadTest {
	private int j;
	public static void main(String[] args){
		ThreadTest tt = new ThreadTest();
		for(int i=0;i<2;i++){
			new Thread(tt.new Inc()).start();
		}
		for(int i=0;i<2;i++){
			new Thread(tt.new Dec()).start();
		}
	}
	
	private synchronized void inc(){
		j++;
		System.out.println("Thread "+ Thread.currentThread().getName() + " change j ++ "+j);
	}
	
	private synchronized void dec(){
		j--;
		System.out.println("Thread "+ Thread.currentThread().getName() + " change j -- "+j);
	}
	
	class Dec implements Runnable{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true){
				dec();
			}
		}
		
	}
	class Inc implements Runnable{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true){
				inc();
			}
		}
		
	}
	
}

 