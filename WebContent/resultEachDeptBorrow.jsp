<%@ page contentType="text/html;charset=euc-kr"
         import="java.sql.*, Library.*"  %>

    <HEAD><TITLE>Bean Test</TITLE></HEAD>
  
    <BODY>
    <h3>����Ʈ����</h3>
                
    <% 
   
    ResultSet rs = DB.getRSBorrowByDept();
	       
	if (rs != null) {
		request.setAttribute("title", "�˻� ���");

		request.setAttribute("RS", rs);
		request.getRequestDispatcher("listRS.jsp").forward(request, response);
	} else
		out.println("�ƹ��� å�� ������ �ʾҽ��ϴ�.");
	%>
	
    </BODY>
</HTML>