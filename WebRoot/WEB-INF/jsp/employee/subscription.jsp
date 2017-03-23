<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="cn.edu.pku.user.domain.*"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>更新订阅信息</title>
    
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
						订阅推送
					</div>
					
					<div class="panel-body">
						<form class="form-horizontal" action="employee/update" method="post" id="subscriptionForm">
							<input type="hidden" name="employeeId" value="${employee.id }">
							<div class="form-group">
								<label for="subscription-num" class="col-sm-3 control-label">推送数量</label>
								<div class="col-sm-6"><input name="subscriptionNum" type="text" id="subscriptionNum" value="${subscriptionNum }"
											class="form-control" required></div>
							</div>
							<div class="form-group">
								<label for="rec-freq" class="col-sm-3 control-label">推送频率</label>
								<div class="col-sm-3">
									<select name="recFreqDay" id="recFreqDay" class="form-control">
										<option value="${recFreqDay }">
										<%
											int recFreqDay = (Integer)session.getAttribute("recFreqDay");
											if (recFreqDay == 0) {
										%>每天
										<%	
											} else if (recFreqDay == 1) {
										%>每周一
										<%		
											} else if (recFreqDay == 2) {
										%>每周二
										<%		
											} else if (recFreqDay == 3) {
										%>每周三
										<%
											} else if (recFreqDay == 4) {
										%>每周四
										<%
											} else if (recFreqDay == 5) {
										%>每周五
										<%
											} else if (recFreqDay == 6) {
										%>每周六
										<%
											} else if (recFreqDay == 7) {
										%>每周日
										<%
											}
										%>
										</option>
										<option value="0">每天</option>
										<option value="1">每周一</option>
										<option value="2">每周二</option>
										<option value="3">每周三</option>
										<option value="4">每周四</option>
										<option value="5">每周五</option>
										<option value="6">每周六</option>
										<option value="7">每周日</option>
									</select>
								</div>
								<div class="col-sm-3">
									<select name="recFreqHour" id="recFreqHour" class="form-control">
										<%
										int recFreqHour = (Integer)session.getAttribute("recFreqHour");
										for (int i = 0; i < 24; i ++) {
											if (i == recFreqHour) {
												%>
													<option value="<%=i%>" selected="selected"><%=i%>点</option>
												<%
											} else {
												%>
													<option value="<%=i%>"><%=i%>点</option>
												<%
											}
										}
										%>
									</select>
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-3 col-md-offset-3">
									<button id="save" class="btn btn-primary btn-block" type="submit">保存</button>
								</div>
								<div class="col-md-3">
									<button id="cancelSubscription" class="btn btn-block" type="button" onclick="cancel()">重置</button>
								</div>
							</div>
							<div class="form-group">
								<label for="" class="col-sm-3 control-label">已订阅</label>
								<div class="col-md-6">
								<label for="" class="control-label">
								<%
									if (recFreqDay == 0) {
								%>每天
								<%	
									} else if (recFreqDay == 1) {
								%>每周一
								<%		
									} else if (recFreqDay == 2) {
								%>每周二
								<%		
									} else if (recFreqDay == 3) {
								%>每周三
								<%
									} else if (recFreqDay == 4) {
								%>每周四
								<%
									} else if (recFreqDay == 5) {
								%>每周五
								<%
									} else if (recFreqDay == 6) {
								%>每周六
								<%
									} else if (recFreqDay == 7) {
								%>每周日
								<%
									}
								%>${recFreqHour}点推送${subscriptionNum}个职位
								</label>
								</div>
							</div>
						</form>
					</div>
					
				</div>
			</div>
		</div>
	</div>
	
	<!-- js在最后加载 -->
	<script type="text/javascript" src="bootstrap/js/jquery-1.11.3.min.js" charset="UTF-8"></script>
	<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		function cancel() {
			var form = document.getElementById("subscriptionForm");
			form.action = "employee/check";
			form.submit();
		}
	</script>
  </body>
</html>
