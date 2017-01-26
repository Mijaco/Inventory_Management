<%@include file="../common/pdHeader.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- ---------- Start of Style and Script for Location Grid ------------- -->
<style type="text/css">
.btn-add {
	margin-bottom: 20px;
}

.ui-widget-overlay {
	opacity: .6 !important;
}
</style>
<!-- ---------- End of Style and Script for Location Grid ------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/asBuilt/newAsBuiltList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> As Built
				 List
			</a>
		</div>
		<h1 class="center blue" style="margin-top: 0px;">As Built</h1>
	</div>
	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px;">
		<!-- --------------------- -->
		<form id="myForm" action="${pageContext.request.contextPath}/asBuilt/asBuiltSubmitApproved.do" method="POST">
		<div class="oe_title">

			<table class="col-sm-12 table">
				<tr class="">
					<td class="success">Work Order No: <input type="hidden" name="woNumber"
						value="${asBuiltMst.woNumber}"
						id="woNumber" /><input type="hidden" name="asBuiltNo" id="asBuiltNo"
						value="${asBuiltMst.asBuiltNo}"
						id="woNumber" /><input type="hidden" name="id"
						value="${asBuiltMst.id}"
						id="woNumber" /> <input type="hidden"
						value="${returnStateCode}" id="returnStateCode" name="return_state"  />
						<input type="hidden" name="contextPath" id="contextPath"
						value="${pageContext.request.contextPath}" />																		
					</td>

					<td class="info">${asBuiltMst.woNumber}</td>
					<td class="success">Created By:</td>
					<td class="info">${asBuiltMst.createdBy}</td>
					<td class="success">Created Date:</td>
					<td class="info"><fmt:formatDate
	value="${asBuiltMst.createdDate}" pattern="dd-MM-yyyy" /></td>
				</tr>
				<tr class="">
					<td class="success">As Built No:</td>
					<td class="info">${asBuiltMst.asBuiltNo}</td>
					<td class="success">Remarks:</td>
					<td class="info">${asBuiltMst.remarks}</td>
					<td class="success">Present Status:</td>
					<td class="info"><strong> ${currentStatus} </strong></td>
				</tr>

			</table>
		</div>

		<div style="background: white;">
			<c:if test="${empty asBuiltDtlList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty asBuiltDtlList}">
				<table id="requisitionListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
								<tr
									style="background: #579EC8; color: white; font-weight: normal;">
									
									<td style="width:30px;">Item Code</td>
									<td style="width:200px;">Name Of Materials</td>
									<td style="">Unit</td>
									<td style="">Consumed</td>
									<td style="width:70px;">Re-Use</td>
									<td style="">Total</td>
									<td style="">Recovery</td>
									<td style="">Re-Balance</td>
									<td style="">Remarks</td>
								</tr>
								 <tr style="background: #579EC8; color: white; font-weight: normal;">
									
									<td style=""></td>
									<td style=""></td>
									<td style=""></td>
									<td style=""></td>
									<td style=""></td>
									<td style=""></td>
									<td style="width:150px;">Serviceable&nbsp;<b>|</b>&nbsp;UnServiceable</td>
									<td style="">Serviceable&nbsp;<b>|</b>&nbsp;UnServiceable</td>
									<td style=""></td>
								</tr> 
							</thead>

					<tbody>
						<c:forEach items="${asBuiltDtlList}"
							var="asBuiltDtl" varStatus="loop">
							<tr>

								<td><c:out value="${asBuiltDtl.itemCode}" /><input name="itemCode" type="hidden" value="${asBuiltDtl.itemCode}" /></td>
								<td><c:out value="${asBuiltDtl.itemName}" /></td>
								<td><c:out value="${asBuiltDtl.uom}" /></td>
								<td><c:out value="${asBuiltDtl.consume}" /><input id="consume${loop.index}" name="consume" type="hidden" value="${asBuiltDtl.consume}" /></td>
								<td>${asBuiltDtl.reUse}</td>
								<td><input class="form-control total"
											type="number" step="0.01" value="${asBuiltDtl.total}" name="total"
											id="total${loop.index}" /></td>
								<td style="width:150px;"><input style="width:80px;" class="recServiceable"
											type="number" step="0.01" value="${asBuiltDtl.recServiceable}" name="recServiceable" onblur="calc(this)"
											id="recServiceable${loop.index}" />
									<input style="width:80px;" class="recUnServiceable"
											type="number" step="0.01" value="${asBuiltDtl.recUnServiceable}" name="recUnServiceable" onblur="autoShow(this.id)"
											id="recUnServiceable${loop.index}" /></td>
								<td><input style="width:80px;" class="reBalServiceable"
											type="number" step="0.01" value="${asBuiltDtl.reBalServiceable}" name="reBalServiceable"
											id="reBalServiceable${loop.index}" />
									<input style="width:80px;" class="reBalUnServiceable"
											type="number" step="0.01" value="${asBuiltDtl.reBalUnServiceable}" name="reBalUnServiceable"
											id="reBalUnServiceable${loop.index}" /></td>
								<td><input class="form-control remarks"
											type="text" value="${asBuiltDtl.remarks}" name="remarks"
											id="remarks${loop.index}" /></td>
									</tr>
							<c:set var="count" value="${loop.count}" scope="page" />
						</c:forEach>
					</tbody>
				</table>
			</c:if>
		</div>

		</form>
	</div>
</div>


<!-- ------Start of Location Grid Div ----- -->
<div id="myGrid" title="Title">
	<form action="" method="post">
		<table id="tblAppendGrid">
		</table>
	</form>
</div>
<!-- ------End of Location Grid Div ----- -->

<script
	src="${pageContext.request.contextPath}/resources/assets/js/contractor/asBuiltShow.js"></script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>