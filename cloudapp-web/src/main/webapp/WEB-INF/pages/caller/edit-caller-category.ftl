<#include "/WEB-INF/macro/frame.ftl">
<@frame path="customer/customer-list" menu="customer" title="类型编辑">
	<dl class="viewbox">
		<dt class="tit">
            <h3 class="fl"><#if (category.id)??>修改<#else>新增</#if>类型</h3>
            <div class="clear"></div>
        </dt>
        
		<form action="save-caller-category.html" method="post" onsubmit="return checkForm()">
			<#if (category.id)?exists>
				<input name="id" type="hidden" value="${category.id}" />
			</#if>
			<input name="customerId" type="hidden" value="${category.customerId}" />
			<dd>
				<table>
	                <tr>
	                   	<th width="85px"><span class="red">*</span> <strong>类型名称：</strong></th>
	                    <td>
	                    	<input id="name" name="name" type="text" onKeyDown="if(this.value.length >= 23 && (event.keyCode != 8)){ return false }" value="${(category.name)!}"/>
	                    </td>
	                </tr>
	                
	                <tr>
	                    <th><strong>关联呼叫器：</strong></th>
	                    <td>
							<ul id="callers" class="ztree"></ul>
	                		<input type="hidden" name="callerIds" id="callerIds" />
	                    </td>
                	</tr>
				</table>
			</dd>
			
			<div>
				<input type="submit" value="提 交" class="butn" />
				<input type="button" value="返 回" class="butn" onclick="javascript:window.history.go(-1);" />
			</div>
		</form>
	</dl>
	
	<link rel="stylesheet" href="${resBase}/plugins/ztree/zTreeStyle.css" type="text/css" />
	<script type="text/javascript" src="${resBase}/plugins/ztree/jquery.ztree.core-3.5.min.js"></script>
	<script type="text/javascript" src="${resBase}/plugins/ztree/jquery.ztree.excheck-3.5.min.js"></script>
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
		        url: "${sysBase}/api/get-callers-by-category.json",
		        // 选择父节点时会自动将节点中的参数传回服务器再重新取结果
		        autoParam: ["id","categoryId"]
		    },
		    callback : {
	            onCheck : onCheck
	        },
			view: {showIcon: true}
		};
		
		function onCheck(event, treeId, treeNode){
			var zTree = $.fn.zTree.getZTreeObj("callers");
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
					},500);//延时1.5秒
				}
			} 
		};
			    
	    function onExpand(event, treeId, treeNode) {
			var zTree = $.fn.zTree.getZTreeObj("callers");
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
		
		var zNodes =[];
		var node;
		<#list categoryList as gp>
			node = {id:'${gp.id}', pId:0, <#if (category.id)??>categoryId:${category.id},</#if> name:"${gp.name}", isParent:true, open:false<#if (category.id)?? && (category.id=gp.id)>,nocheck:true</#if>};
			zNodes.push(node);
		</#list>
		
		$(document).ready(function(){
			var zTreeObj = $.fn.zTree.init($("#callers"), setting, zNodes);
			<#if (category.id)??>
				var rootNode_0 = zTreeObj.getNodeByParam('id',${category.id},null);
            	zTreeObj.expandNode(rootNode_0, true, false, false, false);
			</#if>
		});
		
		
		function checkForm(){
			if($("#name").val() == null || $("#name").val().replace(/\s+/g,"")==""){
				showModalBox("请输入名称");
				return false;
			}
		
			var treeObj = $.fn.zTree.getZTreeObj("callers");
			var nodes = treeObj.getCheckedNodes();
			var ids = [];
			for(i=0;i<nodes.length;i++){
				var id = nodes[i].id;
				if(!nodes[i].isParent){		//不能是父节点
					ids.push(id);
				}
			}
			$("#callerIds").val(ids.join(","));
			return true;
		}
	</@script>
</@frame>