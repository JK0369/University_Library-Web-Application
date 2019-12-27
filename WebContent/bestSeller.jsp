<%@ page contentType="text/html;charset=euc-kr"
         import="java.sql.*,Library.*, java.util.Date"  %>
<HTML>
<HEAD>
<TITLE>도서 검색</TITLE>
</HEAD>

<BODY>
    <% Student student = (Student) session.getAttribute("user"); 
       System.out.println("  << for debug >> in rootTopMenu:Student = " + student.getId()); 
 
       %>
        
	<table width=100%>
		<tr>
			<td  align=center width=15%><img src="img/bankLogo-small.gif"  /></td>
			
			<td width=70%>
				<H2 align=center>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;도서검색 &nbsp; &nbsp; &nbsp; &nbsp;  </H2>
			</td>
			
			<td width=15%>

				<form name=form1 method=post action=systemLogout.jsp>

					<H6>
						<TABLE align=left border=0 cellpadding=1 cellspacing="1"
							bordercolor="#DDDDDD"  >

							<TR align=left bgcolor=white >
								<TD><img src="img/id2.gif" /></TD>
								<TD bgcolor="#DDDDDD"> <%= student.getId() %>  </TD>
							</TR>

							<TR align=left bgcolor=white >
								<TD><img src="img/name.gif" /></TD>
								<TD bgcolor="#DDDDDD" > <%= student.getSname() %> </TD>
							</TR>
							
							<TR>
								<TD colspan=2 align=center valign=center><input type="submit" value="로그아웃"></TD>
							</TR>
							

						</TABLE>
					</H6>
				</form>

			</td>
		</tr>
		<tr>
			<td colspan=3>
				<H5 align=center>
					<table width="90%" align=center valign=top border="1"
						cellpadding="1" cellspacing="0" bordercolor="#999999">
						<tr align=center>
							<td><a href="bestSeller.jsp">도서 순위
							</a></td>
							<td><a href="bestSellerInThisMonth.jsp">이달의 도서
							</a></td>
							<td><a href="allBook.jsp">모든 도서목록 
							</a></td>							
						</tr>
					</table>
				</H5>
		</tr>
		
				
		<tr width=100% height=700>
		    <td align=center colspan=3>				
					<iframe width=100% height=700 src=resultBestSeller.jsp allowTransparency="true" name=iframeContent seamless="seamless">					
	        </td>
	    <tr>
	</table>
</BODY>
</HTML>
>