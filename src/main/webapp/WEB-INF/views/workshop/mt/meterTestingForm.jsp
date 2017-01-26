<%@include file="../../common/wsHeader.jsp"%>
<link
	href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
	rel="stylesheet">

<style>
textarea {
	resize: none;
}
</style>
<!-- -------------------End of Header-------------------------- -->
<!-- @author nasrin -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/mt/registerList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Meter
				Testing List
			</a>
		</div>

		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Meter
			Testing Register Form</h1>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<!-- --------------------- -->
		<form method="POST"
			action="${pageContext.request.contextPath}/mt/registerSave.do">

			<!-- start for submit  -->
			<div class="oe_title">
				<div class="col-md-6 col-sm-6">

					<div class="form-group">
						<label for="refNo" class="col-sm-4 control-label">Reference
							No. :</label>
						<div class="col-sm-8">
							<div class="ui-widget">
								<input type="text" class="form-control" id="refNo"
									style="border: 0; border-bottom: 2px ridge;" value=""
									name="refNo" />
							</div>
						</div>
					</div>

					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="consumerName" class="col-sm-4 control-label">Consumer
							Name :</label>
						<div class="col-sm-8">
							<div class="ui-widget">
								<input type="text" class="form-control" id="consumerName"
									style="border: 0; border-bottom: 2px ridge;" value=""
									name="consumerName" />
							</div>
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>

					<div class="form-group">
						<label for="meterNo" class="col-sm-4 control-label">Meter
							No. :</label>
						<div class="col-sm-8">
							<div class="ui-widget">
								<input type="text" class="form-control" id="meterNo"
									style="border: 0; border-bottom: 2px ridge;" value=""
									name="meterNo" />
							</div>
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="meterType" class="col-sm-4 control-label">Meter
							type :</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="meterType"
								placeholder="" style="border: 0; border-bottom: 2px ridge;"
								name="meterType">
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="receivedDate" class="col-sm-4 control-label">Received
							Date :</label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control datepicker-13"
								id="receivedDate" value="" readonly="readonly"
								style="border: 0; border-bottom: 2px ridge;" name="receivedDate" />
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="ctRatioLine" class="col-sm-4 col-md-4 control-label">CT
							Ratio Line :</label>
						<div class="col-sm-8 col-md-8">
							<textarea rows="3" cols="53" name="ctRatioLine" id="ctRatioLine"></textarea>
						</div>
					</div>

					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="readingAsLeft" class="col-sm-4 col-md-4 control-label">Reading
							As Left :</label>
						<div class="col-sm-8 col-md-8">
							<textarea rows="3" cols="53" name="readingAsLeft"
								id="readingAsLeft"></textarea>
						</div>
					</div>

				</div>

				<div class="col-md-6 col-sm-6">
					<div class="form-group">
						<label for="deptId" class="col-sm-4 control-label align-right">Received
							From :</label>
						<div class="col-sm-8">
							<input type="text" list="senderDeptName" class="form-control"
								name="senderDeptName" value=""
								style="border: 0; border-bottom: 2px ridge;" />
							<datalist id="senderDeptName">
								<c:forEach items="${depts}" var="department">
									<option>${department.deptName}</option>
								</c:forEach>
							</datalist>
						</div>
					</div>
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="consumerAddress"
							class="col-sm-4 control-label align-right">Consumer
							Address : </label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="consumerAddress"
								value="" style="border: 0; border-bottom: 2px ridge;"
								name="consumerAddress" />
						</div>
					</div>

					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>

					<div class="form-group">
						<label for="sanctionedLoad"
							class="col-sm-4 control-label align-right">Sanctioned
							Load (KW) :</label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control" id="sanctionedLoad"
								value="" style="border: 0; border-bottom: 2px ridge;"
								name="sanctionedLoad" />
						</div>
					</div>
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>

					<div class="form-group">
						<label for="meterSource"
							class="col-sm-4 col-md-4 control-label align-right">Meter
							Source :</label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control" id="meterSource" value=""
								style="border: 0; border-bottom: 2px ridge;" name="meterSource" />
						</div>
					</div>

					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>

					<div class="form-group">
						<label for="omfAndDmf" class="col-sm-4 control-label align-right">OMF
							&amp; DMF :</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="omfAndDmf" value=""
								style="border: 0; border-bottom: 2px ridge;" name="omfAndDmf" />
						</div>
					</div>

					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>

					<div class="form-group">
						<label for="ctRatioMeter"
							class="col-sm-4 control-label align-right">CT Ratio Meter
							:</label>
						<div class="col-sm-8">
							<textarea rows="3" cols="53" name="ctRatioMeter"
								id="ctRatioMeter"></textarea>
						</div>
					</div>

					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>

					<div class="form-group">
						<label for="sealInfo" class="col-sm-4 control-label align-right">Seal
							Information :</label>
						<div class="col-sm-8">
							<textarea rows="3" cols="53" name="sealInfo" id="sealInfo"></textarea>
						</div>
					</div>

				</div>

				<div class="col-md-12" style="padding-top: 15px;">
					<div class="form-group">
						<label for="remarks" class="col-sm-2 control-label">
							Remarks :</label>
						<div class="col-sm-10">
							<textarea rows="3" cols="146" name="remarks" id="remarks"></textarea>
						</div>
					</div>
				</div>

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
<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>