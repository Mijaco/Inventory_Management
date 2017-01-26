<%@include file="../../common/csHeader.jsp"%>

<style type="text/css">
.btn-add {
	margin-bottom: 20px;
}

.ui-widget-overlay {
	opacity: .6 !important;
}

.ui-dialog-title {
	color: white !important;
}

.ui-dialog {
	max-width: 280px !important;
}

.ui-widget-header {
	background: blue !important;
}

.ui-button.ui-widget.ui-state-default.ui-corner-all.ui-button-icon-only.ui-dialog-titlebar-close {
	color: white !important;
	opacity: .8 !important;
	background: transparent !important;
}

.loader {
	position: fixed;
	left: 47% !important;
	top: 48% !important;
	z-index: 9999;
}

.ui-dialog-content.ui-widget-content {
	background: #dadada !important;
}

</style>

<div id="loading" title="Generating Inventory">
    <i class="loader fa fa-refresh fa-spin fa-3x fa-fw"></i> <br> <br> <span
		class="text-success" style="margin-left: 85px !important; font-weight: bold; background: #dadada;">Generating...</span>
</div>

<!-- ---------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">

		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Physical
			Store Inventory</h1>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<!-- ------ Form Start ---------- -->
		<form
			action="${pageContext.request.contextPath}/inventory/physicalStoreInventory.do"
			method="POST" id="inventoryForm">
			<input type="hidden" name="contextPath" id="contextPath"
				value="${pageContext.request.contextPath}">
			<div class="oe_title">

				<div class="col-sm-6">
					<div class="form-group">
						<label for="inventoryDate" style="font-weight: bold"
							class="col-sm-4 col-md-4 control-label align-right">Inventory
							Date :</label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control datepicker-15"
								name="dateText" id="inventoryDate"
								style="border: 0; border-bottom: 2px ridge;" readonly="readonly"
								required="required">
								<h5 class="text-danger invDate hide"><strong>This field is required</strong></h5>
						</div>
					</div>
				</div>


				<div class="col-md-6" style="">
					<div class="col-xs-12 col-sm-12">
						<button type="button" onclick="submitForm()" id="saveButton"
							style="margin-right: 10px; border-radius: 6px;"
							class="width-20 pull-right btn btn-md btn-success right">
							<i class="ace-icon fa fa-save"></i> <span class="bigger-50">Generate</span>
						</button>
					</div>
				</div>

			</div>

		</form>
		<!-- --------------------- -->
	</div>
</div>




<script>
	function submitForm() {
		var inventoryDate = $("#inventoryDate").val();
		
		if (inventoryDate == "" || inventoryDate == null) {
			$('.invDate').removeClass('hide');
			return;
		} else {
			$('.invDate').addClass('hide');
			$("#loading").dialog('open');
			$('#saveButton').prop('disabled', true);
			$("#inventoryForm").submit();
		}
	}
	
	$( document ).ready( function() {
		$("#loading").dialog({
		    hide: 'slide',
			show: 'slide',
			autoOpen: false
		});
	});
	
</script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%-- <script
		src="${pageContext.request.contextPath}/resources/assets/js/bootstrap.min.js"></script> --%>
<%@include file="../../common/ibcsFooter.jsp"%>