/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.awt.Color;
import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new Slope();

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }
    
    private class Slope implements Comparator<Point>{

		@Override
		public int compare(Point o1, Point o2) {
			// TODO Auto-generated method stub
			if(slopeTo(o1) > slopeTo(o2)){
				return 1;
			}else if(slopeTo(o1)<slopeTo(o2)){
				return -1;
			}
			else return 0;
		}
    	
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
    	StdDraw.setXscale(0, 100000);
    	StdDraw.setYscale(0, 100000);
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        if(this.compareTo(that) == 0){
        	return Float.NEGATIVE_INFINITY;
        }
        if(this.x == that.x){
        	return Float.POSITIVE_INFINITY;
        }
    	if(this.y == that.y){
        	return 0.0;
        }
    	return (double) (that.y-this.y)/(that.x-this.x);
        
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        if(this.y==that.y && this.x==that.x){
        	return 0;
        }
        if(this.y<that.y || (this.y == that.y && this.x<that.x)){
        	return -1;
        }
        return 1;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        Point a1 = new Point(3,4);
        Point a2 = new Point(500,600);
        a1.draw();
        a2.draw();
        a1.drawTo(a2);
        
    }
}
