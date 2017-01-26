<%@include file="../common/faHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->


<style>
.loader {
	position: fixed;
	left: 50%;
	top: 50%;
	z-index: 9999;
}

.ui-widget-overlay {
	opacity: .6 !important;
}
</style>

<!-- -------------------------------------------------------------------------------------- -->
<div class="loader">
	<i class="fa fa-refresh fa-spin fa-3x fa-fw"></i> <br> <span
		class="orange">Loading...</span>
</div>

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;"></div>

		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Fixed Assets Receive</h1>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">

			<c:if test="${empty cSStoreTicketDtlList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<input type='hidden' value='${pageContext.request.contextPath}'
				id='contextPath'>
			<c:if test="${!empty cSStoreTicketDtlList}">
				<table id="stTicketDtlTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Item Code</td>
							<td style="">Description</td>
							<td style="">Unit</td>
							<td style="">Quantity</td>
							<td style="">Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${cSStoreTicketDtlList}" var="csStoreTicketDtl"
							varStatus="status">
							<tr>

								<td><input type="hidden" id="itemId${status.index}"
									value="${csStoreTicketDtl.itemId}" /><input type="hidden" id="id${status.index}" 
									value="${csStoreTicketDtl.id}" />
								<c:out value="${csStoreTicketDtl.itemId}" /></td>
								<td><input type="hidden" id="itemName${status.index}"
									value="${csStoreTicketDtl.description}" />
								<c:out value="${csStoreTicketDtl.description}" /></td>
								<td><c:out value="${csStoreTicketDtl.uom}" /></td>
								<td><input type="hidden" id="quantity${status.index}"
									value="${csStoreTicketDtl.quantity}" />
								<c:out value="${csStoreTicketDtl.quantity}" /></td>
								<td><c:if test="${csStoreTicketDtl.fixedAsset==false && csStoreTicketDtl.ledgerName=='NEW_SERVICEABLE'}"><input type="button" id="transfer" value="Transfer To Asset Book"
									onClick="openDialoge('id${status.index}','itemId${status.index}','itemName${status.index}','quantity${status.index}')" />
									</c:if><c:if test="${csStoreTicketDtl.fixedAsset==true}">Transfered To Asset Book</c:if></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
		</div>
	</div>
</div>
<div id="myDialogeBox" class="hide">
	<div>
		<form
			action="${pageContext.request.contextPath}/fixedAssets/saveFixedAsset.do"
			id="fixedAssetForm" method="POST" onSubmit="return check()">
			<!-- Master Info :: start -->
			<div class="table-responsive">
				<table class="table table-bordered">
					<tbody>
						 <tr>
							<td class="col-xs-2 success">Ticket No</td>
							<td class="col-xs-4 info"><input type=hidden id="assetId"
								name="assetId" value="" /><input
								type="text" id="ticketNo" name="ticketId"
								value="${cSStoreTicketMst.ticketNo}" readonly="readonly" /></td>
							<td class="col-xs-2 success">Ticket Date</td>
							 <td class="col-xs-4 info">
							<input type="text" id="ticketDate"
								name="ticketDate" value="<fmt:formatDate
					value="${cSStoreTicketMst.ticketDate}" pattern="dd-MM-yyyy" />"
								readonly="readonly" /> 
								</td> 

						</tr> 

						 <tr>
						<td class="col-xs-2 success">From</td>
						<td class="col-xs-4 info"><input type="text" class="form-control" name="fromDept" id="fromDept" value="<c:choose><c:when test="${deptName=='cs'}">CENTRAL STORE</c:when><c:otherwise>SUB STORE</c:otherwise></c:choose>" readonly="readonly" />
						</td>
						<td class="col-xs-2 success">To</td>
						<td class="col-xs-4 info"><input type="text" id="toDept" name="toDept" value="${cSStoreTicketMst.issuedTo}" readonly="readonly" /></td>
						</tr>
					
					<tr>
						<td class="col-xs-2 success">itemId</td>
						<td class="col-xs-4 info"><input type="text" class="form-control" id="itemCode"
								style="border: 0; border-bottom: 2px ridge;" name="itemId"
								value="" readonly="readonly"/></td>
						<td class="col-xs-2 success">Item Name</td>
						<td class="col-xs-4 info"><input type="text" class="form-control" id="itemName"
								style="border: 0; border-bottom: 2px ridge;" name="itemName"
								value=""  readonly="readonly"/></td>
						</tr>
					
					<tr>
						<td class="col-xs-2 success">Quantity</td>
						<td class="col-xs-4 info"><input type="text" class="form-control" id="quantity"
								style="border: 0; border-bottom: 2px ridge;" name="quantity"
								value=""  readonly="readonly"/></td>
						<td class="col-xs-2 success">Khath Name</td>
						<td class="col-xs-4 info"><input type="hidden" name="khatCode" value="${cSStoreTicketMst.khathId}"/>${cSStoreTicketMst.khathName}</td>
						</tr>
					
					<tr>
						<td class="col-xs-2 success">A/c Head Name</td>
						<td class="col-xs-4 info">
							<select class="form-control" id="assetCategoryCode" name="assetCategoryCode" style="border: 0; border-bottom: 2px ridge;">
											<option value="">Select</option>
								<c:if test="${!empty fixedAssetCategory}">
										<c:forEach items="${fixedAssetCategory}" var="category">
											<option value="${category.assetCategoryCode}">
												<c:out value="${category.assetCategoryName}" />
											</option>
										</c:forEach>
								</c:if>
							</select>
						</td>
						<td class="col-xs-2 success">Asset Type</td>
						<td class="col-xs-4 info">
							<select class="form-control assetType" name="assetType"
								id="assetType" style="border: 0; border-bottom: 2px ridge;">
								<option value="Operating">Operating</option>
								<option value="Non-Operating">Non-Operating</option>
							</select>
						</td>
						</tr>
					
					<tr>
						<td class="col-xs-2 success">Average Price</td>
						<td class="col-xs-4 info"><input type="text" class="form-control" id="avgPrice"
								style="border: 0; border-bottom: 2px ridge;" name="avgPrice"
								value="" onBlur="setTotalCost()"/></td>
						<td class="col-xs-2 success">Total Price</td>
						<td class="col-xs-4 info"><input type="text" class="form-control" id="totalPrice"
								style="border: 0; border-bottom: 2px ridge;" name="totalPrice"
								value="" readonly="readonly"/></td>
						</tr> 
					<tr>
						<td class="col-xs-2 success">Life Time</td>
						<td class="col-xs-4 info"><input type="text" class="form-control" id="lifeTime"
								style="border: 0; border-bottom: 2px ridge;" name="lifeTime"
								value="" readonly="readonly"/></td>
						<td class="col-xs-2 success">Depreciation Rate</td>
						<td class="col-xs-4 info"><input type="text" class="form-control" id="depreciationRate"
								style="border: 0; border-bottom: 2px ridge;" name="depreciationRate"
								value="" readonly="readonly"/></td>
					</tr> 
					<tr>
						<td class="col-xs-2 success">Serial Number</td>
						<td class="col-xs-4 info"><input class="form-control"
								type="text" id="serialNumber" name="serialNumber"
								value="" style="border: 0; border-bottom: 2px ridge;"/></td>
						<td class="col-xs-2 success">Model Number</td>
						<td class="col-xs-4 info"><input class="form-control"
								type="text" id="modelNumber" name="modelNumber"
								value="" style="border: 0; border-bottom: 2px ridge;"/>
						</td>
					</tr> 
					</tbody>
				</table>
			</div>
			<!-- Master Info :: end -->
		</form>
	</div>
</div>
<!-- ---------------------- -->

<script>
	$(window).load(function() {
		$('.loader').fadeOut('slow');
	});
	
	function setTotalCost() {
		
		var avgPrice = $('#avgPrice').val().trim();
		var avgPriceFloat = parseFloat(avgPrice).toFixed(2);
		var quantity = $('#quantity').val().trim();
		var quantityFloat = parseFloat(quantity).toFixed(2);
		var totalCost = (avgPriceFloat * quantityFloat).toFixed(2);
		$('#totalPrice').val(totalCost);

	}
	$(function() {
		$("#myDialogeBox").dialog({
			autoOpen : false,
			closeOnEscape : false,
			modal : true,
			draggable : false,
			resizable : false,
			position : {
				my : "center",
				at : "center",
				of : window
			},
			show : {
				effect : "blind",
				duration : 10
			},
			hide : 'blind',
			width : $(window).width() - 600,
			height : 500,
			buttons : [ {
				text : 'Save',
				click : function() {
					// send post request
					$("#fixedAssetForm").submit();
					// $('#myDialogeBox').empty();
					//getCountBtn();
				}
			}, {
				text : 'Close',
				click : function() {
					$(this).dialog("close");
				}
			} ]
		});

	});

	function openDialoge(id,itemId,name,qty) {
		var idd = document.getElementById(id).value;
		var itemIdd = document.getElementById(itemId).value;
		var named = document.getElementById(name).value;
		var qtyd = document.getElementById(qty).value;
		//alert(itemIdd);
		getAvgPrice(itemIdd, qtyd);
		getLifeTime(itemIdd);
		$('#myDialogeBox').removeClass('hide');
		$(".ui-dialog-titlebar").hide();
		$("#assetId").val(idd);
		$("#itemCode").val(itemIdd);
		$("#itemName").val(named);
		$("#quantity").val(qtyd);
		$("#myDialogeBox").dialog("open");
	}

	function getAvgPrice(idValue, qtyValue) {
		$.ajax({
			type : "post",
			url : 'avgPrice.do',
			async : false,
			data : 'itemCode=' + idValue,
			success : function(response) {
				$("#avgPrice").val(response);
				$("#totalPrice").val(response * qtyValue);
			},
			error : function() {
			}
		});

		return true;
	}
	function getLifeTime(idValue) {
		$.ajax({
			type : "post",
			url : 'lifeTime.do',
			async : false,
			data : 'itemCode=' + idValue,
			success : function(response) {
				var s=response.split(",");
				$("#lifeTime").val(s[0]);
				$("#depreciationRate").val(s[1]);
			},
			error : function() {
			}
		});

		return true;
	}
	
	//function getCountBtn() {
		//var transfer=("#transfer").val();
		
		//var p=0;
		/* for(var i=1;i<transfer.length;i++){
			p++;
		} */
//alert(document.getElementById("transfer").length);
		//return true;
	//}
</script>

<script
	src="${pageContext.request.contextPath}/resources/assets/js/jquery.dataTables.min.js"></script>

<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.bootstrap.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.tableTools.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.colVis.min.js"></script>
<script>
	$(document)
			.ready(
					function() {
						$('#stTicketDtlTable').DataTable({

							"order" : [ [ 1, 'asc' ] ]
						});
						document.getElementById('stTicketDtlTable_length').style.display = 'none';
						//document.getElementById('inventoryLookupItemTable_filter').style.display = 'none';

						$(".alert.alert-success").fadeTo(4000, .5).slideUp(500,
								function() {
									//  $(".alert.alert-success").alert('close');
								});

						$(".alert.alert-danger").fadeTo(4000, .5).slideUp(500,
								function() {
									//  $(".alert.alert-danger").alert('close');
								});
					});
	
	
				function check() {
				
					var assetCategoryCode = $('#assetCategoryCode').val();

				
					var inputVal = new Array(assetCategoryCode);

					var inputMessage = new Array("Asset Category Code");

					$('.error').hide();

					if (inputVal[0] == "") {
						$('#assetCategoryCode').after(
								'<span class="error" style="color:red"> Please enter '
										+ inputMessage[0] + '</span>');
						return false;
					}else{

					return true;}
				}
</script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>