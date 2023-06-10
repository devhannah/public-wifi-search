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
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wifi.com.dto.Bookmark;
import wifi.com.dto.Wifi;
import wifi.com.dao.*;

@WebServlet("/wifi-detail.do")
public class WifiDetail extends HttpServlet{

	private static final String url = "jdbc:mariadb://localhost:3306/wifi";
	private static final String dbUserId = "root";
	private static final String dbPassword = "mysql";
	private static Connection connection = null;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		/* 한글 깨짐 방지 */
		resp.setContentType("text/html; charset=UTF-8");
		
		/* 북마크 가져오기 */
		List<Bookmark> bookmarkList = new ArrayList<>();
		Bookmark bookmark = new Bookmark();
		
		String wifiMgrNo = req.getParameter("wifiMgrNo");
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		if (wifiMgrNo != null) {
			
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			Wifi wifi = new Wifi();
			
			try {
				connection = DriverManager.getConnection(url, dbUserId, dbPassword);
				
				String sql = "select X_SWIFI_MGR_NO, DISTANCE, X_SWIFI_WRDOFC, X_SWIFI_MAIN_NM,"
						+ " X_SWIFI_ADRES1, X_SWIFI_ADRES2, X_SWIFI_INSTL_FLOOR, X_SWIFI_INSTL_TY,"
						+ " X_SWIFI_INSTL_MBY, X_SWIFI_SVC_SE, X_SWIFI_CMCWR, X_SWIFI_CNSTC_YEAR,"
						+ " X_SWIFI_INOUT_DOOR, X_SWIFI_REMARS3, LAT, LNT, WORK_DTTM" 
							+ " from wifi where X_SWIFI_MGR_NO = ?";
				
				pstmt = connection.prepareStatement(sql);
				pstmt.setString(1, wifiMgrNo);
				
				rs = pstmt.executeQuery();
				
				if (rs.next()) {
					
					wifi.setWifiMgrNo(rs.getString("X_SWIFI_MGR_NO"));
					wifi.setBrg(rs.getString("X_SWIFI_WRDOFC"));
					wifi.setName(rs.getString("X_SWIFI_MAIN_NM"));
					wifi.setAdd1(rs.getString("X_SWIFI_ADRES1"));
					wifi.setAdd2(rs.getString("X_SWIFI_ADRES2"));
					wifi.setFloor(rs.getString("X_SWIFI_INSTL_FLOOR"));
					wifi.setInstall(rs.getString("X_SWIFI_INSTL_TY"));
					wifi.setInstallBy(rs.getString("X_SWIFI_INSTL_MBY"));
					wifi.setService(rs.getString("X_SWIFI_SVC_SE"));
					wifi.setNetwork(rs.getString("X_SWIFI_CMCWR"));
					wifi.setYear(rs.getString("X_SWIFI_CNSTC_YEAR"));
					wifi.setInOut(rs.getString("X_SWIFI_INOUT_DOOR"));
					wifi.setEnv(rs.getString("X_SWIFI_REMARS3"));
					wifi.setLat(rs.getString("LAT"));
					wifi.setLnt(rs.getString("LNT"));
					wifi.setInstallDate(rs.getString("WORK_DTTM"));
		
				}
				
				String bookmarkSql = "select BOOKMARK_ID, BM_NAME, BM_ORDER from bookmark";
				pstmt = connection.prepareStatement(bookmarkSql);
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					
					bookmark = new Bookmark();
					
					bookmark.setBookmarkId(rs.getInt("BOOKMARK_ID"));
					bookmark.setBookmarkName(rs.getString("BM_NAME"));
					bookmark.setBookmarkOrder(rs.getInt("BM_ORDER"));
					bookmarkList.add(bookmark);
					System.out.println("bookmarkList: "+bookmarkList);
					
				}
				
				req.setAttribute("wifi", wifi);
				req.setAttribute("bookmarkList", bookmarkList);
				req.getRequestDispatcher("wifi-detail.jsp").forward(req, resp);
					
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
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

        } else {
        	
        	resp.sendRedirect("./index.jsp");
        	
        }
        
	}
		
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		/* 한글 깨짐 방지 */
		req.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");
		
		/* 파라미터 정보 넘겨 받기 */
		String wifiMgrNo = req.getParameter("wifiMgrNo");
		Wifi wifi = new Wifi();
		wifi.setWifiMgrNo(wifiMgrNo);
		
		Bookmark bookmark = new Bookmark();
		String bookmarkId = req.getParameter("bookmarkId");
		bookmark.setBookmarkId(Integer.parseInt(bookmarkId));
		String bookmarkName = req.getParameter("bookmarkName");
		bookmark.setBookmarkName(bookmarkName);
		
		/* 북마크 그룹 정보 넘겨 받기 */
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
					+ "BOOKMARK_ID=? "
					+ "where X_SWIFI_MGR_NO= ?;";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, wifi.getBookmarkId());
			pstmt.setString(2, wifi.getWifiMgrNo());
			
			System.out.println("=== 넘겨준 값 ===");
			System.out.println(wifi.getBookmarkId());
			System.out.println(wifi.getWifiMgrNo());
			
			
			int updated = pstmt.executeUpdate();
			
			if (updated > 0) {
				System.out.println("북마크 추가 성공");
			} else {
				System.out.println("북마크 추가 실패");
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
	
