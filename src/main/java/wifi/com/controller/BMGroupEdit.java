package wifi.com.controller;

import java.io.IOException;

import java.io.UnsupportedEncodingException;
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
import wifi.com.dto.Wifi;

@WebServlet("/bookmark-group-edit.do")
public class BMGroupEdit extends HttpServlet{

	private static final String url = "jdbc:mariadb://localhost:3306/wifi";
	private static final String dbUserId = "root";
	private static final String dbPassword = "mysql";
	private static Connection connection = null;
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		/* 한글 깨짐 방지 */
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");

		Bookmark bookmark = new Bookmark();
		String bookmarkId = req.getParameter("bookmarkId");
		String bookmarkName = req.getParameter("bookmarkName");
		LocalDateTime time = LocalDateTime.now();
		String bookmarkOrder = req.getParameter("bookmarkOrder");
		bookmark.setBookmarkId(Integer.parseInt(bookmarkId));
		bookmark.setBookmarkName(bookmarkName);
		bookmark.setBookmarkOrder(Integer.parseInt(bookmarkOrder));	
		
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {
			connection = DriverManager.getConnection(url, dbUserId, dbPassword);
			
			String sql = "update bookmark set "
					+ "BM_NAME=?, BM_ORDER=?, BM_EDIT=? "
					+ "where BOOKMARK_ID= ?;";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, bookmark.getBookmarkName());
			pstmt.setInt(2, bookmark.getBookmarkOrder());
			pstmt.setTimestamp(3, Timestamp.valueOf(time));
			pstmt.setInt(4, bookmark.getBookmarkId());
			
			int updated = pstmt.executeUpdate();
			
			if (updated > 0) {
				System.out.println("북마크 그룹 수정 성공");
			} else {
				System.out.println("북마크 그룹 수정 실패");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {

            try {
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

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
