<%@include file="../../common/csHeader.jsp"%>
<!-- Author: Ihteshamul Alam -->
<style>
.loader {
	position: fixed;
	left: 50%;
	top: 50%;
	z-index: 9999;
}

.ui-dialog-title {
	color: #fff !important;
}

.multiselect-container.dropdown-menu {
	z-index: 9999999;
}

.ui-widget-overlay {
	opacity: .6 !important;
}
</style>
<link
	href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
	rel="stylesheet">
<!-- -------------------------------------------------------------------------------------- -->
<div class="loader">
	<i class="fa fa-refresh fa-spin fa-3x fa-fw"></i> <br> <span
		class="orange">Loading...</span>
</div>

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<h2 class="center blue ubuntu-font"
				style="margin-top: 10px; font-style: italic;">Delivery of Unservicable Item
				</h2>
			<h4 class="center blue ubuntu-font"
				style="margin-top: 0; font-style: italic;">${department.deptName}</h4>
		</div>
	</div>

	<div class="row"
		style="background-color: white; padding-left: 10px; margin: 10px; padding-bottom: 10px; padding-top: 10px;">
		<!-- --------------------- -->

		<div class="col-sm-12 table-responsive">
			<form id="auctionMaterialDeliveryForm"
				action="${pageContext.request.contextPath}/ac/saveAuctionMaterialsDelivery.do"
				method="POST" id="saveCondamn">
				
				<input type="hidden" id="contextPath"
					value="${pageContext.request.contextPath}"> <input
					type="hidden" id="deptId" value="${deptName.deptId}"> <input
					type="hidden" id="uuid" name="uuid" value="${uuid}"> <input
					type="hidden" id="rowId">
					<input type="hidden" id="buttonType">
				<table class="table table-bordered table-hover">
					<tbody>

						<tr>
							<td class="col-sm-2 success text-right"
								style="font-weight: bold;">Receiver Name:&nbsp;<strong class='red'>*</strong></td>
							<td class="col-sm-4"><input type="text" name="receiverName"
								id="receiverName" required="required"
								placeholder="Please Input Receiver Name" class="form-control"
								style="width: 100%;"></td>
							<td class="col-sm-2 success text-right"
								style="font-weight: bold;">Delivery Date:&nbsp;<strong class='red'>*</strong></td>
							<td class="col-sm-4"><input type="text" name="deliveryDate"
								id="deliveryDate" required="required"
								class="form-control datepicker-16" style="width: 100%;">
							</td>
						</tr>
						<tr>
							<td class="col-sm-2 success text-right"
								style="font-weight: bold;">Receiver Mobile No:</td>
							<td class="col-sm-4"><input type="text"
								name="receiverContactNo" id="receiverContactNo"
								placeholder="Please Input Mobile No" class="form-control"
								style="width: 100%;"></td>
							<td class="col-sm-2 success text-right"
								style="font-weight: bold;">Vehicle No:</td>
							<td class="col-sm-4"><input type="text" name="carriedBy"
								id="carriedBy" placeholder="Please Car No" class="form-control"
								style="width: 100%;"></td>
						</tr>
						<tr>
							<td class="col-sm-2 success text-right"
								style="font-weight: bold;">Work Order No:&nbsp;<strong class='red'>*</strong></td>
							<td class="col-sm-4"><select name="woId" id="woId"
								onchange="loadAuctionItem()" class="form-control"
								style="width: 100%;">
									<option value="">Select Work Order</option>
									<c:if test="${!empty workOrderMstList}">
										<c:forEach items="${workOrderMstList}" var="workOrderMst">
											<option value="${workOrderMst.id}">${workOrderMst.workOrderNo}</option>
										</c:forEach>
									</c:if>
							</select> <input type="hidden" name="workOrderNo" id="workOrderNo"
								value="0"> <input type="hidden" name="mstId" id="mstId"
								value="0"></td>
							<td class="col-sm-2 success text-right"
								style="font-weight: bold;">Select Item(s):&nbsp;<strong class='red'>*</strong></td>
							<td class="col-sm-4"><select name="itemCode" id="itemCode"
								style="width: 100%;" >
									<option value="0" disabled selected>-- Select Item
										Code --</option>
							</select></td>
						</tr>

					</tbody>
				</table>

				<table class="table table-bordered" id="dataList">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<th>Item Code</th>
							<th>Item Name</th>
							<th>Auction Qty</th>
							<th>Remaining Qty</th>
							<th>Delivered Qty</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody></tbody>
				</table>

				<div class="col-md-12 col-xs-12" align="center">
					<button id="saveButton" type="button" class="btn btn-primary"
						style="border-radius: 6px;">
						<i class="fa fa-fw fa-save"></i>&nbsp;Save
					</button>
					<!-- onclick="checkAndSave()" -->
				</div>
			</form>
		</div>

	</div>
</div>

<div id="myDialogeBox">
	<br /> <br /> <br />
	<div class="col-md-12 col-sm-12">

		<!-- Here receiver name is uuid -->
		<input type="hidden" id="deliveryItemCode">
		<div class="table-responsive col-md-12">
			<table class="table table-bordered" id="deliverableQty">
				<thead>
					<tr>
						<th>Project</th>
						<th>Present Stock</th>
						<th>Delivery Qty</th>
						<th>Action</th>
					</tr>
				</thead>
				<tbody></tbody>
			</table>
		</div>
	</div>
</div>

<%-- <script
	src="${pageContext.request.contextPath}/resources/assets/js/bootstrap.min.js"></script> --%>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/jquery.dataTables.min.js"></script>

<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.bootstrap.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.tableTools.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.colVis.min.js"></script>

<script>
	$(window).load(function() {
		$('.loader').fadeOut('slow');
	});
	
	//Added by: Ihteshamul Alam
	$(document).ready( function() {
		$('#saveButton').click( function() {
			var haserror = false;
			
			if( haserror == true ) {
				return;
			} else {
				$('#saveButton').prop('disabled', true);
				$('#auctionMaterialDeliveryForm').submit();
			}
		});
	});
</script>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/centralStore/auction/condemnMaterials.js"></script>
<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>
