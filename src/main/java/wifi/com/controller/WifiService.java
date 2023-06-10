package wifi.com.controller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import wifi.com.dao.TestDatabase;
import wifi.com.dto.Wifi;

public class WifiService {
	
	private static final String url = "jdbc:mariadb://localhost:3306/wifi";
	private static final String dbUserId = "root";
	private static final String dbPassword = "mysql";
	private static Connection connection = null;
	

	/* Open Api 호출해서 DB 저장하기 (속도 향상) */
	public void save(List<Wifi> wifiList) {
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		int cur = 0;
		int idx = 0;
		
		try {
			
			connection = DriverManager.getConnection(url, dbUserId, dbPassword);
			
			String sql = " insert into wifi (X_SWIFI_MGR_NO, DISTANCE, X_SWIFI_WRDOFC, X_SWIFI_MAIN_NM,"
					+ " X_SWIFI_ADRES1, X_SWIFI_ADRES2, X_SWIFI_INSTL_FLOOR, X_SWIFI_INSTL_TY, X_SWIFI_INSTL_MBY,"
					+ " X_SWIFI_SVC_SE, X_SWIFI_CMCWR, X_SWIFI_CNSTC_YEAR, X_SWIFI_INOUT_DOOR, X_SWIFI_REMARS3,"
					+ " LAT, LNT, WORK_DTTM) " +
                    " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
			
			pstmt = connection.prepareStatement(sql);
			
			for (int i = 0; i < wifiList.size(); i++) {
				
				pstmt.setString(1, wifiList.get(i).getWifiMgrNo());
				pstmt.setString(2, wifiList.get(i).getDistance());
				pstmt.setString(3, wifiList.get(i).getBrg());
				pstmt.setString(4, wifiList.get(i).getName());
				pstmt.setString(5, wifiList.get(i).getAdd1());
				pstmt.setString(6, wifiList.get(i).getAdd2());
				pstmt.setString(7, wifiList.get(i).getFloor());
				pstmt.setString(8, wifiList.get(i).getInstall());
				pstmt.setString(9, wifiList.get(i).getInstallBy());
				pstmt.setString(10, wifiList.get(i).getService());
				pstmt.setString(11, wifiList.get(i).getNetwork());
				pstmt.setString(12, wifiList.get(i).getYear());
				pstmt.setString(13, wifiList.get(i).getInOut());
				pstmt.setString(14, wifiList.get(i).getEnv());
				pstmt.setString(15, wifiList.get(i).getLat()); 
				pstmt.setString(16, wifiList.get(i).getLnt()); 
				pstmt.setString(17, wifiList.get(i).getInstallDate());
				
				pstmt.addBatch();
				if (i % 100 == 0) {
					cur = i;
					pstmt.executeBatch();
					pstmt.clearBatch();
					pstmt.clearParameters();
				}
			}
			
			if (cur < wifiList.size()) {
				pstmt.executeBatch();
				pstmt.clearBatch();
				pstmt.clearParameters();
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

	/** 자바로 조회하고 업데이트하고 저장하는 과정이 오래 걸려서 sql 쿼리를 사용해 처리하는 방식으로 바꿈 (시행착오) */

	/* 내 위치 기반 주변 와이파이 20개 뽑아오기 */
	public List<Wifi> searchList(Wifi wifi, String lat, String lnt) {

		List<Wifi> wifiList = new ArrayList<Wifi>();
		wifi = new Wifi();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			connection = DriverManager.getConnection(url, dbUserId, dbPassword);
			
			String sql = "select "
					+ " X_SWIFI_MGR_NO, X_SWIFI_WRDOFC, X_SWIFI_MAIN_NM, X_SWIFI_ADRES1, "
					+ " X_SWIFI_ADRES2, X_SWIFI_INSTL_FLOOR, X_SWIFI_INSTL_TY, "
					+ " X_SWIFI_INSTL_MBY, X_SWIFI_SVC_SE, X_SWIFI_CMCWR, "
					+ " X_SWIFI_CNSTC_YEAR, X_SWIFI_INOUT_DOOR, X_SWIFI_REMARS3, "
					+ " LAT, LNT, WORK_DTTM, "
					+ "(6371 * acos "
					+ "(cos (radians(?)) "
					+ "*cos(radians(LAT)) "
					+ "*cos(radians(LNT)-radians(?)) "
					+ "+sin(radians(?)) "
					+ "*sin(radians(LAT)))) "
					+ "as distance "
					+ "from wifi "
					+ "order by distance asc limit 20";
			
			pstmt = connection.prepareStatement(sql);
			
			pstmt.setString(1, lnt);
			pstmt.setString(2, lat);
			pstmt.setString(3, lnt);
			
			rs= pstmt.executeQuery();
			
			while (rs.next()) {
				
				String wifiMgrNo = rs.getString("X_SWIFI_MGR_NO");
				String dist = rs.getString("distance");
				String brg = rs.getString("X_SWIFI_WRDOFC");
				String name = rs.getString("X_SWIFI_MAIN_NM");
				String add1 = rs.getString("X_SWIFI_ADRES1");
				String add2 = rs.getString("X_SWIFI_ADRES2");
				String floor = rs.getString("X_SWIFI_INSTL_FLOOR");
				String install = rs.getString("X_SWIFI_INSTL_TY");
				String installBy = rs.getString("X_SWIFI_INSTL_MBY");
				String service = rs.getString("X_SWIFI_SVC_SE");
				String network = rs.getString("X_SWIFI_CMCWR");
				String year = rs.getString("X_SWIFI_CNSTC_YEAR");
				String inOut = rs.getString("X_SWIFI_INOUT_DOOR");
				String env = rs.getString("X_SWIFI_REMARS3");
				String latFin = rs.getString("LAT");
				String lntFin = rs.getString("LNT");
				String installDate = rs.getString("WORK_DTTM");
				
				wifi = new Wifi();
				
				wifi.setDistance(dist);	
				wifi.setWifiMgrNo(wifiMgrNo);
				wifi.setBrg(brg);
				wifi.setName(name);
				wifi.setAdd1(add1);
				wifi.setAdd2(add2);
				wifi.setFloor(floor);
				wifi.setInstall(install);
				wifi.setInstallBy(installBy);
				wifi.setService(service);
				wifi.setNetwork(network);
				wifi.setYear(year);
				wifi.setInOut(inOut);
				wifi.setEnv(env);
				wifi.setLat(latFin);
				wifi.setLnt(lntFin);
				wifi.setInstallDate(installDate);
				
				wifiList.add(wifi);	
				
			}
			
		} catch (SQLException e){
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
		
		return wifiList;
		
	}
		
}
