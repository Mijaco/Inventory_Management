<%@include file="../common/csHeader.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/cs/itemRecieved/storeTicketlist.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>Store
				Ticket List
			</a>
		</div>
		<h2 class="center blue"
			style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Store
			Ticket</h2>
		<h4 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptName}</h4>
	</div>

	<div class="row"
		style="background-color: white; padding-left: 10px; margin: 10px; padding-bottom: 10px;">
		<!-- --------------------- -->
		<div class="oe_title">

			<table class="col-sm-12 table">
				<tr class="">
					<td class="success">Store Ticket:</td>
					<td class="info">${csStoreTicketMst.ticketNo}</td>
					<td class="success">Return Slip No:</td>
					<td class="info">${csStoreTicketMst.operationId}</td>
					<td class="success">Work Order / Chalan No:</td>
					<td class="info">${csStoreTicketMst.workOrderNo}</td>
				</tr>
				<tr class="">
					<td class="success">Received From:</td>
					<td class="info">${csStoreTicketMst.receivedFrom}</td>
					<td class="success">Ticket Date:</td>
					<td class="info"><fmt:formatDate
							value="${csStoreTicketMst.ticketDate}"
							pattern="dd-MM-yyyy" /></td>
					<td class="success">Received By:</td>
					<td class="info">${csStoreTicketMst.createdBy}</td>

				</tr>
				<tr class="">
					<td class="success">Return By:</td>
					<td class="info">${csStoreTicketMst.returnBy}</td>
					<td class="success">Created By:</td>
					<td class="info">${csStoreTicketMst.createdBy}</td>
					<td class="success">Present Status:</td>
					<td class="info"><strong> ${currentStatus} </strong></td>
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
									value="${storeTicketApproveHistory.createdDate}"
									pattern="dd-MM-yyyy" /></td>
						</tr>
					</table>
				</c:forEach>
			</c:if>
		</div>

		<div style="background: white;">
			<c:if test="${empty csStoreTicketDtlList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty csStoreTicketDtlList}">
				<table id="requisitionListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Item Code</td>
							<td style="">Description</td>
							<td style="">Item Type</td>
							<td style="">Unit</td>
							<!-- <td style="">Total</td> -->
							<td style="">Quantity</td>
							<!-- <td style="">Reparable</td>
							<td style="">Non-Reparable</td> -->
							<td style="">Ledger No</td>
							<td style="">Page No</td>
							<td style="">Cost</td>
							<td style="">Remarks</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${csStoreTicketDtlList}" var="csStoreTicketDtl">
							<tr>

								<td><c:out value="${csStoreTicketDtl.itemId}" /></td>
								<td><c:out value="${csStoreTicketDtl.description}" /></td>
								<td><c:out value="${csStoreTicketDtl.ledgerName}" /></td>
								<td><c:out value="${csStoreTicketDtl.uom}" /></td>
								<%-- <td><c:out value="${csStoreTicketDtl.totalQty}" /></td> --%>
								<td><c:out value="${csStoreTicketDtl.quantity}" /></td>
								<%-- <td><c:out value="${csStoreTicketDtl.repairQty}" /></td>
								<td><c:out value="${csStoreTicketDtl.damageQty}" /></td> --%>
								<td><c:out value="${csStoreTicketDtl.ledgerBookNo}" /></td>
								<td><c:out value="${csStoreTicketDtl.ledgerPageNo}" /></td>
								<td><c:out value="${csStoreTicketDtl.cost}" /></td>
								<td><c:out value="${csStoreTicketDtl.remarks}" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>

		</div>



		<div class="text-center">
			<%-- <a class="btn btn-primary"
				href="${pageContext.request.contextPath}/cs/itemRecieved/storeTicketSubmitApproved.do?ticketNo=${csStoreTicketMst.ticketNo}&operationId=${csStoreTicketMst.operationId}"
				style="text-decoration: none; border-radius: 6px;">
				${buttonValue} </a> --%>
			<form id="myForm"
				action="${pageContext.request.contextPath}/cs/itemRecieved/storeTicketSubmitApproved.do"
				method="POST">

				<input type="hidden" name="operationId" id="operationId"
					value="${csStoreTicketMst.operationId}" /> <input type="hidden"
					name="ticketNo" id="ticketNo" value="${csStoreTicketMst.ticketNo}" />

				<button type="button" class="btn btn-primary" id="generateButton"
					style="text-decoration: none; border-radius: 6px;">${buttonValue}
				</button>
			</form>

		</div>

		<hr />

		<!-- -------------------------- -->

		<script>
			function returnSlipDtlModalEdit(id) {

				$
						.ajax({
							url : '${pageContext.request.contextPath}/cs/returnSlip/viewReturnTicketDtl.do',
							data : "{id:" + id + "}",
							contentType : "application/json",
							success : function(data) {
								var returnSlipDtl = JSON.parse(data);
								$("#description")
										.val(returnSlipDtl.description);
								$("#qtyRetrun").val(returnSlipDtl.qtyRetrun);
								$("#qtyReceived")
										.val(returnSlipDtl.qtyReceived);
								$("#remarks").val(returnSlipDtl.remarks);
								$("#returnSlipDtlId").val(returnSlipDtl.id);
								$("#returnSlipMstId").val(
										returnSlipDtl.returnSlipMst.id);
							},
							error : function(data) {
								alert("Server Error");
							},
							type : 'POST'
						});
			}
			
			//Added by: Ihteshamul Alam
			$(document).ready( function() {
				$('#generateButton').click( function() {
					var haserror = false;
					
					if( haserror == true ) {
						return;
					} else {
						$('#generateButton').prop('disabled', true);
						$('form').submit();
					}
				});
			});
			
		</script>
	</div>
</div>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>