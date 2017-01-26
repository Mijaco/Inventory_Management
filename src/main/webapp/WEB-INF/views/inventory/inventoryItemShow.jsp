<%@include file="../inventory/inventoryheader.jsp"%>
<!-- ---------------------End of Header-------------------------- -->



<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/inventory/listInventoryItem.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Inventory Item List
			</a> <a
				href="${pageContext.request.contextPath}/inventory/editInventoryItem.do?id=${selectInventoryItem.id}"
				style="text-decoration: none;" class="btn btn-info btn-sm"> <span
				class="ace-icon fa fa-pencil bigger-130" aria-hidden="true"></span>
				Edit
			</a>
		</div>
		
		<h1 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
				Inventory Item Show</h1>
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
									style="font-weight: bold;">Item Group:</td>
								<td class="col-sm-7">
									<input type="text" class="form-control" id="categoryId" readonly="readonly"
										style="border: 0; border-bottom: 2px ridge;"  name="categoryId" value="${selectInventoryItem.categoryId}">
								</td>
							</tr>
							
							<tr>
								<td class="col-sm-3 success" align="right"
									style="font-weight: bold;">Item Name:</td>
								<td class="col-sm-7">
									<input type="text" class="form-control" id="itemName" readonly="readonly"
										style="border: 0; border-bottom: 2px ridge;" name="itemName"  value="${selectInventoryItem.itemName}">
								</td>
							</tr>
							
							<tr>
								<td class="col-sm-3 success" align="right"
									style="font-weight: bold;">Item Code:</td>
								<td class="col-sm-7">
									<input type="text" class="form-control" id="itemId" readonly="readonly"
										style="border: 0; border-bottom: 2px ridge;"  name="itemId" value="${selectInventoryItem.itemId}">
								</td>
							</tr>
							
							<tr>
								<td class="col-sm-3 success" align="right"
									style="font-weight: bold;">Old Item Code:</td>
								<td class="col-sm-7">
									<input type="text" class="form-control" id="oldItemCode" readonly="readonly"
										style="border: 0; border-bottom: 2px ridge;"  name="oldItemCode" value="${selectInventoryItem.oldItemCode}">
								</td>
							</tr>
							
							<tr>
								<td class="col-sm-3 success" align="right"
									style="font-weight: bold;">Unit:</td>
								<td class="col-sm-7">
									<input type="text" class="form-control" id="unitCode" readonly="readonly"
										style="border: 0; border-bottom: 2px ridge;" name = "unitCode" value="${selectInventoryItem.unitCode}">
								</td>
							</tr>
							
							<tr>
								<td class="col-sm-3 success" align="right"
									style="font-weight: bold;">Item Type:</td>
								<td class="col-sm-7">
									<input type="text" class="form-control" id="itemType" readonly="readonly"
										style="border: 0; border-bottom: 2px ridge;" name = "itemType" value="${selectInventoryItem.itemType}">
								</td>
							</tr>
							
							<tr>
								<td class="col-sm-3 success" align="right"
									style="font-weight: bold;">Remarks:</td>
								<td class="col-sm-7">
									<input type="text" class="form-control" id="inventoryItemUint" readonly="readonly"
										style="border: 0; border-bottom: 2px ridge;" name = "inventoryItemUint" value="${selectInventoryItem.remarks}">
								</td>
							</tr>
							
							<tr>
								<td class="col-sm-3 success" align="right"
									style="font-weight: bold;"></td>
								<td class="col-sm-7">
									<label>
										<c:choose>
											<c:when test="${selectInventoryItem.fixedAsset==1}">
												<input type="checkbox" checked="checked"
													onclick="return false" name="fixedAsset"
													value="${selectInventoryItem.fixedAsset}">
											</c:when>
											<c:when test="${selectInventoryItem.fixedAsset==0}">
												<input type="checkbox" name="fixedAsset"
													onclick="return false"
													value="${selectInventoryItem.fixedAsset}">
											</c:when>
										</c:choose> Fixed Assets
									</label>
									<br><br>
									<label>
										<c:choose>
											<c:when test="${selectInventoryItem.specialApproval==1}">
												<input type="checkbox" checked="checked"
													onclick="return false" name="specialApproval"
													value="${selectInventoryItem.specialApproval}">
											</c:when>
											<c:when test="${selectInventoryItem.specialApproval==0}">
												<input type="checkbox" name="specialApproval"
													onclick="return false"
													value="${selectInventoryItem.specialApproval}">
											</c:when>
										</c:choose> Special Item
									</label>
									<br> <br>
									<label>
										<c:choose>
											<c:when test="${selectInventoryItem.itemActive==1}">
												<input type="checkbox" checked="checked"
													onclick="return false" name="itemActive"
													value="${selectInventoryItem.itemActive}">
											</c:when>
											<c:when test="${selectInventoryItem.itemActive==0}">
												<input type="checkbox" name="itemActive"
													onclick="return false"
													value="${selectInventoryItem.itemActive}">
											</c:when>
										</c:choose>Is Active?
									</label>
								</td>
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