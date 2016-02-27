<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="cn.edu.pku.search.domain.*"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>搜索结果</title>

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
	<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="employee.jsp">找工作</a> <a
				class="navbar-brand" href="employer.jsp">找简历</a>
		</div>
		<div class="navbar-collapse collapse">
			<c:if test="${empty employee }">
				<form action="/employee/login" method="post"
					class="navbar-form navbar-right">
					<div class="form-group">
						<input name="username" type="text" placeholder="Email"
							class="form-control">
					</div>
					<div class="form-group">
						<input name="password" type="password" placeholder="Password"
							class="form-control">
					</div>
					<button type="submit" class="btn btn-primary">登录</button>
					<button type="button" class="btn btn-primary"
						onclick="javascript:window.location.href='/employee/regist'">求职者注册</button>
				</form>
			</c:if>
			<c:if test="${not empty employee }">
				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown" role="button" aria-haspopup="true"
						aria-expanded="true"> ${employee.email } <span class="caret"></span>
					</a>
						<ul class="dropdown-menu" role="menu">
							<c:if test="${employee.hasResume == 0 }">
								<li><a href="resume/addResume">添加简历</a></li>
							</c:if>
							<c:if test="${employee.hasResume == 1 }">
								<li><a href="resume/checkResume?employeeId=${employee.id }">查看简历</a></li>
								<li><a href="search/updateRelevanceForEmployee">更新匹配度</a></li>
								<li><a href="search/listMatchRecruitment?offset=0">查看匹配职位</a></li>
							</c:if>
							<li role="separator" class="divider"></li>
							<li><a href="employee/logout">退出</a></li>
						</ul></li>
				</ul>
			</c:if>
		</div>
		<!--/.navbar-collapse -->
	</div>
	</nav>

	<div class="container">
		<div class="row">
			<div class="col-md-7">
				<!-- 搜索栏 -->
				<form action="search/searchRecruitment" method="post"
					class="form-inline">
					<input style="width:80%" type="text" name="key" value="${key }"
						class="form-control input-lg" placeholder="职位关键词"> 
					<input type="hidden" name="offset" value="0">
					<button type="submit" class="btn btn-lg btn-primary">找工作</button>
				</form>
				<br>

				<%
					Pager<AbstractRecruitment> pager = (Pager<AbstractRecruitment>) request
							.getAttribute("searchResult");
					for (AbstractRecruitment abs : pager.getDatas()) {
						if (abs instanceof Recruitment) {
							Recruitment recruitment = (Recruitment) abs;
				%>
				<h3>
					<a href="resume/checkRecruitment?recruitId=<%=recruitment.getId()%>" target="_blank"><%=recruitment.getTitle()%></a>
				</h3>
				<p>
					<%=recruitment.getUploadTime()%>
					&nbsp &nbsp &nbsp &nbsp &nbsp
					<%=recruitment.getCompany()%>
				</p>
				<p><%=recruitment.getDescription()%></p>
				
				<%
					} else {
							RecruitmentBBS recruitment = (RecruitmentBBS) abs;
				%>
				<h3>
					<a href="<%=recruitment.getUrl()%>" target="_blank"><%=recruitment.getTitle()%></a>
				</h3>
				<p>
					<%=recruitment.getTime()%>
					&nbsp &nbsp &nbsp &nbsp &nbsp
					<%=recruitment.getSource()%>
					&nbsp &nbsp &nbsp &nbsp &nbsp <a href="<%=recruitment.getSnapshotUrl()%>">快照</a>
				</p>
				<p><%=recruitment.getContent()%></p>

				<%
					}
				%>
				<br> 
				<%
					}
				%>

				<!-- 分页 -->
				<ul class="pagination">
					<c:if test="${searchResult.offset > 0 }">
						<li><a
							href="search/searchRecruitment?key=${key }&offset=${searchResult.offset-searchResult.size}">&laquo;</a></li>
					</c:if>
					<c:forEach var="id" begin="0" end="9">
						<c:if test="${id * searchResult.size < searchResult.total }">
							<c:if test="${id * searchResult.size != searchResult.offset }">
								<li><a
									href="search/searchRecruitment?key=${key }&offset=${id*searchResult.size}">${id+1}</a></li>
							</c:if>
							<c:if test="${id * searchResult.size == searchResult.offset }">
								<li class="active"><a href="javascript:void(0)">${id+1}<span
										class="sr-only">(current)</span></a></li>
							</c:if>
						</c:if>
					</c:forEach>
					<c:if
						test="${searchResult.offset+searchResult.size<searchResult.total }">
						<li><a
							href="search/searchRecruitment?key=${key}&offset=${searchResult.offset+searchResult.size}">&raquo;</a></li>
					</c:if>
				</ul>
				<!-- 分页 -->
			</div>
			<!-- /.container -->
		</div>
	</div>
	<!-- js在最后加载 -->
	<script src="bootstrap/js/jquery-1.11.3.min.js"></script>
	<script src="bootstrap/js/bootstrap.min.js"></script>
</body>
</html>
