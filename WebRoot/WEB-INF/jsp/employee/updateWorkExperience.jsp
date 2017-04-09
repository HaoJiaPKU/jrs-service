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
    
    <title>更新工作经历</title>
    
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
		<!-- 工作信息 -->
		<div class="row">
			<div class="col-md-8 col-md-offset-2 jumbotron">
				<div class="">
					<div class="" style="font-size:18px;">
						<p><strong>工作经历</strong></p>
					</div>
					<hr style="height:4px; border-top:2px solid #e4dddd;"/>
					
					<div class="panel-body">
						<form class="form-horizontal" action="resume/updateWorkExperience" method="post" id="workForm">
							<input type="hidden" name="employeeId" value="${workExperience.employeeId }">
							<input type="hidden" name="id" value="${workExperience.id }">
							<div class="form-group">
								<label for="company" class="col-sm-2 control-label">公司</label>
								<div class="col-sm-9"><input name="company" type="text" id="company" value="${workExperience.company }"
											class="form-control"></div>
							</div>
							<div class="form-group">
								<label for="jobTitle" class="col-sm-2 control-label">职位</label>
								<div class="col-sm-9"><input name="jobTitle" type="text" id="jobTitle" value="${workExperience.jobTitle }"
											class="form-control"></div>
							</div>
							<div class="form-group">
								<label for="city" class="col-sm-2 control-label">所在城市</label>
								<div class="col-sm-9"><input name="city" type="text" id="city" value="${workExperience.city }"
											class="form-control"></div>
							</div>
							<div class="form-group">
								<label for="salary" class="col-sm-2 control-label">薪资</label>
								<div class="col-sm-9"><input name="salary" type="text" id="salary" value="${workExperience.salary }"
											class="form-control"></div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">工作时间</label>
								<div class="col-sm-2">
									<table>
										<tr>
											<td><input type="text" name="yearBegin" id="yearBegin"
												class="form-control"
												value="${fn:split(workExperience.dateBegin,'-')[0] }"
												required></td>
											<td>年</td>
										</tr>
									</table>
								</div>
								<div class="col-sm-2">
									<table>
										<tr>
											<td><input type="text" name="monthBegin" id="monthBegin"
												class="form-control"
												value="${fn:split(workExperience.dateBegin,'-')[1] }"
												required></td>
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
												value="${fn:split(workExperience.dateEnd,'-')[0] }" required></td>
											<td>年</td>
										</tr>
									</table>
								</div>
								<div class="col-sm-2">
									<table>
										<tr>
											<td><input type="text" name="monthEnd" id="monthEnd"
												class="form-control"
												value="${fn:split(workExperience.dateEnd,'-')[1] }" required></td>
											<td>月</td>
										</tr>
									</table>
								</div>
							</div>
							<div class="form-group">
								<label for="description" class="col-sm-2 control-label">经历描述</label>
								<div class="col-sm-9">
									<textarea name="description" id="description"
										class="form-control" rows="3">${workExperience.description }</textarea>
								</div>
							</div>	
							<div class="form-group">
								<div class="col-md-4 col-md-offset-2">
									<button id="save" class="btn btn-primary btn-block" type="submit">保存</button>
								</div>
								<div class="col-md-4">
									<button id="eduBtn" class="btn btn-block" type="button" onclick="cancel()">取消</button>
								</div>
							</div>
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
