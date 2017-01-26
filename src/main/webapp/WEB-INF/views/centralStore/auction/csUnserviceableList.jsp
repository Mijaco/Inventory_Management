<%@include file="../../common/csHeader.jsp"%>



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
			Central Store Unserviceable Item List upto
			<fmt:formatDate value="${auctionMst.countDate}" pattern="dd-MM-yyyy" />
		</h2>
		<input type="hidden" id="contextPath"
			value="${pageContext.request.contextPath}" />

		<div class="alert alert-success hide" id='updateAlert'>
			<strong>Success!</strong> Information is updated.
		</div>
		<div class="alert alert-danger hide" id='updateValidationError'>
			<strong>Update Faile!</strong> Quantity can not greater than Ledger.
		</div>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">

		<div class="col-sm-12 table-responsive">
			<c:if test="${empty auctionDtlList}">
				<div class="col-sm-12 center">
					<p class="red ubuntu-font">
						<i>No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty auctionDtlList}">
				<table id="dataList" style=""
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td>ID</td>
							<td>Project Name</td>
							<td>Item Code</td>
							<td>Description</td>
							<td>Unit</td>
							<td>Ledger Qty</td>
							<td>Actual Qty</td>
							<td>Action</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${auctionDtlList}" var="dtl" varStatus="loop">
							<tr>
								<td>${dtl.id}</td>

								<td>${dtl.descoKhath.khathName}</td>
								<td>${dtl.itemMaster.itemId}</td>
								<td>${dtl.itemMaster.itemName}</td>
								<td>${dtl.itemMaster.unitCode}</td>

								<td id="ledgerQty${dtl.id}">${dtl.ledgerQty}</td>
								<td><input type="number" step="0.001"
									id="storeFinalQty${dtl.id}" value="${dtl.storeFinalQty}" /></td>

								<td class="center">
									<button type="button" id="updateBtn${dtl.id}"
										onclick="updateQty(${dtl.id})" style="border-radius: 6px;"
										class="width-10  btn btn-sm btn-success">
										<i class="ace-icon fa fa-edit"></i> <span class="bigger-30">Update</span>
									</button>

									<button type="button" id="deleteBtn${dtl.id}"
										onclick="deleteDtl(${dtl.id})" style="border-radius: 6px;"
										class="width-10  btn btn-sm btn-danger">
										<i class="fa fa-times"></i> <span class="bigger-30">Delete</span>
									</button>
								</td>

							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
		</div>
		<!-- --------------------- -->
		<div class="col-md-12 center"
			style="padding-top: 15px; margin-top: 5px;">


			<div class="col-sm-offset-5 col-sm-2">
				<button type="button" class="btn btn-md btn-primary"
					style="margin-left: 10px; border-radius: 6px;">
					<i class="fa fa-paper-plane"></i> <span class="bigger-50">Send
						to Admin</span>
				</button>
			</div>
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
	
	eleteDtl
	function deleteDtl(id){
		var contextPath= $('#contextPath').val();
		var path= contextPath + '/auction/store/deleteAuctionDtl.do';
		var params = {id : id};		
		var cDataJsonString = JSON.stringify(params);
		$.ajax({			
			url : path,
			data : cDataJsonString,
			contentType : "application/json",
			success : function(data) {
				var result = JSON.parse(data);
				$('.alert.alert-success').removeClass('hide');
				$(".alert.alert-success").fadeTo(5000, 500).slideUp(500);				
			},
			error : function(data) {
				alert("From JS: Server Error");
			},
			type : 'POST'
		});
	}
	
	function updateQty(id){
		var contextPath= $('#contextPath').val();
		var path= contextPath + '/auction/store/updateAuctionDtl.do';
		var editedQty=$('#storeFinalQty'+id).val() || 0;
			editedQty = parseFloat(editedQty);
		var storeFinalQty= parseFloat(editedQty.toFixed(3));
		var ledgerQty= parseFloat($('#ledgerQty'+id).text());
		
		
		if(storeFinalQty>ledgerQty){
			$('.alert.alert-danger').removeClass('hide');
			$(".alert.alert-danger").fadeTo(5000, 500).slideUp(500);
			$('#storeFinalQty'+id).focus();
			return;
		}
		var params = {id : id, storeFinalQty:storeFinalQty};
		
		var cDataJsonString = JSON.stringify(params);
		
		$.ajax({			
			url : path,
			data : cDataJsonString,
			contentType : "application/json",
			success : function(data) {
				var result = JSON.parse(data);
				$('.alert.alert-success').removeClass('hide');
				$(".alert.alert-success").fadeTo(5000, 500).slideUp(500);				
			},
			error : function(data) {
				alert("From JS: Server Error");
			},
			type : 'POST'
		});
	}
	
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>
