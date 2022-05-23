package question4;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;
public class beautifulString{
	static int beautyOfString(String string){
		int sum = 0;
		string = string.replaceAll("[^a-zA-Z]", "").toLowerCase();
		HashMap<Character,Integer> map = new HashMap<Character, Integer>();
		for(int i = 0; i<string.length(); i++){
			Character c = new Character(string.charAt(i));
			Integer val = map.get(c);
			if(val != null){
				map.put(c, new Integer(val+1));
			}
			else{
				map.put(c, new Integer(1));
			}
		}
		Integer[] values = map.values().toArray(new Integer[map.size()]);
		Arrays.sort(values);
		Collections.reverse(Arrays.asList(values));
		int temp = 26;
		for(int i = 0; i<values.length; i++){
			sum += (temp*values[i]);
			temp--;
		}
		return sum;
	}

public static void main(String args[]) {
	String s="ABbCcc";
	beautifulString B=new beautifulString();
	int a=B.beautyOfString(s);
	System.out.println(a);}
}
	



		
	
			