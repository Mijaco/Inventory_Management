<%@include file="../common/lsHeader.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">

		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/c2ls/storeTicketlist.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Store
				Ticket List
			</a>

		</div>
		<h2 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Store
			Ticket (Return)</h2>

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
					<td class="info">${csStoreTicketMst.ticketNo}</td>
					<td class="success">Return Slip No:</td>
					<td class="info">${csStoreTicketMst.operationId}</td>
					<td class="success">Work Order No:</td>
					<td class="info">${csStoreTicketMst.workOrderNo}</td>
				</tr>
				<tr class="">
					<td class="success">Ticket Date:</td>
					<td class="info"><fmt:formatDate
							value="${csStoreTicketMst.ticketDate}" pattern="dd-MM-yyyy" /></td>
					<td class="success">Return By:</td>
					<td class="info">${csStoreTicketMst.returnBy}</td>

					<td class="success">Received By:</td>
					<td class="info">${csStoreTicketMst.receivedBy}</td>

				</tr>
				<tr class="">

					<td class="success">Created By:</td>
					<td class="info">${csStoreTicketMst.createdBy}</td>
					<td class="success">Created Date:</td>
					<td class="info"><fmt:formatDate
							value="${csStoreTicketMst.createdDate}"
							pattern="dd-MM-yyyy hh:mm:ss a" /></td>

					<td class="success">Present Status:</td>
					<td class="info"><strong> ${currentStatus} </strong></td>

				</tr>


			</table>
		</div>


		<div class="">
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
							<td class="info col-sm-3"><fmt:formatDate
									value="${storeTicketApproveHistory.createdDate}"
									pattern="dd-MM-yyyy hh:mm:ss a" /> <%-- ${storeTicketApproveHistory.createdDate} --%></td>
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
							<td style="">Unit</td>
							<!-- <td style="">Total</td> -->
							<td style="">Quantity</td>
							<!-- <td style="">Reparable</td>
							<td style="">Non-Reparable</td> -->
							<td style="">Ledger No</td>
							<td style="">Page No</td>
							<!-- 							<td style="">Cost</td> -->
							<td style="">Remarks</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${lsStoreTicketDtlList}" var="csStoreTicketDtl">
							<tr>

								<td><c:out value="${csStoreTicketDtl.itemId}" /></td>
								<td><c:out value="${csStoreTicketDtl.description}" /></td>
								<td><c:out value="${csStoreTicketDtl.uom}" /></td>
								<%-- <td><c:out value="${csStoreTicketDtl.totalQty}" /></td> --%>
								<td><c:out value="${csStoreTicketDtl.quantity}" /></td>
								<%-- <td><c:out value="${csStoreTicketDtl.repairQty}" /></td>
								<td><c:out value="${csStoreTicketDtl.damageQty}" /></td> --%>
								<td><c:out value="${csStoreTicketDtl.ledgerBookNo}" /></td>
								<td><c:out value="${csStoreTicketDtl.ledgerPageNo}" /></td>
								<%-- 								<td><c:out value="${csStoreTicketDtl.cost}" /></td> --%>
								<td><c:out value="${csStoreTicketDtl.remarks}" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>

		</div>
		
		<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">
		<input type="hidden" id="ticketNo" value="${csStoreTicketMst.ticketNo}">
		<input type="hidden" id="operationId" value="${csStoreTicketMst.operationId}">


		<div class="text-center">
			<button class="btn btn-primary" id="generateButton"
				onclick="generate()"
				style="text-decoration: none; border-radius: 6px;">
				${buttonValue} </button>


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
			
			//Added by: Iheshamul Alam
			function generate() {
				var haserror = false;
				
				if( haserror == true ) {
					return;
				} else {
					
					$('#generateButton').prop('disabled', true);
					var ticketNo = $('#ticketNo').val();
					var operationId = $('#operationId').val();
					
					window.location = "${pageContext.request.contextPath}/c2ls/storeTicketSubmitApproved.do?ticketNo="+ticketNo+"&operationId="+operationId;
				}
			}
		</script>
	</div>
</div>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>