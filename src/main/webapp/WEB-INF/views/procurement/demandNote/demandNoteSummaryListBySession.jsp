<%@include file="../../common/procurementHeader.jsp"%>
<!--End of Header -->

<!-- @author: Ihteshamul Alam -->

<link
	href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
	rel="stylesheet">

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit col-md-2" style="display: block;">
			<%-- <a href="${pageContext.request.contextPath}/mps/dn/demandNoteList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Demand Note Sumamry
			</a> --%>

		</div>
		<br>
		<div class="col-md-12">
			<h2 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
				Demand Note Summary of Annexure - ${annexureType} : ${descoSession.sessionName}</h2>
		</div>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px; margin-left: 90px; margin-right: 90px;">
		
		<c:if test="${empty demandNoteSummaryList}">
			<h4 class="text-center green" style="font-weight: bold;">Sorry! No data found in database!!</h4>
		</c:if>
		<c:if test="${!empty demandNoteSummaryList}">
			<%-- <input type="hidden" name="id" id="id" value="${sessionid}">
			<input type="hidden" name="annexureType" id="annexureType" value="${annexureType}">
			<input type="hidden" name="contextPath" id="contextPath"
				value="${pageContext.request.contextPath}"> --%>
			<table class="table table-bordered table-hover" id="demandNoteDtl">
				<thead>
					<tr>
						
<!-- 						<th style="background: #579EC8; color: #fff;" class="col-md-1">Annexure type</th> -->
						<th style="background: #579EC8; color: #fff;" class="col-md-1">Item Code</th>
						<th style="background: #579EC8; color: #fff;" class="col-md-2">Item Name</th>
						<th style="background: #579EC8; color: #fff;" class="col-md-1">Unit</th>
						<th style="background: #579EC8; color: #fff;" class="col-md-2">Required Quantity</th>
						<th style="background: #579EC8; color: #fff;" class="col-md-2">
							<c:out value="${annexureType == '2' ? 'Rate' : 'Est. Unit Cost'}"></c:out>
						</th>
						<th style="background: #579EC8; color: #fff;" class="col-md-2">
							<c:out value="${annexureType == '2' ? 'Amount' : 'Est. Total Cost'}"></c:out>
						</th>
						<c:if test="${annexureType != '2'}">
							<th style="background: #579EC8; color: #fff;" class="col-md-1">Previous Year Consumption</th>
						</c:if>
						<c:if test="${annexureType == '2'}">
							<th style="background: #579EC8; color: #fff;" class="col-md-1">Existing Quantity</th>
						</c:if>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${demandNoteSummaryList}" var="dnSummary">
						<tr>
<%-- 							<td>Annexure - ${dnSummary.annexureType}</td> --%>
							<td>${dnSummary.itemCode}</td>
							<td>${dnSummary.itemName}</td>
							<td>${dnSummary.unit}</td>
							<td>${dnSummary.requiredQunatity}</td>
							<td>${dnSummary.estimateTotalCost}</td>
							<td>${dnSummary.estimateTotalCost*dnSummary.requiredQunatity}</td>
							<c:if test="${annexureType != '2'}">
								<td>${dnSummary.previousYearConsumption}</td>
							</c:if>
							<c:if test="${annexureType == '2'}">
								<td>${dnSummary.existingQty}</td>
							</c:if>
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
	$( document ).ready( function() {
		$('#demandNoteDtl').DataTable();
		document.getElementById('demandNoteDtl_length').style.display = 'none';
		//document.getElementById('demandNoteDtl_filter').style.display = 'none';
	});
</script>
<!-- Footer -->
<%@include file="../../common/ibcsFooter.jsp"%>