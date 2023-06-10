<%@page import="java.sql.Timestamp"%>
<%@page import="java.util.Date"%>
<%@page import="wifi.com.dto.Wifi"%>
<%@page import="java.util.List"%>
<%@page import="wifi.com.controller.BMGroupList"%>
<%@page import="wifi.com.dto.Bookmark"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>북마크 삭제</title>
<style>
body {
	text-align: center;
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

button>a {
	text-decoration: none;
	color: black;
}

table {
	margin-left: auto;
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

tr {
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
	BMGroupList bmGroupList = new BMGroupList();
	Wifi wifi = (Wifi) request.getAttribute("wifi");
	Bookmark bookmark = (Bookmark) request.getAttribute("bookmark");
	String wifiMgrNo = request.getParameter("wifiMgrNo");
	String bookmarkId = request.getParameter("bookmarkId");
	%>

	<h1>북마크 삭제</h1>

	<div>
		<a href="./index.jsp"><button>홈</button></a> 
		<a href="./history.do"><button>위치 히스토리 목록</button></a> 
		<a href="./bookmark-list.do"><button>북마크 보기</button></a> 
		<a href="./bookmark-group.do"><button>북마크 그룹 관리</button></a>
	</div>

	<h4>북마크를 삭제하시겠습니까?</h4>
	<form
		action="./bookmark-delete.do?bookmarkId=<%=bookmark.getBookmarkId()%>&wifiMgrNo=<%=wifi.getWifiMgrNo()%>"
		method="post">
		<table>
			<thead>
				<tr>
					<th>북마크이름</th>
					<th>와이파이명</th>
					<th>등록일자</th>
					<th>비고</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><%=bookmark.getBookmarkName()%></td>
					<td><%=wifi.getName()%></td>
					<td><%=bookmark.getBookmarkEnroll()%></td>
					<td>
						<button type="submit">삭제</button>
					</td>
				</tr>
			</tbody>
		</table>
	</form>
	<div>
		<a href="./bookmark-list.do"><button>돌아가기</button></a>
	</div>
</body>
</html>