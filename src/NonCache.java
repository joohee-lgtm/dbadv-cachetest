import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class NonCache {
	ArrayList<Integer> keyList = new ArrayList<Integer>();
	Connection con;
	long hit;
	long miss;

	public NonCache(ArrayList<Integer> keyList, Connection con) {
		this.con = con;
		this.keyList = keyList;
		hit = 0;
		miss = 0;
	}

	public void service() {
		System.out.println("non cache test");
		long time = System.currentTimeMillis();
		for(int i=0; i<keyList.size(); i++){
			int key = keyList.get(i);
			getValue(key);
		}
		time = System.currentTimeMillis() - time;
		System.out.println("진행 시간 : " + time);
		System.out.println("hit : " + hit);
		System.out.println("miss : " + miss);
	}

	public void getValue(int key) {
		miss++;
		PreparedStatement pstmt = null;
		String sql = "select * from test where k=?";
		try {
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, key);
			pstmt.execute();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
