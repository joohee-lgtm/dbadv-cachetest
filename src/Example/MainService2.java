package Example;
import java.io.BufferedWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;


public class MainService2 {
	/*
	public static void main(String[] args) throws IOException{
		int data = 10000;
		String column =",";
		String row = "\n";

		FileWriter fw = new FileWriter("dataset.csv");
		BufferedWriter bw = new BufferedWriter(fw);
		
		for (int i=1 ; i<data+1 ; i++){
			String random = randomString().toString();
			StringBuffer temp = new StringBuffer();
			temp.append(i + column + random + row);
			System.out.println(temp.toString());
			bw.write(temp.toString());
		}
		bw.close();
	}
	
	public static StringBuffer randomString(){
		String str = new String("01234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%^&*_+=");
		int n = str.length();
		StringBuffer result = new StringBuffer();
		Random r = new Random();
		for (int i=0 ; i<10000 ; i++){
			result.append(str.charAt(r.nextInt(n)));
		}
		return result;
	}
	*/
}