<%@include file="../common/procurementHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<h4>
			<a href="#" style="text-decoration: none;">Procurement
				Requisition Flow</a> / New
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
			action="${pageContext.request.contextPath}/procurement/requisition/procFlowPrioritySave.do">
			<div class="oe_title">
			
				<div class="col-md-12 col-sm-12">
					<div class="form-group">
						<label for="stateName" class="col-sm-2 control-label">State Name</label>
						<div class="col-sm-10">
							<!-- <input type="text" class="form-control" id="stateName"
								placeholder="INITIATED"
								style="border: 0; border-bottom: 2px ridge;" name="stateName" /> -->
								<select class="form-control" id="stateName" style="border: 0; border-bottom: 2px ridge;"
								name="stateName">
								<option disabled selected> --- Select a State --- </option>
								<c:if test="${!empty procFlowPriorityList}">
								<c:forEach var="procFlowPriority" items="${procFlowPriorityList}">
								<option><c:out value="${procFlowPriority.stateName}"></c:out></option>
								</c:forEach>
								</c:if>
								</select>
						</div>
					</div>

					<div class="form-group">
						<label for="priority" class="col-sm-2 control-label">Priority</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="priority"
								placeholder="100" style="border: 0; border-bottom: 2px ridge;"
								name="priority" />
						</div>
					</div>
					<!-- <div class="col-sm-12">&nbsp;&nbsp;</div> -->
					<div class="form-group">
						<label for="roleName" class="col-sm-2 control-label">Role Name</label>
						<div class="col-sm-10">
							<!-- <input type="text" class="form-control" id="roleName"
								style="border: 0; border-bottom: 2px ridge;"
								name="roleName"> -->
								<select class="form-control" id="roleName" style="border: 0; border-bottom: 2px ridge;"
								name="roleName">
								<option disabled selected> --- Select a Role --- </option>
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
							maxlength="1000" name="remarks"></textarea>
					</div>

				</div>
				<div class="col-md-12">
					<label for="active" class="col-sm-2 control-label">Is Active ?</label>
					<div class="form-group col-sm-10">
						<input type="checkbox" checked="true" name="active" id="active"/>
					</div>
					<input type="hidden" class="form-control" id="createdBy"
								value='<sec:authentication property="principal.username" />' name="createdBy"/>
				</div>
				<div class="col-md-12">
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
		</form>
		<!-- --------------------- -->
	</div>
</div>



<script>
	function itemLeaveChange() {
		//var item_id = document.getElementById("itemName").value;
		var e = document.getElementsByName("itemName")[0];
		var item_id = e.options[e.selectedIndex].value;
		//alert(item_id);

		$
				.ajax({
					url : '${pageContext.request.contextPath}/procurement/requisition/viewInventoryItem.do',
					data : "{inventoryItemId:" + item_id + "}",
					contentType : "application/json",
					success : function(data) {
						var inventoryItem = JSON.parse(data);
						//alert(inventoryItem.inventoryItemName);
						$(".itemCode").val(inventoryItem.inventoryItemItemCode);
						$(".uom").val(inventoryItem.inventoryItemUint);
						/* $("#itemName1").val(requisitionDtl.itemName);
						$("#itemCode1").val(requisitionDtl.itemCode);
						$("#uom1").val(requisitionDtl.uom);
						$("#quantity1").val(requisitionDtl.quantity);
						$("#costCenter1").val(requisitionDtl.costCenter); 	
						$("#requisitionDtlId").val(requisitionDtl.id); */
					},
					error : function(data) {
						alert("Server Error");
					},
					type : 'POST'
				});
	}
</script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>