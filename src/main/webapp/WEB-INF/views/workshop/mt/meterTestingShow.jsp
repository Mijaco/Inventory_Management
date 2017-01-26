<%@include file="../../common/wsHeader.jsp"%>
<link
	href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
	rel="stylesheet">

<style>
textarea {
	resize: none;
}
</style>
<!-- -------------------End of Header-------------------------- -->
<!-- @author nasrin -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/mt/registerList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Meter
				Testing List
			</a>
		</div>

		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Meter
			Testing (Ref. No: ${meterTestingRegister.refNo})</h1>
		<h2 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Meter
			No : ${meterTestingRegister.meterNo}</h2>
	</div>

	<div class="container">
		<div class="row"
			style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
			<!-- --------------------- -->
			<c:if test="${!empty meterTestingRegister}">

				<table id="meterTestingRegister"
					class="table table-striped table-hover table-bordered">
					<tbody>
						<tr>
							<th>Reference No.</th>
							<td><c:out value="${meterTestingRegister.refNo}" /></td>
							<th>S&amp;D Division</th>
							<td><c:out value="${meterTestingRegister.senderDeptName}" /></td>
							<th>Current Status</th>
							<td><c:out value="${meterTestingRegister.status}" /></td>
						</tr>
						<tr>
							<th>Consumer Name</th>
							<td><c:out value="${meterTestingRegister.consumerName}" /></td>
							<th>Consumer Address</th>
							<td><c:out value="${meterTestingRegister.consumerAddress}" /></td>
							<th>Sanctioned Load</th>
							<td><c:out value="${meterTestingRegister.sanctionedLoad}" /></td>
						</tr>
						<tr>
							<th>Meter No.</th>
							<td><c:out value="${meterTestingRegister.meterNo}" /></td>
							<th>Meter Type</th>
							<td><c:out value="${meterTestingRegister.meterType}" /></td>
							<th>Meter Source</th>
							<td><c:out value="${meterTestingRegister.meterSource}" /></td>
						</tr>
						<tr>
							<th>OMF &amp; DMF</th>
							<td><c:out value="${meterTestingRegister.omfAndDmf}" /></td>
							<th>Received Date</th>
							<td><c:out value="${meterTestingRegister.receivedDate}" /></td>
							<th>Test Date</th>
							<td><c:out value="${meterTestingRegister.testDate}" /></td>
						</tr>
						<tr>
							<th>CT Ratio (Line)</th>
							<td><c:out value="${meterTestingRegister.ctRatioLine}" /></td>
							<th>CT Ratio (Meter)</th>
							<td><c:out value="${meterTestingRegister.ctRatioMeter}" /></td>
							<th>Reading As Left</th>
							<td><c:out value="${meterTestingRegister.readingAsLeft}" /></td>
						</tr>
						<tr>
							<th>Seal Information</th>
							<td><c:out value="${meterTestingRegister.sealInfo}" /></td>
							<th>Receiver Name</th>
							<td><c:out value="${meterTestingRegister.receiverName}" /></td>
							<th>Delivery Date</th>
							<td><c:out value="${meterTestingRegister.deliveryDate}" /></td>
						</tr>
						<tr>							
							<th>Remarks</th>
							<td colspan="5"><c:out value="${meterTestingRegister.remarks}" /></td>
						</tr>
					</tbody>
				</table>
			</c:if>
			<!-- --------------------- -->
		</div>
	</div>
	<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
	<%@include file="../../common/ibcsFooter.jsp"%>