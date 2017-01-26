<%@include file="../common/adminheader.jsp"%>
<!-- Header -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/adminPanel.do"
				style="text-decoration: none;" class="btn btn-success btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Role List
			</a>
		</div>
		
		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
			Add New Role</h1>
	</div>
	<div class="row" style="background-color: white; padding: 10px; margin: 10px;">
		<form action="${pageContext.request.contextPath}/adminpanel/role/save.do" method="POST">
			<input name="id" value="${roles.id}" hidden="true">
			<input name="role_id" value="${roles.role_id}" hidden="true">
			<div class="col-xs-12 table-responsive">
				<table class="table table-bordered table-hover">
					<tbody>
						<tr>
							<td class="col-xs-3 success text-right" style="font-weight: bold;">Role Name:&nbsp;<strong class='red'>*</strong></td>
							<td>
								<div class="form-group">
									<div class="col-xs-11">
										<input type="text" style="text-transform: uppercase"
											class="form-control" onblur="checkUserRole()" id="role"
											name="role" value="${roles.role}">
									</div>
									<span> <i id="workOrderDecision" style="font-size: 2em;"></i>
									</span>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
				<div class="col-xs-12" align="center">
					<button type="button" id="saveButton" class="btn btn-success"
						disabled style="border-radius: 6px;">
						<i class="ace-icon fa fa-save"></i> <span class="bigger-50">Submit</span>
					</button>
					
					<button type="reset" class="btn btn-danger" style="border-radius: 6px;">
						<i class="ace-icon fa fa-refresh"></i> <span class="bigger-50">Reset</span>
					</button>
					
				</div>
			</div>

			<input type="hidden" name="contextPath" id="contextPath"
				value="${pageContext.request.contextPath}">
			<input type="hidden" id="loadresponse" value="1" />
		</form>
	</div>
</div>

<script>
	function checkUserRole() {
		var role = $("#role").val();
		var submitButton = $("#saveButton");
	
		var contextPath = $('#contextPath').val();
	
		var workOrderDecision = $("#workOrderDecision");
		
		$('#loadresponse').load( contextPath + '/adminpanel/checkUserRole.do', { 'role':role }, function(d) {
			if( d == "success" ) {
				submitButton.prop("disabled", false);
				workOrderDecision.removeClass();
				workOrderDecision.addClass("glyphicon glyphicon-ok-sign green");
			} else {
				submitButton.prop("disabled", true);
				workOrderDecision.removeClass();
				workOrderDecision.addClass("glyphicon glyphicon-remove-sign red");
			}
		});//
		
		$('#saveButton').click( function() {
			$('#saveButton').prop('disabled', true);
			$('form').submit();
		}); 
	}
</script>

<!-- Footer -->
<%@include file="../common/ibcsFooter.jsp"%>