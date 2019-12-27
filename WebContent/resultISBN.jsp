<%@ page contentType="text/html;charset=euc-kr"
         import="java.sql.*, Library.*"  %>
       <jsp:useBean class="Library.Book" id="book" scope="request" />
    	<jsp:setProperty  name="book" property="isbn" />
<HTML> 
    <HEAD><TITLE>Bean Test</TITLE></HEAD>
  
    <BODY>
    <h3>[ISBN : <%=book.getIsbn()%>]의 검색 결과</h3>
            
    <% 
    String isbn = book.getIsbn();
    ResultSet rs = DB.getRSFindBook(isbn);
	       
	if (rs != null) {
		request.setAttribute("title", "검색 결과");
		request.setAttribute("RS", rs);
		request.getRequestDispatcher("listRS.jsp").forward(request, response);
		
	} else
		out.println("해당 도서는 존재하지 않습니다.");
	%>
	
	
	
    </BODY>
</HTML>