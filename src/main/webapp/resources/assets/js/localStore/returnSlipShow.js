function editItem(element) {
	var rowid = $(element).attr('id');
	var sequence = rowid.substr(rowid.length - 1);

	var id = $('#dtlId' + sequence).val().trim();

	var qtyNewServiceable = $('#qtyNewServiceable' + sequence).val().trim();
	var qtyNewServiceableFloat = parseFloat(qtyNewServiceable).toFixed(3);

	var qtyRecServiceable = $('#qtyRecServiceable' + sequence).val().trim();
	var qtyRecServiceableFloat = parseFloat(qtyRecServiceable).toFixed(3);

	var qtyUnServiceable = $('#qtyUnServiceable' + sequence).val().trim();
	var qtyUnServiceableFloat = parseFloat(qtyUnServiceable).toFixed(3);

	var totalReturn = $('#totalReturn' + sequence).val().trim();
	var totalReturnFloat = parseFloat(totalReturn).toFixed(3);

	var cData = {
			id : id,
			qtyNewServiceable : qtyNewServiceableFloat,
			qtyRecServiceable : qtyRecServiceableFloat,
			qtyUnServiceable : qtyUnServiceableFloat,
			totalReturn : totalReturnFloat
	}
	var cDataJsonString = JSON.stringify(cData);
	$.ajax({
		type : "POST",
		contentType : "application/json; charset=utf-8",
		url : $('#contextPath').val() + "/ls/editReturnSlipDtl.do",
		data : cDataJsonString,
		success : function(data) {
			//Now updated RR Dtl qty for that item
			alert(data);
		},
		dataType : "json"
	});

}

function setTotalQuantity(element) {
	var id = $(element).attr('id');
	var sequence = id.substr(id.length - 1);

	var qtyNewServiceable = $('#qtyNewServiceable' + sequence).val()
	.trim();
	var qtyNewServiceableFloat = parseFloat(qtyNewServiceable).toFixed(3);

	var qtyRecServiceable = $('#qtyRecServiceable' + sequence).val()
	.trim();
	var qtyRecServiceableFloat = parseFloat(qtyRecServiceable).toFixed(3);

	var qtyUnServiceable = $('#qtyUnServiceable' + sequence).val()
	.trim();
	var qtyUnServiceableFloat = parseFloat(qtyUnServiceable).toFixed(3);

	var x = parseFloat(qtyNewServiceableFloat);
	var y = parseFloat(qtyRecServiceableFloat);
	var z = parseFloat(qtyUnServiceableFloat);

	var totalReturn = (x + y + z).toFixed(3);
	$('#totalReturn' + sequence).val(totalReturn);

}


function deleteItem(id, returnTo, returnSlipNo) {
	if( confirm("Do you want to delete this Item?") == true ) {
		var baseURL = $('#contextPath').val();
		var path = baseURL + "/ls/returnSlip/deleteItem.do";
		var param = {
				'id'			: id,
				'returnTo'		: returnTo,
				'returnSlipNo' 	: returnSlipNo
		}
		postSubmit( path, param, "POST" );
		//location.reload();
	}
}


function itemLeaveChange(element) {

	var temp = $(element).closest("div").parent().parent().attr("id");
	var sequence = temp.substr(temp.length - 1);

	var e = $(element).closest("div").parent().parent().find('.itemName').attr(
			'id', sequence);
	// var e = document.getElementById('' + sequence);
	var e1 = e[0];
	var item_id = e1.options[e1.selectedIndex].value;
	$.ajax({
		// url : 'procurement/requisition/viewInventoryItem.do',
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
}

function setTotalCost(element) {
	var id = $(element).attr('id');
	var sequence = id.substr(id.length - 1);

	var unitCost = $('#quantityIssued' + sequence).val().trim();
	var unitCostFloat = parseFloat(unitCost).toFixed(3);

	var qty = $('#quantityRequired' + sequence).val().trim();
	var qtyFloat = parseFloat(qty).toFixed(3);

	var qty1 = $('#unitCost' + sequence).val().trim();
	var qtyFloat1 = parseFloat(qty1).toFixed(3);
	var x = parseFloat(unitCostFloat);
	var y = parseFloat(qtyFloat);
	var z = parseFloat(qtyFloat1);

	var totalCost = (x + y + z).toFixed(3);
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

						// start of seting id on quantityIssued fields
						var quantityIssuedDiv = childDiv
								.getElementsByTagName('div')[5], quantityIssuedInput = quantityIssuedDiv
								.getElementsByTagName('input')[0];
						quantityIssuedInput.setAttribute('id', 'quantityIssued'
								+ newNum);
						// end of seting id on quantityIssued fields

						// start of seting id on unitCost fields
						var unitCostDiv = childDiv.getElementsByTagName('div')[6], unitCostInput = unitCostDiv
								.getElementsByTagName('input')[0];
						unitCostInput.setAttribute('id', 'unitCost' + newNum);
						// end of seting id on unitCost fields

						// start of seting id on totalCost fields
						var totalCostDiv = childDiv.getElementsByTagName('div')[7], totalCostInput = totalCostDiv
								.getElementsByTagName('input')[0];
						totalCostInput.setAttribute('id', 'totalCost' + newNum);
						// end of seting id on totalCost fields
						
						//errTotRet
						var errTotRetDiv = childDiv.getElementsByTagName('div')[7], errTotRetInput = totalCostDiv
						.getElementsByTagName('strong')[0];
						errTotRetInput.setAttribute('id', 'errTotRet' + newNum);

						newEntry.find('input').val('');
						newEntry.find('input[type=number]').val('0');

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
					//$("#saveLSCSReturnSlip").submit();
					// $('#myDialogeBox').empty();
					var counter = 0, haserror = false;
					
					$('.totalCost').each( function() {
						if( $( this ).val() == null || $.trim( $( this ).val() ) == '' || $.trim( $( this ).val() ) == '0' || $.trim( $( this ).val() ) == '0.0' || $.trim( $( this ).val() ) == '0.00' || $.trim( $( this ).val() ) == '0.000' ) {
							var id = this.id;
							var name = this.name;
							var sequence = id.substr( name.length );
							
							$('#errTotRet'+sequence).removeClass('hide');
							
							counter++;
						} else {
							
							var id = this.id;
							var name = this.name;
							var sequence = id.substr( name.length );
							
							$('#errTotRet'+sequence).addClass('hide');
						}
					});
					
					if( counter > 0 ) {
						haserror = true;
					}
					
					if( haserror == true ) {
						return;
					} else {
						$( '#saveLSCSReturnSlip' ).submit();
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