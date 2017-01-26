function deleteItem( id, requisitionTo, requisitionNo ) {
	if( confirm("Do you want to delete this Item?") == true ) {
		var baseURL = $('#contextPath').val();
		var path = baseURL + "/ls/deleteItem.do";
		var param = {
				'id': id,
				'requisitionTo': requisitionTo,
				'requisitionNo': requisitionNo
		}
		postSubmit( path, param, "POST" );
		//location.reload();
	}
} // delete an item by id
			
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

function categoryLeaveChange(element) {

	var temp = $(element).closest("div").parent().parent().attr("id");
	var sequence = temp.substr(temp.length - 1)

	$(element).closest("div").parent().parent().find('.category').attr('id',
			sequence);
	var e = document.getElementById('' + sequence);
	var categoryId = e.options[e.selectedIndex].value;
	// alert(categoryId);
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
						.text(this.itemId+' - '+this.itemName));
			});
		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
} //category on change

function itemLeaveChange(element) {

	var temp = $(element).closest("div").parent().parent().attr("id");
	var name = $(element).closest("div").parent().parent().attr("name");
	var sequence = temp.substr(name);

	var e = $(element).closest("div").parent().parent().find('.itemName').attr(
			'id', sequence);
	var l = $(element).closest("div").parent().parent().find('.ledgerName')
			.attr('id', sequence);
	// var e = document.getElementById('' + sequence);
	var e1 = e[0];
	var l1 = l[0];
	var item_id = e1.options[e1.selectedIndex].value;
	// var ledgerName= l1.options[l1.selectedIndex].value;
	var khathId = $('#khathId').val();
	// var ledgerName = $('#ledgerName'+sequence).val();
	var requisitionTo = $('#requisitionTo').val();

	$.ajax({
		// url : 'procurement/requisition/viewInventoryItem.do',
		url : 'viewInventoryItem.do',
		// data : "{id:" + item_id + ",
		// ledgerName="+ledgerName+",khathId="+khathId+",
		// requisitionTo="+requisitionTo+"}",
		data : "{id:" + item_id + ",khathId=" + khathId + ", requisitionTo="
				+ requisitionTo + "}",
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
} //item on change

function reqQtyNotGreaterThenCurrentStock(element) {
	var id = $(element).attr('id');
	var name = $(element).attr('name');
	var sequence = id.substr(name.length);

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

} //check stock and allocation limit

function setTotalCost(element) {
	var id = $(element).attr('id');
	var name = $(element).attr('name');
	var sequence = id.substr(name.length);

	var unitCost = $('#unitCost' + sequence).val().trim() || 0;
	if (unitCost == 0) {
		$('#unitCost' + sequence).val(0);
	}
	var unitCostFloat = parseFloat(unitCost).toFixed(2);

	var qty = $('#quantityRequired' + sequence).val().trim() || 0;
	if (qty == 0) {
		$('#quantityRequired' + sequence).val(1);
	}

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

						// start of seting id on expectedQty fields
						var expectedQtyDiv = childDiv
						.getElementsByTagName('div')[4], expectedQtyInput = expectedQtyDiv
						.getElementsByTagName('input')[0];
						expectedQtyInput.setAttribute('id', 'currentStock'
								+ newNum);
						// end of seting id on expectedQty fields

						// start of seting id on receivedQty fields
						var receivedQtyDiv = childDiv
						.getElementsByTagName('div')[5], receivedQtyInput = receivedQtyDiv
						.getElementsByTagName('input')[0];
						receivedQtyInput.setAttribute('id', 'quantityRequired'
								+ newNum);
						
						//reqQty error
						var reqQtyDiv = childDiv
						.getElementsByTagName('div')[5], reqQtyInput = reqQtyDiv
						.getElementsByTagName('strong')[0];
						reqQtyInput.setAttribute('id', 'reqQty'
						+ newNum);
						
						// end of seting id on receivedQty fields
						newEntry.find('input').val('');
						newEntry.find('#quantityRequired'+newNum).val('0');

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

function editItem(id) {			
	$.ajax({								
		url : '${pageContext.request.contextPath}/cs/itemRecieved/itemEdit.do',
		data : "{id:"+id+"}",
		contentType : "application/json",
		success : function(data) {									
			var item = JSON.parse(data);
				$("#modal_itemId").val(item.itemId);
				$("#modal_description").val(item.description);
				$("#modal_expectedQuantity").val(item.expectedQty);
				$("#modal_id").val(item.id);
				$("#modal_receivedQuantity").val(item.receivedQty);
				$("#modal_reminingQuantity").val(item.remainingQty);					
				
		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
}

function receivedQtyToRemainQty(element) {	
	var expected = parseInt($('#modal_expectedQuantity').val().trim(), 10);
	var received = parseInt($(element).val().trim(), 10);
	if (received > expected) {
		$(element).val($('#modal_expectedQuantity').val());
	}
	$('#modal_reminingQuantity').val(
			$('#modal_expectedQuantity').val()
					- $('#modal_receivedQuantity').val());
}

function validateAllocation(element) {
	var id = $(element).attr('id');
	var name = $(element).attr('name');
	var sequence = id.substr(name.length);

	var quantityRequired = $('#quantityRequired' + sequence).val().trim();
	var itemCode = $('#itemCode' + sequence).val().trim();
	if (itemCode.length > 0 || quantityRequired > 0) {
		var cData = {
			quantityRequired : quantityRequired,
			itemCode : itemCode
		}
		$
				.ajax({
					url : $("#contextPath").val()
							+ '/allocation/validateAllocation.do',
					data : JSON.stringify(cData),
					contentType : "application/json",
					success : function(data) {
						var cData = JSON.parse(data);
						if (cData == "success") {
							// alert(cData);
							$('.alert.alert-success').removeClass('hide');
							$(".alert.alert-success").fadeTo(2000, 500)
									.slideUp(
											500,
											function() {
												// $(".alert.alert-success").alert('close');
											});
						} else {
							$('#quantityRequired' + sequence).val(0);
							$('.alert.alert-danger').removeClass('hide');
							$(".alert.alert-danger").fadeTo(2000, 500)
									.slideUp(
											500,
											function() {
											//	$(".alert.alert-danger").alert('close');
											});
						}
					},
					error : function(data) {
						var cData = JSON.parse(data);
						alert(cData);
					},
					type : 'POST'
				});

	}
}