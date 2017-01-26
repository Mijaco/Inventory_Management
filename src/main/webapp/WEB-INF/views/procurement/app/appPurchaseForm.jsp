<%@include file="../../common/procurementHeader.jsp"%>
<!-- ----------------------------------------- -->
<!-- Author: Ihteshamul Alam, IBCS -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/assets/css/bootstrap_multiselect/bootstrap-multiselect.css" />

<style>
textarea {
	resize: none;
}
</style>

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="col-md-12">
			<h1 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
				${procurementPackageMst.packageName}
			</h1>
		</div>
	</div>

	<div class="col-xs-12"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<form
			action="${pageContext.request.contextPath}/app/purchase/saveProcurementForm.do"
			method="POST" enctype="multipart/form-data">
			<input type="hidden" id="contextPath"
				value="${pageContext.request.contextPath}">
			<div class="table-responsive">
				<table class="table table-bordered">
					<tbody>
						<tr>
							<td class="col-xs-2 success text-right">Requisition Ref.:</td>
							<td class="col-xs-2"><input type="text" required="required"
								id="requisitionRef" name="requisitionRef" class="form-control"/>
								<strong class="red requisitionRef hide">This field is required</strong>
							</td>
							<td class="col-xs-2 success text-right">Project Name:</td>
							<td class="col-xs-2"><!-- <input type="text" required="required"
								id="projectName" name="projectName" class="form-control"> -->
								<select class="form-control" name="projectName" id="descoKhathList" required="required">
										<option value="">Select Project</option>
										<c:forEach items="${descoKhathList}" var="descoKhath">
											<option value="${descoKhath.khathName}">${descoKhath.khathName}</option>
										</c:forEach>
									</select>
									<strong class="red descoKhathList hide">This field is required</strong>
									<input type="hidden" value="${sessionId}"
								id="sessionId" name="id" class="form-control">
							</td>

							<td class="col-xs-2 success text-right">Annexure No:</td>
							<td class="col-xs-2"><input type="text" class="form-control"
								readonly="readonly" id="annexureNo"
								value="${procurementPackageMst.annexureNo}" name="annexureNo" /></td>
						</tr>
						<tr>
							<td class="col-xs-2 success text-right">Draft Tender Copy:</td>
							<td class="col-xs-2"><input type="file" readonly="readonly"
								id="draftTenderDoc" name="draftTenderDoc" required
								class="form-control" />
								<strong class="red draftTenderDoc hide">This field is required</strong>
								</td>
							<td class="col-xs-2 success text-right">Approved Requisition
								Copy:</td>
							<td class="col-xs-2"><input type="file" readonly="readonly"
								id="requisitionAppDoc" required name="requisitionAppDoc"
								class="form-control" />
								<strong class="red requisitionAppDoc hide">This field is required</strong>
								</td>
							<td class="col-xs-2 success text-right">Comments:</td>
							<td class="col-xs-2"><textarea id="comments"
									required="required" name="comments" class="form-control"></textarea>
								<strong class="red comments hide">This field is required</strong>
							</td>
						</tr>
					</tbody>
				</table>
			</div>

			<div class="col-md-12 col-sm-12">
				<!-- Item Details :: start -->
				<c:if test="${empty procPackDtlList}">
					<h4 align="center" class="green">
						<strong>Sorry! No data found in database.</strong>
					</h4>
				</c:if>
				<c:if test="${!empty procPackDtlList}">
					<div class="table-responsive">
						<table class="table table-bordered">
							<thead>
								<tr style="background: #579EC8; color: #fff;">
									<th class="col-xs-1">Item Code</th>
									<th class="col-xs-3">Item Name</th>
									<th class="col-xs-1">Unit</th>
									<th class="col-xs-1">Quantity</th>
									<th class="col-xs-1">Unit Cost</th>
									<th class="col-xs-1">Total Cost</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${procPackDtlList}" var="procPackDtl">
									<tr>
										<td>${procPackDtl.itemCode}</td>
										<td>${procPackDtl.itemName}</td>
										<td>${procPackDtl.unit}</td>
										<td>${procPackDtl.qunatity}</td>
										<td>${procPackDtl.unitCost}</td>
										<td>${procPackDtl.totalCost}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</c:if>
				<!-- Item Details :: end -->
			</div>

			<div class="col-sm-12 center" style="margin: 5px 0 15px 0;">
				<button type="button" class="btn btn-success btn-md" id='saveButton'
					style="border-radius: 6px;" onclick="fileRequiredCheck()">
					<i class="fa fa-fw fa-save"></i>&nbsp;Submit
				</button>
			</div>
		</form>
	</div>
</div>



<script>
	$(document).ready(function() {
		$('#saveButton').click( function() {
			var haserror = false;
			
			if( $('#draftTenderDoc').val() == null || $.trim( $('#draftTenderDoc').val() ) == '' ) {
				$('.draftTenderDoc').removeClass('hide');
				haserror = true;
			} else {
				$('.draftTenderDoc').addClass('hide');
			}
			
			if( $('#requisitionAppDoc').val() == null || $.trim( $('#requisitionAppDoc').val() ) == '' ) {
				$('.requisitionAppDoc').removeClass('hide');
				haserror = true;
			} else {
				$('.requisitionAppDoc').addClass('hide');
			}			
			
			if( $('#requisitionRef').val() == null || $.trim( $('#requisitionRef').val() ) == '' ) {
				$('.requisitionRef').removeClass('hide');
				haserror = true;
			} else {
				$('.requisitionRef').addClass('hide');
			}
			
			if( $('#descoKhathList').val() == null || $.trim( $('#descoKhathList').val() ) == '' ) {
				$('.descoKhathList').removeClass('hide');
				haserror = true;
			} else {
				$('.descoKhathList').addClass('hide');
			}
			
			if( $('#comments').val() == null || $.trim( $('#comments').val() ) == '' ) {
				$('.comments').removeClass('hide');
				haserror = true;
			} else {
				$('.comments').addClass('hide');
			}
			
			if( haserror == true ) {
				return;
			} else {
				$('form').submit();
			}
		});
	});
</script>

<%-- <script
	src="${pageContext.request.contextPath}/resources/assets/js/procurement/procurementPackage.js"></script> --%>

<!-- ----------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>