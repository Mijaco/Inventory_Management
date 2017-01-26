<%@include file="../../common/wsContractorHeader.jsp"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<style>
.loader {
	position: fixed;
	left: 50%;
	top: 50%;
	z-index: 9999;
}
</style>
<!-- -------------------------------------------------------------------------------------- -->
<div class="loader">
	<i class="fa fa-refresh fa-spin fa-3x fa-fw"></i> <br> <span
		class="orange">Loading...</span>
</div>

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<h2 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Store
				Requisition List (Transformer Repair)</h2>

		</div>
	</div>

	<div class="row" 
		style="background-color: white; padding: 10px; padding-left: 20px; margin: 10px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">
			<c:if test="${empty centralStoreRequisitionMstList}">
				<div class="col-sm-12 center">
					<h2 class="green">
						<i>Congratulation!!! You have no pending task</i>
					</h2>
				</div>
			</c:if>

			<input type="hidden" value="${pageContext.request.contextPath}" id="contextPath"/>

			<c:if test="${!empty centralStoreRequisitionMstList}">			
				<table id="centralStoreRequisitionListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">PK</td>
							<td style="">Requisition No</td>
							<td style="">Indenter Name</td>
							<td style="">Requisition Date</td>
							<td style="">Store Ticket No</td>
							<td style="">Gate Pass No</td>							
							<td style="">Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${centralStoreRequisitionMstList}"
							var="centralStoreRequisitionMst">
							
							<tr>
							
								<td>${centralStoreRequisitionMst.id}</td>
								<td><a  href="#"
									onclick="openJobwiseItemSetupForm('${centralStoreRequisitionMst.requisitionNo}')"
									style="text-decoration: none;">${centralStoreRequisitionMst.requisitionNo}</a></td>
								<td><%-- ${centralStoreRequisitionMst.identerDesignation}, --%>
									${centralStoreRequisitionMst.deptName}</td>
								<td><fmt:formatDate
										value="${centralStoreRequisitionMst.requisitionDate}"
										pattern="dd-MM-yyyy hh:mm:ss a" /></td>
								<td><c:out value="${centralStoreRequisitionMst.storeTicketNO}" /></td>
								<td><c:out value="${centralStoreRequisitionMst.gatePassNo}" /></td>
								

								<td>
									<button type="button"
										onclick="openJobwiseItemSetupForm('${centralStoreRequisitionMst.requisitionNo}')"
										style="border-radius: 6px;"
										class="width-10  btn btn-sm btn-success">
										<i class="ace-icon fa fa-save"></i> <span class="bigger-30">Set</span>
									</button>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
			<!-- --------------------- -->
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
	$(document)
			.ready(
					function() {
						$('#centralStoreRequisitionListTable').DataTable({
							"order" : [ [ 0, "desc" ] ],
							"columnDefs" : [ {
								"targets" : [ 0 ],
								"visible" : false,
								"searchable" : false
							} ]
						});
						document
								.getElementById('centralStoreRequisitionListTable_length').style.display = 'none';
						//document.getElementById('centralStoreRequisitionListTable_filter').style.display = 'none';

					});

	function openJobwiseItemSetupForm(reqNo) {
		var contextPath=$("#contextPath").val();
		var action='/ws/xf/getJobWiseItemIssueForm.do';
		var path=contextPath+action;

		var params = {
				requisitionNo : reqNo
		}
		postSubmit(path, params, 'POST');

	}
</script>

<script>
	$(window).load(function() {
		$('.loader').fadeOut('slow');
	});
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>
