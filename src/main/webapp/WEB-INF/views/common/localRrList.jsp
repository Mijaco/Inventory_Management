<%@include file="../common/lprrHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<!-- @author: Ihteshamul Alam, IBCS -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row" style="background-color: white; padding: 10px; padding-left: 20px">
		
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/lrr/localReceivingReportForm.do" style="text-decoration: none;" class="btn btn-primary btn-sm"> 
				<span class="glyphicon glyphicon-th-list" aria-hidden="true">
				</span> Local RR Form
			</a>

			<h1 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
				Local RR List</h1>	
			
		</div>		
		
	</div>
	
	<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">
	
	<div class="container">
			<div class="col-md-12 col-sm-12" style="background-color: white; padding: 10px 0 10px 0; 
			margin-top: 10px; margin-bottom: 10px;">
		
		<!-- --------------------- -->
		<div style="margin: 10px !important;">
			<div class="col-md-12 table-responsive">
			<c:if test="${empty localRRMstList}">
				<h4 class="text-center" style="color: green;">Sorry! No data found in database</h4>
			</c:if>
			
			<div style="width: 1800px;">
				<c:if test="${!empty localRRMstList}">
				<table class="table table-bordered" id="pettyCashListTable">
					<thead>
						<tr>
							<th style="background: #579EC8; color: white; font-weight: normal;">LRR No</th>
							<th style="background: #579EC8; color: white; font-weight: normal;">Contract No</th>
							<th style="background: #579EC8; color: white; font-weight: normal;">Contract Date</th>
							<th style="background: #579EC8; color: white; font-weight: normal;">Invoice No</th>
							<th style="background: #579EC8; color: white; font-weight: normal;">Invoice Date</th>
							<th style="background: #579EC8; color: white; font-weight: normal;">Supplier Name</th>
							<th style="background: #579EC8; color: white; font-weight: normal;">Deliver Date</th>
							<th style="background: #579EC8; color: white; font-weight: normal;">PLI Doc</th>
							<th style="background: #579EC8; color: white; font-weight: normal;">PLI Date</th>
							<th style="background: #579EC8; color: white; font-weight: normal;">Action</th>
						</tr>
					</thead>
					
					<tbody>
						<c:forEach items="${localRRMstList}" var="localRRMstList">
							<tr>
								<td>${localRRMstList.lRRNo}</td>
								<td>${localRRMstList.contractNo}</td>
								<td>
									<fmt:formatDate
										value="${localRRMstList.contractDate}" pattern="dd-MM-yyyy" /></td>
								<td>${localRRMstList.invoiceNo}</td>
								<td>
									<fmt:formatDate
										value="${localRRMstList.invoiceDate}" pattern="dd-MM-yyyy" /></td>
								<td>${localRRMstList.supplierName}</td>
								<td>
									<fmt:formatDate
										value="${localRRMstList.deliveryDate}" pattern="dd-MM-yyyy" /></td>
								<td>
									<c:if test="${!empty localRRMstList.referenceDoc}">
										<form target="_blank"
											action="${pageContext.request.contextPath}/lrr/download.do"
												method="POST">
											<input type="hidden" value="${localRRMstList.referenceDoc}"
													name="referenceDoc" />
											<button type="submit" class="fa fa-file-pdf-o red center"
												aria-hidden="true" style="font-size: 1.5em;"></button>
										</form>
									</c:if>
								</td>
								<td>
									<fmt:formatDate
										value="${localRRMstList.pliDate}" pattern="dd-MM-yyyy" /></td>
								<td>
									<a href="javascript:void(0)" class="btn btn-primary btn-xs" style="border-radius: 6px;"
									onclick="redirectShow(${localRRMstList.id})"> <i class="fa fa-fw fa-eye"></i>&nbsp;Show
									</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
			</div>
			
		</div>
		</div>
		<!-- --------------------- -->		
	</div>	
	</div>	
</div>

<script>
	function redirectShow(id) {
		var baseURL = $("#contextPath").val();
		var params = {
				id : id
		}
		var path = baseURL + "/lrr/lrrShow.do";
		
		postSubmit(path, params, "POST");
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

	<script type="text/javascript">
		$(document)
				.ready(
						function() {
							$('#pettyCashListTable').DataTable();
							document
									.getElementById('pettyCashListTable_length').style.display = 'none';
							//document.getElementById('demandNoteMstListTable_filter').style.display = 'none';
						});
	</script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>