<%@include file="../../common/procurementHeader.jsp"%>
<!-- ------End of Header------ -->


<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
			APP Wise Purchase Form</h1>
	</div>

	<div class="container">
	
		<div class="col-xs-8 col-xs-offset-2" style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
			<form method="POST" action="${pageContext.request.contextPath}/app/purchase/getProcurementForm.do">
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
							<tr class="annexureNoTr hidden">
								<td class="col-xs-2 success text-right" style="font-weight: bold;">Annexure No:</td>
								<td class="col-xs-8">
									<select class="form-control annexureNo" name="annexureNo" id="annexureNo">
										<option value="0">Select Annexure</option>
									</select>
									<strong class="text-danger annexureType text-center hide" style="font-weight: bold; font-size: 16px;">Invalid Annexure No</strong>
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
			
			if( $('#annexureNo').val() == null || $.trim( $('#annexureNo').val() ) == '0' ) {
				$('.annexureNo').removeClass('hide');
				haserror = true;
			} else {
				$('.annexureNo').addClass('hide');
			}
			
			if( haserror == true ) {
				return;
			} else {
				$('form').submit();
			}
		});
	});
	
	function descoSessionLeaveChange(element) {
		// alert($(element).val());

		var contextPath = $("#contextPath").val()
		var sessionId = $(element).val();
		$.ajax({
			url : contextPath + '/app/purchase/procPackage.do',
			data : "{id: '" + sessionId + "'}",
			contentType : "application/json",
			success : function(data) {
				var procPackList = JSON.parse(data);
				var annexureNos = $('.annexureNo');
				var annexureNoTr = $('.annexureNoTr');
				annexureNoTr.removeClass('hidden');

				annexureNos.empty();
				// csStoreTicketNos.removeClass('hidden');

				annexureNos.append($("<option></option>").attr("value", '')
						.text('Select an Annexure'));
				$.each(procPackList, function(annexureNo, annexureNo) {
					annexureNos.append($("<option></option>").attr("value",
							this.annexureNo).text(this.annexureNo));
				});
			},
			error : function(data) {
				alert("Server Error");
			},
			type : 'POST'
		});
	}
</script>

<!-- ------ Footer ------ -->
<%@include file="../../common/ibcsFooter.jsp"%>