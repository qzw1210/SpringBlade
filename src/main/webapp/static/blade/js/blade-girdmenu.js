$.extend({
	contextMenuInit : function(gridtbl, rightMenuHtml, code) {
		// 将页面菜单设置放到此处
		var option = {
			// 重写onContextMenu和onShowMenu事件
			onContextMenu : function(e) { // 显示菜单
				// var rowId = $(e.target).closest("tr.jqgrow").attr("id"); //
				// 获得RowID
				if ($(e.target).attr("id") == "dontShow")
					return false;
				else
					return true;
			},
			onShowMenu : function(e, menu) { // 显示菜单
				return menu;
			},
			menuStyle : { // 菜单样式
				listStyle : 'none',
				padding : '1px',
				margin : '0px',
				backgroundColor : '#fff',
				border : '1px solid #999',
				width : '100px'
			},
			itemHoverStyle : { // 点击菜单上面的样式
				border : '1px solid #999',
				backgroundColor : '#e8e8e8'
			},
			bindings : {
				'rightMenu_add' : function(t) {
					$("#" + code + "_add").click();
				},
				'rightMenu_edit' : function(t) {
					$("#" + code + "_edit").click();
				},
				'rightMenu_remove' : function(t) {
					$("#" + code + "_remove").click();
				},
				'rightMenu_del' : function(t) {
					$("#" + code + "_del").click();
				},
				'rightMenu_view' : function(t) {
					$("#" + code + "_view").click();
				},
				'rightMenu_refresh' : function(t) {
					$("#gotoReset").click();
				},
				'rightMenu_excel' : function(t) {
					var colnames = $(gridtbl)
							.jqGrid('getGridParam', 'colNames');
					var colmodel = $(gridtbl)
							.jqGrid('getGridParam', 'colModel');
					var postdata = $(gridtbl)
							.jqGrid('getGridParam', 'postData');
					var source = (typeof (export_source) == "undefined") ? (code + ".list") : export_source;
					$.post(ctx + "/excel/preExport", {
						code : code,
						colnames : JSON.stringify(colnames),
						colmodel : JSON.stringify(colmodel),
						postdata : JSON.stringify(postdata),
						source : source
					}, function(data) {
						if (data.code === 0) {
							window.top.location.href = ctx + "/excel/export?code=" + data.data;
						} else {
							layer.alert(data.message, {
								icon : 2,
								title : "导出失败"
							});
						}
					}, "json");
				}
			}
		};

		$('<div class="contextMenu" id="myMenu1"></div>').hide().appendTo(
				'body'); // 在页面增加div

		$("#myMenu1").html(rightMenuHtml);

		$(gridtbl).contextMenu("myMenu1", option);
	},
});
