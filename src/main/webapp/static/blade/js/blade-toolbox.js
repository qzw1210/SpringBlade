	
	/********************** 公共工具类 ***************/
	var BladeTool = {
	    isNotEmpty: function(val) {
	        return ! this.isEmpty(val);
	    },
	    isEmpty: function(val) {
	        if ((val == null || typeof(val) == "undefined") || (typeof(val) == "string" && val == "" && val != "undefined")) {
	            return true;
	        } else {
	            return false;
	        }
	    },
	    isDebug: function() {
	        if (this.isNotEmpty(configDebug) && configDebug == "true") {
	            return true;
	        } else {
	            return false;
	        }
	    },
	    format: function(val) {
	        if (this.isEmpty(val)) {
	            return "";
	        } else {
	            return val;
	        }
	    },
	    //去除元素内所有内容 strIds："#id1,#id2,#id3"
	    emptyHtml: function(strIds) {
	        try {
	            var ids = strIds.trim(",").split(",");
	            $(ids).each(function() {
	                var obj = $(this.toString());
	                if (obj.length > 0) {
	                    $(obj).each(function() {
	                        $(this).html("");
	                    });
	                } else {
	                    obj.html("");
	                }
	            });
	        } catch(ex) {
	            if (BladeTool.isDebug()) {
	                throw new Error("js方法：【BladeTool.emptyHtml(strIds)】，error！");
	            }
	        }
	    },
	    //去除元素的值 strIds："#id1,#id2,#id3"
	    emptyValue: function(strIds) {
	        try {
	            var ids = strIds.trim(",").split(",");
	            $(ids).each(function() {
	                var obj = $(this.toString());
	                if (obj.length > 0) {
	                    $(obj).each(function() {
	                        $(this).val("");
	                    });
	                } else {
	                    obj.val("");
	                }
	            });
	        } catch(ex) {
	            if (BladeTool.isDebug()) {
	                throw new Error("js方法：【BladeTool.emptyValue(strIds)】，error！");
	            }
	        }
	    },
	    //去除Textarea内所有内容 strIds："#id1,#id2,#id3"
	    emptyTextarea: function(strIds) {
	        try {
	            var ids = strIds.trim(",").split(",");
	            $(ids).each(function() {
	                var obj = $(this.toString());
	                if (obj.length > 0) {
	                    $(obj).each(function() {
	                        $(this).empty();
	                        $(this).val("");
	                    });
	                } else {
	                    obj.empty();
	                    obj.val("");
	                }
	            });
	        } catch(ex) {
	            if (BladeTool.isDebug()) {
	                throw new Error("js方法：【BladeTool.emptyTextarea(strIds)】，error！");
	            }
	        }
	    }
	};
	
	
	/********************** date工具类 ***************/
	Date.prototype.format = function(format) {
	    var o = {
	        "M+": this.getMonth() + 1,
	        //month
	        "d+": this.getDate(),
	        //day
	        "h+": this.getHours(),
	        //hour
	        "m+": this.getMinutes(),
	        //minute
	        "s+": this.getSeconds(),
	        //second
	        "q+": Math.floor((this.getMonth() + 3) / 3),
	        //quarter
	        "S": this.getMilliseconds() //millisecond
	    };
	    if (/(y+)/.test(format)) format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	    for (var k in o) if (new RegExp("(" + k + ")").test(format)) format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
	    return format;
	};
	
	/********************** String工具类***************/
	
	//trim去掉字符串两边的指定字符,默去空格
	String.prototype.trim = function(tag) {
	    if (!tag) {
	        tag = '\\s';
	    } else {
	        if (tag == '\\') {
	            tag = '\\\\';
	        } else if (tag == ',' || tag == '|' || tag == ';') {
	            tag = '\\' + tag;
	        } else {
	            tag = '\\s';
	        }
	    }
	    eval('var reg=/(^' + tag + '+)|(' + tag + '+$)/g;');
	    return this.replace(reg, '');
	};
	
	//字符串截取后面加入...
	String.prototype.interceptString = function(len) {
	    if (this.length > len) {
	        return this.substring(0, len) + "...";
	    } else {
	        return this;
	    }
	};
	
	//将一个字符串用给定的字符变成数组
	String.prototype.toArray = function(tag) {
	    if (this.indexOf(tag) != -1) {
	        return this.split(tag);
	    } else {
	        if (this != '') {
	            return [this.toString()];
	        } else {
	            return [];
	        }
	    }
	};
	
	//只留下数字(0123456789)
	String.prototype.toNumber = function() {
	    return this.replace(/\D/g, "");
	};
	
	//保留中文  
	String.prototype.toCN = function() {
	    var regEx = /[^\u4e00-\u9fa5\uf900-\ufa2d]/g;
	    return this.replace(regEx, '');
	};
	
	//转成int
	String.prototype.toInt = function() {
	    var temp = this.replace(/\D/g, "");
	    return isNaN(parseInt(temp)) ? this.toString() : parseInt(temp);
	};
	
	//是否是以XX开头
	String.prototype.startsWith = function(tag) {
	    return this.substring(0, tag.length) == tag;
	};
	
	//是否已XX结尾
	String.prototype.endWith = function(tag) {
	    return this.substring(this.length - tag.length) == tag;
	};
	
	//StringBuffer
	var StringBuffer = function() {
	    this._strs = new Array;
	};
	
	StringBuffer.prototype.append = function(str) {
	    this._strs.push(str);
	};
	
	StringBuffer.prototype.toString = function() {
	    return this._strs.join("");
	};
	
	String.prototype.replaceAll = function(s1, s2) {
	    return this.replace(new RegExp(s1, "gm"), s2);
	};
	
	String.prototype.trimLen = function(maxLen) {
		var len = 0;
		var _str = "";
		for ( var i = 0; i < this.length; i++) {
			var c = this.charCodeAt(i);
			if ((c >= 0x0001 && c <= 0x007e) || (0xff60 <= c && c <= 0xff9f)) {
				_str = _str + this.charAt(i);
				len++;
			} else {
				_str = _str + this.charAt(i);
				len += 2;
			}
			if (len >= maxLen) {
				return _str;
			}
		}
		return _str;
	};
	
	/********************** Arry ***************/
	//根据数据取得再数组中的索引
	Array.prototype.indexOf = function(obj) {
	    for (var i = 0; i < this.length; i++) {
	        if (obj == this[i]) {
	            return i;
	        }
	    }
	    return - 1;
	};
	//移除数组中的某元素
	Array.prototype.remove = function(obj) {
	    for (var i = 0; i < this.length; i++) {
	        if (obj == this[i]) {
	            this.splice(i, 1);
	            break;
	        }
	    }
	    return this;
	};
	//判断元素是否在数组中
	Array.prototype.contains = function(obj) {
	    for (var i = 0; i < this.length; i++) {
	        if (obj == this[i]) {
	            return true;
	        }
	    }
	    return false;
	};
	
	/********************** 浏览器相关操作 ***************/
	
	//进入全屏模式,  判断各种浏览器，找到正确的方法
	var launchFullScreen = function(element) {
	    if (element.requestFullscreen) {
	        element.requestFullscreen();
	    } else if (element.mozRequestFullScreen) {
	        element.mozRequestFullScreen();
	    } else if (element.webkitRequestFullscreen) {
	        element.webkitRequestFullscreen();
	    } else if (element.msRequestFullscreen) {
	        element.msRequestFullscreen();
	    }
	    return true;
	};
	
	//退出全屏模式
	var exitFullScreen = function() {
	    if (document.exitFullscreen) {
	        document.exitFullscreen();
	    } else if (document.mozCancelFullScreen) {
	        document.mozCancelFullScreen();
	    } else if (document.webkitExitFullscreen) {
	        document.webkitExitFullscreen();
	    }
	    return false;
	};
	
	//cookie操作
	var CookieUtil = {
	    path: "/",
	    domain: 'demo.j2ee.com',
	    add: function(name, val) {
	        $.cookie(name, val, {
	            expires: 7,
	            path: this.path,
	            domain: this.domain,
	            secure: true
	        });
	    },
	    remove: function(name) {
	        $.cookie(name, null, {
	            path: this.path,
	            domain: this.domain
	        });
	    },
	    get: function(name) {
	        $.cookie(name, {
	            path: this.path,
	            domain: this.domain
	        });
	    }
	};
	
	var CookieKit = {
		set : function (name, value) {
			var Days = 30;
			var exp = new Date();
			exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
			document.cookie = name + "=" + escape (value) + ";expires=" + exp.toGMTString();
		},
		get : function (name) {
			var arr,reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
			if(arr = document.cookie.match(reg))
				return unescape(arr[2]);
			else
				return null;
		},
		remove : function (name) {
			var exp = new Date();
			exp.setTime(exp.getTime() - 1);
			var cval = this.get(name);
			if(cval != null)
			document.cookie = name + "=" + cval + ";expires=" + exp.toGMTString();
		}
	}
	
	//#################################  layer  ########################################

	/*
	 * layer弹出框
	 */
	function layer_alert(msg, type) {
		if (type == "success") {
			layer.alert(msg, {
				icon : 1
			});
			return;
		}
		if (type == "error") {
			layer.alert(msg, {
				icon : 2
			});
			return;
		}
		if (type == "ask") {
			layer.alert(msg, {
				icon : 3
			});
			return;
		}
		if (type == "warn") {
			layer.alert(msg, {
				icon : 7
			});
			return;
		}
	}

	function layer_post(data) {
		if (data.code === 0) {
			layer.alert(data.message, {
				icon : 1
			});
			return;
		}
		if (data.code === 1 || data.code === 999) {
			layer.alert(data.message, {
				icon : 2
			});
			return;
		}
		if (data.code === 2) {
			layer.alert(data.message, {
				icon : 7
			});
			return;
		}
		if (data.code === 3) {
			layer.alert(data.message, {
				icon : 7
			});
			return;
		}
	}

	function appLoading() {
		return layer.load(0, {
			shade : false
		});
	}

	function clearLoading(index) {
		layer.close(index);
	}

	//##########################-↑-##########################-↑-##########################

	//#################################  传参   ########################################

	// 传递参数解析, 根据参数名,解析 url 传递的参数值.
	function request(paras) {
		var url = location.href;
		var paraString = url.substring(url.indexOf("?") + 1, url.length).split("&");
		var paraObj = {};
		for ( var i = 0; j = paraString[i]; i++) {
			paraObj[j.substring(0, j.indexOf("=")).toLowerCase()] = j.substring(j
					.indexOf("=") + 1, j.length);
		}
		var returnValue = paraObj[paras.toLowerCase()];
		if (typeof (returnValue) == "undefined") {
			return "";
		} else {
			return returnValue;
		}
	};

	//##########################-↑-##########################-↑-##########################

	