<#include "/WEB-INF/macro/frame.ftl">
<@listFrame path="admin/role-list" menu="admin" title="角色列表">
	<div class="tit">
		<h3 class="fl">角色列表</h3>
        <div class="clear"></div>
	</div>
	<form action="" method="post">
		<div class="clear space_10"></div>
	</form>
    <div class="p_b_nav fl"><span class="p_b_left"></span><a href="edit-role.html">新增角色</a><span class="p_b_right"></span></div>
	<table border="0" cellspacing="0" cellpadding="0">
		<tr>
			<th>角色id</th>
			<th>角色名称</th>
			<th class="align_center" style="width:100px">操作</th>
		</tr>
		<#list roleList as r>
			<tr>
				<td>${r.id!}</td>
				<td>${r.name!}</td>
				<td class="align_center">
					<a href="edit-role.html?id=${r.id}" class="edit" title="编辑">&nbsp;</a>
					<a href="javascript:;" onclick="del('${r.id}')" class="del" title="删除">&nbsp;</a>
				</td>
			</tr>
		</#list>
	</table>
	<@script>
		function del(id){
			if(confirm("确定要删除吗?")){
			 	$.ajax({
	             type: "POST", 
	             url: "del-role.json",
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
</@listFrame>
