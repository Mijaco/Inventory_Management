function editItem(element) {
	var rowid = $(element).attr('id');
	var sequence = rowid.substr(rowid.length - 1);

	var id = $('#dtlId' + sequence).val().trim();

	var qtyNewServiceable = $('#qtyNewServiceable' + sequence).val().trim();
	var qtyNewServiceableFloat = parseFloat(qtyNewServiceable).toFixed(3);

	var qtyRecServiceable = $('#qtyRecServiceable' + sequence).val().trim();
	var qtyRecServiceableFloat = parseFloat(qtyRecServiceable).toFixed(3);

	var qtyUnServiceable = $('#qtyUnServiceable' + sequence).val().trim();
	var qtyUnServiceableFloat = parseFloat(qtyUnServiceable).toFixed(3);

	var totalReturn = $('#totalReturn' + sequence).val().trim();
	var totalReturnFloat = parseFloat(totalReturn).toFixed(3);

	var cData = {
		id : id,
		qtyNewServiceableRcv : qtyNewServiceableFloat,
		qtyRecServiceableRcv : qtyRecServiceableFloat,
		qtyUnServiceableRcv : qtyUnServiceableFloat,
		totalReturnRcv : totalReturnFloat
	}
	var cDataJsonString = JSON.stringify(cData);
	$.ajax({
		type : "POST",
		contentType : "application/json; charset=utf-8",
		url : $('#contextPath').val() + "/c2ls/editReturnSlipDtlCs.do",
		data : cDataJsonString,
		success : function(data) {
			// Now updated RR Dtl qty for that item
			alert(data);
		},
		dataType : "json"
	});

}

function setTotalQuantity(element) {
	var id = $(element).attr('id');
	var sequence = id.substr(id.length - 1);

	var qtyNewServiceable = $('#qtyNewServiceable' + sequence).val().trim();
	var qtyNewServiceableFloat = parseFloat(qtyNewServiceable).toFixed(3);

	var qtyRecServiceable = $('#qtyRecServiceable' + sequence).val().trim();
	var qtyRecServiceableFloat = parseFloat(qtyRecServiceable).toFixed(3);

	var qtyUnServiceable = $('#qtyUnServiceable' + sequence).val().trim();
	var qtyUnServiceableFloat = parseFloat(qtyUnServiceable).toFixed(3);

	var x = parseFloat(qtyNewServiceableFloat);
	var y = parseFloat(qtyRecServiceableFloat);
	var z = parseFloat(qtyUnServiceableFloat);

	var totalReturn = (x + y + z).toFixed(3);
	$('#totalReturn' + sequence).val(totalReturn);

}

function forwardToUpper(stateCode) {
	if ($('#justification').val() == null
			|| $.trim($('#justification').val()) == '') {
		$('.justification').removeClass('hide');
		$('#justification').focus();
		return;
	} else {
		var justification = $('#justification').val();
		var returnSlipNo = $('#returnSlipNo').val();

		// window.location =
		// "${pageContext.request.contextPath}/cs/itemRecieved/itemReceivedForwordTo.do?receivedReportNo="+param1+"$justification="+justification;
		window.location = $('#contextPath').val()
				+ "/c2ls/returnSlip/sendTo.do?returnSlipNo=" + returnSlipNo
				+ "&justification=" + justification + "&stateCode=" + stateCode;
	}

}
function backToLower(stateCode) {
	if ($('#justification').val() == null
			|| $.trim($('#justification').val()) == '') {
		$('.justification').removeClass('hide');
		$('#justification').focus();
		return;
	} else {
		var justification = $('#justification').val();
		var returnSlipNo = $('#returnSlipNo').val();

		// window.location =
		// "${pageContext.request.contextPath}/cs/itemRecieved/itemReceivedForwordTo.do?receivedReportNo="+param1+"$justification="+justification;
		window.location = $('#contextPath').val()
				+ "/c2ls/returnSlip/backTo.do?returnSlipNo=" + returnSlipNo
				+ "&justification=" + justification + "&stateCode=" + stateCode;
	}

}

function approveing() {
	if ($('#justification').val() == null
			|| $.trim($('#justification').val()) == '') {
		$('.justification').removeClass('hide');
		$('#justification').focus();
		return;
	} else {
		$('#buttonApproving').prop('disabled', true);
		var justification = $('#justification').val();
		var returnSlipNo = $('#returnSlipNo').val();

		var returnStateCode = $('#returnStateCode').val();

		window.location = $('#contextPath').val()
				+ "/c2ls/itemReturnSlipApproved.do?returnSlipNo="
				+ returnSlipNo + "&justification=" + justification
				+ "&return_state=" + returnStateCode;
	}

}

// added by taleb
// called when user clicks Set Button to save location || added by Ashid
function setLocation() {
	var x = '';
	var u = '';
	var rowId = '';
	var l = [];
	var q = [];
	var led = [];

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
		if (e.attr('name') == 'ledgerName') {
			led.push(e.val());
		}
	});

	var locQty = {};

	for (var i = 0; i < q.length; i++) {
		locQty[l[i]] = q[i];
	}

	var locLedQty = [];

	for (var i = 0; i < q.length; i++) {
		var obj = {};
		obj.ledgerName = led[i];
		obj.locationId = l[i];
		obj.quantity = q[i];
		locLedQty.push(obj);
	}

	var itemWiseLocationQty = {
		itemCode : x,
		uuid : u,
		locQtyDtl : locQty
	};

	var itemWiseLedgerLocationQty = {
		itemCode : x,
		uuid : u,
		ledLocQtyList : locLedQty
	};

	var locationQtyJsonString = JSON.stringify(itemWiseLedgerLocationQty);

	$.ajax({
		type : "POST",
		contentType : "application/json; charset=utf-8",
		url : $('#modalForm').attr('action'),
		data : locationQtyJsonString,
		success : function(data) {
			// setTotalQtyByUuidAndItemCode();
			setQtyAftrModalClose();
			resetLocation();
		},
		dataType : "json"
	});
	// will set total received qty of selected Item

}
// get And Set Total Qty on receive box of dtl row after set Location
function setTotalQtyByUuidAndItemCode() {
	var uuid = $("#uuid").val();
	var itemCode = $("#hiddenItemCode").val();
	var rowId = $("#hiddenIndex").val();
	var cData = {
		uuid : uuid,
		itemCode : itemCode
	}
	var cDataJsonString = JSON.stringify(cData);
	$.ajax({
		type : "POST",
		contentType : "application/json; charset=utf-8",
		url : $('#contextPath').val()
				+ '/c2ls/rs/getTotalQtyByUuidAndItemCode.do',
		data : cDataJsonString,
		success : function(data) {
			// set updated value in rcv qty
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
						var ledgerNameDiv = mainDiv.getElementsByTagName('div')[0], ledgerNameInput = ledgerNameDiv
								.getElementsByTagName('select')[0];
						ledgerNameInput.setAttribute('id', 'ledgerName'
								+ newNum);

						var locationDiv = mainDiv.getElementsByTagName('div')[1], locationInput = locationDiv
								.getElementsByTagName('select')[0];
						locationInput.setAttribute('id', 'location' + newNum);

						var locationQtyDiv = mainDiv
								.getElementsByTagName('div')[2], locationQtyInput = locationQtyDiv
								.getElementsByTagName('input')[0];
						locationQtyInput.setAttribute('id', 'locationQty'
								+ newNum);

						newEntry.find('input').val('');
						// newEntry.find('select').val('');
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
	var itemCode = $("#dynamicTable").attr("rel");
	var rowId = $("#dynamicTable").attr("data-rowid");
	var cData = {
		uuid : uuid,
		itemCode : itemCode
	}
	var cDataJsonString = JSON.stringify(cData);
	$.ajax({
		type : "POST",
		contentType : "application/json; charset=utf-8",
		url : $('#contextPath').val()
				+ "/c2ls/rs/getTotalQtyByUuidAndItemCode.do",
		data : cDataJsonString,
		success : function(data) {
			// set updated value in rcv qty
			$("#qtyNewServiceable" + rowId).val(data.qtyNewServiceable);
			$("#qtyRecServiceable" + rowId).val(data.qtyRecServiceable);
			$("#qtyUnServiceable" + rowId).val(data.qtyUnServiceable);
			$("#totalReturn" + rowId).val(data.totalReturn);
		},
		dataType : "json"
	});
}

function setQtyAftrModalClose() {
	var uuid = $("#uuid").val();
	var itemCode = $('#hiddenItemCode').val();
	var rowId = $('#hiddenIndex').val();
	// var itemCode =$("#myModal").attr("rel");
	// var rowId = $("#myModal").attr("data-rowid");
	var cData = {
		uuid : uuid,
		itemCode : itemCode
	}
	var cDataJsonString = JSON.stringify(cData);
	$.ajax({
		type : "POST",
		contentType : "application/json; charset=utf-8",
		url : $('#contextPath').val()
				+ "/ls/c2rs/getTotalQtyByUuidAndItemCode.do",
		data : cDataJsonString,
		success : function(data) {
			// set updated value in rcv qty
			$("#qtyNewServiceable" + rowId).val(data.qtyNewServiceable);
			$("#qtyRecServiceable" + rowId).val(data.qtyRecServiceable);
			$("#qtyUnServiceable" + rowId).val(data.qtyUnServiceable);
			$("#totalReturn" + rowId).val(data.totalReturn);
		},
		dataType : "json"
	});
}

// open dialog for location edit
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
				url : $('#contextPath').val() + "/c2ls/rs/getTemplocation.do",
				data : cDataJsonString,
				success : function(data) {
					$('#dynamicTable').append(
							'<table class="col-sm-12 table"></table>');
					$("#dynamicTable").attr("rel", itemCode.toString());
					$("#dynamicTable").attr("data-rowid", rowid);
					var table = $('#dynamicTable').children();
					if (data.length > 0) {
						table
								.append('<tr class="success"><td>Ledger Name</td><td>Location Name</td><td>Quantity</td><td>Update</td><td>Delete</td></tr>');
					} else {
						table
								.append('<tr class="danger center"><td colspan="5">Sorry!!! No Location is set for this item.</td></tr>');
					}

					for (var i = 0; i < data.length; i++) {
						var row = data[i];
						table
								.append('<tr><td><input type="hidden" id="loc'
										+ i
										+ '" value="'
										+ row.ledgerName
										+ '" />'
										+ row.ledgerName
										+ '</td><td><input type="hidden" id="loc'
										+ i
										+ '" value="'
										+ row.locationId
										+ '" />'
										+ row.locationName
										+ '</td><td><input type="number" style="width:70%" id="qty'
										+ i
										+ '" value="'
										+ row.quantity
										+ '" /></td><td><a href="#" class="btn btn-success" onclick="updateLocationQty('
										+ row.id
										+ ','
										+ i
										+ ')"> <i class="glyphicon glyphicon-ok"></i></a></td>'
										+ '<td><a href="#" class="btn btn-danger" onclick="deleteLocationQty('
										+ row.id
										+ ','
										+ i
										+ ')"> <i class="glyphicon glyphicon-remove"></i></a></td>'
										+ '</tr>');
					}

				},
				dataType : "json"
			});
	$("#myDialog").dialog("open");
}

// update location qty
function updateLocationQty(pk, index) {
	var q = $("#qty" + index).val();
	var cData = {
		quantity : q,
		id : pk
	}
	var cDataJsonString = JSON.stringify(cData);

	$.ajax({
		type : "POST",
		contentType : "application/json; charset=utf-8",
		url : $('#contextPath').val() + "/c2ls/rs/updateLocationQty.do",
		data : cDataJsonString,
		success : function(data) {
			alert(data);
		},
		dataType : "json"
	});
}

// delete an row of location
function deleteLocationQty(pk, index) {
	var cData = {
		id : pk
	};
	var cDataJsonString = JSON.stringify(cData);

	$.ajax({
		type : "POST",
		contentType : "application/json; charset=utf-8",
		url : $('#contextPath').val() + "/c2ls/rs/deleteLocationQty.do",
		data : cDataJsonString,
		success : function(data) {
			alert(data);
			loadItemLocationAfterDelete();
		},
		dataType : "json"
	});
}

// reload edit page after delete
function loadItemLocationAfterDelete() {
	var itemCode = $("#dynamicTable").attr("rel");
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
				url : $('#contextPath').val() + "/c2ls/rs/getTemplocation.do",
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
										+ '" /></td><td><a href="#" class="btn btn-success" onclick="updateLocationQty('
										+ row.id
										+ ','
										+ i
										+ ')"> <i class="glyphicon glyphicon-ok"></i></a></td>'
										+ '<td><a href="#" class="btn btn-danger" onclick="deleteLocationQty('
										+ row.id
										+ ','
										+ i
										+ ')"> <i class="glyphicon glyphicon-remove"></i></a></td>'
										+ '</tr>');
					}

				},
				dataType : "json"
			});
}
