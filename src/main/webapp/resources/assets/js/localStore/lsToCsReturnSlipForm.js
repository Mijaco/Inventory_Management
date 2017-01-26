function itemLeaveChange(element) {

	var id = $(element).attr('id');
	var name = $(element).attr('name');
	var sequence = id.substr(name.length);

	var item_id = $("#" + id).val();
	
	$.ajax({
		url : 'viewInventoryItem.do',
		data : "{id:" + item_id + "}",
		contentType : "application/json",
		success : function(data) {
			var inventoryItem = JSON.parse(data);
			$(element).closest("div").parent().parent().find('.itemCode').val(
					inventoryItem.itemId);
			$(element).closest("div").parent().parent().find('.uom').val(
					inventoryItem.unitCode);
			$(element).closest("div").parent().parent().find('.description')
					.val(inventoryItem.itemName);
			/*$(element).closest("div").parent().parent().find('.currentStock')
			.val(inventoryItem.currentStock);*/
			
		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
}

function categoryLeaveChange(element) {
	var id = $(element).attr('id');
	var name = $(element).attr('name');
	var sequence = id.substr(name.length);

	var categoryId = $("#" + id).val();

	$.ajax({

		url : 'viewInventoryItemCategory.do',
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
						.text(this.itemId + ' - ' + this.itemName));
			});
		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
}

function setTotalCost(element) {
	var id = $(element).attr('id');
	var name = $(element).attr('name');
	var sequence = id.substr(name.length);

	var nsQty = $('#qtyNewServiceable' + sequence).val().trim();
	var nsQtyFloat = parseFloat(nsQty).toFixed(3);

	var rsQty = $('#qtyRecServiceable' + sequence).val().trim();
	var rsQtyFloat = parseFloat(rsQty).toFixed(3);

	var usQty = $('#qtyUnServiceable' + sequence).val().trim();
	var usQtyFloat = parseFloat(usQty).toFixed(3);

	var x = parseFloat(nsQtyFloat);
	var y = parseFloat(rsQtyFloat);
	var z = parseFloat(usQtyFloat);

	var totalReturn = (x + y + z).toFixed(3);
	$('#totalReturn' + sequence).val(totalReturn);

}

$(function() {
	$(document)
			.on(
					'click',
					'.btn-add',
					function(e) {
						var num = $('.clonedArea').length;
						var newNum = num + 1;

						var controlForm = $('.controls div:first'), currentEntry = $(
								this).parents('.entry:first'), newEntry = $(
								currentEntry.clone().attr('id',
										'myArea' + newNum).addClass(
										'clonedArea')).appendTo(controlForm);

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
								.getElementsByTagName('select')[0], descriptionInput = itemNameDiv
								.getElementsByTagName('input')[0];
						itemNameInput.setAttribute('id', 'itemName' + newNum);
						descriptionInput.setAttribute('id', 'description'
								+ newNum);
						// end of setting id on itemName fields

						// start of setting id on itemCode fields
						var itemCodeDiv = childDiv.getElementsByTagName('div')[2], itemCodeInput = itemCodeDiv
								.getElementsByTagName('input')[0];
						itemCodeInput.setAttribute('id', 'itemCode' + newNum);
						// end of setting id on itemCode fields

						// start of setting id on uom fields
						var uomDiv = childDiv.getElementsByTagName('div')[3], uomInput = uomDiv
								.getElementsByTagName('input')[0];
						uomInput.setAttribute('id', 'uom' + newNum);
						// end of setting id on uom fields

						// start of setting id on qtyNewServiceable fields
						var qtyNewServiceableDiv = childDiv
								.getElementsByTagName('div')[4], qtyNewServiceableInput = qtyNewServiceableDiv
								.getElementsByTagName('input')[0];
						qtyNewServiceableInput.setAttribute('id',
								'qtyNewServiceable' + newNum);
						// end of setting id on qtyNewServiceable fields

						// start of setting id on qtyRecServiceable fields
						var qtyRecServiceableDiv = childDiv
								.getElementsByTagName('div')[5], qtyRecServiceableInput = qtyRecServiceableDiv
								.getElementsByTagName('input')[0];
						qtyRecServiceableInput.setAttribute('id',
								'qtyRecServiceable' + newNum);
						// end of setting id on qtyRecServiceable fields
						
						// start of setting id on qtyUnServiceable fields
						var qtyUnServiceableDiv = childDiv
								.getElementsByTagName('div')[6], qtyUnServiceableInput = qtyUnServiceableDiv
								.getElementsByTagName('input')[0];
						qtyUnServiceableInput.setAttribute('id',
								'qtyUnServiceable' + newNum);
						// end of setting id on qtyUnServiceable fields

						// start of setting id on totalReturn fields
						var totalReturnDiv = childDiv
								.getElementsByTagName('div')[7], totalReturnInput = totalReturnDiv
								.getElementsByTagName('input')[0];
						totalReturnInput.setAttribute('id', 'totalReturn'
								+ newNum);
						// end of setting id on totalReturn fields
						
						//errTotRet
						var errTotRetDiv = childDiv
						.getElementsByTagName('div')[7], errTotRetInput = totalReturnDiv
						.getElementsByTagName('strong')[0];
						errTotRetInput.setAttribute('id', 'errTotRet'
						+ newNum);

						newEntry.find('input').val('');
						$('#qtyNewServiceable' + newNum).val(0);
						$('#qtyRecServiceable' + newNum).val(0);
						$('#qtyUnServiceable' + newNum).val(0);
						$('#totalReturn' + newNum).val(0);

						/*
						 * controlForm .find('.entry:not(:last) .btn-add')
						 * .removeClass('btn-add') .addClass('btn-remove')
						 * .removeClass('btn-success') .addClass('btn-danger')
						 * .html( '<span class="glyphicon glyphicon-minus"></span>');
						 */
					}).on('click', '.btn-remove', function(e) {
				$(this).parents('.entry:first').remove();

				// e.preventDefault();
				return false;
			});

});
