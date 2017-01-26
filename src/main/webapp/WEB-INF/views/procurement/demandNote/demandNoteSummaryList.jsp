<%@include file="../../common/mpsHeader.jsp"%>
<!--End of Header -->

<!-- Author: Ihteshamul Alam-->
<link
	href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
	rel="stylesheet">

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		
		<div class="col-md-12">
			<h2 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
				Requirements Summary: ${annexureType=='1C'?'System Materials':'General Items'}</h2>

		</div>
	</div>
	

	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px; ">

		<div style="background: #fff; color: red; text-align: center;">
			<c:if test="${!empty message}">
				<h4>${message}</h4>
			</c:if>
		</div>

		<c:if test="${empty demandNoteDtlListDb}">
			<h4 class="text-center green" style="font-weight: bold;">Sorry! No data found in database!!</h4>
		</c:if>
		<c:if test="${!empty demandNoteDtlListDb}">
			<input type="hidden" name="id" id="id" value="${sessionid}">
			<input type="hidden" name="annexureType" id="annexureType" value="${annexureType}">
			<input type="hidden" name="contextPath" id="contextPath"
				value="${pageContext.request.contextPath}">
			<table class="table table-bordered table-hover" id="demandNoteDtl">
				<thead>
					<tr>
						<th style="background: #579EC8; color: #fff;" class="col-md-1">Item
							Code</th>
						<th style="background: #579EC8; color: #fff;" class="col-md-2">Item
							Name</th>
						<th style="background: #579EC8; color: #fff;" class="col-md-1">Unit</th>
						<th style="background: #579EC8; color: #fff;" class="col-md-2">Required
							Quantity</th>
						<th style="background: #579EC8; color: #fff;" class="col-md-2">Est.
							Unit Cost/Rate</th>
						<th style="background: #579EC8; color: #fff;" class="col-md-2">Est.
							Total Cost/Amount</th>
						<th style="background: #579EC8; color: #fff;" class="col-md-1">Action</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${demandNoteDtlListDb}" var="dnDtlDb">
						<tr>
							<td>${dnDtlDb.itemMaster.itemId}</td>
							<td>${dnDtlDb.itemMaster.itemName}</td>
							<td>${dnDtlDb.itemMaster.unitCode}</td>
							<td>${dnDtlDb.requiredQunatity}</td>
							<td>${dnDtlDb.estimateUnitCost}</td>
							<td>${dnDtlDb.estimateTotalCost}</td>
							<td><a href="javascript:void(0)"
								class="btn btn-primary btn-xs" style="border-radius: 6px;"
								onclick="returnurl('${dnDtlDb.itemMaster.itemId}')"><i
									class="fa fa-fw fa-eye"></i>&nbsp;Details </a></td>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>
</div>

<script>

	function returnurl(itemCode) {
		var contextPath = $('#contextPath').val();
		var sessionid = $('#id').val();
		var path = contextPath + "/mps/dn/demandNoteSummaryShow.do";
		var params = {
				'remarks' 	: itemCode,
				'id' 		: sessionid
		}
		postSubmit(path, params, 'POST');
	}
</script>

<script
	src="${pageContext.request.contextPath}/resources/assets/js/jquery.dataTables.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.bootstrap.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.tableTools.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.colVis.min.js"></script>
<script>
	$( document ).ready( function() {
		$('#demandNoteDtl').DataTable();
		document.getElementById('demandNoteDtl_length').style.display = 'none';
		//document.getElementById('demandNoteDtl_filter').style.display = 'none';
	});
</script>
<!-- Footer -->
<%@include file="../../common/ibcsFooter.jsp"%>