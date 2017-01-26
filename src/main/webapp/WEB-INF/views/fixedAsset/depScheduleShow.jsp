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

		
<table class="table table-bordered">
<h3 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;"> Depreciation Schedule</h3>		
<h4 class="left blue" style="margin-top: 0; font-style: bold; font-family: 'Ubuntu Condensed', sans-serif;">Serial Number : ${serialNumber}</h4>
<h4 class="left blue" style="margin-top: 0; font-style: bold; font-family: 'Ubuntu Condensed', sans-serif;">Asset ID : ${itemId}</h4>			
<h4 class="left blue" style="margin-top: 0; font-style: bold; font-family: 'Ubuntu Condensed', sans-serif;">Asset Name : ${itemName}</h4>			
			<thead>

						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<th class="col-xs-1">Purchase Date</th>
							<th class="col-xs-2">Purchase Price</th>
							<th class="col-xs-1">Session Year</th>
							<th class="col-xs-2">Depreciation Upto Last Year</th>							
							<th class="col-xs-2">Current Year Depreciation</th>
							<th class="col-xs-2">CuDepreciation</th>
							<th class="col-xs-2">Written Down Value</th>
						</tr>
					</thead>
					<tbody>
<c:forEach items="${depreciationSummeryList}" var="depnSummeryList"
							varStatus="status">
							<tr>
								<td class="col-xs-1"><fmt:formatDate value="${depnSummeryList.purchaseDate}" pattern="dd-MM-yyyy" /></td>
								<td class="col-xs-2"><fmt:formatNumber groupingUsed="false" type="number" value="${depnSummeryList.purchasePrice}"/></td>
								<td class="col-xs-1">${depnSummeryList.sessionYear}</td>
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