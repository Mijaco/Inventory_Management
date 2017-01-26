<%@include file="../inventory/inventoryheader.jsp"%>
<!-- ---------------------End of Header-------------------------- -->



<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<h4>
			<a href="#" style="text-decoration: none;">Item Sub Group</a> / Edit
		</h4>
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/inventory/listSubItemgroup.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Item Sub
				Group List
			</a> <a
				href="${pageContext.request.contextPath}/inventory/editItemSubGroup.do?itemSubGroupId=${selectItemSubGroup.itemSubGroupId}"
				style="text-decoration: none;" class="btn btn-info btn-sm"> <span
				class="glyphicon glyphicon-eye-open" aria-hidden="true"></span> Edit
			</a>
		</div>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px; margin-left: 90px; margin-right: 90px;">
		<!-- --------------------- -->
		<form:form >

			<div class="col-md-12 col-md-offset-2">
				<div class="oe_title">


					<div class="col-md-6">

						<div class="form-group">
							<label for="itemGroupName" class="col-sm-4 control-label">Group
								Name</label>
							<div class="col-md-8">
								<input type="text" class="form-control" readonly="readonly"
									style="border: 0; border-bottom: 2px ridge;"
									path="itemGroupName" name="itemGroupName"
									value="${selectItemSubGroup.itemGroupName}" />
							</div>
						</div>
						
						<div class="form-group">
							<label for="itemGroupName" class="col-sm-4 control-label">Sub Group
								Name</label>
							<div class="col-md-8">
								<input type="text" class="form-control" readonly="readonly"
									style="border: 0; border-bottom: 2px ridge;"
									path="itemSubGroupName" name="i temSubGroupName"
									value="${selectItemSubGroup.itemSubGroupName}" />
							</div>
						</div>


						<div class="form-group">
							<label class="col-md-4 control-label" for="prependedcheckbox"></label>
							<div class="col-md-4">
								<div class="input-group">

									<div class="checkbox">
										<label>
										<c:choose>
										
										<c:when test="${selectItemSubGroup.itemSubGroupIsActive==1}">
										<input type="checkbox" checked="checked" readonly="readonly"
											name="isActive" value="${selectItemSubGroup.itemSubGroupIsActive}"></c:when>
										<c:when test="${selectItemSubGroup.itemSubGroupIsActive==0}">
													<input type="checkbox" readonly="readonly" name="isActive"
														value="${selectItemSubGroup.itemSubGroupIsActive}">
												</c:when>
											
										</c:choose>
										Active</label>
									</div>

									</label>
								</div>

							</div>
						</div>
						<input type="text" value="${selectItemSubGroup.itemSubGroupId}"
							hidden="true" name="itemSubGroupId" />
					</div>


				</div>
			</div>
		</form:form>
		<!-- --------------------- -->
	</div>
</div>




<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>