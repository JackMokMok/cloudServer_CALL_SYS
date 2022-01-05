<#include "/WEB-INF/macro/frame.ftl">
<@listFrame path="callers/all-caller-host-list" menu="callers" title="呼叫主机列表">
	<div class="tit">
         <h3 class="fl">呼叫主机列表</h3>
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

			<label class="fl padding_6"><span class="title">是否在线：</span>
				<select name="online">
					<option value="">请选择</option>
					<#list onlineList as o>
						<option value="${o.code}" <#if (bean.online)?? && bean.online = o.code>selected</#if>>${o.name}</option>
					</#list>
				</select>&nbsp;&nbsp;&nbsp;&nbsp;
			</label>
			
			<div class="clear"></div>
		</div>
		<div class="search_butn"><input type="submit" value="搜 索" class="butn" /></div>
		<div class="clear space_10"></div>
	</form>
	
	<table border="0" cellspacing="0" cellpadding="0">
		<tr>
			<th>场所名称</th>
			<th>呼叫主机MAC</th>
			<th>呼叫主机名称</th>
			<th>是否在线</th>
            <th class="align_center" style="width:160px">操作</th>
		</tr>
		<#list callerHostList as t>
			<tr>
				<td>${(t.customerName)!}</td>
                <td>${(t.mac)!}</td>
                <td>${(t.name)!}</td>
                <td>${(t.onlineName)!}</td>
				<td class="align_center editTd">
					<button data-tag="noHide" onclick="showUl(this);">操作</button>
					<div data-tag="noHide" class="popDiv">
						<ul class="ulDef" data-name="popUl">
							<#if (self.isPathAvailable("host/del-caller-host"))>
								<li><a class="itema" data-tag="noHide" href="javascript:del(${t.id})">删除</a></li>
							</#if>
						</ul>
					</div>
				</td>
			</tr>
		</#list>
	</table>
	
	<@script>
		function del(id){
			if(confirm("是否要删除该呼叫主机?")){
			 	$.ajax({
	             type: "POST", 
	             url: "${sysBase}/host/del-caller-host.json",
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
