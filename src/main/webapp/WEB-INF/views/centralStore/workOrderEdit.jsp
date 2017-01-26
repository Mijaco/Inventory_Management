<%@include file="../common/csHeader.jsp"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!-- -------------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/workOrder/list.do"
				style="text-decoration: none;" class="btn btn-success btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Work
				Order List
			</a>
			<h2 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Work
				Order Show</h2>

			<h4 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptName}</h4>

			<%-- <h5 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptAddress}</h5> --%>
		</div>
	</div>

	<div class="row" style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px; margin-left: 90px; margin-right: 90px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">
			<c:if test="${!empty successflag}">
				<div class="alert alert-success" id='updatealert'>
					<strong>Success!</strong> Information is updated.
				</div>
			</c:if>
			
			<c:if test="${!empty deleteflag}">
				<div class="alert alert-danger" id='deletealert'>
					<strong>Success!</strong> Information is deleted.
				</div>
			</c:if>
			
			<div class="col-sm-12 center">
				<table class="table table-striped table-hover table-bordered">
					<tr>
						<th class="success center">Work Order No</th>
						<td class="info"><c:out value="${workOrderMst.workOrderNo}" /></td>
						<th class="success center">Work Order Date</th>
						<td class="info"><fmt:formatDate
								value="${workOrderMst.contractDate}" pattern="dd-MM-yyyy" /></td>
						<th class="success center">Supplier Name</th>
						<td class="info"><c:out
								value="${workOrderMst.supplierName}" /></td>
					</tr>
					<tr>
						<th class="success center">PSI Performed</th>
						<td class="info"><c:choose>
								<c:when test="${workOrderMst.psi}"> Yes
									</c:when>
								<c:otherwise> No </c:otherwise>
							</c:choose></td>
						<th class="success center">PLI Performed</th>
						<td class="info"><c:choose>
								<c:when test="${workOrderMst.pli}"> Yes
									</c:when>
								<c:otherwise> No </c:otherwise>
							</c:choose></td>
						<th class="success center">Project Name</th>
						<td class="info"><c:out value="${khathInfo.khathName}" /></td>
					</tr>
				</table>
			</div>

			<c:if test="${!empty workOrderDtlList}">
				<table id="centralStoreRequisitionListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Item Id</td>
							<td style="">Description</td>
							<td style="">Expected Quantity</td>
							<td style="">Unit Cost</td>
							<td style="">Remarks</td>
							<td style="">Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${workOrderDtlList}" var="workOrderDtl"
							varStatus="loop">

							<tr>
								<form id="forms${loop}"
									action="${pageContext.request.contextPath}/workOrder/update.do"
									method="POST">
									<input type='hidden'
				value='${pageContext.request.contextPath}' id='contextPath'>
								<td><c:out value="${workOrderDtl.itemId}" /></td>
								<td><c:out value="${workOrderDtl.description}" /></td>
								<td><input class="form-control" type="number"
									name="itemQty" value="${workOrderDtl.itemQty}" step="0.001" />
								</td>
								<td><input class="form-control" type="number" name="cost"
									value="${workOrderDtl.cost}" step="0.001" /></td>
								<td><input class="form-control" type="text" name="remarks"
									value="${workOrderDtl.remarks}" /> <input class="form-control"
									type="hidden" name="id" value="${workOrderDtl.id}" /></td>
								<%-- <td><c:out value="${workOrderDtl.cost}" /></td>
								<td><c:out value="${workOrderDtl.remarks}" /> </td> --%>

								<td><a href="#" class="red" onclick="showConfirmation(${workOrderDtl.id})">
										<i class="glyphicon glyphicon-remove bigger-130"></i>
								</a> | <%-- <a href="#" class="green"
											onclick="postSubmit('${pageContext.request.contextPath}/workOrder/update.do',{id:'${workOrderDtl.id}'},'POST')">
											<i class="ace-icon fa fa-pencil bigger-130"></i>
										</a> --%>
									<button class="green" type="submit">
										<i class="glyphicon glyphicon-ok bigger-130"></i>
									</button></td>
								</form>

							</tr>

						</c:forEach>
					</tbody>
				</table>
			</c:if>
			<!-- --------------------- -->
		</div>
	</div>
</div>

<script>
	function showConfirmation(id) {
		var response = confirm("Do you want to delete this record?");
		if( response == true ) {
			//postSubmit('${pageContext.request.contextPath}/workOrder/delete.do',{'id':id},'POST');
			var path = $('#contextPath').val() + "/workOrder/delete.do";
			var param = {
				id : id
			}
			postSubmit(path, param, 'POST');
		} else {
			//console.log( "null" );
		}
	}
	
	$( document ).ready( function() {
		$(".alert.alert-success").fadeTo(4000, .5).slideUp(500, function() {
			  //  $(".alert.alert-success").alert('close');
		});
		
		$(".alert.alert-danger").fadeTo(4000, .5).slideUp(500, function() {
			  //  $(".alert.alert-danger").alert('close');
		});
	});
</script>
<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
