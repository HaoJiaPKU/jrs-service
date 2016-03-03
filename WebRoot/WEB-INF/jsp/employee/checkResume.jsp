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
						<div><div style="float: left;">基本资料</div><div style="float: right">
						<a style="color:white" href="resume/updateResume"><u>编辑资料</u></a></div>
						<div style="clear: both;"></div></div>
					</div>
					<div class="panel-body">
						<div class="col-md-4">
							<a href="resume/changePhoto">
								<img src="${resume.photo }" class="img-responsive" alt="如果未正常显示，请刷新页面">
							</a>
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
						<div><div style="float: left;">教育经历</div><div style="float: right">
						<a style="color:white" href="resume/addEducation"><u>新增经历</u></a></div>
						<div style="clear: both;"></div></div>
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
							
							<%request.setAttribute("enter", "\n"); %>
							<c:set value="${ fn:split(edu.description, enter) }" var="description" />
							<c:forEach items="${ description }" var="desc">
								<p>${ desc } </p>
							</c:forEach>
							
							<div class="text-right"><a href="resume/updateEducation?eduId=${edu.id }" >编辑</a>
							<a href="resume/deleteEducation?eduId=${edu.id }&employeeId=${edu.employeeId}">删除</a></div>
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
						<div><div style="float: left;">工作经历</div><div style="float: right">
						<a style="color:white" href="resume/addWorkExperience"><u>新增经历</u></a></div>
						<div style="clear: both;"></div></div>
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
							
							<%request.setAttribute("enter", "\n"); %>
							<c:set value="${ fn:split(work.description, enter) }" var="description" />
							<c:forEach items="${ description }" var="desc">
								<p>${ desc } </p>
							</c:forEach>
							
							<div class="text-right"><a href="resume/updateWorkExperience?workId=${work.id }" >编辑</a>
							<a href="resume/deleteWork?workId=${work.id }&employeeId=${work.employeeId}">删除</a></div>
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
						<div><div style="float: left;">专长</div><div style="float: right">
						<a style="color:white" href="resume/updateSpeciality"><u>修改</u></a></div>
						<div style="clear: both;"></div></div>
					</div>
					<div class="panel-body">
						<%request.setAttribute("enter", "\n"); %>
						<c:set value="${ fn:split(resume.speciality, enter) }" var="description" />
						<c:forEach items="${ description }" var="desc">
							<p>${ desc } </p>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
		
		<!-- 奖惩信息 -->
		<div class="row">
			<div class="col-md-6 col-md-offset-3">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<div><div style="float: left;">奖惩信息</div><div style="float: right">
						<a style="color:white" href="resume/updateReward"><u>修改</u></a></div>
						<div style="clear: both;"></div></div>
					</div>
					<div class="panel-body">
						<%request.setAttribute("enter", "\n"); %>
						<c:set value="${ fn:split(resume.rewardAndPunishment, enter) }" var="description" />
						<c:forEach items="${ description }" var="desc">
							<p>${ desc } </p>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
		
		<!-- 其他信息 -->
		<div class="row">
			<div class="col-md-6 col-md-offset-3">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<div><div style="float: left;">其他信息</div><div style="float: right">
						<a style="color:white" href="resume/updateOtherInfo"><u>修改</u></a></div>
						<div style="clear: both;"></div></div>
					</div>
					<div class="panel-body">
						<%request.setAttribute("enter", "\n"); %>
						<c:set value="${ fn:split(resume.otherInfo, enter) }" var="description" />
						<c:forEach items="${ description }" var="desc">
							<p>${ desc } </p>
						</c:forEach>
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
