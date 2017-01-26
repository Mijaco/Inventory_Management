<%@include file="../common/faHeader.jsp"%>
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
			
		</div>

		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Asset Details</h1>
	</div> 
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<!-- --------------------- -->
		<button title="Back" onclick="window.history.back()"><span class="fa fa-hand-o-left fa-2x blue"></span></button>		
		<div class="col-sm-12 table-responsive">
<table class="table table-bordered">
<h3 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Asset Basic Information</h3>
<tr>
								<td class="col-xs-2 success">Asset ID:</td>
								<td class="col-xs-4 info">${itemId}</td>
								<td class="col-xs-2 success">Asset Name</td>
								<td class="col-xs-4 info">${itemName}</td>
							</tr>
							<tr>
								<td class="col-xs-2 success">Model Number</td>
								<td class="col-xs-4 info">${fixedAsset2.modelNumber}</td>
								<td class="col-xs-2 success">Brand Name</td>
								<td class="col-xs-4 info">${fixedAsset2.brandName}</td>
							</tr>
							<tr>
								<td class="col-xs-2 success">Serial Number</td>
								<td class="col-xs-4 info">${serialNumber}</td>
								<td class="col-xs-2 success">Location</td>
								<td class="col-xs-4 info">${locationName}</td>
							</tr>
							<tr>
								<td class="col-xs-2 success">Quantity</td>
								<td class="col-xs-4 info">${quantity}</td>
								<td class="col-xs-2 success">Asset Status</td>
								<td class="col-xs-4 info"><c:if test="${fixedAsset.writeOff=='0'}">OPEN</c:if></td>
							</tr>
</table>
			<table class="table table-bordered">
<h3 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Goods Received Information</h3>
<tr align="center">
								<td class="col-xs-2 success">GRN No:</td>
								<td class="col-xs-4 info">${serialNumber}</td>
								<td class="col-xs-2 success">Receive Date</td>
								<td class="col-xs-4 info"><fmt:formatDate
					value="${receiveDate}" pattern="dd-MM-yyyy" /></td>
							</tr>
							<tr>
								<td class="col-xs-2 success">Item Name</td>
								<td class="col-xs-4 info">${itemName}</td>
								<td class="col-xs-2 success">Cost</td>
								<td class="col-xs-4 info"><fmt:formatNumber groupingUsed="false" type="number" value="${totalPrice}"/></td>
							</tr>
							<tr>
								<td class="col-xs-2 success">Depreciation Value</td>
								<td class="col-xs-4 info">${depreciationSummery.depCurrentYear}</td>
								<td class="col-xs-2 success">Written Down Value</td>
								<td class="col-xs-4 info">${depreciationSummery.writtenDownValue}</td>
							</tr>
							<tr>
								<td class="col-xs-2 success">Unit</td>
								<td class="col-xs-4 info">${serialNumber}</td>
								<td class="col-xs-2 success">Quantity</td>
								<td class="col-xs-4 info">${quantity}</td>
							</tr>
</table>
		<table class="table table-bordered">
<h3 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Asset Depreciation Information</h3>
<tr align="center">
								<td class="col-xs-2 success">Depreciation Method:</td>
								<td class="col-xs-4 info">Straight Line</td>
								<td class="col-xs-2 success">Depreciation Ratio (Yearly)</td>
								<td class="col-xs-4 info">${itemMaster.depreciationRate}</td>
							</tr>
							<tr>
								<td class="col-xs-2 success">Depreciation Period</td>
								<td class="col-xs-4 info">${depreciationSummery.sessionYear}</td>
								<td class="col-xs-2 success">Life Time</td>
								<td class="col-xs-4 info">${itemMaster.lifeTime}</td>
							</tr>
							
</table>

<table class="table table-bordered">
<h3 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;"> Depreciation Schedule</h3>
			<thead>

						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<th>Purchase Date</th>
							<th>Purchase Price</th>
							<th>Session Year</th>
							<th>Depreciation Upto Last Year</th>							
							<th>Current Year Depreciation</th>
							<th>CuDepreciation</th>
							<th>Written Down Value</th>
						</tr>
					</thead>
					<tbody>
<c:forEach items="${depreciationSummeryList}" var="depnSummeryList"
							varStatus="status">
							<tr>
								<td class="col-xs-2"><fmt:formatDate value="${depnSummeryList.purchaseDate}" pattern="dd-MM-yyyy" /></td>
								<td class="col-xs-2"><fmt:formatNumber groupingUsed="false" type="number" value="${depnSummeryList.purchasePrice}"/></td>
								<td class="col-xs-2">${depnSummeryList.sessionYear}</td>
								<td class="col-xs-2"><fmt:formatNumber groupingUsed="false" type="number" value="${depnSummeryList.depUptoLastYear}"/></td>
								<td class="col-xs-2">${depnSummeryList.depCurrentYear}</td>
								<td class="col-xs-2"><fmt:formatNumber groupingUsed="false" type="number" value="${depnSummeryList.cuDepreciation}"/></td>
								<td class="col-xs-2"><fmt:formatNumber groupingUsed="false" type="number" value="${depnSummeryList.writtenDownValue}"/></td>
							</tr>
							</c:forEach>
							</tbody>
</table>		
		</div>
	</div>
</div>

<script>
	$(window).load( function() {
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
			document.getElementById('inventoryLookupItemTable_length').style.display = 'none';
			//document.getElementById('inventoryLookupItemTable_filter').style.display = 'none';
			
			
			$(".alert.alert-success").fadeTo(4000, .5).slideUp(500, function() {
				  //  $(".alert.alert-success").alert('close');
			});
			
			$(".alert.alert-danger").fadeTo(4000, .5).slideUp(500, function() {
				  //  $(".alert.alert-danger").alert('close');
			});
		});
		
function redirectURL(id) {
	var baseURL = $('#contextPath').val();
	var path = baseURL + "/fixedAssets/depShow.do";
	var param = {
			'id' : id
	}
	postSubmit(path, param, "POST");
}
</script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>