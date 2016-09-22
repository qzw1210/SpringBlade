	<script src="${ctxPath}/static/laydate/laydate.js"></script>	
	<script type="text/javascript"> 
		$(function(){
			var _elem="_${x.index}";
			laydate({
			    elem: '#'+_elem,
			    format: '${x.format!'YYYY-MM-DD hh:mm:ss'}', // 分隔符可以任意定义，该例子表示只显示年月日
			    //festival: true, //显示节日
			    choose: function(datas){ //选择日期完毕的回调
			      /*   alert('得到：'+datas); */
			    }
			});
			
			 $("#_${x.index!}").bind("focus",function(){
					var _name = $("#_${x.index!}").attr("name").replace("token_", "");
					$("#_${x.index!}").attr("name", _name);
					$("#form_token").val(1);
			 });
		});	
	</script>	
	@ var val = x.value!'';
	@ var token = "token_";
	@ if (val != ""){
	@ 	token = "";	
	@}
	<input type="text" id="_${x.index!}" name="${token}${table!x.table}.${x.index!}" class="form-control" ${x.required!} ${x.disabled!}  value="${x.value!}" placeholder="请输入${x.name!}"  />