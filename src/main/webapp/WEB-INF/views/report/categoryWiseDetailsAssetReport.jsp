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
						id="itemCategory" onchange="showSubCategory(this.value)">
							<option value="">Select Category</option>
							<c:forEach items="${itemCategoryList}" var="itemCategory">
								<option value="${itemCategory.categoryId}">${itemCategory.categoryName}</option>
							</c:forEach>
					</select></td>
					
					<td id="col1" style="display:none;" class="col-xs-1 success text-right">Sub Category</td>
						<td id="col2" style="display:none;" class="col-xs-2">
						<select	class="form-control subCategory" name="subCategory" id="subCategory"
									style="border: 0; border-bottom: 2px ridge;" onChange="showColl(this.value)">
									<option value="">Select</option>
										<option value="Grid Sub-Station">Grid Sub-Station</option>
										<option value="Sub-Station">Sub-Station</option>
										<option value="Switching-Station">Switching-Station</option>
										<option value="Transformer">Transformer</option>
										<option value="Meter">Meter</option>
										<option value="Tools And Equipment">Tools & Equipment</option>
								</select></td>
						<td id="col3" style="display:none;">Project Name</td>
						<td id="col4" style="display:none;"><input type="text" class="form-control" id="projectName" width="15px"
								style="border: 0; border-bottom: 2px ridge;" name="projectName"
								value="" /></td>
								
								<td id="col5" style="display:none;" class="col-xs-2 success">Sub Category</td>
								<td id="col6" style="display:none;" class="col-xs-2 info">
								<select
									class="form-control subCategory" name="subCategory" id="subCategory1"
									style="border: 0; border-bottom: 2px ridge;">
									<option value="">Select</option>
										<option value="11 KV Under Ground Cable Line">11 KV Under Ground Cable Line</option>
										<option value="33 KV Under Ground Cable Line">33 KV Under Ground Cable Line</option>
										<option value=".4 KV Overhead Line">.4 KV Overhead Line</option>
										<option value="11 KV Overhead Line">11 KV Overhead Line</option>
										<option value="33 KV Overhead Line">33 KV Overhead Line</option>
								</select>
								<input type="date" class="form-control datepicker-14" id="date1" 
								style="border: 0; border-bottom: 2px ridge;" name="date1" >
								<input type="date" class="form-control datepicker-14" id="date2" 
								style="border: 0; border-bottom: 2px ridge;" name="date2" >
								</td>

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
function showSubCategory(value){
	//alert(value);

if (value == "9996") {//Distribution Equipment
	$("#col1").show();
	 $("#col2").show();
	 $("#col5").hide();
	 $("#col6").hide();
}else if(value == "10101"){//Distribution Line
	$("#col5").show();
	 $("#col6").show();
	 $("#col1").hide();
	 $("#col2").hide();}

}
function showColl(value){
	//alert(value);

if (value == "Sub-Station") {//Distribution Equipment-->Sub-Station
	$("#col3").show();
	 $("#col4").show();
}else{
	$("#col3").hide();
$("#col4").hide();}

}
	var birtAddr = document.getElementById("birtAddr").value;



	function query() {
		var param = $('#itemCategory').val();

		//alert(param+"==="+param1);
		if(param=='9996'){
			//alert(param);
			if ($.trim(param) == '' || param == null) {
				return;
			} else {
				var param1 = $('#subCategory').val();
			var param2 = $('#projectName').val();
			document.getElementById('requisitionReportFrame').src = birtAddr
			+ '?__report=desco/procurement/category_wise_details_fixed_assets_report.rptdesign&__toolbar=false&__showtitle=false&__title=&P_SUB_CATEGORY='
			+ param1+'&P_PROJECT_NAME='+ param2;
		}
		
		}else if(param=='10101'){
			
		if ($.trim(param) == '' || param == null) {
			return;
		} else {
			var param1 = $('#subCategory1').val();
			var param2 = $('#date1').val();
			var param3 = $('#date2').val();
			alert(param1+"=="+param2+"=="+param3);
			document.getElementById('requisitionReportFrame').src = birtAddr
					+ '?__report=desco/procurement/location_wise_fixed_asset_ledger.rptdesign&__toolbar=false&__showtitle=false&__title=&P_FROM_DATE='+ param2+'&P_TO_DATE='+ param3+'&P_SUB_CATEGORY='
						+ param1;
		}
		}

	}
</script>

<!-- ------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
