<#include "/WEB-INF/macro/frame.ftl">
<@listFrame path="customer/customer-list" menu="customer" title="设备类型">
	<nav style="display:inline-block">
		<ul>
			<li><a href="caller-list.html?customerId=${bean.customerId}">呼叫器管理</a></li>
			<li><a href="caller-group-list.html?customerId=${bean.customerId}">分组管理</a></li>
			<li class="active"><a  href="javascript:">类型管理</a></li>
		</ul>
	</nav>
	<div style="display:inline-block;float:right;font-size:1.2em;font-weight:bold">${customer.name}</div>
	<div style="height:6px"></div>
    <form action="" method="post">
    	<input type="hidden" name="customerId" value="${bean.customerId}"/>
		<div class="search">
			<label class="fl padding_6">
				<span class="title">类型名称：</span><input type="text" name="name" value="${(bean.name)!''}" style="width:70px;" />&nbsp;&nbsp;&nbsp;&nbsp;
			</label>
			<div class="clear"></div>
		</div>
		<div class="search_butn"><input type="submit" value="搜 索" class="butn" /></div>
		<div class="clear space_10"></div>
	</form>
	
	<#if (self.isPathAvailable("caller/edit-caller-category"))>
		<div class="p_b_nav fl">
			<span class="p_b_left"></span><a href="edit-caller-category.html?customerId=${bean.customerId}">新增类型</a><span class="p_b_right"></span>
		</div>
	</#if>
	
	<table border="0" cellspacing="0" cellpadding="0">
		<tr>
			<th>类型名称</th>
            <th class="align_center" style="width:100px">操作</th>
		</tr>
		<#list categoryList as g>
			<tr>
                <td>${(g.name)!}</td>
                <td class="align_center">
                	<a href="edit-caller-category.html?id=${g.id}" class="edit" title="编辑">&nbsp;</a>
                </td>
			</tr>
		</#list>
	</table>
	<@script>  
        $(document).ready(function () {
        	var nav = $('nav');
			var line = $('<div/>').addClass('line');
			line.appendTo(nav);
			
			var active = nav.find('.active');
			var pos = 0;
			var wid = 0;
			
			if(active.length) {
			  pos = active.position().left;
			  wid = active.width();
			  line.css({
			    left: pos,
			    width: wid
			  });
			}
        });
	</@script>
</@listFrame>
