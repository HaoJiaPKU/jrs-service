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
    
    <title>更新简历信息</title>
    
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
		<!-- 添加基本信息 -->
		<div class="row">
			<div class="col-md-7 col-md-offset-3">
				<div class="panel panel-primary">
					<div class="panel-heading">
						基本信息
					</div>
					
					<div class="panel-body">
						<form class="form-horizontal" action="resume/updateResume" method="post" id="basicForm">
							<input type="hidden" name="employeeId" value="${employee.id }">
							<div class="form-group">
								<label for="name" class="col-md-3 control-label">姓名</label>
								<div class="col-md-6"><input name="name" type="text" id="name" placeholder="姓名"
											value="${resume.name }" class="form-control"></div>
							</div>
							<div class="form-group">
								<label class="control-label col-md-3">性别</label>
								<div class="col-md-6">
									<c:if test="${resume.gender == '男' }">
										<label class="radio-inline">
										  <input type="radio" name="gender" value="男" checked="checked"> 男
										</label>
										<label class="radio-inline">
										  <input type="radio" name="gender" value="女"> 女
										</label>
									</c:if>
									<c:if test="${resume.gender == '女' }">
										<label class="radio-inline">
										  <input type="radio" name="gender" value="男"> 男
										</label>
										<label class="radio-inline">
										  <input type="radio" name="gender" value="女" checked="checked"> 女
										</label>
									</c:if>
								</div>
							</div>
							<div class="form-group">
								<label for="politics" class="col-md-3 control-label">政治面貌</label>
								<div class="col-md-6"><input name="politics" type="text" id="politics" placeholder="政治面貌"
											value="${resume.politics }" class="form-control"></div>
							</div>
							<div class="form-group">
				                <label for="birthday" class="col-md-3 control-label">生日</label>
				                <div class="col-md-6"><div class="input-group date form_date data-date="" data-date-format="yyyy-MM-dd" data-link-format="yyyy-MM-dd">
				                    <input class="form-control" name="birthday" id="birthday" type="text" value="${resume.birthday }" readonly>
				                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
									<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
				                </div></div>
							</div>
							<div class="form-group">
								<label for="age" class="col-md-3 control-label">年龄</label>
								<div class="col-md-6"><input name="age" type="text" id="age" placeholder="年龄"
											value="${resume.age }" class="form-control"></div>
							</div>
							<div class="form-group">
								<label for="email" class="col-md-3 control-label">邮箱</label>
								<div class="col-md-6"><input name="email" type="text" id="email" placeholder="邮箱"
											value="${resume.email }" class="form-control"></div>
							</div>
							<div class="form-group">
								<label for="phone" class="col-md-3 control-label">手机</label>
								<div class="col-md-6"><input name="phone" type="text" id="phone" placeholder="手机"
											value="${resume.phone }" class="form-control"></div>
							</div>
							<div class="form-group">
								<label for="educationBackground" class="col-md-3 control-label">学历</label>
								<div class="col-md-6"><input name="educationBackground" type="text" id="educationBackground" placeholder="学历"
											value="${resume.educationBackground }" class="form-control"></div>
							</div>
							<div class="form-group">
								<label for="salary" class="col-md-3 control-label">期望薪水</label>
								<div class="col-md-6"><input name="salary" type="text" id="salary" placeholder="期望薪水" value="${resume.salary }" class="form-control"></div>
							</div>
							<div class="form-group">
								<label for="workingPlace" class="col-md-3 control-label">期望工作地点</label>
								<div class="col-md-6"><input name="workingPlace" type="text" id="workingPlace" placeholder="期望工作地点"
											value="${resume.workingPlace }" class="form-control"></div>
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
		</div><!-- 添加基本信息 -->
	</div>
	
	<!-- js在最后加载 -->
	<script type="text/javascript" src="bootstrap/js/jquery-1.11.3.min.js" charset="UTF-8"></script>
	<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		function cancel() {
			var form = document.getElementById("basicForm");
			form.action = "resume/checkResume";
			form.submit();
		}
	</script>
  </body>
</html>
