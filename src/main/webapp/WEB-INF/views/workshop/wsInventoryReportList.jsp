<%-- <%@include file="../common/wsContractorHeader.jsp"%> --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="${pageHeader}" />
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
		<c:if test="${!empty flag}">
			<a href="${pageContext.request.contextPath}/inventory/inventoryReportForm.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Create Inventory Report 
			</a>
			</c:if>
		</div>

		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Inventory Report List</h1>
	</div>

	<div class="row"
		style="background-color: white; padding-left: 10px; margin: 10px; padding-bottom: 10px;">
		<!-- --------------------- -->
					<input type='hidden' value='${pageContext.request.contextPath}'
				id='contextPath'>
			<c:if test="${!empty wsInventoryMst}">
				<table id="inventoryLookupItemTable"
					class="table table-striped table-hover table-bordered">
					<thead>

						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<th>Inventory Report No.</th>
							<th>Inventory Date</th>
							<th>Transformer Serial No</th>
							<th>Manufacture Name</th>
							<th>Works Type</th>
						</tr>
					</thead>
					<tbody>

						<c:forEach items="${wsInventoryMst}"
							var="inventoryMst" varStatus="loop">
							<tr>
								<%-- <td> ${loop.index+1}
								</td> --%>
								<td><a
									href="${pageContext.request.contextPath}/inventory/wsInventoryReportShow.do?id=${inventoryMst.id}&&wsInventoryNo=${inventoryMst.wsInventoryNo}"
									style="text-decoration: none;"> <c:out
											value="${inventoryMst.wsInventoryNo}" /></a>
											</td>
								
								<td>${inventoryMst.inventoryDate}</td>
								<td>${inventoryMst.transformerSerialNo}</td>
								<td>${inventoryMst.manufacturedName}</td>
								<td>${inventoryMst.typeOfWork}</td>
								
							</tr>


						</c:forEach>
					</tbody>
				</table>
			</c:if>
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
				
				"order" : [ [ 1, 'desc' ] ]
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
		

	
</script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>