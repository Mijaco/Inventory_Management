<%@include file="../inventory/inventoryheader.jsp"%>
<!-- ---------------------End of Header-------------------------- -->



<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit col-sm-2" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/inventory/listInventoryItem.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Item
				List
			</a>
		</div>

		<h2 class="col-sm-8 center blue"
			style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
			Add New Item</h2>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px;">
		<!-- --------------------- -->
		<form:form method="POST"
			action="${pageContext.request.contextPath}/inventory/addInventoryItem.do"
			id="inventoryItemForm" commandName="itemMaster">

			<div class="col-md-12 col-md-offset-2">
				<div class="oe_title">

					<div class="col-md-8">
						<div class="form-group">
							<label for="itemGroupList" class="col-sm-3 control-label">Category
							</label>
							<div class="col-md-9">
								<form:select path="categoryId" id="categoryId"
									class="form-control"
									onstyle="border: 0; border-bottom: 2px ridge;">
									<form:option value="" disabled="disabled">
										<c:out value="Select Category" />
									</form:option>
									<c:if test="${!empty itemGroupList}">
										<c:forEach items="${itemGroupList}" var="itemGroupList">

											<form:option value="${itemGroupList.categoryId}">
												<c:out value="${itemGroupList.categoryName}" />
											</form:option>
										</c:forEach>
									</c:if>
								</form:select>
							</div>
						</div>
						<br> <br>
						<div class="form-group">
							<label for="itemId" class="col-sm-3 control-label">Item
								Code</label>
							<div class="col-sm-2">
								<input type="text" class="form-control" id="category"
									readonly="readonly" name="category" />
							</div>
							<div class="col-sm-7">
								<input type="text" class="form-control" id="itemId"
									name="itemId" />
							</div>
						</div>
						<br /> <br>

						<div class="form-group">
							<label for="oldItemCode" class="col-sm-3 control-label">Item
								Code (Old)</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="oldItemCode"
									name="oldItemCode" />
							</div>
						</div>
						<br /> <br>
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
							<label for="inventoryItemName" class="col-sm-3 control-label">Item
								Name</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="itemName"
									name="itemName" />
							</div>
						</div>
						<br> <br>
						<div class="form-group">
							<label for="lookupList" class="col-sm-3 control-label">UOM</label>
							<div class="col-sm-9">
								<form:select path="unitCode" class="form-control">
									<form:option value="" disabled="disabled">
										<c:out value="Select Unit" />
									</form:option>
									<c:if test="${!empty lookupList}">
										<c:forEach items="${lookupList}" var="lookupList">

											<form:option value="${lookupList.title}">
												<c:out value="${lookupList.title}" />
											</form:option>
										</c:forEach>
									</c:if>
								</form:select>
							</div>
						</div>
						<br> <br>
						<div class="form-group">
							<label for="remarks" class="col-sm-3 control-label">Remarks</label>
							<div class="col-sm-9">
								<input type="text" class="form-control" id="remarks"
									name="remarks" />
							</div>
						</div>
						<br> <br>
						<div class="form-group">
							<div class="col-sm-offset-3 col-sm-9">
								<div class="checkbox">
									<label><input type="checkbox" checked="checked"
										name="isActive" value="active">Active</label>
								</div>
							</div>
						</div>

						<div class="form-group">
							<div class="col-sm-offset-3 col-sm-9">
								<div class="checkbox">
									<label for="isFixedAsset"> <input type="checkbox"
										id="isFixedAsset" name="isFixedAsset"> Fixed Assets
									</label>
								</div>
							</div>
						</div>

						<div class="form-group">
							<div class="col-sm-offset-3 col-sm-9">
								<div class="checkbox">
									<label for="isSpecial"> <input type="checkbox"
										id="isSpecial" value="1" name="isSpecial"> Is Special
									</label>
								</div>
							</div>
						</div>

					</div>


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
		$("#inventoryItemForm").validate({

			// Specify the validation rules
			rules : {
				inventoryItemName : "required",
				inventoryItemItemCode : "required",
				inventoryItemType : "required"

			},

			// Specify the validation error messages
			messages : {
				inventoryItemName : "Please enter Item Name",
				inventoryItemItemCode : "Please enter Item Code",
				inventoryItemType : "Please enter Item Type"
			},

			submitHandler : function(form) {
				form.submit();
			}
		});

	});
</script>



<script>
	$("select#inventoryItemGroupName")
			.change(
					function() {

						$
								.getJSON(
										"${pageContext.request.contextPath}/inventory/searchSubGroupByGroupName.do",
										{
											itemGroupName : $(this).val()
										},
										function(data) {
											var options = '';
											for (var i = 0; i < data.length; i++) {
												options += '<option value="' + data[i].itemSubGroupName + '">'
														+ data[i].itemSubGroupName
														+ '</option>';
											}
											$(
													"select#inventoryItemSubGroupName")
													.html(options);
										});
					});

	$("select#categoryId").change(function() {

		var categoryId = $(this).val() + ".";

		$("#category").val(categoryId);

	});
</script>




<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>