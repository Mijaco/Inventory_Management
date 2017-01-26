function itemLeaveChange(element) {
	//alert("contractNo");
	var temp = $(element).closest("div").parent().parent().attr("id");
	var sequence = temp.substr(temp.length - 1);

	var e = $(element).closest("div").parent().parent().find('.itemName').attr(
			'id', sequence);
	// var e = document.getElementById('' + sequence);
	var e1 = e[0];
	var item_id = e1.options[e1.selectedIndex].value;
	var contractNo = document.getElementById("contractNo").value;

	//alert(contractNo+"---"+item_id);
	$.ajax({
		// url : 'procurement/requisition/viewInventoryItem.do',
		url : 'cnViewInventoryItem.do',
		data : "{id:" + item_id + "," + contractNo + "}",
		//data : "{id:" + item_id + "}",
		contentType : "application/json",
		success : function(data) {
			//alert(data);
			var PndJobDtl = JSON.parse(data);
			$(element).closest("div").parent().parent().find('.itemCode').val(
					PndJobDtl.itemCode);
			$(element).closest("div").parent().parent().find('.uom').val(
					PndJobDtl.uom);
			$(element).closest("div").parent().parent().find('.description')
			.val(PndJobDtl.itemName);
			$(element).closest("div").parent().parent().find('.currentStock')
					.val(PndJobDtl.remainningQuantity);
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

						// start of seting id on quantityIssued fields
						var quantityIssuedDiv = childDiv
								.getElementsByTagName('div')[6], quantityIssuedInput = quantityIssuedDiv
								.getElementsByTagName('input')[0];
						quantityIssuedInput.setAttribute('id', 'quantityIssued'
								+ newNum);
						// end of seting id on quantityIssued fields

						// start of seting id on unitCost fields
						var unitCostDiv = childDiv.getElementsByTagName('div')[7], unitCostInput = unitCostDiv
								.getElementsByTagName('input')[0];
						unitCostInput.setAttribute('id', 'unitCost' + newNum);
						// end of seting id on unitCost fields

						// start of seting id on totalCost fields
						var totalCostDiv = childDiv.getElementsByTagName('div')[8], totalCostInput = totalCostDiv
								.getElementsByTagName('input')[0];
						totalCostInput.setAttribute('id', 'totalCost' + newNum);
						// end of seting id on totalCost fields

						newEntry.find('input').val('');

						controlForm
								.find('.entry:not(:last) .btn-add')
								.removeClass('btn-add')
								.addClass('btn-remove')
								.removeClass('btn-success')
								.addClass('btn-danger')
								.html(
										'<span class="glyphicon glyphicon-minus"></span>');
					}).on('click', '.btn-remove', function(e) {
				$(this).parents('.entry:first').remove();

				// e.preventDefault();
				return false;
			});

});