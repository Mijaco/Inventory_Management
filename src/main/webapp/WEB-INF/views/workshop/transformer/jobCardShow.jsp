<%@include file="../../common/wsHeader.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- -------------------End of Header-------------------------- -->
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
	.review{
background-color:white;
width:100px;
height:70px;
border:2px solid blue;
position:fixed;
top:45%;
right: 0;
}
</style>
<!-- -------------------------------------------------------------------------------------- -->
<div class="loader">
	<i class="fa fa-refresh fa-spin fa-3x fa-fw"></i> <br> <span
		class="orange">Loading...</span>
</div>


<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<!-- <h4>
			<a href="#" style="text-decoration: none;">Item Received</a> / Show
		</h4> -->
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/jobcard/jobList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Job
				List
			</a>
			<!-- 	<button accesskey="D" class="btn btn-info btn-sm" type="button">
				Discard</button> -->
		</div>
		<h1 class="center blue" style="margin-top: 0px;">Job Card</h1>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px; margin-left: 90px; margin-right: 90px;">
		<!-- --------------------- -->
		<div class="oe_title">

			<table class="col-sm-12 table">
				<tr class="">
					<td class="success">Job Card No:</td>
					<td class="info">${jobCardMstDb.jobCardNo}<input type="hidden"
						id="jobCardNo" value="${jobCardMstDb.jobCardNo}" />
						<input type="hidden"
						id="jobCardMstId" value="${jobCardMstDb.id}" />
						 <input
						type="hidden" id="contextPath"
						value="${pageContext.request.contextPath}" />
					</td>
					<td class="success">CreatedBy:</td>
					<td class="info">${jobCardMstDb.createdBy}</td>
					<td class="success">Created Date:</td>
					<td class="info"><fmt:formatDate
							value="${jobCardMstDb.createdDate}"
							pattern="dd-MM-yyyy" /></td>
				</tr>
				<tr class="">
					<td class="success">Transformer Serial No.:</td>
					<td class="info">${jobCardMstDb.transformerSerialNo}</td>
					<td class="success">Store Ticket/Reference No.:</td>
					<td class="info">${jobCardMstDb.typeOfWork}</td>
					<td class="success">Manufactured Name:</td>
					<td class="info">${jobCardMstDb.manufacturedName}</td>
				</tr>
				
				<tr class="">
					<td class="success">Version:</td>
					<td class="info">${jobCardMstDb.version}</td>
					<td class="success">Manufactured Year:</td>
					<td class="info">${jobCardMstDb.transformerRegister.manufacturedYear}</td>
					<td class="success">Status:</td>
					<td class="info bold"> <strong> ${currentStatus}d </strong></td>
				</tr>

			</table>
		</div>

		<br> <br>
		<hr>
		<div class="alert alert-success hide">
			<strong>Success!</strong> Job Card Item is updated.
		</div>
		<div class="alert alert-danger hide">
			<strong>Fail!</strong> Job Card Item is not updated.
		</div>

		<c:if test="${!empty approveHistoryList}">
			<button data-toggle="collapse" data-target="#demo">
				<span class="glyphicon glyphicon-collapse-down"></span>
			</button>
		</c:if>

		<div id="demo" class="collapse">
			<c:if test="${!empty approveHistoryList}">
				<c:forEach items="${approveHistoryList}" var="approveHistory">
					<table class="col-sm-12 table">

						<tr class="">
							<td class="warning col-sm-3"
								style="font-weight: bold; font-size: 1.5em; font-style: italic; text-transform: capitalize">${fn:toLowerCase(approveHistory.approvalHeader)}</td>
							<td class="success col-sm-2" style="text-transform: capitalize">${fn:toLowerCase(approveHistory.stateName)}
								By:</td>
							<td class="success col-sm-2 text-left">
								${approveHistory.cEmpFullName} <c:if
									test="${!empty approveHistory.cDesignation}">
									, ${approveHistory.cDesignation} 
								</c:if> <c:if test="${!empty approveHistory.cEmpId}">
									( ${approveHistory.cEmpId} )
								</c:if>
							</td>
							<td class="info col-sm-2 text-left"
								style="text-transform: capitalize">${fn:toLowerCase(approveHistory.stateName)}
								Date:</td>
							<td class="info col-sm-3"><fmt:formatDate
									value="${approveHistory.createdDate}"
									pattern="dd-MM-yyyy" /></td>
						</tr>
						<c:if test="${!empty approveHistory.justification}">
							<tr class="">
								<td class="col-sm-1"></td>
								<td class="danger col-sm-1">Comment(s) :</td>
								<td class="danger col-sm-11" colspan="3"
									title="${approveHistory.justification}">${approveHistory.justification}</td>
							</tr>
						</c:if>
					</table>
				</c:forEach>
			</c:if>
		</div>

		<div style="background: white;">
		
		<!-- 	added by nasrin	 -->		
					<div align="right" class="review">
										<a href="#" data-toggle="modal" data-target="#editModal"
											class="editModal">
											<!-- <i class="ace-icon fa fa-eye bigger-130"></i> -->
											<b>Preview Inventory Report</b>
										</a>
					</div>
			<c:if test="${empty jobCardDtlList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty jobCardDtlList}">
				<table id="jobCardListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Item Code</td>
							<td style="">Description</td>
							<td style="">Unit</td>
							<td style="">Required Quantity</td>
							<td style="">Recovery Quantity</td>
							<td style="">Remarks</td>
							<td style="">Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${jobCardDtlList}" var="jobCardDtl"
							varStatus="loop">
							<tr>
								<td><c:out value="${jobCardDtl.itemCode}" /> <input
									type="hidden" name="jobCardDtlId" class="jobCardDtlId"
									value="${jobCardDtl.id}" /></td>
								<td><c:out value="${jobCardDtl.itemName}" /></td>
								<td><c:out value="${jobCardDtl.unit}" /></td>
								<td><input type="number" step="0.01"
									id="quantityUsed${loop.index}" class="quantityUsed"
									style="border: 0; border-bottom: 2px ridge;"
									value="${jobCardDtl.quantityUsed}" name="quantityUsed" /></td>
								<td><input type="number" name="quantityRecovery"
									class="quantityRecovery" step="0.01"
									style="border: 0; border-bottom: 2px ridge;"
									value="${jobCardDtl.quantityRecovery}"
									id="quantityRecovery${loop.index}" /></td>
								<td><input type="text" name="remarks" class="remarks"
									value="${jobCardDtl.remarks}"
									style="border: 0; border-bottom: 2px ridge;"
									id="remarks${loop.index}" /></td>
								<td>
									<%-- <a href="#" class="green"
									onclick="postSubmit('${pageContext.request.contextPath}/jobcard/update.do',{id:'${jobCardDtl.id}'},'POST')">
										<i class="ace-icon fa fa-pencil bigger-130"></i>
								</a> --%> <a class="blue"
									onclick="updateJobCard(${loop.index}, ${jobCardDtl.id})"
									href="javascript:void(0)" title="Update"> <i
										class="fa fa-2x fa-edit"></i>
								</a> <a href="#" class="red" title="Delete"
									onclick="postSubmit('${pageContext.request.contextPath}/jobcard/delete.do',{id:'${jobCardDtl.id}'},'POST')">
										<i class="ace-icon fa fa-trash-o bigger-130"></i>
								</a>
								</td>
							</tr>
							<c:set var="count" value="${loop.count}" scope="page" />
						</c:forEach>
					</tbody>
				</table>
			</c:if>
		</div>

<div id="editModal" class="modal fade editModal" role="dialog">
			<div class="modal-dialog modal-lg">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title center">Inventory Item Report</h4>
					</div>

					<!-- --------------------- -->
					<c:if test="${!empty wsInventoryDtl}">
				<table id="jobCardListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							
							
							<th class=" success">Item Description</th>
				<th class="success">Unit</th>
				<th class="success">Standard Quantity</th>
				<th class="success">Useable</th>
				<th class="success">Un-Useable</th>
				<th class="success">Not Found</th>
				<th class="success">Total Found</th>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${wsInventoryDtl}" var="wsDtl">
							<tr>
								<td>${wsDtl.itemName}</td>
								<td><c:out value="${wsDtl.unit}" /></td>
								<td><c:out value="${wsDtl.standardQuantity}" /></td>
								<td>${wsDtl.useableQuantity}</td>
								<td>${wsDtl.unUseableQuantity}</td>
								<td>${wsDtl.notFound}</td>
								<td>${wsDtl.totalFound}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
				</div>
			</div>
		</div>


		<div class="row">
			<label class="col-xs-2"> <strong>Comment(s) : </strong></label>
			<div class="col-xs-10">
				<textarea class="form-control" rows="2" cols="" id="justification"
					required></textarea>
			</div>
		</div>

		<div class="col-xs-12">
			<hr />
		</div>
		<div class="row">
			<div class="col-md-4 col-sm-4 text-left">
				<c:if test="${!empty backManRcvProcs}">
					<div class="dropup pull-left">
						<button class="btn btn-warning dropdown-toggle" type="button"
							style="border-radius: 6px;" data-toggle="dropdown"
							aria-haspopup="true" aria-expanded="false">
							Back to <span class="caret"></span>
						</button>
						<ul class="dropdown-menu" aria-labelledby="dropdownMenu2">
							<c:forEach items="${backManRcvProcs}" var="backMan">
								<li class=""><a
									href="Javascript:backToLower(${backMan.stateCode})">For
										${backMan.buttonName}</a></li>
							</c:forEach>
						</ul>
					</div>
				</c:if>
			</div>
			<div class="col-md-4 col-sm-4 text-center">
				<a class="btn btn-primary" href="Javascript:approveJobCard()"
					style="text-decoration: none; border-radius: 6px;">${buttonValue}</a>
				<a class="btn btn-primary" href="Javascript:updateAllJobCard()"
					style="text-decoration: none; border-radius: 6px;">Update All</a>
			</div>
			<div class="col-md-4 col-sm-4 text-right">

				<c:if test="${!empty nextManRcvProcs}">

					<div class="dropup pull-right">
						<button class="btn btn-danger dropdown-toggle" type="button"
							style="border-radius: 6px;" data-toggle="dropdown"
							aria-haspopup="true" aria-expanded="false">
							Send to <span class="caret"></span>
						</button>
						<ul class="dropdown-menu" aria-labelledby="dropdownMenu2">
							<c:forEach items="${nextManRcvProcs}" var="nextMan">
								<li class=""><a
									href="Javascript:forwardToUpper(${nextMan.stateCode})">For
										${nextMan.buttonName}</a></li>
							</c:forEach>
						</ul>
					</div>
				</c:if>
			</div>
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
						$('#jobCardListTable').DataTable({
							"order" : [ [ 0, "asc" ] ],
							"paging": false,
							"info": false
						});
						document.getElementById('jobCardListTable_length').style.display = 'none';
						//document.getElementById('jobCardListTable_filter').style.display = 'none';
					});
</script>

<script>
	$(window).load(function() {
		$('.loader').fadeOut('slow');
	});
</script>

<script
	src="${pageContext.request.contextPath}/resources/assets/js/workshop/jobCardShow.js"></script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>