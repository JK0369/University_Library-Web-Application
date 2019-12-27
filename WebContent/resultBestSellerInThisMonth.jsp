<%@ page contentType="text/html;charset=euc-kr"
         import="java.sql.*, Library.*"  %>

    <HEAD><TITLE>Bean Test</TITLE></HEAD>
  
    <BODY>
    <h3>베스트셀러</h3>
                
    <% 
   
    ResultSet rs = DB.getRSBestSellerThisMonth();
	       
	if (rs != null) {
		request.setAttribute("title", "검색 결과");

		request.setAttribute("RS", rs);
		request.getRequestDispatcher("listRSWithButton.jsp").forward(request, response);
	} else
		out.println("베스트 셀러가 존재하지 않습니다.");
	%>
	
    </BODY>
</HTML>