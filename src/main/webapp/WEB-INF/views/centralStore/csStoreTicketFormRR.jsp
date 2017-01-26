<%@include file="../common/csHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->



<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<!-- <h4>
			<a href="#" style="text-decoration: none;">Central Store Receive</a>
			/ New
		</h4> -->
		<div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/cs/itemRecieved/storeTicketlist.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Store Ticket List
			</a>
			<!-- <button accesskey="D" class="btn btn-info btn-sm" type="button">
				Discard</button> -->
		</div>
		<h1 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Store
				Ticket Information</h1>
	</div>

	<div class="row"
		style="background-color: white; padding-left: 10px; margin: 10px; padding-bottom: 10px;">
		<!-- --------------------- -->
		<form method="POST"
			action="${pageContext.request.contextPath}/cs/itemRecieved/saveStoreTicket.do">
			<div class="oe_title">

				<div class="col-md-6 col-sm-6">
					<div class="form-group">
						<label for="ticketNo" class="col-sm-4 control-label">Ticket
							No</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="ticketNo"
								style="border: 0; border-bottom: 2px ridge;" name="ticketNo"
								value="${csStoreTicketMst.ticketNo}" readonly="readonly" />
						</div>
					</div>
					<c:if test="${!empty csStoreTicketMst.workOrderNo}">
						<div class="col-sm-10">&nbsp;&nbsp;</div>
						<div class="form-group">
							<label for="workOrderNo" class="col-sm-4 control-label">Work
								Order No</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" id="workOrderNo"
									style="border: 0; border-bottom: 2px ridge;"
									value="${csStoreTicketMst.workOrderNo}" name="workOrderNo"
									readonly="readonly" />
							</div>
						</div>
					</c:if>

					<c:if test="${!empty csStoreTicketMst.returnBy}">
						<div class="col-sm-10">&nbsp;&nbsp;</div>
						<div class="form-group">
							<label for="returnBy" class="col-sm-4 control-label">Return
								By</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" id="returnBy"
									style="border: 0; border-bottom: 2px ridge;"
									value="${csStoreTicketMst.returnBy}" name="returnBy"
									readonly="readonly" />
							</div>
						</div>
					</c:if>
					<c:if test="${!empty csStoreTicketMst.issuedTo}">
						<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
						<div class="form-group">
							<label for="issuedTo" class="col-sm-4 control-label">Issued
								To</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" id="issuedTo"
									style="border: 0; border-bottom: 2px ridge;"
									value="${csStoreTicketMst.issuedTo}" name="issuedTo"
									readonly="readonly" />
							</div>
						</div>
					</c:if>

				</div>

				<div class="col-md-6 col-sm-6">
					<div class="form-group">
						<label for="operationId" class="col-sm-4 control-label">R.
							No</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="operationId"
								style="border: 0; border-bottom: 2px ridge;"
								value="${csStoreTicketMst.operationId}" name="operationId"
								readonly="readonly" />
						</div>
					</div>
					<c:if test="${!empty csStoreTicketMst.receivedFrom}">
						<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
						<div class="form-group">
							<label for="receivedFrom" class="col-sm-4 control-label">Received
								From</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" id="receivedFrom"
									style="border: 0; border-bottom: 2px ridge;"
									value="${csStoreTicketMst.receivedFrom}" name="receivedFrom"
									readonly="readonly" />
							</div>
						</div>
					</c:if>
					<c:if test="${!empty csStoreTicketMst.returnFor}">
						<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
						<div class="form-group">
							<label for="returnFor" class="col-sm-4 control-label">Return
								For</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" id="returnFor"
									style="border: 0; border-bottom: 2px ridge;"
									value="${csStoreTicketMst.returnFor}" name="returnFor" />
							</div>
						</div>
					</c:if>
					<c:if test="${!empty csStoreTicketMst.issuedFor}">
						<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
						<div class="form-group">
							<label for="issuedFor" class="col-sm-4 control-label">Issued
								For</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" id="issuedFor"
									style="border: 0; border-bottom: 2px ridge;"
									value="${csStoreTicketMst.issuedFor}" name="issuedFor" />
							</div>
						</div>
					</c:if>

					<div>
						<input type="hidden" class="form-control" id="createdBy"
							value='<sec:authentication property="principal.username" />'
							name="createdBy" /> <input type="hidden" class="form-control"
							id="roleName"
							value='<sec:authentication property="principal.Authorities[0]" />'
							name="roleName" /> <input type="hidden" class="form-control"
							id="id" style="border: 0; border-bottom: 2px ridge;" name="id"
							value="${csStoreTicketMst.id}" />
					</div>

				</div>
				<div class="col-md-12 col-sm-12">
					<div class="form-group" style="margin-top: 1em;">
						<label for="fax" class="col-sm-4 control-label"
							style="font-style: italic; font-weight: bold;"> Store
							Ticket Item(s):</label>

						<div class="col-md-12 col-sm-12 col-xs-12">
							<hr />
							<div class="col-xs-12">
								<c:if test="${!empty csProcItemRcvDtlList}">
									<table id="requisitionListTable"
										class="table table-striped table-hover table-bordered table-responsive"
										style="width: 70%">
										<thead>
											<tr
												style="background: #579EC8; color: white; font-weight: normal;">
												<td style="">Item Code</td>
												<td style="">Description</td>
												<td style="">Unit</td>
												<td style="">Total Quantity</td>
												<td style="">Useable Qty</td>
												<!-- <td style="">Repair Qty</td>
												<td style="">Damage Qty</td> -->
												<td style="">Book No</td>
												<td style="">Page No</td>
												<td style="">Cost</td>
												<td style="">Remarks</td>
											</tr>
										</thead>

										<tbody>
											<c:forEach items="${csProcItemRcvDtlList}"
												var="csProcItemRcvDtl">
												<tr>
													<td><input type="text" name="itemId"
														value="${csProcItemRcvDtl.itemId}" readonly="readonly" /></td>
													<td><input type="text" name="description"
														value="${csProcItemRcvDtl.description}"
														readonly="readonly" /></td>
													<td><input type="text" name="uom" readonly="readonly"
														style="width: 100%;" value="${csProcItemRcvDtl.uom}" /></td>
													<td><input type="text" name="receivedQty"
														value="${csProcItemRcvDtl.receivedQty}"
														readonly="readonly" /></td>
													<td><input type="text" name="useableQty"
														value="${csProcItemRcvDtl.receivedQty}"
														readonly="readonly" /></td>
													<!-- <td><input type="text" name="repairQty"
														style="width: 100%" /></td>
													<td><input type="text" name="damageQty"
														style="width: 100%" /></td> -->
													<td><input type="text" name="ledgerBookNo"
														style="width: 100%;" value="0" /></td>
													<td><input type="text" name="ledgerPageNo"
														style="width: 100%;" value="0" /></td>
													<td><input type="text" name="cost" readonly="readonly"
														value="${csProcItemRcvDtl.cost}" /></td>
													<td><input type="text" name="remarks"
														readonly="readonly" value="${csProcItemRcvDtl.remarks}" /></td>
												</tr>
											</c:forEach>
										</tbody>
									</table>
								</c:if>
							</div>
							<hr />
						</div>
					</div>

				</div>

				<div class="col-md-12">
					<div class="form-group" style="margin-top: 2em;">
						<button type="reset" class="width-20 pull-left btn btn-sm">
							<i class="ace-icon fa fa-refresh"></i> <span class="bigger-50">Reset</span>
						</button>
						<button type="submit" style="margin-left: 10px;"
							class="width-20 pull-left btn btn-sm btn-success">
							<i class="ace-icon fa fa-save"></i> <span class="bigger-50">Save</span>
						</button>
					</div>
				</div>
			</div>
		</form>
		<!-- --------------------- -->
	</div>
</div>
<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>