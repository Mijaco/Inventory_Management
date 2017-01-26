<%@include file="../../common/wsContractorHeader.jsp"%>
<%@ page import="java.util.Date"%>
<jsp:useBean id="now" class="java.util.Date" />
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!-- @author: Ihteshamul Alam, IBCS -->

<link
	href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
	rel="stylesheet">
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

		<!-- Edit Button :; start -->
		<div class="col-md-12">
			<div class="col-xs-12 col-sm-6">
				<button id="editbtn" style="margin-right: 10px; border-radius: 6px;"
					class="width-20 pull-left btn btn-primary">
					<i class="ace-icon fa fa-edit"></i> <span class="bigger-50">Edit</span>
				</button>
			</div>
		</div>
		<!-- Edit Button :: end -->

		<h2 class="center blue"
			style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
			Transformer Test Report(3-PHASE) Show</h2>
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
		<c:if test="${empty testReport3P}">
			<div class="col-sm-12 center">
				<p class="red">
					<i>Sorry!!! No Data Found in Database. </i>
				</p>
			</div>
		</c:if>
		<form action="" method="POST" onsubmit="return false">
			<c:if test="${!empty testReport3P}">
				<input type='hidden' value='${pageContext.request.contextPath}'
					id='contextPath'>



				<div class="col-sm-12 table-responsive">
					<table id="inventoryList" style=""
						class="table table-striped table-hover table-bordered">

						<tbody>
							<tr>
								<td class="col-md-3 success">Type of Work <input
									type="hidden" id="id" name="id" value="${testReport3P.id}" />
									<input type="hidden" id="tsfRegMstId" name="tsfRegMstId"
									value="${testReport3P.tsfRegMst.id}" />

								</td>
								<td class="col-md-3">${testReport3P.tsfRegMst.typeOfWork}</td>
								<td class="col-md-3 success">Test/Repairing Date</td>
								<td class="col-md-3"><input style="width: 100%" type="text"
									class="datepicker-14" id="testDate" name="testDate"
									readonly="readonly" value="${testReport3P.testDate}"></td>
							</tr>
							<tr>
								<td class="col-md-3 success">Transformer No.</td>
								<td class="col-md-3">${testReport3P.tsfRegMst.transformerSerialNo}</td>
								<td class="success">Job No.</td>
								<td>${testReport3P.tsfRegMst.jobNo}</td>
							</tr>
							<tr>
								<td class="success">Name of Manufacturer</td>
								<td>${testReport3P.tsfRegMst.manufacturedName}</td>
								<td class="success">Year of Manufacturer</td>
								<td>${testReport3P.tsfRegMst.manufacturedYear}</td>
							</tr>
							<tr>
								<td class="success">Previous Repair Date</td>
								<td><fmt:formatDate value="${testReport3P.prDate}"
										pattern="dd-MM-yyyy" /> <input type="hidden" id="prDate"
									name="prDate"
									value="${testReport3P.tsfRegMst.previousRepairDate}" /></td>
								<td class="success">Capacity</td>
								<td>${testReport3P.tsfRegMst.kvaRating}</td>
							</tr>
							<tr>
								<td class="success">% Impedance</td>
								<td><input type="text" style="width: 100%" id="impedance"
									name="impedance" value="${testReport3P.impedance}"
									readonly="readonly"></td>
							</tr>
						</tbody>
					</table>

				</div>
				<!-- Master Data :: end -->
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
									type="number" id="ct_hv_a_b" name="ct_hv_a_b"
									value="${testReport3P.ct_hv_a_b}" readonly="readonly"></td>
								<td class="col-sm-1 text-center">a-b</td>
								<td class="col-sm-3"><input style="width: 100%"
									type="number" id="ct_lv_a_b" name="ct_lv_a_b"
									value="${testReport3P.ct_lv_a_b}" readonly="readonly"></td>
								<td class="col-sm-1 text-center">a-n</td>
								<td class="col-sm-3"><input style="width: 100%"
									type="number" id="ct_lv_a_n" name="ct_lv_a_n"
									value="${testReport3P.ct_lv_a_n}" readonly="readonly"></td>
							</tr>

							<tr>
								<td class="col-sm-1 text-center">B-C</td>
								<td class="col-sm-3"><input style="width: 100%"
									type="number" id="ct_hv_b_c" name="ct_hv_b_c"
									value="${testReport3P.ct_hv_b_c}" readonly="readonly"></td>
								<td class="col-sm-1 text-center">b-c</td>
								<td class="col-sm-3"><input style="width: 100%"
									type="number" id="ct_lv_b_c" name="ct_lv_b_c"
									value="${testReport3P.ct_lv_b_c}" readonly="readonly"></td>
								<td class="col-sm-1 text-center">b-n</td>
								<td class="col-sm-3"><input style="width: 100%"
									type="number" id="ct_lv_b_n" name="ct_lv_b_n"
									value="${testReport3P.ct_lv_b_n}" readonly="readonly"></td>
							</tr>

							<tr>
								<td class="col-sm-1 text-center">C-A</td>
								<td class="col-sm-3"><input style="width: 100%"
									type="number" id="ct_hv_c_a" name="ct_hv_c_a"
									value="${testReport3P.ct_hv_c_a}" readonly="readonly"></td>
								<td class="col-sm-1 text-center">c-a</td>
								<td class="col-sm-3"><input style="width: 100%"
									type="number" id="ct_lv_c_a" name="ct_lv_c_a"
									value="${testReport3P.ct_lv_c_a}" readonly="readonly"></td>
								<td class="col-sm-1 text-center">c-n</td>
								<td class="col-sm-3"><input style="width: 100%"
									type="number" id="ct_lv_c_n" name="ct_lv_c_n"
									value="${testReport3P.ct_lv_c_n}" readonly="readonly"></td>
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
									placeholder="Ratio" value="${testReport3P.rt_ratio}"
									readonly="readonly"></td>
								<td rowspan=2 style="vertical-align: middle;"><input
									style="width: 100%" type="text" id="rt_remarks"
									name="rt_remarks" placeholder="Remarks"
									value="${testReport3P.rt_remarks}" readonly="readonly"></td>
							</tr>
							<tr>
								<td><input style="width: 100%" type="number" id="rt_av_a_b"
									name="rt_av_a_b" value="${testReport3P.rt_av_a_b}"
									readonly="readonly"></td>
								<td><input style="width: 100%" type="number" id="rt_av_b_c"
									name="rt_av_b_c" value="${testReport3P.rt_av_b_c}"
									readonly="readonly"></td>
								<td><input style="width: 100%" type="number" id="rt_av_c_a"
									name="rt_av_c_a" value="${testReport3P.rt_av_c_a}"
									readonly="readonly"></td>
								<td><input style="width: 100%" type="number" step="0.1"
									id="rt_mlv_a_b" name="rt_mlv_a_b"
									value="${testReport3P.rt_mlv_a_b}" readonly="readonly"></td>
								<td><input style="width: 100%" type="number" step="0.1"
									id="rt_mlv_b_c" name="rt_mlv_b_c"
									value="${testReport3P.rt_mlv_b_c}" readonly="readonly"></td>
								<td><input style="width: 100%" type="number" step="0.1"
									id="rt_mlv_c_a" name="rt_mlv_c_a"
									value="${testReport3P.rt_mlv_c_a}" readonly="readonly"></td>
								<td><input style="width: 100%" type="number" step="0.1"
									id="rt_mlv_a_n" name="rt_mlv_a_n"
									value="${testReport3P.rt_mlv_a_n}" readonly="readonly"></td>
								<td><input style="width: 100%" type="number" step="0.1"
									id="rt_mlv_b_n" name="rt_mlv_b_n"
									value="${testReport3P.rt_mlv_b_n}" readonly="readonly"></td>
								<td><input style="width: 100%" type="number" step="0.1"
									id="rt_mlv_c_n" name="rt_mlv_c_n"
									value="${testReport3P.rt_mlv_c_n}" readonly="readonly"></td>
							</tr>
						</tbody>
					</table>
				</div>
				<!-- Ratio Test by Volt meter method table :: end -->

				<div class="col-sm-12 table-responsive">
					<h5 style="text-decoration: underline; font-weight: bold">3.
						Insulation Resistance Test by 1 KV Meggar.</h5>
					<table class="table table-bordered">
						<thead>
							<tr>
								<th class="text-center col-sm-2">HV-LVG (M&Omega;)</th>
								<th class="text-center col-sm-2">LV-HVG (M&Omega;)</th>
								<th class="text-center col-sm-2">HV-LV (M&Omega;)</th>
								<th class="text-center col-sm-3">Winding/oil temperature
									&deg;C</th>
								<th class="text-center col-sm-3">Remarks</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td><input style="width: 100%" type="number"
									id="irt_hv_lvg" name="irt_hv_lvg"
									value="${testReport3P.irt_hv_lvg}" readonly="readonly"></td>
								<td><input style="width: 100%" type="number"
									id="irt_lv_hvg" name="irt_lv_hvg"
									value="${testReport3P.irt_lv_hvg}" readonly="readonly"></td>
								<td><input style="width: 100%" type="number" id="irt_hv_lv"
									name="irt_hv_lv" value="${testReport3P.irt_hv_lv}"
									readonly="readonly"></td>
								<td><input style="width: 100%" type="text"
									id="irt_oil_temp" name="irt_oil_temp"
									value="${testReport3P.irt_oil_temp}" readonly="readonly"></td>
								<td><input style="width: 100%" type="text" id="irt_remarks"
									name="irt_remarks" value="${testReport3P.irt_remarks}"
									readonly="readonly"></td>
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
								<td><input style="width: 100%" type="number"
									id="llit_hv_ab" name="llit_hv_ab"
									value="${testReport3P.llit_hv_ab}" readonly="readonly"></td>
								<td><input style="width: 100%" type="number"
									id="llit_hv_bc" name="llit_hv_bc"
									value="${testReport3P.llit_hv_bc}" readonly="readonly"></td>
								<td><input style="width: 100%" type="number"
									id="llit_hv_ca" name="llit_hv_ca"
									value="${testReport3P.llit_hv_ca}" readonly="readonly"></td>
								<td><input style="width: 100%" type="number" step="0.1" readonly="readonly"
									id="llit_hvs_a" name="llit_hvs_a" value="${testReport3P.llit_hvs_a}"></td>
								<td><input style="width: 100%" type="number" step="0.1" readonly="readonly"
									id="llit_hvs_b" name="llit_hvs_b" value="${testReport3P.llit_hvs_c}"></td>
								<td><input style="width: 100%" type="number" step="0.1" readonly="readonly"
									id="llit_hvs_c" name="llit_hvs_c" value="${testReport3P.llit_hvs_c}"></td>
								<td><input style="width: 100%" type="number" id="llit_lv_a"
									name="llit_lv_a" value="${testReport3P.llit_lv_a}"
									readonly="readonly"></td>
								<td><input style="width: 100%" type="number" id="llit_lv_b"
									name="llit_lv_b" value="${testReport3P.llit_lv_b}"
									readonly="readonly"></td>
								<td><input style="width: 100%" type="number" id="llit_lv_c"
									name="llit_lv_c" value="${testReport3P.llit_lv_c}"
									readonly="readonly"></td>
								<td><input style="width: 100%" type="number" step="0.1"
									id="llit_lv_n" name="llit_lv_n"
									value="${testReport3P.llit_lv_n}" readonly="readonly"></td>
								<td><input style="width: 100%" type="text"
									id="llit_lv_remarks" name="llit_lv_remarks"
									value="${testReport3P.llit_lv_remarks}" readonly="readonly">
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
								<td><input style="width: 100%" type="text"
									id="llit_load_loss" name="llit_load_loss"
									value="${testReport3P.llit_load_loss}" readonly="readonly">
								</td>
								<td><input style="width: 100%" type="text"
									id="llit_copper_loss" name="llit_copper_loss"
									value="${testReport3P.llit_copper_loss}" readonly="readonly">
								</td>
								<td><input style="width: 100%" type="text"
									id="llit_impedance_volt" name="llit_impedance_volt"
									value="${testReport3P.llit_impedance_volt}" readonly="readonly">
								</td>
								<td><input style="width: 100%" type="text"
									id="llit_percent_impedance" name="llit_percent_impedance"
									value="${testReport3P.llit_percent_impedance}"
									readonly="readonly"></td>
								<td><input style="width: 100%" type="text"
									id="llit_remarks" name="llit_remarks"
									value="${testReport3P.llit_remarks}" readonly="readonly"></td>
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
								<th class="text-center" colspan=6>Voltage at LV side
									(volts)</th>
								<th class="text-center" colspan=3>No load current at LV
									side (Amps)</th>
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
								<td><input style="width: 100%" type="number"
									id="ilt_lvv_ab" name="ilt_lvv_ab"
									value="${testReport3P.ilt_lvv_ab}" readonly="readonly"></td>
								<td><input style="width: 100%" type="number"
									id="ilt_lvv_bc" name="ilt_lvv_bc"
									value="${testReport3P.ilt_lvv_bc}" readonly="readonly"></td>
								<td><input style="width: 100%" type="number"
									id="ilt_lvv_ca" name="ilt_lvv_ca"
									value="${testReport3P.ilt_lvv_ca}" readonly="readonly"></td>
								<td><input style="width: 100%" type="number"
									id="ilt_lvv_an" name="ilt_lvv_an"
									value="${testReport3P.ilt_lvv_an}" readonly="readonly"></td>
								<td><input style="width: 100%" type="number"
									id="ilt_lvv_bn" name="ilt_lvv_bn"
									value="${testReport3P.ilt_lvv_bn}" readonly="readonly"></td>
								<td><input style="width: 100%" type="number"
									id="ilt_lvv_cn" name="ilt_lvv_cn"
									value="${testReport3P.ilt_lvv_cn}" readonly="readonly"></td>
								<td><input style="width: 100%" type="number" step="0.1"
									id="ilt_lva_a" name="ilt_lva_a"
									value="${testReport3P.ilt_lva_a}" readonly="readonly"></td>
								<td><input style="width: 100%" type="number" step="0.1"
									id="ilt_lva_b" name="ilt_lva_b"
									value="${testReport3P.ilt_lva_b}" readonly="readonly"></td>
								<td><input style="width: 100%" type="number" step="0.1"
									id="ilt_lva_c" name="ilt_lva_c"
									value="${testReport3P.ilt_lva_c}" readonly="readonly"></td>
								<td><input style="width: 100%" type="text" id="ilt_remarks"
									name="ilt_remarks" value="${testReport3P.ilt_remarks}"
									readonly="readonly"></td>
							</tr>
						</tbody>
					</table>
				</div>
				<!-- Test 5 table :: end -->
				<div class="col-sm-12 table-responsive">
					<h5 style="text-decoration: underline; font-weight: bold">Transformer
						oil Test (Gap-2.5mm)</h5>
					<table class="table table-bordered">
						<thead>
							<tr>
								<th>Dielectric break down voltage of transformer oil (IEC
									Standard)</th>
								<th>Dielectric break down voltage (KV)</th>
								<th>Remarks</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td><input style="width: 100%" type="text"
									id="tot_dieletric_volt_transformer"
									name="tot_dieletric_volt_transformer"
									value="${testReport3P.tot_dieletric_volt_transformer}"
									readonly="readonly"></td>
								<td><input style="width: 100%" type="text"
									id="tot_dieletric_kv" name="tot_dieletric_kv"
									value="${testReport3P.tot_dieletric_kv}" readonly="readonly">
								</td>
								<td><input style="width: 100%" type="text" id="tot_remarks"
									name="tot_remarks" value="${testReport3P.tot_remarks}"
									readonly="readonly"></td>
							</tr>
						</tbody>
					</table>
				</div>
				<!-- Transformer oil Test :: end -->

				<div class="col-md-12"
					style="padding-top: 15px; margin-bottom: 10px;">
					<div class="col-xs-12 col-sm-6">
						<button type="submit" id="updatebtn"
							style="margin-right: 10px; border-radius: 6px;"
							class="hide width-20 pull-right btn btn-lg btn-success">
							<i class="ace-icon fa fa-save"></i> <span class="bigger-50">Update</span>
						</button>
					</div>

					<div class="col-xs-12 col-sm-6">
						<button type="button" id="cancelbtn"
							class="hide width-20  pull-left btn btn-lg btn-danger"
							style="margin-left: 10px; border-radius: 6px;">
							<i class="ace-icon fa fa-refresh"></i> <span class="bigger-50">Cancel</span>
						</button>
					</div>
				</div>
			</c:if>
		</form>

		<!-- Approval Block :: start -->

		<div id="approvalblock">
			<div class="col-sm-12" id="approvalBlock">

				<!-- Approval History::start -->
				<c:if test="${!empty approvalHistoryList}">
					<button data-toggle="collapse" data-target="#demo">
						<span class="glyphicon glyphicon-collapse-down"></span>
					</button>
				</c:if>
				<div id="demo" class="collapse">
					<c:if test="${!empty approvalHistoryList}">
						<c:forEach items="${approvalHistoryList}" var="approvalHistory">

							<table class="col-sm-12 table">

								<tr class="">
									<td class="warning col-sm-3"
										style="font-weight: bold; font-size: 1.5em; font-style: italic; text-transform: capitalize">${fn:toLowerCase(approvalHistory.approvalHeader)}</td>
									<td class="success col-sm-2" style="text-transform: capitalize">${fn:toLowerCase(approvalHistory.stateName)}
										By:</td>
									<td class="success col-sm-2 text-left">
										${approvalHistory.cEmpFullName} <c:if
											test="${!empty approvalHistory.cDesignation}">
									, ${approvalHistory.cDesignation} 
								</c:if> <c:if test="${!empty approvalHistory.cEmpId}">
									(${approvalHistory.cEmpId})
								</c:if>
									</td>
									<td class="info col-sm-2 text-left"
										style="text-transform: capitalize">${fn:toLowerCase(approvalHistory.stateName)}
										Date:</td>
									<td class="info col-sm-3"><fmt:formatDate
											value="${approvalHistory.createdDate}" pattern="dd-MM-yyyy" /></td>
								</tr>
								<c:if test="${!empty approvalHistory.justification}">
									<tr class="">
										<td></td>
										<td class="danger col-sm-1">Comments:</td>
										<td class="danger col-sm-11" colspan="3"
											title="${approvalHistory.justification}">${approvalHistory.justification}</td>
									</tr>
								</c:if>
							</table>
						</c:forEach>
					</c:if>
				</div>

				<!-- Approval History::end -->

				<!-- <div class="text-center"> -->
				<div class="row">
					<label class="col-xs-2"> <strong>Comment(s) : </strong></label>
					<div class="col-xs-10">
						<textarea class="form-control" rows="2" cols="" id="justification"></textarea>
					</div>
				</div>
				<div class="col-sm-12">
					<hr />
				</div>
				<div class="col-sm-12">
					<div class="col-md-4 col-sm-4 text-left">
						<c:if test="${!empty backManRcvProcs}">
							<div class="dropup pull-left">
								<button class="btn btn-warning dropdown-toggle" type="button"
									style="border-radius: 6px;" data-toggle="dropdown"
									aria-haspopup="true" aria-expanded="false">
									Back to <span class="caret"></span>
								</button>
								<ul class="dropdown-menu" aria-labelledby="dropdownMenu2">
									<c:forEach items="${backManRcvProcs}" var="backMan">
										<li class=""><a
											href="Javascript:backToLower(${backMan.stateCode})">For
												${backMan.buttonName}</a></li>
									</c:forEach>
								</ul>
							</div>
						</c:if>
					</div>
					<div class="col-md-4 col-sm-4 text-center">
						<a class="btn btn-primary" href="Javascript:approveTTR()"
							style="text-decoration: none; border-radius: 6px;">
							${buttonValue}</a>
					</div>
					<div class="col-md-4 col-sm-4 text-right">
						<c:if test="${!empty nextManRcvProcs}">
							<div class="dropup pull-right">
								<button class="btn btn-danger dropdown-toggle" type="button"
									style="border-radius: 6px;" data-toggle="dropdown"
									aria-haspopup="true" aria-expanded="false">
									Send to <span class="caret"></span>
								</button>
								<ul class="dropdown-menu" aria-labelledby="dropdownMenu2">
									<c:forEach items="${nextManRcvProcs}" var="nextMan">
										<li class=""><a
											href="Javascript:forwardToUpper(${nextMan.stateCode})"  onclick="sendToUpper(${nextMan.stateCode})">For
												${nextMan.buttonName}</a></li>
									</c:forEach>
								</ul>
							</div>
						</c:if>
					</div>
				</div>
			</div>
		</div>

		<!-- Approval Block :: end -->
	</div>
</div>


<script>
	function cancelMode() {
		$('form').find('input, textarea').attr("readonly", "readonly");
		$('#editbtn').removeClass('hide');
		$('#updatebtn').addClass('hide');
		$('#cancelbtn').addClass('hide');
		$('#approvalblock').removeClass('hide');
	}

	function editMode() {
		$('form').find('input, textarea').removeAttr("readonly");
		$('#updatebtn').removeClass('hide');
		$('#editbtn').addClass('hide');
		$('#cancelbtn').removeClass('hide');
		$('#approvalblock').addClass('hide');
	}

	function updateTestReport3P() {
		var cData = {
			id : $('#id').val(),
			temp : $('#testDate').val(),
			// prDate : $('#prDate').val(),
			impedance : $('#impedance').val(),
			//
			ct_hv_a_b : $('#ct_hv_a_b').val(),
			ct_lv_a_b : $('#ct_lv_a_b').val(),
			ct_lv_a_n : $('#ct_lv_a_n').val(),
			ct_hv_b_c : $('#ct_hv_b_c').val(),
			ct_lv_b_c : $('#ct_lv_b_c').val(),
			ct_lv_b_n : $('#ct_lv_b_n').val(),
			ct_hv_c_a : $('#ct_hv_c_a').val(),
			ct_lv_c_a : $('#ct_lv_c_a').val(),
			ct_lv_c_n : $('#ct_lv_c_n').val(),
			//
			rt_ratio : $('#rt_ratio').val(),
			rt_remarks : $('#rt_remarks').val(),
			rt_av_a_b : $('#rt_av_a_b').val(),
			rt_av_b_c : $('#rt_av_b_c').val(),
			rt_av_c_a : $('#rt_av_c_a').val(),
			rt_mlv_a_b : $('#rt_mlv_a_b').val(),
			rt_mlv_b_c : $('#rt_mlv_b_c').val(),
			rt_mlv_c_a : $('#rt_mlv_c_a').val(),
			rt_mlv_a_n : $('#rt_mlv_a_n').val(),
			rt_mlv_b_n : $('#rt_mlv_b_n').val(),
			rt_mlv_c_n : $('#rt_mlv_c_n').val(),
			//
			irt_hv_lvg : $('#irt_hv_lvg').val(),
			irt_lv_hvg : $('#irt_lv_hvg').val(),
			irt_hv_lv : $('#irt_hv_lv').val(),
			irt_oil_temp : $('#irt_oil_temp').val(),
			irt_remarks : $('#irt_remarks').val(),
			//
			llit_hv_ab : $('#llit_hv_ab').val(),
			llit_hv_bc : $('#llit_hv_bc').val(),
			llit_hv_ca : $('#llit_hv_ca').val(),
			llit_hvs_a : $('#llit_hvs_a').val(),
			llit_hvs_b : $('#llit_hvs_b').val(),
			llit_hvs_c : $('#llit_hvs_c').val(),
			llit_lv_a : $('#llit_lv_a').val(),
			llit_lv_b : $('#llit_lv_b').val(),
			llit_lv_c : $('#llit_lv_c').val(),
			llit_lv_n : $('#llit_lv_n').val(),
			llit_lv_remarks : $('#llit_lv_remarks').val(),
			llit_load_loss : $('#llit_load_loss').val(),
			llit_copper_loss : $('#llit_copper_loss').val(),
			llit_impedance_volt : $('#llit_impedance_volt').val(),
			llit_percent_impedance : $('#llit_percent_impedance').val(),
			llit_remarks : $('#llit_remarks').val(),
			//
			ilt_lvv_ab : $('#ilt_lvv_ab').val(),
			ilt_lvv_bc : $('#ilt_lvv_bc').val(),
			ilt_lvv_ca : $('#ilt_lvv_ca').val(),
			ilt_lvv_an : $('#ilt_lvv_an').val(),
			ilt_lvv_bn : $('#ilt_lvv_bn').val(),
			ilt_lvv_cn : $('#ilt_lvv_cn').val(),
			ilt_lva_a : $('#ilt_lva_a').val(),
			ilt_lva_b : $('#ilt_lva_b').val(),
			ilt_lva_c : $('#ilt_lva_c').val(),
			ilt_remarks : $('#ilt_remarks').val(),
			//
			tot_dieletric_volt_transformer : $(
					'#tot_dieletric_volt_transformer').val(),
			tot_dieletric_kv : $('#tot_dieletric_kv').val(),
			tot_remarks : $('#tot_remarks').val()
		}

		var cDataJsonString = JSON.stringify(cData);
		var path = $('#contextPath').val() + "/ws/xf/updateTestReport3P.do";

		$.ajax({
			url : path,
			data : cDataJsonString,
			contentType : "application/json",
			success : function(data) {
				var result = JSON.parse(data);
				if (result == 'success') {
					$('.alert.alert-success').removeClass('hide');
					$(".alert.alert-success").fadeTo(5000, 500).slideUp(500,
							function() {
							});
					cancelMode();
				} else {
					$('.alert.alert-danger').removeClass('hide');
					$(".alert.alert-danger").fadeTo(5000, 500).slideUp(500,
							function() {
							});
				}
			},
			error : function(data) {
				alert("Server Error");
			},
			type : 'POST'
		});
	}
	
	function approveTTR() {
		var contextPath = $('#contextPath').val();
		var path = contextPath + '/ws/xf/approveTTR.do';
		var cData = {
			receivedReportNo : $('#tsfRegMstId').val(),
			justification : $('#justification').val()
		};

		postSubmit(path, cData, 'POST');
	}
	
	function sendToUpper( stateCode ) {
		var contextPath = $('#contextPath').val();
		var path = contextPath + '/ws/xf/sendToUpper.do';
		var cData = {
			receivedReportNo : $('#tsfRegMstId').val(),
			justification : $('#justification').val(),
			stateCode : stateCode
		};
		postSubmit(path, cData, 'POST');
	}

	$(window).load(function() {
		$('.loader').fadeOut('slow');
	});

	$(document).ready(function() {
		$('#editbtn').click(function() {
			editMode();

		}); //editbtn
		$('#cancelbtn').click(function() {
			cancelMode();
		});

		$('#updatebtn').click(function() {
			updateTestReport3P();
		});
	});
</script>

<!-- ------------------------------------ -->
<%@include file="../../common/ibcsFooter.jsp"%>
