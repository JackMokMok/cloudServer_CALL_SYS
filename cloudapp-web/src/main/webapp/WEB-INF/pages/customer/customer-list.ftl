<#include "/WEB-INF/macro/frame.ftl">
<@listFrame path="customer/customer-list" menu="customer" title="场所管理">
	<div class="tit">
         <h3 class="fl">场所管理</h3>
         <div class="clear"></div>
    </div>
    <form action="" method="post">
		<div class="search">
			<label class="fl padding_6">
				<span class="title">场所id：</span><input type="text" maxlength="11" oninput="value=value.replace(/[^\d]/g,'')" name="id" value="${(bean.id)!''}" style="width:70px;" />&nbsp;&nbsp;&nbsp;&nbsp;
			</label>
			
			<label class="fl padding_6">
				<span class="title">场所名称：</span><input type="text" name="name" value="${(bean.name)!''}" style="width:140px;" />&nbsp;&nbsp;&nbsp;&nbsp;
			</label>
			
			<label class="fl padding_6">
				<span class="title">授权到期：</span><input type="text" name="authDate" value="${(bean.authDate?string("yyyy-MM-dd"))!}" style="width:90px;height:14px;" readonly="readonly" onClick="WdatePicker()" class="Wdate" />&nbsp;&nbsp;&nbsp;&nbsp;
			</label>

            <label class="fl padding_6"><span class="title">离线天数：</span>
                <select name="offDay">
                    <option value="">请选择</option>
                    <#list offDayList as odl>
                        <option value="${odl.code}" <#if (bean.offDay)?? && bean.offDay = odl.code>selected</#if>>${odl.name}</option>
                    </#list>
                </select>&nbsp;&nbsp;&nbsp;&nbsp;
            </label>

			<label class="fl padding_6"><span class="title">排序规则：</span>
				<select name="sortingRules">
					<option value="">请选择</option>
					<#list sortingRulesList as s>
						<option value="${s.code}" <#if (bean.sortingRules)?? && bean.sortingRules = s.code>selected</#if>>${s.name}</option>
					</#list>
				</select>&nbsp;&nbsp;&nbsp;&nbsp;
			</label>
			<div class="clear"></div>
		</div>
		<div class="search_butn"><input type="submit" value="搜 索" class="butn" /></div>
		<div class="clear space_10"></div>
	</form>
	
	<#if (self.isPathAvailable("customer/edit-customer"))>
		<div class="p_b_nav fl">
			<span class="p_b_left"></span><a href="edit-customer.html">新增场所</a><span class="p_b_right"></span>
		</div>
	</#if>
	
	<#if (self.isPathAvailable("customer/edit-auths"))>
		<div class="p_b_nav fl">
			<span class="p_b_left"></span><a href="edit-auths.html">批量延期</a><span class="p_b_right"></span>
		</div>
	</#if>

    <#if (self.isPathAvailable("customer/refresh-customer-list"))>
        <div class="p_b_nav fl">
            <span class="p_b_left"></span><a href="refresh-customer-list.html">刷新离线天数</a><span class="p_b_right"></span>
        </div>
    </#if>
	
	<table border="0" cellspacing="0" cellpadding="0">
		<tr>
			<th width="50px">id</th>
			<th>场所名称</th>
			<th>授权点数</th>
			<th>授权到期</th>
			<th>离线天数</th>
            <th class="align_center" style="width:160px">操作</th>
		</tr>
		<#list customerList as t>
			<tr style="<#if (t.diffDay<=0)>color:#FF0000;<#elseif (t.diffDay >0) && (t.diffDay <10)>color:#FFA500;<#else><#if (t.noneHeartDay)?? && (t.noneHeartDay >= 5)>color:#8799a3;</#if></#if>">
                <td>${(t.id)!}</td>
                <td>${(t.name)!}</td>
                <td>${(t.authCount)!}</td>
                <td>${(t.authDate?string("yyyy-MM-dd"))!}</td>
				<td>${(t.noneHeartDay)!}</td>
                <td class="align_center editTd">
                	<button data-tag="noHide" onclick="showUl(this);">操作</button>
					<div data-tag="noHide" class="popDiv">
	                    <ul class="ulDef" data-name="popUl">
	                    	<#if (self.isPathAvailable("customer/edit-customer"))>
								<li><a class="itema" data-tag="noHide" href="edit-customer.html?id=${t.id}">编辑</a></li>
	                        </#if>
							
							<#if (self.isPathAvailable("customer/del-customer"))>
								<li><a class="itema" data-tag="noHide" href="javascript:del(${t.id})">删除</a></li>
	                        </#if>

							<#if (self.isPathAvailable("host/caller-host-list"))>
								<li><a target="_blank" class="itema" data-tag="noHide" href="../host/caller-host-list.html?customerId=${t.id}">呼叫主机</a></li>
							</#if>

							<#if (self.isPathAvailable("caller/caller-list"))>
								<li><a target="_blank" class="itema" data-tag="noHide" href="../caller/caller-list.html?customerId=${t.id}">呼叫器</a></li>
							</#if>

							<#if (self.isPathAvailable("message/call-list"))>
								<li><a target="_blank" class="itema" data-tag="noHide" href="../message/call-list.html?customerId=${t.id}">呼叫信息</a></li>
							</#if>

							<#if (self.isPathAvailable("expand/expand-list"))>
								<li><a target="_blank" class="itema" data-tag="noHide" href="../expand/expand-list.html?customerId=${t.id}">拓展设备</a></li>
							</#if>
	                    </ul>
	                </div>
                </td>
			</tr>
		</#list>
	</table>
	
	<@datetimejs />
	
	<@script>
		function del(id){
			if(confirm("是否要删除该场所?")){
			 	$.ajax({
	             type: "POST", 
	             url: "del-customer.json",
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
