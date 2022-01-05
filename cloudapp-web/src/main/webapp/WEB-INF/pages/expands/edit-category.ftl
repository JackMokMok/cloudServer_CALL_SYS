<#include "/WEB-INF/macro/frame.ftl">
<@frame path="expands/category-list" menu="expands" title="类型编辑">
	<dl class="viewbox">
		<dt class="tit">
            <h3 class="fl"><#if (category.id)??>修改<#else>新建</#if>类型</h3>
            <div class="clear"></div>
        </dt>
        <dd>
		<form action="save-category.html" method="post" class="validate">
			<#if (category.id)??>
				<input name="id" type="hidden" value="${category.id}" />
			</#if>
			
			<table>
				<tr>
                   	<th width="80px"><span class="red">*</span><strong>类型名称：</strong></th>
                   	<td>
                  		<input name="name" type="text" value="${(category.name)!}" onKeyDown="if(this.value.length >= 11 && (event.keyCode != 8)){ return false }" validate="required" data-requiredMessage="请输入类型名称" />
                    </td>
                </tr>
                
				<tr>
                   	<th><span class="red">*</span> <strong>类型编码：</strong></th>
                    <td>
                    	<input name="code" type="text" value="${(category.code)!}" onKeyDown="if(this.value.length >= 35 && (event.keyCode != 8)){ return false }" validate="required" data-requiredMessage="请输入类型编码" />
                    </td>
                </tr>
                
                <tr>
                   	<th><span class="red">*</span> <strong>默认配置：</strong></th>
                   	<td>
                		<textarea name="defaultConf" onKeyDown="if(this.value.length >= 511 && (event.keyCode != 8)){ return false }" style="width:300px;height:100px;" validate="required" data-requiredMessage="请输入默认配置">${(category.defaultConf)!''}</textarea>
                    </td>
                </tr>
			</table>
			
			<div>
				<input type="submit" value="提 交" class="butn" />
				<input type="button" value="返 回" class="butn" onclick="javascript:window.history.go(-1);" />
			</div>
		</form>
		</dd>
	</dl>
</@frame>
