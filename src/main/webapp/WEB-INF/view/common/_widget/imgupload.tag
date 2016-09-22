		<div class="form-group">
			<div class="col-sm-10" id="_${x.index!}_BTN">
				<label class="control-label no-padding-right" id="${x.index!}_upload" style="cursor:pointer;"><i class="fa fa-cloud-upload"></i>&nbsp;选择图片</label>&nbsp;&nbsp;
				<label class="control-label no-padding-right" id="${x.index!}_delete" style="cursor:pointer;"><i class="fa fa-times"></i>&nbsp;删除图片</label>
				<input type="hidden" id="_${x.index!}" data-type="imgupload" name="token_${table!x.table}.${x.index!}"  value=""  class="form-control"/>
			</div>
		</div>
		<div class="form-group">
			<div class="col-sm-8">
				<image id="_${x.index!}_IMG" src="${ctxPath}/static/blade/img/img.jpg" data-auto="${x.auto!}" style="padding:2px; border:1px solid #ccc;cursor:pointer;width:${x.width!'170px'};height:${x.height!'110px'};"></image>
			</div>
		</div>
		<div class="form-group" style="display:none;">
			<textarea  id="${x.index!}_editor" name="${x.index!}_editor" ></textarea>
		</div>
		
		<script charset="utf-8" src="${ctxPath}/static/kindeditor/kindeditor.js"></script>
		<script type="text/javascript">
			$(function(){
				$("#_${x.index!}_IMG").bind("click",function() {
					var src = $(this).attr("src");
					if(src != "${ctxPath}/static/blade/img/img.jpg"){
						window.open(src);
					}
				});
				
				$("#${x.index!}_delete").bind("click",function() {
					var _name = $("#_${x.index!}").attr("name").replace("token_", "");
					$("#_${x.index!}").attr("name", _name);
					$("#_${x.index!}").val("");
					$("#_${x.index!}_IMG").attr("src","${ctxPath}/static/blade/img/img.jpg");
					$("#form_token").val(1);
				});
				

				KindEditor.ready(function(K) {
					var editor = K.create('textarea[name="${x.index!}_editor"]', {
						uploadJson : '${ctxPath}/kindeditor/upload_${x.blob!'json'}',
						fileManagerJson : '${ctxPath}/kindeditor/file_manager_json',
						allowFileManager : false
					});
					//----插入图片-----
		            K("#${x.index!}_upload").click(function () {
		                editor.loadPlugin('image', function () {
		                    editor.plugin.imageDialog({
		                        clickFn: function (url,title) {
		        					var _name = $("#_${x.index!}").attr("name").replace("token_", "");
		        					$("#_${x.index!}").attr("name", _name);
		                        	$("#_${x.index!}").val(title);//返回附件表中id
		                            $("#_${x.index!}_IMG").attr("src",url);
		                            $("#form_token").val(1);
		                            editor.hideDialog();
		                        }
		                    });
		                });
		            });
		            //-----插入图片-----
				});
			});
			
			
			
			
			
			function initImgUpload(id, type){
				$.post("${ctxPath}/kindeditor/initimg", {id : id}, function(data){
					if(data.code === 0){
						if(type == "view"){
							$("#_${x.index!}_BTN").css("display", "none");
						}
						$("#_${x.index!}_IMG").attr("src", "${ctxPath}"+ data.data.URL);
					}
					else{
						layer.alert("加载图片失败", {
							icon : 7
						});
					}
					
				}, "json");
			}
			
			
			
		</script>