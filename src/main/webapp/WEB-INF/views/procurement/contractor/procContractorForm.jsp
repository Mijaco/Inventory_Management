<%-- <%@include file="../../common/procurementHeader.jsp"%> --%>

<%@include file="../../common/lsHeader.jsp"%>
<link
	href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
	rel="stylesheet">
<!-- -------------------End of Header-------------------------- -->
<!-- @author nasrin -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/ls/contractorList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Contractor List
			</a>
		</div>

		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">New
			Contractor Form</h1>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px;">
		<!-- --------------------- -->
		<form method="POST" enctype="multipart/form-data"
			action="${pageContext.request.contextPath}/proc/contractorSave.do">

			<input type="hidden" name="contextPath" id="contextPath"
				value="${pageContext.request.contextPath}"> <input
				type="hidden" name="khathId" id="khathId" value="${descoKhath.id}">

			<input type="hidden" name="contractorType" id="contractorType"
				value="SND">
			<!-- start for submit  -->
			<div class="oe_title">
				<div class="col-md-6 col-sm-6">

					<div class="form-group">
						<label for="contractNo" class="col-sm-4 control-label">Contract
							No:&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-7">
							<input type="text" class="form-control" id="contractNo" value=""
								onblur="checkContractNumber()"
								style="border: 0; border-bottom: 2px ridge;" name="contractNo" />
						</div>
						<div class="col-sm-1">
							<i id="workOrderDecision" style="font-size: 2em;" class=""></i>
						</div>
					</div>

					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">Contractor Name:&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-8">
							<div class="ui-widget">
								<input type="text" class="form-control" id="contractorName"
									style="border: 0; border-bottom: 2px ridge;" value=""
									name="contractorName" />
							</div>
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>

					<div class="form-group">
						<label class="col-sm-4 control-label">Contractor Type:&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-8">
							<div class="ui-widget">
								<input type="text" class="form-control" id="division"
									style="border: 0; border-bottom: 2px ridge;" value=""
									name="division" />
							</div>
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<%-- <input type="text" id="contractNo" value="${contractNo}" name="contractNo" />  --%>

						<label for="address" class="col-sm-4 control-label">Address
							:</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="address"
								placeholder="" style="border: 0; border-bottom: 2px ridge;"
								name="address">
						</div>
					</div>
				</div>

				<div class="col-md-6 col-sm-6">

					<div class="form-group">
						<label for="tenderNo" class="col-sm-4 control-label align-right">Tender
							No:&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="tenderNo" value=""
								style="border: 0; border-bottom: 2px ridge;" name="tenderNo" />
						</div>
					</div>

					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>

					<div class="form-group">
						<label for="contractDate"
							class="col-sm-4 control-label align-right">Contract Date:&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control datepicker-15"
								id="contractDate" value="" readonly="readonly"
								style="border: 0; border-bottom: 2px ridge;" name="contractDate" />
						</div>
					</div>
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>

					<div class="form-group">
						<label for="expiryDate"
							class="col-sm-4 col-md-4 control-label align-right">Expire
							Date:&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control datepicker-21"
								id="expiryDate" value="" readonly="readonly"
								style="border: 0; border-bottom: 2px ridge;" name="expiryDate" />
						</div>
					</div>
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>

					<!-- 	<div class="form-group">
						<label for="updateValidityDate"
							class="col-sm-4 col-md-4 control-label align-right">Updated
							Validity Date :</label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control datepicker-13"
								id="updateValidityDate" value=""
								style="border: 0; border-bottom: 2px ridge;"
								name="updateValidityDate" />
						</div>
					</div>
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div> 
					
					<div class="form-group">
						<label for="deptId" class="col-sm-2 control-label">Contract For						
							:</label>
						<div class="col-sm-10">
							<select name="deptId" class="form-control" id="deptId"
								style="border: 0; border-bottom: 2px ridge;">
								<option disabled="disabled" selected="selected">---
									select a S&D ---</option>
								<c:forEach items="${depts}" var="department">
									<option value="${department.id}">${department.deptName}</option>
								</c:forEach>
							</select>
						</div>
					</div> -->
					<div class="form-group">
						<label for="deptId" class="col-sm-4 control-label align-right">Contract For:&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-8">
							 <select name="deptId" class="form-control" id="deptId"
								style="border: 0; border-bottom: 2px ridge;">
								<option disabled="disabled" selected="selected">---
									select a S&amp;D ---</option>
								<c:forEach items="${depts}" var="department">
									<option value="${department.deptId}">${department.deptName}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>


				<div class="col-md-12 col-sm-12">
					<div class="form-group" style="margin-top: 1em;">
						<label for="fax" class="col-sm-4 control-label"
							style="font-style: italic; font-weight: bold;">Contractor
							Representative(s):</label>

						<div class="col-xs-12 table-responsive">
							<div class="table">
								<div style="width: 1600px;">
									<hr />
									<div class="col-xs-12">
										<div class="form-group col-sm-2 col-xs-12">
											<b>Name</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>User Id</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Designation</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Email</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Mobile</b>
										</div>
										<div class="form-group col-sm-2 col-xs-12">
											<b>Address</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Start Date</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>End Date</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Photo</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Add More</b>
										</div>
									</div>

									<div class="col-xs-12">
										<div class="row">
											<div class="control-group" id="fields">
												<div class="controls">
													<div class="aaa">
														<div class="col-xs-12 entry" id="myArea0">
															<div class="row">
																<div class="form-group col-sm-2 col-xs-12">
																	<input class="form-control representativeName"
																		name="representativeName" type="text"
																		placeholder="Enter Name" required="required"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control userId" name="repUserId"
																		required="required" type="text" placeholder="R2016xxx"
																		id="repUserId0" onblur="checkUser(this)"
																		style="border: 0; border-bottom: 2px ridge;" />
																	<h5 class="text-danger hide" id="errUserId0"><strong>Invalid userid</strong></h5>
																</div>
																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control designation"
																		name="reDesignation" type="text"
																		placeholder="Enter designation"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>

																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control email" required="required"
																		onblur="validateDuplicateEmail(this)" name="repEmail"
																		id="repEmail0" type="email" placeholder="Enter email id"
																		style="border: 0; border-bottom: 2px ridge;" />
																	<h5 class="text-danger hide" id="errUserEmail0"><strong>Invalid Email</strong></h5>
																</div>

																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control mobile" name="repMobile"
																		type="text" placeholder="Enter mobile number"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>

																<div class="form-group col-sm-2 col-xs-12">
																	<input class="form-control address" name="repAddress"
																		type="text" placeholder="Enter address"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control datepicker-15 startDate"
																		id="startDate0"
																		name="repStartDate" type="text" placeholder="Set date"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control datepicker-21 endDate"
																		id="endDate0" name="repEndDate" type="text"
																		placeholder="Set date"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>

																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control picture" name="picture"
																		type="file" accept="image/*"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1 col-xs-3">
																	<button class="btn btn-success btn-add" type="button">
																		<span class="glyphicon glyphicon-plus"></span>
																	</button>
																	
																	<button class="btn btn-danger btn-remove" type="button">
													                  	<span class="glyphicon glyphicon-minus"></span>
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


				<div class="col-md-12" style="padding-top: 15px;">
					<div class="col-xs-12 col-sm-6">
						<button type="submit" id="saveButton" disabled="disabled"
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



<script
	src="${pageContext.request.contextPath}/resources/assets/js/procurement/contractor/procContractorForm.js"></script>
<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>