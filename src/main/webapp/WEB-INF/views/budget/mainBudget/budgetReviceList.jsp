<%@include file="../../common/budgetHeader.jsp"%>


<link
	href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
	rel="stylesheet">

<!--End of Header -->

<div class="container-fluid icon-box" style="background-color: #858585;"
	id="lp_form_div">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit col-md-2" style="display: block;">
			<a href="${pageContext.request.contextPath}/budget/addNewBudget.do" style="text-decoration: none;"
				class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>Add New Budget
			</a>
<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">
		</div>
		<div class="col-md-8">
			<h2 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Revise Budget
				List</h2>
			<%-- <h4 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${department.deptName}</h4>

 --%>
		</div>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px 0;">

		<div class="col-sm-12 table-responsive">
			<c:if test="${empty budgetMstList}">
				<div class="col-sm-12 center">
					<p class="green">
						<i>Sorry!!! No Data Found. </i>
					</p>
				</div>
			</c:if>


			<c:if test="${!empty budgetMstList}">
				<table id="procurementPackageMstListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">

							<!-- <td style="">Budget id</td> -->
							<td style="">Budget Year</td>
							<td style="">Budget Type</td>
							<td style="">Status</td>
							<!-- <td style="">Cost Source</td> -->
							<td style="">Amount</td>
							<td style="">Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${budgetMstList}" varStatus="loop"
							var="budgetMst">
							<tr>
								<%-- <td>${budgetMst.id}</td> --%>
								<td>${budgetMst.descoSession.sessionName}<input
									type="hidden" id="pk${loop.index}" value="${budgetMst.id}" />
								</td>
								<td>${budgetMst.budgetType}</td>
								<td>${budgetMst.budgetStatus}</td>
								<%-- <td>${budgetMst.projectFundSrc}</td> --%>
								<td>
								<fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="3" value="${budgetMst.totalAmount}" /></td>
								<td>
								
									
								<a
									href="${pageContext.request.contextPath}/budget/showReviseBudgetDetail.do?id=${budgetMst.id}"
									class="btn btn-primary btn-sm"> <span
									class="glyphicon glyphicon-eye-open"></span> Details
								</a>
								 <button
									onclick="approveRevice(${budgetMst.id})"
									class="btn btn-default btn-sm"><span class="glyphicon glyphicon-ok"></span> Approve
								</button>
										

									</td>

							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
		</div>
	</div>


	<script
		src="${pageContext.request.contextPath}/resources/assets/js/budget/budgetEntryForm.js"></script>
		<!-- TODO: move script to js file -->
		<script type="text/javascript">
		
		function deleteThis( id ) {
			
			if( confirm( "Do you want to delete this Budget?" ) == true ) {
				var contextPath = $('#contextPath').val();
				var params = {
					'id' : id
				}
				
				$.ajax({
					url : contextPath + '/budget/deleteBudget.do',
					/* data : params,
					contentType : "application/text", */
					data : "{id:" + id + "}",
					contentType : "application/json",
					success : function(data) {
						location.reload();
					},
					error : function(data) {
						alert("Server Error");
					},
					type : 'POST'
				});
			}
		}
		
	function approveThis( id ) {
			
			if( confirm( "Do you want to Approve this Budget?" ) == true ) {
				var contextPath = $('#contextPath').val();
				var params = {
					'id' : id
				}
				
				$.ajax({
					url : contextPath + '/budget/approveBudget.do',
					/* data : params,
					contentType : "application/text", */
					data : "{id:" + id + "}",
					contentType : "application/json",
					success : function(data) {
						location.reload();
					},
					error : function(data) {
						alert("Server Error");
					},
					type : 'POST'
				});
			}
		}
	
	function approveRevice( id ) {
		
		if( confirm( "Do you want to Approve this Budget?" ) == true ) {
			var contextPath = $('#contextPath').val();
			var params = {
				'id' : id
			}
			
			$.ajax({
				url : contextPath + '/budget/approveReviceBudget.do',
				/* data : params,
				contentType : "application/text", */
				data : "{id:" + id + "}",
				contentType : "application/json",
				success : function(data) {
					location.reload();
				},
				error : function(data) {
					alert("Server Error");
				},
				type : 'POST'
			});
		}
	}
		</script>

	<!-- Start of Footer  -->
	<%@include file="../../common/ibcsFooter.jsp"%>