<%@include file="../common/cnHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<!-- <div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/job/jobList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Store
				Requisition List
			</a>
		</div> -->

		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Construction Nature Form</h1>
	</div>
	<div class="row" style="background-color: white; padding: 10px; margin: 10px;">
		<form method="POST" action="${pageContext.request.contextPath}/job/saveConstructionNature.do">
			<div class="form-group">
				<label for="name" class="col-sm-4 control-label">Construction
					Nature:&nbsp;<strong class='red'>*</strong></label>
				<div class="col-sm-7">
					<input type="text" class="form-control" id="name"
						placeholder="Construction Nature" onblur="checkConstructionNature()"
						style="border: 0; border-bottom: 2px ridge;" name="name" required/>
				</div>
				<div class="col-sm-1">
					<i id="workOrderDecision" style="font-size: 2em;" class=""></i>
				</div>
			</div>
			<div class="form-group">
				<label for="name" class="col-sm-4 control-label">Remarks:</label>
				<div class="col-sm-7">
					<input type="text" class="form-control" id="remarks"
						placeholder="Remarks"
						style="border: 0; border-bottom: 2px ridge;" name="remarks" />
				</div>
			</div>
			
			<input type="hidden" name="contextPath" id="contextPath" value="${pageContext.request.contextPath}">

			<div class="col-md-12" style="padding-top: 15px;">
				<div class="col-xs-12 col-sm-6">
					<button type="submit"
						style="margin-right: 10px; border-radius: 6px;" disabled
						class="width-20 pull-right btn btn-lg btn-success" id="saveButton">
						<i class="ace-icon fa fa-save"></i> <span class="bigger-50">Save</span>
					</button>
				</div>

				<div class="col-xs-12 col-sm-6">
					<button type="reset"
						class="width-20  pull-left btn btn-lg btn-danger"
						style="margin-left: 10px; border-radius: 6px;">
						<i class="ace-icon fa fa-refresh"></i> <span class="bigger-50">Reset</span>
					</button>
				</div>
			</div>
		</form>
	</div>
</div>

<script>
	function checkConstructionNature() {
		var contextPath = $('#contextPath').val();
		var saveButton = $('#saveButton');
		var name = $('#name').val();
		var workOrderDecision = $("#workOrderDecision");
		
		$.ajax({
			url : contextPath + '/job/checkConstructionNature.do',
			data : {"name":name},
			success : function(data) {
				//var result = JSON.parse(data);
				//alert(data);
				if (data == 'success') {
					saveButton.prop("disabled", false);
					workOrderDecision.removeClass('hide');
					workOrderDecision.removeClass("glyphicon-ok-sign");
					workOrderDecision.removeClass("red");
					workOrderDecision
							.addClass("glyphicon glyphicon-ok-sign green");

				} else {
					workOrderDecision.removeClass("glyphicon-ok-sign");
					workOrderDecision.removeClass("green");
					workOrderDecision.addClass("glyphicon glyphicon-remove-sign red");
					saveButton.prop("disabled", true);
					workOrderDecision.removeClass('hide');
				}
			},
			error : function(data) {
				alert(data);
			},
			type : 'POST'
		});
	}
</script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>