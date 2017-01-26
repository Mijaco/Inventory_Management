<%@include file="../common/csHeader.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<!-- <h4>
			<a href="#" style="text-decoration: none;">Store Ticket</a> / Show
		</h4> -->
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/cs/itemRecieved/gatePassList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Gate Pass List
			</a>
			<!-- <button accesskey="D" class="btn btn-info btn-sm" type="button">
				Discard</button> -->
		</div>
		<h1 class="center blue"
			style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Gate Pass Show</h1>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px; margin-left: 90px; margin-right: 90px;">
		<!-- --------------------- -->
		<div class="oe_title">

			<table class="col-sm-12 table">
				<tr class="">
					<td class="success">Gate Pass No:</td>
					<td class="info">${csGatePassMstdb.gatePassNo}</td>
					<td class="success">Requisition. No:</td>
					<td class="info">${csGatePassMstdb.requisitonNo}</td>
					<td class="success">Ticket No:</td>
					<td class="info">${csGatePassMstdb.ticketNo}</td>
				</tr>
				<tr class="">
					<td class="success">Ticket Date:</td>
					<td class="info"><fmt:formatDate
	value="${csGatePassMstdb.gatePassDate}" pattern="dd-MM-yyyy" /></td>
					<td class="success">Issued By:</td>
					<td class="info">${csGatePassMstdb.createdBy}</td>
					<td class="success">Issued To:</td>
					<td class="info">${csGatePassMstdb.issuedTo}</td>

				</tr>
				<tr class="">
					<td class="success">Created By:</td>
					<td class="info">${csGatePassMstdb.createdBy}</td>
					<td class="success">Created Date:</td>
					<td class="info"><fmt:formatDate
	value="${csGatePassMstdb.createdDate}" pattern="dd-MM-yyyy" /></td>
					<td class="success">Present Status:</td>
					<td class="info"><strong> ${currentStatus} </strong></td>
				</tr>
			</table>
		</div>

		<c:if test="${!empty gatePassApproveHistoryList}">
			<button data-toggle="collapse" data-target="#demo">
				<span class="glyphicon glyphicon-collapse-down"></span>
			</button>
		</c:if>

		<div id="demo" class="collapse">
			<c:if test="${!empty gatePassApproveHistoryList}">
				<!-- <div class="" style="font-weight: bold;">Approval History</div> -->

				<c:forEach items="${gatePassApproveHistoryList}"
					var="gatePassApproveHistory">
					<div class=""
						style="font-weight: bold; font-style: italic; text-transform: capitalize">${fn:toLowerCase(gatePassApproveHistory.approvalHeader)}</div>
					<hr style="margin: 5px 0px 10px 0px" />
					<table class="col-sm-12 table">

						<tr class="">
							<td class="success col-sm-3" style="text-transform: capitalize">${fn:toLowerCase(gatePassApproveHistory.stateName)}
								By:</td>
							<td class="success col-sm-3 text-left">
								${gatePassApproveHistory.cEmpFullName} <c:if
									test="${!empty gatePassApproveHistory.cDesignation}">
									, ${gatePassApproveHistory.cDesignation} 
								</c:if> <c:if test="${!empty gatePassApproveHistory.cEmpId}">
									( ${gatePassApproveHistory.cEmpId} )
								</c:if>
							</td>
							<td class="info col-sm-3 text-right"
								style="text-transform: capitalize">${fn:toLowerCase(gatePassApproveHistory.stateName)}
								Date:</td>
							<td class="info col-sm-3"><fmt:formatDate
									value="${gatePassApproveHistory.createdDate}"
									pattern="dd-MM-yyyy" /></td>
						</tr>
					</table>
				</c:forEach>
			</c:if>
		</div>

		<div style="background: white;">
			<c:if test="${empty csGatePassDtlList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty csGatePassDtlList}">
				<table id="requisitionListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Item Code</td>
							<td style="">Description</td>
							<td style="">Unit</td>
							<td style="">Quantity</td>
							
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${csGatePassDtlList}" var="csGatePassDtl">
							<tr>

								<td><c:out value="${csGatePassDtl.itemCode}" /></td>
								<td><c:out value="${csGatePassDtl.description}" /></td>
								<td><c:out value="${csGatePassDtl.uom}" /></td>
								<td><c:out value="${csGatePassDtl.quantity}" /></td>
								
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>

		</div>



		<div class="text-center">
			<a class="btn btn-primary"
				href="${pageContext.request.contextPath}/cs/gatePassSubmitApproved.do?ticketNo=${csGatePassMstdb.ticketNo}&requisitonNo=${csGatePassMstdb.requisitonNo}&gatePassNo=${csGatePassMstdb.gatePassNo}"
				style="text-decoration: none; border-radius: 6px;">
				${buttonValue} </a>


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
		</script>
	</div>
</div>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>