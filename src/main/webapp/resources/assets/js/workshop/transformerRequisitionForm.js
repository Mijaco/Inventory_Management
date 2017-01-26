function itemLeaveChange(element) {

	var temp = $(element).closest("div").parent().parent().attr("id");
	var idPrefix="myArea";
	 var sequence = temp.substr(idPrefix.length);
	var e = $(element).closest("div").parent().parent().find('.itemNameSelect').attr(
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
		url : 'wsViewInventoryItem.do',
		data : cData,
		contentType : "application/json",
		success : function(data) {
			var inventoryItem = JSON.parse(data);
			//alert(inventoryItem.itemId+"-------"+inventoryItem.currentStock);
			$(element).closest("div").parent().parent().find('.itemCode').val(
					inventoryItem.itemId);
			$(element).closest("div").parent().parent().find('.uom').val(
					inventoryItem.unitCode);
			$(element).closest("div").parent().parent().find('.itemName')
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

	//var temp = $(element).closest("div").parent().parent().attr("id");
	//var sequence = temp.substr(temp.length - 1)

	//$(element).closest("div").parent().parent().find('.category').attr('id',sequence);
	//var e = document.getElementById('' + sequence);
	var categoryId = e.options[e.selectedIndex].value;
	// alert(categoryId);
	$.ajax({

		url : 'wsViewInventoryItemCategory.do',
		data : "{categoryId:" + categoryId + "}",
		contentType : "application/json",
		success : function(data) {
			var itemList = JSON.parse(data);
			var itemNames = $(element).closest("div").parent().parent().find(
					'.itemNameSelect');

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
						$('.error').hide();
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

						// start of setting id on ItemName select fields
						var matItemNameDiv = childDiv
								.getElementsByTagName('div')[1], matItemName = matItemNameDiv
								.getElementsByTagName('select')[0];
						matItemName.setAttribute('id', 'itemNameSelect'
								+ newNum);
						// end of setting id on ItemName select fields
						
						// start of setting id on ItemName input fields
						var matItemNameInputDiv = childDiv
								.getElementsByTagName('div')[1], matItemNameInput = matItemNameInputDiv
								.getElementsByTagName('input')[0];
						matItemNameInput.setAttribute('id', 'itemName'+ newNum);
						
						var matItemCodeInputDiv = childDiv
						.getElementsByTagName('div')[2], matItemCodeInput = matItemCodeInputDiv
						.getElementsByTagName('input')[0];
				matItemCodeInput.setAttribute('id', 'itemCode'+ newNum);
						// end of setting id on ItemName input fields
						
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

						
						newEntry.find('input').val('');
						newEntry.find('.quantityRequired').val(0.0);

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

function func(value) {
	var iqd = document.getElementById(value).id;
	var name = document.getElementById(value).name;
	var sequence = iqd.substr(name.length);
	return sequence;
}

function validQty(element){
	//reqQtyNotGreaterThenCurrentStock(element);
	var id = $(element).attr('id');
	var name = $(element).attr('name');
	 var index = id.substr(name.length);
	 var currentStockValue='currentStock'+index;
	var quantityRequiredIdValue='quantityRequired'+index;
	var itemCodeIdValue='itemCode'+index;
	var workOrderNumber=document.getElementById("workOrderNumber").value;
	 validContractorAllocation(workOrderNumber, quantityRequiredIdValue);
	var itemCodeVal=document.getElementById(itemCodeIdValue).value;
	var quantityRequiredVal=document.getElementById(quantityRequiredIdValue).value;
	var currentStockVal=document.getElementById(currentStockValue).value;
	//alert(quantityRequiredVal);
	if(currentStockVal==0){
		  alert("Quantity is not available in Central Store");
		  document.getElementById(quantityRequiredIdValue).value=0;
		return false;
	}
	/*if(currentStockVal < quantityRequiredVal){
		  alert("quantity is not available"+currentStockVal+"--"+quantityRequiredVal);
		  document.getElementById(quantityRequiredIdValue).value=0;
		return false;
	}*/
	
	$.ajax({
   	  type: "POST",
   	  url : 'validQty.do',
   	  async: false,    
   	  data:'itemCode='+ itemCodeVal+'&quantityRequired='+quantityRequiredVal+'&contractNo='+workOrderNumber,
   	  
   	  success: function(response){
   		  //alert(response);
   	 if (response==false){
        alert("Your Required quantity is not available");
        document.getElementById(quantityRequiredIdValue).value=0;
   	 }
         
        // s = $.parseJSON(response);
   	  },
   	  error: function(){      
   	  }
   	 });
	//}
   	  return true;
 }
function validContractorAllocation(contractNo, quantityRequiredIdValue) {
	$.ajax({
		type : "post",
		url : 'validContractor.do',
		async : false,
		data : 'contractNo=' + contractNo,
		success : function(response) {
			 if (response==false){
			        alert("Allocation of this contractor is not set yet");

					  document.getElementById(quantityRequiredIdValue).value=0;
			        	return false;
			   	 }
			       
		},
		error : function() {
		}
	});

	return true;
}

/*$(document).ready(
		function() {

			$('#submit').click(function() {
				return validateForm();
			});

			function validateForm() {
				
				var inputMessage = new Array("Quantity");

				$('.error').hide();
				
				for(var i=0;i<rowCount;i++){
					//var quantity = ;	
					if ($('#quantity'+i).val() == 0) {
						$('#quantity'+i).after(
								'<span class="error" style="color:red"> Please enter '
										+ inputMessage[0] + '</span>');
						return false;
					}	
				}

				return true;
			}
		});*/
$(document).ready(
		function() {

			$('#submit').click(function() {
				return validateForm();
			});

			function validateForm() {
				var row = $('.clonedArea').length;
				var rowCount = row+1;
				
				var inputMessage = new Array("Quantity");

				$('.error').hide();				
				
				for(var i=0;i<rowCount;i++){
					//var quantity = ;	
					if ($('#quantityRequired'+i).val() == 0) {
						$('#quantityRequired'+i).after(
								'<span class="error" style="color:red"> Please enter '
										+ inputMessage[0] + '</span>');
						return false;
					}	
				}

				return true;
			}
		});