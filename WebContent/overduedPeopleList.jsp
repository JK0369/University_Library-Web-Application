<%@ page contentType="text/html;charset=euc-kr"
         import="java.sql.*,Library.*, java.util.Date"  %>
<HTML>
<HEAD>
<TITLE>Normal 매니저 업무처리</TITLE>
</HEAD>

<BODY>
    <% NormalManager normalManager = (NormalManager) session.getAttribute("user"); 
       System.out.println("  << for debug >> in NormalTopMenu:NormalManager = " + normalManager.getId()); 
 
       %>
        
	<table width=100%>
		<tr>
			<td  align=center width=15%><img src="img/management.gif" weight=70 height=70/></td>
			
			<td width=70%>
				<H2 align=center>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;Normal Manager 업무처리&nbsp; &nbsp; &nbsp; &nbsp;  </H2>
			</td>
			
			<td width=15%>

				<form name=form1 method=post action=systemLogout.jsp>

					<H6>
						<TABLE align=left border=0 cellpadding=1 cellspacing="1"
							bordercolor="#DDDDDD"  >

							<TR align=left bgcolor=white >
								<TD><img src="img/id2.gif" /></TD>
								<TD bgcolor="#DDDDDD"> <%= normalManager.getId() %>  </TD>
							</TR>

							<TR align=left bgcolor=white >
								<TD><img src="img/name.gif" /></TD>
								<TD bgcolor="#DDDDDD" > <%= normalManager.getMname() %> </TD>
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
							<td><a href="overduedBookList.jsp" >연체 중 도서 리스트
							</a></td>
							<td><a href="overduedPeopleList.jsp" >연체자 정보 리스트 
							</a></td>
							<td><a href="handlingOverdue.jsp" >연체 처리 및 갱신
							</a></td>							
						</tr>
					</table>
				</H5>
		</tr>
			
		<tr width=100% height=700>
		    <td colspan=3>				
					<iframe width=100% height=700 src=resultOverduePeopleList.jsp name=iframeContent seamless="seamless">					
	        </td>
	    <tr>
	</table>
</BODY>
</HTML>
