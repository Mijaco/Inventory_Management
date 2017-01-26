<%@include file="../common/csHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<h4>
			<a href="#" style="text-decoration: none;">Store Requisition</a> /
			Show
		</h4>
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/cs/store/listCsReceive.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>Central  Store
				Receive List
			</a> <a
				href="${pageContext.request.contextPath}/cs/store/centralStoreReceiveEdit.do?id=${centralStoreReceiveMst.id}"
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
						<label for="rrNo" class="col-sm-4 control-label"> R.R. no</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="rrNo"
								placeholder="Desco/RRno/001" name = "rrNo" value="${centralStoreReceiveMst.rrNo}"
								style="border: 0; border-bottom: 2px ridge;"/>

						</div>
					</div>
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="storeSection" class="col-sm-4 control-label">Store
							Section</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="storeSection" value="${centralStoreReceiveMst.storeSection}"
								placeholder="section" style="border: 0; border-bottom: 2px ridge;"
								name="storeSection" />
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="supplier" class="col-sm-4 control-label">Suppler</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="supplier" value="${centralStoreReceiveMst.supplier}"
								placeholder="supplier name" style="border: 0; border-bottom: 2px ridge;"
								name="supplier">
						</div>
					</div>

					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="workOrder" class="col-sm-4 control-label">Work order/contract no</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="workOrder" value="${centralStoreReceiveMst.workOrder}"
								placeholder="work order"
								style="border: 0; border-bottom: 2px ridge;" name="workOrder">
						</div>
					</div>

					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="woNo" class="col-sm-4 control-label">Work
							Order No/Contract Date</label>
						<div class="col-sm-8">
							<input type="date" class="form-control" id="workDate" value='<fmt:formatDate
	value="${centralStoreReceiveMst.workDate}" pattern="dd-MM-yyyy" />'
								placeholder="yyyy-dd-MM"
								style="border: 0; border-bottom: 2px ridge;" name="workDate">
						</div>
					</div>

				</div>

				<div class="col-md-6 col-sm-6">

					<div class="form-group">
						<label for="dateOfDelivery" class="col-sm-4 col-md-4 control-label">Date of delivery</label>
						<div class="col-sm-8 col-md-8">
							<input type="date" class="form-control" id="dateOfDelivery" value='<fmt:formatDate
	value="${centralStoreReceiveMst.dateOfDelivery}" pattern="dd-MM-yyyy" />'
								style="border: 0; border-bottom: 2px ridge;" name="dateOfDelivery" />
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="challanNo" class="col-sm-4 col-md-4 control-label">Challan No</label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control" id="challanNo" value="${centralStoreReceiveMst.challanNo}"
								style="border: 0; border-bottom: 2px ridge;" name="challanNo"
								placeholder="DESCO/2015-2016/Ch001" />
						</div>
					</div>
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="returnFor" class="col-sm-4 col-md-4 control-label">Landing Date</label>
						<div class="col-sm-8 col-md-8">
							<input type="date" class="form-control" id="invoiceDate" value='<fmt:formatDate
	value="${centralStoreReceiveMst.landingDate}" pattern="dd-MM-yyyy" />'
								style="border: 0; border-bottom: 2px ridge;"
								name="invoiceDate" />
						</div>
					</div>
					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="billOfLanding" class="col-sm-4 col-md-4 control-label">Bill of landing</label>
						<div class="col-sm-8 col-md-8">
							<input type="date" class="form-control" id="billOfLanding" value='<fmt:formatDate
	value="${centralStoreReceiveMst.billOfLanding}" pattern="dd-MM-yyyy" />'
								style="border: 0; border-bottom: 2px ridge;" name="billOfLanding" />
						</div>
					</div>

					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="landingDate" class="col-sm-4 col-md-4 control-label">Landing Date</label>
						<div class="col-sm-8 col-md-8">
							<input type="date" class="form-control" id="landingDate" value='<fmt:formatDate
	value="${centralStoreReceiveMst.landingDate}" pattern="dd-MM-yyyy" />'
								style="border: 0; border-bottom: 2px ridge;" name="landingDate" />
						</div>
					</div>

				</div>

			</div>


			<c:if test="${!empty centralStoreReceiveDtlList}">
				<div class="col-md-12">
					<div class="form-group" style="margin-top: 1em;">
						<label for="fax" class="col-sm-4 control-label"
							style="font-style: italic; font-weight: bold;">Requested
							Item(s):</label>
						<div class="col-sm-12">
							<hr />

							<c:forEach items="${centralStoreReceiveDtlList}"
								var="centralStoreReceiveDtlList">
								<div class="row">
									<div class="center">
										<div class="form-group col-md-4">
											<input class="form-control" name="itemName" type="text"
												value="${centralStoreReceiveDtlList.partNo}"
												readonly="readonly"
												style="border: 0; border-bottom: 2px ridge;" />
										</div>
										<div class="form-group col-md-2">
											<input class="form-control" name="description" type="text"
												value="${centralStoreReceiveDtlList.description }"
												readonly="readonly"
												style="border: 0; border-bottom: 2px ridge;" />
										</div>
										<div class="form-group col-md-1">
											<input class="form-control" name="unit" type="text"
												value="${centralStoreReceiveDtlList.unit }"
												readonly="readonly"
												style="border: 0; border-bottom: 2px ridge;" />
										</div>
										<div class="form-group col-md-2">
											<input class="form-control" name="quantity" type="text"
												value="${centralStoreReceiveDtlList.quantity }"
												readonly="readonly"
												style="border: 0; border-bottom: 2px ridge;" />
										</div>
										<div class="form-group  col-md-2">
											<input class="form-control" name="costCenter" type="text"
												value="${centralStoreReceiveDtlList.cost }"
												readonly="readonly"
												style="border: 0; border-bottom: 2px ridge;" />
										</div>
										<div class="form-group  col-md-2">
											<input class="form-control" name="costCenter" type="text"
												value="${centralStoreReceiveDtlList.number}"
												readonly="readonly"
												style="border: 0; border-bottom: 2px ridge;" />
										</div>
										<div class="form-group  col-md-2">
											<input class="form-control" name="costCenter" type="text"
												value="${centralStoreReceiveDtlList.remarks}"
												readonly="readonly"
												style="border: 0; border-bottom: 2px ridge;" />
										</div>

										<div class="form-group  col-md-1">
											<a
												onclick="editStoreRequisitionDtl(${centralStoreReceiveDtlList.id})"
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
									data-target="#editRequisitionDtlModal"> <i
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
				<h4 class="modal-title center">
					Add New Item Under RR No. <i>${centralStoreReceiveMst.rrNo}</i>
				</h4>
			</div>


			<div class="col-xs-12 col-md-12" style="background-color: white;">
				<div class="col-xs-12 col-md-10 col-md-offset-1 ">
					<%--<form class="form-horizontal"> --%>
					<form method="POST" class="form-horizontal"
						action="${pageContext.request.contextPath}/inventory/storeRequisitionDtlSave.do">
						<div class="form-group">
							<label for="partNo" class="col-sm-2 control-label">Item
								Name</label>
							<div class="col-sm-10">
								<input class="form-control" name="partNo" type="text"
									placeholder="BRB Cable" id="partNo"
									style="border: 0; border-bottom: 2px ridge;" />
							</div>
						</div>

						<div class="form-group">
							<label for="Description" class="col-sm-2 control-label">Description</label>
							<div class="col-sm-10">
								<input class="form-control" name="description" type="text"
									placeholder="" id="description"
									style="border: 0; border-bottom: 2px ridge;" />
							</div>
						</div>

						<div class="form-group">
							<label for="unit" class="col-sm-2 control-label">Unit</label>
							<div class="col-sm-10">
								<input class="form-control" name="unit" type="text"
									placeholder="Pieces" id="unit"
									style="border: 0; border-bottom: 2px ridge;" />
							</div>
						</div>

						<div class="form-group">
							<label for="quantity" class="col-sm-2 control-label">Quantity
								</label>
							<div class="col-sm-10">
								<input class="form-control" name="quantity" type="text"
									placeholder="C001" id="quantity"
									style="border: 0; border-bottom: 2px ridge;" />
							</div>
						</div>

						<div class="form-group">
							<label for="cost" class="col-sm-2 control-label">Cost</label>
							<div class="col-sm-10">
								<input class="form-control" name="cost" type="text" id="uom"
									placeholder="100.0"
									style="border: 0; border-bottom: 2px ridge;" />
							</div>
						</div>

						<div class="form-group">
							<label for="number" class="col-sm-2 control-label">Number</label>
							<div class="col-sm-10">
								<input class="form-control" name="number" type="text"
									placeholder="1" id="number"
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
									name="centralStoreReceiveId" type="text"
									value="${centralStoreReceiveMst.id}" hidden="true" />
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
					Edit Receiving Form Requisition No. <i>${centralStoreReceiveMst.rrNo}</i>
				</h4>
			</div>


			<div class="col-xs-12 col-md-12" style="background-color: white;">
				<div class="col-xs-12 col-md-10 col-md-offset-1 ">
					<%--<form class="form-horizontal"> --%>
					<form method="POST" class="form-horizontal"
						action="${pageContext.request.contextPath}/cs/store/centralStoreReceiveDtlEdit.do"
						commandName="centralStoreReceiveMst">
						<div class="form-group">
							<label for="partNo1" class="col-sm-2 control-label">Item
								Name</label>
							<div class="col-sm-10">
								<input class="form-control" name="partNo" type="text"
									placeholder="BRB Cable" id="partNo1"
									style="border: 0; border-bottom: 2px ridge;" />
							</div>
						</div>

						<div class="form-group">
							<label for="description1" class="col-sm-2 control-label">Item
								Code</label>
							<div class="col-sm-10">
								<input class="form-control" name="description" type="text"
									placeholder="C001" id="description1"
									style="border: 0; border-bottom: 2px ridge;" />
							</div>
						</div>

						<div class="form-group">
							<label for="unit1" class="col-sm-2 control-label">Unit</label>
							<div class="col-sm-10">
								<input class="form-control" name="unit" type="text" id="unit1"
									placeholder="Meter"
									style="border: 0; border-bottom: 2px ridge;" />
							</div>
						</div>

						<div class="form-group">
							<label for="quantity1" class="col-sm-2 control-label">Quantity</label>
							<div class="col-sm-10">
								<input class="form-control" name="quantity1" type="text"
									placeholder="1" id="quantity1"
									style="border: 0; border-bottom: 2px ridge;" />
							</div>
						</div>
						
						<div class="form-group">
							<label for="Cost1" class="col-sm-2 control-label">Cost</label>
							<div class="col-sm-10">
								<input class="form-control" name="Cost1" type="text"
									placeholder="1" id="Cost1"
									style="border: 0; border-bottom: 2px ridge;" />
							</div>
						</div>
						
						<div class="form-group">
							<label for="number1" class="col-sm-2 control-label">Number</label>
							<div class="col-sm-10">
								<input class="form-control" name="number" type="text" 
									placeholder="1" id="number1"
									style="border: 0; border-bottom: 2px ridge;" />
							</div>
						</div>
						
						<div class="form-group">
									<input name="id" type="text" hidden="true" id="centralStoreReceiveId" value="0"/> <input
									name="centralStoreReceiveId" type="text"
									value="${centralStoreReceiveMst.id}" hidden="true" />
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
				 $("#itemName1").val(centralStoreReceiveDtlList.itemName);
				 $("#itemCode1").val(centralStoreReceiveDtlList.itemCode);
				 $("#uom1").val(centralStoreReceiveDtlList.uom);
				 $("#quantity1").val(centralStoreReceiveDtlList.quantity);
				 $("#costCenter1").val(centralStoreReceiveDtlList.costCenter); 	
				 $("#requisitionDtlId").val(centralStoreReceiveDtlList.id);
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