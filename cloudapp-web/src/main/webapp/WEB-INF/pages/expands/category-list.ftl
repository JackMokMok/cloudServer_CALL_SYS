<#include "/WEB-INF/macro/frame.ftl">
<@listFrame path="expands/category-list" menu="expands" title="设备类型">
	<div class="tit">
         <h3 class="fl">
         	设备类型
        </h3>
         <div class="clear"></div>
    </div>
	<form action="" method="post">
		<div class="search">
			<label class="fl padding_6">
				<span class="title">类型名称：</span>
				<input type="text" name="name" value="${(bean.name)!''}" style="width:110px;" />&nbsp;&nbsp;&nbsp;&nbsp;
			</label>
			
			<label class="fl padding_6">
				<span class="title">类型编码：</span>
				<input type="text" name="code" value="${(bean.code)!''}" style="width:110px;" />&nbsp;&nbsp;&nbsp;&nbsp;
			</label>
			
			<div class="clear"></div>
		</div>
		<div class="search_butn"><input type="submit" value="搜 索" class="butn" /></div>
		<div class="clear space_10"></div>
	</form>
    <div class="fl" style='height:30px;'>
        <div class="p_b_nav fl">
            <span class="p_b_left"></span>
                <a href="edit-category.html">新增类型</a>
            <span class="p_b_right"></span>
        </div>
    </div>
	<table border="0" cellspacing="0" cellpadding="0">
		<tr>
			<th>类型名称</th>
			<th>类型编码</th>
			<th>默认配置</th>
            <th width="120px" class="align_center">操作</th>
		</tr>
		<#list categoryList as d>
			<tr>
				<td>${(d.name)!}</td>
				<td>${(d.code)!}</td>
				<td>${(d.defaultConf)!}</td>
				<td class="align_center">
					<a href="edit-category.html?id=${d.id}" class="edit" title="编辑">&nbsp;</a>
					<a href="javascript:del('${d.id}');" class="del" title="删除">&nbsp;</a>
				</td>
			</tr>
		</#list>
	</table>
</@listFrame>
<@script>
		function del(id){
			if(confirm("确定要删除吗?")){
			 	$.ajax({
	             type: "POST", 
	             url: "del-category.json",
	             data: {id:id},
	             dataType: "json",
	             success: function(data){
	             	if(data.code == 200){
	             		showModalBox("删除成功！");
	             		setTimeout("location.reload()",1000);
	             	}else{
	             		showModalBox(data.msg);
	             	}
	             }
         	 	});
			}
		}
</@script>