<%@include file="../inventory/inventoryheader.jsp"%>
<!-- ---------------------End of Header-------------------------- -->



<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<h4>
			<a href="#" style="text-decoration: none;">Item Sub Group</a> / New
		</h4>
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/inventory/listSubItemgroup.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Item
				Sub Group List
			</a>
			<button accesskey="D" class="btn btn-info btn-sm" type="button">
				Discard</button>
		</div>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px; margin-left: 90px; margin-right: 90px;">
		<!-- --------------------- -->
		<form:form method="POST" id="itemSubGroupForm" novalidate="novalidate"
			action="${pageContext.request.contextPath}/inventory/addSubItemGroup.do"
			commandName="itemSubGroup">

			<div class="col-md-12 col-md-offset-2">
				<div class="oe_title">


					<div class="col-md-6">

						<div class="form-group">
							<label for="itemGroupName" class="col-sm-4 control-label">Group
								Name</label>
							<div class="col-md-8">
								<form:select path="itemGroupName" class="form-control" id ="groupName"
									style="border: 0; border-bottom: 2px ridge;">
									<option disabled selected>-- select an Item --</option>

									<c:if test="${!empty itemGroupSubList}">
										<c:forEach items="${itemGroupSubList}" var="itemGroupSubList">

											<form:option value="${itemGroupSubList.itemGroupName}">
												<c:out value="${itemGroupSubList.itemGroupName}" />
											</form:option>
										</c:forEach>
									</c:if>
								</form:select>
							</div>
						</div>


						<div class="form-group">
							<label for="itemSubGroupName" class="col-sm-4 control-label">Sub
								Group</label>
							<div class="col-md-8">
								<input type="text" class="form-control" id="itemSubGroupName" 
									style="border: 0; border-bottom: 2px ridge;"
									name="itemSubGroupName" />
								<form:errors path="itemSubGroupName" cssClass="alert-danger"  />  	
							</div>
						</div>

						<div class="form-group">
							<label class="col-md-4 control-label" for="prependedcheckbox"></label>
							<div class="col-sm-8">
								<div class="input-group">

									<div class="checkbox">
										<label><input type="checkbox" checked="checked"
											name="isActive" value="active">Active</label>
									</div>

									</label>
								</div>

							</div>
						</div>

					</div>




					<div class="col-md-10 col-md-offset-1">
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
			</div>
		</form:form>
		<!-- --------------------- -->
	</div>
</div>

 <script>
  
  // When the browser is ready...
  $(function() {
  
    // Setup form validation on the #register-form element
    $("#itemSubGroupForm").validate({
    
        // Specify the validation rules
        rules: {
        	groupName: "required",
        	itemSubGroupName: "required",
           
        },
        
        // Specify the validation error messages
        messages: {
        	itemSubGroupName: "Please enter Sub Group Name",
        	groupName: "Please select Group Name",
        },
        
        submitHandler: function(form) {
            form.submit();
        }
    });

  });
  
  </script>


<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>