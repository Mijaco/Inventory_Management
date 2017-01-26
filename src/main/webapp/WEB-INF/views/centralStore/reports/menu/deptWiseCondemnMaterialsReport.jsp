<%@include file="../../../common/homeHeader.jsp"%>
<!-- -------------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="col-md-2 o_form_buttons_edit" style="display: block;">
			<input type="text" id="birtAddr" value="${properties['birtAddr']}"
				hidden="true" />
		</div>
		<div class="col-md-12 blue Ubuntu-font">
			<div class="col-md-4 " style="display: block;">
			<h5 style="text-align: right;">Auction ID:</h5></div>
			<div class="col-md-4 " style="display: block;">
				<select class="form-control " id="auctionId"
					style="border: 0; border-bottom: 2px ridge;">
					<c:forEach items="${auctionProcessMstList}" var="auctionProcessMst">
						<option value="${auctionProcessMst.id}">${auctionProcessMst.auctionName} - ${auctionProcessMst.auctionCategory.name}</option>
					</c:forEach>
				</select>
			</div>
			<div class="col-md-4" style="display: block;">
				<button type="button" class="btn btn-sm btn-info" onclick="query();">Query</button>
			</div>
		</div>

		
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
		var param1 = document.getElementById("auctionId").value;
		document.getElementById('requisitionReportFrame').src = birtAddr
				+ '?__report=desco/procurement/distribution_list_of_condemn.rptdesign&__toolbar=false&__showtitle=false&__title=&P_AUC_PROCESS_MST_ID='
				+ param1;
	}
</script>
<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../../../common/ibcsFooter.jsp"%>
