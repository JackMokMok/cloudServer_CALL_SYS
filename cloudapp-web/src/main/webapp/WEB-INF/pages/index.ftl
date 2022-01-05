<#include "/WEB-INF/macro/frame.ftl">
<@frame title="首页">
	<div class="table">
		<div class="tit">
			<h3>系统统计</h3>
		</div>
		<dl class="homeLinks space_20">
			<dd>
				<ul>
					<li>
						<a href="${sysBase}/callers/all-caller-host-list.html?online=1"><img src="${resBase}/images/online.png" style="width:16px;"/>&nbsp;在线呼叫主机 <em id="online_host" style="color:red;">0</em> 个</a>
					</li>
					<li>
						<a href="${sysBase}/callers/all-caller-host-list.html?online=0"><img src="${resBase}/images/offline.png" style="width:16px;"/>&nbsp;离线呼叫主机 <em id="offline_host" style="color:red;">0</em> 个</a>
					</li>
				</ul>
			</dd>
		</dl>
	</div>
</@frame>
<script>
	$(document).ready(function(){
		$.post("${sysBase}/api/online-caller-host.json",function(data){
			if(data.code == 200){
				var c = data.data.split(",");
				$("#online_host").html(c[0]);
				$("#offline_host").html(c[1]);
			}
		},"json");
	});
        
</script>
