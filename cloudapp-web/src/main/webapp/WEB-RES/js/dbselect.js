function selectUnselectMatchingOptions(obj, regex, which, only) {
    if (window.RegExp) {
        if (which == "select") {
            var selected1 = true;
            var selected2 = false;
        } else {
            if (which == "unselect") {
                var selected1 = false;
                var selected2 = true;
            } else {
                return;
            }
        }
        var re = new RegExp(regex);
        for (var i = 0; i < obj.options.length; i++) {
            if (re.test(obj.options[i].text)) {
                obj.options[i].selected = selected1;
            } else {
                if (only == true) {
                    obj.options[i].selected = selected2;
                }
            }
        }
    }
}
function selectMatchingOptions(obj, regex) {
    selectUnselectMatchingOptions(obj, regex, "select", false);
}
function selectOnlyMatchingOptions(obj, regex) {
    selectUnselectMatchingOptions(obj, regex, "select", true);
}
function unSelectMatchingOptions(obj, regex) {
    selectUnselectMatchingOptions(obj, regex, "unselect", false);
}
function sortSelect(obj) {
    var o = new Array();
    if (obj.options == null) {
        return;
    }
    for (var i = 0; i < obj.options.length; i++) {
        o[o.length] = new Option(obj.options[i].text, obj.options[i].value, obj.options[i].defaultSelected, obj.options[i].selected);
    }
    if (o.length == 0) {
        return;
    }
    o = o.sort(function (a, b) {
        return a.text.localeCompare(b.text);
    });
    for (var i = 0; i < o.length; i++) {
        obj.options[i] = new Option(o[i].text, o[i].value, o[i].defaultSelected, o[i].selected);
    }
}
function selectAllOptions(obj) {
    for (var i = 0; i < obj.options.length; i++) {
        obj.options[i].selected = true;
    }
}
function moveSelectedOptions(from, to) {
    if (arguments.length > 3) {
        var regex = arguments[3];
        if (regex != "") {
            unSelectMatchingOptions(from, regex);
        }
    }
    for (var i = 0; i < from.options.length; i++) {
        var o = from.options[i];
        if (o.selected) {
            to.options[to.options.length] = new Option(o.text, o.value, false, false);
        }
    }
    for (var i = (from.options.length - 1); i >= 0; i--) {
        var o = from.options[i];
        if (o.selected) {
            from.options[i] = null;
        }
    }
    if ((arguments.length < 3) || (arguments[2] == true)) {
        sortSelect(from);
        sortSelect(to);
    }
    from.selectedIndex = -1;
    to.selectedIndex = -1;
}
function copySelectedOptions(from, to) {
    var options = new Object();
    for (var i = 0; i < to.options.length; i++) {
        options[to.options[i].text] = true;
    }
    for (var i = 0; i < from.options.length; i++) {
        var o = from.options[i];
        if (o.selected) {
            if (options[o.text] == null || options[o.text] == "undefined") {
                to.options[to.options.length] = new Option(o.text, o.value, false, false);
            }
        }
    }
    if ((arguments.length < 3) || (arguments[2] == true)) {
        sortSelect(to);
    }
    from.selectedIndex = -1;
    to.selectedIndex = -1;
}
function moveAllOptions(from, to) {
    selectAllOptions(from);
    if (arguments.length == 2) {
        moveSelectedOptions(from, to);
    } else {
        if (arguments.length == 3) {
            moveSelectedOptions(from, to, arguments[2]);
        } else {
            if (arguments.length == 4) {
                moveSelectedOptions(from, to, arguments[2], arguments[3]);
            }
        }
    }
}
function copyAllOptions(from, to) {
    selectAllOptions(from);
    if (arguments.length == 2) {
        copySelectedOptions(from, to);
    } else {
        if (arguments.length == 3) {
            copySelectedOptions(from, to, arguments[2]);
        }
    }
}
function swapOptions(obj, i, j) {
    var o = obj.options;
    var i_selected = o[i].selected;
    var j_selected = o[j].selected;
    var temp = new Option(o[i].text, o[i].value, o[i].defaultSelected, o[i].selected);
    var temp2 = new Option(o[j].text, o[j].value, o[j].defaultSelected, o[j].selected);
    o[i] = temp2;
    o[j] = temp;
    o[i].selected = j_selected;
    o[j].selected = i_selected;
}
function moveOptionUp(obj) {
    var selectedCount = 0;
    for (i = 0; i < obj.options.length; i++) {
        if (obj.options[i].selected) {
            selectedCount++;
        }
    }
    if (selectedCount != 1) {
        return;
    }
    var i = obj.selectedIndex;
    if (i == 0) {
        return;
    }
    swapOptions(obj, i, i - 1);
    obj.options[i - 1].selected = true;
}
function moveOptionDown(obj) {
    var selectedCount = 0;
    for (i = 0; i < obj.options.length; i++) {
        if (obj.options[i].selected) {
            selectedCount++;
        }
    }
    if (selectedCount != 1) {
        return;
    }
    var i = obj.selectedIndex;
    if (i == (obj.options.length - 1)) {
        return;
    }
    swapOptions(obj, i, i + 1);
    obj.options[i + 1].selected = true;
}
function removeSelectedOptions(from) {
    for (var i = (from.options.length - 1); i >= 0; i--) {
        var o = from.options[i];
        if (o.selected) {
            from.options[i] = null;
        }
    }
    from.selectedIndex = -1;
}
function moveOptionByValue(from,to,val){
	var i = 0;
	for(;i<from.options.length;i++){
		if(val==from.options[i].value){
			from.options[i].selected = true;
			break;
		}
	}
	moveSelectedOptions(from,to);
}