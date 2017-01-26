<%@include file="../common/lsHeader.jsp"%>
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

		<h2 class="center blue"
			style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
			Local Purchase Details</h2>
		<input type="hidden" id="contextPath"
			value="${pageContext.request.contextPath}" />
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px;">
		<div class="col-sm-12 table-responsive">

			<c:if test="${!empty localPurchaseMst}">
				<table class="table table-striped table-hover table-bordered">
					<tr>
						<td class="success">Purchase ID</td>
						<td>${localPurchaseMst.localPurchaseNo}</td>
						<td class="success">Purchased By</td>
						<td>${localPurchaseMst.department.deptName}</td>
						<td class="success">Inv./Ref. No</td>
						<td>${localPurchaseMst.referenceNo}</td>

					</tr>
					<tr>
						<td class="success">Supplier Name</td>
						<td>${localPurchaseMst.supplierName}</td>
						<td class="success">Purchase Date</td>
						<td>
							<fmt:formatDate value="${localPurchaseMst.purchaseDate}"
										pattern="dd-MM-yyyy" />
						</td>
						<td class="success">Reference Doc</td>
						<td><c:if test="${!empty localPurchaseMst.referenceDoc}">
								<form target="_blank"
									action="${pageContext.request.contextPath}/ls/lp/download.do"
									method="POST">
									<input type="hidden" value="${localPurchaseMst.referenceDoc}"
										name="referenceDoc" />
									<button type="submit" class="fa fa-file-pdf-o red center"
										aria-hidden="true" style="font-size: 1.5em;"></button>
								</form>
							</c:if></td>
					</tr>
				</table>
			</c:if>
		</div>
		<div class="col-sm-12 table-responsive">
			
			<div class="alert alert-success hide">
				<!-- <a href="javascript:void(0)" class="close" data-dismiss="alert" aria-label="close"><strong style="font-size:1.3em;">&times;</strong></a>  -->
				<strong>Success!</strong> Information is updated.
			</div>
			<div class="alert alert-danger hide">
				<!--<a href="#" class="close" data-dismiss="alert" aria-label="close"><strong style="font-size:1.3em;">&times;</strong></a>  -->
				<strong>Fail!</strong> Information is not updated.
			</div>
			
			<c:if test="${empty localPurchaseMst}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty localPurchaseDtlList}">
				<table id="dataList" style=""
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td>Item Code</td>
							<td>Item Name</td>
							<td>Unit</td>
							<td>Purchase Qty</td>
							<td>Unit Cost</td>
							<td>Total Cost</td>
							<td>Remarks</td>
							<td>Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${localPurchaseDtlList}" var="lpDtl"
							varStatus="loop">
							<tr>
								<td>${lpDtl.itemCode}</td>
								<td>${lpDtl.itemName}</td>
								<td>${lpDtl.uom}</td>
								<td class="initshow${loop.index}">
									<input type="text"  class="initshow${loop.index}" onblur="generateTotalCost(${loop.index})" id="receivedQty${loop.index}" value="${lpDtl.receivedQty}" disabled>
								</td>
								
								<td>
									<input type="text"  class="initshow${loop.index}" onblur="generateTotalCost(${loop.index})" id="unitCost${loop.index}" value="${lpDtl.unitCost}" disabled>
								</td>
								
								<td>
									<input type="text" id="totalCost${loop.index}" value="${lpDtl.totalCost}" disabled>
								</td>
								
								<td>${lpDtl.remarks}</td>
								<td>
									<button type="button" id="btn${loop.index}"
										onclick="editLP(${loop.index})" style="border-radius: 6px;"
										class="width-10  btn btn-sm btn-success">
										<i class="ace-icon fa fa-edit"></i> <span class="bigger-30">Edit</span>
									</button>
									<button type="button" id="update${loop.index}"
										onclick="updateLP(${loop.index}, ${lpDtl.id})" style="border-radius: 6px;"
										class="width-10  btn btn-sm btn-success hide">
										<i class="ace-icon fa fa-save"></i> <span class="bigger-30">&nbsp;Update</span>
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

<script>
	function editLP(index) {
		$('.initshow'+index).removeAttr('disabled');
		$('#receivedQty'+index).focus();
		$('#btn'+index).addClass('hide');
		$('#update'+index).removeClass('hide');
	} //editLP
	
	function generateTotalCost(index) {
		if( $('#receivedQty'+index).val() == null || $.trim( $('#receivedQty'+index).val() ) == '' ) {
			$('#totalCost'+index).val('0');
		} else if( unitCost = $('#unitCost'+index).val() == null || $.trim( unitCost = $('#unitCost'+index).val() ) == '' ) {
			$('#totalCost'+index).val('0');
		} else {
			var receivedQty = $('#receivedQty'+index).val();
			var unitCost = $('#unitCost'+index).val();
			$('#totalCost'+index).val(receivedQty*unitCost);
		}
	}
	
	function updateLP(index, id) {
		var receivedQty = $('#receivedQty'+index).val();
		var unitCost = $('#unitCost'+index).val();
		var totalCost = $('#totalCost'+index).val();
		var contextPath = $('#contextPath').val();
		
		
		$.ajax({
			url : contextPath + '/ls/lp/updateLocalPurchase.do',
			data : "{receivedQty:" + receivedQty + ", unitCost:"+ unitCost +", totalCost:"+ totalCost +", id:"+id+"}",
			contentType : "application/json",
			success : function(data) {
				if( data == 'success' ) {
					
					$('#receivedQty'+index).val(receivedQty);
					$('#unitCost'+index).val(unitCost);
					$('#totalCost'+index).val(totalCost);
					
					$('.initshow'+index).attr('disabled', 'disabled');
					$('#update'+index).addClass('hide');
					$('#btn'+index).removeClass('hide');
					
					
					$('.alert.alert-success').removeClass('hide');
					$(".alert.alert-success").fadeTo(5000, 500).slideUp(500,
						function() {
							// $(".alert.alert-success").alert('close');
						});
				} else {
					$('.alert.alert-danger').removeClass('hide');
					$(".alert.alert-danger").fadeTo(5000, 500).slideUp(500,
						function() {
							// $(".alert.alert-success").alert('close');
						});
				}
				//alert(data);
			},
			error : function(data) {
				alert("Server Error");
			},
			type : 'POST'
		}); //ajax
	} //updateLP
	
</script>

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
			 "paging": false,
			 "info": false,
			 "searching": false,
			"columnDefs" : [ {
				"targets" : [ 0 ],
				"visible" : true,
				"searchable" : true
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
		var path= contextPath + '/ls/lp/viewLocalPurchase.do';		
		var cData = {id : mstId};
		
		postSubmit(path, cData, 'POST');
	}
	
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
