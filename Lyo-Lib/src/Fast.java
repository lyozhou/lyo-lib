import java.util.Arrays;


public class Fast {
	public static void main(String[] args){
		In input = new In(args[0]);
		int N = input.readInt();
		Point[] array = new Point[N];
		int i=0;
		Comparable[] sorted = new Comparable[N];
		
		while(!input.isEmpty()){
			array[i] = new Point(input.readInt(),input.readInt());
			array[i++].draw();
		}
		
		double[] result = new double[N];		
		for(int p=0;p<array.length;p++){
//			for(int q=0;q<array.length;q++){
//				result[q] = array[p].slopeTo(array[q]);
//			}
//			Arrays.sort(result);
//			for(int q=0;q<array.length;q++){
//				StdOut.print(result[q]+" ");
//			}
//			StdOut.println("");
			Arrays.sort(array,array[p].SLOPE_ORDER);
			for(int q=0;q<array.length;q++){
				StdOut.print(array[p].slopeTo(array[q])+" ");
			}
			StdOut.println("");
			int count=1;
			int k = 1;
			for(;k<array.length ;k++){
				if (array[p].slopeTo(array[k]) == array[p].slopeTo(array[k-1]) ){
					count++;
				}
				else{
					if(count>2){
						Point[] tmp = new Point[count+1];
						tmp[0] = array[p];
						for(int i0=0,i1=k-1;i0<count&&i1>k-count-1;i0++,i1--){
							tmp[i0+1] = array[i1];
						}
						Arrays.sort(tmp);
						for(int i1=0;i1<count+1;i1++){
							StdOut.print(tmp[i1]);
							if (i1 < count)
								StdOut.print(" -> ");
						}
						StdOut.println();
					}
					count = 1;
				}
			}
			
		}
	}

}
