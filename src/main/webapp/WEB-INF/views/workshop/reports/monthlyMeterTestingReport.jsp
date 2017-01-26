<%@include file="../../common/wsHeader.jsp"%>

<link
 href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel="stylesheet">
<!-- -------------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<h1 class="center blue">
			Monthly Meter Testing Report <i> <b> </b>
			</i>
		</h1>
		<table class="table table-bordered table-hover">
			<%-- <!-- <tr>
					<td colspan="5"
						style="font-weight: normal; font-size: 18px; text-align: center; font-family: 'Ubuntu Condensed', sans-serif;">
						Total Transformer Receive Report</td>
				</tr> -->
				<tr>
					
					<td class="col-xs-1 success text-right" style="font-weight: bold;">Contract No
						:</td>
					<td class="col-xs-2">
						<input type="text" id="contracNo" class="form-control" value="DESCO/Procurement/PI and T/2016/102" />
					
					</td>
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
				</tr> --%>

			<tr>
				<td class="col-sm-3 success text-right" style="font-weight: bold;">Meter
					Type:</td>
				<td class="col-sm-3"><select name="meterType" id="meterType"
					class="form-control" style="width: 100%">
						<option value="" selected>Select Meter Type</option>
						<option value="HTCT">HTCT</option>
						<option value="LTCT">LTCT</option>
						<option value="WC">Whole Current Meter</option>
						<option value="CT">CT</option>
						<option value="PT">PT</option>
				</select></td>
				<td class="col-sm-3 success text-right" style="font-weight: bold;">Meter
					Category:</td>
				<td class="col-sm-3"><select name="meterCategory"
					id="meterCategory" class="form-control" style="width: 100%">
						<option value="" selected>Select Meter Category</option>
						<option value="NEW">NEW</option>
						<option value="OLD">OLD</option>
				</select> <input type="hidden" id="birtAddr"
					value="${properties['birtAddr']}" hidden="true" /></td>
			</tr>
			<tr>
				<td class="col-sm-3 success text-right" style="font-weight: bold;">From
					Date:</td>
				<td class="col-sm-3"><input type="text"
					class="form-control datepicker-15" id="fromDate" name="fromDate"
					style="width: 100%"></td>
				<td class="col-sm-3 success text-right" style="font-weight: bold;">To
					Date:</td>
				<td class="col-sm-3"><input type="text"
					class="form-control datepicker-15" id="toDate" name="toDate"
					style="width: 100%"></td>
			</tr>
		</table>

		<div class="col-md-12" align="center">
			<button class="btn btn-purple btn-sm" style="border-radius: 6px;"
				id="queryBtn" onclick="query()">
				<i class="fa fa-search"></i> Query
			</button>
		</div>
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
	
	var date = new Date();
	var eYear = date.getFullYear();
	var eMonth = date.getMonth() + 1;
	var eDay = date.getDate();
	
	//console.log(eYear+"-"+eMonth+"-"+eDay);

	function query() {
		
		var sDate = (document.getElementById("fromDate").value).split("-");
		var eDate = (document.getElementById("toDate").value).split("-");
		var param1 = "";
		var param2 = "";
		
		if(sDate == null || sDate ==''){
			param1 = eYear+"-"+(eMonth-1)+"-"+eDay;
		}else{
			param1 = sDate[2]+"-"+sDate[1]+"-"+sDate[0];
		}
		
		if(eDate == null || eDate ==''){
			param2 = eYear+"-"+(eMonth)+"-"+eDay;
		}else{
			param2 = eDate[2]+"-"+eDate[1]+"-"+eDate[0];
		}
		//var param1 = sDate[2]+"-"+sDate[1]+"-"+sDate[0];
		//var param2 = eDate[2]+"-"+eDate[1]+"-"+eDate[0];
		
		
		var param3 = document.getElementById("meterCategory").value;
		var param4 = document.getElementById("meterType").value; 
		
		document.getElementById('requisitionReportFrame').src = birtAddr
				+ '?__report=desco/procurement/month_wise_meter_report.rptdesign&__toolbar=false&__showtitle=false&__title=&P_START_DATE='+param1+'&P_END_DATE='+param2+'&P_METER_CAT='+param3+'&P_METER_TYPE='+param4;
	}
	
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>
