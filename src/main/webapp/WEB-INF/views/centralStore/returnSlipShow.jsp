<%@include file="../common/csHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<h4>
			<a href="#" style="text-decoration: none;">Return Slip</a> / Show
		</h4>
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/cs/returnSlip/list.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Return Slip List
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
						<th class="col-md-2 col-sm-2">Return Slip No</th>
						<td class="col-md-1 col-sm-1">:</td>
						<td class="col-md-9 col-sm-9">${returnSlip.rsNo}</td>
					</tr>
					<tr>
						<th class="col-md-2 col-sm-2">Return Slip Date</th>
						<td class="col-md-1 col-sm-1">:</td>
						<td class="col-md-9 col-sm-9"><fmt:formatDate
							value="${returnSlip.rsDate}" pattern="dd-MM-yyyy" /></td>
					</tr>
					<tr>
						<th class="col-md-2 col-sm-2">Work Order No</th>
						<td class="col-md-1 col-sm-1">:</td>
						<td class="col-md-9 col-sm-9">${returnSlip.woNo}</td>
					</tr>
					<tr>
						<th class="col-md-2 col-sm-2">Work Order Date</th>
						<td class="col-md-1 col-sm-1">:</td>
						<td class="col-md-9 col-sm-9"><fmt:formatDate
							value="${returnSlip.woDate}" pattern="dd-MM-yyyy" /></td>
					</tr>
					<tr>
						<th class="col-md-2 col-sm-2">Received From</th>
						<td class="col-md-1 col-sm-1">:</td>
						<td class="col-md-9 col-sm-9">${returnSlip.receiveFrm}</td>
					</tr>
					<tr>
						<th class="col-md-2 col-sm-2">Zone/Area</th>
						<td class="col-md-1 col-sm-1">:</td>
						<td class="col-md-9 col-sm-9">${returnSlip.zone}</td>
					</tr>
					<tr>
						<th class="col-md-2 col-sm-2">Is Active ?</th>
						<td class="col-md-1 col-sm-1">:</td>
						<td class="col-md-9 col-sm-9"><c:if
								test="${returnSlip.active eq true}">Yes</c:if> <c:if
								test="${returnSlip.active eq false}">No</c:if></td>
					</tr>
					<tr>
						<th class="col-md-2 col-sm-2">Comment(s)</th>
						<td class="col-md-1 col-sm-1">:</td>
						<td class="col-md-9 col-sm-9">${returnSlip.remarks}</td>
					</tr>
					<tr>
						<th class="col-md-2 col-sm-2">Created By</th>
						<td class="col-md-1 col-sm-1">:</td>
						<td class="col-md-9 col-sm-9">${returnSlip.createdBy}</td>
					</tr>
					<tr>
						<th class="col-md-2 col-sm-2">Created Date</th>
						<td class="col-md-1 col-sm-1">:</td>
						<td class="col-md-9 col-sm-9"><fmt:formatDate
							value="${returnSlip.createdDate}" pattern="dd-MM-yyyy" /></td>
					</tr>
					<tr>
						<th class="col-md-2 col-sm-2">Modified By</th>
						<td class="col-md-1 col-sm-1">:</td>
						<td class="col-md-9 col-sm-9">${returnSlip.modifiedBy}</td>
					</tr>
					<tr>
						<th class="col-md-2 col-sm-2">Modified Date</th>
						<td class="col-md-1 col-sm-1">:</td>
						<td class="col-md-9 col-sm-9"><fmt:formatDate
							value="${returnSlip.modifiedDate}" pattern="dd-MM-yyyy" /></td>
					</tr>
				</table>
			</div>

			<div style="background: white;">
				<c:if test="${empty returnSlipDtlList}">
					<div class="col-sm-12 center">
						<p class="red">
							<i>Sorry!!! No Data Found in Database. </i>
						</p>
					</div>
				</c:if>
				<c:if test="${!empty returnSlipDtlList}">
					<table id="requisitionListTable"
						class="table table-striped table-hover table-bordered">
						<thead>
							<tr
								style="background: #579EC8; color: white; font-weight: normal;">
								<td style="">Item Code</td>
								<td style="">Description</td>
								<td style="">Returned Quantity</td>
								<td style="">Received Quantity</td>
								<td style="">Remarks</td>
								<td style="">Action</td>
							</tr>
						</thead>

						<tbody>
							<c:forEach items="${returnSlipDtlList}" var="returnSlipDtl">
								<tr>

									<td><c:out value="${returnSlipDtl.itemCode}" /></td>
									<td><c:out value="${returnSlipDtl.description}" /></td>
									<td><c:out value="${returnSlipDtl.qtyRetrun}" /></td>
									<td><c:out value="${returnSlipDtl.qtyReceived}" /></td>
									<td><c:out value="${returnSlipDtl.remarks}" /></td>

									<td>
										<div class="action-buttons">
											<a onclick="returnSlipDtlModalEdit(${returnSlipDtl.id})"
												data-toggle="modal" data-target="#returnSlipDtlModalEdit">
												<i
												class="form-control blue ace-icon fa fa-pencil bigger-130"></i>
											</a>
											<%-- <a
												href="${pageContext.request.contextPath}/cs/returnSlip/dtlEdit.do?id=${returnSlipDtl.id}">
												<i class="ace-icon fa fa-pencil bigger-130"></i>
											</a> --%> <a class="btn btn-danger btn-xs" style="border-radius: 6px;"
												href="${pageContext.request.contextPath}/cs/returnSlip/Dtldelete.do?id=${returnSlipDtl.id}&returnSlipMstId=${returnSlip.id}">
												<i class="fa fa-fw fa-trash-o"></i>&nbsp;Delete
											</a>
										</div>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</c:if>

			</div>

		</div>

		<div class="">
			<a href="#" style="text-decoration: none;" data-toggle="modal"
				data-target="#returnSlipDtlModalAdd"> <i
				class="ace-icon fa fa-plus-circle bigger-130"></i> Add more Item
			</a>
		</div>

		<hr />

		<!-- -------------------------- -->

		<div id="returnSlipDtlModalAdd" class="modal fade" role="dialog">
			<div class="modal-dialog modal-lg">

				<!-- Modal content-->
				<div class="modal-content" style="background-color: white;">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title center">
							Add New Item Under RS No. <i>${returnSlip.rsNo}</i>
						</h4>
					</div>


					<div class="col-xs-12 col-md-12" style="background-color: white;">
						<div class="col-xs-12 col-md-10 col-md-offset-1 ">
							<%--<form class="form-horizontal"> --%>
							<form method="POST" class="form-horizontal"
								action="${pageContext.request.contextPath}/cs/returnSlip/dtlSave.do">
								<div class="form-group">
									<label for="itemCodeAdd" class="col-sm-2 control-label">Item
										Name</label>
									<div class="col-sm-10">
										<select class="form-control itemName"
											onchange="itemLeaveChanged(this)" id="itemCodeAdd"
											name="itemCode" style="border: 0; border-bottom: 2px ridge;">
											<option disabled selected>-- select an Item --</option>
											<c:if test="${!empty invItemList}">
												<c:forEach items="${invItemList}" var="invItem">
													<option value="${invItem.inventoryItemItemCode}">
														<c:out value="${invItem.inventoryItemName}" /></option>
												</c:forEach>
											</c:if>
										</select>
									</div>
								</div>

								<div class="form-group">
									<label for="descriptionAdd" class="col-sm-2 control-label">Description</label>
									<div class="col-sm-10">
										<input class="form-control" name="description" type="text"
											id="descriptionAdd" placeholder="Description of Item"
											style="border: 0; border-bottom: 2px ridge;" />
									</div>
								</div>

								<div class="form-group">
									<label for="qtyRetrunAdd" class="col-sm-2 control-label">Returned
										Quantity</label>
									<div class="col-sm-10">
										<input class="form-control" name="qtyRetrun" type="text"
											id="qtyRetrunAdd" style="border: 0; border-bottom: 2px ridge;" />
									</div>
								</div>

								<div class="form-group">
									<label for="qtyReceivedAdd" class="col-sm-2 control-label">Received
										Quantity </label>
									<div class="col-sm-10">
										<input class="form-control" name="qtyReceived" type="text"
											id="qtyReceivedAdd" style="border: 0; border-bottom: 2px ridge;" />
									</div>
								</div>

								<div class="form-group">
									<label for="remarksAdd" class="col-sm-2 control-label">Remarks</label>
									<div class="col-sm-10">
										<input class="form-control" name="remarks" type="text"
											id="remarksAdd" style="border: 0; border-bottom: 2px ridge;" />
									</div>
								</div>

								<div>
									<input
										type="hidden" class="form-control" id="createdBy"
										value='<sec:authentication property="principal.username" />'
										name="createdBy" /> <input type="hidden"
										name="returnSlipMstId" value="${returnSlip.id}" />
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

		<!-- -------------------------- -->


		<div id="returnSlipDtlModalEdit" class="modal fade" role="dialog">
			<div class="modal-dialog modal-lg">

				<!-- Modal content-->
				<div class="modal-content" style="background-color: white;">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title center">
							Edit an Item Under RS No. <i>${returnSlip.rsNo}</i>
						</h4>
					</div>


					<div class="col-xs-12 col-md-12" style="background-color: white;">
						<div class="col-xs-12 col-md-10 col-md-offset-1 ">
							<%--<form class="form-horizontal"> --%>
							<form method="POST" class="form-horizontal"
								action="${pageContext.request.contextPath}/cs/returnSlip/dtlUpdate.do">
								<div class="form-group">
									<label for="itemCode" class="col-sm-2 control-label">Item
										Name</label>
									<div class="col-sm-10">
										<select class="form-control itemName"
											onchange="itemLeaveChanged(this)" id="itemCode"
											name="itemCode" style="border: 0; border-bottom: 2px ridge;">
											<option disabled selected>-- select an Item --</option>
											<c:if test="${!empty invItemList}">
												<c:forEach items="${invItemList}" var="invItem">
													<option value="${invItem.inventoryItemItemCode}">
														<c:out value="${invItem.inventoryItemName}" /></option>
												</c:forEach>
											</c:if>
										</select>
									</div>
								</div>

								<div class="form-group">
									<label for="description" class="col-sm-2 control-label">Description</label>
									<div class="col-sm-10">
										<input class="form-control" name="description" type="text"
											id="description" placeholder="Description of Item"
											style="border: 0; border-bottom: 2px ridge;" />
									</div>
								</div>

								<div class="form-group">
									<label for="qtyRetrun" class="col-sm-2 control-label">Returned
										Quantity</label>
									<div class="col-sm-10">
										<input class="form-control" name="qtyRetrun" type="text"
											id="qtyRetrun" style="border: 0; border-bottom: 2px ridge;" />
									</div>
								</div>

								<div class="form-group">
									<label for="qtyReceived" class="col-sm-2 control-label">Received
										Quantity </label>
									<div class="col-sm-10">
										<input class="form-control" name="qtyReceived" type="text"
											id="qtyReceived" style="border: 0; border-bottom: 2px ridge;" />
									</div>
								</div>

								<div class="form-group">
									<label for="remarks" class="col-sm-2 control-label">Remarks</label>
									<div class="col-sm-10">
										<input class="form-control" name="remarks" type="text"
											id="remarks" style="border: 0; border-bottom: 2px ridge;" />
									</div>
								</div>

								<div>
									<input class="form-control" name="id" type="hidden"
										id="returnSlipDtlId"
										style="border: 0; border-bottom: 2px ridge;" /> <input
										type="hidden" class="form-control" id="modifiedBy"
										value='<sec:authentication property="principal.username" />'
										name="modifiedBy" /> <input type="hidden"
										name="returnSlipMstId" id="returnSlipMstId" />
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
		<!-- <script type="text/javascript">
			$(document)
					.ready(
							function() {
								$('#requisitionListTable').DataTable();
								document
										.getElementById('requisitionListTable_length').style.display = 'none';
								document
										.getElementById('requisitionListTable_filter').style.display = 'none';

							});
		</script> -->

		<script>
	function returnSlipDtlModalEdit(id){
		
		 $.ajax({
			url : '${pageContext.request.contextPath}/cs/returnSlip/viewReturnTicketDtl.do',
			data : "{id:"+id+"}",
			contentType : "application/json",
			success : function(data) {									
				var returnSlipDtl = JSON.parse(data);	
				 $("#description").val(returnSlipDtl.description);		 
				 $("#qtyRetrun").val(returnSlipDtl.qtyRetrun);				
				 $("#qtyReceived").val(returnSlipDtl.qtyReceived);
				 $("#remarks").val(returnSlipDtl.remarks);	
				 $("#returnSlipDtlId").val(returnSlipDtl.id);
				 $("#returnSlipMstId").val(returnSlipDtl.returnSlipMst.id);
			},
			error : function(data) {
				alert("Server Error");
			},
			type : 'POST'
		}); 
	}			
</script>
	</div>
</div>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>