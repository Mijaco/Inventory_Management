<%@include file="../common/wsContractorHeader.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/ws/asBuilt/asBuiltList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> As Built List
			</a>
		</div>
		<h2 class="center blue"
			style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">As Built Detail Form</h2>
		<h4 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptName}</h4>

		
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px; margin-left: 90px; margin-right: 90px;">
		<!-- --------------------- -->
		<form
			action="${pageContext.request.contextPath}/ws/asBuilt/asBuiltReportSave.do"
			method="POST" id="myForm">
			<div class="oe_title">
				<div class="col-xs-12">
					<table class="col-xs-12 table">
						<tr class="">

							<td class="info">Work Order No:</td>
							<td class="" id="contractNo">${contractor.contractNo}<input
								type="hidden" name="woNumber"
								value="${contractor.contractNo}"><input
								type="hidden" name="contextPath" id="contextPath"
								value="${pageContext.request.contextPath}">
							</td>
							<td class="info">Work Order Date:</td>
							<td class=""><fmt:formatDate
									value="${contractor.contractDate}"
									pattern="dd-MM-yyyy" /></td>
							<td class="info">Indenter:</td>
							<td class="">${deptName}</td>
						</tr>

						<tr class="">
							 <td class="info">Job Number:</td>
							<td class="info">${jobNo}<input
								type="hidden" name="jobNo"
								value="${jobNo}"></td>
							<td class="info">Remarks :</td>
							<td class="" colspan="5"><input class="form-control" type="text"
								name="remarks" /></td>

						</tr>
					</table>
				</div>
			</div>

			<div style="background: white;">
				<c:if test="${empty jobCardDtlList}">
					<div class="col-sm-12 center">
						<p class="red">
							<i>Sorry!!! No Data Found in Database. </i>
						</p>
					</div>
				</c:if>

						<table id="testTable"
							class="table table-striped table-hover table-bordered">
							<thead>
								<tr
									style="background: #579EC8; color: white; font-weight: normal;">
									<td style="width:10px;">SL No.</td>
									<td style="width:30px;">Item Code</td>
									<td style="width:200px;">Name Of Materials</td>
									<td style="">Unit</td>
									<td style="">Allocation Quantity</td>
									<td style="">Received Quantity </td>
									<td style="">Materials Consumed</td>
									<td style="">Materials in hand</td>
									<!-- <td style="">Total</td> -->
									<td style="">Remarks</td>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${jobCardDtlList}" var="asBuilt"
									varStatus="loop">
									<tr><%-- <c:set var="test" value="${loop.index+1}"></c:set> --%>
										<td>${loop.index+1}</td>
										<td><c:out value="${asBuilt.itemCode}" /><input	type="hidden" value="${asBuilt.itemCode}" name="itemCode" /></td>
										<td><c:out value="${asBuilt.itemName}" /><input	type="hidden" value="${asBuilt.itemName}" name="itemName" /></td>
										<td><c:out value="${asBuilt.unit}" /><input type="hidden" value="${asBuilt.unit}" name="uom" /></td>
										<td><c:out value="${asBuilt.quantityUsed}" /><input type="hidden" value="${asBuilt.quantityUsed}" id="materialsQuantity${loop.index}" name="materialsQuantity" /></td>
										<td><c:out value="${asBuilt.quantityUsed-asBuilt.remainningQuantity}" /><input type="hidden" value="${asBuilt.quantityUsed-asBuilt.remainningQuantity}" id="receivedQuantity${loop.index}" name="receivedQuantity" /></td>
										<td><input type="number" step="0.01" value="0" id="materialsConsume${loop.index}" name="materialsConsume" onblur="getTotalCost(this)" /></td>
										<td style="width:150px;"><input style="width:80px;" type="number" step="0.01"
											type="number" step="0.01" value="0" name="materialsInHand" id="materialsInHand${loop.index}" readonly="readonly" />
										<td><input class="form-control remarks"
											type="text" name="remarks"
											id="remarks${loop.index}" /></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
				<div class="col-md-12 center" style="padding-top: 15px;">
					<div class="col-xs-12 col-sm-12">
						<button type="submit"
							style="margin-right: 6px; border-radius: 6px;"
							class="width-10 pull-center btn btn-lg btn-success">Save</button>
					</div>
				</div>
			</div>
		</form>
	</div>

</div>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/workshop/wsAsBuiltDetailForm.js"></script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>