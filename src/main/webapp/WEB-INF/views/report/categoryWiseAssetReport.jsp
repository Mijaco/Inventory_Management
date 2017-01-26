<%@include file="../common/faHeader.jsp"%>
		<link href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel="stylesheet">
<!-- -------------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 0 10px; padding-left: 20px">

		<input type="hidden" id="birtAddr" value="${properties['birtAddr']}" />
		<!--<input type="hidden" id="departmentId" value="${department.id}" />  -->

		<div class="">
			<table class="table table-bordered table-hover">
				<tr>
					<td colspan="5"
						style="font-weight: normal; font-size: 18px; text-align: center; font-family: 'Ubuntu Condensed', sans-serif;">
						Category Wise Asset Report</td>
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


					<td class="col-xs-1 success text-right" style="font-weight: bold;">date</td>
					<td class="col-xs-2"><input type="date" class="form-control datepicker-14" id="contractDate" 
								style="border: 0; border-bottom: 2px ridge;" name="contractDate" ></td>

					<td class="col-xs-2 center"><button 
							class="btn btn-success btn-sm" style="border-radius: 6px;"
							id="queryBtn" onclick="query()">
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



	function query() {
		var param1 = $('#itemCategory').val();
		var param2 = $('#contractDate').val();
		
		if ($.trim(param1) == '' || param1 == null) {
			return;
		} else {
			document.getElementById('requisitionReportFrame').src = birtAddr
					+ '?__report=desco/procurement/category_wise_asset_report.rptdesign&__toolbar=false&__showtitle=false&__title=&P_CATEGORY_ID='
					+ param1 + '&P_INPUT_DATE=' + param2;
		}

	}
</script>

<!-- ------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
