<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<h3>관리자 추가</h3>
	<form action="resultAddManager.jsp" method=post>
		ID : 
			<input type="text" name="id" size=15> <br>
		P/W :    
		    <input type="text" name="pw" size=15> <br>
		이름 :   
			<input type="text" name="mname" size=15> <br>
		주민등록번호(-포함) :
		<input type="text" name="rrn" size=20> <br> 
		경력 :
		<input type="text" name="career" size=20> <br>
		사번 : 
		<input type="text" name="mno" size=20> <br> 
		<input type="submit" size=25 value="등록">
	</form>
</body>
</html>
</html>