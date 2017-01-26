<%@include file="../../common/pdHeader.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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

.ui-widget-overlay {
	opacity: .6 !important;
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
			<a
				href="${pageContext.request.contextPath}/job/jobAssignPendingList.do"
				class="btn btn-info btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Job
				Assign List
			</a>
		</div>
		<h2 class="center blue"
			style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Job
			Assign Details</h2>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 10px; margin-top: 10px; margin-bottom: 10px; margin-left: 10px; margin-right: 10px;">
		<!-- --------------------- -->
		<c:if test="${!empty approvalHierarchyHistoryList}">
			<button data-toggle="collapse" data-target="#demo">
				<span class="glyphicon glyphicon-collapse-down"></span>
			</button>
		</c:if>

		<div id="demo" class="collapse">
			<c:if test="${!empty approvalHierarchyHistoryList}">
				<c:forEach items="${approvalHierarchyHistoryList}"
					var="approvalHierarchyHistory">
					<table class="col-sm-12 table">
						<tr class="">
							<td class="warning col-sm-3"
								style="font-weight: bold; font-size: 1.5em; font-style: italic; text-transform: capitalize">${fn:toLowerCase(approvalHierarchyHistory.approvalHeader)}</td>
							<td class="success col-sm-2" style="text-transform: capitalize">${fn:toLowerCase(approvalHierarchyHistory.stateName)}
								By:</td>
							<td class="success col-sm-2 text-left">
								${approvalHierarchyHistory.cEmpFullName} <c:if
									test="${!empty approvalHierarchyHistory.cDesignation}">
									, ${approvalHierarchyHistory.cDesignation} 
								</c:if> <c:if test="${!empty approvalHierarchyHistory.cEmpId}">
									( ${approvalHierarchyHistory.cEmpId} )
								</c:if>
							</td>
							<td class="info col-sm-2 text-left"
								style="text-transform: capitalize">${fn:toLowerCase(approvalHierarchyHistory.stateName)}
								Date:</td>
							<td class="info col-sm-3"><fmt:formatDate
									value="${approvalHierarchyHistory.createdDate}"
									pattern="dd-MM-yyyy HH:mm:ss a" /></td>
						</tr>
						<c:if test="${!empty approvalHierarchyHistory.justification}">
							<tr class="">
								<td></td>
								<td class="danger col-sm-1">Comment(s) :</td>
								<td class="danger col-sm-11" colspan="3"
									title="${approvalHierarchyHistory.justification}">${approvalHierarchyHistory.justification}</td>
							</tr>
						</c:if>
					</table>
				</c:forEach>
			</c:if>
		</div>

		<div class="oe_title">
			<div class="col-xs-12">
				<table class="col-xs-12 table">
					<tr class="">
						<td class="success">Contract No:</td>
						<td class="info">${pndJobMst.woNumber}<input type="hidden"
							value="${pndJobMst.id}" name="jobPkId" id="jobPkId"> <input
							type="hidden" value="${pageContext.request.contextPath}"
							name="contextPath" id="contextPath">
						</td>
						<td class="success">Job No:</td>
						<td class="info">${pndJobMst.jobNo}</td>
						<td class="success">Title:</td>
						<td class="info">${pndJobMst.jobTitle}</td>
					</tr>

					<tr class="">
						<td class="success">Name:</td>
						<td class="info">${pndJobMst.name}</td>
						<td class="success">Address:</td>
						<td class="info">${pndJobMst.address}</td>
						<td class="success">Lot:</td>
						<td class="info">${pndJobMst.lot}</td>
					</tr>
					<tr class="">
						<td class="success">PD No:</td>
						<td class="info">${pndJobMst.pdNo}</td>
						<td class="success">PnD No:</td>
						<td class="info">${pndJobMst.pndNo}</td>
						<td class="success">Location :</td>
						<td class="info">${pndJobMst.jobLocation}</td>

					</tr>
				</table>
			</div>
		</div>

		<div style="background: white;">
			<c:if test="${empty pndJobDtlList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty pndJobDtlList}">
				<table id="testTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Item Code</td>
							<td style="">Description</td>
							<td style="">Unit</td>
							<td style="">Total Quantity</td>
							<td style="">Extended Quantity</td>
							<td style="">Remaining Quantity</td>
							<td style="">Remarks</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${pndJobDtlList}" var="pndJobDtl">
							<tr>
								<td><c:out value="${pndJobDtl.itemCode}" /></td>
								<td><c:out value="${pndJobDtl.itemName}" /></td>
								<td><c:out value="${pndJobDtl.uom}" /></td>
								<td><c:out value="${pndJobDtl.quantity}" /></td>
								<td><c:out value="${pndJobDtl.extendQuantity}" /></td>
								<td><c:out value="${pndJobDtl.remainningQuantity}" /></td>
								<td><c:out value="${pndJobDtl.remarks}" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
		</div>


		<!-- <div class="text-center"> -->
		<div class="row" style='margin-top: 50px;'>
			<label class="col-xs-2"> <strong>Comment(s):&nbsp;<span class='red'>*</span></strong></label>
			<div class="col-xs-10">
				<textarea required class="form-control" rows="2" cols=""
					id="justification" name="justification"></textarea>
				<strong class="justification text-danger hide">This field is required</strong>
			</div>
		</div>
		<div class="col-xs-12">
			<hr />
		</div>
		
		<div class="row">
			<div class="col-sm-6 text-left">
				<c:if test="${!empty backManRcvProcs}">
					<div class="dropup pull-right">
						<button class="btn btn-purple dropdown-toggle" type="button"
							style="border-radius: 6px;" data-toggle="dropdown"
							aria-haspopup="true" aria-expanded="false">
							Back to <span class="caret"></span>
						</button>
						<ul class="dropdown-menu">
							<c:forEach items="${backManRcvProcs}" var="backedMan">
								<li class="dropdown-submenu"><a tabindex="-1"
									href="javascript:void(0)"> For ${backedMan.buttonName} </a> <c:if
										test="${!empty backedMan.authUser}">
										<ul class="dropdown-menu"
											style="height: ${fn:length(backedMan.authUser)*32}px">
											<c:forEach items="${backedMan.authUser}" var="users">
												<li><a tabindex="-1"
													href="Javascript:backToLower(${backedMan.stateCode}, '${users.userid}')">
														${users.name}-(${users.designation})</a></li>
											</c:forEach>
										</ul>
									</c:if></li>
							</c:forEach>
						</ul>
					</div>
				</c:if>
			</div>

			<div class="col-sm-6 text-center">
				<c:if test="${!empty nextManRcvProcs}">
					<div class="dropup pull-left">
						<input type="hidden" name="userid" id="userid" value=""> <input
							type="hidden" name="stateCode" id="stateCode" value="">


						<button class="btn btn-info dropdown-toggle" type="button"
							style="border-radius: 6px;" data-toggle="dropdown"
							aria-haspopup="true" aria-expanded="false">
							Send to <span class="caret"></span>
						</button>
						<ul class="dropdown-menu">
							<c:forEach items="${nextManRcvProcs}" var="nextMan">
								<li class="dropdown-submenu"><a tabindex="-1"
									href="javascript:void(0)"> For ${nextMan.buttonName} </a> <c:if
										test="${!empty nextMan.authUser}">
										<ul class="dropdown-menu"
											style="height: ${fn:length(nextMan.authUser)*32}px">
											<c:forEach items="${nextMan.authUser}" var="users">
												<li><a tabindex="-1"
													href="Javascript:forwardToUpper(${nextMan.stateCode}, '${users.userid}')">
														${users.name}-(${users.designation})</a></li>
											</c:forEach>
										</ul>
									</c:if></li>
							</c:forEach>
						</ul>
					</div>
				</c:if>
				<c:if test="${empty nextManRcvProcs}">
					<div class="pull-left" style="margin-left: 25px;">
						<a href="Javascript:approvedJobAssign()" class="btn btn-success"
							style="border-radius: 6px;"> Approve</a>
					</div>
				</c:if>
			</div>
		</div>

		<!-- --------------------------- -->
	</div>
</div>

<script>
	$(window).load(function() {
		$('.loader').fadeOut('slow');
	});
</script>

<script
	src="${pageContext.request.contextPath}/resources/assets/js/contractor/jobAssignDetailsShow.js"></script>
<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>