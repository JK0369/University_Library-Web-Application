<%@ page contentType="text/html;charset=euc-kr"
         import="java.sql.*,Library.*, java.util.Date"  %>
<HTML>
  <HEAD><TITLE>�α��� ó��</TITLE></HEAD>
  
  <BODY>
    <%  
   	   request.setCharacterEncoding("euc-kr");
   	   
	   String ID = request.getParameter("ID");
       String password = request.getParameter("password");
       
       System.out.println("  <<for debug >> loginID: '" + ID + "', password='" + password + "'\n");      
       
       if (ID.equals("") || password.equals("")) {
       		out.print("<script>alert('�α��� ���̵�� �н����尡 ��� �Էµ��� �ʾҽ��ϴ�.')</script>");
       		
       		out.print("<script>window.top.frames[0].location='topFrameForLogin.html'</script>");
        }
        
        Object user = LibrarySystem.loginAndDoWork(ID, password);
        
        int check; // check�� ���� 1,2,3 ������� Student, NormalManager, RootManager
		if(user instanceof Student) check=1;				
		else if(user instanceof Manager && !((Manager) user).getId().equals("root")) check=2;
		else check=3;
        
        System.out.println("  <<for debug >> loginID: '" + ID + "', password='" + password+"\n");      
     	
       if (user == null) {
      		out.print("<script>alert('���̵�: " + ID + ", �н�����: " + password + " - �߸��� ���̵� �Ǵ� �н������Դϴ�.')</script>");     
       		out.print("<script>window.top.location='systemForLogin.html'</script>");

       }
       else {
    	    String name="";
    	    if(check==1) name=((Student)user).getSname();
    	    else if(check==2) name = ((NormalManager)user).getMname();
    	    else name = ((RootManager)user).getMname();
    	    
    	   	System.out.println("  <<for debug >> �α����� ���̵� : " + ID + ", ����� �̸�: " + name + "\n");
   			session.setAttribute("check", check); // check==1,2,3 ���� Student, NormalManager, RootManager
     		session.setAttribute("user", user);  // ���� ��ü�� �α����� ��ü user�� �̸� "user"�� ����
     	
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