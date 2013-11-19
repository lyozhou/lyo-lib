package ConCurrent2;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*----------------------------------------------------------------
 *  Author:        Lyo Zhou
 *  Written:       2013��11��18��       
 *        
 * 
 *  Purpose: 
 *  there are read lock and write lock
 *  totol 5 reader Thread and 5 writer thread
 *  Beacuse write and read thread run so fast
 *  so we print 10 times read result,maybe first time the value 
 *  that not modified at all
 *
 *----------------------------------------------------------------*/

public class ReadWriterLockTest {
	public static void main(String[] args) {
		PricesInfo pricesInfo=new PricesInfo();
		Reader readers[]=new Reader[5];
	    Thread threadsReader[]=new Thread[5];
	    
	    for (int i=0; i<5; i++){
	      readers[i]=new Reader(pricesInfo);
	      threadsReader[i]=new Thread(readers[i]);
	    }
	    Writer writer=new Writer(pricesInfo);
	      Thread  threadWriter=new Thread(writer);
	      for (int i=0; i<5; i++){
	          threadsReader[i].start();
	        }
	        threadWriter.start();
	}
}

class Writer implements Runnable {
	private PricesInfo pricesInfo;
	  
	  public Writer (PricesInfo pricesInfo){
	    this.pricesInfo=pricesInfo;
	  }

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		 for (int i=0; i<3; i++) {
		      System.out.printf("Writer: Attempt to modify the prices.\n");
		      pricesInfo.setPrices(Math.random()*10, Math.random()*8);
		      System.out.printf("Writer: Prices have been modified.\n");
		      try {
		        Thread.sleep(2);
		      } catch (InterruptedException e) {
		        e.printStackTrace();
		      }
		    }
	}
}

class Reader implements Runnable {
	private PricesInfo pricesInfo;
	  
	  public Reader (PricesInfo pricesInfo){
	    this.pricesInfo=pricesInfo;
	  }

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		for (int i=0; i<10; i++){
		      System.out.printf("%s: Price 1: %f\n", Thread.
		currentThread().getName(),pricesInfo.getPrice1());
		      System.out.printf("%s: Price 2: %f\n", Thread.
		currentThread().getName(),pricesInfo.getPrice2());
		    }
	}
	  
}

class PricesInfo {
	private double price1;
	private double price2;
	private ReadWriteLock lock;

	public PricesInfo() {
		price1 = 1.0;
		price2 = 2.0;
		lock = new ReentrantReadWriteLock();
	}

	public double getPrice1() {
		lock.readLock().lock();
		double value = price1;
		lock.readLock().unlock();
		return value;
	}

	public double getPrice2() {
		lock.readLock().lock();
		double value = price2;
		lock.readLock().unlock();
		return value;
	}

	public void setPrices(double price1, double price2) {
		lock.writeLock().lock();
		this.price1 = price1;
		this.price2 = price2;
		lock.writeLock().unlock();
	}
}
