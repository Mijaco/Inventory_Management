<%@include file="../common/csHeader.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/cs/received/finalList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Item
				Received List
			</a>
		</div>
		<h2 class="center blue"
			style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Item
			Received (Return) Show</h2>
		<h4 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptName}</h4>

		<%-- <h5 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptAddress}</h5> --%>
	</div>

	<div class="row"
		style="background-color: white; padding-left: 10px; margin: 10px; padding-bottom: 10px; padding-top: 10px;">
		<!-- --------------------- -->
		<div class="oe_title">

			<table class="col-sm-12 table">
				<tr class="">
					<td class="success">Return Slip No:</td>
					<td class="info">${returnSlipMst.returnSlipNo}</td>
					<td class="success">Return Slip Date:</td>
					<td class="info"><fmt:formatDate
							value="${returnSlipMst.returnSlipDate}"
							pattern="dd-MM-yyyy" /></td>
					<td class="success">Approved</td>
					<td class="info"><c:choose>
							<c:when test="${returnSlipMst.approved}">
       										Yes
    									</c:when>

							<c:otherwise>
        									No
    									</c:otherwise>
						</c:choose></td>
				</tr>

				<tr class="">
					<td class="success">Received From:</td>
					<td class="info">${returnSlipMst.receiveFrom}</td>
					<td class="success">Created By:</td>
					<td class="info">${returnSlipMst.createdBy}</td>
					<td class="success">Created Date:</td>
					<td class="info"><fmt:formatDate
							value="${returnSlipMst.createdDate}"
							pattern="dd-MM-yyyy" /></td>
				</tr>
			</table>
		</div>


		<c:if test="${!empty approveHistoryHistoryList}">
			<button data-toggle="collapse" data-target="#demo">
				<span class="glyphicon glyphicon-collapse-down"></span>
			</button>
		</c:if>
		<div id="demo" class="collapse">
			<c:if test="${!empty approveHistoryHistoryList}">
				<!-- <div class="" style="font-weight: bold;">Approval History</div> -->

				<c:forEach items="${approveHistoryHistoryList}"
					var="approveHistoryHistory">
					<div class=""
						style="font-weight: bold; font-style: italic; text-transform: capitalize">${fn:toLowerCase(approveHistoryHistory.approvalHeader)}</div>
					<hr style="margin: 5px 0px 10px 0px" />
					<table class="col-sm-12 table">

						<tr class="">
							<td class="success col-sm-3" style="text-transform: capitalize">${fn:toLowerCase(approveHistoryHistory.stateName)}
								By:</td>
							<td class="success col-sm-3 text-left">
								${approveHistoryHistory.cEmpFullName} <c:if
									test="${!empty approveHistoryHistory.cDesignation}">
									, ${approveHistoryHistory.cDesignation} 
								</c:if> <c:if test="${!empty approveHistoryHistory.cEmpId}">
									( ${approveHistoryHistory.cEmpId} )
								</c:if>
							</td>
							<td class="info col-sm-3 text-right"
								style="text-transform: capitalize">${fn:toLowerCase(approveHistoryHistory.stateName)}
								Date:</td>
							<td class="info col-sm-3"><fmt:formatDate
							value="${approveHistoryHistory.createdDate}" pattern="dd-MM-yyyy" /></td>
						</tr>
					</table>
				</c:forEach>
			</c:if>
		</div>

		<div style="background: white;">
			<c:if test="${empty returnSlipDtlList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty returnSlipDtlList}">
				<table id="testTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Item Code</td>
							<td style="">Description</td>
							<td style="">Unit</td>
							<td style="">New Serviceable</td>
							<td style="">Recovery Serviceable</td>
							<td style="">UnServiceable</td>
							<td style="">Remarks</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${returnSlipDtlList}" var="returnSlipDtl">
							<tr>
								<td><c:out value="${returnSlipDtl.itemCode}" /></td>
								<td><c:out value="${returnSlipDtl.description}" /></td>
								<td><c:out value="${returnSlipDtl.uom}" /></td>
								<td><c:out value="${returnSlipDtl.qtyNewServiceableRcv}" /></td>
								<td><c:out value="${returnSlipDtl.qtyRecServiceableRcv}" /></td>
								<td><c:out value="${returnSlipDtl.qtyUnServiceableRcv}" /></td>
								<td><c:out value="${returnSlipDtl.remarks}" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
		</div>
	</div>
</div>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>