<%@page import="wifi.com.dto.Bookmark"%>
<%@page import="wifi.com.controller.WifiService"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="wifi.com.dto.Wifi"%>
<%@page import="wifi.com.dao.TestDatabase"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>와이파이 상세 정보</title>
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
	
	a {
		text-decoration: none;
		color: black;
	}
	
	table {
			margin-left : auto;
			margin-right: auto;
			width: 50%;
		}
	
	th {
		border: solid 1px grey;
		border-collapse: collapse;
		color: white;
		background-color: #50C785;
		font-size: small;
	}
	
	td {
		border: solid 1px grey;
		border-collapse: collapse;
		color: black;
		background-color: white;
		font-size: small;
	}
	
</style>

</head>
<body>

<%

Wifi wifi = (Wifi)request.getAttribute("wifi");

/** 북마크 정보 가져오기 */

List<Bookmark> bookmarkList = (ArrayList) request.getAttribute("bookmarkList");
Bookmark bookmark = new Bookmark();

%>

<h1>와이파이 상세 정보</h1>

<div>
	<a href="./index.jsp"><button>홈</button></a>
	<a href="./history.do"><button>위치 히스토리 목록</button></a>
	<a href="./bookmark-list.do"><button>북마크 보기</button></a>
	<a href="./bookmark-group.do"><button>북마크 그룹 관리</button></a>
</div>

<form action="./wifi-detail.do?wifiMgrNo=<%=wifi.getWifiMgrNo() %>" method="post">
	<select name="bookmarkId">
		<% 
			for (int i = 0; i < bookmarkList.size(); i++) {
		%>
		<option value="<%=bookmarkList.get(i).getBookmarkId()%>"><%=bookmarkList.get(i).getBookmarkName() %></option>
		<% } %>
	</select>
	<button type="submit">북마크 추가하기</button>
</form>

<table>

	<tr><th>관리번호</th><td><%=wifi.getWifiMgrNo() %></td></tr>
	<tr><th>자치구</th><td><%=wifi.getBrg()%></td></tr>
	<tr><th>와이파이명</th><td><%=wifi.getName() %></td></tr>
	<tr><th>도로명주소</th><td><%=wifi.getAdd1() %></td></tr>
	<tr><th>상세주소</th><td><%=wifi.getAdd2() %></td></tr>
	<tr><th>설치위치(층)</th><td><%=wifi.getFloor() %></td></tr>
	<tr><th>설치유형</th><td><%=wifi.getInstall() %></td></tr>
	<tr><th>설치기관</th><td><%=wifi.getInstallBy() %></td></tr>
	<tr><th>서비스구분</th><td><%=wifi.getService() %></td></tr>
	<tr><th>망종류</th><td><%=wifi.getNetwork()%></td></tr>
	<tr><th>설치년도</th><td><%=wifi.getYear() %></td></tr>
	<tr><th>실내외구분</th><td><%=wifi.getInOut() %></td></tr>
	<tr><th>WIFI접속환경</th><td><%=wifi.getEnv() %></td></tr>
	<tr><th>x좌표</th><td><%=wifi.getLnt() %></td></tr>
	<tr><th>y좌표</th><td><%=wifi.getLat() %></td></tr>
	<tr><th>작업일자</th><td><%=wifi.getInstallDate()%></td></tr>

</table>

	

</body>
</html>