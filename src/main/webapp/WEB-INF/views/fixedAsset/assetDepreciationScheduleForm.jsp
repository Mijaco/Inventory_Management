<%@include file="../common/faHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->


<style>
.loader {
	position: fixed;
	left: 50%;
	top: 50%;
	z-index: 9999;
}

.ui-widget-overlay {
	opacity: .6 !important;
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
		<div class="o_form_buttons_edit" style="display: block;"></div>

		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Fixed Asset List</h1>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">

			<c:if test="${empty fixedAssetReceives}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<input type='hidden' value='${pageContext.request.contextPath}'
				id='contextPath'>
			<c:if test="${!empty fixedAssetReceives}">
				<table id="inventoryLookupItemTable"
					class="table table-striped table-hover table-bordered">
					<thead>

						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<th>Serial No.</th>	
							<th>Asset Id</th>
							<th>Asset Name</th>							
							<th>Receive Date</th>
							<th>Location</th>
							<th>Quantity</th>
							<th>Total Price</th>
						</tr>
					</thead>
					<tbody>
 <!-- href="javascript:void(0)" -->
						<c:forEach items="${fixedAssetReceives}" var="fixedAssetReceive"
							varStatus="status">
								<tr><td class="col-xs-1"><a onclick="redirectURL('${fixedAssetReceive.fixedAssetId}','${fixedAssetReceive.serialNumber}','${fixedAssetReceive.faRegKey}')" href="javascript:void(0)" style="text-decoration: none;">${fixedAssetReceive.serialNumber}</a></td>
							<td>${fixedAssetReceive.fixedAssetId}</td>								
								<td class="col-xs-3">${fixedAssetReceive.fixedAssetName}</td>
								<td class="col-xs-1"><input type="hidden"
									value="${fixedAssetReceive.fixedAssetId}" id="assetId${status.index}" />
									<input type="hidden"
									value="${fixedAssetReceive.remarks}" id="flag${status.index}" /><input type="hidden"
									value="${fixedAssetReceive.purchaseDate}" id="receiveDate${status.index}" /><fmt:formatDate
					value="${fixedAssetReceive.purchaseDate}" pattern="dd-MM-yyyy" /></td>
								<td class="col-xs-2"><input type="hidden"
									value="${fixedAssetReceive.locationId}" id="locationId${status.index}" />${fixedAssetReceive.locationId}</td>
								<td>${fixedAssetReceive.quantity}</td>
								<td><input type="hidden"
									value="<fmt:formatNumber groupingUsed="false" type="number" value="${fixedAssetReceive.totalPrice}"/>" id="totalPrice${status.index}" /><fmt:formatNumber type="number" 
             groupingUsed="false" value="${fixedAssetReceive.totalPrice}" /></td>
								
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
		</div>
	</div>
</div>


<script>
	$(window).load(function() {
		$('.loader').fadeOut('slow');
	});
</script>

<script
	src="${pageContext.request.contextPath}/resources/assets/js/jquery.dataTables.min.js"></script>

<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.bootstrap.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.tableTools.min.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/assets/js/dataTables.colVis.min.js"></script>
<script>
	$(document)
			.ready(
					function() {
						$('#inventoryLookupItemTable').DataTable({

							"order" : [ [ 2, 'desc' ] ]
						});
						document
								.getElementById('inventoryLookupItemTable_length').style.display = 'none';
						//document.getElementById('inventoryLookupItemTable_filter').style.display = 'none';

						$(".alert.alert-success").fadeTo(4000, .5).slideUp(500,
								function() {
									//  $(".alert.alert-success").alert('close');
								});

						$(".alert.alert-danger").fadeTo(4000, .5).slideUp(500,
								function() {
									//  $(".alert.alert-danger").alert('close');
								});
					});

	
	function redirectURL(id,serialNumber,faRegKey) {
		alert(serialNumber);
		var baseURL = $('#contextPath').val();
		var path = baseURL + "/fixedAssets/depSchedule.do";
		var param = {
				'fixedAssetId' : id,
				'serialNumber' : serialNumber,
				//'remarks' : remarks,
				//'totalPrice' : totalPrice,
				'faRegKey' : faRegKey
		}
		postSubmit(path, param, "POST");
	}
</script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>