function checkWorkOrder() {
	var workOrderNo = $("#workOrderNo").val();
	var saveButton = $("#saveButton");
	
	var contextPath = $("#contextPath").val();

	var workOrderDecision = $("#workOrderDecision");

	$.ajax({
		url : contextPath + '/workOrder/checkWorkOrder.do',
		data : "{workOrderNo:'" + workOrderNo + "'}",
		contentType : "application/json",
		success : function(data) {
			var result = JSON.parse(data);
			// alert(data);
			if (result == 'success') {
				saveButton.prop("disabled", false);
				workOrderDecision.removeClass();
				workOrderDecision
						.addClass("glyphicon glyphicon-ok-sign green big");

			} else {
				saveButton.prop("disabled", true);
				workOrderDecision.removeClass();
				workOrderDecision
						.addClass("glyphicon glyphicon-remove-sign red big");
			}
		},
		error : function(data) {
			alert("Server Error");
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

						// start of seting id on expectedQty fields
						var expectedQtyDiv = childDiv
								.getElementsByTagName('div')[4], expectedQtyInput = expectedQtyDiv
								.getElementsByTagName('input')[0];
						expectedQtyInput.setAttribute('id', 'expectedQty'
								+ newNum);
						// end of seting id on expectedQty fields

						// start of seting id on receivedQty fields
						var receivedQtyDiv = childDiv
								.getElementsByTagName('div')[5], receivedQtyInput = receivedQtyDiv
								.getElementsByTagName('input')[0];
						receivedQtyInput.setAttribute('id', 'receivedQty'
								+ newNum);
						// end of seting id on receivedQty fields

						// start of seting id on remainingQty fields
						var remainingQtyDiv = childDiv
								.getElementsByTagName('div')[6], remainingQtyInput = remainingQtyDiv
								.getElementsByTagName('input')[0];
						remainingQtyInput.setAttribute('id', 'remainingQty'
								+ newNum);
						// end of seting id on remainingQty fields

						newEntry.find('input').val('');

//						controlForm
//								.find('.entry:not(:last) .btn-add')
//								.removeClass('btn-add')
//								.addClass('btn-remove')
//								.removeClass('btn-success')
//								.addClass('btn-danger')
//								.html(
//										'<span class="glyphicon glyphicon-minus"></span>');
					}).on('click', '.btn-remove', function(e) {
				$(this).parents('.entry:first').remove();

				// e.preventDefault();
				return false;
			});

});

function expectQtyEqualReceive(element) {
	var id = $(element).attr('id');
	var sequence = id.substr(id.length - 1);
	$('#receivedQty' + sequence).val($('#expectedQty' + sequence).val());
}

function receivedQtyToRemainQty(element) {
	var id = $(element).attr('id');
	var sequence = id.substr(id.length - 1);

	var expected = parseInt($('#expectedQty' + sequence).val().trim(), 10);

	var received = parseInt($(element).val().trim(), 10);

	if (received > expected) {
		$(element).val($('#expectedQty' + sequence).val());
	}

	$('#remainingQty' + sequence).val(
			$('#expectedQty' + sequence).val()
					- $('#receivedQty' + sequence).val());

}

function itemLeaveChange(element) {

	var temp = $(element).closest("div").parent().parent().attr("id");
	var sequence = temp.substr(temp.length - 1);
	
	var contextPath = $("#contextPath").val();

	var e = $(element).closest("div").parent().parent().find('.itemName').attr(
			'id', sequence);
	var e1 = e[0];
	var item_id = e1.options[e1.selectedIndex].value;
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

	var temp = $(element).closest("div").parent().parent().attr("id");
	var sequence = temp.substr(temp.length - 1);
	
	var contextPath = $("#contextPath").val();

	$(element).closest("div").parent().parent().find('.category').attr('id',
			sequence);
	var e = document.getElementById('' + sequence);
	var categoryId = e.options[e.selectedIndex].value;
	// alert(categoryId);
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
						.text(this.itemId+" - "+this.itemName));
			});
		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
}
