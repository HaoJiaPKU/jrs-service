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
    
    <title>修改照片</title>
    
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
		<!-- 基本信息 -->
		<div class="row">
			<div class="col-md-6 col-md-offset-3">
				<div class="panel panel-primary">
					<div class="panel-heading">
						上传头像
					</div>
					<div class="panel-body">
						<form class="form-horizontal" action="resume/changePhoto" method="post" id="basicForm" enctype="multipart/form-data">
							<div class="form-group">
								<label for="photo" class="col-md-3 control-label">上传照片</label>
								<div class="col-md-6"><input name="photo" type="file" id="photo" class="form-control"></div>
								<div class="col-md-6 col-md-offset-3"><p class="help-block">建议图片大小：150*150</p></div>
							</div>
							<div class="form-group">
								<div class="col-md-3 col-md-offset-3">
									<button class="btn btn-block btn-primary" type="submit">上传</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div><!-- 基本信息 -->
	</div>
  </body>
</html>
