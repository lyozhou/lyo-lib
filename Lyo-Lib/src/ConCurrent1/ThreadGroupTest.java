package ConCurrent1;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/*----------------------------------------------------------------
 *  Author:        Lyo Zhou
 *  Written:       2013/11/17       
 *        
 * 
 *  Purpose: 
 *  
 *
 *----------------------------------------------------------------*/

public class ThreadGroupTest {
	public static void main(String[] args){
		ThreadGroup tg = new ThreadGroup("Searcher");
		Result result = new Result();
		SearchTask st = new SearchTask(result);
		for(int i=0;i<5;i++){
			Thread thread = new Thread(tg,st);
			thread.start();
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.printf("Number of Threads: %d\n",tg.activeCount());
		System.out.printf("Information about the Thread Group\n");
		tg.list();
		Thread[] threads=new Thread[tg.activeCount()];
	    tg.enumerate(threads);     // ???????????????? 
	    for (int i=0; i<tg.activeCount(); i++) {
	      System.out.printf("Thread %s: %s\n",threads[i].getName(),threads[i].getState());
	    }
	    waitFinish(tg);
	    tg.interrupt();
	}

	/********************************************
	 * @Title:waitFinish  
	 * @Description:
	 *
	 *
	 * @param tg
	 * @return void
	 * @throws
	 ********************************************/
	private static void waitFinish(ThreadGroup tg) {
		// TODO Auto-generated method stub
		while (tg.activeCount()>9) {
		      try {
		        TimeUnit.SECONDS.sleep(1);
		      } catch (InterruptedException e) {
		        e.printStackTrace();
		      }
		    }
	}
}

class SearchTask implements Runnable{
	private Result result;
	
	public SearchTask(Result result){
		this.result = result;
	}
	@Override
	public void run() {
		String name=Thread.currentThread().getName();
	    System.out.printf("Thread %s: Start\n",name);
	    try {
	      doTask();
	      result.setName(name);
	    } catch (InterruptedException e) {
	      System.out.printf("Thread %s: Interrupted\n",name);
	      return;
	    }
	    System.out.printf("Thread %s: End\n",name);
	}
	/**********************************************
	 * @Title:doTask  
	 * @Description:
	 *
	 *
	 * @return void
	 * @throws InterruptedException
	 ********************************************/
	private void doTask() throws InterruptedException {
		// TODO Auto-generated method stub
		Random random=new Random((new Date()).getTime());
	    int value=(int)(random.nextDouble()*100);
	    System.out.printf("Thread %s: %d\n",Thread.currentThread().getName(),value);
	    TimeUnit.SECONDS.sleep(value);
	}
	
}


class Result{
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
 