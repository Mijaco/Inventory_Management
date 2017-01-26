
//materials 
function itemLeaveChange(element) {
	// alert("contractNo");
	var temp = $(element).closest("div").parent().parent().attr("id");

	var idPrefix = "myArea";
	var sequence = temp.substr(idPrefix.length);

	var e = $(element).closest("div").parent().parent().find('.matItemNameSelect')
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
			$(element).closest("div").parent().parent().find('.matItemName')
					.val(ItemMaster.itemName);
			$(element).closest("div").parent().parent().find('.matUnitPrice')
					.val(ItemMaster.price);
		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
}
//materials item chose function
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
					'.matItemNameSelect');

			itemNames.empty();

			itemNames.append($("<option></option>").attr("value", '').text(
					'Select Item'));
			$.each(itemList, function(id, itemName) {
				itemNames.append($("<option></option>").attr("value", this.id)
						.text(this.itemName + " [" +this.itemId+ "]"));
			});
		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
}

//installation category choose function
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
					'#insItemNameSelect' + sequence);

			itemNames.empty();

			itemNames.append($("<option></option>").attr("value", '').text(
					'Select Item'));
			$.each(itemList, function(id, itemName) {
				itemNames.append($("<option></option>").attr("value", this.id)
						.text(this.itemName + " [" +this.itemId+ "]"));
			});
		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
}

//installation item choose function
function itemLeaveChange1(element) {
	// alert("contractNo");
	var temp = $(element).closest("div").parent().parent().attr("id");

	var idPrefix = "costIns";
	var sequence = temp.substr(idPrefix.length);

	var e = $(element).closest("div").parent().parent().find('.insItemNameSelect')
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
			$(element).closest("div").parent().parent().find('.insItemName')
					.val(ItemMaster.itemName);
			$(element).closest("div").parent().parent().find('.insUnitPrice')
					.val(ItemMaster.price);
		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
}

//recovery category choose function
function categoryLeaveChange2(element) {

	/*
	 * var temp = $(element).closest("div").parent().parent().attr("id"); var
	 * sequence = temp.substr(temp.length - 1)
	 */
	/*
	 * var temp = $(element).closest("div").parent().parent().attr("id");
	 * 
	 * var idPrefix="myAreaaa"; var sequence = temp.substr(idPrefix.length);
	 */
	// alert(sequence);
	/*
	 * $(element).closest("div").parent().parent().find('.recCategory').attr('id',
	 * sequence); var e = document.getElementById('' + sequence);
	 */
	/* var categoryId = e.options[e.selectedIndex].value; */
	/* var categoryId = document.getElementById("recCategory"+sequence).value; */
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
					'#recItemNameSelect' + sequence);

			itemNames.empty();

			itemNames.append($("<option></option>").attr("value", '').text(
					'Select Item'));
			$.each(itemList, function(id, itemName) {
				itemNames.append($("<option></option>").attr("value", this.id)
						.text(this.itemName + " [" +this.itemId+ "]"));
			});
		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
}

//recovery item choose function
function itemLeaveChange2(element) {
	// alert("contractNo");
	var temp = $(element).closest("div").parent().parent().attr("id");

	var idPrefix = "myareaa";
	var sequence = temp.substr(idPrefix.length);

	var e = $(element).closest("div").parent().parent().find('.recItemNameSelect')
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
			$(element).closest("div").parent().parent().find('.recItemName')
					.val(ItemMaster.itemName);
			$(element).closest("div").parent().parent().find('.recUnitPrice')
					.val(ItemMaster.price);
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
						var count = parseInt($('#matCount').val());
						// alert(count);
						var num = $('.clonedArea').length;
						var newNum = num + 1;
						newNum += count;
						//alert(newNum);
						var controlForm = $('.controls div:first'), currentEntry = $(
								this).parents('.entry:first'), newEntry = $(
								currentEntry.clone().attr('id',
										'myArea' + newNum).addClass(
										'clonedArea')).appendTo(controlForm);

						// set dynamic id on item qty fields

						var mainDiv = document
								.getElementById('myArea' + newNum), childDiv = mainDiv
								.getElementsByTagName('div')[0];

						// start of setting id on Category fields
						var catDiv = childDiv.getElementsByTagName('div')[0], catInput = catDiv
								.getElementsByTagName('select')[0];
						catInput.setAttribute('id', 'matCategory' + newNum);
						// end of setting id on Category fields

						// start of setting id on ItemName select fields
						var matItemNameDiv = childDiv
								.getElementsByTagName('div')[1], matItemName = matItemNameDiv
								.getElementsByTagName('select')[0];
						matItemName.setAttribute('id', 'matItemNameSelect'
								+ newNum);
						// end of setting id on ItemName select fields
						
						// start of setting id on ItemName input fields
						var matItemNameInputDiv = childDiv
								.getElementsByTagName('div')[1], matItemNameInput = matItemNameInputDiv
								.getElementsByTagName('input')[0];
						matItemNameInput.setAttribute('id', 'matItemName'+ newNum);
						// end of setting id on ItemName input fields
						

						// start of setting id on matQuantity fields
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
					'.btn-add-cost-ins',
					function(e) {
						// e.preventDefault();
						var count = parseInt($('#insCount').val());
						var num = $('.clonedAreaa').length;
						var newNum = num + 1;
						newNum += count;
						var controlForm = $('.controls1 div:first'), currentEntry = $(
								this).parents('.entry1:first'), newEntry = $(
								currentEntry.clone().attr('id',
										'costIns' + newNum).addClass(
										'clonedAreaa')).appendTo(controlForm);

						// set dynamic id on item qty fields

						var mainDiv = document.getElementById('costIns'
								+ newNum), childDiv = mainDiv
								.getElementsByTagName('div')[0];

						// start of seting id on insCategory fields
						var insCategoryDiv = childDiv
								.getElementsByTagName('div')[0], insCategory = insCategoryDiv
								.getElementsByTagName('select')[0];
						insCategory.setAttribute('id', 'insCategory' + newNum);
						// end of seting id on insCategory fields
						
						// start of seting id on ItemName fields
						var insItemNameDiv = childDiv
								.getElementsByTagName('div')[1], insItemName = insItemNameDiv
								.getElementsByTagName('input')[0];
						insItemName.setAttribute('id', 'insItemName' + newNum);
						// end of seting id on ItemName fields
						
						// start of seting id on insItemNameSelect fields
						var insItemNameSelectDiv = childDiv
								.getElementsByTagName('div')[1], insItemNameSelect = insItemNameSelectDiv
								.getElementsByTagName('select')[0];
						insItemNameSelect.setAttribute('id', 'insItemNameSelect' + newNum);
						// end of seting id on insItemNameSelect fields

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

						controlForm
								.find('.entry1:not(:last) .btn-add-cost-ins')
								.removeClass('btn-add-cost-ins')
								.addClass('btn-remove1')
								.removeClass('btn-success')
								.addClass('btn-danger')
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
						var count = parseInt($('#recCount').val());
						var num = $('.clonedArea2').length;
						var newNum = num + 1;
						newNum += count;

						var controlForm = $('.controls2 div:first'), currentEntry = $(
								this).parents('.entry2:first'), newEntry = $(
								currentEntry.clone().attr('id',
										'myAreaa' + newNum).addClass(
										'clonedArea2')).appendTo(controlForm);

						// set dynamic id on item qty fields

						var mainDiv = document.getElementById('myAreaa'
								+ newNum), childDiv = mainDiv
								.getElementsByTagName('div')[0];

						// start of seting id on matQuantity fields
						var insCategoryDiv = childDiv
								.getElementsByTagName('div')[0], insCategory = insCategoryDiv
								.getElementsByTagName('select')[0];
						insCategory.setAttribute('id', 'recCategory' + newNum);

						// start of seting id on insItemName fields
						var insItemNameDiv = childDiv
								.getElementsByTagName('div')[1], insItemName = insItemNameDiv
								.getElementsByTagName('input')[0];
						insItemName.setAttribute('id', 'recItemName' + newNum);
						// end of seting id on insItemName fields
						
						// start of seting id on insItemNameSelect fields
						var insItemNameSelectDiv = childDiv
								.getElementsByTagName('div')[1], insItemNameSelect = insItemNameSelectDiv
								.getElementsByTagName('select')[0];
						insItemNameSelect.setAttribute('id', 'recItemNameSelect' + newNum);
						// end of seting id on insItemName fields
						
						// start of seting id on recQuantity fields
						var unitDiv = childDiv.getElementsByTagName('div')[2], unitInput = unitDiv
								.getElementsByTagName('input')[0];
						unitInput.setAttribute('id', 'recUom' + newNum);
						var quantityDiv = childDiv.getElementsByTagName('div')[3], quantityInput = quantityDiv
								.getElementsByTagName('input')[0];
						quantityInput
								.setAttribute('id', 'recQuantity' + newNum);
						// end of seting id on recQuantity fields

						// start of seting id on recUnitPrice fields
						var unitPriceDiv = childDiv.getElementsByTagName('div')[4], unitPriceInput = unitPriceDiv
								.getElementsByTagName('input')[0];
						unitPriceInput.setAttribute('id', 'recUnitPrice'
								+ newNum);
						// end of seting id on recUnitPrice fields

						// start of seting id on unitCost fields
						var matAmountDiv = childDiv.getElementsByTagName('div')[5], matAmountInput = matAmountDiv
								.getElementsByTagName('input')[0];
						matAmountInput.setAttribute('id', 'recAmount' + newNum);
						// end of seting id on unitCost fields
						// end of seting id on totalCost fields

						newEntry.find('input').val('');

						controlForm
								.find('.entry2:not(:last) .btn-add2')
								.removeClass('btn-add2')
								.addClass('btn-remove2')
								.removeClass('btn-success')
								.addClass('btn-danger')
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
						var count = parseInt($('#misCount').val());
						var num = $('.clonedArea3').length;
						var newNum = num + 1;
						newNum += count;

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

						controlForm
								.find('.entry3:not(:last) .btn-add3')
								.removeClass('btn-add3')
								.addClass('btn-remove3')
								.removeClass('btn-success')
								.addClass('btn-danger')
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

		$("#misItemName0").autocomplete({
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

function matHide(value) {
	value= parseInt(value);
	var div = document.getElementById('myArea' + value);
	// alert(value);
	if (div.style.display !== 'none') {
		div.style.display = 'none';
	} else {
		div.style.display = 'block';
	}
	document.getElementById('matItemName' + value).value = "";
	document.getElementById('matQuantity' + value).value = 0;
	document.getElementById('matUnitPrice' + value).value = 0;
	document.getElementById('matAmount' + value).value = 0;

}
function insHide(value) {

	var div = document.getElementById('costIns' + value);
	// alert(value);
	if (div.style.display !== 'none') {
		div.style.display = 'none';
	} else {
		div.style.display = 'block';
	}
	document.getElementById('insItemName' + value).value = "";
	document.getElementById('insQuantity' + value).value = 0;
	document.getElementById('insUnitPrice' + value).value = 0;
	document.getElementById('insAmount' + value).value = 0;

}
function recHide(value) {

	var div = document.getElementById('myAreaa' + value);
	// alert(value);
	if (div.style.display !== 'none') {
		div.style.display = 'none';
	} else {
		div.style.display = 'block';
	}
	document.getElementById('recItemName' + value).value = "";
	document.getElementById('recQuantity' + value).value = 0;
	document.getElementById('recUnitPrice' + value).value = 0;
	document.getElementById('recAmount' + value).value = 0;

}
function misHide(value) {

	var div = document.getElementById('myArea3' + value);
	// alert(value);
	if (div.style.display !== 'none') {
		div.style.display = 'none';
	} else {
		div.style.display = 'block';
	}
	document.getElementById('misItemName' + value).value = "";
	document.getElementById('misQuantity' + value).value = 0;
	document.getElementById('misUnitPrice' + value).value = 0;
	document.getElementById('misAmount' + value).value = 0;

}