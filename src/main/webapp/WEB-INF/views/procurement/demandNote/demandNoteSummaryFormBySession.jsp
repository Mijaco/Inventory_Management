<%@include file="../../common/procurementHeader.jsp"%>
<!-- ------End of Header------ -->

<!-- @author: Ihteshamul Alam -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
<%-- 			<a href="${pageContext.request.contextPath}/proc/contractorList.do" --%>
<!-- 				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span -->
<!-- 				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> -->
<!-- 				Contractor List -->
<!-- 			</a> -->
		</div>

		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Requirement Summary</h1>
	</div>

	<div class="container">
	
		<div class="col-xs-8 col-xs-offset-2" style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
			<form method="POST" action="${pageContext.request.contextPath}/mps/dn/demandNoteSummaryBySession.do">
				<div class="table-responsive col-xs-12">
					<table class="table table-bordered table-hover">
						<tbody>
							<tr>
								<td class="col-xs-2 success text-right" style="font-weight: bold;">Sessions:</td>
								<td class="col-xs-8">
									<select class="form-control" name="id" id="sessionName">
										<option value="0">Select Session</option>
										<c:forEach items="${descoSession}" var="sessions">
											<option value="${sessions.id}">${sessions.sessionName}</option>
										</c:forEach>
									</select>
									<strong class="text-danger sessionName text-center hide" style="font-weight: bold; font-size: 16px;">Invalid Session</strong>
								</td>
							</tr>
							<tr>
								<td class="col-xs-2 success text-right" style="font-weight: bold;">Annexure Type:</td>
								<td class="col-xs-8">
									<select class="form-control" name="annexureType" id="annexureType">
										<option value="0">Select Annexure</option>
										<option value="1">Annexure - 1</option>
										<option value="2">Annexure - 2</option>
										<option value="3">Annexure - 3</option>
									</select>
									<strong class="text-danger annexureType text-center hide" style="font-weight: bold; font-size: 16px;">Invalid Annexure Type</strong>
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
			
			if( $('#annexureType').val() == null || $.trim( $('#annexureType').val() ) == '0' ) {
				$('.annexureType').removeClass('hide');
				haserror = true;
			} else {
				$('.annexureType').addClass('hide');
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