<#if user??> <#else> <#assign user = ""> </#if> 
<#include "/WEB-INF/macro/frame.ftl">
<@html>
    <@head title="登录">
        <link rel="stylesheet" type="text/css" href="${resBase}/css/login.css"  />
    </@head>
    <body class="login">
        <div class="login_box">
            <div class="left_p"></div>
            <form action="login.html" method="post" class="login_form validate atips">
                <div class="logo"></div>
                <dl class="inpt">
                    <dd><input type="text" onKeyDown="if(this.value.length >= 63 && (event.keyCode != 8)){ return false }" value="${user}" tip="请输入账号" class="userName" id="username" name="username" onKeyDown="if(this.value.length >= 20 && (event.keyCode != 8)){ return false }" validate="required" data-requiredMessage="请输入账号" /></dd>
                    <dd><input type="password" onKeyDown="if(this.value.length >= 127 && (event.keyCode != 8)){ return false }" value="" tip="请输入密码" class="password" name="password" onKeyDown="if(this.value.length >= 20 && (event.keyCode != 8)){ return false }" validate="required" data-requiredMessage="请输入密码" /></dd>
                    <dd><input type="text" onKeyDown="if(this.value.length >= 4 && (event.keyCode != 8) && (event.keyCode != 13)){ return false }" value="" tip="验证码" class="verifCode fl" name="checkcode" autocomplete="off" validate="required" data-requiredMessage="请输入验证码" /><a href="javascript:void(0);" class="verifImg" id="checkcodeBtn"><img src="checkcode.html" id="checkcode" alt="看不清楚？换一张" /></a></dd>
                </dl>
                <dl class="button">
                    <dd><input type="submit" value="登 录" class="subt" /></dd>
                </dl>
            </form>
        </div>
        <div align='center'><a href="http://www.szpretv.com" target="_blank">深圳普瑞尔智能系统有限公司</a> 版权所有 <br/>系统版本：v${version}&nbsp;&nbsp;elephant</div>
    </body>
    <@script>
        (function(){
            var img=document.getElementById('checkcode'),
            link=document.getElementById('checkcodeBtn');
            var src=img.src;
            link.onclick=function(){
                img.src=src+'?s='+Math.random();
                link.blur();
            }
        })()
    </@script>
    <@actionErrorMsg />
</@html>
