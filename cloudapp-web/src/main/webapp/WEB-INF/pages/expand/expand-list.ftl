<#include "/WEB-INF/macro/frame.ftl">
<@listFrame path="customer/customer-list" menu="customer" title="扩展设备">
	<div class="tit">
         <h3 class="fl">扩展设备（${customer.name}）</h3>
         <div class="clear"></div>
    </div>
    <form action="" method="post">
		<div class="search">
			<label class="fl padding_6">
				<span class="title">MAC：</span><input type="text" name="mac" value="${(bean.mac)!''}" style="width:120px;" />&nbsp;&nbsp;&nbsp;&nbsp;
			</label>
			
			<label class="fl padding_6"><span class="title">应用类型：</span>
                <select name="code">
                	<option value="">请选择</option>
	            	<#list categoryList as c>
	            		<option value="${c.code}" <#if (bean.code)?? && bean.code = c.code>selected</#if>>${c.name}</option>
					</#list>
				</select>&nbsp;&nbsp;&nbsp;&nbsp;
            </label>
			
			<label class="fl padding_6">
				<span class="title">设备名称：</span><input type="text" name="name" value="${(bean.name)!''}" style="width:70px;" />
				&nbsp;&nbsp;&nbsp;&nbsp;
			</label>
			
			<div class="clear"></div>
		</div>
		<div class="search_butn"><input type="submit" value="搜 索" class="butn" /></div>
		<div class="clear space_10"></div>
	</form>
	
	<#if (self.isPathAvailable("expand/edit-expand"))>
		<div class="p_b_nav fl">
			<span class="p_b_left"></span><a href="edit-expand.html?customerId=${bean.customerId}">新增设备</a><span class="p_b_right"></span>
		</div>
	</#if>
	
	<table border="0" cellspacing="0" cellpadding="0">
		<tr>
			<th>MAC地址</th>
			<th>类型名称</th>
			<th>设备名称</th>
			<th>是否在线</th>
			<th>关联分组</th>
            <th class="align_center" style="width:160px">操作</th>
		</tr>
		<#list expandList as t>
			<tr>
                <td>${(t.mac)!}</td>
                <td>${(t.categoryName)!}</td>
                <td>${(t.name)!}</td>
                <td>${(t.onlineName)!}</td>
                <td>${(t.groupName)!}</td>
                <td class="align_center">
                	<a href="edit-expand.html?id=${t.id}" class="view-edit" title="编辑">&nbsp;</a>
					<a href="expand-details.html?id=${t.id}" class="details" title="详情">&nbsp;</a>
					<a href="javascript:del('${t.id}');" class="view-del"  title="删除">&nbsp;</a>
                </td>
			</tr>
		</#list>
	</table>
	
	<@script>
		function del(id){
			if(confirm("是否要删除该设备?")){
			 	$.ajax({
	             type: "POST", 
	             url: "del-expand.json",
	             data: {id:id},
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
	</@script>
</@listFrame>
