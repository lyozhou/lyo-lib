import java.lang.reflect.Array;
import java.util.Arrays;


public class Brute {
	public static void main(String[] args){
		In input = new In(args[0]);
		int N = input.readInt();
		Point[] array = new Point[N];
		int i=0;
		
		while(!input.isEmpty()){
			array[i] = new Point(input.readInt(),input.readInt());
			array[i++].draw();
		}
		
		for(int p=0;p<N-3;p++){
			for(int q=p+1;q<N-2;q++){
				for(int r=q+1;r<N-1;r++){
					for(int s=r+1;s<N;s++){
						if(array[p].slopeTo(array[q]) == array[q].slopeTo(array[r]) && array[p].slopeTo(array[q]) == array[r].slopeTo(array[s])){
							Point[] tmp = {array[p],array[q],array[r],array[s]};
							Arrays.sort(tmp);
							for(int i1=0;i1<4;i1++){
								StdOut.print(tmp[i1]);
								if (i1 < 3)
									StdOut.print(" -> ");
							}
							StdOut.println();
							tmp[0].drawTo(tmp[3]);
						}
					}
				}
			}
		}
	}
}
