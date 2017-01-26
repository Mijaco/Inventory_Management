<%@include file="../common/pdHeader.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<link href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel="stylesheet">
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

.vertical-alignment-helper {
    display:table;
    height: 100%;
    width: 100%;
    pointer-events:none; /* This makes sure that we can still click outside of the modal to close it */
}
.vertical-align-center {
    /* To center vertically */
    display: table-cell;
    vertical-align: middle;
    pointer-events:none;
}
.modal-content {
    /* Bootstrap sets the size of the modal in the modal-dialog class, we need to inherit it */
    width:inherit;
    height:inherit;
    /* To center horizontally */
    margin: 0 auto;
    pointer-events: all;
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
			<a href="${pageContext.request.contextPath}/contractor/finalList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Contract List
			</a>
		</div>
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
	
	<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">
	
	<div class="row" style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px; margin-left: 90px; margin-right: 90px;">
		<!-- --------------------- -->
		<div class="col-md-12 col-xs-12 col-sm-12" align="left">
			<button type="button" class="btn btn-primary btn-sm"
			style="border-radius: 6px;" data-backdrop="static" data-keyboard="false" data-toggle="modal" data-target="#editModal"> <i class="fa fa-fw fa-edit"></i>&nbsp; Edit</button>
			
		</div>
		<br><br>
		<div class="oe_title">
			<div class="col-xs-12">
			
				<input type="hidden" id="id" value="${contractor.id}">
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
						<td class="info">
							<p id="contName">${contractor.contractorName}</p>
						</td>
						<td class="success">Contractor Address:</td>
						<td class="success">
							<p id="contAddress">${contractor.address}</p>
						</td>
						<td class="warning">Division:</td>
						<td class="warning">
							<p id="contDivision">${contractor.division}</p>
						</td>
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

		<div class="col-md-12 col-xs-12 col-sm-12" align="left">
			<button type="button" class="btn btn-success btn-sm"
				style="border-radius: 6px;" data-backdrop="static"
				data-keyboard="false" data-toggle="modal"
				data-target="#representativeModal">
				<i class="fa fa-fw fa-plus"></i>&nbsp; Add Representative(s)
			</button>

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
										<td style="">Action</td>
									</tr>
								</thead>

								<tbody>
									<c:forEach items="${contractorRepresentiveList}"
										var="contractorRep" varStatus="loop">
										<tr>
											<td><c:out value="${contractorRep.contractNo}" /></td>
											<td>
												<p id="mRepName${loop.index}">${contractorRep.representiveName}</p>
												<p class="hide" id="nRepName${loop.index}">
													<input type="text" style="width: 100%; margin-left: 0 !important;"
														id="iRepName${loop.index}" value="${contractorRep.representiveName}">
												</p>
											</td>
											<td>
												<p id="mAddress${loop.index}">${contractorRep.address}</p>
												<p class="hide" id="nAddress${loop.index}">
													<input type="text" style="width: 100%; margin-left: 0 !important;"
														id="iAddress${loop.index}" value="${contractorRep.address}">
												</p>
											</td>
											<td>
												<p id="mDesignation${loop.index}">${contractorRep.designation}</p>
												<p class="hide" id="nDesignation${loop.index}">
													<input type="text" style="width: 100%; margin-left: 0 !important;"
														id="iDesignation${loop.index}" value="${contractorRep.designation}">
												</p>
											</td>
											<td><c:out value="${contractorRep.contactNo}" /></td>
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
											<td>
												<a id="editbtn${loop.index}" onclick="editThis(${loop.index})" href="javascript:vod(0)" class="btn btn-primary btn-xs" style="border-radius: 6px">
													 <i class="fa fa-fw fa-edit"></i>&nbsp;Edit
												</a>
												<a id="updateBtn${loop.index}" onclick="updateInfo(${contractorRep.id},${loop.index})" href="javascript:vod(0)" class="btn btn-success btn-xs hide" style="border-radius: 6px">
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


<div class="modal fade" tabindex="-1" role="dialog" id="editModal">
  <div class="vertical-alignment-helper">
  	<div class="modal-dialog vertical-align-center" style="width: 1200px;" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Update MST Information</h4>
      </div>
      <div class="modal-body">
        <div class="col-md-12 col-sm-12 col-xs-12">
        	<table class="table table-bordered">
        		<tbody>
        			<tr>
        				<td class="success text-right">Contractor Name</td>
        				<td>
        					<input type="text" id="contractorName" class="form-control" style="width: 100%" value="${contractor.contractorName}">
        				</td>
        				<td class="success text-right">Contractor Address</td>
        				<td>
        					<input type="text" id="address" class="form-control" style="width: 100%" value="${contractor.address}">
        				</td>
        				<td class="success text-right">Division</td>
        				<td>
        					<input type="text" id="division" class="form-control" style="width: 100%" value="${contractor.division}">
        				</td>
        			</tr>
        		</tbody>
        	</table>
        </div> <hr />
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-success btn-sm" style="border-radius: 6px;" id="updateMstInfo">
        	<i class="fa fa-fw fa-repeat"></i>&nbsp;Update</button>
        <button id="closeBtn" type="button" class="btn btn-danger btn-sm"  style="border-radius: 6px;" data-dismiss="modal">
        	<i class="fa fa-fw fa-times"></i>&nbsp;Close</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
  </div>
</div><!-- /.modal -->

<!-- Add Representative Modal :: Start -->

<div class="modal fade" tabindex="-1" role="dialog" id="representativeModal">
  <div class="vertical-alignment-helper">
  	<div class="modal-dialog vertical-align-center" style="width: 1200px;" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">Add Contractor Representative(s)</h4>
      </div>
      <div class="modal-body">
      	<form action="${pageContext.request.contextPath}/pnd/saveMultipleRepresentativeInfo.do" method="POST" id="saveMultipleFormData"
      		enctype="multipart/form-data">
      		<div class="col-xs-12 table-responsive">
      			
      			<input type="hidden" id="contractorId" name="contractorId" value="${contractor.id}">
      		
				<div class="table">
					<div style="width: 1600px;">
						<hr />
						<div class="col-xs-12">
							<div class="form-group col-sm-2 col-xs-12">
								<b>Name</b>
							</div>
							<div class="form-group col-sm-1 col-xs-12">
								<b>User Id</b>
							</div>
							<div class="form-group col-sm-1 col-xs-12">
								<b>Designation</b>
							</div>
							<div class="form-group col-sm-1 col-xs-12">
								<b>Email</b>
							</div>
							<div class="form-group col-sm-1 col-xs-12">
								<b>Mobile</b>
							</div>
							<div class="form-group col-sm-2 col-xs-12">
								<b>Address</b>
							</div>
							<div class="form-group col-sm-1 col-xs-12">
								<b>Start Date</b>
							</div>
							<div class="form-group col-sm-1 col-xs-12">
								<b>End Date</b>
							</div>
							<div class="form-group col-sm-1 col-xs-12">
								<b>Photo</b>
							</div>
							<div class="form-group col-sm-1 col-xs-12">
								<b>Add More</b>
							</div>
						</div>
		
						<div class="col-xs-12">
							<div class="row">
								<div class="control-group" id="fields">
									<div class="controls">
										<div class="aaa">
											<div class="col-xs-12 entry" id="myArea0">
												<div class="row">
													<div class="form-group col-sm-2 col-xs-12">
														<input class="form-control representativeName"
															name="representativeName" type="text"
															placeholder="Enter Name" required="required"
															style="border: 0; border-bottom: 2px ridge;" />
													</div>
													<div class="form-group col-sm-1 col-xs-12">
														<input class="form-control userId" name="repUserId" required="required"
															type="text" placeholder="R2016xxx" id="repUserId0" onblur="checkUser(this)"
															style="border: 0; border-bottom: 2px ridge;" />
														<h5 class="text-danger hide" id="errUserId0"><strong>Invalid userid</strong></h5>
													</div>
													<div class="form-group col-sm-1 col-xs-12">
														<input class="form-control designation" 
															name="reDesignation" type="text" placeholder="Enter designation"
															style="border: 0; border-bottom: 2px ridge;" />
													</div>
		
													<div class="form-group col-sm-1 col-xs-12">
														<input class="form-control email" required="required"
															onblur="validateDuplicateEmail(this)" name="repEmail"
															id="repEmail0" type="email" placeholder="Enter email id"
															style="border: 0; border-bottom: 2px ridge;" />
														<h5 class="text-danger hide" id="errUserEmail0"><strong>Invalid Email</strong></h5>
													</div>
		
													<div class="form-group col-sm-1 col-xs-12">
														<input class="form-control mobile" name="repMobile"
															type="text" placeholder="Enter mobile number"
															style="border: 0; border-bottom: 2px ridge;" />
													</div>
		
													<div class="form-group col-sm-2 col-xs-12">
														<input class="form-control address" name="repAddress"
															type="text" placeholder="Enter address"
															style="border: 0; border-bottom: 2px ridge;" />
													</div>
													<div class="form-group col-sm-1 col-xs-12">
														<input class="form-control datepicker-15 startDate" name="repStartDate"
															type="text" placeholder="Set date" id="startDate0"
															style="border: 0; border-bottom: 2px ridge;" />
													</div>
													<div class="form-group col-sm-1 col-xs-12">
														<input class="form-control datepicker-21 endDate" id="endDate0" name="repEndDate"
															type="text" placeholder="Set date"
															style="border: 0; border-bottom: 2px ridge;" />
													</div>
		
													<div class="form-group col-sm-1 col-xs-12">
														<input class="form-control picture" name="picture" type="file"
															accept="image/*"
															style="border: 0; border-bottom: 2px ridge;" />
													</div>
													<div class="form-group col-sm-1 col-xs-3">
														<button class="btn btn-success btn-add" type="button">
															<span class="glyphicon glyphicon-plus"></span>
														</button>
		
														<button class="btn btn-danger btn-remove" type="button">
										                  	<span class="glyphicon glyphicon-minus"></span>
										                 </button>
													</div>
												</div>
											</div>
										</div>
										<!-- ---------------------- -->
		
									</div>
								</div>
							</div>
						</div>
		
					</div>
				</div>
			</div>
      	</form>
      </div>
      <br /> <hr /> <hr /> <hr /> <hr />
      <div class="modal-footer">
        <button type="button" class="btn btn-success btn-sm" style="border-radius: 6px;" id="saveButton" disabled>
        	<i class="fa fa-fw fa-repeat"></i>&nbsp;Update</button>
        <button id="closeBtn" type="button" class="btn btn-danger btn-sm"  style="border-radius: 6px;" data-dismiss="modal">
        	<i class="fa fa-fw fa-times"></i>&nbsp;Close</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
  </div>
</div><!-- /.modal -->

<!-- Add Representative Modal :: End -->

<script>
	
	//Added by; Ihteshamul Alam
	function editThis(id) {
		$('#editbtn'+id).addClass('hide');
		$('#updateBtn'+id).removeClass('hide');
		
		$('#mRepName'+id).addClass('hide');
		$('#nRepName'+id).removeClass('hide');
		
		$('#mAddress'+id).addClass('hide');
		$('#nAddress'+id).removeClass('hide');
		
		$('#mDesignation'+id).addClass('hide');
		$('#nDesignation'+id).removeClass('hide');
		
		
		$('#iRepName'+id).focus();
		
	} //edit button click :: dtl
	
	function updateInfo( id, index ) {
		
		var representiveName = $('#iRepName'+index).val();
		var designation = $('#iDesignation'+index).val();
		var address = $('#iAddress'+index).val();
		
		var baseURL = $('#contextPath').val();
		
		$.ajax({
			url : baseURL + "/pnd/updateRepresentativeInfo.do",
			data : {"id":id, "representiveName":representiveName, "address":address, "designation":designation},
			success : function(data) {
				if( data == "success" ) { 
					alert('Representative Info. is updated');
					$('#mRepName'+index).text( representiveName );
					$('#mDesignation'+index).text( designation );
					$('#mAddress'+index).text( address );

					$('#editbtn'+index).removeClass('hide');
					$('#updateBtn'+index).addClass('hide');
					
					$('#mRepName'+index).removeClass('hide');
					$('#nRepName'+index).addClass('hide');
					
					$('#mAddress'+index).removeClass('hide');
					$('#nAddress'+index).addClass('hide');
					
					$('#mDesignation'+index).removeClass('hide');
					$('#nDesignation'+index).addClass('hide');
				}
				else {
					alert('Representative Info is not updated. Try again');
				}
			},
			error : function(data) {
				alert("Server Error");
			},
			type : 'POST'
		}); //ajax
		
		
	} //update button click :: dtl

	$( document ).ready( function() {
		$('#updateMstInfo').click( function() {
			var contractorName = $('#contractorName').val();
			var address = $('#address').val();
			var division = $('#division').val();
			var id = $('#id').val();
			
			var baseURL = $('#contextPath').val();
			
			$.ajax({
				url : baseURL + "/pnd/updateContractorMstInfo.do",
				data : {"id":id, "contractorName":contractorName, "address":address, "division":division},
				success : function(data) {
					if( data == "success" ) {
						alert('Information is updated');
						$('#contName').text( contractorName );
						$('#contAddress').text( address );
						$('#contDivision').text( division );
						//$('#editModal').modal('hide');
						//location.reload();
						$('#closeBtn').click();
					}
					else {
						alert('Contractor Info is not updated. Try again');
						//$('#editModal').modal('hide');
						$('#closeBtn').click();
					}
				},
				error : function(data) {
					alert("Server Error");
				},
				type : 'POST'
			}); //ajax
		}); //update MST Info
		
		$('#saveButton').click( function() {
			$('#saveMultipleFormData').submit();
		});
	});
	
</script>

<script
	src="${pageContext.request.contextPath}/resources/assets/js/contractor/finalContractorRepresentativeList.js"></script>

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
	});
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
