<#include "/WEB-INF/macro/frame.ftl">
<@frame path="customer/customer-list" menu="customer" title="场所编辑">
	<dl class="viewbox">
		<dt class="tit">
            <h3 class="fl"><#if (customer.id)??>修改<#else>新增</#if>场所</h3>
            <div class="clear"></div>
        </dt>
        
		<form action="save-customer.html" method="post" onsubmit="return checkForm()">
			<#if (customer.id)?exists>
				<input name="id" type="hidden" value="${customer.id}" />
			</#if>
			<input type="hidden" name="adIds" id="adIds" />
			<input type="hidden" name="appIds" id="appIds" />
			<dd>
				<table>
					<tr>
	                   	<th width="85px"><span class="red">*</span> <strong>场所分组：</strong></th>
	                   	<td>
	                    	<select name="cusGroupId" id="cusGroupId">
		                    	<option value="">请选择分组</option>
		                		<#list groupList as g>
		                			<option value="${g.id}" <#if (customer.cusGroupId)?? && customer.cusGroupId = g.id>selected</#if>>${g.name}</option>
								</#list>
							</select>
	                    </td>
                    </tr>
				
	                <tr>
	                   	<th><span class="red">*</span> <strong>场所名称：</strong></th>
	                    <td>
	                    	<input name="name" id="name" type="text" onKeyDown="if(this.value.length >= 23 && (event.keyCode != 8)){ return false }" value="${(customer.name)!}"/>
	                    </td>
	                </tr>
	                
	                <tr>
	                   	<th><span class="red">*</span> <strong>注册码：</strong></th>
	                    <td>
	                    	<input name="code" id="code" type="text" onKeyDown="if(this.value.length >= 11 && (event.keyCode != 8)){ return false }" value="${(customer.code)!}"/>
	                    	*默认填写客户编码
	                    </td>
	                </tr>
	                
	                <tr>
	                   	<th><span class="red">*</span> <strong>授权点数：</strong></th>
	                    <td>
	                    	<input name="authCount" id="authCount" type="text" onKeyup="value=value.replace(/[^\d]/g,'')" onKeyDown="if(this.value.length >= 7 && (event.keyCode != 8)){ return false }" value="${(customer.authCount)!}"/>
	                    </td>
	                </tr>
	                
	                <tr>
	                   	<th><span class="red">*</span> <strong>授权到期：</strong></th>
	                    <td>
	                 		<input name="authDate" id="authDate" type="text" style="height:14px;" value="${(customer.authDate?string("yyyy-MM-dd"))!}" readonly="readonly" onClick="WdatePicker()" class="Wdate"/>
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
	<@autocomplete/>
	<@datetimejs />
	<style>
		.setInput{
			border:none;
			padding:0;
			margin:0;
			width:100%;
			height:30px;
		}
	</style>
	<link rel="stylesheet" href="${resBase}/plugins/ztree/zTreeStyle.css" type="text/css" />
	<script type="text/javascript" src="${resBase}/plugins/ztree/jquery.ztree.core-3.5.min.js"></script>
	<script type="text/javascript" src="${resBase}/plugins/ztree/jquery.ztree.excheck-3.5.min.js"></script>
	<@script>
		var setting = {
			check: {enable: true},
			data: {
				simpleData: {
					enable: true,//是否采用简单数据模式
		            idKey: "id",//树节点ID名称
		            pIdKey: "pid",//父节点ID名称
		            rootPId: 0,//根节点ID
				}
			},
			view: {showIcon: true}
		};
		
		function checkMobile() {
		    var userAgentInfo = navigator.userAgent;
		    var Agents = ["Android", "iPhone",
		                "SymbianOS", "Windows Phone",
		                "iPad", "iPod"];
		    var flag = true;
		    for (var v = 0; v < Agents.length; v++) {
		        if (userAgentInfo.indexOf(Agents[v]) > 0) {
		            flag = false;
		            break;
		        }
		    }
		    if(!flag){
		    	$("#imgTd").hide();
		    }
		}
	
		$(document).ready(function () {
			setNameListener();
		});
		
		function setNameListener(){
			$('#name').bind("input.autocomplete", function () {
				$(this).flushCache(); 
                $(this).trigger('keydown.autocomplete');
            });

            $("#name").autocomplete("${sysBase}/api/get-mywork-customer.json",//数据处理的页面地址或url地址
              {
                width: 450,
                max: 30,
                scrollHeight: 350,
                delay:400,
                matchCase: false,
                dataType: "json",//数据格式
                extraParams: {
                   // 传递参数方式
                    name: function () {return $.trim($("#name").val())}
                },
                parse: function (data) {
                    if (!data || data == null || data == "" || data.code != 200 ) {
                        return {};
                    } else {
                        var items = JSON.parse(data.data);
                        return $.map(items, function (row) {
                            return {
                                data: row,
                                value: row.code,
                                result: row.name
                            };
                        });
                    }

                },
                formatItem: function (item) {
                    return item.code + "："  + item.name;//提示的内容显示内容及格式设置
                }
            }).result(function (event, data, formatted) {
                //选择提示内容后，触发的事件或操作(若无,可以不要此节)
                $('#code').val(data.code);
            });
		}
		
		function checkForm(){
			if($("#cusGroupId").val() == null || $("#cusGroupId").val()==""){
				showModalBox("请选择分组");
				return false;
			}
			
			if($("#name").val() == null || $("#name").val().replace(/\s+/g,"")==""){
				showModalBox("请填写场所名称");
				return false;
			}
			
			if($("#code").val() == null || $("#code").val().replace(/\s+/g,"")==""){
				showModalBox("请填写注册码");
				return false;
			}
			
			if($("#authCount").val() == null || $("#authCount").val().replace(/\s+/g,"")==""){
				showModalBox("请填写授权点数");
				return false;
			}
			
			if($("#authDate").val() == null || $("#authDate").val().replace(/\s+/g,"")==""){
				showModalBox("请选择授权到期日期");
				return false;
			}
			return true;
		}
	</@script>
</@frame>