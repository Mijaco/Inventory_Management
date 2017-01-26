<%-- <%@include file="../common/wsContractorHeader.jsp"%> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="${pageHeader}" />

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<!-- <h4>
			<a href="#" style="text-decoration: none;">Item Received</a> / Show
		</h4> -->
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/inventory/wsInventoryReportList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Inventory Report List
			</a>
			<!-- 	<button accesskey="D" class="btn btn-info btn-sm" type="button">
				Discard</button> -->
		</div>
		<h1 class="center blue" style="margin-top: 0px;">Inventory Report </h1>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px; margin-left: 90px; margin-right: 90px;">
		<!-- --------------------- -->
		<div class="oe_title">

			<table class="col-sm-12 table">
				<tr class="">
					<td class="success">Inventory Report No: <input type="hidden"
						value="${wsInventoryReportMst.wsInventoryNo}" id="inventoryNo" />
						<input type="hidden" value="${returnStateCode}"
						id="returnStateCode" />
						<input type="hidden" name="contextPath" id="contextPath"
						value="${pageContext.request.contextPath}" />
					</td>

					<td class="info">${wsInventoryReportMst.wsInventoryNo}</td>
					<td class="success">Created By:</td>
					<td class="info">${wsInventoryReportMst.createdBy}</td>
					<td class="success">Created Date:</td>
					<td class="info"><fmt:formatDate value="${wsInventoryReportMst.createdDate}" pattern="dd-MM-yyyy" /></td>
				</tr>
				<tr class="">
					<td class="success">Modified By:</td>
					<td class="info">${wsInventoryReportMst.modifiedBy}</td>
					<td class="success">Modified Date:</td>
					<td class="info"><fmt:formatDate value="${wsInventoryReportMst.modifiedDate}" pattern="dd-MM-yyyy" /></td>
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
					<%-- <div class=""
						style="font-weight: bold; font-style: italic; text-transform: capitalize">${fn:toLowerCase(itemRcvApproveHistory.approvalHeader)}</div>
					<hr style="margin: 5px 0px 10px 0px" /> --%>
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
									pattern="dd-MM-yyyy" /></td>
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
			<c:if test="${empty wsInventoryReportDtl}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty wsInventoryReportDtl}">
				<table id="requisitionListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Item Code</td>
							<td style="">Description</td>
							<td style="">Unit</td>
							<td style="">Standard Quantity</td>
							<td style="">Useable</td>
							<td style="">Un-Useable</td>
							<td style="">Not Found</td> 
							<td style="">Total Found</td> 
							<td style="">Remarks</td>
							<td style="">Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${wsInventoryReportDtl}" var="inventoryReportDtl" varStatus="loop">
							<tr>

								<td><c:out value="${inventoryReportDtl.itemCode}" /></td>
								<td><c:out value="${inventoryReportDtl.itemName}" /></td>
								<td><c:out value="${inventoryReportDtl.unit}" /></td>
								<td><input type="hidden" class="pk" id="pk${loop.index}" value="${inventoryReportDtl.id}" />
								<input type="number" id="standardQuantity${loop.index}"								 	
									class="standardQuantity" style="width: 100%" name="standardQuantity"
									readonly="readonly" value="${inventoryReportDtl.standardQuantity}"
									step=".01" /></td>
									<td>
								<input type="number" id="useableQuantity${loop.index}"								 	
									class="useableQuantity" style="width: 100%" name="useableQuantity"
									readonly="readonly" value="${inventoryReportDtl.useableQuantity}"
									step=".01" /></td>
									<td>
								<input type="number" id="unUseableQuantity${loop.index}"								 	
									class="unUseableQuantity" style="width: 100%" name="unUseableQuantity"
									readonly="readonly" value="${inventoryReportDtl.unUseableQuantity}"
									step=".01" /></td>
									<td>
								<input type="number" id="notFound${loop.index}"								 	
									class="notFound" style="width: 100%" name="notFound"
									readonly="readonly" value="${inventoryReportDtl.notFound}"
									step=".01" /></td>
									<td>
								<input type="number" id="totalFound{loop.index}"								 	
									class="totalFound" style="width: 100%" name="totalFound"
									readonly="readonly" value="${inventoryReportDtl.totalFound}"
									step=".01" /></td>
								<td>
								<input type="text" id="remarks{loop.index}"								 	
									class="remarks" style="width: 100%" name="remarks"
									readonly="readonly" value="${inventoryReportDtl.remarks}"/></td>
								<td>
																	
									<button type="button" id="editBtn${loop.index}"
										onclick="enableEditMode(${loop.index})"
										style="border-radius: 6px;"
										class="width-10  btn btn-sm btn-warning">
										<i class="ace-icon fa fa-edit"></i> <span class="bigger-30">Edit</span>
									</button> 

								 <button type="button" id="updateBtn${loop.index}"
										onclick="enableUpdateMode(${loop.index})"
										style="border-radius: 6px; display: none;"
										class="width-10  btn btn-sm btn-success">
										<i class="ace-icon fa fa-save"></i> <span class="bigger-30">Update</span>
									</button> 

									 <button type="button" id="editBtn${loop.index}"
										onclick="deleteAnItem(${loop.index})"
										style="border-radius: 6px;"
										class="width-10  btn btn-sm btn-danger">
										<i class="ace-icon fa fa-delete"></i> <span class="bigger-30">Delete</span>
									</button> 
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
 
		<script>
		
	function forwardToUpper(stateCode){
		var justification = $('#justification').val();
		var inventoryNo = $('#inventoryNo').val();
		//window.location = "${pageContext.request.contextPath}/cs/itemRecieved/itemReceivedForwordTo.do?receivedReportNo="+param1+"$justification="+justification;
		window.location = "${pageContext.request.contextPath}/inventory/sendTo.do?inventoryNo="+inventoryNo+"&justification="+justification+"&stateCode="+stateCode;
	}
	
	function backToLower(stateCode){
		var justification = $('#justification').val();
		var inventoryNo = $('#inventoryNo').val();		
		//window.location = "${pageContext.request.contextPath}/cs/itemRecieved/itemReceivedForwordTo.do?receivedReportNo="+param1+"$justification="+justification;
		window.location = "${pageContext.request.contextPath}/inventory/backTo.do?inventoryNo="+inventoryNo+"&justification="+justification+"&stateCode="+stateCode;
	}
	
	function approveing(){		
		var justification = $('#justification').val();
		var inventoryNo = $('#inventoryNo').val();		

		var returnStateCode = $('#returnStateCode').val();
		
		window.location = "${pageContext.request.contextPath}/inventory/itemInventoryApproved.do?inventoryNo="+inventoryNo+"&justification="+justification+"&return_state="+returnStateCode;
	}
	
	function enableEditMode(n) {
		$('#editBtn' + n).css("display", "none");
		$("#standardQuantity" + n).removeAttr("readonly");
		 $("#useableQuantity"+n).removeAttr("readonly");
		 $("#unUseableQuantity"+n).removeAttr("readonly");
		$("#remarks"+n).removeAttr("readonly");
		$('#updateBtn' + n).css("display", "");

	}

	function enableUpdateMode(n) {
		$('#updateBtn' + n).css("display", "none");
		$("#standardQuantity" + n).attr("readonly", "readonly");
		 $("#useableQuantity"+n).attr("readonly","readonly");
		 $("#unUseableQuantity"+n).attr("readonly","readonly");
		$("#remarks"+n).attr("readonly", "readonly");
		$('#editBtn' + n).css("display", "");
		updateRequisition(n);
	}

	//function updateWsMatsRequisition(n) {
		//qantityValidation(n);	
	//}
	function updateRequisition (n) {
		var id = $('#pk' + n).val();
		var contextPath = $('#contextPath').val();
		var path = contextPath + '/inventory/updateWsInventory.do';

		var cData = {
			id : id,
			standardQuantity : $("#standardQuantity" + n).val(),
			useableQuantity : $("#useableQuantity" + n).val(),
			unUseableQuantity : $("#unUseableQuantity" + n).val(),
			notFound : $("#notFound" + n).val(),
			totalFound : $("#totalFound" + n).val(),
			remarks : $("#remarks" + n).val(),
		};
		var cDataJsonString = JSON.stringify(cData);
		$.ajax({
			url : path,
			data : cDataJsonString,
			contentType : "application/json",
			success : function(data) {
				var result = JSON.parse(data);
				if (result == 'success') {
					$('.alert.alert-success').removeClass('hide');
					$(".alert.alert-success").fadeTo(5000, 500).slideUp(500,
							function() {
							});
				} else {
					$('.alert.alert-danger').removeClass('hide');
					$(".alert.alert-danger").fadeTo(5000, 500).slideUp(500,
							function() {
							});
				}

			},
			error : function(data) {
				alert("Server Error");
				$('.alert.alert-danger').removeClass('hide');
				$(".alert.alert-danger").fadeTo(5000, 500).slideUp(500);
			},
			type : 'POST'
		});
	}

	function deleteAnItem(n) {
		var id = $('#pk' + n).val();
		var contextPath = $('#contextPath').val();
		var path = contextPath + '/inventory/deleteAnItem.do';

		var cData = {
			id : id
		};
		var cDataJsonString = JSON.stringify(cData);
		postSubmit(path, cData, 'POST');

	}

</script>
	</div>
</div>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>