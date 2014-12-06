<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
<%@ include file="/common/head.jsp" %>
<script type="text/javascript">
	$(function(){
		
		
		$('#functionTree').tree({
			url : 'sys/function/function!getFunctionTree.action',
			onClick: function(node){
				var title = node.text;
				var url = node.url;
				var iconcls = node.iconCls;
				if(!!!iconcls){
					return ;
				}
				if($('#centerPenel').tabs('exists',title)){
					$('#centerPenel').tabs('select',title);
				}else{
					$('#centerPenel').tabs('add',{   
			 		    title:title,
			 		   	href: url,
			 		   	iconCls : node.iconCls,
			 		    closable:true  
			 		});
				}
			}
		});
		
		
	})
</script>

</head>
<body class="easyui-layout" fit=true style="width:100%;height:100%">   
    <div data-options="region:'north'" id="northPanel" style="height:10%;"></div>   
    <div data-options="region:'west',title:'菜单',split:true" id="westPanel" style="width:15%;">
    	<ul id="functionTree" class="easyui-tree"></ul>
    </div>   
    <div data-options="region:'center'" id="centerPenel" class="easyui-tabs" style="width:100%;height:100%">
    	  <div title="首页" style="padding:20px;display:none;">   
                tab1   
          </div> 
    </div>   

</body>  
</html>