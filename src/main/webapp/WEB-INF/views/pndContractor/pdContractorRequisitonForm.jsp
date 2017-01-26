<%@include file="../common/pndCnHeader.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/cnpd/pnd/requisitionList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Store
				Requisition List
			</a>
		</div>
		<h2 class="center blue"
			style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Store
			Requisition Form-2</h2>
		<h4 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptName}</h4>

		<%-- <h5 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptAddress}</h5> --%>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 10px; margin-top: 10px; margin-bottom: 10px; margin-left: 10px; margin-right: 10px;">
		<!-- --------------------- -->
		<form
			action="${pageContext.request.contextPath}/cnpd/pnd/storeRequisitionSave.do"
			method="POST" id="myForm">
			<div class="oe_title">
				<div class="col-xs-12">
					<table class="col-xs-12 table">
						<tr class="">

							<td class="info">Work Order No:</td>
							<td class="info" id="contractNo">${contractor.contractNo}<input
								type="hidden" name="contextPath" id="contextPath"
								value="${pageContext.request.contextPath}">
							</td>
							<td class="success">Work Order Date:</td>
							<td class="success"><fmt:formatDate
									value="${contractor.contractDate}"
									pattern="dd-MM-yyyy" /></td>
							<td class="danger">Indentor:</td>
							<td class="danger">${deptName}</td>
						</tr>

						<tr class="">
							<td class="info">Receiver Name:</td>
							<td class="info">${senderInfo.receivedBy}
								<input name="receivedBy" value="${senderInfo.receivedBy}" type="hidden"/>
							</td>
							<td class="success">Requisition To:&nbsp;<strong class='red'>*</strong></td>
							<td class="success"><select name="requisitionTo"
								onchange="storeLeaveChange(this)" id="requisitionTo"
								class="form-control">
									<option value="cs">Central Store</option>
									<option value="ss">Sub Store</option>
							</select></td>
							<td class="danger">Remarks :</td>
							<td class="danger"><input class="form-control" type="text"
								name="remarks" /></td>

						</tr>
					</table>
				</div>
			</div>

			<div style="background: white;">
				<c:if test="${empty requestedJobList}">
					<div class="col-sm-12 center">
						<p class="red">
							<i>Sorry!!! No Data Found in Database. </i>
						</p>
					</div>
				</c:if>
				<c:if test="${!empty requestedJobList}">
					<c:forEach items="${requestedJobList}" var="requestedJobs" varStatus="loopOne">

						<table id="testTable"
							class="table table-striped table-hover table-bordered">
							<thead>
								<tr
									style="background: #579EC8; color: white; font-weight: normal;">
									<td style="">Job No</td>
									<td style="">Item Code</td>
									<td style="">Description</td>
									<td style="">Unit</td>
									<td style="">Total Quantity</td>
									<td style="">Remaining Quantity</td>
									<td style="">Required Quantity</td>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${requestedJobs}" var="pndJobDtl"
									varStatus="loop">
									<tr>
										<td><input type="hidden" name="pndJobDtlId"
											value="${pndJobDtl.id}" /> <input type="hidden" name="jobNo"
											value="${pndJobDtl.jobNo}" /> <c:out
												value="${pndJobDtl.jobNo}" /></td>
										<td><c:out value="${pndJobDtl.itemCode}" /></td>
										<td><c:out value="${pndJobDtl.itemName}" /></td>
										<td><c:out value="${pndJobDtl.uom}" /></td>
										<td><c:out value="${pndJobDtl.quantity}" /></td>
										<td><c:out value="${pndJobDtl.remainningQuantity}" /></td>
										<td><input class="form-control requiredQuantity"
											type="number" step="0.01" value="0" name="requiredQuantity"
											id="requiredQuantity${loopOne.index}${loop.index}"
											onblur="quantityValidation(this, ${pndJobDtl.id}, ${pndJobDtl.remainningQuantity})" />
											<div>
												<span id="requiredQty-validation-error${loopOne.index}${loop.index}"
													class="bold"
													style="font-size: 10px; color: red; display: none;">Required
													quantity can not be greater <br /> than Remaining
													quantity.
												</span> <span id="requiredQty-validation-db-error${loopOne.index}${loop.index}"
													class="bold"
													style="font-size: 10px; color: red; display: none;">Required
													quantity is not available <br />in Store. Please reduce
													quantity.
												</span>
											</div></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</c:forEach>
				</c:if>
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
	src="${pageContext.request.contextPath}/resources/assets/js/pndContractor/pdContractorRequisitionForm.js"></script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>