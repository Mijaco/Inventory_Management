<%@include file="../inventory/inventoryheader.jsp"%>
<!-- ---------------------End of Header-------------------------- -->



<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">

		<div class="col-sm-2 o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/inventory/listItemgroup.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Item
				Category List
			</a>
		</div>
		<h2 class="col-sm-8 center blue"
			style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
			Add New Item Category</h2>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px;">
		<!-- --------------------- -->
		<form:form method="POST" id="itemGroupForm"
			action="${pageContext.request.contextPath}/inventory/addItemGroup.do"
			commandName="itemCategory">

			<div class="col-md-12 col-md-offset-2">
				<div class="oe_title">

					<div class="col-md-8">
					
						<div class="form-group">
							<label for="categoryName" class="col-sm-3 control-label">Category
								Name</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="categoryName"
									required="required" name="categoryName" />
							</div>
						</div>
						<br> <br>
						
						<div class="form-group">
							<label for="categoryId" class="col-sm-3 control-label">Category
								Code</label>
							<div class="col-sm-9">
								<input type="number" class="form-control" id="categoryId" required="required"
									name="categoryId" />
							</div>
						</div>
						<br> <br>
						
						<div class="form-group">
							<label for="oldCategoryId" class="col-sm-3 control-label">Category
								Code (Old)</label>
							<div class="col-sm-9">
								<input type="number" class="form-control" id="oldCategoryId"
									name="oldCategoryId" />
							</div>
						</div>
						<br> <br>
						
						<div class="form-group">
							<label for="itemType" class="col-sm-3 control-label">Item
								Type</label>
							<div class="col-sm-9">
								<select name="itemType" id="itemType" class="form-control">
									<option value="C">Construction Item</option>
									<option value="G">General Item</option>
								</select>
							</div>
						</div>
						<br> <br>
						
						<div class="form-group">
							<label for="itemCategoryRemarks" class="col-sm-3 control-label">Remarks</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="itemCategoryRemarks"
									name="itemCategoryRemarks" />
							</div>
						</div>
						<br> <br>
						
						<div class="form-group">
							<label class="col-md-3 control-label" for="prependedcheckbox"></label>
							<div class="col-md-9">
								<div class="input-group">
									<div class="checkbox">
										<label><input type="checkbox" checked path="itemGroupIsActive"
											name="isActive"
											value="active">Active</label>
									</div>
								</div>
							</div>
						</div>
						
					</div>

					<%-- <div class="col-md-6">
						
							<div class="form-group">
							<label for="categoryId" class="col-sm-4 control-label">Category
								Id</label>
							<div class="col-md-8">
								<form:input type="text" class="form-control"
									style="border: 0; border-bottom: 2px ridge;"
									path="categoryId" id="categoryId" />
								<form:errors path="categoryId" cssClass="alert-danger" />
							</div>
						</div>

						<div class="form-group">
							<label for="itemGroupName" class="col-sm-4 control-label">Category
								Name</label>
							<div class="col-md-8">
								<form:input type="text" class="form-control"
									style="border: 0; border-bottom: 2px ridge;"
									path="categoryName" id="categoryName" />
								<form:errors path="categoryName" cssClass="alert-danger" />
							</div>
						</div>


						<div class="form-group">
							<label for="itemGroupName" class="col-sm-4 control-label">Remarks</label>
							<div class="col-md-8">
								<form:input type="text" class="form-control"
									style="border: 0; border-bottom: 2px ridge;"
									path="itemCategoryRemarks" id="itemCategoryRemarks" />
								<form:errors path="itemCategoryRemarks" cssClass="alert-danger" />
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-4 control-label" for="prependedcheckbox"></label>
							<div class="col-md-4">
								<div class="input-group">
									<div class="checkbox">
										<label><input type="checkbox" checked=path=
											"itemGroupIsActive"
											name="isActive"
											value="active">Active</label>
									</div>

									</label>
								</div>

							</div>
						</div>

					</div>

 --%>


					<!-- 					<div class="col-md-10 col-md-offset-1"> -->
					<!-- 						<div class="form-group" style="margin-top: 2em;"> -->
					<!-- 							<button type="reset" class="width-20 pull-left btn btn-sm"> -->
					<!-- 								<i class="ace-icon fa fa-refresh"></i> <span class="bigger-50">Reset</span> -->
					<!-- 							</button> -->
					<!-- 							<button type="submit" style="margin-left: 10px;" -->
					<!-- 								class="width-20 pull-left btn btn-sm btn-success"> -->
					<!-- 								<i class="ace-icon fa fa-save"></i> <span class="bigger-50">Save</span> -->
					<!-- 							</button> -->
					<!-- 						</div> -->

					<!-- 					</div> -->

					<div class="col-md-12" style="padding-top: 15px;">
						<div class='col-md-2'></div>
						<div class='col-md-4'>
							<button type="submit" style="border-radius: 6px;"
								class="btn btn-md btn-success">
								<i class="fa fa-save"></i>&nbsp;Save
							</button>

							<button type="reset" class="btn btn-md btn-danger"
								style="border-radius: 6px; margin-left: 10px;">
								<i class="fa fa-refresh"></i>&nbsp;Reset
							</button>
						</div>
						<div class='col-md-4'></div>
					</div>


				</div>
			</div>
		</form:form>
		<!-- --------------------- -->
	</div>
</div>



<script>
	// When the browser is ready...
	$(function() {

		// Setup form validation on the #register-form element
		$("#itemGroupForm").validate({

			// Specify the validation rules
			rules : {
				itemGroupName : "required",

			},

			// Specify the validation error messages
			messages : {
				itemGroupName : "Please enter Group Name",
			},

			submitHandler : function(form) {
				form.submit();
			}
		});

	});
</script>






















<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>