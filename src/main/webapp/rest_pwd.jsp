﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="common/path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>重置密码-不错啊</title>
<link rel="stylesheet" type="text/css" href="${realPath}/css/login.css?version=2.5">
</head>
<body>
	<%@ include file="common/head.jsp" %>
	<div class="main">
		<div class="left">
			<div style="font-weight:bold;font-size:16px;padding-left:50px;color:#666666;height:30px;">重置密码1-1</div>
			<div class="login_p" style="margin-top:1px;">
				<div class="login_tit">账号：</div>
				<div class="login_input"><input type="text" id="account"></div>
				<div class="login_info">用户名或者邮箱</div>
				<div class="clear"></div>
			</div>
			<div class="login_p">
				<div class="login_tit">验证码：</div>
				<div class="login_input login_code"><input type="text" class="code" id="code"></div>
				<div class="login_code_img">
					<a href="javaScript:refreshImage()" onfocus="this.blur();"><img id="codeImage" src="common/image.jsp" height="23" border="0"/></a>
				</div>
				<div class="login_code_refresh"><a href="javaScript:refreshImage()" class="login_code_refresh_link">看不清？</a></div>
				<div class="clear"></div>
			</div>
			<div class="login_p">
				<div class="login_tit"></div>
				<div class="login_submit">
					<a href="javascript:void(0)" class="btn" id="rest_btn">给我发送重置密码连接</a>
					<!-- <a href="" class="foregetpsw">忘记密码？</a> -->
					<span class="result_info"><i id="warm_icon"></i><span id="warm_info"></span></span>
				</div>
			</div>
		</div>
		<div class="right">
			<div class="login_right_tit">
				为什么收不到重置密码邮件？？
			</div>
			<ul>
				<li>1、是否输入正确的邮箱地址(注册时使用的)</li>
				<li>2、可能被邮件系统当成垃圾邮件，检查垃圾箱</li>
				<li>3、可能是本站的邮件系统繁忙或者故障，请稍候再试或者联系管理员</li>
			</ul>
		</div>
		<div class="clear"></div>
	</div>
	<script type="text/javascript" src="${realPath}/js/user.restpwd.js?version=2.5"></script>
	<%@ include file="common/foot.jsp" %>
</body>
</html>