<%@include file="../common/reportHeader.jsp"%>
<!-- -------------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		
		<h1 class="center blue"
			style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Material
			List Report According To W/O</h1>
	</div>

	<div class="row" style="background: white;">
		<div class="col-sm-12 table-responsive">
			<!-- --------------------- -->

			<div class="form-group col-xs-6">
				<div class="form-group col-xs-4">
					<label for="exampleInputName2">Work Order No.</label>
				</div>
				<div class="col-xs-8">
					<input type="text" class="form-control" id="param1" name="param1"
						value="CN0001" style="width: 100%"> <input
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
				<!--  <iframe frameborder="0" class="col-md-12" src="http://localhost:8085/birt/frameset?__report=desco/procurement/requisition_report.rptdesign&P_PRF_NO='DESCO/2015-2016/PRF001'"-->
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

						//var param2   = document.getElementById("param2").value;
						//param1="DESCO/2015-2016/PRF001";
						document.getElementById('requisitionReportFrame').src = birtAddr
								+ '?__report=desco/procurement/con_meterial_list_by_wo.rptdesign&__toolbar=false&__showtitle=false&__title=&P_WO_NO='
								+ param1;

					});

	function query() {
		var param1 = document.getElementById("param1").value;
		//var param2   = document.getElementById("param2").value;	
		document.getElementById('requisitionReportFrame').src = birtAddr
				+ '?__report=desco/procurement/con_meterial_list_by_wo.rptdesign&__toolbar=false&__showtitle=false&__title=&P_WO_NO='
				+ param1;
	}
</script>
<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
