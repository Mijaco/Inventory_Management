<%@include file="../common/lprrHeader.jsp"%>

<%@ page import="java.util.Date"%>
<jsp:useBean id="now" class="java.util.Date" />

<style>
.loader {
	position: fixed;
	left: 50%;
	top: 50%;
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
		<div class="o_form_buttons_edit col-md-2" style="display: block;">
			<a href="${pageContext.request.contextPath}/settings/localPurchaseForm.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Local
				Purchase Form
			</a>
		</div> <br> <br>
		<h2 class="center blue"
			style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
			Local Purchase List</h2>
		<input type="hidden" id="contextPath"
			value="${pageContext.request.contextPath}" />
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px;">

		<div class="col-sm-12 table-responsive">
			<c:if test="${empty localPurchaseMstList}">
				<div class="col-sm-12 center">
					<p class="green">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty localPurchaseMstList}">
				<table id="dataList" style=""
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td>ID</td>
							<td>Purchase ID</td>
							<td>Purchased By</td>
							<td>Supplier Name</td>
							<td>Inv./Ref. No</td>
							<td>Reference Doc</td>
							<td>Remarks</td>
							<td>Purchase Date</td>
							<td>Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${localPurchaseMstList}" var="lpMst"
							varStatus="loop">
							<tr>
								<td>${lpMst.id}</td>
								<td>${lpMst.localPurchaseNo}</td>
								<td>${lpMst.department.deptName}</td>
								<td>${lpMst.supplierName}</td>
								<td>${lpMst.referenceNo}</td>
								<td><c:if test="${!empty lpMst.referenceDoc}">
										<form target="_blank"
											action="${pageContext.request.contextPath}/settings/download.do"
											method="POST">
											<input type="hidden" value="${lpMst.referenceDoc}"
												name="referenceDoc" />
											<button type="submit" class="fa fa-file-pdf-o red center"
												aria-hidden="true" style="font-size: 1.5em;"></button>
										</form>
									</c:if></td>
								<td>${lpMst.remarks}</td>
								<td><fmt:formatDate value="${lpMst.purchaseDate}"
										pattern="dd-MM-yyyy" /></td>
								<td>
									<button type="button" id="viewLP${loop.index}"
										onclick="viewLP(${lpMst.id})" style="border-radius: 6px;"
										class="width-10  btn btn-sm btn-success">
										<i class="ace-icon fa fa-edit"></i> <span class="bigger-30">View</span>
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
			"columnDefs" : [ {
				"targets" : [ 0 ],
				"visible" : false,
				"searchable" : false
			} ],
			"order" : [ [ 0, 'desc' ] ]
		});
		document.getElementById('dataList_length').style.display = 'none';
		// document.getElementById('dataList_filter').style.display = 'none';
	});

	$(window).load(function() {
		$('.loader').fadeOut('slow');
	});
	
	
	function viewLP(mstId){
		var contextPath= $('#contextPath').val();
		var path= contextPath + '/settings/viewLocalPurchase.do';		
		var cData = {id : mstId};
		
		postSubmit(path, cData, 'POST');
	}
	
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
