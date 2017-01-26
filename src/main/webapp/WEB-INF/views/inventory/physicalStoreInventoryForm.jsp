<%@include file="../inventory/inventoryheader.jsp"%>
<style type="text/css">
.btn-add {
	margin-bottom: 20px;
}

.ui-widget-overlay {
	opacity: .6 !important;
}
</style>
<!-- ---------------------------------------------------------------------------------- -->
<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">

		<h1 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Physical Store Inventory</h1>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px;">
		<!-- ------ Form Start ---------- -->
		<form>
			<input type="hidden" name="contextPath" id="contextPath"
				value="${pageContext.request.contextPath}">
			<div class="oe_title">

				<div class="col-sm-6">
					<div class="form-group">
						<label for="inventoryDate" style="font-weight: bold"
							class="col-sm-4 col-md-4 control-label align-right">Inventory
							Date :</label>
						<div class="col-sm-8 col-md-8">
							<select name="inventoryDate" class="form-control"
								id="inventoryDate" style="border: 0; border-bottom: 2px ridge;">
								<option value="" selected="selected">Select a Session</option>
								<c:forEach items="${inventoryDateList}" var="inventoryDate">
									<option value="${inventoryDate}">${inventoryDate}</option>
								</c:forEach>
							</select>
						</div>
					</div>
				</div>


				<div class="col-md-6" style="">
					<div class="col-xs-12 col-sm-12">
						<button type="button" onclick="submitForm()"
							style="margin-right: 10px; border-radius: 6px;"
							class="width-20 pull-right btn btn-lg btn-success right"
							id="saveButton" name="saveButton">
							<i class="ace-icon fa fa-save"></i> <span class="bigger-50">Next</span>
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
		// inventoryDate=new Date(inventoryDate);
		var cData = {
			inventoryDate : inventoryDate
		}
		var contextPath = $("#contextPath").val();
		var path = contextPath + "/inventory/getPhysicalStoreInventoryForm.do";

		postSubmit(path, cData, 'POST');
	}
</script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%-- <script
		src="${pageContext.request.contextPath}/resources/assets/js/bootstrap.min.js"></script> --%>
<%@include file="../common/ibcsFooter.jsp"%>