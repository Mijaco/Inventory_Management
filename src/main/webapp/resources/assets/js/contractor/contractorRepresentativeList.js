/**
 * @author: Ihteshamul Alam
 */
$(function() {
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
						emailInput.setAttribute('id', 'repEmail'+ newNum);
						
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
						});
					}).on('click', '.btn-remove', function(e) {
				$(this).parents('.entry:first').remove();
				return false;
			});

});

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