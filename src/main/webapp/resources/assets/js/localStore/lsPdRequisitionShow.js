var contextPath = $('#contextPath').val();
var requisitionTo = $('#requisitionTo').val();


//Added by: Ihteshamul Alam
function deleteThis( id, requisitionNo, requisitionTo) {
	if( confirm("Do you want delete this item?") == true ) {
		var params = {
				'id' : id,
				'requisitionNo' : requisitionNo,
				'requisitionTo' : requisitionTo
		}
		
		var path = contextPath + "/ls/pd/deleteItem.do";
		
		postSubmit(path, params, "POST");
	}
}

function forwardToUpper(stateCode) {
	if( $('#justification').val() == null || $.trim( $('#justification').val() ) == '' ) {
		$('.justification').removeClass('hide');
		$('#justification').focus();
		return;
	} else {
		var justification = $('#justification').val();
		var requisitionNo = $('#requisitionNo').val();
		window.location = contextPath
				+ "/ls/pd/requisition/sendTo.do?requisitionNo=" + requisitionNo
				+ "&justification=" + justification + "&stateCode=" + stateCode + "&requisitionTo="+requisitionTo;
	}
}

function backToLower(stateCode) {
	if( $('#justification').val() == null || $.trim( $('#justification').val() ) == '' ) {
		$('.justification').removeClass('hide');
		$('#justification').focus();
		return;
	} else {
		var justification = $('#justification').val();
		var requisitionNo = $('#requisitionNo').val();
		window.location = contextPath
				+ "/ls/pd/requisition/backTo.do?requisitionNo=" + requisitionNo
				+ "&justification=" + justification + "&stateCode=" + stateCode + "&requisitionTo="+requisitionTo;
	}
}

function approveing() {
	if( $('#justification').val() == null || $.trim( $('#justification').val() ) == '' ) {
		$('.justification').removeClass('hide');
		$('#justification').focus();
		return;
	} else {
		$('#approveButton').prop('disabled', true);
		$('.justification').addClass('hide');
		var justification = $('#justification').val();
		var requisitionNo = $('#requisitionNo').val();
		
		var returnStateCode = $('#returnStateCode').val();
		window.location = contextPath
				+ "/ls/pd/requisition/approve.do?requisitionNo=" + requisitionNo
				+ "&justification=" + justification + "&return_state="
				+ returnStateCode + "&requisitionTo="+requisitionTo;
	}
}

//Added by: Ihteshaul Alam
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
//					$("#saveLSStoreRequisition").submit();
					// $('#myDialogeBox').empty();
					var counter = 0, haserror = false;
			    	
			    	$('.quantityRequired').each( function() {
			    		if( $(this).val() == null || $.trim( $(this).val() ) == '' || $.trim( $(this).val() ) == '0' ) {
			    			counter++;
			    			
			    			var id = this.id;
			    			var name = this.name;
			    			var sequence = id.substr( name.length );

							$( '#reqQty'+sequence ).removeClass('hide');
			    		} else {
			    			var id = this.id;
			    			var name = this.name;
			    			var sequence = id.substr( name.length );
			    			$( '#reqQty'+sequence ).addClass('hide');
			    		}
			    	});
			    	
			    	if( counter > 0 ) {
			    		haserror = true;
			    	}
			    	
			    	if( haserror == true ) {
			    		return;
			    	} else {
			    		$('#saveLSStoreRequisition').submit();
			    	}
				}
			}, {
				text : 'Close',
				click : function() {
					$(this).dialog("close");
				}
			} ]
		});

	});
	
function openDialoge() {
	$('#myDialogeBox').removeClass('hide');
	$(".ui-dialog-titlebar").hide();
	$("#myDialogeBox").dialog("open");
}

function itemLeaveChange(element) {
	var id = $(element).attr("id");
	var name = $(element).attr("name");
	var sequence = id.substr(name.length);
	var contextPath = $("#contextPath").val();
	var item_id = $("#" + id).val();
	var requisitionTo = $('#requisitionTo').val();
	var path = contextPath+"/ls/pd/viewInventoryItem.do";
	var khathId = $("#khathId").val();

	var cData = {
		id : item_id,
		requisitionTo : requisitionTo,
		khathId : khathId
	}

	$.ajax({

		url : path,
		data : JSON.stringify(cData),
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
			$(element).closest("div").parent().parent().find(
					'.quantityRequired').val(0);
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
	var path = contextPath+"/ls/viewInventoryItemCategory.do";

	$.ajax({

		url : path,
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

function reqQtyNotGreaterThenCurrentStock(element) {
	var id = $(element).attr('id');
	var name = $(element).attr('name');
	var sequence = id.substr(name.length);

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

						// start of setting id on category dropdown
						var categoryDiv = childDiv.getElementsByTagName('div')[0], categoryInput = categoryDiv
								.getElementsByTagName('select')[0];
						categoryInput.setAttribute('id', 'category' + newNum);

						// start of setting id on itemId dropdown
						var itemIdDiv = childDiv.getElementsByTagName('div')[1], itemIdInput = itemIdDiv
								.getElementsByTagName('select')[0];
						itemIdInput.setAttribute('id', 'itemId' + newNum);

						// start of setting id on itemCode fields
						var itemCodeDiv = childDiv.getElementsByTagName('div')[2], itemCodeInput = itemCodeDiv
								.getElementsByTagName('input')[0];
						itemCodeInput.setAttribute('id', 'itemCode' + newNum);

						// start of setting id on expectedQty fields
						var expectedQtyDiv = childDiv
								.getElementsByTagName('div')[4], expectedQtyInput = expectedQtyDiv
								.getElementsByTagName('input')[0];
						expectedQtyInput.setAttribute('id', 'currentStock'
								+ newNum);
						// end of setting id on expectedQty fields

						// start of setting id on receivedQty fields
						var receivedQtyDiv = childDiv
								.getElementsByTagName('div')[5], receivedQtyInput = receivedQtyDiv
								.getElementsByTagName('input')[0];
						receivedQtyInput.setAttribute('id', 'quantityRequired'
								+ newNum);
						// end of seting id on receivedQty fields

						// reqQty
						var reqQtyDiv = childDiv.getElementsByTagName('div')[5], reqQtyInput = reqQtyDiv
								.getElementsByTagName('strong')[0];
						reqQtyInput.setAttribute('id', 'reqQty' + newNum);

						// start of seting id on remainingQty fields
						/*
						 * var remainingQtyDiv = childDiv
						 * .getElementsByTagName('div')[6], remainingQtyInput =
						 * remainingQtyDiv .getElementsByTagName('input')[0];
						 * remainingQtyInput.setAttribute('id', 'unitCost' +
						 * newNum);
						 */
						// end of seting id on remainingQty fields
						// start of seting id on unitCost fields
						/*
						 * var unitCostDiv =
						 * childDiv.getElementsByTagName('div')[7],
						 * unitCostInput = unitCostDiv
						 * .getElementsByTagName('input')[0];
						 * unitCostInput.setAttribute('id', 'unitCost' +
						 * newNum);
						 */
						// end of seting id on unitCost fields
						// start of seting id on totalCost fields
						/*
						 * var totalCostDiv =
						 * childDiv.getElementsByTagName('div')[8],
						 * totalCostInput = totalCostDiv
						 * .getElementsByTagName('input')[0];
						 * totalCostInput.setAttribute('id', 'totalCost' +
						 * newNum);
						 */
						// end of seting id on totalCost fields
						newEntry.find('input').val('');
						newEntry.find('#quantityRequired' + newNum).val('0');

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

