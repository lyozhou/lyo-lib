package JavaBasic;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;

/*----------------------------------------------------------------
 *  Author:        Lyo Zhou
 *  Written:       2013年11月16日       
 *        
 * 
 *  Purpose: 
 *  copy java/.java file to jad/.jad
 *
 *----------------------------------------------------------------*/

public class Program2 {
	public static void main(String[] args) throws Exception {
		File srcDic = new File("java");
		if (!(srcDic.exists() && srcDic.isDirectory())) {
			throw new Exception("Dictory is not exist ! ");
		}
		File[] files = srcDic.listFiles(new FilenameFilter() {

			@Override
			public boolean accept(File file, String name) {
				// TODO Auto-generated method stub
				return name.endsWith(".java");
			};
		});
		
		File destDic = new File("jad");
		if(!destDic.exists()){
			destDic.mkdir();    // if not exist, make it
		}
		for(File f:files){
			FileInputStream fis = new FileInputStream(f);
			String destName = f.getName().replaceAll("\\.java$", ".jad");
			FileOutputStream fos = new FileOutputStream(new File(destDic,destName));
			copy(fis,fos);
			fis.close();
			fos.close();
		}
	}

	/********************************************  
	 * @Title: copy  
	 * @Description: 
	 *
	 *
	 * @param fis
	 * @param fos
	 * @return void
	 * @throws  
	 * @throws IOException 
	*********************************************/
	private static void copy(FileInputStream fis, FileOutputStream fos) throws IOException {
		// TODO Auto-generated method stub
		int len = 0;
		byte[] buffer = new byte[1024];
		while((len=fis.read(buffer)) != -1){
			fos.write(buffer,0,len);
		}
	}

}
