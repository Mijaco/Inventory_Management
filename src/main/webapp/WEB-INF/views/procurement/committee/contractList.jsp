<%@include file="../../common/committeeHeader.jsp"%>
<!--End of Header -->
<link
	href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
	rel="stylesheet">

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit col-md-12" style="display: inline;">
			<input type="hidden" id="contextPath"
				value="${pageContext.request.contextPath}" /> <a
				href="${pageContext.request.contextPath}/cms/contractInfo.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Contract Management Info
			</a>

		</div>
		<br>
		<div class="col-md-12">
			<h2 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
				Contract Management List</h2>

		</div>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin: 10px;">

		<div class="col-sm-12 table-responsive">
			<c:if test="${empty contractManagementList}">
				<div class="col-sm-12 center">
					<p class="green">
						<i>Congratulation!!! No Pending Task in CMS. </i>
					</p>
				</div>
			</c:if>

			<c:if test="${!empty contractManagementList}">
				<table id="contractManagementListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Contract No</td>
							<td style="">Contract Date</td>
							<td style="">Contractor Name</td>
							<td style="">Contractor Address</td>
							<td style="">Contract Document</td>

							<td style="">Duration</td>
							<td style="">PG Amount</td>
							<td style="">PG Date</td>
							<td style="">Contract Expired Date</td>
							<td style="">Contract Extended Date</td>

							<td style="">Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${contractManagementList}"
							var="contractManagement">
							<tr>
								<td><a href="javascript:void(0)" class="blue"
									onclick="view(${contractManagement.id} , ${stateCode})">${contractManagement.contractNo}
								</a></td>
								<td><fmt:formatDate
										value="${contractManagement.contractDate}"
										pattern="dd-MM-yyyy" /></td>
								<td>${contractManagement.contractorName}</td>
								<td>${contractManagement.contractorAddress}</td>

								<td><c:if test="${!empty contractManagement.contractDoc}">
										<a target="_blank"
											href="${pageContext.request.contextPath}/app/purchase/download.do?downloadDocFile=${contractManagement.contractDoc}">
											<span class="fa fa-file-pdf-o red center" aria-hidden="true"
											style="font-size: 1.5em;"></span>
										</a>
									</c:if></td>

								<td>${contractManagement.contractValidityMonth}Month(s)</td>
								<td>${contractManagement.pgAmount}</td>
								<td><fmt:formatDate value="${contractManagement.pgDate}"
										pattern="dd-MM-yyyy" /></td>
								<td><fmt:formatDate
										value="${contractManagement.contractExpiredDate}"
										pattern="dd-MM-yyyy" /></td>
								<td><fmt:formatDate
										value="${contractManagement.contractExtendedDate}"
										pattern="dd-MM-yyyy" /></td>

								<td>
									<div class="action-buttons center">
										<button class="btn btn-primary btn-xs"
											onclick="view(${contractManagement.id} , ${stateCode})"
											style="border-radius: 4px;" id="contractManagement">
											<i class="fa fa-fw fa-eye"></i>&nbsp;Show
										</button>
									</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
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
		$('#contractManagementListTable').DataTable({
			"order" : [ [ 2, "desc" ] ],
			"bLengthChange" : false
		});
	});
	

	function view(id, stateCode) {			
		var contextPath=$("#contextPath").val();
		var cData = {"id" : id, "stateCode" : stateCode}
		var path=contextPath+'/cms/contractShow.do';	
		postSubmit(path, cData, 'POST');		
	}
</script>


<!-- Footer -->
<%@include file="../../common/ibcsFooter.jsp"%>