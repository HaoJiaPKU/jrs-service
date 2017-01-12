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
			<div class="col-md-8 col-md-offset-2">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<h4>${position.title }</h4>
					</div>
					<div class="panel-body">
						<p><font color="#428bca">来自：</font>${position.company }</p>
						<br>
						<div class="row">
							<div class="col-md-4">
								<p><font color="#428bca">公司行业：</font>${position.business }</p>
							</div>
							<div class="col-md-4">
								<p><font color="#428bca">公司规模：</font>${position.scale }</p>
							</div>
							<div class="col-md-4">
								<p><font color="#428bca">公司类型：</font>${position.type }</p>
							</div>
						</div>
						<div class="row">
							<div class="col-md-4">
								<p><font color="#428bca">薪资：</font>${position.salary }</p>
							</div>
							<div class="col-md-4">
								<p><font color="#428bca">学历：</font>${position.degree }</p>
							</div>
							<div class="col-md-4">
								<p><font color="#428bca">工作地点：</font>${position.city }</p>
							</div>
						</div>
						<div class="row">
							<div class="col-md-4">
								<p><font color="#428bca">职位名称：</font>${position.position }</p>
							</div>
							<div class="col-md-4">
								<p><font color="#428bca">招聘人数：</font>${position.recruitNum }</p>
							</div>
							<div class="col-md-4">
								<p><font color="#428bca">发布时间：</font>${position.uploadTime }</p>
							</div>
						</div>
						<br>
						
						<font color="#428bca">职位描述：</font><br><br>
						<%request.setAttribute("enter", "\n"); %>
						<c:set value="${ fn:split(position.description, enter) }" var="description" />
						<c:forEach items="${ description }" var="desc">
							<p>${ desc } </p>
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
