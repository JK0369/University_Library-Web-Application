<%@ page contentType="text/html;charset=euc-kr" import="java.sql.*, java.util.*, Library.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
System.out.println("test in borrow top");
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
   <%
      Student student = (Student) session.getAttribute("user"); 
      System.out.println("  << for debug >> in borrow:Student = " + student.getId());
       
       
      String[] value = request.getParameterValues("book");
      if(value.length>3)
         out.print("<script>alert(\'3권을 초과하여 예약할 수 없습니다.');</script>");
      else{
         DB.buffer.clear();
         ResultSet rs1 = null;
         for(int i =0; i < value.length; i++)
         {
            DB.buffer.add(value[i]);
            rs1 = DB.getRSFindBook((String)DB.buffer.get(i));
            int result = DB.borrowBook(rs1,student.getSno());
            if (result < 0)
            {
               out.print("<script>alert(\'대출불가인 도서가 포함되었습니다. .');</script>");
               return;
            }
         }
         out.print("<script>alert(\'예약완료!!');</script>");
      }
      
   %>
   
</body>
</html>