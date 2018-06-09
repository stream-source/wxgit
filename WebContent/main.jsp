<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>学生信息管理系统主页面</title>
<%
	//权限验证
		//获取session的值。判断是否为空，如果为空，返回登录页面
		if(session.getAttribute("login") == null){
			//客户端响应重定向到登录页面
			response.sendRedirect("index.jsp");
			return;
		}
%>
<!-- easyui使用，先将库引入到项目中 -->
<link rel="stylesheet" type="text/css"
	href="jquery-easyui-1.5.5.2/themes/default/easyui.css">
<link rel="stylesheet" type="text/css"
	href="jquery-easyui-1.5.5.2/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="jquery-easyui-1.5.5.2/demo.css">
<script type="text/javascript" src="jquery-easyui-1.5.5.2/jquery.min.js"></script>
<script type="text/javascript"
	src="jquery-easyui-1.5.5.2/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="jquery-easyui-1.5.5.2/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	$(function() {
		//数据treedate=[{text:"",children:[{},{}];}];
		var treeData = [ {
			text : "信息管理",//根
			//子菜单
			children : [ {
				text : "班级信息管理",
				attribute : {
					url : "gradeInfoManager.jsp"
				}
			}, {
				text : "学生信息管理",
				attribute : {
					url : "studentInfoManager.jsp"
				}
			} ]

		} ];
		//实例化
		$("#tree").tree({
			data : treeData,
			lines : true,
			onClick : function(node) {//点击子节点，新增tab
				if (node.attribute) {//存在属性
					//调用openTab方法
					openTab(node.text, node.attribute.url);
				}
			}
		});

		//新增tabs方法,Id:tabs,方法:add，select,exists都是内置的，不需要自己定义
		function openTab(text, url) {
			//判断是否打开选项卡，打开后不再重复打开
			var e = $("#tabs").tabs('exists', text);
			if (e) {//存在，则直接选中
				$("#tabs").tabs('select', text);
			} else {//否则不存在直接打开
				//显示该选项卡的内容，连接到ztree中的url
				var content="<iframe frameborder='0' scrolling='auto' style='width:100%;height:100%' src="+url+"></iframe>";
				$("#tabs").tabs('add', {
					title : text,
					closable : true,//关闭tab属性
					content : content
				});
			}
		}
	});
</script>
</head>
<body class="easyui-layout">
	<!-- 北部 -->
	<div region="north" style="height: 100px; background-color: #E0EDFF"">
		<!-- <h1 align="center" color=""> 欢迎使用学生信息管理系统</h1> -->
		<div align="left" style="width:85%;float:left"><img src="image/main.jpg"></div>
		<div style="padding-top: 70px;padding-right: 15px">
		<!-- 可利用jsp的内置对象session，获取当前用户 -->
			欢迎您：&nbsp;<font color="red" size="3">${login.userName }</font>
		</div>
		
	</div>
	<!-- 中部 -->
	<div region="center">
		<div class="easyui-tabs" id="tabs" border="false" fit="true">
			<div title="首页">
				<!-- padding-top：填充效果 -->
				<div align="center" style="padding-top: 100px">
					<font size="20">欢迎使用学生信息管理系统</font>
				</div>
			</div>
		</div>
	</div>
	<!-- 西部 -->
	<!-- split使区域能够移动大小 -->
	<div region="west" style="width: 150px" title="菜单导航" split="true">
		<ul id="tree" class="ztree"></ul>
	</div>
	<!-- 南部 -->
	<div region="south" style="height: 50px" align="center">
		<a href="http://www.nyist.edu.cn/">版权所有@南阳理工学院</a>
	</div>
</body>
</html>