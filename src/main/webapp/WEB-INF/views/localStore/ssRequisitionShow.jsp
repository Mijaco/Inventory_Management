<%@include file="../common/ssHeader.jsp"%>
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
			<a href="${pageContext.request.contextPath}/ls/requisitionList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Item
				Requisition List
			</a>
		</div>
		<h2 class="center blue ubuntu-font" style="margin-top: 0px;">Store
			Requisition</h2>

		<h4 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptName}</h4>

		<%-- <h5 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptAddress}</h5> --%>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; margin:10px;">
		<!-- --------------------- -->
		<div class="oe_title">

			<table class="col-sm-12 table">
				<%-- <tr class="">
					<td class="success">Requisition No: <input type="hidden"
						value="${subStoreRequisitionMst.requisitionNo}" id="requisitionNo" />
						<input type="hidden" value="${returnStateCode}"
						id="returnStateCode" /> <input type="hidden"
						value="${subStoreRequisitionMst.uuid}" id="uuid" /> <input
						type="hidden" name="contextPath" id="contextPath"
						value="${pageContext .request.contextPath}" />
						<input type="hidden" value=${locationList}
						id="locationList" />
						<input type="hidden" value=${ledgerBookList}
						id="ledgerBookList" />
						 
					</td>

					<td class="info">${subStoreRequisitionMst.requisitionNo}</td>
					<td class="success">Created By:</td>
					<td class="info">${subStoreRequisitionMst.createdBy}</td>
					<td class="success">Created Date:</td>
					<td class="info"><fmt:formatDate
						value="${subStoreRequisitionMst.createdDate}" pattern="dd-MM-yyyy" /></td>
				</tr>
				<tr class="">
					<td class="success">Modified By:</td>
					<td class="info">${subStoreRequisitionMst.modifiedBy}</td>
					<td class="success">Modified Date:</td>
					<td class="info"><fmt:formatDate
						value="${subStoreRequisitionMst.modifiedDate}" pattern="dd-MM-yyyy" /></td>
					<td class="success">Present Status:</td>
					<td class="info"><strong> ${currentStatus} </strong></td>
				</tr> --%>

				<tr class="">
					<td class="success">Requisition No: <input type="hidden"
						value="${subStoreRequisitionMst.requisitionNo}" id="requisitionNo" />
						<input type="hidden" value="${returnStateCode}"
						id="returnStateCode" /> <input type="hidden"
						value="${subStoreRequisitionMst.uuid}" id="uuid" /> <input
						type="hidden" name="contextPath" id="contextPath"
						value="${pageContext .request.contextPath}" /> <input
						type="hidden" value=${locationList } id="locationList" /> <input
						type="hidden" value=${ledgerBookList
						} id="ledgerBookList" />
					</td>

					<td class="info">${subStoreRequisitionMst.requisitionNo}</td>
					<td class="success">Created By:</td>
					<td class="info">${subStoreRequisitionMst.createdBy}</td>
					<td class="success">Requisition To:</td>
					<td class="info"><c:if
							test="${subStoreRequisitionMst.requisitionTo=='cs'}">Central Store </c:if>
						<c:if test="${subStoreRequisitionMst.requisitionTo=='ss'}">Sub Store </c:if>
					</td>
				</tr>
				<tr class="">
					<td class="success">Indenter</td>
					<td class="info">${subStoreRequisitionMst.identerDesignation},
						${subStoreRequisitionMst.deptName}</td>
					<td class="success">Requisition Date:</td>
					<td class="info"><fmt:formatDate
							value="${subStoreRequisitionMst.createdDate}"
							pattern="dd-MM-yyyy hh:mm:ss a" /></td>
					<td class="success">Present Status:</td>
					<td class="info"><strong> ${currentStatus} </strong></td>
				</tr>
				<tr>
					<td class="success">Project Name:</td>
					<td class="info">${subStoreRequisitionMst.khathName}</td>
					<td class="success">Carried By:</td>
					<td class="info">${subStoreRequisitionMst.carriedBy}</td>
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
				<!-- <div class="" style="font-weight: bold;">Approval History</div> -->

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
									pattern="dd-MM-yyyy hh:mm:ss a" /></td>
						</tr>
						<tr class="">
							<td></td>
							<td class="danger col-sm-1">Comment(s) :</td>
							<td class="danger" title="${itemRcvApproveHistory.justification}">${itemRcvApproveHistory.justification}</td>
							<td class="success" colspan="2"><c:if
									test="${!empty itemRcvApproveHistory.historyChange}">
									<strong>Log</strong>: ${itemRcvApproveHistory.historyChange}
								</c:if></td>
						</tr>

					</table>
				</c:forEach>
			</c:if>
		</div>


		<div style="background: white;">
			<c:if test="${empty subStoreRequisitionDtlList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty subStoreRequisitionDtlList}">
				<table id="requisitionListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Item Code</td>
							<td style="">Description</td>
							<td style="">Unit</td>
							<td style="">Required Qty</td>
							<td style="">Store Qty (NS)</td>
							<td style="">Store Qty (RS)</td>
							<td style="">Issued Qty</td>
							<!-- <td style="">Unit Cost</td>
							<td style="">Total Cost</td> -->
							<td style="">Remarks</td>
							<td style="">Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${subStoreRequisitionDtlList}"
							var="subStoreRequisitionDtl" varStatus="loop">
							<tr>

								<td><c:out value="${subStoreRequisitionDtl.itemCode}" /></td>
								<td><c:out value="${subStoreRequisitionDtl.itemName}" /></td>
								<td><c:out value="${subStoreRequisitionDtl.uom}" /></td>
								<td id="requiredQty${loop.index}" class="requiredQty"><c:out
										value="${subStoreRequisitionDtl.quantityRequired}" /></td>
								<td>${subStoreRequisitionDtl.newServiceableStockQty}</td>
								<td>${subStoreRequisitionDtl.recoveryServiceableStockQty}</td>
								<td><input class="issuedQty" type="number"
									readOnly="readonly" id="issuedQty${loop.index}"
									name="quantityIssued" step="0.001"
									value="${subStoreRequisitionDtl.quantityIssued}" /></td>

								<td><c:out value="${subStoreRequisitionDtl.remarks}" /></td>
								<td>
									<div class="action-buttons"
										data-itemcode="${subStoreRequisitionDtl.itemCode}">
										<a href="#" class="btn btn-primary"
											id="setDialog${loop.index}"> <i
											class="glyphicon glyphicon-edit"
											onclick="openGridDialog(this)" aria-hidden="true"> </i>
										</a>
									</div>
								</td>
							</tr>
							<c:set var="count" value="${loop.count}" scope="page" />
						</c:forEach>
					</tbody>
				</table>
			</c:if>

		</div>



		<!-- <div class="text-center"> -->
		<div class="row">
			<label class="col-xs-2"> <strong>Comment(s):&nbsp;<strong class='red'>*</strong></strong></label>
			<div class="col-xs-10">
				<textarea class="form-control" rows="2" cols="" id="justification"></textarea>
				<strong class="justification text-danger hide">This field is required</strong>
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
				<button class="btn btn-primary" id="approveButton"
					onclick="approveLsSsRequisition()"
					style="text-decoration: none; border-radius: 6px;">
					${buttonValue} </button>
				
				<a class="btn btn-danger"
					href="Javascript:ssrejectProcess()"
					style="text-decoration: none; border-radius: 6px;">
					<i class="fa fa-fw fa-ban"></i>&nbsp;Reject
				</a>
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

<!-- ------Start of Location Grid Div ----- -->
<div id="myGrid" title="Title">
	<form action="" method="post">
		<table id="tblAppendGrid">
		</table>
	</form>
</div>
<!-- ------End of Location Grid Div ----- -->

<script
	src="${pageContext.request.contextPath}/resources/assets/js/localStore/lsSsRequisitionShowAddedLocation.js"></script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>