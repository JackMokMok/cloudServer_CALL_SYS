<#include "/WEB-INF/macro/frame.ftl">
<@frame path="customer/customer-list" menu="customer" title="呼叫主机编辑">
	<dl class="viewbox">
		<dt class="tit">
            <h3 class="fl"><#if (callerHost.id)??>修改<#else>新增</#if>呼叫主机</h3>
            <div class="clear"></div>
        </dt>
        <dd>
		<form action="/host/save-caller-host.html" method="post" class="validate">
			<#if (callerHost.id)??>
				<input name="id" type="hidden" value="${callerHost.id}" />
			</#if>
			<input name="customerId" type="hidden" value="${callerHost.customerId}" />
			
			<table>
				<tr>
                   	<th width="100"><span class="red">*</span> <strong>MAC地址：</strong></th>
                    <td>
                    	<input name="mac" type="text" value="${(callerHost.mac)!}" onKeyDown="if(this.value.length >= 63 && (event.keyCode != 8)){ return false }" validate="required" data-requiredMessage="请输入MAC" />
                    </td>
                </tr>

                <tr>
                   	<th><span class="red">*</span> <strong>呼叫主机名称：</strong></th>
                    <td>
                    	<input name="name" type="text" value="${(callerHost.name)!}" onKeyDown="if(this.value.length >= 47 && (event.keyCode != 8)){ return false }" validate="required" data-requiredMessage="请输入呼叫主机名称" />
                    </td>
                </tr>
                
			</table>
			<div><input type="submit" value="提 交" class="butn" /><input type="button" value="返 回" class="butn" onclick="javascript:window.history.go(-1);" /></div>
		</form>
		</dd>
	</dl>
	<@script>

	</@script>
</@frame>
