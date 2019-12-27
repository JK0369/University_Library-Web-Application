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
 <H2>학생 회원 가입</H2>
 <HR>
  <base target="bottomFrame">
     <form method="post" action="systemRegisterInDB.jsp" name="form1">
  <table width="250" border="1" align="center" cellspacing="0" cellpadding="5">
 
    <tr> 
    <td colspan="2" align="center">회원가입</td>
  </tr>
  
  <tr> 
      <td> 아이디</td>
      <td><input type="text" name="id" size=10></td>
  </tr>
  
  <tr> 
      <td> 패스워드</td>
      <td><input type="text" name="pw" size=10></td>
  </tr>
  
   <tr> 
      <td> 학번</td>
      <td><input type="text" name="sno" size=7></td>
  </tr>
  
   <tr> 
      <td> 이름</td>
      <td><input type="text" name="sname" size=10></td>
  </tr>
  
   <tr> 
   <td>학과</td>
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
      <td> 학년</td>
      
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
        <input type="submit" name="result" value="입력"></td>
  </tr>
  
</table>
</form>
  </BODY>
</HTML>
