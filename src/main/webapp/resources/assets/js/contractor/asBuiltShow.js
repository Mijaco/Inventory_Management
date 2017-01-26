function getTotalCost(element) {
	var id = $(element).attr('id');
	var name = $(element).attr('name');
	 var sequence = id.substr(name.length);
	
	 var consume = $('#consume' + sequence).val().trim();
	var consumeFloat = parseFloat(consume).toFixed(2);
	//alert(consumeFloat);
	var reUse = $('#reUse' + sequence).val().trim();
	var reUseFloat = parseFloat(reUse).toFixed(2);
	var totalVal = ((+consumeFloat) + (+reUseFloat)).toFixed(2);	
	
	var recServiceable = $('#recServiceable' + sequence).val().trim();
	var recServiceableFloat = parseFloat(recServiceable).toFixed(2);
	
	var minVal = (recServiceableFloat - reUseFloat).toFixed(2);	
	
	$('#total' + sequence).val(totalVal);
	$('#reBalServiceable' + sequence).val(minVal);
}
function calc(element) {
	var id = $(element).attr('id');
	var name = $(element).attr('name');
	 var sequence = id.substr(name.length);
	
	 var recServiceable = $('#recServiceable' + sequence).val().trim();
	var recServiceableFloat = parseFloat(recServiceable).toFixed(2);
	
	var reUse = $('#reUse' + sequence).val().trim();
	var reUseFloat = parseFloat(reUse).toFixed(2);
	
	var minVal = (recServiceableFloat - reUseFloat).toFixed(2);
	$('#reBalServiceable' + sequence).val(minVal);

}

function func(value) {
	var iqd=document.getElementById(value).id;
	var name = document.getElementById(value).name;
	 var sequence = iqd.substr(name.length);
	return sequence;
}


function autoShow(idValue) {
	var index=func(idValue); 
	var recUnServiceable =document.getElementById(idValue).value;
	var recUnServiceableFloat = parseFloat(recUnServiceable).toFixed(2);
	document.getElementById("reBalUnServiceable"+index).value=recUnServiceableFloat;
}

function forwardToUpper(stateCode) {
	var justification = $('#justification').val();
	var asBuiltNo = $('#asBuiltNo').val();

	// window.location =
	// "${pageContext.request.contextPath}/cs/itemRecieved/itemReceivedForwordTo.do?receivedReportNo="+param1+"$justification="+justification;
	window.location = $('#contextPath').val() + "/asBuilt/sendTo.do?asBuiltNo="
			+ asBuiltNo
			+ "&justification="
			+ justification
			+ "&stateCode="
			+ stateCode;
}
function backToLower(stateCode) {
	var justification = $('#justification').val();
	var asBuiltNo = $('#asBuiltNo').val();

	// window.location =
	// "${pageContext.request.contextPath}/cs/itemRecieved/itemReceivedForwordTo.do?receivedReportNo="+param1+"$justification="+justification;
	window.location = $('#contextPath').val() + "/asBuilt/backTo.do?asBuiltNo="
			+ asBuiltNo
			+ "&justification="
			+ justification
			+ "&stateCode="
			+ stateCode;
}

/*function approveing() {
	var justification = $('#justification').val();
	var pndNo = $('#pndNo').val();
	var returnStateCode = $('#returnStateCode').val();

	window.location = $('#contextPath').val() + "/template/costEstimationSubmitApproved.do?pndNo="
			+ pndNo
			+ "&justification="
			+ justification
			+ "&return_state=" + returnStateCode;
}*/