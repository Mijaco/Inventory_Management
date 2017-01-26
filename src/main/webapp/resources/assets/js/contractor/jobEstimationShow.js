function itemLeaveChange(element) {
	//alert("contractNo");
	var temp = $(element).closest("tr").attr("id");
	//alert(temp);
	var idPrefix="myArea";
	 var sequence = temp.substr(idPrefix.length);

	var e = $(element).closest("div").parent().parent().find('#matItemName'+sequence).attr(
			'id', sequence);
	// var e = document.getElementById('' + sequence);
	var e1 = e[0];
	var item_id = e1.options[e1.selectedIndex].value;
	//alert(item_id+"-----------"+sequence);
	$.ajax({
		// url : 'procurement/requisition/viewInventoryItem.do',
		url : 'viewInventoryItem.do',
		data : "{id:" + item_id + "}",
		//data : "{id:" + item_id + "}",
		contentType : "application/json",
		success : function(data) {
			//alert(ItemMaster.itemName);
			var ItemMaster = JSON.parse(data);
			//alert(ItemMaster.id);
			$(element).closest("div").parent().parent().find('#matItemCode'+sequence).val(
					ItemMaster.itemId);
			$(element).closest("div").parent().parent().find('#matUom'+sequence).val(
					ItemMaster.unitCode);
			//('#description'+sequence).val("");
			$(element).closest("div").parent().parent().find('#description'+sequence)
			.val(ItemMaster.itemName);
		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
}

function itemLeaveChange1(element) {
	//alert("contractNo");
	var temp = $(element).closest("tr").attr("id");
	//alert(temp);
	var idPrefix="myArea1";
	 var sequence = temp.substr(idPrefix.length);

	var e = $(element).closest("div").parent().parent().find('#insItemName'+sequence).attr(
			'id', sequence);
	
	// var e = document.getElementById('' + sequence);
	var e1 = e[0];
	var item_id = e1.options[e1.selectedIndex].value;
	alert(item_id+"-----------"+sequence);
	//alert(contractNo+"---"+item_id);R
	$.ajax({
		// url : 'procurement/requisition/viewInventoryItem.do',
		url : 'viewInventoryItem.do',
		data : "{id:" + item_id + "}",
		//data : "{id:" + item_id + "}",
		contentType : "application/json",
		success : function(data) {
			//alert(ItemMaster.itemName);
			var ItemMaster = JSON.parse(data);
			//alert(ItemMaster.id);
			/*$(element).closest("div").parent().parent().find('#insItemCode'+sequence).val(
					ItemMaster.itemId);*/
			$(element).closest("div").parent().parent().find('#insUom'+sequence).val(
					ItemMaster.unitCode);
			//('#description'+sequence).val("");
			$(element).closest("div").parent().parent().find('#descriptions'+sequence)
			.val(ItemMaster.itemName);
		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
}
function itemLeaveChange2(element) {
	//alert("contractNo");
	var temp = $(element).closest("div").parent().parent().attr("id");
	var sequence = temp.substr(temp.length - 1);

	var e = $(element).closest("div").parent().parent().find('.matItemName').attr(
			'id', sequence);
	// var e = document.getElementById('' + sequence);
	var e1 = e[0];
	var item_id = e1.options[e1.selectedIndex].value;

	//alert(contractNo+"---"+item_id);R
	$.ajax({
		// url : 'procurement/requisition/viewInventoryItem.do',
		url : 'viewInventoryItem.do',
		data : "{id:" + item_id + "}",
		//data : "{id:" + item_id + "}",
		contentType : "application/json",
		success : function(data) {
			//alert(data);
			var ItemMaster = JSON.parse(data);
			$(element).closest("div").parent().parent().find('.matItemCode').val(
					ItemMaster.itemId);
			$(element).closest("div").parent().parent().find('.matUom').val(
					ItemMaster.unitCode);
			$(element).closest("div").parent().parent().find('.description')
			.val(ItemMaster.itemName);
		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
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

						// start of seting id on currentStock fields
						/*var currentStockDiv = childDiv
								.getElementsByTagName('div')[4], currentStockInput = currentStockDiv
								.getElementsByTagName('input')[0];
						currentStockInput.setAttribute('id', 'currentStock'
								+ newNum);*/
						// end of seting id on currentStock fields

						// start of seting id on quantityRequired fields
						/*var quantityRequiredDiv = childDiv
								.getElementsByTagName('div')[5], quantityRequiredInput = quantityRequiredDiv
								.getElementsByTagName('input')[0];
						quantityRequiredInput.setAttribute('id',
								'quantityRequired' + newNum);*/
						// end of seting id on quantityRequired fields

						// start of seting id on matQuantity fields
						var quantityDiv = childDiv
								.getElementsByTagName('div')[4], quantityInput = quantityDiv
								.getElementsByTagName('input')[0];
						quantityInput.setAttribute('id', 'matQuantity'
								+ newNum);
						// end of seting id on quantityIssued fields

						// start of seting id on unitCost fields
						var unitPriceDiv = childDiv.getElementsByTagName('div')[5], unitPriceInput = unitPriceDiv
								.getElementsByTagName('input')[0];
						unitPriceInput.setAttribute('id', 'matUnitPrice' + newNum);
						// end of seting id on unitCost fields
						
						// start of seting id on unitCost fields
						var matAmountDiv = childDiv.getElementsByTagName('div')[6], matAmountInput = matAmountDiv
								.getElementsByTagName('input')[0];
						matAmountInput.setAttribute('id', 'matAmount' + newNum);
						// end of seting id on unitCost fields

						// start of seting id on totalCost fields
						/*var totalCostDiv = childDiv.getElementsByTagName('div')[8], totalCostInput = totalCostDiv
								.getElementsByTagName('input')[0];
						totalCostInput.setAttribute('id', 'totalCost' + newNum);*/
						// end of seting id on totalCost fields

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

				// e.preventDefault();
				return false;
			});

});



$(function() {
	$(document)
			.on(
					'click',
					'.btn-add1',
					function(e) {
						// e.preventDefault();

						var num = $('.clonedArea1').length;
						var newNum = num + 1;

						var controlForm = $('.controls1 div:first'), currentEntry = $(
								this).parents('.entry1:first'), newEntry = $(
								currentEntry.clone().attr('id',
										'myArea1' + newNum).addClass(
										'clonedArea1')).appendTo(controlForm);

						// set dynamic id on item qty fields

						var mainDiv = document
								.getElementById('myArea1' + newNum), childDiv = mainDiv
								.getElementsByTagName('div')[0];

						
						
						// start of seting id on matQuantity fields
						var quantityDiv = childDiv
								.getElementsByTagName('div')[3], quantityInput = quantityDiv
								.getElementsByTagName('input')[0];
						quantityInput.setAttribute('id', 'insQuantity'
								+ newNum);
						// end of seting id on quantityIssued fields

						// start of seting id on unitCost fields
						var unitPriceDiv = childDiv.getElementsByTagName('div')[4], unitPriceInput = unitPriceDiv
								.getElementsByTagName('input')[0];
						unitPriceInput.setAttribute('id', 'insUnitPrice' + newNum);
						// end of seting id on unitCost fields
						
						// start of seting id on unitCost fields
						var matAmountDiv = childDiv.getElementsByTagName('div')[5], matAmountInput = matAmountDiv
								.getElementsByTagName('input')[0];
						matAmountInput.setAttribute('id', 'insAmount' + newNum);
						// end of seting id on unitCost fields
						// end of seting id on totalCost fields

						newEntry.find('input').val('');

						controlForm
								.find('.entry1:not(:last) .btn-add1')
								.removeClass('btn-add1')
								.addClass('btn-remove1')
								.removeClass('btn-success1')
								.addClass('btn-danger1')
								.html(
										'<span class="glyphicon glyphicon-minus"></span>');
					}).on('click', '.btn-remove1', function(e) {
				$(this).parents('.entry1:first').remove();

				// e.preventDefault();
				return false;
			});

});

$(function() {
	$(document)
			.on(
					'click',
					'.btn-add2',
					function(e) {
						// e.preventDefault();

						var num = $('.clonedArea2').length;
						var newNum = num + 1;

						var controlForm = $('.controls2 div:first'), currentEntry = $(
								this).parents('.entry2:first'), newEntry = $(
								currentEntry.clone().attr('id',
										'myArea2' + newNum).addClass(
										'clonedArea2')).appendTo(controlForm);

						// set dynamic id on item qty fields

						var mainDiv = document
								.getElementById('myArea2' + newNum), childDiv = mainDiv
								.getElementsByTagName('div')[0];
						// start of seting id on itemName fields
						/*var itemNameDiv = childDiv
								.getElementsByTagName('div')[0], itemNameInput = itemNameDiv
								.getElementsByTagName('input')[0];
						itemNameInput.setAttribute('id', 'recItemName'
								+ newNum);*/
						// end of seting id on itemName fields
						// start of seting id on matQuantity fields
						var quantityDiv = childDiv
								.getElementsByTagName('div')[2], quantityInput = quantityDiv
								.getElementsByTagName('input')[0];
						quantityInput.setAttribute('id', 'recQuantity'
								+ newNum);
						// end of seting id on quantityIssued fields

						// start of seting id on unitCost fields
						var unitPriceDiv = childDiv.getElementsByTagName('div')[3], unitPriceInput = unitPriceDiv
								.getElementsByTagName('input')[0];
						unitPriceInput.setAttribute('id', 'recUnitPrice' + newNum);
						// end of seting id on unitCost fields
						
						// start of seting id on unitCost fields
						var matAmountDiv = childDiv.getElementsByTagName('div')[4], matAmountInput = matAmountDiv
								.getElementsByTagName('input')[0];
						matAmountInput.setAttribute('id', 'recAmount' + newNum);
						// end of seting id on unitCost fields
						// end of seting id on totalCost fields

						newEntry.find('input').val('');

						controlForm
								.find('.entry2:not(:last) .btn-add2')
								.removeClass('btn-add2')
								.addClass('btn-remove2')
								.removeClass('btn-success2')
								.addClass('btn-danger2')
								.html(
										'<span class="glyphicon glyphicon-minus"></span>');
					}).on('click', '.btn-remove2', function(e) {
				$(this).parents('.entry2:first').remove();

				// e.preventDefault();
				return false;
			});

});

$(function() {
	$(document)
			.on(
					'click',
					'.btn-add3',
					function(e) {
						// e.preventDefault();

						var num = $('.clonedArea3').length;
						var newNum = num + 1;

						var controlForm = $('.controls3 div:first'), currentEntry = $(
								this).parents('.entry3:first'), newEntry = $(
								currentEntry.clone().attr('id',
										'myArea3' + newNum).addClass(
										'clonedArea3')).appendTo(controlForm);

						// set dynamic id on item qty fields

						var mainDiv = document
								.getElementById('myArea3' + newNum), childDiv = mainDiv
								.getElementsByTagName('div')[0];
						// start of seting id on itemName fields
						/*var itemNameDiv = childDiv
								.getElementsByTagName('div')[0], itemNameInput = itemNameDiv
								.getElementsByTagName('input')[0];
						itemNameInput.setAttribute('id', 'misItemName'
								+ newNum);*/
						// end of seting id on itemName fields
						// start of seting id on matQuantity fields
						var quantityDiv = childDiv
								.getElementsByTagName('div')[2], quantityInput = quantityDiv
								.getElementsByTagName('input')[0];
						quantityInput.setAttribute('id', 'misQuantity'
								+ newNum);
						// end of seting id on quantityIssued fields

						// start of seting id on unitCost fields
						var unitPriceDiv = childDiv.getElementsByTagName('div')[3], unitPriceInput = unitPriceDiv
								.getElementsByTagName('input')[0];
						unitPriceInput.setAttribute('id', 'misUnitPrice' + newNum);
						// end of seting id on unitCost fields
						
						// start of seting id on unitCost fields
						var matAmountDiv = childDiv.getElementsByTagName('div')[4], matAmountInput = matAmountDiv
								.getElementsByTagName('input')[0];
						matAmountInput.setAttribute('id', 'misAmount' + newNum);
						// end of seting id on unitCost fields
						// end of seting id on totalCost fields

						newEntry.find('input').val('');

						controlForm
								.find('.entry3:not(:last) .btn-add3')
								.removeClass('btn-add3')
								.addClass('btn-remove3')
								.removeClass('btn-success3')
								.addClass('btn-danger3')
								.html(
										'<span class="glyphicon glyphicon-minus"></span>');
					}).on('click', '.btn-remove3', function(e) {
				$(this).parents('.entry3:first').remove();

				// e.preventDefault();
				return false;
			});

});

$(document).ready(function() {
	$(function() {

		$("#recItemName").autocomplete({
			source : function(request, response) {
				// alert(request.term);
				$.ajax({
					url : 'getItems.do',
					type : "POST",
					data : {
						itemName : request.term
					},

					dataType : "json",

					success : function(data) {
						response($.map(data, function(v, i) {
							return {
								label : v.itemName,
								value : v.itemName
							};

						}));
					}
				});
			}
		});
	});
});

$(document).ready(function() {
	$(function() {

		$("#misItemName").autocomplete({
			source : function(request, response) {
				// alert(request.term);
				$.ajax({
					url : 'getItems.do',
					type : "POST",
					data : {
						itemName : request.term
					},

					dataType : "json",

					success : function(data) {
						response($.map(data, function(v, i) {
							return {
								label : v.itemName,
								value : v.itemName
							};

						}));
					}
				});
			}
		});
	});
});

function setMatTotalCost(element) {
	var id = $(element).attr('id');
	var name = $(element).attr('name');
	 var sequence = id.substr(name.length);
	var matQuantity = $('#matQuantity' + sequence).val().trim();
	var matQuantityFloat = parseFloat(matQuantity).toFixed(2);
	var matUnitPrice = $('#matUnitPrice' + sequence).val().trim();
	var matUnitPriceFloat = parseFloat(matUnitPrice).toFixed(2);
	//alert(sequence+"--"+matQuantity);
	var totalCost = (matQuantityFloat * matUnitPriceFloat).toFixed(2);
	$('#matAmount' + sequence).val(totalCost);

}
function setInsTotalCost(element) {
	var id = $(element).attr('id');
	var name = $(element).attr('name');
	 var sequence = id.substr(name.length);
	var matQuantity = $('#insQuantity' + sequence).val().trim();
	var matQuantityFloat = parseFloat(matQuantity).toFixed(2);
	var matUnitPrice = $('#insUnitPrice' + sequence).val().trim();
	var matUnitPriceFloat = parseFloat(matUnitPrice).toFixed(2);
	//alert(sequence+"--"+matQuantity);
	var totalCost = (matQuantityFloat * matUnitPriceFloat).toFixed(2);
	$('#insAmount' + sequence).val(totalCost);

}
function setRecTotalCost(element) {
	var id = $(element).attr('id');
	var name = $(element).attr('name');
	 var sequence = id.substr(name.length);
	var matQuantity = $('#recQuantity' + sequence).val().trim();
	var matQuantityFloat = parseFloat(matQuantity).toFixed(2);
	var matUnitPrice = $('#recUnitPrice' + sequence).val().trim();
	var matUnitPriceFloat = parseFloat(matUnitPrice).toFixed(2);
	//alert(sequence+"--"+matQuantity);
	var totalCost = (matQuantityFloat * matUnitPriceFloat).toFixed(2);
	$('#recAmount' + sequence).val(totalCost);

}
function setMisTotalCost(element) {
	var id = $(element).attr('id');
	var name = $(element).attr('name');
	 var sequence = id.substr(name.length);
	var matQuantity = $('#misQuantity' + sequence).val().trim();
	var matQuantityFloat = parseFloat(matQuantity).toFixed(2);
	var matUnitPrice = $('#misUnitPrice' + sequence).val().trim();
	var matUnitPriceFloat = parseFloat(matUnitPrice).toFixed(2);
	//alert(sequence+"--"+matQuantityFloat);
	var totalCost = (matQuantityFloat * matUnitPriceFloat).toFixed(2);
	$('#misAmount' + sequence).val(totalCost);

}
/*function forwardToUpper(stateCode) {
	var justification = $('#justification').val();
	var pndNo = $('#pndNo').val();

	// window.location =
	// "${pageContext.request.contextPath}/cs/itemRecieved/itemReceivedForwordTo.do?receivedReportNo="+param1+"$justification="+justification;
	window.location = $('#contextPath').val() + "/template/sendTo.do?pndNo="
			+ pndNo
			+ "&justification="
			+ justification
			+ "&stateCode="
			+ stateCode;
}*/
function forwardToUpper(stateCode, userid) {
	var justification = $('#justification').val();
	var pndNo = $('#pndNo').val();
	
	$('#userid').val(userid);
	$('#stateCode').val(stateCode);

	// window.location =
	// "${pageContext.request.contextPath}/cs/itemRecieved/itemReceivedForwordTo.do?receivedReportNo="+param1+"$justification="+justification;
	/*window.location = $('#contextPath').val() + "/template/sendTo.do?pndNo="
			+ pndNo
			+ "&justification="
			+ justification
			+ "&stateCode="
			+ stateCode
			+ "&userid="
			+ userid;*/
	
	document.getElementById('myForm').submit();
}
/*function backToLower(stateCode) {
	var justification = $('#justification').val();
	var pndNo = $('#pndNo').val();

	// window.location =
	// "${pageContext.request.contextPath}/cs/itemRecieved/itemReceivedForwordTo.do?receivedReportNo="+param1+"$justification="+justification;
	window.location = $('#contextPath').val() + "/template/backTo.do?pndNo="
			+ pndNo
			+ "&justification="
			+ justification
			+ "&stateCode="
			+ stateCode;
}*/

function backToLower(stateCode, userid) {
	var justification = $('#justification').val();
	var pndNo = $('#pndNo').val();

	// window.location =
	// "${pageContext.request.contextPath}/cs/itemRecieved/itemReceivedForwordTo.do?receivedReportNo="+param1+"$justification="+justification;
	window.location = $('#contextPath').val() + "/template/backTo.do?pndNo="
			+ pndNo
			+ "&justification="
			+ justification
			+ "&stateCode="
			+ stateCode
			+ "&userid="
			+ userid;
}

/*function approveing() {
	var justification = $('#justification').val();
	var pndNo = $('#pndNo').val();
	var returnStateCode = $('#returnStateCode').val();

	window.location = $('#contextPath').val() + "/template/costEstimationSubmitApproved.do?pndNo="
			+ pndNo
			+ "&justification="
			+ justification
			+ "&return_state=" + returnStateCode;
}*/

function approvedCostEstimation(){
	var justification = $('#justification').val();
	var pndNo = $('#pndNo').val();
	var contextPath = $('#contextPath').val();
	var cData = {
		pndNo : pndNo,			
		justification : justification
	}		
	var path=contextPath+"/template/costEstimationApproved.do";		
	postSubmit(path, cData, 'POST');
}