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
    
    <title>更新其他信息</title>
    
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
		<!-- 添加基本信息 -->
		<div class="row">
			<div class="col-md-8 col-md-offset-2 jumbotron">
				<div class="">
					<div class="" style="font-size:18px;">
						<p><strong>其他信息</strong></p>
					</div>
					<hr style="height:4px; border-top:2px solid #e4dddd;"/>
					
					<div class="panel-body">
						<form class="form-horizontal" action="resume/updateOtherInfo" method="post" id="basicForm">
							<div class="form-group">
								<div class="col-md-12">
									<textarea name="otherInfo" id="otherInfo" class="form-control" rows="3">${resume.otherInfo }</textarea>
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-2 col-md-offset-10">
									<button id="save" class="btn btn-primary btn-block" type="submit">保存</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
  </body>
</html>
