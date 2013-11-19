import java.util.Arrays;
import java.util.Comparator;

/*----------------------------------------------------------------
 *  Author:        Lyo Zhou
 *  Written:       2013-9-9       
 *        
 * 
 *  Purpose:  sort same data using diffrent key,etc: BY_NAME or BY_AGE
 *
 *----------------------------------------------------------------*/

public class ComparatorSort {
	public static final Comparator<ComparatorSort> BY_NAME = new ByName();
	public static final Comparator<ComparatorSort> BY_AGE = new ByAge();
	private String studentName;
	private Integer studentAge;
	
	public ComparatorSort(){
		
	}
	
	public ComparatorSort(String name, Integer age){
		this.studentName = name;
		this.studentAge = age;
	}
	
	/********************************************  
	 * @Title: ByName  
	 * @Description: 
	 * 
	 * Define a comparator method, which could be used at
	 * 
	 * Arrays.sort(arrayName, sortMethod)
	 * 
	 * @throws  
	*********************************************/
	private static class ByName implements Comparator<ComparatorSort> {
		@Override
		public int compare(ComparatorSort u, ComparatorSort v) {
			// TODO Auto-generated method stub
			return u.studentName.compareTo(v.studentName);
		}
		
	}
	private static class ByAge implements Comparator<ComparatorSort> {
		
		@Override
		public int compare(ComparatorSort u, ComparatorSort v) {
			// TODO Auto-generated method stub
			return u.studentAge.compareTo(v.studentAge);
		}
		
	}
	
	/********************************************  
	 * @Title: sort  
	 * @Description: 
	 * 
	 * This is a similar function as Arrays.sort
	 * 
	 * check out different someday
	 * 
	 * @throws  
	*********************************************/
	
	public static void sort(Object[] a, Comparator comparator){
		int N = a.length ;
		for(int i=0; i<N; i++){
			for(int j=i;j>0 && less(comparator,a[j],a[j-1]);j--){
				exch(a,j,j-1);
			}
		}
	}
	
	private static boolean less(Comparator c, Object v, Object w){
		return c.compare(v, w) < 0;
	}
	
	private static void exch(Object[] a, int i, int j){
		Object swap = a[i];
		a[i] = a[j];
		a[j] = swap;
	}
	
	public static void print(Object[] a){
		for(int i=0; i<a.length;i++){
			ComparatorSort tmp = (ComparatorSort) a[i];
			System.out.print(tmp.studentName + " " + tmp.studentAge +"\r\n");
		}
	}
	
	public static void main(String[] args){
		ComparatorSort[] cs = new ComparatorSort[4];
		cs[0] = new ComparatorSort("lyo",12);
		cs[1] = new ComparatorSort("fyo",22);
		cs[2] = new ComparatorSort("uyo",13);
		cs[3] = new ComparatorSort("ayo",3);
		sort(cs,ComparatorSort.BY_AGE);
//		Arrays.sort(cs,ComparatorSort.BY_AGE);
		print(cs);
	}
	
}
