$(function() {
	$(document)
			.on(
					'click',
					'.btn-add',
					function(e) {
						var num = $('.clonedArea').length;
						/*
						 * var controlForm = $('.main-div div:first'),
						 * currentEntry = $(
						 * this).parents('.full-element:first'), newEntry = $(
						 * currentEntry.clone().attr('id', 'myArea' +
						 * newNum).addClass(
						 * 'full-element')).appendTo(controlForm);
						 */

						var newNum = num + 1;
						var controlForm = $('.controls div:first'), currentEntry = $(
								this).parents('.entry:first'), newEntry = $(
								currentEntry.clone().attr('id',
										'myArea' + newNum).addClass(
										'clonedArea')).appendTo(controlForm);

						var mainDiv = document
								.getElementById('myArea' + newNum), childDiv = mainDiv
								.getElementsByTagName('div')[0];
						// start of setting id on category fields
						var categoryDiv = childDiv.getElementsByTagName('div')[0], categoryInput = categoryDiv
								.getElementsByTagName('select')[0];
						categoryDiv.setAttribute('disabled', '');
						categoryInput.setAttribute('id', 'category' + newNum);

						// end of setting id on category fields

						// start of setting id on description and itemName
						var itemNameDiv = childDiv.getElementsByTagName('div')[1], descriptionInput = itemNameDiv
								.getElementsByTagName('input')[0], itemNameInput = itemNameDiv
								.getElementsByTagName('select')[0];
						descriptionInput.setAttribute('id', 'description'
								+ newNum);
						itemNameInput.setAttribute('id', 'itemName' + newNum);
						// end of setting id on description and itemName

						// start of setting id on itemCode
						var itemCodeDiv = childDiv.getElementsByTagName('div')[2], itemIdInput = itemCodeDiv
								.getElementsByTagName('input')[0], itemCodeInput = itemCodeDiv
								.getElementsByTagName('input')[1];
						itemIdInput.setAttribute('id', 'itemId' + newNum);
						itemCodeInput.setAttribute('id', 'itemCode' + newNum);
						// end of setting id on itemCode
						var costSource = childDiv.getElementsByTagName('div')[6], costSourceSelect = costSource
								.getElementsByTagName('select')[0];
						costSourceSelect.setAttribute('id', 'costSource'
								+ newNum);
						// start of setting id on uom
						var uomDiv = childDiv.getElementsByTagName('div')[3], uomInput = uomDiv
								.getElementsByTagName('input')[0];
						uomInput.setAttribute('id', 'uom' + newNum);
						// end of setting id on uom

						// start of setting id on receivedQty fields
						var receivedQtyDiv = childDiv
								.getElementsByTagName('div')[4], receivedQtyInput = receivedQtyDiv
								.getElementsByTagName('input')[0];
						receivedQtyInput.setAttribute('id', 'receivedQty'
								+ newNum);
						// end of setting id on receivedQty fields

						// start of setting id on unitCost
						var unitCostDiv = childDiv.getElementsByTagName('div')[5], unitCostInput = unitCostDiv
								.getElementsByTagName('input')[0];
						unitCostInput.setAttribute('id', 'unitCost' + newNum);
						// end of setting id on unitCost

						// start of setting id on totalCost
						var totalCostDiv = childDiv.getElementsByTagName('div')[7], totalCostInput = totalCostDiv
								.getElementsByTagName('input')[0];
						totalCostInput.setAttribute('id', 'totalCost' + newNum);

						var expenditureDiv = childDiv
								.getElementsByTagName('div')[8], expenditureCat = expenditureDiv
								.getElementsByTagName('select')[0];
						console.log(expenditureDiv);
						expenditureCat.setAttribute('id', 'expCat' + newNum);

						// end of setting id on totalCost

						// start of setting id on remarks
						/*
						 * var remarksDiv =
						 * childDiv.getElementsByTagName('div')[7], remarksInput =
						 * remarksDiv .getElementsByTagName('input')[0];
						 */// remarksInput.setAttribute('id', 'remarks' +
						// newNum);
						// end of setting id on remarks
						newEntry.find('.category')[0].disabled = false;
						newEntry.find('.itemName')[0].disabled = false;
						newEntry.find('input').val('');
						newEntry.find('.receivedQty').val(0.0);
						newEntry.find('.unitCost').val(0.0);
						newEntry.find('.totalCost').val(0.0);

						/*
						 * controlForm .find('.entry:not(:last) .btn-add')
						 * .removeClass('btn-add') .addClass('btn-remove')
						 * .removeClass('btn-success') .addClass('btn-danger')
						 * .html( '<span class="glyphicon glyphicon-minus"></span>');
						 */
					})
			.on(
					'click',
					'.btn-add-more',
					function(e) {
						var num = $('.clonedArea').length;

						/*
						 * var controlForm = $('.main-div div:first'),
						 * currentEntry = $(
						 * this).parents('.full-element:first'), newEntry = $(
						 * currentEntry.clone().attr('id', 'myArea' +
						 * newNum).addClass(
						 * 'full-element')).appendTo(controlForm);
						 */
						var newNum = num;
						var controlForm = $('.controls div:first'), currentEntry = $(
								this).parents('.entry:first'), newEntry = $(
								currentEntry.clone().attr('id',
										'myArea' + newNum).addClass(
										'clonedArea')).appendTo(controlForm);

						var mainDiv = document
								.getElementById('myArea' + newNum), childDiv = mainDiv
								.getElementsByTagName('div')[0];
						// start of setting id on category fields
						var categoryDiv = childDiv.getElementsByTagName('div')[0], categoryInput = categoryDiv
								.getElementsByTagName('select')[0], idInput = categoryDiv
								.getElementsByClassName('budgetDtlId')[0];
						categoryInput.setAttribute('id', 'category' + newNum);
						idInput.setAttribute('value', '0');
						idInput.setAttribute('id', 'budgetDtl' + newNum);
						/*
						 * idInput.setAttribute('id', 'budgetDtl'+newNum);
						 * idInput.setAttribute('value', '0');
						 */
						// end of setting id on category fields
						// start of setting id on description and itemName
						var itemNameDiv = childDiv.getElementsByTagName('div')[1], descriptionInput = itemNameDiv
								.getElementsByTagName('input')[0], itemNameInput = itemNameDiv
								.getElementsByTagName('select')[0];
						descriptionInput.setAttribute('id', 'description'
								+ newNum);
						itemNameInput.setAttribute('id', 'itemName' + newNum);
						// end of setting id on description and itemName

						// start of setting id on itemCode
						var itemCodeDiv = childDiv.getElementsByTagName('div')[2], itemCodeInput = itemCodeDiv
								.getElementsByTagName('input')[0];
						itemCodeInput.setAttribute('id', 'itemCode' + newNum);
						// end of setting id on itemCode

						// start of setting id on uom
						var uomDiv = childDiv.getElementsByTagName('div')[3], uomInput = uomDiv
								.getElementsByTagName('input')[0];
						uomInput.setAttribute('id', 'uom' + newNum);
						// end of setting id on uom

						// start of setting id on receivedQty fields
						var receivedQtyDiv = childDiv
								.getElementsByTagName('div')[4], receivedQtyInput = receivedQtyDiv
								.getElementsByTagName('input')[0];
						receivedQtyInput.setAttribute('id', 'receivedQty'
								+ newNum);
						// end of setting id on receivedQty fields

						// start of setting id on unitCost
						var unitCostDiv = childDiv.getElementsByTagName('div')[5], unitCostInput = unitCostDiv
								.getElementsByTagName('input')[0];
						unitCostInput.setAttribute('id', 'unitCost' + newNum);
						// end of setting id on unitCost

						// start of setting id on totalCost
						var totalCostDiv = childDiv.getElementsByTagName('div')[7], totalCostInput = totalCostDiv
								.getElementsByTagName('input')[0];
						totalCostInput.setAttribute('id', 'totalCost' + newNum);
						// end of setting id on totalCost

						var expenditureDiv = childDiv
								.getElementsByTagName('div')[8], expenditureCat = expenditureDiv
								.getElementsByTagName('select')[0];
						console.log(expenditureDiv);
						expenditureCat.setAttribute('id', 'expCat' + newNum);
						// start of setting id on remarks
						/*
						 * var remarksDiv =
						 * childDiv.getElementsByTagName('div')[7], remarksInput =
						 * remarksDiv .getElementsByTagName('input')[0];
						 */// remarksInput.setAttribute('id', 'remarks' +
						// newNum);
						// end of setting id on remarks
						newEntry.find('input').val('');
						newEntry.find('.receivedQty').val(0.0);
						newEntry.find('.unitCost').val(0.0);
						newEntry.find('.totalCost').val(0.0);
						newEntry.find('.category')
								.val(
										$(
												'#category' + newNum
														+ ' option:first')
												.val());
						newEntry.find('.itemName')
								.val(
										$(
												'#itemName' + newNum
														+ ' option:first')
												.val());

						/*
						 * controlForm .find('.entry:not(:last) .btn-add')
						 * .removeClass('btn-add') .addClass('btn-remove')
						 * .removeClass('btn-success') .addClass('btn-danger')
						 * .html( '<span class="glyphicon glyphicon-minus"></span>');
						 */
					}).on(
					'click',
					'.btn-remove',
					function(e) {
						if ($('.entry').length > 1) {
							var contextPath = $('#contextPath').val();
							var budgetDtlId = $(this).parents('.entry:first')
									.find('.budgetDtlId').val();
							if (budgetDtlId && budgetDtlId > 0) {
								var params = {
									'id' : budgetDtlId
								}
								$.ajax({
									url : contextPath
											+ '/budget/deleteBudgetDtl.do',
									/*
									 * data : params, contentType :
									 * "application/json",
									 */
									data : "{id:" + budgetDtlId + "}",
									contentType : "application/json",
									success : function(data) {

									},
									error : function(data) {
										alert("Server Error");
									},
									type : 'POST'
								});
							}

							// adjusting grand total cost
							var totalCost = $(this).parents('.entry:first')
									.find('.totalCost').val();
							var grandTotal = $("#grandTotal").text();
							grandTotal = parseFloat(grandTotal)
							grandTotal -= totalCost;
							$("#grandTotal").text(numberWithCommas(grandTotal.toFixed(2)));

							$(this).parents('.entry:first').remove();
							return false;
						}

					});

});

function itemLeaveChange(element) {
	var id = $(element).attr("id");
	var name = $(element).attr("name");
	var sequence = id.substr(name.length);
	var contextPath = $("#contextPath").val();
	var item_id = $("#" + id).val();
	var callAjax = true;
	$('.itemName').each(
			function() {
				if ($(this).attr("id") != $(element).attr("id")
						&& $(this).val() == item_id) {
					$('#btn-modal').click();
					$('#myModalLabelMsg').text("Item already added!");
					$("#" + id).val($("#" + id + " option:first").val());
					callAjax = false;
					return false;
				}
			})
	if (callAjax) {
		$.ajax({
			url : contextPath + '/workOrder/viewInventoryItem.do',
			data : "{id:" + item_id + "}",
			contentType : "application/json",
			success : function(data) {
				var inventoryItem = JSON.parse(data);
				$(element).closest("div").parent().parent().find('.itemCode')
						.val(inventoryItem.itemId);
				$(element).closest("div").parent().parent().find('.itemId')
						.val(inventoryItem.id);
				$(element).closest("div").parent().parent().find('.uom').val(
						inventoryItem.unitCode);
				$(element).closest("div").parent().parent()
						.find('.description').val(inventoryItem.itemName);
				/*
				 * $(element).closest("div").parent().parent().find('.totalCost')
				 * .val(inventoryItem.itemName);
				 */
			},
			error : function(data) {
				alert("Server Error");
			},
			type : 'POST'
		});
	}

}

function categoryLeaveChange(element) {
	var id = $(element).attr("id");
	var name = $(element).attr("name");
	var sequence = id.substr(name.length);
	var contextPath = $("#contextPath").val();
	var categoryId = $("#" + id).val();

	$.ajax({
		url : contextPath + '/workOrder/viewInventoryItemCategory.do',
		data : "{categoryId:" + categoryId + "}",
		contentType : "application/json",
		success : function(data) {
			var itemList = JSON.parse(data);
			var itemNames = $(element).closest("div").parent().parent().find(
					'.itemName');
			itemNames.empty();
			itemNames.append($("<option></option>").attr("value", '').text(
					'Select Item'));
			$.each(itemList, function(id, itemName) {
				itemNames.append($("<option></option>").attr("value", this.id)
						.text(this.itemId + ' - ' + this.itemName));
			});
		},
		error : function(data) {
			alert("Server Error");
		},
		type : 'POST'
	});
}

$("#budgetEntryForm").submit(
		function(event) {
			$('input:submit').attr("disabled", true);
			var submitForm = true;
			if ($('#descoSessionForBudget').val()
					//&& $('#budgetTypeForBudget').val()
					) {

				// checking each budgetDtl has required value using .entity (div
				// class)
				$(".entry").each(
						function(index) {
							// checking item has selected
							if (!$(this).find('.itemId').val()
									|| $(this).find('.itemId').val() < 1) {
								event.preventDefault();
								// showFormError("Item did not selected!");
								// showing modal
								$('#btn-modal').click();
								$('#myModalLabelMsg').text(
										"Item did not selected!");
								submitForm = false;
								return false;
								
								// $('#myModal').modal('show');
							}

							// checking item quantity provided
							if (!$(this).find('.receivedQty').val()
									|| $(this).find('.receivedQty').val() < 1) {
								event.preventDefault();
								// showFormError("Item Quantity is missing!");
								// showing modal
								$('#btn-modal').click();
								$('#myModalLabelMsg').text(
										"Item Quantity is missing!");
								submitForm = false;
								return false;
							}

							// checkiing rate has been provided
							if (!$(this).find('.unitCost').val()
									|| $(this).find('.unitCost').val() < 1) {
								event.preventDefault();
								// showFormError("Item Rate is missing!");
								// showing modal
								$('#btn-modal').click();
								$('#myModalLabelMsg').text(
										"Item Rate is missing!");
								submitForm = false;
								return false;
							}

						});
				if (submitForm) {
					$("#budgetEntryForm").submit();
				}else{
					$('input:submit').attr("disabled", false);
				}

			} else {
				event.preventDefault();
				// alert("Provide Budget Session and Budget Type");
				$('#btn-modal').click();
				$('#myModalLabelMsg').text(
						"Provide Budget Session");
				$('input:submit').attr("disabled", false);
			}

			/*
			 * function showFormError(message){ $('#btn-modal').click();
			 * $('#myModalLabelMsg').text(message); submitForm = false; return
			 * false; }
			 */

		});

$("#frmBudgetEdit").submit(
		function(event) {
			var submitForm = true;
			if ($('#descoSessionForBudget').val()
					//&& $('#budgetTypeForBudget').val()
					) {

				// checking each budgetDtl has required value using .entity (div
				// class)
				$(".entry").each(
						function(index) {
							// checking item has selected
							if (!$(this).find('.itemId').val()
									|| $(this).find('.itemId').val() < 1) {
								event.preventDefault();
								// showFormError("Item did not selected!");
								// showing modal
								$('#btn-modal').click();
								$('#myModalLabelMsg').text(
										"Item did not selected!");
								submitForm = false;
								return false;
								// $('#myModal').modal('show');
							}

							// checking item quantity provided
							if (!$(this).find('.receivedQty').val()
									|| $(this).find('.receivedQty').val() < 1) {
								event.preventDefault();
								// showFormError("Item Quantity is missing!");
								// showing modal
								$('#btn-modal').click();
								$('#myModalLabelMsg').text(
										"Item Quantity is missing!");
								submitForm = false;
								return false;
							}

							// checkiing rate has been provided
							if (!$(this).find('.unitCost').val()
									|| $(this).find('.unitCost').val() < 1) {
								event.preventDefault();
								// showFormError("Item Rate is missing!");
								// showing modal
								$('#btn-modal').click();
								$('#myModalLabelMsg').text(
										"Item Rate is missing!");
								submitForm = false;
								return false;
							}

						});
				if (submitForm) {
					$("#frmBudgetEdit").submit();
				}

			} else {
				event.preventDefault();
				// alert("Provide Budget Session and Budget Type");
				$('#btn-modal').click();
				$('#myModalLabelMsg').text(
						"Provide Budget Session");
			}

		});


$("#frmBudgetRevice").submit(
		function(event) {
			$('input:submit').attr("disabled", true);
			var submitForm = true;
				// checking each budgetDtl has required value using .entity (div
				// class)
				$(".entry").each(
						function(index) {
							// checking item has selected
							if (!$(this).find('.itemId').val()
									|| $(this).find('.itemId').val() < 1) {
								event.preventDefault();
								// showFormError("Item did not selected!");
								// showing modal
								$('#btn-modal').click();
								$('#myModalLabelMsg').text(
										"Item did not selected!");
								submitForm = false;
								return false;
								
								// $('#myModal').modal('show');
							}

							// checking item quantity provided
							if (!$(this).find('.receivedQty').val()
									|| $(this).find('.receivedQty').val() < 1) {
								event.preventDefault();
								// showFormError("Item Quantity is missing!");
								// showing modal
								$('#btn-modal').click();
								$('#myModalLabelMsg').text(
										"Item Quantity is missing!");
								submitForm = false;
								return false;
							}

							// checkiing rate has been provided
							if (!$(this).find('.unitCost').val()
									|| $(this).find('.unitCost').val() < 1) {
								event.preventDefault();
								// showFormError("Item Rate is missing!");
								// showing modal
								$('#btn-modal').click();
								$('#myModalLabelMsg').text(
										"Item Rate is missing!");
								submitForm = false;
								return false;
							}

						});
				if (submitForm) {
					$("#budgetEntryForm").submit();
				}else{
					event.preventDefault();
					$('input:submit').attr("disabled", false);
				}

			

		});
function setTotalCost(element) {
	var id = $(element).attr("id");
	var name = $(element).attr("data-field-name");
	var sequence = id.substr(name.length);
	var contextPath = $("#contextPath").val();

	// getting the value before changing to adjust with grand total
	var preCost = parseFloat($("#totalCost" + sequence).val().replace(/,/g, ''));

	var receivedQty = parseFloat($("#receivedQty" + sequence).val());
	var unitCost = parseFloat($("#unitCost" + sequence).val().replace(/,/g, ''));
	var totalCost = receivedQty * unitCost;
	$("#totalCost" + sequence).val(totalCost.toFixed(2));

	// setting grandTotal
	var grandTotal = $("#grandTotal").text().replace(/,/g, '');
	grandTotal = parseFloat(grandTotal);
console.log("gtotal :"+grandTotal);
	// deducting previouly added totalCost
	grandTotal = parseFloat(grandTotal -= preCost);

	// adding totalCost to grandTotal
	grandTotal += totalCost;
	$("#grandTotal").text(numberWithCommas(grandTotal.toFixed(2)));

	
}
//to use comma with number

function numberWithCommas(x) {
	 var parts = x.toString().split(".");
parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ","); 
return	 parts.join("."); }


$(document)
		.ready(
				function() {

					// show astrick mark for required fields
					$('.form-control[required]').closest(".form-group")
							.children("label").append(
									"<span class='red'> *</span>");

					/*
					 * $('#frmBudgetDetails').show();
					 * $('#frmBudgetEdit').hide();
					 */
					/*
					 * $('#btnEditBudget').on("click", function() {
					 * $('#frmBudgetEdit').show();
					 * $('#frmBudgetDetails').hide(); });
					 */
					$('#btn-modal').hide();

					/*
					 * $('#verifyDepartment').click(function() {
					 * 
					 * var loginUserDeptId = $('#loginUserDeptId').val(); var
					 * deptId = $('#departmentName').val();
					 * 
					 * if (deptId == "" || deptId == null) {
					 * $('#invalid_dept_warning').removeClass('hide'); } else {
					 * if (loginUserDeptId == deptId) {
					 * $('#invalid_dept_warning').addClass('hide');
					 * $('#department_div').addClass('hide');
					 * $('#lp_form_div').removeClass('hide'); } else {
					 * $('#invalid_dept_warning').removeClass('hide'); } }
					 * 
					 * });
					 */

					$("#descoSessionForBudget").on("change", function() {
						// checking app exists for this session
						/*
						 * checkAppForBudget().done(handleData); function
						 * handleData(data) { var convertedData =
						 * JSON.parse(data); if(convertedData.id > 0){
						 * checkDuplicateBudget(); }else{
						 * $('#appYearForBudget').text("");
						 * $("#descoSessionForBudget").val(
						 * $("#descoSessionForBudget option:first") .val());
						 * alert("No APP Found for this session!"); } }
						 */

						checkDuplicateBudget();
						getItemsBySession();
					});
					$("#budgetTypeForBudget").on("change", function() {

						checkDuplicateBudget();
					});

					function checkDuplicateBudget() {
						var contextPath = $('#contextPath').val();

						var id = $('#descoSessionForBudget').val();
						//var budgetType = $('#budgetTypeForBudget').val();

						var obj = {
								id : id
							/*descoSession : {
								
							}*//*,
							budgetType : {
								id : budgetType
							}*/
							
						}
						var str = JSON.stringify(obj);
						/*var path = contextPath
								+ '/budget/budgetMst/budgetSessionAndType/findOne.do';*/
						var path = contextPath
						+ '/budget/budgetMst/budgetSession/findOne.do';
						if (id == "") {
							return;
						}
						$('#appYearForBudget').text($("#descoSessionForBudget option:selected").text());
//						if (id && budgetType) {
						if (id) {
							$.ajax({
										url : path,
										data : str,
										contentType : "application/json",
										success : function(data) {
											var result = JSON.parse(data);
											console.log(result);
											if (result.id > 0) {
												$('#appYearForBudget').text("");
												$('#btn-modal').click();
												$('#myModalLabelMsg')
														.text(
																"Budget already added!");
												// alert("");
												/*$("#budgetTypeForBudget")
														.val(
																$(
																		"#budgetTypeForBudget option:first")
																		.val());*/
											} /*else {
												$('#appYearForBudget')
														.text(
																$(
																		"#descoSessionForBudget option:selected")
																		.text());
											}*/

										},
										error : function(data) {
											alert("Server Error");
										},
										type : 'POST'
									});
						}

					}

					function getItemsBySession() {
						var returnVal = false;
						var grandTotal = 0.0;
						var contextPath = $('#contextPath').val();
						var id = $('#descoSessionForBudget').val();
						var obj = {
							id : id
						}
						var str = JSON.stringify(obj);
						var path = contextPath
								+ '/budget/descoSession/findProcDetails.do';
						if (id == "") {
							return;
						}

						return $
								.ajax({
									url : path,
									data : str,
									contentType : "application/json",
									success : function(data) {
										var result = JSON.parse(data);
										/*console.log("total data :"
												+ result.length);*/
										console.log(result);
										if (result.length > 0) {
											console.log("comes to if");
											var controlForm = $('.controls div:first'), currentEntry = $(
													'.entry:first').clone();
											$('.entry').remove();											

											for (var i = 0; i < result.length; i++) {
												grandTotal += result[i].totalCost;
												var newNum = i;
												
												newEntry = $(
														currentEntry
																.clone()
																.attr(
																		'id',
																		'myArea'
																				+ newNum)
																.addClass(
																		'clonedArea'))
														.appendTo(controlForm);
												var mainDiv = document
														.getElementById('myArea'
																+ newNum), childDiv = mainDiv
														.getElementsByTagName('div')[0];
												// start of setting id on
												// category fields
												var categoryDiv = childDiv
														.getElementsByTagName('div')[0], categoryInput = categoryDiv
														.getElementsByTagName('select')[0];
												categoryInput.setAttribute(
														'id', 'category'
																+ newNum);
											

												// start of setting id on
												// description and itemName
												var itemNameDiv = childDiv
														.getElementsByTagName('div')[1], descriptionInput = itemNameDiv
														.getElementsByTagName('input')[0], itemNameInput = itemNameDiv
														.getElementsByTagName('select')[0];
												descriptionInput.setAttribute(
														'id', 'description'
																+ newNum);
												itemNameInput.setAttribute(
														'id', 'itemName'
																+ newNum);
												// end of setting id on
												// description and itemName

												// start of setting id on
												// itemCode
												var itemCodeDiv = childDiv
														.getElementsByTagName('div')[2], itemCodeInput = itemCodeDiv
														.getElementsByTagName('input')[1], itemIdInput = itemCodeDiv
														.getElementsByTagName('input')[0];
												itemIdInput.setAttribute('id',
														'itemId' + newNum);
												itemCodeInput.setAttribute(
														'id', 'itemCode'
																+ newNum);
												// end of setting id on itemCode

												// start of setting id on uom
												var uomDiv = childDiv
														.getElementsByTagName('div')[3], uomInput = uomDiv
														.getElementsByTagName('input')[0];
												uomInput.setAttribute('id',
														'uom' + newNum);
												// end of setting id on uom

												// start of setting id on
												// receivedQty fields
												var receivedQtyDiv = childDiv
														.getElementsByTagName('div')[4], receivedQtyInput = receivedQtyDiv
														.getElementsByTagName('input')[0];
												receivedQtyInput.setAttribute(
														'id', 'receivedQty'
																+ newNum);
												// end of setting id on
												// receivedQty fields

												// start of setting id on
												// unitCost
												var unitCostDiv = childDiv
														.getElementsByTagName('div')[5], unitCostInput = unitCostDiv
														.getElementsByTagName('input')[0];
												unitCostInput.setAttribute(
														'id', 'unitCost'
																+ newNum);
												// end of setting id on unitCost

												// start of setting id on
												// totalCost
												var costSource = childDiv
														.getElementsByTagName('div')[6], costSourceSelect = costSource
														.getElementsByTagName('select')[0];
												costSourceSelect.setAttribute(
														'id', 'costSource'
																+ newNum);
												// totalCost
												var totalCostDiv = childDiv
														.getElementsByTagName('div')[7], totalCostInput = totalCostDiv
														.getElementsByTagName('input')[0];
												totalCostInput.setAttribute(
														'id', 'totalCost'
																+ newNum);
												// end of setting id on
												// totalCost

												// start of setting id on
												// remarks
												/*
												 * var remarksDiv =
												 * childDiv.getElementsByTagName('div')[7],
												 * remarksInput = remarksDiv
												 * .getElementsByTagName('input')[0];
												 */// remarksInput.setAttribute('id',
												// 'remarks' +
												// newNum);
												// end of setting id on remarks
												// newEntry.find('input').val(
												// result[i]);
												newEntry
														.find('.receivedQty')
														.val(result[i].qunatity);
												newEntry.find('.itemId').val(
														result[i].itemCode);
												newEntry.find('.itemCode').val(
														result[i].itemCode);
												newEntry.find('.uom').val(
														result[i].unit);
												newEntry.find('.unitCost').val(
														result[i].unitCost);
												newEntry
														.find('.totalCost')
														.val(
																result[i].totalCost.toFixed(2));
												newEntry
														.find('.category')
														.val(
																$(
																		'#category'
																				+ newNum
																				+ ' option:first')
																		.val());

												$(
														"#itemName"
																+ newNum
																+ " option:first")
														.remove();
												$('#itemName' + newNum).append(
																"<option value='' >"
																		+ result[i].itemName
																		+ "</option>");
												var text = result[i].procurementPackageMst.sourceOfFund;
												//make costsource selected based on APP
												$("#costSource"+ newNum+ " option").filter(function() {
												    return this.text == text; 
												}).attr('selected', true);
												
												
												//console.log($("#costSource"+ newNum+ " option:contains("+ result[i].procurementPackageMst.sourceOfFund+ ")"));
												//$("#costSource"+ newNum+ " option:contains("+ result[i].procurementPackageMst.sourceOfFund+ ")");
												//$('#costSource'+ newNum).find('option[text="'+result[i].procurementPackageMst.sourceOfFund+'"]').attr("selected", true);
											}
											//console.log("grand total :"+grandTotal);
											//console.log(document.getElementById("gTotal"));
											//document.getElementById("gTotal").innerHTML = grandTotal;											
											//jQuery($('.grandTotal')).html(""+grandTotal);
											$('#grandTotal').text(numberWithCommas(grandTotal.toFixed(2)));
										} else {
											console.log("comes to else");
											$('#grandTotal').text(0.0);
											$('.entry').not(':first').remove();
											$('.entry').find('.receivedQty').val("0.0");
											$('.entry').find('.itemId').val();
											$('.entry').find('.itemCode').val("");
											$('.entry').find('.itemName  option:first').text("Select Item");
											$('.entry').find('.uom').val(0.0);
											$('.entry').find('.unitCost').val(0.0);
											$('.entry').find('.totalCost').val(0.0);										}
										
									},
									error : function(data) {
										alert("Server Error");
									},
									type : 'POST'
								});

					}

					function checkAppForBudget() {
						var returnVal = false;
						var contextPath = $('#contextPath').val();
						var id = $('#descoSessionForBudget').val();
						var obj = {
							id : id
						}
						var str = JSON.stringify(obj);
						var path = contextPath
								+ '/budget/budgetMst/app/session/findOne.do';
						if (id == "") {
							return;
						}

						return $.ajax({
							url : path,
							data : str,
							contentType : "application/json",
							success : function(data) {
								var result = JSON.parse(data);

							},
							error : function(data) {
								alert("Server Error");
							},
							type : 'POST'
						});

					}
				});
