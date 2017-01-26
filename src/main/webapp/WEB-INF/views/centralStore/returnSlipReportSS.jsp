<%@include file="../common/csHeader.jsp"%>
<!-- -------------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<h4>
			<a href="#" style="text-decoration: none;">Central Store</a> /Report
		</h4>
		<div class="o_form_buttons_edit col-sm-6" style="display: block;"></div>
	</div>

	<div class="row" style="background: white;">
		<div class="col-sm-12 table-responsive">
			<!-- --------------------- -->			

			<div class="form-group col-xs-5">
				<div class="form-group col-xs-2">
					<label for="exampleInputName2">Return Slip No</label>
				</div>
				<div class="col-xs-10">
					<input type="text" class="form-control" id="param1" name="param1"
						value="LS-CS-RS-2016-04-00000021" style="width: 100%">
						<input type="text" id="birtAddr" value="${properties['birtAddr']}" hidden="true" />
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
