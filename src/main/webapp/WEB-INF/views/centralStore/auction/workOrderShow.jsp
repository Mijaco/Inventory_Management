<%@include file="../../common/auctionHeader.jsp"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!-- -------------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/ac/wolist.do"
				style="text-decoration: none;" class="btn btn-success btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Work Order List
			</a>
			<h2 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Work
				Order Show</h2>

			<h4 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptName}</h4>
		</div>
	</div>

	<div class="row" style="background-color: white; padding: 10px; padding-left: 20px; margin: 10px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">		

			<div class="">
				<table class="table table-striped table-hover table-bordered">
					<tr>
						<th class="success center">Work Order No</th>
						<td class="info"><c:out value="${workOrderMst.workOrderNo}" /></td>
						<th class="success center">Work Order Date</th>
						<td class="info"><fmt:formatDate
								value="${workOrderMst.contractDate}" pattern="dd-MM-yyyy" /></td>
						<th class="success center">Contractor Name</th>
						<td class="info"><c:out value="${workOrderMst.supplierName}" /></td>
					</tr>
					<%-- <tr>
						<th class="success center">Project Name</th>
						<td class="info">${khathInfo.khathName}</td>
					</tr> --%>
				</table>
			</div>

			<c:if test="${!empty workOrderDtlList}">
				<table id="centralStoreRequisitionListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Store</td>
							<td style="">Item Code</td>
							<td style="">Item Name</td>
							<td style="">Auction Quantity</td>
							<td style="">Delivered Quantity</td>
							<td style="">Remaining Quantity</td>
							<td style="">Unit Cost</td>
							
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${workOrderDtlList}" var="workOrderDtl"
							varStatus="loop">

							<tr>
								<td>${workOrderDtl.departments.deptName}</td>
								<td>${workOrderDtl.itemMaster.itemId}</td>

								<td>${workOrderDtl.itemMaster.itemName}</td>
								<td>${workOrderDtl.auctionQty}</td>

								<%-- <td><fmt:formatDate
										value="${workOrderDtl.itemQty}"
										pattern="dd-MM-yyyy" /></td> --%>

								<td>${workOrderDtl.auctionQty - workOrderDtl.remainingQty}</td>
								<td>${workOrderDtl.remainingQty}</td>
								<td>${workOrderDtl.cost}</td>
								
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
<%@include file="../../common/ibcsFooter.jsp"%>
