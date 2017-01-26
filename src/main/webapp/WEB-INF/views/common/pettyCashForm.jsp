<%@include file="../common/pettyCashHeader.jsp"%>

<link
	href="http://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css"
	rel="stylesheet">

<!--End of Header -->
<div class="container-fluid icon-box ashid"
	style="background-color: #858585;" id="department_div">
	<div class="col-xs-8 col-xs-offset-2"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">

		<div class="table-responsive col-xs-12">
			<table class="table table-bordered table-hover">
				<tbody>
					<tr>
						<td class="col-xs-2 success text-right" style="font-weight: bold;">Department
							Name: <input type="hidden" id="loginUserDeptId"
							value="${department.id}" />
						</td>
						<td class="col-xs-8"><select class="form-control" name="id"
							id="departmentName">
								<option value="">Select Your Department</option>
								<c:forEach items="${departmentList}" var="dept">
									<option value="${dept.id}">${dept.deptName}</option>
								</c:forEach>
						</select> <strong class="text-danger text-center hide"
							id="invalid_dept_warning"
							style="font-weight: bold; font-size: 16px;">Invalid
								Department!!! Please select correct Department.</strong></td>
					</tr>
				</tbody>
			</table>
		</div>
		<div class="col-xs-12" align="center">
			<button type="button" id="verifyDepartment"
				class="btn btn-success btn-md" style="border-radius: 6px;">
				<i class="fa fa-fw fa-lock"></i>Verify
			</button>
		</div>

	</div>
</div>
<div class="container-fluid icon-box hide"
	style="background-color: #858585;" id="lp_form_div">
	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px">
		<div class="o_form_buttons_edit col-md-2" style="display: block;">
			<a
				href="${pageContext.request.contextPath}/pettycash/pettyCashList.do"
				style="text-decoration: none;" class="btn btn-primary btn-sm"> <span
				class="glyphicon glyphicon-th-list" aria-hidden="true"></span>Petty Cash List 
			</a>

		</div>
		<div class="col-md-8">
			<h2 class="center blue"
				style="margin-top: 10px; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">Local
				Petty Cash Form</h2>
			<h4 class="center blue"
				style="margin-top: 0; font-style: italic; font-family: 'Ubuntu Condensed', sans-serif;">${department.deptName}</h4>


		</div>
	</div>

	<div class="row"
		style="background-color: white; padding: 10px; padding-left: 20px; margin-top: 10px; margin-bottom: 10px;">
		<!-- --------------------- -->
		<form method="POST" id="pettyCashForm"
			action="${pageContext.request.contextPath}/pettycash/pettyCashSave.do"
			enctype="multipart/form-data">
			<div class="oe_title">
				
				<input type="hidden" id="deptCode" name="deptCode" value="${department.id}">
				<input type="hidden" value="${pageContext.request.contextPath}"
					id="contextPath">
				<div class="col-md-6 col-sm-6">
					<div class="form-group">
						<label for="supplierName"
							class="col-sm-4 col-md-4 control-label text-right">Purchase By:</label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control" id="purchaseBy"
								style="border: 0; border-bottom: 2px ridge;" name="purchaseBy" />
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>
					
					<div class="form-group">
						<label for="supplyDate"
							class="col-sm-4 col-md-4 control-label text-right">Purchase Date:</label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control datepicker-15" id="purchaseDate"
								style="border: 0; border-bottom: 2px ridge;" name="purchaseDate" />
						</div>
					</div>
					<div class="col-sm-10">&nbsp;&nbsp;</div>

				</div>

				<div class="col-md-6 col-sm-6">

					<div class="form-group">
						<label for="purchaseNo"
							class="col-sm-4 col-md-4 control-label text-right">Invoice/Reference No:</label>
						<div class="col-sm-8 col-md-8">
							<input type="text" class="form-control"
								id="referenceNo" style="border: 0; border-bottom: 2px ridge;"
								name="referenceNo" />
						</div>
					</div>

					<div class="col-sm-10 col-md-9">&nbsp;&nbsp;</div>

					<div class="form-group">
						<label for="uploadDoc"
							class="col-sm-4 col-md-4 control-label text-right">Upload
							Doc. :</label>
						<div class="col-sm-8 col-md-8">
							<input type="file" id="uploadDoc" accept="application/pdf"
								class='form-control'
								style="border: 0; border-bottom: 2px ridge;" name="uploadDoc" />
						</div>
					</div>

				</div>

				<div class="col-md-12 col-sm-12">
					<div class="form-group" style="margin-top: 1em;">
						<p class="col-sm-12 btn btn-primary btn-sm">Purchased Item
							List</p>
						<div class="col-xs-12 table-responsive">
							<div class="table">
								<div style="width: 100%;">
									<div class="col-xs-12">
										<div class="form-group col-xs-3">
											<b>Head of Accounts</b>
										</div>
										<div class="form-group col-xs-3">
											<b>Description</b>
										</div>
										<div class="form-group col-xs-2">
											<b>Total Cost</b>
										</div>
										<div class="form-group col-xs-2">
											<b>Remarks</b>
										</div>
									</div>

									<div class="col-xs-12">
										<div class="row">
											<div class="control-group" id="fields">
												<div class="controls">
													<div class="aaa">
														<div class="col-xs-12 entry" id="myArea0">
															<div class="row">
																<div class="form-group col-xs-3">
																	<select class="form-control pettyCashCode" id="pettyCashCode0"
																		name="pettyCashCode" onchange="setpettyCashHead(this)"
																		style="border: 0; border-bottom: 2px ridge;">
																		<option disabled selected value="0">Head of Accounts</option>
																		<c:if test="${!empty pettyCashHeadList}">
																			<c:forEach items="${pettyCashHeadList}" var="pettyCash">
																				<option value="${pettyCash.pettyCashCode}">${pettyCash.pettyCashHead} [${pettyCash.pettyCashCode}]</option>
																			</c:forEach>
																		</c:if>	
																	</select>
																	<input type="hidden" id="pettyCashHead0" name="pettyCashHead" value="">
																</div>

																<div class="form-group col-xs-3">
																	<input class="form-control description" type="text" name="description"
																		id="description0" class="description"
																		style="border: 0; border-bottom: 2px ridge;">
																</div>
																<div class="form-group col-xs-2">
																	<input class="form-control totalCost" name="totalCost"
																		id="totalCost0" type="number" step="0.001"
																		style="border: 0; border-bottom: 2px ridge;" />
																	<h5 class="text-danger costError hide" id="costError0"><strong>Total cost can't 0</strong></h5>
																</div>
																<div class="form-group col-xs-2">
																	<input class="form-control remarks" name="remarks"
																		type="text" placeholder="Remarks" id="remarks0"
																		style="border: 0; border-bottom: 2px ridge;" />
																</div>
																<div class="form-group col-xs-2">
																	<button class="btn btn-success btn-add" type="button">
																		<span class="glyphicon glyphicon-plus"></span>
																	</button>
																	<button class="btn btn-danger btn-remove" type="button">
																		<span class="glyphicon glyphicon-minus"></span>
																	</button>

																</div>
															</div>

														</div>
													</div>
													<!-- ---------------------- -->


												</div>
											</div>
										</div>
									</div>
									<hr />

								</div>
							</div>
						</div>

					</div>

				</div>

				<div class="col-md-12" style="padding-top: 15px;">
					<div class="col-xs-12 col-sm-6">
						<button type="button" id="saveButton"
							style="margin-right: 10px; border-radius: 6px;"
							class="width-20 pull-right btn btn-md btn-success">
							<i class="ace-icon fa fa-save"></i> <span class="bigger-50">Save</span>
						</button>
					</div>

					<div class="col-xs-12 col-sm-6">
						<button type="reset"
							class="width-20  pull-left btn btn-md btn-danger"
							style="margin-left: 10px; border-radius: 6px;">
							<i class="ace-icon fa fa-refresh"></i> <span class="bigger-50">Reset</span>
						</button>
					</div>
				</div>
			</div>

		</form>
		<!-- --------------------- -->
	</div>
</div>

<script>
	$( document ).ready( function() {
		
		$('#saveButton').click( function() {
			var hasError = false, counter = 0;
			$('.totalCost').each( function() {
				if( $(this).val() == null || $.trim( $(this).val() ) == '' || $.trim( $(this).val() ) == '0' || $.trim( $(this).val() ) == '0.0' || $.trim( $(this).val() ) == '0.00' || $.trim( $(this).val() ) == '0.000' ) {
					
					var id = this.id;
					var name = this.name;
					var sequence = id.substr( name.length );
					$('#costError'+sequence).removeClass('hide');
					counter++;
					
				} else {
					
					var id = this.id;
					var name = this.name;
					var sequence = id.substr( name.length );
					$('#costError'+sequence).addClass('hide');
				}
			}); // jQuery .each
			
			if( counter > 0 ) {
				hasError = true;
			}
			
			if( hasError == true ) {
				return;
			} else {
				$('#pettyCashForm').submit();
			}
			
		}); // saveButton click
	});
</script>

<script
	src="${pageContext.request.contextPath}/resources/assets/js/common/pettyCash.js"></script>

<!-- Start of Footer  -->
<%@include file="../common/ibcsFooter.jsp"%>