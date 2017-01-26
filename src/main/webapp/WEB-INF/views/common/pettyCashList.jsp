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

			<h1 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
				Petty Cash List</h1>	
			
		</div>		
		
	</div>
	
	<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">
	
	<div class="container">
			<div class="col-sm-10 col-sm-offset-1" style="background-color: white; padding: 10px; padding-left: 20px; 
			margin-top: 10px; margin-bottom: 10px;">
		
		<!-- --------------------- -->
		<div class="col-md-12 table-responsive">
			<c:if test="${empty pettyCashMstList}">
				<h4 class="text-center" style="color: green;">Sorry! No data found in database</h4>
			</c:if>
			
			<c:if test="${!empty pettyCashMstList}">
				<table class="table table-bordered" id="pettyCashListTable">
					<thead>
						<tr>
							<th style="background: #579EC8; color: white; font-weight: normal;">Purchase By</th>
							<th style="background: #579EC8; color: white; font-weight: normal;">Purchase Date</th>
							<th style="background: #579EC8; color: white; font-weight: normal;">Invoice/Reference No</th>
							<th style="background: #579EC8; color: white; font-weight: normal;">Reference Doc</th>
							<th style="background: #579EC8; color: white; font-weight: normal;">Action</th>
						</tr>
					</thead>
					
					<tbody>
						<c:forEach items="${pettyCashMstList}" var="pettyCash">
							<tr>
								<td>${pettyCash.purchaseBy}</td>
								<td><fmt:formatDate value="${pettyCash.purchaseDate}"
										pattern="dd-MM-yyyy" />
								</td>
								<td>${pettyCash.referenceNo}</td>
								<td>
									<c:if test="${!empty pettyCash.referenceDoc}">
										<form target="_blank"
											action="${pageContext.request.contextPath}/petty/download.do"
												method="POST">
											<input type="hidden" value="${pettyCash.referenceDoc}"
													name="referenceDoc" />
											<button type="submit" class="fa fa-file-pdf-o red center"
												aria-hidden="true" style="font-size: 1.5em;"></button>
										</form>
									</c:if>
								</td>
								<td>
									<a class="btn btn-primary btn-xs" style="border-radius: 6px;"
									href="javascript:void(0)" onclick="redirectShowPage(${pettyCash.id})">
									<i class="fa fa-fw fa-eye"></i>&nbsp;Show
									</a>
								</td>
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

<script>
	function redirectShowPage(id) {
		var baseURL = $("#contextPath").val();
		var params = {
				id : id
		}
		var path = baseURL + "/pettycash/pettyCashShow.do";
		
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