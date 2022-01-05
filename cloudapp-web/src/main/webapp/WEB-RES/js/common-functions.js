$(document).ready(function() {
//伸缩菜单
	$("#nav > li > a.collapsed + ul").slideToggle(0);//页面加载完成后，与a标签并列的ul标签调用slideToggle()方法，即页面收起来（默认进入首页，所有标签都是收起来的）
	$("#nav > li > a").click(function() {//监听a标签点击事件
		$("#nav .heading").removeClass("expanded").addClass("collapsed");//移除expanded和添加collapsed的class属性
		$("#nav > li > ul").slideUp();//li标签下，所有的ul标签都会被隐藏掉
		//toggleClass方法是，存在的属性就删除，不存在就添加之；找到$(this)为a标签的父标签再再找到下一级的ul标签调用slideToggle方法；
		$(this).toggleClass('expanded').toggleClass('collapsed').parent().find('> ul').slideToggle('medium');
	});
// 选择全部checkboxes
   $("#checkboxall").click(function()
      {
      var checked_status = this.checked;
      $("input[name=ids]").each(function() {
      this.checked = checked_status;
      });
      });
//当前时间
	$(function(){  
      setInterval(function(){   
      $("#currentTime").text(new Date().toLocaleString());   
     },1000);   
	});
//页面高度固定
	var winHeight = $(window).height();
	$(".content").css("height",winHeight-97);
	$(".sidebar").css("height",winHeight-97);
	$(window).resize(function(){
			var winHeight = $(window).height()-97;
			$(".content").css("height",winHeight);
			$(".sidebar").css("height",winHeight);
		});
	$("#nav").click(function(){
		if($("#nav").height() > $(".sidebar").height()-100){
			$(".sidebar").css("overflow-y","scroll");
			$(".sidebar").css("overflow-x","hidden");
		}else if($("#nav").height() <= $(".sidebar").height()){
			$(".sidebar").removeAttr("style");
			$(".sidebar").css("height",$(".content").height());
			}
		});

//Tip小贴士
	$(".tip").mouseover(function(){
		var tip = $(this).attr("tip");
		var FatherHeight = $(this).height();
		var left = $(this).offset().left;
		var top = $(this).offset().top - FatherHeight - 20;
			if($(".tipText").text()!==tip){
			$(this).after("<span class='tipText' style='left:"+left+"px;top:"+top+"px'>"+tip+"<em></em></span>");
			}
		});
	$(".tip").mouseout(function(){
		$(".tipText").remove();
	});
// 获取输入框Tip并写入此input
	$(".atips input").each(function() {
		if ($(this).val() == "") {
			var tip = $(this).attr("tip");
			$(this).attr("value", tip);
		}
	});
// 文本框提示
	function inputTipText() {
		$(".atips input")
		.each(function() {
			var oldVal = $(this).val();
			var tip = $(this).attr("tip");
			if ($(this).val() == tip) {
				$(this).css({
					"color" : "#888"
				}).focus(function() {
					if ($(this).val() != oldVal) {
						$(this).css({
							"color" : "#333"
						});
					} else {
						$(this).val("").css({
							"color" : "#000"
						});
					}
				}).blur(function() {
					if ($(this).val() == "") {
						$(this).val(oldVal).css({
							"color" : "#888"
						});
					}
				});
			}
		});
	}
	$(function() {
		inputTipText();
	});
//登录框判断
	$(".login_form .subt").click(function(){
		var flag = true;
		$(".login_form input").each(function(){
				if($(this).val()==$(this).attr("tip")){
					showModalBox($(this).attr("tip"));	
					flag = false;
				}
			});
			return flag;
		});

//tabs
$(".tabs .title h3").eq(0).addClass("cur");
$(".tabs .tabscont td").eq(0).show();
$(".tabs .title h3").click(function(e) {
    $(this).addClass("cur").siblings().removeClass("cur");
	$(".tabs .tabscont td").eq($(this).index()).show().siblings("td").hide();
});


});
