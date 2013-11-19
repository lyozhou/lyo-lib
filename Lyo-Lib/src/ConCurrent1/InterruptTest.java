package ConCurrent1;

/*----------------------------------------------------------------
 *  Author:        Lyo Zhou
 *  Written:       2013/11/17      
 *        
 * 
 *  Purpose: 
 *  extends Thread class
 *
 *----------------------------------------------------------------*/

public class InterruptTest {
	public static void main(String[] args){
		Thread prime = new PrimeGenerator(123);
		prime.start();
		try {
			Thread.sleep(3000);
			prime.interrupt();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

class PrimeGenerator extends Thread{
	private int number ;
	public PrimeGenerator(int num){
		this.number = number;
	}
	public void run(){
		while(true){
			if(isPrime()){
				System.out.printf("%d number is prime \n",number);
			}
			if(isInterrupted()){   // just extends Thread has isInterrupted()
				System.out.println("Thread has been interrupt !");
				return;
			}
			number++;
		}
	}
	
	/********************************************
	 * @Title:isPrime  
	 * @Description:
	 *
	 *
	 * @return
	 * @return boolean
	 * @throws
	 ********************************************/
	private boolean isPrime() {
		// TODO Auto-generated method stub
		if(number%2==0)
			return false;
		for(int i=3;i<number;i++){
			if(number%i==0)
				return false;
		}
		return true;
	}
}
 