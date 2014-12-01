<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css"
	href="<%=path %>/resources/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=path %>/resources/easyui/themes/icon.css">
<script type="text/javascript" src="<%=path %>/resources/easyui/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/easyui/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/easyui/js/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
$(function(){
	$('#userlist').datagrid({    
	    url:'datagrid_data.json',
	    fit : true,
		width :'100%',
		height: '100%',
	    columns:[[    
	        {field:'code',title:'代码'},    
	        {field:'name',title:'名称'},    
	        {field:'price',title:'价格',width:100,align:'right'}    
	    ]]    
	});	
})
  
</script>
</head>
<body>
	<table id="userlist" ></table>  
</body>
</html>