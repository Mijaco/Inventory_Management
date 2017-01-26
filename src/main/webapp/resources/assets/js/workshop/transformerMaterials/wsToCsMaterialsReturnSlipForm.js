var contextPath=$("#contextPath").val();
/*function itemLeaveChange(element) {

	var temp = $(element).closest("div").parent().parent().attr("id");
	var sequence = temp.substr(temp.length - 1);

	var e = $(element).closest("div").parent().parent().find('.itemName').attr(
			'id', sequence);
	// var e = document.getElementById('' + sequence);
	var e1 = e[0];
	var item_id = e1.options[e1.selectedIndex].value;
	$.ajax({
		// url : 'procurement/requisition/viewInventoryItem.do',
		//url : 'viewInventoryItem.do',
		url : contextPath+'/wsm/returnSlip/wsReturnViewInventoryItem.do',
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
}*/

function itemLeaveChange(element) {
	//alert(itemName);
	var id = $(element).attr('id');
	var name = $(element).attr('name');
	 var index = id.substr(name.length);
	// var contextPath=document.getElementById('contextPath').value;
	//alert(document.getElementById('workOrderNumber').value+"----"+document.getElementById('itemCodeSelect'+index).value);
	$.ajax({
		type : "post",
		url : 'getItemDataFromAsbuilt.do',
		async : false,
		data : 'itemCode=' + document.getElementById('itemCodeSelect'+index).value+'&workOrderNumber='+document.getElementById('workOrderNumber').value,
		success : function(response) {
			var s = response.split(":");
			//alert("#quantityRequired"+index);
			$("#itemCode"+index).val(s[0]);
			$("#itemName"+index).val(s[1]);
			$("#unit"+index).val(s[2]);
			document.getElementById('quantityRequired'+index).value=s[3];
			document.getElementById('quantityIssued'+index).focus();
			
			//$("#quantityRequired"+index).val(s[3].toFixed(3));
			//$("#receivedDate").val(s[4]);
		},
		error : function() {
		}
	});

	return true;
}

function categoryLeaveChange(element) {

	var temp = $(element).closest("div").parent().parent().attr("id");
	var sequence = temp.substr(temp.length - 1)

	//var contextPath=$("#contextPath").val();
	$(element).closest("div").parent().parent().find('.category').attr('id',
			sequence);
	var e = document.getElementById('' + sequence);
	var categoryId = e.options[e.selectedIndex].value;
	// alert(categoryId);
	$.ajax({

		//url : 'wsReturnViewInventoryItemCategory.do',
		url : contextPath+'/wsm/wsReturnViewInventoryItemCategory.do',
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
	validContractorAllocation(element);
	var id = $(element).attr('id');
	var sequence = id.substr(id.length - 1);

	var unitCost = $('#quantityIssued' + sequence).val().trim();
	var unitCostFloat = parseFloat(unitCost).toFixed(2);

	var qty = $('#quantityRequired' + sequence).val().trim();
	var qtyFloat = parseFloat(qty).toFixed(2);

	var qty1 = $('#unitCost' + sequence).val().trim();
	var qtyFloat1 = parseFloat(qty1).toFixed(2);
	var x = parseFloat(unitCostFloat);
	var y = parseFloat(qtyFloat);
	var z = parseFloat(qtyFloat1);

	var totalCost = (x + y + z).toFixed(2);
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
						var itemCodeSelectDiv = childDiv
								.getElementsByTagName('div')[0], itemCodeSelectInput = itemCodeSelectDiv
								.getElementsByTagName('select')[0];
						itemCodeSelectInput.setAttribute('id', 'itemCodeSelect'
								+ newNum);
						// end of seting id on currentStock fields
						
						// start of seting id on currentStock fields
						var itemNameDiv = childDiv
								.getElementsByTagName('div')[0], itemNameInput = itemNameDiv
								.getElementsByTagName('input')[0];
						itemNameInput.setAttribute('id', 'itemName'
								+ newNum);
						// end of seting id on currentStock fields
						
						// start of seting id on currentStock fields
						var itemCodeDiv = childDiv
								.getElementsByTagName('div')[1], itemCodeInput = itemCodeDiv
								.getElementsByTagName('input')[0];
						itemCodeInput.setAttribute('id', 'itemCode'
								+ newNum);
						// end of seting id on currentStock fields
						
						// start of seting id on currentStock fields
						var unitDiv = childDiv
								.getElementsByTagName('div')[2], unitInput = unitDiv
								.getElementsByTagName('input')[0];
						unitInput.setAttribute('id', 'unit'
								+ newNum);
						// end of seting id on currentStock fields

						// start of seting id on quantityRequired fields
						var quantityRequiredDiv = childDiv
								.getElementsByTagName('div')[3], quantityRequiredInput = quantityRequiredDiv
								.getElementsByTagName('input')[0];
						quantityRequiredInput.setAttribute('id',
								'quantityRequired' + newNum);
						// end of seting id on quantityRequired fields

						// start of seting id on quantityIssued fields
						var quantityIssuedDiv = childDiv
								.getElementsByTagName('div')[4], quantityIssuedInput = quantityIssuedDiv
								.getElementsByTagName('input')[0];
						quantityIssuedInput.setAttribute('id', 'quantityIssued'
								+ newNum);
						// end of seting id on quantityIssued fields

						// start of seting id on unitCost fields
						var unitCostDiv = childDiv.getElementsByTagName('div')[5], unitCostInput = unitCostDiv
								.getElementsByTagName('input')[0];
						unitCostInput.setAttribute('id', 'unitCost' + newNum);
						// end of seting id on unitCost fields

						// start of seting id on totalCost fields
						var totalCostDiv = childDiv.getElementsByTagName('div')[6], totalCostInput = totalCostDiv
								.getElementsByTagName('input')[0];
						totalCostInput.setAttribute('id', 'totalCost' + newNum);
						// end of seting id on totalCost fields

						newEntry.find('input').val('');
						newEntry.find('.quantityRequired').val(0.0);
						newEntry.find('.quantityIssued').val(0.0);
						newEntry.find('.unitCost').val(0.0);
						newEntry.find('.totalCost').val(0.0);

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
					if ($('#quantityRequired'+i).val() == 0 && $('#quantityIssued'+i).val() == 0 && $('#unitCost'+i).val() == 0) {
						/*$('#quantityRequired'+i).after(
								'<span class="error" style="color:red"> Please enter atleast one quantity field</span>');
						*/
						alert("Please enter atleast one quantity field");
						return false;
					}
					if ($('#totalCost'+i).val() == 0) {
						
						alert("Please enter Total Return field");
						return false;
					}
					
				}

				return true;
			}
		});

function validContractorAllocation(element) {
	//alert("in valid");
	var id = $(element).attr('id');
	var name = $(element).attr('name');
	 var index = id.substr(name.length);
	var quantityRequiredIdValue='quantityRequired'+index;
	var quantityIssuedValue='quantityIssued'+index;
	var unitCostValue='unitCost'+index;
	var totalCostValue='totalCost'+index;
	var workOrderNumber=document.getElementById("workOrderNumber").value;
	//var quantityRequiredVal=document.getElementById(quantityRequiredIdValue).value;
		//alert(1)
	$.ajax({
		type : "post",
		url : 'validContractor.do',
		async : false,
		data : 'contractNo=' + workOrderNumber,
		success : function(response) {
			 if (response==false){
			        alert("Allocation of this contractor is not set yet");
			       // $('#quantityRequired'+index).val(0); $('#quantityIssued'+index).val(0); $('#unitCost'+index).val(0);
					  document.getElementById(quantityRequiredIdValue).value=0.0;
					  document.getElementById(quantityIssuedValue).value=0.0;
					  document.getElementById(unitCostValue).value=0.0;
					  document.getElementById(totalCostValue).value=0.0;
			        	return false;
			   	 }			       
		},
		error : function() {
		}
	});

	return true;
}

/*function itemLeaveChange(element) {
	//alert(itemName);
	var id = $(element).attr('id');
	var name = $(element).attr('name');
	 var index = id.substr(name.length);
	
	$.ajax({
		type : "post",
		url : '${pageContext.request.contextPath}/getItemData.do',
		async : false,
		data : 'itemCode=' + document.getElementById('itemCodeSelect'+index).value+'&workOrderNumber='+document.getElementById('workOrderNumber').value,
		success : function(response) {
			var s = response.split(",");
			$("#itemCode").val(s[0]);
			$("#itemName").val(s[1]);
			$("#unit").val(s[2]);
			$("#quantityUsed").val(s[3]);
			//$("#receivedDate").val(s[4]);
		},
		error : function() {
		}
	});

	return true;
}
*/