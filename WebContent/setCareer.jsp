<%@ page contentType="text/html;charset=euc-kr"
         import="java.sql.*, Library.*"  %>

    <HEAD><TITLE>Bean Test</TITLE></HEAD>
  
    <BODY>
    <h3>����Ʈ����</h3>
                
    <% 
    DB.updateCareer();
    ResultSet rs = DB.getRSFromManager();       
	if (rs != null) {
		request.setAttribute("title", "�˻� ���");

		request.setAttribute("RS", rs);
		request.getRequestDispatcher("listRSSetCareer.jsp").forward(request, response);
	} else
		out.println("�����ڰ� �������� �ʽ��ϴ�.");
	%>
	
    </BODY>
</HTML>