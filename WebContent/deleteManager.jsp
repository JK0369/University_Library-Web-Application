<%@ page contentType="text/html;charset=euc-kr" import="java.sql.*, java.util.*, Library.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<%String[] value = request.getParameterValues("manager");
		for(int i =0; i < value.length; i++)
		{
			DB.buffer.add(value[i]);
		}
		for(int i =0; i < DB.buffer.size(); i++){
		DB.deleteManager(Integer.parseInt(DB.buffer.get(i))); 
		}
	%>
	<H3>삭제완료</H3>
</body>
</html>