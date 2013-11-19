package ConCurrent1;

import java.util.ArrayDeque;
import java.util.Date;
import java.util.Deque;

/*----------------------------------------------------------------
 *  Author:        Lyo Zhou
 *  Written:       2013/11/17     
 *        
 * 
 *  Purpose: 
 *  
 *
 *----------------------------------------------------------------*/

public class DaemonThreadTest {
	public static void main(String[] args) {
		Deque<Event> deque = new ArrayDeque<Event>();
		WriterTask writer = new WriterTask(deque);
		for (int i = 0; i < 3; i++) {
			Thread thread = new Thread(writer);
			thread.start();
		}
		cleanerTask ct = new cleanerTask(deque);
		ct.start();
	}
}

class WriterTask implements Runnable {
	private Deque<Event> deque;

	public WriterTask(Deque<Event> deque) {
		this.deque = deque;
	}

	@Override
	public void run() {
		for (int i = 0; i < 100; i++) {
			Event event = new Event();
			event.setDate(new Date());
			event.setEvent(String.format(
					"The thread %s has generated an event", Thread
							.currentThread().getId()));
			deque.addFirst(event);
			System.out.println(deque.size());
			try {
				Thread.sleep(1000);   // if comment sleep(), daemon Thread won't be invoke
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

class cleanerTask extends Thread {
	private Deque<Event> deque;

	public cleanerTask(Deque<Event> deque) {
		this.deque = deque;
		setDaemon(true);
	}

	@Override
	public void run() {
		while (true) {
			Date date = new Date();
			clean(date);
		}
	}


	private void clean(Date date) {
		long difference;
		boolean delete;
		// TODO Auto-generated method stub
		if (deque.size() == 0) {
			return;
		}
		delete = false;
		do {
			Event event = deque.getLast();
			difference = date.getTime() - event.getDate().getTime();
			if (difference > 10000) {
				System.out.printf("Cleaner: %s\n", event.getEvent());
				deque.removeLast();
				delete = true;
			}
		} while (difference > 10000);
		if (delete) {
			System.out.printf("Cleaner: Size of the queue: %d\n", deque.size());
		}
	}
}

class Event {
	private Date date;
	private String event;

	public Event() {

	}

	public Event(Date date, String event) {
		this.date = date;
		this.event = event;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

}
