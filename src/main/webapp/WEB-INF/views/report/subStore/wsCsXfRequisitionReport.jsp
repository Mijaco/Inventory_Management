<%@include file="../../common/csHeader.jsp"%>
<!-- -------------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="col-md-2 o_form_buttons_edit" style="display: block;">
		 <input
				type="text" id="birtAddr" value="${properties['birtAddr']}"
				hidden="true" />
		</div>

		<h4 class="center blue col-md-offset-3 col-md-6"
			style="font-style: italic; margin-top: 0px; margin-left: 50px; font-family: 'Ubuntu Condensed', sans-serif;">Store
			Requisition <input type="text" id="param1" name="param1"
						style="width: 40%"> 
						<button type="button" class="btn btn-sm btn-info"
						onclick="query();">Query</button></h4>
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
	var param1 = document.getElementById("param1").value;	
	document.getElementById('requisitionReportFrame').src = birtAddr
			+ '?__report=desco/procurement/wsx_to_cs_requisition.rptdesign&__toolbar=false&__showtitle=false&__title=&P_REQUISITION_NO='
			+ param1;
}
</script>
<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>
