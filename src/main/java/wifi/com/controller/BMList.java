package wifi.com.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

@WebServlet("/bookmark-list.do")
public class BMList extends HttpServlet {

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
		Wifi wifi = new Wifi();
		List<Wifi> wifiList = new ArrayList<Wifi>();
		
		
		try {
			connection = DriverManager.getConnection(url, dbUserId, dbPassword);
			
			String sql = " select b.BOOKMARK_ID, b.BM_NAME, b.BM_ENROLL, "
					+ "w.X_SWIFI_MAIN_NM, w.X_SWIFI_MGR_NO from bookmark b join wifi w "
					+ "on b.BOOKMARK_ID = w.BOOKMARK_ID;";
			
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				int bookmarkId = rs.getInt("BOOKMARK_ID");
				String bookmarkName = rs.getString("BM_NAME");
				Date bookmarkEnroll = rs.getTimestamp("BM_ENROLL");
				String name = rs.getString("X_SWIFI_MAIN_NM");
				String mgrNo = rs.getString("X_SWIFI_MGR_NO");
				
				bookmark = new Bookmark();
				
				bookmark.setBookmarkId(bookmarkId);
				bookmark.setBookmarkName(bookmarkName);
				bookmark.setBookmarkEnroll(bookmarkEnroll);
				
				wifi = new Wifi();
				wifi.setName(name);
				wifi.setBookmarkId(bookmarkId);
				wifi.setWifiMgrNo(mgrNo);
				
				wifiList.add(wifi);
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
        req.setAttribute("wifiList",wifiList);
        req.getRequestDispatcher("bookmark-list.jsp").forward(req, resp);
		
		
	}
	
	
}
