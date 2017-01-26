//called when user clicks Set Button to save location || added by Ashid
function setLocation() {
	var x = '';
	var u = '';
	var rowId = '';
	var l = [];
	var q = [];

	$('#modalForm input, #modalForm select').each(function(index) {
		var e = $(this);

		if (e.attr('name') == 'itemCode') {
			x = e.val();
		}
		if (e.attr('name') == 'uuid') {
			u = e.val();
		}
		if (e.attr('name') == 'index') {
			rowId = e.val();
		}
		if (e.attr('name') == 'locationId') {
			l.push(e.val());
		}
		if (e.attr('name') == 'locationQty') {
			q.push(e.val());
		}
	});

	var locQty = {};

	for (var i = 0; i < q.length; i++) {
		locQty[l[i]] = q[i];
	}

	var itemWiseLocationQty = {
		itemCode : x,
		uuid : u,
		locQtyDtl : locQty
	};

	var locationQtyJsonString = JSON.stringify(itemWiseLocationQty);

	$.ajax({
		type : "POST",
		contentType : "application/json; charset=utf-8",
		url : $('#modalForm').attr('action'),
		data : locationQtyJsonString,
		success : function(data) {
			setTotalQtyByUuidAndItemCode();
			resetLocation();
		},
		dataType : "json"
	});
	// will set total received qty of selected Item
	

}
// get And Set Total Qty on receive box of dtl row after set Location
function setTotalQtyByUuidAndItemCode() {
	var uuid = $("#uuid").val();
	var itemCode =$("#hiddenItemCode").val();
	var rowId = $("#hiddenIndex").val();
	var cData = {
			uuid : uuid,
			itemCode : itemCode
		}
	var cDataJsonString = JSON.stringify(cData);
	$
	.ajax({
		type : "POST",
		contentType : "application/json; charset=utf-8",
		url : $('#contextPath').val()+'/ls/getTotalQtyByUuidAndItemCode.do',
		data : cDataJsonString,
		success : function(data) {
			//set updated value in rcv qty
			$("#issuedQty" + rowId).val(data);
		},
		dataType : "json"
	});
	
}
// called when user clicks reset Button || added by Ashid
function resetLocation() {
	$('.clonedArea').remove();
	$('#modalForm input').val('');
	$('#modalForm input').attr('readonly', true);
	$('#modalForm select').val('Select Location');
	$('#modalForm select option:selected').attr("selected", null);
	$('#modalForm').find('.btn-remove').removeClass('btn-remove').addClass(
			'btn-add').removeClass('btn-danger').addClass('btn-success').html(
			'<span class="glyphicon glyphicon-plus"></span>');
	$('#myModal').modal('hide');
}

// called when user clicks set Button to open location modal || added by Ashid
function setModalHeader(itemCode, index) {

	$("#itemCodeforLocationModal").text(itemCode);
	$("#hiddenItemCode").val(itemCode);
	$("#hiddenIndex").val(index);
	$("#hiddenUUID").val($("#uuid").val());
}


// called when user select a location || added by Ashid
function locationChange(element) {
	var id = $(element).attr('id');
	var sequence = id.substr(id.length - 1);
	$('#locationQty' + sequence).attr('readonly', false);
}

//

$(function() {
	$(document)
			.on(
					'click',
					'.btn-add',
					function(e) {
						var num = $('.clonedArea').length;
						var newNum = num + 1;
						var controlForm = $('.myControl div:first'), currentEntry = $(
								this).parents('.entry:first'), newEntry = $(
								currentEntry.clone().attr('id',
										'myArea' + newNum).addClass(
										'clonedArea')).appendTo(controlForm);
						// set dynamic id on item qty fields locationQty
						var mainDiv = document
								.getElementById('myArea' + newNum), childDiv = mainDiv
								.getElementsByTagName('div')[0];
						// start of seting id on location fields
						var locationDiv = mainDiv.getElementsByTagName('div')[0], locationInput = locationDiv
								.getElementsByTagName('select')[0];
						locationInput.setAttribute('id', 'location' + newNum);

						var locationQtyDiv = mainDiv
								.getElementsByTagName('div')[1], locationQtyInput = locationQtyDiv
								.getElementsByTagName('input')[0];
						locationQtyInput.setAttribute('id', 'locationQty'
								+ newNum);

						newEntry.find('input').val('');
						controlForm
								.find('.entry:not(:last) .btn-add')
								.removeClass('btn-add')
								.addClass('btn-remove')
								.removeClass('btn-success')
								.addClass('btn-danger')
								.html(
										'<span class="glyphicon glyphicon-minus"></span>');
					}).on('click', '.btn-remove', function(e) {
				$(this).parents('.entry:first').remove();
				return false;
			});

});

// 

function expectQtyEqualReceive(element) {
	var id = $(element).attr('id');
	var sequence = id.substr(id.length - 1);
	$('#receivedQty' + sequence).val($('#expectedQty' + sequence).val());
}

//

function receivedQtyToRemainQty(element) {
	var id = $(element).attr('id');
	var sequence = id.substr(id.length - 1);

	var expected = parseInt($('#expectedQty' + sequence).val().trim(), 10);

	var received = parseInt($(element).val().trim(), 10);

	if (received > expected) {
		$(element).val($('#expectedQty' + sequence).val());
	}

	$('#remainingQty' + sequence).val(
			$('#expectedQty' + sequence).val()
					- $('#receivedQty' + sequence).val());

}

//

$(document)
		.ready(
				function() {
					$(function() {

						$("#contractNo")
								.autocomplete(
										{
											source : function(request, response) {
												// alert(request.term);
												$
														.ajax({
															url : 'getWwOrder.do',
															type : "POST",
															data : {
																contractNo : request.term
															},

															dataType : "json",

															success : function(
																	data) {
																response($
																		.map(
																				data,
																				function(
																						v,
																						i) {
																					return {
																						label : v.workOrderNo,
																						value : v.workOrderNo
																					};

																				}));
															}

														});

											},
											response : function(event, ui) {
												if (!ui.content.length) {
													var noResult = {
														value : "",
														label : "No matching your request"
													};
													ui.content.push(noResult);
												}
											},
											select : function(event, ui) {
												// alert(ui.item.label);
												window.location.href = 'viewWorkOrderData.do?contractNo='
														+ ui.item.label;
											},
											minLength : 1
										});
					});
				});

// /

$(document).ready(
		function() {

			$('#submit').click(function() {
				return validateForm();
				// return detailCheck();
			});

			function validateForm() {

				/*
				 * var nameReg = /^[A-Za-z]+$/; var numberReg = /^[0-9]+$/; var
				 * emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
				 */
				var contractNo = $('#contractNo').val();
				var chalanNo = $('#chalanNo').val();
				var invoiceDate = $('#invoiceDate').val();
				var supplierName = $('#supplierName').val();
				var delivaryDate = $('#deliveryDate').val();

				var inputVal = new Array(contractNo, chalanNo, invoiceDate,
						supplierName, deliveryDate);

				var inputMessage = new Array("contract No", "chalan No",
						"invoiceDate", "supplier Name", "delivery Date");

				$('.error').hide();

				if (inputVal[0] == "") {
					$('#contractNo').after(
							'<span class="error" style="color:red"> Please enter '
									+ inputMessage[0] + '</span>');
					return false;
				}
				if (inputVal[1] == "") {
					$('#chalanNo').after(
							'<span class="error" style="color:red"> Please enter '
									+ inputMessage[1] + '</span>');
					return false;
				}

				if (inputVal[2] == "") {
					$('#invoiceDate').after(
							'<span class="error" style="color:red"> Please enter '
									+ inputMessage[2] + '</span>');
					return false;
				}
				if (inputVal[3] == "") {
					$('#supplierName').after(
							'<span class="error" style="color:red"> Please a enter '
									+ inputMessage[3] + ' </span>');
					return false;
				}

				if (inputVal[4] == "") {
					$('#deliveryDate').after(
							'<span class="error" style="color:red"> Please enter '
									+ inputMessage[4] + '</span>');
					return false;
				}

				return true;
			}			
		});

// location edit dialog is set hidden
$(function() {
	$("#myDialog").dialog({
		autoOpen : false,
		closeOnEscape : false,
		modal : true,
		draggable : false,
		resizable : false,
		position : 'center',
		show : 'blind',
		hide : 'blind',
		width : 600,
		buttons : {
			"Close" : function() {
				getTotalQtyByUuidAndItemCode();
				$(this).dialog("close");				
				$('#dynamicTable').empty();
			}
		}
	});

});

function getTotalQtyByUuidAndItemCode() {
	var uuid = $("#uuid").val();
	var itemCode =$("#dynamicTable").attr("rel");
	var rowId = $("#dynamicTable").attr("data-rowid");
	var cData = {
			uuid : uuid,
			itemCode : itemCode
		}
	var cDataJsonString = JSON.stringify(cData);
	$
	.ajax({
		type : "POST",
		contentType : "application/json; charset=utf-8",
		url : $('#contextPath').val()
				+ "/ls/getTotalQtyByUuidAndItemCode.do",
		data : cDataJsonString,
		success : function(data) {
			//set updated value in rcv qty
			$("#issuedQty" + rowId).val(data);
		},
		dataType : "json"
	});	
}

//open dialog for location edit
function editItemLocation(itemCode, rowid) {
	
	var cData = {
		uuid : $("#uuid").val(),
		itemCode : itemCode.toString()
	}
	var cDataJsonString = JSON.stringify(cData);
	$
			.ajax({
				type : "POST",
				contentType : "application/json; charset=utf-8",
				url : $('#contextPath').val()
						+ "/ls/getTemplocation.do",
				data : cDataJsonString,
				success : function(data) {
					$('#dynamicTable').append(
							'<table class="col-sm-12 table"></table>');
					$("#dynamicTable").attr("rel", itemCode.toString());
					$("#dynamicTable").attr("data-rowid", rowid);
					var table = $('#dynamicTable').children();
					if (data.length > 0) {
						table
								.append('<tr class="success"><td>Location Name</td><td>Item Quantity</td><td>Update</td><td>Delete</td></tr>');
					} else {
						table
								.append('<tr class="danger center"><td colspan="4">Sorry!!! No Location is set for this item.</td></tr>');
					}

					for (var i = 0; i < data.length; i++) {
						var row = data[i];
						table
								.append('<tr><td><input type="hidden" id="loc'
										+ i
										+ '" value="'
										+ row.locationId
										+ '" />'
										+ row.locationName
										+ '</td><td><input type="number" id="qty'
										+ i
										+ '" value="'
										+ row.quantity
										+ '" /></td><td><a href="#" class="btn btn-success" onclick="updateLocationQty('+row.id+','+i
										+')"> <i class="glyphicon glyphicon-ok"></i></a></td>'
										+'<td><a href="#" class="btn btn-danger" onclick="deleteLocationQty('+row.id+','+i+')"> <i class="glyphicon glyphicon-remove"></i></a></td>'
										+'</tr>');
					}

				},
				dataType : "json"
			});
	$("#myDialog").dialog("open");
}


//update location qty
function updateLocationQty(pk, index) {
	var q=$("#qty"+index).val();
	var cData = {
			quantity : q,
			id : pk
		}
	var cDataJsonString = JSON.stringify(cData);
	
	$.ajax({
		type : "POST",
		contentType : "application/json; charset=utf-8",
		url : $('#contextPath').val()
				+ "/ls/updateLocationQty.do",
		data : cDataJsonString,
		success : function(data) {
			alert(data);
		},
		dataType : "json"
	});
}


//delete an row of location
function deleteLocationQty(pk, index) {
	var cData = {id : pk};
	var cDataJsonString = JSON.stringify(cData);
	
	$.ajax({
		type : "POST",
		contentType : "application/json; charset=utf-8",
		url : $('#contextPath').val()
				+ "/ls/deleteLocationQty.do",
		data : cDataJsonString,
		success : function(data) {					
			alert(data);
			loadItemLocationAfterDelete();
		},
		dataType : "json"
	});
}

//reload edit page after delete
function loadItemLocationAfterDelete() {
	var itemCode=$("#dynamicTable").attr("rel");
	$('#dynamicTable').empty();
	
	var cData = {
		uuid : $("#uuid").val(),
		itemCode : itemCode.toString()
	}
	var cDataJsonString = JSON.stringify(cData);
	$
			.ajax({
				type : "POST",
				contentType : "application/json; charset=utf-8",
				url : $('#contextPath').val()
						+ "/ls/getTemplocation.do",
				data : cDataJsonString,
				success : function(data) {
					$('#dynamicTable').append(
							'<table class="col-sm-12 table"></table>');
					var table = $('#dynamicTable').children();
					if (data.length > 0) {
						table
								.append('<tr class="success"><td>Location Name</td><td>Item Quantity</td><td>Update</td><td>Delete</td></tr>');
					} else {
						table
								.append('<tr class="danger center"><td colspan="4">Sorry!!! No Location is set for this item.</td></tr>');
					}

					for (var i = 0; i < data.length; i++) {
						var row = data[i];
						table
								.append('<tr><td><input type="hidden" id="loc'
										+ i
										+ '" value="'
										+ row.locationId
										+ '" />'
										+ row.locationName
										+ '</td><td><input type="number" id="qty'
										+ i
										+ '" value="'
										+ row.quantity
										+ '" /></td><td><a href="#" class="btn btn-success" onclick="updateLocationQty('+row.id+','+i
										+')"> <i class="glyphicon glyphicon-ok"></i></a></td>'
										+'<td><a href="#" class="btn btn-danger" onclick="deleteLocationQty('+row.id+','+i+')"> <i class="glyphicon glyphicon-remove"></i></a></td>'
										+'</tr>');
					}

				},
				dataType : "json"
			});	
}

// before
function forwardToUpper(stateCode){
	var justification = $('#justification').val();
	var requisitionNo = $('#requisitionNo').val();
	
	var quantityIssued = [];
	  $(".quantityIssued").each(function(){
	     var th= $(this);
	     
	     quantityIssued.push(th.val());
	    
	   });	
	
	//window.location = "${pageContext.request.contextPath}/cs/itemRecieved/itemReceivedForwordTo.do?receivedReportNo="+param1+"$justification="+justification;
	  if(quantityIssued.length>0){
		  window.location = $('#contextPath').val()+"/ls/requisition/sendTo.do?requisitionNo="+requisitionNo+"&justification="+justification+"&stateCode="+stateCode+"&issueqty="+quantityIssued;
	  }
}

function backToLower(stateCode){
	var justification = $('#justification').val();
	var requisitionNo = $('#requisitionNo').val();		
	//window.location = "${pageContext.request.contextPath}/cs/itemRecieved/itemReceivedForwordTo.do?receivedReportNo="+param1+"$justification="+justification;
	window.location = $('#contextPath').val()+"/ls/requisition/backTo.do?requisitionNo="+requisitionNo+"&justification="+justification+"&stateCode="+stateCode;
}

function approveing(){		
	var justification = $('#justification').val();
	var requisitionNo = $('#requisitionNo').val();		

	var returnStateCode = $('#returnStateCode').val();
	
	var quantityIssued = [];
	  $(".quantityIssued").each(function(){
	     var th= $(this);
	     
	     quantityIssued.push(th.val());
	    
	   });	
	if(quantityIssued.length>0){
	window.location = $('#contextPath').val()+"/ls/itemRequisitionSubmitApproved.do?requisitionNo="+requisitionNo+"&justification="+justification+"&return_state="+returnStateCode+"&issueqty="+quantityIssued;
	}else{
		alert("Issued Quantity can not be 0 or null");
	}
}
