<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">
		
		<title>求职者注册</title>
		
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
			<form action="employee/regist" method="post">
				<div class="form-group">
					<label for="email">邮箱</label>
					<input type="text" name="email" class="form-control" id="email"
						placeholder="邮箱" required autofocus>
				</div>
				<div class="form-group">
					<label for="password">密码</label>
					<input type="password" name="password" class="form-control" id="password"
						placeholder="密码" required>
				</div>
				<div class="form-group">
					<button class="btn btn-primary btn-block" type="submit">注册</button>
				</div>
			</form>
			</div>
			</div>
		</div><!-- /container -->
				
		<script src="bootstrap/js/bootstrap.min.js"></script>
		<script src="bootstrap/js/jquery-2.1.4.min.js"></script>
		
	</body>
</html>
