<%@include file="../common/pettyCashHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<!-- @author: Ihteshamul Alam, IBCS -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row" style="background-color: white; padding: 10px; padding-left: 20px">
		
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/pettycash/pettyCashHeadList.do" style="text-decoration: none;" class="btn btn-primary btn-sm"> 
				<span class="glyphicon glyphicon-th-list" aria-hidden="true">
				</span> Petty Cash Head List
			</a>

			<h1 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
				Petty Cash Head Entry Form</h1>	
			
		</div>		
		
	</div>
	
	<div class="container">
			<div class="col-sm-8 col-sm-offset-2" style="background-color: white; padding: 10px; padding-left: 20px; 
			margin-top: 10px; margin-bottom: 10px;">
		
		<!-- --------------------- -->
		<form method="POST" action="${pageContext.request.contextPath}/pettycash/pettyCashHeadSave.do">
			<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">
			<div class="col-sm-12 table-responsive">
				<table class="table table-bordered table-hover">
					<tbody>
						<tr>
							<td class="col-sm-3 success text-right" style="font-weight: bold;">Code No:</td>
							<td class="col-sm-9">
								<input type="text" class="form-control" id="pettyCashCode"
									placeholder="Code No" style="border: 0; border-bottom: 2px ridge;"
									name="pettyCashCode" required>
									<h5 class="text-danger codeerror hide"><strong>Invalid Code</strong></h5>
							</td>
						</tr>
						<tr>
							<td class="col-sm-3 success text-right" style="font-weight: bold;">Petty Cash Head:</td>
							<td class="col-sm-9">
								<input type="text" class="form-control" style="border: 0; border-bottom: 2px ridge;" id="pettyCashHead" name="pettyCashHead" placeholder="Petty Cash Head" required>
								<h5 class="text-danger headerror hide"><strong>Invalid Head</strong></h5>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="col-md-12" align="center">
				<button type="button" class="btn btn-success" style="border-radius: 6px;" id="saveButton">
					<i class="fa fa-fw fa-save"></i>&nbsp;Save
				</button>
			</div>
		</form>
		<!-- --------------------- -->		
	</div>	
	</div>	
</div>

<script>

	$( document ).ready( function() {
		$('#saveButton').click( function() {
			var haserror = false;
			
			if( $('#pettyCashCode').val() == null || $.trim( $('#pettyCashCode').val() ) == '' ) {
				
				$('.codeerror').removeClass('hide').html('<strong>Invalid Code</strong>');
				haserror = true;
			} else {
				
				$('.codeerror').addClass('hide');
			}
			
			if( $('#pettyCashHead').val() == null || $.trim( $('#pettyCashHead').val() ) == '' ) {
				
				$('.headerror').removeClass('hide');
				haserror = true;
			} else {
				
				$('.headerror').addClass('hide');
			}
			
			if( haserror == true ) {
				return;
			} else {
				
				var id = $("#pettyCashCode").val();
				var baseURL = $('#contextPath').val();
				
				$.ajax({
					url : baseURL + "/pettycash/checkPettyCashCode.do",
					data : {"pettyCashCode":id},
					success : function(data) {
						if( data == "success" ) { 
							if( $('.codeerror').hasClass('hide') == false ) {
								$('.codeerror').addClass('hide');
							}
							
							$('form').submit();
						}
						else {
							$('.codeerror').removeClass('hide').html("<strong>Petty Code is already used. Try another</strong>");
						}
					},
					error : function(data) {
						alert("Server Error");
					},
					type : 'POST'
				}); //ajax
			}
			
		});
	});

</script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>