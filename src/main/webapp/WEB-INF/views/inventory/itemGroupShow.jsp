<%@include file="../inventory/inventoryheader.jsp"%>
<!-- ---------------------End of Header-------------------------- -->



<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">

		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/inventory/listItemgroup.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Item
				Category List
			</a> <a
				href="${pageContext.request.contextPath}/inventory/editItemGroup.do?id=${selectItemGroup.id}"
				style="text-decoration: none;" class="btn btn-info btn-sm"> <span
				class="ace-icon fa fa-pencil bigger-130" aria-hidden="true"></span>
				Edit
			</a>
		</div>
		<h1 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
				Item Category Show</h1>
	</div>

	<div class="container-fluid">
		<div class="row"
			style="background-color: white; padding: 10px; margin: 10px;">

			<!-- --------------------- -->
			<form role="form">

				<div class="col-sm-12 table-responsive">
					<table class="table table-hover table-bordered">
						<tbody>
							<tr>
								<td class="col-sm-3 success" align="right"
									style="font-weight: bold;">Category Name:</td>
								<td class="col-sm-7"><input type="text"
									class="form-control"
									style="border: 0; border-bottom: 2px ridge;"
									name="categoryName" value="${selectItemGroup.categoryName}" readonly>
								</td>
							</tr>

							<tr>
								<td class="col-sm-3 success" align="right"
									style="font-weight: bold;">Category Id:</td>
								<td class="col-sm-7"><input type="text"
									class="form-control"
									style="border: 0; border-bottom: 2px ridge;" name="categoryId"
									value="${selectItemGroup.categoryId}" readonly></td>
							</tr>

							<tr>
								<td class="col-sm-3 success" align="right"
									style="font-weight: bold;">Category Id (Old):</td>
								<td class="col-sm-7"><input type="text"
									class="form-control"
									style="border: 0; border-bottom: 2px ridge;"
									name="oldCategoryId" value="${selectItemGroup.oldCategoryId}" readonly>
								</td>
							</tr>

							<tr>
								<td class="col-sm-3 success" align="right"
									style="font-weight: bold;">Item Type:</td>
								<td class="col-sm-7">${itemGroupList.itemType=='G' ? 'General Item':'Construction Item'}</td>
							</tr>

							<tr>
								<td class="col-sm-3 success" align="right"
									style="font-weight: bold;">Remarks:</td>
								<td class="col-sm-7"><input type="text"
									class="form-control"
									style="border: 0; border-bottom: 2px ridge;"
									name="itemCategoryRemarks"
									value="${selectItemGroup.itemCategoryRemarks}" readonly></td>
							</tr>

							<tr>
								<td class="col-sm-3 success" align="right"
									style="font-weight: bold;">Is Active?</td>
								<td class="col-sm-7"><label> <c:choose>
											<c:when test="${selectItemGroup.activeItemCategory==1}">
												<input type="checkbox" checked="checked" name="isActive"
													value="${selectItemGroup.activeItemCategory}">
											</c:when>
											<c:when test="${selectItemGroup.activeItemCategory==0}">
												<input type="checkbox" name="isActive"
													value="${selectItemGroup.activeItemCategory}">
											</c:when>

										</c:choose> Active
								</label> <input type="hidden" value="${selectItemGroup.id}"
									name="selectitemGroupId"></td>
							</tr>
						</tbody>
					</table>
				</div>
			</form>
			<!-- --------------------- -->
		</div>
	</div>
</div>


























<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>