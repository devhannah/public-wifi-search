import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.simple.JSONValue;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



public class Test {
	
	
	public static void main(String[] args) throws IOException {

        /*URL*/
        StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088");

        // http://openapi.seoul.go.kr:8088/(인증키)/xml/TbPublicWifiInfo/1/5/
        // CardSubwayStatsNew
        urlBuilder.append("/" + URLEncoder.encode("525857575668616e35337971796a4c","UTF-8") );
        /*인증키 (sample사용시에는 호출시 제한됩니다.)*/
        urlBuilder.append("/" + URLEncoder.encode("json","UTF-8") );
        /*요청파일타입 (xml,xmlf,xls,json) */
        urlBuilder.append("/" + URLEncoder.encode("TbPublicWifiInfo","UTF-8"));
        /*서비스명 (대소문자 구분 필수입니다.)*/
        urlBuilder.append("/" + URLEncoder.encode("1","UTF-8"));
        /*요청시작위치 (sample인증키 사용시 5이내 숫자)*/
        urlBuilder.append("/" + URLEncoder.encode("50","UTF-8"));
        /*요청종료위치(sample인증키 사용시 5이상 숫자 선택 안 됨)*/

        // 상위 5개는 필수적으로 순서바꾸지 않고 호출해야 합니다.
        // 서비스별 추가 요청 인자이며 자세한 내용은 각 서비스별 '요청인자'부분에 자세히 나와 있습니다.
        urlBuilder.append("/" + URLEncoder.encode("송파구","UTF-8"));

        /* 서비스별 추가 요청인자들*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        /* 연결 자체에 대한 확인이 필요하므로 추가합니다.*/

        BufferedReader rd;
        // 서비스코드가 정상이면 200~300사이의 숫자가 나옵니다.

        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream())); }

        String result = rd.readLine();
        
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        
        JSONParser parser = new JSONParser();
        
		JSONObject jsonObject; // 파싱할 object
		JSONObject tbPublicWifiInfoAll; // TbPublicWifiInfo
		// JSONObject tbPublicWifiTotal; // 와이파이 개수
		JSONArray tbPublicWifiResult; // 와이파이 총 정보들
		JSONArray tbPublicWifiRows;
		JSONObject tbPublicWifiRow; // 와이파이 개별 정보들
		
		try {
			jsonObject = (JSONObject) parser.parse(result);
			System.out.println(jsonObject);
			
			tbPublicWifiInfoAll = (JSONObject) jsonObject.get("TbPublicWifiInfo");
			System.out.println(tbPublicWifiInfoAll);
			// System.out.println(tbPublicWifiInfoAll.containsKey("list_total_count"));
			
			String tbPublicWifiTotal = tbPublicWifiInfoAll.get("list_total_count").toString();
			System.out.println(tbPublicWifiTotal);
			
			tbPublicWifiRows = (JSONArray) tbPublicWifiInfoAll.get("row");
			System.out.println(tbPublicWifiRows);
			
			for (int i = 0; i < tbPublicWifiRows.size(); i++) {
				tbPublicWifiRow = (JSONObject)tbPublicWifiRows.get(i);
				System.out.print(tbPublicWifiRow.get("X_SWIFI_ADRES1"));
			}
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());
    }
	
	
}