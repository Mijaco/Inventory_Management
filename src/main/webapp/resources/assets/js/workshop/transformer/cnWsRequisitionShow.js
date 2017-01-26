$(document).ready(function() {
	var storeCode = $("#requisitionTo").val();
	if (storeCode == 'cs') {
		$("#storeDestination").text('Central Store');
	} else if (storeCode == 'ss') {
		$("#storeDestination").text('Sub Store');
	}
});

function qantityValidation(element) {
	var temp = $(element).attr("id");
	var idPrefix = "requiredQty";
	var index = temp.substr(idPrefix.length);

	var remainQty = $.trim($("#remainingQty" + index).text());
	remainQty = parseFloat(remainQty);

	var requiredQty = $("#requiredQty" + index).val();
	requiredQty = parseFloat(requiredQty);

	// 1. First Check Remaining Qty vs. Required Quantity from GUI
	// 2. If pass, Then check Required Quantity vs. DB Store Quantity
	if (requiredQty > 0) {
		if (requiredQty > remainQty) {
			$("#requiredQty-validation-error" + index).css('display', 'inline');
			$("#requiredQty" + index).focus();
			$("#requiredQty" + index).val(0);
			$("#requiredQty" + index).css('border','none');
		} else {
			$("#requiredQty-validation-error" + index).css('display', 'none');
			// alert("Go ahead");
			var jsonData = {
				id : $("#id" + index).val(),
				quantity : requiredQty.toFixed(2),
				requisitionTo : $("#requisitionTo").val(),
				contractNo : $("#workOrderNumber").val()
			}
			var cData = JSON.stringify(jsonData);

			$.ajax({
				url : $("#contextPath").val() + '/cnws/storeQuantityCheck.do',
				data : cData,
				contentType : "application/json",
				success : function(data) {
					data = JSON.parse(data);
					if (data == 'success') {
						$("#requiredQty-validation-db-error" + index).css(
								'display', 'none');
						$("#requiredQty" + index).css('border','2px solid #0f0');
					} else {

						$("#requiredQty-validation-db-error" + index).css(
								'display', 'inline');
						$("#requiredQty" + index).css('border','none');
						$("#requiredQty" + index).focus();
						$("#requiredQty" + index).val(0);
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
		$("#requiredQty" + index).css('border','none');
	}

}

function approveCnPdStoreRequisition() {
	var contextPath=$("#contextPath").val();
	var requisitionNo = $("#requisitionNo").val();
	var justification = $("#justification").val();
	var reqDtlIdList = [];
	var reqQtyList = [];

	$(".reqDtlId").each(function() {
		reqDtlIdList.push($(this).val());
	});

	$(".requiredQty").each(function() {
		reqQtyList.push($(this).val());
	});

	var cData = {
		requisitionNo : $("#requisitionNo").val(),
		cnReqDtlId : reqDtlIdList,
		quantityRequired : reqQtyList,
		justification : justification
	}
	
	var path=contextPath+"/cnws/approveCnWsRequisition.do";
	
	postSubmit(path, cData, 'POST');
	

}

function forwardToUpper(stateCode) {
	var contextPath=$("#contextPath").val();
	var requisitionNo = $("#requisitionNo").val();
	var justification = $("#justification").val();
	/*var reqDtlIdList = [];
	var reqQtyList = [];

	$(".reqDtlId").each(function() {
		reqDtlIdList.push($(this).val());
	});

	$(".requiredQty").each(function() {
		reqQtyList.push($(this).val());
	});*/

	var cData = {
		requisitionNo : $("#requisitionNo").val(),
		//cnReqDtlId : reqDtlIdList,
		//quantityRequired : reqQtyList,
		justification : justification,
		stateCode : stateCode
	}
	
	var path=contextPath+"/cnws/sendToCnWsRequisition.do";
	
	postSubmit(path, cData, 'POST');
}


function backToLower(stateCode) {
	var contextPath=$("#contextPath").val();
	var requisitionNo = $("#requisitionNo").val();
	var justification = $("#justification").val();
	/*var reqDtlIdList = [];
	var reqQtyList = [];

	$(".reqDtlId").each(function() {
		reqDtlIdList.push($(this).val());
	});

	$(".requiredQty").each(function() {
		reqQtyList.push($(this).val());
	});*/

	var cData = {
		requisitionNo : $("#requisitionNo").val(),
		//cnReqDtlId : reqDtlIdList,
		//quantityRequired : reqQtyList,
		justification : justification,
		stateCode : stateCode
	}
	
	var path=contextPath+"/cnws/backToCnWsRequisition.do";
	
	postSubmit(path, cData, 'POST');
}