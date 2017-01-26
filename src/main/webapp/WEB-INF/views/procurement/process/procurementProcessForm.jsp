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
			<h1 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
				<em> Procurement Committee Form </em>
			</h1>
		</div>
	</div>
	
	<div class="container">
			<div class="col-md-8 col-md-offset-2" style="background-color: white; padding: 10px; padding-left: 20px; 
				margin-top: 10px; margin-bottom: 10px;">
				<div class="table-responsive">
				<form action="${pageContext.request.contextPath}/process/procurementProcessSave.do" method="POST" class="form-horizontal">
					<table class="table table-bordered">
						<tbody>
							<tr>
								<td class="col-xs-3 text-right success" style="font-weight: bold; vertical-align: middle;">Username</td>
								<td class="col-xs-8">
									<select name="userid" id="userid" style="width: 100%;">
										<option value="0" disabled selected>Select User</option>
										<c:if test="${!empty userlist}">
											<c:forEach items="${userlist}" var="user">
												<option value="${user.id}">${user.name} (${user.designation})</option>
											</c:forEach>
										</c:if>
									</select>
									<h4 class="text-danger userid hide"><strong>Invalid username</strong></h4>
								</td>
							</tr>
							
							<tr>
								<td class="col-xs-3 text-right success" style="font-weight: bold; vertical-align: middle;">Is Active?</td>
								<td class="col-xs-8">
									<div class="checkbox">
										<label> <input type="checkbox" name="chkbox" id="chkbox" checked> Active
										</label>
										<input type="hidden" id="active" name="active" value="1">
									</div>
								</td>
							</tr>
							
							<tr>
								<td class="col-xs-3 text-right success" style="font-weight: bold; vertical-align: middle;">Remarks</td>
								<td class="col-xs-8">
									<textarea name="remarks" id="remarks" style="width: 100%;"></textarea>
								</td>
							</tr>
						</tbody>
					</table>
					<div class="col-xs-12" align="center">
						<button type="button" id="saveProcess" class="btn btn-md btn-success" style="border-radius: 6px;"><i class="fa fa-fw fa-save"></i>&nbsp; Save</button>
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
		
		$('#saveProcess').click( function() {
			//userid
			var haserror = false;
			
			if( $('#userid').val() == null || $.trim( $('#userid').val() ) == '0' ) {
				haserror = true;
				$('.userid').removeClass('hide');
			} else {
				$('.userid').addClass('hide');
			}
			
			if( haserror == true ) {
				return;
			} else {
				$('form').submit();
			}
		});
		
	});
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>