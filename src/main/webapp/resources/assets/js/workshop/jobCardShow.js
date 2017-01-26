$(document).ready(function() {
	var storeCode = $("#requisitionTo").val();
	if (storeCode == 'cs') {
		$("#storeDestination").text('Central Store');
	} else if (storeCode == 'ss') {
		$("#storeDestination").text('Sub Store');
	}
	
	
	//All checkbox selection - Added by Ihteshamul Alam
	$('#checkboxallselect').click( function(){
		$('.checkboxs').each( function() {
			$(this).prop('checked', true);
		});
	});
	
	
	//Multiple job deletion - Added by Ihteshamul Alam
	$("#checkboxdeleteselected").click( function() {
		var data = ""; var counter = 0;
		$('.checkboxs').each( function() {
			if( $(this).is(':checked') ) {
				if( counter == 0 ) {
					data += $(this).val();
				} else {
					data += "###" + $(this).val();
				}
				counter++;
			}
		});
		//console.log(data);
		var contextPath = $('#contextPath').val();
		var cData = {
				"jobCardNo" : data
		}
		var path = contextPath + "/jobcard/deleteMultiple.do";
		postSubmit(path, cData, 'POST');
	});
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
			$("#requiredQty" + index).css('border', 'none');
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
				url : $("#contextPath").val() + '/cnpd/storeQuantityCheck.do',
				data : cData,
				contentType : "application/json",
				success : function(data) {
					data = JSON.parse(data);
					if (data == 'success') {
						$("#requiredQty-validation-db-error" + index).css(
								'display', 'none');
						$("#requiredQty" + index).css('border',
								'2px solid #0f0');
					} else {

						$("#requiredQty-validation-db-error" + index).css(
								'display', 'inline');
						$("#requiredQty" + index).css('border', 'none');
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
		$("#requiredQty" + index).css('border', 'none');
	}

}

function approveJobCard() {
	var contextPath = $("#contextPath").val();
	var id = $("#jobCardMstId").val();
	var justification = $("#justification").val();
	/*
	 * var reqDtlIdList = []; var reqQtyList = [];
	 * 
	 * $(".reqDtlId").each(function() { reqDtlIdList.push($(this).val()); });
	 * 
	 * $(".requiredQty").each(function() { reqQtyList.push($(this).val()); });
	 */

	var cData = {
		id : id,
		// cnReqDtlId : reqDtlIdList,
		// quantityRequired : reqQtyList,
		justification : justification
	}

	var path = contextPath + "/jobcard/jobCardSubmitApproved.do";

	postSubmit(path, cData, 'POST');

}

function forwardToUpper(stateCode) {
	//alert($("#jobCardMstId").val());
	var contextPath = $("#contextPath").val();
	var jobCardNo = $("#jobCardNo").val();
	// var id = $("#jobCardMstId").val();
	var justification = $("#justification").val();

	var cData = {
		// jobCardNo : jobCardNo,
		id : $("#jobCardMstId").val(),
		justification : justification,
		stateCode : stateCode
	}

	var path = contextPath + "/jobcard/sendTo.do";

	postSubmit(path, cData, 'POST');
}

function backToLower(stateCode) {
	var contextPath = $("#contextPath").val();
	var jobCardNo = $("#jobCardNo").val();
	var justification = $("#justification").val();

	var cData = {
		// jobCardNo : jobCardNo,
		id : $("#jobCardMstId").val(),
		justification : justification,
		stateCode : stateCode
	}

	var path = contextPath + "/jobcard/backTo.do";

	postSubmit(path, cData, 'POST');
}

function updateJobCard(index, id) {
	var quantityUsed = $("#quantityUsed" + index).val();
	var quantityRecovery = $("#quantityRecovery" + index).val();
	var remarks = $("#remarks" + index).val();
	var path = $('#contextPath').val() + "/jobcard/update.do";
	var params = {
		id : id,
		quantityUsed : quantityUsed,
		quantityRecovery : quantityRecovery,
		remarks : remarks
	}
	// postSubmit(path, params ,'POST');

	var cDataJsonString = JSON.stringify(params);
	$.ajax({
		url : path,
		data : cDataJsonString,
		contentType : "application/json",
		success : function(data) {
			var pData = JSON.parse(data);
			if (pData == 'success') {
				$('.alert.alert-success').removeClass('hide');
				$(".alert.alert-success").fadeTo(5000, .5).slideUp(500,
						function() {
							// $(".alert.alert-success").alert('close');
						});
			} else {
				$('.alert.alert-danger').removeClass('hide');
				$(".alert.alert-danger").fadeTo(5000, .5).slideUp(500,
						function() {
							// $(".alert.alert-danger").alert('close');
						});
			}
		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
}

function updateAllJobCard() {
	var quantityUsed = $(".quantityUsed").val();
	var quantityRecovery = $(".quantityRecovery").val();
	var remarks = $(".remarks").val();
	var remarks = $(".jobCardDtlId").val();

	var idList = [];
	$(".jobCardDtlId").each(function() {
		var th = $(this);
		idList.push(th.val());
	});

	var remarksList = [];
	$(".remarks").each(function() {
		var th = $(this);
		remarksList.push(th.val());
	});

	var quantityUsedList = [];
	$(".quantityUsed").each(function() {
		var th = $(this);
		quantityUsedList.push(th.val());
	});

	var quantityRecoveryList = [];
	$(".quantityRecovery").each(function() {
		var th = $(this);
		quantityRecoveryList.push(th.val());
	});

	var path = $('#contextPath').val() + "/jobcard/updateAll.do";
	var params = {
		jobDtlIdList : idList,
		quantityUsed : quantityUsedList,
		quantityRecovery : quantityRecoveryList,
		remarks : remarksList
	}
	postSubmit(path, params, 'POST');
}