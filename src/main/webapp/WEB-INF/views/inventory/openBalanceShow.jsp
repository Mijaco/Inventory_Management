<%@include file="../inventory/inventoryheader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<h4>
			<a href="#" style="text-decoration: none;">Open Balance</a> / Show
		</h4>
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/inventory/listOpeningBalance.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Opening Balance List
			</a> <a
				href="${pageContext.request.contextPath}/inventory/editOpeningBalance.do?id=${selectOpeningBalance.id}"
				style="text-decoration: none;" class="btn btn-info btn-sm"> <span
				class="ace-icon fa fa-pencil bigger-130" aria-hidden="true"></span>
				Edit
			</a>
		</div>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px; margin-left: 90px; margin-right: 90px;">
		<!-- --------------------- -->

		<div class="oe_title">
			<form method="POST" class=""
				action="${pageContext.request.contextPath}/inventory/openBalanceUpdate.do">
				<div class="col-md-12 col-md-offset-2">
					<div class="col-md-6">
						<div class="form-group">
							<label for="openingDate" class="col-sm-4 control-label">Open
								Date</label>
							<div class="col-md-8">
								<input type="datetime" class="form-control" readonly="true"
									value='<fmt:formatDate
									value="${selectOpeningBalance.openingDate}" pattern="dd-MM-yyyy" />'
									style="border: 0; border-bottom: 2px ridge;" path="openingDate"
									name="openingDate" />
							</div>
						</div>

						<div class="form-group">
							<label for="itemName" class="col-sm-4 control-label">Item
								Name</label>
							<div class="col-md-8">
								<input type="text" class="form-control"
									value="${selectOpeningBalance.itemName}"
									style="border: 0; border-bottom: 2px ridge;" id="itemName"
									name="itemName" />
							</div>
						</div>

						<div class="form-group">
							<label for="balanceQuantity" class="col-sm-4 control-label">Balance
								Qty</label>
							<div class="col-md-8">
								<input type="text" class="form-control"
									value="${selectOpeningBalance.balanceQuantity}"
									style="border: 0; border-bottom: 2px ridge;"
									id="balanceQuantity" name="balanceQuantity" />
							</div>
						</div>

						<div class="form-group">
							<label for="rate" class="col-sm-4 control-label">Rate</label>
							<div class="col-md-8">
								<input type="text" class="form-control"
									value="${selectOpeningBalance.rate}"
									style="border: 0; border-bottom: 2px ridge;" id="rate"
									name="rate" />
							</div>
						</div>

						<div class="form-group">
							<label for="amount" class="col-sm-4 control-label">Total
								Amount</label>
							<div class="col-md-8">
								<input type="text" class="form-control"
									value="${selectOpeningBalance.amount}"
									style="border: 0; border-bottom: 2px ridge;" id="amount"
									name="amount" />
							</div>
						</div>
						<input type="text" value="${selectOpeningBalance.id}"
							hidden="true" name="selectOpeningBalanceId" />

					</div>

				</div>
			</form>
		</div>

		<!-- --------------------- -->
	</div>
</div>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>