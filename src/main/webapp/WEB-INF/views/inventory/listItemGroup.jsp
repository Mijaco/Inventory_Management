
<%@include file="../inventory/inventoryheader.jsp"%>
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
		<div class="col-sm-2 o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/inventory/showitemgroup.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Create Item Group
			</a>
		</div>
		<h2 class="col-sm-8 center blue"
			style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
			Item Category List</h2>
	</div>

	<div class="row" 
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px; margin-left: 15px; margin-right: 15px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">
			<c:if test="${empty itemGroupList}">
				<div class="col-sm-12 center">
					<p class="red">
						<i>Sorry!!! No Data Found in Database. </i>
					</p>
				</div>
			</c:if>
			<c:if test="${!empty itemGroupList}">
				<%-- <div class="col-sm-6 pull-right">
					<form method="POST"
						action="${pageContext.request.contextPath}/inventory/searchByGroupName.do">

						<div class="form-group col-sm-9 col-sm-offset-2">
							<input type="text" class="form-control" id="search"
								placeholder="Search by Item Category By Category Name."
								style="border: 0; border-bottom: 2px ridge;" name="categoryName" />
						</div>
						<div class="col-sm-1">
							<button type="submit"
								class="btn btn-info glyphicon glyphicon-search"></button>
						</div>
					</form>
				</div> --%>
				
				
				<table id="itemGroupList"
					class="table table-striped table-hover table-bordered">

					<thead>
						<tr
							style="background: #579EC8; color: white; font-weight: normal;">
							<td style="">Category Code</td>
							<td style="">Category Name</td>
							<td style="">Item Type</td>
							<td style="">Old Code</td>
							<td style="" class="col-xs-2">Action</td>

						</tr>
					</thead>

					<tbody>
						<c:forEach items="${itemGroupList}" var="itemGroupList">
							<tr>
								<%-- <td><a
									href="${pageContext.request.contextPath}/inventory/showItemGroup.do?itemGroupId=${itemGroupList.itemGroupName}"
									style="text-decoration: none;"></a></td> --%>
								<td><c:out value="${itemGroupList.categoryId}" /></td>
								<td><c:out value="${itemGroupList.categoryName}" /></td>
								<td>${itemGroupList.itemType=='G' ? 'General Item':'Construction Item'}</td>
								<td><c:out value="${itemGroupList.oldCategoryId}" /></td>

								<td>
									<div class="action-buttons">
										<a class="btn btn-primary btn-xs" style="border-radius: 6px;"
											href="${pageContext.request.contextPath}/inventory/editItemGroup.do?id=${itemGroupList.id}">
											<i class="ace-icon fa fa-pencil bigger-130"></i>&nbsp;Edit
										
										</a> <a class="btn btn-success btn-xs" style="border-radius: 6px;"
											href="${pageContext.request.contextPath}/inventory/showItemGroup.do?id=${itemGroupList.id}">
											<i class="fa fa-eye"></i>&nbsp;Show
										</a>
									</div>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:if>
			<!-- --------------------- -->




			<!-- --------------------- -->


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
		$('#itemGroupList').DataTable();
		document.getElementById('itemGroupList_length').style.display = 'none';
		// document.getElementById('itemGroupList_filter').style.display = 'none';

	});
</script>

<script>
	$(window).load(function() {
		$('.loader').fadeOut('slow');
	});
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
