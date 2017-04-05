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
    
    <title>简历搜索</title>
    
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
				<a class="navbar-brand" href="employee.jsp">职位搜索</a>
				<a class="navbar-brand" href="employer.jsp"><span style="color:orange">简历搜索</span></a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<c:if test="${empty employer }">
					<form action="/employer/login" method="post" class="navbar-form navbar-right">
						<div class="form-group">
							<input name="email" type="text" placeholder="email"
								class="form-control">
						</div>
						<div class="form-group">
							<input name="password" type="password" placeholder="Password"
								class="form-control">
						</div>
						<button type="submit" class="btn btn-primary">登录</button>
						<button type="button" class="btn btn-primary" onclick="javascript:window.location.href='/employer/regist'">注册</button>
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
								<li><a href="position/addPosition">发布职位信息</a></li>
								<li><a href="position/listPosition?employerId=${employer.id }">查看已发布职位</a>
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
				<form action="employer.jsp" method="post" class="form-inline" style="text-align:center;">
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
