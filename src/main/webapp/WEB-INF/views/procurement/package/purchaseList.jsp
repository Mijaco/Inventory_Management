<%@include file="../../common/procurementHeader.jsp"%>
<!--End of Header -->
<link
	href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
	rel="stylesheet">

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit  col-md-2">
			<a href="#" style="text-decoration: none;"
				class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Back
			</a>
		</div>
		<div class="col-md-8 ">
			<h2 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
				Purchase Status from APP</h2>

		</div>

		<div class="col-sm-12 text-right">
			<input type="hidden" id="contextPath"
				value="${pageContext.request.contextPath}" />
		</div>

	</div>

	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px 0;">

		<div class="col-sm-12 table-responsive">
			<c:if test="${empty list}">
				<div class="col-sm-12 center">
					<p class="green">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>


			<c:if test="${!empty list}">
				<table id="procurementList"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Id</td>
							<td style="">Financial Year</td>
							<td style="">Annexure No</td>
							<td style="">Package Name</td>
							<td style="">Current Stage</td>


						</tr>
					</thead>

					<tbody>
						<c:forEach items="${list}" varStatus="loop" var="hs">
							<tr>
								<td>${hs.appPurchaseMst.id}</td>
								<td>${hs.appPurchaseMst.descoSession.sessionName}</td>
								<td>${hs.appPurchaseMst.procurementPackageMst.annexureNo}</td>
								<td>${hs.appPurchaseMst.procurementPackageMst.packageName}</td>
								<td><b>${hs.approvalHeader}</b></td>



							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
		</div>
		<!-- Confirm All Btn -->
		<div class="col-md-12 center">
			<button class="btn btn-sm btn-danger" style="border-radius: 6px;"
				onclick="confirmAll('${appFyId}')">Confirm All</button>
		</div>
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

<script type="text/javascript">
	$(document).ready(function() {
		$('#procurementList').DataTable({
			"columnDefs" : [ {
				"targets" : [ 0 ],
				"visible" : false
			} ],
			"order" : [ [ 0, "desc" ] ],
			"bLengthChange" : false
		});
	});

	$(window).load(function() {
		$('.loader').fadeOut('slow');
	});
</script>


<!-- Footer -->
<%@include file="../../common/ibcsFooter.jsp"%>