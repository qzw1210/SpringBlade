	<label class="pull-left inline" style="margin-top:5px;">
		<input id="${x.index!}_chb" type="checkbox" ${x.checked!} ${disabled!} class="ace ace-switch ace-switch-5" />
		<span class="lbl middle"></span>
	</label>	
	
	<input type="hidden" id="_${x.index!}" name="${table!x.table}.${x.index!}" value="0">
	
	<script type="text/javascript">
		$(function(){
			$("#${x.index!}_chb").bind("click",function(){
				$("#form_token").val(1);
				
				if($("#${x.index!}_chb").is(":checked")){
					$("#_${x.index!}").val("1");
				}
				else{
					$("#_${x.index!}").val("0");
				}
			});
		});
	</script>