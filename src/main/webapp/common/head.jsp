<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script>
global.type = "${type}";
</script>

<div id="top_search">
	<div class="top_search_center" style="position:relative;">
		<div class="top_logo">
				<a href="http://www.bucuoa.com"><img alt="www.bucuoa.com" src="${realPath}/images/logo.png" noLazyload="true"></a>
		</div>
		<div class="top_search_con" style="float:right;margin-right:8px;">
		
		</div>
		<div class="clear"></div>
	</div>
</div>
<div id="head">
	<div class="head_con">
		<ul class="left">
			<li><a href="//www.bucuoa.com" class="selected1" onFocus="this.blur()">首&nbsp;页</a></li>
<%-- 			<li><a  href="${realPath}/group/all"  class="selected2" onFocus="this.blur()">群组</a></li>
 --%>		</ul>
	</div>
</div>
