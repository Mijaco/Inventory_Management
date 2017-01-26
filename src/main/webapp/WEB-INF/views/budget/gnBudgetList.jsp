<%@include file="../common/budgetHeader.jsp"%>

<%@ page import="java.util.Date"%>
<jsp:useBean id="now" class="java.util.Date" />
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<style>
.loader {
	position: absolute;
	left: 50%;
	top: 70%;
	z-index: 9999;
}
</style>

<div class="loader">
	<i class="fa fa-refresh fa-spin fa-3x fa-fw"></i> <br> <span
		class="orange">Loading...</span>
</div>

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit col-md-3" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/budget/generalItemUploadForm.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Upload Budget for General Items
			</a>
		</div>
		<h2 class="center blue col-md-6"
			style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
			Budget Allocation for the ${descoSession.sessionName}</h2>
		<input type="hidden" id="contextPath"
			value="${pageContext.request.contextPath}" />
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px;">




		<div class="col-sm-12 table-responsive">
			<c:if test="${fn:length(duplicateItemList) > 2 }">
				<div class="col-sm-12 center">
					<p class="red">
						<Strong>Duplicate Item(s) found: </Strong>${duplicateItemList}
					</p>
				</div>
			</c:if>

			<c:if test="${empty generalItemBudgetMstList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty generalItemBudgetMstList}">
				<table id="dataList" style=""
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td>Code No.</td>
							<td>Particulars</td>
							<td>Unit</td>
							<td>Quantity</td>
							<td>Rate</td>
							<td>Amount</td>
							<td>Action</td>
						</tr>
					</thead>


					<tbody>
						<c:forEach items="${generalItemBudgetMstList}" var="gnBudgetMst"
							varStatus="loop">
							<tr>
								<td>${gnBudgetMst.itemCode}</td>
								<td>${gnBudgetMst.itemName}</td>
								<td>${gnBudgetMst.uom}</td>

								<td>${gnBudgetMst.qty}</td>
								<td>${gnBudgetMst.unitCost}</td>
								<td>${gnBudgetMst.totalCost}</td>

								<td class="center">
									<button type="button" id="viewGB${loop.index}"
										onclick="viewGB(${gnBudgetMst.id})"
										style="border-radius: 6px;"
										class="width-10  btn btn-sm btn-success">
										<i class="ace-icon fa fa-edit"></i> <span class="bigger-30">Details</span>
									</button>
								</td>

							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
		</div>
		<!-- --------------------- -->

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

<script type="text/javascript">
	$(document).ready(function() {
		$('#dataList').DataTable({			
			"order" : [ [ 0, 'asc' ] ],
			"bLengthChange" : false
		});
		
	});

	$(window).load(function() {
		$('.loader').fadeOut('slow');
	});
	
	
	function viewGB(mstId){
		var contextPath= $('#contextPath').val();
		var path= contextPath + '/budget/showGnItemBudget.do';		
		var cData = {id : mstId};
		
		postSubmit(path, cData, 'POST');
	}
	
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
