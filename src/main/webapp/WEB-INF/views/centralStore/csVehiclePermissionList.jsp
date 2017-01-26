<%@include file="../common/csHeader.jsp"%>
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
	
	<div class="row" style="background-color: white; padding: 10px; padding-left: 20px">
		
		<div class="o_form_buttons_edit" style="display: block;">
			
			<a href="${pageContext.request.contextPath}/cs/vehiclePermission.do"	
			style="text-decoration: none;" class="btn btn-success btn-sm"> 
			<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span> 
			Vehicle Permission Form 
			</a>
		
		</div>		
		
		<h1 class="center blue"	style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Vehicle Permission List</h1>
	
	</div>

	<div class="row" style="background-color: white; padding-left: 10px; margin: 10px;">
		
		<div class="col-sm-12 table-responsive">
			<c:if test="${empty vehiclePermissionList}">
				<div class="col-sm-12 center">
					<p class="green" style="font-size: 16px;">
						<i>You have no Vehicle Permission Slip.</i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty vehiclePermissionList}">
				<div class="col-sm-6 pull-right">
<!-- 					<form method="POST"	action="#"> -->
<!-- 						<div class="form-group col-sm-9 col-sm-offset-2"> -->
<!-- 							<input type="text" class="form-control" id="searchBySlipNo" -->
<!-- 								placeholder="Search by Vehicle Permission Slip No." -->
<!-- 								style="border: 0; border-bottom: 2px ridge;" name="searchBySlipNo" /> -->
<!-- 						</div> -->
<!-- 						<div class="col-sm-1"> -->
<!-- 							<button type="submit" -->
<!-- 								class="btn btn-info glyphicon glyphicon-search"></button> -->
<!-- 						</div> -->
<!-- 					</form> -->
				</div>
				<table id="vehiclePermissionListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr	style="background: #579EC8; color: white; font-weight: normal;">
							<th style="">Slip No</th>
							<th style="">Entry Date</th>
							<th style="">Contractor Name</th>
							<th style="">Vehicle No</th>
							<th style="">Driver Name</th>
							<th style="">Driving License No</th>
							<th style="">Requisition By</th>
							<th style="">Action</th>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${vehiclePermissionList}" var="vehiclePermissionList">
							<tr>
								<td><a href="${pageContext.request.contextPath}/cs/showVehiclePermission.do?id=${vehiclePermissionList.id}" 
								style="text-decoration: none;">
									<c:out value="${vehiclePermissionList.slipNo}" /></a></td>
								<td><fmt:formatDate value="${vehiclePermissionList.entryTime}"
										pattern="hh:mm:ss a dd-MM-yyyy" /></td>
								<td><c:out value="${vehiclePermissionList.contractorName}" /></td>
								<td><c:out value="${vehiclePermissionList.vehicleNumber}" /></td>
								<td><c:out value="${vehiclePermissionList.driverName}"/></td>
								<td><c:out value="${vehiclePermissionList.drivingLicenceNo}"/></td>
								<td><c:out value="${vehiclePermissionList.requisitionBy}" /></td>
								<td>
									<div class="action-buttons">
										<a href="javascript:void(0)" class="btn btn-primary btn-xs"
										style="border-radius: 6px;"><i class="fa fa-fw fa-edit"></i>&nbsp;Edit</a>
										<a class="btn btn-success btn-xs" href="${pageContext.request.contextPath}
										/cs/showVehiclePermission.do?id=${vehiclePermissionList.id}" style="border-radius: 6px;">
										<i class="fa fa-fw fa-eye"></i>&nbsp;Show</a>
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
<script>
$(document).ready(
		function() {
			$('#vehiclePermissionListTable').DataTable();
			document.getElementById(
					'vehiclePermissionListTable_length').style.display = 'none';
			//document.getElementById('vehiclePermissionListTable_filter').style.display = 'none';
		});

		/*function createNewDiv() {
			document.getElementsByClassName("container-fluid").innerHTML = '<object type="type/html" data="home.html" ></object>';
		}*/
</script>

<script>
	$(window).load( function() {
		$('.loader').fadeOut('slow');
	});
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
