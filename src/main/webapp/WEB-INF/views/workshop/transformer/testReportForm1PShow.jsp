<%@include file="../../common/wsContractorHeader.jsp"%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
<!-- ------------------------------------------------------ -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;"
			id="editBtnBlock">
			<a href="javascript:void(0)" onclick="editMode()"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="fa fa-1x fa-edit" aria-hidden="true"></span> Edit
			</a>
		</div>
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
		<c:if test="${empty report1P}">
			<div class="col-sm-12 center">
				<p class="red">
					<i>Sorry!!! No Data Found in Database. </i>
				</p>
			</div>
		</c:if>

		<form id="form1" action="">


			<c:if test="${!empty report1P}">
				<div class="col-sm-12 table-responsive">
					<table id="inventoryList" style=""
						class="table table-striped table-hover table-bordered">
						<tbody>
							<tr>
								<td class="bold col-sm-2 success">Type of Work : <input
									type="hidden" id="id" name="id" value="${report1P.id}" /> <input
									type="hidden" id="tsfRegMstId" name="tsfRegMstId"
									value="${report1P.tsfRegMst.id}" />
								</td>
								<td class="col-sm-2">${report1P.tsfRegMst.typeOfWork}</td>
								<td class="bold col-sm-2 success">Manufactured By :</td>
								<td class="col-sm-2">${report1P.tsfRegMst.manufacturedName}</td>
								<td class="bold col-sm-2 success">Previous Repair Date :</td>
								<td class="col-sm-2"><fmt:formatDate
										value="${report1P.prDate}" pattern="dd-MM-yyyy" /> <input
									type="hidden" id="prDate" name="prDate"
									value="${report1P.prDate}" /></td>
							</tr>
							<tr>
								<td class="bold col-sm-2 success">Transformer Type : <input
									type="hidden" id="transformerType" name="transformerType"
									value="Distribution" />
								</td>
								<td>Distribution</td>

								<td class="bold col-sm-2 success">Manufacturing Year :</td>
								<td>${report1P.tsfRegMst.manufacturedYear}</td>

								<td class="bold col-sm-2 success">Repairing/Test Date :</td>
								<td><input type="text" class="form-control datepicker-13"
									value="${report1P.testDate}" id="testDate" name="testDate"
									readonly="readonly" required="required" /></td>

							</tr>
							<tr>
								<td class="bold col-sm-2 success">Transformer S.L :</td>
								<td>${report1P.tsfRegMst.transformerSerialNo}</td>
								<td class="bold col-sm-2 success">Rating (KVA) :</td>
								<td>${report1P.tsfRegMst.kvaRating}</td>
								<td class="bold col-sm-2 success">Job No :</td>
								<td>${report1P.tsfRegMst.jobNo}</td>
							</tr>
						</tbody>
					</table>

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
									readonly="readonly" value="${report1P.mt_ht_g}" type="number"
									id="mt_ht_g" name="mt_ht_g"></td>
								<td class="bold col-sm-2 success">HT-HT :</td>
								<td class="col-sm-2"><input style="width: 100%"
									readonly="readonly" value="${report1P.mt_ht_ht}" type="number"
									id="mt_ht_ht" name="mt_ht_ht"></td>
								<td class="bold col-sm-4 success">Remarks:</td>
							</tr>
							<tr>
								<td class="bold col-sm-2 success">LT-G :</td>
								<td class="col-sm-2"><input style="width: 100%"
									readonly="readonly" value="${report1P.mt_lt_g}" type="number"
									id="mt_lt_g" name="mt_lt_g"></td>
								<td class="bold col-sm-2 success">LT-LT :</td>
								<td class="col-sm-2"><input style="width: 100%"
									readonly="readonly" value="${report1P.mt_lt_lt}" type="number"
									id="mt_lt_lt" name="mt_lt_lt"></td>
								<td class="col-sm-4" rowspan="2"><textarea
										readonly="readonly" style="width: 100%" rows="3" cols=""
										id="mt_remarks" name="mt_remarks">${report1P.mt_remarks}</textarea></td>
							</tr>
							<tr>
								<td class="bold col-sm-2 success">HT-LT :</td>
								<td class="col-sm-2"><input style="width: 100%"
									readonly="readonly" value="${report1P.mt_ht_lt}" type="number"
									id="mt_ht_lt" name="mt_ht_lt"></td>
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
								<td class="bold col-sm-4 default center" colspan="2">HT
									Side (Volt)</td>
								<td class="bold col-sm-4 default center" colspan="2">LT
									Side (Volt)</td>
								<td class="bold col-sm-4 success">Remarks:</td>
							</tr>
							<tr>
								<td class="bold col-sm-2 success">H1-H2 :</td>
								<td class="col-sm-2"><input style="width: 100%"
									readonly="readonly" value="${report1P.vrt_ht_h1_h2}"
									type="number" id="vrt_ht_h1_h2" name="vrt_ht_h1_h2"></td>
								<td class="bold col-sm-2 success">X1-X2 :</td>
								<td class="col-sm-2"><input style="width: 100%"
									readonly="readonly" value="${report1P.vrt_lt_x1_x2}"
									type="number" id="vrt_lt_x1_x2" name="vrt_lt_x1_x2"></td>
								<td class="col-sm-4"><textarea style="width: 100%" rows=""
										readonly="readonly" cols="" id="vrt_remarks"
										name="vrt_remarks">${report1P.vrt_remarks}</textarea></td>
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
								<td class="bold col-sm-9 default center" colspan="6">HT
									Side</td>
								<td class="bold col-sm-3 default center" colspan="2">LT
									Side</td>
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
									readonly="readonly" value="${report1P.sct_ht_h1_h2}"
									type="number" id="sct_ht_h1_h2" name="sct_ht_h1_h2"></td>
								<td class="bold col-sm-1 success">lx :</td>
								<td class="col-sm-2"><input style="width: 100%"
									readonly="readonly" value="${report1P.sct_ht_lx}" type="number"
									id="sct_ht_lx" name="sct_ht_lx"></td>
								<td class="bold col-sm-1 success">WA :</td>
								<td class="col-sm-2"><input style="width: 100%"
									readonly="readonly" value="${report1P.sct_ht_wa}" type="number"
									id="sct_ht_wa" name="sct_ht_wa"></td>
								<td class="bold col-sm-1 success">lx :</td>
								<td class="col-sm-2"><input style="width: 100%"
									readonly="readonly" value="${report1P.sct_lt_lx}" type="number"
									id="sct_lt_lx" name="sct_lt_lx"></td>
							</tr>
							<tr>
								<td class="bold col-sm-7 text-right" colspan="5">Toatl
									Short Circuit Loss:</td>
								<td class="col-sm-5" colspan="3"><input style="width: 100%"
									readonly="readonly" value="${report1P.sct_tsc_loss}"
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
								<td class="bold col-sm-9 default center" colspan="6">LT
									Side</td>
								<td class="bold col-sm-3 default center" colspan="2">HT
									Side Open</td>
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
									readonly="readonly" value="${report1P.oct_lt_h1_h2}"
									type="number" id="oct_lt_h1_h2" name="oct_lt_h1_h2"></td>
								<td class="bold col-sm-1 success">WA :</td>
								<td class="col-sm-2"><input style="width: 100%"
									readonly="readonly" value="${report1P.oct_lt_wa}" type="number"
									id="oct_lt_wa" name="oct_lt_wa"></td>
								<td class="bold col-sm-1 success">lx :</td>
								<td class="col-sm-2"><input style="width: 100%"
									readonly="readonly" value="${report1P.oct_lt_lx}" type="number"
									id="oct_lt_lx" name="oct_lt_lx"></td>
								<td class="bold col-sm-1 success">lx :</td>
								<td class="col-sm-2"><input style="width: 100%"
									readonly="readonly" value="${report1P.oct_ht_lx}" type="number"
									id="oct_ht_lx" name="oct_ht_lx"></td>
							</tr>
							<tr>
								<td class="bold col-sm-7 text-right" colspan="5">Toatl Open
									Circuit Loss:</td>
								<td class="col-sm-5" colspan="3"><input style="width: 100%"
									readonly="readonly" value="${report1P.oct_toc_loss}"
									type="number" id="oct_toc_loss" name="oct_toc_loss"></td>
							</tr>

						</tbody>
					</table>
				</div>
				<!-- Open Circuit Test Table :: end -->

				<div class="col-sm-12 table-responsive"
					style="padding-bottom: 15px;">
					<h5 class="col-sm-4"
						style="text-decoration: underline; font-weight: bold">5. Oil
						Di-Electric Strength Test (Gap 2.5mm):</h5>
					<span class="col-sm-2"> <input style="width: 100%"
						readonly="readonly" value="${report1P.odst}" type="number"
						id="odst" name="odst">
					</span> <span class="bold">KV</span>
				</div>
				<div
					style="background-color: #858585; height: 5px; margin-bottom: 15px;"
					class="col-xs-12">&nbsp;</div>
				<div class="col-md-12" style="padding-top: 15px; display: none;"
					id="updateBtnBlock">
					<div class="col-xs-12 col-sm-6">
						<button type="button" onclick="updateTestReport1P()"
							style="margin-right: 10px; border-radius: 6px;"
							class="width-20 pull-right btn btn-lg btn-success">
							<i class="ace-icon fa fa-save"></i> <span class="bigger-50">Update</span>
						</button>
					</div>

					<div class="col-xs-12 col-sm-6">
						<button type="button" onclick="cancelMode()"
							class="width-20  pull-left btn btn-lg btn-danger"
							style="margin-left: 10px; border-radius: 6px;">
							<i class="ace-icon fa fa-refresh"></i> <span class="bigger-50">Cancel</span>
						</button>
					</div>
				</div>
			</c:if>
		</form>


		<!-- Approval Block :: Start -->

		<div class="col-sm-12" id="approvalBlock">

			<!-- Approval History::start -->
			<c:if test="${!empty approvalHistoryList}">
				<button data-toggle="collapse" data-target="#demo"
					title="View Approval History">
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
										href="Javascript:sendToUpper(${nextMan.stateCode})">For
											${nextMan.buttonName}</a></li>
								</c:forEach>
							</ul>
						</div>
					</c:if>
				</div>
			</div>
		</div>

		<!-- Approval Block :: end -->
	</div>
</div>


<script type="text/javascript">
	function editMode() {
		$('#approvalBlock').css("display", "none");
		$('#updateBtnBlock').css("display", "");
		$('#editBtnBlock').css("display", "none");
		$('#form1 input,#form1 textarea').removeAttr('readonly');

	}

	function cancelMode() {
		$('#approvalBlock').css("display", "");
		$('#updateBtnBlock').css("display", "none");
		$('#editBtnBlock').css("display", "");
		$('#form1 input, #form1 textarea').attr('readonly', 'readonly');
	}

	function updateTestReport1P() {
		var cData = {
			id : $("#id").val(),
			temp : $("#testDate").val(),
			mt_ht_g : $("#mt_ht_g").val(),
			mt_ht_ht : $("#mt_ht_ht").val(),
			mt_lt_g : $("#mt_lt_g").val(),
			mt_lt_lt : $("#mt_lt_lt").val(),
			mt_remarks : $("#mt_remarks").val(),
			mt_ht_lt : $("#mt_ht_lt").val(),
			vrt_ht_h1_h2 : $("#vrt_ht_h1_h2").val(),
			vrt_lt_x1_x2 : $("#vrt_lt_x1_x2").val(),
			vrt_remarks : $("#vrt_remarks").val(),
			sct_ht_h1_h2 : $("#sct_ht_h1_h2").val(),
			sct_ht_lx : $("#sct_ht_lx").val(),
			sct_ht_wa : $("#sct_ht_wa").val(),
			sct_lt_lx : $("#sct_lt_lx").val(),
			sct_tsc_loss : $("#sct_tsc_loss").val(),
			oct_lt_h1_h2 : $("#oct_lt_h1_h2").val(),
			oct_lt_wa : $("#oct_lt_wa").val(),
			oct_lt_lx : $("#oct_lt_lx").val(),
			oct_ht_lx : $("#oct_ht_lx").val(),
			oct_toc_loss : $("#oct_toc_loss").val(),
			odst : $("#odst").val(),
			tsfRegMstId : $("#tsfRegMstId").val()
		}

		var cDataJsonString = JSON.stringify(cData);
		var contextPath = $("#contextPath").val();
		var path = contextPath + '/ws/xf/updateTestReport1P.do';

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
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>
