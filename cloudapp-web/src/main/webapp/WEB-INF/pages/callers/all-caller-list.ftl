<#include "/WEB-INF/macro/frame.ftl">
<@listFrame path="callers/all-caller-list" menu="callers" title="呼叫器列表">
	<div class="tit">
		<h3 class="fl">呼叫器列表</h3>
		<div class="clear"></div>
	</div>
    <form action="" method="post">
		<div class="search">
			<label class="fl padding_6">
				<span class="title">呼叫器编码：</span><input type="text" name="code" value="${(bean.code)!''}" style="width:120px;" />
				&nbsp;&nbsp;&nbsp;&nbsp;
			</label>
			
			<div class="clear"></div>
		</div>
		<div class="search_butn"><input type="submit" value="搜 索" class="butn" /></div>
		<div class="clear space_10"></div>
	</form>

	<div class="fl">
		<#if (self.isPathAvailable("callers/del-all-caller"))>
			<div class="p_b_nav fl">
				<span class="p_b_left"></span><a href="javascript:getIds();">&nbsp;删除&nbsp;</a>
				<span class="p_b_right"></span>
			</div>
		</#if>

		<#if (self.isPathAvailable("callers/edit-all-caller"))>
			<div class="p_b_nav fl">
				<span class="p_b_left"></span><a href="edit-all-caller.html">新增呼叫器</a><span class="p_b_right"></span>
			</div>
		</#if>
	</div>
	
	<table border="0" cellspacing="0" cellpadding="0">
		<tr>
			<th width="30"><input type="checkbox" id="checkAll" /></th>
			<th>呼叫器编码</th>
			<th>录入时间</th>
            <th class="align_center" style="width:160px">操作</th>
		</tr>
		<#list allCallerList as a>
			<tr>
				<td><input type="checkbox" name="eids" value="${a.id}" /></td>
                <td>${(a.code)!}</td>
				<td>${(a.checkInTime?string("yyyy-MM-dd HH:mm:ss"))!}</td>
                <td class="align_center">
                	<a href="edit-all-caller.html?id=${a.id}" class="view-edit" title="编辑">&nbsp;</a>
					<#if (self.isPathAvailable("callers/del-all-caller"))>
						<a href="javascript:del('${a.id}');" class="view-del"  title="删除">&nbsp;</a>
					</#if>
                </td>
			</tr>
		</#list>
	</table>
	
	<@script>
		function del(ids){
			if(confirm("是否要删除所选设备?")){
				$.ajax({
					type: "POST",
					url: "del-all-caller.json",
					data: {ids:ids},
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

		function getIds(){
			var arr = new Array();
			$("[name='eids']:checked").each(function(){
				arr.push($(this).val());
			})
			var str = arr.join(",");
			if(str==''){
				alert("请选择删除对象！");
				return;
			}
			del(str);
		}

		$(document).ready(function () {
			$("#checkAll").bind("click",function(){
				if($(this).attr('checked') == 'checked') {
					$("[name='eids']").attr("checked",'true');
				}else{
					$("[name='eids']").removeAttr("checked");
				}
			});

			var nav = $('nav');
			var line = $('<div/>').addClass('line');
			line.appendTo(nav);

			var active = nav.find('.active');
			var pos = 0;
			var wid = 0;

			if(active.length) {
				pos = active.position().left;
				wid = active.width();
				line.css({
					left: pos,
					width: wid
				});
			}
		});
	</@script>
</@listFrame>
