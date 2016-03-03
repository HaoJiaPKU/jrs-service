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
    
    <title>找简历</title>
    
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
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<a class="navbar-brand" href="employee.jsp">找工作</a>
				<a class="navbar-brand" href="employer.jsp">找简历</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<c:if test="${empty employer }">
					<form action="/employer/login" method="post" class="navbar-form navbar-right">
						<div class="form-group">
							<input name="username" type="text" placeholder="Email"
								class="form-control">
						</div>
						<div class="form-group">
							<input name="password" type="password" placeholder="Password"
								class="form-control">
						</div>
						<button type="submit" class="btn btn-primary">登录</button>
						<button type="button" class="btn btn-primary" onclick="javascript:window.location.href='/employer/regist'">企业注册</button>
					</form>
				</c:if>
				<c:if test="${not empty employer }">
					<ul class="nav navbar-nav navbar-right">
						<li class="dropdown">
							 <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="true">
								${employer.email }
								<span class="caret"></span>
							</a>
							<ul class="dropdown-menu" role="menu">
								<li><a href="recruitment/addRecruitment">发布招聘信息</a></li>
								<li><a href="recruitment/listRecruitment?employerId=${employer.id }">查看已发布信息</a>
								<li role="separator" class="divider"></li>
								<li><a href="employer/logout">退出</a></li>
							</ul>
						</li>
					</ul>
				</c:if>
			</div>
			<!--/.navbar-collapse -->
		</div>
	</nav>

	<div class="container">
		<div class="row">
			<div class="col-md-7">
				<!-- 搜索栏 -->
				<form action="search/searchResume" method="post" class="form-inline">
					<input style="width:80%" type="text" name="key" value="${key }" class="form-control input-lg" placeholder="简历关键词">
					<input type="hidden" name="offset" value="0">
					<button type="submit" class="btn btn-lg btn-primary">找简历</button>
				</form>
				<br>
			
				<%	Pager<AbstractResume> pager = (Pager<AbstractResume>) request
							.getAttribute("searchResult");%>
				<%	for (AbstractResume abs : pager.getDatas()) { %>
						<!-- 本网站的简历 -->		
				<%		if (abs instanceof Resume) {  %>
				<%			Resume resume = (Resume) abs;  %>
				<h3>
					<a href="recruitment/checkResume?employeeId=<%=resume.getEmployeeId()%>" target="_blank"><%=resume.getName()+"-"+resume.getEducationBackground()%></a>
				</h3>
				
						<!-- 51job上的简历 -->
				<%		} else { %>
				<%			Resume51Job resume = (Resume51Job)abs;  %>
				
				<h3>
					<a href="<%=resume.getPath()%>" target="_blank"><%=resume.getPath()%></a>
				</h3>
				<iframe width="110%" height="30%" border="1" src="<%=resume.getPath()%>" style="zoom:0.5;"></iframe>
				

				<%
					}
				%>
				<br> 
				<%
					}
				%>
				  	
				<!-- 分页 -->
			  	<ul class="pagination">
			  		<c:if test="${searchResult.offset > 0 }">
			  			<li><a href="search?key=${key }&city=${city }&offset=${searchResult.offset-searchResult.size}">&laquo;</a></li>
			  		</c:if>
			  		<c:forEach var="id" begin="0" end="9">
			  			<c:if test="${id * searchResult.size < searchResult.total }">
			  				<li><a href="search?key=${key }&offset=${id*searchResult.size}">${id+1}</a></li>
			  			</c:if>
			  		</c:forEach>
			  		<c:if test="${searchResult.offset+searchResult.size<searchResult.total }">
			  			<li><a href="search?key=${key}&offset=${searchResult.offset+searchResult.size}">&raquo;</a></li>
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
