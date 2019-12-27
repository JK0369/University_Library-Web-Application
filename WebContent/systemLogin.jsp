<%@ page contentType="text/html;charset=euc-kr"
         import="java.sql.*,Library.*, java.util.Date"  %>
<HTML>
  <HEAD><TITLE>로그인 처리</TITLE></HEAD>
  
  <BODY>
    <%  
   	   request.setCharacterEncoding("euc-kr");
   	   
	   String ID = request.getParameter("ID");
       String password = request.getParameter("password");
       
       System.out.println("  <<for debug >> loginID: '" + ID + "', password='" + password + "'\n");      
       
       if (ID.equals("") || password.equals("")) {
       		out.print("<script>alert('로그인 아이디와 패스워드가 모두 입력되지 않았습니다.')</script>");
       		
       		out.print("<script>window.top.frames[0].location='topFrameForLogin.html'</script>");
        }
        
        Object user = LibrarySystem.loginAndDoWork(ID, password);
        
        int check; // check의 값이 1,2,3 순서대로 Student, NormalManager, RootManager
		if(user instanceof Student) check=1;				
		else if(user instanceof Manager && !((Manager) user).getId().equals("root")) check=2;
		else check=3;
        
        System.out.println("  <<for debug >> loginID: '" + ID + "', password='" + password+"\n");      
     	
       if (user == null) {
      		out.print("<script>alert('아이디: " + ID + ", 패스워드: " + password + " - 잘못된 아이디 또는 패스워드입니다.')</script>");     
       		out.print("<script>window.top.location='systemForLogin.html'</script>");

       }
       else {
    	    String name="";
    	    if(check==1) name=((Student)user).getSname();
    	    else if(check==2) name = ((NormalManager)user).getMname();
    	    else name = ((RootManager)user).getMname();
    	    
    	   	System.out.println("  <<for debug >> 로그인한 아이디 : " + ID + ", 사용자 이름: " + name + "\n");
   			session.setAttribute("check", check); // check==1,2,3 각각 Student, NormalManager, RootManager
     		session.setAttribute("user", user);  // 세션 객체에 로그인한 객체 user를 이름 "user"로 저장
     	
     		if (user instanceof Student){
     			out.print("<script>window.top.location='systemAfterStudentLogin.jsp'</script>"); 
      		 }
       		else if (user instanceof NormalManager){
     			out.print("<script>window.top.location='systemAfterNormalLogin.jsp'</script>"); 
       		}
       		else {
     			out.print("<script>window.top.location='systemAfterRootLogin.jsp'</script>"); 
       		}
       }
    %>
    
  </BODY>
</HTML>