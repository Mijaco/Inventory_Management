<%@include file="../../common/committeeHeader.jsp"%>
<!-- ----------------------------------------- -->

<style>
textarea {
	resize: none;
}

.multiselect-container.dropdown-menu {
	z-index: 9999999;
}

.ui-widget-overlay {
	opacity: .6 !important;
}
</style>
<link
	href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
	rel="stylesheet">

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit col-md-2" style="display: block;">
			<%-- <a href="${pageContext.request.contextPath}/mps/dn/demandNoteList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Annexure List
			</a> --%>
		</div>
		<div class="col-md-8">
			<h1 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">
				Contract Management - Show</h1>
		</div>
	</div>

	<div class="col-xs-12"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">

		<input type="hidden" id="contextPath"
			value="${pageContext.request.contextPath}">

		<!-- Master Info :: start -->
		<div class="table-responsive">
			<table class="table table-bordered">
				<tbody>
					<tr>
						<td class="col-xs-2 success">Title</td>
						<td class="col-xs-4 info">${cms.appPurchaseMst.procurementPackageMst.packageName}</td>
						<td class="col-xs-2 success">Quantity</td>
						<td class="col-xs-4 info">${cms.id}</td>
					</tr>

					<tr>
						<td class="col-xs-2 success">Contract No</td>
						<td class="col-xs-4 info">${cms.contractNo}</td>

						<td class="col-xs-2 success">Contract Date</td>
						<td class="col-xs-4 info">${cms.contractDate}</td>


					</tr>
					<tr>
						<td class="col-xs-2 success">Supplier Name</td>
						<td class="col-xs-4 info">${cms.contractorName}</td>
						<td class="col-xs-2 success">Validity (Month)</td>
						<td class="col-xs-4 info">${cms.contractValidityMonth}</td>


					</tr>

					<tr>
						<td class="col-xs-2 success">PG Amount</td>
						<td class="col-xs-4 info">${cms.pgAmount}</td>
						<td class="col-xs-2 success">Contract Document</td>
						<td class="col-xs-4 info"><c:if
								test="${!empty cms.contractDoc}">
								<a target="_blank"
									href="${pageContext.request.contextPath}/app/purchase/download.do?downloadDocFile=${cms.contractDoc}">
									<span class="fa fa-file-pdf-o red center" aria-hidden="true"
									style="font-size: 1.5em;"></span>
								</a>
							</c:if></td>

					</tr>
					<c:if test="${!empty cms.drwApprovalDate}">
						<tr>
							<td class="col-xs-2 success">Drawing Approval Date</td>
							<td class="col-xs-4 info">${cms.drwApprovalDate}</td>
							<td class="col-xs-2 success">Drawing Approval Copy</td>
							<td class="col-xs-4 info"><c:if
									test="${!empty cms.drwApprovalCopy}">
									<form target="_blank"
										action="${pageContext.request.contextPath}/cms/download.do"
										method="POST">
										<input type="hidden" value="${cms.drwApprovalCopy}"
											name="downloadPath" />
										<button type="submit" class="fa fa-file-pdf-o red center"
											aria-hidden="true" style="font-size: 1.5em;"></button>
									</form>
								</c:if></td>
						</tr>
						<tr>
							<td class="col-xs-2 success">Remarks</td>
							<td class="col-xs-10" colspan="3">${cms.drwApprovalRemarks}</td>
						</tr>
					</c:if>
				</tbody>
			</table>
		</div>
		<!-- Master Info :: end -->

		<div class="center">
			<c:if test="${stateCode=='300'}">
				<form
					action="${pageContext.request.contextPath}/cms/updateContractManagementByStateCode.do"
					method="POST" enctype="multipart/form-data">
					<table class="table table-bordered">
						<tbody>
							<tr>
								<td colspan="4" class=""
									style="font-weight: bold; font-size: 14px; color: white; background: #579EC8;">${cmsHistory.approvalHeader}</td>
							<tr>
								<td class="col-xs-2 success">PSI Date</td>
								<td class="col-xs-4"><input type="text" name="psiDate"
									id="psiDate" class="datepicker-15"
									style="width: 100%; border: 0; border-bottom: 2px ridge;"
									required="required"></td>
								<td class="col-xs-2 success">PSI Report</td>
								<td class="col-xs-4"><input type="file" name="refDoc"
									id="refDoc" accept="application/pdf"></td>
							</tr>

							<tr>
								<td class="col-xs-2 success">PSI Remarks</td>
								<td class="col-xs-10" colspan="3"><textarea
										name="psiRemarks" id="psiRemarks" style="width: 100%"></textarea>
								</td>

							</tr>
							<tr>
								<td class="col-xs-2 success text-right">Send For</td>
								<td class="col-xs-4"><select class="form-control"
									style="border: 0; border-bottom: 2px ridge;" name="stateCode"
									id="sendFor" required="required">
										<option value="">Select Process</option>
										<c:if test="${!empty approvalHierarchyList}">
											<c:forEach items="${approvalHierarchyList}"
												var="approvalHierarchy">
												<c:if test="${approvalHierarchy.stateCode!='300'}">
													<option value="${approvalHierarchy.stateCode}">${approvalHierarchy.approvalHeader}</option>
												</c:if>
											</c:forEach>
										</c:if>
								</select></td>
								<td class="col-xs-2 success">Send To</td>
								<td class="col-xs-4"><select class="form-control"
									style="border: 0; border-bottom: 2px ridge;" name="userid"
									id="sendTo" required="required">
										<option value="">Select Officer</option>
										<c:if test="${!empty committeeList}">
											<c:forEach items="${committeeList}" var="committee">
												<option value="${committee.authUser.userid}">${committee.authUser.name}
													(${committee.authUser.designation})</option>
											</c:forEach>
										</c:if>
								</select></td>

							</tr>
						</tbody>
					</table>

					<div class="center">

						<button class="btn btn-sm btn-primary"
							style="border-radius: 6px; margin-bottom: 8px;">
							<i class="fa fa-arrow-right"> </i> Send to Next
						</button>
						<input type="hidden" name="id" value="${cms.id}">
					</div>

				</form>
			</c:if>
		</div>

	</div>
</div>



<!-- ----------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>