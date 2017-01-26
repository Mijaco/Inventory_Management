<%@include file="../common/wsHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->
<style>
.bold {
	font-weight: bold;
}
</style>

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/ws/jobCardLookupItemList.do" style="text-decoration: none;"
				class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Job
				Card Template List
			</a>
		</div>

		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Add
			New Item For Workshop Job Card</h1>
	</div>

	<div class="col-sm-offset-2 col-sm-8"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<!-- --------------------- -->
		<div class="col-sm-12 table-responsive">

			<form method="post"
				action="${pageContext.request.contextPath}/ws/jobLookupItemSave.do">
				<!-- Block :: start -->
				<input type="hidden" id="contextPath"
					value="${pageContext.request.contextPath}" />

				<div class="table-responsive">
					<table class="table  table-hover table-bordered" id="">
						<tbody>
							<tr>
								<td class="bold col-sm-4 success text-right">Item Category:&nbsp;<strong class='red'>*</strong></td>
								<td class="col-sm-8"><select class="form-control"
									id="category" onchange="categoryLeaveChange(this)"
									style="width: 100%; border: 0; border-bottom: 2px ridge;">
										<option disabled selected>Select Category</option>
										<c:if test="${!empty itemCategoryList}">
											<c:forEach items="${itemCategoryList}" var="category">
												<option value="${category.categoryId}">
													<c:out value="${category.categoryName}" /></option>
											</c:forEach>
										</c:if>
								</select></td>
							</tr>
							<tr>
								<td class="bold col-sm-4 success text-right">Item Name:&nbsp;<strong class='red'>*</strong></td>
								<td class="col-sm-8"><input type="hidden" name="itemName"
									id="itemName" /> <select class="form-control"
									onchange="itemLeaveChange(this)" id="iName"
									style="border: 0; border-bottom: 2px ridge;">
										<option disabled selected>Select Item Name</option>
								</select></td>
							</tr>
							<tr>
								<td class="bold col-sm-4 success text-right">Item Code :</td>
								<td class="col-sm-8"><input
									style="width: 100%; border: 0; border-bottom: 2px ridge;"
									type="text" id="itemCode" name="itemCode"></td>
							</tr>
							<tr>
								<td class="bold col-sm-4 success text-right">Unit:</td>
								<td class="col-sm-8"><input
									style="width: 100%; border: 0; border-bottom: 2px ridge;"
									type="text" id="unit" name="unit"></td>
							</tr>
							<tr>
								<td class="bold col-sm-4 success text-right">Type of Work:&nbsp;<strong class='red'>*</strong></td>
								<td class="col-sm-8">
									<!-- <input style="width: 100%; border: 0; border-bottom: 2px ridge;"
								type="text" id="" name=""> --> <select
									style="width: 100%; border: 0; border-bottom: 2px ridge;"
									class="form-group" name="typeOfWork" id="typeOfWork">
										<option selected="selected" disabled="disabled">--
											Select a Type --</option>
										<option value="REPAIR WORKS">Repair Works</option>
										<option value="PREVENTIVE MAINTENANCE">Preventive
											Works</option>
								</select>
								</td>
							</tr>
						</tbody>
					</table>
					<div class="col-md-12" style="padding-top: 15px;">
						<div class="col-xs-12 col-sm-6">
							<button type="submit" id="saveButton"
								style="margin-right: 10px; border-radius: 6px;"
								class="pull-right btn btn-lg btn-success">
								<i class="ace-icon fa fa-save"></i> <span class="bigger-50">Save</span>
							</button>
						</div>

						<div class="col-xs-12 col-sm-6">
							<button type="reset" class="pull-left btn btn-lg btn-danger"
								style="margin-left: 10px; border-radius: 6px;">
								<i class="ace-icon fa fa-refresh"></i> <span class="bigger-50">Reset</span>
							</button>
						</div>
					</div>
				</div>
				<!-- Block :: end -->
			</form>
		</div>
	</div>

</div>

<script type="text/javascript">
	function categoryLeaveChange(element) {
		var categoryId = $("#category").val();
		var contextPath = $("#contextPath").val();
		$
				.ajax({
					url : contextPath
							+ '/cs/itemRecieved/viewInventoryItemCategory.do',
					data : "{categoryId:" + categoryId + "}",
					contentType : "application/json",
					success : function(data) {
						var itemList = JSON.parse(data);
						var itemNames = $("#iName");
						itemNames.empty();
						itemNames.append($("<option></option>").attr("value",
								'').text('Select Item'));
						$.each(itemList, function(itemId, itemName) {
							itemNames.append($("<option></option>").attr(
									"value", this.id).text(this.itemName+" ["+this.itemId+"]"));
						});
					},
					error : function(data) {
						alert("Server Error");
					},
					type : 'POST'
				});
	}

	function itemLeaveChange(element) {
		var item_id = $("#iName").val();
		var contextPath = $("#contextPath").val();

		$.ajax({
			url : contextPath + '/cs/itemRecieved/viewInventoryItem.do',
			data : "{id:" + item_id + "}",
			contentType : "application/json",
			success : function(data) {
				var inventoryItem = JSON.parse(data);
				$("#itemName").val(inventoryItem.itemName);
				$("#itemCode").val(inventoryItem.itemId);
				$("#unit").val(inventoryItem.unitCode);

			},
			error : function(data) {
				alert("Server Error");
			},
			type : 'POST'
		});
	}
</script>


<link
	href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
	rel="stylesheet">


<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>