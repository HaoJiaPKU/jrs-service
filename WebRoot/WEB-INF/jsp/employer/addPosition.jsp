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
    
    <title>发布招聘信息</title>
    
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
			<div class="col-md-8 col-md-offset-2 jumbotron">
				<div class="" style="font-size:18px;">
						<p><strong>填写职位信息</strong></p>
					</div>
					<hr style="height:4px; border-top:2px solid #e4dddd;"/>
				<form class="form-horizontal" action="position/addPosition" method="post" id="addBasic" enctype="multipart/form-data">
					<input type="hidden" name="employerId" value="${employer.id }">
					<input type="hidden" name="attachNum" id="attachNum" value="0">
					<div class="form-group">
						<label for="title" class="col-sm-2 control-label">标题</label>
						<div class="col-sm-4"><input name="title" type="text" id="title"
									class="form-control"></div>
						<label for="position" class="col-sm-2 control-label">职位名称</label>
						<div class="col-sm-4"><input name="position" type="text" id="position"
									class="form-control"></div>
					</div>
					<div class="form-group">
						<label for="degree" class="col-sm-2 control-label">学历要求</label>
						<div class="col-sm-4"><input name="degree" type="text" id="degree"
									class="form-control"></div>
						<label for="city" class="col-sm-2 control-label">工作地点</label>
						<div class="col-sm-4"><input name="city" type="text" id="city"
									class="form-control"></div>
					</div>
					<div class="form-group">
						<label for="company" class="col-sm-2 control-label">公司名称</label>
						<div class="col-sm-4"><input name="company" type="text" id="company"
									class="form-control"></div>
						<label for="business" class="col-sm-2 control-label">公司行业</label>
						<div class="col-sm-4"><input name="business" type="text" id="business"
									class="form-control"></div>
					</div>
					<div class="form-group">
						<label for="scale" class="col-sm-2 control-label">公司规模</label>
						<div class="col-sm-4"><input name="scale" type="text" id="scale"
									class="form-control"></div>
						<label for="type" class="col-sm-2 control-label">公司类型</label>
						<div class="col-sm-4"><input name="type" type="text" id="type"
									class="form-control"></div>
					</div>
					<div class="form-group">
						<label for="salary" class="col-sm-2 control-label">月薪</label>
						<div class="col-sm-4"><input name="salary" type="text" id="salary"
									class="form-control"></div>
						<label for="recruitNum" class="col-sm-2 control-label">招聘人数</label>
						<div class="col-sm-4"><input name="recruitNum" type="text" id="recruitNum"
									class="form-control"></div>
					</div>
					<div class="form-group">
						<label for="description" class="col-sm-2 control-label">职位描述</label>
						<div class="col-sm-10">
							<textarea name="description" id="description" class="form-control" rows="5"></textarea>
						</div>
					</div>
					<div class="form-group">
						<label for="attachment" class="col-sm-2 control-label">添加附件</label>
						<div class="col-sm-10" id="attachDiv">
							<input name="attachment0" type="file" id="attachment0" class="form-control">
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-3 col-md-offset-2">
							<a class="cursor:pointer;" style="color:#33aeb7;" href="javascript:addAttachment()">添加附件</a>
						</div>
					</div>
					<div class="form-group">
						<div class="col-md-3 col-md-offset-2"><button class="btn btn-primary btn-block" type="submit">保存</button></div>
					</div>
				</form>
			</div>
		</div>
	</div>
	
	<!-- js在最后加载 -->
	<script type="text/javascript" src="bootstrap/js/jquery-1.11.3.min.js" charset="UTF-8"></script>
	<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		function addAttachment() {
			var attach = document.getElementById("attachNum");
			attach.value = Number(attach.value)+1;
			var attachDiv = document.getElementById("attachDiv");
			var input = document.createElement('input');
			input.setAttribute("type", "file");
			input.setAttribute("name", "attachment"+attach.value);
			input.setAttribute("class", "form-control");
			input.setAttribute("id", "attachment"+attach.value);
			attachDiv.appendChild(input);
		}
	</script>
  </body>
</html>
