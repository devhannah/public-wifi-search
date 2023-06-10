<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>북마크 이름 추가</title>
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

a {
	text-decoration: none;
	color: black;
}

table {
	margin-left: auto;
	margin-right: auto;
	padding: 50px;
	border: solid 1px grey;
}

th {
	border: solid 1px grey;
	border-collapse: collapse;
	color: white;
	background-color: #50C785;
	font-size: small;
}

td {
	border: none;
	border-collapse: collapse;
	color: black;
	background-color: white;
	font-size: small;
}
</style>
</head>
<body>

	<h1>북마크 이름 추가</h1>
	<div>
		<a href="./index.jsp"><button>홈</button></a> 
		<a href="./history.do"><button>위치 히스토리 목록</button></a> 
		<a href="./bookmark-list.do"><button>북마크 보기</button></a> 
		<a href="./bookmark-group.do"><button>북마크 그룹 관리</button></a>
	</div>
	<form action="./bookmark-group-add.do" method="post">
		<table>
			<tr>
				<th>북마크 이름</th>
				<td><input name="bookmarkName"></input></td>
			</tr>
			<tr>
				<th>순서</th>
				<td><input name="bookmarkOrder"></input></td>
			</tr>
		</table>
		<input type="hidden" name="bookmarkEnroll"></input>
		<div>
			<button type="reset">초기화</button>
			<button type="submit">추가</button>
		</div>
	</form>

</body>
</html>