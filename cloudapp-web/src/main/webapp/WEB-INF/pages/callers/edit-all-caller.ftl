<#include "/WEB-INF/macro/frame.ftl">
<@frame path="callers/all-caller-list" menu="callers" title="呼叫器编辑">
	<dl class="viewbox">
		<dt class="tit">
            <h3 class="fl"><#if (allCaller.id)??>修改<#else>新增</#if>呼叫器</h3>
            <div class="clear"></div>
        </dt>
        <dd>
		<form action="save-all-caller.html" method="post" class="validate">
			<#if (allCaller.id)??>
				<input name="id" type="hidden" value="${allCaller.id}" />
			</#if>
			<table>
				<tr>
					<th width="100px"><span class="red">*</span> <strong>呼叫器编码：</strong></th>
					<td>
						<input name="code" type="text" value="${(allCaller.code)!}" onKeyDown="if(this.value.length >= 47 && (event.keyCode != 8)){ return false }" validate="required" data-requiredMessage="请输入呼叫器编码" />
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
