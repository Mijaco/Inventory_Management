/**
 * Author: Ihteshamul Alam
 */


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

						// start of setting id on requiredQty fields
						var requiredQtyDiv = childDiv
								.getElementsByTagName('div')[4], requiredQtyInput = requiredQtyDiv
								.getElementsByTagName('input')[0];
						requiredQtyInput.setAttribute('id', 'requiredQty'
								+ newNum);
						// end of setting id on requiredQty fields
						
						// start of setting id on errReqQty fields
						var errorReqQtyDiv = childDiv
						.getElementsByTagName('div')[4], errorReqQtyInput = requiredQtyDiv
						.getElementsByTagName('strong')[0];
						errorReqQtyInput.setAttribute('id', 'errorReqQty'
								+ newNum);
						// end of setting id on requiredQty fields
						
						

						// start of setting id on unitCost
						var ledgerBookDiv = childDiv.getElementsByTagName('div')[5], ledgerBookInput = ledgerBookDiv
								.getElementsByTagName('input')[0];
						ledgerBookInput.setAttribute('id', 'ledgerBook' + newNum);
						// end of setting id on unitCost

						// start of setting id on totalCost
						var pageNoDiv = childDiv.getElementsByTagName('div')[6], pageNoInput = pageNoDiv
								.getElementsByTagName('input')[0];
						pageNoInput.setAttribute('id', 'pageNo' + newNum);
						// end of setting id on totalCost

						// start of setting id on remarks
						var remarksDiv = childDiv.getElementsByTagName('div')[7], remarksInput = remarksDiv
								.getElementsByTagName('input')[0];
						remarksInput.setAttribute('id', 'remarks' + newNum);
						// end of setting id on remarks

						newEntry.find('input').val('');

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
						.text(this.itemName+' ['+this.itemId+']'));
			});
		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
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
});