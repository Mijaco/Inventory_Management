function itemLeaveChange(element) {
	
	//alert("hello");

	var temp = $(element).closest("div").parent().parent().attr("id");
	var sequence = temp.substr(temp.length - 1);
	var contextPath=$("#contextPath").val();
	var e = $(element).closest("div").parent().parent().find('.itemName').attr(
			'id', sequence);
	// var e = document.getElementById('' + sequence);
	var e1 = e[0];
	var item_id = e1.options[e1.selectedIndex].value;
	//alert(item_id);
	$.ajax({
		// url : 'procurement/requisition/viewInventoryItem.do',
		url : contextPath+'/wsx/returnSlip/wsReturnViewInventoryItem.do',
		data : "{itemCode:" + item_id + "}",
		contentType : "application/json",
		success : function(data) {
			var inventoryItem = JSON.parse(data);
			$(element).closest("div").parent().parent().find('.itemCode').val(
					inventoryItem.itemId);
			$(element).closest("div").parent().parent().find('.uom').val(
					inventoryItem.unitCode);
			$(element).closest("div").parent().parent().find('.description')
					.val(inventoryItem.itemName);
			/*$(element).closest("div").parent().parent().find('.currentStock')
					.val(inventoryItem.currentStock);*/
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
var contextPath=$("#contextPath").val();
	$(element).closest("div").parent().parent().find('.category').attr('id',
			sequence);
	var e = document.getElementById('' + sequence);
	var categoryId = e.options[e.selectedIndex].value;
	 alert(contextPath+'/wsReturnViewInventoryItemCategory.do');
	$.ajax({

		url : contextPath+'/wsReturnViewInventoryItemCategory.do',
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
						.text(this.itemName));
			});
		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
}

function setTotalCost(element) {
	
	//validContractorAllocation(element);
	var id = $(element).attr('id');
	var sequence = id.substr(id.length - 1);

	var unitCost = $('#qtyRecServiceable' + sequence).val().trim();
	var unitCostFloat = parseFloat(unitCost).toFixed(2);

	var qty = $('#qtyNewServiceable' + sequence).val().trim();
	var qtyFloat = parseFloat(qty).toFixed(2);

	var qty1 = $('#qtyUnServiceable' + sequence).val().trim();
	var qtyFloat1 = parseFloat(qty1).toFixed(2);
	var x = parseFloat(unitCostFloat);
	var y = parseFloat(qtyFloat);
	var z = parseFloat(qtyFloat1);

	var totalCost = (x + y + z).toFixed(2);
	$('#totalReturn' + sequence).val(totalCost);

}

$(function() {
	$(document)
			.on(
					'click',
					'.btn-add',
					function(e) {
						$('.error').hide();
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
						/*var currentStockDiv = childDiv
								.getElementsByTagName('div')[3], currentStockInput = currentStockDiv
								.getElementsByTagName('input')[0];
						currentStockInput.setAttribute('id', 'currentStock'
								+ newNum);*/
						// end of seting id on currentStock fields

						// start of seting id on quantityRequired fields
						var quantityRequiredDiv = childDiv
								.getElementsByTagName('div')[3], quantityRequiredInput = quantityRequiredDiv
								.getElementsByTagName('input')[0];
						quantityRequiredInput.setAttribute('id',
								'qtyNewServiceable' + newNum);
						// end of seting id on quantityRequired fields

						// start of seting id on quantityIssued fields
						var quantityIssuedDiv = childDiv
								.getElementsByTagName('div')[4], quantityIssuedInput = quantityIssuedDiv
								.getElementsByTagName('input')[0];
						quantityIssuedInput.setAttribute('id', 'qtyRecServiceable'
								+ newNum);
						quantityIssuedInput.setValue(0);
						// end of seting id on quantityIssued fields

						// start of seting id on unitCost fields
						var unitCostDiv = childDiv.getElementsByTagName('div')[5], unitCostInput = unitCostDiv
								.getElementsByTagName('input')[0];
						unitCostInput.setAttribute('id', 'qtyUnServiceable' + newNum);
						// end of seting id on unitCost fields

						// start of seting id on totalCost fields
						var totalCostDiv = childDiv.getElementsByTagName('div')[6], totalCostInput = totalCostDiv
								.getElementsByTagName('input')[0];
						totalCostInput.setAttribute('id', 'totalReturn' + newNum);
						// end of seting id on totalCost fields

					
						newEntry.find('input').val('');
						newEntry.find('.qtyNewServiceable').val(0.0);
						newEntry.find('.qtyRecServiceable').val(0.0);
						newEntry.find('.qtyUnServiceable').val(0.0);
						newEntry.find('.totalReturn').val(0.0);

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
$(document).ready(function() {
	$(function() {

		$("#workOrderNo").autocomplete({
			source : function(request, response) {
				// alert(request.term);
				$.ajax({
					url : 'getWwOrderToReturn.do',
					type : "POST",
					data : {
						contractNo : request.term
					},

					dataType : "json",

					success : function(data) {
						response($.map(data, function(v, i) {
							return {
								label : v.workOrderNo,
								value : v.workOrderNo
							};

						}));
					}

				});

			}// ,
		/*
		 * response : function(event, ui) { if (!ui.content.length) { var
		 * noResult = { value : "", label : "No matching your request" };
		 * ui.content.push(noResult); } },
		 */
		/*
		 * select : function(event, ui) { // alert(ui.item.label);
		 * window.location.href = 'viewWorkOrderData.do?contractNo=' +
		 * ui.item.label; },
		 */
		/* minLength : 1 */
		});
	});
});

$(document).ready(
		function() {

			$('#submit').click(function() {
				return validateForm();
			});

			function validateForm() {
				
				//var inputMessage = new Array("Quantity", "QuantityIssued");

				$('.error').hide();
				var row = $('.clonedArea').length;
				var rowCount = row+1;
				for(var i=0;i<rowCount;i++){
					//alert("in loop");
					//var quantity = ;	
					if ($('#qtyNewServiceable'+i).val() == 0 && $('#qtyRecServiceable'+i).val() == 0 && $('#qtyUnServiceable'+i).val() == 0) {
						/*$('#quantityRequired'+i).after(
								'<span class="error" style="color:red"> Please enter atleast one quantity field</span>');
						*/
						alert("Please enter atleast one quantity field");
						return false;
					}
					if ($('#totalReturn'+i).val() == 0) {
						
						alert("Please enter Total Return field");
						return false;
					}
					
				}

				return true;
			}
		});
function validContractorAllocation(element) {
	//alert("in valid");
	//var id = $(element).attr('id');
	//var name = $(element).attr('name');
	var id = $(element).attr('id');
	var index = id.substr(id.length - 1);

	// var index = id.substr(name.length);
	 //alert(index);
	var qtyNewServiceableValue='qtyNewServiceable'+index;
	var qtyRecServiceableValue='qtyRecServiceable'+index;
	var qtyUnServiceableValue='qtyUnServiceable'+index;
	var totalReturnValue='totalReturn'+index;
	var workOrderNumber=document.getElementById("workOrderNumber").value;
	//var quantityRequiredVal=document.getElementById(quantityRequiredIdValue).value;
		
	$.ajax({
		type : "post",
		url : 'validContractor.do',
		async : false,
		data : 'contractNo=' + workOrderNumber,
		success : function(response) {
			 if (response==false){
			        alert("Allocation of this contractor is not set yet");

					  document.getElementById(qtyNewServiceableValue).value=0;
					  document.getElementById(qtyRecServiceableValue).value=0;
					  document.getElementById(qtyUnServiceableValue).value=0;
					  document.getElementById(totalReturnValue).value=0;
			        	return false;
			   	 }			       
		},
		error : function() {
		}
	});

	return true;
}
