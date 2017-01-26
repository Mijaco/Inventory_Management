<%@include file="../../common/settingsHeader.jsp"%>
<!-- -------------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">

		<input type="hidden" id="birtAddr" value="${properties['birtAddr']}" />
		<input type="hidden" id="departmentId" value="${department.id}" />

		<div class="text-right col-md-6">
			<h3
				style="margin-top: 0; font-family: 'Ubuntu Condensed', sans-serif;">
				Requirement Report of Coded Items for :</h3>
		</div>
		<div class="text-right blue col-md-2">
			<select class="form-control" name="id" id="sessionName"
				required="required">
				<option value="">Select Financial Year</option>
				<c:forEach items="${descoSession}" var="sessions">
					<option value="${sessions.id}">${sessions.sessionName}</option>
				</c:forEach>
			</select>
		</div>
		<div class="col-md-2">
			<a class="btn btn-success btn-sm" style="border-radius: 6px;"
				onclick="query()" href="JavaScript:void()"><i
				class="fa fa-search"></i> Query</a>
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
		var param2 = $('#departmentId').val();
		var param3 = ''; // '102'
		var param4 = '';
		if ($.trim(param1) == '' || param1 == null) {
			return;
		} else {
			document.getElementById('requisitionReportFrame').src = birtAddr
					+ '?__report=desco/procurement/requirement_note_coded.rptdesign&__toolbar=false&__showtitle=false&__title=&P_SESSION_ID='
					+ param1+'&P_DEPARTMENT_ID='+param2+'&P_CATEGORY_ID='+param3+'&P_ITEM_ID='+param4;
		}

	}
</script>
<!-- ------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>
