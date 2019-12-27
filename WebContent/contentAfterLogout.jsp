<%@ page contentType="text/html;charset=euc-kr"
         import="java.sql.*,Library.*, java.util.Date"  %>
<HTML>
  <HEAD><TITLE>로그아웃 처리 후 화면</TITLE></HEAD>
  
  <BODY>
     <H3 align=center></H3>
     <br>
     
     <% 
     int check = (Integer)session.getAttribute("check");
     Object user = session.getAttribute("user");
     String name;
     String id;
     
     if(check==1){
    	 name = ((Student)user).getSname();
    	 id = ((Student)user).getId();
     }
     else if(check == 2){
    	 name = ((NormalManager)user).getMname();
    	 id = ((NormalManager)user).getId();
     }
     else{
    	 name = ((RootManager)user).getMname();
    	 id = ((RootManager)user).getId();
     }
     
         System.out.println("  << for debug >> in rootTopMenu: user = " + id); 
     %>
     
     	<table width=100%>
	<tr>
		<td>
			<td width=100% align=center><img src="img/thankYou.gif" width=170 height=170 alt="표시안됨" /></td>
		</td>
	</tr>
     
          <table align=center border="1" cellpadding="8" cellspacing="0"
				bordercolor="#999999">
		<tr>
			<td>
				<H4 align=center>
					<br>로그아웃 하셨습니다. </H4>
			</td>
		</tr>
		<tr>
			<td>
				<H4 align=center>
					<br><%= name%>님, 수고하셨습니다.<br> <br>보람차고 즐거운 시간 가지시기 바랍니다.
				</H4>
			</td>
		</tr>
	</table>
  </BODY>
</HTML>
