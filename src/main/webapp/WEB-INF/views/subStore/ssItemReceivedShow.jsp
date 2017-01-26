<%@include file="../common/ssHeader.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- -------------------End of Header-------------------------- -->

<!--Start ||  Location Grid CSS -->
<style type="text/css">
.ui-widget-overlay {
	opacity: .6 !important;
}

input[readonly] {
	background: #fff !important;
}

</style>
<!-- End || Location Grid CSS -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/ss/itemRecieved/csItemRequisitionReceivedList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Item
				Received List
			</a>
		</div>
		<h1 class="center blue" style="margin-top: 0px;">Item Received
			from CS</h1>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px;">
		<!-- --------------------- -->
		<div class="oe_title">

			<table class="col-sm-12 table">
				<tr class="">
					<td class="success">Requisition No: <input type="hidden"
						value="${centralStoreRequisitionMst.requisitionNo}"
						id="requisitionNo" /> <input type="hidden"
						value="${returnStateCode}" id="returnStateCode" /> <input
						type="hidden" id="locationList" value=${locationList } /> <input
						type="hidden" id="ledgerBookList" value=${ledgerBookList } />
					</td>

					<td class="info">${centralStoreRequisitionMst.requisitionNo}<input
						type="hidden" value="${uuid}" id="uuid" /><input type="hidden"
						name="contextPath" id="contextPath"
						value="${pageContext.request.contextPath}" /></td>
					<td class="success">Created By:</td>
					<td class="info">${centralStoreRequisitionMst.createdBy}</td>
					<td class="success">Created Date:</td>
					<td class="info"><fmt:formatDate
							value="${centralStoreRequisitionMst.createdDate}"
							pattern="dd-MM-yyyy hh:mm:ss a" /></td>
				</tr>
				<tr class="">
					<td class="success">Modified By:</td>
					<td class="info">${centralStoreRequisitionMst.modifiedBy}</td>
					<td class="success">Modified Date:</td>
					<td class="info"><fmt:formatDate
							value="${centralStoreRequisitionMst.modifiedDate}"
							pattern="dd-MM-yyyy hh:mm:ss a" /></td>
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
				<c:forEach items="${itemRcvApproveHistoryList}"
					var="itemRcvApproveHistory">
					<table class="col-sm-12 table">

						<tr class="">
							<td class="warning col-sm-3"
								style="font-weight: bold; font-size: 1.5em; font-style: italic; text-transform: capitalize">${fn:toLowerCase(itemRcvApproveHistory.approvalHeader)}</td>
							<td class="success col-sm-2" style="text-transform: capitalize">${fn:toLowerCase(itemRcvApproveHistory.stateName)}
								By:</td>
							<td class="success col-sm-2 text-left">
								${itemRcvApproveHistory.cEmpFullName} <c:if
									test="${!empty itemRcvApproveHistory.cDesignation}">
									, ${itemRcvApproveHistory.cDesignation} 
								</c:if> <c:if test="${!empty itemRcvApproveHistory.cEmpId}">
									( ${itemRcvApproveHistory.cEmpId} )
								</c:if>
							</td>
							<td class="info col-sm-2 text-left"
								style="text-transform: capitalize">${fn:toLowerCase(itemRcvApproveHistory.stateName)}
								Date:</td>
							<td class="info col-sm-3"><fmt:formatDate
									value="${itemRcvApproveHistory.createdDate}"
									pattern="dd-MM-yyyy hh:mm:ss a" /></td>
						</tr>
						<c:if test="${!empty itemRcvApproveHistory.justification}">
							<tr class="">
								<td></td>
								<td class="danger col-sm-1">Comment(s) :</td>
								<td class="danger col-sm-11" colspan="3"
									title="${itemRcvApproveHistory.justification}">${itemRcvApproveHistory.justification}</td>
							</tr>
						</c:if>
					</table>
				</c:forEach>
			</c:if>
		</div>


		<div style="background: white;">
			<c:if test="${empty centralStoreRequisitionDtlList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty centralStoreRequisitionDtlList}">
				<table id="requisitionListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Item Code</td>
							<td style="">Description</td>
							<td style="">Unit</td>
							<td style="">Required Qty</td>
							<td style="">Issued Qty</td>
							<td style="width: 15px;">Received Qty</td>
							<!-- 							<td style="">Unit Cost</td> -->
							<!-- 							<td style="">Total Cost</td> -->
							<td style="">Remarks</td>

							<td style="">Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${centralStoreRequisitionDtlList}"
							var="centralStoreRequisitionDtl" varStatus="loop">
							<tr>
								<td><c:out value="${centralStoreRequisitionDtl.itemCode}" /></td>
								<td><c:out value="${centralStoreRequisitionDtl.itemName}" /></td>
								<td><c:out value="${centralStoreRequisitionDtl.uom}" /></td>
								<td><c:out
										value="${centralStoreRequisitionDtl.quantityRequired}" /></td>
								<td>${centralStoreRequisitionDtl.quantityIssued}
									<input class="qtyIssue" type="hidden" value="${centralStoreRequisitionDtl.quantityIssued}" name="qtyIssued" id="qtyIssued${loop.index}" readonly>
								</td>
								<td><c:if test="${!centralStoreRequisitionMst.received}">
										<input type="text" readonly="readonly" value=""
											id="quantityIssued${loop.index}" name="quantityIssued" />
									</c:if> <c:if test="${centralStoreRequisitionMst.received}">
								${centralStoreRequisitionDtl.quantityIssued}
								</c:if>
								<strong class="text-danger qtyError hide" id="qtyError${loop.index}">Issued Qty. and Received Qty. must be same</strong>
								</td>
								<%-- 								<td><c:out value="${centralStoreRequisitionDtl.unitCost}" /></td> --%>
								<%-- 								<td><c:out value="${centralStoreRequisitionDtl.totalCost}" /></td> --%>
								<td><c:out value="${centralStoreRequisitionDtl.remarks}" /></td>

								<td><c:if test="${!centralStoreRequisitionMst.received}">
										<div class="action-buttons"
											data-itemcode="${centralStoreRequisitionDtl.itemCode}">
											<a href="#" class="btn btn-primary"
												id="setDialog${loop.index}"> <i
												class="glyphicon glyphicon-edit"
												onclick="openGridDialog(this)" aria-hidden="true"> </i>
											</a>
										</div>
									</c:if></td>

							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
		</div>
		<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">
		<input type="hidden" id="received" name="received" value="${centralStoreRequisitionMst.received}">
		<input type="hidden" id="mstId" name="id" value="${centralStoreRequisitionMst.id}">
		<!-- ----- -->
		<c:if test="${!centralStoreRequisitionMst.received}">
			<hr />
			<div class="center">
				<button type="button" id="receiveButton"
					class="btn btn-success" style="border-radius: 6px;"><i class="fa fa-fw fa-download"></i>&nbsp;Receive</button>
			</div>
		</c:if>
		<!-- ----------- -->

	</div>
</div>

<!-- -------------------------- -->
<div id="myGrid" title="Title">
	<form action="" method="post">
		<table id="tblAppendGrid">
		</table>
	</form>
</div>
<!-- -------------------------- -->

<script>
	//qtyIssue
	function redirectURL(id, received) {
		var baseURL = $('#contextPath').val();
		var path = baseURL + "/ss/itemRecieved/storeRequisitionReceiving.do";
		var params = {
				id: id,
				received: received
		}
		
		postSubmit(path, params, "GET");
		
		//${pageContext.request.contextPath}/ss/itemRecieved/storeRequisitionReceiving.do?id=${centralStoreRequisitionMst.id}&received=${centralStoreRequisitionMst.received}
	}
	
	$( document ).ready( function() {
		
		$('#receiveButton').click( function() {
			var haserror = false, counter = 0;
			
			
			$('.qtyIssue').each( function() {
				
				var name = this.name;
				var id = this.id;
				var sequence = id.substr( name.length );
				
				var iq = parseFloat( $('#qtyIssued'+sequence).val() ).toFixed(3);
				var rq = parseFloat( $('#quantityIssued'+sequence).val() ).toFixed(3);
				
				if( iq != rq ) {
					counter++;
					$('#qtyError'+sequence).removeClass('hide');
				} else {
					$('#qtyError'+sequence).addClass('hide');
				}
			}); // jQuery .each
			
			if( counter > 0 ) {
				haserror = true;
			}
			
			if( haserror == true ) {
				return;
			} else {
				//
				var id = $('#mstId').val();
				var received = $('#received').val();
				redirectURL(id, received);
			}
		});
	});

</script>

<script
	src="${pageContext.request.contextPath}/resources/assets/js/subStore/ssAddLocationForItemReceive.js"></script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>