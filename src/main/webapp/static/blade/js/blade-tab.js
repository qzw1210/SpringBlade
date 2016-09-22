var addTabs = function(obj) {
	id = obj.id;

	$(".active").removeClass("active");

	// 如果TAB不存在，创建一个新的TAB
	if (!$("#" + id)[0]) {
		// 固定TAB中IFRAME高度,根据需要自己修改
		// mainHeight = $(document.body).height();
		mainHeight = $(window).height() - 135;
		// 创建新TAB的title
		title = '<li id="tab_' + id + '" class="li-tab"><a href="#' + id
				+ '" data-toggle="tab"><i class="' + obj.icon + '"></i>&nbsp;'
				+ obj.title + '';

		// 是否允许关闭
		if (obj.close) {
			title += '&nbsp;&nbsp; <i aria-controls="' + id
					+ '" class="close-tab fa fa-times-circle"></i>';
		}
		title += '</a></li>';
		// 是否指定TAB内容
		if (obj.content) {
			content = '<div role="tabpanel" class="tab-pane iframe-tab" id="'
					+ id + '">' + obj.content + '</div>';
		} else { // 没有内容，使用IFRAME打开链接
			content = '<div role="tabpanel" class="tab-pane iframe-tab" id="'
					+ id
					+ '"><iframe data-type="tab_iframe" id="iframe_'
					+ id
					+ '" src="'
					+ obj.url
					+ '" width="100%" height="'
					+ mainHeight
					+ '" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes" allowtransparency="yes"></iframe></div>';
		}

		var left = parseInt($("#tmsp_tab").css("left")) - tabWidth() - 1;
		// 加入TABS
		$(".nav-tabs").append(title);
		$(".tab-content").append(content);
		if (tabWidth() > middleWidth()) {
			$("#tmsp_tab").animate({
				left : left
			});
		}
	}
	// 如果存在则定位
	else {
		var prevLeft = parseInt($("#sidebar_container").css("width"));
		var prevRight = $("#btn_backward").offset().left;
		var prevTab = $("#tab_" + id).offset().left;
		var tabItems = $("#tmsp_tab li");
		var _width = 0;
		if (prevTab < prevLeft || prevTab > prevRight) {
			for (var i = 0, l = tabItems.length; i < l; i++) {
				if ($(tabItems[i]).attr("id") == ("tab_" + id)) {
					break;
				}
				_width += tabItems[i].offsetWidth;
			}
			$("#tmsp_tab").animate({
				left : -_width - 1
			});
		}
	}

	// 激活TAB
	$("#tab_" + id).addClass('active');
	$("#" + id).addClass("active");
	$("a[data-addtabs=" + obj.id + "]").closest("li").addClass("active");
};

var closeTab = function(id) {
	// 如果关闭的是当前激活的TAB，激活他的前一个TAB
	if ($("#tmsp_tab li.active").attr("id") == "tab_" + id) {
		$(".active").removeClass("active");
		$("#tab_" + id).prev().addClass('active');
		$("#" + id).prev().addClass('active');
		var tabid = $("#" + id).prev().attr("id");
		$("a[data-addtabs=" + tabid + "]").closest("li").addClass("active");
	}
	// 关闭TAB
	$("#tab_" + id).remove();
	$("#" + id).remove();
	showTab();
};

var autoClose = function(id, pid) {
	$(".active").removeClass("active");
	$("#tab_" + pid).addClass("active");
	$("#" + pid).addClass("active");

	$("a[data-addtabs=" + pid + "]").closest("li").addClass("active");
	$("#tab_" + id).remove();
	$("#" + id).remove();
	showTab();
};

var closeAll = function() {
	$(".li-tab").remove();
	$(".iframe-tab").remove();
	$(".active").removeClass("active");
	$("#home").addClass("active");
	$("#welcome_tab").addClass("active");
	$("#sidebar_home").addClass("active");
	$("#tmsp_tab").animate({
		left : -1
	});
};

var closeOther = function() {
	$(".li-tab").not('.active').remove();
	$(".iframe-tab").not('.active').remove();
	$("#tmsp_tab").animate({
		left : -1
	});
};

var closeThis = function() {
	var $tab = $("#tmsp_tab .active").prev();
	var $content = $("#tmsp_tab_content .active").prev();
	$("#tmsp_tab .active").not("#welcome_tab").remove();
	$("#tmsp_tab_content .active").not("#home").remove();
	$(".active").not("#home").not("#welcome_tab").not("#sidebar_home").removeClass("active");
	$tab.addClass('active');
	$content.addClass('active');
	var tabid = $content.attr("id");
	$("a[data-addtabs=" + tabid + "]").closest("li").addClass("active");
	showTab();
};

var tab_backward = function() {
	var middle = middleWidth();
	var left = parseInt($("#tmsp_tab").css("left"));
	var len = (tabWidth() - left);
	var _left = (middle + left);
	if (len < middle) {
		$("#tmsp_tab").animate({
			left : -1
		});
	} else {
		_left = (_left > 0) ? -1 : _left;
		$("#tmsp_tab").animate({
			left : _left
		});
	}
};

var tab_forward = function() {
	var left = parseInt($("#tmsp_tab").css("left"));
	var middle = middleWidth();
	var _width = 0;
	if ((tabAllWidth() + left) > middle) {
		var tabItems = $("#tmsp_tab li");
		for (var i = 0, l = tabItems.length; i < l; i++) {
			if ((_width + tabItems[i].offsetWidth) < middle) {
				_width += tabItems[i].offsetWidth;
			}
		}
		$("#tmsp_tab").animate({
			left : -_width + left
		});
	} else {
		$("#tmsp_tab").animate({
			left : -1
		});
	}
};

var showTab = function() {
	var prevLeft = parseInt($("#sidebar_container").css("width"));
	var prevRight = $("#btn_backward").offset().left;
	var prevTab = $("#tmsp_tab .active").offset().left;
	var tabItems = $("#tmsp_tab li");
	var _width = 0;
	if (prevTab < prevLeft || prevTab > prevRight) {
		for (var i = 0, l = tabItems.length; i < l; i++) {
			if ($(tabItems[i]).hasClass("active")) {
				break;
			}
			_width += tabItems[i].offsetWidth;
		}
		$("#tmsp_tab").animate({
			left : -_width - 1
		});
	}
};

var reloadTab = function() {
	var id = $("#tmsp_tab_content .active iframe").attr("id");
	if (id == undefined) {
		window.top.location.reload(true);
		return;
	}
	document.getElementById(id).contentWindow.location.reload(true);

};

var reloadTabById = function(id) {
	document.getElementById("iframe_" + id).contentWindow.isAutoPage = true;
	document.getElementById("iframe_" + id).contentWindow.searchGrid();
};

var tabWidth = function() {
	var tabWidth = 0;
	var left = parseInt($("#tmsp_tab").css("left"));
	var tabItems = $("#tmsp_tab li");
	for (var i = 0; i < tabItems.length; i++) {
		tabWidth += tabItems[i].offsetWidth;
	}
	return tabWidth + left;
};

var tabMiddleWidth = function() {
	var tabItems = $("#tmsp_tab li");
	var tabWidth = 0;
	var left = parseInt($("#tmsp_tab").css("left"));
	for (var i = 0, l = tabItems.length; i < l; i++) {
		if ((tabWidth + tabItems[i].offsetWidth) < middle) {
			tabWidth += tabItems[i].offsetWidth;
		}
	}
	return tabWidth + left;
};

var tabAllWidth = function() {
	var tabWidth = 0;
	var tabItems = $("#tmsp_tab li");
	for (var i = 0; i < tabItems.length; i++) {
		tabWidth += tabItems[i].offsetWidth;
	}
	return tabWidth;
};

var middleWidth = function() {
	var prevLeft = parseInt($("#sidebar_container").css("width"));
	var prevRight = $("#btn_backward").offset().left;
	var middleWidth = prevRight - prevLeft;
	return middleWidth;
};

$(function() {
	$("[data-addtabs]").on("click", function() {
		addTabs({
			id : $(this).attr("data-addtabs"),
			title : $(this).attr('data-title'),
			url : $(this).attr('data-url'),
			icon : $(this).attr('data-icon'),
			close : true
		});
	});

	$(".nav-tabs").on("click", ".li-tab",
			function() {
				var id = $(this).attr("id");
				if (document.getElementById(id)) {
					$("#sidebar_container .active").removeClass("active");
					$("a[data-addtabs=" + id.replace("tab_", "") + "]")
							.closest("li").addClass("active");
				}
			});

	$(".nav-tabs").on("click", ".close-tab", function() {
		id = $(this).attr("aria-controls");
		closeTab(id);
	});

	$("#welcome_tab").on("click", function() {
		$(".active").removeClass("active");
		$("#sidebar_home").addClass("active");
		$("#home").addClass("active");
	});

	$(window).resize(
			function() {
				$("iframe[data-type=tab_iframe]").attr("height", $(window).height() - 135);
			});

});