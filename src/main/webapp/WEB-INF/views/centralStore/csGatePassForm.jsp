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
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span> Gate
				Pass List
			</a>
			<!-- <button accesskey="D" class="btn btn-info btn-sm" type="button">
				Discard</button> -->
		</div>
		<h1 class="center blue"
			style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Gate
			Pass Form</h1>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<!-- --------------------- -->
		<form method="POST"
			action="${pageContext.request.contextPath}/cs/saveGatePass.do">
			<div class="oe_title">

				<div class="col-md-6 col-sm-6">

					<c:if test="${!empty csGatePassMst.gatePassNo}">

						<div class="form-group">
							<label for="gatePassNo" class="col-sm-4 control-label">Gate
								Pass No</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" id="gatePassNo"
									style="border: 0; border-bottom: 2px ridge;"
									value="${csGatePassMst.gatePassNo}" name="gatePassNo"
									readonly="readonly" />
							</div>
						</div>
					</c:if>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					<div class="form-group">
						<label for="ticketNo" class="col-sm-4 control-label">Ticket
							No</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="ticketNo"
								style="border: 0; border-bottom: 2px ridge;" name="ticketNo"
								value="${csGatePassMst.ticketNo}" readonly="readonly" />
						</div>
					</div>


				</div>

				<div class="col-md-6 col-sm-6">
					<div class="form-group">
						<label for="operationId" class="col-sm-4 control-label">Requisition
							No</label>
						<div class="col-sm-8">
							<input type="text" class="form-control" id="operationId"
								style="border: 0; border-bottom: 2px ridge;"
								value="${csGatePassMst.requisitonNo}" name="requisitonNo"
								readonly="readonly" />
						</div>
					</div>

					<c:if test="${!empty csGatePassMst.issuedTo}">
						<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
						<div class="form-group">
							<label for="issuedTo" class="col-sm-4 control-label">Issued
								To</label>
							<div class="col-sm-8">
								<input type="text" class="form-control" id="issuedTo"
									style="border: 0; border-bottom: 2px ridge;"
									value="${csGatePassMst.issuedTo}" name="issuedTo"
									readonly="readonly" />
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
							value="${csGatePassMst.id}" />
					</div>

				</div>
				<div class="col-md-12 col-sm-12">
					<div class="form-group" style="margin-top: 1em;">
						<label for="fax" class="col-sm-4 control-label"
							style="font-style: italic; font-weight: bold;"> Gate Pass
							Item(s):</label>

						<div class="col-md-12 col-sm-12 col-xs-12">
							<hr />
							<div class="col-xs-12">
								<c:if test="${!empty csReqDtlList}">
									<table id="requisitionListTable"
										class="table table-striped table-hover table-bordered table-responsive"
										style="width: 100%">
										<thead>
											<tr
												style="background: #579EC8; color: white; font-weight: normal;">
												<td style="">Item Code</td>
												<td style="">Description</td>
												<td style="">Unit</td>
												<td style="">Quantity</td>
											</tr>
										</thead>

										<tbody>
											<c:forEach items="${csReqDtlList}" var="csReqDtl">
												<tr>
													<td><input type="text" name="itemCode"
														value="${csReqDtl.itemCode}" readonly="readonly" /></td>
													<td><input type="text" name="description"
														value="${csReqDtl.itemName}" readonly="readonly" /></td>
													<td><input type="text" name="uom" readonly="readonly"
														value="${csReqDtl.uom}" /></td>
													<td><input type="text" name="quantity"
														value="${csReqDtl.quantityIssued}" readonly="readonly" /></td>
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