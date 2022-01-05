<#include "/WEB-INF/macro/core.ftl">

<#macro head title="">
	<@corehead title="${title}">
		<link rel="stylesheet" type="text/css" href="${resBase}/css/base.css" />
		<link rel="stylesheet" type="text/css" href="${resBase}/css/common.css" />
		<link rel="stylesheet" type="text/css" href="${resBase}/css/index.css" />
		<script src="${resBase}/js/jquery-1.7.1.min.js"></script>
		<script src="${resBase}/js/jquery.simple.modal.js"></script>
		<script src="${resBase}/js/jquery.form.validate.js"></script>
		<script src="${resBase}/js/common-functions.js"></script>
		<script src="${resBase}/js/jquery.form.js"></script>
	</@corehead>
	<#nested>
</#macro>

<#-- 分页  -->
<#macro pageContext>
	<div class="pagenav fr">
		<ul class="fl">
			<#if pageInfo.no<=1>
				<li><a href="javascript:" class="disable">首&nbsp;页</a></li>
                <li><a href="javascript:" class="disable">上一页</a></li> 
				<#if pageInfo.totalPage <= pageInfo.no>
					<li><a href="javascript:" class="disable">下一页</a></li>
					<li><a href="javascript:" class="disable">末&nbsp;页</a></li>
				<#else>
					<li><a href="javascript:goToPage(${pageInfo.no+1});">下一页</a></li>
					<li><a href="javascript:goToPage(${pageInfo.totalPage});">末&nbsp;页</a></li>
				</#if>
			<#elseif pageInfo.totalPage <= pageInfo.no>
				<li><a href="javascript:goToPage(1);">首&nbsp;页</a></li>
                <li><a href="javascript:goToPage(${pageInfo.no-1});">上一页</a></li> 
				<li><a href="javascript:" class="disable">下一页</a></li>
				<li><a href="javascript:" class="disable">末&nbsp;页</a></li>
			<#else>
				<li><a href="javascript:goToPage(1);">首&nbsp;页</a></li>
                <li><a href="javascript:goToPage(${pageInfo.no-1});">上一页</a></li> 
				<li><a href="javascript:goToPage(${pageInfo.no+1});">下一页</a></li>
				<li><a href="javascript:goToPage(${pageInfo.totalPage});">末&nbsp;页</a></li>
			</#if>
		</ul>
		第&nbsp;${pageInfo.no}&nbsp;页/共&nbsp;${pageInfo.totalPage}&nbsp;页&nbsp;&nbsp;共&nbsp;${(pageInfo.totalRecord)!}&nbsp;条&nbsp;
		<input type="text" style="width:40px" id="pageinput" value="${pageInfo.no}" onfocus="javascript:this.select();" />
		<input type="button" value="转到" class="butn" onclick="javascript:goToPage(document.getElementById('pageinput').value)" />
	</div>
	<@script>
		function goToPage(page) {
			if(page<1||page>${pageInfo.totalPage}){
				alert("请输入正确的页码");
				return;
			}
			if(isNaN(page)){
				alert("请输入正确的页码");
				return;
			}
			$("form").append("<input type=\"hidden\" name=\"no\" value=\"" + page + "\"/>");
			$("form").submit();
			//window.location.href='${pageInfo.urlWithoutPage!''}no='+page;;
		}
	</@script>
</#macro>

<#macro frameFoot>
	<div class="footer">
		<a href="http://www.szpretv.com" target="_blank">深圳普瑞尔智能系统有限公司</a><br/>
        版权所有<br/>
		系统版本 v${SYS_VERSION}
	</div>
</#macro>

<#macro frame path="" menu="" title="" location="">
<@html>
	<@head title="${title}" />
	<#assign currentMenu = "" />
	<#assign currentPath = "" />
	<body>
	<#--头部 开始-->
	<div class="header">
    	<div class="logo fl"><a href="${sysBase}"></a></div>
        <div class="top_nav fr">
        	<div class="align_right">欢迎您，${(self.realname)!''}&nbsp;&nbsp;${(self.username)!''}&nbsp;&nbsp;&nbsp;&nbsp;</div>
            <div class="space_20 align_right">
            <a href="${sysBase}"><b>系统首页</b></a>&nbsp;
            <a id="bindingWx" style="display:none;" href="javascript:"><b>绑定公众号</b>&nbsp;</a>
            <#if self.username = 'develop'>
            	<a href="${sysBase}/check-file.html"><b>文件检测</b></a>&nbsp;
            </#if>
            <a href="${sysBase}/pwd.html"><b>修改密码</b></a>&nbsp;
            <a href="${sysBase}/logout.html"><b>安全退出</b><span class="rx">&nbsp;</span></a>
            </div>
        </div>
    </div>
	<#--头部 结束-->
	<#--主区域 开始-->
	<#--边栏 开始-->
	<div class="sidebar">
		<ul id="nav">
			<#list self.menus as m>
				<li>
					<a class="<#if m.code = menu><#assign currentMenu = m.name />expanded<#else>collapsed</#if> heading">
						<img class="nicon" src="${resBase}/images/icon/${m.code}.png" style="height:40px;" />&nbsp;
						${m.name}
					</a>

					<ul class="navigation">
						<#list m.paths as p>
						<li><a href="${sysBase}/${p.code}.html" <#if p.code = path><#assign currentPath = p.name />class="selected"><#else>>-</#if> ${p.name}</a></li>
						</#list>
					 </ul>
				</li>
			</#list>
		</ul>
		<@frameFoot />
	</div>
    	<#--边栏 结束-->
    	<#--内容 开始-->
    	<div class="content">
            <div class="cont">
    			<#nested>
    		</div>
	    </div>
    	<#--内容 结束-->
	<#--主区域 结束-->
	
	<div id="overlay"></div>
	<div id="win"><img src='${resBase}/images/loading.gif'/> 正在上传，请稍等！</div>
	
	<@actionErrorMsg />
	<@actionSuccessMsg />
	<@actionWxMsg />
	</body>
</@html>
</#macro>

<#macro listFrame path="" menu="" title="" location="" ispage=true>
<@frame path="${path}" menu="${menu}" title="${title}" location="${location}">
	<div class="table">
		<#nested>
		<#if ispage>
		<div class="padding_6">
              <@pageContext />
        </div>
        </#if>
	</div>
</@frame>
</#macro>

<#macro actionWxMsg>
<#if self?exists>
	<@script>
		var ua = navigator.userAgent.toLowerCase();

	    var isWeixin = ua.indexOf('micromessenger') != -1;
	
	    if (!isWeixin) {
	    	$("#bindingWx").hide();
	    } else {
	    	var url = "${sysBase}/bind-openid.html";
	    	$('#bindingWx').attr("href","http://cloud.szpretv.com/url-wx-enter?url=" + url);
	     	$("#bindingWx").show();
	    }
	    
	    function showUl(e){
	        var ul = e.parentNode.getElementsByTagName("ul")[0];
            
	        var uls = document.getElementsByTagName("ul");
	        for(var i=0;i<uls.length;i++){
	            var oul = uls[i];
	            if(oul !== ul && oul.getAttribute("data-name") == "popUl"){
	                oul.className="ulDef";
	            }
	        }
	        
	        if(ul.className== "ulDef ulShow"){
	            ul.className="ulDef";
	        }else{
	            ul.className = "ulDef ulShow";
	        }
	    }
	    
	    function showWin(msg){
			if(msg!=''){
				$("#win").html("<img src='${resBase}/images/loading.gif'/>" + msg);
			}else{
				$("#overlay").show();
				$("#win").show();
			}
		}
		
		function hideWin(){
			$("#overlay").hide();
			$("#win").hide();
		}
	    
	    document.addEventListener("click", function(e){
		     // 判断被点击的元素
		     if( e.target.getAttribute("data-tag") != "noHide" ){
		     	var uls = document.getElementsByTagName("ul");
		        for(var i=0;i<uls.length;i++){
                    var ul = uls[i];
                    if(ul.getAttribute("data-name") == "popUl"){
                        ul.setAttribute("class","ulDef");
                    }
		           
		        }
		     }
		});
	</@script>
</#if>
</#macro>

<#macro actionErrorMsg>
<#if message?exists>
	<@script>
	$(document).ready(function(){
		var err='${message!}';
		if(err!=''){
			showModalBox(err);
		}
	});
	</@script>
</#if>
</#macro>

<#macro actionSuccessMsg>
<#if successMsg?exists>
	<@script>
	$(document).ready(function(){
		showModalBox('${successMsg}');
	});
	</@script>
</#if>
</#macro>