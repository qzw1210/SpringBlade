(function () {
	var $ax = function (url, success) {
		this.url = url;
		this.type = "post";
		this.data = {};
		this.dataType = "json";
		this.async = true;
		this.success = success;
	};
	
	$ax.prototype = {
		start : function () {	
			var me = this;
			
			if (this.url.indexOf("?") == -1) {
				this.url = this.url + "?jstime=" + new Date().getTime();
			} else {
				this.url = this.url + "&jstime=" + new Date().getTime();
			}
			
			$.ajax({
		        type: this.type,
		        url: this.url,
		        dataType: this.dataType,
		        async: this.async,
		        data: this.data,
				beforeSend: function(data) {
					//loadDialog(data);
				},
		        success: function(data) {
		        	me.success(data);
		        }
		    });
		}, 
		set : function (key, value) {
			if (typeof key == "object") {
				for (var i in key) {
					if (typeof i == "function")
						continue;
					this.data[i] = key[i];
				}
			} else {
				this.data[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
			}
			return this;
		},
		clear : function () {
			this.data = {};
			return this;
		}
	};
	
	$(document).ajaxStop(function(){
		//closeDialog(data);
	});
	
	window.$ax = $ax;
	
} ());