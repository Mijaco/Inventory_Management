$(document).ready(
		function() {

			$('#submit').click(function() {
				return validateForm();
				// return detailCheck();
			});
			
			//This will set end date in first row of Contractor Representative(s)

			function validateForm() {
				var row = $('.clonedArea').length;
				var rowCount = row+1;
				
				var contractNo = $('#workOrderNo').val();
				

				var inputVal = new Array(contractNo);

				var inputMessage = new Array("Contract No");

				$('.error').hide();

				
				if (inputVal[0] == "") {
					$('#workOrderNo').after(
							'<span class="error" style="color:red"> Please enter '
									+ inputMessage[0] + '</span>');
					return false;
				}
				for(var i=0;i<rowCount;i++){
					//var quantity = ;	

					if ($('#repairTarget1P'+i).val() == 0 || $('#repairTarget1P'+i).val() == "") {
						$('#repairTarget1P'+i).after(
								'<span class="error" style="color:red"> Please input this field </span>');
						return false;
					}
					if ($('#repairTarget3P'+i).val() == 0 || $('#repairTarget3P'+i).val() == "") {
						$('#repairTarget3P'+i).after(
								'<span class="error" style="color:red"> Please input this field </span>');
						return false;
					}
					if ($('#preventiveTarget1P'+i).val() == 0 || $('#preventiveTarget1P'+i).val() == "") {
						$('#preventiveTarget1P'+i).after(
								'<span class="error" style="color:red"> Please input this field </span>');
						return false;
					}
					if ($('#preventiveTarget3P'+i).val() == 0 || $('#preventiveTarget3P'+i).val() == "") {
						$('#preventiveTarget3P'+i).after(
								'<span class="error" style="color:red"> Please input this field </span>');
						return false;
					}
					if ($('#targetDate'+i).val() == 0 || $('#targetDate'+i).val() == "") {
						$('#targetDate'+i).after(
								'<span class="error" style="color:red"> Please input this field </span>');
						return false;
					}
					if ($('#strTargetMonth'+i).val() == 0 || $('#strTargetMonth'+i).val() == "") {
						$('#strTargetMonth'+i).after(
								'<span class="error" style="color:red"> Please input this field </span>');
						return false;
					}
				}

				return true;
			}

		});

function checkContractNumber() {
	var contractNo = $("#contractNo").val();
	var saveButton = $("#saveButton");
	var contextPath = $("#contextPath").val();

	var workOrderDecision = $("#workOrderDecision");

	$.ajax({
		url : contextPath + '/pnd/checkDuplicateContractNumber.do',
		data : "{contractNo:'" + contractNo + "'}",
		contentType : "application/json",
		success : function(data) {
			var result = JSON.parse(data);
			// alert(data);
			if (result == 'success') {
				saveButton.prop("disabled", false);
				workOrderDecision.removeClass();
				workOrderDecision
						.addClass("glyphicon glyphicon-ok-sign green big");

			} else {
				saveButton.prop("disabled", true);
				workOrderDecision.removeClass();
				workOrderDecision
						.addClass("glyphicon glyphicon-remove-sign red big");
			}
		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
}

$(function() {
	//$('.datepicker-13').datepicker();
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

						// set dynamic id on item qty fields

						var mainDiv = document
								.getElementById('myArea' + newNum), childDiv = mainDiv
								.getElementsByTagName('div')[0];

						// start of setting id on userId fields
						var userIdDiv = childDiv
								.getElementsByTagName('div')[0], userIdInput = userIdDiv
								.getElementsByTagName('input')[0];
						userIdInput.setAttribute('id', 'repairTarget1P'+ newNum);
						// end of setting id on userId fields

						// start of setting id on email fields
						var emailDiv = childDiv
								.getElementsByTagName('div')[1], emailInput = emailDiv
								.getElementsByTagName('input')[0];
						emailInput.setAttribute('id', 'repairTarget3P'+ newNum);
						// end of setting id on email fields
						
						// start of setting id on startDate fields
						var startDateDiv = childDiv
								.getElementsByTagName('div')[2], startDateInput = startDateDiv
								.getElementsByTagName('input')[0];
						startDateInput.setAttribute('id', 'preventiveTarget1P'+ newNum);
						// end of setting id on startDate fields
						
						// start of setting id on startDate fields
						var startDateDiv = childDiv
								.getElementsByTagName('div')[3], startDateInput = startDateDiv
								.getElementsByTagName('input')[0];
						startDateInput.setAttribute('id', 'preventiveTarget3P'+ newNum);
						// end of setting id on startDate fields
						
						// start of setting id on endDate fields
						var targetDateDiv = childDiv
								.getElementsByTagName('div')[4], targetDateInput = targetDateDiv
								.getElementsByTagName('input')[0];
						targetDateInput.setAttribute('id', 'targetDate'+ newNum);
						// end of setting id on endDate fields
						
						// start of setting id on endDate fields
						var strTargetMonthDiv = childDiv
								.getElementsByTagName('div')[5], strTargetMonthInput = strTargetMonthDiv
								.getElementsByTagName('input')[0];
						strTargetMonthInput.setAttribute('id', 'strTargetMonth'+ newNum);
						// end of setting id on endDate fields
						
						//var endDate = $('#expiryDate').val();
						
						newEntry.find('input').val('');
						$('#targetDate'+newNum).val('');
						/*controlForm
								.find('.entry:not(:last) .btn-add')
								.removeClass('btn-add')
								.addClass('btn-remove')
								.removeClass('btn-success')
								.addClass('btn-danger')
								.html(
										'<span class="glyphicon glyphicon-minus"></span>');*/
						newEntry.find('.datepicker-13,.datepicker-15').removeClass('hasDatepicker').datepicker({
							dateFormat: 'dd-mm-yy'
						});
					}).on('click', '.btn-remove', function(e) {
				$(this).parents('.entry:first').remove();
				return false;
			});

});

function checkUser(element) {
	var userid = $(element).val();
	var saveButton = $("#saveButton");
	var contextPath = $("#contextPath").val();

	// var workOrderDecision = $("#workOrderDecision");
	 

	$.ajax({
		url : contextPath + '/pnd/checkUser.do',
		data : "{userid:" + userid + "}",
		contentType : "application/json",
		success : function(data) {
			var result = JSON.parse(data);
			// alert(data);
			if (result == 'success') {
				saveButton.prop("disabled", false);
				$(element).siblings().css({"display": "none"});

			} else {
				saveButton.prop("disabled", true);
				 $(element).focus().select();
				 $(element).siblings().css({"display": "block"});
			}
		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
}

$(document).ready(function() {
	$(function() {
		$("#workOrderNo").autocomplete({
			source : function(request, response) {
				$.ajax({
					url : 'getwOrderNo.do',
					type : "POST",
					data : {
						wOrderNo : request.term
					},
					dataType : "json",
					success : function(data) {
						response($.map(data, function(v, i) {
							return {
								label : v.contractNo,
								value : v.contractNo
							};
						}));
					}

				});

			},
			/*select : function(event, ui) {
				getItemData(ui.item.label);
			},*/
			minLength : 1
		});
	});
});


function getMonth(element){
	var id = $(element).attr('id');
	var name = $(element).attr('name');
	var sequence = id.substr(name.length);
	$.ajax({
		type : "post",
		url : 'showMonth.do',
		async : false,
		data : 'targetDate=' + document.getElementById('targetDate'+sequence).value,
		success : function(response) {
			if (response != null || response != "") {
				document.getElementById('strTargetMonth' + sequence).value = response;
			}
		},
		error : function() {
		}
	});

	return true;
	
}
$('.targetDate').datepicker({
    dateFormat: 'dd-mm-yy',
    //numberOfMonths: 1,
    onSelect: function(selected) {
    	if(checkMonth(this))
    	{//alert(checkMonth(this));
    		getMonth(this);
    	
    	}else{alert("Target for this month is already included");
    	
    	}
    	
    }
});
/*$(document).ready(
		function() {*/

//$(".targetDate").datepicker({
	//  onSelect: function() {
	   // $(this).change();
	    //getMonth(this);
		  //document.getElementById('strTargetMonth' + sequence).value ="abc"
	  //}
	//});
		
	//});

function checkMonth(element){
	var id = $(element).attr('id');
	var name = $(element).attr('name');
	var sequence = id.substr(name.length);
	var s="";
	$.ajax({
		type : "post",
		url : 'checkMonth.do',
		async : false,
		data : 'targetDate=' + document.getElementById('targetDate'+sequence).value+'&workOrderNo='+ document.getElementById('workOrderNo').value,
		success : function(response) {
			s=response;
			if (response) {
				//alert(s);
				
				//return s;
			}else{
				document.getElementById('strTargetMonth'+sequence).value="";//alert(s);
			}
		},
		error : function() {
		}
	});

	return s;
	
}

