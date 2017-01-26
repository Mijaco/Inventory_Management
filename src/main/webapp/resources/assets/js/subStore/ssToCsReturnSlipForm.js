function itemLeaveChange(element) {

	var temp = $(element).closest("div").parent().parent().attr("id");
	var sequence = temp.substr(temp.length - 1);

	var e = $(element).closest("div").parent().parent().find('.itemName').attr(
			'id', sequence);
	// var e = document.getElementById('' + sequence);
	var e1 = e[0];
	var item_id = e1.options[e1.selectedIndex].value;
	var khathId = $('option:selected','#khathId').val();
	var mainData = {
		itemCode : item_id,
		khathId : khathId
	}
	var cData = JSON.stringify(mainData);
	$.ajax({
		url : 'ssReturnSlipViewInventoryItem.do',
		data : cData,
		contentType : "application/json",
		success : function(data) {
			var inventoryItem = JSON.parse(data);
			$(element).closest("div").parent().parent().find('.itemCode').val(
					inventoryItem.itemCode);
			$(element).closest("div").parent().parent().find('.uom').val(
					inventoryItem.uom);
			$(element).closest("div").parent().parent().find('.description')
					.val(inventoryItem.itemName);
			$(element).closest("div").parent().parent().find('.nscurrentStock')
			  .val(inventoryItem.nsStockQuantity);
			$(element).closest("div").parent().parent().find('.rscurrentStock')
			.val(inventoryItem.rsStockQuantity);
			
			$(element).closest("div").parent().parent().find('.action-buttons')
					.attr("data-itemcode", inventoryItem.itemCode);
			/*
			 * $(element).closest("div").parent().parent().find('.currentStock')
			 * .val(inventoryItem.currentStock);
			 */
		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
}

function categoryLeaveChange(element) {

	var temp = $(element).closest("div").parent().parent().attr("id");
	var sequence = temp.substr(temp.length - 1)

	$(element).closest("div").parent().parent().find('.category').attr('id',
			sequence);
	var e = document.getElementById('' + sequence);
	var categoryId = e.options[e.selectedIndex].value;
	// alert(categoryId);
	$.ajax({

		url : 'ssReturnViewInventoryItemCategory.do',
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
				itemNames.append($("<option></option>").attr("value", this.itemId)
						.text(this.itemId+' - '+this.itemName));
			});
		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
}
/*
function reqQtyNotGreaterThenCurrentStock(element) {
	var id = $(element).attr('id');
	var sequence = id.substr(id.length - 1);

	// var expected = parseInt($('#currentStock' + sequence).val().trim(), 10);

	var currentStock = parseFloat($('#currentStock' + sequence).val().trim())
			.toFixed(2);
	currentStock = parseFloat(currentStock);

	var temp = $('#quantityRequired' + sequence).val().trim();
	var quantityRequired = parseFloat(temp).toFixed(2);
	quantityRequired = parseFloat(quantityRequired);

	// alert(currentStock);

	if (currentStock < quantityRequired) {
		$(element).val($('#currentStock' + sequence).val());
	}

}

*/

function setTotalReturn(element) {
	var id = $(element).attr('id');
	var sequence = id.substr(id.length - 1);

	var nsQty = $('#qtyNewServiceable' + sequence).val().trim();
	var nsQtyFloat = parseFloat(nsQty).toFixed(3);

	var rsQty = $('#qtyRecServiceable' + sequence).val().trim();
	var rsQtyFloat = parseFloat(rsQty).toFixed(3);
	
	var usQty = $('#qtyUnServiceable' + sequence).val().trim();
	var usQtyFloat = parseFloat(usQty).toFixed(3);
	
	var x = parseFloat(nsQtyFloat);
	var y = parseFloat(rsQtyFloat);
	var z = parseFloat(usQtyFloat);

	var totalReturnQty = (x + y + z).toFixed(3);
	$('#totalReturn' + sequence).val(totalReturnQty);

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

						// start of setting id on location Grid 
						/*
						 * 
						var locationDiv = childDiv.getElementsByTagName('div')[4], locationDivAnchor = locationDiv
								.getElementsByTagName('a')[0];
						locationDivAnchor.setAttribute('id', 'setDialog'
								+ newNum);
								
						*/
						// end of setting id on location Grid 
							
						// start of setting id on nscurrentStock fields
						var nscurrentStockDiv = childDiv.getElementsByTagName('div')[4], nscurrentStockInput = nscurrentStockDiv
								.getElementsByTagName('input')[0];
						nscurrentStockInput.setAttribute('id', 'nscurrentStock' + newNum);
						
						// start of setting id on rscurrentStock fields
						var rscurrentStockDiv = childDiv.getElementsByTagName('div')[5], rscurrentStockInput = rscurrentStockDiv
								.getElementsByTagName('input')[0];
						rscurrentStockInput.setAttribute('id', 'rscurrentStock' + newNum);
						
						// start of setting id on qtyNewServiceable fields
						var quantityRequiredDiv = childDiv
								.getElementsByTagName('div')[6], quantityRequiredInput = quantityRequiredDiv
								.getElementsByTagName('input')[0];
						quantityRequiredInput.setAttribute('id',
								'qtyNewServiceable' + newNum);
						// end of setting id on qtyNewServiceable fields

						// start of setting id on qtyRecServiceable fields
						var quantityIssuedDiv = childDiv
								.getElementsByTagName('div')[7], quantityIssuedInput = quantityIssuedDiv
								.getElementsByTagName('input')[0];
						quantityIssuedInput.setAttribute('id', 'qtyRecServiceable'+ newNum);
						// end of setting id on qtyRecServiceable fields

						// start of setting id on qtyUnServiceable fields
						var unitCostDiv = childDiv.getElementsByTagName('div')[8], unitCostInput = unitCostDiv
								.getElementsByTagName('input')[0];
						unitCostInput.setAttribute('id', 'qtyUnServiceable' + newNum);
						// end of seting id on qtyUnServiceable fields

						// start of seting id on totalReturn fields
						var totalCostDiv = childDiv.getElementsByTagName('div')[9], totalCostInput = totalCostDiv
								.getElementsByTagName('input')[0];
						totalCostInput.setAttribute('id', 'totalReturn' + newNum);
						// end of seting id on totalReturn fields
						
						//errTotRet
						var errTotRetDiv = childDiv.getElementsByTagName('div')[9], errTotRetInput = totalCostDiv
						.getElementsByTagName('strong')[0];
						errTotRetInput.setAttribute('id', 'errTotRet' + newNum);

						newEntry.find('input').val('');
						newEntry.find('.qtyNewServiceable').val(0);
						newEntry.find('.qtyRecServiceable').val(0);
						newEntry.find('.qtyUnServiceable').val(0);
						newEntry.find('.totalReturn').val(0);

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
$(document).ready(function() {
	$(function() {

		$("#workOrderNo").autocomplete({
			source : function(request, response) {
				// alert(request.term);
				$.ajax({
					url : 'getWwOrderToReturn.do',
					type : "POST",
					data : {
						contractNo : request.term
					},

					dataType : "json",

					success : function(data) {
						response($.map(data, function(v, i) {
							return {
								label : v.workOrderNo,
								value : v.workOrderNo
							};

						}));
					}

				});

			}// ,
		/*
		 * response : function(event, ui) { if (!ui.content.length) { var
		 * noResult = { value : "", label : "No matching your request" };
		 * ui.content.push(noResult); } },
		 */
		/*
		 * select : function(event, ui) { // alert(ui.item.label);
		 * window.location.href = 'viewWorkOrderData.do?contractNo=' +
		 * ui.item.label; },
		 */
		/* minLength : 1 */
		});
	});
});


//Start Codes for Location Grid

//append Grid Code block

$(function() {
	$("#myGrid").dialog({
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
						caption : 'Dynamic Location Setup DESCO Store',
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
									ctrlOptions :$('#ledgerBookList').val()
								},
								{
									name : 'location',
									display : 'Location',
									type : 'select',
									ctrlOptions :$('#locationList').val(),
									// ctrlOptions : data.locationOptions,
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
								
								var rowId=$("#tblAppendGrid").attr("data-rowid");
								// save to db
								var cData = {
									uuid : $("#uuid").val(),
									itemCode : $("#tblAppendGrid").attr("data-itemcode"),
									locationList : realData
								}
								var cDataJsonString = JSON.stringify(cData);
								$.ajax({
									url : $("#contextPath").val()
											+ '/ss/cs/rt/saveLocation7sDtl.do',
									data : cDataJsonString,
									contentType : "application/json",
									success : function(data) {
										var sData = JSON.parse(data);
										// alert(sData.toFixed(2));
										var r = $("#tblAppendGrid").attr("data-rowid");
										$("#qtyNewServiceable" + r).val(sData.qtyNewServiceableRcv.toFixed(2));
										$("#qtyRecServiceable" + r).val(sData.qtyRecServiceableRcv.toFixed(2));
										$("#qtyUnServiceable" + r).val(sData.qtyUnServiceableRcv.toFixed(2));
										$("#totalReturn" + r).val(sData.totalReturnRcv.toFixed(2));
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

//Get Godown List
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
//Get Floor List
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

//Get Sector List
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

//Get Rake List
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

//Get Bin List
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

//End Codes for Location Grid
