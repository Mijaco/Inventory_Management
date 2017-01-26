<%@include file="../common/adminheader.jsp"%>
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
	<i class="fa fa-refresh fa-spin fa-3x fa-fw"></i> <br>
	<span class="orange">Loading...</span>
</div>

<div class="container-fluid icon-box" style="background-color: #858585;">
	
	<div class="row" style="background-color: white; padding: 10px; padding-left: 20px">
		
		<div class="o_form_buttons_edit" style="display: block;">
			
			<a href="${pageContext.request.contextPath}/settings/add/newDepartmentForm.do"	
			style="text-decoration: none;" class="btn btn-success btn-sm"> 
			<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span> 
			Add New Department 
			</a>
		
		</div>		
		
		<h1 class="center blue"	style="margin-top: 0; font-style: italic; 
		font-family: 'Ubuntu Condensed', sans-serif;"> List of Department</h1>
	
	</div>

	<div class="row" style="background-color: white; padding: 10px; margin: 10px;">
		
		<div class="col-sm-12 table-responsive">
			<c:if test="${empty activeDepartmentList}">
				<div class="col-sm-12 center">
					<p class="green" style="font-size: 16px;">
						<i> You have no Department </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty activeDepartmentList}">
<!-- 				<div class="col-sm-6 pull-right"> -->
<!-- 					<form method="POST"	action="#"> -->
<!-- 						<div class="form-group col-sm-9 col-sm-offset-2"> -->
<!-- 							<input type="text" class="form-control" id="searchBySlipNo" -->
<!-- 								placeholder="Search by Department Name" -->
<!-- 								style="border: 0; border-bottom: 2px ridge;" name="searchByDeptName" /> -->
<!-- 						</div> -->
<!-- 						<div class="col-sm-1"> -->
<!-- 							<button type="submit" -->
<!-- 								class="btn btn-info glyphicon glyphicon-search"></button> -->
<!-- 						</div> -->
<!-- 					</form> -->
<!-- 				</div> -->

				<table id="activeDepartmentListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr	style="background: #579EC8; color: white; font-weight: normal;">
							
							<th style="text-align: center;"> Department Name </th>
							<!-- <th style="text-align: center;"> Parent Department </th>
							<th style="text-align: center;"> Department Level </th> -->
							<th style="text-align: center;"> Address </th>
							<!-- <th style="text-align: center;"> Department ID </th> -->
							<th style="text-align: center;"> Contact Person </th>
							<th style="text-align: center;"> Contact Number </th>
							<!-- <th style="text-align: center;"> E-mail </th>
							<th style="text-align: center;"> Remarks </th> -->
							<th style="text-align: center;" class="col-xs-2"> Action </th>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${activeDepartmentList}" var="activeDepartmentList">
							<tr>
								<td><a href="${pageContext.request.contextPath}/settings/show/department.do?id=${activeDepartmentList.id}" 
								style="text-decoration: none;">${activeDepartmentList.deptName}</a></td>
								<%-- <td><c:out value="${activeDepartmentList.parent}" /></td> --%>
								<%-- <td><c:out value="${com.ibcs.desco.admin.model.Departments.findById(activeDepartmentList.parent).deptName}" /></td> --%>
								<%-- <td><c:out value="${activeDepartmentList.deptLevel}" /></td> --%>
								<td><c:out value="${activeDepartmentList.address}"/></td>
								<%-- <td><c:out value="${activeDepartmentList.deptId}"/></td> --%>
								<td><c:out value="${activeDepartmentList.contactPersion}" /></td>
								<td><c:out value="${activeDepartmentList.contactNo}" /></td>
								<%-- <td><c:out value="${activeDepartmentList.email}" /></td>
								<td><c:out value="${activeDepartmentList.remarks}" /></td> --%>
								<td style="text-align: center;">
									<div class="action-buttons">										
										<a 	class="btn btn-xs btn-primary" style="border-radius: 6px;"
											href="${pageContext.request.contextPath}
											/settings/update/department.do?
											id=${activeDepartmentList.id}">
											<i class="fa fa-fw fa-edit"></i>&nbsp;Edit
										</a>
										
										<a 	class="btn btn-xs btn-success" style="border-radius: 6px;" 
											href="${pageContext.request.contextPath}
											/settings/show/department.do?id=${activeDepartmentList.id}">
											<i class="fa fa-fw fa-eye"></i>&nbsp;View
										</a>
										
										<%-- <a 	class="red"
											href="${pageContext.request.contextPath}
											/settings/delete/department.do?
											id=${activeDepartmentList.id}">
											<i class="ace-icon fa fa-trash-o bigger-130"></i>
										</a> --%>
										
									</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
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
				$('#activeDepartmentListTable').DataTable({
					"columnDefs" : [
									{
										"targets" : [ 3 ],
										"visible" : false
									}
								],
					"order" : [ [ 2, 'asc' ] ]
				});
				document.getElementById(
						'activeDepartmentListTable_length').style.display = 'none';
				//document.getElementById('activeDepartmentListTable_filter').style.display = 'none';
			});

// 			function createNewDiv() {
// 				document.getElementsByClassName("container-fluid").innerHTML = '<object type="type/html" data="home.html" ></object>';
// 			}
		</script>
	</div>
</div>

<script>
	$(window).load( function() {
		$('.loader').fadeOut('slow');
	});
</script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>