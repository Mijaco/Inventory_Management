<%@include file="../../common/wsHeader.jsp"%>
<!-- -------------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<h1 class="center blue">Transformer Close Out Report <i> <b>   </b> </i>
		</h1>
		<table class="table table-bordered table-hover">
				<!-- <tr>
					<td colspan="5"
						style="font-weight: normal; font-size: 18px; text-align: center; font-family: 'Ubuntu Condensed', sans-serif;">
						Total Transformer Receive Report</td>
				</tr> -->
				<tr>
					
					<td class="col-xs-1 success">From Date</td>
					<td class="col-xs-2"><input type="text"
				id="birtAddr" value="${properties['birtAddr']}" hidden="true" />
						<input type="text" id="fromDate" class="form-control datepicker-13" value="" />
					</td>
					<td class="col-xs-1 success">To Date</td>
					<td class="col-xs-2">
						<input type="text" id="toDate" class="form-control datepicker-13" value="" />
					</td>
					
					<td class="col-xs-2 center"><button 
							class="btn btn-primary btn-sm" style="border-radius: 6px;"
							id="queryBtn" onclick="query()">
							<i class="fa fa-search"></i> Query
						</button></td>
				</tr>

			</table>
		</div>
		
		<div class="row" style="background: white;">
		<div class="col-sm-12 table-responsive">
			<!-- --------------------- -->

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

	function query() {
		var param1 = document.getElementById("fromDate").value;
		var param2 = document.getElementById("toDate").value;
		
			document.getElementById('requisitionReportFrame').src = birtAddr
			+ '?__report=desco/procurement/ws_closeout_report.rptdesign&__toolbar=false&__showtitle=false&__title=&P_START_DATE='+param1+'&P_END_DATE='+param2;		
	}
	
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>
