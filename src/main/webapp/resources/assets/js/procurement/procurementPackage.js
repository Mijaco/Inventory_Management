function checkAnnexureNo() {
	var annexureNo = $('#annexureNo').val();
	var contextPath = $('#contextPath').val();
	var saveButton = $('#saveButton');
//	alert(annexureNo);
	$.ajax({
		url : contextPath + '/mps/checkAnnexureNo.do',
		data : {"annexureNo":annexureNo},
		success : function(data) {
			//var result = JSON.parse(data);
			//alert(data);
			if (data == 'success') {
				if( $('.annexure').hasClass('hide') == false ) {
					$('.annexure').addClass('hide');
				}
				saveButton.prop("disabled", false);
			} else if( data == 'blank' ) {
				$('.annexure').removeClass('hide').text('This field is required');
				saveButton.prop("disabled", true);
			} else {
				$('.annexure').removeClass('hide').text('Annexure no. is used.');
				saveButton.prop("disabled", true);
			}
		},
		error : function(data) {
			alert(data);
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

						// start of setting id on description and itemName
						var itemNameDiv = childDiv.getElementsByTagName('div')[1], descriptionInput = itemNameDiv
								.getElementsByTagName('input')[0], itemNameInput = itemNameDiv
								.getElementsByTagName('select')[0];
						descriptionInput.setAttribute('id', 'description'
								+ newNum);
						itemNameInput.setAttribute('id', 'itemName' + newNum);
						// end of setting id on description and itemName

						// start of setting id on itemCode
						var itemCodeDiv = childDiv.getElementsByTagName('div')[2], itemCodeInput = itemCodeDiv
								.getElementsByTagName('input')[0];
						itemCodeInput.setAttribute('id', 'itemCode' + newNum);
						// end of setting id on itemCode

						// start of setting id on unit
						var unitDiv = childDiv.getElementsByTagName('div')[3], unitInput = unitDiv
								.getElementsByTagName('input')[0];
						unitInput.setAttribute('id', 'unit' + newNum);
						// end of setting id on unit

						// start of setting id on qunatity fields
						var qunatityDiv = childDiv.getElementsByTagName('div')[4], qunatityInput = qunatityDiv
								.getElementsByTagName('input')[0];
						qunatityInput.setAttribute('id', 'qunatity' + newNum);
						// end of setting id on qunatity fields

						// start of setting id on unitCost
						var unitCostDiv = childDiv.getElementsByTagName('div')[5], unitCostInput = unitCostDiv
								.getElementsByTagName('input')[0];
						unitCostInput.setAttribute('id', 'unitCost' + newNum);
						// end of setting id on unitCost

						// start of setting id on totalCost
						var totalCostDiv = childDiv.getElementsByTagName('div')[6], totalCostInput = totalCostDiv
								.getElementsByTagName('input')[0];
						totalCostInput.setAttribute('id', 'totalCost' + newNum);
						// end of setting id on totalCost

						/*
						 * // start of setting id on amount var amountDiv =
						 * childDiv.getElementsByTagName('div')[7], amountInput =
						 * amountDiv .getElementsByTagName('input')[0];
						 * amountInput.setAttribute('id', 'amount' + newNum); //
						 * end of setting id on amount
						 */
						newEntry.find('input').val('');
						/*
						newEntry.find('.qunatity').val(0.0);
						newEntry.find('.unitCost').val(0.0);
						newEntry.find('.totalCost').val(0.0);
						 newEntry.find('.amount').val(0.0); */

						/*
						 * controlForm .find('.entry:not(:last) .btn-add')
						 * .removeClass('btn-add') .addClass('btn-remove')
						 * .removeClass('btn-success') .addClass('btn-danger')
						 * .html( '<span class="glyphicon glyphicon-minus"></span>');
						 */
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
			$(element).closest("div").parent().parent().find('.unit').val(
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
	if (categoryId == 'others') {
		getNonCodedInput(sequence);
	} else {
		$("#description"+sequence).attr("type","hidden");	
		$("#itemName"+sequence).removeClass("hide");
		$("#unit"+sequence).attr("readonly", true);
		
		$.ajax({
			url : contextPath + '/workOrder/viewInventoryItemCategory.do',
			data : "{categoryId:" + categoryId + "}",
			contentType : "application/json",
			success : function(data) {
				var itemList = JSON.parse(data);
				var itemNames = $(element).closest("div").parent().parent()
						.find('.itemName');
				itemNames.empty();
				itemNames.append($("<option></option>").attr("value", '').text(
						'Select Item'));
				$.each(itemList, function(id, itemName) {
					itemNames.append($("<option></option>").attr("value",
							this.id).text(this.itemId+" - "+this.itemName));
				});
			},
			error : function(data) {
				alert("Server Error");
			},
			type : 'POST'
		});
	}
}

$( document ).ready( function() {
	$('#qtyFlag').change( function() {
//		$('#estimatedCost').val( '' );
		$('#procurementQty').val( '' );
		
		var quantity = 0;
		if( $('#qtyFlag').val() == '1' ) {
			$('.qunatity').each( function() {
				if( $(this).val() == null || $.trim( $(this).val() ) == '' ) { }
				else {
					quantity += parseFloat($(this).val());
				}
			});
			$('#procurementQty').val( quantity.toFixed(2) );
		}
	});
});

function setTotalCost(element) {
	var id = $(element).attr("id");
	var name = $(element).attr("name");
	var sequence = id.substr(name.length);
	var contextPath = $("#contextPath").val();

	var qunatity = parseFloat($("#qunatity" + sequence).val() || 0);
	var unitCost = parseFloat($("#unitCost" + sequence).val() || 0);
	var totalCost = qunatity * unitCost;
	$("#totalCost" + sequence).val(totalCost.toFixed(2));
	
	var quantity = 0, ttCost = 0;
	
	if( $('#qtyFlag').val() == '1' ) {
		$('.qunatity').each( function() {
			if( $(this).val() == null || $.trim( $(this).val() ) == '' ) { }
			else {
				quantity += parseFloat($(this).val());
			}
		});
		//alert('NOS');
	
		$('#procurementQty').val( quantity.toFixed(2) );
	}
	
	$('.totalCost').each( function() {
		if( $(this).val() == null || $.trim( $(this).val() ) == '' ) { }
		else {
			ttCost += parseFloat($(this).val());
		}
	});
	$('#estimatedCost').val( ttCost.toFixed(2) );

}

function getNonCodedInput(sequence){
	
	
	$("#description"+sequence).attr("type","text");	
	$("#itemName"+sequence).addClass("hide");
	$("#unit"+sequence).attr("readonly", false);	
	// alert(sequence);
	
	
}