<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>职位搜索</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="bootstrap/css/style.css" rel="stylesheet">
	
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
				<a class="navbar-brand" href="index.jsp">Jobpopo</a>
				<a class="navbar-brand" href="employee.jsp"><span style="color:orange">职位搜索</span></a>
				<a class="navbar-brand" href="employer.jsp">简历搜索</a>
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
						<button type="button" class="btn btn-primary" onclick="javascript:window.location.href='/employee/regist'">注册</button>
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
									<li><a href="search/updateRelevanceForEmployee">更新推荐职位</a></li>
									<li><a href="search/listMatchPosition?offset=0">查看推荐职位</a></li>
									<li><a href="employee/check?employeeId=${employee.id }">订阅职位推送</a></li>
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

	<!-- 搜索栏 -->
	<div class="container">
		<div class="row">
			<div class="col-md-12">
				<div id="single" class="carousel slide" data-ride="carousel" data-shift="1">
					<div class="carousel-inner">
						<ul class="row item active">
							<li class="col-xs-12 one">
					  			<img src="bootstrap/img/guideEmployee/guideEmployee1.jpg" class="img-responsive">
					  			<!-- 1080px * 500px(style.css) => 1216px * 564px -->
							</li>
				  		</ul>
					  	<ul class="row item">
						  	<li class="col-xs-12 two">
							  	<img src="bootstrap/img/guideEmployee/guideEmployee2.jpg" class="img-responsive">
							</li>
							<li class="col-xs-12 three">
							  	<img src="bootstrap/img/guideEmployee/guideEmployee3.jpg" class="img-responsive">
							</li>
							<li class="col-xs-12 four">
							  	<img src="bootstrap/img/guideEmployee/guideEmployee4.jpg" class="img-responsive">
							</li>
					  	</ul>
					</div>
					<a class="carousel-control left" href="#single" data-slide="prev">Previous</a>
					<a class="carousel-control right" href="#single" data-slide="next">Next</a>
				</div>
			</div>
			<div class="col-md-8 col-md-offset-2">
				<form action="search/listMatchPosition?offset=0" method="post" class="form-inline" style="text-align:center;">
					<button style="width:15%" type="submit" class="btn btn-lg btn-primary">跳过教程</button>
				</form>
			</div>
		</div>
	</div><!-- /.container -->
	
	<!-- js在最后加载 -->
	<script src="bootstrap/js/jquery-1.11.3.min.js"></script>
	<script src="bootstrap/js/bootstrap.min.js"></script>
	<script src="bootstrap/js/index.js"></script>
	
  </body>
</html>
