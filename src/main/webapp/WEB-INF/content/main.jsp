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
					
					 /*为选项卡绑定右键*/
				    $(".tabs li").on('contextmenu',function(e){
				        
				        /* 选中当前触发事件的选项卡 */
						var subtitle =$(this).text();
						$('#centerPanel').tabs('select',subtitle);
				        
				        //显示快捷菜单
						e.preventDefault();					//屏蔽浏览器的菜单
						$('#mainmenu').menu('show', {   
							  left: e.pageX,   
							  top: e.pageY   
							});
						
						
				        return false;
				    });
				}
			}
		});
		
		//关闭当前页
		$('#maintabclose').click(function(){
 				//获取当前选中页
 				var currenttab = $('#centerPenel').tabs('getSelected');
				var currTitle = currenttab.panel('options').title;    
		        $('#centerPenel').tabs('close', currTitle);
		}) ; 
		
		  //除当前之外关闭所有
	    $("#maintabothers").click(function(){
	        var currTab = $('#centerPenel').tabs('getSelected');
	        var currTitle = currTab.panel('options').title;    
	        
	        $(".tabs li").each(function(i, n){
	            var title = $(n).text();
	            if(currTitle != title && title != '首页'){//需要保留首页
	                $('#centerPenel').tabs('close',title);            
	            }
	        });
	    });
		
		 //关闭所有
	    $("#maintaball").click(function(){
	        $(".tabs li").each(function(i, n){
	            var title = $(n).text();
	            if(title != '首页'){	//需要保留首页
	            	$('#centerPenel').tabs('close',title);    
	            }
	        });
	    }); 
		
		
	})
</script>

</head>
<body class="easyui-layout" fit=true style="width:100%;height:100%">   
    <div data-options="region:'north'" id="northPanel" style="overflow: hidden;  background: #D2E0F2 repeat-x center 50%;
        line-height: 20px; color: #fff;height:30px" >欢迎使用</div>   
    <div data-options="region:'west',title:'菜单',split:true" id="westPanel" style="width:15%;">
    	<ul id="functionTree" class="easyui-tree"></ul>
    </div>   
    <div data-options="region:'center'" id="centerPenel" class="easyui-tabs" style="width:100%;height:100%">
    	  <div title="首页" style="padding:20px;display:none;">   
                tab1   
          </div> 
    </div>
    <div id="mainmenu" class="easyui-menu">
    	<div id="maintabclose">关闭当前页</div>
    	<div class="menu-sep"></div>
		<div id="maintabothers">关闭其他页</div>
		<div id="maintaball">关闭所有页</div>
    </div>   

</body>  
</html>