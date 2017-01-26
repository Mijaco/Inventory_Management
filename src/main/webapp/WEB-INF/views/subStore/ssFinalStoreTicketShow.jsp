<%@include file="../common/ssHeader.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/ss/storeTicket/finalList.do"
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
					<td class="info">${ssStoreTicketMst.ticketNo}</td>
					<td class="success">R. No:</td>
					<td class="info">${ssStoreTicketMst.operationId}</td>
					<td class="success">Ticket Date:</td>
					<td class="info"><fmt:formatDate
							value="${ssStoreTicketMst.ticketDate}"
							pattern="dd-MM-yyyy" /></td>
					<%-- <td >${csStoreTicketMst.ticketDate}</td> --%>

				</tr>
				<c:if
					test="${ssStoreTicketMst.storeTicketType eq 'LS_SS_REQUISITION' ||
				ssStoreTicketMst.storeTicketType eq 'CN_SS_REQUISITION'}">
					<tr class="">
						<td class="success">Issued To:</td>
						<td class="info">${ssStoreTicketMst.issuedTo}</td>
						<td class="success">Issued For:</td>
						<td class="info">${ssStoreTicketMst.issuedFor}-(${ssStoreTicketMst.sndCode})</td>
						<td class="success">Issued By:</td>
						<td class="info">${ssStoreTicketMst.createdBy}</td>
					</tr>
				</c:if>
								
				<c:if
					test="${ssStoreTicketMst.storeTicketType eq 'LS_SS_RETURN_SLIP' ||
				csStoreTicketMst.storeTicketType eq 'CN_SS_RETURN_SLIP'}">
					<tr class="">
						<td class="success">Received From:</td>
						<td class="info">${ssStoreTicketMst.receivedFrom}</td>
						<td class="success">Return For :</td>
						<td class="info">${ssStoreTicketMst.returnFor}-(${ssStoreTicketMst.sndCode})</td>
						<td class="success">Received By:</td>
						<td class="info">${ssStoreTicketMst.createdBy}</td>
					</tr>
				</c:if>


				<tr class="">
					<td class="success">Ticket Type:</td>
					<td class="info">${ssStoreTicketMst.storeTicketType}</td>
					<td class="success">Created By:</td>
					<td class="info">${ssStoreTicketMst.createdBy}</td>
					<td class="success">Created Date:</td>
					<td class="info"><fmt:formatDate
							value="${ssStoreTicketMst.createdDate}"
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
							<td class="success col-sm-3 text-left">
								${storeTicketApproveHistory.cEmpFullName} <c:if
									test="${!empty storeTicketApproveHistory.cDesignation}">
									, ${storeTicketApproveHistory.cDesignation} 
								</c:if> <c:if test="${!empty storeTicketApproveHistory.cEmpId}">
									( ${storeTicketApproveHistory.cEmpId} )
								</c:if>
							</td>
							<td class="info col-sm-3 text-right"
								style="text-transform: capitalize">${fn:toLowerCase(storeTicketApproveHistory.stateName)}
								Date:</td>
							<td class="info col-sm-3"><fmt:formatDate
								value="${storeTicketApproveHistory.createdDate}" pattern="dd-MM-yyyy" /></td>
						</tr>
					</table>
				</c:forEach>
			</c:if>
		</div>

		<div style="background: white;">
			<c:if test="${empty ssStoreTicketDtlList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty ssStoreTicketDtlList}">
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
						<c:forEach items="${ssStoreTicketDtlList}" var="ssStoreTicketDtl">
							<tr>
								<td><c:out value="${ssStoreTicketDtl.itemId}" /></td>
								<td><c:out value="${ssStoreTicketDtl.description}" /></td>
								<td><c:out value="${ssStoreTicketDtl.ledgerName}" /></td>
								<td><c:out value="${ssStoreTicketDtl.uom}" /></td>
								<td><c:out value="${ssStoreTicketDtl.quantity}" /></td>
								<td><c:out value="${ssStoreTicketDtl.ledgerBookNo}" /></td>
								<td><c:out value="${ssStoreTicketDtl.ledgerPageNo}" /></td>
<%-- 								<td><c:out value="${ssStoreTicketDtl.cost}" /></td> --%>
								<td><c:out value="${ssStoreTicketDtl.remarks}" /></td>
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