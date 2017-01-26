
function getTotalCost(element) {
	var id = $(element).attr('id');
	var name = $(element).attr('name');
	 var sequence = id.substr(name.length);
	
	 var consume = $('#consume' + sequence).val().trim();
	var consumeFloat = parseFloat(consume).toFixed(3);
	//alert(consumeFloat);
	var reUse = $('#reUse' + sequence).val().trim();
	var reUseFloat = parseFloat(reUse).toFixed(3);
	var totalVal = ((+consumeFloat) + (+reUseFloat)).toFixed(3);	
	
	var recServiceable = $('#recServiceable' + sequence).val().trim();
	var recServiceableFloat = parseFloat(recServiceable).toFixed(3);
	
	var minVal = (recServiceableFloat - reUseFloat).toFixed(3);	
	
	$('#total' + sequence).val(totalVal);
	$('#reBalServiceable' + sequence).val(minVal);
}
function calc(element) {
	var id = $(element).attr('id');
	var name = $(element).attr('name');
	 var sequence = id.substr(name.length);
	
	 var recServiceable = $('#recServiceable' + sequence).val().trim();
	var recServiceableFloat = parseFloat(recServiceable).toFixed(3);
	
	var reUse = $('#reUse' + sequence).val().trim();
	var reUseFloat = parseFloat(reUse).toFixed(3);
	
	var minVal = (recServiceableFloat - reUseFloat).toFixed(3);
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
	var recUnServiceableFloat = parseFloat(recUnServiceable).toFixed(3);
	document.getElementById("reBalUnServiceable"+index).value=recUnServiceableFloat;
}