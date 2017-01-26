$(function() {
	$(document)
			.on(
					'click',
					'.btn-add',
					function(e) {
						$('.error').hide();
						var num = $('.clonedArea').length;
						
						var newNum = num + 1;
						//alert(newNum);
						var controlForm = $('.controls div:first'), currentEntry = $(
								this).parents('.entry:first'), newEntry = $(
								currentEntry.clone().attr('id',
										'myArea' + newNum).addClass(
										'clonedArea')).appendTo(controlForm);

						var mainDiv = document
								.getElementById('myArea' + newNum), childDiv = mainDiv
								.getElementsByTagName('div')[0];

						// start of setting id on category fields
						var categoryDiv = childDiv.getElementsByTagName('div')[0], categoryInput = categoryDiv
								.getElementsByTagName('select')[0];
						categoryInput.setAttribute('id', 'category' + newNum);
						// end of setting id on category fields

						// start of setting id on description and itemName
						var itemNameDiv = childDiv.getElementsByTagName('div')[1], descriptionInput = itemNameDiv
								.getElementsByTagName('input')[0], itemNameInput = itemNameDiv
								.getElementsByTagName('select')[0];
						descriptionInput.setAttribute('id', 'description'
								+ newNum);
						itemNameInput.setAttribute('id', 'itemName' + newNum);
						// end of setting id on description and itemName

						// start of setting id on itemCode
						var itemCodeDiv = childDiv.getElementsByTagName('div')[2], itemCodeInput = itemCodeDiv
								.getElementsByTagName('input')[0];
						itemCodeInput.setAttribute('id', 'itemCode' + newNum);
						// end of setting id on itemCode

						// start of setting id on uom
						var uomDiv = childDiv.getElementsByTagName('div')[3], uomInput = uomDiv
								.getElementsByTagName('input')[0];
						uomInput.setAttribute('id', 'uom' + newNum);
						// end of setting id on uom

						// start of setting id on receivedQty fields
						/*var receivedQtyDiv = childDiv
								.getElementsByTagName('div')[4], receivedQtyInput = receivedQtyDiv
								.getElementsByTagName('input')[0];
						receivedQtyInput.setAttribute('id', 'receivedQty'
								+ newNum);*/
						// end of setting id on receivedQty fields

						// start of setting id on unitCost
						/*var unitCostDiv = childDiv.getElementsByTagName('div')[5], unitCostInput = unitCostDiv
								.getElementsByTagName('input')[0];
						unitCostInput.setAttribute('id', 'unitCost' + newNum);*/
						// end of setting id on unitCost

						// start of setting id on totalCost
						var totalCostDiv = childDiv.getElementsByTagName('div')[4], totalCostInput = totalCostDiv
								.getElementsByTagName('input')[0];
						totalCostInput.setAttribute('id', 'quantity' + newNum);
						// end of setting id on totalCost

						// start of setting id on remarks
						var remarksDiv = childDiv.getElementsByTagName('div')[5], remarksInput = remarksDiv
								.getElementsByTagName('input')[0];
						remarksInput.setAttribute('id', 'remarks' + newNum);
						// end of setting id on remarks

						newEntry.find('input').val('');
						newEntry.find('.quantity').val(0.0);
						//newEntry.find('.unitCost').val(0.0);
						//newEntry.find('.totalCost').val(0.0);

						/*
						 * controlForm .find('.entry:not(:last) .btn-add')
						 * .removeClass('btn-add') .addClass('btn-remove')
						 * .removeClass('btn-success') .addClass('btn-danger')
						 * .html( '<span class="glyphicon glyphicon-minus"></span>');
						 */
					}).on('click', '.btn-remove', function(e) {
				$(this).parents('.entry:first').remove();
				
				return false;
			});
});

function itemLeaveChange(element) {
	var id = $(element).attr("id");
	var name = $(element).attr("name");
	var sequence = id.substr(name.length);
	var contextPath = $("#contextPath").val();
	var item_id = $("#" + id).val();

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
	var id = $(element).attr("id");
	var name = $(element).attr("name");
	var sequence = id.substr(name.length);
	var contextPath = $("#contextPath").val();
	var categoryId = $("#" + id).val();

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
	var id = $(element).attr("id");
	var name = $(element).attr("name");
	var sequence = id.substr(name.length);
	var contextPath = $("#contextPath").val();

	var receivedQty = parseFloat($("#receivedQty" + sequence).val());
	var unitCost = parseFloat($("#unitCost" + sequence).val());
	var totalCost = receivedQty * unitCost;
	$("#totalCost" + sequence).val(totalCost.toFixed(2));

}

function validQty(element){
	
	var id = $(element).attr('id');
	var name = $(element).attr('name');
	 var index = id.substr(name.length);
	
	var quantityIdValue='quantity'+index;
	var itemCodeIdValue='itemCode'+index;
	var workOrderNumber=document.getElementById("workOrderNumber").value;
	var itemCodeVal=document.getElementById(itemCodeIdValue).value;
	var quantityVal=document.getElementById(quantityIdValue).value;
	//alert(quantityRequiredVal);
	if(workOrderNumber!="Contract No"){
	
	$.ajax({
   	  type: "POST",
   	  url : 'validQty.do',
   	  async: false,    
   	  data:'itemCode='+ itemCodeVal+'&quantity='+quantityVal+'&contractNo='+workOrderNumber,
   	  
   	  success: function(response){
   		  //alert(response);
    if (response==false){
        alert("This Contractor ("+workOrderNumber+") quantity is not available");
        document.getElementById(quantityIdValue).value=0;
   	 }
         
   	  },
   	  error: function(){      
   	  }
   	 });
	}else{ alert("Contract no should not null"); document.getElementById(quantityIdValue).value=0;}
   	  return true;
 }

$(document).ready(
		function() {

			$('#submit').click(function() {
				return validateForm();
			});

			function validateForm() {
				var row = $('.clonedArea').length;
				var rowCount = row+1;
				var receiveDate = $('#receiveDate').val();
				var deptId = $('#deptId').val();
				var referenceDoc = $('#referenceDoc').val();

				//
				//alert(rowCount);
				var inputVal = new Array(receiveDate, deptId, referenceDoc);

				var inputMessage = new Array("Receive Date", "Department Name", "Reference Doc", "Quantity");

				$('.error').hide();

				if (inputVal[0] == "") {
					$('#receiveDate').after(
							'<span class="error" style="color:red"> Please enter '
									+ inputMessage[0] + '</span>');
					return false;
				}
				if (inputVal[1] == null) {
					$('#deptId').after(
							'<span class="error" style="color:red"> Please choose '
									+ inputMessage[1] + '</span>');
					return false;
				}
				if (inputVal[2] == "") {
					$('#referenceDoc').after(
							'<span class="error" style="color:red"> Please choose '
									+ inputMessage[2] + '</span>');
					return false;
				}
				
				
				for(var i=0;i<rowCount;i++){
					//var quantity = ;	
					if ($('#quantity'+i).val() == 0) {
						$('#quantity'+i).after(
								'<span class="error" style="color:red"> Please enter '
										+ inputMessage[3] + '</span>');
						return false;
					}	
				}

				return true;
			}
		});