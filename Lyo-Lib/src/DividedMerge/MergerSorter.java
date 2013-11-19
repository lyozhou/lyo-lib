package DividedMerge;

public class MergerSorter<T> {
	private Comparable[] array;
	private Comparable[] aux;    //a copy from array
	
	public void merge(int lo, int mid, int hi){
		int i = lo;
		int j = mid+1;
		
        for (int k = lo; k <= hi; k++) {
            aux[k] = array[k]; 
        }
		
		for(int k=lo; k<=hi;k++){
			if(i>mid) array[k] = aux[j++];      //means first half has finished, second half have sth left
			else if(j>hi) array[k] = aux[i++];  //means second half has finished, first half have sth left
			else if(aux[i].compareTo(aux[j])>0) //means a[i] > a[j]
				array[k] = aux[j++];
			else 
				array[k] = aux[i++];
		}
	}
	
	public void sort(int lo, int hi){
		if(hi<=lo) return;
		int mid = lo + (hi-lo)/2;
		sort(lo,mid);
		sort(mid+1,hi);
		merge(lo,mid,hi);
	}
	
	public void sort(Comparable[] array){
		this.array = array;
		this.aux = new Comparable[array.length];
		sort(0,array.length-1);
	}
	
	public void mergeSort(Comparable[] array){
		sort(array);
	}
	
	public static void main(String[] args){
		String[] a = {"hello","world","how","are","you"};   // Using Generic type
		MergerSorter mergeSort = new MergerSorter();
		mergeSort.sort(a);
		for(int i=0;i<a.length;i++){
			System.out.print(a[i]+" ");
		}
	}
}
