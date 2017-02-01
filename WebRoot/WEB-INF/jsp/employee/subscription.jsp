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
										<option value="${recFreqHour }">${recFreqHour }点</option>
										<option value="0">0点</option>
										<option value="1">1点</option>
										<option value="2">2点</option>
										<option value="3">3点</option>
										<option value="4">4点</option>
										<option value="5">5点</option>
										<option value="6">6点</option>
										<option value="7">7点</option>
										<option value="8">8点</option>
										<option value="9">9点</option>
										<option value="10">10点</option>
										<option value="11">11点</option>
										<option value="12">12点</option>
										<option value="13">13点</option>
										<option value="14">14点</option>
										<option value="15">15点</option>
										<option value="16">16点</option>
										<option value="17">17点</option>
										<option value="18">18点</option>
										<option value="19">19点</option>
										<option value="20">20点</option>
										<option value="21">21点</option>
										<option value="22">22点</option>
										<option value="23">23点</option>
									</select>
								</div>
							</div>
							<div class="form-group">
								<div class="col-md-2 col-md-offset-3">
									<button id="save" class="btn btn-primary btn-block" type="submit">保存</button>
								</div>
								<div class="col-md-2">
									<button id="cancelSubscription" class="btn btn-block" type="button" onclick="cancel()">取消</button>
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
