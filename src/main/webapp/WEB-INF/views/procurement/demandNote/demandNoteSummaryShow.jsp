<%@include file="../../common/mpsHeader.jsp"%>
<!--End of Header -->
<link
	href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
	rel="stylesheet">

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">

		<div class="col-md-12">
			<h2 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
				Department wise Requirement Details</h2>
			<h4 class="center"
				style="margin-top: 10px; font-family: 'Ubuntu Condensed', sans-serif;">
				Item Name : ${itemMaster.itemName}, Code: ${itemMaster.itemId}, Unit
				: ${itemMaster.unitCode}</h4>
		</div>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px;">

		<c:if test="${empty demandNoteDtlDb}">
			<h4 class="text-center green" style="font-weight: bold;">Sorry!
				No data found in database!!</h4>
		</c:if>
		<c:if test="${!empty demandNoteDtlDb}">
			<input type="hidden" name="contextPath" id="contextPath"
				value="${pageContext.request.contextPath}">
			<table class="table table-bordered table-hover" id="demandNoteDtl">
				<thead>
					<tr style="background: #579EC8; color: #fff;">
						<th class="col-md-1">Department	Name</th>						
						<th class="col-md-2">Required Quantity</th>
						<th class="col-md-2">Estimated Unit Cost / Rate</th>
						<th class="col-md-2">Estimated Total Cost / Amount</th>						
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${demandNoteDtlDb}" var="dnDtlDb">
						<tr>
							<td>${dnDtlDb.demandNoteMst.department.deptName}</td>
							<td>${dnDtlDb.requiredQunatity}</td>
							<td>${dnDtlDb.estimateUnitCost}</td>
							<td>${dnDtlDb.estimateTotalCost}</td>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>
</div>

<script
	src="${pageContext.request.contextPath}/resources/assets/js/jquery.dataTables.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.bootstrap.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.tableTools.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.colVis.min.js"></script>
<script>
	$(document).ready(function() {
		$('#demandNoteDtl').DataTable();
		document.getElementById('demandNoteDtl_length').style.display = 'none';
		//document.getElementById('demandNoteDtl_filter').style.display = 'none';
	});
</script>
<!-- Footer -->
<%@include file="../../common/ibcsFooter.jsp"%>