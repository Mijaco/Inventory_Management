<%@include file="../common/ssHeader.jsp"%>
<!-- -------------------End of Header-------------------------- -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		
		<%-- <div class="o_form_buttons_edit" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/ss/list.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Central Store Item Return List
			</a>
		</div> --%>
		<h2 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Store Ticket
			</h2>

		<h4 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptName}</h4>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; margin: 10px;">
		<!-- --------------------- -->
		<form method="POST"
			action="${pageContext.request.contextPath}/ss/returnSlip/saveStoreTicketForSSReturnSlip.do">
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

				</div>

				<div class="col-md-6 col-sm-6">
					<div class="form-group">
						<label for="operationId" class="col-sm-4 control-label">Return
							Slip No</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="operationId"
								style="border: 0; border-bottom: 2px ridge;"
								value="${csStoreTicketMst.operationId}" name="operationId"
								readonly="readonly" />
						</div>
					</div>
				<%-- 	<c:if test="${!empty csStoreTicketMst.receivedFrom}">
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
					</c:if> --%>
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

					<%-- <c:if test="${!empty csStoreTicketMst.issuedTo}">
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
 --%>
					<div>
						<input type="hidden" class="form-control" id="createdBy"
							value='<sec:authentication property="principal.username" />'
							name="createdBy" /> <input type="hidden" class="form-control"
							id="roleName"
							value='<sec:authentication property="principal.Authorities[0]" />'
							name="roleName" /> <input type="hidden" class="form-control"
							id="id" style="border: 0; border-bottom: 2px ridge;" name="id"
							value="${csStoreTicketMst.id}" /> <input type="hidden"
							class="form-control" id="storeTicketType"
							style="border: 0; border-bottom: 2px ridge;"
							name="storeTicketType"
							value="${csStoreTicketMst.storeTicketType}" />
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
								<c:if test="${!empty ssReturnSlipDtlList}">
									<div class="col-xs-12 table-responsive">
										<div class="table">
											<div style="width: 2400px;">
												<table id="requisitionListTable"
													class="table table-striped table-hover table-bordered table-responsive">
													<thead>
														<tr
															style="background: #579EC8; color: white; font-weight: normal;">
															<td style="">Item Code</td>
															<td style="">Description</td>
															<td style="">Unit</td>
															<td style="">Total Quantity</td>
															<td style="">Servicable Qty</td>
															<td style="">Recovery Servicable Qty</td>
															<td style="">UnServicable Qty</td>
															<td style="">Book No</td>
															<td style="">Page No</td>
															<td style="">Remarks</td>
														</tr>
													</thead>

													<tbody>
														<c:forEach items="${ssReturnSlipDtlList}"
															var="ssReturnSlipDtl">
															<tr>
																<td><input type="text" name="itemId"
																	value="${ssReturnSlipDtl.itemCode}" readonly="readonly" /></td>
																<td><input type="text" name="description"
																	value="${ssReturnSlipDtl.description}" readonly="readonly" /></td>
																	
																<td><input type="text" name="uom"
																	readonly="readonly" sytle="width:100%"
																	value="${ssReturnSlipDtl.uom}" /></td>
																<td><input type="text"
																	value="${ssReturnSlipDtl.totalReturnRcv}"
																	readonly="readonly" />																	
																	<%-- <input type="hidden"
																	name="ledgerName"
																	value="${csReturnSlipDtl.ledgerName}"
																	readonly="readonly" /> --%></td>
																<td><input type="text" name=qtyNewServiceable readonly="readonly"
																	value="${ssReturnSlipDtl.qtyNewServiceableRcv}" /></td>
																<td><input type="text" name="qtyRecServiceable" readonly="readonly"
																	value="${ssReturnSlipDtl.qtyRecServiceableRcv}" /></td>
																<td><input type="text" name="qtyUnServiceable" readonly="readonly"
																	value="${ssReturnSlipDtl.qtyUnServiceableRcv}" /></td>
																<td><input type="text" name="ledgerBookNo" value="0"/>
																</td>
																<td><input type="text" name="ledgerPageNo" value="0"/></td>
																<%-- <td><input type="text" name="cost" value="${csReturnSlipDtl.cost}" /></td> --%>
																<td><input type="text" name="remarks"
																	value="${ssReturnSlipDtl.remarks}" /></td>
															</tr>
														</c:forEach>
													</tbody>
												</table>
											</div>
										</div>
									</div>
								</c:if>
							</div>
							<hr />
						</div>
					</div>

				</div>

				<div class="col-md-12" align="center">
					<div class="form-group" style="margin-top: 2em;">
						<button type="button" id="saveButton" style="margin-left: 10px; border-radius: 6px;"
							class="btn btn-lg btn-success">
							<i class="fa fa-fw fa-save"></i>&nbsp;Save
						</button>
						
						<button type="reset" class="btn btn-lg btn-danger" style="border-radius: 6px;">
							<i class="ace-icon fa fa-refresh"></i>&nbsp;Reset
						</button>
					</div>
				</div>
			</div>
		</form>
		<!-- --------------------- -->
	</div>
</div>

<script>
	//Added by: Ihteshamul Alam
	$(document).ready( function() {
		$('#saveButton').click( function() {
			var haserror = false;
			if( haserror == true ) {
				return;
			} else {
				$('#saveButton').prop('disabled', true);
				$('form').submit();
			}
		});
	});
</script>

<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>