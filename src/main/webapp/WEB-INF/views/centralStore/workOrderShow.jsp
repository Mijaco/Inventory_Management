<%@include file="../common/csHeader.jsp"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!-- -------------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<!-- <h4>
			<a href="#" style="text-decoration: none;">Store Requisition</a> /
			List
		</h4> -->
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/workOrder/list.do"
				style="text-decoration: none;" class="btn btn-success btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Work Order List
			</a>
			<h2 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Work
				Order Show</h2>

			<h4 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptName}</h4>

			<%-- <h5 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptAddress}</h5> --%>
		</div>
	</div>

	<div class="row" style="background-color: white; padding: 10px; padding-left: 20px; margin: 10px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">
			<%-- <c:if test="${empty lsItemTransactionDtlList}">
				<div class="col-sm-12 center">
					<h2 class="green">
						<i>Congratulation!!! You have no pending task</i>
					</h2>
				</div>
			</c:if> --%>

			<div class="col-sm-12 center">
				<table class="table table-striped table-hover table-bordered">
					<tr>
						<th class="success center">Work Order No</th>
						<td class="info"><c:out value="${workOrderMst.workOrderNo}" /></td>
						<th class="success center">Work Order Date</th>
						<td class="info">
							<fmt:formatDate
								value="${workOrderMst.contractDate}" pattern="dd-MM-yyyy" />
						</td>
						<th class="success center">Supplier Name</th>
						<td class="info"><c:out
								value="${workOrderMst.supplierName}" /></td>
					</tr>
					<tr>
						<th class="success center">PSI Performed</th>
						<td class="info"><c:choose>
								<c:when test="${workOrderMst.psi}"> Yes
									</c:when>
								<c:otherwise> No </c:otherwise>
							</c:choose></td>
						<th class="success center">PLI Performed</th>
						<td class="info"><c:choose>
								<c:when test="${workOrderMst.pli}"> Yes
									</c:when>
								<c:otherwise> No </c:otherwise>
							</c:choose></td>
						<th class="success center">Project Name</th>
						<td class="info">${khathInfo.khathName}</td>
					</tr>
				</table>
			</div>

			<c:if test="${!empty workOrderDtlList}">
				<table id="centralStoreRequisitionListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Item Id</td>
							<td style="">Description</td>
							<td style="">Purchase Quantity</td>
							<td style="">Remaining Quantity</td>
							<td style="">Unit Cost</td>
							<td style="">Remarks</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${workOrderDtlList}" var="workOrderDtl"
							varStatus="loop">

							<tr>
								<td><c:out value="${workOrderDtl.itemId}" /></td>

								<td><c:out value="${workOrderDtl.description}" /></td>
								<td><c:out value="${workOrderDtl.itemQty}" /></td>

								<%-- <td><fmt:formatDate
										value="${workOrderDtl.itemQty}"
										pattern="dd-MM-yyyy" /></td> --%>

								<td><c:out value="${workOrderDtl.remainingQty}" /></td>

								<td><c:out value="${workOrderDtl.cost}" /></td>

								<td><c:out value="${workOrderDtl.remarks}" /> </td>	
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
			<!-- --------------------- -->
		</div>

	</div>
</div>
<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
