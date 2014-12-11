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
		
		$('#showOrder').numberbox({
			precision : 0
		});
		
		//左边的树
		$('#functionWestTree').tree({
			url : '${ctx}/sys/function/function!getFunctionTree.action',
			onClick: function(node){
				var id = node.id;
				$('#functionlist').datagrid('reload',{id : id});
				$('#functionlist').datagrid('unselectAll');
			}
		});
		
		
		//选择的树
		$('#parentFunction').combotree({
			url : '${ctx}/sys/function/function!getFunctionTree.action',
			valueField : 'id',  
	        textField : 'text',  
	        required : true,  
	        editable : false,
			multiple:false,
			onLoadSuccess : function(node, data) {
				 var t = $(this);  
           
		            if (data) {  
		                $(data).each(function(index, d) {  
		                    if (this.state == 'closed') {
		                        t.tree('expandAll',d.target);  
		                    }  
		                });  
		            }  
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
								$('#functionform').get(0).reset();
									
								//var node = $('#functionWestTree').tree('getSelected');
								
							//console.log(node.id);
// 								var url = '${ctx}/sys/function/function!getFunctionTree.action'
								
								//$('#parentFunction').combotree('reload');
								
// 								var selectnode = $('#parentFunction').combotree('tree').tree('find',node.id);
								
// 								$('#parentFunction').combotree('tree').tree('select',selectnode.target);
								
								//$('#parentFunction').combotree('setValue',node.id);
								$('#parentFunction').combotree('tree').tree('reload');
								//显示表单
								$('#functiondialog').dialog('open');

							}
						},{
							text : '修改功能',
							iconCls :'icon-edit',
							handler:function(){
								flag = 'edit';
								var arr = $('#functionlist').datagrid('getSelections');
								if(arr.length != 1){
									$.messager.alert("提示信息!",'只能选择一行记录进行修改!','warning');
								}else{
									$('#functiondialog').dialog({
										title:'修改功能'
									});
									
									//打开表单
									$('#functiondialog').dialog('open');

									//清空表单
									$('#functionform').get(0).reset();

									$('#functionform').form('load',{
										'vo.id' : arr[0].id,
										'vo.functionName': arr[0].functionName,
										'vo.url': arr[0].url,
										'vo.icon': arr[0].icon,
										'vo.showOrder': arr[0].showOrder,
										//'vo.parentFunction': arr[0].parentFunction,
										'vo.visible' : arr[0].visible,
										'vo.status'	: arr[0].status
									});
									
									$('#parentFunction').combotree('tree').tree('reload');
									$('#parentFunction').combotree('setValue',{id: arr[0].parentFunction});
								}
							}
						},{
							text : '删除功能',
							iconCls :'icon-cancel',
							handler:function(){
								var arr = $('#functionlist').datagrid('getSelections');
								if(arr.length <= 0){
									$.messager.alert("提示信息!",'至少选择一行记录进行删除!','warning');
								}else{
									$.messager.confirm('提示信息' , '将会删除子功能,确认删除?' , function(btn){
										if(btn){
											var ids = '';
											for(var i=0, len=arr.length; i<len; i++){
												ids += arr[i].id+',';
											}
											ids = ids.substring(0, ids.length-1);
											$.post(
													'${ctx}/sys/function/function!deleteFunction.action',
													{ids:ids},
													function(result){
														
														//1 刷新数据表格
														$('#functionlist').datagrid('reload');
														//2 清空idField
														$('#functionlist').datagrid('unselectAll');
														//3刷新左边的树
														$('#functionWestTree').tree('reload');
														//4提示信息
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
						}],
					frozenColumns:[[
		                {field:'ck',checkbox:true}
		            ]],
				    columns:[[    
				        {field:'functionName',title:'模块名称'},    
				        {field:'url',title:'URL'},
				        {field:'icon',title:'图标'},
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
		
		$('#submit').click(function(){
			if($('#functionform').form('validate')){
				var url = flag=='add'?'${ctx}/sys/function/function!addFunction.action':'${ctx}/sys/function/function!updateFunction.action';
				$.ajax({
					type : 'post',
					url	: url,
					cache : false,
					data : $("#functionform").serialize(),	//调用自定义的序列化表单
					dataType : 'json',
					success:function(result){
						//1关闭窗口
						$('#functiondialog').dialog('close');
						//2刷新datagrid并取消选择状态
						$('#functionlist').datagrid('reload');
						$('#functionlist').datagrid('unselectAll');
						//3刷新左边的树
						$('#functionWestTree').tree('reload');
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
	<div id="functiondialog"  modal="true" style="width:500px;height:300px">
		<form id="functionform" class="easyui-form" action="" method="post" >
			<input type="hidden" id="userid" name="vo.id" />
			<table >
				<tr >
					<td><s:label>模块名称</s:label></td>
					<td><input type="text" id="functionname" name="vo.functionName" class="easyui-validatebox" required="true" /></td>
					<td><s:label>图标</s:label></td>
					<td><input type="text" id="icon" name="vo.icon" class="easyui-validatebox" required="true"/></td>
				</tr>
				<tr >
					<td><s:label>URL</s:label></td>
					<td><input type="text" id="url" name="vo.url" class="easyui-validatebox" required="true"/></td>
					<td><s:label>显示顺序</s:label></td>
					<td><input type="text" id="showOrder" name="vo.showOrder" class="easyui-validatebox" required="true"/></td>
				</tr>
				<tr >
					<td><s:label>父模块</s:label></td>
					<td><select required="true" id="parentFunction" name="vo.parentFunction" style="width:100%" class="easyui-combotree" ></select></td>
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