<%@include file="../common/wsHeader.jsp"%>
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
			<a href="${pageContext.request.contextPath}/ws/xf/contractorList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Contractor List
			</a>
		</div>
		<div class="o_form_buttons_edit" style="display: block;">
			<h2 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Contractor
				Representative List</h2>

			<h4 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptName}</h4>

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
								pattern="dd-MM-yyyy" /><input type="hidden" name="contextPath" id="contextPath"
						value="${pageContext.request.contextPath}" /></td>
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
										<td style="">End Date</td>
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
											<td><c:out value="${contractorRep.representiveName}" /></td>
											<td><c:out value="${contractorRep.address}" /></td>
											<td><c:out value="${contractorRep.designation}" /></td>
											<td><c:out value="${contractorRep.contactNo}" /></td>
											<td><c:out value="${contractorRep.email}" /></td>
											<td><fmt:formatDate value="${contractorRep.listedDate}"
													pattern="dd-MM-yyyy" /></td>
											<td><input type="text" id="endDate${loop.index}" value="<fmt:formatDate value="${contractorRep.endDate}"
													pattern="dd-MM-yyyy" />" class="form-control datepicker-15" pattern = "dd-MM-yyyy" readonly="readonly" /></td>
													
											<td><c:out value="${contractorRep.userId}" /><input type="hidden" name="id" id="pk${loop.index}"
						value="${contractorRep.id}" /></td>
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
											<button type="button" id="editBtn${loop.index}"
										onclick="enableEditMode(${loop.index})"
										style="border-radius: 6px;"
										class="width-10  btn btn-sm btn-warning">
										<i class="ace-icon fa fa-edit"></i> <span class="bigger-30">Edit</span>
									</button>

									<button type="button" id="updateBtn${loop.index}"
										onclick="enableUpdateMode(${loop.index})"
										style="border-radius: 6px; display: none;"
										class="width-10  btn btn-sm btn-success">
										<i class="ace-icon fa fa-save"></i> <span class="bigger-30">Update</span>
									</button>
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
	});
	
	function enableEditMode(n) {
		$('#editBtn' + n).css("display", "none");
		$("#endDate" + n).removeAttr("readonly");
		//$("#endDate" + n).attr("class", "datepicker-15");
		$('#updateBtn' + n).css("display", "");

	}

	function enableUpdateMode(n) {
		$('#updateBtn' + n).css("display", "none");
		$("#endDate" + n).attr("readonly", "readonly");
		//$("#endDate" + n).removeAttr("class");
		$('#editBtn' + n).css("display", "");
		updateData(n);
	}
	function updateData(n) {
		var id = $('#pk' + n).val();
		var contextPath = $('#contextPath').val();
		var path = contextPath + '/ws/updateWsContractorRep.do';
		var cData = {
			id : id,
			endDateTxt : $("#endDate" + n).val(),
		};
		var cDataJsonString = JSON.stringify(cData);
		$.ajax({
			url : path,
			data : cDataJsonString,
			contentType : "application/json",
			success : function(data) {
				var result = JSON.parse(data);
				if (result == 'success') {
					$('.alert.alert-success').removeClass('hide');
					$(".alert.alert-success").fadeTo(5000, 500).slideUp(500,
							function() {
							});
				} else {
					$('.alert.alert-danger').removeClass('hide');
					$(".alert.alert-danger").fadeTo(5000, 500).slideUp(500,
							function() {
							});
				}

			},
			error : function(data) {
				alert("Server Error");
				$('.alert.alert-danger').removeClass('hide');
				$(".alert.alert-danger").fadeTo(5000, 500).slideUp(500);
			},
			type : 'POST'
		});
	}
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
