function editItem(element) {	
	var rowIndex = $(element).attr('id');
	var sequence = rowIndex.substr( rowIndex.length - 1 );

	var id = $('#dtlId'+sequence).val();
	var qtyNewServiceable = parseFloat( $('#qtyNewServiceable'+sequence).val() ).toFixed(3);
	var qtyRecServiceable = parseFloat( $('#qtyRecServiceable'+sequence).val() ).toFixed(3);
	var qtyUnServiceable = parseFloat( $('#qtyUnServiceable'+sequence).val() ).toFixed(3);
	var totalReturn = parseFloat( $('#totalReturn'+sequence).val() ).toFixed(3);
	
	var cdata = {
			id: id,
			qtyNewServiceable: qtyNewServiceable,
			qtyUnServiceable: qtyUnServiceable,
			qtyRecServiceable: qtyRecServiceable,
			totalReturn: totalReturn
	}

	var cDataJsonString = JSON.stringify(cdata);
	
	$.ajax({
		type : "POST",
		contentType : "application/json; charset=utf-8",
		url : $('#contextPath').val() + "/ls/returnSlip/editItem.do",
		data : cDataJsonString,
		success : function(data) {
			//Now updated RR Dtl qty for that item
			alert(data);
		},
		dataType : "json"
	});
}

function deleteItem( id, returnTo, returnSlipNo) {
	if( confirm( "Do you want to delete this Item?" ) == true ) {
		var baseURL = $('#contextPath').val();
		var path = baseURL + "/ls/returnSlip/deleteItem.do";
		var params = {
				'id'			: id,
				'returnTo'		: returnTo,
				'returnSlipNo' 	: returnSlipNo
		}
		
		postSubmit(path, params, "POST");
	}
}

function setTotalQuantity( element ) {
	var rowIndex = $( element ).attr('id');
	var sequence = rowIndex.substr( rowIndex.length - 1 );

	var id = $('#dtlId'+sequence).val();
	var x = parseFloat( $('#qtyNewServiceable'+sequence).val() );
	var y = parseFloat( $('#qtyRecServiceable'+sequence).val() );
	var z = parseFloat( $('#qtyUnServiceable'+sequence).val() );
	/* var totalReturn = parseFloat( $('#totalReturn'+sequence).val() ).toFixed(2); */
	var w = parseFloat( x + y + z ).toFixed(3);
	$('#totalReturn'+sequence).val( w );
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

	var unitCost = $('#quantityIssued' + sequence).val();
	var unitCostFloat = parseFloat(unitCost).toFixed(3);

	var qty = $('#quantityRequired' + sequence).val();
	var qtyFloat = parseFloat(qty).toFixed(3);

	var qty1 = $('#unitCost' + sequence).val();
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
					$("#saveLSReturnSlip").submit();
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
	
function openDialoge() {
	$('#myDialogeBox').removeClass('hide');
	$(".ui-dialog-titlebar").hide();
	$("#myDialogeBox").dialog("open");
}