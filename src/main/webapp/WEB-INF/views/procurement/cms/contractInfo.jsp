<%@include file="../../common/procurementHeader.jsp"%>
<link
	href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
	rel="stylesheet">

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/cms/contractManagementList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Contract List
			</a>
		</div>

		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Contract
			Management System (CMS)</h1>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<!-- --------------------- -->
		<form method="POST"
			action="${pageContext.request.contextPath}/cms/saveContractInfo.do">



			<input type="hidden" name="contextPath" id="contextPath"
				value="${pageContext.request.contextPath}">


			<!-- start for submit  -->
			<div class="oe_title">
				<div class="col-md-6 col-sm-6">

					<div class="form-group">
						<label for="contractNo" class="col-sm-4 control-label">Contract
							No : </label>
						<div class="col-sm-8">
							<select name="id" class="form-control" id="contractNo"
								required="required" style="border: 0; border-bottom: 2px ridge;"
								onchange="loadContractDetails()">
								<option value="">Select Work Order No</option>
								<c:forEach items="${appPurchaseMstList}" var="appPurchaseMst">
									<option value="${appPurchaseMst.id}">${appPurchaseMst.workOrderNo}</option>
								</c:forEach>
							</select>
						</div>

					</div>

					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">Contractor Name :</label>
						<div class="col-sm-8">
							<div class="ui-widget">
								<input type="text" class="form-control" id="contractorName"
									style="border: 0; border-bottom: 2px ridge;"
									readonly="readonly" name="contractorName" />
							</div>
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>

					<div class="form-group">
						<label class="col-sm-4 control-label">Contract Duration
							(Month) :</label>
						<div class="col-sm-8">
							<div class="ui-widget">
								<input type="number" class="form-control" id="contractDuration"
									min="1" maxlength="120"
									style="border: 0; border-bottom: 2px ridge;" value="1"
									required="required" onblur="setExpiredDate()" name="contractValidityMonth" />
							</div>
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">

						<label for="address" class="col-sm-4 control-label">Address
							:</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="address"
								placeholder="" style="border: 0; border-bottom: 2px ridge;"
								name="contractorAddress">
						</div>
					</div>
				</div>

				<div class="col-md-6 col-sm-6">

					<div class="form-group">
						<label for="tenderNo" class="col-sm-4 control-label align-right">Tender
							No : </label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="tenderNo"
								readonly="readonly" style="border: 0; border-bottom: 2px ridge;"
								name="tenderNo" />
						</div>
					</div>

					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>

					<div class="form-group">
						<label for="contractDate"
							class="col-sm-4 control-label align-right">Contract Date
							:</label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control" id="contractDate"
								value="" readonly="readonly"
								style="border: 0; border-bottom: 2px ridge;" name="contractDate" />
						</div>
					</div>
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>

					<div class="form-group">
						<label for="expiryDate"
							class="col-sm-4 col-md-4 control-label align-right">Expire
							Date :</label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control" id="expiryDate" value=""
								readonly="readonly" style="border: 0; border-bottom: 2px ridge;"
								name="contractExpiredDate" />
						</div>
					</div>
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>

					<div class="form-group">
						<label for="deptId" class="col-sm-4 control-label align-right">PG
							Amount :</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="pgAmount" value=""
								readonly="readonly" style="border: 0; border-bottom: 2px ridge;"
								name="pgAmount" />
						</div>
					</div>
				</div>



				<div class="col-md-12" style="padding-top: 15px;">
					<div class="col-xs-12 col-sm-6">
						<button type="submit" id="saveButton"
							style="margin-right: 10px; border-radius: 6px;"
							class="width-20 pull-right btn btn-md btn-success">
							<i class="ace-icon fa fa-save"></i> <span class="bigger-50">Save</span>
						</button>
					</div>

					<div class="col-xs-12 col-sm-6">
						<button type="reset"
							class="width-20  pull-left btn btn-md btn-danger"
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

<script type="text/javascript">
	function loadContractDetails() {
		var id = $('#contractNo').val();
		var contextPath = $('#contextPath').val();
		var path = contextPath + '/cms/loadContractDetails.do';

		var cData = {
			id : id
		};
		var cDataJsonString = JSON.stringify(cData);
		$.ajax({
			url : path,
			data : cDataJsonString,
			contentType : "application/json",
			success : function(data) {
				var purchaseMst = JSON.parse(data);
				$('#contractorName').val(purchaseMst.contractorName);
				$('#tenderNo').val(purchaseMst.tenderNo);
				var cDate = new Date(purchaseMst.workOrderDate);

				var formatted = $.datepicker.formatDate("dd-mm-yy", cDate);
				$('#contractDate').val(formatted);
				$('#pgAmount').val(purchaseMst.pgAmount);
			},
			error : function(data) {
				alert("Server Error");
			},
			type : 'POST'
		});

	}
	function setExpiredDate() {
		var contractDate = $('#contractDate').val();
		var contractDuration = $('#contractDuration').val() == '' ? 0 : $(
				'#contractDuration').val();
		var month = parseInt(contractDuration);

		var from = contractDate.split("-");
		var cDate = new Date(from[2], from[1] - 1, from[0]);
		cDate.setMonth(cDate.getMonth() + month);

		var formatted = $.datepicker.formatDate("dd-mm-yy", cDate);
		$('#expiryDate').val(formatted);

	}
</script>
<%@include file="../../common/ibcsFooter.jsp"%>