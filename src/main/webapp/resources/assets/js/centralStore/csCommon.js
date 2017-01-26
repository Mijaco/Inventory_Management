/*!
 * Custom JS v0.0.1 
 * Copyright @ 2016, IBCS Software Ltd.
 * Author: Ahasanul Ashid, Programmer, Bespoke Solution.
 */

// Date Picker Function
$(function() {
	$(".datepicker-13").datepicker({
		dateFormat : 'yy-mm-dd'
	});
	$(".datepicker-13").datepicker("option", "maxDate", new Date());
	$(".datepicker-13").datepicker("hide");
});


//For end date selection -- Ihteshamul Alam
$(function() {
	$(".datepicker-14").datepicker({
		dateFormat : 'yy-mm-dd'
	});
	$(".datepicker-14").datepicker("hide");
});

//BD style date selection -- Ashid
$(function() {
	$(".datepicker-15").datepicker({
		dateFormat : 'dd-mm-yy'
	});
	$(".datepicker-15").datepicker("option", "maxDate", new Date());
	$(".datepicker-15").datepicker("hide");
});

//For end date selection -- Ihteshamul Alam
$(function() {
	$(".datepicker-16").datepicker({
		dateFormat : 'dd-mm-yy'
	});
	$(".datepicker-16").datepicker("hide");
});

// submit form by JavaScript
function postSubmit(path, params, method) {
	method = method || "post"; // Set method to post by default if not
								// specified.

	// The rest of this code assumes you are not using a library.
	// It can be made less wordy if you use one.
	var form = document.createElement("form");
	form.setAttribute("method", method);
	form.setAttribute("action", path); 
	form.setAttribute("id", "ibcs415");

	for ( var key in params) {
		if (params.hasOwnProperty(key)) {
			var hiddenField = document.createElement("input");
			hiddenField.setAttribute("type", "hidden");
			hiddenField.setAttribute("name", key);
			hiddenField.setAttribute("value", params[key]);

			form.appendChild(hiddenField);
		}
	}
	document.body.appendChild(form);
	form.submit();
	$("#ibcs415").remove();
	//document.body.removeChild(form);
}