<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>更新教育信息</title>
    
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
				<a class="navbar-brand" href="employee.jsp">找工作</a>
				<a class="navbar-brand" href="employer.jsp">找简历</a>
			</div>
			<div class="navbar-collapse collapse">
				<c:if test="${empty employee }">
					<form action="/employee/login" method="post" class="navbar-form navbar-right">
						<div class="form-group">
							<input name="username" type="text" placeholder="Email"
								class="form-control">
						</div>
						<div class="form-group">
							<input name="password" type="password" placeholder="Password"
								class="form-control">
						</div>
						<button type="submit" class="btn btn-primary">登录</button>
						<button type="button" class="btn btn-primary" onclick="javascript:window.location.href='/employee/regist'">求职者注册</button>
					</form>
				</c:if>
				<c:if test="${not empty employee }">
					<ul class="nav navbar-nav navbar-right">
						<li class="dropdown">
							 <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="true">
								${employee.email }
								<span class="caret"></span>
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
							</ul>
						</li>
					</ul>
				</c:if>
			</div>
			<!--/.navbar-collapse -->
		</div>
	</nav>
	
	<div class="container">
		<!-- 基本信息 -->
		<div class="row">
			<div class="col-md-7 col-md-offset-3">
				<div class="panel panel-primary">
					<div class="panel-heading">
						教育经历
					</div>
					
					<div class="panel-body">
						<form class="form-horizontal" action="resume/updateEducation" method="post" id="eduForm">
							<input type="hidden" name="employeeId" value="${education.employeeId }">
							<input type="hidden" name="id" value="${education.id }">
							<div class="form-group">
								<label for="school" class="col-sm-3 control-label">学校</label>
								<div class="col-sm-6"><input name="school" type="text" id="school" value="${education.school }"
											class="form-control" required></div>
							</div>
							<div class="form-group">
								<label for="academy" class="col-sm-3 control-label">学院</label>
								<div class="col-sm-6"><input name="academy" type="text" id="academy" value="${education.academy }"
											class="form-control" required></div>
							</div>
							<div class="form-group">
								<label for="major" class="col-sm-3 control-label">专业</label>
								<div class="col-sm-6"><input name="major" type="text" id="major" value="${education.major }"
											class="form-control" required></div>
							</div>
							<div class="form-group">
								<label for="degree" class="col-sm-3 control-label">学历</label>
								<div class="col-sm-6"><input name="degree" type="text" id="degree" value="${education.degree }"
											class="form-control" required></div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">就读时间</label>
								<div class="col-sm-2">
									<table>
										<tr>
											<td><input type="text" name="yearBegin" id="yearBegin"
												class="form-control"
												value="${fn:split(education.dateBegin,'-')[0] }" required></td>
											<td>年</td>
										</tr>
									</table>
								</div>
								<div class="col-sm-2">
									<table>
										<tr>
											<td><input type="text" name="monthBegin" id="monthBegin"
												class="form-control"
												value="${fn:split(education.dateBegin,'-')[1] }" required></td>
											<td>月</td>
										</tr>
									</table>
								</div>
								<label class="col-sm-1 control-label">至</label>
								<div class="col-sm-2">
									<table>
										<tr>
											<td><input type="text" name="yearEnd" id="yearEnd"
												class="form-control"
												value="${fn:split(education.dateEnd,'-')[0] }" required></td>
											<td>年</td>
										</tr>
									</table>
								</div>
								<div class="col-sm-2">
									<table>
										<tr>
											<td><input type="text" name="monthEnd" id="monthEnd"
												class="form-control"
												value="${fn:split(education.dateEnd,'-')[1] }" required></td>
											<td>月</td>
										</tr>
									</table>
								</div>
							</div>
							<div class="form-group">
								<label for="description" class="col-sm-3 control-label">经历描述</label>
								<div class="col-sm-9">
									<textarea name="description" id="description"
										class="form-control" rows="3">${education.description }</textarea>
								</div>
							</div>	
							<div class="form-group">
								<div class="col-md-2 col-md-offset-3">
									<button id="save" class="btn btn-primary btn-block" type="submit">保存</button>
								</div>
								<div class="col-md-2">
									<button id="eduBtn" class="btn btn-block" type="button" onclick="cancel()">取消</button>
								</div>
							</div>
						</form>
					</div>
					
				</div>
			</div>
		</div><!-- 教育信息 -->
	</div>
	
	<!-- js在最后加载 -->
	<script type="text/javascript" src="bootstrap/js/jquery-1.11.3.min.js" charset="UTF-8"></script>
	<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		function cancel() {
			var form = document.getElementById("eduForm");
			form.action = "resume/checkResume";
			form.submit();
		}
	</script>
  </body>
</html>
