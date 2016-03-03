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
    <!-- 导航条 -->
    <jsp:include page="navigation.jsp"/>
	
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
