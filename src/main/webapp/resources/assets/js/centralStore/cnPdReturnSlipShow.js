function forwardToUpper(stateCode) {
	if( $('#justification').val() == null || $.trim( $('#justification').val() ) == '' ) {
		$('.justification').removeClass('hide');
		$('#justification').focus();
		return;
	} else {
		var contextPath=$("#contextPath").val();
		var justification = $('#justification').val();
		var returnSlipNo = $('#returnSlipNo').val();
		var returnTo = $('#returnTo').val();
		
		window.location = $("#contextPath").val()
				+ "/cn/pdReturnSlip/sendTo.do?returnSlipNo=" + returnSlipNo
				+ "&justification=" + justification + "&stateCode=" + stateCode+"&returnTo="+returnTo;
	}

}

function backToLower(stateCode) {
	if( $('#justification').val() == null || $.trim( $('#justification').val() ) == '' ) {
		$('.justification').removeClass('hide');
		$('#justification').focus();
		return;
	} else {
		var contextPath=$("#contextPath").val();
		var justification = $('#justification').val();
		var returnSlipNo = $('#returnSlipNo').val();
		var returnTo = $('#returnTo').val();

		window.location = $("#contextPath").val()
				+ "/cn/returnSlip/backTo.do?returnSlipNo=" + returnSlipNo
				+ "&justification=" + justification + "&stateCode=" + stateCode+"&returnTo="+returnTo;
	}
}

function approveCnPdReturnSlip() {
	if( $('#justification').val() == null || $.trim( $('#justification').val() ) == '' ) {
		$('.justification').removeClass('hide');
		$('#justification').focus();
		return;
	} else {
		$('#approveButton').prop('disabled', true);
		var contextPath=$("#contextPath").val();
		var justification = $('#justification').val();
		var returnSlipNo = $('#returnSlipNo').val();
		var returnStateCode = $('#returnStateCode').val();
		var returnTo = $('#returnTo').val();
		
		var path = $("#contextPath").val() + '/cn/pd/itemReturnSlipApproved.do';

		var params = {
			returnSlipNo : returnSlipNo,
			justification : justification,
			return_state : returnStateCode,
			returnTo : returnTo
		}
		postSubmit(path, params, 'POST');
	}
}

function editItem(id) {

	$.ajax({
		url : $("#contextPath").val() + '/cs/itemRecieved/itemEdit.do',
		data : "{id:" + id + "}",
		contentType : "application/json",
		success : function(data) {
			var item = JSON.parse(data);
			$("#modal_itemId").val(item.itemId);
			$("#modal_description").val(item.description);
			$("#modal_expectedQuantity").val(item.expectedQty);
			$("#modal_id").val(item.id);
			$("#modal_receivedQuantity").val(item.receivedQty);
			$("#modal_reminingQuantity").val(item.remainingQty);

		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
}

// Start Codes for Location Grid

// append Grid Code block

$(function() {
	$("#myGrid").dialog({
		title: "Dynamic Location Setup DESCO Store",
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
		width : 1000,
		buttons : {
			"Close" : function() {
				// getTotalQtyByUuidAndItemCode();
				$(this).dialog("close");
				$('#tblAppendGrid').empty();
			}
		}
	});

});

function myGrid(data) {
	$('#tblAppendGrid')
			.appendGrid(
					{
						// caption : '',
						initRows : 1,
						columns : [
								{
									name : 'id',
									display : 'primaryKey',
									type : 'hidden',
									value : 0
								},
								{
									name : 'ledgerName',
									display : 'Ledger Name',
									type : 'select',
									ctrlOptions : $('#ledgerBookList').val()
								},
								{
									name : 'location',
									display : 'Location',
									type : 'select',
									ctrlOptions : $('#locationList').val(),
									onChange : handleChangeLocation
								},
								{
									name : 'godown',
									display : 'Godown',
									ctrlOptions : data.godownOptions,
									type : 'select',
									onChange : handleChangeGodown
								},
								{
									name : 'floor',
									display : 'Floor',
									ctrlOptions : data.floorOptions,
									type : 'select',
									onChange : handleChangeFloor
								},
								{
									name : 'sector',
									display : 'Block',
									ctrlOptions : data.sectorOptions,
									type : 'select',
									onChange : handleChangeSector
								},
								{
									name : 'rake',
									display : 'Rack',
									ctrlOptions : data.rakeOptions,
									type : 'select',
									onChange : handleChangeRake
								},
								{
									name : 'bin',
									display : 'Bin',
									ctrlOptions : data.binOptions,
									type : 'select'
								// ,onChange : handleChange
								},
								{
									name : 'quantity',
									display : 'Quantity',
									type : 'number',
									ctrlAttr : {
										title : 'Please input number upto 3 decimal place'
									},
									uiTooltip : {
										show : true
									},
									value : 0,
									emptyCriteria : function(value) {
										return (value <= 0);
									}
								} ],
						beforeRowRemove : function(caller, rowIndex) {
							var flag = confirm('Are you sure to remove this row: '
									+ rowIndex + '?');
							var rowData = $('#tblAppendGrid').appendGrid(
									'getRowValue', rowIndex);
							if (flag) {
								if (rowData.id == "0") {
									alert('Row: '
											+ rowIndex
											+ ' successfully removed from Grid.');
								} else {
									$
											.ajax({
												url : $("#contextPath").val()
														+ '/cs/khath/deleteRowFromTempLocation.do',
												data : "{id:" + rowData.id
														+ "}",
												contentType : "application/json",
												success : function(data) {
													var sData = JSON
															.parse(data);
													if (sData == "success") {
														alert('Row: '
																+ rowIndex
																+ ' successfully removed from DB.');
													}
												},
												error : function(data) {
													alert("Server Error");
												},
												type : 'POST'
											});

								}
							}
							return flag;
						},
						afterRowAppended : function(caller, parentRowIndex, id) {
							var i = parentRowIndex == null ? 0
									: parentRowIndex + 1;
							var r = $('#tblAppendGrid').appendGrid(
									'getUniqueIndex', i);
							if (i > 0 && data.initData.length > 0) {
								$("#tblAppendGrid_godown_" + r).empty();
								$("#tblAppendGrid_floor_" + r).empty();
								$("#tblAppendGrid_sector_" + r).empty();
								$("#tblAppendGrid_rake_" + r).empty();
								$("#tblAppendGrid_bin_" + r).empty();
							}
						},
						hideButtons : {
							insert : true
						},
						initData : data.initData,
						customFooterButtons : [ {
							uiButton : {
								icons : {
									primary : 'ui-icon-refresh'
								},
								label : 'Save or Update All'
							},
							btnCss : {
								'color' : '#222'
							},
							btnAttr : {
								'id' : 'saveAll'
							},
							click : function(evt) {
								var count = $('#tblAppendGrid').appendGrid(
										'getRowCount');
								var realData = [];
								for (var z = 0; z < count; z++) {
									if (!$('#tblAppendGrid').appendGrid(
											'isRowEmpty', z)) {
										realData.push($('#tblAppendGrid')
												.appendGrid('getRowValue', z));
									}
								}

								var rowId = $("#tblAppendGrid").attr(
										"data-rowid");
								// save to db
								var cData = {
									uuid : $("#uuid").val(),
									itemCode : $("#tblAppendGrid").attr(
											"data-itemcode"),
									ledgerName : $("#dtlId" + rowId).val(),
									locationList : realData
								}
								var cDataJsonString = JSON.stringify(cData);
								$.ajax({
									url : $("#contextPath").val()
											+ '/ls/cs/rt/saveLocation7sDtl.do',
									data : cDataJsonString,
									contentType : "application/json",
									success : function(data) {
										var sData = JSON.parse(data);
										// alert(sData.toFixed(3));
										var r = $("#tblAppendGrid").attr(
												"data-rowid");
										$("#qtyNewServiceable" + r).text(
												sData.qtyNewServiceableRcv
														.toFixed(3));
										$("#qtyRecServiceable" + r).text(
												sData.qtyRecServiceableRcv
														.toFixed(3));
										$("#qtyUnServiceable" + r).text(
												sData.qtyUnServiceableRcv
														.toFixed(3));
										$("#totalReturn" + r)
												.text(
														sData.totalReturnRcv
																.toFixed(3));
										$("#myGrid").dialog("close");
										$('#tblAppendGrid').empty();
									},
									error : function(data) {
										alert("Server Error");
									},
									type : 'POST'
								});
								//
							},
							atTheFront : false
						} ]
					});
}

function openGridDialog(element) {
	var id = $(element).parent().attr("id");
	var sequence = id.substr(id.length - 1);
	var itemCode = $(element).closest("div").attr("data-itemCode");
	$("#tblAppendGrid").attr("data-itemCode", itemCode.toString());
	$("#tblAppendGrid").attr("data-rowid", sequence);
	var cData = {
		uuid : $("#uuid").val(),
		itemCode : itemCode
	}
	$.ajax({
		url : $("#contextPath").val() + '/cs/khath/loadDataFromTemp.do',
		data : JSON.stringify(cData),
		contentType : "application/json",
		success : function(data) {
			var cData = JSON.parse(data);
			// alert(initData);
			myGrid(cData);
			$("#myGrid").dialog("open");
		},
		error : function(data) {
			alert("Unable load data from temp location");
		},
		type : 'POST'
	});
}

function handleChange(evt, rowIndex) {
	alert('Selected Value = ' + rowIndex + ': ' + evt.target.value);
}

// Get Godown List
function handleChangeLocation(evt, rowIndex) {
	var locationId = evt.target.value;
	var row = rowIndex + 1;
	// getGodawnByLocationId
	$.ajax({
		url : $("#contextPath").val() + '/cs/khath/getLocationByParentId.do',
		data : "{id:" + locationId + ", storeLevel: 2}",
		contentType : "application/json",
		success : function(data) {
			var godownList = JSON.parse(data);
			$("#tblAppendGrid_godown_" + row).empty();
			var godownOptions = "";
			for (var i = 0; i < godownList.length; i++) {
				if (i == 0) {
					var option = document.createElement("option");
					option.setAttribute("value", null);
					option.text = 'Select Godown';
					$("#tblAppendGrid_godown_" + row).append(option);
				}
				var option = document.createElement("option");
				option.setAttribute("value", godownList[i].id);
				option.text = godownList[i].name;
				$("#tblAppendGrid_godown_" + row).append(option);
			}
		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
}
// Get Floor List
function handleChangeGodown(evt, rowIndex) {
	var locationId = evt.target.value;
	var row = rowIndex + 1;
	$.ajax({
		url : $("#contextPath").val() + '/cs/khath/getLocationByParentId.do',
		data : "{id:" + locationId + ", storeLevel: 3}",
		contentType : "application/json",
		success : function(data) {
			var floorList = JSON.parse(data);
			$("#tblAppendGrid_floor_" + row).empty();
			var godownOptions = "";
			for (var i = 0; i < floorList.length; i++) {
				if (i == 0) {
					var option = document.createElement("option");
					option.setAttribute("value", null);
					option.text = 'Select Floor';
					$("#tblAppendGrid_floor_" + row).append(option);
				}
				var option = document.createElement("option");
				option.setAttribute("value", floorList[i].id);
				option.text = floorList[i].name;
				$("#tblAppendGrid_floor_" + row).append(option);
			}
		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
}

// Get Sector List
function handleChangeFloor(evt, rowIndex) {
	var locationId = evt.target.value;
	var row = rowIndex + 1;
	$.ajax({
		url : $("#contextPath").val() + '/cs/khath/getLocationByParentId.do',
		data : "{id:" + locationId + ", storeLevel: 4}",
		contentType : "application/json",
		success : function(data) {
			var floorList = JSON.parse(data);
			$("#tblAppendGrid_sector_" + row).empty();
			var godownOptions = "";
			for (var i = 0; i < floorList.length; i++) {
				if (i == 0) {
					var option = document.createElement("option");
					option.setAttribute("value", null);
					option.text = 'Select Sector';
					$("#tblAppendGrid_sector_" + row).append(option);
				}
				var option = document.createElement("option");
				option.setAttribute("value", floorList[i].id);
				option.text = floorList[i].name;
				$("#tblAppendGrid_sector_" + row).append(option);
			}
		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
}

// Get Rake List
function handleChangeSector(evt, rowIndex) {
	var locationId = evt.target.value;
	var row = rowIndex + 1;
	$.ajax({
		url : $("#contextPath").val() + '/cs/khath/getLocationByParentId.do',
		data : "{id:" + locationId + ", storeLevel: 5}",
		contentType : "application/json",
		success : function(data) {
			var floorList = JSON.parse(data);
			$("#tblAppendGrid_rake_" + row).empty();
			var godownOptions = "";
			for (var i = 0; i < floorList.length; i++) {
				if (i == 0) {
					var option = document.createElement("option");
					option.setAttribute("value", null);
					option.text = 'Select Rake';
					$("#tblAppendGrid_rake_" + row).append(option);
				}
				var option = document.createElement("option");
				option.setAttribute("value", floorList[i].id);
				option.text = floorList[i].name;
				$("#tblAppendGrid_rake_" + row).append(option);
			}
		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
}

// Get Bin List
function handleChangeRake(evt, rowIndex) {
	var locationId = evt.target.value;
	var row = rowIndex + 1;
	$.ajax({
		url : $("#contextPath").val() + '/cs/khath/getLocationByParentId.do',
		data : "{id:" + locationId + ", storeLevel: 6}",
		contentType : "application/json",
		success : function(data) {
			var floorList = JSON.parse(data);
			$("#tblAppendGrid_bin_" + row).empty();
			var godownOptions = "";
			for (var i = 0; i < floorList.length; i++) {
				if (i == 0) {
					var option = document.createElement("option");
					option.setAttribute("value", null);
					option.text = 'Select Bin';
					$("#tblAppendGrid_bin_" + row).append(option);
				}
				var option = document.createElement("option");
				option.setAttribute("value", floorList[i].id);
				option.text = floorList[i].name;
				$("#tblAppendGrid_bin_" + row).append(option);
			}
		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
}

// End Codes for Location Grid
