<%@include file="../common/csHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<h4>
			<a href="#" style="text-decoration: none;">Return Slip</a> / Edit
		</h4>
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/cs/returnSlip/list.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Returned List
			</a>
			<button accesskey="D" class="btn btn-info btn-sm" type="button">
				Discard</button>
		</div>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px; margin-left: 90px; margin-right: 90px;">
		<!-- --------------------- -->
		<form method="POST"
			action="${pageContext.request.contextPath}/cs/returnSlip/update.do">
			<div class="oe_title">

				<div class="col-md-6 col-sm-6">
					<div class="form-group">
						<label for="rsNo" class="col-sm-4 control-label"> R.S. No.</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="rsNo"
								placeholder="RS-1234" value="${returnSlipMst.rsNo}" readonly="readonly"
								style="border: 0; border-bottom: 2px ridge;" name="rsNo" />

						</div>
					</div>
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="woNo" class="col-sm-4 control-label">Work
							Order No</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="woNo"
								placeholder="wo-1234" value="${returnSlipMst.woNo}"
								style="border: 0; border-bottom: 2px ridge;" name="woNo" />
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="zone" class="col-sm-4 control-label">Zone/Area</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="zone"
								placeholder="Mirpur" value="${returnSlipMst.zone }"
								style="border: 0; border-bottom: 2px ridge;" name="zone">
						</div>
					</div>

				</div>

				<div class="col-md-6 col-sm-6">
					<div class="form-group">
						<label for="rsDate" class="col-sm-4 col-md-4 control-label">R.S.
							Date</label>
						<div class="col-sm-8 col-md-8">
							<input type="date" class="form-control" id="rsDate" value="${returnSlipMst.rsDate }"
								style="border: 0; border-bottom: 2px ridge;" name="rsDate" />
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="woDate" class="col-sm-4 col-md-4 control-label">W.O.
							Date</label>
						<div class="col-sm-8 col-md-8">
							<input type="date" class="form-control" id="woDate" value="${returnSlipMst.woDate }"
								style="border: 0; border-bottom: 2px ridge;" name="woDate" />
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="receivedFrom" class="col-sm-4 col-md-4 control-label">Received
							From.</label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control" id="receivedFrom" value="${returnSlipMst.receiveFrm }"
								style="border: 0; border-bottom: 2px ridge;" name="receiveFrm"
								placeholder="Tongi" /> <input type="hidden"
								class="form-control" id="modifiedBy"
								value='<sec:authentication property="principal.username" />'
								name="modifiedBy" />
								<input type="hidden"
								class="form-control" id="id"
								value="${returnSlipMst.id }"
								name="id" />
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
							<i class="ace-icon fa fa-save"></i> <span class="bigger-50">Update</span>
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
						/* $(element).closest("div").parent().parent().find(
								'.itemCode').val(
								inventoryItem.inventoryItemItemCode);
						$(element).closest("div").parent().parent()
								.find('.uom').val(
										inventoryItem.inventoryItemUint); */
						$(element).closest("div").parent().parent().find(
								'.itemNameHideField').val(
								inventoryItem.inventoryItemItemCode);
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