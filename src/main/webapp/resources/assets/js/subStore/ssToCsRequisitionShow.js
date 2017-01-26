function editItem(id){	

	$.ajax({								
		url : $('#contextPath').val() + '/ss/itemEdit.do',
		data : "{id:"+id+"}",
		contentType : "application/json",
		success : function(data) {									
			var item = JSON.parse(data);
			//alert(item.itemCode);
			$("#modal_itemId").val(item.itemCode);
			$("#modal_quantityRequired").val(item.quantityRequired);
			$("#modal_id").val(item.id);					
			$("#modal_requisitionId").val(item.centralStoreRequisitionId);
			$("#modal_requisitionTo").val(item.requisitionTo);					

		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
}


//Added by: Ihteshamul Alam
function deleteItemById(id, requisitionNo) {
//	alert(id);
	if( confirm("Do you want to delete this item?") == true ) {
		var baseURL = $('#contextPath').val();
		var path = baseURL + "/ss/deleteItemById.do";
		var param = {
				'id'			: id,
				'requisitionNo' : requisitionNo
		}
		postSubmit( path, param, "POST" );
	}
}

function receivedQtyToRemainQty(element) {	
	
	var expected = parseInt($('#modal_expectedQuantity').val().trim(), 10);

	var received = parseInt($(element).val().trim(), 10);

	if (received > expected) {
		$(element).val($('#modal_expectedQuantity').val());
	}

	/* $('#modal_reminingQuantity').val(
			$('#modal_expectedQuantity').val()
					- $('#modal_receivedQuantity').val());*/

}

//Custom Modal Init

$(function() {
	$("#myDialogeBox").dialog({
		autoOpen : false,
		closeOnEscape : false,
		modal : true,
		draggable : false,
		resizable : false,
		position : {
			my : "center",
			at : "center",
			of : window
		},
		show : {
			effect : "blind",
			duration : 10
		},
		hide : 'blind',
		width : $(window).width() - 100,
		buttons : [ {
			text : 'Save',
			click : function() {
				// send post request
				$("#saveLSStoreRequisition").submit();
				// $('#myDialogeBox').empty();
			}
		}, {
			text : 'Close',
				click : function() {
					$(this).dialog("close");
				}
			} ]
		});
	});

//Show modal
function openDialoge() {
	$('#myDialogeBox').removeClass('hide');
	$(".ui-dialog-titlebar").hide();
	$("#myDialogeBox").dialog("open");
}

function itemLeaveChange(element) {

	var temp = $(element).closest("div").parent().parent().attr("id");
	var idPrefix="myArea";
	 var sequence = temp.substr(idPrefix.length);
	var e = $(element).closest("div").parent().parent().find('.itemName').attr(
			'id', sequence);
	// var e = document.getElementById('' + sequence);
	var e1 = e[0];
	var item_id = e1.options[e1.selectedIndex].value;
	var khathId = $('#khathId').val();
	//var s="ledgerName"+sequence;
	//var ledgerName = $("#"+s).val();
	var mainData={
			id : item_id,
			khathId : khathId
			//ledgerName : ledgerName
	}
	
	var cData = JSON.stringify(mainData);
	
	$.ajax({
		// url : 'procurement/requisition/viewInventoryItem.do',
		url : 'ssViewInventoryItem.do',
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
						.text(this.itemId+' - '+this.itemName));
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
			.toFixed(2);
	currentStock = parseFloat(currentStock);

	var temp = $('#quantityRequired' + sequence).val().trim();
	var quantityRequired = parseFloat(temp).toFixed(2);
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
	var unitCostFloat = parseFloat(unitCost).toFixed(2);

	var qty = $('#quantityRequired' + sequence).val().trim();
	var qtyFloat = parseFloat(qty).toFixed(2);

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
						
						// start of setting id on category fields
						var categoryDiv = childDiv.getElementsByTagName('div')[0], categoryInput = categoryDiv.getElementsByTagName('select')[0];
						categoryInput.setAttribute('id', 'category' + newNum);
						
						// start of setting id on itemName fields
						var itemNameDiv = childDiv.getElementsByTagName('div')[1], itemNameInput = itemNameDiv
						.getElementsByTagName('select')[0];
						itemNameInput.setAttribute('id', 'itemName' + newNum);
						
						// start of seting id on itemCode fields
						var itemCodeDiv = childDiv.getElementsByTagName('div')[2], itemCodeInput = itemCodeDiv
						.getElementsByTagName('input')[0];
						itemCodeInput.setAttribute('id', 'itemCode' + newNum);

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

						// start of seting id on remarks fields
						var remarksDiv = childDiv.getElementsByTagName('div')[6], remarksInput = remarksDiv
								.getElementsByTagName('input')[0];
						remarksInput.setAttribute('id', 'remarks' + newNum);
						// end of seting id on remarks fields

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