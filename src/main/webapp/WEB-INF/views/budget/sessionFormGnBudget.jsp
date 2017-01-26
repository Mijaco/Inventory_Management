<%@include file="../common/budgetHeader.jsp"%>

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Financial
			Year wise Budget for General Items</h1>
	</div>

	<div class="container">

		<div class="col-xs-8 col-xs-offset-2"
			style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
			<form method="POST"
				action="${pageContext.request.contextPath}/budget/generalItemList.do">
				<div class="table-responsive col-xs-12">
					<table class="table table-bordered table-hover">
						<tbody>
							<tr>
								<td class="col-xs-2 success text-right"
									style="font-weight: bold;">Financial Year:</td>
								<td class="col-xs-8"><select class="form-control" name="id"
									id="sessionName" required="required">
										<option value="">Select Financial Year</option>
										<c:forEach items="${descoSessionList}" var="sessions">
											<option value="${sessions.id}">${sessions.sessionName}</option>
										</c:forEach>
								</select> <strong class="text-danger sessionName text-center hide"
									style="font-weight: bold; font-size: 16px;">Invalid
										Financial Year!</strong></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="col-xs-12" align="center">
					<button type="submit" id="summaryButton"
						class="btn btn-success btn-lg" style="border-radius: 6px;">
						<i class="fa fa-fw fa-eye"></i>&nbsp;View
					</button>
				</div>
			</form>
		</div>

	</div>
</div>

<script>
	$(document).ready(
			function() {
				$('#summaryButton').click(
						function() {
							var haserror = false;

							if ($('#sessionName').val() == null
									|| $.trim($('#sessionName').val()) == '') {
								$('.sessionName').removeClass('hide');
								haserror = true;
							} else {
								$('.sessionName').addClass('hide');
							}

						});
			});
</script>

<!-- ------ Footer ------ -->
<%@include file="../common/ibcsFooter.jsp"%>