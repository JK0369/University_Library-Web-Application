<%@ page contentType="text/html;charset=euc-kr"
         import="java.sql.*, Library.*"  %>

    <HEAD><TITLE>Bean Test</TITLE></HEAD>
  
    <BODY>
    <h3>연체중인 도서</h3>
                
    <% 
   
    ResultSet rs = DB.getRSOverdueStudentList();
	       
	if (rs != null) {
		request.setAttribute("title", "검색 결과");

		request.setAttribute("RS", rs);
		request.getRequestDispatcher("listRS.jsp").forward(request, response);
	} else
		out.println("연체 중인 도서가 존재하지 않습니다.");
	%>
	
    </BODY>
</HTML>