<%@ page contentType="text/html;charset=euc-kr"
         import="java.sql.*, Library.*"  %>
       <jsp:useBean class="Library.Book" id="book" scope="request" />
    	<jsp:setProperty  name="book" property="isbn" />
<HTML> 
    <HEAD><TITLE></TITLE></HEAD>
  
    <BODY>
 <% Student student = (Student) session.getAttribute("user"); 
       System.out.println("  << for debug >> in rootTopMenu:Student = " + student.getId()); 
 
 	
    ResultSet rs = DB.getRSBorrowStudent(student.getSno());
	       
	if (rs != null) {
		request.setAttribute("title", "�˻� ���");

		request.setAttribute("RS", rs);
		request.getRequestDispatcher("listRSExtend.jsp").forward(request, response);
	} else
		out.println("�ƹ��� å�� ������ �ʾҽ��ϴ�.");
	%>
	
	
	
    </BODY>
</HTML>