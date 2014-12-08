<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户列表</title>
<%@ include file="/common/head.jsp" %>
</head>
<body>
<script type="text/javascript">

	$(function(){
		var flag ;		//undefined 判断新增和修改方法


		
		$('#userlist').datagrid({
			idField:'id',
			fitColumns:true ,
		    url:'${ctx}/sys/user/user!getAllUser.action',
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
						flag = 'add';
						//设置表单参数
						$('#userdialog').dialog({
							title:'新增用户'
						});
						//清空表单
						$('#userform').get(0).reset();
						//显示表单
						$('#userdialog').dialog('open');

					}
				},{
					text : '修改用户',
					iconCls :'icon-edit',
					handler:function(){
						flag = 'edit';
						var arr = $('#userlist').datagrid('getSelections');
						if(arr.length != 1){
							$.messager.alert("提示信息!",'只能选择一行记录进行修改!','warning');
						}else{
							$('#userdialog').dialog({
								title:'修改用户'
							});
							
							//打开表单
							$('#userdialog').dialog('open');

							//清空表单
							$('#userform').get(0).reset();

							$('#userform').form('load',{
								'vo.id' : arr[0].id,
								'vo.username': arr[0].username,
								'vo.password': arr[0].password,
								'vo.visible' : arr[0].visible,
								'vo.status'	: arr[0].status
							})
						}
					}
				},{
					text : '删除用户',
					iconCls :'icon-cancel',
					handler:function(){
						var arr = $('#userlist').datagrid('getSelections');
						if(arr.length <= 0){
							$.messager.alert("提示信息!",'只能选择一行记录进行修改!','warning');
						}else{
							$.messager.confirm('提示信息' , '确认删除?' , function(btn){
								if(btn){
									var ids = '';
									for(var i=0, len=arr.length; i<len; i++){
										ids += arr[i].id+',';
									}
									ids = ids.substring(0, ids.length-1);
									$.post(
											'${ctx}/sys/user/user!deleteUsers.action',
											{ids:ids},
											function(result){
												//1 刷新数据表格
												$('#userlist').datagrid('reload');
												//2 清空idField
												$('#userlist').datagrid('unselectAll');
												//3提示信息
												$.messager.show({
													title : result.type,
													msg : result.message
												})
												 
											})
								}else {
									return ;
								}
	
							})	
							
						}
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
						}},
		        {field:'modifier',title:'修改人'},
		        {field:'modificationDate',title:'修改日期'},
		        {field:'UUIDEntity',title:'备注'}
		    ]],
		    pagination : true,
		    pageSize : 3,
		    pageList:[3,6,9,12,15]
		});	
		
		$('#submit').click(function(){
			if($('#userform').form('validate')){
				var url = flag=='add'?'${ctx}/sys/user/user!addUser.action':'${ctx}/sys/user/user!updateUser.action';
				$.ajax({
					type : 'post',
					url	: url,
					cache : false,
					data : $("#userform").serialize(),	//调用自定义的序列化表单
					dataType : 'json',
					success:function(result){
						//1关闭窗口
						$('#userdialog').dialog('close');
						//2刷新datagrid并取消选择状态
						$('#userlist').datagrid('reload');
						$('#userlist').datagrid('unselectAll');
						//3提示信息
						$.messager.show({
							title : result.type,
							msg : result.message
						})
					},
					error:function(result){
						$.messager.show({
							title : result.type,
							msg : result.message
						})
					}
				})
			}
		})
		
	})
  
</script>
	<table id="userlist"></table>  
	<s:div id="userdialog" modal="true" style="width:500px;height:300px">
		<s:form id="userform" action="" method="post" >
			<s:hidden id="userid" name="vo.id"></s:hidden>
			<table >
				<tr >
					<td><s:label>用户名</s:label></td>
					<td><s:textfield id="username" name="vo.username" class="easyui-validatebox" required="true"></s:textfield></td>
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
						<s:a id="submit" class="easyui-linkbutton">确定</s:a>
						<s:a id="reset" class="easyui-linkbutton">重置</s:a>
						<s:a id="close" class="easyui-linkbutton">关闭</s:a>
					</td>
				</tr>
			</table>
		</s:form>
	</s:div>
</body>
</html>