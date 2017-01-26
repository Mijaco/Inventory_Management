<%@include file="../common/csHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<h4>
			<a href="#" style="text-decoration: none;">Approval Hierarchy</a> /
			Edit
		</h4>
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/common/approvalHierarchyList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Approval Hierarchy List
			</a>
			<button accesskey="D" class="btn btn-info btn-sm" type="button">
				Discard</button>
		</div>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px; margin-left: 90px; margin-right: 90px;">
		<!-- --------------------- -->
		<form method="POST"
			action="${pageContext.request.contextPath}/common/approvalHierarchyUpdate.do">
			<div class="oe_title">

				<div class="col-md-12 col-sm-12">
					<%-- <div class="form-group">
						<label for="stateName" class="col-sm-2 control-label">State
							Name</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="stateName"
								placeholder="INITIATED"
								style="border: 0; border-bottom: 2px ridge;" name="stateName"
								value="${approvalHierarchy.stateName}" />
						</div>
					</div> --%>
					
					<div class="form-group">
						<label for="operationName" class="col-sm-2 control-label">Operation
							Name</label>
						<div class="col-sm-10">
							<select class="form-control" id="operationName"
								style="border: 0; border-bottom: 2px ridge;" name="operationName">
								<option selected>${approvalHierarchy.operationName}</option>
								<c:if test="${!empty operationList}">
									<c:forEach var="operation" items="${operationList}">
										<option><c:out value="${operation.operationName}"></c:out></option>
									</c:forEach>
								</c:if>
							</select>
						</div>
					</div>
					
					
					<div class="form-group">
						<label for="stateName" class="col-sm-2 control-label">State
							Name</label>
						<div class="col-sm-10">
							<select class="form-control" id="roleName"
								style="border: 0; border-bottom: 2px ridge;" name="stateName">
								<option selected>${approvalHierarchy.stateName}</option>
								<c:if test="${!empty stateList}">
									<c:forEach var="state" items="${stateList}">
										<option><c:out value="${state.stateName}"></c:out></option>
									</c:forEach>
								</c:if>
							</select>
						</div>
					</div>

					<div class="form-group">
						<label for="stateCode" class="col-sm-2 control-label">State Code</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="stateCode"
								value="${approvalHierarchy.stateCode}"
								style="border: 0; border-bottom: 2px ridge;" name="stateCode" />
						</div>
					</div>
					<!-- <div class="col-sm-12">&nbsp;&nbsp;</div> -->
					<div class="form-group">
						<label for="roleName" class="col-sm-2 control-label">Role
							Name</label>
						<div class="col-sm-10">
							<!-- <input type="text" class="form-control" id="roleName"
								style="border: 0; border-bottom: 2px ridge;"
								name="roleName"> -->
							<select class="form-control" id="roleName"
								style="border: 0; border-bottom: 2px ridge;" name="roleName">
								<option selected>${approvalHierarchy.roleName}</option>
								<c:if test="${!empty roleList}">
									<c:forEach var="role" items="${roleList}">
										<option><c:out value="${role.role}"></c:out></option>
									</c:forEach>
								</c:if>
							</select>
						</div>
					</div>

					<div class="form-group">
						<label for="buttonName" class="col-sm-2 control-label">Button
							Name </label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="buttonName"
								placeholder="Submit" value="${approvalHierarchy.buttonName}"
								style="border: 0; border-bottom: 2px ridge;" name="buttonName" />
						</div>
					</div>

					<div class="form-group">
						<label for="approvalHeader" class="col-sm-2 control-label">Approval
							Header</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="approvalHeader"
								placeholder="History" value="${approvalHierarchy.approvalHeader}"
								style="border: 0; border-bottom: 2px ridge;"
								name="approvalHeader" />
						</div>
					</div>

				</div>


				<div class="col-md-12">
					<label for="remarks" class="col-sm-4 control-label">Comments
						:</label>
					<div class="form-group col-sm-12">
						<textarea rows="3" cols="" id="remarks" class="form-control"
							maxlength="1000" name="remarks">${approvalHierarchy.remarks}</textarea>
					</div>

				</div>
				<div class="col-md-12">
					<label for="active" class="col-sm-2 control-label">Is
						Active ?</label>
					<div class="form-group col-sm-10">
						<c:if test="${approvalHierarchy.active eq true}">
							<input type="checkbox" checked name="active" id="active" />
						</c:if>
						<c:if test="${approvalHierarchy.active eq false}">
							<input type="checkbox" name="active" id="active" />
						</c:if>

					</div>
					<input type="hidden" class="form-control" id="modifiedBy"
						value='<sec:authentication property="principal.username" />'
						name="modifiedBy" /> <input type="hidden" class="form-control"
						id="porcid" value='${approvalHierarchy.id}' name="id" />
				</div>
				<div class="col-md-12">
					<div class="form-group" style="margin-top: 2em;">
						<button type="reset" class="width-20 pull-left btn btn-sm">
							<i class="ace-icon fa fa-refresh"></i> <span class="bigger-50">Reset</span>
						</button>
						<button type="submit" style="margin-left: 10px;"
							class="width-20 pull-left btn btn-sm btn-success">
							<i class="ace-icon fa fa-save"></i> <span class="bigger-50">Update</span>
						</button>
					</div>
				</div>
			</div>
		</form>
		<!-- --------------------- -->
	</div>
</div>
<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>