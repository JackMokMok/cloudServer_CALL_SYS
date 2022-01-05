<#include "/WEB-INF/macro/frame.ftl">
<@frame path="customer/customer-list" menu="customer" title="批量延期">
	<dl class="viewbox">
		<dt class="tit">
            <h3 class="fl">批量延期</h3>
            <div class="clear"></div>
        </dt>
        
		<form action="save-auths.html" method="post" onsubmit="return checkForm()">
			<input type="hidden" name="cusIds" id="cusIds" />
			<table id="appTb">
            	<tr>
	            	<th width="70px"><span class="red">*</span> <strong>延期日期：</strong></th>
	            	<td>
	               		<input type="text" id="authDate" name="authDate" style="height:14px;" value="" readonly="readonly" onClick="WdatePicker()" class="Wdate"/>
	              	</td>
	       		</tr>
           		
           		<tr>
           			<th><strong>延期场所：</strong></th>
           			<td><ul id="customers" class="ztree"></ul></td>
           		</tr>
			</table>
			<div>
				<input type="submit" value="提 交" class="butn" />
				<input type="button" value="返 回" class="butn" onclick="javascript:window.history.go(-1);" />
			</div>
		</form>
	</dl>
	<link rel="stylesheet" href="${resBase}/plugins/ztree/zTreeStyle.css" type="text/css" />
	<script type="text/javascript" src="${resBase}/plugins/ztree/jquery.ztree.core-3.5.min.js"></script>
	<script type="text/javascript" src="${resBase}/plugins/ztree/jquery.ztree.excheck-3.5.min.js"></script>
	<@datetimejs />
	<@script>
		var setting = {
			check: {enable: true},
			data: {
				simpleData: {
					enable: false
				}
			},
		    async: {
		        // 开启异步加载功能
		        enable: true,
		        // 数据服务地址
		        url: "${sysBase}/api/get-customers.json",
		        // 选择父节点时会自动将节点中的参数传回服务器再重新取结果
		        autoParam: ["id","msg"]
		    },
		    callback : {
	            onCheck : onCheck
	        },
			view: {showIcon: true}
		};
		
		var zNodes =[];
		<#list groupList as gp>
			zNodes.push({id:'${gp.id}', msg:"authDate", pId:0, name:"${gp.name}", isParent:true, open:false});
		</#list>
		
		function onCheck(event, treeId, treeNode){
			var zTree = $.fn.zTree.getZTreeObj("customers");
			if(treeNode.isParent){
				if (!treeNode.open){
					zTree.expandNode(treeNode, true, true, false);
					onExpand(event, treeId, treeNode);
					setTimeout(function(){
					var children=treeNode.children;
					for(var i=0;i<children.length;i++){
						if(children[i].isParent ){
							zTree.expandNode(children[i], false, false, false);
							}
						}
					//zTree.expandNode(treeNode, false, false, false);
					},500);//延时500毫秒
				}else{
					var children=treeNode.children;
					for(var i=0;i<children.length;i++){
						if(children[i].isParent ){
							if (!children[i].open){
							zTree.expandNode(children[i], true, true, false);
							onExpand(event, treeId, children[i]);
							}
						}
					}
					setTimeout(function(){
						var children=treeNode.children;
						for(var i=0;i<children.length;i++){
							if(children[i].isParent ){
							zTree.expandNode(children[i], false, false, false);
							}
						}
					},500);//延时0.5秒
				}
			} 
		};
			    
	    function onExpand(event, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("customers");
			var checked=treeNode.checked;
			setTimeout(function(){
				var children=treeNode.children;
				for(var i=0;i<children.length;i++){
					zTree.checkNode(children[i],checked,checked);
					if(children[i].isParent){
					if (!children[i].open){
							zTree.expandNode(children[i], true, true, false);
							onExpand(event, treeId, children[i]);
							zTree.expandNode(children[i], false, false, false);
						}
					}
				}
			},500);//延时0.5秒
		};
	
		
		$(document).ready(function () {
			$.fn.zTree.init($("#customers"), setting, zNodes);
		});
		
		function checkForm(){
			if($("#authDate").val() == null || $("#authDate").val().replace(/\s+/g,"")==""){
				showModalBox("请选择延期日期");
				return false;
			}
			
		
			var treeObj = $.fn.zTree.getZTreeObj("customers");
			var nodes = treeObj.getCheckedNodes();
			var ids = [];
			for(i=0;i<nodes.length;i++){
				var id = nodes[i].id;
				if(!nodes[i].isParent){		//不能是父节点
					ids.push(id);
				}
			}
			$("#cusIds").val(ids.join(","));
			return true;
		}
		
	</@script>
</@frame>