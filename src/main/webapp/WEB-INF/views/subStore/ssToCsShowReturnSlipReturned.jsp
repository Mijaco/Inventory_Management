<%@include file="../common/ssHeader.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- -------------------End of Header-------------------------- -->

<!-- -------------------Start of Style and Script for Location Grid-------------------------- -->
<style type="text/css">
.btn-add {
	margin-bottom: 20px;
}

.ui-widget-overlay {
	opacity: .6 !important;
}
</style>
<!-- -------------------End of Style and Script for Location Grid-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/ss/locUp/returnedItemList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Return Slip List
			</a>
		</div>
		<h2 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Return
			Slip</h2>

		<h4 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptName}</h4>

		<%-- <h5 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptAddress}</h5> --%>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin: 10px; ">
		<!-- --------------------- -->
		<div class="oe_title">

			<table class="col-sm-12 table">
				<tr class="">
					<td class="success">Return Slip No: <input type="hidden"
						value="${returnSlipMst.returnSlipNo}" id="returnSlipNo" /> <input
						type="hidden" value="${returnStateCode}" id="returnStateCode" />
						<input type="hidden" value="${returnSlipMst.uuid}" id="uuid"
						name="uuid" /> <input type="hidden" name="contextPath"
						id="contextPath" value="${pageContext.request.contextPath}" /> <input
						type="hidden" value=${locationList } id="locationList" /> <input
						type="hidden" value=${ledgerBookList } id="ledgerBookList" />
					</td>

					<td class="info">${returnSlipMst.returnSlipNo}</td>
					<td class="success">Created By:</td>
					<td class="info">${returnSlipMst.createdBy}</td>
					<td class="success">Created Date:</td>
					<td class="info"><fmt:formatDate
						value="${returnSlipMst.createdDate}" pattern="dd-MM-yyyy" /></td>
				</tr>
				

			</table>
		</div>


		<%-- <c:if test="${!empty approveHistoryList}">
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
							<td class="success col-sm-2 text-left">${approveHistory.createdBy}</td>
							<td class="info col-sm-2 text-left"
								style="text-transform: capitalize">${fn:toLowerCase(approveHistory.stateName)}
								Date:</td>
							<td class="info col-sm-3">${approveHistory.createdDate}</td>
						</tr>
						<c:if test="${!empty approveHistory.justification}">
							<tr class="">
								<td class="danger col-sm-1">Justification :</td>
								<td class="danger col-sm-11" colspan="4"
									title="${approveHistory.justification}">${approveHistory.justification}</td>
							</tr>
						</c:if>
					</table>
				</c:forEach>
			</c:if>
		</div> --%>


		<div style="background: white;">
			<c:if test="${empty returnSlipDtlList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty returnSlipDtlList}">
				<div class="col-xs-12 table-responsive">
					<div class="table">
						<div style="width: 1800px;">
							<table id="requisitionListTable"
								class="table table-striped table-hover table-bordered table-responsive">
								<thead>
									<tr
										style="background: #579EC8; color: white; font-weight: normal;">
										<td style="">Item Code</td>
										<td style="">Description</td>
										<td style="">Unit</td>
										<td style="">Received Quantity (CS) <br /> New
											Serviceable
										</td>
										<td style="">Received Quantity (CS)<br /> Recovery
											Serviceable
										</td>
										<td style="">Received Quantity (CS)<br /> UnServiceable
										</td>
										<td style="">Total Received (CS)</td>

										<td style="">Return Quantity (SS)<br /> New Serviceable
										</td>
										<td style="">Return Quantity (SS)<br /> Recovery
											Serviceable
										</td>
										<td style="">Return Quantity (SS)<br /> UnServiceable
										</td>
										<td style="">Total Return (SS)</td>
										<td style="">Remarks</td>
										<td style="">Action</td>
									</tr>
								</thead>

								<tbody>
									<c:forEach items="${returnSlipDtlList}" var="returnSlipDtl"
										varStatus="loop">
										<tr>
											<td><c:out value="${returnSlipDtl.itemCode}" /></td>
											<td><c:out value="${returnSlipDtl.description}" /></td>
											<td><c:out value="${returnSlipDtl.uom}" /></td>
											<td class="qtyNewServiceableRcv"
												id="qtyNewServiceableRcv${loop.index}"><c:out
													value="${returnSlipDtl.qtyNewServiceableRcv}" /></td>
											<td class="qtyRecServiceableRcv"
												id="qtyRecServiceableRcv${loop.index}"><c:out
													value="${returnSlipDtl.qtyRecServiceableRcv}" /></td>
											<td class="qtyUnServiceableRcv"
												id="qtyUnServiceableRcv${loop.index}"><c:out
													value="${returnSlipDtl.qtyUnServiceableRcv}" /></td>
											<td class="totalReturnRcv" id="totalReturnRcv${loop.index}"><c:out
													value="${returnSlipDtl.totalReturnRcv}" /></td>
											<td class="qtyNewServiceable"
												id="qtyNewServiceable${loop.index}">0.0</td>
											<td class="qtyRecServiceable"
												id="qtyRecServiceable${loop.index}">0.0</td>
											<td class="qtyUnServiceable"
												id="qtyUnServiceable${loop.index}">0.0</td>
											<td class="totalReturn" id="totalReturn${loop.index}">0.0</td>
											<td><c:out value="${returnSlipDtl.remarks}" /></td>
											<td>
												<div class="action-buttons" id="locationBtnDiv${loop.index}"
													data-itemcode="${returnSlipDtl.itemCode}">
													<a href="#" class="btn btn-primary"
														id="setDialog${loop.index}"> <i
														class="glyphicon glyphicon-edit"
														onclick="openGridDialog(this)" aria-hidden="true"> </i>
													</a>
												</div>
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</c:if>
		</div>
		<hr />
		<c:if test="${returnSlipMst.returned eq false}">
			<div class="col-xs-12 center" style="margin-top: 15px">
				<button class="btn btn-success small" style="border-radius: 6px"
					onclick="checkAndPostRequest('${pageContext.request.contextPath}/ss/locUp/ssCsItemReturnedLocationUpdate.do',{id:'${returnSlipMst.id}'},'POST')">
					Update Location</button>

			</div>
		</c:if>
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
	src="${pageContext.request.contextPath}/resources/assets/js/subStore/ssToCsShowReturnSlipReturned.js"></script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>