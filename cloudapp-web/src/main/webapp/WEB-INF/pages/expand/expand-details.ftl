<#include "/WEB-INF/macro/frame.ftl">
<@frame path="expand/expand-details" menu="expand" title="设备详情">
	<dl class="viewbox">
		<dt class="tit">
            <h3 class="fl">设备详情</h3>
            <div class="clear"></div>
        </dt>
		<table>
			<tr>
               	<th width="70px">位置号：</th>
                <td>${(expand.name)!}</td>
                <th width="70px">主板：</th>
                <td>${(info.buildBoard)!}</td>
            </tr>
            
            <tr>
               	<th>MAC地址：</th>
                <td>${(expand.mac)!}</td>
                <th>Build.ID：</th>
                <td>${(info.buildId)!}</td>
            </tr>
            
            <tr>
            	<th>设备类型：</th>
                <td>${(expand.categoryName)!}</td>
                <th>Build.MODEL：</th>
                <td>${(info.buildModel)!}</td>
            </tr>
            
            <tr>
                <th>局域网IP：</th>
                <td>${(info.lanIp)!}</td>
                <th>设备配置：</th>
                <td>${(expand.conf)!}</td>
            </tr>
            
            <tr>
                <th>空闲存储：</th>
                <td><#if (info.freeStorage)??>${info.freeStorage/(1024*1024)}M</#if></td>
                <th>最近登录：</th>
                <td>${(info.loginTime?string("yyyy-MM-dd HH:mm:ss"))!}</td>
            </tr>
            
            <tr>
                <th>固件版本：</th>
                <td>${(info.buildDisplay)!}</td>
                <th>编译时间：</th>
                <td id="buildTime"></td>
            </tr>
            
            <tr>
               	<th>版本名：</th>
                <td>${(info.verName)!}</td>
                <th>版本号：</th>
                <td>${(info.verCode)!}</td>
            </tr>
		</table>
	</dl>
	<@script>
		<#if (info.buildTime)?exists>
			var time = timestampToTime(${(info.buildTime)!});
			$("#buildTime").html(time);
		</#if>
	
	
		function timestampToTime(timestamp) {
	        var date = new Date(timestamp);		//时间戳为10位需*1000，时间戳为13位的话不需乘1000
	        var Y = date.getFullYear() + '';
	        var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '';
	        var D = (date.getDate()<10?'0'+date.getDate():date.getDate()) + '';
	        var h = (date.getHours()<10?'0'+date.getHours(): date.getHours()) + '';
	        var m = (date.getMinutes()<10?'0'+date.getMinutes():date.getMinutes()) + '';
	        //var s = date.getSeconds()<10?'0'+date.getSeconds():date.getSeconds();
	        return Y+M+D+h+m;
	    }
	
	</@script>
</@frame>