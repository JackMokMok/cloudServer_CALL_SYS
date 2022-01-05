<#include "/WEB-INF/macro/frame.ftl">
<@frame path="admin/admin-list" menu="admin" title="账号编辑">
    <dl class="viewbox">
        <dt class="tit">
            <h3 class="fl"><#if (admin.id)?exists>修改账号<#else>新增账号</#if></h3>
            <div class="clear"></div>
        </dt>
        <form action="save-admin.html" method="post" class="validate">
            <#if (admin.id)?exists>
				<input name="id" type="hidden" value="${admin.id}" />
			</#if>
        <dd>
            <table>
                <tr>
                    <th width="80"><span class="red">*</span> <strong>账号：</strong></th>
                    <td><input name="username" type="text" onKeyDown="if(this.value.length >= 62 && (event.keyCode != 8)){ return false }" value="${(admin.username)!}" validate="required" data-requiredMessage="请输入账号" /></td>
                </tr>
                <tr>
                    <th><span class="red">*</span> <strong>密码：</strong></th>
                    <td>
                        <#if (admin.id)?exists>
							<input name="password" onKeyDown="if(this.value.length >= 15 && (event.keyCode != 8)){ return false }" type="password" /> 如不修改请留空
						<#else>
							<input name="password" onKeyDown="if(this.value.length >= 15 && (event.keyCode != 8)){ return false }" type="password" validate="required" data-requiredMessage="请输入密码" />
						</#if>
                    </td>
                </tr>
                <tr>
                    <th><span class="red">*</span> <strong>确认密码：</strong></th>
                    <td>
                        <#if (admin.id)?exists>
							<input name="password_check" onKeyDown="if(this.value.length >= 15 && (event.keyCode != 8)){ return false }" type="password" /> 如不修改请留空
						<#else>
							<input name="password_check" onKeyDown="if(this.value.length >= 15 && (event.keyCode != 8)){ return false }" type="password" validate="required" data-requiredMessage="请输入密码" />
						</#if>
                    </td>
                </tr>
                <tr>
                   	<th><span class="red">*</span> <strong>真实姓名：</strong></th>
                    <td>
                		<input name="realname" onKeyDown="if(this.value.length >= 63 && (event.keyCode != 8)){ return false }" type="text" value="${(admin.realname)!}" validate="required" data-requiredMessage="请输入真实姓名" />
                    </td>
                </tr>
                
                
                <tr>
                   	<th><span class="red">*</span><strong>状态：</strong></th>
                    <td>
                		<#list statusList as s>
	                	<label>
	                		<input name="status" type="radio" value="${s.code}" ${(((admin.status)!10) == (s.code)?number)?string('checked','')}> ${s.name}&nbsp;&nbsp;
	                	</label>
						</#list>
                    </td>
                </tr>
                
                <tr>
                   	<th><span class="red">*</span> <strong>邮箱地址：</strong></th>
                    <td>
                		<input name="email" onKeyDown="if(this.value.length >= 127 && (event.keyCode != 8)){ return false }" type="text" value="${(admin.email)!}" validate="required" data-requiredMessage="请输入邮箱地址" />
                    </td>
                </tr>
                
                <tr>
                   	<th><span class="red">*</span> <strong>角色：</strong></th>
                    <td>
                    	<select name="roleId"  validate="required" data-requiredMessage="请选择角色">
	                    	<option value="">请选择角色</option>
	                		<#list roleList as r>
	                			<option value="${r.id}"  <#if (admin.roleId)?? && admin.roleId = r.id>selected</#if>>${r.name}</option>
							</#list>
						</select>
                    </td>
                 </tr>
               </table>
            </dd>
            	<div>
               		<input type="button" class="butn" value="提交" onclick="setRole();" />
               		<input type="button" value="返 回" class="butn" onclick="javascript:window.history.go(-1);" />
                </div>
    	</form>
    </dl>
    
	<@script>
		
		function setRole(){
            if($("input[name='password']").val() != $("input[name='password_check']").val()) {
                showModalBox("密码输入有误");
				return;
            }
		    $("form").submit();
		}
	
	</@script>
</@frame>
