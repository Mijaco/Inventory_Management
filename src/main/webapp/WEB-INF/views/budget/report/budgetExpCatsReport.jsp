<%@include file="../../common/budgetHeader.jsp"%>
<!-- -------------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">

		<input type="hidden" id="birtAddr" value="${properties['birtAddr']}" />
<div class="row">
		<div class="text-right col-md-3">
			<h3
				style="margin-top: 0; font-family: 'Ubuntu Condensed', sans-serif;">
				Budget Details Report for :</h3>
		</div>
		<div class="col-md-2">
			<select class="form-control" name="id" id="sessionName"
				required="required">
				<option value="">Select Financial Year</option>
				<c:forEach items="${descoSession}" var="sessions">
					<option value="${sessions.id}">${sessions.sessionName}</option>
				</c:forEach>
			</select>
		</div>
		<div class="text-right col-md-2">
			<h3
				style="margin-top: 0; font-family: 'Ubuntu Condensed', sans-serif;">
				Expenditure Category :</h3>
		</div>
		
		<div class="col-md-2">
			<select class="form-control" name="id" id="bExpCAt"
				required="required">
				<option value="">Select Expenditure Category</option>
				<c:forEach items="${expCats}" var="expCat">
					<option value="${expCat.id}">${expCat.title}</option>
				</c:forEach>
			</select>
		</div>
		<div class="col-md-2 pull-right">
			<a class="btn btn-success btn-sm" style="border-radius: 6px;"
				onclick="query()" href="JavaScript:void()"><i
				class="fa fa-search"></i> Query</a>
		</div>
</div>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; ">
		<div class="table-responsive">
			<!-- --------------------- -->
			<div class="row">
				<iframe frameborder="0" class="col-md-12"
					style="margin: 10px 0 10px 0; height: 435px;"
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
		var param1 = $('#sessionName').val();
		var param2 = $('#bExpCAt').val();
		if ($.trim(param1) == '' || param1 == null) {
			return;
		} else {
			document.getElementById('requisitionReportFrame').src = birtAddr
					+ '?__report=desco/procurement/budget_exp_category_wise.rptdesign&__toolbar=false&__showtitle=false&__title=&P_DESCO_SESSION='
					+ param1+'&P_EXP_CAT='+ param2;
		}

	}
</script>
<!-- ------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>
