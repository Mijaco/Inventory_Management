function itemLeaveChange(element) {
	// alert("contractNo");
	var temp = $(element).closest("div").parent().parent().attr("id");

	var idPrefix = "myArea";
	var sequence = temp.substr(idPrefix.length);

	var e = $(element).closest("div").parent().parent().find('.matItemName')
			.attr('id', sequence);
	// var e = document.getElementById('' + sequence);
	var e1 = e[0];
	var item_id = e1.options[e1.selectedIndex].value;

	// alert(contractNo+"---"+item_id);R
	$.ajax({
		// url : 'procurement/requisition/viewInventoryItem.do',
		url : 'viewInventoryItem.do',
		data : "{id:" + item_id + "}",
		// data : "{id:" + item_id + "}",
		contentType : "application/json",
		success : function(data) {
			// alert(data);
			var ItemMaster = JSON.parse(data);
			$(element).closest("div").parent().parent().find('.matItemCode')
					.val(ItemMaster.itemId);
			$(element).closest("div").parent().parent().find('.matUom').val(
					ItemMaster.unitCode);
			$(element).closest("div").parent().parent().find('.description')
					.val(ItemMaster.itemName);
			$(element).closest("div").parent().parent().find('.matUnitPrice').val(
					ItemMaster.price);
		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
}

function categoryLeaveChange(element) {

	/*
	 * var temp = $(element).closest("div").parent().parent().attr("id"); var
	 * sequence = temp.substr(temp.length - 1)
	 */
	var temp = $(element).closest("div").parent().parent().attr("id");

	var idPrefix = "myArea";
	var sequence = temp.substr(idPrefix.length);

	$(element).closest("div").parent().parent().find('.matCategory').attr('id',
			sequence);
	var e = document.getElementById('' + sequence);
	var categoryId = e.options[e.selectedIndex].value;
	$.ajax({

		url : 'viewInventoryItemCategory.do',
		data : "{categoryId:" + categoryId + "}",
		contentType : "application/json",
		success : function(data) {
			var itemList = JSON.parse(data);
			var itemNames = $(element).closest("div").parent().parent().find(
					'.matItemName');

			itemNames.empty();

			itemNames.append($("<option></option>").attr("value", '').text(
					'Select Item'));
			$.each(itemList, function(id, itemName) {
				itemNames.append($("<option></option>").attr("value", this.id)
						.text(this.itemId+" - "+this.itemName));
			});
		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
}
function categoryLeaveChange1(element) {

	/*
	 * var temp = $(element).closest("div").parent().parent().attr("id"); var
	 * sequence = temp.substr(temp.length - 1)
	 */
	// var temp = $(element).closest("div").parent().parent().attr("id");
	// var sequence = temp.substr(temp.length-1);
	// var idPrefix="costIns";
	// var sequence = temp.substr(idPrefix.length);
	// $(element).closest("div").parent().parent().find('.insCategory').attr('id',sequence);
	//
	// var categoryId = document.getElementById("insCategory"+sequence).value;
	var id = $(element).attr("id");
	var name = $(element).attr('name');
	var sequence = id.substr(name.length);

	var e = document.getElementById('insCategory' + sequence);
	var categoryId = e.options[e.selectedIndex].value;
	$.ajax({

		url : 'viewInventoryItemCategory.do',
		data : "{categoryId:" + categoryId + "}",
		contentType : "application/json",
		success : function(data) {
			var itemList = JSON.parse(data);
			var itemNames = $(element).closest("div").parent().parent().find(
					'#insItemName' + sequence);

			itemNames.empty();

			itemNames.append($("<option></option>").attr("value", '').text(
					'Select Item'));
			$.each(itemList, function(id, itemName) {
				itemNames.append($("<option></option>").attr("value", this.id)
						.text(this.itemId+" - "+this.itemName));
			});
		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
}

function categoryLeaveChange2(element) {

	/*var temp = $(element).closest("div").parent().parent().attr("id");
	var sequence = temp.substr(temp.length - 1)*/
	/*var temp = $(element).closest("div").parent().parent().attr("id");

	 var idPrefix="myAreaaa";
	 var sequence = temp.substr(idPrefix.length);
*/
//alert(sequence);
	/*$(element).closest("div").parent().parent().find('.recCategory').attr('id',
			sequence);
	var e = document.getElementById('' + sequence);*/
	/*var categoryId = e.options[e.selectedIndex].value;*/
	/*var categoryId = document.getElementById("recCategory"+sequence).value;*/
	// alert(categoryId);
	var id = $(element).attr("id");
	var name = $(element).attr('name');
	var sequence = id.substr(name.length);

	var e = document.getElementById('recCategory' + sequence);
	var categoryId = e.options[e.selectedIndex].value;
$.ajax({

		url : 'viewInventoryItemCategory2.do',
		data : "{categoryId:" + categoryId + "}",
		contentType : "application/json",
		success : function(data) {
			var itemList = JSON.parse(data);
			var itemNames = $(element).closest("div").parent().parent().find(
					'#recItemName'+sequence);

			itemNames.empty();

			itemNames.append($("<option></option>").attr("value", '').text(
					'Select Item'));
			$.each(itemList, function(id, itemName) {
				itemNames.append($("<option></option>").attr("value", this.id)
						.text(this.itemId+" - "+this.itemName));
			});
		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
}


function itemLeaveChange1(element) {
	// alert("contractNo");
	var temp = $(element).closest("div").parent().parent().attr("id");

	var idPrefix = "costIns";
	var sequence = temp.substr(idPrefix.length);

	var e = $(element).closest("div").parent().parent().find('.insItemName')
			.attr('id', sequence);
	// var e = document.getElementById('' + sequence);
	var e1 = e[0];
	var item_id = e1.options[e1.selectedIndex].value;

	// alert(contractNo+"---"+item_id);R
	$.ajax({
		// url : 'procurement/requisition/viewInventoryItem.do',
		url : 'viewInventoryItem.do',
		data : "{id:" + item_id + "}",
		// data : "{id:" + item_id + "}",
		contentType : "application/json",
		success : function(data) {
			// alert(data);
			var ItemMaster = JSON.parse(data);
			/*
			 * $(element).closest("div").parent().parent().find('.matItemCode').val(
			 * ItemMaster.itemId);
			 */
			$(element).closest("div").parent().parent().find('.insUom').val(
					ItemMaster.unitCode);
			$(element).closest("div").parent().parent().find('.description1')
					.val(ItemMaster.itemName);
			$(element).closest("div").parent().parent().find('.insUnitPrice').val(
					ItemMaster.price);
		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
}
function itemLeaveChange2(element) {
	// alert("contractNo");
	var temp = $(element).closest("div").parent().parent().attr("id");

	var idPrefix = "myareaa";
	var sequence = temp.substr(idPrefix.length);

	var e = $(element).closest("div").parent().parent().find('.recItemName')
			.attr('id', sequence);
	// var e = document.getElementById('' + sequence);
	var e1 = e[0];
	var item_id = e1.options[e1.selectedIndex].value;

	// alert(contractNo+"---"+item_id);R
	$.ajax({
		// url : 'procurement/requisition/viewInventoryItem.do',
		url : 'viewInventoryItem.do',
		data : "{id:" + item_id + "}",
		// data : "{id:" + item_id + "}",
		contentType : "application/json",
		success : function(data) {
			// alert(data);
			var ItemMaster = JSON.parse(data);
			/*
			 * $(element).closest("div").parent().parent().find('.matItemCode').val(
			 * ItemMaster.itemId);
			 */
			$(element).closest("div").parent().parent().find('.recUom').val(
					ItemMaster.unitCode);
			$(element).closest("div").parent().parent().find('.description2')
					.val(ItemMaster.itemName);
			$(element).closest("div").parent().parent().find('.recUnitPrice').val(
					ItemMaster.price);
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
						$('.error').hide();
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

						// start of seting id on matQuantity fields
						var quantityDiv = childDiv.getElementsByTagName('div')[4], quantityInput = quantityDiv
								.getElementsByTagName('input')[0];
						quantityInput
								.setAttribute('id', 'matQuantity' + newNum);
						// end of seting id on quantityIssued fields

						// start of seting id on unitCost fields
						var unitPriceDiv = childDiv.getElementsByTagName('div')[5], unitPriceInput = unitPriceDiv
								.getElementsByTagName('input')[0];
						unitPriceInput.setAttribute('id', 'matUnitPrice'
								+ newNum);
						// end of seting id on unitCost fields

						// start of seting id on unitCost fields
						var matAmountDiv = childDiv.getElementsByTagName('div')[6], matAmountInput = matAmountDiv
								.getElementsByTagName('input')[0];
						matAmountInput.setAttribute('id', 'matAmount' + newNum);
						// end of seting id on totalCost fields
						newEntry.find('input').val('');
						newEntry.find('.matQuantity').val(0.0);

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

$(function() {
	$(document)
			.on(
					'click',
					'.btn-add-cost-ins',
					function(e) {
						$('.error').hide();
						// e.preventDefault();

						var num = $('.clonedAreaa').length;
						var newNum = num + 1;

						var controlForm = $('.controls1 div:first'), currentEntry = $(
								this).parents('.entry1:first'), newEntry = $(
								currentEntry.clone().attr('id',
										'costIns' + newNum).addClass(
										'clonedAreaa')).appendTo(controlForm);

						// set dynamic id on item qty fields

						var mainDiv = document.getElementById('costIns'
								+ newNum), childDiv = mainDiv
								.getElementsByTagName('div')[0];

						// start of seting id on matQuantity fields
						var insCategoryDiv = childDiv
								.getElementsByTagName('div')[0], insCategory = insCategoryDiv
								.getElementsByTagName('select')[0];
						insCategory.setAttribute('id', 'insCategory' + newNum);

						/*
						 * var descriptionDiv = childDiv
						 * .getElementsByTagName('div')[1], description =
						 * descriptionDiv .getElementsByTagName('input')[0];
						 * description.setAttribute('id', 'description' +
						 * newNum);
						 */

						var insItemNameDiv = childDiv
								.getElementsByTagName('div')[1], insItemName = insItemNameDiv
								.getElementsByTagName('select')[0];
						insItemName.setAttribute('id', 'insItemName' + newNum);
						// end of seting id on quantityIssued fields

						// start of seting id on unitCost fields
						var insQuantityDiv = childDiv
								.getElementsByTagName('div')[3], insQuantityInput = insQuantityDiv
								.getElementsByTagName('input')[0];
						insQuantityInput.setAttribute('id', 'insQuantity'
								+ newNum);

						var insUnitPriceDiv = childDiv
								.getElementsByTagName('div')[4], insUnitPriceInput = insUnitPriceDiv
								.getElementsByTagName('input')[0];
						insUnitPriceInput.setAttribute('id', 'insUnitPrice'
								+ newNum);
						// end of seting id on totalCost fields
						newEntry.find('input').val('');

						var insAmountDiv = childDiv.getElementsByTagName('div')[5], insAmountInput = insAmountDiv
								.getElementsByTagName('input')[0];
						insAmountInput.setAttribute('id', 'insAmount' + newNum);
						// end of seting id on totalCost fields
						newEntry.find('input').val('');
						newEntry.find('.insQuantity').val(0.0);

						/*controlForm
								.find('.entry1:not(:last) .btn-add-cost-ins')
								.removeClass('btn-add-cost-ins')
								.addClass('btn-remove1')
								.removeClass('btn-success')
								.addClass('btn-danger')
								.html(
										'<span class="glyphicon glyphicon-minus"></span>');*/
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
						$('.error').hide();
						// e.preventDefault();

						var num = $('.clonedArea2').length;
						var newNum = num + 1;

						var controlForm = $('.controls2 div:first'), currentEntry = $(
								this).parents('.entry2:first'), newEntry = $(
								currentEntry.clone().attr('id',
										'myAreaa' + newNum).addClass(
										'clonedArea')).appendTo(controlForm);

						// set dynamic id on item qty fields

						var mainDiv = document.getElementById('myAreaa'
								+ newNum), childDiv = mainDiv
								.getElementsByTagName('div')[0];
						
						// start of seting id on matQuantity fields
						var insCategoryDiv = childDiv
								.getElementsByTagName('div')[0], insCategory = insCategoryDiv
								.getElementsByTagName('select')[0];
						insCategory.setAttribute('id', 'recCategory' + newNum);

						/*
						 * var descriptionDiv = childDiv
						 * .getElementsByTagName('div')[1], description =
						 * descriptionDiv .getElementsByTagName('input')[0];
						 * description.setAttribute('id', 'description' +
						 * newNum);
						 */

						var insItemNameDiv = childDiv
								.getElementsByTagName('div')[1], insItemName = insItemNameDiv
								.getElementsByTagName('select')[0];
						insItemName.setAttribute('id', 'recItemName' + newNum);
						// end of seting id on quantityIssued fields
						// start of seting id on unit fields
						var unitDiv = childDiv.getElementsByTagName('div')[2], unitInput = unitDiv
								.getElementsByTagName('input')[0];
						unitInput.setAttribute('id', 'recUom' + newNum);
						var quantityDiv = childDiv.getElementsByTagName('div')[3], quantityInput = quantityDiv
								.getElementsByTagName('input')[0];
						quantityInput
								.setAttribute('id', 'recQuantity' + newNum);
						// end of seting id on quantityIssued fields

						// start of seting id on unitCost fields
						var unitPriceDiv = childDiv.getElementsByTagName('div')[4], unitPriceInput = unitPriceDiv
								.getElementsByTagName('input')[0];
						unitPriceInput.setAttribute('id', 'recUnitPrice'
								+ newNum);
						// end of seting id on unitCost fields

						// start of seting id on unitCost fields
						var matAmountDiv = childDiv.getElementsByTagName('div')[5], matAmountInput = matAmountDiv
								.getElementsByTagName('input')[0];
						matAmountInput.setAttribute('id', 'recAmount' + newNum);
						// end of seting id on unitCost fields
						// end of seting id on totalCost fields

						newEntry.find('input').val('');
						newEntry.find('.recQuantity').val(0.0);

						/*controlForm
								.find('.entry2:not(:last) .btn-add2')
								.removeClass('btn-add2')
								.addClass('btn-remove2')
								.removeClass('btn-success')
								.addClass('btn-danger')
								.html(
										'<span class="glyphicon glyphicon-minus"></span>');*/
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
						$('.error').hide();
						// e.preventDefault();

						var num = $('.clonedArea3').length;
						var newNum = num + 1;

						var controlForm = $('.controls3 div:first'), currentEntry = $(
								this).parents('.entry3:first'), newEntry = $(
								currentEntry.clone().attr('id',
										'myArea3' + newNum).addClass(
										'clonedArea3')).appendTo(controlForm);

						// set dynamic id on item qty fields

						var mainDiv = document.getElementById('myArea3'
								+ newNum), childDiv = mainDiv
								.getElementsByTagName('div')[0];

						// start of seting id on itemName fields
						var itemNameDiv = childDiv.getElementsByTagName('div')[0], itemNameInput = itemNameDiv
								.getElementsByTagName('input')[0];
						itemNameInput
								.setAttribute('id', 'misItemName' + newNum);
						// end of seting id on itemName fields
						// start of seting id on unit fields
						var unitDiv = childDiv.getElementsByTagName('div')[1], unitInput = unitDiv
								.getElementsByTagName('input')[0];
						unitInput.setAttribute('id', 'misUom' + newNum);
						// start of seting id on matQuantity fields
						var quantityDiv = childDiv.getElementsByTagName('div')[2], quantityInput = quantityDiv
								.getElementsByTagName('input')[0];
						quantityInput
								.setAttribute('id', 'misQuantity' + newNum);
						// end of seting id on quantityIssued fields

						// start of seting id on unitCost fields
						var unitPriceDiv = childDiv.getElementsByTagName('div')[3], unitPriceInput = unitPriceDiv
								.getElementsByTagName('input')[0];
						unitPriceInput.setAttribute('id', 'misUnitPrice'
								+ newNum);
						// end of seting id on unitCost fields

						// start of seting id on unitCost fields
						var matAmountDiv = childDiv.getElementsByTagName('div')[4], matAmountInput = matAmountDiv
								.getElementsByTagName('input')[0];
						matAmountInput.setAttribute('id', 'misAmount' + newNum);
						// end of seting id on unitCost fields
						// end of seting id on totalCost fields

						newEntry.find('input').val('');
						newEntry.find('.misQuantity').val(0.0);

						/*controlForm
								.find('.entry3:not(:last) .btn-add3')
								.removeClass('btn-add3')
								.addClass('btn-remove3')
								.removeClass('btn-success')
								.addClass('btn-danger')
								.html(
										'<span class="glyphicon glyphicon-minus"></span>');*/
					}).on('click', '.btn-remove3', function(e) {
				$(this).parents('.entry3:first').remove();

				// e.preventDefault();
				return false;
			});
});

$(document).ready(function() {
	$(function() {

		$("#recItemName0").autocomplete({
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

		$("#recItemName1").autocomplete({
			source : function(request, response) {
				alert(request.term);
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

		$("#recItemName2").autocomplete({
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

		$(".misItemName").autocomplete({
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

		$("#misItemName1").autocomplete({
			source : function(request, response) {
				alert(request.term);
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

		$("#misItemName2").autocomplete({
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
	// alert(sequence);
	var matQuantity = $('#matQuantity' + sequence).val().trim();
	var matQuantityFloat = parseFloat(matQuantity).toFixed(2);
	var matUnitPrice = $('#matUnitPrice' + sequence).val().trim();
	var matUnitPriceFloat = parseFloat(matUnitPrice).toFixed(2);
	// alert(sequence+"--"+matQuantity);
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
	// alert(sequence+"--"+matQuantity);
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
	// alert(sequence+"--"+matQuantity);
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
	// alert(sequence+"--"+matQuantityFloat);
	var totalCost = (matQuantityFloat * matUnitPriceFloat).toFixed(2);
	$('#misAmount' + sequence).val(totalCost);

}

function func(value) {
	var iqd = document.getElementById(value).id;
	var name = document.getElementById(value).name;
	var sequence = iqd.substr(name.length);
	return sequence;
}

function showUom(idValue) {
	// alert(idValue);
	var index = func(idValue);
	$.ajax({
		type : "post",
		url : 'showUnit.do',
		async : false,
		data : 'itemName=' + document.getElementById(idValue).value,
		success : function(response) {
			if (response != null || response != "") {
				document.getElementById('recUom' + index).value = response;
			}
		},
		error : function() {
		}
	});

	return true;
}

function showMiscUom(idValue) {
	var index = func(idValue);
	$.ajax({
		type : "post",
		url : 'showUnit.do',
		async : false,
		data : 'itemName=' + document.getElementById(idValue).value,
		success : function(response) {
			if (response != null || response != "") {
				document.getElementById('misUom' + index).value = response;
			}
		},
		error : function() {
		}
	});

	return true;
}

$(document).ready(
		function() {

			$('#submit').click(function() {
				return validateForm();
			});

			function validateForm() {
				var row = $('.clonedArea').length;
				var rowCount = row+1;
				
				var name = $('#name').val();
				var serviceCharge = $('#serviceCharge').val();
				var address = $('#address').val();
				
				var inputVal = new Array(name, serviceCharge, address);

				var inputMessage = new Array("Name", "service Charge", "Address", "Cost Materials Quantity", "Installation Quantity", "Recovery Quantity", "Miscellaneous Quantity", "Miscellaneous price"/*, "Cost Materials Price"*/);

				$('.error').hide();	
				
				if (inputVal[0] == "") {
					$('#name').after(
							'<span class="error" style="color:red"> Please enter '
									+ inputMessage[0] + '</span>');
					return false;
				}
				
				if (inputVal[1] == "") {
					$('#serviceCharge').after(
							'<span class="error" style="color:red"> Please enter '
									+ inputMessage[1] + '</span>');
					return false;
				}
				if (inputVal[2] == "") {
					$('#address').after(
							'<span class="error" style="color:red"> Please enter '
									+ inputMessage[2] + '</span>');
					return false;
				}
				
				for(var i=0;i<rowCount;i++){
					//var quantity = ;	
					if ($('#matQuantity'+i).val() == 0) {
						$('#matQuantity'+i).after(
								'<span class="error" style="color:red"> Please enter '
										+ inputMessage[3] + '</span>');
						return false;
					}
					/*if ($('#matUnitPrice'+i).val() == 0) {
						$('#matUnitPrice'+i).after(
								'<span class="error" style="color:red"> Please enter '
										+ inputMessage[4] + '</span>');
						return false;
					}*/
				}
				
				var row2 = $('.clonedAreaa').length;
				var rowCount2 = row2+1;
				
				for(var i=0;i<rowCount2;i++){
					//var quantity = ;	
					if ($('#insQuantity'+i).val() == 0) {
						$('#insQuantity'+i).after(
								'<span class="error" style="color:red"> Please enter '
										+ inputMessage[4] + '</span>');
						return false;
					}
				}
				
				var row3 = $('.clonedArea2').length;
				var rowCount3 = row3+1;
				
				for(var i=0;i<rowCount3;i++){
					//var quantity = ;	
					if ($('#recQuantity'+i).val() == 0) {
						$('#recQuantity'+i).after(
								'<span class="error" style="color:red"> Please enter '
										+ inputMessage[5] + '</span>');
						return false;
					}
				}
				
				var row4 = $('.clonedArea3').length;
				var rowCount4 = row4+1;
				
				for(var i=0;i<rowCount4;i++){
					//var quantity = ;	
					if ($('#misQuantity'+i).val() == 0) {
						$('#misQuantity'+i).after(
								'<span class="error" style="color:red"> Please enter '
										+ inputMessage[6] + '</span>');
						return false;
					}
					if ($('#misUnitPrice'+i).val() == 0 || $('#misUnitPrice'+i).val() == "") {
						$('#misUnitPrice'+i).after(
								'<span class="error" style="color:red"> Please enter '
										+ inputMessage[7] + '</span>');
						return false;
					}
					
				}
				return true;
			}
		});