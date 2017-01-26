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
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Asset
			Receive</h1>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<!-- --------------------- -->
		<form
			action="${pageContext.request.contextPath}/fixedAssets/saveFixedAssetAccHead.do" method="POST">
		<div class="table-responsive">
			<table class="table table-bordered">
				<tbody>
				<tr>
						<td class="col-xs-2 success">A/c Head Code
							</td>
						<td class="col-xs-4 info"><input type="text" class="form-control" id="assetCategoryCode"
								style="border: 0; border-bottom: 2px ridge;" name="assetCategoryCode"
								value="" /></td>
						<td class="col-xs-2 success">A/c Head Name
							</td>
						<td class="col-xs-4 info"><input type="text" class="form-control" id="assetCategoryName"
								style="border: 0; border-bottom: 2px ridge;" name="assetCategoryName"
								value="" /></td>
						
					</tr>
					<tr>
						
						<td class="col-xs-2 success">Item Category Name</td>
						<td class="col-xs-4 info">
							<select class="form-control itemCategoryCode" id="itemCategoryCode"
								style="border: 0; border-bottom: 2px ridge;" name="itemCategoryCode">
																		<option value="">Category</option>
																		<c:if test="${!empty categoryList}">
																			<c:forEach items="${categoryList}" var="category">
																				<option value="${category.categoryId}">
																					<c:out value="${category.categoryName}" /></option>
																			</c:forEach>
																		</c:if>
																	</select>
							</td>
						<td class="col-xs-2 success">
							</td>
						<td class="col-xs-4 info"></td>
					</tr>
					
					
					<tr>
						<td class="col-xs-2 success"></td>
						<td class="col-xs-4 info"></td>
						<td class="col-xs-2 success"></td>
						<td class="col-xs-4 info">	
						<button type="submit"
							style="margin-right: 10px; border-radius: 6px;"
							class="width-20 pull-left btn btn-lg btn-success">
							<i class="ace-icon fa fa-save"></i> <span class="bigger-50">Save</span>
						</button>
						<button type="reset"
							class="width-20  pull-left btn btn-lg btn-danger"
							style="margin-left: 10px; border-radius: 6px;">
							<i class="ace-icon fa fa-refresh"></i> <span class="bigger-50">Reset</span>
						</button></td>
						</tr>
				</tbody>
			</table>
		</div>
		</form>
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

</script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>