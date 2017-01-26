<%@include file="../../common/budgetHeader.jsp"%>
<!-- ------End of Header------ -->

<!-- @author: Ihteshamul Alam -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
		</div>

		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Budget for APP</h1>
	</div>

	<div class="container">
	
		<div class="col-xs-8 col-xs-offset-2" style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
			<form method="POST" action="${pageContext.request.contextPath}/bgt/budgetAPPMstList.do">
				<div class="table-responsive col-xs-12">
					<table class="table table-bordered table-hover">
						<tbody>
							<tr>
								<td class="col-xs-2 success text-right" style="font-weight: bold;">Financial Year:</td>
								<td class="col-xs-8">
									<select class="form-control" name="id" id="sessionName">
										<option selected="selected" disabled="disabled">Select Financial Year</option>
										<c:forEach items="${descoSession}" var="sessions">
											<option value="${sessions.id}">${sessions.sessionName}</option>
										</c:forEach>
									</select>
									<strong class="text-danger sessionName text-center hide" style="font-weight: bold; font-size: 16px;">Invalid Session</strong>
								</td>
							</tr>
							<tr>
								<td class="col-xs-2 success text-right" style="font-weight: bold;">Package Type:</td>
								<td class="col-xs-8">
									<select class="form-control" name="packageType" id="packageType">
										<option value="">Select Package Type</option>
										<option value="1">GOODS</option>
										<option value="2">WORKS  &amp; SERVICES</option>
										<option value="3">MISCELLANEOUS</option>
										
									</select>
									<strong class="text-danger sessionName text-center hide" style="font-weight: bold; font-size: 16px;">Invalid Session</strong>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="col-xs-12" align="center">
					<button type="button" id="summaryButton" class="btn btn-success btn-lg" style="border-radius: 6px;">
						<i class="fa fa-fw fa-eye"></i>&nbsp;View
					</button>
				</div>
			</form>
		</div>
		
	</div>
</div>

<script>
	$( document ).ready( function() {
		$('#summaryButton').click( function() {
			var haserror = false;
			
			if( $('#sessionName').val() == null || $.trim( $('#sessionName').val() ) == '0' ) {
				$('.sessionName').removeClass('hide');
				haserror = true;
			} else {
				$('.sessionName').addClass('hide');
			}
			
			if( haserror == true ) {
				return;
			} else {
				$('form').submit();
			}
		});
	});
</script>

<!-- ------ Footer ------ -->
<%@include file="../../common/ibcsFooter.jsp"%>