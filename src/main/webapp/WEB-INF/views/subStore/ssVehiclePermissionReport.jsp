<%@include file="../common/ssHeader.jsp"%>
<!-- -------------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<h1 class="center blue">
			Your Vehicle Permission Slip <b>${operationId}</b> is ready.
		</h1>
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/ss/vehiclePermissionList.do"
				style="text-decoration: none;" class="btn btn-success btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Vehicle Permission List
			</a>
			<input type="hidden" value="${operationId}" id="operationId"/> 
			<input type="text" id="birtAddr" value="${properties['birtAddr']}" hidden="true" />
		</div>
	</div>

<div class="row" style="background: white;">
		<div class="col-sm-12 table-responsive">
			<div class="col-xs-12">
				<iframe frameborder="0" class="col-md-12" 				
					style="margin: 10px 0 10px 0; height: 410px;"
					id="vehiclePermissionReportFrame">
					<p>Your browser does not support iframes.</p>
				</iframe>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">	
	var birtAddr = document.getElementById("birtAddr").value;	
	$(document)
			.ready(
					function() {
						var param1 = document.getElementById("operationId").value;	
						document.getElementById('vehiclePermissionReportFrame').src = birtAddr	+ '?__report=desco/procurement/ss_vehicle_permission_report.rptdesign&__toolbar=false&__showtitle=false&__title=&P_SLIP_NO='+ param1;
					});
	
	</script>
<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
