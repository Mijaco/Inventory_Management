<%@include file="../common/csHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<h4>
			<a href="#" style="text-decoration: none;">Store
				Requisition</a> / Show
		</h4>
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/cs/store/listCsRequisition.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Store Requisition List
			</a> <a
				href="${pageContext.request.contextPath}/cs/store/centralStorerequisitionEdit.do?id=${storeRequisitionMst.id}"
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
						<label for="srfNo" class="col-sm-4 control-label">Requisition no</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="srfNo"
								value="${centralStoreRequisitionMst.requisitionNo}" readonly="readonly"
								style="border: 0; border-bottom: 2px ridge;" name="srfNo" />
						</div>
					</div>

					<div class="form-group">
						<label for="identerName" class="col-sm-4 control-label">Identer Name
							</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="identerName"
								value="${centralStoreRequisitionMst.identerName}" readonly="readonly"
								placeholder="admin" style="border: 0; border-bottom: 2px ridge;"
								name="identerName" />

						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="requisitionTo" class="col-sm-4 control-label">Gate Pass No
							</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id=requisitionDate
								value='<fmt:formatDate value="${centralStoreRequisitionMst.requisitionDate}" pattern="dd-MM-yyyy"/>'readonly="readonly"
								style="border: 0; border-bottom: 2px ridge;"
								name="getPassNo">
						</div>
					</div>
				</div>
				
					
				<div class="col-md-6 col-sm-6" >
					<div class="form-group">
						<label for="requisitionDate" class="col-sm-4 control-label">Gate Pass Date.
							</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="getPassDate"
								readonly="readonly"
								value='<fmt:formatDate value="${centralStoreRequisitionMst.getPassDate}" pattern="dd-MM-yyyy"/>'
								style="border: 0; border-bottom: 2px ridge;" />
						</div>
					</div>
					
					
				</div>
				
				<div class="col-md-6 col-sm-6" >
					<div class="form-group">
						<label for="requisitionDate" class="col-sm-4 control-label">Gate Pass No.
							</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="getPassNo"
								readonly="readonly"
								value="${centralStoreRequisitionMst.getPassNo}"
								style="border: 0; border-bottom: 2px ridge;" />
						</div>
					</div>
					
					
				</div>
			
			</div>


			<c:if test="${!empty centralStoreRequisitionDtlList}">
				<div class="col-md-12">
					<div class="form-group" style="margin-top: 1em;">
						<label for="fax" class="col-sm-4 control-label"
							style="font-style: italic; font-weight: bold;">Requested
							Item(s):</label>
						<div class="col-sm-12">
							<hr />

							<c:forEach items="${centralStoreRequisitionDtlList}" var="centralStoreRequisitionDtlList">
								<div class="row">
									<div class="center">
										<div class="form-group col-md-4">
											<input class="form-control" name="itemName" type="text"
												value="${centralStoreRequisitionDtlList.itemCode }" readonly="readonly"
												style="border: 0; border-bottom: 2px ridge;" />
										</div>
										<div class="form-group col-md-2">
											<input class="form-control" name="descriptionMaterials" type="text"
												value="${centralStoreRequisitionDtlList.descriptionMaterials }" readonly="readonly"
												style="border: 0; border-bottom: 2px ridge;" />
										</div>
										<div class="form-group col-md-1">
											<input class="form-control" name="noSize" type="text"
												value="${centralStoreRequisitionDtlList.noSize }" readonly="readonly"
												style="border: 0; border-bottom: 2px ridge;" />
										</div>
										<div class="form-group col-md-2">
											<input class="form-control" name="quantityRequired" type="text"
												value="${centralStoreRequisitionDtlList.quantityRequired }" readonly="readonly"
												style="border: 0; border-bottom: 2px ridge;" />
										</div>
										<div class="form-group  col-md-2">
											<input class="form-control" name="quantityIssued" type="text"
												value="${centralStoreRequisitionDtlList.quantityIssued }" readonly="readonly"
												style="border: 0; border-bottom: 2px ridge;" />
										</div>
										<div class="form-group  col-md-2">
											<input class="form-control" name="unitCost" type="text"
												value="${centralStoreRequisitionDtlList.unitCost }" readonly="readonly"
												style="border: 0; border-bottom: 2px ridge;" />
										</div>
										<div class="form-group  col-md-2">
											<input class="form-control" name="totalCost" type="text"
												value="${centralStoreRequisitionDtlList.totalCost }" readonly="readonly"
												style="border: 0; border-bottom: 2px ridge;" />
										</div>
										<div class="form-group  col-md-2">
											<input class="form-control" name="remarks" type="text"
												value="${centralStoreRequisitionDtlList.remarks }" readonly="readonly"
												style="border: 0; border-bottom: 2px ridge;" />
										</div>
										

										<div class="form-group  col-md-1">
											<a onclick="editStoreRequisitionDtl(${centralStoreRequisitionMst.id})"  data-toggle="modal"
									data-target="#newRequisitionDtlModal"> <i
												class="form-control blue ace-icon fa fa-pencil bigger-130"></i></a>
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
				<h4 class="modal-title center">Add New Item Under Requisition No. <i>${centralStoreRequisitionMst.requisitionNo}</i></h4>
			</div>


			<div class="col-xs-12 col-md-12"  style="background-color: white;">
				<div class="col-xs-12 col-md-10 col-md-offset-1 ">
					<%--<form class="form-horizontal"> --%>
						 <form method="POST" class="form-horizontal"
				action="${pageContext.request.contextPath}/cs/store/centralStoreRequisittionDtlEdit.do">
						<div class="form-group">
							<label for="itemName" class="col-sm-2 control-label">Item
								Name</label>
							<div class="col-sm-10">
								<input class="form-control" name="itemCode" type="text"
									placeholder="BRB Cable" id="itemCode"
									style="border: 0; border-bottom: 2px ridge;" />
							</div>
						</div>

						<div class="form-group">
							<label for="descriptionMaterials" class="col-sm-2 control-label">Description Materials
								</label>
							<div class="col-sm-10">
								<input class="form-control" name="descriptionMaterials" type="text"
									placeholder="C001" id="descriptionMaterials"
									style="border: 0; border-bottom: 2px ridge;" />
							</div>
						</div>

						<div class="form-group">
							<label for="noSize" class="col-sm-2 control-label">No Or Size</label>
							<div class="col-sm-10">
								<input class="form-control" name="noSize" type="text" id="noSize"
									placeholder="Meter" style="border: 0; border-bottom: 2px ridge;" />
							</div>
						</div>

						<div class="form-group">
							<label for="quantityRequired" class="col-sm-2 control-label">Quantity Required</label>
							<div class="col-sm-10">
								<input class="form-control" name="quantityRequired" type="text"
									placeholder="1" id="quantityRequired" 
									style="border: 0; border-bottom: 2px ridge;" />
							</div>
						</div>
						
						<div class="form-group">
							<label for="quantityRequired" class="col-sm-2 control-label">Quantity Required</label>
							<div class="col-sm-10">
								<input class="form-control" name="quantityRequired" type="text"
									placeholder="1" id="quantityRequired" 
									style="border: 0; border-bottom: 2px ridge;" />
							</div>
						</div>
						
						
						
						<div class="form-group">
							<label for="quantityIssued" class="col-sm-2 control-label">Quantity Issued</label>
							<div class="col-sm-10">
								<input class="form-control" name="quantityIssued" type="text"
									placeholder="1" id="quantityIssued" 
									style="border: 0; border-bottom: 2px ridge;" />
							</div>
						</div>

						<div class="form-group">
							<label for="unitCost" class="col-sm-2 control-label">Unit Cost</label>
							<div class="col-sm-10">
								<input class="form-control" name="costCenter" type="text"
									placeholder="Finance & Accounts" id="unitCost"
									style="border: 0; border-bottom: 2px ridge;" />
						</div>			
						<div class="form-group">
							<label for="totalCost" class="col-sm-2 control-label">Total Cost</label>
							<div class="col-sm-10">
								<input class="form-control" name="totalCost" type="text"
									placeholder="1" id="totalCost" 
									style="border: 0; border-bottom: 2px ridge;" />
							</div>
						</div>
						
						<div class="form-group">
							<label for="remarks" class="col-sm-2 control-label">Remarks</label>
							<div class="col-sm-10">
								<input class="form-control" name="remarks" type="text"
									placeholder="1" id="remarks" 
									style="border: 0; border-bottom: 2px ridge;" />
							</div>
								
									
								<input name="centralStoreRequisitionId" type="text" value="${centralStoreRequisitionMst.id}" hidden="true"/>
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
				<h4 class="modal-title center">Edit Item Under SRF No. <i>${storeRequisitionMst.srfNo}</i></h4>
			</div>


			<div class="col-xs-12 col-md-12"  style="background-color: white;">
				<div class="col-xs-12 col-md-10 col-md-offset-1 ">
					<%--<form class="form-horizontal"> --%>
						 <form method="POST" class="form-horizontal"
				action="${pageContext.request.contextPath}/inventory/storeRequisittionDtlEdit.do"
				commandName="storeRequisitionMst">
						<div class="form-group">
							<label for="itemName1" class="col-sm-2 control-label">Item
								Name</label>
							<div class="col-sm-10">
								<input class="form-control" name="itemName" type="text"
									placeholder="BRB Cable" id="itemName1"
									style="border: 0; border-bottom: 2px ridge;" />
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
									placeholder="Meter" style="border: 0; border-bottom: 2px ridge;" />
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
									style="border: 0; border-bottom: 2px ridge;" />
									
								<input name="id" type="text" hidden="true" id="requisitionDtlId"/>
								<input name="requisitionId" type="text" value="${storeRequisitionMst.id}" hidden="true"/>
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
	function editStoreRequisitionDtl(id){
		
		$.ajax({
			url : '${pageContext.request.contextPath}/inventory/viewStoreRequisitionDtl.do',
			data : "{id:"+id+"}",
			contentType : "application/json",
			success : function(data) {									
				var requisitionDtl = JSON.parse(data);	
				 $("#itemName1").val(requisitionDtl.itemName);
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


<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>