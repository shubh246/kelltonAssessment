package codingquestionone;



public class LongestSequence {
	String name;
	String LongestWord(String name) {
		String temp="";
		String words[]=name.split(" ");
		for(String s:words) {
			if(s.length()>temp.length()) {
				temp=s;
			}
		}
		
		String result="";
		for(int i = 0; i<temp.length();i++) {
			String start="*";
			result+= temp.charAt(i);
			for(int j=0; j<=i;j++) {
				result+="*";
			}
		}
		return result;
		
	}
	
public static void main(String args[]) {
	LongestSequence L=new LongestSequence();
	String s1=L.LongestWord("cat dog hello");
	System.out.println(s1);
}
}
