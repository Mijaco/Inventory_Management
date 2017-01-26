<%@include file="../common/csHeader.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- -------------------End of Header-------------------------- -->
<!-- CSS -->
<style type="text/css">
.ui-widget-overlay {    
    opacity: .6!important;   
}
</style>
<script>
var locationList = ${locationList};
</script>
<!-- -------------------End of Style and Script-------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="#"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Project Transfer List
			</a>			
		</div>
		<h1 class="center blue" style="margin-top: 0px;">Ledger Transfer</h1>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px;">
		<!-- --------------------- -->
		<div class="oe_title">

			<table class="col-sm-12 table">
				<tr class="">
					<td class="success">Project Transfer No: <input type="hidden"
						value="${khathTransferMst.transferNo}" id="transferNo" /> <input
						type="hidden" value="${returnStateCode}" id="returnStateCode" />
						<input type="hidden" value="${khathTransferMst.uuid}" id="uuid" />
						<input type="hidden" value="${pageContext.request.contextPath}" id="contextPath" />
						<input type="hidden" value="${khathTransferMst.ledgerBook}" id="ledgerNameFrom" />
						
						
					</td>

					<td class="info">${khathTransferMst.transferNo}</td>
					<td class="success">Project From:</td>
					<td class="info">${khathTransferMst.khathFrom}</td>
					<td class="success">Project To:</td>
					<td class="info">${khathTransferMst.khathTo}</td>
				</tr>
				<tr class="">
					<td class="success">Ledger Book:</td>
					<td class="info">${khathTransferMst.ledgerBook}</td>
					<td class="success">UUID:</td>
					<td class="info"><%-- ${khathTransferMst.uuid} --%></td>
					<td class="success">Remarks:</td>
					<td class="info">${khathTransferMst.remarks}</td>

				</tr>
				<tr class="">
					<td class="success">Transfer Date:</td>
					<td class="info"><fmt:formatDate
	value="${khathTransferMst.createdDate}" pattern="dd-MM-yyyy" /></td>
					<td class="success">Created By:</td>
					<td class="info">${khathTransferMst.createdBy}</td>
					<td class="success">Created Date:</td>
					<td class="info"><fmt:formatDate
	value="${khathTransferMst.createdDate}" pattern="dd-MM-yyyy" /></td>

				</tr>
				<tr class="">
					<td class="success">Modified By:</td>
					<td class="info">${csProcItemRcvMst.modifiedBy}</td>
					<td class="success">Modified Date:</td>
					<td class="info"><fmt:formatDate
	value="${csProcItemRcvMst.modifiedDate}" pattern="dd-MM-yyyy" /></td>
					<td class="success">Present Status:</td>
					<td class="info"><strong> ${currentStatus} </strong></td>
				</tr>

			</table>
		</div>
		
		<c:if test="${!empty khathHierarchyHistoryList}">
			<button data-toggle="collapse" data-target="#demo">
				<span class="glyphicon glyphicon-collapse-down"></span>
			</button>
		</c:if>


		<div id="demo" class="collapse">
			<c:if test="${!empty khathHierarchyHistoryList}">

				<c:forEach items="${khathHierarchyHistoryList}"
					var="khathHierarchyHistory">
					<table class="col-sm-12 table">

						<tr class="">
							<td class="warning col-sm-3"
								style="font-weight: bold; font-size: 1.5em; font-style: italic; text-transform: capitalize">${fn:toLowerCase(khathHierarchyHistory.approvalHeader)}</td>
							<td class="success col-sm-2" style="text-transform: capitalize">${fn:toLowerCase(khathHierarchyHistory.stateName)}
								By:</td>
							<td class="success col-sm-2 text-left">
								${khathHierarchyHistory.cEmpFullName} <c:if
									test="${!empty khathHierarchyHistory.cDesignation}">
									, ${khathHierarchyHistory.cDesignation} 
								</c:if> <c:if test="${!empty khathHierarchyHistory.cEmpId}">
									( ${khathHierarchyHistory.cEmpId} )
								</c:if>
							</td>
							<td class="info col-sm-2 text-left"
								style="text-transform: capitalize">${fn:toLowerCase(khathHierarchyHistory.stateName)}
								Date:</td>
							<td class="info col-sm-3"><fmt:formatDate
	value="${khathHierarchyHistory.createdDate}" pattern="dd-MM-yyyy" /></td>
						</tr>
						<c:if test="${!empty khathHierarchyHistory.justification}">
							<tr class="">
								<td></td>
								<td class="danger col-sm-1">Comment(s):</td>
								<td class="danger col-sm-11" colspan="3"
									title="${khathHierarchyHistory.justification}">${khathHierarchyHistory.justification}</td>
							</tr>
						</c:if>
					</table>
				</c:forEach>
			</c:if>
		</div>


		<div style="background: white;">
			<c:if test="${empty khathTransferDtlList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty khathTransferDtlList}">
				<table id="requisitionListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Item Code</td>
							<td style="">Description</td>
							<td style="">Unit</td>
							<td style="">Present Qty</td>
							<td style="">Transfer Qty</td>
<!-- 							<td style="">Unit Cost</td> -->
<!-- 							<td style="">Total Cost</td> -->
							<td style="">Remarks</td>
							<td style="">Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${khathTransferDtlList}" var="khathTransferDtl" varStatus="loop">
							<tr>

								<td><c:out value="${khathTransferDtl.itemId}" /></td>
								<td><c:out value="${khathTransferDtl.description}" /></td>
								<td><c:out value="${khathTransferDtl.uom}" /></td>
								<td>
								<%-- ${khathTransferDtl.presentQty} --%>
								<fmt:formatNumber
										type="number" minFractionDigits="3" groupingUsed="false"
										value="${khathTransferDtl.presentQty}" />
								</td>
								<td id="transferedQty${loop.index}">
								<%-- ${khathTransferDtl.trnasferQty} --%>
								<fmt:formatNumber
										type="number" minFractionDigits="3" groupingUsed="false"
										value="${khathTransferDtl.trnasferQty}" />
								</td>
<%-- 								<td  id="unitCost${loop.index}"><c:out value="${khathTransferDtl.unitCost}" /></td> --%>
<%-- 								<td  id="totalCost${loop.index}"><c:out value="${khathTransferDtl.unitCost*khathTransferDtl.trnasferQty}" /></td> --%>
								<td><c:out value="${khathTransferDtl.remarks}" /></td>
								<td>
									<div class="action-buttons" data-itemcode="${khathTransferDtl.itemId}">
										<a href="#" class="btn btn-primary" id="setDialog${loop.index}">
											<i class="glyphicon glyphicon-edit"  onclick="openGridDialog(this)"
											aria-hidden="true"> </i>
										</a>
										<%-- <a href="#" data-toggle="modal" data-target="#editModal"
											class="editModal" onclick="editItem(${khathTransferDtl.id})">
											<i class="ace-icon fa fa-pencil bigger-130"></i>
										</a>
										<a href="#" onclick="editItemLocation(${khathTransferDtl.itemId})">
											<i class="glyphicon glyphicon-edit" aria-hidden="true"></i>
										</a> --%>
									</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>

		</div>



		<!-- <div class="text-center"> -->
		<div class="row">
			<label class="col-xs-2"> <strong>Comment(s):&nbsp;<span class='red'>*</span></strong></label>
			<div class="col-xs-10">
				<textarea class="form-control" rows="2" cols="" id="justification"></textarea>
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
						</ul>
					</div>
				</c:if>
			</div>
			<div class="col-md-4 col-sm-4 text-center">
				<a class="btn btn-primary" href="Javascript:appoveKhathTransfer()"
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
							action="${pageContext.request.contextPath}/cs/itemRecieved/updateItem.do">
							<div class="oe_title">

								<input class="o_form_input o_form_field o_form_required"
									id="modal_itemId" placeholder="Item Code" type="text"
									readonly="readonly"
									style="border: 0; border-bottom: 2px ridge; width: 100%; font-size: 1em" />

								<input class="o_form_input o_form_field o_form_required"
									id="modal_description" placeholder="Description" type="text"
									readonly="readonly"
									style="border: 0; border-bottom: 2px ridge; width: 100%; font-size: 1em" />

								<input class="o_form_input o_form_field o_form_required"
									id="modal_expectedQuantity" placeholder="Expected Quantity"
									type="text" readonly="readonly"
									style="border: 0; border-bottom: 2px ridge; width: 100%; font-size: 1em" />

								<input class="o_form_input o_form_field o_form_required"
									id="modal_receivedQuantity" placeholder="Received Quantity"
									type="text" name="receivedQty"
									onkeyup="receivedQtyToRemainQty(this)"
									style="border: 0; border-bottom: 2px ridge; width: 100%; font-size: 1em" />

								<input class="o_form_input o_form_field o_form_required"
									id="modal_reminingQuantity" placeholder="Remining Quantity"
									type="text" name="remainingQty" readonly="readonly"
									style="border: 0; border-bottom: 2px ridge; width: 100%; font-size: 1em" />

								<input type="text" hidden="true" id="modal_id" name="id" />
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

	</div>
</div>

<!-- -------------------------- -->
<div id="myDialog" title="Title">
	<div id="dynamicTable">
	</div>
</div>

<div id="myGrid" title="Title">
	<form action="" method="post">
		<table id="tblAppendGrid">
		</table>
	 </form>
</div>
<!-- -------------------------- -->

<!-- JS call -->
<script src="${pageContext.request.contextPath}/resources/assets/js/centralStore/csKhathTransferShow.js"></script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>