﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="common/path.jsp" %>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登陆不错啊-不错啊</title>
<link rel="stylesheet" type="text/css" href="${realPath}/css/login.css?version=2.5">
</head>
<body>
	<%@ include file="common/head.jsp" %>
	<div class="main">
		<div class="left">
			<div class="login_p" style="margin-top:1px;">
				<div class="login_tit">账号：</div>
				<div class="login_input"><input type="text" id="account"></div>
				<div class="login_info">用户名或者邮箱</div>
				<div class="clear"></div>
			</div>
			<div class="login_p">
				<div class="login_tit">登陆密码：</div>
				<div class="login_input"><input type="password" id="pwd"></div>
				<div class="clear"></div>
			</div>
			<div class="login_p">
				<div class="login_tit">验证码：</div>
				<div class="login_input login_code"><input type="text" class="code" id="code"></div>
				<div class="login_code_img">
					<a href="javaScript:refreshImage()" onfocus="this.blur();"><img id="codeImage" src="common/image.jsp" height="23" width="65" border="0"/></a>
				</div>
				<div class="login_code_refresh"><a href="javaScript:refreshImage()" class="login_code_refresh_link">看不清？</a></div>
				<div class="clear"></div>
			</div>
			<div class="login_p">
				<div class="login_tit"></div>
				<div class="login_check"><input type="checkbox" value="Y" id="remember_check"/></div>
				<div class="login_remember" id="remember_login">
					记住我的登陆信息
					<span class="login_remember_info">
						(请勿在公用电脑或者网吧内使用此项)
					</span>
				</div>
				<div class="clear"></div>
			</div>
			<div class="login_p">
				<div class="login_tit"></div>
				<div class="login_submit">
					<a href="javascript:void(0)" class="btn" id="login_btn">现在登陆</a>
					<a href="${realPath}/restpwd" class="foregetpsw">忘记密码？</a>
					<span class="result_info"><i id="warm_icon"></i><span id="warm_info"></span></span>
				</div>
				<!-- <a href="https://api.weibo.com/oauth2/authorize?client_id=3552164515&redirect_uri=http://ulewo.com/open_weibo" target="_blank">微博登录</a> -->
			</div>
		</div>
		<div class="right">
			<div class="login_register">
				没有账号？<a href="javascript:goto_register()">注册新会员</a>	
			</div>
			<div class="login_right_tit">
				登陆后可以？
			</div>
			<ul>
				<li>1、发布帖子、吐槽和博客</li>
				<li>2、参与帖子、吐槽、博客的讨论和评论</li>
				<li>3、和别人分享生活中的点滴</li>
				<li>4、随时得到不错啊最新的更新信息</li>
				<li>5、认识更多窝窝好友</li>
			</ul>
		</div>
		<div class="clear"></div>
	</div>
	<script type="text/javascript">
	var redirectUrl = "${param.redirectUrl}"||"http://www.bucuoa.com";
	</script>
	<script type="text/javascript" src="js/user.login.js?version=2.5"></script>
	<%@ include file="common/foot.jsp" %>
</body>
</html>