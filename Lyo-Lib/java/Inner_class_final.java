
/*----------------------------------------------------------------
 *  Author:        Lyo Zhou
 *  Written:       2013年8月28日       
 *        
 * 
 *  Purpose: 为什么匿名内部类参数必须为final类型
 *  
 *
 *----------------------------------------------------------------*/

public class Inner_class_final {
	public static void test(final String s){   //if var s isnot a final var,it will crash
		System.out.println("lyo");
		class ABCclass{
			public void innerTest(){
				String x = s;
				System.out.println(x);
			}
		}
		ABCclass abc = new ABCclass();
		abc.innerTest();
	}
	
	public static void main(String[] args){
		Inner_class_final icf = new Inner_class_final();
		icf.test("lyo");
	}
}
 