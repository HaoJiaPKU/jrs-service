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
			<div class="col-md-8 col-md-offset-2 jumbotron">
				<div class="">
					<div class="" style="font-size:18px;">
						<div style="float:left;"><p><strong>基本资料</strong></p></div>
						<div style="clear: both;"></div>
						<hr style="height:4px; border-top:2px solid #e4dddd;"/>
					</div>
					<div class="panel-body">
						<div class="col-md-8">
							<p><strong style="color:#727272; font-size:20px;">
								${resume.name }</strong>&nbsp &nbsp
							<strong style="color:#727272; font-size:16px;">
							${resume.educationBackground }&nbsp|&nbsp${resume.gender }&nbsp|&nbsp${resume.age }
							&nbsp|&nbsp${resume.politics }
							</strong></p>
							<p><strong style="color:#727272; font-size:16px;">手机：${resume.phone }</strong></p>
							<p><strong style="color:#727272; font-size:16px;">邮箱：${resume.email }</strong></p>
							<p><strong style="color:#727272; font-size:16px;">期望工作地点：${resume.workingPlace }</strong></p>
							<p><strong style="color:#727272; font-size:16px;">期望行业类别：${resume.industryIntension}</strong></p>
							<p><strong style="color:#727272; font-size:16px;">期望职位类别：${resume.categoryIntension}</strong></p>
						</div>
						<div class="col-md-4">
							<img src="${resume.photo }" class="img-responsive" alt="未上传头像">
						</div>
					</div>
				</div>
			</div>
		</div><!-- 基本信息 -->
		
		<!-- 教育信息 -->
		<div class="row">
			<div class="col-md-8 col-md-offset-2 jumbotron">
				<div class="">
					<div class="" style="font-size:18px;">
						<div style="float:left;"><p><strong>教育经历</strong></p></div>
						<div style="clear:both;"></div>
						<hr style="height:4px; border-top:2px solid #e4dddd;"/>
					</div>
					
					<c:forEach items="${eduList }" var="edu" varStatus="index">
						<div class="panel-body">
							<p><strong style="color:#727272; font-size:16px;">${edu.school }</strong></p>
							<div>
								<div style="float: left;"><p><strong style="color:#727272; font-size:16px;">${edu.degree }&nbsp&nbsp${edu.academy }&nbsp&nbsp${edu.major }</strong></p></div>
								<div style="float: right"><p><strong style="color:#727272; font-size:16px;">${edu.dateBegin }至${edu.dateEnd }</strong></p></div>
								<div style="clear: both;"></div>
							</div>
							<br>
							<p><strong style="color:#727272; font-size:16px;">${edu.description }</strong></p>
						</div>
					</c:forEach>
				</div>
			</div>
		</div><!-- 教育信息 -->
		
		<!-- 工作经历 -->
		<div class="row">
			<div class="col-md-8 col-md-offset-2 jumbotron">
				<div class="">
					<div class="" style="font-size:18px;">
						<div style="float:left;"><p><strong>工作经历</strong></p></div>
						<div style="clear:both;"></div>
						<hr style="height:4px; border-top:2px solid #e4dddd;"/>
					</div>
					
					<c:forEach items="${workList }" var="work" varStatus="index">
						<div class="panel-body">
							<p><strong style="color:#727272; font-size:20px;">${work.company }</strong></p>
							<div>
								<div style="float: left;"><p><strong style="color:#727272; font-size:16px;">${work.jobTitle }</strong></p></div>
								<div style="float: right"><p><strong style="color:#727272; font-size:16px;">${work.dateBegin }至${work.dateEnd }</strong></p></div>
								<div style="clear: both;"></div>
							</div>
							<br>
							<p><strong style="color:#727272; font-size:16px;">${work.description }</strong></p>
						</div>
						
					</c:forEach>
				</div>
			</div>
		</div>
		
		<!-- 特长信息 -->
		<div class="row">
			<div class="col-md-8 col-md-offset-2 jumbotron">
				<div class="">
					<div class="" style="font-size:18px;">
						<div style="float:left;"><p><strong>特长信息</strong></p></div>
						<div style="clear:both;"></div>
						<hr style="height:4px; border-top:2px solid #e4dddd;"/>
					</div>
					<div class="panel-body">
						<p><strong style="color:#727272; font-size:16px;">${resume.speciality }</strong></p>
					</div>
				</div>
			</div>
		</div>
		
		<!-- 奖惩信息 -->
		<div class="row">
			<div class="col-md-8 col-md-offset-2 jumbotron">
				<div class="">
					<div class="" style="font-size:18px;">
						<div style="float:left;"><p><strong>奖惩信息</strong></p></div>
						<div style="clear:both;"></div>
						<hr style="height:4px; border-top:2px solid #e4dddd;"/>
					</div>
					<div class="panel-body">
						<p><strong style="color:#727272; font-size:16px;">${resume.rewardAndPunishment }</strong></p>
					</div>
				</div>
			</div>
		</div>
		
		<!-- 其他信息 -->
		<div class="row">
			<div class="col-md-8 col-md-offset-2 jumbotron">
				<div class="">
					<div class="" style="font-size:18px;">
						<div style="float:left;"><p><strong>其他信息</strong></p></div>
						<div style="clear:both;"></div>
						<hr style="height:4px; border-top:2px solid #e4dddd;"/>
					</div>
					<div class="panel-body">
						<p><strong style="color:#727272; font-size:16px;">${resume.otherInfo }</strong></p>
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
