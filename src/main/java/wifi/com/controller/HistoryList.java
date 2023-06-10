package wifi.com.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import wifi.com.dto.History;

@WebServlet("/history.do")
public class HistoryList extends HttpServlet {

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
		History history = new History();
		List<History> historyList = new ArrayList<History>();
		
		try {
			connection = DriverManager.getConnection(url, dbUserId, dbPassword);
		
			String sql = "select history_id, LAT, LNT, VIEW_DATE "
					+ "from history order by history_id desc";
			
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				
				int historyId = rs.getInt("history_id");
				String lat = rs.getString("LAT");
				String lnt = rs.getString("LNT");
				Date viewDate = rs.getTimestamp("VIEW_DATE");

				history = new History();
				
				history.setHistoryId(historyId);
				history.setLat(lat);
				history.setLnt(lnt);
				history.setViewDate(viewDate);
				
				historyList.add(history);
				
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
 
        req.setAttribute("historyList", historyList);
        req.getRequestDispatcher("history.jsp").forward(req, resp);
		
	}
	
}
