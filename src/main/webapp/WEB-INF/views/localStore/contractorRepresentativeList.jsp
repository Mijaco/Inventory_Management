<%@include file="../common/lsHeader.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!-- -------------------------------------------------------------------------------------- -->
<style>
.table>tbody>tr>td {
	vertical-align: middle;
}
</style>

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
			<a href="${pageContext.request.contextPath}/ls/newRepresentativeForm.do"
				style="text-decoration: none;" class="btn btn-success btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				New Representative
			</a>
			
			<a href="${pageContext.request.contextPath}/ls/contractorList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Contractor List
			</a>
		</div>
		
		<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">
		
		<div class="o_form_buttons_edit" style="display: block;">
			<h2 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Contractor
				Representative List</h2>

			<h4 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptName}</h4>

			<%-- <h5 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptAddress}</h5> --%>
		</div>
	</div>

	<div class="row" style="background-color: white; padding: 10px; padding-left: 20px; margin: 10px;">
		<!-- --------------------- -->

		<div class="oe_title">
			<div class="col-xs-12">
				<table class="col-sm-12 table">
					<tr class="">
						<td class="info">Contract No:</td>
						<td class="info">${contractor.contractNo}</td>
						<td class="success">Contractor Date:</td>
						<td class="success"><fmt:formatDate
								value="${contractor.contractDate}"
								pattern="dd-MM-yyyy" /></td>
						<td class="warning">Expired Date:</td>
						<td class="warning"><fmt:formatDate
								value="${contractor.updatedValidityDate}"
								pattern="dd-MM-yyyy" /></td>
					</tr>

					<tr class="">
						<td class="info">Contractor Name:</td>
						<td class="info">${contractor.contractorName}</td>
						<td class="success">Contractor Address:</td>
						<td class="success">${contractor.address}</td>
						<td class="warning">Division:</td>
						<td class="warning">${contractor.division}</td>
					</tr>
					<tr class="">
						<td class="info">Tender No:</td>
						<td class="info">${contractor.tenderNo}</td>
						<td class="success">Project:</td>
						<td class="success">${contractor.khathName}</td>

						<td class="warning">Is Active?</td>
						<td class="warning"><c:choose>
								<c:when test="${contractor.active}">
       										Yes
    									</c:when>

								<c:otherwise>
        									No
    									</c:otherwise>
							</c:choose></td>

					</tr>
				</table>
			</div>
		</div>

		<div class="col-sm-12 table-responsive">
			<c:if test="${empty contractorRepresentiveList}">
				<div class="col-sm-12 center">
					<h2 class="green">
						<i>Sorry!!! No Contractor Representative Found in Database. </i>
					</h2>
				</div>
			</c:if>

			<c:if test="${!empty contractorRepresentiveList}">
			
				<div class="alert alert-success hide">
					<strong>Success!</strong>&nbsp;Representative Information is updated.
				</div>
			
				<div class="col-xs-12 table-responsive">
					<div class="table">
						<div style="width: 1800px;">
							<table id="contractorListTable"
								class="table table-striped table-hover table-bordered table-responsive">

								<thead>
									<tr
										style="background: #579EC8; color: white; font-weight: normal;">
										<td style="">Contract No</td>
										<td style="">Representative Name</td>
										<td style="">Address</td>
										<td style="">Designation</td>
										<td style="">Contact No</td>
										<td style="">Email</td>
										<td style="">Listed Date</td>
										<td style="">User Id</td>
										<td style="">Picture</td>
										<td style="">Sign</td>
										<td style="">Active Performed</td>
										<td style="">Remarks</td>
										<td>Action</td>
									</tr>
								</thead>

								<tbody>
									<c:forEach items="${contractorRepresentiveList}"
										var="contractorRep" varStatus="loop">
										<tr>
											<td><c:out value="${contractorRep.contractNo}" /></td>
											<td>
												<p>
													<input type="hidden" id="id${loop.index}" value="${contractorRep.id}">
													
													<input class="form-control ivalue" id="representiveName${loop.index}" value="${contractorRep.representiveName}" disabled="disabled"
													 style="background: #fff !important;">
												</p>
												
											</td>
											<td>
												<p>
													<input class="form-control ivalue" id="address${loop.index}" value="${contractorRep.address}" disabled="disabled"
													 style="background: #fff !important;">
												</p>
											</td>
											<td>
												<p>
													<input class="form-control ivalue" id="designation${loop.index}" value="${contractorRep.designation}" disabled="disabled"
													 style="background: #fff !important;">
												</p>
											</td>
											<td>
												<p>
													<input class="form-control ivalue" id="contactNo${loop.index}" value="${contractorRep.contactNo}" disabled="disabled"
													 style="background: #fff !important;">
												</p>
											</td>
											<td><c:out value="${contractorRep.email}" /></td>
											<td><fmt:formatDate value="${contractorRep.listedDate}"
													pattern="dd-MM-yyyy" /></td>
											<td><c:out value="${contractorRep.userId}" /></td>
											<td class="center"><img
												src="data:image/jpeg;base64,${contractorRep.picture}"
												alt="..." width="80px"></td>
											<!-- ---- -->
											<td><c:out value="${contractorRep.signature}" /></td>


											<td id="active${loop.index}"><c:choose>
													<c:when test="${contractorRep.active}">
			       										Yes
			    									</c:when>

													<c:otherwise>
			        									No
			    									</c:otherwise>
												</c:choose></td>
											<td><c:out value="${contractorRep.remarks}" /></td>
											<td class="action-buttons col-xs-1">
												<a href="javascript:void(0)"
												 	class="btn btn-primary btn-xs" id="editbtn${loop.index}" onclick="editAction(${loop.index})" style="border-radius: 6px;">
												 	<i class="fa fa-fw fa-edit"></i>&nbsp;Edit
												 </a>
												<a href="javascript:void(0)"
													class="btn btn-success btn-xs hide" id="updatebtn${loop.index}" onclick="updateAction(${loop.index})" style="border-radius: 6px;">
													<i class="fa fa-fw fa-repeat"></i>&nbsp;Update
												</a>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</c:if>
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

<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$('#contractorListTable').DataTable();
						document.getElementById('contractorListTable_length').style.display = 'none';
						document.getElementById('contractorListTable_filter').style.display = 'none';
					});
</script>

<script>
	$(window).load( function() {
		$('.loader').fadeOut('slow');
	}); //init loader
	
	function editAction( eq ) {
		$('#updatebtn'+eq).removeClass('hide');

		$(".ivalue").each( function() {
			$(this).prop('disabled', false);
			$(this).css({
				'outline':'2px solid #d35400'
			});
		});
		
		
		$('#editbtn'+eq).addClass('hide');
	}
	
	function updateAction( eq ) {
		$('#updatebtn'+eq).addClass('hide');
		
		var representiveName = $('#representiveName'+eq).val();
		var address = $('#address'+eq).val();
		var designation = $('#designation'+eq).val();
		var contactNo = $('#contactNo'+eq).val();
		var id = $('#id'+eq).val();
		
		var baseURL = $('#contextPath').val();
		
		
		$.ajax({
			url : baseURL + "/ls/updateRepresentativeInfo.do",
					data : {"id":id, "representiveName":representiveName, "address":address, "designation":designation, "contactNo":contactNo},
					success : function(data) {
						if( data == "success" ) { 
							$('.alert.alert-success').removeClass('hide').fadeTo(4000, 500).slideUp(500, function() {});
						}
						else {
							//
						}
					},
					error : function(data) {
						alert("Server Error");
					},
					type : 'POST'
		});
		
		
		$(".ivalue").each( function() {
			$(this).prop('disabled', true);
			$(this).css({
				'outline':'0'
			});
		});
		
		$('#editbtn'+eq).removeClass('hide');
	} //update
	
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
