<%@ page contentType="text/html;charset=euc-kr"
         import="java.sql.*,Library.*, java.util.Date"  %>
<jsp:useBean id="s" class="Library.StudentBeans" scope="page" />
<jsp:setProperty name="s" property="*" />

<HTML>
  <BODY>
    <!--  �л� ������ �޾Ƽ� DB ���̺� �Է�	 -->
    <%  
   	   request.setCharacterEncoding("euc-kr");   
   	   DB.loadConnect();
   	   String id = s.getId();
   	   String pw = s.getPw();
   	   int sno = s.getSno();
   	   String sname = s.getSname();
   	   String dept = s.getDept();
   	   int year = s.getYear();
   	   
   	   // �ѱ۷� �Ѱ� ���� ��, ���� ���� ����
   	   sname = new String(sname.getBytes("ISO-8859-1"), "EUC-KR");
   	   dept = new String(dept.getBytes("ISO-8859-1"), "EUC-KR");
   	   
   	   Student newStudent = new Student(sno, sname, year, dept, id, pw);
   	   DB.insertStudent(newStudent);
    %>
    out.print("<script>window.top.location='formForLogin.html'</script>"); 
    
  </BODY>
</HTML>