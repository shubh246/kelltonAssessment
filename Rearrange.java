package question4;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class Rearrange {
	public static void main(String[] args) throws IOException {
		File file = new File("C:\\Users\\HP\\Documents\\rearrange.txt");
		BufferedReader buffer = new BufferedReader(new FileReader(file));
		String line;
		while ((line = buffer.readLine()) != null) {
			line = line.trim();

				System.out.println(getPrint(line));

		}
	}

	private static String getPrint(String line) {
		String input1 = line.split(";")[0];
		String input2 = line.split(";")[1];

		String [] wordArr = input1.split(" ");

		String [] keyArr = input2.split(" ");


		List<String> wordList = new LinkedList<String>(Arrays.asList(wordArr));

		StringBuffer sb = new StringBuffer();

		int maxLoop = keyArr.length;

		if (keyArr.length >wordArr.length) {
			maxLoop = wordArr.length;
		}

		String [] resultArr =  new String [wordArr.length];
		for (int i = 0; i< maxLoop; i++){
			resultArr [Integer.parseInt(keyArr[i])-1] = (wordArr[i]+ " ");

			wordList.remove(wordArr[i]);
		}

			for (int t = 0; t <wordArr.length; t++){
				if (resultArr[t] != null)
					sb.append(resultArr[t]);
				else {
					sb.append(wordList.get(0)+" ");
					wordList.remove(0);
				}
			}



		return removeLast(sb.toString(), " ");



	}



	private static String removeLast(String input, String lastChar){
		String result = input;
		if (input.contains(lastChar)){
			result = input.substring(0,input.lastIndexOf(lastChar));
		}
		return result;
	}}