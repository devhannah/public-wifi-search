<%@page import="wifi.com.controller.HistoryService"%>
<%@page import="wifi.com.dao.TestDatabase"%>
<%@page import="java.util.ArrayList"%>
<%@page import="wifi.com.dto.Wifi"%>
<%@page import="java.util.List"%>
<%@page import="wifi.com.controller.WifiService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>와이파이 정보 구하기</title>
<style>

	body {
		text-align : center;
	}
	
	div {
		margin: 50px auto;
		align-items: center;		
		width: auto;
	}
	
	button {
		background-color: lightgrey;
		margin: auto;
		border: solid 1px grey;
	}
	
	table {
		margin-left : auto;
		margin-right: auto;
		width: 100%;
	}
	
	th {
		border: solid 1px grey;
		border-collapse: collapse;
		color: white;
		background-color: #50C785;
		font-size: small;
	}
	
	tr {
		border: solid 1px grey;
		border-collapse: collapse;
		color: black;
		background-color: white;
		font-size: small;
	}
	
	button > a {
		text-decoration: none;
		color: black;
	}
	
	#lat, #lnt {
		text-align: center;
	}
</style>
</head>
<body>

	<% 
		TestDatabase.getConnection();
		WifiService wifiService = new WifiService();
		HistoryService historyService = new HistoryService();
		Wifi wifis = new Wifi();
		String lat = request.getParameter("lat");
		String lnt = request.getParameter("lnt");
		if (lat != null && lnt != null) {
			historyService.save(lat, lnt);			
		}

	%>
	
	<div>
		<h1>와이파이 정보 구하기</h1>
	</div>
	
	<div>
		<a href="./index.jsp"><button>홈</button></a>
		<a href="./history.do"><button>위치 히스토리 목록</button></a>
		<a href="./bookmark-list.do"><button>북마크 보기</button></a>
		<a href="bookmark-group.do"><button>북마크 그룹 관리</button></a>
	</div>
	
	<div>
		<a href="./load-wifi.jsp"><button>Open API 와이파이 정보 가져오기</button></a>
	</div>
	
	<form method="get">
		<div>
			LAT: <input type="text" id="lat" name="lat"></input>
			LNT: <input type="text" id="lnt" name="lnt"></input>
			<button id="myLocation"> 내 위치 가져오기 </button>
			<button type="submit"> 근처 와이파이 정보 가져오기 </button>
		</div>
	</form>
	
	<table>
		<thead>
			<tr>
				<th>거리(Km)</th>
				<th>관리번호</th>
				<th>자치구</th>
				<th>와이파이명</th>
				<th>도로명주소</th>
				<th>상세주소</th>
				<th>설치위치(층)</th>
				<th>설치유형</th>
				<th>설치기관</th>
				<th>서비스구분</th>
				<th>망종류</th>
				<th>설치년도</th>
				<th>실내외구분</th>
				<th>WIFI접속환경</th>
				<th>X좌표</th>
				<th>Y좌표</th>
				<th>작업일자</th>
			</tr>
		</thead>
		<tbody>		
				<% 
					if (lat == null && lnt == null) {
				%>
			<tr>
				<td colspan="17" style="padding: 100px"> 위치를 조회해주세요 </td>
			</tr>
			<tr>
				<% 	}
				else {
					 List<Wifi> wifiList = wifiService.searchList(wifis, lat, lnt);
					for (Wifi wifi : wifiList) {
				%>
				<td><%=wifi.getDistance().substring(0,8) %></td>
				<td><%=wifi.getWifiMgrNo() %></td>
				<td><%=wifi.getBrg() %></td>
				<td>
					<a href="./wifi-detail.do?wifiMgrNo=<%=wifi.getWifiMgrNo()%>"><%=wifi.getName() %>
					</a>
				</td>
				<td><%=wifi.getAdd1() %></td>
				<td><%=wifi.getAdd2() %></td>
				<td><%=wifi.getFloor() %></td>
				<td><%=wifi.getInstall() %></td>
				<td><%=wifi.getInstallBy() %></td>
				<td><%=wifi.getService() %></td>
				<td><%=wifi.getNetwork() %></td>
				<td><%=wifi.getYear() %></td>
				<td><%=wifi.getInOut() %></td>
				<td><%=wifi.getEnv() %></td>
				<td><%=wifi.getLnt() %></td>
				<td><%=wifi.getLat() %></td>
				<td><%=wifi.getInstallDate() %></td>
			</tr>
				<% }
				} %>
		</tbody>
	</table>
	
	<script src="public/geo-location.js"></script>

</body>
</html>