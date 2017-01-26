<%@include file="../common/wsHeader.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/ws/requisitionList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Item
				Requisition List
			</a>
			
		</div>
		<h2 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Item Requisition</h2>
		
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin: 10px; ">
		<!-- --------------------- -->
		<div class="oe_title">

			<table class="col-sm-12 table">
				<tr class="">
					<td class="success">Requisition No: <input type="hidden"
						value="${centralStoreRequisitionMst.requisitionNo}" id="requisitionNo" />
						<input type="hidden" value="${returnStateCode}"
						id="returnStateCode" />
					</td>

					<td class="info">${centralStoreRequisitionMst.requisitionNo}</td>
					<td class="success">Requisition By:</td>
					<td class="info">${centralStoreRequisitionMst.deptName}</td>
					<td class="success">Requisition Date:</td>
					<td class="info"><fmt:formatDate
						value="${centralStoreRequisitionMst.createdDate}" pattern="dd-MM-yyyy hh:mm:ss a" /></td>
				</tr>
				<tr class="">
					<td class="success">Contract No:</td>
					<td class="info">${centralStoreRequisitionMst.workOrderNumber}</td>
					<td class="success">Requisition To:</td>
					<td class="info">Central Store</td>
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
								value="${itemRcvApproveHistory.createdDate}" pattern="dd-MM-yyyy hh:mm:ss a" /></td>
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
							<td style="">Issued Quantity</td>
<!-- 							<td style="">Unit Cost</td> -->
<!-- 							<td style="">Total Cost</td> -->
							<td style="">Remarks</td>
							<td style="">Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${centralStoreRequisitionDtlList}" var="subStoreRequisitionDtl">
							<tr>

								<td><c:out value="${subStoreRequisitionDtl.itemCode}" /></td>
								<td><c:out value="${subStoreRequisitionDtl.itemName}" /></td>
								<td><c:out value="${subStoreRequisitionDtl.uom}" /></td>
								<td><c:out value="${subStoreRequisitionDtl.quantityRequired}" /></td>
								<td><c:out value="${subStoreRequisitionDtl.quantityIssued}" /></td>
<%-- 								<td><c:out value="${subStoreRequisitionDtl.unitCost}" /></td> --%>
<%-- 								<td><c:out value="${subStoreRequisitionDtl.totalCost}" /></td> --%>
								<td><c:out value="${subStoreRequisitionDtl.remarks}" /></td>
								<td>
									<%-- <div class="action-buttons">
										<a href="#" data-toggle="modal" data-target="#editModal"
											class="editModal" onclick="editItem(${subStoreRequisitionDtl.id})">
											<i class="ace-icon fa fa-pencil bigger-130"></i>
										</a>
									</div> --%>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>

		</div>



		<!-- <div class="text-center"> -->
		<div class="row">
			<label class="col-xs-2"> <strong>Comment(s) : </strong></label>
			<div class="col-xs-10">
				<textarea class="form-control" rows="2" cols="" id="justification"></textarea>
			</div>
		</div>
		<div class="col-xs-12">
			<hr />
		</div>
		<div class="row">
			<div class="col-md-4 col-sm-4 text-left">
				<c:if test="${!empty backManRcvProcs}">
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
						
						</ul>
					</div>
				</c:if>
			</div>
			<div class="col-md-4 col-sm-4 text-center">
				
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
							action="${pageContext.request.contextPath}/ws/updateItem.do">
							<div class="oe_title">

								<input class="o_form_input o_form_field o_form_required"
									id="modal_itemId" placeholder="Item Code" type="text" name="ItemCode"
									readonly="readonly"
									style="border: 0; border-bottom: 2px ridge; width: 100%; font-size: 1em" />

								<input class="o_form_input o_form_field o_form_required" name="quantityRequired"
									id="modal_quantityRequired" placeholder="Required Quantity"
									type="text"
									style="border: 0; border-bottom: 2px ridge; width: 100%; font-size: 1em" />

								<input type="text" hidden="true" id="modal_id" name="id" />
								<input type="text" hidden="true" id="modal_requisitionId" name="centralStoreRequisitionId" />
								<!-- <input type="text" id="requisitionTo" name="requisitionTo" /> -->
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

		<!-- -------------------------- -->

		<script>
		function editItem(id){	
			
			$.ajax({								
				url : '${pageContext.request.contextPath}/ws/itemEdit.do',
				data : "{id:"+id+"}",
				contentType : "application/json",
				success : function(data) {									
					var item = JSON.parse(data);
					//alert(item.centralStoreRequisitionMst.id);
						$("#modal_itemId").val(item.itemCode);
						$("#modal_quantityRequired").val(item.quantityRequired);
						$("#modal_id").val(item.id);					
						$("#modal_requisitionId").val(item.centralStoreRequisitionMst.id);
						//$("#modal_requisitionTo").val(item.requisitionTo);					
						
				},
				error : function(data) {
					alert("Server Error");
				},
				type : 'POST'
			});
		}
	</script>

		<script>
		function receivedQtyToRemainQty(element) {	
			
			var expected = parseInt($('#modal_expectedQuantity').val().trim(), 10);

			var received = parseInt($(element).val().trim(), 10);

			if (received > expected) {
				$(element).val($('#modal_expectedQuantity').val());
			}

			$('#modal_reminingQuantity').val(
					$('#modal_expectedQuantity').val()
							- $('#modal_receivedQuantity').val());

		}
		
	function forwardToUpper(stateCode){
		var justification = $('#justification').val();
		var requisitionNo = $('#requisitionNo').val();
		//window.location = "${pageContext.request.contextPath}/cs/itemRecieved/itemReceivedForwordTo.do?receivedReportNo="+param1+"$justification="+justification;
		window.location = "${pageContext.request.contextPath}/ws/requisition/sendTo.do?requisitionNo="+requisitionNo+"&justification="+justification+"&stateCode="+stateCode;
	}
	
	function backToLower(stateCode){
		var justification = $('#justification').val();
		var requisitionNo = $('#requisitionNo').val();		
		//window.location = "${pageContext.request.contextPath}/cs/itemRecieved/itemReceivedForwordTo.do?receivedReportNo="+param1+"$justification="+justification;
		window.location = "${pageContext.request.contextPath}/ws/requisition/backTo.do?requisitionNo="+requisitionNo+"&justification="+justification+"&stateCode="+stateCode;
	}
	
	function approveing(){		
		var justification = $('#justification').val();
		var requisitionNo = $('#requisitionNo').val();		

		var returnStateCode = $('#returnStateCode').val();
		
		window.location = "${pageContext.request.contextPath}/ws/itemRequisitionSubmitApproved.do?requisitionNo="+requisitionNo+"&justification="+justification+"&return_state="+returnStateCode;
	}
</script>
	</div>
</div>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>