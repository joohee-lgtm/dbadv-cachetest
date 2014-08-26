import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class MainService {	
	static ArrayList<Integer> keyList = new ArrayList<Integer>();

	public static void main(String[] args) {
		
		/*
		// 2. 실험(시뮬레이션) 데이터 준비
		DataSetBuilder dsb = new DataSetBuilder();
		try {
			dsb.service();
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		Connection conn = createConnection();
		makeList();
		
		NonCache nct = new NonCache(keyList, conn);
		nct.service();
		
		UnlimitedCache ulc = new UnlimitedCache(keyList, conn);
		ulc.service();
		
		LimitedCache pct = new LimitedCache(keyList, conn, 800);
		pct.service();
	}
	
	public static Connection createConnection(){
		Connection conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:mysql://10.73.45.72/cacheTest", "root", "88");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	public static void makeList(){
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader("ranCal.txt"));
			String line;
			try {
				line = br.readLine();
				while(line != null){
					keyList.add(Integer.parseInt(line));
					line = br.readLine();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void connectionClose(Connection con) throws SQLException {
		con.close();
	}

}
