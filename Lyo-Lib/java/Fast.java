import java.util.Arrays;

/*----------------------------------------------------------------
 *  Author:        Lyo Zhou
 *  Written:       2013-9-11       
 *        
 * 
 *  Purpose:  Remarkably, it is possible to solve the problem much 
 *  faster than the brute-force solution described above. Given a 
 *  point p, the following method determines whether p participates 
 *  in a set of 4 or more collinear points.
 *  
 *  1、Think of p as the origin.
 *  
 *  2、For each other point q, determine the slope it makes with p
 *  
 *  3、Sort the points according to the slopes they makes with p
 *  
 *  4、Check if any 3 (or more) adjacent points in the sorted order 
 *  have equal slopes with respect to p. If so, these points, together 
 *  with p, are collinear.
 *
 *----------------------------------------------------------------*/

public class Fast {
    
	public static void main(String[] args)
   	{
       	int nPoints,x,y,i,j,k,l,cont,m,n=0;
        double value;
        Point [] points;
        Point [] orderedPoints;
        
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        try 
        {
            In in = new In(args[0]);
            nPoints = Integer.parseInt(in.readLine());
            points = new Point[nPoints];
            for(i=0;i<nPoints;i++)
            {
                x=Integer.parseInt(in.readString());
                y=Integer.parseInt(in.readString());
                points[i] = new Point(x,y);
                points[i].draw();
            }

            for(i=1;i<=nPoints-3;i++)
        	{
                k=nPoints-i;
                orderedPoints= new Point[k];
                System.arraycopy(points, 0, orderedPoints, 0, k);
                Arrays.sort(orderedPoints,0,k,points[k].SLOPE_ORDER);
                j=0;
                do
                {
                    cont=1;
                    m=j;                    
                    value=points[k].slopeTo(orderedPoints[j++]);
                    while(j<k&&points[k].slopeTo(orderedPoints[j])==value){cont++;n=j;j++;}
                    if(cont>=3)
                    {
                    	Point[] tmp = new Point[n-m+2];
                    	int i0 = 1;
                    	tmp[0] = points[k];
                        for(l=m;l<=n;i0++,l++)
                        {
                        	tmp[i0] = orderedPoints[l];
                        }
                        Arrays.sort(tmp);
                        StdOut.print(tmp[0].toString()); 
                        for(int i1=1;i1<tmp.length ;i1++){
                        	StdOut.print(" -> " + tmp[i1].toString()); 
                        	tmp[0].drawTo(tmp[i1]);
                        }
                        StdOut.println();
                    }
                }while(j<k);
            }    
        }
		catch (Exception e) { System.out.println(e); }
   }
}
//public class Fast {
//	public static void main(String[] args){
//		In input = new In(args[0]);
//		int N = input.readInt();
//		Point[] array = new Point[N];
//		int i=0;
//		Comparable[] sorted = new Comparable[N];
//		
//		while(!input.isEmpty()){
//			array[i] = new Point(input.readInt(),input.readInt());
//			array[i++].draw();
//		}
//		
//		double[] result = new double[N];		
//		for(int p=0;p<array.length;p++){
//			Arrays.sort(array,array[p].SLOPE_ORDER);
//			for(int q=0;q<array.length;q++){
//				StdOut.print(array[p].slopeTo(array[q])+" ");
//			}
//			StdOut.println("");
//			int count=1;
//			int k = 1;
//			for(;k<array.length ;k++){
//				if (array[p].slopeTo(array[k]) == array[p].slopeTo(array[k-1]) ){
//					count++;
//				}
//				else{
//					if(count>2){
//						Point[] tmp = new Point[count+1];
//						tmp[0] = array[p];
//						for(int i0=0,i1=k-1;i0<count&&i1>k-count-1;i0++,i1--){
//							tmp[i0+1] = array[i1];
//						}
//						Arrays.sort(tmp);
//						for(int i1=0;i1<count+1;i1++){
//							StdOut.print(tmp[i1]);
//							if (i1 < count)
//								StdOut.print(" -> ");
//						}
//						StdOut.println();
//					}
//					count = 1;
//				}
//			}
//			
//		}
//	}
//
//}


