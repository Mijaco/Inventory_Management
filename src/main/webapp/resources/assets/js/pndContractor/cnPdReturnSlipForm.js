function itemLeaveChange(element) {
	var id = $(element).attr("id");
	var name = $(element).attr('name');
	var sequence = id.substr(name.length);
	var item_id = $("#" + id).val();
	$
			.ajax({
				url : $("#contextPath").val()
						+ '/cs/itemRecieved/viewInventoryItem.do',
				data : "{id:" + item_id + "}",
				contentType : "application/json",
				success : function(data) {
					var inventoryItem = JSON.parse(data);
					$(element).closest("div").parent().parent().find(
							'.itemCode').val(inventoryItem.itemId);
					$(element).closest("div").parent().parent().find('.uom')
							.val(inventoryItem.unitCode);
					$(element).closest("div").parent().parent().find(
							'.description').val(inventoryItem.itemName);
					$(element).closest("div").parent().parent().find(
							'.currentStock').val(inventoryItem.currentStock);
				},
				error : function(data) {
					alert("Server Error");
				},
				type : 'POST'
			});
}

function categoryLeaveChange(element) {
	var id = $(element).attr("id");
	var name = $(element).attr('name');
	var sequence = id.substr(name.length);
	var categoryId = $("#" + id).val();
	$.ajax({
		url : $("#contextPath").val()
				+ '/cs/itemRecieved/viewInventoryItemCategory.do',
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
						.text(this.itemId+' - '+this.itemName));
			});
		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
}

function setTotalCost(element) {
	var id = $(element).attr("id");
	var name = $(element).attr('name');
	var sequence = id.substr(name.length);

	var ns = $('#qtyNewServiceable' + sequence).val().trim();
	var nsFloat = parseFloat(ns).toFixed(3);

	var rs = $('#qtyRecServiceable' + sequence).val().trim();
	var rsFloat = parseFloat(rs).toFixed(3);

	var us = $('#qtyUnServiceable' + sequence).val().trim();
	var usFloat = parseFloat(us).toFixed(3);
	var x = parseFloat(nsFloat);
	var y = parseFloat(rsFloat);
	var z = parseFloat(usFloat);

	var totalCost = (x + y + z).toFixed(3);
	$('#totalReturn' + sequence).val(totalCost);

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

						// start of setting id on category fields
						var categoryDiv = childDiv.getElementsByTagName('div')[0], categoryInput = categoryDiv
								.getElementsByTagName('select')[0];
						categoryInput.setAttribute('id', 'category' + newNum);
						// end of setting id on category fields

						// start of setting id on itemName fields
						var itemNameDiv = childDiv.getElementsByTagName('div')[1], itemNameInput = itemNameDiv
								.getElementsByTagName('select')[0];
						itemNameInput.setAttribute('id', 'itemName' + newNum);
						// end of setting id on itemName fields

						// start of setting id on qtyNewServiceable fields
						var currentStockDiv = childDiv
								.getElementsByTagName('div')[4], currentStockInput = currentStockDiv
								.getElementsByTagName('input')[0];
						currentStockInput.setAttribute('id', 'qtyNewServiceable'
								+ newNum);
						// end of setting id on qtyNewServiceable fields

						// start of setting id on qtyRecServiceable fields
						var quantityRequiredDiv = childDiv
								.getElementsByTagName('div')[5], quantityRequiredInput = quantityRequiredDiv
								.getElementsByTagName('input')[0];
						quantityRequiredInput.setAttribute('id',
								'qtyRecServiceable' + newNum);
						// end of setting id on qtyRecServiceable fields

						// start of setting id on qtyUnServiceable fields
						var quantityIssuedDiv = childDiv
								.getElementsByTagName('div')[6], quantityIssuedInput = quantityIssuedDiv
								.getElementsByTagName('input')[0];
						quantityIssuedInput.setAttribute('id', 'qtyUnServiceable'
								+ newNum);
						// end of setting id on qtyUnServiceable fields
						
						// start of setting id on totalReturn fields
						var quantityIssuedDiv = childDiv
								.getElementsByTagName('div')[7], quantityIssuedInput = quantityIssuedDiv
								.getElementsByTagName('input')[0];
						quantityIssuedInput.setAttribute('id', 'totalReturn'
								+ newNum);
						// end of setting id on totalReturn fields
						
						
						newEntry.find('input').val('');
						$('#qtyNewServiceable'+ newNum).val(0);
						$('#qtyRecServiceable'+ newNum).val(0);
						$('#qtyUnServiceable'+ newNum).val(0);
						$('#totalReturn'+ newNum).val(0);

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