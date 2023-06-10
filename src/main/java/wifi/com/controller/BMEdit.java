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
import wifi.com.dto.Wifi;

@WebServlet("/bookmark-delete.do")
public class BMEdit extends HttpServlet {

	private static final String url = "jdbc:mariadb://localhost:3306/wifi";
	private static final String dbUserId = "root";
	private static final String dbPassword = "mysql";
	private static Connection connection = null;
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		/* 한글 깨짐 방지 */
		resp.setContentType("text/html; charset=UTF-8");
		
		/* 북마크 가져오기 */
		Bookmark bookmark = new Bookmark();
		
		String wifiMgrNo = req.getParameter("wifiMgrNo");
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Wifi wifi = new Wifi();
		
		try {
			connection = DriverManager.getConnection(url, dbUserId, dbPassword);
			
			String sql = " select b.BOOKMARK_ID, b.BM_NAME, b.BM_ENROLL, "
					+ "w.X_SWIFI_MAIN_NM, w.X_SWIFI_MGR_NO from bookmark b join wifi w "
					+ "on b.BOOKMARK_ID = w.BOOKMARK_ID and w.X_SWIFI_MGR_NO=?;";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, wifiMgrNo);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				
				int bookmarkId = rs.getInt("BOOKMARK_ID");
				String bookmarkName = rs.getString("BM_NAME");
				Date bookmarkEnroll = rs.getTimestamp("BM_ENROLL");
				String name = rs.getString("X_SWIFI_MAIN_NM");
				String mgrNo = rs.getString("X_SWIFI_MGR_NO");
				int wifiBookmarkId = rs.getInt("BOOKMARK_ID");
				
				bookmark = new Bookmark();
				
				bookmark.setBookmarkId(bookmarkId);
				bookmark.setBookmarkName(bookmarkName);
				bookmark.setBookmarkEnroll(bookmarkEnroll);
				System.out.println("bookmark"+bookmark);
				
				wifi = new Wifi();
				wifi.setName(name);
				wifi.setBookmarkId(bookmarkId);
				wifi.setWifiMgrNo(mgrNo);
				System.out.println("wifi: "+wifi);
				
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
		
        req.setAttribute("bookmark", bookmark);
        req.setAttribute("wifi",wifi);
        req.getRequestDispatcher("bookmark-delete.jsp").forward(req, resp);
		
		
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		/* 한글 깨짐 방지 */
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");

		String bookmarkId = req.getParameter("bookmarkId");
		Bookmark bookmark = new Bookmark();
		bookmark.setBookmarkId(Integer.parseInt(bookmarkId));
		String bookmarkName = req.getParameter("bookmarkName");
		bookmark.setBookmarkName(bookmarkName);
		
		Wifi wifi = new Wifi();
		String wifiMgrno = req.getParameter("wifiMgrNo");
		wifi.setWifiMgrNo(wifiMgrno);
		wifi.setBookmarkId(bookmark.getBookmarkId());
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		
		try {
			connection = DriverManager.getConnection(url, dbUserId, dbPassword);
			
			String sql = "update wifi set "
					+ "BOOKMARK_ID= ? "
					+ "where X_SWIFI_MGR_NO= ?;";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, null);
			pstmt.setString(2, wifi.getWifiMgrNo());
			
			int updated = pstmt.executeUpdate();
			
			if (updated > 0) {
				System.out.println("북마크 삭제 성공");
			} else {
				System.out.println("북마크 삭제 실패");
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
		
        resp.sendRedirect("./bookmark-list.do");
		
	}
	
	
	
}
