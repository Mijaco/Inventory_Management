<%@include file="../common/csHeader.jsp"%>
<%-- 
<link href="${pageContext.request.contextPath}/resources/assets/appendGrid/jquery-ui.theme.min.css" rel="stylesheet"> --%>

<!--This CSS is ok for Datepicker, But It is changing the color of Location Grid -->
<link
	href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
	rel="stylesheet">

<!-- -------------------End of Header-------------------------- -->

<!-- CSS Start for Location Grid-->
<style type="text/css">
.btn-add {
	margin-bottom: 20px;
}

.ui-widget-header {
	border: 1px solid #aed0ea;
	background: #deedf7
		url("${pageContext.request.contextPath}/resources/assets/appendGrid/images/ui-bg_highlight-soft_100_deedf7_1x100.png")
		50% 50% repeat-x;
	color: #222;
	font-weight: bold;
}

.ui-state-active, .ui-widget-content .ui-state-active, .ui-widget-header .ui-state-active
	{
	border: 1px solid #2694e8;
	background: #3baae3
		url("${pageContext.request.contextPath}/resources/assets/appendGrid/images/ui-bg_glass_50_3baae3_1x400.png")
		50% 50% repeat-x;
	font-weight: bold;
	color: #fff;
}

.ui-state-default .ui-icon {
	background-image:
		url("${pageContext.request.contextPath}/resources/assets/appendGrid/images/ui-icons_3d80b3_256x240.png");
}

.ui-widget-overlay {
	opacity: .6 !important;
}
</style>
<!-- ---End of Style and Script ------ -->

<div class="container-fluid icon-box" style="background-color: #858585;">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit" style="display: block;">
			<a href="${pageContext.request.contextPath}/cs/itemRecieved/list.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>
				Central Store Receive List
			</a>

		</div>

		<h2 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Receiving
			Report Form</h2>

		<h4 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptName}</h4>

		<%-- <h5 class="center blue"
			style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${deptAddress}</h5> --%>
	</div>

	<div class="row"
		style="background-color: white; padding-left: 10px; margin: 10px; padding-bottom: 10px;">
		<!-- --------------------- -->
		<c:if test="${empty wOrderMst}">
			<div class="ui-widget">
				<input type="text" class="form-control" id="contractNo"
					placeholder="Enter a Work order or Contract number. Example: 1"
					style="border: 0; border-bottom: 2px ridge;" value=""
					name="contractNo" />
			</div>
		</c:if>

		<br>

		<form method="POST" id="itemRcv"
			action="${pageContext.request.contextPath}/cs/itemRecieved/itemReceivedSave.do">
			<c:if test="${!empty wOrderMst}">
				<div class="oe_title">
					<div class="col-md-6 col-sm-6">
						<div class="form-group">
							<label for="contractNo" class="col-sm-4 control-label">WO/Contract
								No&nbsp;<strong class='red'>*</strong></label>
							<div class="col-sm-8">
								<div class="ui-widget">
									<input type="text" class="form-control" id="contractNo"
										placeholder="1234"
										style="border: 0; border-bottom: 2px ridge;"
										value="${wOrderMst.workOrderNo}" name="contractNo" /> <input
										type="hidden" name="contextPath" id="contextPath"
										value="${pageContext.request.contextPath}" /> <input
										type="hidden" id="khathId" name="khathId"
										value="${wOrderMst.khathId}" /> <input type="hidden"
										name="uuid" id="uuid" value="${uuid}" /> <input type="hidden"
										name="ledgerName" id="ledgerName" value="${ledgerName}" /> <input
										type="hidden" id="locationList" value=${locationList } />
								</div>
							</div>
						</div>
						<div class="col-sm-10">&nbsp;&nbsp;</div>
						<div class="form-group">
							<label for="chalanNo" class="col-sm-4 control-label">Invoice/Chalan
								No&nbsp;<strong class='red'>*</strong></label>
							<div class="col-sm-8">
								<input type="text" class="form-control" id="chalanNo"
									placeholder="1234" style="border: 0; border-bottom: 2px ridge;"
									name="chalanNo" required>
								<strong class="text-danger hide chalanNo">This field is required</strong>
							</div>
						</div>

						<div class="col-sm-10">&nbsp;&nbsp;</div>
						<div class="form-group">
							<label for="supplierName" class="col-sm-4 col-md-4 control-label">Supplier
								Name&nbsp;<strong class='red'>*</strong></label>
							<div class="col-sm-8 col-md-8">
								<input type="text" class="form-control" id="supplierName"
									style="border: 0; border-bottom: 2px ridge;"
									name="supplierName" value="${wOrderMst.supplierName}"
									readonly="readonly" />
							</div>
						</div>
						<div class="col-sm-10">&nbsp;&nbsp;</div>
						<div class="form-group">
							<label for="billOfLanding;" class="col-sm-4 control-label">Bill
								of Landing </label>
							<div class="col-sm-8">
								<input type="text" class="form-control" id="billOfLanding;"
									style="border: 0; border-bottom: 2px ridge;"
									name="billOfLanding" />
								<%--  readonly="readonly" value="${wOrderMst.billOfLanding}" --%>
							</div>
						</div>

					</div>
					<div class="col-md-6 col-sm-6">

						<div class="form-group">
							<label for="contractDate" class="col-sm-4 col-md-4 control-label">Contract
								Date</label>
							<div class="col-sm-8 col-md-8">
								<!-- <input type="date" class="form-control" id="contractDate"
								style="border: 0; border-bottom: 2px ridge;" name="contractDate" /> -->
								<input type="text" id="contractDate"
									style="border: 0; border-bottom: 2px ridge;"
									name="contractDate" value="${wOrderMst.contractDate}"
									readonly="readonly" />
							</div>
						</div>

						<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
						<div class="form-group">
							<label for="returnFor" class="col-sm-4 col-md-4 control-label">Invoice/Chalan
								Date&nbsp;<strong class='red'>*</strong></label>
							<div class="col-sm-8 col-md-8">
								<!-- <input type="date" class="form-control" id="invoiceDate"
								style="border: 0; border-bottom: 2px ridge;" name="invoiceDate" /> -->
								<input type="text" class="form-control datepicker-13"
									id="invoiceDate" style="border: 0; border-bottom: 2px ridge;"
									name="invoiceDate" required>
								<strong class="text-danger hide invoiceDate">This field is required</strong>
							</div>
						</div>

						<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
						<div class="form-group">
							<label for="deliveryDate" class="col-sm-4 col-md-4 control-label">Delivery
								Date&nbsp;<strong class='red'>*</strong></label>
							<div class="col-sm-8 col-md-8">
								<!-- <input type="date" class="form-control" id="delivaryDate"
								style="border: 0; border-bottom: 2px ridge;" name="deliveryDate" /> -->
								<input type="text" class="form-control datepicker-14"
									id="delivaryDate" style="border: 0; border-bottom: 2px ridge;"
									name="deliveryDate" required>
								<strong class="text-danger hide delivaryDate">This field is required</strong>
							</div>
						</div>
						<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>
						<div class="form-group">
							<label for="landingDate" class="col-sm-4 control-label">Landing
								Date</label>
							<div class="col-sm-8">
								<!-- <input type="date" class="form-control" id="landingDate"
								placeholder="yyyy-dd-MM"
								style="border: 0; border-bottom: 2px ridge;" name="landingDate"> -->
								<input type="text" class="form-control datepicker-13"
									id="landingDate" style="border: 0; border-bottom: 2px ridge;"
									name="landingDate">
							</div>
						</div>

						<div>
							<input type="hidden" class="form-control" id="createdBy"
								value='<sec:authentication property="principal.username" />'
								name="createdBy" /> <input type="hidden" class="form-control"
								id="roleName"
								value='<sec:authentication property="principal.Authorities[0]" />'
								name="roleName" />
						</div>

					</div>
					<!-- <div class="col-md-12 col-sm-12"> style="overflow-x: scroll; width: 60em;white-space: nowrap;" -->
					<div class="col-md-12 col-sm-12">
						<div class="form-group" style="margin-top: 1em;">
							<label for="fax" class="col-sm-4 control-label"
								style="font-style: italic; font-weight: bold;">Receiving
								Item(s):</label>

							<div class="col-xs-12 table-responsive">
								<div class="table">
									<div style="width: 2150px;">
										<hr />

										<div class="col-xs-12">

											<div class="form-group col-sm-1 col-xs-12">
												<b>Item Code</b>
											</div>

											<div class="form-group col-sm-2 col-xs-12">
												<b>Description</b>
											</div>
											<div class="form-group col-sm-1 col-xs-12">
												<b>Unit</b>
											</div>
											<div class="form-group col-sm-1 col-xs-12">
												<b>Purchased Qty</b>
											</div>

											<div class="form-group col-sm-1 col-xs-12">
												<b>Receiving Qty</b>
											</div>


											<!-- <div class="form-group col-sm-1 col-xs-12">
												<b>Rem. Qty</b>
											</div>
 -->
											<div class="form-group col-sm-1 col-xs-12 hide">
												<b>Unit Cost</b>
											</div>

<!-- 											<div class="totalCost hide form-group col-sm-1 col-xs-12"> -->
<!-- 												<b>Total Cost</b> -->
<!-- 											</div> -->

											<div class="form-group col-sm-1 col-xs-12">
												<b>Ledger Book</b>
											</div>
											<div class="form-group col-sm-1 col-xs-12">
												<b>Page No</b>
											</div>
											<div class="form-group col-sm-1 col-xs-12">
												<b>Remarks</b>
											</div>
											<div class="form-group col-sm-1 col-xs-12">
												<b>Location</b>
											</div>
										</div>
										<c:if test="${!empty wOrderDtl}">
											<div class="col-xs-12">
												<div class="row">
													<div class="control-group" id="fields">
														<div class="controls">
															<div class="aaa">
																<!-- <form role="form" autocomplete="off">  -->
																<div class="col-xs-12 entry" id="myArea0">

																	<c:forEach items="${wOrderDtl}" var="orderDtl"
																		varStatus="loop">
																		<div class="row">
																			<div class="form-group col-sm-1 col-xs-12">
																				<input class="form-control itemCode" name="itemId"
																					type="text" placeholder="itemCode"
																					readonly="readonly"
																					style="border: 0; border-bottom: 2px ridge;"
																					value="${orderDtl.itemId}" />
																			</div>
																			<div class="form-group col-sm-2 col-xs-12">
																				<input class="form-control uom" id="description"
																					name="description" type="text"
																					placeholder="Description" readonly="readonly"
																					style="border: 0; border-bottom: 2px ridge;"
																					value="${orderDtl.description}" />
																			</div>
																			<div class="form-group col-sm-1 col-xs-12">
																				<input class="form-control uom" name="uom"
																					type="text" placeholder="Unit" readonly="readonly"
																					style="border: 0; border-bottom: 2px ridge;"
																					value="${orderDtl.uom}" />
																			</div>
																			<div class="form-group col-sm-1 col-xs-12">
																				<input class="form-control expectedQty"
																					name="expectedQty" id="expectedQty${loop.index}"
																					type="number"
																					style="border: 0; border-bottom: 2px ridge;"
																					<%-- value="${orderDtl.remainingQty}" --%>
																					value='<fmt:formatNumber groupingUsed="false"
																						type="number" minFractionDigits="3" maxFractionDigits="3"
																						value="${orderDtl.remainingQty}" />'
																					readonly="readonly" />
																			</div>

																			<div class="form-group col-sm-1 col-xs-12">
																				<input class="form-control receivedQty"
																					onkeyup="receivedQtyToRemainQty(this)"
																					name="receivedQty" id="receivedQty${loop.index}"
																					type="number" value="0" readonly="readonly"
																					style="border: 0; border-bottom: 2px ridge;" />
																					
																					<strong class="text-danger errorQty hide" id="errorQty${loop.index}">
																						Quantity mismatch
																					</strong>
																			</div>

																			<%-- <div class="form-group col-sm-1 col-xs-12">
																				<input class="form-control remainingQty"
																					name="remainingQty" id="remainingQty${loop.index}"
																					type="number" readonly="readonly" value="0"
																					style="border: 0; border-bottom: 2px ridge;" />
																			</div> --%>

																			<div class="form-group col-sm-1 col-xs-12 hide">
																				<input class="form-control" name="cost" type="text"
																					placeholder="Unit Cost" id="cost${loop.index}"
																					style="border: 0; border-bottom: 2px ridge;"
																					value="${orderDtl.cost}" readonly="readonly" />
																			</div>
																			<!-- Total cost -->
<!-- 																			<div -->
<!-- 																				class="totalCost hide form-group col-sm-1 col-xs-12"> -->
<!-- 																				<input class="form-control" name="totalCost" -->
<!-- 																					type="text" placeholder="Total Cost" -->
<%-- 																					id="totalCost${loop.index}" --%>
<!-- 																					style="border: 0; border-bottom: 2px ridge;" -->
<!-- 																					value="" readonly="readonly" /> -->
<!-- 																			</div> -->
																			<div class="form-group col-sm-1 col-xs-12">
																				<input class="form-control" name="ledgerBookNo"
																					type="text" placeholder="Ledger No"
																					style="border: 0; border-bottom: 2px ridge;" />
																			</div>
																			<div class="form-group col-sm-1 col-xs-12">
																				<input class="form-control" name="ledgerPageNo"
																					type="text" placeholder="Page No"
																					style="border: 0; border-bottom: 2px ridge;" />
																			</div>
																			<div class="form-group col-sm-1 col-xs-12">
																				<input class="form-control" name="remarks"
																					type="text" placeholder="Remarks" value="NA"
																					style="border: 0; border-bottom: 2px ridge;" />
																			</div>

																			<div class="form-group col-sm-1 col-xs-12"
																				data-itemCode="${orderDtl.itemId}">
																				<a href="#" class="btn btn-primary"
																					id="setDialog${loop.index}"> <i
																					class="glyphicon glyphicon-edit"
																					onclick="openGridDialog(this)" aria-hidden="true">
																				</i>
																				</a>
																			</div>
																		</div>
																		<c:set var="count" value="${loop.count}" scope="page" />
																	</c:forEach>
																</div>
															</div>
															<!-- ---------------------- -->


														</div>
													</div>
												</div>
											</div>
										</c:if>
										<hr />

									</div>
								</div>
							</div>

						</div>

					</div>

					<!-- Test -->
					<div class="col-md-12" style="padding-top: 15px;">
						<div class="col-xs-12 col-sm-6">
							<button type="button" id="saveButton"
								style="margin-right: 10px; border-radius: 6px;"
								class="width-20 pull-right btn btn-lg btn-success">
								<i class="ace-icon fa fa-save"></i> <span class="bigger-50">Save</span>
							</button>
						</div>

						<div class="col-xs-12 col-sm-6">
							<button type="reset"
								class="width-20  pull-left btn btn-lg btn-danger"
								style="margin-left: 10px; border-radius: 6px;">
								<i class="ace-icon fa fa-refresh"></i> <span class="bigger-50">Reset</span>
							</button>
						</div>

					</div>
					<!-- Test -->
				</div>
				<input type="hidden" value="<c:out value ="${count}"/>"
					id="chekcount" />
			</c:if>
			<br>
			<c:if test="${empty wOrderMst}">
				<c:if test="${!empty flag && flag=='y'}">
					<font color="red">This Work Order Allready Received </font>
				</c:if>
			</c:if>
		</form>
	</div>
</div>
<!-- -------------------------- -->
<div id="myGrid" title="Title">
	<form action="" method="post">
		<table id="tblAppendGrid">
		</table>
	</form>
</div>

<script>
	$( document ).ready( function() {
		$('#saveButton').click( function() {
			var haserror = false, counter = 0;
			
			$('.expectedQty').each( function() {
				var id = this.id;
				var name = this.name;
				var sequence = id.substr( name.length );
				
				var requiredQty = parseFloat( $('#receivedQty'+sequence).val() );
				var expectedQty = parseFloat( $('#expectedQty'+sequence).val() );
				
				if( requiredQty == 0 ) {
					$('#errorQty'+sequence).removeClass('hide').text("This field is required");
					counter++;
				} else if( requiredQty > expectedQty ) {
					$('#errorQty'+sequence).removeClass('hide').text('Quantity mismatch');
					counter++;
					
				} else {
					$('#errorQty'+sequence).addClass('hide');
				}
			});
			
			if( counter > 0 ) {
				haserror = true;
			}
			
			if( $('#chalanNo').val() == null || $.trim( $('#chalanNo').val() ) == '' ) {
				$('.chalanNo').removeClass('hide');
				haserror = true;
			} else {
				$('.chalanNo').addClass('hide');
			}
			
			if( $('#invoiceDate').val() == null || $.trim( $('#invoiceDate').val() ) == '' ) {
				$('.invoiceDate').removeClass('hide');
				haserror = true;
			} else {
				$('.invoiceDate').addClass('hide');
			}
			
			if( $('#delivaryDate').val() == null || $.trim( $('#delivaryDate').val() ) == '' ) {
				$('.delivaryDate').removeClass('hide');
				haserror = true;
			} else {
				$('.delivaryDate').addClass('hide');
			}
			
			if( haserror == true ) {
				return;
			} else {
				$('#itemRcv').submit();
			}
		});
	});
</script>

<!-- -------------------------- -->
<script
	src="${pageContext.request.contextPath}/resources/assets/js/centralStore/csProcItemReceivedFormByWOrder.js"></script>
<!-- ------------------------------------Start of Footer-------------------------------------------------- -->
<%@include file="../common/ibcsFooter.jsp"%>