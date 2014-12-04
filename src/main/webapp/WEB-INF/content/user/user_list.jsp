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
		    url:'<%=path%>/user/user!getAllUser.action',
		    fit : true,
		    loadMsg:'数据正在加载,请稍候...',
		    remoteSort:false,
		    fitColumns: true,
			width :'100%',
			height: '50%',
			toolbar : [
           		{
					text : '新增用户',
					iconCls :'icon-add',
					handler:function(){
						

					}
				},{
					text : '修改用户',
					iconCls :'icon-edit',
					handler:function(){
						alert('修改用户');
					}
				},{
					text : '删除用户',
					iconCls :'icon-cancel',
					handler:function(){
						alert('删除用户');
					}
				},{
					text : '查询用户',
					iconCls :'icon-search',
					handler:function(){
						alert('查询用户');
					}
				}],
			frozenColumns:[[
                {field:'ck',checkbox:true}
            ]],
		    columns:[[    
		        {field:'username',title:'用户名'},    
		        {field:'password',title:'名称'},
		        {field:'creator',title:'创建人'},
		        {field:'visible',title:'是否可见',
		        	formatter:function(value,rowData,rowIndex){
		        		if(value){
							return '是' ;
						} else{
							return '否' ; 
						}
		        	}
        		},    
		        {field:'status',title:'状态',
       				formatter:function(value,rowData,rowIndex){
		        		if(value == 1){
							return '<span style=color:green; >启用</span>' ;
						} else if(value == 2){
							return '<span style=color:red; >停用</span>' ; 
						} else if(value == 3){
							return '<span style=color:gray; >作废</span>' ;
						}
	        	}},
		        {field:'creator',title:'创建人'},
		        {field:'creationDate',title:'创建日期',
       				formatter:function(value,rowData,rowIndex){
		        		console.info(value);
						}},
		        {field:'modifier',title:'修改人'},
		        {field:'modificationDate',title:'修改日期'},
		        {field:'UUIDEntity',title:'备注'}
		    ]],
		    pagination : true,
		    pageSize : 3,
		    pageList:[3,6,9,12,15]
		});	
	})
  
</script>
	<table id="userlist" ></table>  
	<
</body>
</html>