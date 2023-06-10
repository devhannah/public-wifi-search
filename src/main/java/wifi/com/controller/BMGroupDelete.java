package wifi.com.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/bookmark-group-delete.do")
public class BMGroupDelete extends HttpServlet {

	private static final String url = "jdbc:mariadb://localhost:3306/wifi";
	private static final String dbUserId = "root";
	private static final String dbPassword = "mysql";
	private static Connection connection = null;
	
	public void doGet (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String bookmarkIdParam = req.getParameter("bookmarkId");
		int bookmarkId = Integer.parseInt(bookmarkIdParam);
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			connection = DriverManager.getConnection(url, dbUserId, dbPassword);
			
			String sql = "delete from bookmark where BOOKMARK_ID = ?; ";
			
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, bookmarkId);
			
			int deleted = pstmt.executeUpdate();
			
			if (deleted > 0) {
				System.out.println("북마크 그룹 삭제 성공");
			} else {
				System.out.println("삭제할 데이터가 없습니다.");
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
		
        req.setAttribute("bookmarkId", bookmarkId);			
        resp.sendRedirect("./bookmark-group.do");
        
		
	}
	
}
