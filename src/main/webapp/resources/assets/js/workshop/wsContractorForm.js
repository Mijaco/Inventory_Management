$(document).ready(
		function() {

			$('#submit').click(function() {
				return validateForm();
				// return detailCheck();
			});
			
			//This will set end date in first row of Contractor Representative(s)
			
			$('#expiryDate').change( function() {
				var edDate = $('#expiryDate').val();
				$('#endDate0').val( edDate );
			});

			function validateForm() {
				var contractNo = $('#contractNo').val();
				var tenderNo = $('#tenderNo').val();
				var contractName = $('#contractName').val();
				var contactNo = $('#contactNo').val();
				var userId = $('#userId').val();
				var endDate = $('#endDate').val();
				var listedDate = $('#listedDate').val();
				var address = $('#address').val();

				var inputVal = new Array(contractNo, tenderNo, contractName,
						contactNo, userId, endDate, listedDate, address);

				var inputMessage = new Array("Contract No", "Tender No",
						"Contract Name", "Contact No", "User Id", "End Date",
						"Listed Date", "Address");

				$('.error').hide();

				if (inputVal[1] == "") {
					$('#contractNo').after(
							'<span class="error" style="color:red"> Please enter '
									+ inputMessage[1] + '</span>');
					return false;
				}
				if (inputVal[0] == "") {
					$('#tenderNo').after(
							'<span class="error" style="color:red"> Please enter '
									+ inputMessage[0] + '</span>');
					return false;
				}
				if (inputVal[2] == "") {
					$('#designation').after(
							'<span class="error" style="color:red"> Please enter '
									+ inputMessage[2] + '</span>');
					return false;
				}

				if (inputVal[3] == "") {
					$('#contactNo').after(
							'<span class="error" style="color:red"> Please enter '
									+ inputMessage[3] + '</span>');
					return false;
				}
				if (inputVal[4] == "") {
					$('#userId').after(
							'<span class="error" style="color:red"> Please a enter '
									+ inputMessage[4] + ' </span>');
					return false;
				}

				if (inputVal[5] == "") {
					$('#endDate').after(
							'<span class="error" style="color:red"> Please enter '
									+ inputMessage[5] + '</span>');
					return false;
				}
				if (inputVal[6] == "") {
					$('#listedDate').after(
							'<span class="error" style="color:red"> Please enter '
									+ inputMessage[6] + '</span>');
					return false;
				}
				if (inputVal[7] == "") {
					$('#address').after(
							'<span class="error" style="color:red"> Please enter '
									+ inputMessage[7] + '</span>');
					return false;
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
								.getElementsByTagName('div')[1], userIdInput = userIdDiv
								.getElementsByTagName('input')[0];
						userIdInput.setAttribute('id', 'repUserId'+ newNum);
						
						var errUserIdDiv = childDiv
						.getElementsByTagName('div')[1], errUserIdInput = userIdDiv
						.getElementsByTagName('h5')[0];
						errUserIdInput.setAttribute('id', 'errUserId'+ newNum);
						// end of setting id on userId fields

						// start of setting id on email fields
						var emailDiv = childDiv
								.getElementsByTagName('div')[3], emailInput = emailDiv
								.getElementsByTagName('input')[0];
						emailInput.setAttribute('id', 'email'+ newNum);
						
						var errUserEmailDiv = childDiv
						.getElementsByTagName('div')[3], errUserEmailInput = emailDiv
						.getElementsByTagName('h5')[0];
						errUserEmailInput.setAttribute('id', 'errUserEmail'+ newNum);
						// end of setting id on email fields
						
						// start of setting id on startDate fields
						var startDateDiv = childDiv
								.getElementsByTagName('div')[6], startDateInput = startDateDiv
								.getElementsByTagName('input')[0];
						startDateInput.setAttribute('id', 'startDate'+ newNum);
						// end of setting id on startDate fields
						
						// start of setting id on endDate fields
						var endDateDiv = childDiv
								.getElementsByTagName('div')[7], endDateInput = endDateDiv
								.getElementsByTagName('input')[0];
						endDateInput.setAttribute('id', 'endDate'+ newNum);
						// end of setting id on endDate fields
						
						var endDate = $('#expiryDate').val();
						
						newEntry.find('input').val('');
						$('#endDate'+newNum).val(endDate);
						/*controlForm
								.find('.entry:not(:last) .btn-add')
								.removeClass('btn-add')
								.addClass('btn-remove')
								.removeClass('btn-success')
								.addClass('btn-danger')
								.html(
										'<span class="glyphicon glyphicon-minus"></span>');*/
						newEntry.find('.datepicker-15,.datepicker-21').removeClass('hasDatepicker').datepicker({
							dateFormat: 'dd-mm-yy'
						}).val('');
					}).on('click', '.btn-remove', function(e) {
				$(this).parents('.entry:first').remove();
				return false;
			});

});

//Added by: Ihteshamul Alam
function checkUser(element) {
	var name = $(element).attr('name');
	var id = $(element).attr('id');
	var sequence = id.substr( name.length );
	var userid = $('#repUserId'+sequence).val();
	var email = $('#repEmail'+sequence).val();
	
	var saveButton = $("#saveButton");
	var contextPath = $("#contextPath").val();

	// var workOrderDecision = $("#workOrderDecision");
	var cData = {
			'userid' : userid
	}
	
	var jData = JSON.stringify(cData);

	$.ajax({
		url : contextPath + '/pnd/checkUser.do',
		data : jData,
		contentType : "application/json",
		success : function(data) {
			var result = JSON.parse(data);
			// alert(data);
			if (result == 'success') {
				if( email == "" || email == null ) { }
				else {
					saveButton.prop("disabled", false);
				}
				
				$('#errUserId'+sequence).addClass('hide');

			} else {
				saveButton.prop("disabled", true);
				$('#repUserId'+sequence).focus().select();
				$('#errUserId'+sequence).removeClass('hide');
			}
		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
}

//Added by: Ihteshamul Alam
function validateDuplicateEmail(e) {
	var name = $(e).attr('name');
	var id = $(e).attr('id');
	var sequence = id.substr( name.length );
	var email = $('#repEmail'+sequence).val();
	var userid = $('#repUserId'+sequence).val();
	
	var saveButton = $("#saveButton");
	var contextPath = $("#contextPath").val();

	// var workOrderDecision = $("#workOrderDecision");
	var cData = {
			'email' : email
	}
	
	var jData = JSON.stringify(cData);

	$.ajax({
		url : contextPath + '/pnd/checkEmail.do',
		data : jData,
		contentType : "application/json",
		success : function(data) {
			var result = JSON.parse(data);
			// alert(data);
			if (result == 'success') {
				if( userid == null || userid == "" ) { }
				else {
					saveButton.prop("disabled", false);
				}
				
				$('#errUserEmail'+sequence).addClass('hide');

			} else {
				saveButton.prop("disabled", true);
				$('#repEmail'+sequence).focus().select();
				$('#errUserEmail'+sequence).removeClass('hide');
			}
		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
}
