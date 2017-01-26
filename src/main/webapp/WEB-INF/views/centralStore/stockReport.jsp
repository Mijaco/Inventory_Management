<%@include file="../common/csHeader.jsp"%>
<!-- -------------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<h1 class="center blue"
			style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Stock
			Report</h1>
	</div>

	<div class="row" style="background: white;">
		<div class="col-sm-12 table-responsive">
			<!-- --------------------- -->

			<div class="form-group col-xs-4">
				<div class="form-group col-xs-4">
					<label for="param1">Khath Name :</label> <input type="text"
						id="birtAddr" value="${properties['birtAddr']}" hidden="true" />
					<input type="hidden" id="contextPath"
						value="${pageContext.request.contextPath}" />
				</div>
				<div class="col-xs-8">
					<select class="form-control" name="param1" id="param1" TABINDEX=1
						style="border: 0; border-bottom: 2px ridge;">
						<c:if test="${!empty khathList}">
							<c:forEach items="${khathList}" var="khath">
								<option value="${khath.id}">
									<c:out value="${khath.khathName}" /></option>
							</c:forEach>
						</c:if>
					</select>
				</div>
			</div>

			<div class="form-group col-xs-3">
				<select class="form-control category" id="category"
					onchange="categoryLeaveChange(this)"
					style="border: 0; border-bottom: 2px ridge;">
					<option disabled selected>--Select Category--</option>
					<c:if test="${!empty categoryList}">
						<c:forEach items="${categoryList}" var="category">
							<option value="${category.categoryId}">
								<c:out value="${category.categoryName}" /></option>
						</c:forEach>
					</c:if>
				</select>
			</div>

			<div class="form-group col-xs-4">

				<div class="">
					<select class="form-control param2" id="param2"
						style="border: 0; border-bottom: 2px ridge;">
						<option value="">--Select an item--</option>
						<c:if test="${!empty itemList}">
							<c:forEach items="${itemList}" var="item">
								<option value="${item.itemId}">
									<c:out value="${item.itemName}" /></option>
							</c:forEach>
						</c:if>
					</select>
				</div>
			</div>


			<div class="form-group col-xs-1">

				<div class="">
					<button type="button" class="btn btn-sm btn-info"
						onclick="query();">Query</button>
				</div>
			</div>


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
	$(document)
			.ready(
					function() {
						var param1 = document.getElementById("param1").value;
						var param2 = document.getElementById("param2").value;
						//param1="100.102";   
						document.getElementById('requisitionReportFrame').src = birtAddr
								+ '?__report=desco/procurement/khath_wise_ledger_report.rptdesign&__toolbar=false&__showtitle=false&__title=&P_KHATH_ID='
								+ param1 + '&P_ITEM_ID=' + param2;
					});

	function query() {
		var param1 = document.getElementById("param1").value;
		var param2 = document.getElementById("param2").value;
		
		document.getElementById('requisitionReportFrame').src = birtAddr
				+ '?__report=desco/procurement/khath_wise_ledger_report.rptdesign&__toolbar=false&__showtitle=false&__title=&P_KHATH_ID='
				+ param1 + '&P_ITEM_ID=' + param2;
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
						var itemNames = $("#param2");
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
