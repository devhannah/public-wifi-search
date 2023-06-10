package wifi.com.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wifi.com.dto.Bookmark;

@WebServlet("/bookmark-group.do")
public class BMGroupList extends HttpServlet {
	
	private static final String url = "jdbc:mariadb://localhost:3306/wifi";
	private static final String dbUserId = "root";
	private static final String dbPassword = "mysql";
	private static Connection connection = null;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		/* 한글 깨짐 방지 */
		resp.setContentType("text/html; charset=UTF-8");
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Bookmark bookmark = new Bookmark();
		List<Bookmark> bookmarkList = new ArrayList<Bookmark>();
		
		try {
			connection = DriverManager.getConnection(url, dbUserId, dbPassword);
		
			String sql = "select BOOKMARK_ID, BM_NAME, BM_ORDER, "
					+ "BM_ENROLL, BM_EDIT from bookmark;";
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				int bookmarkId = rs.getInt("BOOKMARK_ID");
				String bookmarkName = rs.getString("BM_NAME");
				int bookmarkOrder = rs.getInt("BM_ORDER");
				Date bookmarkEnroll = rs.getTimestamp("BM_ENROLL");
				Date bookmarkEdit = rs.getTimestamp("BM_EDIT");
		
				bookmark = new Bookmark();
				
				bookmark.setBookmarkId(bookmarkId);
				bookmark.setBookmarkName(bookmarkName);
				bookmark.setBookmarkOrder(bookmarkOrder);
				bookmark.setBookmarkEnroll(bookmarkEnroll);
				bookmark.setBookmarkEdit(bookmarkEdit);
				
				bookmarkList.add(bookmark);
				
			}
		
			
		} catch (SQLException e) {
			e.printStackTrace();
		} try {
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
        

        req.setAttribute("bookmarkList", bookmarkList);
        req.getRequestDispatcher("bookmark-group.jsp").forward(req, resp);
		
		
		
	}

}
