/*
 * Author: Ihteshamul Alam
 */

$( document ).ready( function() {
	$('#saveContent').click( function() {
		var haserror = false;
		var counter = 0;
		
		if( $('#receiverName').val() == null || $.trim( $('#receiverName').val() ) == '' ) {
			$('.receiverName').removeClass('hide');
			haserror = true;
		} else {
			$('.receiverName').addClass('hide');
		}
		
		//
		if( $('#deliveryDate').val() == null || $.trim( $('#deliveryDate').val() ) == '' ) {
			$('.deliveryDate').removeClass('hide');
			haserror = true;
		} else {
			$('.deliveryDate').addClass('hide');
		}
		
		if( $('#woId').val() == null || $.trim( $('#woId').val() ) == '' || $.trim( $('#woId').val() ) == '0' ) {
			$('.woId').removeClass('hide');
			haserror = true;
		} else {
			$('.woId').addClass('hide');
		}
		
		$('.receivedQty').each( function() {
			var id = this.id;
			var name = this.name;
			var sequence = id.substr( name.length );
			
			var receivedQty = $('#receivedQty'+sequence).val();
			
			if( receivedQty == null || $.trim( receivedQty ) == '' || $.trim( receivedQty ) == '0' || $.trim( receivedQty ) == '0.0' || $.trim( receivedQty ) == '0.00' || $.trim( receivedQty ) == '0.000' ) {
				$('#errRecdQty'+sequence).removeClass('hide');
				counter++;
			} else {
				$('#errRecdQty'+sequence).addClass('hide');
			}
		});//check allocated delivery qty
		
		if( counter > 0 ) {
			haserror = true;
		}
		
		if( haserror == true ) {
			return;
		} else {
			$('#saveContent').prop('disabled', true);
			$('#saveCondamn').submit();
		}
	});
});

function addRow(gap, index, id) {
	var td1 = '<td>' + gap.itemMaster.itemId
			+ "<input type='hidden' name='id' value='" + gap.id + "'>"
			+ "<input type='hidden' name='itemCode' value='"
			+ gap.itemMaster.itemId + "'>" + '</td>';
	var td2 = "<td id='itemName" + index + "'>" + gap.itemMaster.itemName
			+ "</td>";
	var td3 = '<td>' + gap.auctionQty + '</td>';
	var td4 = "<td>" + gap.remainingQty
			+ "<input type='hidden' id='remainingQty" + index + "' value='"
			+ gap.remainingQty + "' >" + "</td>";

	var td5 = '<td>'
			+ "<input type='number' min='0' step='0.001' class='receivedQty' readonly id='receivedQty"
			+ index
			+ "' name='receivedQty' onblur='validateDeliveredQty()' required='required' style='width: 100%;' />"
			+ "<span class='text-danger hide' id='errRecdQty" + index
			+ "'> <strong>Invalid Field</strong> </span>" + '</td>';

	var td6 = '<td>'
			+ "<button type='button' class='btn btn-primary btn-xs' title='Edit' onclick='openDialoge("
			+ gap.itemMaster.itemId + ", " + index
			+ ")'><i class='fa fa-fw fa-edit'></i></button>" + '</td>';
	var tr = '<tr>' + td1 + td2 + td3 + td4 + td5 + td6 + '</tr>';

	$('#dataList > tbody').append(tr);
	$("#itemCode option[value='" + id + "']").remove();
	$('#itemCode').val("0");
}

function saveMode(id, index) {
	var baseURL = $('#contextPath').val();
	var params = {
		itemCode : id
	}

	var cData = JSON.stringify(params);
	$
			.ajax({
				url : baseURL + "/ac/loadLsItemTnxMstData.do",
				data : cData,
				contentType : "application/json",
				success : function(data) {
					var gap = JSON.parse(data);
					$('#rowId').val(index);
					for ( var i in gap) {
						var td1 = "<td id='khathName"
								+ i
								+ "'>"
								+ gap[i].khathName
								+ "<input class='khathIdList' type='hidden' id='khathIdList"
								+ i + "' name='khathIdList' value='"
								+ gap[i].khathId + "'>" + "</td>";
						var td2 = "<td id='baseQty" + i + "'>"
								+ gap[i].quantity
								+ "<input type='hidden' id='itemCode" + i
								+ "' value='" + id + "'>" + "</td>";

						var td3 = "<td>"
								+ "<input type='number' class='deliveryQtyList' onblur='validateItemStock("
								+ i
								+ ")' min='0.001' step='0.001' id='quantity"
								+ i
								+ "' name='deliveryQtyList'>"
								+ "<br><strong class='hide text-danger' id='errDeliveryQty"
								+ i
								+ "'>Delivery Qty. can't greater than Present Stock.</strong>"
								+ "</td>";
						var td4 = "<td> <button class='btn btn-danger btn-xs' style='border-radius: 6px;' onclick='removeRow("
								+ i
								+ ")'> <i class='fa fa-fw fa-trash-o'></i>&nbsp;Remove</button> </td>";
						var tr = "<tr id='row" + i + "'>" + td1 + td2 + td3
								+ td4 + "</tr>";
						$('#deliverableQty > tbody').append(tr);

						$('#deliveryItemCode').val(id);
					}
				},
				error : function(data) {
					alert("Server Error");
				},
				type : 'POST'
			});
}

function updateMode(id, index) {
	var baseURL = $('#contextPath').val();
	var uuid = $('#uuid').val();
	var params = {
		uuid : uuid,
		itemCode : id
	}
	var cData = JSON.stringify(params);
	$
			.ajax({
				url : baseURL + "/ac/loadLsItemTempLocData.do",
				data : cData,
				contentType : "application/json",
				success : function(data) {
					var gap = JSON.parse(data);

					$('#rowId').val(index);
					var baseQty = $('#remainingQty' + index).val();
					for ( var i in gap) {
						var td1 = "<td id='khathName"
								+ i
								+ "'>"
								+ gap[i].khathName
								+ "<input class='khathIdList' type='hidden' id='khathIdList"
								+ i + "' name='khathIdList' value='"
								+ gap[i].khathId + "'>" + "</td>";
						var td2 = "<td id='baseQty" + i + "'>"
								+ gap[i].quantity
								+ "<input type='hidden' id='itemCode" + i
								+ "' value='" + id + "'>" + "</td>";

						var td3 = "<td>"
								+ "<input type='number' class='deliveryQtyList' onblur='validateItemStock("
								+ i
								+ ")' min='0.001' step='0.001' id='quantity"
								+ i
								+ "' name='deliveryQtyList' value='"
								+ gap[i].deliveredQty
								+ "'>"
								+ "<br><strong class='hide text-danger' id='errDeliveryQty"
								+ i
								+ "'>Delivery Qty. can't greater than Present Stock.</strong>"
								+ "</td>";
						var td4 = "";

						if (gap[i].deliveredQty == 0) {
							td4 = "<td> "
									+ "<button class='btn btn-purple btn-xs' id='remove"
									+ i
									+ "' style='border-radius: 6px;' onclick='removeRow("
									+ i
									+ ")'> <i class='fa fa-fw fa-ban'></i>&nbsp;Remove</button>"
							"</td>";
						} else {
							td4 = "<td> "
									+ "<button class='btn btn-danger btn-xs' id='delete"
									+ i
									+ "' style='border-radius: 6px;' onclick='deleteRow("
									+ i
									+ ","
									+ gap[i].itemCode
									+ ","
									+ baseQty
									+ ")'> <i class='fa fa-fw fa-trash-o'></i>&nbsp;Delete</button>"
							"</td>";
						}

						var tr = "<tr id='row" + i + "'>" + td1 + td2 + td3
								+ td4 + "</tr>";
						$('#deliverableQty > tbody').append(tr);

						$('#deliveryItemCode').val(id);
					}
				},
				error : function(data) {
					alert("Server Error");
				},
				type : 'POST'
			});
}

function openDialoge(id, index) {

	if ($('#receivedQty' + index).val() == ''
			|| $('#receivedQty' + index).val() == null) {
		$('#buttonType').val('save');
		saveMode(id, index);
	} else {
		$('#buttonType').val('update');
		updateMode(id, index);
	}

	var title = "Project wise userviceable qty. of item: "
			+ $('#itemName' + index).text() + " ( " + id + ")";
	$('.ui-dialog-title').text(title);
	$(
			'.ui-button.ui-widget.ui-state-default.ui-corner-all.ui-button-icon-only.ui-dialog-titlebar-close')
			.addClass('hide');

	$("#myDialogeBox").dialog("open");
}

function validateItemStock(index) {
	var currentStock = parseFloat($('#baseQty' + index).text());
	var deliveryQty = parseFloat($('#quantity' + index).val());

	if (deliveryQty > currentStock) {
		$('#errDeliveryQty' + index).removeClass('hide');
		$('#quantity' + index).focus();
	} else {
		$('#errDeliveryQty' + index).addClass('hide');
	}
}

function deleteRow(index, itemCode, parentIndex) {
	if (confirm("Do you want to delete this record from database?") == true) {
		var baseURL = $('#contextPath').val();
		var uuid = $('#uuid').val();
		var khath = $('#khathIdList' + index).val();

		var params = {
			uuid : uuid,
			khathId : khath,
			itemCode : itemCode
		}

		var cData = JSON.stringify(params);

		$.ajax({
			url : baseURL + "/ac/deleteCsItemTempLocData.do",
			data : cData,
			contentType : "application/json",
			success : function(data) {
				var gap = JSON.parse(data);
				if (gap == "success") {

					$('#deliverableQty > tbody > #row' + index).remove();
					// reset delivered qty
					var quantity = 0;
					$('.deliveryQtyList').each(function() {
						if ($(this).val() == null || $(this).val() == '') {
						} else {
							var q = parseFloat($(this).val());
							quantity += q;
						}
					}); // jQuery each
					var rowId = $('#rowId').val();
					$('#receivedQty' + rowId).val(quantity);
				} else {
					alert(gap);
				}
			},
			error : function(data) {
				alert("Server Error");
			},
			type : 'POST'
		});
	}
}

function removeRow(index) {
	if (confirm("Do you want to remove this row?") == true) {
		$('#deliverableQty > tbody > #row' + index).remove();
	}
}

$(function() {
	$("#myDialogeBox")
			.dialog(
					{
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
						width : $(window).width() - 100,
						buttons : [
								{
									text : 'Save',
									click : function() {
										if ($('#buttonType').val() == 'save') {
											var haserror = false;
											var counter = 0, allocate = 0;

											var rId = $('#rowId').val();

											var remainingQty = parseFloat($(
													'#remainingQty' + rId)
													.val());

											$('.khathIdList')
													.each(
															function() {
																var id = this.id;
																var name = this.name;
																var sequence = id
																		.substr(name.length);

																var currentStock = ($(
																		'#baseQty'
																				+ sequence)
																		.text() == "") ? 0
																		: parseFloat($(
																				'#baseQty'
																						+ sequence)
																				.text());
																var deliveryQty = ($(
																		'#quantity'
																				+ sequence)
																		.val() == "") ? 0
																		: parseFloat($(
																				'#quantity'
																						+ sequence)
																				.val());

																allocate += deliveryQty;

																if (deliveryQty == null
																		|| deliveryQty == '0'
																		|| deliveryQty == '0.0'
																		|| deliveryQty == '0.00'
																		|| deliveryQty == '0.000') {
																	counter++;
																	$(
																			'#errDeliveryQty'
																					+ sequence)
																			.removeClass(
																					'hide')
																			.text(
																					"Invalid field");
																} else if (deliveryQty > currentStock) {
																	$(
																			'#errDeliveryQty'
																					+ sequence)
																			.removeClass(
																					'hide')
																			.text(
																					"Delivery Qty. can't greater than Present Stock.");
																	// $('#quantity'+sequence).focus();
																	counter++;
																} else {
																	$(
																			'#errDeliveryQty'
																					+ sequence)
																			.addClass(
																					'hide');
																}
															}); // jQuery each

											if (counter > 0) {
												haserror = true;
											}

											if (allocate > remainingQty) {
												alert("Delivered Qty can not greater than Remaining Qty");
												haserror = true;
											}

											if (haserror == true) {
												return;
											} else {
												var deliveryQty = [];
												var khathId = [];
												var uuid = $('#uuid').val();
												var itemCOde = $(
														'#deliveryItemCode')
														.val();
												var totalQty = 0;

												$('.deliveryQtyList')
														.each(
																function() {
																	deliveryQty
																			.push($(
																					this)
																					.val());

																	var qt = parseFloat($(
																			this)
																			.val());
																	totalQty += qt;
																}); // deliveryQtyList

												$('.khathIdList')
														.each(
																function() {
																	khathId
																			.push($(
																					this)
																					.val());
																}); // khathIdList

												var params = {
													itemCode : itemCOde,
													khathIdList : khathId,
													deliveryQtyList : deliveryQty,
													uuid : uuid
												}

												var cData = JSON
														.stringify(params);
												var baseURL = $('#contextPath')
														.val();
												$
														.ajax({
															url : baseURL
																	+ "/ac/saveDeliveryItem.do",
															data : cData,
															contentType : "application/json",
															success : function(
																	data) {
																var gap = jQuery
																		.parseJSON(data);
																if (gap == 'success') {
																	var rowId = $(
																			'#rowId')
																			.val();
																	$(
																			'#receivedQty'
																					+ rowId)
																			.val(
																					totalQty);
																}
															},
															error : function(
																	data) {
																alert("Server Error");
															},
															type : 'POST'
														}); // ajax
												$(
														'#deliverableQty > tbody > tr')
														.remove();
												$(this).dialog("close");
											}
										} else if ($('#buttonType').val() == 'update') {
											var haserror = false;
											var counter = 0, allocate = 0;

											var rId = $('#rowId').val();

											var remainingQty = parseFloat($(
													'#remainingQty' + rId)
													.val());

											$('.khathIdList')
													.each(
															function() {
																var id = this.id;
																var name = this.name;
																var sequence = id
																		.substr(name.length);

																var currentStock = ($(
																		'#baseQty'
																				+ sequence)
																		.text() == "") ? 0
																		: parseFloat($(
																				'#baseQty'
																						+ sequence)
																				.text());
																var deliveryQty = ($(
																		'#quantity'
																				+ sequence)
																		.val() == "") ? 0
																		: parseFloat($(
																				'#quantity'
																						+ sequence)
																				.val());

																allocate += deliveryQty;

																if (deliveryQty == null
																		|| deliveryQty == '0'
																		|| deliveryQty == '0.0'
																		|| deliveryQty == '0.00'
																		|| deliveryQty == '0.000') {
																	counter++;
																	$(
																			'#errDeliveryQty'
																					+ sequence)
																			.removeClass(
																					'hide')
																			.text(
																					"Invalid field");
																} else if (deliveryQty > currentStock) {
																	$(
																			'#errDeliveryQty'
																					+ sequence)
																			.removeClass(
																					'hide')
																			.text(
																					"Delivery Qty. can't greater than Present Stock.");
																	// $('#quantity'+sequence).focus();
																	counter++;
																} else {
																	$(
																			'#errDeliveryQty'
																					+ sequence)
																			.addClass(
																					'hide');
																}
															}); // jQuery each

											if (counter > 0) {
												haserror = true;
											}

											if (allocate > remainingQty) {
												alert("Delivered Qty can not greater than Remaining Qty");
												haserror = true;
											}

											if (haserror == true) {
												return;
											} else {
												var deliveryQty = [];
												var khathId = [];
												var uuid = $('#uuid').val();
												var itemCOde = $(
														'#deliveryItemCode')
														.val();
												var totalQty = 0;

												$('.deliveryQtyList')
														.each(
																function() {
																	deliveryQty
																			.push($(
																					this)
																					.val());

																	var qt = parseFloat($(
																			this)
																			.val());
																	totalQty += qt;
																}); // deliveryQtyList

												$('.khathIdList')
														.each(
																function() {
																	khathId
																			.push($(
																					this)
																					.val());
																}); // khathIdList

												var params = {
													itemCode : itemCOde,
													khathIdList : khathId,
													deliveryQtyList : deliveryQty,
													uuid : uuid
												}

												var cData = JSON
														.stringify(params);
												var baseURL = $('#contextPath')
														.val();
												$
														.ajax({
															url : baseURL
																	+ "/ac/updateCsItemTempLocData.do",
															data : cData,
															contentType : "application/json",
															success : function(
																	data) {
																var gap = jQuery
																		.parseJSON(data);
																if (gap == 'success') {
																	var rowId = $(
																			'#rowId')
																			.val();
																	$(
																			'#receivedQty'
																					+ rowId)
																			.val(
																					totalQty);
																}
															},
															error : function(
																	data) {
																alert("Server Error");
															},
															type : 'POST'
														}); // ajax

												$(
														'#deliverableQty > tbody > tr')
														.remove();
												$(this).dialog("close");
											}
										}
									}
								},
								{
									text : 'Close',
									click : function() {
										$('#deliverableQty > tbody > tr')
												.remove();
										$(this).dialog("close");
									}
								} ]
					});

});

function loadAuctionItem() {

	$('#workOrderNo').val($('option:selected', '#woId').text());
	$('#mstId').val($('option:selected', '#woId').val());

	// clear itemCode select box and table
	var initOption = "<option value=''> -- Select Item Code -- </option>";
	$('#itemCode > option').remove();
	$('#itemCode').append(initOption);

	// clear 2nd table
	$('#dataList > tbody > tr').remove();
	$('#saveContent').addClass('hide');

	// Load itemCode select box from Db
	var baseURL = $('#contextPath').val();
	var id = $('option:selected', '#woId').val();
	var deptId = $('#deptId').val();
	var params = {
		id : id,
		uom : deptId
	}
	if (id == null || id == '') {
		return;
	}
	var cData = JSON.stringify(params);

	$.ajax({
		url : baseURL + "/ac/loadItemCodebyWoNo.do",
		data : cData,
		contentType : "application/json",
		success : function(data) {
			var gap = JSON.parse(data);

			for ( var ii in gap) {
				var option = "<option value='" + gap[ii].id + "'>"
						+ gap[ii].itemMaster.itemId + " - "
						+ gap[ii].itemMaster.itemName + "</option>";
				$('#itemCode').append(option);
			}
		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
}

function addItem() {
	var id = $('option:selected', '#itemCode').val();
	var baseURL = $('#contextPath').val();
	var params = {
		id : id
	}
	var cData = JSON.stringify(params);

	$.ajax({
		url : baseURL + "/ac/loadItemInfo.do",
		data : cData,
		contentType : "application/json",
		success : function(data) {
			var gap = JSON.parse(data);
			var rowCount = $('#dataList tr').length;
			addRow(gap, rowCount, id);
			$('#saveContent').removeClass('hide');
		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	}); // ajax
}

function validateDeliveredQty() {
	var haserror = false;
	var counter = 0;
	$('.receivedQty')
			.each(
					function() {
						var name = this.name;
						var id = this.id;
						var sequence = id.substr(name.length);
						var requiredQty = $('#receivedQty' + sequence).val() || 0;
						requiredQty = parseFloat(requiredQty);
						var reaminingQty = parseFloat($(
								'#remainingQty' + sequence).val());

						if (requiredQty > reaminingQty) {
							$('#errRecdQty' + sequence)
									.removeClass('hide')
									.find('strong')
									.text(
											'Delivered quantity can\'t greater than Remaining Qty');
							counter++;
						} else {
							$('#errRecdQty' + sequence).addClass('hide');
						}

					});
	if (counter > 0) {
		haserror = true;
		$('#saveContent').attr("disabled", true);
	} else {
		$('#saveContent').removeAttr("disabled");
	}
	return haserror;
}

function checkAndSave() {
	if (haserror == true) {

		return;
	} else {
		$('#saveCondamn').submit();
	}
}
