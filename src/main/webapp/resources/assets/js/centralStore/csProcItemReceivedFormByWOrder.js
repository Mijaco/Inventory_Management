
$(document)
		.ready(function() {
					$(function() {
						$("#contractNo")
								.autocomplete(
										{
											source : function(request, response) {
												$.ajax({
														url : 'getWwOrder.do',
														type : "POST",
														data : {
															contractNo : request.term
														},
														dataType : "json",
														success : function(																	data) {
															response($
																	.map(data,
																			function(v,i) {
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

			/*
			 * function detailCheck(){
			 * 
			 * var checkCount = $('#checkCount').val(); for(var i=0;i<checkCount;i++){
			 * alert(checkCount); var remainingQty = $('#remainingQty'+i).val();
			 * var receivedQty = $('#receivedQty'+i).val(); alert(remainingQty);
			 * if((remainingQty) == 0){ if((receivedQty) == 0){
			 * $('#receivedQty'+i).after('<span class="error"
			 * style="color:red"> Please enter receivedQty</span>'); return
			 * false; } }
			 * 
			 * if((receivedQty) == 0){ if((remainingQty) == 0){
			 * $('#remainingQty'+i).after('<span class="error"
			 * style="color:red"> Please enter remainingQty</span>'); return
			 * false; } } } return true; }
			 */
		});

// New location Grid Added/
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
				$(this).dialog("close");
				$('#tblAppendGrid').empty();
			}
		}
	});

});

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
									value : $("#ledgerName").val()									
								},
								{
									name : 'location',
									display : 'Location',
									type : 'select',
									ctrlOptions : $("#locationList").val(),
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
										ledgerName:$("#ledgerName").val(), 	
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
										$("#receivedQty"+r).val(sData.toFixed(3));
										//$('.totalCost').removeClass('hide');
										//var totalCost = sData.toFixed(2) * $('#cost'+r).val();
										//$('#totalCost'+r).val( totalCost.toFixed(2) );
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


//
