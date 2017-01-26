<%@include file="../../common/procurementHeader.jsp"%>
<!-- -------------------------------------------------------------------------------------- -->

<!-- Author: Shimul, IBCS -->

<style>

	textarea {
		resize: none;
	}

</style>

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">

		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/process/procurementProcessList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"> </span>
				Procurement Committee List
			</a>
			<h2 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
				<em> Edit Procurement Committee Information </em>
			</h2>
		</div>
	</div>
	
	<div class="container">
			<div class="col-md-8 col-md-offset-2" style="background-color: white; padding: 10px; padding-left: 20px; 
				margin-top: 10px; margin-bottom: 10px;">
				<div class="table-responsive">
				<form action="${pageContext.request.contextPath}/process/procurementProcessUpdate.do" method="POST" class="form-horizontal">
					<table class="table table-bordered">
						<tbody>
							<tr>
								<td class="col-xs-3 text-right success" style="font-weight: bold; vertical-align: middle;">Username *</td>
								<td class="col-xs-8">
									<input type="text" value="${ppComm.authUser.name}" readonly style="width: 100%;">
									<input type="hidden" id="id" name="id" value="${ppComm.id}">
								</td>
							</tr>
							
							<tr>
								<td class="col-xs-3 text-right success" style="font-weight: bold; vertical-align: middle;">Is Active?</td>
								<td class="col-xs-8">
									<div class="checkbox">
										<c:if test="${ppComm.active == true}">
											<label> <input type="checkbox" name="chkbox"
												id="chkbox" checked> Active
											</label>
											<input type="hidden" id="active" name="active" value="1">
										</c:if>
										
										<c:if test="${ppComm.active == false}">
											<label> <input type="checkbox" name="chkbox"
												id="chkbox"> Active
											</label>
											<input type="hidden" id="active" name="active" value="0">
										</c:if>
									</div>
								</td>
							</tr>
							
							<tr>
								<td class="col-xs-3 text-right success" style="font-weight: bold; vertical-align: middle;">Remarks</td>
								<td class="col-xs-8">
									<textarea name="remarks" id="remarks" style="width: 100%;">${ppComm.remarks}</textarea>
								</td>
							</tr>
						</tbody>
					</table>
					<div class="col-xs-12" align="center">
						<button type="submit" id="saveProcess" class="btn btn-md btn-success" style="border-radius: 6px;"><i class="fa fa-fw fa-repeat"></i>&nbsp; Update</button>
					</div>
				</form>
			</div>
			</div>
	</div>
</div>

<script>
	$( document ).ready( function() {
		
		$('#chkbox').click( function() {
			if( $('#chkbox').is(':checked') ) {
				$('#active').val('1');
			} else {
				$('#active').val('0');
			}
		}); //checkbox
		
	});
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>