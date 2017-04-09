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
    
    <title>My JSP 'publishResume.jsp' starting page</title>
    
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
		<div class="row">
			<div class="col-md-8 col-md-offset-2 jumbotron">
				<div class="">
					<div class="" style="font-size:18px; text-align:center;">
						简历填写完毕，请选择发布或取消
					</div>
					<div style="clear: both;"></div>
					<hr style="height:4px; border-top:2px solid #e4dddd;"/>
							
					<form class="form-horizontal" action="resume/publishResume"
						method="post" id="basicForm">
						<div class="col-md-1 col-md-offset-5">
							<button class="btn btn-primary" type="submit">发布</button>
						</div>
						<div class="col-md-1">
							<a class="btn btn-primary" href="resume/cancelPublishResume" role="button">取消</a>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
  
    <script type="text/javascript" src="bootstrap/js/jquery-1.11.3.min.js" charset="UTF-8"></script>
	<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
  </body>
</html>
