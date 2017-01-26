<%@include file="../common/csHeader.jsp"%>
<!-- -------------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		
		<h1 class="center blue"
			style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Vehicle
			Permission Report</h1>
	</div>

	<div class="row" style="background: white;">
		<div class="col-sm-12 table-responsive">
			<!-- --------------------- -->

			<div class="form-group col-xs-6">
				
				<div class="form-group col-xs-3">
					<label for="exampleInputName2">Permission Date :</label>
				</div>
				
				<div class="col-xs-9">
					<input type="text" class="form-control datepicker-13" id="permissionDate" 
					name="permissionDate" style="border: 0; border-bottom: 2px ridge;"> 
					<input type="text"
						id="birtAddr" value="${properties['birtAddr']}" hidden="true" />
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
 
	var birtAddr = document.getElementById("birtAddr").value;
	//alert(birtAddr);

	$(document)
			.ready(
					function() {
						var today = new Date();
						var d2= today.getDate();
						var m2= today.getMonth()+1;
						var y2= today.getFullYear();
						var permissionDate=y2+'-'+m2+'-'+d2;

						/* var startDate = new Date(today.getTime() - 30*24*60*60*1000);
						var d1= startDate.getDate();
						var m1= startDate.getMonth()+1;
						var y1= startDate.getFullYear();
						var sDate=y1+'-'+m1+'-'+d1; */
						
						$("#permissionDate").val(permissionDate);
						//$("#param3").val(eDate);
						
						var permissionDate = document.getElementById("permissionDate").value;
						//var param2 = document.getElementById("param2").value;
						//var param3 = document.getElementById("param3").value;
						
						//param1="100.102";
						document.getElementById('requisitionReportFrame').src = birtAddr
								+ '?__report=desco/procurement/cs_vehicle_permission_daily_report.rptdesign&__toolbar=false&__showtitle=false&__title=&P_DAY='
								+ permissionDate;

					});

	function query() {
		var permissionDate = document.getElementById("permissionDate").value;
		//var param2 = document.getElementById("param2").value;
		//var param3 = document.getElementById("param3").value;
		//alert(param2);

		document.getElementById('requisitionReportFrame').src = birtAddr
				+ '?__report=desco/procurement/cs_vehicle_permission_daily_report.rptdesign&__toolbar=false&__showtitle=false&__title=&P_DAY='
				+ permissionDate;
	}
</script>
<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
