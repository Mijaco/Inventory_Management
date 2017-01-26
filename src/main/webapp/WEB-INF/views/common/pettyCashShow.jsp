<%@include file="../common/pettyCashHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<!-- @author: Ihteshamul Alam, IBCS -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row" style="background-color: white; padding: 10px; padding-left: 20px">
		
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/pettycash/pettyCashForm.do" style="text-decoration: none;" class="btn btn-primary btn-sm"> 
				<span class="glyphicon glyphicon-th-list" aria-hidden="true">
				</span> Petty Cash Form
			</a>
			
			<a href="${pageContext.request.contextPath}/pettycash/pettyCashList.do" style="text-decoration: none;" class="btn btn-primary btn-sm"> 
				<span class="glyphicon glyphicon-th-list" aria-hidden="true">
				</span> Petty Cash List
			</a>

			<h1 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
				Petty Cash Show</h1>	
			
		</div>		
		
	</div>
	
	<div class="container">
			<div class="col-sm-10 col-sm-offset-1" style="background-color: white; padding: 10px; padding-left: 20px; 
			margin-top: 10px; margin-bottom: 10px;">
		
		<c:if test="${!empty pettyCashMstInfo}">
			<div class="col-md-12 table-responsive">
				<table class="table table-bordered">
					<tr>
						<td class="col-md-3 success">>Purchase By</td>
						<td class="col-md-3">${pettyCashMstInfo.purchaseBy}</td>
						<td class="col-md-3 success">Purchase Date</td>
						<td class="col-md-3">${pettyCashMstInfo.purchaseDate}</td>
					</tr>
					<tr>
						<td class="col-md-3 success">Invoice/Reference No</td>
						<td class="col-md-3">${pettyCashMstInfo.referenceNo}</td>
						<td class="col-md-3 success">Reference Doc</td>
						<td class="col-md-3">
							<c:if test="${!empty pettyCashMstInfo.referenceDoc}">
								<form target="_blank"
									action="${pageContext.request.contextPath}/petty/download.do"
									method="POST">
									<input type="hidden" value="${pettyCashMstInfo.referenceDoc}"
										name="referenceDoc" />
									<button type="submit" class="fa fa-file-pdf-o red center"
										aria-hidden="true" style="font-size: 1.5em;"></button>
								</form>
							</c:if>
						</td>
					</tr>
				</table>
			</div>
		</c:if>
		
		<!-- --------------------- -->
		<div class="col-md-12 table-responsive">
			<c:if test="${empty pettyCashDtl}">
				<h4 class="text-center" style="color: green;">Sorry! No data found in database</h4>
			</c:if>
			
			<c:if test="${!empty pettyCashDtl}">
				<table class="table table-bordered" id="pettyCashShowTable">
					<thead>
						<tr>
							<th style="background: #579EC8; color: white; font-weight: normal;">Code No</th>
							<th style="background: #579EC8; color: white; font-weight: normal;">Petty Cash Heading</th>
							<th style="background: #579EC8; color: white; font-weight: normal;">Description</th>
							<th style="background: #579EC8; color: white; font-weight: normal;">Total Cost</th>
							<th style="background: #579EC8; color: white; font-weight: normal;">Remarks</th>
						</tr>
					</thead>
					
					<tbody>
						<c:forEach items="${pettyCashDtl}" var="pettyCash">
							<tr>
								<td>${pettyCash.pettyCashCode}</td>
								<td>${pettyCash.pettyCashHead}</td>
								<td>${pettyCash.description}</td>
								<td>${pettyCash.totalCost}</td>
								<td>${pettyCash.remarks}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
		</div>
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