<%@ page contentType="text/html;charset=euc-kr"
         import="java.sql.*, Library.*"  %>

    <HEAD><TITLE>Bean Test</TITLE></HEAD>
  
    <BODY>
                
    <% 
    Student student = (Student) session.getAttribute("user"); 
    ResultSet rs = DB.getRSHistory(student.getSno());
	       
	if (rs != null) {
		request.setAttribute("title", "검색 결과");

		request.setAttribute("RS", rs);
		request.getRequestDispatcher("listRS.jsp").forward(request, response);
	} else
		out.println("대출이력이 없습니다.");
	%>
	
    </BODY>
</HTML>