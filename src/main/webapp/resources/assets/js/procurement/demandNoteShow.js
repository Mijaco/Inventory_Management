$(document).ready(function() {
	$('#demandNoteDtl').DataTable({
		"columnDefs" : [ {"width" : "10%", "targets" : 4}, 
		                 {"width" : "10%", "targets" : 5}, 
		                 {"width" : "10%", "targets" : 6}, 
		                 {"width" : "10%", "targets" : 7}, 
		                 {"width" : "15%", "targets" : 8}],
		
		"order" : [ [ 0, 'asc' ] ],
		"paging" : false,
		"searching" : true,
		"info" : false

	});
});
function editDtl(index) {
	$('#btnUpdateDtl' + index).removeClass('hide');
	$('#btnEditDtl' + index).addClass('hide');

	$('#rQty' + index).attr('readonly', false);
	$('#eUnitCost' + index).attr('readonly', false);
	$('#pYearConsumption' + index).attr('readonly', false);
}

function setTotalCost2(element) {
	var id = $(element).attr("id");
	var name = $(element).attr("name");
	var sequence = id.substr(name.length);
	var contextPath = $("#contextPath").val();

	var reqQty = $("#rQty" + sequence).val();
	var uc = $("#eUnitCost" + sequence).val();
	(reqQty == '') ? reqQty = 0 : reqQty = reqQty;
	(uc == '') ? uc = 0 : uc = uc;

	var requiredQunatity = parseFloat(reqQty);
	var unitCost = parseFloat(uc);
	var totalCost = requiredQunatity * unitCost;
	$("#eTotalCost" + sequence).val(totalCost.toFixed(2));

}

function updateDtl(index) {
	$('#btnUpdateDtl' + index).addClass('hide');
	$('#btnEditDtl' + index).removeClass('hide');

	var requiredQunatity = $('#rQty' + index);
	requiredQunatity.attr('readonly', 'readonly');
	var estimateUnitCost = $('#eUnitCost' + index);
	estimateUnitCost.attr('readonly', 'readonly');
	var estimateTotalCost = $('#eTotalCost' + index);
	estimateTotalCost.attr('readonly', 'readonly');
	var previousYearConsumption = $('#pYearConsumption' + index);
	previousYearConsumption.attr('readonly', 'readonly');

	var path = $('#contextPath').val() + "/mps/dn/demandNoteDtlUpdate.do";
	var params = {
		id : $('#demandNoteDtlId' + index).val(),
		requiredQunatity : parseFloat(requiredQunatity.val()),
		estimateUnitCost : parseFloat(estimateUnitCost.val()),
		estimateTotalCost : parseFloat(estimateTotalCost.val()),
		previousYearConsumption : parseFloat(previousYearConsumption.val())
	}

	var cDataJsonString = JSON.stringify(params);
	$.ajax({
		url : path,
		data : cDataJsonString,
		contentType : "application/json",
		success : function(data) {
			var pData = JSON.parse(data);
			if (pData == 'success') {
				$('.alert.alert-success').removeClass('hide');
				$(".alert.alert-success").fadeTo(5000, .5).slideUp(500);
			} else {
				$('.alert.alert-danger').removeClass('hide');
				$(".alert.alert-danger").fadeTo(5000, .5).slideUp(500);
			}
		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
}

function confirmDelete(index) {
	if (confirm("Do you want to delete this item?") == true) {
		var contextPath = $('#contextPath').val();
		var cData = {
			"id" : $('#demandNoteDtlId' + index).val()
		}
		var path = contextPath + "/mps/dn/demandNoteDtlDelete.do";
		postSubmit(path, cData, 'POST');
	}

}

$(function() {
	$("#myDialogeBox").dialog({
		autoOpen : false,
		closeOnEscape : false,
		modal : true,
		draggable : false,
		resizable : false,
		position : {
			my : "center",
			at : "center",
			of : window
		},
		show : {
			effect : "blind",
			duration : 10
		},
		hide : 'blind',
		width : $(window).width() - 100,
		buttons : [ {
			text : 'Save',
			click : function() {
				// send post request
				$("#saveNewDemandDtlForm").submit();
				// $('#myDialogeBox').empty();
			}
		}, {
			text : 'Close',
			click : function() {
				$(this).dialog("close");
			}
		} ]
	});

});

function openDialoge() {
	$('#myDialogeBox').removeClass('hide');
	$(".ui-dialog-titlebar").hide();
	$("#myDialogeBox").dialog("open");
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

						// start of setting id on requiredQunatity fields
						var requiredQunatityDiv = childDiv
								.getElementsByTagName('div')[4], requiredQunatityInput = requiredQunatityDiv
								.getElementsByTagName('input')[0];
						requiredQunatityInput.setAttribute('id',
								'requiredQunatity' + newNum);
						// end of setting id on requiredQunatity fields

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

						// start of setting id on previousYearConsumption
						var previousYearConsumptionDiv = childDiv
								.getElementsByTagName('div')[7], previousYearConsumptionInput = previousYearConsumptionDiv
								.getElementsByTagName('input')[0];
						previousYearConsumptionInput.setAttribute('id',
								'previousYearConsumption' + newNum);
						// end of setting id on previousYearConsumption

						newEntry.find('input').val('');
						// newEntry.find('.requiredQunatity').val(0.0);
						// newEntry.find('.unitCost').val(0.0);
						newEntry.find('.totalCost').val(0.0);
						newEntry.find('.previousYearConsumption').val(0.0);

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

	var reqQty = $("#requiredQunatity" + sequence).val();
	var uc = $("#unitCost" + sequence).val();
	(reqQty == '') ? reqQty = 0 : reqQty = reqQty;
	(uc == '') ? uc = 0 : uc = uc;

	var requiredQunatity = parseFloat(reqQty);
	var unitCost = parseFloat(uc);
	var totalCost = requiredQunatity * unitCost;
	$("#totalCost" + sequence).val(totalCost.toFixed(2));

}

$("#confirmButton").click(function() {
	if (confirm("***N.B: After Confirm, you can not modify this requirement Note!!!\nDo you want to confirm now?") == true) {
		var contextPath = $('#contextPath').val();
		var cData = {
			"id" : $("#demandNoteMstId").val()
		}
		var path = contextPath + "/mps/dn/confirmDemandNote.do";
		postSubmit(path, cData, 'POST');
	}
});
