function forwardToUpper(stateCode) {
	if( $('#justification').val() == null || $.trim( $('#justification').val() ) == '' ) {
		$('.justification').removeClass('hide');
		$('#justification').focus();
		return;
	} else {
		var justification = $('#justification').val();
		var returnSlipNo = $('#returnSlipNo').val();
		var returnTo = $('#returnTo').val();
		
		window.location = $("#contextPath").val()
				+ "/cn/pdReturnSlip/sendTo.do?returnSlipNo=" + returnSlipNo
				+ "&justification=" + justification + "&stateCode=" + stateCode + "&returnTo" + returnTo;
	}
}

function backToLower(stateCode) {
	if( $('#justification').val() == null || $.trim( $('#justification').val() ) == '' ) {
		$('.justification').removeClass('hide');
		$('#justification').focus();
		return;
	} else {
		var justification = $('#justification').val();
		var returnSlipNo = $('#returnSlipNo').val();
		var returnTo = $('#returnTo').val();

		window.location = $("#contextPath").val()+"/cn/returnSlip/backTo.do?returnSlipNo="
				+ returnSlipNo
				+ "&justification="
				+ justification
				+ "&stateCode="
				+ stateCode
				+ "&returnTo"
				+returnTo;
	}
	
}

function approveCnPdReturnSlip() {
	if( $('#justification').val() == null || $.trim( $('#justification').val() ) == '' ) {
		$('.justification').removeClass('hide');
		$('#justification').focus();
		return;
	} else {
		$('#approveButton').prop('disabled', true);
		var justification = $('#justification').val();
		var returnSlipNo = $('#returnSlipNo').val();
		var returnStateCode = $('#returnStateCode').val();
		var returnTo = $('#returnTo').val();
		
		var path= $("#contextPath").val()+'/cn/pd/itemReturnSlipApproved.do';
		
		var params={
				returnSlipNo: returnSlipNo,
				justification: justification,
				return_state: returnStateCode,
				returnTo: returnTo
		}	
		postSubmit(path, params, 'POST') ;
	}
	
}

function editItem(id) {

	$.ajax({
		url :  $("#contextPath").val()+'/cs/itemRecieved/itemEdit.do',
		data : "{id:" + id + "}",
		contentType : "application/json",
		success : function(data) {
			var item = JSON.parse(data);
			$("#modal_itemId").val(item.itemId);
			$("#modal_description").val(item.description);
			$("#modal_expectedQuantity").val(item.expectedQty);
			$("#modal_id").val(item.id);
			$("#modal_receivedQuantity").val(item.receivedQty);
			$("#modal_reminingQuantity").val(item.remainingQty);

		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
}
