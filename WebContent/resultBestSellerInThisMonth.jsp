<%@ page contentType="text/html;charset=euc-kr"
         import="java.sql.*, Library.*"  %>

    <HEAD><TITLE>Bean Test</TITLE></HEAD>
  
    <BODY>
    <h3>����Ʈ����</h3>
                
    <% 
   
    ResultSet rs = DB.getRSBestSellerThisMonth();
	       
	if (rs != null) {
		request.setAttribute("title", "�˻� ���");

		request.setAttribute("RS", rs);
		request.getRequestDispatcher("listRSWithButton.jsp").forward(request, response);
	} else
		out.println("����Ʈ ������ �������� �ʽ��ϴ�.");
	%>
	
    </BODY>
</HTML>