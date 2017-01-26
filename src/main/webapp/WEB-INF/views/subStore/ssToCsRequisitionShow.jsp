<%@include file="../common/ssHeader.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- -------------------End of Header-------------------------- -->

<style type="text/css">
	.ui-widget-overlay {
		opacity: .6 !important;
	}
</style>

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/ss/requisitionList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Item
				Requisition List
			</a>
		</div>
		<h2 class="center blue" style="margin-top: 0px;">Item Requisition</h2>
		<h4 class="center blue" style="margin-top: 0px;">Sub Store</h4>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px;">
		<!-- --------------------- -->
		<div class="oe_title">

			<table class="col-sm-12 table">
				<tr class="">
					<td class="success">Requisition No: <input type="hidden"
						value="${centralStoreRequisitionMst.requisitionNo}" id="requisitionNo" />
						<input type="hidden" value="${returnStateCode}"
						id="returnStateCode" /><input
						type="hidden" value="${centralStoreRequisitionMst.uuid}" id="uuid" />
						<input type="hidden" name="contextPath" id="contextPath"
						value="${pageContext.request.contextPath}" />
					</td>

					<td class="info">${centralStoreRequisitionMst.requisitionNo}</td>
					<td class="success">Created By:</td>
					<td class="info">${centralStoreRequisitionMst.createdBy}</td>
					<td class="success">Created Date:</td>
					<td class="info"><fmt:formatDate
						value="${centralStoreRequisitionMst.createdDate}" pattern="dd-MM-yyyy hh:mm:ss a" /></td>
				</tr>
				<tr class="">
					<td class="success">Modified By:</td>
					<td class="info">${centralStoreRequisitionMst.modifiedBy}</td>
					<td class="success">Modified Date:</td>
					<td class="info"><fmt:formatDate
						value="${centralStoreRequisitionMst.modifiedDate}" pattern="dd-MM-yyyy hh:mm:ss a" /></td>
					<td class="success">Present Status:</td>
					<td class="info"><strong> ${currentStatus} </strong></td>
				</tr>

			</table>
		</div>

		<c:if test="${!empty itemRcvApproveHistoryList}">
			<button data-toggle="collapse" data-target="#demo">
				<span class="glyphicon glyphicon-collapse-down"></span>
			</button>
		</c:if>

		<div id="demo" class="collapse">
			<c:if test="${!empty itemRcvApproveHistoryList}">
				<!-- <div class="" style="font-weight: bold;">Approval History</div> -->

				<c:forEach items="${itemRcvApproveHistoryList}"
					var="itemRcvApproveHistory">
					<table class="col-sm-12 table">

						<tr class="">
							<td class="warning col-sm-3"
								style="font-weight: bold; font-size: 1.5em; font-style: italic; text-transform: capitalize">${fn:toLowerCase(itemRcvApproveHistory.approvalHeader)}</td>
							<td class="success col-sm-2" style="text-transform: capitalize">${fn:toLowerCase(itemRcvApproveHistory.stateName)}
								By:</td>
							<td class="success col-sm-2 text-left">
								${itemRcvApproveHistory.cEmpFullName} <c:if
									test="${!empty itemRcvApproveHistory.cDesignation}">
									, ${itemRcvApproveHistory.cDesignation} 
								</c:if> <c:if test="${!empty itemRcvApproveHistory.cEmpId}">
									( ${itemRcvApproveHistory.cEmpId} )
								</c:if>
							</td>
							<td class="info col-sm-2 text-left"
								style="text-transform: capitalize">${fn:toLowerCase(itemRcvApproveHistory.stateName)}
								Date:</td>
							<td class="info col-sm-3"><fmt:formatDate
									value="${itemRcvApproveHistory.createdDate}"
									pattern="dd-MM-yyyy hh:mm:ss a" /></td>
						</tr>
						<c:if test="${!empty itemRcvApproveHistory.justification}">
							<tr class="">
								<td></td>
								<td class="danger col-sm-1">Comment(s) :</td>
								<td class="danger col-sm-11" colspan="3"
									title="${itemRcvApproveHistory.justification}">${itemRcvApproveHistory.justification}</td>
							</tr>
						</c:if>
					</table>
				</c:forEach>
			</c:if>
		</div>
	
		
		<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">
		<input type="hidden" id="khathId" value="${centralStoreRequisitionMst.khathId}">
		
		
		<div style="background: white;">
			<c:if test="${empty centralStoreRequisitionDtlList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty centralStoreRequisitionDtlList}">
				<table id="requisitionListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Item Code</td>
							<td style="">Description</td>
							<td style="">Unit</td>
							<td style="">Required Quantity</td>
							<td style="">Remarks</td>
							<td style="">Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${centralStoreRequisitionDtlList}" var="centralStoreRequisitionDtl">
							<tr>

								<td><c:out value="${centralStoreRequisitionDtl.itemCode}" /></td>
								<td><c:out value="${centralStoreRequisitionDtl.itemName}" /></td>
								<td><c:out value="${centralStoreRequisitionDtl.uom}" /></td>
								<td><c:out value="${centralStoreRequisitionDtl.quantityRequired}" /></td>
								<td><c:out value="${centralStoreRequisitionDtl.remarks}" /></td>
								<td class="col-xs-2 center" >
									<div class="action-buttons">
										<%-- <a href="javascript:void(0)" data-toggle="modal" data-target="#editModal"
											class="editModal btn btn-xs btn-primary" style="border-radius: 6px;" onclick="editItem(${centralStoreRequisitionDtl.id})">
											<i class="fa fa-fw fa-edit"></i>&nbsp;Edit
										</a> --%>
										
										<a href="javascript:void(0)" class="btn btn-xs btn-danger"
											style="border-radius: 6px;" onclick="deleteItemById(${centralStoreRequisitionDtl.id}, '${centralStoreRequisitionMst.requisitionNo}')">
											<i class="fa fa-fw fa-trash-o"></i>&nbsp;Delete
										</a>
									</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
			
			<div class="col-xs-12 align-right">
				<button class="btn btn-xs btn-success"
					style="border-radius: 6px; margin-right: 10px;"
					onclick="openDialoge()">
					<i class="fa fa-fw fa-plus"></i>&nbsp; Add More Item
				</button>
			</div>
			
		</div>
		<br><br>
		
		<!-- <div class="text-center"> -->
		<div class="row">
			<label class="col-xs-2"> <strong>Comment(s):&nbsp;<span class='red'>*</span></strong></label>
			<div class="col-xs-10">
				<textarea required class="form-control" rows="2" cols="" id="justification"></textarea>
				<strong class="justification text-danger hide">This field is required</strong>
			</div>
		</div>
		<div class="col-xs-12">
			<hr />
		</div>
		<div class="row">
			<div class="col-md-4 col-sm-4 text-left">
				<c:if test="${!empty backManRcvProcs}">
					<!-- <button onclick="backToLower()" class="btn btn-warning"
						style="text-decoration: none; border-radius: 6px;">< Back
						To</button> -->
					<div class="dropup pull-left">
						<button class="btn btn-warning dropdown-toggle" type="button"
							style="border-radius: 6px;" data-toggle="dropdown"
							aria-haspopup="true" aria-expanded="false">
							Back to <span class="caret"></span>
						</button>
						<ul class="dropdown-menu" aria-labelledby="dropdownMenu2">

							<c:forEach items="${backManRcvProcs}" var="backMan">
								<li class=""><a
									href="Javascript:backToLower(${backMan.stateCode})">For
										${backMan.buttonName}</a></li>
							</c:forEach>
							<!-- <li class=""><a href="Javascript:forwardToUpper()">Action</a></li>
						<li class=""><a href="Javascript:forwardToUpper()">Another
								action</a></li> -->
						</ul>
					</div>
				</c:if>
			</div>
			<div class="col-md-4 col-sm-4 text-center">
				<%-- <a class="btn btn-primary"
					href="${pageContext.request.contextPath}/cs/itemRecieved/itemReceivedSubmitApproved.do?receivedReportNo=${centralStoreRequisitionMst.rrNo}"
					style="text-decoration: none; border-radius: 6px;">
					${buttonValue} </a> --%>
				<a class="btn btn-primary" href="Javascript:approveing()"
					style="text-decoration: none; border-radius: 6px;">
					${buttonValue} </a>
			</div>
			<div class="col-md-4 col-sm-4 text-right">

				<c:if test="${!empty nextManRcvProcs}">

					<div class="dropup pull-right">
						<button class="btn btn-danger dropdown-toggle" type="button"
							style="border-radius: 6px;" data-toggle="dropdown"
							aria-haspopup="true" aria-expanded="false">
							Send to <span class="caret"></span>
						</button>
						<ul class="dropdown-menu" aria-labelledby="dropdownMenu2">

							<c:forEach items="${nextManRcvProcs}" var="nextMan">
								<li class=""><a
									href="Javascript:forwardToUpper(${nextMan.stateCode})">For
										${nextMan.buttonName}</a></li>
							</c:forEach>
							<!-- <li class=""><a href="Javascript:forwardToUpper()">Action</a></li>
						<li class=""><a href="Javascript:forwardToUpper()">Another
								action</a></li> -->
						</ul>
					</div>
				</c:if>
			</div>
		</div>
		<hr />

		<div id="editModal" class="modal fade editModal" role="dialog">
			<div class="modal-dialog modal-lg">

				<!-- Modal content-->
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title center">Edit an Item</h4>
					</div>

					<!-- --------------------- -->
					<div class="row"
						style="background-color: white; padding: 5px; padding-left: 10px; margin-top: 5px; margin-bottom: 5px; margin-left: 15px; margin-right: 5px;">

						<form method="POST"
							action="${pageContext.request.contextPath}/ss/updateItem.do">
							<div class="oe_title">

								<input class="o_form_input o_form_field o_form_required"
									id="modal_itemId" placeholder="Item Code" type="text" name="ItemCode"
									readonly="readonly"
									style="border: 0; border-bottom: 2px ridge; width: 100%; font-size: 1em" />

								<input class="o_form_input o_form_field o_form_required" name="quantityRequired"
									id="modal_quantityRequired" placeholder="Required Quantity"
									type="text"
									style="border: 0; border-bottom: 2px ridge; width: 100%; font-size: 1em" />

								<input type="text" hidden="true" id="modal_id" name="id" name="id" />
								<input type="text" hidden="true" id="modal_requisitionId" name="id" name="centralStoreRequisitionId" />
								<input type="text" hidden="true" id="requisitionTo" name="id" name="requisitionTo" />
								<hr />
								<div class="text-center">
									<input class="btn btn-success" type="submit" value="Update"
										style="border-radius: 6px" />
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		
		<div id="myDialogeBox" class="hide">
			<div class="col-md-12 col-sm-12">
				<div class="alert alert-success hide">
					<!-- <a href="javascript:void(0)" class="close" data-dismiss="alert" aria-label="close"><strong style="font-size:1.3em;">&times;</strong></a>  -->
					<strong>Success!</strong> Allocation is available.
				</div>
				<div class="alert alert-danger hide">
					<!--<a href="#" class="close" data-dismiss="alert" aria-label="close"><strong style="font-size:1.3em;">&times;</strong></a>  -->
					<strong>Fail!</strong>Allocation Limit is finished.
				</div>
				<form
					action="${pageContext.request.contextPath}/ss/storeRequisitionSaveMultipleItem.do"
					id="saveLSStoreRequisition" method="post">
					<!-- <input type="hidden" id="requisitionTo" name="requisitionTo" value="ss"> -->
					<input type="hidden" id="requisitionNo" name="requisitionNo" value="${centralStoreRequisitionMst.requisitionNo}">
					<input name="id" value="${centralStoreRequisitionMst.id}" type="hidden" /> <input
						type="hidden" name="" value="" />
					<div class="form-group" style="margin-top: 1em;">
						<p class="col-sm-12 btn btn-primary btn-sm">Add New Item For
							Store Requisition</p>
						<div class="col-xs-12 table-responsive">
							<div class="table">
								<div> <!-- style="width: 1500px;" -->
									<hr />
									<div class="col-xs-12">
										<div class="form-group col-sm-2 col-xs-12">
											<b>Category</b>
										</div>
										
										<!-- <div class="form-group col-sm-1 col-xs-12">
											<b>Ledger Name</b>
										</div> -->
										<div class="form-group col-sm-2 col-xs-12">
											<b>Item Name</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Item Code</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Unit</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Reserve Qty</b>
										</div>

										<div class="form-group col-sm-1 col-xs-12">
											<b>Required Qty</b>
										</div>

										<!-- <div class="form-group col-sm-1 col-xs-12">
											<b>Unit Cost</b>
										</div>
										<div class="form-group col-sm-1 col-xs-12">
											<b>Total Cost</b>
										</div> -->
										<div class="form-group col-sm-2 col-xs-12">
											<b>Remarks</b>
										</div>
									</div>

									<div class="col-xs-12">
										<div class="row">
											<div class="control-group" id="fields">
												<div class="controls">
													<div class="aaa">
														<div class="col-xs-12 entry" id="myArea0">
															<div class="row">
																<div class="form-group col-sm-2 col-xs-12">																	
																	<select class="form-control category" id="category0" name="category"
																		onchange="categoryLeaveChange(this)"
																		style="border: 0; border-bottom: 2px ridge;">
																		<option disabled selected>Category</option>
																		
																		<c:if test="${!empty categoryList}">
																			<c:forEach items="${categoryList}" var="category">
																				<option value="${category.categoryId}">${category.categoryId} - ${category.categoryName}</option>
																			</c:forEach>
																		</c:if>
																	</select>
																</div>
																<%-- <div class="form-group col-sm-1 col-xs-12">
																	<select class="form-control ledgerName" name="ledgerName"
																		id="ledgerName0" style="border: 0; border-bottom: 2px ridge;">
																		<!-- <option disabled selected>Select One</option> -->
																		<c:if test="${!empty ledgerBooks}">
																			<c:forEach items="${ledgerBooks}" var="ledgerBook">
																				<option value="${ledgerBook}">
																					<c:out value="${ledgerBook}" /></option>
																			</c:forEach>
																		</c:if>
																	</select>
																</div> --%>

																<div class="form-group col-sm-2 col-xs-12">	
																	<input type="hidden" name="itemName"
																		class="description" />																
																	<select
																		class="form-control itemName" name="iName" id="itemName0"
																		onchange="itemLeaveChange(this)"
																		style="border: 0; border-bottom: 2px ridge;">
																		<option disabled selected>Item Name</option>																		
																	</select>
																</div>
																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control itemCode" name="itemCode" id="itemCode0"
																		type="text" placeholder="itemCode" readonly="readonly"
																		style="border: 0; border-bottom: 2px ridge;">
																</div>
																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control uom" name="uom" type="text"
																		placeholder="Unit" readonly="readonly"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control currentStock" readonly="readonly" 
																		name="currentStock" id="currentStock0" type="number"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																	<!-- onkeyup="reqQtyNotGreaterThenCurrentStock(this)" -->
																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control quantityRequired" step="0.001" min="0.1"
																		onkeyup="reqQtyNotGreaterThenCurrentStock(this)"
																		name="quantityRequired" id="quantityRequired0" type="number"
																		style="border: 0; border-bottom: 2px ridge;" required>
																</div>

																
																<!-- <div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control" name="unitCost" type="number" id="unitCost0"
																		placeholder="Unit Cost" onkeyup="setTotalCost(this)" value="0"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-1 col-xs-12">
																	<input class="form-control" name="totalCost" readonly="readonly" 
																		type="text" placeholder="Total Cost" id="totalCost0"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div> -->
																
																<div class="form-group col-sm-2 col-xs-12">
																	<input class="form-control" name="remarks" id="remarks0" type="text"
																		placeholder="Remarks"  value=" "
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-sm-2 col-xs-3">
																	<button class="btn btn-success btn-add" type="button">
																		<span class="glyphicon glyphicon-plus"></span>
																	</button>
																	<button class="btn btn-danger btn-remove" type="button">
																		<span class="glyphicon glyphicon-minus"></span>
																	</button>

																</div>
															</div>
														</div>
													</div>
													<!-- ---------------------- -->

												</div>
											</div>
										</div>
									</div>

									<hr />

								</div>
							</div>
						</div>

					</div>
				</form>
			</div>
		</div>

		<!-- -------------------------- -->
		<script src="${pageContext.request.contextPath}/resources/assets/js/subStore/ssToCsRequisitionShow.js"></script>
		<script>
		
		
	function forwardToUpper(stateCode){
		if( $('#justification').val() == null || $.trim( $('#justification').val() ) == '' ) {
			$('.justification').removeClass('hide');
			$('#justification').focus();
			return;
		} else {
			var justification = $('#justification').val();
			var requisitionNo = $('#requisitionNo').val();
			//window.location = "${pageContext.request.contextPath}/cs/itemRecieved/itemReceivedForwordTo.do?receivedReportNo="+param1+"$justification="+justification;
			window.location = "${pageContext.request.contextPath}/ss/requisition/sendTo.do?requisitionNo="+requisitionNo+"&justification="+justification+"&stateCode="+stateCode;
		}
		
	}
	
	function backToLower(stateCode){
		if( $('#justification').val() == null || $.trim( $('#justification').val() ) == '' ) {
			$('.justification').removeClass('hide');
			$('#justification').focus();
			return;
		} else {
			var justification = $('#justification').val();
			var requisitionNo = $('#requisitionNo').val();		
			//window.location = "${pageContext.request.contextPath}/cs/itemRecieved/itemReceivedForwordTo.do?receivedReportNo="+param1+"$justification="+justification;
			window.location = "${pageContext.request.contextPath}/ss/requisition/backTo.do?requisitionNo="+requisitionNo+"&justification="+justification+"&stateCode="+stateCode;
		}
		
	}
	
	function approveing(){
		if( $('#justification').val() == null || $.trim( $('#justification').val() ) == '' ) {
			$('.justification').removeClass('hide');
			$('#justification').focus();
			return;
		} else {
			var justification = $('#justification').val();
			var requisitionNo = $('#requisitionNo').val();		

			var returnStateCode = $('#returnStateCode').val();
			
			window.location = "${pageContext.request.contextPath}/ss/itemRequisitionSubmitApproved.do?requisitionNo="+requisitionNo+"&justification="+justification+"&return_state="+returnStateCode;
		}
		
	}
</script>
	</div>
</div>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>