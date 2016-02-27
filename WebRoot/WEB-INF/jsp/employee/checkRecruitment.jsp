<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>查看招聘信息</title>
    
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
			<div class="navbar-collapse collapse">
				<c:if test="${empty employee }">
					<form action="/employee/login" method="post" class="navbar-form navbar-right">
						<div class="form-group">
							<input name="username" type="text" placeholder="Email"
								class="form-control">
						</div>
						<div class="form-group">
							<input name="password" type="password" placeholder="Password"
								class="form-control">
						</div>
						<button type="submit" class="btn btn-primary">登录</button>
						<button type="button" class="btn btn-primary" onclick="javascript:window.location.href='/employee/regist'">求职者注册</button>
					</form>
				</c:if>
				<c:if test="${not empty employee }">
					<ul class="nav navbar-nav navbar-right">
						<li class="dropdown">
							 <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="true">
								${employee.email }
								<span class="caret"></span>
							</a>
							<ul class="dropdown-menu" role="menu">
								<c:if test="${employee.hasResume == 0 }">
									<li><a href="resume/addResume">添加简历</a></li>
								</c:if>
								<c:if test="${employee.hasResume == 1 }">
									<li><a href="resume/checkResume?employeeId=${employee.id }">查看简历</a></li>
									<li><a href="search/updateRelevanceForEmployee">更新匹配度</a></li>
									<li><a href="search/listMatchRecruitment?offset=0">查看匹配职位</a></li>
								</c:if>
								<li role="separator" class="divider"></li>
								<li><a href="employee/logout">退出</a></li>
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
			<div class="col-md-8 col-md-offset-2">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<h4>${recruitment.title }</h4>
					</div>
					<div class="panel-body">
						<p>来自：${recruitment.company }</p>
						<br>
						<div class="row">
							<div class="col-md-4">
								<p>公司行业：${recruitment.business }</p>
							</div>
							<div class="col-md-4">
								<p>公司规模：${recruitment.scale }</p>
							</div>
							<div class="col-md-4">
								<p>公司类型：${recruitment.type }</p>
							</div>
						</div>
						<div class="row">
							<div class="col-md-4">
								<p>薪资：${recruitment.salary }</p>
							</div>
							<div class="col-md-4">
								<p>学历：${recruitment.degree }</p>
							</div>
							<div class="col-md-4">
								<p>工作地点：${recruitment.city }</p>
							</div>
						</div>
						<div class="row">
							<div class="col-md-4">
								<p>职位名称：${recruitment.position }</p>
							</div>
							<div class="col-md-4">
								<p>招聘人数：${recruitment.recruitNum }</p>
							</div>
							<div class="col-md-4">
								<p>发布时间：${recruitment.uploadTime }</p>
							</div>
						</div>
						<br>
						<p>
							<%request.setAttribute("enter", "\n"); %>
							职位描述：<br>
							<c:set value="${ fn:split(recruitment.description, enter) }" var="description" />
							<c:forEach items="${ description }" var="desc">
								${ desc } <br />
							</c:forEach>
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>
    
	<script type="text/javascript" src="bootstrap/js/jquery-1.11.3.min.js" charset="UTF-8"></script>
	<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
  </body>
</html>
