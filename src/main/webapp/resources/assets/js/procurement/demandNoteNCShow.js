function editDtl(index) {
	$('#btnUpdateDtl' + index).removeClass('hide');
	$('#btnEditDtl' + index).addClass('hide');

	$('#rQty' + index).attr('readonly', false);
	$('#eUnitCost' + index).attr('readonly', false);
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

	var path = $('#contextPath').val() + "/mps/dn/demandNoteNCDtlUpdate.do";
	var params = {
		id : $('#demandNoteDtlId' + index).val(),
		requiredQunatity : parseFloat(requiredQunatity.val()),
		estimateUnitCost : parseFloat(estimateUnitCost.val()),
		estimateTotalCost : parseFloat(estimateTotalCost.val())
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
		var path = contextPath + "/mps/dn/demandNoteNCDtlDelete.do";
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
				$("#saveNewNCDemandDtlForm").submit();
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

						// start of setting id on itemCode
						var descriptionDiv = childDiv.getElementsByTagName('div')[1], descriptionInput = descriptionDiv
								.getElementsByTagName('input')[0];
						descriptionInput.setAttribute('id', 'description' + newNum);
						// end of setting id on itemCode

						// start of setting id on uom
						var uomDiv = childDiv.getElementsByTagName('div')[1], uomInput = uomDiv
								.getElementsByTagName('input')[0];
						uomInput.setAttribute('id', 'uom' + newNum);
						// end of setting id on uom

						// start of setting id on requiredQuantity fields
						var requiredQuantityDiv = childDiv
								.getElementsByTagName('div')[2], requiredQuantityInput = requiredQuantityDiv
								.getElementsByTagName('input')[0];
						requiredQuantityInput.setAttribute('id',
								'requiredQuantity' + newNum);
						// end of setting id on requiredQunatity fields

						// start of setting id on unitCost
						var unitCostDiv = childDiv.getElementsByTagName('div')[3], unitCostInput = unitCostDiv
								.getElementsByTagName('input')[0];
						unitCostInput.setAttribute('id', 'unitCost' + newNum);
						// end of setting id on unitCost

						// start of setting id on totalCost
						var totalCostDiv = childDiv.getElementsByTagName('div')[4], totalCostInput = totalCostDiv
								.getElementsByTagName('input')[0];
						totalCostInput.setAttribute('id', 'totalCost' + newNum);
						// end of setting id on totalCost

						newEntry.find('input').val('');				
						
					}).on('click', '.btn-remove', function(e) {
				$(this).parents('.entry:first').remove();
				return false;
			});

});

function setTotalCost(element) {
	var id = $(element).attr("id");
	var name = $(element).attr("name");
	var sequence = id.substr(name.length);
	var contextPath = $("#contextPath").val();

	var reqQty = $("#requiredQuantity" + sequence).val();
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
