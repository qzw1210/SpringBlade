		<div class="form-group" style="display:${display!'block'}">
			<div class="col-sm-10" id="_${x.index!}_BTN">
				<label class="control-label no-padding-right" id="${x.index!}_upload" style="cursor:pointer;"><i class="fa fa-cloud-upload"></i>&nbsp;选择附件</label>			
				<input type="hidden" id="_${x.index!}" data-type="fileupload" name="${table!x.table}.${x.index!}"  value=""  class="form-control"/>
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-12" id="${x.index!}_file">
			
			</div>
		</div>
		<div class="form-group" style="display:none;">
			<textarea  id="${x.index!}_editor" name="${x.index!}_editor" ></textarea>
		</div>
		
		<script charset="utf-8" src="${ctxPath}/static/kindeditor/kindeditor.js"></script>
		<script type="text/javascript">
			$(function(){
				KindEditor.ready(function(K) {
					var editor = K.create('textarea[name="${x.index!}_editor"]', {
						uploadJson : '${ctxPath}/kindeditor/upload_${x.blob!'json'}',
						fileManagerJson : '${ctxPath}/kindeditor/file_manager_json',
						allowFileManager : false
					});
					//----插入文件-----
		            K("#${x.index!}_upload").click(function () {
		                editor.loadPlugin('insertfile', function () {
		                    editor.plugin.fileDialog({
		                        clickFn: function (url,title) {
		            				var id = title.split('|')[0];
		            				var name = title.split('|')[1];
	            					var ids = $("#_${x.index!}").val();
	            					if(ids == ""){
	            						ids = id;
	            					}
	            					else{
		            					ids += "," + id;
	            					}
	            					$("#_${x.index!}").val(ids);//返回附件表中id
		                            $("#${x.index!}_file").append(_${x.index!}_getFile(url, id, name));
		                            $("#form_token").val(1);
		                            editor.hideDialog();
		                        }
		                    });
		                });
		            });
		            //-----插入文件-----
				});
				
				
			});
			
			
			function _${x.index!}_initFileUpload(ids, type){
				$.ajax({
			        type: "post",
			        url: "${ctxPath}/kindeditor/initfile",
			        dataType: "json",
			        async: false,
			        data: {ids : ids},
			        success: function(data) {
			        	if(data.code === 0){
							var file = data.data;
							for(var x = 0; x < file.length; x++){
								var id = file[x].id;
								var name = file[x].name;
								var url = "${ctxPath}/kindeditor/renderFile/" + id;
								if(type == "edit"){
									$("#${x.index!}_file").append(_${x.index!}_getFile(url, id, name));
								}
								else{
									$("#_${x.index!}_BTN").css("display", "none");
									$("#${x.index!}_file").append(_${x.index!}_getViewFile(url, id, name));
								}
								
							}
						}
						else{
							layer.alert("加载附件失败", {
								icon : 7
							});
						}
			        }
			    });
			}
			
			function _${x.index!}_getFile(url, id, name){
				var html = [];
				html.push('<div class="alert alert-block alert-success" id="${x.index!}_file_' + id + '">');
				html.push("	<button type=\"button\" class=\"close\" onclick=\"_${x.index!}_deleteFile(" + id + ",'" + name + "')\">");
				html.push('		<i class="ace-icon fa fa-times"></i>');
				html.push('	</button>');
				html.push('	<i class="ace-icon fa fa-cloud-download green"></i>');
				html.push('	<strong class="green">');
				html.push('		<a class="green" href="'+url+'" target="_blank">' + name + '</a>');
				html.push('	</strong>');
				html.push('</div>');
				return html.join('');
			}
			
			function _${x.index!}_getViewFile(url, id, name){
				var html = [];
				html.push('<div class="alert alert-block alert-success" id="${x.index!}_file_' + id + '">');
				html.push('	<i class="ace-icon fa fa-cloud-download green"></i>');
				html.push('	<strong class="green">');
				html.push('		<a class="green" href="'+url+'" target="_blank">' + name + '</a>');
				html.push('	</strong>');
				html.push('</div>');
				return html.join('');
			}
			
			function _${x.index!}_deleteFile(id, name){
				 layer.confirm('是否删除选中文件 (' + name + ') ？', {
		                icon: 3,
		                btn: ['确定', '取消'] //按钮
		            }, function () {
		           		$("#${x.index!}_file_" + id).remove();
		           		var ids = $("#_${x.index!}").val();
		           		
		           		var idArr = ids.split(",");
		           		if(_contains(idArr, id)){
		           			idArr.remove(id);
		           		}
		           		
						var newids = idArr.join(",");
						if(newids == ""){
							newids == "0";
						}
						
		           		$("#_${x.index!}").val(newids);
		           	 	$("#form_token").val(1);
		           		layer.msg("删除成功!", {icon:1});
		            }, function () {
		                //layer.msg('已取消');
		            });
			}
			
			function _contains(arr, obj){
				for (var i = 0; i < arr.length; i++) {
			        if (obj == arr[i] || obj.toString() == arr[i]) {
			            return true;
			        }
			    }
			    return false;
			}
			
		</script>