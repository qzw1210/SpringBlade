//创建和初始化地图函数：
function initMap() {
    //setMapEvent(); //设置地图事件
    addMapControl(); //向地图添加控件
}

function setCenterZoom(markerArr) {
    map = new BMap.Map('map');
    window.map = map; //将map变量存储在全局
    var p0 = markerArr[0].point.split("|")[0];//获得第一个点的经纬度
    var p1 = markerArr[0].point.split("|")[1];
    var poi = new BMap.Point(p0, p1);//设置地图中心点
    map.centerAndZoom(poi, 17);//设置地图的缩放层级
    map.enableScrollWheelZoom();
    map.setMinZoom(11); //地图的最小缩放级别
    map.setMaxZoom(18); //地图的最大缩放级别(百度提供的实际最大可到19)
}

//创建marker
function addMarker(markerArr) {

    map.clearOverlays(); //先清除所有覆盖物

    if (markerArr.length == 0) {
        return false;
    }

    for (var i = 0; i < markerArr.length; i++) {

        var json = markerArr[i];
        var p0 = json.point.split("|")[0];
        var p1 = json.point.split("|")[1];
        var point = new BMap.Point(p0, p1);
        var iconImg = createIcon(json.icon);
        var marker = new BMap.Marker(point, { icon: iconImg });

        //var label = new BMap.Label(json.content.split("|")[0], { offset: new BMap.Size(15, -20) });

        //var result = BMapLib.GeoUtils.isPointInRect(marker.point, map.getBounds());
        //if (result == true) {
            createIcon1(json);
        //}
        //else {
        //    map.removeOverlay(marker);
        //}
    }
}



//创建一个Icon
function createIcon(src) {
    //        var icon = new BMap.Icon("http://app.baidu.com/map/images/us_mk_icon.png", new BMap.Size(json.w, json.h), { imageOffset: new BMap.Size(-json.l, -json.t), infoWindowOffset: new BMap.Size(json.lb + 5, 1), offset: new BMap.Size(json.x, json.h) })
    var icon = new BMap.Icon(src, new BMap.Size(40, 40), { imageOffset: new BMap.Size(0, 0), infoWindowOffset: new BMap.Size(10, 1), offset: new BMap.Size(6, 21) });
    return icon;
}
function createIcon1(json) {
    ComplexCustomOverlay.prototype = new BMap.Overlay();
    var myCompOverlay = new ComplexCustomOverlay(json);
    ComplexCustomOverlay.prototype.initialize = function (map) {
        //var MyPoint = this._point;
        var IconStatus = this._IconStatus;
        //var MyJson = this._json;
        var boxHtml1 = this._IconInfo;
        var boxHtml = this._IconName;


        this._map = map;
        var div = this._div = document.createElement("div");
        div.style.position = "absolute";
        div.style.zIndex = BMap.Overlay.getZIndex(this._point.lat);
        div.style.backgroundColor =(IconStatus=="4"?"#E192C2": (IconStatus == "2" ? "#ff7919" : (IconStatus == "3" ? "#e6e6e6" : "#5eb832")));
        div.style.border = "1px solid " + (IconStatus=="4"?"#D48ABA":(IconStatus == "2" ? "#d9610b" : (IconStatus == "3" ? "#999999" : "#3e881a")));
        div.style.color = IconStatus == 3 ? "black" : "white";
        div.style.height = "18px";
        div.style.padding = "5px";
        div.style.lineHeight = "18px";
        div.style.whiteSpace = "nowrap";
        div.style.MozUserSelect = "none";
        div.style.fontSize = "12px";
        div.style.zIndex = 0;
        div.appendChild(document.createTextNode(boxHtml));


        var arrow = this._arrow = document.createElement("div");
        arrow.style.background = "url(plugins/map/label.png) no-repeat";
        arrow.style.position = "absolute";
        arrow.style.width = "11px";
        arrow.style.height = "9px";
        arrow.style.backgroundPosition = "0px " + (IconStatus=="4"?"-36px":(IconStatus == "2" ? "-9px" : (IconStatus == "3" ? "-18px" : "0px")));
        arrow.style.top = "28px";
        arrow.style.left = "10px";
        arrow.style.overflow = "hidden";
        div.appendChild(arrow);

        div.onmouseover = function () {
            div.style.color = "white";
            this.style.backgroundColor = "#00aced";
            this.style.borderColor = "#1495cc";
            this.style.cursor = "pointer";
            this.style.zIndex = 2;
            arrow.style.backgroundPosition = "0px -27px";
            $(this).html(boxHtml1);
            div.appendChild(arrow);
        };

        div.onmouseout = function () {
            this.style.zIndex = 0;
            div.style.color = IconStatus == 3 ? "black" : "white";
            this.style.backgroundColor = (IconStatus=="4"?"#E192C2": (IconStatus == "2" ? "#ff7919" : (IconStatus == "3" ? "#e6e6e6" : "#5eb832")));
            this.style.borderColor = (IconStatus=="4"?"#D48ABA": (IconStatus == "2" ? "#d9610b" : (IconStatus == "3" ? "#999999" : "#3e881a")));
            arrow.style.backgroundPosition = "0px " + (IconStatus=="4"?"-36px":(IconStatus == "2" ? "-9px" : (IconStatus == "3" ? "-18px" : "0px")));
            $(this).html(boxHtml);
            div.appendChild(arrow);
        };
        div.onclick = function () {//点击事件

        };

        map.getPanes().labelPane.appendChild(div);

        return div;
    };

    ComplexCustomOverlay.prototype.draw = function () {
        var map = this._map;
        var pixel = map.pointToOverlayPixel(this._point);
        this._div.style.left = pixel.x - parseInt(this._arrow.style.left) + "px";
        this._div.style.top = pixel.y - 30 + "px";
    };
    map.addOverlay(myCompOverlay);

}

function ComplexCustomOverlay(json) {
    this._point = new BMap.Point(json.point.split("|")[0], json.point.split("|")[1]);
    this._IconStatus = json.IconStatus;
    this._json = json;
    this._IconInfo = json.IconInfo;
    this._IconName = json.IconName;
}


function AddPolyline(markerArr, strokeColor) {
    var polylinePointsArray = [];
    for (var i = 0; i < markerArr.length; i++) {
        var P = markerArr[i].point;
        var p0 = P.split("|")[0];
        var p1 = P.split("|")[1];
        polylinePointsArray[i] = new BMap.Point(p0, p1);
    }
    polyline = new BMap.Polyline(polylinePointsArray, { strokeColor: strokeColor, strokeWeight: 3, strokeOpacity: 0.5 });
    map.addOverlay(polyline);
}




//地图事件设置函数：
function setMapEvent() {
    map.enableScrollWheelZoom(); //启用地图滚轮放大缩小
    map.addEventListener("dragend", dargendHandler); //拖动结束，更新搜索结果
    setAddLpMarkerEvent();

    map.addEventListener("zoomstart", zoomStartHandler);
    map.addEventListener("zoomend", skipCertainLevel);
}

function setAddLpMarkerEvent() {
    map.addEventListener("tilesloaded", addMarker);
    map.addEventListener("zoomend", addMarker);
    map.addEventListener("moveend", moveend);
}

function setRemoveLpMarkerEvent() {
    map.removeEventListener("tilesloaded", addMarker);
    map.removeEventListener("zoomend", addMarker);
    map.removeEventListener("moveend", moveend);
}

function moveend() {
    loadmap(index);
}
//地图控件添加函数：
function addMapControl() {
    //    //向地图中添加缩放控件
    //    var ctrl_nav = new BMap.NavigationControl({ anchor: BMAP_ANCHOR_TOP_LEFT, type: BMAP_NAVIGATION_CONTROL_LARGE });
    //    map.addControl(ctrl_nav);

    //向地图中添加比例尺控件
    var ctrl_sca = new BMap.ScaleControl({ anchor: BMAP_ANCHOR_BOTTOM_LEFT });
    map.addControl(ctrl_sca);
}