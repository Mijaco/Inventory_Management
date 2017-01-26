<%@include file="../common/csHeader.jsp"%>
<!-- -------------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<h4>
			Return Slip Report ( Local Store )
		</h4>
		<div class="o_form_buttons_edit col-sm-6" style="display: block;"></div>
	</div>

	<div class="row" style="background: white;">
		<!-- --------------------- -->
		<div class="container">
			<div class="row">
				<div class="col-md-3"></div>
					<div class="col-md-2"><label class="control-label" for="exampleInputName2">Return Slip No:</label></div>
					<div class="col-md-3">
						<input type="text" class="form-control" id="param1" name="param1"
							value="LS-CS-RS-2016-04-00000021">
					</div>
					<input type="hidden" class="form-control" id="birtAddr"
						value="${properties['birtAddr']}">
						&nbsp;&nbsp;
					<div class="col-md-3"><button type="button" class="btn btn-sm btn-info" onclick="query();">Query</button></div>
			</div>
		</div>

		<!-- --------------------- -->
		<div class="col-md-12">
			<iframe frameborder="0"
				style="margin: 10px 0 10px 0; height: 410px; width: 100%"
				id="requisitionReportFrame">
				<p>Your browser does not support iframes.</p>
			</iframe>
		</div>
	</div>
</div>


<script type="text/javascript">
		
	var birtAddr = document.getElementById("birtAddr").value;
	//alert(birtAddr);
	
	$(document)
			.ready(
					function() {
						var param1 = document.getElementById("param1").value;
						document.getElementById('requisitionReportFrame').src = birtAddr	+ '?__report=desco/procurement/cs_return_slip_report.rptdesign&__toolbar=false&__showtitle=false&__title=&P_RS_NO='+ param1;

					});

	function query() {
		var param1 = document.getElementById("param1").value;	
		document.getElementById('requisitionReportFrame').src = birtAddr
				+ '?__report=desco/procurement/cs_return_slip_report.rptdesign&__toolbar=false&__showtitle=false&__title=&P_RS_NO='
				+ param1;
	}
</script>
<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
