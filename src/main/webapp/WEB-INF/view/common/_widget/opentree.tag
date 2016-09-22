

<input type="text" id="_${x.index!}_INPUT" ${x.required!} placeholder="${placeholder!}" class="form-control" />
<input type="hidden" id="_${x.index!}" data-type="opentree" name="token_${table!x.table}.${x.index!}" value="${x.value!}" />


<script type="text/javascript">
	$(function(){
		$("#_${x.index!}_INPUT").bind("click",function(){
			var val = $("#_${x.index!}").val();
			val = (val == "") ? 0 : val;
			layer.open({
        	    type: 2,
        	    title:"树形选择",
        	    area: ["250", "420px"],
        	    fix: false, //不固定
        	    maxmin: true,
        	    content: "${ctxPath}/ztree/open/?type=${x.type!0}&index=_${x.index!0}&name=${x.name!0}&source=${x.source!0}&check=${x.check!0}&where=${x.where!0}&intercept=${x.intercept!0}&ext=${x.ext!0}&val=" + val
        	});
		});
		
		if($("#_${x.index!}").val() != ""){
			initOpenTree($("#_${x.index!}").val());
		}
		
		
	});
	
	function initOpenTree(val){
		$.post("${ctxPath}/ztree/getTreeListName",{source:"${x.source!}", type:"${x.type!}", where:"${x.where!}", intercept:"${x.intercept!}", val:val},function(data){
			if(data.code === 0){
				$("#_${x.index!}_INPUT").val(data.data);
			}
		}, "json");
	}
	
</script>