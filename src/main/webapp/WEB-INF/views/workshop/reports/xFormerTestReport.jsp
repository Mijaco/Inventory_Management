<%@include file="../../common/wsHeader.jsp"%>
<!-- -------------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<h1 class="center blue">Transformer Test Summary Report <i> <b>   </b> </i>
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
					<td class="col-xs-1 success text-right" style="font-weight: bold;">
						XFormer Type:</td>
					<td class="col-xs-2">
						<select class="form-control" id="xFormerType">
								<option value="">Select</option>
									<option value="1-PHASE">Single Phase</option>
									<option value="3-PHASE">3 Phase</option>						
						</select>
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
		var param2 = document.getElementById("fromDate").value;
		var param3 = document.getElementById("toDate").value;
		var param1 = document.getElementById("xFormerType").value;
		//alert(param1+"-"+param2+"-"+param3+"-"+param4)
		if(param1=="1-PHASE"){
		document.getElementById('requisitionReportFrame').src = birtAddr
				+ '?__report=desco/procurement/test_report_1p.rptdesign&__toolbar=false&__showtitle=false&__title=&P_TRANS_TYPE='
				+ param1+'&P_START_DATE='+param2+'&P_END_DATE='+param3;
		}else {
			document.getElementById('requisitionReportFrame').src = birtAddr
			+ '?__report=desco/procurement/test_report_3p.rptdesign&__toolbar=false&__showtitle=false&__title=&P_TRANS_TYPE='
			+ param1+'&P_START_DATE='+param2+'&P_END_DATE='+param3;
		}
	}
	
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>
