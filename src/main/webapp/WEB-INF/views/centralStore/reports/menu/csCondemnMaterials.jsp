<%@include file="../../../common/homeHeader.jsp"%>
<!-- -------------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="col-md-2 o_form_buttons_edit" style="display: block;">
			<input type="text" id="birtAddr" value="${properties['birtAddr']}"
				hidden="true" /> <input type="text" id="auctionName"
				value="${auctionName}" hidden="true" />
		</div>

		<div class="col-md-12 blue Ubuntu-font">
			<h4 style="text-align: center;">List of Obsolete/Condemn
				Materials</h4>
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
	$(document)
			.ready(
					function() {
						var birtAddr = document.getElementById("birtAddr").value;
						var param1 = document.getElementById("auctionName").value;

						document.getElementById('requisitionReportFrame').src = birtAddr
								+ '?__report=desco/procurement/list_of_condemn_materials.rptdesign&__toolbar=false&__showtitle=false&__title=&P_AUCTION_NAME='
								+ param1;

					});
</script>
<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../../../common/ibcsFooter.jsp"%>
