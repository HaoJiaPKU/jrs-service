<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="cn.edu.pku.search.domain.*"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>查看匹配简历</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<style>
		body {
			padding-top: 80px;
		}
	</style>

  </head>
  
  <body>
    
  	<!-- 导航条 -->
    <jsp:include page="navigation.jsp"/>
	
	<div class="container">
		<div class="row">
			<div class="col-md-7">
				<%	Pager<MatchResume> relevancePager = (Pager<MatchResume>) session.getAttribute("relevancePager");%>
				<%	for (MatchResume match : relevancePager.getDatas()) { %>
						<!-- 本网站的简历 -->		
				<%		if (match.getResume() instanceof Resume) {  %>
				<%			Resume resume = (Resume) match.getResume();  %>
				<h3>
					<a href="recruitment/checkResume?employeeId=<%=resume.getEmployeeId()%>" target="_blank"><%=resume.getName()+"-"+resume.getEducationBackground()%></a>
				</h3>
					相关度：<%=match.getRelevance() %>
				
						<!-- 51job上的简历 -->
				<%		} else { %>
				<%			Resume51Job resume = (Resume51Job)match.getResume();  %>
				
				<h3>
					<a href="<%=resume.getPath()%>" target="_blank">查看完整简历</a>
				</h3>
					相关度：<%=match.getRelevance() %>
				
				<iframe width="110%" height="30%" border="1" src="<%=resume.getPath()%>" style="zoom:0.5;"></iframe>
				<br>

				<%
					}
				%>
				<br> 
				<%
					}
				%>
				
			</div>
		</div>
	
		<!-- 分页 -->
		<div class="row">
			<div class="col-md-8 col-md-offset-2">
				<ul class="pagination">
					<c:if test="${relevancePager.offset > 0 }">
						<li><a
							href="search/listMatchResume?recruitmentId=<%=request.getParameter("recruitmentId")%>&offset=${relevancePager.offset-relevancePager.size}">&laquo;</a></li>
					</c:if>
					<c:forEach var="id" begin="0" end="9">
						<c:if test="${id * relevancePager.size < relevancePager.total }">
							<c:if test="${id * relevancePager.size != relevancePager.offset }">
								<li><a
									href="search/listMatchResume?recruitmentId=<%=request.getParameter("recruitmentId")%>&offset=${id*relevancePager.size}">${id+1}</a></li>
							</c:if>
							<c:if test="${id * relevancePager.size == relevancePager.offset }">
								<li class="active"><a href="javascript:void(0)">${id+1}<span
										class="sr-only">(current)</span></a></li>
							</c:if>
						</c:if>
					</c:forEach>
					<c:if test="${relevancePager.offset+relevancePager.size<relevancePager.total }">
						<li><a
							href="search/listMatchResume?recruitmentId=<%=request.getParameter("recruitmentId")%>&offset=${relevancePager.offset+relevancePager.size}">&raquo;</a></li>
					</c:if>
				</ul>
			</div>
		</div>


	</div>
	<!-- js在最后加载 -->
	<script type="text/javascript" src="bootstrap/js/jquery-1.11.3.min.js" charset="UTF-8"></script>
	<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
	
  </body>
</html>
