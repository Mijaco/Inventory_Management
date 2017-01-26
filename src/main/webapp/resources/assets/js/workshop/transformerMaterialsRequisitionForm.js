/*function itemLeaveChange(element) {

	var temp = $(element).closest("div").parent().parent().attr("id");
	var idPrefix = "myArea";
	var sequence = temp.substr(idPrefix.length);
	var e = $(element).closest("div").parent().parent().find('.itemName').attr(
			'id', sequence);
	// var e = document.getElementById('' + sequence);
	var e1 = e[0];
	var item_id = e1.options[e1.selectedIndex].value;
	var khathId = $('#khathId').val();
	// var s="ledgerName"+sequence;
	// var ledgerName = $("#"+s).val();
	var mainData = {
		id : item_id,
		khathId : khathId
	// ledgerName : ledgerName
	}

	var cData = JSON.stringify(mainData);

	$.ajax({
		// url : 'procurement/requisition/viewInventoryItem.do',
		url : 'wsViewInventoryItem.do',
		data : cData,
		contentType : "application/json",
		success : function(data) {
			var inventoryItem = JSON.parse(data);
			$(element).closest("div").parent().parent().find('.itemCode').val(
					inventoryItem.itemId);
			$(element).closest("div").parent().parent().find('.uom').val(
					inventoryItem.unitCode);
			$(element).closest("div").parent().parent().find('.description')
					.val(inventoryItem.itemName);
			$(element).closest("div").parent().parent().find('.currentStock')
					.val(inventoryItem.currentStock.toFixed(3));
		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
}*/

function categoryLeaveChange(element) {

	var temp = $(element).closest("div").parent().parent().attr("id");
	var sequence = temp.substr(temp.length - 1)

	$(element).closest("div").parent().parent().find('.category').attr('id',
			sequence);
	var e = document.getElementById('' + sequence);
	var categoryId = e.options[e.selectedIndex].value;
	// alert(categoryId);
	$.ajax({

		url : 'ssViewInventoryItemCategory.do',
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
						.text(this.itemName + ' [' + this.itemId + ']'));
			});
		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
}

function reqQtyNotGreaterThenCurrentStock(element) {
	var id = $(element).attr('id');
	var name = $(element).attr('name');
	var sequence = id.substr(name.length);

	var currentStock = parseFloat($('#currentStock' + sequence).val().trim())
			.toFixed(3);
	currentStock = parseFloat(currentStock);

	var temp = $('#quantityRequired' + sequence).val().trim();
	var quantityRequired = parseFloat(temp).toFixed(3);
	quantityRequired = parseFloat(quantityRequired);

	// alert(currentStock);

	if (currentStock < quantityRequired) {
		$(element).val($('#currentStock' + sequence).val());
	}

}

function setTotalCost(element) {
	var id = $(element).attr('id');
	var sequence = id.substr(id.length - 1);

	var unitCost = $('#unitCost' + sequence).val().trim();
	var unitCostFloat = parseFloat(unitCost).toFixed(3);

	var qty = $('#quantityRequired' + sequence).val().trim();
	var qtyFloat = parseFloat(qty).toFixed(3);

	var totalCost = parseFloat(unitCostFloat * qtyFloat).toFixed(3);
	$('#totalCost' + sequence).val(totalCost);

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

						// start of seting id on currentStock fields
						var itemCodeSelectDiv = childDiv
								.getElementsByTagName('div')[0], itemCodeSelectInput = itemCodeSelectDiv
								.getElementsByTagName('select')[0];
						itemCodeSelectInput.setAttribute('id', 'itemCodeSelect'
								+ newNum);
						// end of seting id on currentStock fields
						
						// start of seting id on currentStock fields
						var itemNameDiv = childDiv
								.getElementsByTagName('div')[0], itemNameInput = itemNameDiv
								.getElementsByTagName('input')[0];
						itemNameInput.setAttribute('id', 'itemName'
								+ newNum);
						// end of seting id on currentStock fields
						
						// start of seting id on currentStock fields
						var itemCodeDiv = childDiv
								.getElementsByTagName('div')[1], itemCodeInput = itemCodeDiv
								.getElementsByTagName('input')[0];
						itemCodeInput.setAttribute('id', 'itemCode'
								+ newNum);
						// end of seting id on currentStock fields
						
						// start of seting id on currentStock fields
						var unitDiv = childDiv
								.getElementsByTagName('div')[2], unitInput = unitDiv
								.getElementsByTagName('input')[0];
						unitInput.setAttribute('id', 'unit'
								+ newNum);
						// end of seting id on currentStock fields
						
						// start of seting id on currentStock fields
						var currentStockDiv = childDiv
								.getElementsByTagName('div')[3], currentStockInput = currentStockDiv
								.getElementsByTagName('input')[0];
						currentStockInput.setAttribute('id', 'currentStock'
								+ newNum);
						// end of seting id on currentStock fields

						// start of seting id on quantityRequired fields
						var quantityRequiredDiv = childDiv
								.getElementsByTagName('div')[4], quantityRequiredInput = quantityRequiredDiv
								.getElementsByTagName('input')[0];
						quantityRequiredInput.setAttribute('id',
								'quantityRequired' + newNum);
						// end of seting id on quantityRequired fields

						newEntry.find('input').val('');
						newEntry.find('.quantityRequired').val(0.0);

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

$(document).ready(
		function() {

			$('#submit').click(function() {
				return validateForm();
			});

			function validateForm() {
				var row = $('.clonedArea').length;
				var rowCount = row + 1;

				var inputMessage = new Array("Quantity");

				$('.error').hide();

				for (var i = 0; i < rowCount; i++) {
					// var quantity = ;
					if ($('#quantityRequired' + i).val() == 0) {
						$('#quantityRequired' + i).after(
								'<span class="error" style="color:red"> Please enter '
										+ inputMessage[0] + '</span>');
						return false;
					}
				}

				return true;
			}
		});

function validContractorAllocation(element) {
	var id = $(element).attr('id');
	var name = $(element).attr('name');
	var index = id.substr(name.length);
	var contextPath = $('#contextPath').val();
	var path = contextPath + "/ws/checkJobSummary.do";
	var quantityRequired = $('#quantityRequired' + index).val();
	var itemCode = $('#itemCode' + index).val();

	$.ajax({
		type : "post",
		url : 'validContractor.do',
		async : false,
		data : 'itemId=' + itemCode,
		success : function(response) {
			if (response == false) {
				alert("Allocation of this contractor is not set yet");

				document.getElementById(quantityRequiredIdValue).value = 0;
				return false;
			}
		},
		error : function() {
		}
	});

	return true;
}

function itemLeaveChange(element) {
	//alert(itemName);
	var id = $(element).attr('id');
	var name = $(element).attr('name');
	 var index = id.substr(name.length);
	// var contextPath=document.getElementById('contextPath').value;
	//alert(document.getElementById('workOrderNumber').value+"----"+document.getElementById('itemCodeSelect'+index).value);
	$.ajax({
		type : "post",
		url : 'getItemData.do',
		async : false,
		data : 'itemCode=' + document.getElementById('itemCodeSelect'+index).value+'&workOrderNumber='+document.getElementById('workOrderNumber').value,
		success : function(response) {
			var s = response.split(":");
			//alert("#quantityRequired"+index);
			$("#itemCode"+index).val(s[0]);
			$("#itemName"+index).val(s[1]);
			$("#unit"+index).val(s[2]);
			if(s[3]>s[4]){
				alert("researve qty ["+s[4]+"] shortage than required qty ["+s[3]+"]");
			document.getElementById('quantityRequired'+index).focus();
			}
			document.getElementById('quantityRequired'+index).value=s[3];
			document.getElementById('currentStock'+index).value=s[4];
			
			//$("#quantityRequired"+index).val(s[3].toFixed(3));
			//$("#receivedDate").val(s[4]);
		},
		error : function() {
		}
	});

	return true;
}