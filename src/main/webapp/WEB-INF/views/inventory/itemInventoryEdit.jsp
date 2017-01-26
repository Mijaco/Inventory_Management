<%@include file="../inventory/inventoryheader.jsp"%>
<!-- ---------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit col-md-3" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/inventory/listInventoryItem.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Inventory Item List
			</a> <a
				href="${pageContext.request.contextPath}/inventory/showInventoryById.do?id=${selectInventoryItem.id}"
				style="text-decoration: none;" class="btn btn-info btn-sm"> <span
				class="glyphicon glyphicon-eye-open" aria-hidden="true"></span> Show
			</a>
		</div>
		<div class="col-md-6">
			<h3 class="blue center">Item Edit Form</h3>
		</div>
	</div>
	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px;">
		<form action="/"
			method="POST" class="form-horizontal col-sm-offset-2 col-sm-8">
			<input type="hidden"  id="contextPath" value="${pageContext.request.contextPath}">
			<input type="hidden"  id="pk" value="${selectInventoryItem.id}">
			<div class="form-group">
				<label for="categoryId" class="col-sm-2 control-label">Item
					Group </label>
				<div class="col-sm-10">
					<select name="categoryId" id="categoryId" class="form-control">
						<option value="">Select an Category</option>
						<c:if test="${!empty itemGroupList}">
							<c:forEach items="${itemGroupList}" var="itemGroupList">
								<option value="${itemGroupList.categoryId}">
									<c:out value="${itemGroupList.categoryName}" />
								</option>
							</c:forEach>
						</c:if>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label for="itemName" class="col-sm-2 control-label">Item
					Name </label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="itemName"
						name="itemName" value="${selectInventoryItem.itemName}">
				</div>
			</div>
			<div class="form-group">
				<label for="itemId" class="col-sm-2 control-label">Item Code</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="itemId" name="itemId"
						value="${selectInventoryItem.itemId}">
				</div>
			</div>
			<div class="form-group">
				<label for="itemId" class="col-sm-2 control-label">Old Item Code</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="itemId" name="itemId"
						value="${selectInventoryItem.oldItemCode}">
				</div>
			</div>
			<div class="form-group">
				<label for="unitCode" class="col-sm-2 control-label">Unit</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="unitCode"
						name="unitCode" value="${selectInventoryItem.unitCode}">
				</div>
			</div>
			<div class="form-group">
				<label for="itemType" class="col-sm-2 control-label">Item
					Type</label>
				<div class="col-sm-10">
					<select name="itemType" id="itemType" class="form-control">
						<option value="C">Construction Item</option>
						<option value="G">General Item</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label for="remarks" class="col-sm-2 control-label">Remarks</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" id="remarks" name="remarks"
						value="${selectInventoryItem.remarks}">
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<div class="checkbox">
						<label for="fixedAsset"> <c:choose>
								<c:when test="${selectInventoryItem.fixedAsset==1}">
									<input type="checkbox" checked="checked" id="fixedAsset"
										name="fixedAsset">
								</c:when>								
								<c:otherwise>
							      <input type="checkbox" id="fixedAsset" name="fixedAsset">
							    </c:otherwise>
							</c:choose> Fixed Assets
						</label>
					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<div class="checkbox">
						<label for="specialApproval"> <c:choose>
								<c:when test="${selectInventoryItem.specialApproval==1}">
									<input type="checkbox" checked="checked" id="specialApproval"
										name="specialApproval">
								</c:when>								
								<c:otherwise>
							       <input type="checkbox" id="specialApproval"
										name="specialApproval">
							    </c:otherwise>
							</c:choose> Special Items
						</label>

					</div>
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<div class="checkbox">
						<label for="itemActive"> <c:choose>
								<c:when test="${selectInventoryItem.itemActive==1}">
									<input type="checkbox" checked="checked" id="itemActive"
										name="itemActive">
								</c:when>
								<c:otherwise>
							      <input type="checkbox" id="itemActive" name="itemActive">
							    </c:otherwise>
							</c:choose> Is Active?
						</label>

					</div>
				</div>
			</div>

			<div class="col-md-12" style="padding-top: 15px;">
				<div class="col-xs-1"></div>
				<div class="col-xs-12 col-sm-6">
					<button type="button" id="saveButton" onclick="updateItemMaster()"
						style="margin-right: 10px; border-radius: 6px;"
						class="pull-right btn btn-lg btn-success">
						<i class="ace-icon fa fa-save"></i> <span class="bigger-50">Update</span>
					</button>
				</div>
			</div>
		</form>

		<!-- 		-------- -->
	</div>
</div>


<script>
	$(function() {
		$("#categoryId").val("${selectInventoryItem.categoryId}");
		$("#itemType").val("${selectInventoryItem.itemType}");		
		$("label").css( "font-weight", "bold" );
	});
	
	function updateItemMaster() {
		var id = $('#pk').val();
		var categoryId = $('#categoryId').val();
		var itemName = $('#itemName').val();
		var itemId = $('#itemId').val();
		var unitCode = $('#unitCode').val();
		var itemType = $('#itemType').val();
		var remarks = $('#remarks').val();
		var itemActive = $("#itemActive").is( ":checked" )?1:0;
		var fixedAsset = $("#fixedAsset").is( ":checked" )?1:0;
		var specialApproval = $("#specialApproval").is( ":checked" )?1:0;		
		var path= $("#contextPath").val()+'/inventory/inventoryItemUpdate.do';
		
		var params={
				id: id,
				categoryId: categoryId,
				itemName: itemName,
				itemId: itemId,
				unitCode: unitCode,
				itemType: itemType,
				remarks: remarks,
				itemActive: itemActive,
				fixedAsset: fixedAsset,
				specialApproval: specialApproval
		}	
		postSubmit(path, params, 'POST') ;
	}
</script>


<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>