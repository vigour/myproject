<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/css/ext-all.css" />
		<script type="text/javascript" src="<%=basePath%>resources/js/extjs/bootstrap.js" ></script>
		<script type="text/javascript" src="<%=basePath%>resources/js/extjs/ext-lang-zh_CN.js" ></script>
		<script type="text/javascript" src="<%=basePath%>resources/js/extjs/ux/TabCloseMenu.js" ></script>
		<script type="text/javascript" src="<%=basePath%>resources/js/extjs/ux/TabScrollerMenu.js"></script>
		<script type="text/javascript" src="<%=basePath%>resources/js/extjs/ux/BoxReorderer.js"></script>
		<script type="text/javascript" src="<%=basePath%>resources/js/extjs/ux/TabReorderer.js"></script>
		<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/css/ux/TabScrollerMenu.css" />
  </head>
</html>