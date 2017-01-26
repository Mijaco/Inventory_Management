<%@include file="../../../common/csHeader.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- -------------------End of Header-------------------------- -->

<!-- -------------------Start of Style and Script for Location Grid-------------------------- -->
<style type="text/css">
.btn-add {
	margin-bottom: 20px;
}
.ui-widget-overlay {    
    opacity: .6!important;   
}
</style>

<!-- -------------------End of Style and Script for Location Grid-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/ls/requisitionList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Item	Requisition List
			</a>
		</div>
		<h2 class="center blue" style="margin-top: 0px;">Store Requisition</h2>

		<h4 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptName}</h4>

		<h5 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptAddress}</h5>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px; margin-left: 90px; margin-right: 90px;">
		<!-- --------------------- -->
		<div class="oe_title">

			<table class="col-sm-12 table">
				<tr class="">
					<td class="success">Requisition No: <input type="hidden"
						value="${centralStoreRequisitionMst.requisitionNo}"
						id="requisitionNo" /> <input type="hidden"
						value="${returnStateCode}" id="returnStateCode" /> <input
						type="hidden" value="${centralStoreRequisitionMst.uuid}" id="uuid" />
						<input type="hidden" name="contextPath" id="contextPath"
						value="${pageContext.request.contextPath}" />
						<input type="hidden" name="locationList" id="locationList"
						value=${locationList} />
						<input type="hidden" name="ledgerBookList" id="ledgerBookList"
						value=${ledgerBookList} />					
					</td>

					<td class="info">${centralStoreRequisitionMst.requisitionNo}</td>
					<td class="success">Created By:</td>
					<td class="info">${centralStoreRequisitionMst.createdBy}</td>
					<td class="success">Created Date:</td>
					<td class="info">
						<fmt:formatDate
							value="${centralStoreRequisitionMst.createdDate}" pattern="dd-MM-yyyy" />
					</td>
				</tr>
				<tr class="">
					<td class="success">Modified By:</td>
					<td class="info">${centralStoreRequisitionMst.modifiedBy}</td>
					<td class="success">Modified Date:</td>
					<td class="info">
						<fmt:formatDate value="${centralStoreRequisitionMst.modifiedDate}" 
							pattern="dd-MM-yyyy" />
					</td>
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
							<td class="info col-sm-3">
								<fmt:formatDate value="${itemRcvApproveHistory.createdDate}" 
									pattern="dd-MM-yyyy" />
							</td>
						</tr>
						<c:if test="${!empty itemRcvApproveHistory.justification}">
							<tr class="">
								<td class="col-sm-1"></td>
								<td class="danger col-sm-1">Comments :</td>
								<td class="danger col-sm-11" colspan="3"
									title="${itemRcvApproveHistory.justification}">${itemRcvApproveHistory.justification}</td>
							</tr>
						</c:if>
					</table>
				</c:forEach>
			</c:if>
		</div>


		<div style="background: white;">
			<c:if test="${empty centralStoreRequisitionDtlList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty centralStoreRequisitionDtlList}">
				<table id="requisitionListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Item Code</td>
							<td style="">Description</td>
							<td style="">Unit</td>
							<td style="">Required Quantity</td>
							<td style="">Issued Quantity</td>
							<!-- <td style="">Unit Cost</td>
							<td style="">Total Cost</td> -->
							<td style="">Remarks</td>
							<td style="">Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${centralStoreRequisitionDtlList}"
							var="centralStoreRequisitionDtl" varStatus="loop">
							<tr>

								<td><c:out value="${centralStoreRequisitionDtl.itemCode}" /></td>
								<td><c:out value="${centralStoreRequisitionDtl.itemName}" /></td>
								<td><c:out value="${centralStoreRequisitionDtl.uom}" /></td>
								<td><c:out
										value="${centralStoreRequisitionDtl.quantityRequired}" /></td>
								<%-- <td><c:out value="${centralStoreRequisitionDtl.quantityIssued}" /></td> --%>
								<td><input class="quantityIssued" type="number"
									readOnly="readonly" id="issuedQty${loop.index}"
									name="quantityIssued"
									value="${centralStoreRequisitionDtl.quantityIssued}" /></td>
								<%-- <td><c:out value="${centralStoreRequisitionDtl.unitCost}" /></td>
								<td><c:out value="${centralStoreRequisitionDtl.totalCost}" /></td> --%>
								<td><c:out value="${centralStoreRequisitionDtl.remarks}" /></td>
								<td>
									<div class="action-buttons"  data-itemcode="${centralStoreRequisitionDtl.itemCode}">
										<a href="#" class="btn btn-primary" id="setDialog${loop.index}">
											<i class="glyphicon glyphicon-edit"  onclick="openGridDialog(this)"
											aria-hidden="true"> </i>
										</a>										
									</div>
								</td>
							</tr>
							<c:set var="count" value="${loop.index}" scope="page" />
						</c:forEach>
					</tbody>
				</table>
			</c:if>

		</div>



		<!-- <div class="text-center"> -->
		<div class="row">
			<label class="col-xs-2"> <strong>Comments : </strong></label>
			<div class="col-xs-10">
				<textarea class="form-control" rows="2" cols="" id="justification"></textarea>
			</div>
		</div>
		<div class="col-xs-12">
			<hr />
		</div>
		<div class="row">
			<div class="col-md-4 col-sm-4 text-left">
				<c:if test="${!empty backManRcvProcs}">
					<!-- <button onclick="backToLower()" class="btn btn-warning"
						style="text-decoration: none; border-radius: 6px;">< Back
						To</button> -->
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
							<!-- <li class=""><a href="Javascript:forwardToUpper()">Action</a></li>
						<li class=""><a href="Javascript:forwardToUpper()">Another
								action</a></li> -->
						</ul>
					</div>
				</c:if>
			</div>
			<div class="col-md-4 col-sm-4 text-center">
				<%-- <a class="btn btn-primary"
					href="${pageContext.request.contextPath}/cs/itemRecieved/itemReceivedSubmitApproved.do?receivedReportNo=${centralStoreRequisitionMst.rrNo}"
					style="text-decoration: none; border-radius: 6px;">
					${buttonValue} </a> --%>
				<a class="btn btn-primary" href="Javascript:approveing()"
					style="text-decoration: none; border-radius: 6px;">
					${buttonValue} </a>
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
	src="${pageContext.request.contextPath}/resources/assets/js/workshop/transformer/wsCsRequisitionShow.js"></script>
<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../../../common/ibcsFooter.jsp"%>