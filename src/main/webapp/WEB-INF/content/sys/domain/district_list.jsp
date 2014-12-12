<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>区域列表</title>
<%@ include file="/common/head.jsp" %>
</head>
<body>
<script type="text/javascript">

	$(function(){
		var flag ;		//undefined 判断新增和修改方法


		
		$('#regionlist').datagrid({
			idField:'id',
			fitColumns:true ,
		    url:'${ctx}/sys/domain/region!getAllRegion.action',
		    fit : true,
		    loadMsg:'数据正在加载,请稍候...',
		    remoteSort:false,
		    fitColumns: true,
			width :'100%',
			height: '50%',
			toolbar : [
           		{
					text : '新增区域',
					iconCls :'icon-add',
					handler:function(){
						flag = 'add';
						//设置表单参数
						$('#regiondialog').dialog({
							title:'新增区域'
						});
						//清空表单
						$('#regionform').get(0).reset();
						//显示表单
						$('#regiondialog').dialog('open');

					}
				},{
					text : '修改区域',
					iconCls :'icon-edit',
					handler:function(){
						flag = 'edit';
						var arr = $('#regionlist').datagrid('getSelections');
						if(arr.length != 1){
							$.messager.alert("提示信息!",'只能选择一行记录进行修改!','warning');
							$('#regionlist').datagrid('unselectAll');
						}else{
							$('#regiondialog').dialog({
								title:'修改用户'
							});
							
							//打开表单
							$('#regiondialog').dialog('open');

							//清空表单
							$('#regionform').get(0).reset();

							$('#regionform').form('load',{
								'vo.id' : arr[0].id,
								'vo.regionName': arr[0].regionName,
								'vo.regionCode': arr[0].regionCode,
								'vo.shortName': arr[0].shortName,
								'vo.description': arr[0].description,
								'vo.visible' : arr[0].visible,
								'vo.status'	: arr[0].status
							})
						}
					}
				},{
					text : '删除用户',
					iconCls :'icon-cancel',
					handler:function(){
						var arr = $('#regionlist').datagrid('getSelections');
						if(arr.length <= 0){
							$.messager.alert("提示信息!",'至少选择一行记录进行删除!','warning');
						}else{
							$.messager.confirm('提示信息' , '确认删除?' , function(btn){
								if(btn){
									var ids = '';
									for(var i=0, len=arr.length; i<len; i++){
										ids += arr[i].id+',';
									}
									ids = ids.substring(0, ids.length-1);
									$.post(
											'${ctx}/sys/domain/region!deleteRegions.action',
											{ids:ids},
											function(result){
												//1 刷新数据表格
												$('#regionlist').datagrid('reload');
												//2 清空idField
												$('#regionlist').datagrid('unselectAll');
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
		        {field:'regionName',title:'区域名称'},    
		        {field:'regionCode',title:'区域编码'},
		        {field:'shortName',title:'区域简称'},
		        {field:'description',title:'描述'},
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
		        {field:'remark',title:'备注'}
		    ]],
		    pagination : true,
		    pageSize : 3,
		    pageList:[3,6,9,12,15]
		});	
		
		$('#submit').click(function(){
			if($('#regionform').form('validate')){
				var url = flag=='add'?'${ctx}/sys/domain/region!addRegion.action':'${ctx}/sys/domain/region!updateRegion.action';
				$.ajax({
					type : 'post',
					url	: url,
					cache : false,
					data : $("#regionform").serialize(),	//调用自定义的序列化表单
					dataType : 'json',
					success:function(result){
						//1关闭窗口
						$('#regiondialog').dialog('close');
						//2刷新datagrid并取消选择状态
						$('#regionlist').datagrid('reload');
						$('#regionlist').datagrid('unselectAll');
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
		});
		
// 		$('#reset').click(function(){
// 			$('#userform').form('reset');
// 		});
		
// 		$('#close').click(function(){
// 			$('#userdialog').dialog('close');
// 		});
		
	})
  
</script>
	<table id="regionlist"></table>  
	<div id="regiondialog" modal="true" style="width:500px;height:300px">
		<form id="regionform" class="easyui-form" action="" method="post" >
			<input type="hidden" id="userid" name="vo.id" />
			<table >
				<tr >
					<td><s:label>区域名称</s:label></td>
					<td><input type="text" id="regionname" name="vo.regionName" class="easyui-validatebox" required="true"/></td>
					<td><s:label>区域编码</s:label></td>
					<td><input type="text" id="regioncode" name="vo.regionCode" class="easyui-validatebox" required="true"/></td>
				</tr>
				<tr >
					<td><s:label>区域简称</s:label></td>
					<td><input type="text" id="shortName" name="vo.shortName" class="easyui-validatebox" required="true"/></td>
					<td><s:label>区域描述</s:label></td>
					<td><input type="text" id="description" name="vo.description" class="easyui-validatebox" /></td>
				</tr>
				<tr>
					<td><s:label>是否可见</s:label></td>
					<td>
						<input type="radio" id="visible"  name="vo.visible" value="true">是
						<input type="radio" id="visible"  name="vo.visible" value="false">否
					</td>
					<td><s:label>状态</s:label></td>
					<td>
						<input type="radio" id="status"  name="vo.status" value=1>启用
						<input type="radio" id="status"  name="vo.status" value=2>停用
						<input type="radio" id="status"  name="vo.status" value=3>作废
					</td>
				</tr>
				<tr>
					<td colspan="2" align="center">
						<a id="submit" class="easyui-linkbutton">确定</a>
						<a id="reset" class="easyui-linkbutton">重置</a>
						<a id="close" class="easyui-linkbutton">关闭</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>