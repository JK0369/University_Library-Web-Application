<%@ page contentType="text/html;charset=euc-kr"
         import="java.sql.*,Library.*, java.util.Date, java.util.*"  %>
<HTML>
 <body bgcolor="#FFFFFF">
 
 	<table width=100%>
		<tr>
			<td  align=center width=15%><img src="img/student.gif" weight=70 height=70 /></td>
 		</tr>
 	</table>
 <center>
 <H2>�л� ȸ�� ����</H2>
 <HR>
  <base target="bottomFrame">
     <form method="post" action="systemRegisterInDB.jsp" name="form1">
  <table width="250" border="1" align="center" cellspacing="0" cellpadding="5">
 
    <tr> 
    <td colspan="2" align="center">ȸ������</td>
  </tr>
  
  <tr> 
      <td> ���̵�</td>
      <td><input type="text" name="id" size=10></td>
  </tr>
  
  <tr> 
      <td> �н�����</td>
      <td><input type="text" name="pw" size=10></td>
  </tr>
  
   <tr> 
      <td> �й�</td>
      <td><input type="text" name="sno" size=7></td>
  </tr>
  
   <tr> 
      <td> �̸�</td>
      <td><input type="text" name="sname" size=10></td>
  </tr>
  
   <tr> 
   <td>�а�</td>
   <td>
   <p>
         <% request.setCharacterEncoding("euc-kr");   
         	DB.loadConnect();
             Vector depts = DB.selectDistinctDepts(); %>
         
         <p align = center>
         <select size= <%= depts.size()%> name="dept">
          <% 
                for(int i=0; i< depts.size(); i++)
                     out.print("<option value=\"" + depts.get(i) + "\">" 
                                              +  "&nbsp;&nbsp;" + depts.get(i));              
          %>
          </select>
   </td></tr>
  
  <tr> 
      <td> �г�</td>
      
         <td>
  		 <p>
         <p align = center>
         <select size=4 name="year">
          <% 
                for(int i=1; i<= 4; i++)
                     out.print("<option value=\"" + i + "\">" 
                                              +  "&nbsp;&nbsp;" + i);              
          %>
          </select>
   		  </td>
  </tr>
  
  <tr> 
    <td colspan="2" align="center"> 
        <input type="submit" name="result" value="�Է�"></td>
  </tr>
  
</table>
</form>
  </BODY>
</HTML>
