<%@include file="../../common/pdHeader.jsp"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
	<i class="fa fa-refresh fa-spin fa-3x fa-fw"></i> <br> <span
		class="orange">Loading...</span>
</div>

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 10px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/pnd/contractorForm.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"> </span> Add
				Contractor
			</a>

			<h2 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Contractor
				List</h2>

			<h4 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptName}</h4>

			<%-- <h5 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptAddress}</h5> --%>
		</div>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 10px; margin-top: 10px; margin-bottom: 10px; margin-left: 10px; margin-right: 10px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">
			<c:if test="${empty contractorList}">
				<div class="col-sm-12 center">
					<h2 class="green">
						<i>Sorry!!! No Contractor Found in Database. </i>
					</h2>
				</div>
			</c:if>

			<c:if test="${!empty contractorList}">
				<%-- <div class="col-sm-12 pull-right">
					<form method="POST"
						action="${pageContext.request.contextPath}/contractor/finalListSearch.do">

						<div class="form-group col-sm-3">
							<label for="fromDate" class="col-xs-4 control-label">From
								:</label>
							<div class="col-xs-8">
								<input type="text" class="form-control datepicker-13"
									readonly="readonly" id="fromDate" value=''
									style="border: 0; border-bottom: 2px ridge;" name="fromDate" />
							</div>
						</div>

						<div class="form-group col-sm-3">
							<label for="toDate" class="col-xs-4 control-label">To :</label>
							<div class="col-xs-8">
								<input type="text" class="form-control datepicker-13"
									id="toDate" style="border: 0; border-bottom: 2px ridge;"
									readonly="readonly" name="toDate" />
							</div>
						</div>
						<div class="form-group col-xs-2">
							<select class="form-control senderStore"
								name="senderStore" id="senderStore"
								style="border: 0; border-bottom: 2px ridge;">
								<option value="">All</option>
								<option value="ss">Sub Store</option>
								<option value="ls">SND</option>
								<option value="ws">Workshop</option>
							</select>
						</div>
						<div class="form-group col-xs-3">
							<input type="text" class="form-control" id="search"
								placeholder="Search by Requisition No"
								style="border: 0; border-bottom: 2px ridge;" name="requisitionNo" />
						</div>
						<div class="col-sm-1">
							<button type="submit"
								class="btn btn-info glyphicon glyphicon-search"></button>
						</div>
					</form>
				</div> --%>

				<input type="hidden" id="contextPath"
					value="${pageContext.request.contextPath}">

				<div class="col-xs-12 table-responsive">
					<div class="table">
						<div style="width: 1800px;">

							<table id="contractorListTable"
								class="table table-striped table-hover table-bordered table-responsive">
								<thead>
									<tr
										style="background: #579EC8; color: white; font-weight: normal;">
										<td style="">Contract No</td>
										<td style="">Contractor Name</td>
										<td style="">Created Date</td>
										<td style="">Address</td>
										<td style="">Project</td>
										<td style="">Tender No</td>
										<td style="">Contract Date</td>
										<td style="">Expired Date</td>
										<td style="">Representative(s)</td>
										<td style="">Material List</td>
										<td style="">Job(s)</td>
										<td style="">Contractor Status</td>
										<td style="">Reason FCO</td>
										<td style="">Payment Details</td>
										<td style="">FCO Document(s)</td>
										<td style="">Action</td>
									</tr>
								</thead>

								<tbody>
									<c:forEach items="${contractorList}" var="contractor"
										varStatus="loop">
										<tr>
											<td><c:out value="${contractor.contractNo}" /></td>
											<td><c:out value="${contractor.contractorName}" /></td>
											<td><c:out value="${contractor.createdDate}" /></td>
											<td>
												<p id="addressLine${loop.index}">${contractor.address}</p>
												<p class="hide" id="updateaddress${loop.index}">
													<input type="text"
														style="width: 100%; margin-left: 0 !important"
														class="form-control" id="uAddress${loop.index}"
														value="${contractor.address}">
												</p>
											</td>
											<td><c:out value="${contractor.khathName}" /></td>
											<td><c:out value="${contractor.tenderNo}" /></td>
											<td><fmt:formatDate value="${contractor.contractDate}"
													pattern="dd-MM-yyyy" /></td>
											<td><fmt:formatDate
													value="${contractor.updatedValidityDate}"
													pattern="dd-MM-yyyy" /></td>
											<%-- 
											<td id="active${loop.index}">
												<c:choose>
													<c:when test="${contractor.active}">
			       										Yes
			    									</c:when>
			
													<c:otherwise>
			        									No
			    									</c:otherwise>
												</c:choose>
											</td>
											<td><c:out value="${contractor.remarks}" /></td>
											 --%>
											<td><a href="#" class="blue"
												onclick="postSubmit('${pageContext.request.contextPath}/contRep/finalList.do',{id:'${contractor.id}'},'POST')">
													Representative </a></td>

											<td><a href="#" class="blue"
												onclick="postSubmit('${pageContext.request.contextPath}/contMats/finalList.do',{id:'${contractor.id}'},'POST')">
													Material List </a></td>

											<%-- <td><a href="#" class="blue"
												onclick="postSubmit('${pageContext.request.contextPath}/jobs/jobDetails.do',{id:'${contractor.id}'},'POST')">
													Job(s) </a></td> --%>
											<td><a href="#" class="blue"
												onclick="postSubmit('${pageContext.request.contextPath}/jobs/finalList.do',{id:'${contractor.id}'},'POST')">
													Job(s) </a></td>

											<td class="red center"><strong>${contractor.closeOut==null?'Active':contractor.closeOut}
											</strong></td>

											<td style="">${contractor.reasionCloseOut}</td>
											<td style="">${contractor.paymentDetails}</td>
											<td class="col-xs-2"><c:if
													test="${!empty contractor.fcoDoc}">
													<a target="_blank"
														href="${pageContext.request.contextPath}/fcoDoc/download.do?downloadDocFile=${contractor.fcoDoc}">
														<span class="fa fa-file-pdf-o red center"
														aria-hidden="true" style="font-size: 1.5em;"></span>
													</a>
												</c:if></td>

											<td><a class="btn btn-primary btn-xs"
												id="editButton${loop.index}"
												onclick="editThis(${loop.index})"
												style="border-radius: 6px;" href="javascript:void(0)"> <i
													class="fa fa-fw fa-edit"></i>&nbsp;Edit
											</a> <a class="btn btn-success btn-xs hide"
												id="updateButton${loop.index}"
												onclick="updateData(${contractor.id}, ${loop.index})"
												style="border-radius: 6px;" href="javascript:void(0)"> <i
													class="fa fa-fw fa-repeat"></i>&nbsp;Update
											</a></td>
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

<script>
	//Added by: Ihteshamul Alam
	function editThis(id) {
		$('#editButton'+id).addClass('hide');
		
		$('#addressLine'+id).addClass('hide');
		$('#updateaddress'+id).removeClass('hide');
		$('#uAddress'+id).focus().css({
			'outline':'2px solid #e67e22'
		});
		$('#updateButton'+id).removeClass('hide');
	}
	
	function updateData( id, index ) {
		
		var address = $('#uAddress'+index).val();
		var baseURL = $('#contextPath').val();
		
		$.ajax({
			url : baseURL + "/pnd/updateContractorInfo.do",
			data : {"id":id, "address":address},
			success : function(data) {
				if( data == "success" ) { 
					alert('Contractor info. is updated');
					
					$('#updateButton'+index).addClass('hide');
					$('#addressLine'+index).text(address).removeClass('hide');
					$('#updateaddress'+index).addClass('hide');
					$('#editButton'+index).removeClass('hide');
				}
				else {
					alert('Contractor info. is not updated');
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
	/* var $datepicker = $('#fromDate');
	var d = new Date();
	d.setMonth(d.getMonth() - 1, d.getDate());
	$datepicker.datepicker({
		dateFormat : 'yy-mm-dd'
	});
	$datepicker.datepicker('setDate', d);

	var $datepicker1 = $('#toDate');
	$datepicker1.datepicker({
		dateFormat : 'yy-mm-dd'
	});
	$datepicker1.datepicker('setDate', new Date()); */

	$(document)
			.ready(
					function() {
						$('#contractorListTable').DataTable({
							"columnDefs" : [ {
								"targets" : [ 2 ],
								"visible" : false,
								"searchable" : false
							} ],
							"order" : [ [ 2, "desc" ] ]

						});
						document.getElementById('contractorListTable_length').style.display = 'none';
						//document.getElementById('contractorListTable_filter').style.display = 'none';
					});
</script>

<script>
	$(window).load( function() {
		$('.loader').fadeOut('slow');
	});
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>
