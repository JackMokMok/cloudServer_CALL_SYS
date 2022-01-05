<#macro html>
	<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
	<html xmlns="http://www.w3.org/1999/xhtml">
		<#nested>
	</html>
</#macro>

<#-- 标准脚本 -->
<#macro script>
	<script type="text/javascript">
	<!--
		<#nested>
	//-->
	</script>
</#macro>

<#-- 页面head -->
<#macro corehead title="" keywords="" description="">
	<head>
		<#-- 标题 -->
		<title>${title}</title>
		<#-- meta -->
		<meta name="keywords" content="${keywords}">
		<meta name="description" content="${description}">
		<meta http-equiv="Cache-Control" content="no-store" />
		<meta http-equiv="Pragma" content="no-cache" />
		<meta http-equiv="Expires" content="0" />
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<#nested>
	</head>
</#macro>

<#--上传插件-->
<#macro fileupload>
<script type="text/javascript" src="${resBase}/plugins/upload/ajaxfileupload.js"></script>
</#macro>

<#macro uploadify>
<script src="${resBase}/plugins/upload/uploadify/jquery.uploadify.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="${resBase}/plugins/upload/uploadify/uploadify.css">
</#macro>

<#macro plupload>
<link rel="stylesheet" href="${resBase}/plugins/upload/plupload/jquery-ui.css" type="text/css" />
<link rel="stylesheet" href="${resBase}/plugins/upload/plupload/js/jquery.ui.plupload/css/jquery.ui.plupload.css" type="text/css" />
<script type="text/javascript" src="${resBase}/plugins/upload/plupload/jquery-ui.min.js"></script>
<script type="text/javascript" src="${resBase}/plugins/upload/plupload/js/plupload.full.min.js"></script>
<script type="text/javascript" src="${resBase}/plugins/upload/plupload/js/jquery.ui.plupload/jquery.ui.plupload.js"></script>
<script type="text/javascript" src="${resBase}/plugins/upload/plupload/js/zh_CN.js"></script>
</#macro>

<#macro autocomplete>
	<link rel="stylesheet" type="text/css" href="${resBase}/css/jquery.autocomplete.css" />
	<script src="${resBase}/js/jquery.autocomplete.js"></script>
</#macro>

<#--星级评分-->
<#macro lqscore>
	<link rel="stylesheet" type="text/css" href="${resBase}/css/lq-score.css" />
	<script src="${resBase}/js/lq-score.min.js"></script>
</#macro>

<#--日历控件-->
<#macro datejs ids="">
<script type="text/javascript" src="${resBase}/plugins/datepicker/jquery.DatePicker.js"></script>
<@script>
	var ids = '${ids}'.split(",");
	for(i=0; i< ids.length;i++){
      $('#'+ids[i]).datePicker({followOffset : [0, 24]});
    }
</@script>
</#macro>

<#macro datetimejs>
<script type="text/javascript" src="${resBase}/plugins/My97DatePicker/WdatePicker.js"></script>
</#macro>

<#--编辑器-->
<#macro kindeditor>
<script charset="utf-8" src="${resBase}/plugins/kindeditor/kindeditor.js"></script>
<script charset="utf-8" src="${resBase}/plugins/kindeditor/lang/zh_CN.js"></script>
</#macro>