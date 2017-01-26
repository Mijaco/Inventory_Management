<%@include file="../../common/pdHeader.jsp"%>
<!-- -------------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 0 10px; padding-left: 20px">

		<input type="hidden" id="birtAddr" value="${properties['birtAddr']}" />
		<input type="hidden" id="contractorId" value="${contractorId}" />

	</div>
	
	<div class="row" style="background-color: white; padding: 10px; padding-left: 20px">		
		<h1 class="center blue" style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">As Built Report</h1>
	</div>

	<div class="row"
		style="background-color: white; padding-left: 10px; margin: 10px; padding-bottom: 10px; padding-top: 10px;">
		<div class="table-responsive">
			<!-- --------------------- -->
			<div class="row">
				<iframe frameborder="0" class="col-md-12"
					style="margin: 10px 0 10px 0; height: 650px"
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
	var param1 = $('#contractorId').val();
	$(document).ready( function() {
		document.getElementById('requisitionReportFrame').src = birtAddr+ '?__report=desco/procurement/project_as_built_report.rptdesign&__toolbar=false&__showtitle=false&__title=&P_CONTRACTOR_ID='
		+ param1;
		
		//set height :: added by: Shimul
		var wHeight = $( window ).height();
		var rHeight = $('#requisitionReportFrame').height();
		//console.log(wHeight + "  " + rHeight);
		if( rHeight >= wHeight ) {
			rHeight -= 200;
			//console.log(rHeight);
			$( '#requisitionReportFrame' ).css({
		        'height': rHeight + 'px'
		     });
		}
	});
</script>
<!-- ------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>
