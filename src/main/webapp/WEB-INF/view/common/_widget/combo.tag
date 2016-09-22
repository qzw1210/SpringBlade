				<div class="input-group">
                       <input type="text" class="form-control" id="combo_${x.index!}" ${x.required!} placeholder="${placeholder!}">	
						@ var val = x.value!'';
						@ var token = "token_";
						@ if (val != ""){
						@ 	token = "";	
						@}
                       <input type="hidden" id="_${x.index!}" name="${token}${table!x.table}.${x.index!}">
                       <div class="input-group-btn">
                   		<button type="button" id="btn_${x.index!}" class="btn btn-sx btn-white">
							<span>x</span> 
						</button>
                           <ul class="dropdown-menu dropdown-menu-right" role="menu">
                           </ul>
                       </div>
                       <!-- /btn-group -->
                   </div>
                <script src="${ctxPath}/static/assets/js/bootstrap-suggest.js" type="text/javascript" ></script>
				<script type="text/javascript">
					$(function(){
						 $("#btn_${x.index!}").bind("click",function(){
							    $("#_${x.index!}").val("");
							    $("#combo_${x.index!}").val("");
						 });
						 $("#combo_${x.index!}").bind("focus",function(){
								var _name = $("#_${x.index!}").attr("name").replace("token_", "");
								$("#_${x.index!}").attr("name", _name);
								$("#form_token").val(1);
						 });
						 $.post("${ctxPath}/cache/getCombo", {code:"${num!}"}, function (data) {
								if(data.code===0){
								    $("#combo_${x.index!}").bsSuggest({
								    	idField: "id",       
								    	keyField: "text",       
								        data: {
								            'value':data.data
								        }
								    }).on('onSetSelectValue', function (e, keyword) {
								        $("#_${x.index!}").val(keyword.id);
								    });
								    var comboModle=${model!"null"};
								    if(comboModle==null){return;};
								    var _comboid=comboModle["${x.index!}"];
								    for(var i=0;i<data.data.length;i++){
								    	if(data.data[i].id==_comboid){
										    $("#combo_${x.index!}").val(data.data[i].text);
								    		return;
								    	}
								    }
								}
					     }, "json");
					});
				</script>