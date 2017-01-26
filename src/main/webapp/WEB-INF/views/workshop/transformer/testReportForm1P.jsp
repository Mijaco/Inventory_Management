<%@include file="../../common/wsContractorHeader.jsp"%>
<%@ page import="java.util.Date"%>
<jsp:useBean id="now" class="java.util.Date" />
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

.bold {
	font-weight: bold;
}
</style>
<!-- -------------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">

		<h2 class="center blue"
			style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
			Transformer Test Report(1-PHASE)</h2>
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


	<div class="col-sm-12"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">

		<form method="POST"
			action="${pageContext.request.contextPath}/ws/xf/saveTestReport1P.do">
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
							<!-- <input type="hidden" id="" name=""/> -->
							<tr>
								<td class="bold col-sm-2 success">Type of Work : <input
									type="hidden" id="id" name="id" value="${transformer.id}" />
								</td>
								<td class="col-sm-2">${transformer.typeOfWork}</td>
								<td class="bold col-sm-2 success">Manufactured By :</td>
								<td class="col-sm-2">${transformer.manufacturedName}</td>
								<td class="bold col-sm-2 success">Previous Repair Date :</td>
								<td class="col-sm-2"><fmt:formatDate
										value="${transformer.previousRepairDate}" pattern="dd-MM-yyyy" />
									<input type="hidden" id="prDate" name="prDate"
									value="${transformer.previousRepairDate}" /></td>
							</tr>
							<tr>
								<td class="bold col-sm-2 success">Transformer Type : <input
									type="hidden" id="transformerType" name="transformerType"
									value="Distribution" />
								</td>
								<td>Distribution</td>

								<td class="bold col-sm-2 success">Manufacturing Year :</td>
								<td>${transformer.manufacturedYear}</td>

								<td class="bold col-sm-2 success">Repairing/Test Date :</td>
								<td><input type="text" class="form-control datepicker-13"
									id="testDate" name="testDate" /></td>

							</tr>
							<tr>
								<td class="bold col-sm-2 success">Transformer S.L :</td>
								<td>${transformer.transformerSerialNo}</td>
								<td class="bold col-sm-2 success">Rating (KVA) :</td>
								<td>${transformer.kvaRating}</td>
								<td class="bold col-sm-2 success">Job No :</td>
								<td>${transformer.jobNo}</td>
							</tr>
						</tbody>
					</table>
				</c:if>
			</div>
			<!-- Master Info :: end -->

			<!-- Megger Test Block :: start -->
			<div class="col-sm-12 table-responsive">
				<h5 style="text-decoration: underline; font-weight: bold">1.
					Megger Test (Mega ohm):</h5>
				<table class="table table-bordered" id="">
					<tbody>
						<tr>
							<td class="bold col-sm-2 success">HT-G :</td>
							<td class="col-sm-2"><input style="width: 100%"
								type="number" id="mt_ht_g" name="mt_ht_g"></td>
							<td class="bold col-sm-2 success">HT-HT :</td>
							<td class="col-sm-2"><input style="width: 100%"
								type="number" id="mt_ht_ht" name="mt_ht_ht"></td>
							<td class="bold col-sm-4 success">Remarks:</td>
						</tr>
						<tr>
							<td class="bold col-sm-2 success">LT-G :</td>
							<td class="col-sm-2"><input style="width: 100%"
								type="number" id="mt_lt_g" name="mt_lt_g"></td>
							<td class="bold col-sm-2 success">LT-LT :</td>
							<td class="col-sm-2"><input style="width: 100%"
								type="number" id="mt_lt_lt" name="mt_lt_lt"></td>
							<td class="col-sm-4" rowspan="2"><textarea
									style="width: 100%" rows="3" cols="" id="mt_remarks"
									name="mt_remarks"></textarea></td>
						</tr>
						<tr>
							<td class="bold col-sm-2 success">HT-LT :</td>
							<td class="col-sm-2"><input style="width: 100%"
								type="number" id="mt_ht_lt" name="mt_ht_lt"></td>
							<td class=""></td>
							<td class=""></td>
						</tr>
					</tbody>
				</table>
			</div>
			<!-- Megger Test Table :: end -->

			<!-- Voltage Ratio Test Block :: start -->
			<div class="col-sm-12 table-responsive">
				<h5 style="text-decoration: underline; font-weight: bold">2.
					Voltage Ratio Test :</h5>
				<table class="table table-bordered" id="">
					<tbody>
						<tr>
							<td class="bold col-sm-4 default center" colspan="2">HT Side
								(Volt)</td>
							<td class="bold col-sm-4 default center" colspan="2">LT Side
								(Volt)</td>
							<td class="bold col-sm-4 success">Remarks:</td>
						</tr>
						<tr>
							<td class="bold col-sm-2 success">H1-H2 :</td>
							<td class="col-sm-2"><input style="width: 100%"
								type="number" id="vrt_ht_h1_h2" name="vrt_ht_h1_h2"></td>
							<td class="bold col-sm-2 success">X1-X2 :</td>
							<td class="col-sm-2"><input style="width: 100%"
								type="number" id="vrt_lt_x1_x2" name="vrt_lt_x1_x2"></td>
							<td class="col-sm-4"><textarea style="width: 100%" rows=""
									cols="" id="vrt_remarks" name="vrt_remarks"></textarea></td>
						</tr>

					</tbody>
				</table>
			</div>
			<!-- Voltage Ratio Test Table :: end -->

			<!-- Short Circuit Test Block :: start -->
			<div class="col-sm-12 table-responsive">
				<h5 style="text-decoration: underline; font-weight: bold">3.
					Short Circuit Test :</h5>
				<table class="table table-bordered" id="">
					<tbody>
						<tr>
							<td class="bold col-sm-9 default center" colspan="6">HT Side</td>
							<td class="bold col-sm-3 default center" colspan="2">LT Side</td>
						</tr>
						<tr class="active">
							<td class="bold col-sm-3 default center" colspan="2">Volt
								(V)</td>
							<td class="bold col-sm-3 default center" colspan="2">Current
								(A)</td>
							<td class="bold col-sm-3 default center" colspan="2">(Watt)*6</td>
							<td class="bold col-sm-3 default center" colspan="2">Current
								(A)</td>
						</tr>
						<tr>
							<td class="bold col-sm-1 success">H1-H2 :</td>
							<td class="col-sm-2"><input style="width: 100%"
								type="number" id="sct_ht_h1_h2" name="sct_ht_h1_h2"></td>
							<td class="bold col-sm-1 success">lx :</td>
							<td class="col-sm-2"><input style="width: 100%"
								type="number" id="sct_ht_lx" name="sct_ht_lx"></td>
							<td class="bold col-sm-1 success">WA :</td>
							<td class="col-sm-2"><input style="width: 100%"
								type="number" id="sct_ht_wa" name="sct_ht_wa"></td>
							<td class="bold col-sm-1 success">lx :</td>
							<td class="col-sm-2"><input style="width: 100%"
								type="number" id="sct_lt_lx" name="sct_lt_lx"></td>
						</tr>
						<tr>
							<td class="bold col-sm-7 text-right" colspan="5">Toatl Short
								Circuit Loss:</td>
							<td class="col-sm-5" colspan="3"><input style="width: 100%"
								type="number" id="sct_tsc_loss" name="sct_tsc_loss"></td>
						</tr>

					</tbody>
				</table>
			</div>
			<!-- Short Circuit Test Table :: end -->

			<!-- Open Circuit Test Block :: start -->
			<div class="col-sm-12 table-responsive">
				<h5 style="text-decoration: underline; font-weight: bold">4.
					Open Circuit Test :</h5>
				<table class="table table-bordered" id="">
					<tbody>
						<tr>
							<td class="bold col-sm-9 default center" colspan="6">LT Side</td>
							<td class="bold col-sm-3 default center" colspan="2">HT Side
								Open</td>
						</tr>
						<tr class="active">
							<td class="bold col-sm-3 default center" colspan="2">Volt
								(V)</td>
							<td class="bold col-sm-3 default center" colspan="2">(Watt)*6</td>
							<td class="bold col-sm-3 default center" colspan="2">Current
								(A)</td>
							<td class="bold col-sm-3 default center" colspan="2">Current
								(A)</td>
						</tr>
						<tr>
							<td class="bold col-sm-1 success">H1-H2 :</td>
							<td class="col-sm-2"><input style="width: 100%"
								type="number" id="oct_lt_h1_h2" name="oct_lt_h1_h2"></td>
							<td class="bold col-sm-1 success">WA :</td>
							<td class="col-sm-2"><input style="width: 100%"
								type="number" id="oct_lt_wa" name="oct_lt_wa"></td>
							<td class="bold col-sm-1 success">lx :</td>
							<td class="col-sm-2"><input style="width: 100%"
								type="number" id="oct_lt_lx" name="oct_lt_lx"></td>
							<td class="bold col-sm-1 success">lx :</td>
							<td class="col-sm-2"><input style="width: 100%"
								type="number" id="oct_ht_lx" name="oct_ht_lx"></td>
						</tr>
						<tr>
							<td class="bold col-sm-7 text-right" colspan="5">Toatl Open
								Circuit Loss:</td>
							<td class="col-sm-5" colspan="3"><input style="width: 100%"
								type="number" id="oct_toc_loss" name="oct_toc_loss"></td>
						</tr>

					</tbody>
				</table>
			</div>
			<!-- Open Circuit Test Table :: end -->

			<div class="col-sm-12 table-responsive" style="padding-bottom: 15px;">
				<h5 class="col-sm-4"
					style="text-decoration: underline; font-weight: bold">5. Oil
					Di-Electric Strength Test (Gap 2.5mm):</h5>
				<span class="col-sm-2"> <input style="width: 100%"
					type="number" id="odst" name="odst">
				</span> <span class="bold">KV</span>
			</div>
			<div style="background-color: #858585; height: 5px;"
				class="col-xs-12">&nbsp;</div>
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


<script type="text/javascript">
	function createTR(n) {
		var id = $('#pk' + n).val();
		var contextPath = $('#contextPath').val();
		var path = contextPath + '/ws/xf/testReportForm.do';
		var cData = {
			id : id
		};

		postSubmit(path, cData, 'POST');
	}
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>
