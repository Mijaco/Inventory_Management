<%@include file="../common/csHeader.jsp"%>
<!-- -------------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<!-- <div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<h4>
			<a href="#" style="text-decoration: none;">Central Store</a> /Return
			Slip Report
		</h4>
		<div class="o_form_buttons_edit col-sm-6" style="display: block;"></div>
	</div> -->
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="form-group col-xs-offset-3 col-xs-6">
			<div class="form-group col-xs-3">
				<label for="exampleInputName2">Return Slip No</label>
			</div>
			<div class="col-xs-9">
				<input type="text" class="form-control" id="param1" name="param1"
					value="${empty operationId ? 'RS9920160900000001' : operationId}" style="width: 100%">
				<input type="text" id="birtAddr" value="${properties['birtAddr']}"
					hidden="true" />
			</div>
		</div>
		<div class="form-group col-xs-3">
			<div class="col-xs-1">
				<button type="button" class="btn btn-sm btn-info" onclick="query();">Query</button>
			</div>
		</div>
	</div>
	
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin: 10px;">
		<div class="row table-responsive">
			<div class="">
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
	


	<!-- <div class="row" style="background: white;">
		<div class="col-sm-12 table-responsive">
			---------------------
			<div class="col-xs-12">
				<iframe frameborder="0" class="col-md-12"
					style="margin: 10px 0 10px 0; height: 410px;"
					id="requisitionReportFrame">
					<p>Your browser does not support iframes.</p>
				</iframe>
			</div>
			---------------------
		</div>
	</div> -->
</div>


<script type="text/javascript">
	var birtAddr = document.getElementById("birtAddr").value;
	$(document)
			.ready(
					function() {
						var param1 = document.getElementById("param1").value;
						document.getElementById('requisitionReportFrame').src = birtAddr
								+ '?__report=desco/procurement/cn_to_cs_return_slip_report.rptdesign&__toolbar=false&__showtitle=false&__title=&P_RS_NO='
								+ param1;
					});

	function query() {
		var param1 = document.getElementById("param1").value;
		document.getElementById('requisitionReportFrame').src = birtAddr
				+ '?__report=desco/procurement/cn_to_cs_return_slip_report.rptdesign&__toolbar=false&__showtitle=false&__title=&P_RS_NO='
				+ param1;
	}
</script>
<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
