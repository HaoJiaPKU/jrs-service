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
    
    <title>查看已发布招聘信息</title>
    
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
				<table class="table">
					<tr>
						<th>标题</th><th>发布时间</th><th></th><th></th><th></th>
					</tr>
					<c:forEach items="${listRecruitment }" var="recruit" varStatus="index">
						<tr>
							<td>
								<a href="recruitment/checkRecruitment?id=${recruit.id }" target="_blank">${recruit.position }</a>
							</td>
							<td>
								${recruit.uploadTime}
							</td>
							<td>
								<a href="search/updateRelevanceForEmployer?recruitmentId=${recruit.id }">更新匹配度</a>
							</td>
							<td>
								<a href="search/listMatchResume?recruitmentId=${recruit.id }&offset=0">查看匹配简历</a>
							</td>
							<td>
								<a href="recruitment/deleteRecruitment?id=${recruit.id }">删除</a>
							</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>
	
	<script type="text/javascript" src="bootstrap/js/jquery-1.11.3.min.js" charset="UTF-8"></script>
	<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
</body>
</html>
