<%@ page contentType="text/html;charset=euc-kr" import="java.sql.*, java.util.*, Library.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<%
		Student student = (Student) session.getAttribute("user"); 
    	System.out.println("  << for debug >> in extend.jsp :Student = " + student.getId()); 
    	
		String[] value = request.getParameterValues("book");
		
		DB.buffer.clear();
		for(int i =0; i < value.length; i++)
		{
			DB.buffer.add(value[i]);
		}
		System.out.println("test1"); 
		int check2 =0;
		for(int i =0; i < DB.buffer.size(); i++)
		{
			check2 = DB.ExtendStep2(student.getSno(),DB.buffer.get(i));
			check2 = DB.ExtendStep1(student.getSno(),DB.buffer.get(i));
			if(check2 < 1)
			{
				System.out.println("test2"); 
				out.print("<script>alert(\'연장 할 수 없는 도서가 포함되었습니다..');</script>");
				break;
			}
		}
		System.out.println("test3  :"+check2); 
		if(check2 > 0)
			out.print("<script>alert(\'연장완료!!!\');</script>");
		
	
	
	%>
	
</body>
</html>