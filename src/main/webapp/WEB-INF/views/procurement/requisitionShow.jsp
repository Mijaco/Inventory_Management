<%@include file="../common/procurementHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<h4>
			<a href="#" style="text-decoration: none;">Procurement
				Requisition</a> / Show
		</h4>
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/procurement/requisition/list.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Requisition List
			</a> <a
				href="${pageContext.request.contextPath}/procurement/requisition/mstEdit.do?id=${requisitionMst.id}"
				style="text-decoration: none;" class="btn btn-danger btn-sm"> <span
				class="glyphicon glyphicon-edit" aria-hidden="true"></span> Edit
			</a>

		</div>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px; margin-left: 90px; margin-right: 90px;">
		<!-- --------------------- -->

		<div class="oe_title">

			<div class="col-md-6 col-sm-6">
				<div class="form-group">
					<label for="prfNo" class="col-sm-4 control-label">PRF No.</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="prfNo"
							value="${requisitionMst.prfNo}" readonly="readonly"
							style="border: 0; border-bottom: 2px ridge;" name="prfNo" />

					</div>
				</div>
				<div class="col-sm-10">&nbsp;&nbsp;</div>
				<div class="form-group">
					<label for="requisitionOfficer" class="col-sm-4 control-label">Requisition
						By</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="requisitionOfficer"
							value="${requisitionMst.requisitionOfficer}" readonly="readonly"
							placeholder="admin" style="border: 0; border-bottom: 2px ridge;"
							name="requisitionOfficer" />
					</div>
				</div>
				<div class="col-sm-10">&nbsp;&nbsp;</div>
				<div class="form-group">
					<label for="requisitionTo" class="col-sm-4 control-label">Requisition
						To</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="requisitionTo"
							value="${requisitionMst.requisitionTo}" readonly="readonly"
							style="border: 0; border-bottom: 2px ridge;" name="requisitionTo" />
					</div>
				</div>

			</div>

			<%-- <div class="col-md-6">
				<div class="form-group">
					<label for="prfNo" class="col-sm-4 control-label">PRF No.</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="prfNo"
							value="${requisitionMst.prfNo}" readonly="readonly"
							style="border: 0; border-bottom: 2px ridge;" name="prfNo" />


					</div>
				</div>

				<div class="form-group">
					<label for="requisitionOfficer" class="col-sm-4 control-label">Requisition
						By</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="requisitionOfficer"
							value="${requisitionMst.requisitionOfficer}" readonly="readonly"
							placeholder="admin" style="border: 0; border-bottom: 2px ridge;"
							name="requisitionOfficer" />

					</div>
				</div>

				<div class="form-group">
					<label for="requisitionTo" class="col-sm-4 control-label">Requisition
						To</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="requisitionTo"
							value="${requisitionMst.requisitionTo}" readonly="readonly"
							style="border: 0; border-bottom: 2px ridge;" name="requisitionTo">
					</div>
				</div>
			</div> --%>


			<div class="col-md-6 col-sm-6">

				<div class="form-group">
					<label for="requisitionDate"
						class="col-sm-4 col-md-4 control-label">Req. Date</label>
					<div class="col-sm-8 col-md-8">
						<input type="text" class="form-control" id="requisitionDate"
							readonly="readonly"
							value='<fmt:formatDate value="${requisitionMst.requisitionDate}" pattern="dd-MM-yyyy"/>'
							style="border: 0; border-bottom: 2px ridge;"
							name="requisitionDate" />
					</div>
				</div>
				<div class="col-sm-10">&nbsp;&nbsp;</div>
				<div class="form-group">
					<label for="departmentFrom" class="col-sm-4 col-md-4 control-label">Requisition
						From</label>
					<div class="col-sm-8 col-md-8">
						<input type="text" class="form-control" id="departmentFrom"
							value="${requisitionMst.departmentFrom}" readonly="readonly"
							style="border: 0; border-bottom: 2px ridge;"
							name="departmentFrom" />
					</div>
				</div>
				<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
				<div class="form-group">
					<label for="status" class="col-sm-4 col-md-4 control-label">Status</label>
					<div class="col-sm-8 col-md-8">
						<input type="text" class="form-control" id="status"
							value="${stateName}" readonly="readonly"
							style="border: 0; border-bottom: 2px ridge;" name="status" />
					</div>
				</div>

			</div>

			<%-- <div class="col-md-6">
				<div class="form-group">
					<label for="requisitionDate" class="col-sm-4 control-label">Req.
						Date</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="requisitionDate"
							readonly="readonly"
							value='<fmt:formatDate value="${requisitionMst.requisitionDate}" pattern="dd-MM-yyyy"/>'
							style="border: 0; border-bottom: 2px ridge;"
							name="requisitionDate" />
					</div>
				</div>

				<div class="form-group">
					<label for="departmentFrom" class="col-sm-4 control-label">Requisition
						From</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="departmentFrom"
							value="${requisitionMst.departmentFrom}" readonly="readonly"
							style="border: 0; border-bottom: 2px ridge;"
							name="departmentFrom" />
					</div>
				</div>

				<div class="form-group">
					<label for="status" class="col-sm-4 control-label">Status</label>
					<div class="col-sm-8">
						<input type="text" class="form-control" id="status"
							value="${requisitionMst.status}" readonly="readonly"
							style="border: 0; border-bottom: 2px ridge;" name="status" />
						<input type="text" class="form-control" id="status"
							value="${stateName}" readonly="readonly"
							style="border: 0; border-bottom: 2px ridge;" name="status" />
					</div>
				</div>

			</div> --%>


			<c:if test="${!empty requisitionDtlList}">
				<div class="col-md-12">
					<div class="form-group" style="margin-top: 1em;">
						<label for="fax" class="col-sm-4 control-label"
							style="font-style: italic; font-weight: bold;">Requested
							Item(s):</label>
						<div class="col-sm-12">
							<hr />

							<c:forEach items="${requisitionDtlList}" var="requisitionDtl">
								<div class="row">
									<div class="center">
										<div class="form-group col-md-4">
											<input class="form-control" name="itemName" type="text"
												value="${requisitionDtl.itemName }" readonly="readonly"
												style="border: 0; border-bottom: 2px ridge;" />
										</div>
										<div class="form-group col-md-2">
											<input class="form-control" name="itemCode" type="text"
												value="${requisitionDtl.itemCode }" readonly="readonly"
												style="border: 0; border-bottom: 2px ridge;" />
										</div>
										<div class="form-group col-md-1">
											<input class="form-control" name="uom" type="text"
												value="${requisitionDtl.uom }" readonly="readonly"
												style="border: 0; border-bottom: 2px ridge;" />
										</div>
										<div class="form-group col-md-2">
											<input class="form-control" name="quantity" type="text"
												value="${requisitionDtl.quantity }" readonly="readonly"
												style="border: 0; border-bottom: 2px ridge;" />
										</div>
										<div class="form-group  col-md-2">
											<input class="form-control" name="costCenter" type="text"
												value="${requisitionDtl.costCenter }" readonly="readonly"
												style="border: 0; border-bottom: 2px ridge;" />
										</div>

										<div class="form-group  col-md-1">
											<a onclick="editRequisitionDtl(${requisitionDtl.id})"
												data-toggle="modal" data-target="#editRequisitionDtlModal">
												<i
												class="form-control blue ace-icon fa fa-pencil bigger-130"></i>
											</a>
										</div>

									</div>

								</div>
							</c:forEach>

							<div class="">
								<a href="#" style="text-decoration: none;" data-toggle="modal"
									data-target="#newRequisitionDtlModal"> <i
									class="ace-icon fa fa-plus-circle bigger-130"></i> Add more
									Item
								</a>
							</div>

							<hr />
						</div>
					</div>

				</div>
			</c:if>
			<div class="col-md-12">
				<label for="justification" class="col-sm-4 control-label">Comment(s)
					:</label>
				<div class="form-group col-sm-12">
					<textarea rows="3" id="justification" class="form-control"
						maxlength="1000" readonly="readonly" name="justification">${requisitionMst.justification}</textarea>
				</div>

			</div>

			<div class="col-md-12">
				<div class="form-group col-sm-12">
					<form method="POST"
						action="${pageContext.request.contextPath}/procurement/requisition/submit2HeadOfDept.do">
						<input type="text" value="${requisitionMst.prfNo}" hidden="true"
							name="prfNo" /> <input type="text" value="${requisitionMst.id}"
							hidden="true" name="id" /> <input
							class="col-sm-2 btn btn-warning btn-sm" type="submit"
							value="Submit" />
					</form>
				</div>

			</div>

		</div>
		<!-- --------------------- -->
	</div>
</div>

<div id="newRequisitionDtlModal" class="modal fade" role="dialog">
	<div class="modal-dialog modal-lg">

		<!-- Modal content-->
		<div class="modal-content" style="background-color: white;">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title center">
					Add New Item Under PRF No. <i>${requisitionMst.prfNo}</i>
				</h4>
			</div>


			<div class="col-xs-12 col-md-12" style="background-color: white;">
				<div class="col-xs-12 col-md-10 col-md-offset-1 ">
					<%--<form class="form-horizontal"> --%>
					<form method="POST" class="form-horizontal"
						action="${pageContext.request.contextPath}/procurement/requisition/dtlSave.do">
						<div class="form-group">
							<label for="itemName" class="col-sm-2 control-label">Item
								Name</label>
							<div class="col-sm-10">
								<!-- <input class="form-control" name="itemName" type="text"
									placeholder="BRB Cable" id="itemName"
									style="border: 0; border-bottom: 2px ridge;" /> -->
								<input type="hidden" name="itemName" id="itemNameAddMore"
									class="itemNameAddMore" /> <select
									class="form-control itemName" onchange="itemLeaveChanged(this)"
									id="itemName" style="border: 0; border-bottom: 2px ridge;">
									<option disabled selected>-- select an Item --</option>
									<c:if test="${!empty invItemList}">
										<c:forEach items="${invItemList}" var="invItem">
											<option value="${invItem.inventoryItemId}">
												<c:out value="${invItem.inventoryItemName}" /></option>
										</c:forEach>
									</c:if>
								</select>
							</div>
						</div>

						<div class="form-group">
							<label for="itemCode" class="col-sm-2 control-label">Item
								Code</label>
							<div class="col-sm-10">
								<input class="form-control" name="itemCode" type="text"
									placeholder="C001" id="itemCode"
									style="border: 0; border-bottom: 2px ridge;" />
							</div>
						</div>

						<div class="form-group">
							<label for="uom" class="col-sm-2 control-label">Unit</label>
							<div class="col-sm-10">
								<input class="form-control" name="uom" type="text" id="uom"
									placeholder="Meter"
									style="border: 0; border-bottom: 2px ridge;" />
							</div>
						</div>

						<div class="form-group">
							<label for="quantity" class="col-sm-2 control-label">Quantity</label>
							<div class="col-sm-10">
								<input class="form-control" name="quantity" type="text"
									placeholder="1" id="quantity"
									style="border: 0; border-bottom: 2px ridge;" />
							</div>
						</div>

						<div class="form-group">
							<label for="costCenter" class="col-sm-2 control-label">Cost
								Center</label>
							<div class="col-sm-10">
								<input class="form-control" name="costCenter" type="text"
									placeholder="Finance & Accounts" id="costCenter"
									style="border: 0; border-bottom: 2px ridge;" /> <input
									name="requisitionId" type="text" value="${requisitionMst.id}"
									hidden="true" />
							</div>
						</div>

						<div class="form-group">
							<div class="col-sm-12 center">
								<button type="submit" class="btn btn-success">
									<span class="glyphicon glyphicon-floppy-disk"
										aria-hidden="true"></span> Save
								</button>
							</div>
						</div>
					</form>
				</div>

			</div>


		</div>

	</div>
</div>


<div id="editRequisitionDtlModal" class="modal fade" role="dialog">
	<div class="modal-dialog modal-lg">

		<!-- Modal content-->
		<div class="modal-content" style="background-color: white;">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">&times;</button>
				<h4 class="modal-title center">
					Edit Item Under PRF No. <i>${requisitionMst.prfNo}</i>
				</h4>
			</div>


			<div class="col-xs-12 col-md-12" style="background-color: white;">
				<div class="col-xs-12 col-md-10 col-md-offset-1 ">
					<%--<form class="form-horizontal"> --%>
					<form method="POST" class="form-horizontal"
						action="${pageContext.request.contextPath}/procurement/requisition/dtlEdit.do"
						commandName="requisitionMstDtl">
						<div class="form-group">
							<label for="itemName1" class="col-sm-2 control-label">Item
								Name</label>
							<div class="col-sm-10">
								<!-- <input class="form-control" name="itemName" type="text"
									placeholder="BRB Cable" id="itemName1"
									style="border: 0; border-bottom: 2px ridge;" /> -->
								<input type="hidden" name="itemName" id="itemNameUpdate"
									class="itemNameUpdate" /> <select class="form-control itemName"
									id="itemName1" onchange="itemLeaveChange(this)"
									style="border: 0; border-bottom: 2px ridge;">
									<option></option>
									<c:if test="${!empty invItemList}">
										<c:forEach items="${invItemList}" var="invItem">
											<option value="${invItem.inventoryItemId}">
												<c:out value="${invItem.inventoryItemName}" /></option>
										</c:forEach>
									</c:if>
								</select>
							</div>
						</div>

						<div class="form-group">
							<label for="itemCode1" class="col-sm-2 control-label">Item
								Code</label>
							<div class="col-sm-10">
								<input class="form-control" name="itemCode" type="text"
									placeholder="C001" id="itemCode1"
									style="border: 0; border-bottom: 2px ridge;" />
							</div>
						</div>

						<div class="form-group">
							<label for="uom1" class="col-sm-2 control-label">Unit</label>
							<div class="col-sm-10">
								<input class="form-control" name="uom" type="text" id="uom1"
									placeholder="Meter"
									style="border: 0; border-bottom: 2px ridge;" />
							</div>
						</div>

						<div class="form-group">
							<label for="quantity1" class="col-sm-2 control-label">Quantity</label>
							<div class="col-sm-10">
								<input class="form-control" name="quantity" type="text"
									placeholder="1" id="quantity1"
									style="border: 0; border-bottom: 2px ridge;" />
							</div>
						</div>

						<div class="form-group">
							<label for="costCenter1" class="col-sm-2 control-label">Cost
								Center</label>
							<div class="col-sm-10">
								<input class="form-control" name="costCenter" type="text"
									placeholder="Finance & Accounts" id="costCenter1"
									style="border: 0; border-bottom: 2px ridge;" /> <input
									name="id" type="text" hidden="true" id="requisitionDtlId" /> <input
									name="requisitionId" type="text" value="${requisitionMst.id}"
									hidden="true" />
							</div>
						</div>

						<div class="form-group">
							<div class="col-sm-12 center">
								<button type="submit" class="btn btn-success">
									<span class="glyphicon glyphicon-floppy-disk"
										aria-hidden="true"></span> Update
								</button>
							</div>
						</div>
					</form>
				</div>

			</div>


		</div>

	</div>
</div>



<script>
	function editRequisitionDtl(id){
		
		$.ajax({
			url : '${pageContext.request.contextPath}/procurement/requisition/viewRequisitionDtl.do',
			data : "{id:"+id+"}",
			contentType : "application/json",
			success : function(data) {									
				var requisitionDtl = JSON.parse(data);	
				 $("#itemName1").val(requisitionDtl.itemName);
				//document.getElementById("itemName1").value = requisitionDtl.itemName;				 
				 $("#itemCode1").val(requisitionDtl.itemCode);
				 $("#uom1").val(requisitionDtl.uom);
				 $("#quantity1").val(requisitionDtl.quantity);
				 $("#costCenter1").val(requisitionDtl.costCenter); 	
				 $("#requisitionDtlId").val(requisitionDtl.id);
			},
			error : function(data) {
				alert("Server Error");
			},
			type : 'POST'
		});
	}			
</script>

<script>
function itemLeaveChange(element) {

	var e = document.getElementById("itemName1");
	var item_id = e.options[e.selectedIndex].value;
	$
			.ajax({
				url : '${pageContext.request.contextPath}/procurement/requisition/viewInventoryItem.do',
				data : "{inventoryItemId:" + item_id + "}",
				contentType : "application/json",
				success : function(data) {		
					var inventoryItem = JSON.parse(data);
					 $("#itemCode1").val(inventoryItem.inventoryItemItemCode);
					 $("#uom1").val(inventoryItem.inventoryItemUint);
					 $("#itemNameUpdate").val(inventoryItem.inventoryItemName);
				},
				error : function(data) {
					alert("Server Error");
				},
				type : 'POST'
			});
}
</script>

<script>
function itemLeaveChanged(element) {

	var e = document.getElementById("itemName");
	var item_id = e.options[e.selectedIndex].value;
	$
			.ajax({
				url : '${pageContext.request.contextPath}/procurement/requisition/viewInventoryItem.do',
				data : "{inventoryItemId:" + item_id + "}",
				contentType : "application/json",
				success : function(data) {		
					var inventoryItem = JSON.parse(data);
					 $("#itemCode").val(inventoryItem.inventoryItemItemCode);
					 $("#uom").val(inventoryItem.inventoryItemUint);
					 $("#itemNameAddMore").val(inventoryItem.inventoryItemName);
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