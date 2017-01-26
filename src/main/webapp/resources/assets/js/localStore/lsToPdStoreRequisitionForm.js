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
									.slideUp(500, function() {
										// $(".alert.alert-success").alert('close');
									});
						} else {
							$('#quantityRequired' + sequence).val(0);
							$('.alert.alert-danger').removeClass('hide');
							$(".alert.alert-danger").fadeTo(2000, 500).slideUp(
									500, function() {
										// $(".alert.alert-danger").alert('close');
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

$(document).ready(
		function() {

			// Added by: Ihteshamul Alam, IBCS
			var contextPath = $('#contextPath').val();

			$('#khathId').change(
					function() {
						$('#khathName')
								.val(
										$.trim($('option:selected', '#khathId')
												.text()));
					}); // select khath name

			var userList = [];
			var path = contextPath + "/ls/loadAuthUserList.do";

			$('#loadauthList').load(path, {}, function(d) {
				var gap = jQuery.parseJSON(d);
				for ( var ii in gap) {
					var data = gap[ii].name + " (" + gap[ii].userid + ")";
					userList.push(data);
				}
			});

			// Added by: Ihteshamul Alam, IBCS
			$('#receivedBy').autocomplete({
				source : userList,
				response : function(event, ui) {
					// ui.content is the array that's about to be sent to the
					// response callback.
					if (ui.content.length === 0) {
						$("#empty-message").text("No results found");
					} else {
						$("#empty-message").empty();
					}
				}
			}); // autocomplete

			// Added by: Ihteshamul Alam, IBCS
			$('#saveButton').click(
					function() {

						var counter = 0, haserror = false;

						if ($("#receivedBy").val() == null
								|| $.trim($('#receivedBy').val()) == '') {
							$('.receivedBy').removeClass('hide');
							haserror = true;
						} else {
							$('.receivedBy').addClass('hide');
						}

						$('.quantityRequired').each(
								function() {
									if ($(this).val() == null
											|| $.trim($(this).val()) == ''
											|| $.trim($(this).val()) == '0') {
										counter++;

										var id = this.id;
										var name = this.name;
										var sequence = id.substr(name.length);

										$('#reqQty' + sequence).removeClass(
												'hide');
									} else {
										var id = this.id;
										var name = this.name;
										var sequence = id.substr(name.length);
										$('#reqQty' + sequence)
												.addClass('hide');
									}
								});

						if (counter > 0) {
							haserror = true;
						}

						if (haserror == true) {
							return;
						} else {
							$('#saveButton').prop('disabled', true);
							$('form').submit();
						}
					});

		}); // jQuery
