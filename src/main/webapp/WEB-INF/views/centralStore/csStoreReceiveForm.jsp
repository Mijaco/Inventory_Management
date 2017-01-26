<%@include file="../common/csHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->



<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<h4>
			<a href="#" style="text-decoration: none;">Central Store Receive</a>
			/ New
		</h4>
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="/${pageContext.request.contextPath}/cs/store/listCsReceive.do" style="text-decoration: none;"
				class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Central Store
				Receive List
			</a>
			<button accesskey="D" class="btn btn-info btn-sm" type="button">
				Discard</button>
		</div>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<!-- --------------------- -->
		<form method="POST" action="${pageContext.request.contextPath}/cs/store/saveCentralStoreReceive.do">
			<div class="oe_title">

				<div class="col-md-6 col-sm-6">
					<div class="form-group">
						<label for="rrNo" class="col-sm-4 control-label"> R.R. no</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="rrNo" value="${rrNo}"
								placeholder="Desco/RRno/001" name = "rrNo"
								style="border: 0; border-bottom: 2px ridge;"/>

						</div>
					</div>
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="storeSection" class="col-sm-4 control-label">Store
							Section</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="storeSection"
								placeholder="section" style="border: 0; border-bottom: 2px ridge;"
								name="storeSection" />
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="supplier" class="col-sm-4 control-label">Suppler</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="supplier"
								placeholder="supplier name" style="border: 0; border-bottom: 2px ridge;"
								name="supplier">
						</div>
					</div>

					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="workOrder" class="col-sm-4 control-label">Work order/contract no</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="workOrder"
								placeholder="work order"
								style="border: 0; border-bottom: 2px ridge;" name="workOrder">
						</div>
					</div>

					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="woNo" class="col-sm-4 control-label">Work
							Order No/Contract Date</label>
						<div class="col-sm-8">
							<input type="date" class="form-control" id="workDate"
								placeholder="yyyy-dd-MM"
								style="border: 0; border-bottom: 2px ridge;" name="workDate">
						</div>
					</div>

				</div>

				<div class="col-md-6 col-sm-6">

					<div class="form-group">
						<label for="dateOfDelivery" class="col-sm-4 col-md-4 control-label">Date of delivery</label>
						<div class="col-sm-8 col-md-8">
							<input type="date" class="form-control" id="dateOfDelivery"
								style="border: 0; border-bottom: 2px ridge;" name="dateOfDelivery" />
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="challanNo" class="col-sm-4 col-md-4 control-label">Invoice / Challan No.</label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control" id="challanNo"
								style="border: 0; border-bottom: 2px ridge;" name="challanNo"
								placeholder="DESCO/2015-2016/Ch001" />
						</div>
					</div>
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="returnFor" class="col-sm-4 col-md-4 control-label">Invoice Date</label>
						<div class="col-sm-8 col-md-8">
							<input type="date" class="form-control" id="invoiceDate"
								style="border: 0; border-bottom: 2px ridge;"
								name="invoiceDate" />
						</div>
					</div>
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="billOfLanding" class="col-sm-4 col-md-4 control-label">Bill of landing</label>
						<div class="col-sm-8 col-md-8">
							<input type="date" class="form-control" id="billOfLanding"
								style="border: 0; border-bottom: 2px ridge;" name="billOfLanding" />
						</div>
					</div>

					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="landingDate" class="col-sm-4 col-md-4 control-label">Landing Date</label>
						<div class="col-sm-8 col-md-8">
							<input type="date" class="form-control" id="landingDate"
								style="border: 0; border-bottom: 2px ridge;" name="landingDate" />
						</div>
					</div>

				</div>
				<div class="col-md-12 col-sm-12">
					<div class="form-group" style="margin-top: 1em;">
						<label for="fax" class="col-sm-4 control-label"
							style="font-style: italic; font-weight: bold;">Requisition
							Item(s):</label>
						<div class="col-md-12 col-sm-12 col-xs-12">
							<hr />
							<div class="col-xs-12">
								<div class="row">
									<div class="control-group" id="fields">
										<div class="controls">
											<div class="aaa">
												<!-- <form role="form" autocomplete="off">  -->
												<div class="col-xs-12 entry" id="myArea0">
													<div class="row">
														<div class="form-group col-sm-1 col-xs-12">
															<input class="form-control" name="slNo"
																type="text" placeholder="Sl. No." readonly="readonly"
																style="border: 0; border-bottom: 2px ridge;" />
														</div>
														<div class="form-group col-sm-1 col-xs-12">
															
															<!-- <select class="form-control" name="itemName"  onchange="itemLeaveChange()"
																id="itemName" style="border: 0; border-bottom: 2px ridge;"> -->
															<select class="form-control" name="partNo"
																onchange="itemLeaveChange(this)"
																style="border: 0; border-bottom: 2px ridge;">
																<option disabled selected>Code</option>
																 <%-- <c:if test="${!empty invItemList}">
																	<c:forEach items="${invItemList}" var="invItem">
																		<option value="${invItem.inventoryItemId}">
																			<c:out value="${invItem.inventoryItemName}" /></option>
																	</c:forEach>
																</c:if> --%>
															</select>
														</div>
														<div class="form-group col-sm-2 col-xs-12">
															<input class="form-control uom" name="description"
																type="text" placeholder="Description of Materials"
																style="border: 0; border-bottom: 2px ridge;" />
														</div>
														<div class="form-group col-sm-1 col-xs-12">
															<input class="form-control" name="unit" type="text"
																placeholder="Unit"
																style="border: 0; border-bottom: 2px ridge;" />
														</div>
														<div class="form-group col-sm-1 col-xs-12">
															<input class="form-control" name="quantity"
																type="text" placeholder="Quantity Required"
																style="border: 0; border-bottom: 2px ridge;" />
														</div>
														<div class="form-group col-sm-1 col-xs-12">
															<input class="form-control" name="cost" type="text"
																placeholder="Unit Cost"
																style="border: 0; border-bottom: 2px ridge;" />
														</div>
														<div class="form-group col-sm-1 col-xs-12">
															<input class="form-control" name="number" type="text"
																placeholder="Total Cost"
																style="border: 0; border-bottom: 2px ridge;" />
														</div>
														<div class="form-group col-sm-1 col-xs-12">
															<input class="form-control" name="remarks" type="text"
																placeholder="Remarks"
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
	function itemLeaveChange(element) {

		var temp = $(element).closest("div").parent().parent().attr("id");
		var sequence = temp.substr(temp.length - 1)
		//$(element).closest("div").parent().parent().find('.itemCode').val(new Date().getSeconds());
		//$(element).closest("div").parent().parent().find('.uom').val(new Date().getSeconds());

		$(element).closest("div").parent().parent().find('.itemName').attr(
				'id', sequence);
		var e = document.getElementById('' + sequence);
		var item_id = e.options[e.selectedIndex].value;
		$
				.ajax({
					url : '${pageContext.request.contextPath}/procurement/requisition/viewInventoryItem.do',
					data : "{inventoryItemId:" + item_id + "}",
					contentType : "application/json",
					success : function(data) {
						var inventoryItem = JSON.parse(data);
						$(element).closest("div").parent().parent().find(
								'.itemCode').val(
								inventoryItem.inventoryItemItemCode);
						$(element).closest("div").parent().parent()
								.find('.uom').val(
										inventoryItem.inventoryItemUint);
						$(element).closest("div").parent().parent().find(
								'.itemNameHideField').val(
								inventoryItem.inventoryItemName);
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