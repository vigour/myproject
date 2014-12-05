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
				$.ajax({
					type : post,
					url	: flag=='add'?'${ctx}/sys/user/user!save.action':'${ctx}/sys/user/user!update.action',
					cache : false,
					data:$('#userform').seriralize(),	//调用自定义的序列化表单
					dataType : 'json',
					success:function(result){
						//1关闭窗口
						$('#userdialog').dialog('close');
						//2刷新datagrid
						$('#userlist').datagrid('reload');
						//3提示信息
						$.message.show({
							title : result.type,
							msg : result.message
						})
					},
					error:function(result){
						$.message.show({
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
		<s:form id="userform" action="" method="post"  theme="simple">
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
					<td><s:radio list="%{#{'true':'是','false':'否'}}" name="vo.visible" value="true" ></s:radio> </td>
					<td><s:label>状态</s:label></td>
					<td><s:radio list="%{#{'1':'启用','2':'停用','3':'作废'}}" name="vo.status" value="1"></s:radio> </td>
					
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