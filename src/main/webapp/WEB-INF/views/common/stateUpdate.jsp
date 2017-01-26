<%@include file="../common/adminheader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row" style="background-color: white; padding: 10px; padding-left: 20px">
		
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/settings/list/state.do" style="text-decoration: none;" class="btn btn-primary btn-sm"> 
				<span class="glyphicon glyphicon-th-list" aria-hidden="true">
				</span> State List
			</a>

			<h1 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
				Update ${state.stateName} State Information</h1>
		</div>		
		
	</div>
	
	<div class="container">
		<div class="col-xs-8 col-xs-offset-2"
			style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">

			<!-- --------------------- -->
			<form method="POST" action="${pageContext.request.contextPath}/settings/update/existingState.do">
				<div class="col-xs-12 table-responsive">
					<table class="table table-bordered table-hover">
						<tbody>
							<tr>
								<td class="col-xs-3 success text-right" style="font-weight: bold;">State Name:</td>
								<td class="col-xs-9">
									<input type="text" class="form-control" id="stateName" value="${state.stateName }"
										style="border: 0; border-bottom: 2px ridge;" name="stateName">
								</td>
							</tr>
							
							<tr>
								<td class="col-xs-3 success text-right" style="font-weight: bold;">Is Active?</td>
								<td class="col-xs-9">
									<c:if test="${state.active eq true}">
										<input type="checkbox" checked name="isactive" id="isactive" />
										<input type="hidden" id="active" name="active" value="true">
									</c:if> <c:if test="${state.active eq false}">
										<input type="checkbox" name="isactive" id="isactive" />
										<input type="hidden" id="active" name="active" value="false">
									</c:if>
									
								</td>
							</tr>
							
							<tr>
								<td class="col-xs-3 success text-right" style="font-weight: bold;">Remarks:</td>
								<td class="col-xs-9">
									<textarea rows="4" cols="" id="remarks" class="form-control"
										maxlength="1000" name="remarks">${state.remarks}</textarea>
										<input type="hidden" id="id" value="${state.id}" name="id">
								</td>
							</tr>
						</tbody>
					</table>
					<div class="col-md-12">
						<div class="form-group"
							style="margin-top: 2em; margin-right: 1em;" align="center">

							<button type="submit" class="btn btn-lg btn-success"
								style="border-radius: 6px;">
								<i class="ace-icon fa fa-save"></i> <span class="bigger-50">Update</span>
							</button>

							<button type="reset" class="btn btn-lg btn-danger"
								style="margin-right: 10px; border-radius: 6px;">
								<i class="ace-icon fa fa-refresh"></i> <span class="bigger-50">Reset</span>
							</button>
						</div>
					</div>
				</div>
			</form>
			<!-- --------------------- -->
		</div>
	</div>	
</div>

<script>
	$( document ).ready( function() {
		$('#isactive').click( function() {
			if( $('#isactive').prop('checked') == true ) {
				$('#active').val('true');
			} else {
				$('#active').val('false');
			}
			//alert( $('#active').val() );
		});
	});
</script>


<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>