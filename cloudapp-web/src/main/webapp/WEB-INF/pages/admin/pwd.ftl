<#include "/WEB-INF/macro/frame.ftl">
<@frame path="admins" menu="admin" title="管理员编辑">
    <dl class="viewbox">
        <dt class="tit">
            <h3 class="fl">修改密码</h3>
            <div class="clear"></div>
        </dt>
        <form action="update-pwd.html" method="post" class="validate">
        <dd>
            <table>
                <tr>
                    <th style="width:100px;"><span class="red">*</span> <strong>原始密码：</strong></th>
                    <td>
						<input name="opassword" type="password" validate="required" data-requiredMessage="请输入原始密码"/>
                    </td>
                </tr>
                <tr>
                    <th><span class="red">*</span><strong>新密码：</strong></th>
                    <td>
						<input id="pwd" name="password" onKeyDown="if(this.value.length >= 15 && (event.keyCode != 8)){ return false }" type="password" validate="required" data-requiredMessage="请输入新密码"/>
                    </td>
                </tr>
                <tr>
                    <th><span class="red">*</span><strong>确认新密码：</strong></th>
                    <td>
						<input id="cpwd" type="password" onKeyDown="if(this.value.length >= 15 && (event.keyCode != 8)){ return false }" validate="required" data-requiredMessage="请输入确认密码"/>
                    </td>
                </tr>
               </table>
            </dd>
           	<div><input type="button" class="butn" value="提交" onclick="updatePwd();" />
    	</form>
    </dl>
    <@script>
    	function updatePwd(){
    		var pwd = $("#pwd").val();
    		var cpwd = $("#cpwd").val();
    		if(pwd!=cpwd){
    			showModalBox('两次密码输入不一致！');
    			return;
    		}
		    $("form").submit();
		}
    </@script>
</@frame>
