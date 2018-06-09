<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>班级信息管理页面</title>
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
	var url;
	//查询操作
	function gradeSearch() {
		$('#datagrid').datagrid('load', {
			gradeName : $('#s_gradeName').val()
		//与servlet中获取请求参数值的值对应 request.getParameter("gradeName");
		});
	}

	//删除操作
	function gradeDelete() {
		//得到一个集合，获取所选中的行
		var selectedRows = $('#datagrid').datagrid('getSelections');
		if (selectedRows.length == 0) {
			$.messager.alert("系统提示", "请选择要删除的数据！");
			return;
		}
		var strIds = [];
		for (var i = 0; i < selectedRows.length; i++) {
			strIds.push(selectedRows[i].id);
		}
		var ids = strIds.join(",");
		//alert(ids);
		$.messager.confirm("系统提示", "您是否确定要删除<font color='red'>"
				+ selectedRows.length + "</font>条数据信息", function(r) {
			if (r) {
				//ajax代码进行局部刷新
				$.post("gradeDelete", {
					delIds : ids
				}, function(result) {
					if (result.success) {
						//result与servlet中定义的JSONObject对象一致
						$.messager.alert("系统提示", "您已经删除<font color='red'>"
								+ result.delNums + "</font>条数据");
						$('#datagrid').datagrid("reload");
					} else {
						$.messager.alert("系统提示", '<font color=red>'
								+ selectedRows[result.errorIndex].gradeName
								+ '</font>' + result.errorMsg);
					}
				}, "json");
			}
		});
	}

	//实现弹出的对话框关闭(取消)操作
	function closeGradeDialog() {
		$('#dialog').dialog("close");
		resetValue();
	}
	//消除值
	function resetValue() {
		$(gradeName).val("");
		$(gradeDesc).val("");
	}

	//添加操作
	function openGradeAddDialog() {
		$('#dialog').dialog("open").dialog("setTitle", "添加班级信息");
		url = "gradeSave";//与在web.xml中定义的url相同
	}
	//实现添加对话框中的保存按钮
	function saveGradeDialog() {
		$("#fm").form("submit", {
			url : url,
			onSubmit : function() {
				return $(this).form("validate");
			},
			success : function(result) {
				if (result.errorMsg) {
					$.messager.alert("系统提示", result.errorMsg);
					return;
				} else {
					$.messager.alert("系统提示", "保存成功");
					resetValue();
					$('#dialog').dialog("close");
					$('#datagrid').datagrid("reload");
				}
			}
		});
	}

	//修改操作
	function openGradeModifyDialog() {
		var selectedRows = $('#datagrid').datagrid('getSelections');
		if (selectedRows.length != 1) {
			$.messager.alert("系统提示", "请选择一条数据进行修改");
			return;
		}
		var row = selectedRows[0];//获取第一条数据，相当于对象
		$('#dialog').dialog("open").dialog("setTitle", "修改班级信息");
		$('#fm').form("load", row);//直接将对象的数据信息加载到表单中
		url = "gradeSave?id=" + row.id;
	}
</script>
</head>
<body style="margin: 3px">
	<!-- fitColumns属性：自动填充 -->
	<table id="datagrid" title="班级信息" class="easyui-datagrid"
		fitColumns="true" pagination="true" rownumber="true" fit="true"
		url="gradeList" toolbar="#tb">
		<thead>
			<tr>
				<th field="cb" checkbox="true"></th>
				<th field="id" width="50" align="center">编号</th>
				<th field="gradeName" width="100" align="center">班级名称</th>
				<th field="gradeDesc" width="150" align="center">班级描述</th>
			</tr>
		</thead>
	</table>
	<!-- 设置工具栏，添加，删除，修改 -->
	<div id="tb">
		<a href="javascript:openGradeAddDialog()" class="easyui-linkbutton"
			iconCls="icon-add" plain="true">添加</a> <a
			href="javascript:openGradeModifyDialog()" class="easyui-linkbutton"
			iconCls="icon-edit" plain="true">修改</a> <a
			href="javascript:gradeDelete()" class="easyui-linkbutton"
			iconCls="icon-remove" plain="true">删除</a>
		<!-- 查询条件 -->
		<div>
			&nbsp;班级名称：&nbsp;<input type="text" name="s_gradeName"
				id="s_gradeName" /> <a href="javascript:gradeSearch()"
				class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
		</div>
	</div>
	<!-- 弹出对话框操作 -->
	<div id="dialog" class="easyui-dialog"
		style="width: 450px; height: 280px; padding: 10px 20px" closed="true"
		buttons="#dlg-buttons">
		<!-- 单独定义一个按钮div引用他 -->
		<form id="fm" method="post">
			<table>
				<tr>
					<td>班级名称：</td>
					<!-- 实现必填选项 class="easyui-validatabox" required="true"-->
					<td><input type="text" name="gradeName" id="gradeName"
						class="easyui-validatebox" required="true" /></td>
				</tr>
				<tr>
					<td valign="top">班级描述：</td>
					<td><textarea rows="7" cols="30" name="gradeDesc"
							id="gradeDesc"></textarea></td>
				</tr>
			</table>
		</form>
	</div>

	<div id="dlg-buttons">
		<a href="javascript:saveGradeDialog()" class="easyui-linkbutton"
			iconCls="icon-ok">保存</a> <a href="javascript:closeGradeDialog()"
			class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
	</div>
</body>
</html>