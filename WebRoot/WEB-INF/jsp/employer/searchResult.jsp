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
    
    <title>简历搜索</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="bootstrap/rewritecss/content.css" rel="stylesheet">
	
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
			<div class="col-md-8">
				<!-- 搜索栏 -->
				<form action="search/searchResume" method="post" class="form-inline">
					<input style="width:85%" type="text" name="key" value="${key }" class="form-control input-lg" placeholder="简历关键词">
					<input type="hidden" name="offset" value="0">
					<button type="submit" class="btn btn-lg btn-primary">简历搜索</button>
				</form>
			</div>
			<%	ResultPage<AbstractResume> pager = (ResultPage<AbstractResume>) request
						.getAttribute("searchResult");
				for (AbstractResume abs : pager.getDatas()) { 
			%>
		
			<div class="col-md-8 content-list">
				<!-- 本网站的简历 -->		
				<%		if (abs instanceof ResumeJobpopo) {
							ResumeJobpopo resume = (ResumeJobpopo) abs;
				%>
				<h4>
					<a href="position/checkResume?employeeId=<%=resume.getEmployeeId()%>" target="_blank"><%=resume.getName()%> &nbsp &nbsp 简历</a>
				</h4>
				<p>
					<%=resume.getUploadTime().toString().substring(0, 10)%>
					&nbsp &nbsp &nbsp &nbsp &nbsp
					<%=resume.getEducationBackground()%>
					<% if (resume.getIndustryIntension() != null && resume.getIndustryIntension().length() > 0) {%>
					&nbsp &nbsp &nbsp &nbsp &nbsp
					期望行业：<%=resume.getIndustryIntension()%>
					<% }%>
					<% if (resume.getCategoryIntension() != null && resume.getCategoryIntension().length() > 0) {%>
					&nbsp &nbsp &nbsp &nbsp &nbsp
					期望职位：<%=resume.getCategoryIntension()%>
					<% }%>
				</p>
				
				<!-- 51job上的简历 -->
				<%		} else { %>
				<%			Resume51Job resume = (Resume51Job)abs;  %>
				
				<h4>
					<a href="<%=resume.getPath()%>" target="_blank">简历</a>
				</h4>
				<p><iframe width="100%" height="30%" border="1" src="<%=resume.getPath()%>" style="zoom:0.5;"></iframe></p>
				<%
					}
				%>
				<br>
			</div>
			<%
				}
			%>
			
			<div class="col-md-8"> 	
				<!-- 分页 -->
			  	<ul class="pagination">
			  		<c:if test="${searchResult.offset > 0 }">
			  			<li><a href="search/searchResume?key=${key }&city=${city }&offset=${searchResult.offset-searchResult.size}">&laquo;</a></li>
			  		</c:if>
			  		<c:forEach var="id" begin="0" end="9">
			  			<c:if test="${id * searchResult.size < searchResult.total }">
			  				<li><a href="search/searchResume?key=${key }&offset=${id*searchResult.size}">${id+1}</a></li>
			  			</c:if>
			  		</c:forEach>
			  		<c:if test="${searchResult.offset+searchResult.size<searchResult.total }">
			  			<li><a href="search/searchResume?key=${key}&offset=${searchResult.offset+searchResult.size}">&raquo;</a></li>
			  		</c:if>
			  	</ul>
	  		</div>
	  	</div>
	</div><!-- /.container -->
  
  	<!-- js在最后加载 -->
    <script src="bootstrap/js/jquery-1.11.3.min.js"></script>
	<script src="bootstrap/js/bootstrap.min.js"></script>
  </body>
</html>
