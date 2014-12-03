 <%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css"
	href="resources/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="resources/easyui/themes/icon.css">
<script type="text/javascript" src="resources/easyui/js/jquery.min.js"></script>
<script type="text/javascript" src="resources/easyui/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="resources/easyui/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	$(function(){
		$('#login_dialog').dialog({
			title : '登录界面',
			modal : true
		});
		
		$('#username').validatebox({
			required:true,
			missingMessage:'用户名必填'
		});
		
		$('#password').validatebox({
			required:true,
			missingMessage:'密码必填'
		});
		
		$('#login_form').form({
			url:'login.action',
			onSubmit:function(){
				if(!$('#login_form').form('validate')){
					$('#validCodeError').html('<span>输入有误,不能提交表单!</span>');
// 					$.messager.show({
// 						title:'提示信息' , 
// 						msg:验证没有通过,不能提交表单!
// 					});
					return false;
				}
			},
			success:function(result){
				//var result = $.parseJSON(result);
// 				$.messager.show({
// 					title : result.status,
// 					msg:result.messgae
// 				});
			},
			error:function(result){
				alert("失败");
			}
			
		});
		
		$('#submit').click(function(){
			$('#login_form').submit();
		});
		
		$('#reset').click(function(){
			$('#login_form').form('clear');
		});
		
		$('#login_form').find('input').on('keyup',function(event){
			if(event.keyCode == 13){
				$('#login_form').submit();
			}
	});
		
	})
</script>
<title>登录界面</title>
</head>
<body>
	<div id="login_dialog" style="width:300px;height:200px;">
		<br><br>
		<form id="login_form" method="post">  
			<div align="center">
				<label>用户名:</label>
				<input id="username" name="username" type="text"/><br/><br/>
			</div>
			<div align="center">
				<label>密码:&nbsp;&nbsp;</label>
				<input id="password" name="password" type="password" value=""/><br/><br/>
			</div>
			<br>
			<div id ="validCodeError" style="font-size:12px ;color: red;margin-left:60px;margin-top:-15px; *margin-top:0px;"></div>
			<br>
			<div align="center">
				<a class="easyui-linkbutton" id="submit">登录</a>
				<a class="easyui-linkbutton" id="reset">重置</a>
			</div>
		</form>
	</div>
</body>
</html>