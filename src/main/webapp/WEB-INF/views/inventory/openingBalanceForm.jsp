<%@include file="../inventory/inventoryheader.jsp"%>
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/inventory/listOpeningBalance.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Opening Balance List
			</a>
		</div>
		<h1 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
				New Opening Balance</h1>
	</div>
	<div class="container">
		<div class="col-sm-8 col-sm-offset-2"
			style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">

			<!-- --------------------- -->
			<form role="form" method="POST"
			action="${pageContext.request.contextPath}/inventory/saveOpeningBalance.do" id="openingBalanceForm">

				<div class="col-sm-12 table-responsive">
					<table class="table table-hover table-bordered">
						<tbody>
							<tr>
								<td class="col-sm-3 success" align="right"
									style="font-weight: bold;">Open Date:</td>
								<td class="col-sm-7">
									<input type="text" class="form-control" style="border: 0; border-bottom: 2px ridge;"
								readonly="readonly" name="openingDate" id="openingDate">
								</td>
							</tr>
							
							<tr>
								<td class="col-sm-3 success" align="right"
									style="font-weight: bold;">Item Name:</td>
								<td class="col-sm-7">
									<input type="text" class="form-control" style="border: 0; border-bottom: 2px ridge;" id="itemName"
										name="itemName">
								</td>
							</tr>
							
							<tr>
								<td class="col-sm-3 success" align="right"
									style="font-weight: bold;">Balance Qty:</td>
								<td class="col-sm-7">
									<input type="text" class="form-control" style="border: 0; border-bottom: 2px ridge;"
										id="balanceQuantity" name="balanceQuantity">
								</td>
							</tr>
							
							<tr>
								<td class="col-sm-3 success" align="right"
									style="font-weight: bold;">Rate:</td>
								<td class="col-sm-7">
									<input type="text" class="form-control" style="border: 0; border-bottom: 2px ridge;" id="rate"
										name="rate" onblur="getTotalAmount()">
								</td>
							</tr>
							
							<tr>
								<td class="col-sm-3 success" align="right"
									style="font-weight: bold;">Total Amount:</td>
								<td class="col-sm-7">
									<input type="text" class="form-control" style="border: 0; border-bottom: 2px ridge;" id="amount"
										name="amount">
								</td>
							</tr>
						</tbody>
					</table>
					<div class="col-md-12" style="padding-top: 15px;">
						<div class="col-xs-12 col-sm-6">
							<button type="submit" id="saveButton"
								style="margin-right: 10px; border-radius: 6px;"
								class="pull-right btn btn-lg btn-success">
								<i class="ace-icon fa fa-save"></i> <span class="bigger-50">Save</span>
							</button>
						</div>

						<div class="col-xs-12 col-sm-6">
							<button type="reset" class="pull-left btn btn-lg btn-danger"
								style="margin-left: 10px; border-radius: 6px;">
								<i class="ace-icon fa fa-refresh"></i> <span class="bigger-50">Reset</span>
							</button>
						</div>
					</div>
				</div>
			</form>
			<!-- --------------------- -->
		</div>
	</div>
</div>

<script>

	var today = new Date();
	var dd = today.getDate();
	var mm = today.getMonth() + 1; //January is 0!
	var yyyy = today.getFullYear();

	if (dd < 10) {
		dd = '0' + dd
	}
	if (mm < 10) {
		mm = '0' + mm
	}
	//var today = dd+'/'+mm+'/'+yyyy;
	var today = yyyy + '-' + mm + '-' + dd;

	$(openingDate).val(today);

	function getItemNames() {
		var itemNamesArr = [];
		$
				.ajax({
					url : '${pageContext.request.contextPath}/inventory/getItemName.do',
					contentType : "application/json",
					success : function(data) {
						for (var i = 0; i < data.length; i++) {
							itemNamesArr[i] = data[i].itemName
						}
					},
					error : function(data) {
						alert("Server Error");
					},
					type : 'POST'
				});
		return itemNamesArr;
	}

	function getTotalAmount() {
		var rate = document.getElementById("rate").value
		var balanceQuantity = document.getElementById("balanceQuantity").value;
		var totalAmount = rate * balanceQuantity;
		document.getElementById('amount').value = totalAmount;
	}
</script>

<script>
	$(document).ready(function() {
		var availableTags = getItemNames();
		$("#itemName").autocomplete({
			source : availableTags
		});
	});
</script>

<script>
	// When the browser is ready...
	$(function() {

		// Setup form validation on the #register-form element
		$("#openingBalanceForm").validate({

			// Specify the validation rules
			rules : {
				openingDate : "required",
				itemName : "required",
				balanceQuantity : "required",
				rate : "required",
				amount : "required",

			},

			// Specify the validation error messages
			messages : {
				openingDate : "Please enter opening date",
				itemName : "Please enter Item Name",
				balanceQuantity : "Please enter balance quantity",
				rate : "Please Enter Rate of the item",
				amount : "Please enter amount",
			},

			submitHandler : function(form) {
				form.submit();
			}
		});

	});
</script>


<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>