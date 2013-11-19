package JavaBasic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/*----------------------------------------------------------------
 *  Author:        Lyo Zhou
 *  Written:       2013Äê11ÔÂ16ÈÕ       
 *        
 * 
 *  Purpose: 
 *  cross combine a.txt and b.txt into c.txt
 *  a.txt divided with enter
 *  b.txt diveded with space or enter(means in txt, there be 2 seperators)
 *
 *----------------------------------------------------------------*/

public class Program1 {
	public static void main(String[] args){
		try {
			FileManager fma = new FileManager("1.txt",new char[]{'\n'});
			FileManager fmb = new FileManager("2.txt",new char[]{'\n',' '});
			FileWriter fw = new FileWriter(new File("c.txt"),true);
			String aWord , bWord;
			String[] bWords;
			int i = 0;
			while( (aWord = fma.readNext()) != null){
				fw.write(aWord+"\n");
				if((bWord= fmb.readNext())!=null){
					fw.write(bWord+"\n");
				}
			}
			fw.flush();
			fw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}

class FileManager{
	String[] Words;
	int pos = 0;
	
	public FileManager(){
		
	}
	
	public FileManager(String fileName,char[] seperator){
		String regex =  null;
		File f = new File(fileName);   // it won't create a new one, if want create, f.creatNewFile()
		try {
			FileReader fr = new FileReader(f);
			char[] buf = new char[(int) f.length()];
			int len = fr.read(buf);
			String result = new String(buf,0,len);
			if(seperator.length>1){
				regex = "" + seperator[0] + "|" + seperator[1];
			}else{
				regex = "" + seperator[0];
			}
			Words = result.split(regex);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String readNext(){
		if(pos==Words.length){
			return null;
		}
		return Words[pos++];
	}
}
 
