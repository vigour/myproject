<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>功能列表</title>
<%@ include file="/common/head.jsp" %>
</head>
<body>
<script type="text/javascript">
	$(function(){
		var flag ;		//undefined 判断新增和修改方法
		
		
		//左边的树
		$('#functionWestTree').tree({
			url : '${ctx}/sys/function/function!getFunctionTree.action',
			onClick: function(node){
				var id = node.id;
				$('#functionlist').datagrid('reload',{id : id});
			}
		});
		//右边的grid
		$('#functionlist').datagrid({
			idField : 'id',
			fitColumns:true ,
		    url:'${ctx}/sys/function/function!getChildFunction.action',
		    fit : true,
		    loadMsg:'数据正在加载,请稍候...',
		    remoteSort:false,
		    fitColumns: true,
			width :'100%',
			height: '50%',
			toolbar : [
		           		{
							text : '新增功能',
							iconCls :'icon-add',
							handler:function(){
								flag = 'add';
								//设置表单参数
								$('#functiondialog').dialog({
									title:'新增功能'
								});
								//清空表单
								$('#userform').get(0).reset();
								//显示表单
								$('#userdialog').dialog('open');

							}
						},{
							text : '修改功能',
							iconCls :'icon-edit',
							handler:function(){
// 								flag = 'edit';
// 								var arr = $('#userlist').datagrid('getSelections');
// 								if(arr.length != 1){
// 									$.messager.alert("提示信息!",'只能选择一行记录进行修改!','warning');
// 								}else{
// 									$('#userdialog').dialog({
// 										title:'修改用户'
// 									});
									
// 									//打开表单
// 									$('#userdialog').dialog('open');

// 									//清空表单
// 									$('#userform').get(0).reset();

// 									$('#userform').form('load',{
// 										'vo.id' : arr[0].id,
// 										'vo.username': arr[0].username,
// 										'vo.password': arr[0].password,
// 										'vo.visible' : arr[0].visible,
// 										'vo.status'	: arr[0].status
// 									})
// 								}
							}
						},{
							text : '删除功能',
							iconCls :'icon-cancel',
							handler:function(){
// 								var arr = $('#userlist').datagrid('getSelections');
// 								if(arr.length <= 0){
// 									$.messager.alert("提示信息!",'只能选择一行记录进行修改!','warning');
// 								}else{
// 									$.messager.confirm('提示信息' , '确认删除?' , function(btn){
// 										if(btn){
// 											var ids = '';
// 											for(var i=0, len=arr.length; i<len; i++){
// 												ids += arr[i].id+',';
// 											}
// 											ids = ids.substring(0, ids.length-1);
// 											$.post(
// 													'${ctx}/sys/user/user!deleteUsers.action',
// 													{ids:ids},
// 													function(result){
// 														//1 刷新数据表格
// 														$('#userlist').datagrid('reload');
// 														//2 清空idField
// 														$('#userlist').datagrid('unselectAll');
// 														//3提示信息
// 														$.messager.show({
// 															title : result.type,
// 															msg : result.message
// 														})
														 
// 													})
// 										}else {
// 											return ;
// 										}
			
// 									})	
									
// 								}
							}
						}],
					frozenColumns:[[
		                {field:'ck',checkbox:true}
		            ]],
				    columns:[[    
				        {field:'functionName',title:'模块名称'},    
				        {field:'url',title:'url'},
				        {field:'icon',title:'icon'},
				        {field:'parentFunctionName',title:'父模块名称'},
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
				        {field:'creationDate',title:'创建日期'},
				        {field:'modifier',title:'修改人'},
				        {field:'modificationDate',title:'修改日期'},
				        {field:'showOrder',title:'显示顺序'},
				        {field:'remark',title:'备注'}
				    ]],
				    pagination : true,
				    pageSize : 3,
				    pageList:[3,6,9,12,15]
		});
		

	});
		
</script>
	<div id="functionmain" class="easyui-layout" style="width:100%;height:100%">
		 <div data-options="region:'west',split:true" id="functionWestPanel" style="width:20%;">
	    	<ul id="functionWestTree" class="easyui-tree"></ul>
	    </div>   
	    <div data-options="region:'center'" id="functionCenterPenel" style="width:100%;height:100%">
	    	 <table id="functionlist"></table>  
	    </div>
	</div>
	<s:div id="functiondialog" modal="true" style="width:500px;height:300px">
		<s:form id="functionform" class="easyui-form" action="" method="post" >
			<s:hidden id="functionid" name="vo.id"></s:hidden>
			<table >
				<tr >
					<td><s:label>模块名称</s:label></td>
					<td><s:textfield id="functionname" name="vo.functionname" class="easyui-validatebox" required="true"></s:textfield></td>
					<td><s:label>密码</s:label></td>
					<td><s:password id="password" name="vo.password" class="easyui-validatebox" required="true"></s:password></td>
				</tr>
				<tr>
					<td><s:label>是否可见</s:label></td>
					<td><s:radio id="visible" list="%{#{'true':'是','false':'否'}}" name="vo.visible" value="true" ></s:radio> </td>
					<td><s:label>状态</s:label></td>
					<td><s:radio id="status" list="%{#{'1':'启用','2':'停用','3':'作废'}}" name="vo.status" value="1"></s:radio> </td>
					
				</tr>
				<tr>
					<td colspan="2" align="center">
						<a id="submit" class="easyui-linkbutton">确定</a>
						<a id="reset" class="easyui-linkbutton">重置</a>
						<a id="close" class="easyui-linkbutton">关闭</a>
					</td>
				</tr>
			</table>
		</s:form>
	</s:div>
	
</body>
</html>