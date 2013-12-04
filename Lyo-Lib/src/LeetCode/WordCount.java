package LeetCode;

/*----------------------------------------------------------------
 *  Author:        Lyo Zhou
 *  Written:       2013Äê12ÔÂ2ÈÕ       
 *        
 * 
 *  Purpose: dynamic plan to solve wordCount
 *  
 *
 *----------------------------------------------------------------*/

public class WordCount {
	private static boolean containsWord(String keyWord){
		String[] dictionary = {"mobile","samsung","sam","sung","man","mango",
                "icecream","and","go","i","like","ice","cream"};
		for(int i=0;i<dictionary.length;i++){
			if(keyWord.compareTo(dictionary[i])==0){
				return true;
			}
		}
		return false;
	}
	
	private static boolean wordCount(String word){
		boolean[] containFlag = new boolean[word.length()+1];
		if (word.length() == 0)   return true;
		for(int i=1;i<word.length()+1;i++){
			if(containFlag[i]== false && containsWord(word.substring(0, i))){
				containFlag[i] = true;
			}
			if(containFlag[i]==true){
				if(i==word.length()){
					return true;
				}
				for(int j=i+1;j<word.length()+1;j++){
					String tmp = word.substring(i,j);
					if(containFlag[j]==false && containsWord(word.substring(i,j))){
						containFlag[j] = true;
					}
					if(j==word.length()&&containFlag[j]==true){
						return true;
					}
				}
				
			}
		}
//		for (int i = 1; i < word.length()+1; i++)
//	        System.out.print(containFlag[i]);
//		System.out.println("");
		
		return false;
	}
	public static void main(String[] args){
		System.out.println(wordCount("ilikesamsung"));
		System.out.println(wordCount("iiiiiiii"));
		System.out.println(wordCount(""));
		System.out.println(wordCount("ilikelikeimangoiii"));
		System.out.println(wordCount("samsungandmango"));
		System.out.println(wordCount("samsungandmangok"));
	}
}
 