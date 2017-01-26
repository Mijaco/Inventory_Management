/*// location edit dialog is set hidden
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
				// location.reload(true);
			}
		}
	});

});


function getTotalQtyByUuidAndItemCode() {
	var uuid = $("#uuid").val();
	var itemCode =$("#dynamicTable").attr("rel");
	// var rowId = $("#dynamicTable").attr("data-rowid");
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
				+ "/cs/itemRecieved/getTotalQtyByUuidAndItemCode.do",
		data : cDataJsonString,
		success : function(data) {
			//Now updated RR Dtl qty for that item
			var rrNo=$("#recieviedReportNo").val();
			updateRRdtl(rrNo, itemCode, data);	
			location.reload(true);
		},
		dataType : "json"
	});	
}

function updateRRdtl(rrNo, itemCode, Qty){
	var cData = {
			rrNo : rrNo,
			itemId : itemCode,
			receivedQty:Qty
		}
	
	var cDataJsonString = JSON.stringify(cData);
	$
	.ajax({
		type : "POST",
		contentType : "application/json; charset=utf-8",
		url : $('#contextPath').val()
				+ "/cs/itemRecieved/updateRRdtlAfterLocatinUpdate.do",
		data : cDataJsonString,
		success : function(data) {
		//	console.log(data);		
		},
		dataType : "json"
	});
}
// open dialog for location edit
function editItemLocation(itemCode) {
	
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
						+ "/cs/itemRecieved/getTemplocation.do",
				data : cDataJsonString,
				success : function(data) {
					$('#dynamicTable').append(
							'<table class="col-sm-12 table"></table>');
					$("#dynamicTable").attr("rel", itemCode.toString());
					// $("#dynamicTable").attr("data-rowid", rowid);
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
				+ "/cs/itemRecieved/updateLocationQty.do",
		data : cDataJsonString,
		success : function(data) {
			alert(data);
		},
		dataType : "json"
	});
}

// delete an row of location
function deleteLocationQty(pk, index) {
	var cData = {id : pk};
	var cDataJsonString = JSON.stringify(cData);
	
	$.ajax({
		type : "POST",
		contentType : "application/json; charset=utf-8",
		url : $('#contextPath').val()
				+ "/cs/itemRecieved/deleteLocationQty.do",
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
						+ "/cs/itemRecieved/getTemplocation.do",
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

//

function editItem(id) {

	$.ajax({
		url : $('#contextPath').val()+'/cs/itemRecieved/itemEdit.do',
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
*/
function receivedQtyToRemainQty(element) {

	var expected = parseInt($('#modal_expectedQuantity').val().trim(), 10);

	var received = parseInt($(element).val().trim(), 10);

	if (received > expected) {
		$(element).val($('#modal_expectedQuantity').val());
	}

	$('#modal_reminingQuantity').val(
			$('#modal_expectedQuantity').val()
					- $('#modal_receivedQuantity').val());

}

function forwardToUpper(stateCode) {
	if( $('#justification').val() == null || $.trim( $('#justification').val() ) == '' ) {
		$('.justification').removeClass('hide');
		$('#justification').focus();
		return;
	} else {
		var justification = $('#justification').val();
		var recieviedReportNo = $('#transferNo').val();

		// window.location =
		// "${pageContext.request.contextPath}/cs/itemRecieved/itemReceivedForwordTo.do?receivedReportNo="+param1+"$justification="+justification;
		window.location = $('#contextPath').val()+"/cs/khath/kTSendTo.do?receivedReportNo="
				+ recieviedReportNo
				+ "&justification="
				+ justification
				+ "&stateCode=" + stateCode;
	}
}

function backToLower(stateCode) {
	if( $('#justification').val() == null || $.trim( $('#justification').val() ) == '' ) {
		$('.justification').removeClass('hide');
		$('#justification').focus();
		return;
	} else {
		var justification = $('#justification').val();
		var recieviedReportNo = $('#recieviedReportNo').val();

		// window.location =
		// "${pageContext.request.contextPath}/cs/itemRecieved/itemReceivedForwordTo.do?receivedReportNo="+param1+"$justification="+justification;
		window.location = $('#contextPath').val()+"/cs/itemRecieved/itemReceivedBackTo.do?receivedReportNo="
				+ recieviedReportNo
				+ "&justification="
				+ justification
				+ "&stateCode=" + stateCode;
	}
}

function appoveKhathTransfer() {
	if( $('#justification').val() == null || $.trim( $('#justification').val() ) == '' ) {
		$('.justification').removeClass('hide');
		$('#justification').focus();
		return;
	} else {
		var justification = $('#justification').val();
		var transferNo = $('#transferNo').val();

		var returnStateCode = $('#returnStateCode').val();

		window.location = $('#contextPath').val()+"/cs/khath/ktApproved.do?receivedReportNo="
				+ transferNo
				+ "&justification="
				+ justification
				+ "&return_state=" + returnStateCode;
	}
}


///

//append Grid Code block

$(function() {
	$("#myGrid").dialog({
		autoOpen : false,
		closeOnEscape : false,
		modal : true,
		draggable : false,
		resizable : false,
		position : { my: "center", at: "center", of: window },
		show :{ effect: "blind", duration: 10 },
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
						caption : 'Dynamic Location Setup DESCO Store',
						initRows : 1,
						columns : [
								{
									name : 'id',
									display : 'primaryKey',
									type:'hidden',
									value : 0									
								},
								{	name : 'ledgerName',
									display : 'Ledger Name',
									ctrlAttr: { 'readonly': 'readonly' },
									value : $("#ledgerNameFrom").val()									
								},
								{
									name : 'location',
									display : 'Location',
									type : 'select',
									ctrlOptions :locationList,
									//ctrlOptions : data.locationOptions,
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
									//,onChange : handleChange
								},
								{
									name : 'quantity',
									display : 'Quantity',
									type : 'number',
									ctrlAttr : {title : 'Please input number upto 3 decimal place'},
									uiTooltip : {show : true},
									value : 0,									
									emptyCriteria : function(value) {
										return (value <= 0);
									}								
								} ],
						beforeRowRemove : function(caller, rowIndex) {
							var flag= confirm('Are you sure to remove this row: '+rowIndex+'?');
							var rowData= $('#tblAppendGrid').appendGrid('getRowValue', rowIndex);
							if(flag){
								if(rowData.id=="0"){
									alert('Row: '+rowIndex+' successfully removed from Grid.');
								}else{
									$.ajax({
										url : $("#contextPath").val() + '/cs/khath/deleteRowFromTempLocation.do',
										data :  "{id:" + rowData.id + "}",
										contentType : "application/json",
										success : function(data) {
											var sData = JSON.parse(data);
											if(sData=="success"){
												alert('Row: '+rowIndex+' successfully removed from DB.');
											}
										},
										error : function(data) {alert("Server Error");},
										type : 'POST'
									});
									
								}
							}							
							return flag;
						},						
						 afterRowAppended: function (caller, parentRowIndex, id) {	
							 var i=parentRowIndex==null ? 0 : parentRowIndex+1;
							 var r = $('#tblAppendGrid').appendGrid('getUniqueIndex', i);
							 if(i>0 && data.initData.length>0){
								$("#tblAppendGrid_godown_" + r).empty();
								$("#tblAppendGrid_floor_" + r).empty();
								$("#tblAppendGrid_sector_" + r).empty();
								$("#tblAppendGrid_rake_" + r).empty();
								$("#tblAppendGrid_bin_" +r).empty();
							 }							
					    },
					    hideButtons: {insert: true },
						initData: data.initData,
						customFooterButtons : [ {
							uiButton : {
								icons : {primary : 'ui-icon-refresh'},
								label : 'Save or Update All'
							},
							btnCss : {'color' : '#222'},
							btnAttr : {	'id' : 'saveAll'	},
							click : function(evt) {
								var count = $('#tblAppendGrid').appendGrid('getRowCount'); 
								var realData=[];
								for (var z = 0; z < count; z++) {
									if (!$('#tblAppendGrid').appendGrid('isRowEmpty', z)) {
										realData.push( $('#tblAppendGrid').appendGrid('getRowValue', z));										
									}
								}
								//save to db								
								var cData={
										uuid:$("#uuid").val(),
										itemCode: $("#tblAppendGrid").attr("data-itemcode"),
										ledgerName:$("#ledgerNameFrom").val(),
										locationList:realData
								}	
								var cDataJsonString = JSON.stringify(cData);
								$.ajax({
									url : $("#contextPath").val() + '/cs/khath/saveLocation7sDtl.do',
									data : cDataJsonString,
									contentType : "application/json",
									success : function(data) {
										var sData = JSON.parse(data);
										//alert(sData.toFixed(2));
										var r=$("#tblAppendGrid").attr("data-rowid");
										$("#transferedQty"+r).text(sData.toFixed(3));
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
	var cData={	uuid:$("#uuid").val(),itemCode:itemCode }	
	$.ajax({
		url : $("#contextPath").val() + '/cs/khath/loadDataFromTemp.do',
		data : JSON.stringify(cData),
		contentType : "application/json",
		success : function(data) {
			var cData=JSON.parse(data);
			//alert(initData);			
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
