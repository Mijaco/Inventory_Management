<%@include file="../inventory/inventoryheader.jsp"%>
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
	<i class="fa fa-refresh fa-spin fa-3x fa-fw"></i> <br> <span
		class="orange">Loading...</span>
</div>
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/inventory/showInventoryItem.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Create Inventory Item
			</a>
		</div>
		<h1 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
				Inventory Item List</h1>
	</div>

	<div class="row" style="background-color: white; padding: 10px; margin: 10px;">
		<!-- --------------------- -->
		<div class="alert alert-success hide">
			<!-- <a href="javascript:void(0)" class="close" data-dismiss="alert" aria-label="close"><strong style="font-size:1.3em;">&times;</strong></a>  -->
			<strong>Success!</strong> Item information is updated.
		</div>
		<div class="alert alert-danger hide">
			<!--<a href="#" class="close" data-dismiss="alert" aria-label="close"><strong style="font-size:1.3em;">&times;</strong></a>  -->
			<strong>Fail!</strong> Item information is not updated.
		</div>
		<div class="col-sm-12 table-responsive">
			<c:if test="${empty inventoryItemList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty inventoryItemList}">
				<div class="col-sm-6 pull-right">
					<form method="POST"
						action="${pageContext.request.contextPath}/inventory/searchByInventoryItemName.do">
						<div class="form-group col-sm-9 col-sm-offset-2">
							<input type="text" class="form-control" id="search"
								placeholder="Search by Item Name."
								style="border: 0; border-bottom: 2px ridge;"
								name="itemNameForSearch" />
						</div>
						<div class="col-sm-1">
							<button type="submit"
								class="btn btn-info glyphicon glyphicon-search"></button>
						</div>
					</form>
				</div>
				
				<input type='hidden' value='${pageContext.request.contextPath}' id='contextPath'>
				
				<table id="inventoryItemList"
					class="table table-striped table-hover table-bordered">

					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Item Category</td>
							<td style="">Item Code</td>
							<td style="">Item Name</td>
							<td style="">UOM</td>
							<!-- <td style="">Special Item</td> -->
							<td style="">Fixed Asset ?</td>
							<td style="">Item Type</td>
							<td style="">Update</td>
							<td style="" class="col-xs-2">Action</td>
						</tr>
					</thead>
					
					<tbody>
						<c:forEach items="${inventoryItemList}" var="inventoryItemList"
							varStatus="loop">
							<tr>
								<%-- <td><a
									href="${pageContext.request.contextPath}/inventory/showOpeningBalance.do?id=${inventoryItemList.inventoryItemId}"
									style="text-decoration: none;"></a></td> --%>
								<td><c:out value="${inventoryItemList.categoryId}" /></td>
								<td><c:out value="${inventoryItemList.itemId}" /></td>
								<td><c:out value="${inventoryItemList.itemName}" /></td>
								<td><c:out value="${inventoryItemList.unitCode}" /></td>
								<%-- <td><c:out value="${inventoryItemList.specialApproval}" /></td> --%>
								<td><input name="pk" id="pk${loop.index}" class="pk"
									type="hidden" value="${inventoryItemList.id}" /> <select
									name="fixedAsset" id="fixedAsset${loop.index}"
									class="form-control fixedAsset">
										<c:choose>
											<c:when test="${inventoryItemList.fixedAsset==1}">
												<option value="1" selected="selected">Yes</option>
												<option value="0">No</option>
											</c:when>
											<c:otherwise>
												<option value="1">Yes</option>
												<option value="0" selected="selected">No</option>
											</c:otherwise>
										</c:choose>
								</select></td>
								<td><select name="itemType" id="itemType${loop.index}"
									class="itemType form-control">
										<option value=""
											${inventoryItemList.itemType == '' ? 'selected' : ''}>Select
											Type</option>
										<option value="C"
											${inventoryItemList.itemType == 'C' ? 'selected' : ''}>Construction
											Item</option>
										<option value="G"
											${inventoryItemList.itemType == 'G' ? 'selected' : ''}>General
											Item</option>

								</select></td>
								<td>
									<div class="action-buttons center">
										<a onclick="updateItemInfo('${loop.index}')"
											title="Update Item Info" href="JavaScript:void(0)"> <i
											class="glyphicon glyphicon-floppy-open blue bigger-130"></i></a>
									</div>
								</td>
								<td>
									<div class="action-buttons">
										<a class="btn btn-primary btn-xs" style="border-radius: 6px"
											href="${pageContext.request.contextPath}/inventory/editInventoryItem.do?id=${inventoryItemList.id}">
											<i class="ace-icon fa fa-edit"></i>&nbsp;Edit
										</a> <a class="btn btn-success btn-xs" style="border-radius: 6px"
											href="${pageContext.request.contextPath}/inventory/showInventoryById.do?id=${inventoryItemList.id}">
											<i class="fa fa-eye"></i>&nbsp;Show
										</a>
									</div>
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
	$(document)
			.ready(
					function() {
						$('#inventoryItemList').DataTable();
						document.getElementById('inventoryItemList_length').style.display = 'none';
						document.getElementById('inventoryItemList_filter').style.display = 'none';

					});

	function updateItemInfo(index) {
		var pk = $("#pk" + index).val();
		var fixedAsset = $("#fixedAsset" + index).val();
		var itemType = $("#itemType" + index).val();

		var param = {
			id : pk,
			fixedAsset : fixedAsset,
			itemType : itemType
		}
		var path = $('#contextPath').val() + "/inventory/updateItemByAjax.do";
		var cDataJsonString = JSON.stringify(param);
		$.ajax({
			url : path,
			data : cDataJsonString,
			contentType : "application/json",
			success : function(data) {
				var pData = JSON.parse(data);
				if (pData == 'success') {
					$('.alert.alert-success').removeClass('hide');
					$(".alert.alert-success").fadeTo(2000, 500).slideUp(500,
							function() {
								$(".alert.alert-success").alert('close');
							});
				} else {
					$('.alert.alert-danger').removeClass('hide');
					$(".alert.alert-danger").fadeTo(2000, 500).slideUp(500,
							function() {
								$(".alert.alert-danger").alert('close');
							});
				}

			},
			error : function(data) {
				alert("Server Error");
			},
			type : 'POST'
		});

	}
</script>

<script>
	$(window).load(function() {
		$('.loader').fadeOut('slow');
	});
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
