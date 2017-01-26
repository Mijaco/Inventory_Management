function itemLeaveChange(element) {

	var temp = $(element).closest("div").parent().parent().attr("id");
	var sequence = temp.substr(temp.length - 1);
	var e = $(element).closest("div").parent().parent().find('.itemName').attr(
			'id', sequence);
	var e1 = e[0];
	var item_id = e1.options[e1.selectedIndex].value;
	var contextPath = $("#contextPath").val();
	var khathIdFrom = $("#khathIdFrom").val();
	var ledgerNameFrom = $("#ledgerNameFrom").val();
	// alert(khathIdFrom + ': ' + ledgerNameFrom);
	// I have assigned khathId into categoryId and ledgerName into itemName
	var cData = {
		id : item_id,
		categoryId : khathIdFrom,
		itemName : ledgerNameFrom
	}
	var cDataJsonString = JSON.stringify(cData);
	$.ajax({
		
		url : contextPath + '/cs/khath/viewInventoryItem.do',
		data : cDataJsonString,
		contentType : "application/json",
		success : function(data) {
			var inventoryItem = JSON.parse(data);
			$(element).closest("div").parent().parent().find('.itemCode').val(
					inventoryItem.itemId);
			$(element).closest("div").parent().parent().find('.uom').val(
					inventoryItem.unitCode);
			$(element).closest("div").parent().parent()
					.find('.itemDescription').val(inventoryItem.itemName);
			$(element).closest("div").parent().parent().find('.presentQty')
					.val(inventoryItem.remarks);
			$(element).closest("div").parent().parent().find('.locationDiv')
					.attr("data-itemCode", inventoryItem.itemId);

		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
}

function categoryLeaveChange(element) {
	if (!$('#khathIdFrom').val()) {
		alert('Please select Khath name (From).');
		$('#khathIdFrom').select();
		return false;
	}

	var temp = $(element).closest("div").parent().parent().attr("id");
	var sequence = temp.substr(temp.length - 1)

	$(element).closest("div").parent().parent().find('.category').attr('id',
			sequence);
	var e = document.getElementById('' + sequence);
	var categoryId = e.options[e.selectedIndex].value;

	var contextPath = $("#contextPath").val();

	$.ajax({
		url : contextPath + '/cs/itemRecieved/viewInventoryItemCategory.do',
		data : "{categoryId:" + categoryId + "}",
		contentType : "application/json",
		success : function(data) {
			var itemList = JSON.parse(data);
			var itemNames = $(element).closest("div").parent().parent().find(
					'.itemName');
			itemNames.empty();
			itemNames.append($("<option></option>").attr("value", '').text(
					'Select Item'));
			$.each(itemList, function(id, itemName) {
				itemNames.append($("<option></option>").attr("value", this.id)
						.text(this.itemId + " - " + this.itemName));
			});
		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
}

function getTotalCost(element) {
	var id = $(element).attr('id');
	var sequence = id.substr(id.length - 1);

	var transferedQty = $('#transferedQty' + sequence).val().trim();
	transferedQty = parseFloat(transferedQty).toFixed(2);
	transferedQty = parseFloat(transferedQty);

	var unitCost = $(element).val().trim();
	unitCost = parseFloat(unitCost).toFixed(2);
	unitCost = parseFloat(unitCost);

	$('#totalCost' + sequence).val((unitCost * transferedQty).toFixed(2));

}

function validateTransferQty(element) {
	var id = $(element).attr('id');
	var sequence = id.substr(id.length - 1);

	var currentStock = $('#presentQty' + sequence).val().trim();
	currentStock = parseFloat(currentStock).toFixed(2);
	currentStock = parseFloat(currentStock);

	var temp = $('#transferedQty' + sequence).val().trim();
	var quantityRequired = parseFloat(temp).toFixed(2);
	quantityRequired = parseFloat(quantityRequired);

	if (currentStock < quantityRequired) {
		$(element).val($('#presentQty' + sequence).val());
		$('#remainQty' + sequence).val(0);
	} else {
		$('#remainQty' + sequence).val(currentStock - quantityRequired);
	}

}

function setTotalCost(element) {
	var id = $(element).attr('id');
	var sequence = id.substr(id.length - 1);

	var unitCost = $('#unitCost' + sequence).val().trim();
	var unitCostFloat = parseFloat(unitCost).toFixed(2);

	var qty = $('#quantityRequired' + sequence).val().trim();
	var qtyFloat = parseFloat(qty).toFixed(2);

	var totalCost = parseFloat(unitCostFloat * qtyFloat).toFixed(2);
	$('#totalCost' + sequence).val(totalCost);

}

$(function() {
	$(document)
			.on(
					'click',
					'.btn-add',
					function(e) {
						// e.preventDefault();

						var num = $('.clonedArea').length;
						var newNum = num + 1;

						var controlForm = $('.controls div:first'), currentEntry = $(
								this).parents('.entry:first'), newEntry = $(
								currentEntry.clone().attr('id',
										'myArea' + newNum).addClass(
										'clonedArea')).appendTo(controlForm);

						// set dynamic id on item qty fields

						var mainDiv = document
								.getElementById('myArea' + newNum), childDiv = mainDiv
								.getElementsByTagName('div')[0];

						// start of seting id on expectedQty fields
						var expectedQtyDiv = childDiv
								.getElementsByTagName('div')[4], expectedQtyInput = expectedQtyDiv
								.getElementsByTagName('input')[0];
						expectedQtyInput.setAttribute('id', 'presentQty'
								+ newNum);
						// end of seting id on expectedQty fields

						// start of seting id on receivedQty fields
						var receivedQtyDiv = childDiv
								.getElementsByTagName('div')[5], receivedQtyInput = receivedQtyDiv
								.getElementsByTagName('input')[0];
						receivedQtyInput.setAttribute('id', 'transferedQty'
								+ newNum);
						
						var errorMsgDiv = childDiv
						.getElementsByTagName('div')[5], errorMsgInput = errorMsgDiv
						.getElementsByTagName('strong')[0];
						errorMsgInput.setAttribute('id', 'errorMsg'
						+ newNum);
						// end of seting id on receivedQty fields

						var locationDiv = childDiv.getElementsByTagName('div')[6], anchor0 = locationDiv
								.getElementsByTagName('a')[0];
						anchor0.setAttribute('id', 'setDialog' + newNum);

						// start of seting id on remainingQty fields
						var remainingQtyDiv = childDiv
								.getElementsByTagName('div')[7], remainingQtyInput = remainingQtyDiv
								.getElementsByTagName('input')[0];
						remainingQtyInput.setAttribute('id', 'remainQty'
								+ newNum);
						// end of seting id on remainingQty fields

						// start of seting id on unitCost fields
						var unitCostDiv = childDiv.getElementsByTagName('div')[8], unitCostInput = unitCostDiv
								.getElementsByTagName('input')[0];
						unitCostInput.setAttribute('id', 'unitCost' + newNum);
						// end of seting id on unitCost fields

						// start of seting id on totalCost fields
						var totalCostDiv = childDiv.getElementsByTagName('div')[9], totalCostInput = totalCostDiv
								.getElementsByTagName('input')[0];
						totalCostInput.setAttribute('id', 'totalCost' + newNum);
						// end of seting id on totalCost fields

						newEntry.find('input').val('');

						/*controlForm
								.find('.entry:not(:last) .btn-add')
								.removeClass('btn-add')
								.addClass('btn-remove')
								.removeClass('btn-success')
								.addClass('btn-danger')
								.html(
										'<span class="glyphicon glyphicon-minus"></span>');*/
					}).on('click', '.btn-remove', function(e) {
				$(this).parents('.entry:first').remove();

				// e.preventDefault();
				return false;
			});

});

// location dialog is set hidden
$(function() {
	$("#myDialog1").dialog({
		autoOpen : false,
		closeOnEscape : false,
		modal : true,
		draggable : false,
		resizable : false,
		position : 'center',
		show : 'blind',
		hide : 'blind',
		width : 800,
		buttons : {
			"Close" : function() {
				// getTotalQtyByUuidAndItemCode();
				$(this).dialog("close");
				$('#dynamicTable1').empty();
			}
		}
	});

});
function openDialog1(element) {
	var op1 = '';
	for (var i = 0; i < locationList.length; i++) {
		op1 = op1 + '<option value="' + locationList[i].id + '">'
				+ locationList[i].name + '</option>'
	}

	var id = $(element).attr("id");
	var sequence = id.substr(id.length - 1);
	var itemCode = $(element).closest("div").attr("data-itemCode");
	$('#dynamicTable1').append('<table class="col-sm-12 table"></table>');
	$("#dynamicTable1").attr("rel", itemCode.toString());
	$("#dynamicTable1").attr("data-rowid", sequence);

	var table = $('#dynamicTable1').children();
	table
			.append('<tr class="success"><td>Location Name</td><td>Item Quantity</td><td>Update</td><td>Delete</td></tr>');
	table
			.append('<tr><td><select class="form-control" name="" onchange="location1Change(this)" id="" style="border: 0; border-bottom: 2px ridge;">'
					+ op1
					+ '</select>'
					+ '</td><td><input type="text" id="qty" value="'
					+ itemCode
					+ '" /></td><td><a href="#" class="btn btn-success" onclick="updateLocationQty(0,0)"> <i class="glyphicon glyphicon-ok"></i></a></td>'
					+ '<td><a href="#" class="btn btn-danger" onclick="deleteLocationQty(0,0)"> <i class="glyphicon glyphicon-remove"></i></a></td>'
					+ '</tr>');

	$("#myDialog1").dialog("open");
}
// append Grid Code block

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
									ctrlOptions :$("#locationList").val(),
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
									display : 'Rake',
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
										$("#transferedQty"+r).val(sData.toFixed(2));
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
	var cData={
			uuid:$("#uuid").val(),
			itemCode:itemCode
	}	
	$.ajax({
		url : $("#contextPath").val() + '/cs/khath/loadDataFromTemp.do',
		data : JSON.stringify(cData),
		contentType : "application/json",
		success : function(data) {
			var cData=JSON.parse(data);
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



$("#khathIdTo").val($("#khathIdTo option:last()").val());