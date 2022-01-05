<#include "/WEB-INF/macro/frame.ftl">
<@listFrame path="customer/customer-list" menu="customer" title="呼叫主机">
	<div class="tit">
         <h3 class="fl">呼叫主机（${customer.name}）</h3>
         <div class="clear"></div>
    </div>
    <form action="" method="post">
		<div class="search">
			<label class="fl padding_6">
				<span class="title">MAC地址：</span><input type="text" name="mac" value="${(bean.mac)!''}" style="width:140px;" />&nbsp;&nbsp;&nbsp;&nbsp;
			</label>
			
			<label class="fl padding_6">
				<span class="title">呼叫器主机名称：</span><input type="text" name="name" value="${(bean.name)!''}" style="width:140px;" />
				&nbsp;&nbsp;&nbsp;&nbsp;
			</label>
			
			<div class="clear"></div>
		</div>
		<div class="search_butn"><input type="submit" value="搜 索" class="butn" /></div>
		<div class="clear space_10"></div>
	</form>

	<div class="fl">
		<#if (self.isPathAvailable("host/edit-caller-host"))>
			<div class="p_b_nav fl">
				<span class="p_b_left"></span><a href="edit-caller-host.html?customerId=${bean.customerId}">新增呼叫主机</a><span class="p_b_right"></span>
			</div>
		</#if>
	</div>
	
	<table border="0" cellspacing="0" cellpadding="0">
		<tr>
			<th>呼叫主机MAC</th>
			<th>呼叫主机名称</th>
			<th>是否在线</th>
            <th class="align_center" style="width:160px">操作</th>
		</tr>
		<#list callerHostList as t>
			<tr>
                <td>${(t.mac)!}</td>
                <td>${(t.name)!}</td>
                <td>${(t.onlineName)!}</td>
                <td class="align_center">
                	<a href="edit-caller-host.html?id=${t.id}" class="view-edit" title="编辑">&nbsp;</a>
					<a href="javascript:del('${t.id}');" class="view-del"  title="删除">&nbsp;</a>
                </td>
			</tr>
		</#list>
	</table>
	
	<@script>
		function del(id){
			if(confirm("是否要删除该呼叫主机?")){
			 	$.ajax({
	             type: "POST", 
	             url: "del-caller-host.json",
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
