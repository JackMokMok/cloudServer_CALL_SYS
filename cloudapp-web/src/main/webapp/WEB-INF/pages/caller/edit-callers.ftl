<#include "/WEB-INF/macro/frame.ftl">
<@frame path="customer/customer-list" menu="customer" title="批量添加呼叫器">
	<dl class="viewbox">
		<dt class="tit">
            <h3 class="fl">批量添加呼叫器</h3>
            <div class="clear"></div>
        </dt>
        
		<form id="myForm" action="save-callers.json" method="post">
			<input name="customerId" type="hidden" value="${customerId}" />
			<dd>
				<table>
                    <tr>
	                    <td>
	                		<textarea id="callers" name="callers" style="width:380px;height:380px;"></textarea>
	                    </td>
	                    <td>
	                    	<span style="font-weight:bold">格式示例：</span><br/>
							<span style="font-weight:bold">呼叫主机MAC-呼叫器编码-类型-位置号-对接设备的MAC地址（可省略）</span><br/>
	                    	<span>
								9CA525F44690-36DF38-包房-A109<br/>
	                    		9CA525F44690-8D4DD8-休息厅-A110-90:4E:20:00:03:C7
	                    	</span>
	                    	<br/><br/>
	                    	<span style="font-weight:bold">注意事项：</span><br/>
	                    	<span>
	                    		1、未在系统中（当前场所）添加的呼叫主机MAC地址无法添加<br/>
								2、未在系统中（当前场所）添加的类型无法添加
	                    	</span>
	                    </td>
	                </tr>
				</table>
			</dd>
			
			<div>
				<input type="button" value="提 交" onclick="addCallers()" class="butn" />
				<input type="button" value="返回" class="butn" onclick="javascript:window.history.go(-1);"/>
			</div>
		</form>
	</dl>
	
	<@script>
		function addCallers() {
			if($("#callers").val() == ""){
				return;
			}
			$.ajax({
	       		type: "POST", 
	            url: "save-callers.json",
	            data: $('#myForm').serialize(),
	            dataType: "json",
	            success: function(data){
	            	if(data.code == 200){
	             		showModalBox(data.msg);
	             	}else{
	             		showModalBox(data.msg);
	             	}
	            }
         	 });
        }
	</@script>
</@frame>