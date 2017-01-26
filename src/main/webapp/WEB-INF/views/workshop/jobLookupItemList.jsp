<%@include file="../common/wsHeader.jsp"%>
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
			<a href="${pageContext.request.contextPath}/ws/lookupItemForm.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Create New Job Card Lookup Item
			</a>
		</div>

		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Job
			Card Lookup Item List</h1>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">
			<c:if test="${!empty successflag}">
				<div class="alert alert-success" id='updatealert'>
					<strong>Success!</strong> Record is removed.
				</div>
			</c:if>

			<c:if test="${!empty unsuccessflag}">
				<div class="alert alert-danger" id='deletealert'>
					<strong>Sorry!</strong> Record is not removed.
				</div>
			</c:if>

			<c:if test="${empty inventoryLookupItemList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<input type='hidden' value='${pageContext.request.contextPath}'
				id='contextPath'>
			<c:if test="${!empty inventoryLookupItemList}">
				<table id="inventoryLookupItemTable"
					class="table table-striped table-hover table-bordered">
					<thead>

						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<th>SL No.</th>
							<th>Item Code</th>
							<th>Item Name</th>
							<th>Unit</th>
							<th>Works Type</th>
							<th>Remarks</th>
							<th>Action</th>
						</tr>
					</thead>
					<tbody>

						<c:forEach items="${inventoryLookupItemList}"
							var="inventoryLookupItem" varStatus="loop">
							<tr>
								<td> ${loop.index+1}
								</td>
								<td>${inventoryLookupItem.itemCode}</td>
								<td>${inventoryLookupItem.itemName}</td>
								<td>${inventoryLookupItem.unit}</td>
								<td>${inventoryLookupItem.typeOfWork}</td>
								<td>${inventoryLookupItem.remarks}</td>
								<td class="center">
									<button class="btn btn-primary btn-sm" style="border-radius: 6px;"
										onclick="showConfirmation(${inventoryLookupItem.id})">
										<i class="fa fa-fw fa-times"></i>&nbsp;Delete
									</button>
								</td>
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
				"columnDefs" : [
					{
						"targets" : [ 0 ],
						"visible" : false
					},
					{
		                "targets": [ 5 ],
		                "visible": false
		            }
				],
				"order" : [ [ 1, 'asc' ] ]
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
		

	function showConfirmation(id) {
		var response = confirm("Do you want to delete this record?");
		if (response == true) {
			var contextPath = $('#contextPath').val();
			var path = contextPath + "/ws/deleteJobCardLookupItem.do";
			var param = {
				id : id
			}
			postSubmit(path, param, 'POST');
			//alert(contextPath);
		} else {
			//console.log( "null" );
		}
	}
</script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>