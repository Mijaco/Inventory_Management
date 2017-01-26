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
			<h2 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
					${appPurchaseMst.procurementPackageMst.packageName}
			</h2>
		</div>
	</div>

	<div class="col-xs-12"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<form
			action="${pageContext.request.contextPath}/app/purchase/saveProcurementForm.do"
			method="POST">
			<input type="hidden" id="contextPath"
				value="${pageContext.request.contextPath}">
			<div class="table-responsive">
				<table class="table table-bordered">
					<tbody>
						<tr>
							<td class="col-xs-2 success text-right">Requisition Ref.:</td>
							<td class="col-xs-2">
								${appPurchaseMst.requisitionRef}
							</td>
							<td class="col-xs-2 success text-right">Number Of Submission:</td>
							<td class="col-xs-2">
							<input type="number" required="required" id="numberOfSubmission"
								name="numberOfSubmission" class="form-control">
							</td>

							<td class="col-xs-2 success text-right">Annexure No:</td>
							<td class="col-xs-2">${appPurchaseMst.procurementPackageMst.annexureNo}</td>
						</tr>
						<tr>
							<td class="col-xs-2 success text-right">Tender/RFQ Publication Date:</td>
							<td class="col-xs-2"><input type="text"
								readonly="readonly" required="required" id="tendRfqPubDate"
								name="tendRfqPubDate" class="form-control datepicker-14"/></td>

							<td class="col-xs-2 success text-right">Tender/RFQ Submission Date:</td>
							<td class="col-xs-2">
							<input type="text" class="form-control datepicker-14"
								readonly="readonly" required="required" id="tendRfqSubDate"
								name="tendRfqSubDate"/>
							</td>
							<td class="col-xs-2 success text-right">Tender/RFQ Evaluation Date:</td>
							<td class="col-xs-2">
								<input type="text" class="form-control datepicker-14"
								readonly="readonly" required="required" id="tendRfqEvalDate"
								name="tendRfqEvalDate"/>
							</td>
						</tr>

						<tr>
							<td class="col-xs-2 success text-right">NOA Date:</td>
							<td class="col-xs-2">
								<input type="text" class="form-control datepicker-14"
								readonly="readonly" required="required" id="noaDate"
								name="noaDate" />
							</td>
							<td class="col-xs-2 success text-right">Work Order Date:</td>
							<td class="col-xs-2">
								<input type="text" class="form-control datepicker-14"
								readonly="readonly" required="required" id="woDate"
								name="woDate" />
							</td>
							<td class="col-xs-2 success text-right">Contract Amount:</td>
							<td class="col-xs-2">
								<input type="number" step="0.01"
								readonly="readonly" required="required" id="contractAmount"
								name="contractAmount" class="form-control"/>
							</td>
						</tr>

						<tr>
							<td class="col-xs-2 success text-right">Tender/RFQ Copy:</td>
							<td class="col-xs-2"><input type="file"
								readonly="readonly" required="required" id="tendRfqDoc"
								name="tendRfqDoc" class="form-control"/></td>
							<td class="col-xs-2 success text-right">Evaluation Copy:</td>
							<td class="col-xs-2">
								<input type="file"
								readonly="readonly" required="required" id="evaluationDoc"
								name="evaluationDoc" class="form-control"/>
							</td>
							<td class="col-xs-2 success text-right">NOA Copy:</td>
							<td class="col-xs-2">
								<input type="file"
								readonly="readonly" required="required" id="noaDoc"
								name="noaDoc" class="form-control"/>
							</td>
						</tr>
						
						<tr>
							<td class="col-xs-2 success text-right">Work Order Copy:</td>
							<td class="col-xs-2"><input type="file"
								readonly="readonly" required="required" id="woDoc"
								name="woDoc" class="form-control"/></td>
							<td class="col-xs-2 success text-right"> Performance Guarantee(PG):</td>
							<td class="col-xs-2">
								<input type="file"
								readonly="readonly" required="required" id="pgDoc"
								name="pgDoc" class="form-control"/>
							</td>
							<td class="col-xs-2 success text-right">Comments:</td>
							<td class="col-xs-2">
								<textarea id="comments"
									required="required" name="comments" class="form-control"></textarea>
							</td>
						</tr>
					</tbody>
				</table>
			</div>

			<div class="col-md-12 col-sm-12">
				<!-- Item Details :: start -->
				<c:if test="${empty appPurchaseDtlList}">
					<h4 align="center" class="green">
						<strong>Sorry! No data found in database.</strong>
					</h4>
				</c:if>
				<c:if test="${!empty appPurchaseDtlList}">
					<div class="table-responsive">
						<table class="table table-bordered">
							<thead>
								<tr style="background: #579EC8; color: #fff;">
									<th class="col-xs-1">Item Code</th>
									<th class="col-xs-3">Item Name</th>
									<th class="col-xs-1">Unit</th>
									<th class="col-xs-1">Quantity</th>
									<th class="col-xs-1">Unit Cost</th>
									<th class="col-xs-1">Purchase Cost</th>
									<th class="col-xs-1">Total Cost</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${appPurchaseDtlList}" var="appPurchaseDtl">
									<tr>
										<td>${appPurchaseDtl.procurementPackageDtl.itemCode}</td>
										<td>${appPurchaseDtl.procurementPackageDtl.itemName}</td>
										<td>${appPurchaseDtl.procurementPackageDtl.unit}</td>
										<td>${appPurchaseDtl.procurementPackageDtl.qunatity}</td>
										<td>${appPurchaseDtl.purchaseCost}</td>
										<td>${appPurchaseDtl.procurementPackageDtl.unitCost}</td>
										<td>${appPurchaseDtl.procurementPackageDtl.totalCost}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</c:if>
				<!-- Item Details :: end -->
			</div>

			<div class="col-sm-12 center" style="margin: 5px 0 15px 0;">
				<button type="submit" class="btn btn-success btn-md" id='updateButton'
					style="border-radius: 6px;">
					<i class="fa fa-fw fa-update"></i>&nbsp;Update
				</button>
			</div>
		</form>
	</div>
</div>

<!-- ----------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>