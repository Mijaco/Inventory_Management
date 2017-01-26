/**
 * Author: Ihteshamul Alam
 */

function setpettyCashHead(element) {
	
}

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

						// start of setting id on pettyCashHead fields
						var pettyCashCodeDiv = childDiv.getElementsByTagName('div')[0], pettyCashCodeInput = pettyCashCodeDiv
								.getElementsByTagName('select')[0];
						pettyCashCodeInput.setAttribute('id', 'pettyCashCode' + newNum);
						// end of setting id on category fields

						// start of setting id on description
						var descriptionDiv = childDiv.getElementsByTagName('div')[1], descriptionInput = descriptionDiv
								.getElementsByTagName('input')[0];
						descriptionInput.setAttribute('id', 'description'
								+ newNum);
						// end of setting id on description and itemName

						// start of setting id on totalCost
						var totalCostDiv = childDiv.getElementsByTagName('div')[2], totalCostInput = totalCostDiv
								.getElementsByTagName('input')[0];
						totalCostInput.setAttribute('id', 'totalCost' + newNum);
						// end of setting id on totalCost
						
						// start of setting id on costError
						var costErrorDiv = childDiv.getElementsByTagName('div')[2], costErrorInput = totalCostDiv
						.getElementsByTagName('h5')[0];
						costErrorInput.setAttribute('id', 'costError' + newNum);
						// end of setting id on costError

						// start of setting id on uom
						var remarksDiv = childDiv.getElementsByTagName('div')[3], remarksInput = remarksDiv
								.getElementsByTagName('input')[0];
						remarksInput.setAttribute('id', 'remarks' + newNum);
						// end of setting id on uom
						
						newEntry.find('.description').val('');
						newEntry.find('.pettyCashCode').val('0');
						newEntry.find('.remarks').val('');

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
});