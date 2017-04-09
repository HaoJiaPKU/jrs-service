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
    
    <title>查看已发布信息</title>
    
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
			<div class="col-md-10 col-md-offset-1 jumbotron">
				<div class="">
					<div class="" style="font-size:18px;">
						<div style="float:left;"><p><strong>${position.title }</strong></p></div>
						<div style="clear: both;"></div>
						<hr style="height:4px; border-top:2px solid #e4dddd;"/>
					</div>
					<div class="panel-body">
						<p><strong style="font-size:16px;">来自：</strong><strong style="color:#727272; font-size:16px;">${position.company }</strong></p>
						<br>
						<div class="row">
							<div class="col-md-4">
								<p><strong style="font-size:16px;">公司行业：</strong><strong style="color:#727272; font-size:16px;">${position.business }</strong></p>
							</div>
							<div class="col-md-4">
								<p><strong style="font-size:16px;">公司规模：</strong><strong style="color:#727272; font-size:16px;">${position.scale }</strong></p>
							</div>
							<div class="col-md-4">
								<p><strong style="font-size:16px;">公司类型：</strong><strong style="color:#727272; font-size:16px;">${position.type }</strong></p>
							</div>
						</div>
						<div class="row">
							<div class="col-md-4">
								<p><strong style="font-size:16px;">薪资：</strong><strong style="color:#727272; font-size:16px;">${position.salary }</strong></p>
							</div>
							<div class="col-md-4">
								<p><strong style="font-size:16px;">学历：</strong><strong style="color:#727272; font-size:16px;">${position.degree }</strong></p>
							</div>
							<div class="col-md-4">
								<p><strong style="font-size:16px;">工作地点：</strong><strong style="color:#727272; font-size:16px;">${position.city }</strong></p>
							</div>
						</div>
						<div class="row">
							<div class="col-md-4">
								<p><strong style="font-size:16px;">职位名称：</strong><strong style="color:#727272; font-size:16px;">${position.position }</strong></p>
							</div>
							<div class="col-md-4">
								<p><strong style="font-size:16px;">招聘人数：</strong><strong style="color:#727272; font-size:16px;">${position.recruitNum }</strong></p>
							</div>
							<div class="col-md-4">
								<p><strong style="font-size:16px;">发布时间：</strong><strong style="color:#727272; font-size:16px;">${position.uploadTime.toString().substring(0, 10) }</strong></p>
							</div>
						</div>
						<br>
						
						<p><strong style="font-size:16px;">职位描述：</strong></p>
						<%request.setAttribute("enter", "\n"); %>
						<c:set value="${ fn:split(position.description, enter) }" var="description" />
						<c:forEach items="${ description }" var="desc">
							<p><strong style="color:#727272; font-size:16px;">${ desc } </strong></p>
						</c:forEach>
						
					</div>
				</div>
			</div>
		</div>
	</div>
    
	<script type="text/javascript" src="bootstrap/js/jquery-1.11.3.min.js" charset="UTF-8"></script>
	<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
  </body>
</html>
