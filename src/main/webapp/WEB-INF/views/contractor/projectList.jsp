<%@include file="../common/pdHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<!-- @author: Ihteshamul Alam, IBCS -->

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
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/pd/createProject.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Create New Project
			</a>
		</div>

		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Project List</h1>
	</div>
	
	<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">
	
	<div class="row" style="background-color: white; padding: 10px; margin: 10px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">
			<c:if test="${empty projectList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty projectList}">
				<table id="projectListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td>Project Name</td>
							<td>Source of Fund</td>
							<td>PD Name</td>
							<td>Start Date</td>
							<td>End Date</td>
							<td>Project Duration</td>
							<td>Description</td>
							<!-- <td>Remarks</td> -->
							<td class="col-xs-2">Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${projectList}"
							var="project" varStatus="loop">
							<tr>
								<td>
									<a id="kName${loop.index}" href="${pageContext.request.contextPath}/pd/projectList.do?id=${project.id}"
										style="text-decoration: none;">${project.khathName}</a>
									<p id="khName${loop.index}" class="hide">
										<input type="text" class="form-control" id="iKname${loop.index}"
											style="width: 100%; margin-left: 0 !important;" value="${project.khathName}">
									</p>
								</td>
								<td>
									<p id="sFund${loop.index}">${project.sourceOfFund}</p>
									<p id="sIFund${loop.index}" class="hide">
										<select class="form-control" id="ssFund${loop.index}"
											style="width: 100%; margin-left: 0 !important;">
											
											<option value="${project.sourceOfFund}" selected>${project.sourceOfFund}</option>
											<c:if test="${!empty sourceOfFund}">
												<c:forEach var="sourceFund" items="${sourceOfFund}">
													<c:if test="${sourceFund.title != project.sourceOfFund}">
														<option value="${sourceFund.title}">${sourceFund.title}</option>
													</c:if>
												</c:forEach>
											</c:if>
										</select>
									</p>
								</td>
								<td>
									<p id="pdName${loop.index}">${project.pdName}</p>
									<p id="pdIName${loop.index}" class="hide">
										<input type="text" class="form-control" id="pIName${loop.index}"
											style="width: 100%; margin-left: 0 !important;" value="${project.pdName}">
									</p>
								</td>
								<td><fmt:formatDate
								value="${project.startDate}" pattern="dd-MM-yyyy" /></td>
								<td><fmt:formatDate
								value="${project.endDate}" pattern="dd-MM-yyyy" /></td>
								<td>${project.duration}</td>
								<td>
									<p id="description${loop.index}">${project.description}</p>
									<p id="iDescription${loop.index}" class="hide">
										<input type="text" class="form-control" id="iIDescription${loop.index}"
											style="width: 100%; margin-left: 0 !important;" value="${project.description}">
									</p>
								</td>
								<%-- <td>${project.remarks}</td> --%>
								<td>
									<a class="btn btn-primary btn-xs" style="border-radius: 6px;"
										href="${pageContext.request.contextPath}/pd/projectList.do?id=${project.id}">
										<i class="fa fa-eye"></i>&nbsp;Show
									</a>
									<a href="javascript:void(0)" id="editButton${loop.index}" onclick="editThis(${loop.index})" class="btn btn-success btn-xs" style="border-radius: 6px;">
										<i class="fa fa-fw fa-edit"></i>&nbsp;Edit
									</a>
									
									<a href="javascript:void(0)" id="updateButton${loop.index}" onclick="updateData(${project.id}, ${loop.index})"
										class="btn btn-info btn-xs hide" style="border-radius: 6px;">
										<i class="fa fa-fw fa-repeat"></i>&nbsp;Update
									</a>
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

<script>
	//Added by: Ihteshamul Alam
	function editThis(id) {
		$('#editButton'+id).addClass('hide');
		
		$('#kName'+id).addClass('hide');
		$('#khName'+id).removeClass('hide');
		
		$('#sFund'+id).addClass('hide');
		$('#sIFund'+id).removeClass('hide');
		
		$('#pdName'+id).addClass('hide');
		$('#pdIName'+id).removeClass('hide');
		
		$('#description'+id).addClass('hide');
		$('#iDescription'+id).removeClass('hide');
		
		
		$('#uAddress'+id).focus().css({
			'outline':'2px solid #e67e22'
		});
		$('#updateButton'+id).removeClass('hide');
	}
	
	function updateData( id, index ) {
		
		var khathName = $('#iKname'+index).val();
		var sourceOfFund = $('option:selected', '#ssFund'+index).text();
		var pdName = $('#pIName'+index).val();
		var description = $('#iIDescription'+index).val();
		
		var baseURL = $('#contextPath').val();
		
		$.ajax({
			url : baseURL + "/pd/updateProjectInfo.do",
			data : {"id":id, "khathName":khathName, "sourceOfFund":sourceOfFund, "pdName":pdName, "description":description},
			success : function(data) {
				if( data == "success" ) { 
					alert('Project info. is updated');
					
					$('#updateButton'+index).addClass('hide');
					
					$('#kName'+index).text(khathName).removeClass('hide');
					$('#khName'+index).addClass('hide');
					
					$('#sFund'+index).text(sourceOfFund).removeClass('hide');
					$('#sIFund'+index).addClass('hide');
					
					$('#pdName'+index).text(pdName).removeClass('hide');
					$('#pdIName'+index).addClass('hide');
					
					$('#description'+index).text(description).removeClass('hide');
					$('#iDescription'+index).addClass('hide');
					
					$('#editButton'+index).removeClass('hide');
				}
				else {
					alert('Project info. is not updated');
				}
			},
			error : function(data) {
				alert("Server Error");
			},
			type : 'POST'
		});

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
						$('#projectListTable').DataTable();
						document.getElementById('projectListTable_length').style.display = 'none';
						//document.getElementById('projectListTable_filter').style.display = 'none';
					});
</script>

<script>
	$(window).load( function() {
		$('.loader').fadeOut('slow');
	});
</script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>