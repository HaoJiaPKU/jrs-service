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
	<!-- 导航条 -->
    <jsp:include page="navigation.jsp"/>

	<div class="container">
		<div class="row">
			<div class="col-md-7">
				<!-- 搜索栏 -->
				<form action="search/searchPosition" method="post"
					class="form-inline">
					<input style="width:80%" type="text" name="key" value="${key }"
						class="form-control input-lg" placeholder="职位关键词"> 
					<input type="hidden" name="offset" value="0">
					<button type="submit" class="btn btn-lg btn-primary">找工作</button>
				</form>
				<br>

				<%
					Pager<AbstractPosition> pager = (Pager<AbstractPosition>) request
							.getAttribute("searchResult");
					for (AbstractPosition abs : pager.getDatas()) {
						if (abs instanceof PositionJobpopo) {
							PositionJobpopo position = (PositionJobpopo) abs;
				%>
				<h3>
					<a href="resume/checkPosition?recruitId=<%=position.getId()%>" target="_blank"><%=position.getTitle()%></a>
				</h3>
				<p>
					<%=position.getUploadTime().toString().substring(0, 10)%>
					&nbsp &nbsp &nbsp &nbsp &nbsp
					<%=position.getCompany()%>
				</p>
				<p><%=position.getDescription()%></p>
				
				<%
					} else {
							Position position = (Position) abs;
				%>
				<h3>
					<a href="<%=position.getPosUrl()%>" target="_blank"><%=position.getPosTitle()%></a>
				</h3>
				<p>
					<%=position.getPosPublishDate()%>
					&nbsp &nbsp &nbsp &nbsp &nbsp
					<% if (position.getComName() != null
						&& position.getComName().length() > 0) {
					%>
					<%= position.getComName()%>
					<%
						} else {
					%>
					<%= position.getSource()%>
					<%
						}
					%>
					&nbsp &nbsp &nbsp &nbsp &nbsp
				</p>
				<p><%=position.getDisplayContent()%></p>

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
							href="search/searchPosition?key=${key }&offset=${searchResult.offset-searchResult.size}">&laquo;</a></li>
					</c:if>
					<c:forEach var="id" begin="0" end="9">
						<c:if test="${id * searchResult.size < searchResult.total }">
							<c:if test="${id * searchResult.size != searchResult.offset }">
								<li><a
									href="search/searchPosition?key=${key }&offset=${id*searchResult.size}">${id+1}</a></li>
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
							href="search/searchPosition?key=${key}&offset=${searchResult.offset+searchResult.size}">&raquo;</a></li>
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
