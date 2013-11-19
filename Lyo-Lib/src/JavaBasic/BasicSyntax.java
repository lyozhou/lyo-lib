package JavaBasic;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*----------------------------------------------------------------
 *  Author:        Lyo Zhou
 *  Written:       2013年11月15日       
 *        
 * 
 *  Purpose: 
 *  
 *
 *----------------------------------------------------------------*/

public class BasicSyntax {
	static int x = 1;
	public static void main(String[] args){
		BasicSyntax  bs = new BasicSyntax();
		bs.StreamIO();
		
	}
	
	private void test(){
		final StringBuffer a = new StringBuffer("dd");
//		a = new StringBuffer("a");   'this is wrong
		a.append("f");
	}
	
	/********************************************  
	 * @Title: StreamIO  
	 * @Description: 
	 * write chinese character into 1.txt
	 * and read it out in console
	 * 
	 * @return void
	 * @throws  
	*********************************************/
	private void StreamIO(){
		String str = "中国";
		PrintWriter pw;
		try {
			pw = new PrintWriter("3.txt","GBK");
			pw.write(str);
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			FileInputStream fis = new FileInputStream("1.txt");
			InputStreamReader isr = new InputStreamReader(fis,"GBK");
			BufferedReader br = new BufferedReader(isr);
			String line = br.readLine();
			System.out.println(line);
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	private void RunnableTest(){
		new Thread(){
			@Override
			public void run() {
				for(int i=80;i<90;i++)
					System.out.println(i);
			}
		}.start();
		
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				for(int i=90;i<100;i++)
					System.out.println(i);
			}
			
		}).start();
		
		ExecutorService pool = Executors.newFixedThreadPool(3);
		for(int i=0;i<3;i++)
		{
			pool.execute(new Runnable(){
				public void run(){
					Random ran = new Random();
					int m = ran.nextInt(10);
					System.out.println(m);
				}});
    	}

	}
	
	/********************************************  
	 * @Title: WhenFinallyRun  
	 * @Description: 
	 * Finally will be excute even try block has return
	 * if try block has return and finally block has return
	 * it behaves like a stack, first in last out 
	 * so main will get the last in value = 2
	 * 	  try 1
	 *    finally 2
	 *    main 2
	 *	
	 * @return int
	 * @throws  
	*********************************************/
	static int WhenFinallyRun()
	{
		try
		{
			System.out.println("try "+x);
			return x;
		}
		finally
		{
			System.out.println("finally "+ (++x));
			return x;
		}
	}
	
	public static class StaticNestedClass{
		/********************************************  
		 * @Title: runOuterStaticFunc  
		 * @Description: 
		 * just run outer class's static func because itself 
		 * is static
		 * How to use it : 
		 * 		BasicSyntax.StaticNestedClass in = new BasicSyntax.StaticNestedClass();
		 * 		in.runOuterFunc();
		 * 
		 * @return void
		 * @throws  
		*********************************************/
		public void runOuterStaticFunc(){
			fastGet2Multi8();
		}
	}
	
	public class InnerClass{
		/********************************************  
		 * @Title: runOuterFunc  
		 * @Description: 
		 * How to use InnerClass
		 *   BasicSyntax bs = new BasicSyntax();
		 *   BasicSyntax.InnerClass in = bs.new InnerClass();
		 *   in.runOuterFunc();
		 * 		
		 * @return void
		 * @throws  
		*********************************************/
		public void runOuterFunc(){
			getLowerFourNumber();
		}
	}
	
	/********************************************  
	 * @Title: getLowerFourNumber  
	 * @Description: 
	 * using & to get bit calculation
	 *
	 * @return void
	 * @throws  
	*********************************************/
	private void getLowerFourNumber(){
		int num = 0x31;
		int after = num & 0xf0;
		System.out.println("get lower four number of 0x31 is "+ after);
	}
	
	
	/********************************************  
	 * @Title: fastGet2Multi8  
	 * @Description: 
	 * fast way to calculate 2 multi 8 using << 
	 *
	 * @return void
	 * @throws  
	*********************************************/
	private static void fastGet2Multi8(){
		int result = 2 << 3;
		System.out.println("fast way to calculate 2 multi 8 is " + result);
	}
}
 