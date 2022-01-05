<#include "/WEB-INF/macro/frame.ftl">
<@frame path="customer/customer-list" menu="customer" title="呼叫器编辑">
	<dl class="viewbox">
		<dt class="tit">
            <h3 class="fl"><#if (caller.id)??>修改<#else>新增</#if>呼叫器</h3>
            <div class="clear"></div>
        </dt>
        <dd>
		<form action="save-caller.html" method="post" class="validate">
			<#if (caller.id)??>
				<input name="id" type="hidden" value="${caller.id}" />
			</#if>
			<input name="customerId" type="hidden" value="${(caller.customerId)!}" />
			
			<table>
				<tr>
					<th width="100px"><span class="red">*</span> <strong>呼叫器分组：</strong></th>
					<td>
						<select name="groupId" validate="required" data-requiredMessage="请选择分组">
							<option value="">请选择分组</option>
							<#list groupList as g>
								<option value="${g.id}" <#if (caller.groupId)?? && caller.groupId = g.id>selected</#if>>${g.name}</option>
							</#list>
						</select>
					</td>
				</tr>

				<tr>
					<th><span class="red">*</span> <strong>呼叫器类型：</strong></th>
					<td>
						<select name="categoryId" validate="required" data-requiredMessage="请选择类型">
							<option value="">请选择类型</option>
							<#list categoryList as c>
								<option value="${c.id}" <#if (caller.categoryId)?? && caller.categoryId = c.id>selected</#if>>${c.name}</option>
							</#list>
						</select>
					</td>
				</tr>

				<tr>
					<th><span class="red">*</span> <strong>MAC地址：</strong></th>
					<td>
						<select name="mac" validate="required" data-requiredMessage="请选择MAC地址">
							<option value="">请选择MAC地址</option>
							<#list hostList as h>
								<option value="${h.mac}" <#if (caller.mac)?? && caller.mac = h.mac>selected</#if>>${h.name}(${h.mac})</option>
							</#list>
						</select>
					</td>
                </tr>

                <tr>
                   	<th><span class="red">*</span> <strong>呼叫器位置号：</strong></th>
                    <td>
                    	<input name="name" type="text" value="${(caller.name)!}" onKeyDown="if(this.value.length >= 47 && (event.keyCode != 8)){ return false }" validate="required" data-requiredMessage="请输入呼叫器位置号" />
                    </td>
                </tr>

				<tr>
					<th><span class="red">*</span> <strong>呼叫器编码：</strong></th>
					<td>
						<input name="code" type="text" value="${(caller.code)!}" onKeyDown="if(this.value.length >= 47 && (event.keyCode != 8)){ return false }" validate="required" data-requiredMessage="请输入呼叫器编码" />
					</td>
				</tr>

				<tr>
					<th><strong>对接设备的mac地址：</strong></th>
					<td>
						<input name="tvMac" type="text" value="${(caller.tvMac)!}" onKeyDown="if(this.value.length >= 47 && (event.keyCode != 8)){ return false }" />
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
