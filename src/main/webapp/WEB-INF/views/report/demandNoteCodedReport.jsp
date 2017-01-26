<%@include file="../common/settingsHeader.jsp"%>
<!-- -------------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">

		<h1 class="center blue"
			style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Demand Note Coded</h1>
	</div>
	<div class="row" style="background: white;">
		<div class="col-sm-12 table-responsive">
			<!-- --------------------- -->

			<div class="form-group col-xs-4">
				<div class="form-group col-xs-3">
					<label for="exampleInputName2">Item Type.</label>
				</div>
				<div class="col-xs-9">
					<input type="text" class="form-control" id="param1" name="param1"
						value="C" style="width: 100%"> <input
						type="text" id="birtAddr" value="${properties['birtAddr']}"
						hidden="true" />
				</div>


			</div>
					<div class="form-group col-xs-4">
				<div class="form-group col-xs-3">
					<label for="exampleInputName2">Session Id :</label>
				</div>
				<div class="col-xs-9">					
					<input type="text" class="form-control"
					id="param2"  name="param2" value="21"style="width: 100%"> <input
						type="text" id="birtAddr" value="${properties['birtAddr']}"
						hidden="true" />

</div>
			</div>


					<div class="form-group col-xs-4">
				<div class="form-group col-xs-3">
					<label for="exampleInputName2">Category Id :</label>
				</div>
				<div class="col-xs-9">					
					<input type="text" class="form-control"
					id="param3"  name="param3" value="401"style="width: 100%"> <input
						type="text" id="birtAddr" value="${properties['birtAddr']}"
						hidden="true" />

</div>
			</div>
			
			
								<div class="form-group col-xs-4">
				<div class="form-group col-xs-3">
					<label for="exampleInputName2">Annexure Type:</label>
				</div>
				<div class="col-xs-9">					
					<input type="text" class="form-control"
					id="param4"  name="param4" value="1"style="width: 100%"> <input
						type="text" id="birtAddr" value="${properties['birtAddr']}"
						hidden="true" />

</div>
			</div>
			
			
			
								<div class="form-group col-xs-4">
				<div class="form-group col-xs-3">
					<label for="exampleInputName2">Department Id:</label>
				</div>
				<div class="col-xs-9">					
					<input type="text" class="form-control"
					id="param5"  name="param5" value="17"style="width: 100%"> <input
						type="text" id="birtAddr" value="${properties['birtAddr']}"
						hidden="true" />

</div>
			</div>
			
			
											<div class="form-group col-xs-4">
				<div class="form-group col-xs-3">
					<label for="exampleInputName2">Item Id:</label>
				</div>
				<div class="col-xs-9">					
					<input type="text" class="form-control"
					id="param6"  name="param6" value="401.101"style="width: 100%"> <input
						type="text" id="birtAddr" value="${properties['birtAddr']}"
						hidden="true" />

</div>
			</div>
			
			<div class="form-group col-xs-6">
				
				<div class="col-xs-1">
					<button type="button" class="btn btn-sm btn-info"
						onclick="query();">Query</button>
				</div>

			</div>


			<div class="col-xs-12">
				<iframe frameborder="0" class="col-md-12"
					style="margin: 10px 0 10px 0; height: 410px;"
					id="requisitionReportFrame">
					<p>Your browser does not support iframes.</p>
				</iframe>
			</div>

			<!-- --------------------- -->
		</div>
	</div>
</div>


<script type="text/javascript">
	/* var birtAddr =${properties['birtAddr']};
	alert(birtAddr); */

	var birtAddr = document.getElementById("birtAddr").value;
	//alert(birtAddr);

	$(document)
			.ready(
					function() {
						var param1 = document.getElementById("param1").value;
						var param2 = document.getElementById("param2").value;
						var param3 = document.getElementById("param3").value;
						var param4 = document.getElementById("param4").value;
						var param5 = document.getElementById("param5").value;
						var param6 = document.getElementById("param6").value;

						//param1="SR/2015/000015";
						document.getElementById('requisitionReportFrame').src = birtAddr
								+ '?__report=desco/procurement/demand_note_coded.rptdesign&__toolbar=false&__showtitle=false&__title=&P_ITEM_TYPE='
								+ param1+'&P_SESSION_ID='+param2+'&P_CATEGORY_ID='+param3+'&P_ANNEXURE_TYPE='+param4+'&P_DEPARTMENT_ID='+param5+'&P_ITEM_ID='+param6;

					});

		
	function query() {
		var param1 = document.getElementById("param1").value;
		var param2 = document.getElementById("param2").value;
		var param3 = document.getElementById("param3").value;
		var param4 = document.getElementById("param4").value;
		var param5 = document.getElementById("param5").value;
		var param6 = document.getElementById("param6").value;
		document.getElementById('requisitionReportFrame').src = birtAddr
		+ '?__report=desco/procurement/demand_note_coded.rptdesign&__toolbar=false&__showtitle=false&__title=&P_ITEM_TYPE='
		+ param1+'&P_SESSION_ID='+param2+'&P_CATEGORY_ID='+param3+'&P_ANNEXURE_TYPE='+param4+'&P_DEPARTMENT_ID='+param5+'&P_ITEM_ID='+param6;

	}
</script>
<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
