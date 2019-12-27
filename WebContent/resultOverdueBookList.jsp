<%@ page contentType="text/html;charset=euc-kr"
         import="java.sql.*, Library.*"  %>

    <HEAD><TITLE>Bean Test</TITLE></HEAD>
  
    <BODY>
    <h3>연체자 목록</h3>
                
    <% 
   
    ResultSet rs = DB.getRSOverdueBookList();
    
	if (rs != null) {
		request.setAttribute("title", "검색 결과");

		request.setAttribute("RS", rs);
		request.getRequestDispatcher("listRS.jsp").forward(request, response);
	} else
		out.println("연체자가 존재하지 않습니다.");
	%>
	
    </BODY>
</HTML>