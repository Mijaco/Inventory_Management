<%@include file="../../common/lsHeader.jsp"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!-- -------------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/ac/auctionDeliveryList.do"
				style="text-decoration: none;" class="btn btn-success btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Materials Delivery List
			</a>
			<h2 class="center blue ubuntu-font"
				style="margin-top: 0; font-style: italic;">Auction Materials
				Delivery</h2>

			<h4 class="center blue ubuntu-font"
				style="margin-top: 0; font-style: italic;">${department.deptName}</h4>


		</div>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin: 10px;">
		<!-- --------------------- -->
		<div class="">
			<table class="table table-striped table-hover table-bordered">
				<tr>
					<th class="success center">Delivery No :</th>
					<td class="info">${auctionDeliveryMst.deliveryTrxnNo}</td>
					<th class="success center">Delivery Date :</th>
					<td class="info"><fmt:formatDate
							value="${auctionDeliveryMst.deliveryDate}" pattern="dd-MM-yyyy" /></td>
					<th class="success center">Receiver Name :</th>
					<td class="info">${auctionDeliveryMst.receiverName}</td>
				</tr>

				<tr>
					<th class="success center">Receiver Mobile No :</th>
					<td class="info">${auctionDeliveryMst.receiverContactNo}</td>
					<th class="success center">Carried By :</th>
					<td class="info">${auctionDeliveryMst.carriedBy}</td>
					<th class="success center">Work Order No :</th>
					<td class="info">${auctionDeliveryMst.auctionWOEntryMst.workOrderNo}</td>
				</tr>

			</table>
		</div>

		<c:if test="${!empty auctionDeliveryDtlList}">
			<table id="auctionDeliveryDtlListTable"
				class="table table-striped table-hover table-bordered">
				<thead>
					<tr style="background: #579EC8; color: white; font-weight: normal;">
						<td style="">Item Code</td>
						<td style="">Item Name</td>
						<td style="">Unit</td>
						<td style="">Delivery Quantity</td>
						<td style="">Remarks</td>
					</tr>
				</thead>

				<tbody>
					<c:forEach items="${auctionDeliveryDtlList}"
						var="auctionDeliveryDtl" varStatus="loop">
						<tr>
							<td><c:out value="${auctionDeliveryDtl.itemMaster.itemId}" /></td>
							<td><c:out value="${auctionDeliveryDtl.itemMaster.itemName}" /></td>
							<td><c:out value="${auctionDeliveryDtl.itemMaster.unitCode}" /></td>
							<td><c:out value="${auctionDeliveryDtl.deliveryQty}" /></td>
							<td><c:out value="${auctionDeliveryDtl.remarks}" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
		<!-- --------------------- -->
		<div class="col-xs-12" align="center">
			<c:if test="${auctionDeliveryMst.finalSubmit=='0'}">
				<a href="javascript:void(0)" class="btn btn-success btn-xs"
					style="border-radius: 6px;"
					onclick="postSubmit('${pageContext.request.contextPath}/auction/delivery/finalSubmit.do',{'id':'${auctionDeliveryMst.id}'},'POST')">
					<i class="fa fa-fw fa-paper-plane"></i>&nbsp;Final Submit
				</a>
				<a href="javascript:void(0)" class="btn btn-danger btn-xs"
					style="border-radius: 6px;"
					onclick="deleteThis(${auctionDeliveryMst.id})"> <i
					class="fa fa-fw fa-trash-o"></i>&nbsp;Delete
				</a>
			</c:if>
		</div>
	</div>





</div>

<script>
function deleteThis( id ) {
	
	if( confirm( "Do you want to delete this Delevery Details?" ) == true ) {
		var contextPath = $('#contextPath').val();
		var path = contextPath + "/ac/deleteCondemnMaterials.do";
		var params = {
			'id' : id
		}
		postSubmit(path, params, "POST");
	}
}
</script>
<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>
