package ConCurrent1;

import java.io.File;

/*----------------------------------------------------------------
 *  Author:        Lyo Zhou
 *  Written:       2013/11/17     
 *        
 * 
 *  Purpose: 
 *  implements runnable interface
 *  perpouse of using catch InterruptException is to interrupt immediately
 *  in recursive function(like directoryProcess or fileProcess)
 *  
 *
 *----------------------------------------------------------------*/

public class InterruptExceptionTest {
	public static void main(String[] args){
		Thread t = new Thread(new FileSearch("d:\\eclipse\\workspace\\","InterruptExceptionTest.java"));
		t.start();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("time up");
		t.interrupt();
	}
	
}

class FileSearch implements Runnable{
	private String path;
	private String fileName;
	public FileSearch(String path,String fileName){
		this.path = path;
		this.fileName = fileName;
	}

	@Override
	public void run() {
		File srcDir = new File(path);
		if(srcDir.exists()&&srcDir.isDirectory()){
			try{
				directoryProcess(srcDir);
			}
			catch (InterruptedException e){
				System.out.printf("%s: The search has been interrupted",Thread.currentThread().getName());
			}
			if(Thread.interrupted()){
				System.out.printf("%s: The search has been interrupted",Thread.currentThread().getName());
			}
		}
	}
	
	/*******************************************
	 * @Title: directoryProcess
	 * @Description:
	 * compare to InterruptTest,we comments if(Thread.interrupted()){}
	 * then in the run(), we will use if(Thread..) to catch the interruption
	 *
	 * @param srcDir
	 * @throws InterruptedException
	 * @return void
	 * @throws
	 * ********************************************/
	private void directoryProcess(File srcDir) throws InterruptedException {
		// TODO Auto-generated method stub
		File[] lists = srcDir.listFiles();
		for(File f : lists){
			if(f.isDirectory()){
				System.out.printf("search dir %s\n",f.getName());
				directoryProcess(f);
			}else{
				System.out.printf("search file %s\n",f.getName());
				fileProcess(f);
			}
		}
		if(Thread.interrupted()){
			throw new InterruptedException();
		}
	}

	/********************************************
	 * @Title: fileProcess
	 * @Description:
	 * compare to InterruptTest,we comments if(Thread.interrupted()){}
	 *
	 * @param f
	 * @throws InterruptedException
	 * @return void
	 * @throws
	 * *******************************************/
	private void fileProcess(File f) throws InterruptedException {
		// TODO Auto-generated method stub
		if(f.getName().equals(fileName)){
			System.out.printf("%s : %s\n",Thread.currentThread().getName() ,f.getAbsolutePath());
		}
		if(Thread.interrupted()){
			throw new InterruptedException();
		}
	}
	
}
 