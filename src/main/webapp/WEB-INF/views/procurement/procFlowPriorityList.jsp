<%@include file="../common/procurementHeader.jsp"%>
<style>
.loader {
	position: fixed;
	left: 50%;
	top: 50%;
	z-index: 9999;
	/* background-color: none;
	width: 100%;
	height: 100%; */
}
</style>
<!-- -------------------------------------------------------------------------------------- -->
<div class="loader">
	<i class="fa fa-refresh fa-spin fa-3x fa-fw"></i> <br>
	<span class="orange">Loading...</span>
</div>

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<h4>
			<a href="#" style="text-decoration: none;">Procurement
				Requisition</a> / List
		</h4>
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/procurement/requisition/procFlowPriorityForm.do"
				style="text-decoration: none;" class="btn btn-success btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Create Procurement Flow Priority
			</a>
			<!-- <button accesskey="D" class="btn btn-info btn-sm" type="button">
					Discard</button> -->
		</div>
	</div>

	<div class="row" style="background-color: white; padding: 10px; padding-left: 20px; margin: 10px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">
			<c:if test="${empty procurementFlowPriorityList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty procurementFlowPriorityList}">
				<div class="col-sm-6 pull-right">
					<form method="POST"
						action="${pageContext.request.contextPath}/procurement/requisition/searchByRoleProcFlowPriority.do">
						<div class="form-group col-sm-9 col-sm-offset-2">
							<input type="text" class="form-control" id="search"
								placeholder="Search by ROLE"
								style="border: 0; border-bottom: 2px ridge;" name="roleName" />
						</div>
						<div class="col-sm-1">
							<button type="submit"
								class="btn btn-info glyphicon glyphicon-search"></button>
						</div>
					</form>
				</div>
				<table id="requisitionListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">State Name</td>
							<td style="">Role</td>
							<td style="">Priority</td>
							<td style="">Remarks</td>
							<td class="col-xs-3">Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${procurementFlowPriorityList}"
							var="procFlowPriority">
							<tr>
								<td><a
									href="${pageContext.request.contextPath}/procurement/requisition/showProcFlowPriority.do?id=${procFlowPriority.id}"
									style="text-decoration: none;"><c:out
											value="${procFlowPriority.stateName}" /></a></td>
								<td><c:out value="${procFlowPriority.roleName}" /></td>
								<%-- <td><fmt:formatDate
										value="${requisitionMst.requisitionDate}"
										pattern="dd-MM-yyyy" /></td> --%>

								<td><c:out value="${procFlowPriority.priority}" /></td>
								<td><c:out value="${procFlowPriority.remarks}" /></td>

								<td>
									<div class="action-buttons">
										<a class="btn btn-primary btn-xs" style="border-radius: 6px;"
											href="${pageContext.request.contextPath}/procurement/requisition/editProcFlowPriority.do?id=${procFlowPriority.id}">
											<i class="fa fa-fw fa-edit"></i>&nbsp;Edit
										</a> <a class="btn btn-success btn-xs" style="border-radius: 6px;"
											href="${pageContext.request.contextPath}/procurement/requisition/showProcFlowPriority.do?id=${procFlowPriority.id}">
											<i class="fa fa-fw fa-eye"></i>&nbsp;Show
										</a> <a class="btn btn-danger btn-xs" style="border-radius: 6px;"
											href="${pageContext.request.contextPath}/procurement/requisition/deleteProcFlowPriority.do?id=${procFlowPriority.id}">
											<i class="fa fa-fw fa-trash-o"></i>&nbsp;Delete
										</a>
									</div>
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

<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$('#requisitionListTable').DataTable();
						document.getElementById('requisitionListTable_length').style.display = 'none';
						document.getElementById('requisitionListTable_filter').style.display = 'none';

					});
</script>
<!--

//-->

<script>
	$(window).load( function() {
		$('.loader').fadeOut('slow');
	});
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
