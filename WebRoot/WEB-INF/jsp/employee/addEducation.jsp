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
    
    <title>添加教育经历</title>
    
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
						<p><strong>教育经历</strong></p>
					</div>
					<hr style="height:4px; border-top:2px solid #e4dddd;"/>
					
					<div class="panel-body">
						<form class="form-horizontal" action="resume/addEducation" method="post" id="eduForm">
							<input type="hidden" name="employeeId" value="${employee.id }">
							<div class="form-group">
								<label for="school" class="col-sm-2 control-label">学校</label>
								<div class="col-sm-9"><input name="school" type="text" id="school" placeholder="学校"
											class="form-control" required></div>
							</div>
							<div class="form-group">
								<label for="academy" class="col-sm-2 control-label">学院</label>
								<div class="col-sm-9"><input name="academy" type="text" id="academy" placeholder="学院"
											class="form-control" required></div>
							</div>
							<div class="form-group">
								<label for="major" class="col-sm-2 control-label">专业</label>
								<div class="col-sm-9"><input name="major" type="text" id="major" placeholder="专业"
											class="form-control" required></div>
							</div>
							<div class="form-group">
								<label for="degree" class="col-sm-2 control-label">学历</label>
								<div class="col-sm-9"><input name="degree" type="text" id="degree" placeholder="学历"
											class="form-control" required></div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">就读时间</label>
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
								<label for="description" class="col-sm-2 control-label">经历描述</label>
								<div class="col-sm-9">
									<textarea name="description" id="description" class="form-control" rows="3"></textarea>
								</div>
							</div>
							<c:if test="${employee.hasResume == 1}">	
								<div class="form-group">
									<div class="col-md-4 col-md-offset-2">
										<button id="save" class="btn btn-primary btn-block" type="submit">保存</button>
									</div>
									<div class="col-md-4">
										<button id="eduBtn" class="btn btn-block" type="button" onclick="cancel()">取消</button>
									</div>
								</div>
							</c:if>
							<c:if test="${employee.hasResume == 0}">	
								<div class="form-group">
									<div class="col-md-4 col-md-offset-2">
										<button id="save" class="btn btn-primary btn-block" type="submit">保存并继续</button>
									</div>
									<div class="col-md-4">
										<button id="eduBtn" class="btn btn-block" type="button" onclick="window.location.href='resume/addWorkExperience'">跳过</button>
									</div>
								</div>
							</c:if>
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
