<#include "/WEB-INF/macro/frame.ftl">
<@frame path="customer/customer-list" menu="customer" title="分组编辑">
	<dl class="viewbox">
		<dt class="tit">
            <h3 class="fl"><#if (group.id)??>修改<#else>新增</#if>分组</h3>
            <div class="clear"></div>
        </dt>
        
		<form action="save-caller-group.html" method="post" onsubmit="return checkForm()">
			<#if (group.id)?exists>
				<input name="id" type="hidden" value="${group.id}" />
			</#if>
			<input name="customerId" type="hidden" value="${group.customerId}" />
			<dd>
				<table>
	                <tr>
	                   	<th width="85px"><span class="red">*</span> <strong>分组名称：</strong></th>
	                    <td>
	                    	<input id="name" name="name" type="text" onKeyDown="if(this.value.length >= 23 && (event.keyCode != 8)){ return false }" value="${(group.name)!}"/>
	                    </td>
	                </tr>
	                
	                <tr>
	                   	<th><strong>筛选：</strong></th>
	                    <td>
							<checkbox>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<label><input type="checkbox" checked="checked"  id="selected" /> &nbsp;显示已关联</label>
								&nbsp;&nbsp;&nbsp;
								<label><input type="checkbox" checked="checked"  id="unselect" /> &nbsp;显示未关联</label>
							</checkbox>
	                    </td>
	                </tr>
	                
	                <tr>
	                    <th><strong>关联设备：</strong></th>
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
	<script type="text/javascript" src="${resBase}/plugins/ztree/jquery.ztree.exhide.js"></script>
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
		        url: "${sysBase}/api/get-callers-by-group.json",
		        // 选择父节点时会自动将节点中的参数传回服务器再重新取结果
		        autoParam: ["id","groupId"],
		        dataFilter: filter  //异步返回后经过Filter
		    },
		    callback : {
	            onAsyncSuccess: zTreeOnAsyncSuccess,//异步加载完成调用
	            aOnAsyncError : zTreeOnAsyncError,//加载错误的fun
	            onCheck : onCheck
	        },
			view: {showIcon: true}
		};
		
		
		function checkChange(){
			var treeObj = $.fn.zTree.getZTreeObj("callers");
			var hideNodes = treeObj.getNodesByParam("isHidden", true);		//获取全部隐藏节点并显示
			treeObj.showNodes(hideNodes);
			
			var checkNodes = treeObj.getNodesByParam("nocheck",true);			//选中的节点
			var uncheckNodes = treeObj.getNodesByParam("nocheck",false);		//未选中的节点
			
				
			if(!$("#selected").is(':checked')){			//隐藏已选择的节点
				for(i=0;i<checkNodes.length;i++){
					if(!checkNodes[i].isParent){		
						treeObj.hideNode(checkNodes[i]);
					}
				}
			}
			
			if(!$("#unselect").is(':checked')){
				for(i=0;i<uncheckNodes.length;i++){
					if(!uncheckNodes[i].isParent){		//隐藏未选择的节点，不能是父节点
						treeObj.hideNode(uncheckNodes[i]);
					}
				}
			}
		}
		
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
	 
	    /* 获取返回的数据，进行预操作，treeId是treeDemo,异步加载完之后走这个方法，responseData为后台返回数据  */
	    function filter(treeId, parentNode, responseData) {
	//       responseData = responseData.jsonArray;
	        if (!responseData){
	            return null;
	        }
	        var len = responseData.length;
	        var showS = $("#selected").is(':checked');
	        var showU = $("#unselect").is(':checked');
	        for(var i=0;i<len;i++){
	        	var obj = responseData[i];
	        	if(obj.nocheck && !showS){
	        		obj.isHidden = true;
	        	}else if(!obj.nocheck && !showU){
	        		obj.isHidden = true;
	        	}
	        }
	        return responseData;
	    }
	 
	    //异步加载完成时运行
	    function zTreeOnAsyncSuccess(event, treeId, treeNode, msg)  {
	    }
	    //异步加载失败
	    function zTreeOnAsyncError(event, treeId, treeNode, XMLHttpRequest, textStatus, errorThrown)  {
	        alertMsg.error("异步加载节点失败!");
	    }
	    
		$(document).ready(function(){
			var zNodes =[];
			var node;
			<#list categoryList as gp>
				node = {id:'${gp.id}', pId:0, <#if (group.id)??>groupId:${group.id},</#if> name:"${gp.name}", isParent:true, open:false};
				zNodes.push(node);
			</#list>
			var zTreeObj = $.fn.zTree.init($("#callers"), setting, zNodes);
			$("#selected").on("change",checkChange);
			$("#unselect").on("change",checkChange);
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