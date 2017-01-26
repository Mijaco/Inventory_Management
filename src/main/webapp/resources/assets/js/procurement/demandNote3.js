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

						// start of setting id on itemCode
						var descriptionDiv = childDiv.getElementsByTagName('div')[1], descriptionInput = descriptionDiv
								.getElementsByTagName('input')[0];
						descriptionInput.setAttribute('id', 'description' + newNum);
						// end of setting id on itemCode

						// start of setting id on uom
						var uomDiv = childDiv.getElementsByTagName('div')[1], uomInput = uomDiv
								.getElementsByTagName('input')[0];
						uomInput.setAttribute('id', 'uom' + newNum);
						// end of setting id on uom

						// start of setting id on requiredQuantity fields
						var requiredQuantityDiv = childDiv
								.getElementsByTagName('div')[2], requiredQuantityInput = requiredQuantityDiv
								.getElementsByTagName('input')[0];
						requiredQuantityInput.setAttribute('id',
								'requiredQuantity' + newNum);
						// end of setting id on requiredQunatity fields

						// start of setting id on unitCost
						var unitCostDiv = childDiv.getElementsByTagName('div')[3], unitCostInput = unitCostDiv
								.getElementsByTagName('input')[0];
						unitCostInput.setAttribute('id', 'unitCost' + newNum);
						// end of setting id on unitCost

						// start of setting id on totalCost
						var totalCostDiv = childDiv.getElementsByTagName('div')[4], totalCostInput = totalCostDiv
								.getElementsByTagName('input')[0];
						totalCostInput.setAttribute('id', 'totalCost' + newNum);
						// end of setting id on totalCost

						newEntry.find('input').val('');				
						
					}).on('click', '.btn-remove', function(e) {
				$(this).parents('.entry:first').remove();
				return false;
			});

});

function itemLeaveChange(element) {
	var id = $(element).attr("id");
	var name = $(element).attr("name");
	var sequence = id.substr(name.length);
	var contextPath = $("#contextPath").val();
	var item_id = $("#" + id).val();

	$.ajax({
		url : contextPath + '/workOrder/viewInventoryItem.do',
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
		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
}

function categoryLeaveChange(element) {
	var id = $(element).attr("id");
	var name = $(element).attr("name");
	var sequence = id.substr(name.length);
	var contextPath = $("#contextPath").val();
	var categoryId = $("#" + id).val();

	$.ajax({
		url : contextPath + '/workOrder/viewInventoryItemCategory.do',
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
						.text(this.itemName));
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
	var name = $(element).attr("name");
	var sequence = id.substr(name.length);
	var contextPath = $("#contextPath").val();

	var requiredQuantity = parseFloat($("#requiredQuantity" + sequence).val() || 0);
	var unitCost = parseFloat($("#unitCost" + sequence).val() || 0);
	var totalCost = requiredQuantity * unitCost;
	$("#totalCost" + sequence).val(totalCost.toFixed(2));

}