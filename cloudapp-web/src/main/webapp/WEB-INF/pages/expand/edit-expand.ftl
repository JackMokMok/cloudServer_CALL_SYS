<#include "/WEB-INF/macro/frame.ftl">
<@frame path="customer/customer-list" menu="customer" title="设备编辑">
	<dl class="viewbox">
		<dt class="tit">
            <h3 class="fl"><#if (expand.id)??>修改<#else>新建</#if>设备</h3>
            <div class="clear"></div>
        </dt>
        <dd>
		<form action="save-expand.html" method="post" class="validate">
			<#if (expand.id)??>
				<input name="id" type="hidden" value="${expand.id}" />
			</#if>
			<input name="customerId" type="hidden" value="${expand.customerId}" />
			
			<table>
				<tr>
                   	<th width="80"><span class="red">*</span> <strong>MAC地址：</strong></th>
                    <td>
                    	<input name="mac" type="text" value="${(expand.mac)!}" onKeyDown="if(this.value.length >= 63 && (event.keyCode != 8)){ return false }" validate="required" data-requiredMessage="请输入MAC" />
                    </td>
                </tr>
                
                <tr>
                   	<th><span class="red">*</span> <strong>类型名称：</strong></th>
                    <td>
                    	<select name="code" id="appCode" validate="required" data-requiredMessage="请选择应用">
	                    	<option value="">请选择</option>
	                		<#list categoryList as a>
	                			<option value="${a.code}" data-conf="${a.defaultConf}" <#if (expand.code)?? && expand.code = a.code>selected</#if>>${a.name}</option>
							</#list>
						</select>
                    </td>
                </tr>
                
                <tr>
                   	<th><span class="red">*</span> <strong>设备名称：</strong></th>
                    <td>
                    	<input name="name" type="text" value="${(expand.name)!}" onKeyDown="if(this.value.length >= 47 && (event.keyCode != 8)){ return false }" validate="required" data-requiredMessage="请输入设备名称" />
                    </td>
                </tr>
                
                <tr>
                   	<th><span class="red">*</span> <strong>配置：</strong></th>
                   	<td>
                		<textarea name="conf" id="conf" onKeyDown="if(this.value.length >= 1023 && (event.keyCode != 8)){ return false }" style="width:300px;height:100px;" validate="required" data-requiredMessage="请输入配置">${(expand.conf)!''}</textarea>
                    </td>
                </tr>
                
                <tr>
                   	<th><span class="red">*</span> <strong>关联分组：</strong></th>
                    <td>
                    	<select name="callerGroupId" validate="required" data-requiredMessage="请选择分组">
	                    	<option value="">请选择</option>
	                		<#list groupList as g>
	                			<option value="${g.id}" <#if (expand.callerGroupId)?? && expand.callerGroupId = g.id>selected</#if>>${g.name}</option>
							</#list>
						</select>
                    </td>
                </tr>
                
			</table>
			<div><input type="submit" value="提 交" class="butn" /><input type="button" value="返 回" class="butn" onclick="javascript:window.history.go(-1);" /></div>
		</form>
		</dd>
	</dl>
	<@script>
		$(document).ready(function () {
        	$("#appCode").on("change",function(){
				$("#conf").val($("#appCode").find("option:selected").attr("data-conf"));
			});
        });
	</@script>
</@frame>
