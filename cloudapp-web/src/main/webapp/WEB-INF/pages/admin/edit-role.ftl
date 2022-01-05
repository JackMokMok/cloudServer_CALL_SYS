<#include "/WEB-INF/macro/frame.ftl">
<@frame path="admin/role-list" menu="admin" title="角色编辑">
	<dl class="viewbox">
		<dt class="tit">
            <h3 class="fl">新增/修改角色</h3>
            <div class="clear"></div>
        </dt>
		<form action="save-role.html" method="post" class="validate">
			<#if (role.id)?exists>
				<input name="id" type="hidden" value="${role.id}" />
			</#if>
			<dd>
			<table>
				<tr>
                   	<th width="60"><span class="red">*</span>名称：</th>
                    <td><input name="name" type="text" id="name" onKeyDown="if(this.value.length >= 15 && (event.keyCode != 8)){ return false }" value="${(role.name)!}" validate="required" data-requiredMessage="请输入名称"/></td>
                </tr>
                <tr>
                    <th><strong><span class="red">*</span>状态：</strong></th>
                    <td>
                		<#list statusList as s>
                		<label><input name="status" type="radio" value="${s.code}" ${(((role.status)!10) == (s.code)?number)?string('checked','')}> ${s.name}&nbsp;&nbsp;</label>
                		</#list>
                    </td>
                </tr>
                <tr>
                    <th><strong>权限分配：</strong></th>
                    <td>
                    <br/>
					<legend style="color:red;">&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" onclick="checkAll(this);" />&nbsp;全选</legend>
					<#list permissionGroups as pg>
						<fieldset class="roleGroup">
							<legend><input type="checkbox" onclick="checkFieldset(this,'pg${pg_index}');" />&nbsp;${pg.name}</legend>
							<#list pg.paths as p>
								<#assign checked = false>
								<#if roleList??>
									<#list roleList as pc>
										 <#if pc.permission?? && pc.permission==p.code><#assign checked = true><#break></#if>
									</#list>
								</#if>
								<label><input type="checkbox" name="permissions" data="pg${pg_index}" value="${p.code}" <#if checked>checked</#if>/>
								&nbsp;${p.name}</label>
							</#list>
						</fieldset>
					</#list>
                    </td>
                </tr>
			</table>
			<div><input type="submit" value="提 交" class="butn"  /><input type="button" value="返 回" class="butn" onclick="javascript:window.history.go(-1);" /></div>
		</form>
	</dl>
	<@script>
		
		function checkFieldset(index,name){
			if($(index).attr('checked') == 'checked') {
			    $("[data='"+name+"']").attr("checked",'true');
            }else{
				$("[data='"+name+"']").removeAttr("checked");
            }
		}
		
		function checkAll(index){
            if($(index).attr('checked') == 'checked') {
		        $(":checkbox").attr("checked", true);
		    } else {
		        $(":checkbox").attr("checked", false);
		    }
		}
	</@script>
</@frame>
