package ConCurrent2;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/*----------------------------------------------------------------
 *  Author:        Lyo Zhou
 *  Written:       2013/11/18       
 *        
 * 
 *  Purpose: 
 *  size = 10, data = new Date()
 *
 *----------------------------------------------------------------*/

public class ProducerConsumerTest {
	public static void main(String[] args) {
		EventStorage es = new EventStorage();
		Thread thread1 = new Thread(new Producer(es));
		Thread thread2 = new Thread(new Consumer(es));
		thread1.start();
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		thread2.start();

	}
}

class Consumer implements Runnable {
	private EventStorage es;

	public Consumer(EventStorage es) {
		this.es = es;
	}

	@Override
	public void run() {
		for(int i=0;i<100;i++){
		es.get();
		}
	}

}

class Producer implements Runnable {
	private EventStorage es;

	public Producer(EventStorage es) {
		this.es = es;
	}

	@Override
	public void run() {
		for (int i = 0; i < 100; i++) {
			es.set();
		}
	}

}

class EventStorage {
	private int maxSize;
	private List<Date> storage;

	public EventStorage() {
		this.maxSize = 10;
		this.storage = new LinkedList<Date>();
	}

	public synchronized void set() {
		while (storage.size() == maxSize) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		((LinkedList<Date>) storage).offer(new Date());
		System.out.printf("Set: %d\n", storage.size());
		notifyAll();
	}

	public synchronized void get() {
		while (storage.size() == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Date tmp = ((LinkedList<Date>) storage).poll();
		System.out.printf("Get: %d: %s\n", storage.size(), tmp);
		notifyAll();
	}

	public int getSize() {
		return maxSize;
	}

	public void setSize(int maxSize) {
		this.maxSize = maxSize;
	}

	public List<Date> getStorage() {
		return storage;
	}

	public void setStorage(LinkedList<Date> storage) {
		this.storage = storage;
	}
}
