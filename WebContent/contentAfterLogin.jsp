<%@ page contentType="text/html;charset=euc-kr"
         import="java.sql.*,Library.*, java.util.Date"  %>
<HTML>
  <HEAD><TITLE>로그인 후 화면 처리</TITLE></HEAD>
  
  <BODY>
     <% 
      	 String id;
		 String name;
		 String type;
		 Object user = session.getAttribute("user");
		 int check = (Integer)session.getAttribute("check");
		 
     	 if(check==1){
     		 id = ((Student)user).getId();
     		 name = ((Student)user).getSname();
     		 type = "학생";
     	 }
     	 else if(check==2){
     		 id = ((NormalManager)user).getId();
     		 name = ((NormalManager)user).getMname();
     		 type = "Normal Manager";
     	 }
     	 else{
     		 id = ((RootManager)user).getId();
     		 name = ((RootManager)user).getMname();
     		 type = "Root Manager";
     	 }
     	 
     	 System.out.println("  << for debug >> After login, user.id = " + id); 
         		          
     %>
     <br><br>

	<table align=center >
		<TR>
			<td colspan=3 align=center width=15%><img src="img/message.gif" width=70 height=70 alt="표시안됨" /></td>
 		</TR>
	</table>

     <table align=center border="1" cellpadding="8" cellspacing="0"
		bordercolor="#999999">
		
		<tr>
			<td>
				<H4 align=center>
					<br><%= name%>님, 반갑습니다.</H4>
			</td>
		</tr>
		<tr>
			<td>
				<H4 align=center>
					<br><%= type %> 으로 로그인하였습니다. <br><br>
       				LDB 도서관에서 좋은시간 보내시기 바랍니다. 
				</H4>
			</td>
		</tr>

	</table>
	
  </BODY>
</HTML>
