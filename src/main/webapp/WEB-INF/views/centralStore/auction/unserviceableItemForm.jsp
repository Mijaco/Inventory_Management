<%@include file="../../common/csHeader.jsp"%>

<link
	href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
	rel="stylesheet">


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

.ui-button.ui-widget.ui-state-default.ui-corner-all.ui-button-icon-only.ui-dialog-titlebar-close
	{
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

<div id="loading" title="Processing....">
	<i class="loader fa fa-refresh fa-spin fa-3x fa-fw"></i> <br> <br>
	<span class="text-success"
		style="margin-left: 85px !important; font-weight: bold; background: #dadada;">Generating...</span>
</div>

<!-- ---------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">

		<h2 class="center blue ubuntu-font"
			style="margin-top: 0;">Print Obsolete/Condemn Materials of Stores</h2>
	</div>

	<div class="row"
		style="background-color: white; padding-left: 10px; margin: 10px; padding-bottom: 10px; padding-top: 10px;">
		<!-- ------ Form Start ---------- -->
		<form
			action="${pageContext.request.contextPath}/auction/cs/getUnserviceableItems.do"
			method="POST" id="inventoryForm">
			<input type="hidden" name="contextPath" id="contextPath"
				value="${pageContext.request.contextPath}">
			<div class="oe_title">

				<div class="col-sm-5">
					<div class="form-group">
						<label for="auctionCategoryId" style="font-weight: bold"
							class="col-sm-4 col-md-4 control-label align-right">Auction
							Category:&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-8 col-md-8">
							<select id="auctionCategoryId" 
								required="required" name="auctionCategoryId"
								class="form-control">
								<c:if test="${!empty auctionCategoryList}">
									<c:forEach var="obj" items="${auctionCategoryList}">
										<option value="${obj.id}">${obj.name}</option>
									</c:forEach>
								</c:if>
							</select>
							<h5 class="text-danger auctionCatErr hide">
								<strong>This field is required</strong>
							</h5>
							
						</div>
					</div>
				</div>

				<div class="col-sm-5">
					<div class="form-group">
						<label for="inventoryDate" style="font-weight: bold"
							class="col-sm-4 col-md-4 control-label align-right">Count
							date upto:&nbsp;<strong class='red'>*</strong></label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control datepicker-15"
								name="dateText" id="inventoryDate"
								style="border: 0; border-bottom: 2px ridge;" readonly="readonly"
								required="required">
							<h5 class="text-danger invDate hide">
								<strong>This field is required</strong>
							</h5>
						</div>
					</div>
				</div>


				<div class="col-md-2" style="">
					<div class="col-xs-12">
						<button type="button" onclick="submitForm()" id="saveButton"
							style="margin-right: 10px; border-radius: 6px;"
							class="btn btn-sm btn-success">
							<i class="ace-icon fa fa-save"></i> <span class="">Generate</span>
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
				
		var auctionCategoryId = $("#auctionCategoryId").val();
		var inventoryDate = $("#inventoryDate").val();
		
		if (auctionCategoryId == "" || auctionCategoryId == null) {
			$('.auctionCatErr').removeClass('hide');
			return;
		} 

		if (inventoryDate == "" || inventoryDate == null) {
			$('.invDate').removeClass('hide');
			return;
		} else {
			$('#saveButton').prop('disabled', true);
			$('.invDate').addClass('hide');
			$("#loading").dialog('open');
			$('#saveButton').prop('disabled', true);
			$("#inventoryForm").submit();
		}
		
		
	}

	$(document).ready(function() {
		$("#loading").dialog({
			hide : 'slide',
			show : 'slide',
			autoOpen : false
		});
	});
</script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%-- <script
		src="${pageContext.request.contextPath}/resources/assets/js/bootstrap.min.js"></script> --%>
<%@include file="../../common/ibcsFooter.jsp"%>