import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class DataSetBuilder {
	public void service() throws IOException {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for(int i = 1; i<= 1000; i++){
			for(int j=0; j < 9 ; j++ ){
				list.add(i);
			}
		}

		for(int k = 0 ; k < 1000; k++){
			list.add(makeRan());
		}
		
		Collections.shuffle(list);
		
		FileWriter fw = new FileWriter("ranCal.txt");
		BufferedWriter bw = new BufferedWriter(fw);
		
		for(int l = 0 ; l < list.size(); l++){
			bw.write(list.get(l).toString());
			bw.newLine();
		}
		bw.close();
	}
	
	public int makeRan(){
		int max = 9000;
		int min = 1000;
		Random r = new Random();
		int ran = r.nextInt(max - min + 1) + min;
		return ran;
	}

}
