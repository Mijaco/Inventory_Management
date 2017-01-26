$(document).ready(function() {
	var storeCode = $("#requisitionTo").val();
	if (storeCode == 'cs') {
		$("#storeDestination").text('Central Store');
	} else if (storeCode == 'ss') {
		$("#storeDestination").text('Sub Store');
	}
});

function qantityValidation(index) {
	// var temp = $(element).attr("id");
	// var idPrefix = "requiredQty";
	// var index = temp.substr(idPrefix.length);

	var remainingQty = $.trim($("#remainingQty" + index).val());
	remainingQty = parseFloat(remainingQty);

	var requiredQty = $("#quantityRequired" + index).val();
	requiredQty = parseFloat(requiredQty);

	// 1. First Check Remaining Qty vs. Required Quantity from GUI
	// 2. If pass, Then check Required Quantity vs. DB Store Quantity
	if (requiredQty > 0) {
		if (requiredQty > remainingQty) {
			$("#requiredQty-validation-db-error" + index)
					.css('display', 'none');
			$("#requiredQty-validation-zero-error" + index).css('display',
					'none');
			$("#requiredQty-validation-error" + index).css('display', 'inline');
			$("#quantityRequired" + index).focus();
			$("#quantityRequired" + index).val(0);
			$("#quantityRequired" + index).css('border', 'none');
		} else {
			$("#requiredQty-validation-error" + index).css('display', 'none');
			// alert("Go ahead");
			var jsonData = {
				id : $("#pk" + index).val(),
				quantity : requiredQty.toFixed(2),
				requisitionNo : $("#requisitionNo").val(),
				contractNo : $("#workOrderNo").val()
			}
			var cData = JSON.stringify(jsonData);

			$.ajax({
				url : $("#contextPath").val()
						+ '/cnws/storeQuantityCheckShowPage.do',
				data : cData,
				contentType : "application/json",
				success : function(data) {
					data = JSON.parse(data);
					if (data == 'success') {
						$("#requiredQty-validation-db-error" + index).css(
								'display', 'none');
						$("#requiredQty-validation-zero-error" + index).css(
								'display', 'none');
						$("#requiredQty-validation-error" + index).css(
								'display', 'none');
						$("#quantityRequired" + index).css('border',
								'2px solid #0f0');

						updateRequisition(index);
					} else {
						$("#requiredQty-validation-db-error" + index).css(
								'display', 'none');
						$("#requiredQty-validation-zero-error" + index).css(
								'display', 'none');
						$("#requiredQty-validation-db-error" + index).css(
								'display', 'inline');
						$("#quantityRequired" + index).css('border', 'none');
						$("#quantityRequired" + index).focus();
						$("#quantityRequired" + index).val(0);
					}
				},
				error : function(data) {
					alert("Server Error");
				},
				type : 'POST'
			});
		}
	} else {
		$("#requiredQty-validation-error" + index).css('display', 'none');
		$("#requiredQty-validation-db-error" + index).css('display', 'none');
		$("#requiredQty-validation-zero-error" + index)
				.css('display', 'inline');
		$("#quantityRequired" + index).css('border', 'none');
		$("#quantityRequired" + index).focus();
		$("#quantityRequired" + index).val(0);
	}

}

function approveCnWsStoreRequisition() {
	var contextPath = $("#contextPath").val();
	var requisitionNo = $("#requisitionNo").val();
	var justification = $("#justification").val();
	/*
	 * var reqDtlIdList = []; var reqQtyList = [];
	 * 
	 * $(".reqDtlId").each(function() { reqDtlIdList.push($(this).val()); });
	 * 
	 * $(".requiredQty").each(function() { reqQtyList.push($(this).val()); });
	 */

	var cData = {
		requisitionNo : $("#requisitionNo").val(),
		// cnReqDtlId : reqDtlIdList,
		// quantityRequired : reqQtyList,
		justification : justification
	}

	var path = contextPath + "/cnws/approveCnWsMatsRequisition.do";

	postSubmit(path, cData, 'POST');

}

function forwardToUpper(stateCode) {
	var contextPath = $("#contextPath").val();
	var requisitionNo = $("#requisitionNo").val();
	var justification = $("#justification").val();

	var cData = {
		requisitionNo : $("#requisitionNo").val(),
		justification : justification,
		stateCode : stateCode
	}

	var path = contextPath + "/cnws/sendToCnWsMatsRequisition.do";

	postSubmit(path, cData, 'POST');
}

function backToLower(stateCode) {
	var contextPath = $("#contextPath").val();
	var requisitionNo = $("#requisitionNo").val();
	var justification = $("#justification").val();

	var cData = {
		requisitionNo : $("#requisitionNo").val(),
		justification : justification,
		stateCode : stateCode
	}

	var path = contextPath + "/cnws/backToCnWsMatsRequisition.do";

	postSubmit(path, cData, 'POST');
}

function enableEditMode(n) {
	$('#editBtn' + n).css("display", "none");
	$("#quantityRequired" + n).removeAttr("readonly");
	// $("#repairQty3P"+n).removeAttr("readonly");
	// $("#preventiveQty1P"+n).removeAttr("readonly");
	// $("#preventiveQty3P"+n).removeAttr("readonly");
	$("#remarks" + n).removeAttr("readonly");
	$('#updateBtn' + n).css("display", "");

}

function enableUpdateMode(n) {
	$('#updateBtn' + n).css("display", "none");
	$("#quantityRequired" + n).attr("readonly", "readonly");
	// $("#repairQty3P"+n).attr("readonly","readonly");
	// $("#preventiveQty1P"+n).attr("readonly","readonly");
	// $("#preventiveQty3P"+n).attr("readonly","readonly");
	$("#remarks" + n).attr("readonly", "readonly");
	$('#editBtn' + n).css("display", "");
	updateWsMatsRequisition(n);
}

function updateWsMatsRequisition(n) {
	qantityValidation(n);	
}

function updateRequisition (n) {
	var id = $('#pk' + n).val();
	var contextPath = $('#contextPath').val();
	var path = contextPath + '/cnws/updateWsMatsRequisition.do';

	var cData = {
		id : id,
		quantityRequired : $("#quantityRequired" + n).val(),
		remarks : $("#remarks" + n).val(),
	};
	var cDataJsonString = JSON.stringify(cData);
	$.ajax({
		url : path,
		data : cDataJsonString,
		contentType : "application/json",
		success : function(data) {
			var result = JSON.parse(data);
			if (result == 'success') {
				$('.alert.alert-success').removeClass('hide');
				$(".alert.alert-success").fadeTo(5000, 500).slideUp(500,
						function() {
						});
			} else {
				$('.alert.alert-danger').removeClass('hide');
				$(".alert.alert-danger").fadeTo(5000, 500).slideUp(500,
						function() {
						});
			}

		},
		error : function(data) {
			alert("Server Error");
			$('.alert.alert-danger').removeClass('hide');
			$(".alert.alert-danger").fadeTo(5000, 500).slideUp(500);
		},
		type : 'POST'
	});
}

function deleteAnItem(n) {
	var id = $('#pk' + n).val();
	var contextPath = $('#contextPath').val();
	var path = contextPath + '/cnws/deleteAnItem.do';

	var cData = {
		id : id
	};
	var cDataJsonString = JSON.stringify(cData);
	postSubmit(path, cData, 'POST');

}