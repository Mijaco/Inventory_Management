<%@include file="../common/lsHeader.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/ls/storeTicket/finalList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Store
				Ticket List
			</a>
		</div>
		<h2 class="center blue"
			style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Store
			Ticket Show</h2>
		<h4 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptName}</h4>

		<%-- <h5 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptAddress}</h5> --%>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px;">
		<!-- --------------------- -->
		<div class="oe_title">

			<table class="col-sm-12 table">
				<tr class="">
					<td class="success">Store Ticket:</td>
					<td class="info">${lsStoreTicketMst.ticketNo}</td>
					<td class="success">R. No:</td>
					<td class="info">${lsStoreTicketMst.operationId}</td>
					<td class="success">Ticket Date:</td>
					<td class="info"><fmt:formatDate
							value="${lsStoreTicketMst.ticketDate}"
							pattern="dd-MM-yyyy" /></td>
					<%-- <td >${csStoreTicketMst.ticketDate}</td> --%>

				</tr>
				<c:if
					test="${lsStoreTicketMst.storeTicketType eq 'LS_ISSUED_REQUISITION'}">
					<tr class="">
						<td class="success">Issued To:</td>
						<td class="info">${lsStoreTicketMst.issuedTo}</td>
						<td class="success">Issued For:</td>
						<td class="info">${lsStoreTicketMst.issuedFor}-(${lsStoreTicketMst.sndCode})</td>
						<td class="success">Issued By:</td>
						<td class="info">${lsStoreTicketMst.createdBy}</td>
					</tr>
				</c:if>
								
				<c:if
					test="${lsStoreTicketMst.storeTicketType eq 'CN2_LS_RETURN_SLIP'}">
					<tr class="">
						<td class="success">Received From:</td>
						<td class="info">${lsStoreTicketMst.receivedFrom}</td>
						<td class="success">Return For :</td>
						<td class="info">${lsStoreTicketMst.returnFor}-(${lsStoreTicketMst.sndCode})</td>
						<td class="success">Received By:</td>
						<td class="info">${lsStoreTicketMst.createdBy}</td>
					</tr>
				</c:if>


				<tr class="">
					<td class="success">Ticket Type:</td>
					<td class="info">${lsStoreTicketMst.storeTicketType}</td>
					<td class="success">Created By:</td>
					<td class="info">${lsStoreTicketMst.createdBy}</td>
					<td class="success">Created Date:</td>
					<td class="info"><fmt:formatDate
							value="${lsStoreTicketMst.createdDate}"
							pattern="dd-MM-yyyy" /></td>
				</tr>
				<tr class="">
				</tr>

			</table>
		</div>


		<c:if test="${!empty storeTicketApproveHistoryList}">
			<button data-toggle="collapse" data-target="#demo">
				<span class="glyphicon glyphicon-collapse-down"></span>
			</button>
		</c:if>
		<div id="demo" class="collapse">
			<c:if test="${!empty storeTicketApproveHistoryList}">
				<!-- <div class="" style="font-weight: bold;">Approval History</div> -->

				<c:forEach items="${storeTicketApproveHistoryList}"
					var="storeTicketApproveHistory">
					<div class=""
						style="font-weight: bold; font-style: italic; text-transform: capitalize">${fn:toLowerCase(storeTicketApproveHistory.approvalHeader)}</div>
					<hr style="margin: 5px 0px 10px 0px" />
					<table class="col-sm-12 table">

						<tr class="">
							<td class="success col-sm-3" style="text-transform: capitalize">${fn:toLowerCase(storeTicketApproveHistory.stateName)}
								By:</td>
							<td class="success col-sm-3 text-left">${storeTicketApproveHistory.createdBy}</td>
							<td class="info col-sm-3 text-right"
								style="text-transform: capitalize">${fn:toLowerCase(storeTicketApproveHistory.stateName)}
								Date:</td>
							<td class="info col-sm-3">${storeTicketApproveHistory.createdDate}</td>
						</tr>
					</table>
				</c:forEach>
			</c:if>
		</div>

		<div style="background: white;">
			<c:if test="${empty lsStoreTicketDtlList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty lsStoreTicketDtlList}">
				<table id="requisitionListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Item Code</td>
							<td style="">Description</td>
							<td style="">Item Type</td>
							<td style="">Unit</td>
							<td style="">Quantity</td>
							<td style="">Ledger No</td>
							<td style="">Page No</td>
<!-- 							<td style="">Cost</td> -->
							<td style="">Remarks</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${lsStoreTicketDtlList}" var="lsStoreTicketDtl">
							<tr>
								<td><c:out value="${lsStoreTicketDtl.itemId}" /></td>
								<td><c:out value="${lsStoreTicketDtl.description}" /></td>
								<td><c:out value="${lsStoreTicketDtl.ledgerName}" /></td>
								<td><c:out value="${lsStoreTicketDtl.uom}" /></td>
								<td><c:out value="${lsStoreTicketDtl.quantity}" /></td>
								<td><c:out value="${lsStoreTicketDtl.ledgerBookNo}" /></td>
								<td><c:out value="${lsStoreTicketDtl.ledgerPageNo}" /></td>
<%-- 								<td><c:out value="${lsStoreTicketDtl.cost}" /></td> --%>
								<td><c:out value="${lsStoreTicketDtl.remarks}" /></td>
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