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
    
    <title>企业注册</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
		<style type="text/css">
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
								${employer.username }
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
			<div class="col-md-4 col-md-offset-4">
			 	<form class="form-horizontal" action="employer/regist" method="post">
					<div class="form-group">
						<label for="companyname" class="col-sm-3 control-label">企业名称</label>
						<div class="col-sm-9"><input type="text" name="companyname" id="comoanyname" class="form-control"
							placeholder="企业名称" required autofocus></div>
					</div>	
					<div class="form-group">
						<label class="col-sm-3 control-label">公司地址</label>
							<div class="col-sm-3"><input type="text" name="province" id="province" class="form-control"
								placeholder="省" required></div>
							<label for="province" class="col-sm-1 control-label">省</label>
							<div class="col-sm-3"><input type="text" name="city" id="city" class="form-control"
								placeholder="市" required></div>
							<label for="city" class="col-sm-1 control-label">市</label>
					</div>	
					<div class="form-group">
						<label class="col-sm-3 control-label" for="linkman">联系人</label>
						<div class="col-sm-9"><input type="text" name="linkman" class="form-control"
							placeholder="联系人" required></div>
					</div>	
					<div class="form-group">
						<label class="control-label col-sm-3">性别</label>
						<div class="col-sm-9">
							<label class="radio-inline">
							  <input type="radio" name="gender" value="男"> 男
							</label>
							<label class="radio-inline">
							  <input type="radio" name="gender" value="女"> 女
							</label>
						</div>
					</div>	
					<div class="form-group">
						<label class="col-sm-3 control-label" for="phone">联系电话</label>
						<div class="col-sm-9"><input type="text" name="phone" class="form-control"
							placeholder="联系电话" required></div>
					</div>
					
					<hr>
					
					<div class="form-group">
						<label class="col-sm-3 control-label" for="email">邮箱</label>
						<div class="col-sm-9"><input type="text" name="email" class="form-control"
							placeholder="邮箱" required></div>
					</div>	
					<div class="form-group">
						<label class="col-sm-3 control-label" for="password">密码</label>
						<div class="col-sm-9"><input type="password" name="password" class="form-control"
							placeholder="密码" required>	</div>
					</div>	
					<div class="form-group">
						<div class="col-md-3 col-md-offset-4"><button class="btn btn-primary btn-block" type="submit">注册</button></div>
					</div>
				</form>
			</div>
		</div>
	</div><!-- /container -->
				
	<script src="bootstrap/js/bootstrap.min.js"></script>
	<script src="bootstrap/js/jquery-2.1.4.min.js"></script>
  </body>
</html>
