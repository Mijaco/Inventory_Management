<%@include file="../../common/wsContractorHeader.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:useBean id="now" class="java.util.Date" />

<!-- -------------------End of Header-------------------------- -->


<style>
.loader {
	position: fixed;
	left: 50%;
	top: 50%;
	z-index: 9999;
}
</style>
<!-- -------------------------------------------------------------------------------------- -->
<div class="loader">
	<i class="fa fa-refresh fa-spin fa-3x fa-fw"></i> <br> <span
		class="orange">Loading...</span>
</div>

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Item
				Requisition List
			</a>
		</div>

		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Item
			Requisition Form</h1>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">
			<c:if test="${!empty successflag}">
				<div class="alert alert-success" id='updatealert'>
					<strong>Success!</strong> Record is removed.
				</div>
			</c:if>

			<c:if test="${!empty unsuccessflag}">
				<div class="alert alert-danger" id='deletealert'>
					<strong>Sorry!</strong> Record is not removed.
				</div>
			</c:if>

			<c:if test="${empty inventoryLookupItemList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<input type='hidden' value='${pageContext.request.contextPath}'
				id='contextPath'>

			<div class="oe_title">

				<table class="col-sm-12 table">
					<tr class="">
						<td class="success">Indentor:</td>
						<td class="info">Executive Engineer, Testing &amp; Repair
							Division</td>
						<td class="success">Requisition No: <input type="hidden"
							value="" id="requisitionNo" /> <input type="hidden"
							value="${returnStateCode}" id="returnStateCode" /><input
							type="hidden" value="${centralStoreRequisitionMst.uuid}"
							id="uuid" /> <input type="hidden" name="contextPath"
							id="contextPath" value="${pageContext.request.contextPath}" />
						</td>

						<td class="info"></td>

						<td class="success">Date:</td>
						<td class="info"><fmt:formatDate value="${now}"
								pattern="dd-MM-yyyy" /></td>
					</tr>
					<tr class="">

						<td class="success">Contract No:</td>
						<td class="info">${contractorRep.contractor.contractNo}
						<input value="${contractorRep.contractor.contractNo}" id="workOrderNo" type="hidden"/>
						</td>
						<td class="success">Contract Date:</td>
						<td class="info"><fmt:formatDate
								value="${contractorRep.contractor.contractDate}"
								pattern="dd-MM-yyyy" /></td>
						<td class="success">Receiver Name:</td>
						<td class="info">${contractorRep.representiveName}</td>
					</tr>

				</table>
			</div>
			<c:if test="${!empty inventoryLookupItemList}">
				<table id="inventoryLookupItemTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<th>SL No.</th>
							<th>Item Code</th>
							<th>Item Name</th>
							<th>Unit</th>
							<th>Remaining Qty</th>
							<th>Required Qty</th>
							<th>Remarks</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody>

						<c:forEach items="${inventoryLookupItemList}"
							var="inventoryLookupItem" varStatus="loop">
							<tr id="row${loop.index}">
								<td>${loop.index+1}</td>
								<td>${inventoryLookupItem.itemCode}</td>
								<td>${inventoryLookupItem.itemName}</td>
								<td>${inventoryLookupItem.unit}<input class="dtlId"
									name="dtlId" id="dtlId${loop.index}" type="hidden" value="${inventoryLookupItem.id}" />
								</td>
								<td> ${inventoryLookupItem.remainingQty}
								<input id="remainingQty${loop.index}"
									name="remainingQty" type="hidden" value="${inventoryLookupItem.remainingQty}" />
								</td>
								<td><input class="quantityRequired" id="quantityRequired${loop.index}" name="quantityRequired"
									value="0.0" type="number" step="0.001" style="width: 100%;" 
									onblur="qantityValidation(this, ${loop.index})"/>
									
									<div>
										<span id="requiredQty-validation-error${loop.index}"
											class="bold"
											style="font-size: 10px; color: red; display: none;">Required
											quantity can not be greater <br /> than Remaining quantity.
										</span> <span id="requiredQty-validation-db-error${loop.index}"
											class="bold"
											style="font-size: 10px; color: red; display: none;">Required
											quantity is not available <br />in Store. Please reduce
											quantity.
										</span>
										
										<span id="requiredQty-validation-zero-error${loop.index}"
											class="bold"
											style="font-size: 10px; color: red; display: none;"> Required
											quantity can not be 0.
										</span>
									</div>
								
								</td>
								<td><input class="remarksDtl" name="remarksDtl" type="text"
									style="width: 100%;" /></td>
								<td class="center">
									<button class="btn btn-danger btn-xs"
										style="border-radius: 6px;" onclick="removeRow(${loop.index})">
										<i class="fa fa-fw fa-times"></i>&nbsp;Delete
									</button>
								</td>
							</tr>


						</c:forEach>
					</tbody>
				</table>

				<div class="col-md-12" style="padding-top: 15px;">
					<div class="col-xs-12 col-sm-6">
						<button type="button" id="saveButton"
							onclick="saveMatsRequsition()"
							style="margin-right: 10px; border-radius: 6px;"
							class="pull-right btn btn-lg btn-success">
							<i class="ace-icon fa fa-save"></i> <span class="bigger-50">Save</span>
						</button>
					</div>

					<div class="col-xs-12 col-sm-6">
						<button type="reset" class="pull-left btn btn-lg btn-danger"
							style="margin-left: 10px; border-radius: 6px;">
							<i class="ace-icon fa fa-refresh"></i> <span class="bigger-50">Reset</span>
						</button>
					</div>
				</div>
			</c:if>
		</div>
	</div>
</div>

<script
	src="${pageContext.request.contextPath}/resources/assets/js/jquery.dataTables.min.js"></script>

<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.bootstrap.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.tableTools.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.colVis.min.js"></script>
	
<script
	src="${pageContext.request.contextPath}/resources/assets/js/workshop/transformer/getMatsRequisitionForm.js"></script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>