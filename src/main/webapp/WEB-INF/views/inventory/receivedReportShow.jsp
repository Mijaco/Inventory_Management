<%@include file="../inventory/inventoryheader.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/inventory/receivedReportList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> RR
				List
			</a> <input id="contextPath" type="hidden"
				value="${pageContext.request.contextPath}" /> <input id="rrMstId"
				type="hidden" value="${rrMst.id}" /> 

		</div>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px;">

		<div style="background: white;">
			<c:if test="${empty csItemReceivedList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty csItemReceivedList}">
				<h2 class="center blue"
					style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Set
					Unit Price in RR number - ${rrMst.rrNo}</h2>
				<table id="requisitionListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Item Code</td>
							<td style="">Description</td>
							<td style="">Unit</td>
							<td style="">Quantity</td>
							<td style="">Unit Cost</td>
							<td style="">Remarks</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${csItemReceivedList}" var="csItemReceived"
							varStatus="loop">
							<tr>
								<td><c:out value="${csItemReceived.itemId}" /></td>
								<td><c:out value="${csItemReceived.description}" /></td>
								<td><c:out value="${csItemReceived.uom}" /></td>
								<td><c:out value="${csItemReceived.receivedQty}" /></td>
								<td><input class="itemCode" id="itemCode${loop.index}"
									type="hidden" value="${csItemReceived.itemId}" /> <input
									class="quantity" id="quantity${loop.index}" type="hidden"
									value="${csItemReceived.receivedQty}" /> <input
									class="form-control unitCost" id="unitCost${loop.index}"
									type="number" min="0" step="0.01" /></td>
								<td><c:out value="${csItemReceived.remarks}" /></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>

		</div>



		<div class="text-center">
			<a class="btn btn-primary" onclick="setUnitCostFromRR()"
				href="JavaScript:void()"
				style="text-decoration: none; border-radius: 6px;"> Submit </a>
		</div>

		<!-- ----------- --------- -->

		<script>
			function setUnitCostFromRR() {
				var itemCodeList = [];
				$(".itemCode").each(function() {
					var th = $(this);
					itemCodeList.push(th.val());
				});

				var quantityList = [];
				$(".quantity").each(function() {
					var th = $(this);
					quantityList.push(th.val());
				});

				var unitCostList = [];
				$(".unitCost").each(function() {
					var th = $(this);
					unitCostList.push(th.val());
				});

				var path = $('#contextPath').val()
						+ "/inventory/saveUnitPrices.do";
				var rrMstId = $('#rrMstId').val();
				var param = {
					rrMstId : rrMstId,
					itemCodeList : itemCodeList,
					quantityList : quantityList,
					unitCostList : unitCostList
				}
				postSubmit(path, param, 'POST');
			}
		</script>
	</div>
</div>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>