<%@include file="../common/faHeader.jsp"%>
<!-- -------------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 0 10px; padding-left: 20px">

		<input type="hidden" id="birtAddr" value="${properties['birtAddr']}" />
		<input type="hidden" id="contextPath"
			value="${pageContext.request.contextPath}" />

		<div class="">
			<table class="table table-bordered table-hover">
				<tr>
					<td colspan="5"
						style="font-weight: normal; font-size: 18px; text-align: center; font-family: 'Ubuntu Condensed', sans-serif;">
						Asset Purchased Report</td>
				</tr>
				<tr>
					<td class="col-xs-1 success text-right" style="font-weight: bold;">Select
						Category:</td>
					<td class="col-xs-2"><select class="form-control"
						onchange="categoryLeaveChange()" id="category" required="required">
							<option value="">Select Category</option>
							<c:forEach items="${itemCategoryList}" var="category">
								<option value="${category.categoryId}">${category.categoryName}</option>
							</c:forEach>
					</select></td>

					<td class="col-xs-1 success text-right" style="font-weight: bold;">Item
						Name:</td>
					<td class="col-xs-2"><select id="itemName" required="required"
						required="required" class="form-control">
							<option value="">Select Item</option>
					</select></td>

					<td class="col-xs-2 center"><button
							class="btn btn-primary btn-sm" style="border-radius: 6px;"
							id="queryBtn" onclick="query()">
							<i class="fa fa-search"></i> Query
						</button></td>
				</tr>

			</table>
		</div>

	</div>

	<div class="row" style="background: white;">
		<div class="col-sm-12 table-responsive">
			<!-- --------------------- -->

			<div class="col-xs-12">
				<iframe frameborder="0" class="col-md-12"
					style="margin: 10px 0 10px 0; height: 410px;"
					id="requisitionReportFrame">
					<p>Your browser does not support iframes.</p>
				</iframe>
			</div>

			<!-- --------------------- -->
		</div>
	</div>
</div>


<script type="text/javascript">
	var birtAddr = document.getElementById("birtAddr").value;

	function query() {
		var param1 = document.getElementById("itemName").value;
		document.getElementById('requisitionReportFrame').src = birtAddr
				+ '?__report=desco/procurement/asset_purchased_report.rptdesign&__toolbar=false&__showtitle=false&__title=&P_ITEM_ID='
				+ param1;
	}
	// Select * item Category
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
						var itemNames = $("#itemName");
						itemNames.empty();
						itemNames.append($("<option></option>").attr("value",
								'').text('Select Item'));
						$.each(itemList, function(itemId, itemName) {
							itemNames.append($("<option></option>").attr(
									"value", this.itemId).text(this.itemName));
						});
					},
					error : function(data) {
						alert("Server Error");
					},
					type : 'POST'
				});
	}
</script>
<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
