<%@include file="../common/procurementHeader.jsp"%>
<!-- -------------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<h4>
			<a href="#" style="text-decoration: none;">Procurement
				Requisition</a> / List
		</h4>
		<div class="o_form_buttons_edit col-sm-6" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/procurement/requisition/create.do"
				style="text-decoration: none;" class="btn btn-success btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Create Requisition
			</a>


			<!-- <button accesskey="D" class="btn btn-info btn-sm" type="button">
					Discard</button> -->
		</div>
	</div>

	<div class="row" style="background: white;">
		<div class="col-sm-12 table-responsive">
			<!-- --------------------- -->			

			<div class="form-group col-xs-5">
				<div class="form-group col-xs-2">
					<label for="exampleInputName2">PRF No.</label>
				</div>
				<div class="col-xs-10">
					<input type="text" class="form-control" id="param1" name="param1"
						value="DESCO/2015-2016/PRF001" style="width: 100%">
						<input type="text" id="birtAddr" value="${properties['birtAddr']}" hidden="true" />
				</div>


			</div>
			<div class="form-group col-xs-6">
				<div class="form-group col-xs-2">
					<label for="exampleInputName2">Others</label>
				</div>

				<div class="col-xs-9">
					<input type="text" class="form-control" id="param2" name="param2"
						placeholder="Jane Doe" style="width: 100%">
				</div>

				<div class="col-xs-1">
					<button type="button" class="btn btn-sm btn-info"
						onclick="query();">Query</button>
				</div>

			</div>


			<div class="col-xs-12">
				<iframe class="col-md-12"
					style="margin: 10px 0 10px 0; height: 410px; border:none;"
					id="requisitionReportFrame">
					<p>Your browser does not support iframes.</p>
				</iframe>
			</div>

			<!-- --------------------- -->
		</div>
	</div>
</div>

 <script type="text/javascript" src="myscripts.js"></script> 

<script type="text/javascript">
	jQuery(document).ready(function(){
		var reportName = 'abc'
		var param = $('#param1').val();
		generateReport(reportName, param);
	});

/*	function generateReport(reportName, param){
		var birtAddress = $('#birtAddr').val();
		var dynamicRptUrl = '?__report=desco/procurement/requisition_report.rptdesign&__toolbar=false&__showtitle=false&__title=&P_PRF_NO=';
		var reportUrl = birtAddress + dynamicRptUrl+ param;
		$('#requisitionReportFrame').attr('src', reportUrl);
	}*/
</script>
<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>
