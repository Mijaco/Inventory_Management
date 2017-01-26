<%@include file="../common/procurementHeader.jsp"%>

	<div class="container-fluid icon-box"
		style="background-color: #858585;">
		<div class="row"
			style="background-color: white; padding: 10px; padding-left: 20px">
			<h4>
				<a href="#" style="text-decoration: none;">Vendors</a> / New
			</h4>
			<div class="o_form_buttons_edit" style="display: block;">
				<a href="${pageContext.request.contextPath}/procurement/vendor/list.do" style="text-decoration: none;"
					class="btn btn-primary btn-sm"> <span
					class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Vendor
					List
				</a>
				<button accesskey="D"
					class="btn btn-info btn-sm" type="button">
					Discard</button>
			</div>
		</div>

		<div class="row"
			style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px; margin-left: 90px; margin-right: 90px;">
			<!-- --------------------- -->
			<form method="POST"
				action="${pageContext.request.contextPath}/procurement/vendor/save.do"
				commandName="vendor">
				<div class="oe_title">

					<div class="o_radio_item">
						<input class="o_form_radio" type="radio" id="radio42_person"
							onclick="javascript:yesnoCheck();" name="radio42" value="person"
							checked> <label class="o_form_label" for="radio42_person"
							style="margin-right: 10px"><b>Individual </b></label> <input
							class="o_form_radio" type="radio" id="radio42_company"
							onclick="javascript:yesnoCheck();" name="radio42" value="company">
						<label class="o_form_label" for="radio42_company"><b>Company</b></label>
					</div>


					<input class="o_form_input o_form_field o_form_required"
						placeholder="Name" type="text" name="name"
						style="border: 0; border-bottom: 2px ridge; width: 60%; font-size: 2em" />

					<input class="o_form_input o_form_field o_form_required"
						id="company" placeholder="Company" type="text" name="company"
						style="border: 0; border-bottom: 2px ridge; width: 60%; margin-bottom: 20px" />

					<div class="col-md-6">
						<div class="form-group">
							<label for="address" class="col-sm-2 control-label">Address</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="address"
									placeholder="Address 1"
									style="border: 0; border-bottom: 2px ridge;" name="address1" />
								<input type="text" class="form-control" placeholder="Address 2"
									style="border: 0; border-bottom: 2px ridge;" name="address2" />
								<input type="text" class="form-control" placeholder="Address 3"
									style="border: 0; border-bottom: 2px ridge;" name="address3" />
							</div>
						</div>

						<div class="form-group">
							<label for="website" class="col-sm-2 control-label">Website</label>
							<div class="col-sm-10">
								<input type="text" class="form-control" id="website"
									placeholder="www.example.com"
									style="border: 0; border-bottom: 2px ridge;" name="website" />
							</div>
						</div>

						<div class="form-group" style="margin-top: 20em;">
							<button type="reset" class="width-20 pull-left btn btn-sm">
								<i class="ace-icon fa fa-refresh"></i> <span class="bigger-50">Reset</span>
							</button>
							<button type="submit" style="margin-left: 10px;"
								class="width-20 pull-left btn btn-sm btn-success">
								<i class="ace-icon fa fa-save"></i> <span class="bigger-50">Save</span>
							</button>
						</div>

					</div>

					<div class="col-md-6">

						<div class="form-group">
							<label for="jobpos" class="col-sm-3 control-label">Job
								Position</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="jobpos"
									placeholder="ex: Programmer"
									style="border: 0; border-bottom: 2px ridge;" name="jobpos">
							</div>
						</div>

						<div class="form-group">
							<label for="phone" class="col-sm-3 control-label">Phone</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="phone"
									placeholder="Phone Number"
									style="border: 0; border-bottom: 2px ridge;" name="phone" />
							</div>
						</div>

						<div class="form-group">
							<label for="mobile" class="col-sm-3 control-label">Mobile</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="mobile"
									style="border: 0; border-bottom: 2px ridge;" name="mobile" />
							</div>
						</div>

						<div class="form-group">
							<label for="fax" class="col-sm-3 control-label">Fax</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="fax"
									style="border: 0; border-bottom: 2px ridge;" name="fax" />
							</div>
						</div>

						<div class="form-group">
							<label for="email" class="col-sm-3 control-label">Email</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="email"
									style="border: 0; border-bottom: 2px ridge;" name="email" />
							</div>
						</div>

						<div class="form-group">
							<label for="title" class="col-sm-3 control-label">Title</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="title"
									style="border: 0; border-bottom: 2px ridge;" name="title" />
							</div>
						</div>

						<div class="form-group">
							<label for="language" class="col-sm-3 control-label">Language</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="language"
									style="border: 0; border-bottom: 2px ridge;" name="language" />
							</div>
						</div>

						<div class="form-group">
							<label for="tag" class="col-sm-3 control-label">Tag</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="tag"
									style="border: 0; border-bottom: 2px ridge;" name="tag" />
							</div>
						</div>

					</div>

				</div>

				<script>
					function yesnoCheck() {
						if (document.getElementById('radio42_person').checked) {
							document.getElementById('company').value = "";
							document.getElementById('company').style.visibility = 'visible';
						} else {
							document.getElementById('company').value = "";
							document.getElementById('company').style.visibility = 'hidden';
						}
					}
				</script>
			</form>
			<!-- --------------------- -->
		</div>
	</div>

	<!-- -------------------------------------------------------------------------------------- -->
<script
		src="${pageContext.request.contextPath}/resources/assets/js/bootstrap.min.js"></script>
<%@include file="../common/ibcsFooter.jsp"%>