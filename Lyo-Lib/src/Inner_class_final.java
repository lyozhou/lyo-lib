
/*----------------------------------------------------------------
 *  Author:        Lyo Zhou
 *  Written:       2013��8��28��       
 *        
 * 
 *  Purpose: Ϊʲô�����ڲ����������Ϊfinal����
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
 