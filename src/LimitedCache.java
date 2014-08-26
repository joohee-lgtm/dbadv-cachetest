import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;


public class LimitedCache {
	
	HashMap<Integer, String>cache;
	ArrayList<Integer>keyList = new ArrayList<Integer>();
	Connection conn = null;
	LinkedList<Integer> usedList;
	long hit = 0;
	long miss = 0;
	int LIMITED_SIZE;
	
	public LimitedCache(ArrayList<Integer> keyList , Connection conn, int limited){
		cache = new HashMap<Integer, String>();
		usedList = new LinkedList<Integer>();
		this.LIMITED_SIZE = limited;
		this.conn = conn;
		this.keyList = keyList;
		this.hit = 0;
		this.miss = 0;
	}
	
	public void service(){
		System.out.println("limited cache test");
		long time = System.currentTimeMillis();
		for (int i=0 ; i<keyList.size() ; i++){
			int key = keyList.get(i);
			getValue(key);
		}
		time = System.currentTimeMillis() - time;
		System.out.println("진행 시간 : " + time);
		System.out.println("hit : " + hit);
		System.out.println("miss : " + miss);
	}

	private String getValue(int key){
		String value = "";
		if(cache.containsKey(key)){
			getValueFromCache(key);
			hit++;
		} else {
			value = getValueFromRemote(key);
			if(cache.size() > LIMITED_SIZE)
				removeUnusualCache();
			addNewCache(key, value);
			miss++;
		}; 
		changeDataList(key);
		return value;
	}
	
	private String getValueFromCache(int key) {
		String value = cache.get(key);
		return value;
	}
	
	private void removeUnusualCache() {
		int lastKey = usedList.getLast();
		cache.remove(lastKey);
	}
	
	private void addNewCache(int key, String value) {
		cache.put(key, value);
	}
	
	private String getValueFromRemote(int key) {
		String value = "";
		PreparedStatement pstmt = null;
		String sql = "select * from test where k=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, key);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				value = rs.getString("v");
			}
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return value;
	}

	private void changeDataList(int key) {
		int idx = usedList.indexOf(key);
		if(idx > 0 )
			usedList.remove(idx);
		usedList.addFirst(key);
	}
}
