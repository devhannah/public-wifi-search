<%@page import="wifi.com.controller.BMGroupInsert"%>
<%@page import="wifi.com.controller.BMGroupList"%>
<%@page import="wifi.com.dto.Bookmark"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>북마크 그룹</title>
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
	Bookmark bookmark = new Bookmark();
	BMGroupList bmGroupList = new BMGroupList();
	List<Bookmark> bookmarkList = (List<Bookmark>) request.getAttribute("bookmarkList");
	%>
	<h1>북마크 그룹</h1>
	<div>
		<a href="./index.jsp"><button>홈</button></a> 
		<a href="./history.do"><button>위치 히스토리 목록</button></a> 
		<a href="./bookmark-list.do"><button>북마크 보기</button></a> 
		<a href="./bookmark-group.do"><button>북마크 그룹 관리</button></a>
	</div>
	<div>
		<a href="./bookmark-group-add.jsp"><button>북마크 그룹 이름 추가</button></a>
	</div>

	<table>
		<thead>
			<tr>
				<th>ID</th>
				<th>북마크이름</th>
				<th>순서</th>
				<th>등록일자</th>
				<th>수정일자</th>
				<th>비고</th>
			</tr>
		</thead>
		<tbody>
			<%
			if (bookmarkList != null) {
				for (int i = 0; i < bookmarkList.size(); i++) {
					bookmark = bookmarkList.get(i);
			%>
			<tr>
				<td><%=bookmark.getBookmarkId()%></td>
				<td><%=bookmark.getBookmarkName()%></td>
				<td><%=bookmark.getBookmarkOrder()%></td>
				<td><%=bookmark.getBookmarkEnroll()%></td>
				<%
				if (bookmark.getBookmarkEdit() != null) {
				%>
				<td><%=bookmark.getBookmarkEdit()%></td>
				<%
				} else {
				%>
				<td></td>
				<%
				}
				%>
				<td>
					<a href="./bookmark-group-edit.jsp?bookmarkId=<%=bookmark.getBookmarkId()%>"><button>수정</button></a>
					<a href="./bookmark-group-delete.do?bookmarkId=<%=bookmark.getBookmarkId()%>"><button>삭제</button></a>
				</td>
				<%
				}
				}
				%>
			</tr>
		</tbody>
	</table>

</body>
</html>