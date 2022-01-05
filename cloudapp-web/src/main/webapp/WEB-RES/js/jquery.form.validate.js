/*
 * 表单验证插件
 */
(function($) {
	$.formValidate = function(str) {
		if (arguments.length == 1)
			$(str).formValidate();
		else if (arguments.length > 1)
			$(str).formValidate(arguments[1]);
	};
	$.fn.formValidate = function(options) {
		// 配置参数
		$(this).bind(
				'submit',
				function() {
					$("input[type='submit']").attr("disabled","disabled");
					$.formValidate.findError = false;
					$.formValidate.errObj = null;
					$(this).contents().find("[validate]").each(
							$.formValidate.checkFormElem);
					if ($.formValidate.findError) {
						$.formValidate.errObj.focus();
						$("input[type='submit']").removeAttr("disabled");
						return false;
					} else {
						return true;
					}
				});
	};
	$.formValidate.checkFormElem = function() {// 验证单个元素
		$.formValidate.clearError($(this));
		if ($.formValidate.findError)
			return;
		var str = $(this).attr('validate');
		if (!str)
			return;
		var rules = str.split(',');
		for ( var i = 0; i < rules.length; i++) {
			var t = formValidate.test($(this), rules[i]);
			if (t != true) {
				t = $.formValidate.getRuleMessage($(this), rules[i], t);
				$.formValidate.bindError($(this), t);
				break;
			} else
				$.formValidate.clearError($(this));
		}
	};
	$.formValidate.getRuleMessage = function(obj, rule, defaultMessage) {
		var msg = obj.attr('data-' + rule + 'Message');
		if (msg) {
			return msg;
		}
		var dt = obj.parent().prev('dt').text();
		if (dt) {
			defaultMessage = '"' + dt + '"' + defaultMessage;
		}
		return defaultMessage;
	};
	// 设置为错误状态
	$.formValidate.bindError = function(obj, errMsg) {
		showFormErrInfo(obj, errMsg);
		obj.addClass('warning');
		$.formValidate.findError = true;
		if ($.formValidate.errObj == null)
			$.formValidate.errObj = obj;
	};
	// 清除错误状态
	$.formValidate.clearError = function(obj) {
		obj.removeClass('warning');
	};
	$.formValidate.turnOnValidate = function(_con) {
		_con.contents().find("[novalidate]").each(function() {
			turnOnValidateObj($(this));
		});
	};
	$.formValidate.turnOffValidate = function(_con) {
		_con.contents().find("[validate]").each(function() {
			turnOffValidateObj($(this));
			$.formValidate.clearError($(this));
		}); // 隐藏已知错误标识
	};
	$.formValidate.turnOnValidateObj = function(_obj) {
		_obj.attr('validate', _obj.attr('novalidate'));
		_obj.removeAttr('novalidate');
	};
	$.formValidate.turnOffValidateObj = function(_obj) {
		_obj.attr('novalidate', _obj.attr('validate'));
		_obj.removeAttr('validate');
	};
})(jQuery);
$(function() {
	$("form.validate").formValidate();
});
/** *************END 表单验证插件：formValidate ********************* */
var formValidate = {
	test : function(v /* $obj or valueString */, rule) { // 如果通过验证，返回true，否则返回错误原因
		v = $.trim((typeof v == 'object') ? v.val() : v);
		var t = '';
		switch (rule) {
		case 'required':
			if (v.length < 1)
				t = '不能为空';
			break;
		case 'email':
			if (v.length > 0
					&& !/^[\w\.\_\-]+@[\w\.\_\-]+(\.[\w\-]{2,3}){1,2}$/
							.test(v))
				t = '邮箱格式不正确';
			break;
		case 'url':
			if (v.length > 0 && !/^http:\/\/[^s]+$/.test(v))
				t = '网址格式不正确，如:http://www.google.com';
			break;
		case 'phone':
			if (v.length > 0
					&& !/(^(0\d{2,3}-?)?\d{7,9}(-\d{3,4})?$)|(^1[358]\d{9}$)/
							.test(v))
				t = '电话号码格式不正确';
			break;
		case 'int':
			if (v.length > 0 && !/^\d*$/.test(v))
				t = '只能为整数';
			break;
		case 'float':
			if (v.length > 0 && !/^\d+(\.\d+)?$/.test(v))
				t = '只能为数字';
			break;
		case 'cnName':
			if (v.length > 0 && !/^[\u4E00-\u9FA5]{2,4}$/.test(v))
				t = '请输入正确的中文名';
			break;
		case 'realName':
			if (v.length > 0 && !/^[\u4E00-\u9FA5A-Za-z\s]{2,16}$/.test(v))
				t = '请输入正确的姓名';
			break;
		case 'telepPone':
			if (v.length > 0 && !/^(0\d{2,3}-?)?\d{7,9}(-\d{3,4})?$/.test(v))
				t = '固定电话格式不正确';
			break;
		case 'mobilePhone':
			if (v.length > 0 && !/^1[358]\d{9}$/.test(v))
				t = '手机号码格式不正确';
			break;
		case 'date':
			if (v.length > 0 && !/^[12][0-9]{3}-[0-9]{2}-[0-9]{2}$/.test(v))
				t = '日期格式不正确，应为2010-12-1';
			break;
		case 'IDnumber':
			if (!this.isIdCardNo(v))
				t = '身份证号码格式不正确';
			break;
		case 'password':
			if (!/^[^\s]{6,20}$/.test(v))
				t = '密码长度为6-20';
			break;
		case 'ip':
			if (!/((2[0-4]\d|25[0-5]|[01]?\d\d?)\.){3}(2[0-4]\d|25[0-5]|[01]?\d\d?)/.test(v))
				t = 'IP地址格式不正确';
			break;
		default:
			t = '没有定义表单验证规则："' + rule + '"';
		}
		return t.length > 0 ? t : true;
	},
	isIdCardNo : function(num) {
		num = num.toUpperCase();
		// 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X。
		if (!(/(^\d{15}$)|(^\d{17}([0-9]|X)$)/.test(num)))
			return false;
		// 校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
		// 下面分别分析出生日期和校验位
		var len, re;
		len = num.length;
		if (len == 15) {
			re = new RegExp(/^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/);
			var arrSplit = num.match(re);
			// 检查生日日期是否正确
			var dtmBirth = new Date('19' + arrSplit[2] + '/' + arrSplit[3]
					+ '/' + arrSplit[4]);
			var bGoodDay;
			bGoodDay = (dtmBirth.getYear() == Number(arrSplit[2]))
					&& ((dtmBirth.getMonth() + 1) == Number(arrSplit[3]))
					&& (dtmBirth.getDate() == Number(arrSplit[4]));
			if (!bGoodDay) {
				return false; // alert('输入的身份证号里出生日期不对！');
			} else {
				// 将15位身份证转成18位
				// 校验位按照ISO 7064:1983.MOD 11-2的规定生成，X可以认为是数字10。
				var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10,
						5, 8, 4, 2);
				var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5',
						'4', '3', '2');
				var nTemp = 0, i;
				num = num.substr(0, 6) + '19' + num.substr(6, num.length - 6);
				for (i = 0; i < 17; i++) {
					nTemp += num.substr(i, 1) * arrInt[i];
				}
				num += arrCh[nTemp % 11];
				return num;
			}
		} else if (len == 18) {
			re = new RegExp(/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/);
			var arrSplit = num.match(re);
			// 检查生日日期是否正确
			var dtmBirth = new Date(arrSplit[2] + "/" + arrSplit[3] + "/"
					+ arrSplit[4]);
			var bGoodDay;
			bGoodDay = (dtmBirth.getFullYear() == Number(arrSplit[2]))
					&& ((dtmBirth.getMonth() + 1) == Number(arrSplit[3]))
					&& (dtmBirth.getDate() == Number(arrSplit[4]));
			if (!bGoodDay)
				return false; // alert('输入的身份证号里出生日期不对！');
			else {
				// 检验18位身份证的校验码是否正确。 校验位按照ISO 7064:1983.MOD
				// 11-2的规定生成，X可以认为是数字10。
				var valnum;
				var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10,
						5, 8, 4, 2);
				var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5',
						'4', '3', '2');
				var nTemp = 0, i;
				for (i = 0; i < 17; i++)
					nTemp += num.substr(i, 1) * arrInt[i];
				valnum = arrCh[nTemp % 11];
				if (valnum != num.substr(17, 1))
					return false; // alert('18位身份证的校验码不正确！应该为：' + valnum);
				return num;
			}
		}
		return false;
	}
};
/*
 * 验证单个表单元素添加验证规则 $.formValidate.check('#obj1',function(){return
 * $('#obj1').val().length>3},'信息提示语XX');
 */
$.formValidate.check = function(objId, fun, msg) {
	var obj = $('#' + objId);
	var form = obj.parents('form');
	if (!form.attr('data-errFlag')) {
		form.bind('click', function() {
			form.attr('data-errFlag', '2');
		});
	}
	form.bind('submit', function() {
		if (form.attr('data-errFlag') == 1)
			return;
		if (!fun(obj.val())) {
			form.attr('data-errFlag', '1');
			showFormErrInfo(obj, msg);
			return false;
		}
	});
};
// 模态层调用方法
function showFormErrInfo($obj, errMsg) {
	var title ="温馨提示",
		closeBtn = "确定";
	$.modal( '<h4 class="modalBoxTitle">'+title+'</h4>'+'<div class="modalBoxContent"><span class="icon icon-large-warning fl"></span>'+errMsg+'</div>'+'<div class="modalBoxFooter"><a class="btn simplemodal-close"><span>'+closeBtn+'</span></a></div>',{
		opacity : 30,
		minHeight : 80,
		maxWidth : 350,
		overlayClose : true,
		autoResize : true,
		onShow : function() {
			var height = $('#simplemodal-data').height();
			$('#simplemodal-container').height(height);
		},
		onClose : function() {
			$obj.focus();
			$.modal.close();
		}
	});
}

//调用模态层显示信息的方法
function showModalBox (content,title,closeBtn) {
	var title ="温馨提示",
		closeBtn = "确定";
     $.modal( '<h4 class="modalBoxTitle">'+title+'</h4>'+'<div class="modalBoxContent"><span class="icon icon-large-warning fl"></span>'+content+'</div>'+'<div class="modalBoxFooter"><a class="btn simplemodal-close"><span>'+closeBtn+'</span></a></div>',
			{
			    opacity:30, 
			    minHeight:80,
				maxWidth:350,
				overlayClose:true,
				autoResize:true,
				onShow: function(){
					var height=$('#simplemodal-data').height();
					$('#simplemodal-container').height(height);
				}
		    }
		);
}