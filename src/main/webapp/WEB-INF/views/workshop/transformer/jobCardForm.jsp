<%@include file="../../common/wsContractorHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->


<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/jobcard/jobList.do" style="text-decoration: none;"
				class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Job
				Card List
			</a>
		</div>

		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
			Job Card</h1>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<!-- --------------------- -->
		<form method="post"
			action="${pageContext.request.contextPath}/jobcard/newJobCardSave.do">

			<input type="hidden" name="contextPath" id="contextPath"
				value="${pageContext.request.contextPath}">
			<!-- start for submit  -->
			<div class="oe_title">
				<div class="col-sm-12">

					<div class="form-group">
						<label for="itemName" class="col-sm-2 control-label">Work
							Order For : </label>
						<div class="col-sm-10">
							<select name="workOrderNo" class="form-control" id="workOrderNo"
								onchange="woLeaveChange(this)"
								style="border: 0; border-bottom: 2px ridge;">
								<option disabled="disabled" selected="selected">---
									select a Contractor ---</option>
								<c:forEach items="${contractorList}" var="contractor">
									<option value="${contractor.contractNo}">${contractor.contractorName}</option>
								</c:forEach>
							</select>
						</div>
					</div>
					<div class="col-sm-12">&nbsp;&nbsp;</div>
					<div class="form-group csStoreTicketNoDiv hidden">
						<label for="csStoreTicketNo" class="col-sm-2 control-label">Store
							Ticket No/Ref. No. : </label>
						<div class="col-sm-10">
							<input type="hidden" name="ticketNo" class="csStoreTicketNo" />
							<select class="form-control csStoreTicketNo"
								onchange="csStoreTicketNoLeaveChange(this)"
								style="border: 0; border-bottom: 2px ridge;">
								<option disabled selected>--Select a Store Ticket No--</option>
							</select>
						</div>
					</div>

					<div class="col-sm-12">&nbsp;&nbsp;</div>
					<div class="form-group transformerSerialNoDiv hidden">
						<label for="transformerSerialNo" class="col-sm-2 control-label">Transformer
							No. : </label>
						<div class="col-sm-10">
							<!-- <input type="text" name="transformerSerialNo"
								class="transformerSerialNo" id="transformerSerialNo" required="required"/>  --><select
								onchange="transformerLeaveChange(this)" name="transformerSerialNo"
								class="form-control transformerSerialNo"								
								style="border: 0; border-bottom: 2px ridge;">
								<option disabled selected>--Select a Transformer No--</option>
							</select>
							<!--  -->
						</div>
					</div>
					<!-- <div class="col-sm-12">&nbsp;&nbsp;</div>
					<div class="form-group jobNoDiv hidden">
						<label for="jobNo" class="col-sm-2 control-label">Job No.
							: </label>
						<div class="col-sm-9">
							<input type="text" name="jobCardNo" onblur="checkJobNo()"
								style="border: 0; border-bottom: 2px ridge;" id="jobNo"
								class="jobNo form-control" required="required" />
						</div>
						<div class="col-sm-1">
							<i id="workOrderDecision" style="font-size: 2em;" class=""></i>
						</div>
					</div> -->

				</div>

				<div class="col-sm-12 hidden" id="jobCardTemplateList"></div>

				<div class="col-md-12" style="padding-top: 15px;">
					<div class="col-xs-12 col-sm-6">
						<button type="submit" id="saveButton"
							style="margin-right: 10px; border-radius: 6px;"
							class="width-20 pull-right btn btn-lg btn-success">
							<i class="ace-icon fa fa-save"></i> <span class="bigger-50">Save</span>
						</button>
					</div>

					<div class="col-xs-12 col-sm-6">
						<button type="reset"
							class="width-20  pull-left btn btn-lg btn-danger"
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

<!-- jqurey date-picker  -->

<link
	href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
	rel="stylesheet">
<script
	src="${pageContext.request.contextPath}/resources/assets/js/workshop/jobCardForm.js"></script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>