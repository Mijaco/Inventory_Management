<%@include file="../inventory/inventoryheader.jsp"%>
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
			<a href="#" style="text-decoration: none;">Inventory Priority Flow</a> / List
		</h4>
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/inventory/inventoryFlowPriorityForm.do"
				style="text-decoration: none;" class="btn btn-success btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Create Inventory Flow Priority
			</a>
			<!-- <button accesskey="D" class="btn btn-info btn-sm" type="button">
					Discard</button> -->
		</div>
	</div>

	<div class="row" style="background: white;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">
			<c:if test="${empty inventoryFlowPriorityList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty inventoryFlowPriorityList}">
				<div class="col-sm-6 pull-right">
					<form method="POST"
						action="${pageContext.request.contextPath}/inventory/searchByRoleInventoryFlowPriority.do">
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
				<table id="inventoryFlowPriorityListTable"
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
						<c:forEach items="${inventoryFlowPriorityList}"
							var="inventoryFlowPriority">
							<tr>
								<td><a
									href="${pageContext.request.contextPath}/inventory/showInventoryFlowPriority.do?id=${inventoryFlowPriority.id}"
									style="text-decoration: none;"><c:out
											value="${inventoryFlowPriority.stateName}" /></a></td>
								<td><c:out value="${inventoryFlowPriority.roleName}" /></td>
								<%-- <td><fmt:formatDate
										value="${requisitionMst.requisitionDate}"
										pattern="dd-MM-yyyy" /></td> --%>

								<td><c:out value="${inventoryFlowPriority.priority}" /></td>
								<td><c:out value="${inventoryFlowPriority.remarks}" /></td>

								<td>
									<div class="action-buttons">
										<a class="btn btn-primary btn-xs" style="border-radius: 6px;"
											href="${pageContext.request.contextPath}/inventory/editInventoryFlowPriority.do?id=${inventoryFlowPriority.id}">
											<i class="fa fa-fw fa-edit"></i>&nbsp;Edit
										</a> <a class="btn btn-success btn-xs" style="border-radius: 6px;"
											href="${pageContext.request.contextPath}/inventory/showInventoryFlowPriority.do?id=${inventoryFlowPriority.id}">
											<i class="fa fa-fw fa-eye"></i>&nbsp;Show
										</a> <a class="btn btn-danger btn-xs" style="border-radius: 6px;"
											href="${pageContext.request.contextPath}/inventory/deleteInventoryFlowPriority.do?id=${inventoryFlowPriority.id}">
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
						$('#inventoryFlowPriorityListTable').DataTable();
						document.getElementById('inventoryFlowPriorityListTable_length').style.display = 'none';
						document.getElementById('inventoryFlowPriorityListTable_filter').style.display = 'none';

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
