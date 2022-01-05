<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>请修改密码</title>
<link rel="stylesheet" type="text/css" href="${resBase}/css/base.css" />
<link rel="stylesheet" type="text/css" href="${resBase}/css/common.css" />
<link rel="stylesheet" type="text/css" href="${resBase}/css/index.css" />
<script src="${resBase}/js/jquery-1.7.1.min.js"></script>
<script src="${resBase}/js/jquery.simple.modal.js"></script>
<script src="${resBase}/js/jquery.form.validate.js"></script>
<script src="${resBase}/js/common-functions.js"></script>
<script src="${resBase}/js/jquery.form.js"></script>
</head>
<body>
	<div style="margin:0 auto;width:350px;text-align:center;">
        <form action="update-default-pwd.html" method="post" class="validate">
        <dd>
            <table>
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
    	</div>
    <script>
    	<#if message?exists>
			$(document).ready(function(){
				var err='${message!}';
				if(err!=''){
					showModalBox(err);
				}
			});
		</#if>
    	function updatePwd(){
    		var pwd = $("#pwd").val();
    		var cpwd = $("#cpwd").val();
    		if(pwd!=cpwd){
    			showModalBox('两次密码输入不一致！');
    			return;
    		}
		    $("form").submit();
		}
    </script>
   </body>
</html>