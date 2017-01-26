<%@include file="../common/csHeader.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<h4>
			<a href="#" style="text-decoration: none;">Item Received</a> / Show
		</h4>
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/centralStore/rcvPrcess/list.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Item
				Received List
			</a>
		</div>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px; margin-left: 90px; margin-right: 90px;">
		<!-- --------------------- -->
		<div class="oe_title">

			<table class="col-sm-12 table">
				<tr class="">
					<td class="success">Receiving Report No:</td>
					<td class="info">${csItemReceivedList[0].receivedReportNo}</td>
					<td class="success">Invoice/Chalan No:</td>
					<td class="info">${csItemReceivedList[0].chalanNo}</td>
					<td class="success">Invoice/Chalan Date:</td>
					<td class="info">
						<fmt:formatDate value="${csItemReceivedList[0].invoiceDate}" 
									pattern="dd-MM-yyyy" />
					</td>
				</tr>
				<tr class="">
					<td class="success">W.O/Contract No:</td>
					<td class="info">${csItemReceivedList[0].contractNo}</td>
					<td class="success">W.O/Contract Date:</td>
					<td class="info">${csItemReceivedList[0].contractDate}</td>
					<td class="success">Received By:</td>
					<td class="info">${csItemReceivedList[0].receivedBy}</td>
				</tr>
				<tr class="">
					<td class="success">Received Date:</td>
					<td class="info">
						<fmt:formatDate value="${csItemReceivedList[0].receivedDate}" 
									pattern="dd-MM-yyyy" />
					</td>
					<td class="success">Created By:</td>
					<td class="info">${csItemReceivedList[0].createdBy}						
					</td>
					<td class="success">Created Date:</td>
					<td class="info">
						<fmt:formatDate value="${csItemReceivedList[0].createdDate}" 
									pattern="dd-MM-yyyy" />
					</td>

				</tr>
				<tr class="">
					<td class="success">Modified By:</td>
					<td class="info">${csItemReceivedList[0].modifiedBy}</td>
					<td class="success">Modified Date:</td>
					<td class="info">${csItemReceivedList[0].modifiedDate}</td>
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
				<div class="" style="font-weight: bold;">Approval History</div>
				<hr style="margin: 5px 0px 10px 0px"/>
				<c:forEach items="${itemRcvApproveHistoryList}"
					var="itemRcvApproveHistory">					
					<table class="col-sm-12 table">					
						<tr class="">
							<td class="success col-sm-3" style="text-transform: capitalize">${fn:toLowerCase(itemRcvApproveHistory.stateName)} By:</td>
							<td class="success col-sm-3 text-left"> <%-- ${itemRcvApproveHistory.createdBy} --%>
								${itemRcvApproveHistory.cEmpFullName} <c:if
									test="${!empty itemRcvApproveHistory.cDesignation}">
									, ${itemRcvApproveHistory.cDesignation} 
								</c:if> <c:if test="${!empty itemRcvApproveHistory.cEmpId}">
									( ${itemRcvApproveHistory.cEmpId} )
								</c:if>
							</td>
							<td class="info col-sm-3 text-right"  style="text-transform: capitalize">${fn:toLowerCase(itemRcvApproveHistory.stateName)} Date:</td>
							<td class="info col-sm-3">
								<fmt:formatDate value="${itemRcvApproveHistory.createdDate}" 
									pattern="dd-MM-yyyy" />
							</td>							
						</tr>
					</table>					
				</c:forEach>
			</c:if>
		</div>
		
			
		<div style="background: white;">
			<c:if test="${empty csItemReceivedList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty csItemReceivedList}">
				<table id="requisitionListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Item Code</td>
							<td style="">Description</td>
							<td style="">Unit</td>
							<td style="">Quantity</td>
							<td style="">Cost</td>
							<td style="">Remarks</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${csItemReceivedList}" var="csItemReceived">
							<tr>

								<td><c:out value="${csItemReceived.itemId}" /></td>
								<td><c:out value="${csItemReceived.description}" /></td>
								<td><c:out value="${csItemReceived.uom}" /></td>
								<td><c:out value="${csItemReceived.quantity}" /></td>
								<td><c:out value="${csItemReceived.cost}" /></td>
								<td><c:out value="${csItemReceived.remarks}" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>

		</div>

	

	<div class="text-center">
		<a class="btn btn-primary"
			href="${pageContext.request.contextPath}/centralStore/rcvPrcess/itemReceivedSubmitApproved.do?receivedReportNo=${csItemReceivedList[0].receivedReportNo}"
			style="text-decoration: none; border-radius: 6px;"> Submit </a>
			
			
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
							$("#description").val(returnSlipDtl.description);
							$("#qtyRetrun").val(returnSlipDtl.qtyRetrun);
							$("#qtyReceived").val(returnSlipDtl.qtyReceived);
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