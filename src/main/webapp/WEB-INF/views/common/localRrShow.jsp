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
			
			<a href="${pageContext.request.contextPath}/lrr/lrrList.do" style="text-decoration: none;" class="btn btn-primary btn-sm"> 
				<span class="glyphicon glyphicon-th-list" aria-hidden="true">
				</span> Local RR List
			</a>

			<h1 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
				Local RR Show</h1>	
			
		</div>		
		
	</div>
	
	<div class="container">
			<div class="col-sm-12" style="background-color: white; padding: 10px; 
			margin-top: 10px; margin-bottom: 10px;">
		
		<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">
		
		<c:if test="${!empty lclRrMst}">
			<div class="col-md-12 table-responsive">
				<table class="table table-bordered">
					<tr>
						<td class="col-md-2 success">LRR No</td>
						<td class="col-md-2">${lclRrMst.lRRNo}</td>
						<td class="col-md-2 success">Contract No</td>
						<td class="col-md-2">${lclRrMst.contractNo}</td>
						<td class="col-md-2 success">Contract Date</td>
						<td class="col-md-2">
							<fmt:formatDate
								value="${lclRrMst.contractDate}" pattern="dd-MM-yyyy" />
						</td>
					</tr>
					<tr>
						<td class="col-md-2 success">Invoice No</td>
						<td class="col-md-2">${lclRrMst.invoiceNo}</td>
						<td class="col-md-2 success">Invoice Date</td>
						<td class="col-md-2">
							<fmt:formatDate
								value="${lclRrMst.invoiceDate}" pattern="dd-MM-yyyy" />
						</td>
						<td class="col-md-2 success">Supplier Name</td>
						<td class="col-md-2">${lclRrMst.supplierName}</td>
					</tr>
					<tr>
						<td class="col-md-2 success">Deliver Date</td>
						<td class="col-md-2">
							<fmt:formatDate
								value="${lclRrMst.deliveryDate}" pattern="dd-MM-yyyy" />
						</td>
						<td class="col-md-2 success">PLI Doc</td>
						<td class="col-md-2">
							<c:if test="${!empty lclRrMst.referenceDoc}">
									<form target="_blank"
										action="${pageContext.request.contextPath}/lrr/download.do"
										method="POST">
										<input type="hidden" value="${lclRrMst.referenceDoc}"
											name="referenceDoc" />
										<button type="submit" class="fa fa-file-pdf-o red center"
											aria-hidden="true" style="font-size: 1.5em;"></button>
									</form>
								</c:if>
							</td>
						<td class="col-md-2 success">PLI Date</td>
						<td class="col-md-2">
							<fmt:formatDate
								value="${lclRrMst.pliDate}" pattern="dd-MM-yyyy" />
						</td>
					</tr>
				</table>
			</div>
		</c:if>
		
		<!-- --------------------- -->
		<div class="col-md-12 table-responsive">
			<c:if test="${empty localRRDtlList}">
				<h4 class="text-center" style="color: green;">Sorry! No data found in database</h4>
			</c:if>
			
			<c:if test="${!empty localRRDtlList}">
				<table class="table table-bordered" id="pettyCashShowTable">
					<thead>
						<tr>
							<th style="background: #579EC8; color: white; font-weight: normal;">Item Code</th>
							<th style="background: #579EC8; color: white; font-weight: normal;">Item Name</th>
							<th style="background: #579EC8; color: white; font-weight: normal;">Unit</th>
							<th style="background: #579EC8; color: white; font-weight: normal;">Required Qty</th>
							<th style="background: #579EC8; color: white; font-weight: normal;">Ledger No</th>
							<th style="background: #579EC8; color: white; font-weight: normal;">Page No</th>
							<th style="background: #579EC8; color: white; font-weight: normal;">Remarks</th>
						</tr>
					</thead>
					
					<tbody>
						<c:forEach items="${localRRDtlList}" var="localRRDtlList">
							<tr>
								<td>${localRRDtlList.itemCode}</td>
								<td>${localRRDtlList.itemName}</td>
								<td>${localRRDtlList.uom}</td>
								<td>${localRRDtlList.requiredQty}</td>
								<td>${localRRDtlList.ledgerBook}</td>
								<td>${localRRDtlList.pageNo}</td>
								<td>${localRRDtlList.remarks}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
				<c:if test="${lclRrMst.confirm == '0'}">
					<div class="col-md-12" align="center">
						<a href="javascript:void(o)" class="btn btn-primary btn-success" style="border-radius: 6px;"
							onclick="confirmData(${lclRrMst.id})">
						<i class="fa fa-fw fa-check"></i>&nbsp;Confirm
						</a>
					</div>
				</c:if>
			</c:if>
		</div>
		<!-- --------------------- -->		
	</div>	
	</div>	
</div>

<script>
	function confirmData(id) {
		if( confirm("After confirm you cannot submit this again. Do you want to proceed?") == true ) {
			var baseURL = $("#contextPath").val();
			var params = {
					id : id
			}
			var path = baseURL + "/lrr/lrrMstConfirm.do";
			
			postSubmit(path, params, "POST");
		}
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

	<!-- <script type="text/javascript">
		$(document)
				.ready(
						function() {
							$('#pettyCashShowTable').DataTable();
							document
									.getElementById('pettyCashShowTable_length').style.display = 'none';
							//document.getElementById('demandNoteMstListTable_filter').style.display = 'none';
						});
	</script> -->
	
<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>