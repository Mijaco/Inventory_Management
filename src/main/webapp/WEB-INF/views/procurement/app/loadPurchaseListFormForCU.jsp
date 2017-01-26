<%@include file="../../common/procurementHeader.jsp"%>
<!-- ------End of Header------ -->

<!-- @author: Ihteshamul Alam -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
			APP Wise Purchase List</h1>
	</div>

	<div class="container">
	
		<div class="col-xs-8 col-xs-offset-2" style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
			<form action="${pageContext.request.contextPath}/app/purchase/getAppPurchaseList.do">
			<input type="hidden" name="contextPath" id="contextPath"
				value="${pageContext.request.contextPath}">
				<div class="table-responsive col-xs-12">
					<table class="table table-bordered table-hover">
						<tbody>
							<tr>
								<td class="col-xs-2 success text-right" style="font-weight: bold;">Sessions:</td>
								<td class="col-xs-8">
									<select class="form-control" name="id" id="sessionName" onchange="descoSessionLeaveChange(this)">
										<option value="0">Financial Year</option>
										<c:forEach items="${descoSessionList}" var="descoSession">
											<option value="${descoSession.id}">${descoSession.sessionName}</option>
										</c:forEach>
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