<%@include file="../common/procurementHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<h4>
			<a href="#" style="text-decoration: none;">Procurement
				Requisition Flow</a> / Edit
		</h4>
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/procurement/requisition/procFlowPriorityList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Requisition Flow List
			</a>
			<button accesskey="D" class="btn btn-info btn-sm" type="button">
				Discard</button>
		</div>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px; margin-left: 90px; margin-right: 90px;">
		<!-- --------------------- -->
		<form method="POST"
			action="${pageContext.request.contextPath}/procurement/requisition/procFlowPriorityUpdate.do">
			<div class="oe_title">

				<div class="col-md-12 col-sm-12">
					<div class="form-group">
						<label for="stateName" class="col-sm-2 control-label">State
							Name</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="stateName"
								placeholder="INITIATED"
								style="border: 0; border-bottom: 2px ridge;" name="stateName"
								value="${procurementFlowPriority.stateName}" />
						</div>
					</div>

					<div class="form-group">
						<label for="priority" class="col-sm-2 control-label">Priority</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="priority"
								value="${procurementFlowPriority.priority}" placeholder="100"
								style="border: 0; border-bottom: 2px ridge;" name="priority" />
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
								<option selected>${procurementFlowPriority.roleName}</option>
								<c:if test="${!empty roleList}">
									<c:forEach var="role" items="${roleList}">
										<option><c:out value="${role.role}"></c:out></option>
									</c:forEach>
								</c:if>
							</select>
						</div>
					</div>

				</div>


				<div class="col-md-12">
					<label for="remarks" class="col-sm-4 control-label">Comments
						:</label>
					<div class="form-group col-sm-12">
						<textarea rows="3" cols="" id="remarks" class="form-control"
							maxlength="1000" name="remarks">${procurementFlowPriority.remarks}</textarea>
					</div>

				</div>
				<div class="col-md-12">
					<label for="active" class="col-sm-2 control-label">Is
						Active ?</label>
					<div class="form-group col-sm-10">
						<c:if test="${procurementFlowPriority.active eq true}">
							<input type="checkbox" checked name="active" id="active" />
						</c:if>
						<c:if test="${procurementFlowPriority.active eq false}">
							<input type="checkbox" name="active" id="active" />
						</c:if>

					</div>
					<input type="hidden" class="form-control" id="modifiedBy"
						value='<sec:authentication property="principal.username" />'
						name="modifiedBy" /> <input type="hidden" class="form-control"
						id="porcid" value='${procurementFlowPriority.id}' name="id" />
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