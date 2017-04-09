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
    
  	<!-- 导航条 -->
    <jsp:include page="navigation.jsp"/>
	
	<div class="container">
		<div class="row">
			<div class="col-md-8 col-md-offset-2 jumbotron">
			 	<form class="form-horizontal" action="employer/regist" method="post">
					<div class="form-group">
						<label for="companyname" class="col-sm-2 control-label">企业名称</label>
						<div class="col-sm-9"><input type="text" name="companyname" id="comoanyname" class="form-control"
							placeholder="企业名称" required autofocus></div>
					</div>	
					<div class="form-group">
						<label class="col-sm-2 control-label">公司地址</label>
							<div class="col-sm-3"><input type="text" name="province" id="province" class="form-control"
								placeholder="省" required></div>
							<label for="province" class="col-sm-1 control-label">省</label>
							<div class="col-sm-3 col-md-offset-1"><input type="text" name="city" id="city" class="form-control"
								placeholder="市" required></div>
							<label for="city" class="col-sm-1 control-label">市</label>
					</div>	
					<div class="form-group">
						<label class="col-sm-2 control-label" for="linkman">联系人</label>
						<div class="col-sm-9"><input type="text" name="linkman" class="form-control"
							placeholder="联系人" required></div>
					</div>	
					<div class="form-group">
						<label class="control-label col-sm-2">性别</label>
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
						<label class="col-sm-2 control-label" for="phone">联系电话</label>
						<div class="col-sm-9"><input type="text" name="phone" class="form-control"
							placeholder="联系电话" required></div>
					</div>
					
					<hr>
					
					<div class="form-group">
						<label class="col-sm-2 control-label" for="email">邮箱</label>
						<div class="col-sm-9"><input type="text" name="email" class="form-control"
							placeholder="邮箱" required></div>
					</div>	
					<div class="form-group">
						<label class="col-sm-2 control-label" for="password">密码</label>
						<div class="col-sm-9"><input type="password" name="password" class="form-control"
							placeholder="密码" required>	</div>
					</div>	
					<div class="form-group">
						<div class="col-md-3 col-md-offset-2"><button class="btn btn-primary btn-block" type="submit">注册</button></div>
					</div>
				</form>
			</div>
		</div>
	</div><!-- /container -->
				
	<script src="bootstrap/js/bootstrap.min.js"></script>
	<script src="bootstrap/js/jquery-2.1.4.min.js"></script>
  </body>
</html>
