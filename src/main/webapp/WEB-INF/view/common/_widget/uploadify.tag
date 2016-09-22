	<form  method="post" enctype="multipart/form-data">
		<input type="file" name="file_upload" id="file_upload"/>
		<div id="img_upload">
		
		</div>
	</form>
	<link rel="stylesheet" type="text/css" href="${ctxPath}/static/uploadify/uploadify.css">
	<script src="${ctxPath}/static/uploadify/jquery.uploadify.min.js" type="text/javascript"></script>
		<script type="text/javascript">
		$(function(){
			$("#file_upload").uploadify({
				//校验数据
				'swf' : '${ctxPath}/static/uploadify/uploadify.swf', //指定上传控件的主体文件
				//'uploader' : '${ctxPath}/uploadify/uploadBlob', //上传保存到blob字段并返回读取接口
				'uploader' : '${ctxPath}/uploadify/upload',//上传保存文件并返回路径
				'auto' : true, //自动上传
				//'buttonImage' : '${ctxPath}/static/uploadify/uploadify-upload.png', //浏览按钮背景图片
				'multi' : true, //单文件上传
				'fileTypeExts' : '*.gif; *.jpg; *.png; *.flv; *.avi; *.mp4; *.mp3', //允许上传的文件后缀
				'fileSizeLimit' : '300MB', //上传文件的大小限制，单位为B, KB, MB, 或 GB
				'successTimeout' : 30, //成功等待时间
				'onUploadSuccess' : function(file, data, response) {
					//每成功完成一次文件上传时触发一次
					var image=eval("["+data+']')[0];
					
					$("#img_upload").append("<li><img width=80 src="+image.url+" </li>");
					
				},
				'onUploadError' : function(file, data, response) {
					//当上传返回错误时触发
					
				}
			});
		});
	</script>

