package wifi.com.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wifi.com.dto.Bookmark;

@WebServlet("/bookmark-group-add.do")
public class BMGroupInsert extends HttpServlet{

	private static final String url = "jdbc:mariadb://localhost:3306/wifi";
	private static final String dbUserId = "root";
	private static final String dbPassword = "mysql";
	private static Connection connection = null;
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		/* 한글 깨짐 방지 */
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");
		
		String bookmarkName = req.getParameter("bookmarkName");
		String bookmarkOrder = req.getParameter("bookmarkOrder");
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		PreparedStatement pstmt = null;
		
		LocalDateTime time = LocalDateTime.now();
		
		try {
			connection = DriverManager.getConnection(url, dbUserId, dbPassword);
			
			String sql = "insert into bookmark "
					+ "(BM_NAME, BM_ORDER, BM_ENROLL) "
					+ "values (?, ?, ?);";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, bookmarkName);
			pstmt.setInt(2, Integer.parseInt(bookmarkOrder));
			pstmt.setTimestamp(3, Timestamp.valueOf(time));
			
			int affected = pstmt.executeUpdate();
			
			if (affected > 0) {
				System.out.println("북마크 그룹 생성 성공");
			} else {
				System.out.println("북마크 그룹 생성 실패");
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
		
		resp.sendRedirect("./bookmark-group.do");
	}
	
	
}
