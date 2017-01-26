<%@include file="../common/cnHeader.jsp"%>
<link href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel="stylesheet">
<!-- -------------------End of Header-------------------------- -->
<!-- @author nasrin -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/contractor/finalList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Contractor Representative List
			</a>
		</div>



		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">New
			Contractor Representative Form</h1>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<!-- --------------------- -->
		<form method="POST"
			action="${pageContext.request.contextPath}/pnd/contractorRepresentiveSave.do">
			<!-- start for submit  -->
			<div class="oe_title">
				<div class="col-md-6 col-sm-6">
					<div class="form-group">
						<label for="receiveFrom" class="col-sm-4 control-label">Representative
							Name : </label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="representiveName"
								value="" style="border: 0; border-bottom: 2px ridge;"
								name="representiveName" />
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="contractNo" class="col-sm-4 control-label">Contract
							No : </label>
						<div class="col-sm-8">
							<!-- <input type="text" class="form-control" id="contractNo"
								value=""
								style="border: 0; border-bottom: 2px ridge;"
								name="contractNo" /> -->

							<select class="form-control category" name="contractNo"
								id="contractNo" style="border: 0; border-bottom: 2px ridge;">
								<c:if test="${!empty contractorList}">
									<c:forEach items="${contractorList}" var="contractor">
										<option value="${contractor.contractNo}">
											<c:out value="${contractor.contractNo}" /></option>
									</c:forEach>
								</c:if>
							</select>

						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="designation" class="col-sm-4 control-label">Designation
							: </label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="designation" value=""
								style="border: 0; border-bottom: 2px ridge;" name="designation" />
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="contactNo" class="col-sm-4 control-label">Mobile
							No : </label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="contactNo" value=""
								style="border: 0; border-bottom: 2px ridge;" name="contactNo" />
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">Email :</label>
						<div class="col-sm-7">
							<div class="ui-widget">
								<input type="email" class="form-control" id="email"
									style="border: 0; border-bottom: 2px ridge;" 
									placeholder="Enter your valid email id"  onblur=" isEmail()"
									name="email" />
							</div>
						</div>
						<div class="col-sm-1">
							<i id="emailvaldationFlag" style="font-size: 2em;" class=""></i>
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
						<label for="userId" class="col-sm-4 col-md-4 control-label">User
							Id :</label>
						<div class="col-xs-7">
							<input type="hidden" name="contextPath" id="contextPath"
								value="${pageContext.request.contextPath}"> <input
								type="text" class="form-control userId" id="userid"
								name="userId" onblur="checkUser()"
								style="border: 0; border-bottom: 2px ridge;">

						</div>
						<div class="col-sm-1">
							<i id="workOrderDecision" style="font-size: 2em;" class=""></i>
						</div>
					</div>

					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>

					<div class="form-group">
						<label for="listedDate" class="col-sm-4 col-md-4 control-label">Listed
							Date :</label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control datepicker-13"
								id="listedDate" value=""
								style="border: 0; border-bottom: 2px ridge;" name="listedDate" />
						</div>
					</div>
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">Photo :</label>
						<div class="col-sm-8">
							<div class="ui-widget">
								<input type="file" class="form-control" id="picture"
									name="picture" />
							</div>
						</div>
					</div>
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label class="col-sm-4 control-label">Remarks :</label>
						<div class="col-sm-8">
							<div class="ui-widget">
								<input type="text" class="form-control" id="rimarks"
									style="border: 0; border-bottom: 2px ridge;" value=""
									name="rimarks" />
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-12" style="padding-top: 15px;">
					<div class="col-xs-12 col-sm-6">
						<button type="submit" disabled="disabled" id="saveButton"
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
	src="${pageContext.request.contextPath}/resources/assets/js/contractor/contractorRepresentiveForm.js"></script>
<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>