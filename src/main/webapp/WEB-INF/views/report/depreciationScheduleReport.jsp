<%@include file="../common/faHeader.jsp"%>
		<link href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel="stylesheet">
<!-- -------------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 0 10px; padding-left: 20px">

		<input type="hidden" id="birtAddr" value="${properties['birtAddr']}" />
		<!--<input type="hidden" id="departmentId" value="${department.id}" />  -->

		<div class="">
			<table class="table table-bordered table-hover">
				<tr>
					<td colspan="5"
						style="font-weight: normal; font-size: 18px; text-align: center; font-family: 'Ubuntu Condensed', sans-serif;">
						Category Wise Asset Report</td>
				</tr>
				<tr>				
						<td class="col-xs-1">Serial No</td>
						<td class="col-xs-1"><input type="text" class="form-control" id="serialNo" width="15px"
								style="border: 0; border-bottom: 2px ridge;" name="serialNo"
								value="" /></td>							
					<td class="col-xs-1 center"><button 
							class="btn btn-success btn-sm" style="border-radius: 6px;"
							id="queryBtn" onclick="query()">
							<i class="fa fa-search"></i> Query
						</button></td>
				</tr>						
							
			</table>
		</div>

	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px;">
		<div class="table-responsive">
			<!-- --------------------- -->
			<div class="row">
				<iframe frameborder="0" class="col-md-12"
					style="margin: 10px 0 10px 0; height: 415px;"
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

	$(document).ready(function() {
		$(function() {
			$("#serialNo").autocomplete({
				source : function(request, response) {
					$.ajax({
						url : 'getSerialNo.do',
						type : "POST",
						data : {
							serialNo : request.term
						},
						dataType : "json",
						success : function(data) {
							response($.map(data, function(v, i) {
								return {
									label : v.serialNumber,
									value : v.serialNumber
								};
							}));
						}

					});

				},
				/* select : function(event, ui) {
					getItemData(ui.item.label);
				}, */
				minLength : 1
			});
		});
	});


	function query() {
		var param = $('#serialNo').val();

			
		if ($.trim(param) == '' || param == null) {
			return;
		} else {
			document.getElementById('requisitionReportFrame').src = birtAddr
					+ '?__report=desco/procurement/fixed_asset_serial_report.rptdesign&__toolbar=false&__showtitle=false&__title=&P_FA_KEY='+ param;
		
		}

	}
</script>

<!-- ------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
