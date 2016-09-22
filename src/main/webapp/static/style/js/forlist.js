/**
* 表格默认配置
*/
jQuery.extend(jQuery.jgrid.defaults, {
    datatype: "json",
    autowidth: true,
    shrinkToFit: false,
    rownumWidth: 50,
    altRows: true,
    multiselect: true,
    multiboxonly: true,
    jsonReader: { id: "F_IT_XL" }, //将后台返回的xl设置为表的主键
    viewrecords: true,
    rowNum: 10,
    rowList: [10, 20, 30, 50, 100],
    rownumbers: true,
    loadComplete: function () {
        var table = this;
        setTimeout(function () {
            updatePagerIcons(table);
            //自适应表格高度 -- 表格初始化后调用
            autoResize(grid_selector, 223);
            $jqGrid = $(grid_selector);
        }, 0);
    }
});



//replace icons with FontAwesome icons like above
function updatePagerIcons(table) {
    var replacement =
	{
	    'ui-icon-seek-first': 'ace-icon fa fa-angle-double-left bigger-140',
	    'ui-icon-seek-prev': 'ace-icon fa fa-angle-left bigger-140',
	    'ui-icon-seek-next': 'ace-icon fa fa-angle-right bigger-140',
	    'ui-icon-seek-end': 'ace-icon fa fa-angle-double-right bigger-140'
	};
    $('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function () {
        var icon = $(this);
        var $class = $.trim(icon.attr('class').replace('ui-icon', ''));

        if ($class in replacement) icon.attr('class', 'ui-icon ' + replacement[$class]);
    });
};


/**
* jqgrid自适应屏幕
* @author taoyunjie
* @param id,fixHeight
* @returns 
*/
function autoResize(idObj, fixHeight) {
    //初始化时
    $(idObj).setGridHeight($(window).height() - fixHeight);
    //窗口变动时
    $(window).resize(function () {
        $(idObj).setGridHeight($(window).height() - fixHeight);
        $(idObj).jqGrid('setGridWidth', $(".tb-grid").width());
    });
    //菜单收缩时
    var parent_column = $(idObj).closest('[class*="col-"]');
    $(document).on('settings.ace.jqGrid', function (ev, event_name, collapsed) {
        if (event_name === 'sidebar_collapsed' || event_name === 'main_container_fixed') {
            //setTimeout is for webkit only to give time for DOM changes and then redraw!!!
            setTimeout(function () {
                $(idObj).jqGrid('setGridWidth', parent_column.width());
            }, 0);
        }
    });
};


//获取表格单条记录xl（多条时 选取最后选中的）
function getGridXl() {
    return $jqGrid.jqGrid('getGridParam', 'selrow');
}


//获取多行记录的xl值
function getGridXls() {
    return $jqGrid.jqGrid("getGridParam", "selarrrow");
}

function getRowData() {
    return $jqGrid.jqGrid("getRowData", getGridXl()); //根据上面的id获得本行的所有数据
}

//刷新grid
function reloadGrid() {
    exwhere = "";
    var filter = "";
    if (typeof (_filter) != "undefined") {
        filter = _filter;
    }
    $jqGrid.jqGrid('setGridParam', { postData: { where: filter }, page: 1 }).trigger("reloadGrid");
}

//查询grid
function searchGrid() {
    $("#gotoSearch").click();
}
