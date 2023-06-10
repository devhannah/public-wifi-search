package wifi.com.controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import wifi.com.dto.History;
import wifi.com.dto.Wifi;

public class HistoryService {

	private static final String url = "jdbc:mariadb://localhost:3306/wifi";
	private static final String dbUserId = "root";
	private static final String dbPassword = "mysql";
	private static Connection connection = null;
	
	/* 위치 조회 저장, 삭제 */
	
	public void save(String lat, String lnt) {
		
		PreparedStatement pstmt = null;
		
		LocalDateTime time = LocalDateTime.now();
		Wifi wifi = new Wifi();
		wifi.setLat(lat);
		wifi.setLnt(lnt);
		
		try {
			connection = DriverManager.getConnection(url, dbUserId, dbPassword);
	
			/* 위치 히스토리 저장 */
			String sql = "insert into history "
					+ "(LAT, LNT, VIEW_DATE) "
					+ "values (?, ?, ?)";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, wifi.getLat());
			pstmt.setString(2, wifi.getLnt());
			pstmt.setTimestamp(3, Timestamp.valueOf(time));
			
			int affected = pstmt.executeUpdate();
			
			if (affected > 0) {
				System.out.println("history 저장 성공");
			} else {
				System.out.println("history 저장 실패");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			try {
				if (pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if (connection != null && !connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}	
		
	}
	
	public void delete(int historyId) {
		
		
		History history = new History();
		history.setHistoryId(historyId);
		
		PreparedStatement pstmt = null;
		
		try {
			connection = DriverManager.getConnection(url, dbUserId, dbPassword);
	
			/* 위치 히스토리 삭제 */
			String sql = "delete from history "
					+ "where HISTORY_ID= ?";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, history.getHistoryId());
			
			int deleted = pstmt.executeUpdate();
			
			if (deleted > 0) {
				System.out.println("history 삭제 성공");
			} else {
				System.out.println("history 삭제 실패");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			
			try {
				if (pstmt != null && !pstmt.isClosed()) {
					pstmt.close();
					
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if (connection != null && !connection.isClosed()) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}	
		
	}
	
}
