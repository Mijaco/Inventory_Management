<%@include file="../common/lsHeader.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/ls/issued/finalList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Item
				Issued List
			</a>
		</div>
		<h2 class="center blue"
			style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Item
			Issued Show</h2>
		<h4 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptName}</h4>


	</div>

	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px;">
		<!-- --------------------- -->
		<div class="oe_title">

			<table class="col-sm-12 table">
				<tr class="">
					<td class="success">Requisition No:</td>
					<td class="info">${lsIssuedMst.requisitionNo}</td>
					<td class="success">Requisition Date:</td>
					<td class="info"><fmt:formatDate
							value="${lsIssuedMst.requisitionDate}"
							pattern="dd-MM-yyyy hh:mm:ss a" /></td>
					<td class="success">Store Ticket No:</td>
					<td class="info">${lsIssuedMst.storeTicketNO}</td>


				</tr>

				<tr class="">
					<td class="success">Gate Pass No:</td>
					<td class="info">${lsIssuedMst.gatePassNo}</td>
					<td class="success">Approved</td>
					<td class="info"><c:choose>
							<c:when test="${lsIssuedMst.received}">
       										Yes
    									</c:when>

							<c:otherwise>
        									No
    									</c:otherwise>
						</c:choose></td>
					<td class="success">Approved ?</td>
					<td class="info"><c:choose>
							<c:when test="${lsIssuedMst.approved}">
       										Yes
    									</c:when>

							<c:otherwise>
        									No
    									</c:otherwise>
						</c:choose></td>
				</tr>
				<tr class="">
					<td class="success">Received By:</td>
					<td class="info">${lsIssuedMst.receivedBy}</td>
					<td class="success">Created By:</td>
					<td class="info">${lsIssuedMst.createdBy}</td>
					<td class="success">Created Date:</td>
					<td class="info"><fmt:formatDate
							value="${lsIssuedMst.createdDate}"
							pattern="dd-MM-yyyy hh:mm:ss a" /></td>

				</tr>
				<tr class="">
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
									value="${approveHistoryHistory.createdDate}"
									pattern="dd-MM-yyyy hh:mm:ss a" /></td>
						</tr>
					</table>
				</c:forEach>
			</c:if>
		</div>

		<div style="background: white;">
			<c:if test="${empty lsIssuedDtlList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty lsIssuedDtlList}">
				<table id="testTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Item Code</td>
							<td style="">Description</td>
							<td style="">Unit</td>
							<!-- <td style="">Ledger Name</td> -->
							<td style="">Required Quantity</td>
							<td style="">Issued New Serviceable</td>
							<td style="">Issued Recovery Serviceable</td>
							<td style="">Remarks</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${lsIssuedDtlList}" var="lsIssuedDtl">
							<tr>
								<td><c:out value="${lsIssuedDtl.itemCode}" /></td>
								<td><c:out value="${lsIssuedDtl.itemName}" /></td>
								<td><c:out value="${lsIssuedDtl.uom}" /></td>
								<%-- <td><c:out value="${lsIssuedDtl.ledgerName}" /></td> --%>
								<td><c:out value="${lsIssuedDtl.quantityRequired}" /></td>
								<td><c:out value="${lsIssuedDtl.quantityIssuedNS}" /></td>
								<td><c:out value="${lsIssuedDtl.quantityIssuedRS}" /></td>
								<td><c:out value="${lsIssuedDtl.remarks}" /></td>
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