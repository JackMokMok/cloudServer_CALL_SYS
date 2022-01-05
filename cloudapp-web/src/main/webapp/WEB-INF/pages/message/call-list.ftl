<#include "/WEB-INF/macro/frame.ftl">
<@listFrame path="customer/customer-list" menu="customer" title="呼叫消息">
	<div class="tit">
		<h3 class="fl">呼叫消息（${customer.name}）</h3>
		<div class="clear"></div>
	</div>
    <form action="" method="post">
    	<input type="hidden" name="customerId" value="${bean.customerId}"/>
		<div class="search">
			<label class="fl padding_6">
				<span class="title">呼叫编码：</span><input type="text" name="code" value="${(bean.code)!''}" style="width:140px;" />&nbsp;&nbsp;&nbsp;&nbsp;
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
	<div class="fl">
		<#if (self.isPathAvailable("message/finish-call"))>
			<div class="p_b_nav fl">
				<span class="p_b_left"></span><a href="javascript:finish('',${bean.customerId})">完成所有呼叫</a><span class="p_b_right"></span>
			</div>
		</#if>
		
		<#if (self.isPathAvailable("message/empty-call"))>
			<div class="p_b_nav fl">
				<span class="p_b_left"></span><a href="javascript:empty();" >清空所有呼叫</a><span class="p_b_right"></span>
			</div>
		</#if>
	</div>
	
	<table border="0" cellspacing="0" cellpadding="0">
		<tr>
			<th>呼叫编码</th>
			<th>呼叫内容</th>
			<th>呼叫时间</th>
			<th>完成时间</th>
			<th>状态</th>
            <th class="align_center">操作</th>
		</tr>
		<#list callList as c>
			<tr style="<#if (c.status==0)>color:#CD5C5C;</#if>">
                <td>${(c.code)!}</td>
                <td>${(c.content)!}</td>
				<td>${(c.callTime?string("yyyy-MM-dd HH:mm:ss"))!'-'}</td>
                <td>${(c.finishTime?string("yyyy-MM-dd HH:mm:ss"))!'-'}</td>
                 <td>${(c.statusName)!}</td>
                <td class="align_center">
                	<#if (c.status==0)>
                		<button onclick="finish(${c.id},'');">完成请求</button>
                	<#else>
                		-
                	</#if>
                	
                </td>
			</tr>
		</#list>
	</table>
	
	<@script>
		function finish(id,customerId){
			$.ajax({
	        	type: "POST", 
	            url: "finish-call.json",
	            data: {id:id,customerId:customerId},
	            dataType: "json",
	            success: function(data){
	             	if(data.code == 200){
	             		showModalBox("成功！");
	             		setTimeout("location.reload()",1000);
	             	}else{
	             		showModalBox(data.data);
	             	}
	             }
         	 	});
		}
		
		function empty() {
			if(confirm("确定要清空?")){
			 	$.ajax({
	             type: "POST", 
	             url: "empty-call.json",
	             data: {customerId:${bean.customerId}},
	             dataType: "json",
	             success: function(data){
	             	if(data.code == 200){
	             		showModalBox("成功！");
	             		setTimeout("location.reload()",1000);
	             	}else{
	             		showModalBox(data.data);
	             	}
	             }
         	 	});
			}
        }
        
        $(document).ready(function () {
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
