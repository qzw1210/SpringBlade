
	<link rel="stylesheet" href="${ctxPath}/static/kindeditor/themes/default/default.css" />
	<link rel="stylesheet" href="${ctxPath}/static/kindeditor/plugins/code/prettify.css" />
	<script charset="utf-8" src="${ctxPath}/static/kindeditor/kindeditor.js"></script>
	<script charset="utf-8" src="${ctxPath}/static/kindeditor/lang/zh_CN.js"></script>
	<script charset="utf-8" src="${ctxPath}/static/kindeditor/plugins/code/prettify.js"></script>
	<script>
		KindEditor.ready(function(K) {
			var options={
					cssPath : '${ctxPath}/static/kindeditor/plugins/code/prettify.css',
					uploadJson : '${ctxPath}/kindeditor/upload_json',
					//uploadJson : '${ctxPath}/image/upload_blob',
					fileManagerJson : '${ctxPath}/kindeditor/file_manager_json',
					items: ['source', '|', 'fontname', 'fontsize', '|', 'forecolor', 'bold', 'italic', 'underline', '|', 'justifyleft', 'justifycenter', 'justifyright', '|', 'image', 'multiimage', '|', 'plainpaste', 'wordpaste', '|','fullscreen'],
					allowFileManager : true,
					readonlyMode : ${readOnly!false},
					afterFocus:function(){
						var _name = $("#_${x.index!}").attr("name").replace("token_", "");
						$("#_${x.index!}").attr("name", _name);
						$("#form_token").val(1);
					},
					afterCreate : function() {
						var self = this;
						$("#btn_save").bind("click",function(){
							$("#_${x.index!}").val(editor.html());
						});
					}
				};
			var editor = K.create('textarea[name="token_${table!x.table}.${x.index!}"]', options);
		});
	</script>	
	@ var val = x.value!'';
	@ var token = "token_";
	@ if (val != ""){
	@ 	token = "";	
	@}
	<textarea  id="_${x.index!}" name="${token}${table!x.table}.${x.index!}" ${x.required!}   class="form-control" cols="100" rows="8" style="visibility:hidden;width:100%;height:${x.height!'200px'};">${x.value!}</textarea>

