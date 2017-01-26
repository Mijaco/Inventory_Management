<%@include file="../inventory/inventoryheader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<h4>
			<a href="#" style="text-decoration: none;">Inventory
				Requisition Flow</a> / Show
		</h4>
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/inventory/inventoryFlowPriorityList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Inventory Flow List
			</a>
			<button accesskey="D" class="btn btn-info btn-sm" type="button">
				Discard</button>
		</div>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px; margin-left: 90px; margin-right: 90px;">
		<!-- --------------------- -->
		<div class="oe_title">

			<div class="col-md-12 col-sm-12">
				<table class="col-md-12 col-sm-12 table-bordered">
					<tr>
						<th class="col-md-2 col-sm-2">State Name</th>
						<td class="col-md-1 col-sm-1">:</td>
						<td class="col-md-9 col-sm-9">${inventoryFlowPriority.stateName}</td>
					</tr>
					<tr>
						<th class="col-md-2 col-sm-2">Priority</th>
						<td class="col-md-1 col-sm-1">:</td>
						<td class="col-md-9 col-sm-9">${inventoryFlowPriority.priority}</td>
					</tr>
					<tr>
						<th class="col-md-2 col-sm-2">Role Name</th>
						<td class="col-md-1 col-sm-1">:</td>
						<td class="col-md-9 col-sm-9">${inventoryFlowPriority.roleName}</td>
					</tr>
					<tr>
						<th class="col-md-2 col-sm-2">Is Active ?</th>
						<td class="col-md-1 col-sm-1">:</td>
						<td class="col-md-9 col-sm-9">
						<c:if test="${inventoryFlowPriority.active eq true}">Yes</c:if>
						<c:if test="${inventoryFlowPriority.active eq false}">No</c:if>
						</td>
					</tr>
					<tr>
						<th class="col-md-2 col-sm-2">Comments</th>
						<td class="col-md-1 col-sm-1">:</td>
						<td class="col-md-9 col-sm-9">${inventoryFlowPriority.remarks}</td>
					</tr>
					<tr>
						<th class="col-md-2 col-sm-2">Created By</th>
						<td class="col-md-1 col-sm-1">:</td>
						<td class="col-md-9 col-sm-9">${inventoryFlowPriority.createdBy}</td>
					</tr>
					<tr>
						<th class="col-md-2 col-sm-2">Created Date</th>
						<td class="col-md-1 col-sm-1">:</td>
						<td class="col-md-9 col-sm-9"><fmt:formatDate
							value="${inventoryFlowPriority.createdDate}" pattern="dd-MM-yyyy" /></td>
					</tr>
					<tr>
						<th class="col-md-2 col-sm-2">Modified By</th>
						<td class="col-md-1 col-sm-1">:</td>
						<td class="col-md-9 col-sm-9">${inventoryFlowPriority.modifiedBy}</td>
					</tr>
					<tr>
						<th class="col-md-2 col-sm-2">Modified Date</th>
						<td class="col-md-1 col-sm-1">:</td>
						<td class="col-md-9 col-sm-9"><fmt:formatDate
							value="${inventoryFlowPriority.modifiedDate}" pattern="dd-MM-yyyy" /></td>
					</tr>
				</table>
			</div>
		</div>
		<%-- <div class="oe_title">

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
				<div class="form-group">
					<label for="roleName" class="col-sm-2 control-label">Role
						Name</label>
					<div class="col-sm-10">
						<input type="text" class="form-control" id="roleName"
							style="border: 0; border-bottom: 2px ridge;" name="roleName"
							value="${procurementFlowPriority.roleName}" />						
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
				<label for="active" class="col-sm-2 control-label">Is Active
					?</label>
				<div class="form-group col-sm-10">
					<c:if test="${procurementFlowPriority.active eq true}">
						<input type="checkbox" checked name="active" id="active" />
					</c:if>
					<c:if test="${procurementFlowPriority.active eq false}">
						<input type="checkbox" name="active" id="active" />
					</c:if>

				</div>
			</div>
		</div> --%>

		<!-- --------------------- -->
	</div>
</div>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>