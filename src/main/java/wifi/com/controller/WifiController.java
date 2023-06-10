package wifi.com.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import wifi.com.dto.Wifi;

@WebServlet("/load-wifi.jsp")
public class WifiController extends HttpServlet{
	
	private final static WifiService wifiService = new WifiService();
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		/* 한글 깨짐 방지 */
		resp.setContentType("text/html; charset=UTF-8");
		
		try {
			Class.forName("org.mariadb.jdbc.Driver");			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		String result;
		
        /* URL */
        StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088");

        /* URL 1번에 1000개 받아오기 때문에 for문 돌려서 다 받아오기 */
        int j = 1000;
        int start = 1;
        int end = 30000;
        ArrayList<String> urlBuilders = new ArrayList<String>();
        
		for (int i = start; i < end; i += 1000) {
			
			if (i == 24001) break;

            // http://openapi.seoul.go.kr:8088/(인증키)/json/TbPublicWifiInfo/1/5/
            urlBuilder.append("/" + URLEncoder.encode("525857575668616e35337971796a4c","UTF-8") );
            urlBuilder.append("/" + URLEncoder.encode("json","UTF-8") );
            urlBuilder.append("/" + URLEncoder.encode("TbPublicWifiInfo","UTF-8"));
            urlBuilder.append("/" + i + URLEncoder.encode("","UTF-8"));
            urlBuilder.append("/" + j + URLEncoder.encode("","UTF-8"));
           
            j += 1000;
		
            urlBuilders.add(urlBuilder.toString());
            urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088");
            
		}
     
        /* 서비스별 추가 요청인자들 */
        // urlBuilder.append("/" + URLEncoder.encode("송파구","UTF-8"));
        
        /* jsp에 넘겨줄 와이파이 개수 */
        String tbPublicWifis = "";
        
        /** for문 **/
		for (int i = 0; i < urlBuilders.size(); i++) {
			
			URL url = new URL(urlBuilders.get(i).toString());
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-type", "application/json");
			
			/* 연결 자체에 대한 확인이 필요하므로 추가 */
			System.out.println("Response code: " + conn.getResponseCode());
			
			BufferedReader rd;
			/* 서비스코드가 정상이면 200 ~ 300 사이의 숫자 출력 */
			
			if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
				rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			} else {
				rd = new BufferedReader(new InputStreamReader(conn.getErrorStream())); }
			
			result = rd.readLine();
		
	        /* JSON Parsing 후 DB에 저장하기 */
	        JSONParser parser = new JSONParser();
	        
	        /* 파싱할 object */
			JSONObject jsonObject; 
	
			/* TbPublicWifiInfo */
			JSONObject tbPublicWifiInfoAll; 
			
			/* 와이파이 개수 */
			String tbPublicWifiTotal; 
			
			/* 와이파이 총 정보 */
			JSONArray tbPublicWifiRows;
			
			/* 와이파이 개별 정보들 */
			JSONObject tbPublicWifiRow; 
			
			try {
				
				jsonObject = (JSONObject) parser.parse(result);
				
				tbPublicWifiInfoAll = (JSONObject) jsonObject.get("TbPublicWifiInfo");	
				
				tbPublicWifiTotal = tbPublicWifiInfoAll.get("list_total_count").toString();
				
				/* jsp에 넘길 와이파이 총 개수 */
				tbPublicWifis = tbPublicWifiTotal;
				
				tbPublicWifiRows = (JSONArray) tbPublicWifiInfoAll.get("row");
				
				Wifi wifi = new Wifi();
				List<Wifi> wifiList = new ArrayList<>();
				
				for (int k = 0; k < tbPublicWifiRows.size(); k++) {
					
					tbPublicWifiRow = (JSONObject)tbPublicWifiRows.get(k);
					
					/* Wifi에 넘기는 부분 */
					String wifiMgrNo = (String) tbPublicWifiRow.get("X_SWIFI_MGR_NO");
					String distance = "";
					String brg = (String) tbPublicWifiRow.get("X_SWIFI_WRDOFC");
					String name = (String) tbPublicWifiRow.get("X_SWIFI_MAIN_NM");
					String add1 = (String) tbPublicWifiRow.get("X_SWIFI_ADRES1");
					String add2 = (String) tbPublicWifiRow.get("X_SWIFI_ADRES2");
					String floor = (String) tbPublicWifiRow.get("X_SWIFI_INSTL_FLOOR");
					String install = (String) tbPublicWifiRow.get("X_SWIFI_INSTL_TY");
					String installBy = (String) tbPublicWifiRow.get("X_SWIFI_INSTL_MBY");
					String service = (String) tbPublicWifiRow.get("X_SWIFI_SVC_SE");
					String network = (String) tbPublicWifiRow.get("X_SWIFI_CMCWR");
					String year = (String) tbPublicWifiRow.get("X_SWIFI_CNSTC_YEAR");
					String inOut = (String) tbPublicWifiRow.get("X_SWIFI_INOUT_DOOR");
					String env = (String) tbPublicWifiRow.get("X_SWIFI_REMARS3");
					String lat = (String) tbPublicWifiRow.get("LAT");
					String lnt = (String) tbPublicWifiRow.get("LNT");
					String installDate = (String) tbPublicWifiRow.get("WORK_DTTM");
					int bookmarkId = 0;
					
					/* DTO */
					wifi = new Wifi(wifiMgrNo, distance, brg, name, add1, add2, floor, install, installBy,
							service, network, year, inOut, env, lat, lnt, installDate, bookmarkId);
					
					wifiList.add(wifi);
					
				}
				/* CONTROLLER */
				wifiService.save(wifiList);
				
			} catch (ParseException e) {
				
				e.printStackTrace();
				
			}
			
	        rd.close();
	        conn.disconnect();
        
		}

		/* jsp에 넘기기 */
		StringBuffer sb = new StringBuffer();
		sb.append("<title> 와이파이 정보 가져오기 </title>");
		sb.append("<style> body { text-align : center; } div { text-decoration : none; } </style>");
		sb.append("<h1>" + tbPublicWifis + "개의 WIFI 정보를 정상적으로 저장하였습니다. </h1>");
		sb.append("<div><a href='" + "./index.jsp'> 홈으로 가기 </a></div>");
		resp.getWriter().append(sb);
		
	}
	
	
}
