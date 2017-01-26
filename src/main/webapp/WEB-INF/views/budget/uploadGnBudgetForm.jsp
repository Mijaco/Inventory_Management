<%@include file="../common/budgetHeader.jsp"%>
<!-- ------End of Header------ -->

<!-- @author: Ihteshamul Alam -->

<style>
.loader {
	position: absolute;
	left: 50%;
	top: 20%;
	z-index: 9999;
}
</style>

<div class="loader hide">
	<i class="fa fa-refresh fa-spin fa-3x fa-fw"></i> <br> <span
		class="orange">Uploading...</span>
</div>

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;"></div>

		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
			Budget(General Items) Upload Form</h1>
	</div>

	<div class="container">

		<div class="col-xs-8 col-xs-offset-2"
			style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
			<form method="POST"
				action="${pageContext.request.contextPath}/budget/uploadGnItemXLS.do"
				enctype="multipart/form-data">
				<div class="table-responsive col-xs-12">
					<table class="table table-bordered table-hover">
						<tbody>
							<tr>
								<td class="col-xs-2 success text-right">Financial Year:</td>
								<td class="col-xs-8"><select class="form-control" name="id"
									style="border: 0; border-bottom: 2px ridge;" required="required"
									id="sessionName">
										<option value="" >Select
											Financial Year</option>
										<c:forEach items="${descoSession}" var="sessions">
											<option value="${sessions.id}">${sessions.sessionName}</option>
										</c:forEach>
								</select> <strong class="text-danger sessionNameWaring text-center hide"
									style="font-weight: bold; font-size: 16px;">Invalid
										Session</strong></td>
							</tr>
							<tr>
								<td class="col-xs-2 success text-right"><label
									for="uploadGnBtExcelFile" class="">Upload Excel:</label></td>

								<td class="col-xs-8"><input type="file" required="required"
									id="uploadGnBtExcelFile" accept="application/vnd.ms-excel"
									class='form-control' placeholder="upload a .xls File"
									style="border: 0; border-bottom: 2px ridge;"
									name="uploadGnBtExcelFile" /> <strong
									class="text-danger uploadFileWarning text-center hide"
									style="font-weight: bold; font-size: 16px;">Please
										Select an Excel File.</strong></td>

							</tr>
						</tbody>
					</table>
				</div>
				<div class="col-xs-12" align="center">
					<button type="button" id="submitButton"
						class="btn btn-success btn-md" style="border-radius: 6px;">
						<i class="fa fa-fw fa-eye"></i>Upload Budget
					</button>
				</div>
			</form>
		</div>

	</div>
</div>

<script>
	$(document).ready(function() {
		$('#submitButton').click(function() {
			var haserror = false;

			if ($('#uploadGnBtExcelFile').val() == null || $.trim( $('#uploadGnBtExcelFile').val() ) == '' ) {
				$('.uploadFileWarning').removeClass('hide');
				haserror = true;
			} else {
				$('.uploadFileWarning').addClass('hide');
			}
			
			if ($('#sessionName').val() == null || $.trim( $('#sessionName').val() ) == '' ) {
				$('.sessionNameWaring').removeClass('hide');
				haserror = true;
			} else {
				$('.sessionNameWaring').addClass('hide');
			}

			if (haserror == true) {
				return;
			} else {
				$('form').submit();
				$('#submitButton').prop('disabled', true);
				$('.loader').removeClass('hide');
			}
		});
	});
</script>

<!-- ------ Footer ------ -->
<%@include file="../common/ibcsFooter.jsp"%>