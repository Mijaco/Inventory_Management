<%@include file="../common/budgetHeader.jsp"%>
<!-- -------------------------------------------------------------------------------------- -->
<!-- @author: Ihteshamul Alam -->
<style>
	textarea {
		resize: none;
	}
</style>
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row" style="background-color: white; padding: 10px; padding-left: 20px">
		
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/bgt/costCenterList.do" style="text-decoration: none;" class="btn btn-primary btn-sm"> 
				<span class="glyphicon glyphicon-th-list" aria-hidden="true">
				</span>&nbsp;Cost Centre List
			</a>
			
			<h1  class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
			Cost Centre Form</h1>
			
		</div>		
		
	</div>
	
	<div class="container">
		<div class="col-xs-12" style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
			<form action="${pageContext.request.contextPath}/bgt/costCenterSave.do" method="post" class="form-horizontal">
				
				<input type="hidden" id="contextPath" value="${pageContext.request.contextPath}">
				
				<div class="form-group">
					<label for="" class="control-label col-xs-3" style="font-weight: bold;">Name:</label>
					<div class="col-xs-6">
						<input type="text" class="form-control" id="costCentreName" name="costCentreName" style="width: 100%;" required>
					</div>
				</div>
				
				<div class="form-group">
					<label for="" class="control-label col-xs-3" style="font-weight: bold;">Cost Centre Code:</label>
					<div class="col-xs-6">
						<input type="text" class="form-control" onblur="checkCostCentreCode()" id="costCentreCode" name="costCentreCode" style="width: 100%;" required>
						<h5 class="errormsg hide"><strong class="text-danger">Cost Centre Code is already used.</strong></h5>
					</div>
				</div>
				
				<div class="form-group">
					<label for="" class="control-label col-xs-3" style="font-weight: bold;">Department Code:</label>
					<div class="col-xs-6">
						<input type="text" class="form-control" id="deptCode" name="deptCode" style="width: 100%;">
					</div>
				</div>
				
				<div class="form-group">
					<label for="" class="control-label col-xs-3" style="font-weight: bold;">Remarks:</label>
					<div class="col-xs-6">
						<textarea class="form-control" id="remarks" name="remarks" style="width: 100%"></textarea>
					</div>
				</div>
				
				<div class="form-group" align="center">
					<button type="submit" class="btn btn-lg btn-success" id="saveButton"
						style="border-radius: 6px;" disabled>
						<i class="fa fa-fw fa-save"></i>&nbsp;Save
					</button>

					<button type="reset" class="btn btn-lg btn-danger"
						style="border-radius: 6px;">
						<i class="fa fa-fw fa-refresh"></i>&nbsp;Reset
					</button>
				</div>
			</form>
		</div>
	</div>
</div>

<script>
	
	function checkCostCentreCode() {
		var costCentreCode = $('#costCentreCode').val();
		var contextPath = $('#contextPath').val();
		var saveButton = $('#saveButton');
		
		$.ajax({
			url : contextPath + '/bgt/checkCostCentreCode.do',
			data : {"costCentreCode":costCentreCode},
			success : function(data) {
				//var result = JSON.parse(data);
				//alert(data);
				if (data == 'success') {
					if( $('.errormsg').hasClass('hide') == false ) {
						$('.errormsg').addClass('hide');
						saveButton.prop("disabled", false);
					}
					saveButton.prop("disabled", false);
				} else if( data == 'blank' ) {
					$('.errormsg').removeClass('hide').find('strong').text('This field is required');
					saveButton.prop("disabled", true);
				} else {
					$('.errormsg').removeClass('hide').find('strong').text('Cost Centre Code is used.');
					saveButton.prop("disabled", true);
				}
			},
			error : function(data) {
				alert(data);
			},
			type : 'POST'
		});
	}
	
	$( document ).ready( function() {
		$('button').click( function() {
			
		});
	});
</script>

<!-- -------------------------------------------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>