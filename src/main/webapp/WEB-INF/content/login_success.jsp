<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<HTML>
 <HEAD>
  <TITLE></TITLE>
  <meta name="decorator" content="none"/>
  <META NAME="Generator" CONTENT="Rrooyeetone Cybertech">
  <META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8"> 
  <META HTTP-EQUIV="Pragma" CONTENT="no-cache"> 
<script type="text/javascript">
	var nowD = new Date();
	var key = nowD.getDate()+nowD.getDay()+nowD.getTime();
	var mainwin=window.open('<%=path%>/login!mainPage.action','w_main'+key,'fullscreen=0,toolbar=0,location=1,directories=0,status=0,menubar=0,scrollbars=0,resizable=1,titlebar=0,width='+(screen.availWidth*(1-9/1280))+',height='+(screen.availHeight*(1-35/1024)));
	mainwin.focus();
	mainwin.moveTo(0,0);
	mainwin.resizeTo(screen.availWidth,screen.availHeight);
	window.open('','_self','');
	window.opener=null;
	window.close();
</script>  