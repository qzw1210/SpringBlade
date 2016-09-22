	<link rel="stylesheet" href="${ctxPath}/static/assets/css/datepicker.css" />
	<script src="${ctxPath}/static/assets/js/date-time/bootstrap-datepicker.js"></script>	
	@ var val = x.value!'';
	@ var token = "token_";
	@ if (val != ""){
	@ 	token = "";	
	@}
	<input type="text" id="_${x.index!}" name="${token}${table!x.table}.${x.index!}" ${x.required!} ${x.disabled!}  value="${x.value!}" placeholder="请输入${x.name!}" data-date-format="yyyy-mm-dd" class="form-control" />
	<script type="text/javascript">
		$(function(){
			 $("#_${x.index!}").datepicker({
				    showOtherMonths: true,
				    selectOtherMonths: false,
			 });
 
			 $("#_${x.index!}").bind("focus",function(){
					var _name = $("#_${x.index!}").attr("name").replace("token_", "");
					$("#_${x.index!}").attr("name", _name);
					$("#form_token").val(1);
			 });
		});
	</script>	