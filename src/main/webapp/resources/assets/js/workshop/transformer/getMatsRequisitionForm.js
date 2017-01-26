$(document)
	.ready(
		function() {
			$('#inventoryLookupItemTable').DataTable({	
				"paging": false,
				"info": false,
				"order" : [ [ 0, 'asc' ] ]
			});
			document.getElementById('inventoryLookupItemTable_length').style.display = 'none';
			//document.getElementById('inventoryLookupItemTable_filter').style.display = 'none';			
		});
		


function removeRow(id) {
	var flag=confirm("Do you want remove the item?");
	if(flag==true){
		$('#row'+id).remove();
		$('.alert.alert-success').removeClass('hide');
		$(".alert.alert-success").fadeTo(5000, 500).slideUp(500,
				function() {});
	}else{
		return false;
	}
	
} 

function qantityValidation(element, index) {
	var temp = $(element).attr("id");
	//var idPrefix = "requiredQty";
	//var index = temp.substr(idPrefix.length);

	var remainingQty = $.trim($("#remainingQty" + index).val());
	remainingQty = parseFloat(remainingQty);

	var requiredQty = $("#quantityRequired" + index).val();
	requiredQty = parseFloat(requiredQty);

	// 1. First Check Remaining Qty vs. Required Quantity from GUI
	// 2. If pass, Then check Required Quantity vs. DB Store Quantity
	if (requiredQty > 0) {
		if (requiredQty > remainingQty) {
			$("#requiredQty-validation-db-error" + index).css('display', 'none');
			$("#requiredQty-validation-zero-error" + index).css('display', 'none');			
			$("#requiredQty-validation-error" + index).css('display', 'inline');
			$("#quantityRequired" + index).focus();
			$("#quantityRequired" + index).val(0);
			$("#quantityRequired" + index).css('border','none');
		} else {
			$("#requiredQty-validation-error" + index).css('display', 'none');
			// alert("Go ahead");
			var jsonData = {
				id : $("#dtlId" + index).val(),
				quantity : requiredQty.toFixed(2),
				//requisitionTo : $("#requisitionTo").val(),
				contractNo : $("#workOrderNo").val()
			}
			var cData = JSON.stringify(jsonData);

			$.ajax({
				url : $("#contextPath").val() + '/cnws/storeQuantityCheckAndValidation.do',
				data : cData,
				contentType : "application/json",
				success : function(data) {
					data = JSON.parse(data);
					if (data == 'success') {
						$("#requiredQty-validation-db-error" + index).css(
								'display', 'none');
						$("#requiredQty-validation-zero-error" + index).css(
								'display', 'none');
						$("#requiredQty-validation-error" + index).css('display', 'none');
						$("#quantityRequired" + index).css('border','2px solid #0f0');
					} else {
						$("#requiredQty-validation-db-error" + index).css(
								'display', 'none');
						$("#requiredQty-validation-zero-error" + index).css(
								'display', 'none');
						$("#requiredQty-validation-db-error" + index).css(
								'display', 'inline');
						$("#quantityRequired" + index).css('border','none');
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
		$("#requiredQty-validation-zero-error" + index).css('display', 'inline');
		$("#quantityRequired" + index).css('border','none');
		$("#quantityRequired" + index).focus();
		$("#quantityRequired" + index).val(0);
	}

}


$(window).load( function() {
	$('.loader').fadeOut('slow');
});

function saveMatsRequsition(){
	var flag = false;
	var dtlId = $('.dtlId').val();
	var quantityRequired = $('.quantityRequired').val();
	var remarksDtl = $('.remarksDtl').val();
	
	var dtlIdList = [];
	$(".dtlId").each(function() {
		var th = $(this);
		dtlIdList.push(th.val());
	});

	var quantityRequiredList = [];
	$(".quantityRequired").each(function() {
		var th = $(this);
		var doubleVal = parseFloat(th.val());
		if(doubleVal>0){
		quantityRequiredList.push(th.val());
		flag = true;
		}else{		
			alert("Required Quantity can not be 0 or null");
			flag = false;
			return flag;
		}
	});
	if(flag){
	var remarksDtlList = [];
	$(".remarksDtl").each(function() {
		var th = $(this);
		remarksDtlList.push(th.val());
	});
	
	var path = $('#contextPath').val() + "/cnws/wsMatsRequisitionSave.do";
	var params = {
			dtlId : dtlIdList,
			quantityRequired : quantityRequiredList,
			remarksDtl : remarksDtlList
	}
	postSubmit(path, params, 'POST');
	}else{		
		//alert("Required Quantity can not be 0 or null");
	}
}