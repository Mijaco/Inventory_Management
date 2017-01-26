<%@include file="../common/csHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->



<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<!-- <h4>
			<a href="#" style="text-decoration: none;">Central Store Receive</a>
			/ New
		</h4> -->
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/cs/itemRecieved/list.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Central Store Receive List
			</a>
			<!-- <button accesskey="D" class="btn btn-info btn-sm" type="button">
				Discard</button> -->
		</div>
		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Receiving Report Form</h1>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<!-- --------------------- -->
		<form method="POST"
			action="${pageContext.request.contextPath}/cs/itemRecieved/itemReceivedSave.do">
			<div class="oe_title">

				<div class="col-md-6 col-sm-6">
					<div class="form-group">
						<label for="contractNo" class="col-sm-4 control-label">WO/Contract
							No</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="contractNo"
								placeholder="1234" style="border: 0; border-bottom: 2px ridge;"
								name="contractNo" />
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="chalanNo" class="col-sm-4 control-label">Invoice/Chalan
							No</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="chalanNo"
								placeholder="1234" style="border: 0; border-bottom: 2px ridge;"
								name="chalanNo">
						</div>
					</div>

					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="supplierName" class="col-sm-4 col-md-4 control-label">Supplier
							Name</label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control" id="supplierName"
								style="border: 0; border-bottom: 2px ridge;" name="supplierName" />
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="billOfLanding;" class="col-sm-4 control-label">Bill
							of Landing </label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="billOfLanding;"
								style="border: 0; border-bottom: 2px ridge;"
								name="billOfLanding">
						</div>
					</div>

				</div>

				<div class="col-md-6 col-sm-6">

					<div class="form-group">
						<label for="contractDate" class="col-sm-4 col-md-4 control-label">Contract
							Date</label>
						<div class="col-sm-8 col-md-8">
							<!-- <input type="date" class="form-control" id="contractDate"
								style="border: 0; border-bottom: 2px ridge;" name="contractDate" /> -->
							<input type="text" class="form-control datepicker-13"
								id="contractDate" style="border: 0; border-bottom: 2px ridge;"
								name="contractDate" />
						</div>
					</div>

					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="returnFor" class="col-sm-4 col-md-4 control-label">Invoice/Chalan
							Date</label>
						<div class="col-sm-8 col-md-8">
							<!-- <input type="date" class="form-control" id="invoiceDate"
								style="border: 0; border-bottom: 2px ridge;" name="invoiceDate" /> -->
							<input type="text" class="form-control datepicker-13"
								id="invoiceDate" style="border: 0; border-bottom: 2px ridge;"
								name="invoiceDate" />
						</div>
					</div>

					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="deliveryDate" class="col-sm-4 col-md-4 control-label">Delivery
							Date</label>
						<div class="col-sm-8 col-md-8">
							<!-- <input type="date" class="form-control" id="delivaryDate"
								style="border: 0; border-bottom: 2px ridge;" name="deliveryDate" /> -->
							<input type="text" class="form-control datepicker-13"
								id="delivaryDate" style="border: 0; border-bottom: 2px ridge;"
								name="deliveryDate" />
						</div>
					</div>
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="landingDate" class="col-sm-4 control-label">Landing
							Date</label>
						<div class="col-sm-8">
							<!-- <input type="date" class="form-control" id="landingDate"
								placeholder="yyyy-dd-MM"
								style="border: 0; border-bottom: 2px ridge;" name="landingDate"> -->
							<input type="text" class="form-control datepicker-13"
								id="landingDate" style="border: 0; border-bottom: 2px ridge;"
								name="landingDate">
						</div>
					</div>

					<div>
						<input type="hidden" class="form-control" id="createdBy"
							value='<sec:authentication property="principal.username" />'
							name="createdBy" /> <input type="hidden" class="form-control"
							id="roleName"
							value='<sec:authentication property="principal.Authorities[0]" />'
							name="roleName" />
					</div>

				</div>
				<!-- <div class="col-md-12 col-sm-12"> style="overflow-x: scroll; width: 60em;white-space: nowrap;" -->
				<div class="col-md-12 col-sm-12">
					<div class="form-group" style="margin-top: 1em;">
						<label for="fax" class="col-sm-4 control-label"
							style="font-style: italic; font-weight: bold;">Requisition
							Item(s):</label>

						<div class="col-xs-12 table-responsive">
							<div class="table">
								<div style="width: 2500px;">
									<hr />

									<div class="col-xs-12">
										<div class="form-group col-sm-1 col-xs-12">
											<b>Category</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Item Name</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Item Code</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Unit</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Ex. Qty</b>
										</div>

										<div class="form-group col-sm-1 col-xs-12">
											<b>Rcv. Qty</b>
										</div>


										<div class="form-group col-sm-1 col-xs-12">
											<b>Rem. Qty</b>
										</div>

										<div class="form-group col-sm-1 col-xs-12">
											<b>Unit Cost</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Book No</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Page No</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Remarks</b>
										</div>
									</div>

									<div class="col-xs-12">
										<div class="row">
											<div class="control-group" id="fields">
												<div class="controls">
													<div class="aaa">
														<!-- <form role="form" autocomplete="off">  -->
														<div class="col-xs-12 entry" id="myArea0">
															<div class="row">

																<div class="form-group col-sm-1 col-xs-12">

																	<!-- <select class="form-control" name="itemName"  onchange="itemLeaveChange()"
																id="itemName" style="border: 0; border-bottom: 2px ridge;"> -->
																	<select class="form-control category"
																		onchange="categoryLeaveChange(this)"
																		style="border: 0; border-bottom: 2px ridge;">
																		<option disabled selected>Category</option>
																		<c:if test="${!empty categoryList}">
																			<c:forEach items="${categoryList}" var="category">
																				<option value="${category.categoryId}">
																					<c:out value="${category.categoryName}" /></option>
																			</c:forEach>
																		</c:if>
																	</select>
																</div>

																<div class="form-group col-sm-1 col-xs-12">

																	<!-- <select class="form-control" name="itemName"  onchange="itemLeaveChange()"
																id="itemName" style="border: 0; border-bottom: 2px ridge;"> -->
																	<input type="hidden" name="description"
																		class="description" /> <select
																		class="form-control itemName"
																		onchange="itemLeaveChange(this)"
																		style="border: 0; border-bottom: 2px ridge;">
																		<option disabled selected>Item Name</option>																		
																	</select>
																</div>
																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control itemCode" name="itemId"
																		type="text" placeholder="itemCode" readonly="readonly"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control uom" name="uom" type="text"
																		placeholder="Unit" readonly="readonly"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control expectedQty"
																		name="expectedQty" id="expectedQty0" type="number"
																		placeholder="EX. QTY"
																		onkeyup="expectQtyEqualReceive(this)"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>

																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control receivedQty"
																		onkeyup="receivedQtyToRemainQty(this)"
																		name="receivedQty" id="receivedQty0" type="number"
																		value="0" style="border: 0; border-bottom: 2px ridge;" />
																</div>

																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control remainingQty"
																		name="remainingQty" id="remainingQty0" type="number"
																		readonly="readonly" value="0"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control" name="cost" type="text"
																		placeholder="Unit Cost"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control" name="ledgerBookNo"
																		type="text" placeholder="Book No"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control" name="ledgerPageNo"
																		type="text" placeholder="Page No"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control" name="remarks" type="text"
																		placeholder="Remarks" value="No Objection"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1 col-xs-3">

																	<button class="btn btn-success btn-add" type="button">
																		<span class="glyphicon glyphicon-plus"></span>
																	</button>

																</div>
															</div>

														</div>
													</div>
													<!-- ---------------------- -->


												</div>
											</div>
										</div>
									</div>
									<hr />

								</div>
							</div>
						</div>

					</div>

				</div>

				<div class="col-md-12">
					<div class="form-group" style="margin-top: 2em;">
						<button type="reset" class="width-20 pull-left btn btn-sm">
							<i class="ace-icon fa fa-refresh"></i> <span class="bigger-50">Reset</span>
						</button>
						<button type="submit" style="margin-left: 10px;"
							class="width-20 pull-left btn btn-sm btn-success">
							<i class="ace-icon fa fa-save"></i> <span class="bigger-50">Save</span>
						</button>
					</div>

				</div>

			</div>

			<script>
				$(function() {
					$(document)
							.on(
									'click',
									'.btn-add',
									function(e) {
										//e.preventDefault();

										var num = $('.clonedArea').length;
										var newNum = num + 1;

										var controlForm = $('.controls div:first'), currentEntry = $(
												this).parents('.entry:first'), newEntry = $(
												currentEntry.clone().attr('id',
														'myArea' + newNum)
														.addClass('clonedArea'))
												.appendTo(controlForm);

										// set dynamic id on item qty fields

										var mainDiv = document
												.getElementById('myArea'
														+ newNum), childDiv = mainDiv
												.getElementsByTagName('div')[0];

										// start of seting id on expectedQty fields
										var expectedQtyDiv = childDiv
												.getElementsByTagName('div')[4], expectedQtyInput = expectedQtyDiv
												.getElementsByTagName('input')[0];
										expectedQtyInput.setAttribute('id',
												'expectedQty' + newNum);
										// end of seting id on expectedQty fields

										// start of seting id on receivedQty fields
										var receivedQtyDiv = childDiv
												.getElementsByTagName('div')[5], receivedQtyInput = receivedQtyDiv
												.getElementsByTagName('input')[0];
										receivedQtyInput.setAttribute('id',
												'receivedQty' + newNum);
										// end of seting id on receivedQty fields

										// start of seting id on remainingQty fields
										var remainingQtyDiv = childDiv
												.getElementsByTagName('div')[6], remainingQtyInput = remainingQtyDiv
												.getElementsByTagName('input')[0];
										remainingQtyInput.setAttribute('id',
												'remainingQty' + newNum);
										// end of seting id on remainingQty fields

										newEntry.find('input').val('');

										controlForm
												.find(
														'.entry:not(:last) .btn-add')
												.removeClass('btn-add')
												.addClass('btn-remove')
												.removeClass('btn-success')
												.addClass('btn-danger')
												.html(
														'<span class="glyphicon glyphicon-minus"></span>');
									}).on('click', '.btn-remove', function(e) {
								$(this).parents('.entry:first').remove();

								//e.preventDefault();
								return false;
							});

				});
			</script>
		</form>
		<!-- --------------------- -->
	</div>
</div>

<script>
	function expectQtyEqualReceive(element) {
		var id = $(element).attr('id');
		var sequence = id.substr(id.length - 1);
		$('#receivedQty' + sequence).val($('#expectedQty' + sequence).val());
	}

	function receivedQtyToRemainQty(element) {
		var id = $(element).attr('id');
		var sequence = id.substr(id.length - 1);

		var expected = parseInt($('#expectedQty' + sequence).val().trim(), 10);

		var received = parseInt($(element).val().trim(), 10);

		if (received > expected) {
			$(element).val($('#expectedQty' + sequence).val());
		}

		$('#remainingQty' + sequence).val(
				$('#expectedQty' + sequence).val()
						- $('#receivedQty' + sequence).val());

	}
</script>

<script>
	function itemLeaveChange(element) {

		var temp = $(element).closest("div").parent().parent().attr("id");
		var sequence = temp.substr(temp.length - 1);
		// $(element).closest("div").parent().parent().find('.itemCode').val(new
		// Date().getSeconds());
		// $(element).closest("div").parent().parent().find('.uom').val(new
		// Date().getSeconds());

		var e = $(element).closest("div").parent().parent().find('.itemName')
				.attr('id', sequence);
		//var e = document.getElementById('' + sequence);
		var e1 = e[0];
		var item_id = e1.options[e1.selectedIndex].value;
		$.ajax({
			//url : 'procurement/requisition/viewInventoryItem.do',
			url : 'viewInventoryItem.do',
			data : "{id:" + item_id + "}",
			contentType : "application/json",
			success : function(data) {
				var inventoryItem = JSON.parse(data);
				$(element).closest("div").parent().parent().find('.itemCode')
						.val(inventoryItem.itemId);
				$(element).closest("div").parent().parent().find('.uom').val(
						inventoryItem.unitCode);
				$(element).closest("div").parent().parent()
						.find('.description').val(inventoryItem.itemName);
			},
			error : function(data) {
				alert("Server Error");
			},
			type : 'POST'
		});
	}

	function categoryLeaveChange(element) {

		var temp = $(element).closest("div").parent().parent().attr("id");
		var sequence = temp.substr(temp.length - 1)
		// $(element).closest("div").parent().parent().find('.itemCode').val(new
		// Date().getSeconds());
		// $(element).closest("div").parent().parent().find('.uom').val(new
		// Date().getSeconds());

		$(element).closest("div").parent().parent().find('.category').attr(
				'id', sequence);
		var e = document.getElementById('' + sequence);
		var categoryId = e.options[e.selectedIndex].value;
		//alert(categoryId);
		$.ajax({
			//url : 'procurement/requisition/viewInventoryItem.do',
			url : 'viewInventoryItemCategory.do',
			data : "{categoryId:" + categoryId + "}",
			contentType : "application/json",
			success : function(data) {
				var itemList = JSON.parse(data);
				/* var items = {};					
				for(i = 0; i<itemList.length;i++){
					items[itemList[i].id] = itemList[i].itemName;
				} */

				var itemNames = $(element).closest("div").parent().parent()
						.find('.itemName');

				itemNames.empty();

				itemNames.append($("<option></option>").attr("value", '').text(
						'Select Item'));
				$.each(itemList, function(id, itemName) {
					itemNames.append($("<option></option>").attr("value",
							this.id).text(this.itemName));
				});
			},
			error : function(data) {
				alert("Server Error");
			},
			type : 'POST'
		});
	}
</script>
<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>