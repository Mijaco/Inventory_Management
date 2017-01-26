<%@include file="../../common/wsContractorHeader.jsp"%>
<%@ page import="java.util.Date"%>
<jsp:useBean id="now" class="java.util.Date" />

<!-- @author: Ihteshamul Alam, IBCS -->

<link href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel="stylesheet">
<style>
.loader {
	position: fixed;
	left: 50%;
	top: 50%;
	z-index: 9999;
}
</style>
<!-- -------------------------------------------------------------------------------------- -->
<div class="loader">
	<i class="fa fa-refresh fa-spin fa-3x fa-fw"></i> <br> <span
		class="orange">Loading...</span>
</div>

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">

		<h2 class="center blue"
			style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
			Transformer Test Report(3-PHASE)</h2>
		<input type="hidden" id="contextPath"
			value="${pageContext.request.contextPath}" />

		<!-- --------------------- -->
		<div class="alert alert-success hide">
			<strong>Congratulation!</strong> Information is updated successfully.
		</div>
		<div class="alert alert-danger hide">
			<strong>Sorry!</strong> Information update is failed!!!.
		</div>
		<!-- --------------------- -->
	</div>


	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		
		<form action="${pageContext.request.contextPath}/ws/xf/saveTestReport3P.do" method="POST">
			<div class="col-sm-12 table-responsive">
				<c:if test="${empty transformer}">
					<div class="col-sm-12 center">
						<p class="red">
							<i>Sorry!!! No Data Found in Database. </i>
						</p>
					</div>
				</c:if>
				<c:if test="${!empty transformer}">
					<table id="inventoryList" style=""
						class="table table-striped table-hover table-bordered">

						<tbody>
							<tr>
								<td class="col-md-3 success">Type of Work <input
									type="hidden" id="id" name="id" value="${transformer.id}" />
								</td>
								<td class="col-md-3">${transformer.typeOfWork}</td>
								<td class="col-md-3 success">Test/Repairing Date</td>
								<td class="col-md-3"><input style="width: 100%" type="text"
									class="datepicker-14" id="testDate" name="testDate" required="required"></td>
							</tr>
							<tr>
								<td class="col-md-3 success">Transformer No.</td>
								<td class="col-md-3">${transformer.transformerSerialNo}</td>
								<td class="success">Job No.</td>
								<td>${transformer.jobNo}</td>
							</tr>
							<tr>
								<td class="success">Name of Manufacturer</td>
								<td>${transformer.manufacturedName}</td>
								<td class="success">Year of Manufacturer</td>
								<td>${transformer.manufacturedYear}</td>
							</tr>
							<tr>
								<td class="success">Previous Repair Date</td>
								<td> <fmt:formatDate
										value="${transformer.previousRepairDate}" pattern="dd-MM-yyyy" />
										
									<input type="hidden" id="prDate" name="prDate"
									value="${transformer.previousRepairDate}" />
								</td>
								<td class="success">Capacity</td>
								<td>${transformer.kvaRating}</td>
							</tr>
							<tr>
								<td class="success">% Impedance</td>
								<td><input type="text" id="impedance" name="impedance" style="width: 100%"></td>
							</tr>
						</tbody>
					</table>
					<!-- 				<table id="inventoryList" style="" -->
					<!-- 					class="table table-striped table-hover table-bordered"> -->
					<!-- 					<thead> -->
					<!-- 						<tr -->
					<!-- 							style="background: #579EC8; color: white; font-weight: normal;"> -->
					<!-- 							<td>Transformer S.L</td> -->
					<!-- 							<td>Rating (KVA)</td> -->
					<!-- 							<td>Manufactured By</td> -->
					<!-- 							<td>Manufacturing Year</td> -->
					<!-- 							<td>Job No</td> -->
					<!-- 							<td>Type of Work</td> -->
					<!-- 							<td>Received From</td> -->
					<!-- 							<td>Received Date</td> -->
					<!-- 						</tr> -->
					<!-- 					</thead> -->

					<!-- 					<tbody> -->
					<!-- 						<tr> -->
					<%-- 							<td>${transformer.transformerSerialNo}</td> --%>
					<%-- 							<td>${transformer.kvaRating}</td> --%>
					<%-- 							<td>${transformer.manufacturedName}</td> --%>
					<%-- 							<td>${transformer.manufacturedYear}</td> --%>
					<%-- 							<td>${transformer.jobNo}</td> --%>
					<%-- 							<td>${transformer.typeOfWork}</td> --%>
					<%-- 							<td>${transformer.rcvDeptName}</td> --%>
					<%-- 							<td><fmt:formatDate value="${transformer.receivedDate}" --%>
					<%-- 									pattern="dd-MM-yyyy" /></td> --%>

					<!-- 						</tr> -->
					<!-- 					</tbody> -->
					<!-- 				</table> -->
				</c:if>
			</div>
			<!-- --------------------- -->
			<div class="col-sm-12 table-responsive">
				<h5 style="text-decoration: underline; font-weight: bold">1.
					Continuity Test (by AVO meter)</h5>
				<table class="table table-bordered" id="">
					<thead>
						<tr>
							<th class="text-center" colspan=2>HV Side</th>
							<th class="text-center" colspan=4>LV Side</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="col-sm-1 text-center">A-B</td>
							<td class="col-sm-3"><input style="width: 100%"
								type="number" id="ct_hv_a_b" name="ct_hv_a_b"></td>
							<td class="col-sm-1 text-center">a-b</td>
							<td class="col-sm-3"><input style="width: 100%"
								type="number" id="ct_lv_a_b" name="ct_lv_a_b"></td>
							<td class="col-sm-1 text-center">a-n</td>
							<td class="col-sm-3"><input style="width: 100%"
								type="number" id="ct_lv_a_n" name="ct_lv_a_n"></td>
						</tr>

						<tr>
							<td class="col-sm-1 text-center">B-C</td>
							<td class="col-sm-3"><input style="width: 100%"
								type="number" id="ct_hv_b_c" name="ct_hv_b_c"></td>
							<td class="col-sm-1 text-center">b-c</td>
							<td class="col-sm-3"><input style="width: 100%"
								type="number" id="ct_lv_b_c" name="ct_lv_b_c"></td>
							<td class="col-sm-1 text-center">b-n</td>
							<td class="col-sm-3"><input style="width: 100%"
								type="number" id="ct_lv_b_n" name="ct_lv_b_n"></td>
						</tr>

						<tr>
							<td class="col-sm-1 text-center">C-A</td>
							<td class="col-sm-3"><input style="width: 100%"
								type="number" id="ct_hv_c_a" name="ct_hv_c_a"></td>
							<td class="col-sm-1 text-center">c-a</td>
							<td class="col-sm-3"><input style="width: 100%"
								type="number" id="ct_lv_c_a" name="ct_lv_c_a"></td>
							<td class="col-sm-1 text-center">c-n</td>
							<td class="col-sm-3"><input style="width: 100%"
								type="number" id="ct_lv_c_n" name="ct_lv_c_n"></td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- Continuity Test Table :: end -->

			<div class="col-sm-12 table-responsive">
				<h5 style="text-decoration: underline; font-weight: bold">2.
					Ratio Test by Voltmeter method</h5>
				<table class="table table-bordered">
					<thead>
						<tr>
							<th class="text-center col-sm-3" colspan=3>Applied Voltage
								at HV Side (Volt)</th>
							<th class="text-center col-sm-7" colspan=6>Measured LV
								(Volt)</th>
							<th class="text-center col-sm-1">Ratio</th>
							<th class="text-center col-sm-1">Remarks</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td class="text-center ">A-B</td>
							<td class="text-center ">B-C</td>
							<td class="text-center ">C-A</td>
							<td class="text-center ">a-b</td>
							<td class="text-center ">b-c</td>
							<td class="text-center ">c-a</td>
							<td class="text-center ">a-n</td>
							<td class="text-center ">b-n</td>
							<td class="text-center ">c-n</td>
							<td rowspan=2 style="vertical-align: middle;"><input
								style="width: 100%" type="text" id="rt_ratio" name="rt_ratio"
								placeholder="Ratio"></td>
							<td rowspan=2 style="vertical-align: middle;"><input
								style="width: 100%" type="text" id="rt_remarks" name="rt_remarks"
								placeholder="Remarks"></td>
						</tr>
						<tr>
							<td><input style="width: 100%" type="number" id="rt_av_a_b" name="rt_av_a_b">
							</td>
							<td><input style="width: 100%" type="number" id="rt_av_b_c" name="rt_av_b_c">
							</td>
							<td><input style="width: 100%" type="number" id="rt_av_c_a" name="rt_av_c_a">
							</td>
							<td><input style="width: 100%" type="number" step="0.1"
								id="rt_mlv_a_b" name="rt_mlv_a_b"></td>
							<td><input style="width: 100%" type="number" step="0.1"
								id="rt_mlv_b_c" name="rt_mlv_b_c"></td>
							<td><input style="width: 100%" type="number" step="0.1"
								id="rt_mlv_c_a" name="rt_mlv_c_a"></td>
							<td><input style="width: 100%" type="number" step="0.1"
								id="rt_mlv_a_n" name="rt_mlv_a_n"></td>
							<td><input style="width: 100%" type="number" step="0.1"
								id="rt_mlv_b_n" name="rt_mlv_b_n"></td>
							<td><input style="width: 100%" type="number" step="0.1"
								id="rt_mlv_c_n" name="rt_mlv_c_n"></td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- Ratio Test by Voltmeter method table :: end -->

			<div class="col-sm-12 table-responsive">
				<h5 style="text-decoration: underline; font-weight: bold">3.
					Insulation Resistance Test by 1 KV Meggar.</h5>
				<table class="table table-bordered">
					<thead>
						<tr>
							<th class="text-center col-sm-2">HV-LVG (M&Omega;)</th>
							<th class="text-center col-sm-2">LV-HVG (M&Omega;)</th>
							<th class="text-center col-sm-2">HV-LV (M&Omega;)</th>
							<th class="text-center col-sm-3">Winding/oil temparature
								&deg;C</th>
							<th class="text-center col-sm-3">Remarks</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><input style="width: 100%" type="number" id="irt_hv_lvg" name="irt_hv_lvg">
							</td>
							<td><input style="width: 100%" type="number" id="irt_lv_hvg" name="irt_lv_hvg">
							</td>
							<td><input style="width: 100%" type="number" id="irt_hv_lv" name="irt_hv_lv">
							</td>
							<td><input style="width: 100%" type="text" id="irt_oil_temp" name="irt_oil_temp">
							</td>
							<td><input style="width: 100%" type="text" id="irt_remarks" name="irt_remarks">
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- 1 KV Meggar Insulation Resistance Test Table :: end -->

			<div class="col-sm-12 table-responsive">
				<h5 style="text-decoration: underline; font-weight: bold">4.
					Load Loss and Impedance Test (Two watt meter method)</h5>
				<h5 style="text-decoration: underline; font-weight: bold">Supplied
					voltage at HV side with LV side short circuits</h5>
				<table class="table table-bordered">
					<thead>
						<tr>
							<th class="text-center" colspan=3>HV voltage (volts)</th>
							<th class="text-center" colspan=3>HV side current (A)</th>
							<th class="text-center" colspan=4>LV side current (A)</th>
							<th class="text-center" rowspan=2 style="vertical-align: top;">Remarks</th>
						</tr>
						<tr>
							<th class="text-center">AB</th>
							<th class="text-center">BC</th>
							<th class="text-center">CA</th>
							<th class="text-center">A</th>
							<th class="text-center">B</th>
							<th class="text-center">C</th>
							<th class="text-center">a</th>
							<th class="text-center">b</th>
							<th class="text-center">c</th>
							<th class="text-center">n</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><input style="width: 100%" type="number" id="llit_hv_ab" name="llit_hv_ab">
							</td>
							<td><input style="width: 100%" type="number" id="llit_hv_bc" name="llit_hv_bc">
							</td>
							<td><input style="width: 100%" type="number" id="llit_hv_ca" name="llit_hv_ca">
							</td>
							<td><input style="width: 100%" type="number" step="0.1"
								id="llit_hvs_a" name="llit_hvs_a"></td>
							<td><input style="width: 100%" type="number" step="0.1"
								id="llit_hvs_b" name="llit_hvs_b"></td>
							<td><input style="width: 100%" type="number" step="0.1"
								id="llit_hvs_c" name="llit_hvs_c"></td>
							<td><input style="width: 100%" type="number" id="llit_lv_a" name="llit_lv_a">
							</td>
							<td><input style="width: 100%" type="number" id="llit_lv_b" name="llit_lv_b">
							</td>
							<td><input style="width: 100%" type="number" id="llit_lv_c" name="llit_lv_c">
							</td>
							<td><input style="width: 100%" type="number" step="0.1" id="llit_lv_n" name="llit_lv_n"></td>
							<td><input style="width: 100%" type="text" id="llit_lv_remarks" name="llit_lv_remarks">
							</td>
						</tr>
					</tbody>
				</table>

				<table class="table table-bordered">
					<thead>
						<tr>
							<th class="text-center">Load loss (Watt)</th>
							<th class="text-center">Copper loss (Watt)</th>
							<th class="text-center">Impedance voltage</th>
							<th class="text-center">% Impedance</th>
							<th class="text-center">Remarks</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><input style="width: 100%" type="text" id="llit_load_loss" name="llit_load_loss">
							</td>
							<td><input style="width: 100%" type="text" id="llit_copper_loss" name="llit_copper_loss">
							</td>
							<td><input style="width: 100%" type="text" id="llit_impedance_volt" name="llit_impedance_volt">
							</td>
							<td><input style="width: 100%" type="text" id="llit_percent_impedance" name="llit_percent_impedance">
							</td>
							<td><input style="width: 100%" type="text" id="llit_remarks" name="llit_remarks">
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- Test 4 Table :: end -->

			<div class="col-sm-12 table-responsive">
				<h5 style="text-decoration: underline; font-weight: bold">5.
					Iron loss Test and No load current Test or excitation Test</h5>
				<table class="table table-bordered">
					<thead>
						<tr>
							<th class="text-center" colspan=6>Voltage at LV side (volts)</th>
							<th class="text-center" colspan=3>No load current at LV side
								(Amps)</th>
							<th class="text-center" style="vertical-align: top;" rowspan=2>Remarks</th>
						</tr>
						<tr>
							<th class="text-center">ab</th>
							<th class="text-center">bc</th>
							<th class="text-center">ca</th>
							<th class="text-center">an</th>
							<th class="text-center">bn</th>
							<th class="text-center">cn</th>
							<th class="text-center">a</th>
							<th class="text-center">b</th>
							<th class="text-center">c</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><input style="width: 100%" type="number" id="ilt_lvv_ab" name="ilt_lvv_ab">
							</td>
							<td><input style="width: 100%" type="number" id="ilt_lvv_bc" name="ilt_lvv_bc">
							</td>
							<td><input style="width: 100%" type="number" id="ilt_lvv_ca" name="ilt_lvv_ca">
							</td>
							<td><input style="width: 100%" type="number" id="ilt_lvv_an" name="ilt_lvv_an">
							</td>
							<td><input style="width: 100%" type="number" id="ilt_lvv_bn" name="ilt_lvv_bn">
							</td>
							<td><input style="width: 100%" type="number" id="ilt_lvv_cn" name="ilt_lvv_cn">
							</td>
							<td><input style="width: 100%" type="number" step="0.1"
								id="ilt_lva_a" name="ilt_lva_a"></td>
							<td><input style="width: 100%" type="number" step="0.1"
								id="ilt_lva_b" name="ilt_lva_b"></td>
							<td><input style="width: 100%" type="number" step="0.1"
								id="ilt_lva_c" name="ilt_lva_c"></td>
							<td><input style="width: 100%" type="text" id="ilt_remarks" name="ilt_remarks">
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- Test 5 table :: end -->
			<div class="col-sm-12 table-responsive">
				<h5 style="text-decoration: underline; font-weight: bold">Transformar
					oil Test (Gap-2.5mm)</h5>
				<table class="table table-bordered">
					<thead>
						<tr>
							<th>Dielectric break down voltage of transformar oil (IEC
								Stadard)</th>
							<th>Dielectric break down voltage (KV)</th>
							<th>Remarks</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td><input style="width: 100%" type="text" id="tot_dieletric_volt_transformer"
								name="tot_dieletric_volt_transformer">
							</td>
							<td><input style="width: 100%" type="text" id="tot_dieletric_kv" name="tot_dieletric_kv">
							</td>
							<td><input style="width: 100%" type="text" id="tot_remarks" name="tot_remarks">
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- Transformar oil Test :: end -->
			
			<div class="col-md-12" style="padding-top: 15px;">
				<div class="col-xs-12 col-sm-6">
					<button type="submit"
						style="margin-right: 10px; border-radius: 6px;"
						class="width-20 pull-right btn btn-lg btn-success">
						<i class="ace-icon fa fa-save"></i> <span class="bigger-50">Save</span>
					</button>
				</div>

				<div class="col-xs-12 col-sm-6">
					<button type="reset"
						class="width-20  pull-left btn btn-lg btn-danger"
						style="margin-left: 10px; border-radius: 6px;">
						<i class="ace-icon fa fa-refresh"></i> <span class="bigger-50">Reset</span>
					</button>
				</div>
			</div>
		</form>
		
	</div>
</div>

<script>

	$(window).load(function() {
		$('.loader').fadeOut('slow');
	});

<!-- function createTR(n) { -->
<!-- var id = $('#pk' + n).val(); -->
<!-- var contextPath = $('#contextPath').val(); -->
<!-- var path = contextPath + '/ws/xf/testReportForm.do'; -->
<!-- var cData = { -->
<!-- id : id -->
<!-- }; -->

<!-- postSubmit(path, cData, 'POST'); -->
<!-- } -->
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>
