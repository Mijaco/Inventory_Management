<%@include file="../common/csHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->



<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<h4>
			<a href="#" style="text-decoration: none;">Store Ledger</a>
			/ New
		</h4>
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="#" style="text-decoration: none;"
				class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Store
				Ledger Book List
			</a>
			<button accesskey="D" class="btn btn-info btn-sm" type="button">
				Discard</button>
		</div>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px; margin-left: 90px; margin-right: 90px;">
		<!-- --------------------- -->
		<form method="POST" action="#">
			<div class="oe_title">



				<div class="col-md-6 col-sm-6">
					<div class="form-group">
						<label for="gpNo" class="col-sm-4 control-label">Description</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="gpNo"
								placeholder="1234" value="${gpNo}"
								style="border: 0; border-bottom: 2px ridge;" name="gpNo" />

						</div>
					</div>
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="ticketNo" class="col-sm-4 control-label">Model</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="ticketNo"
								placeholder="1234" style="border: 0; border-bottom: 2px ridge;"
								name="ticketNo" />
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="descoFormNo" class="col-sm-4 control-label">Section</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="descoFormNo"
								placeholder="1234" style="border: 0; border-bottom: 2px ridge;"
								name="descoFormNo">
						</div>
					</div>

					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="returnedBy" class="col-sm-4 control-label">Accounting Unit</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="returnedBy"
								placeholder="Mr. xxx"
								style="border: 0; border-bottom: 2px ridge;" name="returnedBy">
						</div>
					</div>

				</div>

				<div class="col-md-6 col-sm-6">
				
				<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="woNo" class="col-sm-4 control-label">Part no</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="woNo"
								placeholder="WO-001"
								style="border: 0; border-bottom: 2px ridge;" name="woNo">
						</div>
					</div>
				

					<div class="form-group">
						<label for="gpDate" class="col-sm-4 col-md-4 control-label">Location</label>
						<div class="col-sm-8 col-md-8">
							<input type="date" class="form-control" id="gpDate"
								style="border: 0; border-bottom: 2px ridge;" name="gpDate" />
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="returnSlipNo" class="col-sm-4 col-md-4 control-label">Maximum</label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control" id="returnSlipNo"
								style="border: 0; border-bottom: 2px ridge;" name="returnSlipNo"
								placeholder="DESCO/2015-2016/PRF001" />
						</div>
					</div>
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="returnFor" class="col-sm-4 col-md-4 control-label">Minimum</label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control" id="returnFor"
								value="Mr. xxxx" style="border: 0; border-bottom: 2px ridge;"
								name="returnFor" />
						</div>
					</div>
					
				</div>
				<div class="col-md-12 col-sm-12">
					<div class="form-group" style="margin-top: 1em;">
						<label for="fax" class="col-sm-4 control-label"
							style="font-style: italic; font-weight: bold;">Requested
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
														

														<div class="form-group col-sm-1 col-xs-3">

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