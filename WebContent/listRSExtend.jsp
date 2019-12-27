<%@ page contentType="text/html;charset=euc-kr" import="java.sql.*, java.util.*, Library.*" %>
<HTML>
  <BODY>
       <% Student student = (Student) session.getAttribute("user"); 
       System.out.println("  << for debug >> in rootTopMenu:Student = " + student.getId()); 
 
       %>
  	   <br>
	   <H3 align=center > <% out.print(request.getAttribute("title"));   %> </H3>
	    
 	<%	
 	 	   request.setCharacterEncoding("euc-kr");
 	
 	       ResultSet rs = (ResultSet) request.getAttribute("RS");
 	       
			rs.last();  // rs 커서를 끝으로 이동하여 투플 개수 확인
			int cntTuples = rs.getRow();  // 투플 개수를 구함
			rs.beforeFirst();  // rs 커서를 처음으로 이동
System.out.print("   >> cntTuples = " + cntTuples + "\n");	
			
			if (cntTuples == 0) { // 투플 개수가 0이면
				out.println("<center>(결과 없음)</center>");
				return;
			}
			
 	       
 System.out.print("   >> rs : " + rs + "\n");	       
 
		   ResultSetMetaData md = rs.getMetaData();
		   int count = md.getColumnCount();
		   String[] columns = new String[count];
		   String[] columnTypes = new String[count];
		   for(int i=0; i<count; i++){
			   columns[i] = md.getColumnLabel(i+1);
			   columnTypes[i] = md.getColumnTypeName(i+1);
System.out.print("   >> clms : " + columns[i] + " " + columnTypes[i]+ "\n");	
     	   }
	  %>
       <%
       
		
			/*if (cntTuples == 1) { // 투플 개수가 1이면 한 라인에 애트리뷰트명과 애트리뷰트 이름을 출력
			    out.print("<table align=center valign=top border=1 cellpadding=8 cellspacing=0 bordercolor=#999999>");
			
				rs.next(); // ResultSet의 커서 이동

				for(int i=0; i<columns.length; i++){
					out.print("<tr><td bgcolor=#DDDDDD>" + columns[i]  + "</td >" + "<td > &nbsp;" +  rs.getObject(columns[i])  + "&nbsp;</td></tr>");	
				}
				
				out.print("</table>");

				return;
			}*/
		out.print("<form action=\"extend.jsp\" name=form1 method=post>");
	    out.print("<table align=center valign=top border=1 cellpadding=8 cellspacing=0 bordercolor=#999999>");
			out.print("<tr bgcolor=#DDDDDD>" );
			for(int i=0; i<columns.length; i++){
				out.print("<th>" + columns[i]  + "</th>" );	
			}
			
			out.print("</tr>" );
			
			while(rs.next()) {
				out.print("<tr>" );
			    String j = "";
				for(int i=0; i<columns.length; i++){
					
					Object obj= rs.getObject(columns[i]);
 // System.out.print("   >> col value : " + (obj) + "\n");
 					if(((String)columns[i]).equals("isbn"))
 					{
 						j = (String)obj;
 					}
					if (obj == null)    // null 객체이면 null을 출력
						out.print("<td> null </td>");
					else if (columnTypes[i].equals("INTEGER") || columnTypes[i].equals("FLOAT")
			                             || columnTypes[i].equals("DOUBLE") || columnTypes[i].equals("BIGINT") )
						out.print("<td align=right>" + obj + "</td>");	
					else if (columnTypes[i].equals("VARCHAR") && ((String) obj).equals(""))
					     out.print("<td> &nbsp; </td>");	
					else if (columnTypes[i].equals("VARCHAR") )
						out.print("<td align=left>"+ obj + "</td>");	
 			     	else
					     out.print("<td>" + obj + "</td>");	
				}
				out.print("<td><input type=\"checkbox\" name=\"book\" value=\""+j+"\"</td>");
				out.print("</tr>");
			}
      %>
	  </table >
	   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	  <input type="submit" value="연장 " name="a">
  </BODY>
</HTML>
