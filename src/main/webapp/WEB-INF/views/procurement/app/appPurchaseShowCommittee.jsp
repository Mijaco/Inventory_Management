<%@include file="../../common/committeeHeader.jsp"%>
<!-- ----------------------------------------- -->
<link
	href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
	rel="stylesheet">
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
				${appPurchaseMst.procurementPackageMst.packageName}</h2>
		</div>
	</div>

	<div class="col-xs-12"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<form
			action="${pageContext.request.contextPath}/app/purchase/updateProcurementForm.do"
			method="POST" enctype="multipart/form-data">
			<div class="table-responsive">
				<input type="hidden" id="contextPath"
					value="${pageContext.request.contextPath}">
				<table class="table table-bordered">
					<tr>
						<td class="col-xs-2 success text-right">Annexure No:</td>
						<td class="col-xs-2">
							${appPurchaseMst.procurementPackageMst.annexureNo}</td>
						<td class="col-xs-2 success text-right">Package Name:</td>
						<td class="col-xs-2">
							${appPurchaseMst.procurementPackageMst.packageName}</td>
						<td class="col-xs-2 success text-right">Financial Year:</td>
						<td class="col-xs-2">
							${appPurchaseMst.descoSession.sessionName}</td>
					</tr>

					<c:if test="${!empty appPurchaseMst.requisitionRef}">
						<tr>
							<td class="col-xs-2 success text-right">Requisition
								Reference:</td>
							<td class="col-xs-4" colspan="3">${appPurchaseMst.requisitionRef}</td>
							<td class="col-xs-2 success text-right">Project Name:</td>
							<td class="col-xs-4">${appPurchaseMst.projectName}</td>
						</tr>
						<tr>
							<td class="col-xs-2 success text-right">Tender No:</td>
							<td class="col-xs-2">${appPurchaseMst.tenderNo}</td>
							<td class="col-xs-2 success text-right">Draft Tender Copy:</td>
							<td class="col-xs-2"><c:if
									test="${!empty appPurchaseMst.draftTenderDoc}">
									<a target="_blank"
										href="${pageContext.request.contextPath}/app/purchase/download.do?downloadDocFile=${appPurchaseMst.draftTenderDoc}">
										<span class="fa fa-file-pdf-o red center" aria-hidden="true"
										style="font-size: 1.5em;"></span>
									</a>
								</c:if></td>
							<td class="col-xs-2 success text-right">Technical
								Specification Copy:</td>
							<td class="col-xs-2"><c:if
									test="${!empty appPurchaseMst.specificationDoc}">
									<a target="_blank"
										href="${pageContext.request.contextPath}/app/purchase/download.do?downloadDocFile=${appPurchaseMst.specificationDoc}">
										<span class="fa fa-file-pdf-o red center" aria-hidden="true"
										style="font-size: 1.5em;"></span>
									</a>
								</c:if></td>
						</tr>
						<tr>
							<td class=" success text-right">Approved Requisition Copy:</td>
							<td class=""><c:if
									test="${!empty appPurchaseMst.requisitionAppDoc}">
									<a target="_blank"
										href="${pageContext.request.contextPath}/app/purchase/download.do?downloadDocFile=${appPurchaseMst.requisitionAppDoc}">
										<span class="fa fa-file-pdf-o red center" aria-hidden="true"
										style="font-size: 1.5em;"></span>
									</a>
								</c:if></td>
							<td class=" success text-right">Comments:</td>
							<td class="" colspan="3">${appPurchaseMst.remarks}</td>
						</tr>
					</c:if>
				</table>

				<%-- <table class="table table-bordered">
					<c:choose>
						<c:when test="${stateCode=='200'}">
							<tr>
								<td class="col-xs-2 success text-right">Purchase
									Requisition Note:</td>
								<td class="col-xs-4"><input type="file" readonly="readonly"
									id="purchaseRequsitionNoteDoc" name="purchaseRequsitionNoteDoc"
									required class="form-control" /> <!-- <input type="file" name="specificationDoc" class="hidden"/>
									<input type="file" name="purchaseRequsitionNoteDoc" class="hidden"/> -->
									<input type="file" name="requisitionAppDoc" class="hidden" />
									<input type="file" name="draftTenderDoc" class="hidden" /> <input
									type="file" name="reviewDoc" class="hidden" /> <input
									type="file" name="approvedDoc" class="hidden" /> <input
									type="file" name="workOrderDoc" class="hidden" /> <input
									type="file" name="tendRfqPubDoc" class="hidden" /> <input
									type="file" name="tendRfqDoc" class="hidden" /> <input
									type="file" name="evaluationDoc" class="hidden" /> <input
									type="file" name="pgDoc" class="hidden" /> <input type="file"
									name="noaDoc" class="hidden" /></td>
								<td class="col-xs-2 success text-right">Technical
									Specification Copy:</td>
								<td class="col-xs-4"><input type="file" readonly="readonly"
									id="specificationDoc" name="specificationDoc" required
									class="form-control" /></td>
							</tr>
						</c:when>
						<c:otherwise>
							<c:if test="${!empty appPurchaseMst.specificationDoc}">
								<tr>
									<td class="col-xs-2 success text-right">Purchase
										Requsition Note Copy:</td>
									<td class="col-xs-4"><c:if
											test="${!empty appPurchaseMst.purchaseRequsitionNoteDoc}">
											<a target="_blank"
												href="${pageContext.request.contextPath}/app/purchase/download.do?downloadDocFile=${appPurchaseMst.purchaseRequsitionNoteDoc}">
												<span class="fa fa-file-pdf-o red center" aria-hidden="true"
												style="font-size: 1.5em;"></span>
											</a>
										</c:if></td>
									<td class="col-xs-2 success text-right">Technical
										Specification Copy:</td>
									<td class="col-xs-4"><c:if
											test="${!empty appPurchaseMst.specificationDoc}">
											<a target="_blank"
												href="${pageContext.request.contextPath}/app/purchase/download.do?downloadDocFile=${appPurchaseMst.specificationDoc}">
												<span class="fa fa-file-pdf-o red center" aria-hidden="true"
												style="font-size: 1.5em;"></span>
											</a>
										</c:if></td>
								</tr>
							</c:if>
						</c:otherwise>
					</c:choose>
				</table>

				<table class="table table-bordered">
					<c:choose>
						<c:when test="${stateCode=='300'}">
							<tr>
								<td class="col-xs-2 success text-right">Justifications:</td>
								<td class="col-xs-10"><textarea rows="3" cols="172"
										name="justification">Approved.</textarea> <input type="file"
									name="purchaseRequsitionNoteDoc" class="hidden" /> <input
									type="file" name="specificationDoc" class="hidden" /> <input
									type="file" name="requisitionAppDoc" class="hidden" /> <input
									type="file" name="draftTenderDoc" class="hidden" /> <input
									type="file" name="reviewDoc" class="hidden" /> <input
									type="file" name="approvedDoc" class="hidden" /> <input
									type="file" name="workOrderDoc" class="hidden" /> <input
									type="file" name="tendRfqPubDoc" class="hidden" /> <input
									type="file" name="tendRfqDoc" class="hidden" /> <input
									type="file" name="evaluationDoc" class="hidden" /> <input
									type="file" name="pgDoc" class="hidden" /> <input type="file"
									name="noaDoc" class="hidden" /></td>
							</tr>
						</c:when>
						<c:otherwise>
							<c:if test="${!empty appPurchaseMst.justification}">
								<tr>
									<td class="col-xs-2 success text-right">Justifications:</td>
									<td class="col-xs-10">${appPurchaseMst.justification}</td>
								</tr>
							</c:if>
						</c:otherwise>
					</c:choose>
				</table>
				<table class="table table-bordered">
					<c:choose>

						<c:when test="${stateCode=='400'}">
							<tr>
								<td class="col-xs-2 success text-right">Requisition Ref.:</td>
								<td class="col-xs-4"><input type="text" required="required"
									id="requisitionRef" name="requisitionRef" class="form-control" />
								</td>
								<td class="col-xs-2 success text-right">Project Name:</td>
								<td class="col-xs-4"><select class="form-control"
									name="projectName" id="descoKhathList" required="required">
										<option value="">Select Project</option>
										<c:forEach items="${descoKhathList}" var="descoKhath">
											<option value="${descoKhath.khathName}">${descoKhath.khathName}</option>
										</c:forEach>
								</select></td>
							</tr>
							<tr>
								<td class="col-xs-2 success text-right">Tender No:</td>
								<td class="col-xs-4"><input type="text" id="tenderNo"
									name="tenderNo" required="required" class="form-control" /></td>
								<td class="col-xs-2 success text-right">Draft Tender Copy:</td>
								<td class="col-xs-4"><input type="file" readonly="readonly"
									id="draftTenderDoc" name="draftTenderDoc" required
									class="form-control" /></td>


							</tr>
							<tr>
								<td class="col-xs-2 success text-right">Approved
									Requisition Copy:</td>
								<td class="col-xs-4"><input type="file" readonly="readonly"
									id="requisitionAppDoc" required name="requisitionAppDoc"
									class="form-control" /></td>
								<td class="col-xs-2 success text-right">Comments:</td>
								<td class="col-xs-4"><textarea id="comments"
										required="required" name="comments" class="form-control"></textarea>

									<input type="file" name="specificationDoc" class="hidden" /> <!-- <input type="file" name="requisitionAppDoc" class="hidden"/>
									<input type="file" name="draftTenderDoc" class="hidden"/> --> <input
									type="file" name="purchaseRequsitionNoteDoc" class="hidden" />
									<input type="file" name="reviewDoc" class="hidden" /> <input
									type="file" name="approvedDoc" class="hidden" /> <input
									type="file" name="workOrderDoc" class="hidden" /> <input
									type="file" name="tendRfqPubDoc" class="hidden" /> <input
									type="file" name="tendRfqDoc" class="hidden" /> <input
									type="file" name="evaluationDoc" class="hidden" /> <input
									type="file" name="pgDoc" class="hidden" /> <input type="file"
									name="noaDoc" class="hidden" /></td>
							</tr>

						</c:when>
						<c:otherwise>
							<c:if test="${!empty appPurchaseMst.requisitionRef}">
								<tr>
									<td class="col-xs-2 success text-right">Requisition Ref:</td>
									<td class="col-xs-4">${appPurchaseMst.requisitionRef}</td>
									<td class="col-xs-2 success text-right">Project Name:</td>
									<td class="col-xs-4">${appPurchaseMst.projectName}</td>
								</tr>
								<tr>									
									<td class="col-xs-2 success text-right">Tender No:</td>
									<td class="col-xs-4">
										${appPurchaseMst.tenderNo}</td>
									<td class="col-xs-2 success text-right">Draft Tender Copy:</td>
									<td class="col-xs-4"><c:if
											test="${!empty appPurchaseMst.draftTenderDoc}">
											<a target="_blank"
												href="${pageContext.request.contextPath}/app/purchase/download.do?downloadDocFile=${appPurchaseMst.draftTenderDoc}">
												<span class="fa fa-file-pdf-o red center" aria-hidden="true"
												style="font-size: 1.5em;"></span>
											</a>
										</c:if>
									</td>									
								</tr>
								<tr>
									<td class="col-xs-2 success text-right">Approved
										Requisition Copy:</td>
									<td class="col-xs-4"><c:if
											test="${!empty appPurchaseMst.requisitionAppDoc}">
											<a target="_blank"
												href="${pageContext.request.contextPath}/app/purchase/download.do?downloadDocFile=${appPurchaseMst.requisitionAppDoc}">
												<span class="fa fa-file-pdf-o red center" aria-hidden="true"
												style="font-size: 1.5em;"></span>
											</a>
										</c:if></td>
									<td class="col-xs-2 success text-right">Comments:</td>
									<td class="col-xs-4">
										${appPurchaseMst.remarks}</td>
								</tr>
							</c:if>
						</c:otherwise>
					</c:choose>
				</table> --%>
				<c:if test="${stateCode=='100'}">
					<input type="file" name="reviewDoc" id="reviewDoc" />
					<input type="file" name="specificationDoc" class="hidden" />
					<input type="file" name="purchaseRequsitionNoteDoc" class="hidden" />
					<input type="file" name="requisitionAppDoc" class="hidden" />
					<input type="file" name="draftTenderDoc" class="hidden" />
					<input type="file" name="reviewDoc" class="hidden" />
					<input type="file" name="approvedDoc" class="hidden" />
					<input type="file" name="workOrderDoc" class="hidden" />
					<input type="file" name="tendRfqPubDoc" class="hidden" />
					<input type="file" name="tendRfqDoc" class="hidden" />
					<input type="file" name="evaluationDoc" class="hidden" />
					<input type="file" name="pgDoc" class="hidden" />
					<input type="file" name="noaDoc" class="hidden" />
				</c:if>

				<div>
					<table class="table table-bordered">
						<c:choose>

							<c:when test="${stateCode=='200'}">

								<tr>
									<td class="col-xs-2 success text-right">Review Date:</td>
									<td class="col-xs-2"><input type="text" name="reviewDate"
										class="form-control datepicker-15" id="reviewDate"
										required="required" /></td>

									<td class="col-xs-2 success text-right">Review Copy:</td>
									<td class="col-xs-6"><input type="file" name="reviewDoc"
										id="reviewDoc" /> <input type="file" name="specificationDoc"
										class="hidden" /> <input type="file"
										name="purchaseRequsitionNoteDoc" class="hidden" /> <input
										type="file" name="requisitionAppDoc" class="hidden" /> <input
										type="file" name="draftTenderDoc" class="hidden" /> <!-- <input type="file" name="reviewDoc" class="hidden"/> -->
										<input type="file" name="approvedDoc" class="hidden" /> <input
										type="file" name="workOrderDoc" class="hidden" /> <input
										type="file" name="tendRfqPubDoc" class="hidden" /> <input
										type="file" name="tendRfqDoc" class="hidden" /> <input
										type="file" name="evaluationDoc" class="hidden" /> <input
										type="file" name="pgDoc" class="hidden" /> <input type="file"
										name="noaDoc" class="hidden" /></td>

								</tr>
							</c:when>

							<c:otherwise>
								<c:if test="${!empty appPurchaseMst.reviewDate}">
									<tr>
										<td class="col-xs-2 success text-right">Review Date:</td>
										<td class="col-xs-2">${appPurchaseMst.reviewDate}</td>
										<td class="col-xs-2 success text-right">Review Copy:</td>
										<td class="col-xs-6"><c:if
												test="${!empty appPurchaseMst.reviewDoc}">
												<a target="_blank"
													href="${pageContext.request.contextPath}/app/purchase/download.do?downloadDocFile=${appPurchaseMst.reviewDoc}">
													<span class="fa fa-file-pdf-o red center"
													aria-hidden="true" style="font-size: 1.5em;"></span>
												</a>
											</c:if></td>
									</tr>
								</c:if>
							</c:otherwise>

						</c:choose>
					</table>
				</div>


				<div>
					<table class="table table-bordered">
						<c:choose>
							<c:when test="${stateCode=='300'}">
								<tr>
									<td class="col-xs-2 success text-right">Approved by:</td>
									<td class="col-xs-2"><input type="text" name="approvedBy"
										id="approvedBy" required="required" /></td>
									<td class="col-xs-2 success text-right">Approved Date:</td>
									<td class="col-xs-2"><input type="text"
										name="approvedDate" class="form-control datepicker-15"
										required="required" /></td>

									<td class="col-xs-2 success text-right">Approved Copy:</td>
									<td class="col-xs-2"><input type="file" name="approvedDoc"
										id="approvedDoc" /> <input type="file"
										name="specificationDoc" class="hidden" /> <input type="file"
										name="purchaseRequsitionNoteDoc" class="hidden" /> <input
										type="file" name="requisitionAppDoc" class="hidden" /> <input
										type="file" name="draftTenderDoc" class="hidden" /> <input
										type="file" name="reviewDoc" class="hidden" /> <!-- <input type="file" name="approvedDoc"  class="hidden"/> -->
										<input type="file" name="workOrderDoc" class="hidden" /> <input
										type="file" name="tendRfqPubDoc" class="hidden" /> <input
										type="file" name="tendRfqDoc" class="hidden" /> <input
										type="file" name="evaluationDoc" class="hidden" /> <input
										type="file" name="pgDoc" class="hidden" /> <input type="file"
										name="noaDoc" class="hidden" /></td>
								</tr>
							</c:when>

							<c:otherwise>
								<c:if test="${!empty appPurchaseMst.approvedBy}">
									<tr>
										<td class="col-xs-2 success text-right">Approved by:</td>
										<td class="col-xs-2">${appPurchaseMst.approvedBy}</td>
										<td class="col-xs-2 success text-right">Approved Date:</td>
										<td class="col-xs-2">${appPurchaseMst.approvedDate}</td>

										<td class="col-xs-2 success text-right">Approved Copy:</td>
										<td class="col-xs-2"><c:if
												test="${!empty appPurchaseMst.approvedDoc}">
												<a target="_blank"
													href="${pageContext.request.contextPath}/app/purchase/download.do?downloadDocFile=${appPurchaseMst.approvedDoc}">
													<span class="fa fa-file-pdf-o red center"
													aria-hidden="true" style="font-size: 1.5em;"></span>
												</a>
											</c:if></td>
									</tr>
								</c:if>
							</c:otherwise>

						</c:choose>
					</table>
				</div>


				<div>
					<table class="table table-bordered">
						<c:choose>
							<c:when test="${stateCode=='400'}">
								<tr>
									<td class="col-xs-2 success text-right">Tender/RFQ
										publication Date:</td>
									<td class="col-xs-2"><input type="text"
										name="tendRfqPubDate" id="tendRfqPubDate"
										class="form-control datepicker-15" required="required" /></td>

									<td class="col-xs-2 success text-right">Tender/RFQ
										Invitation doc copy:</td>
									<td class="col-xs-2"><input type="file" name="tendRfqDoc"
										id="tendRfqDoc" /></td>

									<td class="col-xs-2 success text-right">Tender/RFQ
										submission Date:</td>
									<td class="col-xs-2"><input type="text"
										name="tendRfqSubDate" id="tendRfqSubDate"
										class="form-control datepicker-15" required="required" /></td>
								</tr>
								<tr>
									<td class="col-xs-2 success text-right">Tender/RFQ
										Extension Date:</td>
									<td class="col-xs-2"><input type="text"
										name="tendRfqExtentionDate" id="tendRfqExtentionDate"
										class="form-control datepicker-15" required="required" /></td>
									<td class="col-xs-2 success text-right">Tender publication
										copy:</td>
									<td class="col-xs-2" colspan="3"><input type="file"
										name="tendRfqPubDoc" id="tendRfqPubDoc" /> <input type="file"
										name="specificationDoc" class="hidden" /> <input type="file"
										name="purchaseRequsitionNoteDoc" class="hidden" /> <input
										type="file" name="requisitionAppDoc" class="hidden" /> <input
										type="file" name="draftTenderDoc" class="hidden" /> <input
										type="file" name="reviewDoc" class="hidden" /> <input
										type="file" name="approvedDoc" class="hidden" /> <input
										type="file" name="workOrderDoc" class="hidden" /> <!-- <input type="file" name="tendRfqPubDoc" class="hidden"/>
									<input type="file" name="tendRfqDoc" class="hidden"/> --> <input
										type="file" name="evaluationDoc" class="hidden" /> <input
										type="file" name="pgDoc" class="hidden" /> <input type="file"
										name="noaDoc" class="hidden" /></td>
								</tr>
							</c:when>

							<c:otherwise>
								<c:if test="${!empty appPurchaseMst.tendRfqPubDate}">
									<tr>
										<td class="col-xs-2 success text-right">Tender/RFQ
											publication Date:</td>
										<td class="col-xs-2">${appPurchaseMst.tendRfqPubDate}</td>

										<td class="col-xs-2 success text-right">Tender/RFQ
											Invitation doc copy:</td>
										<td class="col-xs-2"><c:if
												test="${!empty appPurchaseMst.tendRfqDoc}">
												<a target="_blank"
													href="${pageContext.request.contextPath}/app/purchase/download.do?downloadDocFile=${appPurchaseMst.tendRfqDoc}">
													<span class="fa fa-file-pdf-o red center"
													aria-hidden="true" style="font-size: 1.5em;"></span>
												</a>
											</c:if></td>

										<td class="col-xs-2 success text-right">Tender/RFQ
											Submission Date:</td>
										<td class="col-xs-2">${appPurchaseMst.tendRfqSubDate}</td>
									</tr>
									<tr>
										<td class="col-xs-2 success text-right">Tender/RFQ
											Extention Date:</td>
										<td class="col-xs-2">
											${appPurchaseMst.tendRfqExtentionDate}</td>
										<td class="col-xs-2 success text-right">Tender/RFQ
											publication copy:</td>
										<td class="col-xs-2" colspan="3"><c:if
												test="${!empty appPurchaseMst.tendRfqPubDoc}">
												<a target="_blank"
													href="${pageContext.request.contextPath}/app/purchase/download.do?downloadDocFile=${appPurchaseMst.tendRfqPubDoc}">
													<span class="fa fa-file-pdf-o red center"
													aria-hidden="true" style="font-size: 1.5em;"></span>
												</a>
											</c:if></td>
									</tr>
								</c:if>
							</c:otherwise>

						</c:choose>
					</table>
				</div>

				<div>
					<table class="table table-bordered">
						<c:choose>

							<c:when test="${stateCode=='500'}">
								<tr>
									<td class="col-xs-2 success text-right">Tender opening
										Date:</td>
									<td class="col-xs-2"><input type="text"
										name="tendOpeningDate" id="tendOpeningDate"
										required="required" class="form-control datepicker-15" /></td>
									<td class="col-xs-2 success text-right">Number of
										submission:</td>
									<td class="col-xs-6"><input type="number"
										name="numberOfSubmission" required="required" /></td>
								</tr>
								<tr>
									<td class="col-xs-2 success text-right">Tender/RFQ
										Evaluation Date:</td>
									<td class="col-xs-2"><input type="text"
										name="tendRfqEvalDate" class="form-control datepicker-15"
										id="tendRfqEvalDate" required="required" /></td>
									<td class="col-xs-2 success text-right">Tender/RFQ
										Evaluation Copy:</td>
									<td class="col-xs-6"><input type="file"
										name="evaluationDoc" id="evaluationDoc" /> <input type="file"
										name="specificationDoc" class="hidden" /> <input type="file"
										name="purchaseRequsitionNoteDoc" class="hidden" /> <input
										type="file" name="requisitionAppDoc" class="hidden" /> <input
										type="file" name="draftTenderDoc" class="hidden" /> <input
										type="file" name="reviewDoc" class="hidden" /> <input
										type="file" name="approvedDoc" class="hidden" /> <input
										type="file" name="workOrderDoc" class="hidden" /> <input
										type="file" name="tendRfqPubDoc" class="hidden" /> <input
										type="file" name="tendRfqDoc" class="hidden" /> <!-- <input type="file" name="evaluationDoc" class="hidden"/> -->
										<input type="file" name="pgDoc" class="hidden" /> <input
										type="file" name="noaDoc" class="hidden" /></td>
								</tr>
							</c:when>

							<c:otherwise>
								<c:if test="${!empty appPurchaseMst.tendOpeningDate}">
									<tr>
										<td class="col-xs-2 success text-right">Tender/RFQ
											opening Date:</td>
										<td class="col-xs-2">${appPurchaseMst.tendOpeningDate}</td>
										<td class="col-xs-2 success text-right">Number of
											submission:</td>
										<td class="col-xs-6">${appPurchaseMst.numberOfSubmission}</td>
									</tr>
									<tr>
										<td class="col-xs-2 success text-right">Tender/RFQ
											Evaluation Date:</td>
										<td class="col-xs-2">${appPurchaseMst.tendRfqEvalDate}</td>
										<td class="col-xs-2 success text-right">Tender/RFQ
											Evaluation Copy:</td>
										<td class="col-xs-6"><c:if
												test="${!empty appPurchaseMst.evaluationDoc}">
												<a target="_blank"
													href="${pageContext.request.contextPath}/app/purchase/download.do?downloadDocFile=${appPurchaseMst.evaluationDoc}">
													<span class="fa fa-file-pdf-o red center"
													aria-hidden="true" style="font-size: 1.5em;"></span>
												</a>
											</c:if></td>
									</tr>
								</c:if>
							</c:otherwise>

						</c:choose>
					</table>
				</div>

				<div>
					<table class="table table-bordered">
						<c:choose>

							<c:when test="${stateCode=='600'}">
								<tr>
									<td class="col-xs-2 success text-right">NOA Date:</td>
									<td class="col-xs-2" colspan="2"><input type="text"
										name="noaDate" id="noaDate" class="form-control datepicker-15"
										required="required" /></td>
									<td class="col-xs-2 success text-right">NOA Copy:</td>
									<td class="col-xs-2" colspan="2"><input type="file"
										name="noaDoc" value=""> <input type="file"
										name="specificationDoc" class="hidden" /> <input type="file"
										name="purchaseRequsitionNoteDoc" class="hidden" /> <input
										type="file" name="requisitionAppDoc" class="hidden" /> <input
										type="file" name="draftTenderDoc" class="hidden" /> <input
										type="file" name="reviewDoc" class="hidden" /> <input
										type="file" name="approvedDoc" class="hidden" /> <input
										type="file" name="workOrderDoc" class="hidden" /> <input
										type="file" name="tendRfqPubDoc" class="hidden" /> <input
										type="file" name="tendRfqDoc" class="hidden" /> <input
										type="file" name="evaluationDoc" class="hidden" /> <input
										type="file" name="pgDoc" class="hidden" /> <!-- <input type="file" name="noaDoc" class="hidden"/> -->
									</td>
								</tr>
							</c:when>

							<c:otherwise>
								<c:if test="${!empty appPurchaseMst.noaDate}">
									<tr>
										<td class="col-xs-2 success text-right">NOA Date:</td>
										<td class="col-xs-2">${appPurchaseMst.noaDate}</td>
										<td class="col-xs-2 success text-right">NOA Copy:</td>
										<td class="col-xs-6"><c:if
												test="${!empty appPurchaseMst.noaDoc}">
												<a target="_blank"
													href="${pageContext.request.contextPath}/app/purchase/download.do?downloadDocFile=${appPurchaseMst.noaDoc}">
													<span class="fa fa-file-pdf-o red center"
													aria-hidden="true" style="font-size: 1.5em;"></span>
												</a>
											</c:if></td>
									</tr>
								</c:if>
							</c:otherwise>

						</c:choose>
					</table>
				</div>

				<div>
					<table class="table table-bordered">
						<c:choose>
							<c:when test="${stateCode=='700'}">
								<tr>
									<td class="col-xs-2 success text-right">PG Amount:</td>
									<td class="col-xs-2"><input type="number" name="pgAmount"
										required="required" id="pgAmount" step="0.01"
										class="form-control" /></td>
									<td class="col-xs-2 success text-right">PG Expired Date:</td>
									<td class="col-xs-2"><input type="text" name="expiredDate"
										required="required" id="expiredDate"
										class="form-control datepicker-15" /></td>
									<td class="col-xs-2 success text-right">Performace
										Guarantee (PG) Copy:</td>
									<td class="col-xs-2"><input type="file" name="pgDoc"
										id="pgDoc" /> <input type="file" name="specificationDoc"
										class="hidden" /> <input type="file"
										name="purchaseRequsitionNoteDoc" class="hidden" /> <input
										type="file" name="requisitionAppDoc" class="hidden" /> <input
										type="file" name="draftTenderDoc" class="hidden" /> <input
										type="file" name="reviewDoc" class="hidden" /> <input
										type="file" name="approvedDoc" class="hidden" /> <input
										type="file" name="workOrderDoc" class="hidden" /> <input
										type="file" name="tendRfqPubDoc" class="hidden" /> <input
										type="file" name="tendRfqDoc" class="hidden" /> <input
										type="file" name="evaluationDoc" class="hidden" /> <!-- <input type="file" name="pgDoc" class="hidden"/> -->
										<input type="file" name="noaDoc" class="hidden" /></td>
								</tr>
							</c:when>

							<c:otherwise>
								<c:if test="${!empty appPurchaseMst.expiredDate}">
									<tr>
										<td class="col-xs-2 success text-right">PG Amount:</td>
										<td class="col-xs-2">${appPurchaseMst.pgAmount}</td>
										<td class="col-xs-2 success text-right">PG Expired Date:</td>
										<td class="col-xs-2">${appPurchaseMst.expiredDate}</td>
										<td class="col-xs-2 success text-right">Performace
											Guarantee (PG) Copy:</td>
										<td class="col-xs-2"><c:if
												test="${!empty appPurchaseMst.pgDoc}">
												<a target="_blank"
													href="${pageContext.request.contextPath}/app/purchase/download.do?downloadDocFile=${appPurchaseMst.pgDoc}">
													<span class="fa fa-file-pdf-o red center"
													aria-hidden="true" style="font-size: 1.5em;"></span>
												</a>
											</c:if></td>
									</tr>
								</c:if>
							</c:otherwise>

						</c:choose>
					</table>
				</div>

				<div>
					<table class="table table-bordered">
						<c:choose>

							<c:when test="${stateCode=='800'}">
								<tr>
									<td class="col-xs-2 success text-right">Work Order No:</td>
									<td class="col-xs-2"><input type="text" name="workOrderNo"
										onblur="checkWorkOrderNo()" id="workOrderNo"
										style="width: 100%; border: 0; border-bottom: 2px ridge;"
										required="required" /> <strong
										class="text-danger workOrderNo hide">Work Order No.
											is already used</strong></td>
									<td class="col-xs-2 success text-right">Work Order Date:</td>
									<td class="col-xs-2"><input type="text"
										style="width: 100%; border: 0; border-bottom: 2px ridge;"
										name="workOrderDate" id="workOrderDate" required="required"
										class="form-control datepicker-15" /></td>
									<td class="col-xs-2 success text-right">Contract Amount:</td>
									<td class="col-xs-2"><input type="number"
										style="width: 100%; border: 0; border-bottom: 2px ridge;"
										name="contractAmount" id="contractAmount" required="required"
										class="form-control" min="0" /></td>
								</tr>
								<tr>
									<td class="col-xs-2 success text-right">Contractor Name:</td>
									<td class="col-xs-2"><input type="text"
										style="width: 100%; border: 0; border-bottom: 2px ridge;"
										name="contractorName" id="contractorName" required="required" /></td>
									<td class="col-xs-2 success text-right">Work Order Copy:</td>
									<td class="col-xs-6" colspan="3"><input type="file"
										name="workOrderDoc" id="workOrderDoc" /> <input type="file"
										name="specificationDoc" class="hidden" /> <input type="file"
										name="purchaseRequsitionNoteDoc" class="hidden" /> <input
										type="file" name="requisitionAppDoc" class="hidden" /> <input
										type="file" name="draftTenderDoc" class="hidden" /> <input
										type="file" name="reviewDoc" class="hidden" /> <input
										type="file" name="approvedDoc" class="hidden" /> <!-- <input type="file" name="workOrderDoc" class="hidden"/> -->
										<input type="file" name="tendRfqPubDoc" class="hidden" /> <input
										type="file" name="tendRfqDoc" class="hidden" /> <input
										type="file" name="evaluationDoc" class="hidden" /> <input
										type="file" name="pgDoc" class="hidden" /> <input type="file"
										name="noaDoc" class="hidden" /></td>
								</tr>
							</c:when>

							<c:otherwise>
								<c:if test="${!empty appPurchaseMst.workOrderNo}">
									<tr>
										<td class="col-xs-2 success text-right">Work Order No:</td>
										<td class="col-xs-2">${appPurchaseMst.workOrderNo}</td>
										<td class="col-xs-2 success text-right">Work Order Date:</td>
										<td class="col-xs-6">${appPurchaseMst.workOrderDate}</td>
									</tr>
									<tr>
										<td class="col-xs-2 success text-right">Contractor Name:</td>
										<td class="col-xs-2">${appPurchaseMst.contractorName}</td>
										<td class="col-xs-2 success text-right">Work Order Copy:</td>
										<td class="col-xs-6"><c:if
												test="${!empty appPurchaseMst.workOrderDoc}">
												<a target="_blank"
													href="${pageContext.request.contextPath}/app/purchase/download.do?downloadDocFile=${appPurchaseMst.workOrderDoc}">
													<span class="fa fa-file-pdf-o red center"
													aria-hidden="true" style="font-size: 1.5em;"></span>
												</a>
											</c:if></td>
									</tr>
								</c:if>
							</c:otherwise>

						</c:choose>
					</table>
				</div>


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
										<td>
										<fmt:formatNumber type="number" groupingUsed="false" value="${appPurchaseDtl.procurementPackageDtl.totalCost/100000}" /> Million
										<%-- ${appPurchaseDtl.procurementPackageDtl.totalCost} --%></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</c:if>
				<!-- Item Details :: end -->
			</div>




			<table class="col-xs-12 table table-bordered"
				style="margin-bottom: 15px;">
				<c:choose>
					<c:when test="${stateCode=='800'}">
						<tr>
							<td class="col-xs-12 center"><input type="hidden" name="id"
								value="${appPurchaseMst.id}" />
								<button type="submit" class="btn btn-primary btn-sm"
									id='updateButtonUp' style="border-radius: 6px;" disabled>
									<i class="fa fa-refresh"></i> Submit
								</button></td>
						</tr>

					</c:when>

					<c:otherwise>
						<tr>
							<td class="col-xs-2 text-right" style="font-weight: bold;">Send
								To</td>
							<td class="col-xs-3"><select class="form-control"
								name="userid" id="sendTo" required="required">
									<option value="">Select One</option>
									<c:if test="${!empty procProcessCommitteeList}">
										<c:forEach items="${procProcessCommitteeList}"
											var="procProcessCommittee">
											<option value="${procProcessCommittee.authUser.userid}">${procProcessCommittee.authUser.designation}&nbsp;(${procProcessCommittee.authUser.name})</option>
										</c:forEach>
									</c:if>
							</select> <input type="hidden" name="id" value="${appPurchaseMst.id}" /></td>
							<td class="col-xs-2 success text-right"
								style="font-weight: bold;">For :</td>
							<td class="col-xs-3"><select class="form-control"
								name="stateCode" id="sendFor" required="required">
									<option value="">Select One</option>
									<c:if test="${!empty approvalHierarchyList}">
										<c:forEach items="${approvalHierarchyList}"
											var="approvalHierarchy">
											<option value="${approvalHierarchy.stateCode}">${approvalHierarchy.approvalHeader}</option>
										</c:forEach>
									</c:if>
							</select></td>
							<td class="col-xs-2 center">
								<button type="submit" class="btn btn-primary btn-sm"
									id='updateButton' style="border-radius: 6px;">
									<i class="fa fa-refresh"></i> Submit
								</button>
							</td>
						</tr>
					</c:otherwise>

				</c:choose>
			</table>
		</form>
	</div>
</div>

<script>
	//Added by: Shimul, IBCS
	function checkWorkOrderNo() {
		var workOrderNo = $('#workOrderNo').val();
		var baseURL = $('#contextPath').val();
		var saveButton = $('#updateButtonUp');

		$.ajax({
			url : baseURL + "/app/purchase/checkWorkOrderNo.do",
			data : {
				"workOrderNo" : workOrderNo
			},
			success : function(data) {
				if (data == "blank") {
					$('.workOrderNo').removeClass('hide text-success')
							.addClass('text-danger').text(
									'This field is required');
					saveButton.prop("disabled", true);
				} else if (data == "success") {
					$('.workOrderNo').removeClass('hide text-danger').addClass(
							'text-success').text('Valid Work Order No.');
					saveButton.prop("disabled", false);
				} else {
					$('.workOrderNo').removeClass('hide text-success')
							.addClass('text-danger').text(
									'Work Order No is used. Try another');
					saveButton.prop("disabled", true);
				}
			},
			error : function(data) {
				alert("Server Error");
			},
			type : 'POST'
		});
	}
</script>

<!-- ----------------------------------------- -->
<%@include file="../../common/ibcsFooter.jsp"%>