<%@ page contentType="text/html;charset=euc-kr"
         import="java.sql.*,Library.*, java.util.Date"  %>
<HTML>
  <HEAD><TITLE>�α��� �� ȭ�� ó��</TITLE></HEAD>
  
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
     		 type = "�л�";
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
			<td colspan=3 align=center width=15%><img src="img/message.gif" width=70 height=70 alt="ǥ�þȵ�" /></td>
 		</TR>
	</table>

     <table align=center border="1" cellpadding="8" cellspacing="0"
		bordercolor="#999999">
		
		<tr>
			<td>
				<H4 align=center>
					<br><%= name%>��, �ݰ����ϴ�.</H4>
			</td>
		</tr>
		<tr>
			<td>
				<H4 align=center>
					<br><%= type %> ���� �α����Ͽ����ϴ�. <br><br>
       				LDB ���������� �����ð� �����ñ� �ٶ��ϴ�. 
				</H4>
			</td>
		</tr>

	</table>
	
  </BODY>
</HTML>
