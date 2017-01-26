<%@include file="../common/csHeader.jsp"%>
<!-- -------------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<!-- <h4>
			<a href="#" style="text-decoration: none;">Central Store</a> /Report
		</h4>
		<div class="o_form_buttons_edit col-sm-6" style="display: block;">
			<a href="${pageContext.request.contextPath}/createVendor.do"
				style="text-decoration: none;" class="btn btn-success btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Create Vendor
			</a> 


			<button accesskey="D" class="btn btn-info btn-sm" type="button">
					Discard</button>
		</div> -->
		<h1 class="center blue"
			style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Ledger
			Report</h1>
	</div>

	<div class="row" style="background: white;">
		<div class="col-sm-12 table-responsive">
			<!-- --------------------- -->

			<div class="form-group col-xs-3">
				<div class="form-group col-xs-4">
					<label for="exampleInputName2">Item Code:</label>
				</div>
				<div class="col-xs-8">
					<input type="text" class="form-control" id="param1" name="param1"
						value="100.103" style="width: 100%"> <input type="text"
						id="birtAddr" value="${properties['birtAddr']}" hidden="true" />
				</div>


			</div>

			<div class="form-group col-xs-4">
				<div class="form-group col-xs-3">
					<label for="exampleInputName2">Start Date :</label>
				</div>
				<div class="col-xs-9">					
					<input type="text" class="form-control datepicker-13"
					id="param2" style="border: 0; border-bottom: 2px ridge;"
					name="startDate" />
				</div>


			</div>


			<div class="form-group col-xs-4">
				<div class="form-group col-xs-3">
					<label for="exampleInputName2">End Date :</label>
				</div>
				<div class="col-xs-9">
					<input id="param3" class="form-control datepicker-13" 
					type="text" name="endDate" style="border: 0; border-bottom: 2px ridge;">
				</div>





			</div>
			<div class="form-group col-xs-1">
				<!-- <div class="form-group col-xs-2">
					<label for="exampleInputName2">Others</label>
				</div>

				<div class="col-xs-9">
					<input type="text" class="form-control" id="param2" name="param2"
						placeholder="" style="width: 100%">
				</div> -->

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
/* $(function() {
	$(".datepicker-13").datepicker({
		dateFormat : 'yy-mm-dd'
	});
	$(".datepicker-13").datepicker("option", "maxDate", new Date());
	$(".datepicker-13").datepicker("hide");
});
 */
 
 
 
	var birtAddr = document.getElementById("birtAddr").value;
	//alert(birtAddr);

	$(document)
			.ready(
					function() {
						var today = new Date();
						var d2= today.getDate();
						var m2= today.getMonth()+1;
						var y2= today.getFullYear();
						var eDate=y2+'-'+m2+'-'+d2;

						var startDate = new Date(today.getTime() - 30*24*60*60*1000);
						var d1= startDate.getDate();
						var m1= startDate.getMonth()+1;
						var y1= startDate.getFullYear();
						var sDate=y1+'-'+m1+'-'+d1;
						
						$("#param2").val(sDate);
						$("#param3").val(eDate);
						
						var param1 = document.getElementById("param1").value;
						var param2 = document.getElementById("param2").value;
						var param3 = document.getElementById("param3").value;
						
						//param1="100.102";
						document.getElementById('requisitionReportFrame').src = birtAddr
								+ '?__report=desco/procurement/cs_item_wise_ledger_report.rptdesign&__toolbar=false&__showtitle=false&__title=&P_ITEM_ID='
								+ param1+'&P_FROM_DATE='+param2+'&P_TO_DATE='+param3;

					});

	function query() {
		var param1 = document.getElementById("param1").value;
		var param2 = document.getElementById("param2").value;
		var param3 = document.getElementById("param3").value;
		//alert(param2);

		document.getElementById('requisitionReportFrame').src = birtAddr
				+ '?__report=desco/procurement/cs_item_wise_ledger_report.rptdesign&__toolbar=false&__showtitle=false&__title=&P_ITEM_ID='
				+ param1+'&P_FROM_DATE='+param2+'&P_TO_DATE='+param3;
	}
</script>
<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
