<%@include file="../../common/csHeader.jsp"%>
<link
	href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
	rel="stylesheet">


<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit col-md-2" style="display: block;">
			<a href="${pageContext.request.contextPath}/ac/wolist.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Work
				Order List
			</a>
		</div>
		<h2 class="col-md-8 center blue ubuntu-font"
			style="margin-top: 10px; font-style: italic;">Work Order for
			Auction Id: ${condemnMst.auctionProcessMst.auctionName}</h2>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<!-- --------------------- -->
		<form method="POST"
			action="${pageContext.request.contextPath}/ac/auctionWOSave.do"
			enctype="multipart/form-data">
			<div class="oe_title">

				<div class="col-md-6 col-sm-6">
					<div class="form-group">
						<label for="workOrderNo" class="col-sm-4 control-label text-right">Work
							Order No : <input type="hidden" name="condemnMstId" 
							value="${condemnMst.id}"> <input
							type="hidden" name="contextPath" id="contextPath"
							value="${pageContext.request.contextPath}">
						</label>
						<div class="col-sm-7">
							<input type="text" class="form-control" id="workOrderNo"
								onblur="checkWorkOrder()"
								placeholder="Please Enter Your WO No.."
								style="border: 0; border-bottom: 2px ridge;" name="workOrderNo" />
						</div>
						<div class="col-sm-1">
							<i id="workOrderDecision" style="font-size: 2em;" class=""></i>
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="supplierName"
							class="col-sm-4 text-right control-label">Buyer Name: </label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control" id="supplierName"
								style="border: 0; border-bottom: 2px ridge;" name="supplierName" />
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>



					<%--
					<div class="form-group">
						<label class="col-sm-4 control-label">Auction Id </label>
						<div class="col-sm-8">
							
							 <select class="form-control auctionName" name="auctionName"
								required="required" onchange="loadItems(this)" id="auctionName"
								style="border: 0; border-bottom: 2px ridge;">
								<c:if test="${!empty auctionIdList}">
									<option value="">Select an Auction Id</option>
									<c:forEach items="${auctionIdList}" var="auc">
										<option value="${auc}">${auc}</option>
									</c:forEach>
								</c:if>
							</select> 
						</div>
					</div> --%>
				</div>

				<div class="col-md-6 col-sm-6">

					<div class="form-group">
						<label for="contractDate"
							class="col-sm-4 text-right control-label"> Work Order
							Date: </label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control datepicker-15"
								id="contractDate" style="border: 0; border-bottom: 2px ridge;"
								name="contractDate" />
						</div>
					</div>
					<div class="col-sm-10">&nbsp;</div>
					<div class="form-group">
						<div class="form-group">
							<label for="referenceDoc"
								class="col-sm-4 text-right control-label">Upload Work
								Order: Doc. </label>
							<div class="col-sm-8 col-md-8">
								<input type="file" id="referenceDoc" accept="application/pdf"
									class='form-control'
									style="border: 0; border-bottom: 2px ridge;"
									name="referenceDoc" />
							</div>
						</div>
					</div>



				</div>

				<div class="col-md-12 col-sm-12">
					<hr />
					<table id="dataList"
						class="table table-striped table-hover table-bordered">
						<tr style="background: #579EC8; color: white;">
							<!-- <td style="">Store</td> -->
							<td style="">Item Code</td>
							<td style="">Description</td>
							<td style="">Unit</td>
							<td style="">Auction Qty</td>
							<td style="">Unit Cost</td>
							<td style="">Total Cost</td>
						</tr>
						<c:forEach items="${condemnDtlList}" var="obj" varStatus="loop">
							<tr>
								<%-- <td>${obj.departments.deptName}</td> --%>
								<td>${obj.itemMaster.itemId}<input type="hidden" name="pk"
									value="${obj.itemMaster.id}" />
								</td>
								<td>${obj.itemMaster.itemName}</td>
								<td>${obj.itemMaster.unitCode}</td>
								<td id="qty${loop.index}">${obj.condemnQty}</td>
								<td><input type="number" step="0.01" name="cost"
									id="price${loop.index}" onblur="setTotalPrice(${loop.index})"
									required="required" /></td>
								<td id="total${loop.index}" class="totalPrice">0</td>

							</tr>
						</c:forEach>
						<tr>
							<td colspan="5" class="text-right"><strong>Grand
									Total = </strong></td>
							<td id="grandTotal">0</td>
						</tr>
					</table>
				</div>

				<div class="col-md-12"
					style="padding-top: 15px; padding-bottom: 10px;">
					<div class="col-xs-12 col-sm-6">
						<!-- id="saveButton" disabled="disabled" -->
						<button type="submit" id="saveButton" disabled="disabled"
							style="margin-right: 10px; border-radius: 6px;"
							class="width-20 pull-right btn btn-sm btn-success">
							<i class="ace-icon fa fa-save"></i> <span class="bigger-50">Save</span>
						</button>
					</div>

					<div class="col-xs-12 col-sm-6">
						<button type="reset"
							class="width-20  pull-left btn btn-sm btn-danger"
							style="margin-left: 10px; border-radius: 6px;">
							<i class="ace-icon fa fa-refresh"></i> <span class="bigger-50">Reset</span>
						</button>
					</div>
				</div>
			</div>


		</form>
		<!-- --------------------- -->
	</div>
</div>
<script type="text/javascript">

function setTotalPrice(r) {
	var unitPrc = $("#price" + r).val()||0;
	var qty = $("#qty" + r).text();
	unitPrc = parseFloat(unitPrc);
	qty = parseFloat(qty);
	
	var totalPrc = unitPrc*qty;
	$("#total" + r).text(totalPrc.toFixed(2));
	var grandTotal=0;
	$('.totalPrice').each( function() {
		grandTotal+= parseFloat($(this).text());
	})
	$("#grandTotal").text(grandTotal.toFixed(2));
}

	function loadItems(element) {
		var auctionName = $(element).val();
		var contextPath = $('#contextPath').val();
		var path = contextPath + '/auction/cs/loadAuctionDtl.do';
		var params = {
			auctionName : auctionName
		};
		var cDataJsonString = JSON.stringify(params);

		$
				.ajax({
					url : path,
					data : cDataJsonString,
					contentType : "application/json",
					success : function(data) {
						var list = JSON.parse(data);
						for (var i = 0; i < list.length; i++) {
							var hidden1 = '<input type="hidden" name="pk" value="' + list[i].id + '" /> ';
							var td0 = '<td>' + list[i].departments.deptName
									+ '</td>';
							var td1 = '<td>' + list[i].itemMaster.itemId
									+ '</td>';
							var td2 = '<td>' + list[i].itemMaster.itemName
									+ '</td>';
							var td3 = '<td>' + list[i].itemMaster.unitCode
									+ '</td>';
							var td4 = '<td>' + list[i].condemnQty + '</td>';
							var td5 = '<td><input type="number" step="0.01" name="cost" required="required" /></td>';
							var td6 = '<td><input type="text" name="remarks" /> '
									+ hidden1 + '</td>';

							var tr = '<tr>' + td0 + td1 + td2 + td3 + td4 + td5
									+ td6 + '</tr>';

							$('#dataList').append(tr);
						}

						/* $("#selectedUser option[value='" + id + "']").remove();
						$('#selectedUser').val(""); */

					},
					error : function(data) {
						alert("From JS: Server Error");
					},
					type : 'POST'
				});
	}
	
	function checkWorkOrder() {
		var workOrderNo = $("#workOrderNo").val();
		var saveButton = $("#saveButton");
		
		var contextPath = $("#contextPath").val();

		var workOrderDecision = $("#workOrderDecision");

		$.ajax({
			url : contextPath + '/ac/checkWorkOrder.do',
			data : "{workOrderNo:'" + workOrderNo + "'}",
			contentType : "application/json",
			success : function(data) {
				var result = JSON.parse(data);
				// alert(data);
				if (result == 'success') {
					saveButton.prop("disabled", false);
					workOrderDecision.removeClass();
					workOrderDecision
							.addClass("glyphicon glyphicon-ok-sign green big");

				} else {
					saveButton.prop("disabled", true);
					workOrderDecision.removeClass();
					workOrderDecision
							.addClass("glyphicon glyphicon-remove-sign red big");
				}
			},
			error : function(data) {
				alert("Server Error");
			},
			type : 'POST'
		});
	}

</script>

<%-- 
<script
	src="${pageContext.request.contextPath}/resources/assets/js/centralStore/auction/workOrderForm.js"></script>
 --%>
<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>