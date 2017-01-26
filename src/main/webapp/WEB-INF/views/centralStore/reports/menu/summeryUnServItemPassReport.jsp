<%@include file="../../../common/csHeader.jsp"%>
<!-- -------------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="col-md-2 o_form_buttons_edit" style="display: block;">
			<input type="text" id="birtAddr" value="${properties['birtAddr']}"
				hidden="true" />
		</div>

		<h4 class="center blue col-md-offset-1 col-md-10"
			style="font-style: italic; margin-top: 0px; margin-left: 50px; font-family: 'Ubuntu Condensed', sans-serif;">
			Auction Category:&nbsp;&nbsp; <select
				name="categoryId" class="" required="required" id="categoryId"
				style="border: 0; border-bottom: 2px ridge; width: 30%;">
				<option value="0" selected="selected">--- Select Auction
					Category ---</option>
				<c:forEach items="${auctionCategoryList}" var="auctionCategory">
					<option value="${auctionCategory.id}">${auctionCategory.name}</option>
				</c:forEach>
			</select>&nbsp;&nbsp; Auction Name: <select name="auctionProcess" class=""
				id="auctionProcess"
				style="border: 0; border-bottom: 2px ridge; width: 30%;">
				<option value="0" selected="selected">--- Select
					an Auction ---</option>
				<c:forEach items="${auctionProcessMstList}" var="auctionProcessMst">
					<option value="${auctionProcessMst.id}">${auctionProcessMst.auctionName}</option>
				</c:forEach>
			</select>
			<button type="button" class="btn btn-sm btn-info" onclick="query();">Query</button>
		</h4>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px;">
		<div class="row">
			<iframe frameborder="0" class="col-md-12"
				style="margin: 10px 0 10px 0; height: 430px;"
				id="requisitionReportFrame">
				<p>Your browser does not support iframes.</p>
			</iframe>
		</div>
	</div>

</div>
<script type="text/javascript">
	var birtAddr = document.getElementById("birtAddr").value;
	function query() {
		var param1 = document.getElementById("categoryId").value;
		var auctionProcess = document.getElementById("auctionProcess").value;
		document.getElementById('requisitionReportFrame').src = birtAddr
				+ '?__report=desco/procurement/final_value_condemn_materials.rptdesign&__toolbar=false&__showtitle=false&__title=&P_AUC_CAT_ID='
				+ param1+ '&P_AUC_PROCESS_ID=' + auctionProcess;
	}
</script>
<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../../../common/ibcsFooter.jsp"%>
