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

		<h2 class="center blue"
			style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
			Average Price List</h2>
	</div>


	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px;">

		<div class="col-sm-12 table-responsive">
			<c:if test="${empty avgPriceList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty avgPriceList}">
				<%-- <div class="col-sm-6 pull-right">
					<form method="POST" action="">
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
				</div> --%>
				<table id="avgPriceList"
					class="table table-striped table-hover table-bordered">
					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<!-- <td style="">ID</td> -->
							<td style="" width="10%">Item Code</td>
							<td style="" width="50%">Item Name</td>
							<!-- <td style="">UOM</td> -->
							<td style="" width="20%">Average Price (TK)</td>
							<td style="" width="20%">Last Purchases Price (TK)</td>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${avgPriceList}" var="item" varStatus="loop">
							<tr>

								<%-- <td><c:out value="${item.id}" /></td> --%>
								<td><c:out value="${item.itemCode}" /></td>
								<td><c:out value="${item.itemName}" /></td>
								<%-- <td><c:out value="${item.unitCode}" /></td> --%>
								<td><c:out value="${item.price}" /></td>
								<td><c:out value="${item.last_pur_item_price}" /></td>

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
		$('#avgPriceList').DataTable({
			"order" : [ [ 0, 'asc' ] ]
		});

		// inventory/listOpeningBalance
		document.getElementById('avgPriceList_length').style.display = 'none';
		// document.getElementById('avgPriceList_filter').style.display = 'none';
	});
</script>

<script>
	$(window).load(function() {
		$('.loader').fadeOut('slow');
	});
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
