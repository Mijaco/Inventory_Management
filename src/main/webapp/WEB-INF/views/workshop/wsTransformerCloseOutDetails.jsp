<%@include file="../common/wsHeader.jsp"%>
<style>
.loader {
	position: fixed;
	left: 50%;
	top: 50%;
	z-index: 9999;
	/* background-color: none;
	width: 100%;
	height: 100%; */
}
</style>
<!-- -------------------------------------------------------------------------------------- -->
<div class="loader">
	<i class="fa fa-refresh fa-spin fa-3x fa-fw"></i> <br>
	<span class="orange">Loading...</span>
</div>

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		
		<div class="o_form_buttons_edit" style="display: block;">
			
			<h1 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Transformer Close Out
				 List</h1>
		</div>
	</div>

	<div class="row" 
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">
			
			

			<c:if test="${!empty transformerCloseOutMstDetails}">
				
				<table id="storeRequisitionListTable"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="width:150px;">Item Code</td>
									<td style="width:550px;">Name Of Materials</td>
									<td style="width:150px;">Unit</td>
									<!-- <td style="width:150px;">B.F.</td> -->
									<td style="width:150px;">Purchase by P.cash </td>
									<td style="width:150px;">This Period (Store)</td>
									<td style="width:150px;">Total Q-ty.</td>
									<td style="width:150px;">Materials Consumed as per Annex-A</td>
									<td style="width:150px;">Materials To be Return/Materials in Hand</td>
									<td style="width:150px;">Actualy Return</td>
									<td style="width:150px;">Quantity Short</td>
									<td style="width:150px;">Quantity Excess</td>
								</thead>

					<tbody>
						<c:forEach items="${transformerCloseOutMstDetails}"
							var="transformerCloseOutDtl">
							<tr>
								<td class="col-sm-1 col-xs-12">${transformerCloseOutDtl.itemCode}</td>
										<td style="width:550px;">${transformerCloseOutDtl.itemName}</td>
										<td class="col-sm-1 col-xs-12">${transformerCloseOutDtl.uom}</td>
										<td style="width:150px;">${transformerCloseOutDtl.rcvPurCashQty}</td>
										<td style="width:150px;">${transformerCloseOutDtl.rcvFromStoreQty}</td>
										<td style="width:150px;">${transformerCloseOutDtl.totalQty}</td>
										<td style="width:150px;">${transformerCloseOutDtl.materialsConsume}</td>
										<td style="width:150px;">${transformerCloseOutDtl.materialsReturn}</td>
										<td style="width:150px;">${transformerCloseOutDtl.actualReturn}</td>
										<td style="width:150px;">${transformerCloseOutDtl.qtyShort}</td>
										<td style="width:150px;">${transformerCloseOutDtl.qtyExcess}</td>
											
												
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
			<!-- --------------------- -->
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
	$(document)
			.ready(
					function() {
						$('#storeRequisitionListTable').DataTable();
						document
								.getElementById('storeRequisitionListTable_length').style.display = 'none';
						// document.getElementById('storeRequisitionListTable_filter').style.display = 'none';

					});

	function createNewDiv() {
		document.getElementsByClassName("container-fluid").innerHTML = '<object type="type/html" data="home.html" ></object>';
		/* $(".container-fluid").append("<div>hello</div>"); */
	}
</script>
<!--

//-->

<script>
	$(window).load( function() {
		$('.loader').fadeOut('slow');
	});
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
