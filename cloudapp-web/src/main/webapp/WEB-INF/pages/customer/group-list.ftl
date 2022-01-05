<#include "/WEB-INF/macro/frame.ftl">
<@listFrame path="customer/group-list" menu="customer" title="场所分组">
	<div class="tit">
         <h3 class="fl">场所分组</h3>
         <div class="clear"></div>
    </div>
    <form action="" method="post">
		<div class="search">
			<label class="fl padding_6">
				<span class="title">分组名称：</span><input type="text" name="name" value="${(bean.name)!''}" style="width:140px;" />&nbsp;&nbsp;&nbsp;&nbsp;
			</label>
			<div class="clear"></div>
		</div>
		<div class="search_butn"><input type="submit" value="搜 索" class="butn" /></div>
		<div class="clear space_10"></div>
	</form>
	
	<#if (self.isPathAvailable("customer/edit-group"))>
		<div class="p_b_nav fl">
			<span class="p_b_left"></span><a href="edit-group.html">新增分组</a><span class="p_b_right"></span>
		</div>
	</#if>
	
	<table border="0" cellspacing="0" cellpadding="0">
		<tr>
			<th>分组名称</th>
            <th class="align_center" style="width:160px">操作</th>
		</tr>
		<#list groupList as g>
			<tr>
                <td>${(g.name)!}</td>
                <td class="align_center editTd">
                	<button data-tag="noHide" onclick="showUl(this);">操作</button>
					<div data-tag="noHide" class="popDiv">
	                    <ul class="ulDef" data-name="popUl">
	                    	<#if (self.isPathAvailable("customer/edit-group"))>
								<li><a class="itema" data-tag="noHide" href="edit-group.html?id=${g.id}">编辑</a></li>
	                        </#if>
	                        
	                        <#if (self.isPathAvailable("customer/del-group"))>
	                        	<li><a class="itema" data-tag="noHide" href="javascript:del(${g.id})">删除</a></li>
	                        </#if>
	                    </ul>
	                </div>
                </td>
			</tr>
		</#list>
	</table>
	<@script>
		function del(id){
			if(confirm("是否要删除该分组?")){
			 	$.ajax({
	             type: "POST", 
	             url: "del-group.json",
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
