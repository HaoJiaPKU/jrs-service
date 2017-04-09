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
	<%
		int recFreqDay = (Integer)session.getAttribute("recFreqDay");
		int recFreqHour = (Integer)session.getAttribute("recFreqHour");
		int subscriptionNum = (Integer)session.getAttribute("subscriptionNum");
	%>
	<script type="text/javascript">
		console.log(<%=recFreqDay%>);
	</script>
	
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
						<p><strong>订阅推送</strong></p>
					</div>
					<hr style="height:4px; border-top:2px solid #e4dddd;"/>
					
					<div class="panel-body">
						<form class="form-horizontal" action="employee/update" method="post" id="subscriptionForm">
							<input type="hidden" name="employeeId" value="${employee.id }">
							
							<%	
								if (recFreqDay != -1) {
							%>
							
							<div class="form-group">
								<label for="subscription-num" class="col-sm-3 control-label">推送数量</label>
								<div class="col-sm-6"><input name="subscriptionNum" type="text" id="subscriptionNum" value="<%=subscriptionNum%>"
											class="form-control" required></div>
							</div>
							<div class="form-group">
								<label for="rec-freq" class="col-sm-3 control-label">推送频率</label>
								<div class="col-sm-3">
									<select name="recFreqDay" id="recFreqDay" class="form-control">
										<option value="<%=recFreqDay%>">
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
										recFreqHour = (Integer)session.getAttribute("recFreqHour");
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
									<button id="save" class="btn btn-primary btn-block" type="submit">更新订阅</button>
								</div>
								<div class="col-md-3">
									<button id="cancelSubscription" class="btn btn-block" type="button" onclick="cancel()">取消订阅</button>
								</div>
							</div>
							<div class="form-group">
								<label for="" class="col-sm-3 control-label">订阅状态</label>
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
								%><%=recFreqHour%>点推送<%=subscriptionNum%>个职位
								</label>
								</div>
							</div>
							
							<%
								} else {
							%>
							<div class="form-group">
								<label for="subscription-num" class="col-sm-3 control-label">推送数量</label>
								<div class="col-sm-6"><input name="subscriptionNum" type="text" id="subscriptionNum" value="0"
											class="form-control" required></div>
							</div>
							<div class="form-group">
								<label for="rec-freq" class="col-sm-3 control-label">推送频率</label>
								<div class="col-sm-3">
									<select name="recFreqDay" id="recFreqDay" class="form-control">
										<option value="<%=recFreqDay%>"></option>
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
										<option value="<%=recFreqDay%>"></option>
										<%
										recFreqHour = (Integer)session.getAttribute("recFreqHour");
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
									<button id="save" class="btn btn-primary btn-block" type="submit">更新订阅</button>
								</div>
								<div class="col-md-3">
									<button id="cancelSubscription" class="btn btn-block" type="button" onclick="cancel()">取消订阅</button>
								</div>
							</div>
							<div class="form-group">
								<label for="" class="col-sm-3 control-label">订阅状态</label>
								<div class="col-md-6">
								<label for="" class="control-label">已取消订阅</label>
								</div>
							</div>
							<%
								}
							%>
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
	console.log(<%=recFreqDay%>);
		function cancel() {
			<%
			Employee employee = (Employee)session.getAttribute("employee");
			%>
			var employeeId = <%=employee.getId()%>;
			$.ajax({
				url: "employee/update",
				data: {
					employeeId : employeeId,
 					recFreqHour : -1,
					recFreqDay : -1,
					subscriptionNum : 0
				},
				type : "POST",
				async : false,
				dataType : "json",
				contentType : "application/x-www-form-urlencoded; charset=utf-8",
				success : function(json){
				},
				error : function(xhr, status){
					return false;
				},
				complete : function(xhr, status){
					console.log("取消成功");
				}
			});
			<%recFreqDay = -1;%>
			$("#recFreqDay").attr("value", -1);
			var form = document.getElementById("subscriptionForm");
			form.action = "employee/check";
			form.submit();
		}
	</script>
  </body>
</html>
