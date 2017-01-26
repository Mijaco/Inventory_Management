<%@include file="../inventory/inventoryheader.jsp"%>
    <script type="text/javascript">
        $(document).ready(function () {
            $(".date").datepicker({ dateFormat: 'MM/dd/yyyy'});
            $(".date").attr("placeholder","MM/dd/yyyy");

            //$("#param1").datepicker({ dateFormat: 'dd/mm/yyyy'});
            //$("#param2").datepicker({ dateFormat: 'dd/mm/yy'});
        })
    </script>
<!-- -------------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<h4>
			<a href="#" style="text-decoration: none;">Open Balance</a> / Report
		</h4>
				<div class="o_form_buttons_edit" style="display: block;">
				<a href="${pageContext.request.contextPath}/inventory/createOpeningBalance.do"
					style="text-decoration: none;" class="btn btn-primary btn-sm">
					<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
					Create Opening Balance
				</a>
				<!-- <button accesskey="D" class="btn btn-info btn-sm" type="button">
					Discard</button> -->
			</div>


	</div>

	<div class="row" style="background: white;">
		<div class="col-sm-12 table-responsive">
			<!-- --------------------- -->			

			<div class="form-group col-xs-5">
				<div class="form-group col-xs-2">
					<label for="exampleInputName2">From</label>
				</div>
				<div class="col-xs-10">
					<input type="text" class="form-control date" id="param1"  name="param1"
						value="01/01/2016" placeholder='MM/dd/yyyy' style="width: 100%">
						<input type="text" id="birtAddr" value="${properties['birtAddr']}" hidden="true" />
				</div>


			</div>
			<div class="form-group col-xs-6">
				<div class="form-group col-xs-2">
					<label for="exampleInputName2">To</label>
				</div>

				<div class="col-xs-9">
					<input type="text" class="form-control date" id="param2" name="param2"
						placeholder="Jane Doe" value="31/01/2016" style="width: 100%">
				</div>
				
				
				
				

				<div class="col-xs-1">
					<button type="button" class="btn btn-sm btn-info"
						onclick="query();">Query</button>
				</div>

			</div>


			<div class="col-xs-12">
				<!--  <iframe frameborder="0" class="col-md-12" src="http://localhost:8085/birt/frameset?__report=desco/procurement/opening_balance_report.rptdesign&P_PRF_NO='DESCO/2015-2016/PRF001'"-->
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

						var param2   = document.getElementById("param2").value;
						//param1="DESCO/2015-2016/PRF001";
						document.getElementById('requisitionReportFrame').src = birtAddr	+ '?__report=desco/procurement/opening_balance_report.rptdesign&__toolbar=false&__showtitle=false&__title=&P_START_DATE='+ param1+'&P_END_DATE='+ param2;


					});

	function query() {
		var param1 = document.getElementById("param1").value;
		var param2   = document.getElementById("param2").value;	
		document.getElementById('requisitionReportFrame').src = birtAddr
				+ '?__report=desco/procurement/opening_balance_report.rptdesign&__toolbar=false&__showtitle=false&__title=&P_START_DATE='+ param1+'&P_END_DATE='+ param2;
	}
</script>
<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
