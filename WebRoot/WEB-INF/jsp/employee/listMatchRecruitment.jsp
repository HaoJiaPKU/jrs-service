<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="cn.edu.pku.search.domain.*"%>
<%@ page import="cn.edu.pku.user.domain.*"%>
<%@ page import="java.lang.Math"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>查看匹配职位</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">
	
	<link href="bootstrap/rewritecss/radar.css" rel="stylesheet">
	
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
			<div class="col-md-8">
				<%
				Pager<MatchRecruitment> pager = (Pager<MatchRecruitment>) session
						.getAttribute("relevancePager");
				for (MatchRecruitment match : pager.getDatas()) {
					if (match.getRecruitment() instanceof Recruitment) {
						Recruitment recruitment = (Recruitment) match.getRecruitment();
				%>
				<div>
					<h3>
						<a href="resume/checkRecruitment?recruitId=<%=recruitment.getId()%>" target="_blank"><%=recruitment.getTitle()%></a>
					</h3>
				</div>
				<div>
					<p>
						<%=recruitment.getUploadTime()%>
						&nbsp &nbsp &nbsp &nbsp &nbsp
						<%=recruitment.getCompany()%>
						&nbsp &nbsp &nbsp &nbsp &nbsp
						<font color="#11cccc">相关度:<%=(int)(match.getRelevance()*100) %>%</font>
					</p>
				</div>
				<div>
					<p><%=recruitment.getDescription()%></p>
				</div>
				<%
					} else {
						RecruitmentBBS recruitment = (RecruitmentBBS) match.getRecruitment();
				%>
				<div class="col-md-12">
					<h3>
						<a href="<%=recruitment.getUrl()%>" target="_blank"><%=recruitment.getTitle()%></a>
					</h3>
				</div>
				<div class="col-md-12">
					<div class="col-md-8">
							<%=recruitment.getTime()%>
							&nbsp &nbsp &nbsp &nbsp &nbsp
							<%=recruitment.getSource()%>
							&nbsp &nbsp &nbsp &nbsp &nbsp 
							<a href="<%=recruitment.getSnapshotUrl()%>" target="_blank">快照</a>
							&nbsp &nbsp &nbsp &nbsp &nbsp
							<font color="#11cccc">相关度:<%=(int)(match.getRelevance()*100) %>%</font>	
					</div>
					<div class="col-md-4 radar-div">
						<div class="radar-img"><img src="bootstrap/img/radar-thumb.jpg" /></div>
						<div id="recruitment-tag-chart-<%=recruitment.getId()%>" class="radar"></div>
					</div>
				</div>
				<div class="col-md-12 description">
					<div class="col-md-12"><%=recruitment.getContent()%></div>
				</div>
				<%
					}
				}
				%>
					
			</div>
			
			<div class="col-md-4 blog-sidebar">
				<div class="row sidebar-module">
					<ul class="col-md-offset-4 col-md-8 list-group">
						<li class="list-group-item list-group-item-danger">
							<strong>
								<span class="glyphicon glyphicon-heart"></span>
								<span>&nbsp;玩转Jobpopo</span>
							</strong>
						</li>
						<li class="list-group-item">
							<a style="white-space: normal; width:100%;" class="btn btn-lg btn-primary" href="resume/addResume">创建我的简历</a>
						</li>
						<li class="list-group-item">
							<a style="white-space: normal; width:100%;" class="btn btn-lg btn-primary" href="resume/checkResume?employeeId=${employee.id }">查看我的简历</a>
						</li>
						<li class="list-group-item">
							<a style="white-space: normal; width:100%;" class="btn btn-lg btn-primary" href="search/updateRelevanceForEmployee">更新适合我的职位</a>
						</li>
						<li class="list-group-item">
							<a style="white-space: normal; width:100%;" class="btn btn-lg btn-primary" href="employee/check?employeeId=${employee.id }">订阅职位推送</a>
						</li>
						<li class="list-group-item radar-li">
							<a style="white-space: normal; width:100%;" class="btn btn-lg btn-primary radar-a">我的职位雷达图</a>
							<div id="employee-tag-chart" class="radar"></div>
						</li>
					</ul>
				</div>
			</div>
		</div>
	
		<!-- 分页 -->
		<div class="row">
			<div class="col-md-8">
				<ul class="pagination">
					<c:if test="${relevancePager.offset > 0 }">
						<li><a
							href="search/listMatchRecruitment?offset=${relevancePager.offset-relevancePager.size}">&laquo;</a></li>
					</c:if>
					<c:forEach var="id" begin="0" end="9">
						<c:if test="${id * relevancePager.size < relevancePager.total }">
							<c:if test="${id * relevancePager.size != relevancePager.offset }">
								<li><a
									href="search/listMatchRecruitment?offset=${id*relevancePager.size}">${id+1}</a></li>
							</c:if>
							<c:if test="${id * relevancePager.size == relevancePager.offset }">
								<li class="active"><a href="javascript:void(0)">${id+1}<span
										class="sr-only">(current)</span></a></li>
							</c:if>
						</c:if>
					</c:forEach>
					<c:if
						test="${relevancePager.offset+relevancePager.size<relevancePager.total }">
						<li><a
							href="search/listMatchRecruitment?offset=${relevancePager.offset+relevancePager.size}">&raquo;</a></li>
					</c:if>
				</ul>
			</div>
		</div>

	</div>
	<!-- js在最后加载 -->
	<script type="text/javascript" src="bootstrap/js/jquery-1.11.3.min.js" charset="UTF-8"></script>
	<script type="text/javascript" src="bootstrap/js/bootstrap.min.js"></script>
	
  </body>

<script src="bootstrap/js/dist/echarts-all.js"></script>
<script type="text/javascript">
var myChart = echarts.init(document.getElementById('employee-tag-chart'));
var option = {
	    title : {
	        text: '职位雷达图'
	    },
	    calculable : true,
	    polar : [
	        {
	            indicator : [
					<%
						List<EmployeeTag> employeeTagList = (List<EmployeeTag>)
						session.getAttribute("employeeTagList");
						for (int i = 0; i < employeeTagList.size(); i ++) {
					%>
					{text : '<%= employeeTagList.get(i).getTagName() %>', max : 32},
					<%
						}
					%>
	            ],
	            radius : 100
	        }
	    ],
	    series : [
	        {
	            name: '职位雷达图',
	            type: 'radar',
	            itemStyle: {
	                normal: {
	                    areaStyle: {
	                        type: 'default'
	                    }
	                }
	            },
	            data : [
	                {
	                	value : [
	                	<%
							for (int i = 0; i < employeeTagList.size(); i ++) {
						%>
						<%= Math.sqrt(Math.sqrt(employeeTagList.get(i).getTagValue()) * 10) * 10 %>
						,
						<%
							}
	                	%>
	                    ],
	                }
	            ]
	        }
	    ]
	};
myChart.setOption(option);

<%
for (MatchRecruitment match : pager.getDatas()) {
	if (match.getRecruitment() instanceof Recruitment) {
		Recruitment recruitment = (Recruitment) match.getRecruitment();
	} else {
		RecruitmentBBS recruitment = (RecruitmentBBS) match.getRecruitment();
%>
myChart = echarts.init(document.getElementById('recruitment-tag-chart-<%=recruitment.getId()%>'));
option = {
	    title : {
	        text: '职位雷达图'
	    },
	    calculable : true,
	    polar : [
	        {
	            indicator : [
					<%
						List<RecruitmentTag> recruitmentTagList = match.getRecruitmentTagList();
						for (int i = 0; i < recruitmentTagList.size(); i ++) {
					%>
					{text : '<%= recruitmentTagList.get(i).getTagName() %>', max : 32},
					<%
						}
					%>
	            ],
	            radius : 100
	        }
	    ],
	    series : [
	        {
	            name: '职位雷达图',
	            type: 'radar',
	            itemStyle: {
	                normal: {
	                    areaStyle: {
	                        type: 'default'
	                    }
	                }
	            },
	            data : [
	                {
	                	value : [
	                	<%
							for (int i = 0; i < recruitmentTagList.size(); i ++) {
						%>
						<%= Math.sqrt(Math.sqrt(recruitmentTagList.get(i).getTagValue()) * 10) * 10 %>
						,
						<%
							}
	                	%>
	                    ],
	                }
	            ]
	        }
	    ]
	};
myChart.setOption(option);
<%
	}
}
%>
</script>
</html>
