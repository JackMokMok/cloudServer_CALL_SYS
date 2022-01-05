<#include "/WEB-INF/macro/frame.ftl">
<@listFrame path="customer/customer-list" menu="customer" title="呼叫器管理">
	<nav style="display:inline-block">
		<ul>
			<li class="active"><a href="javascript:">呼叫器管理</a></li>
			<li><a href="caller-group-list.html?customerId=${bean.customerId}">分组管理</a></li>
			<li><a  href="caller-category-list.html?customerId=${bean.customerId}">类型管理</a></li>
		</ul>
	</nav>
    <form action="" method="post">
		<div class="search">
			<label class="fl padding_6">
				<span class="title">MAC地址：</span><input type="text" name="mac" value="${(bean.mac)!''}" style="width:120px;" />&nbsp;&nbsp;&nbsp;&nbsp;
			</label>
			
			<label class="fl padding_6">
				<span class="title">呼叫器位置号：</span><input type="text" name="name" value="${(bean.name)!''}" style="width:120px;" />
				&nbsp;&nbsp;&nbsp;&nbsp;
			</label>

			<label class="fl padding_6">
				<span class="title">呼叫器编码：</span><input type="text" name="code" value="${(bean.code)!''}" style="width:120px;" />
				&nbsp;&nbsp;&nbsp;&nbsp;
			</label>
			
			<div class="clear"></div>
		</div>
		<div class="search_butn"><input type="submit" value="搜 索" class="butn" /></div>
		<div class="clear space_10"></div>
	</form>

	<div class="fl">
		<#if (self.isPathAvailable("caller/del-caller"))>
			<div class="p_b_nav fl">
				<span class="p_b_left"></span><a href="javascript:getIds();">&nbsp;删除&nbsp;</a>
				<span class="p_b_right"></span>
			</div>
		</#if>

		<#if (self.isPathAvailable("caller/edit-caller"))>
			<div class="p_b_nav fl">
				<span class="p_b_left"></span><a href="edit-caller.html?customerId=${bean.customerId}">新增呼叫器</a><span class="p_b_right"></span>
			</div>
		</#if>

		<#if (self.isPathAvailable("caller/empty-caller"))>
			<div class="p_b_nav fl">
				<span class="p_b_left"></span><a href="javascript:empty();" >清空呼叫器</a><span class="p_b_right"></span>
			</div>
		</#if>

		<#if (self.isPathAvailable("caller/down-callers"))>
			<div class="p_b_nav fl">
				<span class="p_b_left"></span><a href="down-callers.json?customerId=${bean.customerId}" >批量导出</a><span class="p_b_right"></span>
			</div>
		</#if>

		<#if (self.isPathAvailable("caller/edit-callers"))>
			<div class="p_b_nav fl">
				<span class="p_b_left"></span><a href="edit-callers.html?customerId=${bean.customerId}">批量添加</a><span class="p_b_right"></span>
			</div>
		</#if>
	</div>
	
	<table border="0" cellspacing="0" cellpadding="0">
		<tr>
			<th width="30"><input type="checkbox" id="checkAll" /></th>
			<th>呼叫主机MAC地址</th>
			<th>呼叫主机名称</th>
			<th>呼叫器类型</th>
			<th>呼叫器位置号</th>
			<th>呼叫器编码</th>
			<th>对接设备的mac</th>
			<th>对接场所的名称</th>
            <th class="align_center" style="width:160px">操作</th>
		</tr>
		<#list callerList as c>
			<tr>
				<td><input type="checkbox" name="eids" value="${c.id}" /></td>
                <td>${(c.mac)!}</td>
				<td>${(c.hostName)!}</td>
				<td>${(c.categoryName)!}</td>
                <td>${(c.name)!}</td>
                <td>${(c.code)!}</td>
				<td>${(c.tvMac)!}</td>
				<td>${(c.customerName)!}</td>
                <td class="align_center">
                	<a href="edit-caller.html?id=${c.id}&customerId=${bean.customerId}" class="view-edit" title="编辑">&nbsp;</a>
					<#if (self.isPathAvailable("caller/del-caller"))>
						<a href="javascript:del('${c.id}');" class="view-del"  title="删除">&nbsp;</a>
					</#if>
                </td>
			</tr>
		</#list>
	</table>
	
	<@script>
		function del(ids){
			if(confirm("是否要删除所选设备?")){
				$.ajax({
					type: "POST",
					url: "del-caller.json",
					data: {ids:ids},
					dataType: "json",
					success: function(data){
						if(data.code == 200){
							showModalBox("删除成功！");
							setTimeout("location.reload()",1000);
						}else{
							showModalBox(data.data);
						}
					}
				});
			}
		}

		function empty() {
			if(confirm("确定要清空呼叫器?")){
				$.ajax({
					type: "POST",
					url: "empty-caller.json",
					data: {customerId:${bean.customerId}},
					dataType: "json",
					success: function(data){
						if(data.code == 200){
							showModalBox("清除成功！");
							setTimeout("location.reload()",1000);
						}else{
							showModalBox(data.data);
						}
					}
				});
			}
		}

		function getIds(){
			var arr = new Array();
			$("[name='eids']:checked").each(function(){
				arr.push($(this).val());
			})
			var str = arr.join(",");
			if(str==''){
				alert("请选择删除对象！");
				return;
			}
			del(str);
		}

		$(document).ready(function () {
			$("#checkAll").bind("click",function(){
				if($(this).attr('checked') == 'checked') {
					$("[name='eids']").attr("checked",'true');
				}else{
					$("[name='eids']").removeAttr("checked");
				}
			});

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
