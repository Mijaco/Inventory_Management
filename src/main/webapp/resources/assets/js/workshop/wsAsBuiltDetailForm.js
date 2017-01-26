
function getTotalCost(element) {
	var id = $(element).attr('id');
	var name = $(element).attr('name');
	 var sequence = id.substr(name.length);
	
	 var consume = $('#materialsConsume' + sequence).val().trim();
	var consumeFloat = parseFloat(consume).toFixed(2);	
	
	var receive = $('#receivedQuantity' + sequence).val().trim();
	var receiveFloat = parseFloat(receive).toFixed(2);
	//alert(consumeFloat);
	var totalVal = (receiveFloat - consumeFloat).toFixed(2);
	

	
	$('#materialsInHand' + sequence).val(totalVal);
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