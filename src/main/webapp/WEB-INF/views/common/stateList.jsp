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
			
			<a href="${pageContext.request.contextPath}/settings/add/newStateForm.do"	
			style="text-decoration: none;" class="btn btn-success btn-sm"> 
			<span class="glyphicon glyphicon-th-list" aria-hidden="true"></span> 
			Add New State 
			</a>
		
		</div>		
		
		<h1 class="center blue"	style="margin-top: 0; font-style: italic; 
		font-family: 'Ubuntu Condensed', sans-serif;"> List of State </h1>
	
	</div>

	<div class="row" style="background-color: white; padding: 10px; margin: 10px;">
		
		<div class="col-sm-12 table-responsive">
			<c:if test="${empty stateList}">
				<div class="col-sm-12 center">
					<p class="green" style="font-size: 16px;">
						<i> You have no State </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty stateList}">
<!-- 				<div class="col-sm-6 pull-right"> -->
<!-- 					<form method="POST"	action="#"> -->
<!-- 						<div class="form-group col-sm-9 col-sm-offset-2"> -->
<!-- 							<input type="text" class="form-control" id="searchByStateName" -->
<!-- 								placeholder="Search by State Name" -->
<!-- 								style="border: 0; border-bottom: 2px ridge;" name="searchByStateName" /> -->
<!-- 						</div> -->
<!-- 						<div class="col-sm-1"> -->
<!-- 							<button type="submit" -->
<!-- 								class="btn btn-info glyphicon glyphicon-search"></button> -->
<!-- 						</div> -->
<!-- 					</form> -->
<!-- 				</div> -->
				<table id="stateListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr	style="background: #579EC8; color: white; font-weight: normal;">
							<th style="text-align: center;"> State Name </th>
							<th style="text-align: center;" class="hide"> Created By </th>
							<th style="text-align: center;"> Status </th>
							<th style="text-align: center;"> Remarks </th>
							<th style="text-align: center;" class="col-xs-2"> Action </th>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${stateList}" var="stateList">
							<tr>
								<td><a href="${pageContext.request.contextPath}/settings/show/state.do?id=${stateList.id}" 
								style="text-decoration: none;">
									<c:out value="${stateList.stateName}" /></a></td>
								<td class="hide"><c:out value="${stateList.createdBy}" /></td>
								<td><c:out value="${stateList.active == true ? 'Active' : 'Inactive'}"/></td>
								<td><c:out value="${stateList.remarks}" /></td>								
								<td style="text-align: center;">
									<div class="action-buttons">										
										<a 	class="btn btn-xs btn-primary" style="border-radius: 6px;"
											href="${pageContext.request.contextPath}
											/settings/update/state.do?
											id=${stateList.id}">
											<i class="fa fa-fw fa-edit"></i>&nbsp;Edit
										</a>										
										<a 	class="btn btn-xs btn-success" style="border-radius: 6px;"
											href="${pageContext.request.contextPath}
											/settings/show/state.do?id=${stateList.id}">
											<i class="fa fa-fw fa-eye"></i>&nbsp;View
										</a>
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
				$('#stateListTable').DataTable();
				document.getElementById(
						'stateListTable_length').style.display = 'none';
				//document.getElementById('stateListTable_filter').style.display = 'none';
			});

			function createNewDiv() {
				document.getElementsByClassName("container-fluid").innerHTML = '<object type="type/html" data="home.html" ></object>';
			}
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