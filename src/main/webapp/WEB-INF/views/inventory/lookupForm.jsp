<%@include file="../common/adminheader.jsp"%>
<!-- ---------------------End of Header-------------------------- -->

<!-- Modified old form design by: Ihteshamul Alam, IBCS -->

<style>

	textarea {
		resize: none;
	}

</style>

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/inventory/lookUpList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Lookup List
			</a>
			<h1 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
				New All Lookup Entry Form</h1>

		</div>
	</div>
	<div class="col-sm-8"
		style="background-color: white; padding: 10px; padding-left: 60px; margin-top: 10px; margin-bottom: 10px; margin-left: 15%; margin-right: 15%;">
		<!-- --------------------- -->
		<form action="${pageContext.request.contextPath}/inventory/addLookUp.do" id="lookupForm" method="POST">
			<div class="col-md-12 table-responsive">
				<table class="table table-bordere table-hover">
					<tbody>
						<tr>
							<td class="col-sm-3 success text-right" style="font-weight: bold;">Parent:&nbsp;<strong class='red'>*</strong></td>
							<td class="col-md-9">
								<select name="parentName" class="form-control" style="border: 0; border-bottom: 2px ridge;" required>
									<option disabled selected>-- Select an Item --</option>
									<c:if test="${!empty parentList}">
										<c:forEach items="${parentList}" var="parentList">

											<option value="${parentList.title}">
												<c:out value="${parentList.title}" />
											</option>
										</c:forEach>
									</c:if>
								</select>
							</td>
						</tr>
						<tr>
							<td class="col-sm-3 success text-right" style="font-weight: bold;">Title:&nbsp;<strong class='red'>*</strong></td>
							<td class="col-md-9">
								<input type="text" class="form-control" id="titleForChild"
									style="border: 0; border-bottom: 2px ridge;"
									name="titleForChild" id="titleForChild" required>
							</td>
						</tr>
						<tr>
							<td class="col-sm-3 success text-right" style="font-weight: bold;">Keyword:&nbsp;<strong class='red'>*</strong></td>
							<td class="col-md-9">
								<input type="text" class="form-control" id="title"
									style="border: 0; border-bottom: 2px ridge;"
									name="keyword" id="keyword" required>
							</td>
						</tr>
						<tr>
							<td class="col-sm-3 success text-right" style="font-weight: bold;">Remarks:&nbsp;<strong class='red'>*</strong></td>
							<td class="col-md-9">
								<textarea class="form-control" id="remark"
									style="border: 0; border-bottom: 2px ridge;"
									name="remarks"></textarea>
							</td>
						</tr>
						<tr>
							<td class="col-sm-3 success text-right" style="font-weight: bold;">Is Active?</td>
							<td class="col-md-9">
								<label><input type="checkbox" checked="checked"
									name="isActive" value="active">&nbsp;Active</label>
							</td>
						</tr>
					</tbody>
				</table>
				
				<div class="col-md-12" style="padding-top: 15px;">
					<div class="col-xs-12 col-sm-6">
						<button type="submit" id="saveButton"
							style="margin-right: 10px; border-radius: 6px;"
							class="pull-right btn btn-lg btn-success">
							<i class="ace-icon fa fa-save"></i> <span class="bigger-50">Save</span>
						</button>
					</div>

					<div class="col-xs-12 col-sm-6">
						<button type="reset" class="pull-left btn btn-lg btn-danger"
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

<script>
	// When the browser is ready...
	$(function() {

		// Setup form validation on the #register-form element
		$("#lookupForm").validate({

			// Specify the validation rules
			rules : {
				titleForChild : "required",
				keyword : "required",

			},

			// Specify the validation error messages
			messages : {
				titleForChild : "Please enter title",
				keyword : "Please enter keyword",

			},

			submitHandler : function(form) {
				form.submit();
			}
		});

	});
</script>





















<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>