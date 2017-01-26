<%@include file="../../common/mpsHeader.jsp"%>
<!-- -------------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 0 10px; padding-left: 20px">

		<input type="hidden" id="birtAddr" value="${properties['birtAddr']}" />
		<input type="hidden" id="contextPath"
			value="${pageContext.request.contextPath}">

		<div class="">
			<table class="table table-bordered table-hover">
				<tr>
					<td colspan="6"
						style="font-weight: normal; font-size: 18px; text-align: center; font-family: 'Ubuntu Condensed', sans-serif;">
						Requirement Summary Report (Coded)</td>
				</tr>


				<tr>
					<td class="col-xs-1 success text-right" style="font-weight: bold;">Financial
						Year:</td>
					<td class="col-xs-2"><select class="form-control" name="id"
						onchange="checkFY()" id="sessionName">
							<option value="">Select Financial Year</option>
							<c:forEach items="${descoSession}" var="sessions">
								<option value="${sessions.id}">${sessions.sessionName}</option>
							</c:forEach>
					</select> <strong class="text-danger sessionName text-center hide"
						style="font-weight: bold; font-size: 16px;">Invalid
							Financial Year!</strong></td>

					<td class="col-xs-1 success text-right" style="font-weight: bold;">Department:</td>
					<td class="col-xs-2"><select class="form-control" name="id"
						id="departmentId">
							<option value="">Select Department</option>
							<c:forEach items="${departmentsList}" var="department">
								<option value="${department.id}">${department.deptName}</option>
							</c:forEach>
					</select></td>

					<td class="col-xs-1 success text-right" style="font-weight: bold;">Item
						Type</td>
					<td class="col-xs-2"><select class="form-control" name="id"
						id="requirementType">
							<option value="">Select Type</option>
							<option value="C">Construction Item</option>
							<option value="G">General Item</option>
					</select></td>
				</tr>

				<tr>
					<td class="col-xs-1 success text-right" style="font-weight: bold;">Item
						Category</td>
					<td class="col-xs-2"><select class="form-control" name="id"
						id="itemCategory" onchange="categoryLeaveChange(this)">
							<option value="">Select Category</option>
							<c:forEach items="${itemCategoryList}" var="itemCategory">
								<option value="${itemCategory.categoryId}">${itemCategory.categoryName}</option>
							</c:forEach>
					</select></td>


					<td class="col-xs-1 success text-right" style="font-weight: bold;">Item
						Name</td>
					<td class="col-xs-2"><select class="form-control itemName"
						id="itemName" name="itemName">
							<option value="" selected>Select Item</option>
					</select></td>

					<td colspan="2" class="col-xs-3 center"><button
							disabled="disabled" class="btn btn-success btn-sm"
							style="border-radius: 6px;" id="queryBtn" onclick="query()">
							<i class="fa fa-search"></i> Query
						</button></td>
				</tr>
			</table>
		</div>

	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px;">
		<div class="table-responsive">
			<!-- --------------------- -->
			<div class="row">
				<iframe frameborder="0" class="col-md-12"
					style="margin: 10px 0 10px 0; height: 415px;"
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

	function checkFY() {
		var haserror = false;
		if ($('#sessionName').val() == null
				|| $.trim($('#sessionName').val()) == '') {
			$('.sessionName').removeClass('hide');
			haserror = true;
		} else {
			$('.sessionName').addClass('hide');
		}

		if (haserror == true) {
			$('#queryBtn').attr("disabled", true);
		} else {
			$('#queryBtn').attr("disabled", false);
		}
	};

	function query() {
		var param1 = $('#sessionName').val();
		var param2 = $('#departmentId').val();
		var param3 = $('#requirementType').val();
		var param4 = $('#itemCategory').val();
		var param5 = $('#itemName').val();
		if ($.trim(param1) == '' || param1 == null) {
			return;
		} else {
			var srcPath = birtAddr
					+ '?__report=desco/procurement/requirement_summary_coded.rptdesign&__toolbar=false&__showtitle=false&__title=&P_SESSION_ID='
					+ param1 + '&P_DEPARTMENT_ID=' + param2 + '&P_ITEM_TYPE='
					+ param3 + '&P_CATEGORY_ID=' + param4 + '&P_ITEM_ID='
					+ param5;

			document.getElementById('requisitionReportFrame').src = srcPath;
		}

	}

	function categoryLeaveChange(element) {
		var categoryId = $("#itemCategory").val();
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
<!-- ------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>
