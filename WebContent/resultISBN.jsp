<%@ page contentType="text/html;charset=euc-kr"
         import="java.sql.*, Library.*"  %>
       <jsp:useBean class="Library.Book" id="book" scope="request" />
    	<jsp:setProperty  name="book" property="isbn" />
<HTML> 
    <HEAD><TITLE>Bean Test</TITLE></HEAD>
  
    <BODY>
    <h3>[ISBN : <%=book.getIsbn()%>]�� �˻� ���</h3>
            
    <% 
    String isbn = book.getIsbn();
    ResultSet rs = DB.getRSFindBook(isbn);
	       
	if (rs != null) {
		request.setAttribute("title", "�˻� ���");
		request.setAttribute("RS", rs);
		request.getRequestDispatcher("listRS.jsp").forward(request, response);
		
	} else
		out.println("�ش� ������ �������� �ʽ��ϴ�.");
	%>
	
	
	
    </BODY>
</HTML>