import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class UnlimitedCache {
	HashMap<Integer, String> cache;
	ArrayList<Integer> keyList;
	Connection conn;
	long hit;
	long miss;

	public UnlimitedCache(ArrayList<Integer> keyList, Connection conn) {
		this.keyList = keyList;
		this.conn = conn;
		cache = new HashMap<Integer, String>();
		hit = 0;
		miss = 0;
	}

	public void service() {
		System.out.println("un limited cache");
		long time = System.currentTimeMillis();
		for (int i = 0; i < keyList.size(); i++) {
			int key = keyList.get(i);
			getValue(key);
		}
		time = System.currentTimeMillis() - time;
		System.out.println("진행시간 : " + time);
		System.out.println("hit : " + hit);
		System.out.println("miss : " + miss);
	}

	private String getValue(int key) {
		String value = "";
		if (cache.containsKey(key)) {
			value = getValueFromCache(key);
			hit++;
		} else {
			value = getValueFromRemote(key);
			changeCache(key, value);
			miss++;
		}
		return value;
	}

	private String getValueFromCache(int key) {
		String value = cache.get(key);
		return value;
	}

	private void changeCache(int key, String value) {
		cache.put(key, value);
	}

	private String getValueFromRemote(int key) {
		PreparedStatement pstmt = null;
		String value = null;
		String sql = "select * from test where k=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, key);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				value = rs.getString("v");
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return value;
	}
}
