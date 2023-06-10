<%@page import="java.util.List"%>
<%@page import="wifi.com.controller.HistoryService"%>
<%@page import="java.util.ArrayList"%>
<%@page import="wifi.com.dto.History"%>
<%@page import="wifi.com.dao.TestDatabase"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>위치 히스토리 목록</title>
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
</style>
</head>
<body>

	<%
	
	/* history list 출력 */
	HistoryService historyService = new HistoryService();
	List<History> historyList = (List) request.getAttribute("historyList");

	/* history list에서 history 삭제할 때, 파라미터 값을 넘겨 받았을 경우에 메소드가 실행되는 방식으로 구현 */
	String historyId = request.getParameter("historyId");
	if (historyId != null) {
		historyService.delete(Integer.parseInt(historyId));
		/* 삭제를 할 경우 DB에서는 삭제가 되는데 페이지 이동 변화가 없기 때문에 페이지를 리다렉트 하는 방법으로 페이지를 이동 시킴 */
		response.sendRedirect("./history.do");

	}
	
	%>

	<h1>위치 히스토리 목록</h1>

	<div>
		<a href="./index.jsp"><button>홈</button></a> 
		<a href="./history.do"><button>위치히스토리 목록</button></a> 
		<a href="./bookmark-list.do"><button>북마크 보기</button></a> 
		<a href="./bookmark-group.do"><button>북마크 그룹 관리</button></a>
	</div>

	<table>
		<thead>
			<tr>
				<th>ID</th>
				<th>X좌표</th>
				<th>Y좌표</th>
				<th>조회일자</th>
				<th>비고</th>
			</tr>
		</thead>
		<tbody>
			<%
			if (historyList != null) {
				for (int i = 0; i < historyList.size(); i++) {
					History history = historyList.get(i);
			%>
			<tr>
				<td><%=i + 1%></td>
				<td><%=history.getLat()%></td>
				<td><%=history.getLnt()%></td>
				<td><%=history.getViewDate()%></td>
				<td><a href="./history.do?historyId=<%=history.getHistoryId()%>"><button>삭제</button></a></td>
				<%
				}
				}
				%>
			</tr>
		</tbody>
	</table>
</body>
</html>