function itemLeaveChange(element) {

	var temp = $(element).closest("div").parent().parent().attr("id");
	var sequence = temp.substr(temp.length - 1);

	var e = $(element).closest("div").parent().parent().find('.itemName').attr(
			'id', sequence);
	// var e = document.getElementById('' + sequence);
	var e1 = e[0];
	var item_id = e1.options[e1.selectedIndex].value;
	
	var requisitionTo = $("#requisitionTo").val();
	var contextPath = $("#contextPath").val();
	$.ajax({
		url : contextPath+'/c2ls/viewInventoryItem.do',
		data : "{id:" + item_id + ", requisitionTo:"+requisitionTo+"}",
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
					.val(inventoryItem.currentStock);
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
	
	var requsitionTo = $("#requsitionTo").val();
	var contextPath = $("#contextPath").val();
	// alert(categoryId);
	$.ajax({

		url : contextPath+'/c2ls/viewInventoryItemCategory.do',
		data : "{categoryId:" + categoryId +"}",
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
						.text(this.itemId+' - ' + this.itemName));
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
	var sequence = id.substr(id.length - 1);

	// var expected = parseInt($('#currentStock' + sequence).val().trim(), 10);

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

						// start of seting id on currentStock fields
						var currentStockDiv = childDiv
								.getElementsByTagName('div')[4], currentStockInput = currentStockDiv
								.getElementsByTagName('input')[0];
						currentStockInput.setAttribute('id', 'currentStock'
								+ newNum);
						// end of seting id on currentStock fields

						// start of seting id on quantityRequired fields
						var quantityRequiredDiv = childDiv
								.getElementsByTagName('div')[5], quantityRequiredInput = quantityRequiredDiv
								.getElementsByTagName('input')[0];
						quantityRequiredInput.setAttribute('id',
								'quantityRequired' + newNum);
						// end of seting id on quantityRequired fields
						
						// start of seting id on reqQtyError fields
						var reqQtyErrorDiv = childDiv
						.getElementsByTagName('div')[5], reqQtyErrorInput = quantityRequiredDiv
						.getElementsByTagName('strong')[0];
						reqQtyErrorInput.setAttribute('id',
								'reqQtyError' + newNum);
						// end of seting id on reqQtyError fields

						// start of seting id on quantityIssued fields
						/*var quantityIssuedDiv = childDiv
								.getElementsByTagName('div')[6], quantityIssuedInput = quantityIssuedDiv
								.getElementsByTagName('input')[0];
						quantityIssuedInput.setAttribute('id', 'quantityIssued'
								+ newNum);*/
						// end of seting id on quantityIssued fields

						// start of seting id on unitCost fields
						/*var unitCostDiv = childDiv.getElementsByTagName('div')[7], unitCostInput = unitCostDiv
								.getElementsByTagName('input')[0];
						unitCostInput.setAttribute('id', 'unitCost' + newNum);*/
						// end of seting id on unitCost fields

						// start of seting id on totalCost fields
						/*var totalCostDiv = childDiv.getElementsByTagName('div')[8], totalCostInput = totalCostDiv
								.getElementsByTagName('input')[0];
						totalCostInput.setAttribute('id', 'totalCost' + newNum);*/
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

//Added by: Taleb

$( document ).ready( function() {
	$( '#saveButton' ).click( function() {
		var counter = 0;
		var haserror = false;
		$('.quantityRequired').each( function() {
			if( $(this).val() == null || $.trim( $(this).val() ) == '' || $.trim( $(this).val() ) == '0' || $.trim( $(this).val() ) == '0.0' || $.trim( $(this).val() ) == '0.00' || $.trim( $(this).val() ) == '0.000' ) {
				
				var id = this.id;
				var name = this.name;
				var sequence = id.substr( name.length );
				$('#reqQtyError'+sequence).removeClass('hide');
				counter++;
			} else {
				
				var id = this.id;
				var name = this.name;
				var sequence = id.substr( name.length );
				$('#reqQtyError'+sequence).addClass('hide');
			}
		});
		
		if( counter > 0 ) {
			haserror = true;
		}
		
		if( haserror == true ) {
			return;
		} else {
			$('#saveButton').prop('disabled', true);
			$('#storeRequisitionFormSave').submit();
		}
		
	});
} );