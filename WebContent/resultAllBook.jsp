<%@ page contentType="text/html;charset=euc-kr"
         import="java.sql.*, Library.*"  %>

    <HEAD><TITLE>Bean Test</TITLE></HEAD>
  
    <BODY>
    <h3>베스트셀러</h3>
                
    <% 
   
    ResultSet rs = DB.getRSAllBook();
	       
	if (rs != null) {
		request.setAttribute("title", "검색 결과");

		request.setAttribute("RS", rs);
		request.getRequestDispatcher("listRSWithButton.jsp").forward(request, response);
	} else
		out.println("도서가 존재하지 않습니다.");
	%>
	
    </BODY>
</HTML>