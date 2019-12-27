<%@ page contentType="text/html;charset=euc-kr"
         import="java.sql.*,Library.*, java.util.Date"  %>
<HTML>
  <HEAD><TITLE>로그 아웃 처리</TITLE></HEAD>
  <base target="bottomFrame">
  
  <BODY>
    <%      
   	   request.setCharacterEncoding("euc-kr");   
   	   
       Object user = session.getAttribute("user");
       int check = (Integer)(session.getAttribute("check")); // check==1,2,3 각각 Student, NormalManager, RootManager
       
       if(check==1)
      	 System.out.println("  <<for debug >> logout ID: '" + ((Student)user).getId() + "'\n");
       else if(check==2)
    	 System.out.println("  <<for debug >> logout ID: '" + ((NormalManager)user).getId() + "'\n");
       else
    	 System.out.println("  <<for debug >> logout ID: '" + ((RootManager)user).getId() + "'\n");
       
       out.print("<script>window.top.location='systemAfterLogout.html'</script>"); 
  //     out.print("<script>window.iframeContent.location='contentAfterLogout.jsp'</script>"); 
 //      out.print("<script>parent.frames[1].location='bankTopFrame.jsp'</script>"); 
            
      
 //      out.print("<script>parent.frames[1].location='bankBottomFrameLogout.jsp'</script>"); 
    %>
    
  </BODY>
</HTML>