<#include "/WEB-INF/macro/frame.ftl">
<@listFrame path="admin/admin-list" menu="admin" title="账号列表">
	<div class="tit">
         <h3 class="fl">账号列表</h3>
         <div class="clear"></div>
    </div>
    <form action="admin-list.html" method="post">
		<div class="search">
			<label class="fl padding_6"><span class="title">真实姓名：</span><input type="text" name="realname" value="${(bean.realname)!''}" style="width:70px;" />&nbsp;&nbsp;&nbsp;&nbsp;</label>
			<label class="fl padding_6"><span class="title">账号：</span><input type="text" name="username" value="${(bean.username)!''}" style="width:110px;" />&nbsp;&nbsp;&nbsp;&nbsp;</label>
			<label class="fl padding_6"><span class="title">角色：</span>
				<select name="roleId">
	                <option value="">请选择</option>
	            	<#list roleList as r>
	            		<option value="${r.id}" <#if (bean.roleId)?? && bean.roleId = r.id>selected</#if>>${r.name}</option>
					</#list>
				</select>&nbsp;&nbsp;&nbsp;&nbsp;
			</label>
			
			<label class="fl padding_6"><span class="title">状态：</span>
                <select name="status">
                	<option value="">请选择</option>
	            	<#list statusList as s>
	            		<option value="${s.code}" <#if (bean.status)?? && bean.status = s.code>selected</#if>>${s.name}</option>
					</#list>
				</select>&nbsp;&nbsp;&nbsp;&nbsp;
            </label>
			<div class="clear"></div>
		</div>
		<div class="search_butn"><input type="submit" value="搜 索" class="butn" /></div>
		<div class="clear space_10"></div>
	</form>
	<div class="p_b_nav fl">
		<span class="p_b_left"></span><a href="edit-admin.html">新增账号</a><span class="p_b_right"></span>
	</div>
	
	<table border="0" cellspacing="0" cellpadding="0">
		<tr>
			<th>账号</th>
			<th>角色</th>
            <th>真实姓名</th>
			<th>状态</th>
            <th class="align_center" style="width:160px">操作</th>
		</tr>
		<#list adminList as a>
            <#if (a.username != 'develop')>
			<tr>
				<td>${(a.username)!}</td>
                <td>${(a.roleName)!}</td>
                <td>${a.realname}</td>
                <td>${a.statusName}</td>
                <td class="align_center editTd">
                	<button data-tag="noHide" onclick="showUl(this);">操作</button>
					<div data-tag="noHide" class="popDiv">
	                    <ul class="ulDef" data-name="popUl">
							<li><a class="itema" data-tag="noHide" href="edit-admin.html?id=${a.id}">编辑</a></li>
							<#if (self.isPathAvailable("admin/login-history"))>
	                        	<li><a class="itema" data-tag="noHide" href="login-history.html?adminId=${a.id}">登陆历史</a></li>
	                        </#if>
	                        <#if (self.isPathAvailable("admin/del-admin"))>
	                        	<li><a class="itema" data-tag="noHide" href="javascript:del(${a.id})">删除</a></li>
	                        </#if>
	                        
	                    </ul>
	                </div>
                </td>
			</tr>
            </#if>
		</#list>
	</table>
    <@script>
        function del(id){
			if(confirm("是否要删除该用户?")){
			 	$.ajax({
	             type: "POST", 
	             url: "del-admin.json",
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
