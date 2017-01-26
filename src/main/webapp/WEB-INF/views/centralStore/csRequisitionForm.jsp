<%@include file="../common/csHeader.jsp"%>
<!-- -------------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #5F9AAC">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<h4>
			<a href="#" style="text-decoration: none;">Central Store</a> /
			Requisition Form
		</h4>
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/cs/store/listCsRequisition.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Central Store Requisition List
			</a>
			<button accesskey="D" class="btn btn-info btn-sm" type="button">
				Discard</button>
		</div>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px; margin-left: 90px; margin-right: 90px;">
		<!-- --------------------- -->
		<form method="POST"
			action="${pageContext.request.contextPath}/cs/store/saveCentralStoreRequisition.do">
			<div class="oe_title">



				<div class="col-md-6 col-sm-6">
					<div class="form-group">
						<label for="requisitionNo" class="col-sm-4 control-label">Requisition
							No.</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="requisitionNo"
								placeholder="${requisitionNo}"
								value="${requisitionNo}"
								style="border: 0; border-bottom: 2px ridge;"
								name="requisitionNo" />

						</div>
					</div>

					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="identerName" class="col-sm-4 control-label">Identer
							Name</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="identerName"
								placeholder="Name of Identer"
								style="border: 0; border-bottom: 2px ridge;" name="identerName">
						</div>
					</div>
				</div>

				<div class="col-md-6 col-sm-6">

					<div class="form-group">
						<label for="requisitionDate"
							class="col-sm-4 col-md-4 control-label">Requisition Date</label>
						<div class="col-sm-8 col-md-8">
							<input type="date" class="form-control" id="requisitionDate"
								 style="border: 0; border-bottom: 2px ridge;"
								name="requisitionDate" />
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
															<select class="form-control" name="itemCode"
																onchange="itemLeaveChange(this)"
																style="border: 0; border-bottom: 2px ridge;">
																<option disabled selected>Code</option>
																 <c:if test="${!empty invItemList}">
																	<c:forEach items="${invItemList}" var="invItem">
																		<option value="${invItem.inventoryItemId}">
																			<c:out value="${invItem.inventoryItemName}" /></option>
																	</c:forEach>
																</c:if>
															</select>
														</div>
														<div class="form-group col-sm-2 col-xs-12">
															<input class="form-control uom" name="descriptionMaterials"
																type="text" placeholder="Description of Materials"
																style="border: 0; border-bottom: 2px ridge;" />
														</div>
														<div class="form-group col-sm-1 col-xs-12">
															<input class="form-control" name="noSize" type="text"
																placeholder="No or Size"
																style="border: 0; border-bottom: 2px ridge;" />
														</div>
														<div class="form-group col-sm-1 col-xs-12">
															<input class="form-control" name="unit" type="text"
																placeholder="Unit"
																style="border: 0; border-bottom: 2px ridge;" />
														</div>
														<div class="form-group col-sm-1 col-xs-12">
															<input class="form-control" name="quantityRequired"
																type="text" placeholder="Quantity Required"
																style="border: 0; border-bottom: 2px ridge;" />
														</div>
														<div class="form-group col-sm-1 col-xs-12">
															<input class="form-control" name="quantityIssued" type="text"
																placeholder="Quantity Issued"
																style="border: 0; border-bottom: 2px ridge;" />
														</div>
														<div class="form-group col-sm-1 col-xs-12">
															<input class="form-control" name="unitCost" type="text"
																placeholder="Unit Cost"
																style="border: 0; border-bottom: 2px ridge;" />
														</div>
														<div class="form-group col-sm-1 col-xs-12">
															<input class="form-control" name="totalCost" type="text"
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
				<div class="col-md-12">&nbsp;&nbsp;</div>
				<div class="col-md-12">&nbsp;&nbsp;</div>
				<div class="col-md-6 col-sm-6">
					<div class="form-group">
						<label for="gatePassNo" class="col-sm-4 control-label">Gate
							Pass No.</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="gatePassNo"
								placeholder="${gatePassNo}" value="${prfNo}"
								style="border: 0; border-bottom: 2px ridge;"
								name="getPassNo" />

						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
				</div>

				<div class="col-md-6 col-sm-6">

					<div class="form-group">
						<label for="gatePassDate" class="col-sm-4 col-md-4 control-label">Gate
							Pass Date</label>
						<div class="col-sm-8 col-md-8">
							<input type="date" class="form-control" id="gatePassDate"
								style="border: 0; border-bottom: 2px ridge;"
								name="getPassDate" />
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
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

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>