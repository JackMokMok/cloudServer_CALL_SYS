<#include "/WEB-INF/macro/frame.ftl">
<@frame path="customer/group-list" menu="customer" title="分组编辑">
	<dl class="viewbox">
		<dt class="tit">
            <h3 class="fl"><#if (group.id)??>修改<#else>新增</#if>分组</h3>
            <div class="clear"></div>
        </dt>
        
		<form action="save-group.html" method="post" onsubmit="return checkForm()">
			<#if (group.id)?exists>
				<input name="id" type="hidden" value="${group.id}" />
			</#if>
			<dd>
				<table>
	                <tr>
	                   	<th width="85px"><span class="red">*</span> <strong>分组名称：</strong></th>
	                    <td>
	                    	<input id="name" name="name" type="text" onKeyDown="if(this.value.length >= 23 && (event.keyCode != 8)){ return false }" value="${(group.name)!}"/>
	                    </td>
	                </tr>
	                
	                <tr>
	                    <th><strong>关联场所：</strong></th>
	                    <td>
							<ul id="customers" class="ztree"></ul>
	                		<input type="hidden" name="cusIds" id="cusIds" />
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
		        url: "${sysBase}/api/get-customers-by-group.json",
		        // 选择父节点时会自动将节点中的参数传回服务器再重新取结果
		        autoParam: ["id","groupId"]
		    },
		    callback : {
	            onCheck : onCheck
	        },
			view: {showIcon: true}
		};
		
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
		
		
		$(document).ready(function(){
			var zNodes =[];
			var node;
			<#list groupList as gp>
				node = {id:'${gp.id}', pId:0, <#if (group.id)??>groupId:${group.id},</#if> name:"${gp.name}", isParent:true, open:false<#if (group.id)?? && (group.id=gp.id)>,nocheck:true</#if>};
				zNodes.push(node);
			</#list>
			var zTreeObj = $.fn.zTree.init($("#customers"), setting, zNodes);
			<#if (group.id)??>
				var rootNode_0 = zTreeObj.getNodeByParam('id',${group.id},null);
            	zTreeObj.expandNode(rootNode_0, true, false, false, false);
			</#if>
		});
		
		
		function checkForm(){
			if($("#name").val() == null || $("#name").val().replace(/\s+/g,"")==""){
				showModalBox("请输入名称");
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