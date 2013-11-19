package JavaBasic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TreeSet;

/*----------------------------------------------------------------
 *  Author:        Lyo Zhou
 *  Written:       2013年11月17日       
 *        
 * 
 *  Purpose: 
 *  name.txt, print out unique name and it repeat time, 
 *  sort at acsent
 *
 *----------------------------------------------------------------*/

public class Program4 {
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("name.txt"),"GBK"));
		HashMap<String,Integer> hm = new HashMap<String,Integer>();
		String line = null;
		while((line = br.readLine())!=null){
			dealLine(line,hm);
		}
		sort(hm);
		
	}

	/********************************************  
	 * @Title: sort  
	 * @Description: 
	 *
	 *
	 * @param hm
	 * @return void
	 * @throws  
	*********************************************/
	private static void sort(HashMap<String, Integer> hm) {
		// TODO Auto-generated method stub
		TreeSet<User> ts = new TreeSet<User>(new Comparator<User>() {

			@Override
			public int compare(User u1, User u2) {
				User user1 = (User) u1;
				User user2 = (User) u2;
//				return user1.value<user2.value?-1:user1.value==user2.value?0:1;   //Strange, why cant use this
// otherwise, wangwu 1 tianqi 1 zhaoliu 1, there will be only one print out
				if(user1.value<user2.value)
				{
					return -1;
				}else if(user1.value>user2.value)
				{
					return 1;
				}else
				{
					return user1.name.compareTo(user2.name);
				}

			}
		});
		Iterator iterator = hm.keySet().iterator();
		while(iterator.hasNext()){
			String name = (String) iterator.next();
			Integer value = hm.get(name);
			if(value>0){
				ts.add(new User(value,name));   // define a comparator, when add a new object, it will sort at that defination
			}
		}
		printOut(ts);
	}

	/********************************************  
	 * @Title: printOut  
	 * @Description: 
	 *
	 *
	 * @param hm
	 * @return void
	 * @throws  
	*********************************************/
	private static void printOut(TreeSet ts) {
		// TODO Auto-generated method stub
		Iterator iterator = ts.iterator();
		while(iterator.hasNext()){
			User user = (User) iterator.next();
			System.out.println(user.getName()+" : "+user.getValue());
		}
	}

	/********************************************  
	 * @Title: dealLine  
	 * @Description: 
	 *
	 *
	 * @param line
	 * @param hm
	 * @return void
	 * @throws  
	*********************************************/
	private static void dealLine(String line, HashMap hm) {
		// TODO Auto-generated method stub
		String[] col = line.split(",");
		if(col.length==3){
			String name = col[1];
			Integer value = (Integer) hm.get(name);
			if(value == null){
				value = 0;
			}
			hm.put(name,value+1);
		}
	}
	
	static class User{
		private Integer value;
		private String name;
		
		public User(Integer value,String name){
			this.value = value;
			this.name = name;
		}
		public Integer getValue() {
			return value;
		}
		public void setValue(Integer value) {
			this.value = value;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		
	}
}
 