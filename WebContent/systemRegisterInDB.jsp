<%@ page contentType="text/html;charset=euc-kr"
         import="java.sql.*,Library.*, java.util.Date"  %>
<jsp:useBean id="s" class="Library.StudentBeans" scope="page" />
<jsp:setProperty name="s" property="*" />

<HTML>
  <BODY>
    <!--  학생 정보를 받아서 DB 테이블에 입력	 -->
    <%  
   	   request.setCharacterEncoding("euc-kr");   
   	   DB.loadConnect();
   	   String id = s.getId();
   	   String pw = s.getPw();
   	   int sno = s.getSno();
   	   String sname = s.getSname();
   	   String dept = s.getDept();
   	   int year = s.getYear();
   	   
   	   // 한글로 넘겨 받을 때, 깨짐 현상 방지
   	   sname = new String(sname.getBytes("ISO-8859-1"), "EUC-KR");
   	   dept = new String(dept.getBytes("ISO-8859-1"), "EUC-KR");
   	   
   	   Student newStudent = new Student(sno, sname, year, dept, id, pw);
   	   DB.insertStudent(newStudent);
    %>
    out.print("<script>window.top.location='formForLogin.html'</script>"); 
    
  </BODY>
</HTML>