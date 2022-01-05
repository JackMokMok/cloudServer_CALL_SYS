<#include "/WEB-INF/macro/frame.ftl">
<@listFrame path="admin/admin-list" menu="admin" title="登录历史">
	<div class="tit">
		<h3 class="fl">登陆历史</h3>
        <div class="clear"></div>
	</div>
	<form action="" method="post">
		<div class="clear space_10"></div>
	</form>
	<table border="0" cellspacing="0" cellpadding="0">
		<tr>
			<th>id</th>
			<th>登录时间</th>
			<th>登录ip</th>
		</tr>
		<#list infoList as i>
			<tr>
				<td>${i.id!}</td>
				<td>${(i.loginTime?string("yyyy-MM-dd HH:mm:ss"))!}</td>
				<td>${i.loginIp!}</td>
			</tr>
		</#list>
	</table>
</@listFrame>
