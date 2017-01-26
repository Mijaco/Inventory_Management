function itemLeaveChange(element) {
	// alert("contractNo");
	var temp = $(element).closest("div").parent().parent().attr("id");
	var sequence = temp.substr(temp.length - 1);

	var e = $(element).closest("div").parent().parent().find('.itemName').attr(
			'id', sequence);
	// var e = document.getElementById('' + sequence);
	var e1 = e[0];
	var item_id = e1.options[e1.selectedIndex].value;
	var contractNo = document.getElementById("contractNo").value;

	// alert(contractNo+"---"+item_id);
	$.ajax({
		// url : 'procurement/requisition/viewInventoryItem.do',
		url : 'cnViewInventoryItem.do',
		data : "{id:" + item_id + "," + contractNo + "}",
		// data : "{id:" + item_id + "}",
		contentType : "application/json",
		success : function(data) {
			// alert(data);
			var PndJobDtl = JSON.parse(data);
			$(element).closest("div").parent().parent().find('.itemCode').val(
					PndJobDtl.itemCode);
			$(element).closest("div").parent().parent().find('.uom').val(
					PndJobDtl.uom);
			$(element).closest("div").parent().parent().find('.description')
					.val(PndJobDtl.itemName);
			$(element).closest("div").parent().parent().find('.currentStock')
					.val(PndJobDtl.remainningQuantity);
		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
}

function reqQtyNotGreaterThenCurrentStock(element) {
	var id = $(element).attr('id');
	var sequence = id.substr(id.length - 1);

	// var expected = parseInt($('#currentStock' + sequence).val().trim(), 10);

	var currentStock = parseFloat($('#currentStock' + sequence).val().trim())
			.toFixed(2);
	currentStock = parseFloat(currentStock);

	var temp = $('#quantityRequired' + sequence).val().trim();
	var quantityRequired = parseFloat(temp).toFixed(2);
	quantityRequired = parseFloat(quantityRequired);

	// alert(currentStock);

	if (currentStock < quantityRequired) {
		$(element).val($('#currentStock' + sequence).val());
	}

}

$(function() {
	$(document)
			.on(
					'click',
					'.btn-add',
					function(e) {
						// e.preventDefault();

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

						// start of seting id on currentStock fields
						var currentStockDiv = childDiv
								.getElementsByTagName('div')[4], currentStockInput = currentStockDiv
								.getElementsByTagName('input')[0];
						currentStockInput.setAttribute('id', 'currentStock'
								+ newNum);
						// end of seting id on currentStock fields

						// start of seting id on quantityRequired fields
						var quantityRequiredDiv = childDiv
								.getElementsByTagName('div')[5], quantityRequiredInput = quantityRequiredDiv
								.getElementsByTagName('input')[0];
						quantityRequiredInput.setAttribute('id',
								'quantityRequired' + newNum);
						// end of seting id on quantityRequired fields

						// start of seting id on quantityIssued fields
						var quantityIssuedDiv = childDiv
								.getElementsByTagName('div')[6], quantityIssuedInput = quantityIssuedDiv
								.getElementsByTagName('input')[0];
						quantityIssuedInput.setAttribute('id', 'quantityIssued'
								+ newNum);
						// end of seting id on quantityIssued fields

						// start of seting id on unitCost fields
						var unitCostDiv = childDiv.getElementsByTagName('div')[7], unitCostInput = unitCostDiv
								.getElementsByTagName('input')[0];
						unitCostInput.setAttribute('id', 'unitCost' + newNum);
						// end of seting id on unitCost fields

						// start of seting id on totalCost fields
						var totalCostDiv = childDiv.getElementsByTagName('div')[8], totalCostInput = totalCostDiv
								.getElementsByTagName('input')[0];
						totalCostInput.setAttribute('id', 'totalCost' + newNum);
						// end of seting id on totalCost fields

						newEntry.find('input').val('');

						controlForm
								.find('.entry:not(:last) .btn-add')
								.removeClass('btn-add')
								.addClass('btn-remove')
								.removeClass('btn-success')
								.addClass('btn-danger')
								.html(
										'<span class="glyphicon glyphicon-minus"></span>');
					}).on('click', '.btn-remove', function(e) {
				$(this).parents('.entry:first').remove();

				// e.preventDefault();
				return false;
			});

});

$(document).ready(
		function() {

			$('#submit').click(function() {
				return validateForm();
				// return detailCheck();
			});

			function validateForm() {

				/*
				 * var nameReg = /^[A-Za-z]+$/; var numberReg = /^[0-9]+$/; var
				 * emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
				 */
				var representiveName = $('#representiveName').val();
				var contractNo = $('#contractNo').val();
				var designation = $('#designation').val();
				var contactNo = $('#contactNo').val();
				var userId = $('#userId').val();
				var endDate = $('#endDate').val();
				var listedDate = $('#listedDate').val();
				var address = $('#address').val();

				var inputVal = new Array(representiveName, contractNo,
						designation, contactNo, userId, endDate, listedDate,
						address);

				var inputMessage = new Array("Representive Name",
						"Contract No", "Designation", "Contact No", "User Id",
						"End Date", "Listed Date", "Address");

				$('.error').hide();
				if (inputVal[0] == "") {
					$('#representiveName').after(
							'<span class="error" style="color:red"> Please enter '
									+ inputMessage[0] + '</span>');
					return false;
				}
				if (inputVal[1] == "") {
					$('#contractNo').after(
							'<span class="error" style="color:red"> Please enter '
									+ inputMessage[1] + '</span>');
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
				isEmail();

				return true;
			}
		});

function checkUser() {
	var userid = $("#userid").val();
	var saveButton = $("#saveButton");

	var contextPath = $("#contextPath").val();

	var workOrderDecision = $("#workOrderDecision");

	$.ajax({
		url : contextPath + '/pnd/checkUser.do',
		data : "{userid:" + userid + "}",
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

function isEmail() {
	var email = $("#email").val();
	var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;

	if (regex.test(email)) {
		$("#emailvaldationFlag").removeClass();
		$("#emailvaldationFlag").addClass(
				"glyphicon glyphicon-ok-sign green big");
		return true;
	} else {
		$("#saveButton").prop("disabled", true);
		$("#emailvaldationFlag").removeClass();
		$("#emailvaldationFlag").addClass(
				"glyphicon glyphicon-remove-sign red big");
		return regex.test(email);
	}
}