<%@include file="../../common/procurementHeader.jsp"%>
<!-- -------------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 0 10px; padding-left: 20px">

		<input type="hidden" id="birtAddr" value="${properties['birtAddr']}" />
		<input type="hidden" id="departmentId" value="${department.id}" />

		<div class="">
			<table class="table table-bordered table-hover">
				<tr>
					<td colspan="5"
						style="font-weight: normal; font-size: 18px; text-align: center; font-family: 'Ubuntu Condensed', sans-serif;">
						Annual Procurement Plan (APP)</td>
				</tr>
				<tr>
					<td class="col-xs-1 success text-right" style="font-weight: bold;">Financial
						Year:</td>
					<td class="col-xs-2"><select class="form-control" name="id"
						onchange="checkFY()" id="sessionName">
							<option value="">Select Financial Year</option>
							<c:forEach items="${descoSession}" var="sessions">
								<option value="${sessions.id}">${sessions.sessionName}</option>
							</c:forEach>
					</select> <strong class="text-danger sessionName text-center hide"
						style="font-weight: bold; font-size: 16px;">Invalid
							Financial Year!</strong></td>

					<td class="col-xs-1 success text-right" style="font-weight: bold;">Package Type:</td>
					<td class="col-xs-2"><select id="packageType"
								required="required" name="packageType" required="required"
								class="form-control">									
									<option value="">Select Type</option>
									<option value="1">GOODS</option>
									<option value="2">WORKS &amp; SERVICES</option>
									<option value="3">MISCELLANEOUS</option>									
							</select></td>

					<td class="col-xs-2 center"><button disabled="disabled"
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

	function checkFY() {
		var haserror = false;
		if ($('#sessionName').val() == null
				|| $.trim($('#sessionName').val()) == '') {
			$('.sessionName').removeClass('hide');
			haserror = true;
		} else {
			$('.sessionName').addClass('hide');
		}

		if (haserror == true) {
			$('#queryBtn').attr("disabled", true);
		} else {
			$('#queryBtn').attr("disabled", false);
		}
	};

	function query() {
		var param1 = $('#sessionName').val();
		var param2 = $('#packageType').val();
		
		if ($.trim(param1) == '' || param1 == null) {
			return;
		} else {
			document.getElementById('requisitionReportFrame').src = birtAddr
					+ '?__report=desco/procurement/app_revenue_report.rptdesign&__toolbar=false&__showtitle=false&__title=&P_DESCO_SESSION='
					+ param1 + '&P_PACKAGE_TYPE=' + param2;
		}

	}
</script>
<!-- ------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>
