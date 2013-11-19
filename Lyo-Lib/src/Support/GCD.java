package Support;

public class GCD {
	public Integer gcd(int x, int y){
		if(x==0) return y;
		if(y==0) return x;
		if(x>y) return gcd(x%y,y);
		else return gcd(x,y%x);
	}
	
	public static void main(String[] args){
		GCD g = new GCD();
		System.out.println(g.gcd(6,2));
	}
}
