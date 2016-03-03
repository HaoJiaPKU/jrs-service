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
    
    <title>查看简历</title>
    
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
						基本资料
					</div>
					<div class="panel-body">
						<div class="col-md-4">
							<img src="${resume.photo }" class="img-responsive" alt="如果未正常显示，请刷新页面">
						</div>
						<div class="col-md-8">
							<h2 style="margin-top:10px">
								${resume.name }
							</h2>
							<p>${resume.educationBackground }&nbsp|&nbsp${resume.gender }&nbsp|&nbsp${resume.age }
							&nbsp|&nbsp${resume.politics }
							</p>
							<p>手机：${resume.phone }</p>
							<p>邮箱：${resume.email }</p>
							<p>期望工作地点：${resume.workingPlace }</p>
						</div>
					</div>
				</div>
			</div>
		</div><!-- 基本信息 -->
		
		<!-- 教育信息 -->
		<div class="row">
			<div class="col-md-6 col-md-offset-3">
				<div class="panel panel-primary">
					<div class="panel-heading">
						教育经历
					</div>
					
					<c:forEach items="${eduList }" var="edu" varStatus="index">
						<div class="panel-body">
							<h3>${edu.school }</h3>
							<div>
								<div style="float: left;">${edu.degree }&nbsp&nbsp${edu.academy }&nbsp&nbsp${edu.major }</div>
								<div style="float: right">${edu.dateBegin }至${edu.dateEnd }</div>
								<div style="clear: both;"></div>
							</div>
							<br>
							<p>${edu.description }</p>
						</div>
					</c:forEach>
				</div>
			</div>
		</div><!-- 教育信息 -->
		
		<!-- 工作经历 -->
		<div class="row">
			<div class="col-md-6 col-md-offset-3">
				<div class="panel panel-primary">
					<div class="panel-heading">
						工作经历
					</div>
					
					<c:forEach items="${workList }" var="work" varStatus="index">
						<div class="panel-body">
							<h2>${work.company }</h2>
							<div>
								<div style="float: left;">${work.jobTitle }</div>
								<div style="float: right">${work.dateBegin }至${work.dateEnd }</div>
								<div style="clear: both;"></div>
							</div>
							<br>
							<p>${work.description }</p>
						</div>
						
					</c:forEach>
				</div>
			</div>
		</div>
		
		<!-- 专长 -->
		<div class="row">
			<div class="col-md-6 col-md-offset-3">
				<div class="panel panel-primary">
					<div class="panel-heading">
						专长
					</div>
					<div class="panel-body">
						<p>${resume.speciality }</p>
					</div>
				</div>
			</div>
		</div>
		
		<!-- 奖惩信息 -->
		<div class="row">
			<div class="col-md-6 col-md-offset-3">
				<div class="panel panel-primary">
					<div class="panel-heading">
						奖惩信息
					</div>
					<div class="panel-body">
						<p>${resume.rewardAndPunishment }</p>
					</div>
				</div>
			</div>
		</div>
		
		<!-- 其他信息 -->
		<div class="row">
			<div class="col-md-6 col-md-offset-3">
				<div class="panel panel-primary">
					<div class="panel-heading">
						其他信息
					</div>
					<div class="panel-body">
						<p>${resume.otherInfo }</p>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	
	<!-- js在最后加载 -->
	<script type="text/javascript" src="bootstrap/js/jquery-1.11.3.min.js" charset="UTF-8"></script>
	<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
  </body>
</html>
