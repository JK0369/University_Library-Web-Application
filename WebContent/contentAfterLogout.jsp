<%@ page contentType="text/html;charset=euc-kr"
         import="java.sql.*,Library.*, java.util.Date"  %>
<HTML>
  <HEAD><TITLE>�α׾ƿ� ó�� �� ȭ��</TITLE></HEAD>
  
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
			<td width=100% align=center><img src="img/thankYou.gif" width=170 height=170 alt="ǥ�þȵ�" /></td>
		</td>
	</tr>
     
          <table align=center border="1" cellpadding="8" cellspacing="0"
				bordercolor="#999999">
		<tr>
			<td>
				<H4 align=center>
					<br>�α׾ƿ� �ϼ̽��ϴ�. </H4>
			</td>
		</tr>
		<tr>
			<td>
				<H4 align=center>
					<br><%= name%>��, �����ϼ̽��ϴ�.<br> <br>�������� ��ſ� �ð� �����ñ� �ٶ��ϴ�.
				</H4>
			</td>
		</tr>
	</table>
  </BODY>
</HTML>
