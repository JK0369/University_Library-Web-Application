<%@ page contentType="text/html;charset=euc-kr"
         import="java.sql.*, Library.*"  %>

    <HEAD><TITLE>Bean Test</TITLE></HEAD>
  
    <BODY>
    <h3>��ü���� ����</h3>
                
    <% 
   
    ResultSet rs = DB.getRSOverdueStudentList();
	       
	if (rs != null) {
		request.setAttribute("title", "�˻� ���");

		request.setAttribute("RS", rs);
		request.getRequestDispatcher("listRS.jsp").forward(request, response);
	} else
		out.println("��ü ���� ������ �������� �ʽ��ϴ�.");
	%>
	
    </BODY>
</HTML>