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
    
    <title>添加工作经历</title>
    
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
		<!-- 工作信息 -->
		<div class="row">
			<div class="col-md-7 col-md-offset-3">
				<div class="panel panel-primary">
					<div class="panel-heading">
						工作经历
					</div>
					
					<div class="panel-body">
						<form class="form-horizontal" action="resume/addWorkExperience" method="post" id="workForm">
							<input type="hidden" name="employeeId" value="${employee.id }">
							<div class="form-group">
								<label for="company" class="col-sm-3 control-label">公司</label>
								<div class="col-sm-6"><input name="company" type="text" id="company" placeholder="公司"
											class="form-control"></div>
							</div>
							<div class="form-group">
								<label for="jobTitle" class="col-sm-3 control-label">职位</label>
								<div class="col-sm-6"><input name="jobTitle" type="text" id="jobTitle" placeholder="职位"
											class="form-control"></div>
							</div>
							<div class="form-group">
								<label for="city" class="col-sm-3 control-label">所在城市</label>
								<div class="col-sm-6"><input name="city" type="text" id="city" placeholder="所在城市"
											class="form-control"></div>
							</div>
							<div class="form-group">
								<label for="salary" class="col-sm-3 control-label">薪水</label>
								<div class="col-sm-6"><input name="salary" type="text" id="salary" placeholder="薪水"
											class="form-control"></div>
							</div>
							<div class="form-group">
								<label class="col-sm-3 control-label">工作时间</label>
								<div class="col-sm-2"><table><tr><td><input type="text" name="yearBegin" id="yearBegin" class="form-control"
									required></td><td>年</td></tr></table></div>
								<div class="col-sm-2"><table><tr><td><input type="text" name="monthBegin" id="monthBegin" class="form-control"
									required></td><td>月</td></tr></table></div>
								<label class="col-sm-1 control-label">至</label>
								<div class="col-sm-2"><table><tr><td><input type="text" name="yearEnd" id="yearEnd" class="form-control"
									required></td><td>年</td></tr></table></div>
								<div class="col-sm-2"><table><tr><td><input type="text" name="monthEnd" id="monthEnd" class="form-control"
									required></td><td>月</td></tr></table></div>
							</div>
							<div class="form-group">
								<label for="description" class="col-sm-3 control-label">经历描述</label>
								<div class="col-sm-9">
									<textarea name="description" id="description" class="form-control" rows="3"></textarea>
								</div>
							</div>
							<c:if test="${employee.hasResume == 1}">	
								<div class="form-group">
									<div class="col-md-2 col-md-offset-3">
										<button id="save" class="btn btn-primary btn-block" type="submit">保存</button>
									</div>
									<div class="col-md-2">
										<button id="eduBtn" class="btn btn-block" type="button" onclick="cancel()">取消</button>
									</div>
								</div>
							</c:if>
							<c:if test="${employee.hasResume == 0}">
								<div class="form-group">
									<div class="col-md-3 col-md-offset-3">
										<button id="save" class="btn btn-primary btn-block" type="submit">保存并继续</button>
									</div>
									<div class="col-md-3">
										<button id="eduBtn" class="btn btn-block" type="button" onclick="window.location.href='resume/publishResume'">跳过</button>
									</div>
								</div>
							</c:if>
						</form>
					</div>
					
				</div>
			</div>
		</div><!-- 工作信息 -->
	</div>
	
	<!-- js在最后加载 -->
	<script type="text/javascript" src="bootstrap/js/jquery-1.11.3.min.js" charset="UTF-8"></script>
	<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript">
	function cancel() {
		var form = document.getElementById("workForm");
		form.action = "resume/checkResume";
		form.submit();
	}
	</script>
  </body>
</html>
