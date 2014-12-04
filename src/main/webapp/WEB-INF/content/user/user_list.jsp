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
<title>用户列表</title>
<link rel="stylesheet" type="text/css"
	href="<%=path %>/resources/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=path %>/resources/easyui/themes/icon.css">
<script type="text/javascript" src="<%=path %>/resources/easyui/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/easyui/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=path %>/resources/easyui/js/easyui-lang-zh_CN.js"></script>

</head>
<body>
<script type="text/javascript">
	$(function(){
		$('#userlist').datagrid({
			idField:'id',
			fitColumns:true ,
		    url:'<%=path%>/user/user.action',
		    fit : true,
		    loadMsg:'数据正在加载,请稍候...',
		    fitColumns: true,
			width :'100%',
			height: '100%',
			frozenColumns:[[
                {field:'ck',checkbox:true}
            ]],
		    columns:[[    
		        {field:'code',title:'代码'},    
		        {field:'name',title:'名称'},    
		        {field:'price',title:'价格',width:100,align:'right'}    
		    ]],
		    pagination : true,
		    pageSize : 10,
		    pageList:[5,10,15,20,50]
		});	
	})
  
</script>
	<table id="userlist" ></table>  
</body>
</html>