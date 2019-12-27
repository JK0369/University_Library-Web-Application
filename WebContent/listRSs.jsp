<%@ page contentType="text/html;charset=euc-kr"
         import="java.sql.*,  java.util.Date"  %>

<HTML>
  <BODY>
 	<%	
 	  request.setCharacterEncoding("euc-kr");
 	
 	  ResultSet RSs[] = (ResultSet[]) request.getAttribute("RSs");
      String titles[] = (String[]) request.getAttribute("titles");	
      for (int no=0; no < RSs.length; no++) {
 	
 	       ResultSet rs = RSs[no];
 	       if (rs == null)
 	          continue;
 	       
		   ResultSetMetaData md = rs.getMetaData();
		   int count = md.getColumnCount();
		   String[] columns = new String[count];
		   String[] columnTypes = new String[count];
		   
		   for(int i=0; i<count; i++){
			   columns[i] = md.getColumnLabel(i+1);
			   columnTypes[i] = md.getColumnTypeName(i+1);
		   }
	%>
	       <br>
	   	   <H3 align=center > <% out.print(titles[no]);   %> </H3>
	   
	     <table align=center valign=top border="1" cellpadding="8" cellspacing="0" bordercolor="#999999"> 
    <%
			out.print("<tr bgcolor=#CCCCCC>" );
			for(int i=0; i<count; i++){
				out.print("<th>" + columns[i] + "</th>" );	
			}
			out.print("</tr>" );

			while(rs.next()) {
				out.print("<tr>" );

				for(int i=0; i<columns.length; i++){

					Object obj= rs.getObject(columns[i]);
  System.out.print("   >> col value : " + (obj) + "\n");
 
			        if (columnTypes[i].equals("INTEGER") || columnTypes[i].equals("FLOAT")
			                         || columnTypes[i].equals("DOUBLE") || columnTypes[i].equals("BIGINT") )
						out.print("<td align=right>" + obj + "</td>");	
					else if (obj == null || columnTypes[i].equals("VARCHAR") && ((String) obj).equals(""))
					     out.print("<td> &nbsp; </td>");	
					else if (columnTypes[i].equals("VARCHAR") )
						out.print("<td align=left>" +  obj + "</td>");	
 			     	else
					     out.print("<td>" + obj + "</td>");	
				}
				out.print("</tr>" );

			}
       %>
	       </table > <br> <br> <br>
	   <%
	      }
	   %>
  </BODY>
</HTML>
