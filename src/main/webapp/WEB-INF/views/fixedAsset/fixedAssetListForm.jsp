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
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Fixed Asset List</h1>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">

			<c:if test="${empty fixedAssetReceives}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<input type='hidden' value='${pageContext.request.contextPath}'
				id='contextPath'>
			<c:if test="${!empty fixedAssetReceives}">
				<table id="inventoryLookupItemTable"
					class="table table-striped table-hover table-bordered">
					<thead>

						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<th>Asset Id</th>
							<th>Asset Name</th>	
							<th>Serial No.</th>							
							<th>Receive Date</th>
							<th>Location</th>
							<th>Quantity</th>
							<th>Total Price</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody>
 <!-- href="javascript:void(0)" -->
						<c:forEach items="${fixedAssetReceives}" var="fixedAssetReceive"
							varStatus="status">
							<tr><td><a onclick="redirectURL('${fixedAssetReceive.itemId}','${fixedAssetReceive.receiveDate}','${fixedAssetReceive.remarks}','${fixedAssetReceive.totalPrice}','${fixedAssetReceive.faRegKey}')" href="javascript:void(0)" style="text-decoration: none;">${fixedAssetReceive.itemId}</a></td>								
								<td class="col-xs-3">${fixedAssetReceive.itemName}</td>
								<td class="col-xs-1">${fixedAssetReceive.serialNumber}</td>
								<td class="col-xs-1"><input type="hidden"
									value="${fixedAssetReceive.itemId}" id="assetId${status.index}" />
									<input type="hidden"
									value="${fixedAssetReceive.remarks}" id="flag${status.index}" /><input type="hidden"
									value="${fixedAssetReceive.receiveDate}" id="receiveDate${status.index}" /><fmt:formatDate
					value="${fixedAssetReceive.receiveDate}" pattern="dd-MM-yyyy" /></td>
								<td class="col-xs-2"><input type="hidden"
									value="${fixedAssetReceive.toDept}" id="toDept${status.index}" />${fixedAssetReceive.toDept}</td>
								<td>${fixedAssetReceive.quantity}</td>
								<td><input type="hidden"
									value="<fmt:formatNumber groupingUsed="false" type="number" value="${fixedAssetReceive.totalPrice}"/>" id="totalPrice${status.index}" /><fmt:formatNumber type="number" 
             groupingUsed="false" value="${fixedAssetReceive.totalPrice}" /></td>
								<td>
									<%-- <img alt="" title="Disposal" src="${pageContext.request.contextPath}/resources/assets/img/disposal1.png" height="40" width="40" onclick="openDialoge1('assetId')">
								<img alt="" title="Write Off" src="${pageContext.request.contextPath}/resources/assets/img/writeOff.png" height="40" width="40" onclick="openDialoge2('assetId')">
								<img alt="" title="Location Transfer" src="${pageContext.request.contextPath}/resources/assets/img/location.jpg" height="40" width="40" onclick="openDialoge3('assetId')">
								 --%>
								 <button type="button" title="Show Depreciation Details of Current Year"
										onClick="openDialoge4('assetId${status.index}','receiveDate${status.index}','totalPrice${status.index}','flag${status.index}','${fixedAssetReceive.faRegKey}')">
										<span class="fa fa-info fa-2x blue"></span>
									</button>
								 <button type="button" title="Location Transfer"
										onClick="openDialoge3('assetId${status.index}','toDept${status.index}','receiveDate${status.index}','flag${status.index}')">
										<span class="fa fa-map-marker fa-2x blue"></span>
									</button>
								 <button type="button" title="Write-Off"
										onClick="openDialoge2('assetId${status.index}','receiveDate${status.index}','flag${status.index}')">
										<span class="fa fa-power-off fa-2x blue"></span>
									</button>
									<button type="button" title="Disposal"
										onClick="openDialoge1('assetId${status.index}','receiveDate${status.index}','flag${status.index}')">
										<span class="fa fa-trash fa-2x blue"></span>
									</button>
									
								</td>

							</tr>


						</c:forEach>
					</tbody>
				</table>
			</c:if>
		</div>
	</div>
</div>
<div id="myDialogeBox1" class="hide">
	<div>
		<form
			action="${pageContext.request.contextPath}/fixedAssets/saveDisposal.do"
			id="disposalForm" method="POST" onsubmit="return check1();">
			<!-- Master Info :: start -->
			<div class="table-responsive">
				<table class="table table-bordered">
					<tbody>


						<tr>
							<td class="col-xs-2 success">Reason Of Disposal</td>
							<td class="col-xs-4 info"><input type="hidden" value=""
								id="assetIdD" name="assetId" />
								<input type="hidden" value=""
								id="flagD" name="flag" /> 
								<input type="hidden" value=""
								 name="receiveDate" id="receiveDateD" /><select class="form-control"
								id="reasonOfDisposal"
								style="border: 0; border-bottom: 2px ridge;"
								name="reasonOfDisposal">
									<option value="">Select</option>
									<option value="Lost">Lost</option>
									<option value="Sold">Sold</option>
									<option value="Stolen">Stolen</option>
									<option value="Damaged">Damaged</option>

							</select></td>

							<td class="col-xs-2 success">Written Down Value at time of Disposal</td>
							<td class="col-xs-4 info"><input type="text"
								class="form-control" id="disposalValue"
								style="border: 0; border-bottom: 2px ridge;"
								name="disposalValue" value="" step="0.001" readonly="readonly" /></td>
						</tr>
						<tr>
							<td class="col-xs-2 success">Sale Amount</td>
							<td class="col-xs-4 info">
							<input type="text" value=""
								id="saleValue" name="saleValue" onBlur="getPL()" /></td>

							<td class="col-xs-2 success">Gain / Loss</td>
							<td class="col-xs-4 info"><input type="text"
								class="form-control" id="gainOrLoss" step="0.001"
								style="border: 0; border-bottom: 2px ridge;"
								name="gainOrLoss" value="" readonly="readonly"/></td>
						</tr>
						<tr>

							<td class="col-xs-2 success">Remarks</td>
							<td class="col-xs-4 info"><input type="text"
								class="form-control" id="remarks"
								style="border: 0; border-bottom: 2px ridge;" name="dispRemarks"
								value="" /></td>

							<td class="col-xs-2 success"></td>
							<td class="col-xs-4 info"></td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- Master Info :: end -->
		</form>
	</div>
</div>

<div id="myDialogeBox2" class="hide">
	<div>
		<form
			action="${pageContext.request.contextPath}/fixedAssets/saveWriteOff.do"
			id="writeOffForm" method="POST" onsubmit="return check2();">
			<!-- Master Info :: start -->
			<div class="table-responsive">
				<table class="table table-bordered">
					<tbody>


						<tr>
							<td class="col-xs-2 success">Reason Of Write Off</td>
							<td class="col-xs-4 info"><input type="hidden" value=""
								id="assetIdR" name="assetId" />
								<input type="hidden" value=""
								id="flagR" name="flag" />
								<input type="hidden" value=""
								 name="receiveDate" id="receiveDateR" />
								 <select class="form-control"
								id="reasonWriteOff"
								style="border: 0; border-bottom: 2px ridge;"
								name="reasonWriteOff">
									<option value="">Select</option>
									<option value="Lost">Lost</option>
									<option value="Sold">Sold</option>
									<option value="Stolen">Stolen</option>
									<option value="Damaged">Damaged</option>

							</select></td>

							<td class="col-xs-2 success">Written Down Value at time of Write-Off</td>
							<td class="col-xs-4 info"><input type="number"
								class="form-control" id="writeOffValue"
								style="border: 0; border-bottom: 2px ridge;" name="writeOffValue"
								value="" step="0.001" readonly="readonly" /></td>
						</tr>
						<tr>
							

							<td class="col-xs-2 success">Write-Off Note</td>
							<td class="col-xs-4 info"><input type="text"
								class="form-control" id="writeOffNote"
								style="border: 0; border-bottom: 2px ridge;" name="writeOffNote"
								value="" /></td>
								<td class="col-xs-2 success"></td>
							<td class="col-xs-4 info"></td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- Master Info :: end -->
		</form>
	</div>
</div>
<div id="myDialogeBox3" class="hide">
	<div>
		<form
			action="${pageContext.request.contextPath}/fixedAssets/updateLocation.do"
			id="locationForm" method="POST">
			<!-- Master Info :: start -->
			<div class="table-responsive">
				<table class="table table-bordered">
					<tbody>


						<tr>
							<td class="col-xs-2 success">Current Location</td>
							<td class="col-xs-4 info"><input type="hidden" value=""
								id="assetIdL" name="assetId" /><input type="hidden" value=""
								 name="receiveDate" id="receiveDateL" /> <input type="text"
								class="form-control" id="locIdL"
								style="border: 0; border-bottom: 2px ridge;" value=""
								readonly="readonly" />
								<input type="hidden" value=""
								id="flagL" name="flag" /></td>

							<td class="col-xs-2 success">Transfer To Location</td>
							<td class="col-xs-4 info"> <select class="form-control"
								name="locationId" id="locationId"
								style="border: 0; border-bottom: 2px ridge;">
									<option value="">Select</option>
									<c:if test="${!empty departments}">
										<c:forEach items="${departments}" var="department">
											<option value="${department.deptId}">
												<c:out value="${department.deptName}" /></option>
										</c:forEach>
									</c:if>
							</select></td>
						</tr>
					</tbody>
				</table> 
			</div>
			<!-- Master Info :: end -->
		</form>
	</div>
</div>

<div id="myDialogeBox4" class="hide">
	<div>
		<form
			action="" id="deprList" method="POST">
			<!-- Master Info :: start -->
			<div class="table-responsive">
				<table class="table table-bordered">
					<tbody>


						<tr>
						<td class="col-xs-2 success">Purchase Price</td>
							<td class="col-xs-2 success">Depreciation Upto Last Year</td>
							<td class="col-xs-2 success">Current Year Depreciation</td>
							<td class="col-xs-2 success">CuDepreciation</td>
							<td class="col-xs-2 success">Written Down Value</td>
						</tr>
						<tr>
							<td class="col-xs-2 success"><input type="number" value="0.0"
								 name="" id="a" readonly="readonly" step="0.001" /></td>
							<td class="col-xs-2 success"><input type="number" value="0.0"
								 name="" id="b" readonly="readonly" step="0.001" /></td>
							<td class="col-xs-2 success"><input type="number" value="0.0"
								 name="" id="c" readonly="readonly" step="0.001" /></td>
							<td class="col-xs-2 success"><input type="number" value="0.0"
								 name="" id="d"  readonly="readonly" step="0.001"/></td>
							<td class="col-xs-2 success"><input type="number" value="0.0"
								 name="" id="e" readonly="readonly" step="0.001"/></td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- Master Info :: end -->
		</form>
	</div>
</div>

<script>
	$(window).load(function() {
		$('.loader').fadeOut('slow');
	});
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
						$('#inventoryLookupItemTable').DataTable({

							"order" : [ [ 2, 'desc' ] ]
						});
						document
								.getElementById('inventoryLookupItemTable_length').style.display = 'none';
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

	/* function redirectURL( id, issuedBy ) {
	 var baseURL = $('#contextPath').val();
	 var path = baseURL + "/fixedAssets/stShow.do";
	 var param = {
	 'id' : id,
	 'issuedBy' : issuedBy
	 }
	 postSubmit(path, param, "POST");
	 } */

	//For Disposal
	$(function() {
		$("#myDialogeBox1").dialog({
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
			height : 300,
			buttons : [ {
				text : 'Save',
				click : function() {
					// send post request
					$("#disposalForm").submit();
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

	function openDialoge1(id, purchaseDate, flag) {
		var idd = document.getElementById(id).value;
		$('#assetIdD').val(idd);
		var purchaseDated = document.getElementById(purchaseDate).value;
		$('#receiveDateD').val(purchaseDated);
		var flagd = document.getElementById(flag).value;
		getDepr1(idd,purchaseDated);
		$('#flagD').val(flagd);
		$('#myDialogeBox1').removeClass('hide');
		$(".ui-dialog-titlebar").hide();
		$("#myDialogeBox1").dialog("open");
	}
	function getDepr1(id,purchaseDate) {
		$.ajax({
			type : "post",
			url : 'getDepreciation.do',
			async : false,
			data : 'assetId=' + id +'&purchaseDate=' + purchaseDate,
			success : function(response) {
				var s=response.split(",");
				
				$("#disposalValue").val(s[4]);
			},
			error : function() {
			}
		});

		return true;
	}
function getPL() {
		
		var disposalValue = $('#disposalValue').val().trim();
		var disposalValueFloat = parseFloat(disposalValue).toFixed(2);
		var saleValue = $('#saleValue').val().trim();
		var saleValueFloat = parseFloat(saleValue).toFixed(2);
		var profit = (disposalValueFloat - saleValueFloat).toFixed(2);
		$('#gainOrLoss').val(profit);

	}
	
	//For writeOff
	$(function() {
		$("#myDialogeBox2").dialog({
			autoOpen : false,
			closeOnEscape : false,
			modal : true,
			draggable : false,
			resizable : false,
			//title : "Write Off Item",
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
			height : 200,
			buttons : [ {
				text : 'Save',
				click : function() {
					// send post request
					$("#writeOffForm").submit();
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

	function openDialoge2(id, purchaseDate, flag) {

		var idd = document.getElementById(id).value;
		$('#assetIdR').val(idd);
		var purchaseDated = document.getElementById(purchaseDate).value;
		$('#receiveDateR').val(purchaseDated);
		var flagd = document.getElementById(flag).value;
		$('#flagR').val(flagd);
		getDepr2(idd,purchaseDated);
		$('#myDialogeBox2').removeClass('hide');
		$(".ui-dialog-titlebar").hide();
		$("#myDialogeBox2").dialog("open");
	}
	function getDepr2(id,purchaseDate) {
		$.ajax({
			type : "post",
			url : 'getDepreciation.do',
			async : false,
			data : 'assetId=' + id +'&purchaseDate=' + purchaseDate,
			success : function(response) {
				var s=response.split(",");
				
				$("#writeOffValue").val(s[4]);
			},
			error : function() {
			}
		});

		return true;
	}

	//For Location Transfer
	$(function() {
		$("#myDialogeBox3").dialog({
			autoOpen : false,
			closeOnEscape : false,
			modal : true,
			draggable : false,
			resizable : false,
			title : "Location Transfer",
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
			height : 200,
			buttons : [ {
				text : 'Save',
				click : function() {
					// send post request
					$("#locationForm").submit();
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

	function openDialoge3(id, dept, purchaseDate, flag) {

		var idd = document.getElementById(id).value;
		$('#assetIdL').val(idd);
		var purchaseDated = document.getElementById(purchaseDate).value;
		$('#receiveDateL').val(purchaseDated);
		var deptd = document.getElementById(dept).value;
		$('#locIdL').val(deptd);
		var flagd = document.getElementById(flag).value;
		$('#flagL').val(flagd);
		$('#myDialogeBox3').removeClass('hide');
		$(".ui-dialog-titlebar").hide();
		$("#myDialogeBox3").dialog("open");
	}
	
	//For 
	$(function() {
		$("#myDialogeBox4").dialog({
			autoOpen : false,
			closeOnEscape : false,
			modal : true,
			draggable : false,
			resizable : false,
			title : "Location Transfer",
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
			width : $(window).width() - 500,
			height : 200,
			 buttons : [ /* {
				text : 'Save',
				click : function() {
					// send post request
					//$("#deprList").submit();
					// $('#myDialogeBox').empty();
					//getCountBtn();
				}
			}, */ {
				text : 'Close',
				click : function() {
					$(this).dialog("close");
				}
			} ] 
		});

	});

	function openDialoge4(id, purchaseDate, purchasePrice, flag, faRegKey) {
//alert(faRegKey);
		var idd = document.getElementById(id).value;
		//$('#assetIdL').val(idd);
		var purchaseDated = document.getElementById(purchaseDate).value;
		var purchasePriced = document.getElementById(purchasePrice).value;
		//$('#receiveDateL').val(purchaseDated);
		var flagd = document.getElementById(flag).value;
		//$('#flagL').val(flagd);
		getDepr(idd,purchaseDated,purchasePriced);
		$('#myDialogeBox4').removeClass('hide');
		$(".ui-dialog-titlebar").hide();
		$("#myDialogeBox4").dialog("open");
	}
	function getDepr(id,purchaseDate,purchasePrice) {
		$.ajax({
			type : "post",
			url : 'getDepreciation.do',
			async : false,
			data : 'assetId=' + id +'&purchaseDate=' + purchaseDate+'&purchasePrice=' + purchasePrice,
			success : function(response) {
				var s=response.split(",");
				$("#a").val(s[0]);
				$("#b").val(s[1]);
				$("#c").val(s[2]);
				$("#d").val(s[3]);
				$("#e").val(s[4]);
			},
			error : function() {
			}
		});

		return true;
	}
	
	 function check1() {
				if (document.getElementById("disposalValue").value== 0.0) {
					alert("Current value of this asset is zero");
					return false;
				} 
				return true;
			}
	function check2() {
		if (document.getElementById("writeOffValue").value== 0.0) {
			alert("Current value of this asset is zero");
			return false;
		} 
		return true;
	}
	function redirectURL(id,receiveDate,remarks,totalPrice,faRegKey) {
		var baseURL = $('#contextPath').val();
		var path = baseURL + "/fixedAssets/detailShow.do";
		var param = {
				'itemId' : id,
				'receiveDate' : receiveDate,
				'remarks' : remarks,
				'totalPrice' : totalPrice,
				'faRegKey' : faRegKey
		}
		postSubmit(path, param, "POST");
	}
</script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>