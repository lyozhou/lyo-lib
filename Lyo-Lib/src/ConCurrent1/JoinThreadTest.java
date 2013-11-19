package ConCurrent1;

import java.util.Date;

/*----------------------------------------------------------------
 *  Author:        Lyo Zhou
 *  Written:       2013/11/17       
 *        
 * 
 *  Purpose: 
 *  after B and C sub Thread finished, main Thread A run
 *
 *----------------------------------------------------------------*/

public class JoinThreadTest {
	public static void main(String[] args){
		Thread data = new Thread(new DataSourceLoader(),"dataSource");
		Thread network = new Thread(new NetWorkLoader(),"network");
		data.start();
		network.start();
		try {
			data.join();
//			network.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.printf("Main: Configuration has been loaded: %s\n",new Date());
	}
}

class DataSourceLoader implements Runnable{

	@Override
	public void run() {
		System.out.printf("Beginning data sources loading: %s\n",new Date());
		try {
			Thread.sleep(3000);
			System.out.printf("Data sources loading has finished:%s\n",new Date());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

class NetWorkLoader implements Runnable{

	@Override
	public void run() {
		System.out.printf("Beginning network loading: %s\n",new Date());
		try {
			Thread.sleep(6000);
			System.out.printf("Network loading has finished:%s\n",new Date());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
 