<%@ page contentType="text/html;charset=euc-kr"
         import="java.sql.*, Library.*, java.util.Date"  %>
       <jsp:useBean class="Library.Manager" id="manager" scope="page" />
    	<jsp:setProperty  name="manager" property="id" />
    	<jsp:setProperty  name="manager" property="pw" />
    	<jsp:setProperty  name="manager" property="mname" />
    	<jsp:setProperty  name="manager" property="rrn" />
    	<jsp:setProperty  name="manager" property="career" />
    	<jsp:setProperty  name="manager" property="mno" />
<HTML> 

    <BODY>
    <h3>���</h3>
       <%  
   	   request.setCharacterEncoding("euc-kr");   
   	   DB.loadConnect();
   	   String id = manager.getId();
   	   String pw = manager.getPw();
   	   String mname = manager.getMname();
   	   String rrn = manager.getRrn();
   	   java.sql.Date dateOfEntry = java.sql.Date.valueOf(DB.getDate());
   	   int mno = manager.getMno();
   	   int career = manager.getCareer();
   	   
   	    // �ѱ۷� �Ѱ� ���� ��, ���� ���� ����
   	   mname = new String(mname.getBytes("ISO-8859-1"), "EUC-KR");
   	   NormalManager man = new NormalManager(rrn, mname, career, id, pw, mno, dateOfEntry);
   	    
  
       	DB.insertManager(man);
       	
	    ResultSet rs = DB.getRSFromManagerByMno(man.getMno());
	if (rs != null) {
		request.setAttribute("title", "Ȯ�� ���");
		request.setAttribute("RS", rs);
		request.getRequestDispatcher("listRS.jsp").forward(request, response);
		
	} else
		out.println("��� ����");
	%>
	
    </BODY>
</HTML>