function storeLeaveChange(element) {
	$('.requiredQuantity').val(0);
}

function quantityValidation(element, jobDtlId, remainningQuantity) {

	var temp = $(element).attr("id");
	var idPrefix = "requiredQuantity";
	var index = temp.substr(idPrefix.length);

	var requisitionTo = $('#requisitionTo').val();
	var requiredQuantity = element.value;

	var remainningQty = parseFloat(remainningQuantity);
	var requiredQty = parseFloat(requiredQuantity);

	var contractNo = $('#contractNo').text();

	var contextPath = $("#contextPath").val();
	if (requiredQty > 0) {
		if (remainningQty >= requiredQty) {
			$("#requiredQty-validation-error" + index).css('display', 'none');
			$("#requiredQty-validation-db-error" + index)
					.css('display', 'none');
			var jsonData = {
				id : jobDtlId,
				quantity : requiredQty.toFixed(2),
				requisitionTo : requisitionTo,
				contractNo : contractNo
			}
			var cData = JSON.stringify(jsonData);

			$.ajax({
				url : contextPath + '/cnpd/pnd/quantityValidation.do',
				data : cData,
				contentType : "application/json",
				success : function(data) {
					var result = JSON.parse(data);
					if (result == 'success') {
						$("#requiredQty-validation-db-error" + index).css(
								'display', 'none');
						$("#requiredQuantity" + index).css('border','2px solid #0f0');
					} else {

						$("#requiredQty-validation-db-error" + index).css(
								'display', 'inline');
						$("#requiredQuantity" + index).css('border','none');
						$("#requiredQuantity" + index).focus();
						$("#requiredQuantity" + index).val(0);
					}
				},
				error : function(data) {
					alert("Server Error");
				},
				type : 'POST'
			});
		} else {
			$("#requiredQty-validation-error" + index).css('display', 'inline');
			$("#requiredQuantity" + index).focus();
			$("#requiredQuantity" + index).val(0);
			$("#requiredQuantity" + index).css('border','none');
		}
	} else {
		$("#requiredQty-validation-error" + index).css('display', 'none');
		$("#requiredQty-validation-db-error" + index).css('display', 'none');
		$("#requiredQuantity" + index).css('border','none');
	}
}

function checkZeroQuantity() {
	var flag = false;
	$(".requiredQuantity").each(function() {
		var requiredQuantity = parseFloat($(this).val());
		if (requiredQuantity > 0) {
			flag = true;
			break;
		}
	});

	return flag;

}

$(document).ready(function() {
	// option A
	$("#myForm").submit(function(e) {
		var flag = false;
		$(".requiredQuantity").each(function() {
			var requiredQuantity = parseFloat($(this).val());
			if (requiredQuantity > 0) {
				flag = true;
				return flag;
			}
		});
		if (flag) {
			console.log("Successful");
		} else {

			alert("Please input quantity minimum one item.");
			e.preventDefault(e);
		}
	});
});
