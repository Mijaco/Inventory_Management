<%@include file="../common/cnPndHeader.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- ---------- Start of Style and Script for Location Grid ------------- -->
<style type="text/css">
.btn-add {
	margin-bottom: 20px;
}

.ui-widget-overlay {
	opacity: .6 !important;
}
</style>
<!-- ---------- End of Style and Script for Location Grid ------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/template/pnd/jobEstimationList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Cost
				Estimation List
			</a>
		</div>
		<h1 class="center blue" style="margin-top: 0px;">Cost Estimation</h1>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px; margin-left: 10px; margin-right: 10px;">
		<!-- --------------------- -->
		<%-- <form id="myForm"
			action="${pageContext.request.contextPath}/template/costEstimationSubmitApproved.do"
			method="POST"> --%>
		<form id="myForm"
			action="${pageContext.request.contextPath}/template/sendTo.do"
			method="POST">
			<div class="oe_title">

				<table class="col-sm-12 table">
					<tr class="">
						<td class="success">P&amp;D No: <input type="hidden"
							value="${costEstimationMst.pndNo}" id="pndNo" name="pndNo" /> <input
							type="hidden" value="${returnStateCode}" id="returnStateCode"
							name="return_state" /> <input type="hidden" name="contextPath"
							id="contextPath" value="${pageContext.request.contextPath}" />
						</td>

						<td class="info">${costEstimationMst.pndNo}
						<input
							type="hidden" value="${pageContext.request.contextPath}"
							name="" id="contextPath">
						</td>
						<td class="success">Created By:</td>
						<td class="info">${costEstimationMst.createdBy}</td>
						<td class="success">Created Date:</td>
						<td class="info"><fmt:formatDate
								value="${costEstimationMst.createdDate}" pattern="dd-MM-yyyy" /></td>
					</tr>
					<tr class="">
						<td class="success">Modified By:</td>
						<td class="info">${costEstimationMst.modifiedBy}</td>
						<td class="success">Modified Date:</td>
						<td class="info"><fmt:formatDate
								value="${costEstimationMst.modifiedDate}" pattern="dd-MM-yyyy" /></td>
						<td class="success">Present Status:</td>
						<td class="info"><strong> ${currentStatus} </strong></td>
					</tr>

				</table>
			</div>

			<c:if test="${!empty itemRcvApproveHistoryList}">
				<button data-toggle="collapse" data-target="#demo">
					<span class="glyphicon glyphicon-collapse-down"></span>
				</button>
			</c:if>

			<div id="demo" class="collapse">
				<c:if test="${!empty itemRcvApproveHistoryList}">
					<c:forEach items="${itemRcvApproveHistoryList}"
						var="itemRcvApproveHistory">
						<table class="col-sm-12 table">
							<tr class="">
								<td class="warning col-sm-3"
									style="font-weight: bold; font-size: 1.5em; font-style: italic; text-transform: capitalize">${fn:toLowerCase(itemRcvApproveHistory.approvalHeader)}</td>
								<td class="success col-sm-2" style="text-transform: capitalize">${fn:toLowerCase(itemRcvApproveHistory.stateName)}
									By:</td>
								<td class="success col-sm-2 text-left">
									${itemRcvApproveHistory.cEmpFullName} <c:if
										test="${!empty itemRcvApproveHistory.cDesignation}">
									, ${itemRcvApproveHistory.cDesignation} 
								</c:if> <c:if test="${!empty itemRcvApproveHistory.cEmpId}">
									( ${itemRcvApproveHistory.cEmpId} )
								</c:if>
								</td>
								<td class="info col-sm-2 text-left"
									style="text-transform: capitalize">${fn:toLowerCase(itemRcvApproveHistory.stateName)}
									Date:</td>
								<td class="info col-sm-3"><fmt:formatDate
										value="${itemRcvApproveHistory.createdDate}"
										pattern="dd-MM-yyyy" /></td>
							</tr>
							<c:if test="${!empty itemRcvApproveHistory.justification}">
								<tr class="">
									<td></td>
									<td class="danger col-sm-1">Comment(s) :</td>
									<td class="danger col-sm-11" colspan="3"
										title="${itemRcvApproveHistory.justification}">${itemRcvApproveHistory.justification}</td>
								</tr>
							</c:if>
						</table>
					</c:forEach>
				</c:if>
			</div>

			<label style="font-style: italic; font-weight: bold;">Cost Of
				Materials :</label>
			<div style="background: white;" class="col-xs-12 table-responsive">
				<c:if test="${empty costEstimateMaterialsDtlList}">
					<div class="col-sm-12 center">
						<p class="red">
							<i>Sorry!!! No Data Found in Database. </i>
						</p>
					</div>
				</c:if>
				<c:if test="${!empty costEstimateMaterialsDtlList}">
					<table
						class="table table-striped table-hover table-bordered  col-sm-12">
						<thead>
							<tr
								style="background: #579EC8; color: white; font-weight: normal;">
								<td style="">Item Code</td>
								<td style="">Item Name</td>
								<td style="">Unit</td>
								<td style="">Quantity</td>
								<td style="">Unit Cost</td>
								<td style="">Total Cost</td>
								<td style="">Remarks</td>
							</tr>
						</thead>

						<tbody>
							<c:forEach items="${costEstimateMaterialsDtlList}"
								var="costEstimateMaterialsDtl" varStatus="loop">

								<tr id="myArea${loop.index}">

									<td><input type="text" style="width: 80px;"
										id="matItemCode${loop.index}" name="matItemCode"
										value="${costEstimateMaterialsDtl.itemCode}" /></td>
									<td><input type="hidden" name="matItemName"
										id="description${loop.index}"
										value="${costEstimateMaterialsDtl.itemName}" /> <select
										style="width: 250px;" class="matItemName"
										id="matItemName${loop.index}" onchange="itemLeaveChange(this)">
											<option value="">${costEstimateMaterialsDtl.itemName}</option>
											<c:if test="${!empty itemList}">
												<c:forEach items="${itemList}" var="item">
													<option value="${item.id}">
														<c:out value="${item.itemName}" /></option>
												</c:forEach>
											</c:if>
									</select></td>

									<td><input type="text" style="width: 70px;" class="matUom"
										id="matUom${loop.index}" name="matUom"
										value="${costEstimateMaterialsDtl.uom}" /></td>
									<td><input type="text" id="matQuantity${loop.index}"
										onblur="setMatTotalCost(this)" name="matQuantity"
										value="${costEstimateMaterialsDtl.quantity}" /></td>
									<td><input type="text" id="matUnitPrice${loop.index}"
										onblur="setMatTotalCost(this)" name="matUnitPrice"
										value="${costEstimateMaterialsDtl.unitPrice}" /></td>
									<td><input type="text" id="matAmount${loop.index}"
										name="matAmount" readonly="readonly"
										value="${costEstimateMaterialsDtl.totalPrice}" /></td>
									<td><input type="text" name="matRemarks"
										value="${costEstimateMaterialsDtl.remarks}" /></td>

								</tr>
								<c:set var="count" value="${loop.count}" scope="page" />
							</c:forEach>
						</tbody>
					</table>
				</c:if>
			</div>

			<label for="fax" class="col-sm-4 control-label"
				style="font-style: italic; font-weight: bold;">Cost Of
				Installation :</label>
			<div style="background: white;">
				<c:if test="${empty costEstimateInstallationDtlList}">
					<div class="col-sm-12 center">
						<p class="red">
							<i>Sorry!!! No Data Found in Database. </i>
						</p>
					</div>
				</c:if>
				<c:if test="${!empty costEstimateInstallationDtlList}">
					<table id="requisitionListTable"
						class="table table-striped table-hover table-bordered">
						<thead>
							<tr
								style="background: #579EC8; color: white; font-weight: normal;">

								<td style="">Item Name</td>
								<td style="">Unit</td>
								<td style="">Quantity</td>
								<td style="">Unit Cost</td>
								<td style="">Total Cost</td>
								<td style="">Remarks</td>
							</tr>
						</thead>

						<tbody>
							<c:forEach items="${costEstimateInstallationDtlList}"
								var="costEstimateInstallationDtl" varStatus="loop">
								<tr id="myArea1${loop.index}">

									<td><input type="hidden" name="insItemName"
										class="descriptions${loop.index}"
										value="${costEstimateInstallationDtl.itemName}" /> <select
										class="form-control insItemName" id="insItemName${loop.index}"
										onchange="itemLeaveChange1(this)">
											<option value="">${costEstimateInstallationDtl.itemName}</option>
											<c:if test="${!empty itemList}">
												<c:forEach items="${itemList}" var="item">
													<option value="${item.id}">
														<c:out value="${item.itemName}" /></option>
												</c:forEach>
											</c:if>
									</select></td>

									<td><input type="text" id="insUom${loop.index}"
										name="insUom" value="${costEstimateInstallationDtl.uom}" /></td>
									<td><input type="text" id="insQuantity${loop.index}"
										onblur="setInsTotalCost(this)" name="insQuantity"
										value="${costEstimateInstallationDtl.quantity}" /></td>
									<td><input type="text" id="insUnitPrice${loop.index}"
										onblur="setInsTotalCost(this)" name="insUnitPrice"
										value="${costEstimateInstallationDtl.unitPrice}" /></td>
									<td><input type="text" id="insAmount${loop.index}"
										name="insAmount" readonly="readonly"
										value="${costEstimateInstallationDtl.totalPrice}" /></td>
									<td><input type="text" name="insRemarks"
										value="${costEstimateInstallationDtl.remarks}" /></td>

								</tr>
								<c:set var="count" value="${loop.count}" scope="page" />
							</c:forEach>
						</tbody>
					</table>
				</c:if>
			</div>
			<label for="fax" class="col-sm-4 control-label"
				style="font-style: italic; font-weight: bold;">Cost Of
				Recovery :</label>
			<div style="background: white;">
				<c:if test="${empty costEstimateRecoveryDtlList}">
					<div class="col-sm-12 center">
						<p class="red">
							<i>Sorry!!! No Data Found in Database. </i>
						</p>
					</div>
				</c:if>
				<c:if test="${!empty costEstimateRecoveryDtlList}">
					<table id="requisitionListTable"
						class="table table-striped table-hover table-bordered">
						<thead>
							<tr
								style="background: #579EC8; color: white; font-weight: normal;">

								<td style="">Item Name</td>
								<td style="">Unit</td>
								<td style="">Quantity</td>
								<td style="">Unit Cost</td>
								<td style="">Total Cost</td>
								<td style="">Remarks</td>
							</tr>
						</thead>

						<tbody>
							<c:forEach items="${costEstimateRecoveryDtlList}"
								var="costEstimateRecoveryDtl" varStatus="loop">
								<tr id="myArea2${loop.index}">

									<td><input type="hidden" name="recItemName"
										class="descriptions${loop.index}"
										value="${costEstimateRecoveryDtl.itemName}" /> <select
										class="form-control recItemName" id="recItemName${loop.index}"
										onchange="itemLeaveChange1(this)">
											<option value="">${costEstimateRecoveryDtl.itemName}</option>
											<c:if test="${!empty itemList}">
												<c:forEach items="${itemList}" var="item">
													<option value="${item.id}">
														<c:out value="${item.itemName}" /></option>
												</c:forEach>
											</c:if>
									</select></td>

									<td><input type="text" id="recUom${loop.index}"
										name="recUom" value="${costEstimateRecoveryDtl.uom}" /></td>
									<td><input type="text" id="recQuantity${loop.index}"
										onblur="setRecTotalCost(this)" name="recQuantity"
										value="${costEstimateRecoveryDtl.quantity}" /></td>
									<td><input type="text" id="recUnitPrice${loop.index}"
										onblur="setRecTotalCost(this)" name="recUnitPrice"
										value="${costEstimateRecoveryDtl.unitPrice}" /></td>
									<td><input type="text" id="recAmount${loop.index}"
										name="recAmount" readonly="readonly"
										value="${costEstimateRecoveryDtl.totalPrice}" /></td>
									<td><input type="text" name="recRemarks"
										value="${costEstimateRecoveryDtl.remarks}" /></td>

								</tr>
								<c:set var="count" value="${loop.count}" scope="page" />
							</c:forEach>
						</tbody>
					</table>
				</c:if>
			</div>

			<label for="fax" class="col-sm-4 control-label"
				style="font-style: italic; font-weight: bold;">Cost Of
				Miscellanious :</label>
			<div style="background: white;" class='col-xs-12 table-responsive'>
				<c:if test="${empty costEstimateMiscellaniousDtlList}">
					<div class="col-sm-12 center">
						<p class="red">
							<i>Sorry!!! No Data Found in Database. </i>
						</p>
					</div>
				</c:if>
				<c:if test="${!empty costEstimateMiscellaniousDtlList}">
					<table id="requisitionListTable"
						class="table table-striped table-hover table-bordered">
						<thead>
							<tr
								style="background: #579EC8; color: white; font-weight: normal;">

								<td style="">Item Name</td>
								<td style="">Unit</td>
								<td style="">Quantity</td>
								<td style="">Unit Cost</td>
								<td style="">Total Cost</td>
								<td style="">Remarks</td>
							</tr>
						</thead>

						<tbody>
							<c:forEach items="${costEstimateMiscellaniousDtlList}"
								var="costEstimateMiscellaniousDtl" varStatus="loop">
								<tr id="myArea3${loop.index}">
									<td><input type="text" name="misItemName"
										class="description"
										value="${costEstimateMiscellaniousDtl.itemName}" /></td>
									<td><input type="text" id="misUom${loop.index}"
										name="misUom" value="${costEstimateMiscellaniousDtl.uom}" /></td>
									<td><input type="text" id="misQuantity${loop.index}"
										onblur="setMisTotalCost(this)" name="misQuantity"
										value="${costEstimateMiscellaniousDtl.quantity}" /></td>
									<td><input type="text" id="misUnitPrice${loop.index}"
										onblur="setMisTotalCost(this)" name="misUnitPrice"
										value="${costEstimateMiscellaniousDtl.unitPrice}" /></td>
									<td><input type="text" id="misAmount${loop.index}"
										name="misAmount" readonly="readonly"
										value="${costEstimateMiscellaniousDtl.totalPrice}" /></td>
									<td><input type="text" name="misRemarks"
										value="${costEstimateMiscellaniousDtl.remarks}" /></td>

								</tr>
								<c:set var="count" value="${loop.count}" scope="page" />
							</c:forEach>
						</tbody>
					</table>
				</c:if>
			</div>


			<!-- <div class="text-center"> -->
			<div class="row" style='margin-top: 50px;'>
				<label class="col-xs-2"> <strong>Comment(s) : </strong></label>
				<div class="col-xs-10">
					<textarea required class="form-control" rows="2" cols=""
						id="justification" name="justification"></textarea>
				</div>
			</div> 
			<div class="col-xs-12">
				<hr />
			</div>
			<div class="row">
				<%-- <div class="col-md-4 col-sm-4 text-left">
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
				</div> --%>

				<%-- <div class="col-sm-6 text-left">
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
										href="javascript:void(0)"> For ${backedMan.buttonName} </a> <c:forEach
											items="${backedMan.authUser}" var="users">
											<ul class="dropdown-menu"
												style="height: ${fn:length(backedMan.authUser)*32}px">
												<li><a tabindex="-1"
													href="Javascript:backToLower(${backedMan.stateCode}, '${users.userid}')">
														${users.name}-(${users.designation})</a></li>
											</ul>
										</c:forEach></li>
								</c:forEach>
							</ul>
						</div>
					</c:if>
				</div>

				<div class="col-sm-6 text-center">
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
						<div class="dropup pull-left">
							<input type="hidden" name="userid" id="userid" value="">
							<input type="hidden" name="stateCode" id="stateCode" value="">


							<button class="btn btn-info dropdown-toggle" type="button"
								style="border-radius: 6px;" data-toggle="dropdown"
								aria-haspopup="true" aria-expanded="false">
								Send to <span class="caret"></span>
							</button>
							<ul class="dropdown-menu">
								<c:forEach items="${nextManRcvProcs}" var="nextMan">
									<li class="dropdown-submenu"><a tabindex="-1"
										href="javascript:void(0)"> For ${nextMan.buttonName} </a> <c:forEach
											items="${nextMan.authUser}" var="users">
											<ul class="dropdown-menu"
												style="height: ${fn:length(nextMan.authUser)*32}px">
												<li><a tabindex="-1"
													href="Javascript:forwardToUpper(${nextMan.stateCode}, '${users.userid}')">
														${users.name}-(${users.designation})</a></li>
											</ul>
										</c:forEach></li>
								</c:forEach>
							</ul>
						</div>
					</c:if>
					<div class="pull-left" style="margin-left: 25px;">
						<a href="Javascript:approvedCostEstimation()"
							class="btn btn-success" style="border-radius: 6px;"> Approve</a>
					</div>
				</div> --%>

				<%-- <div class="col-md-4 col-sm-4 text-right">
					<a class="btn btn-primary" href="#"
						onclick="document.getElementById('myForm').submit();"
						style="text-decoration: none; border-radius: 6px;">
						${buttonValue} </a>
				</div> --%>

			</div>
		</form>
	</div>

</div>


<!-- ------Start of Location Grid Div ----- -->
<!-- <div id="myGrid" title="Title">
	<form action="" method="post">
		<table id="tblAppendGrid">
		</table>
	</form>
</div> -->
<!-- ------End of Location Grid Div ----- -->

<script
	src="${pageContext.request.contextPath}/resources/assets/js/contractor/jobEstimationShow.js"></script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>