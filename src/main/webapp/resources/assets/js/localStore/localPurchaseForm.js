$(function() {
	$(document)
			.on(
					'click',
					'.btn-add',
					function(e) {
						var num = $('.clonedArea').length;
						var newNum = num + 1;
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
						var receivedQtyDiv = childDiv
								.getElementsByTagName('div')[4], receivedQtyInput = receivedQtyDiv
								.getElementsByTagName('input')[0];
						receivedQtyInput.setAttribute('id', 'receivedQty'
								+ newNum);
						// end of setting id on receivedQty fields

						// start of setting id on unitCost
						var unitCostDiv = childDiv.getElementsByTagName('div')[5], unitCostInput = unitCostDiv
								.getElementsByTagName('input')[0];
						unitCostInput.setAttribute('id', 'unitCost' + newNum);
						// end of setting id on unitCost

						// start of setting id on totalCost
						var totalCostDiv = childDiv.getElementsByTagName('div')[6], totalCostInput = totalCostDiv
								.getElementsByTagName('input')[0];
						totalCostInput.setAttribute('id', 'totalCost' + newNum);
						// end of setting id on totalCost

						// start of setting id on remarks
						var remarksDiv = childDiv.getElementsByTagName('div')[7], remarksInput = remarksDiv
								.getElementsByTagName('input')[0];
						remarksInput.setAttribute('id', 'remarks' + newNum);
						// end of setting id on remarks

						newEntry.find('input').val('');
						newEntry.find('.receivedQty').val(0.0);
						newEntry.find('.unitCost').val(0.0);
						newEntry.find('.totalCost').val(0.0);

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
	var id = $(element).attr("id");
	var name = $(element).attr("name");
	var sequence = id.substr(name.length);
	var contextPath = $("#contextPath").val();

	var receivedQty = parseFloat($("#receivedQty" + sequence).val());
	var unitCost = parseFloat($("#unitCost" + sequence).val());
	var totalCost = receivedQty * unitCost;
	$("#totalCost" + sequence).val(totalCost.toFixed(2));

}

$(document).ready(function() {

	$('#verifyDepartment').click(function() {

		var loginUserDeptId = $('#loginUserDeptId').val();
		var deptId = $('#departmentName').val();

		if (deptId == "" || deptId == null) {
			$('#invalid_dept_warning').removeClass('hide');
		} else {
			if (loginUserDeptId == deptId) {
				$('#invalid_dept_warning').addClass('hide');
				$('#department_div').addClass('hide');
				$('#lp_form_div').removeClass('hide');
			} else {
				$('#invalid_dept_warning').removeClass('hide');
			}

		}

	});
	
	//Added by: Ihteshamul Alam
	$('#saveButton').click( function() {
		var haserror = false;
		
		if( $('#supplierName').val() == null || $.trim( $('#supplierName').val() ) == '' ) {
			$('.supplierName').removeClass('hide');
			haserror = true;
		} else {
			$('.supplierName').addClass('hide');
		}
		
		if( $('#purchaseDate').val() == null || $.trim( $('#purchaseDate').val() ) == '' ) {
			$('.purchaseDate').removeClass('hide');
			haserror = true;
		} else {
			$('.purchaseDate').addClass('hide');
		}
		
		if( haserror == true ) {
			return;
		} else {
			$('#saveButton').prop('disabled', true);
			$('form').submit();
		}
	});
});